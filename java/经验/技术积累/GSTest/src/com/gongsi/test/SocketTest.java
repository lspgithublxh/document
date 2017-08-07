package com.gongsi.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


/**
 * 文件续写可以用RandomAccessFile,而不必用FileInputStream
 * @author Administrator
 * 2017-7-14
 * com.gongsi.test
 * testTec
 */
public class SocketTest {

	public static void main(String[] args) {
		ServerThread server = new SocketTest().new ServerThread();
		server.start();
	}
	
 class ServerThread extends Thread{
	 
	 @Override
	public void run() {
		 try {
				ServerSocket socket = new ServerSocket(10086);
//				socket.bind()
				Socket server_part = socket.accept();
				System.out.println(server_part.getInetAddress().getHostAddress());
				socketHandler(server_part, "server");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
 }
 
 class ClientThread extends Thread{
	 
	 @Override
	public void run() {
		try {
			Socket socket = new Socket("localhost", 10086);
		   socketHandler(socket, "client");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 }
 
 class ReadThread extends Thread{
	 InputStream in ;
	 String who;
	 public ReadThread(InputStream in, String who) {
		this.in = in;
		this.who = who;
	}
	 
	 @Override
	public void run() {
		read(in, who);
	}
 }
 
 class WriteThread extends Thread{
	 OutputStream out;
	 String who;
	 public WriteThread(OutputStream out, String who){
		 this.out = out;
		 this.who = who;
	 }
	 
	 @Override
	public void run() {
		write(out, who);
	}
 }
 
 public void socketHandler(Socket socket, String who){
	 	WriteThread writer;
	 	ReadThread reader;
		try {
			writer = new WriteThread(socket.getOutputStream(), who);
			reader = new ReadThread(socket.getInputStream(), who);
			writer.start();
			reader.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  
 }
 
 public void read(InputStream in, String who){
	 byte[] b = new byte[1024];
	 int len = 0;
	 boolean read = false;
	 try {
		while(true){
			while((len = in.read(b)) != 0){
				System.out.print(read == false ? who + " received:" : "");
				System.out.println(new String(b));
			 }
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
 }
 
 
 public void write(OutputStream out, String who){
	 try {
		 Scanner scan = new Scanner(System.in);
		 while(true){
			boolean ne = scan.hasNextLine();
			String line = scan.nextLine();
			 System.out.println(who + " said:" + line);
			 out.write(line.getBytes());
			 out.flush();
		 }
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	 
 }
}
