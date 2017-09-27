package com.gongsi.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLTest {

	public static void main(String[] args) {
		try {
			//成功的方式：
//			testURL("http://localhost:9000/sgism/psm/services/ProjectServices?wsdl", "GET");
//			testURL("http://localhost:9000/sgism/workspace/main/index.jsp", "GET");
//			testURL("http://tool.oschina.net/commons", "GET"); //get ,不写数据,text/html
			//1."http://localhost:9000/sgism/workspace/main/index.jsp"
			//2.http://www.baidu.com
//			testURL("http://localhost:9000/sgism/workspace/main/index.jsp");
//			testURL("http://localhost:9000/autoserver/upload/regCode/index.jsp");
//			testURL("http://www.baidu.com", "POST");//http://www.sina.com.cn/
//			testURL("http://www.sina.com.cn/", "GET");
//			testURL("http://news.sina.com.cn/w/2017-07-04/doc-ifyhryex6022724.shtml", "GET");
//			testURL("http://localhost:9000/sgism/psm/services/ProjectServices?wsdl", "GET");
//			testURLWithData("http://localhost:9000/sgism/psm/services/ProjectServices?wsdl", "POST", "");
//			testURLWithData("http://localhost:9000/sgism/psm/services/ProjectServices", "POST", "");
//			testURLWithData("http://localhost:7878/sgism/psm/services/ProjectServices", "POST", "<s>23232131313</s>");
//			getDataFromSpringMVC("http://localhost:9000/sgism/common/tsysrole/bulidtree", "GET", "");
//			getDataFromSpringMVC("http://localhost:9000/sgism/mx/resources/themes/aero/framework.css", "GET", "");
			//ajax请求
//			getDataFromSpringMVC("http://localhost:7878/sgism/workspace/rest/workspace/login?loginName=admin&password=1&passwordTransfer=2", "GET", "");
			test();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void test() throws IOException{
		//1.压力测试
		int i = 1;
		while(true){
			getDataFromSpringMVC("http://localhost:7878/sgism/workspace/rest/workspace/login?loginName=admin&password=1&passwordTransfer=2", "GET", "");
			System.out.println("==================" + i++);
		}
	}
	
	public static void getDataFromSpringMVC(String urls , String method ,String data) throws IOException{
		URL url = new URL(urls);
		URLConnection con = url.openConnection();
		HttpURLConnection con2 = (HttpURLConnection) con;
		con2.setDoInput(true);
		con2.setDoOutput(true);
//		con2.setRequestProperty("Pragma:", "no-cache"); 
//		con2.setRequestProperty("Cache-Control", "no-cache"); 
//		con2.setRequestProperty("Content-Type", "application/json");
		con2.setRequestProperty("Accept", "text/javascript, text/html, application/xml, application/json, text/xml, */*");
		con2.setRequestProperty("X-Requested-With", "XMLHttpRequest");
		con2.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0;) JavaAjax/1.0");
		con2.setRequestMethod(method);
//		con2.connect();
		System.out.println(con2.getResponseCode());
		System.out.println(con2.getResponseMessage());
//		OutputStream out = con2.getOutputStream();
//		out.write(data.getBytes());
//		out.flush();
		//while(true)
		readMethod2(con2);
	}
	
	public static void testURLWithData(String str, String method, String data) throws IOException{
		URL url = new URL(str);
		URLConnection con = url.openConnection();
		HttpURLConnection con2 = (HttpURLConnection) con;
		con2.setDoInput(true);
		con2.setDoOutput(true);
//		con2.setUseCaches(true);
		//设置第一次请求的数据内容不被存储
		con2.setRequestProperty("Pragma:", "no-cache"); 
		con2.setRequestProperty("Cache-Control", "no-cache"); 
		con2.setRequestProperty("Content-Type", "application/soap+xml");
		con2.setRequestProperty("X-Custom-Header1", "Bar");
		con2.setRequestProperty("Origin","localhost");
		con2.setRequestMethod(method);
		con2.connect();
		
		OutputStream out = con2.getOutputStream();
		//请求体
//        String soap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:q0=\"http://ws.itcast.cn/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + 
//                      "<soapenv:Body> <q0:sayHello><arg0>aaa</arg0>  </q0:sayHello> </soapenv:Body> </soapenv:Envelope>"; http://localhost:9000/sgism/psm/services/ProjectServices
		 String soap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:q0=\"http://service.sgism.sgcc.com\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + 
                 "<soapenv:Body> <q0:saveFingerPrint>"+ data +"</q0:saveFingerPrint> </soapenv:Body> </soapenv:Envelope>";
        out.write(soap.getBytes());
        out.flush();
//        out.close();
        //读取数据
        readMethod2(con2);
	}
	
	
	public static void testURL(String str, String method) throws IOException{
		URL url = new URL(str);
		System.out.println(url.getAuthority());
		URLConnection con = url.openConnection();
		HttpURLConnection con2 = (HttpURLConnection) con;
		con2.setDoInput(true);
		con2.setDoOutput(true);
		con2.setUseCaches(true);
//		con2.setRequestProperty("Content-type", "application/x-java-serialized-object");
		con2.setRequestProperty("Content-type", "text/html");
		con2.setRequestMethod(method);
		con2.connect();
		
//		OutputStream out = con2.getOutputStream();
//		ObjectOutputStream out2 = new ObjectOutputStream(out);
//		out2.writeObject(new String("测试数据"));
//		out2.flush();
//		out2.close();
		
		readMethod2(con2);
//		readMethod1(in);
		
//		DataInputStream in2 = new DataInputStream(in);
	}

	private static void readMethod2(HttpURLConnection con2) throws IOException {
		InputStream in = con2.getInputStream();
		BufferedInputStream in2 = new BufferedInputStream(in);
		int len = 0;
		byte[] b = new byte[1024];
		while((len = in2.available()) > 0){
			in2.read(b);
			System.out.println(new String(b));
		}
	}

	private static void readMethod1(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		int len = 0;
		while((len = reader.read()) != -1){
			System.out.println(reader.readLine());
		}
	}
}
