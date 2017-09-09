package com.gongsi.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;


public class FileTransTest {

	public static void main(String[] args) {
		try {
//			fileworkTest();
//			test();
			testFileSize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 异常哟啊手动写
	 * void
	 */
	public static void fileworkTest() throws Exception{
		String[] imgTypes = {"jpg", "png"};
		final List list = Arrays.asList(imgTypes);
		Files.walkFileTree(Paths.get("E:\\resource"), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException {
//				System.out.println("访问目录之前:" + dir.toAbsolutePath());
				return super.preVisitDirectory(dir, attrs);
			}
			
			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc)
					throws IOException {
				// TODO Auto-generated method stub
//				System.out.println("访问目录之后:" + dir.toAbsolutePath());
				return super.postVisitDirectory(dir, exc);
			}
			
			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				// TODO Auto-generated method stub
				try{
					//无类型的文件，注意
					String type = file.toAbsolutePath().toString().split("\\.")[1];
					//FileOutputStream,SocketOutputStream
					
					if(list.contains(type)){
						if(file.toAbsolutePath().toString().contains("李少平.jpg")){
							System.out.println("访问文件之时:" + file.toAbsolutePath());
							FileInputStream in = new FileInputStream(file.toAbsolutePath().toString());//
							FileOutputStream out = new FileOutputStream("E:\\li5.jpg");
							byte[] bytes = new byte[1024];
							int l = 0;
							while((l = in.read(bytes)) > 0){
								out.write(bytes, 0, l);//不多不少的写入
							}
							in.close();
							out.close();
							
						}
					}
				}catch(java.lang.ArrayIndexOutOfBoundsException e){
					//System.out.println("无类型文件：" + file.toAbsolutePath());
				}
				
				return super.visitFile(file, attrs);
			}
			
			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc)
					throws IOException {
				System.out.println("访问文件失败之后:" + file.toAbsolutePath());
				return super.visitFileFailed(file, exc);
			}
	     });
		
		
		
	}
	
	/**
	 * 文件传输
	 * void
	 */
	public static void test(){
		//1.test
		//2.
		try {
			FileInputStream in = new FileInputStream("E:\\resource\\李少平.jpg");
			FileOutputStream out = new FileOutputStream("E:\\li6.jpg");
			byte[] bs = new byte[1024];
			int l = 0;
			while((l = in.read(bs)) > 0){
				out.write(bs, 0, l);
			}
			in.close();
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * void
	 * @throws IOException 
	 */
	public static void testFileSize() throws IOException{
		File file = new File("E:\\resource\\李少平.jpg");
		System.out.println(file.length());
		FileInputStream in = new FileInputStream("E:\\resource\\李少平.jpg");
		System.out.println(in.available());
		System.out.println(Long.valueOf("send;72115".split(";")[1]));
	}
}
