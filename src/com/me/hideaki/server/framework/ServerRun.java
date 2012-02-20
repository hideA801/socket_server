package com.me.hideaki.server.framework;

import java.io.IOException;
import java.net.Socket;

import com.me.hideaki.server.http.servelet.HttpServletOnlyGet;
import com.me.hideaki.server.http.servelet.HttpServletRequest;
import com.me.hideaki.server.http.servelet.HttpServletResponse;

public class ServerRun implements Runnable {

	Socket soc = null;

	public ServerRun(Socket soc) {
		this.soc = soc;
	}

	@Override
	public void run() {
		try {
			ServerHelper servHel = new ServerHelper(soc);
			HttpServletRequest req = servHel.readRequestHeader();
			HttpServletOnlyGet servelet = servHel.callServelet(req, this);
			if (servelet == null) {
				servHel.doResponse(req, null);
			} else {
				HttpServletResponse resp = servHel.methodCall(servelet, req);
				servHel.doResponse(req, resp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (soc != null) {
				try {
					soc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
