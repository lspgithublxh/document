package com.gongsi.fileTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 进程间通信：socket,共享内存--java不能,
 * 线程间通信，非共享内存方式()
 * @author Administrator
 * 2017-8-23
 * com.gongsi.fileTest
 * GSTest
 */
public class ChannelTest {

	public static void main(String[] args) throws IOException {
//		byte s = -128;
//		System.out.println(s);
//		BufferedInputStream
//		ByteBuffer
		base();
	}
	
	public static void base() throws IOException{
		FileInputStream in = new FileInputStream("E:\\a.png");
		FileChannel channel = in.getChannel();
		FileOutputStream out = new FileOutputStream("E:\\b.png");
		FileChannel channel2 = out.getChannel();
		//1.输入流和输出流的文件通道是一样的类型：
		channel.transferTo(0, channel.size(), channel2);//通道里的数据转移，可以转入，可以转出。且也是一次性的。
//		File file = new File("E:\\a.png");
		ByteBuffer b = ByteBuffer.allocate(1024);
		b.put("Hello".getBytes());
		//2.channel同样，有方向的。有是否可写的属性
		channel.write(b);
	}
}
