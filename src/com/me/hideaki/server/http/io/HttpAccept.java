package com.me.hideaki.server.http.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import static com.me.hideaki.server.framework.ConstDifinistion.*;
import com.me.hideaki.server.http.servelet.HttpServletRequest;

public class HttpAccept {

	public HttpServletRequest readRequestHeader(Socket soc) throws Exception {
		HttpServletRequest request = new HttpServletRequest();
		InputStream inputStream = soc.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		if (reader.ready() && ((line = reader.readLine()) != null)) {
			System.out.println(line);
			getMethod(line, request);
		}
		while (reader.ready() && ((line = reader.readLine()) != null)) {
			System.out.println(line);
			if (line.equals(NULL)) {
				break;
			}
			myEntry lineParse = lineParse(line);
			request.header.put(lineParse.key, lineParse.val);
		}
		if (!request.method.equals(POST)) {
			return request;
		}
		if (request.header.get(CONTENT_LENGTH) == null) {
			throw new Exception("content-typeの指定がありません");
		}
		char[] body = new char[Integer.valueOf(request.header.get(CONTENT_LENGTH))];
		reader.read(body);
		setBody(String.valueOf(body));
		System.out.println(String.valueOf(body));
		System.out.println("読み込み終了");
		return request;
	}

	private void setBody(String string) {
		String[] split = string.split(CRLF);
		for (String line : split) {
			String[] sets = line.split(EQUAL);
			if (sets.length != 2) {
				;
			}
		}
	}

	private void getMethod(String line, HttpServletRequest request) throws Exception {
		String[] split = line.split(SPACE, 3);
		if (split.length != 3) {
			throw new Exception("メソッドのヘッダーがおかしいです");
		}
		request.method = split[0];
		if (split[1].contains(QUESTION)) {
			setGetQuery(request, split);
		} else {
			request.dir = split[1];
		}
		request.version = split[2];
	}

	protected void setGetQuery(HttpServletRequest request, String[] split) {
		//正規表現として使わないために\？でバックスラッシュをそのまま表示するのにそのためのエスケープ
		String[] split2 = split[1].split("\\?");
		request.dir = split2[0];
		String[] queries = split2[1].split(AND);
		for (String query : queries) {
			String[] set = query.split(EQUAL);
			request.query.put(set[0], set[1]);
		}
	}

	class myEntry {
		public myEntry(String key, String val) {
			super();
			this.key = key;
			this.val = val;
		}
		String key;
		String val;
	}

	private myEntry lineParse(String line) {
		String[] split = line.split(SPACE, 2);
		if (split.length < 2) {
			return new myEntry(split[0], NULL);
		}
		return new myEntry(split[0].replace(CORON, NULL), split[1]);
	}
}
