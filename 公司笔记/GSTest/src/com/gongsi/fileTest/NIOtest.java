package com.gongsi.fileTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * 对于大并发和大量链接有用
 * 适合于每个链接的流量都低的情景，如聊天。
 * @author Administrator
 * 2017-8-31
 * com.gongsi.fileTest
 * GSTest
 */
public class NIOtest {

	public static void main(String[] args) throws IOException {
		test();
	}
	
	/**
	 * 
	 * com.gongsi.fileTest
	 * void
	 * @throws IOException 
	 */
	public static void test() throws IOException{
		//1.既可以读又可以写的文件
		RandomAccessFile file = new RandomAccessFile(new File("E:\\webservice测试数据 - 副本.txt"), "rw");
		//2.文件通道是有方向的
		FileChannel channel = file.getChannel();
		//3.缓存有标记--两个基本：起点、终点，还有一个容量    。。。。从起点开始读或者写。所以读写之前必须明确当前位置--起点--position,终点--limit..容量capacity。
		//	在写的时候limit就是容量capacity,即在内存块的最大值处
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int length = 0;
		while((length = channel.read(buffer)) > 0){
			buffer.flip();
			out.write(buffer.array());
			buffer.clear();
		}
		System.out.println(out.toString());
		file.close();
		out.close();
		//4.*不常用方法,channel可以用一个buffer数组中依次读出或者依次写入。
		//5.不常用方法,channel到channel，从管道到管道的数据转移
	}
	
	/**
	 * 对于socket通信，一个链接一个channel通道
	 * 有人有翻译的习惯---外文科技
	 * com.gongsi.fileTest
	 * void
	 * @throws IOException 
	 */
	public  void testServer() throws IOException{
		//1.开一个selector
		Selector selector = Selector.open();
		 //定义一个server
		ServerSocketChannel serverCh = ServerSocketChannel.open(); 
		serverCh.configureBlocking(false);
		serverCh.bind(new InetSocketAddress(65500));//InetSocketAddress.createUnresolved("localhost", 65535)
		//注册
		serverCh.register(selector, SelectionKey.OP_ACCEPT);
		
		//等待链接了，注册一个channel
		
		while(true){
			try{
				//监听
				selector.select();
				//获取就绪的channel
				for(SelectionKey key : selector.keys()){
					//事件类型判断--确认
					if(key.isAcceptable()){
						SelectableChannel channel = key.channel();
						ServerSocketChannel handClient =  (ServerSocketChannel) channel;
						SocketChannel clientH = handClient.accept();
						clientH.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
						//创建一个界面，界面中含输入流和输出流。
					}else if(key.isReadable()){
						SocketChannel clientH  = (SocketChannel) key.channel();
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						clientH.read(buffer);
						buffer.flip();
						System.out.println(new String(buffer.array()));
						buffer.clear();
						//触发读线程读
					}else if(key.isWritable()){//系统如何判断可以写了？会不会一直到这里来？有阻塞吗？什么状态是可写？
						//触发写线程写
						SocketChannel clientH  = (SocketChannel) key.channel();
						Scanner scanner = new Scanner(System.in);
						if(scanner.hasNext()){
							ByteBuffer buffer = ByteBuffer.allocate(1024);
							buffer.put(scanner.nextLine().getBytes());
							buffer.flip();
							clientH.write(buffer);
							buffer.clear();
						}
						
					}
				}
			}catch(Exception e){
				
			}
		}
		//2.seletor开始侦听,有事件就返回，所以返回会非常频繁，返回就绪的channel的个数--因为不是一直在调--而是处理后调，所以有就绪积累
		//   可以用在另一个线程里调用wakeUp方法使得退出阻塞
//		selector.select()
//		selector.keys();
	}
	
	/**
	 * 稍显简单
	 * com.gongsi.fileTest
	 * void
	 * @throws IOException 
	 */
	public  void testClient() throws IOException{
		Selector selector = Selector.open();
		SocketChannel client = SocketChannel.open();
		client.bind(new InetSocketAddress(65500));
		client.configureBlocking(false);
		client.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		SocketChannel ch = null;
		while(true){
			try{
				selector.select();//收集事件一次
				for(SelectionKey key : selector.keys()){
					ch = (SocketChannel) key.channel();
					if(key.isConnectable()){
						//写和读
					}else if(key.isReadable()){
						//触发读线程读
					}else if(key.isWritable()){
						//触发写线程写
					}
				}
			}catch(Exception e){
				
			}
		}
	}
	
	/**
	 * 
	 * com.gongsi.fileTest
	 * void
	 */
	public  void handlerSocket(SocketChannel handClient){
		//1.用socket进行读写，2，用本身进行读写，甚至加多线程来读或者去写
		//*创建一个界面线程，开读写线程，绑定读和写线程--即线程中的锁被界面hold，从而控制读和写。其实只有写需要控制，读----有则读
		//  写方法会阻塞
		// 目前不开界面线程
		new WriteThread(handClient).start();
		new ReadThread(handClient).start();
	}
	
	class WriteThread extends Thread{
		
		private  SocketChannel handClient;
		public WriteThread(SocketChannel handClient) {
			this.handClient = handClient;
		}
		
		@Override
		public void run() {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			Scanner reader = new Scanner(System.in);
			while(reader.hasNext()){//阻塞，会
				System.out.println("I write:");
				buffer.put(reader.nextLine().getBytes());
				try {
					buffer.flip();
					handClient.write(buffer);
					buffer.clear();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
//			ByteArrayInputStream in = new 
		}
	}
	
	class ReadThread extends Thread{
		
		private SocketChannel handClient;
		public ReadThread(SocketChannel handClient) {
			this.handClient = handClient;
		}
		@Override
		public void run() {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int len = 0;
			try {
				while((len = handClient.read(buffer)) > 0){//是否会阻塞
					System.out.println("I read:");
					buffer.flip();
					System.out.print(new String(buffer.array()));
					buffer.clear();//其实也是重新设置位置
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
