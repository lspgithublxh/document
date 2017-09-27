package com.gongsi.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class JavaLanguageTest {

	public static void main(String[] args) {
		try {
			System.out.println(File.separator);
			System.out.println("E:\\back");
			System.out.println("E:" + File.separator + "back");
			System.out.println("jPg".equalsIgnoreCase("jPG"));
			System.out.println("KFF".toLowerCase(Locale.ENGLISH));
			System.out.println("壹".toLowerCase(Locale.CHINESE));
			System.out.println("----------------------");
			System.out.println(finallyTest6());
			streamClose();
			System.out.println("------------------");
			System.out.println(finallyTest());;
			System.out.println("------------------");
			System.out.println(finallyTest4());
			System.out.println("------------------");
			System.out.println(finallyTest2());
			
		} catch (IOException e) {
			System.out.println("main");
		}
	}
	
	public static void streamClose(){
		FileInputStream in = null;
		try{
			in = new FileInputStream("E:\\li1501469379494.jpg");
			System.out.println(in.available());
			in.close();
		}catch(Exception e){
			
		}
		safeClose(in);
	}

	private static void safeClose(FileInputStream in) {
		if(in != null){
			System.out.println("exception");
			try{
				in.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	private static void safeClose(FileOutputStream in) {
		if(in != null){
			System.out.println("exception");
			try{
				in.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static String finallyTest() throws IOException{
		try{
			throw new IOException("异常");
		}catch(Exception e){
			System.out.println("catch");
			return "return ";
		}finally{
			System.out.println("finally");
		}
	}
	
	public static String finallyTest4() throws IOException{
		try{
			throw new IOException("异常");
		}catch(Exception e){
			System.out.println("catch");
		}
		System.out.println("finally");
		return "return ";
	}
	
	public static String finallyTest2() throws IOException{
		try{
			throw new IOException("异常");
		}finally{
			System.out.println("finally");
		}
	}
	
	public static String finallyTest3() throws IOException{
		try{
			throw new IOException("异常");
		}finally{
			System.out.println("finally");
			return "";
		}
	}
	
	public static String finallyTest6() throws IOException{
		try{
			System.out.println("xx");;
		}finally{
			System.out.println("finally");
		}
		return "6";
	}
}
