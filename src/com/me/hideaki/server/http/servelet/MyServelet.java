package com.me.hideaki.server.http.servelet;

import java.io.PrintWriter;
import java.rmi.ServerException;

public class MyServelet implements HttpServletOnlyGet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		try {
			PrintWriter out = resp.getWriter();
			out.print("hello, servlet");
			out.print("request URI is " + req.getRequestURI());
			
		} catch (Exception e) {
			throw new ServerException("", e);
		}
	}

}
