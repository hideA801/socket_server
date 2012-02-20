package com.me.hideaki.server.http.servelet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServletResponse {

	public File file;
	public Map<String, String> header = new HashMap<String, String>();
	private List<String> body = new ArrayList<String>();
	
	private int bodySize=0;
	
	public PrintWriter writer =new PrintWriter(new Writer() {
		
		@Override
		public void write(char[] chars, int arg1, int arg2) throws IOException {
			int i=chars.length+"\n".length();
			bodySize +=i;
			body.add(String.valueOf(chars));
		}
		
		@Override
		public void flush() throws IOException {
			;
		}
		
		@Override
		public void close() throws IOException {
			;
		}
	});
	
	
	public PrintWriter getWriter() {
		return writer;
	}
	public List<String> getBody() {
		return body;
	}
	
	public int getBodySize(){
		return bodySize;
	}
}
