package com.me.hideaki.server.framework;

import java.io.IOException;
import java.net.Socket;

import com.me.hideaki.server.classloader.ServletClassLoader;
import com.me.hideaki.server.http.io.HttpAccept;
import com.me.hideaki.server.http.io.HttpPush;
import com.me.hideaki.server.http.servelet.HttpServletOnlyGet;
import com.me.hideaki.server.http.servelet.HttpServletRequest;
import com.me.hideaki.server.http.servelet.HttpServletResponse;

 class ServerHelper {
	Socket soc;

	ServerHelper(Socket soc) {
		this.soc = soc;
	}

	public HttpServletRequest readRequestHeader() throws Exception {
		HttpAccept accept = new HttpAccept();
		return accept.readRequestHeader(soc);
	}

	public HttpServletOnlyGet callServelet(HttpServletRequest req, ServerRun serverObj) throws Exception {
		HttpServletOnlyGet servelet = ServletClassLoader.getInstance(req);
		return servelet;
	}

	public HttpServletResponse methodCall(HttpServletOnlyGet servelet, HttpServletRequest req) throws Exception {
		HttpServletResponse resp = new HttpServletResponse();
		if (req.method.equals("GET")) {
			servelet.doGet(req, resp);
		} else if (req.method.equals("POST")) {
			servelet.doGet(req, resp);
		} else if (req.method.equals("DELETE")) {
			;
		}
		return resp;
	}

	public void doResponse(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		HttpPush httpPush = new HttpPush();
		httpPush.response(soc,req, resp);
	}
}
