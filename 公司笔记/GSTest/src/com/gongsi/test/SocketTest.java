package com.gongsi.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
 
 /**
  * 防止图片传输过来时不能flush情景，要么传end过来，要么先在内存中开一个1025的内存。看是否读完。
  * void
  */
 public void read(InputStream in, String who){
	 byte[] b = new byte[1024];
	 int len = 0;
	 boolean read = false;
	 String re = "";
	 try {
		while(true){
			while((len = in.read(b)) != 0){//是阻塞方法
				System.out.print(read == false ? who + " received:" : "");
				System.out.println(new String(b));
				re = new String(b);//re的长度是1024，非常奇怪--可能有非常多的不可见字符
				if(re.contains("send;")){//接下来要保存图片
					long count = 0;
					Long lenx = Long.valueOf(re.split(";")[1]);
					System.out.println("--------------------------------start:" + in.available());
					FileOutputStream out = new FileOutputStream("E:\\li" + System.currentTimeMillis() + ".jpg");
					while((len = in.read(b)) != 0){//最好加一个睡眠，强行打破这个read--即休息3s后开始强行退出block
						out.write(b, 0, len);
						count +=len;
						if(count == lenx){
							break;
						}
					}
					out.close();
					System.out.println("---------------------------------end");
				}
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
			boolean ne = scan.hasNextLine();//阻塞等待输入的方法
			String line = scan.nextLine();
			 System.out.println(who + " said:" + line);
			 if(line.equals("send")){
				 File file = new File("E:\\resource\\李少平.jpg");
				 out.write(("send;" + file.length() + ";end").getBytes());//字节byte数
			 }else{
				 out.write(line.getBytes());
			 }
			 
			 out.flush();
			 //之后发送：
			 if(line.equals("send")){//发送结束标记，或者事先发送大小---
				 FileInputStream in = new FileInputStream("E:\\resource\\李少平.jpg");
					byte[] bs = new byte[1024];
					int l = 0;
					while((l = in.read(bs)) > 0){
						out.write(bs, 0, l);
					}
					in.close();
					out.flush();
			 }
		 }
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	 
 }
}
