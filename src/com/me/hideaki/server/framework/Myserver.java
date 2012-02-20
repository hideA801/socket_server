package com.me.hideaki.server.framework;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Myserver {
	private static final int SERVER_PORT = 9090;

	public static void main(String[] args) {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(SERVER_PORT);
			while (true) {
				dispose(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void dispose(ServerSocket socket) throws IOException {
		Socket soc = socket.accept();
		Thread thread = new Thread(new ServerRun(soc));
		thread.run();
	}

}
