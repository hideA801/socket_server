package com.me.hideaki.server.http.servelet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServletRequest {
	public Map<String, String> header = new HashMap<String, String>();
	public List<String> body = new ArrayList<String>();
	public String method;
	public String dir;
	public String version;
	public Map<String, String> query = new HashMap<String, String>();
	public String getRequestURI() {
		return header.get("Host")+ dir;
	}
	
	
}
