package com.gongsi.test;

import com.gongsi.test.SocketTest.ClientThread;

public class TCPTest {

	public static void main(String[] args) {
		ClientThread client = new SocketTest().new ClientThread();
		client.start();
	}
	
	
}
