package ltd.forexample.prosser.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;

import ltd.forexample.prosser.service.user.authority.model.ProductOrderVO;

/**
 * 
 * TODO 
 * @author lishaoping
 * @date 2019年10月15日
 * @file DESUtil
 */
public class AESUtil {

	private static final String KEY_ALGORITHM = "AES";          
	 
	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";   
	  private static final String ENCODING = "UTF-8";

	    /**
	     * 签名算法
	     */
	    public static final String SIGN_ALGORITHMS = "SHA1PRNG";
	
	 //密钥
    private static byte[] key = "4ebuyproauthinfo".getBytes();  
    private static SecretKey secretKey = null; 
	
	static {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		    KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM, "BC"); 
		    SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
            random.setSeed(key);//"4ebuyproauthinfo".getBytes(ENCODING)
		    kg.init(128, random);
			secretKey = new SecretKeySpec(key,KEY_ALGORITHM);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String encrypt(String str) {
	   try {
		   Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC"); 
		   cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		   byte[] data = cipher.doFinal(str.getBytes());
		   return URLEncoder.encode(new String(Base64.encodeBase64(data)),"UTF-8"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return null;
	}
	
	public static String decrypt(String str) {
       Cipher cipher;
		try {
			byte[] decoData = Base64.decodeBase64(URLDecoder.decode(str, "UTF-8"));
			cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
			cipher.init(Cipher.DECRYPT_MODE, secretKey); 
	        return new String(cipher.doFinal(decoData));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "error";
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException{ 
//        String str = "{\"userId\":108, \"productId\":4, \"timestamp\":122222}";//setUserId(108L).
      String str = "{\"userId\":\"108\",\"productId\":4,\"endTime\":1585699200000}";
		ProductOrderVO po = new ProductOrderVO().setProductId(1).setEndTime(System.currentTimeMillis() + 5 * 60 * 1000);
        Gson gson = new Gson();
        str = gson.toJson(po);
        System.out.println(gson.fromJson(str, ProductOrderVO.class));
        
        try {
           for(int i = 0; i < 10; i++) {
        	 //打印原文
        	   str += 1;
               System.out.println("原文："+str); 
               String miwen = encrypt(str);
               //打印密文
               System.out.println("加密后：" + miwen);
               //解密密文
               String jiemi = decrypt(miwen);
               System.out.println(jiemi);
          }
           
        } catch (Exception e) {
           e.printStackTrace();
       } 
       
		System.out.println(encrypt(args[0]));
        System.out.println(encrypt(4333+""));
        System.out.println(decrypt("n%2FAU%2FnBIGK5xh7fJMLo3%2FhB5Ploly%2Fc9dwxtAqun5VfgDZH%2BHMXevtlcLQ8dWS8PKB8HSEDv%2B9Z%2FPjbmST1%2BQg%3D%3D"));
      } 
}
		 
