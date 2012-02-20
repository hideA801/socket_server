package com.me.hideaki.server.http.io;

import static com.me.hideaki.server.framework.ConstDifinistion.CONTENT_TYPE;
import static com.me.hideaki.server.framework.ConstDifinistion.CRLF;
import static com.me.hideaki.server.framework.ConstDifinistion.NOT_FOUND;
import static com.me.hideaki.server.framework.ConstDifinistion.SJIS;
import static com.me.hideaki.server.framework.ConstDifinistion.UTF_8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import com.me.hideaki.server.http.servelet.HttpServletRequest;
import com.me.hideaki.server.http.servelet.HttpServletResponse;

public class HttpPush {
	public void response(Socket soc,HttpServletRequest req, HttpServletResponse resp) throws IOException{
		if(resp==null){
			notFindDispose(req, resp, soc);
		}else{
			findDispose(req, resp, soc);
		}
	}
	
	
	public void writeBody(BufferedWriter writer, File file) throws IOException {
		Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF_8)));
		while (scanner.hasNext()) {
			String nextLine = scanner.nextLine();
			writer.write(nextLine);
		}
		if (scanner != null) {
			scanner.close();
		}
	}

	public void notFindDispose(HttpServletRequest req, HttpServletResponse resp, Socket soc) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
		writer.write(req.version + " " + NOT_FOUND+CRLF);
		writer.write(CONTENT_TYPE+CRLF);
		writer.write(CRLF);
		writer.write(NOT_FOUND);
		writer.flush();
		if (writer != null) {
			writer.close();
		}
	}

	 public void findDispose(HttpServletRequest req, HttpServletResponse resp, Socket soc)
			throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream(), UTF_8));
		writeHeader(req, resp,writer);
		writer.write(CRLF);
		List<String> body = resp.getBody();
		for (String line : body) {
			System.out.println(line);
			writer.write(line+CRLF);
		}

		writer.flush();
		if (writer != null) {
			writer.close();
		}
	}


	protected void writeHeader(HttpServletRequest req, HttpServletResponse resp, BufferedWriter writer) throws IOException {
		writer.write(req.version + " 200 OK"+CRLF);
		writer.write(CONTENT_TYPE+UTF_8+CRLF);
		writer.write("Content-Length: "+resp.getBodySize()+CRLF);
		//writer.write("");
	}
}
