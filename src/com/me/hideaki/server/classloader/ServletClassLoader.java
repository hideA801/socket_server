package com.me.hideaki.server.classloader;

import com.me.hideaki.server.http.servelet.HttpServletOnlyGet;
import com.me.hideaki.server.http.servelet.HttpServletRequest;

public class ServletClassLoader {

	public static HttpServletOnlyGet getInstance(HttpServletRequest req) throws Exception {
		ClassMapping mapping = new ClassMapping();
		
		
		return mapping.load(req.dir);
		
	}

}
