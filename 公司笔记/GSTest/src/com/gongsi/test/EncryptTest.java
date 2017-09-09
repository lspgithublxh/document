package com.gongsi.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptTest {

	public static void main(String[] args) {
		//4e4d6c332b6fe62a63afe56171fd3725
		//4e4d6c332b6fe62a63afe56171fd3725
		tts();
		System.out.println();
		System.out.println(md5JS("1"));
		System.out.println(md5Encode("1"));;
	}

	private static void tts() {
		try {
			MessageDigest d = MessageDigest.getInstance("MD5");
			d.update("1".getBytes());
			byte[] b = d.digest();
			for(byte i : b){
				System.out.print(Integer.toHexString(i & 0xff));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static String md5Encode(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch(Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        byte[] byteArray = null;  
        try {  
            byteArray = inStr.getBytes("UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        byte[] md5Bytes = md5.digest(byteArray);  System.out.println(md5Bytes.length);
        StringBuffer hexValue = new StringBuffer(); 
        StringBuffer hexValue2 = new StringBuffer(); 
        for(int i=0;i<md5Bytes.length;i++){  
            int val = md5Bytes[i] & 0xff;  
            if(val<16){  
                hexValue.append("0");  
            }  
            hexValue.append(Integer.toHexString(val));  
            hexValue2.append(Integer.toHexString(val));
        }
        System.out.println(hexValue2.toString()+"--------");
        return hexValue.toString();  
    }  
	
	public static String md5JS(String text){
		StringBuffer buffer = new StringBuffer();
		String hex_tab = "0123456789abcdef";
		for (int i = 0; i < text.length(); i += 1) {
            char x = text.charAt(i);
            buffer.append(hex_tab.charAt((x >>> 4) & 0x0F) +
                hex_tab.charAt(x & 0x0F));
        }
		return buffer.toString();
	}
}
