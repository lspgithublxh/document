package com.gongsi.test;

import java.net.URL;
import java.util.List;

import org.codehaus.xfire.client.Client;

/***
 * 包里的jar包，没有加载，没有找到。里面虽然有合适的包。如何引入这个。
 * @author Administrator
 * 2017-8-29
 * com.gongsi.test
 * GSTest
 */
public class ClassLoaderTest {
	public static String url = "http://127.0.0.1:9001/sgism/psm/services/ProjectServices?wsdl";
	static {
		System.setProperty("java.class.path", "E:\\UAP\\w3\\GSTest\\bin;E:\\resource\\UAP\\UAP\\sguap-server\\WEB-INF\\repository\\platform\\Version2.0.0\\thirdparty\\org.codehaus.xfire_1.2.6.jar;E:\\resource\\UAP\\UAP\\sguap-server\\WEB-INF\\repository\\platform\\Version2.0.0\\thirdparty\\org.apache.commons.logging_1.1.1.jar;E:\\UAP\\w3\\.metadata\\.plugins\\org.eclipse.pde.core\\.external_libraries\\org.codehaus.xfire_1.2.6\\lib\\wsdl4j-1.6.1.jar");

	}
	
	public static void main(String[] args) {
		System.out.println(ClassLoaderTest.class.getClassLoader());
		System.out.println(Thread.currentThread().getContextClassLoader());
		System.out.println(System.getProperty("java.class.path"));
		Object[] obs= null;
		try {
			URL u = new URL(url);
			Client client = new Client(u);
			obs = client.invoke("loadLED", new Object[]{"sdfsf"});
//			obs = client.invoke("loadPassData", new Object[]{para});
			System.out.println(obs[0].toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		testClassLoader();
	}
	
	/**
	 * 查看它的类加载器就知道了它的路径。它所在哪里
	 * 交给父级加载，防止父级已经加载而重复加载。即父级加载过了，子级不能再加载（因为类名和加载器统一）（也导致一些类加载后，后面写的类--但不同方法但需要则不能加载了，即加载了错误的）。所以要专门将某些类先加载（使用bootstrapClassloader加载，，即运行时加参数-Xbootclasspath  -D java.ext.dirs）
	 * 不同加载器，加载同一个类，是不同类型。
	 * 同一个加载器，加载了一个类
	 * com.gongsi.test
	 * void
	 */
	public static void testClassLoader(){
		//抽象类，没有继承任何
		 ClassLoader clasL = ClassLoaderTest.class.getClassLoader();
		while(null != clasL.getParent()){
			 System.out.println(clasL.getParent());
			 clasL = clasL.getParent();
		}
		System.out.println("----------------------------");
		System.out.println(System.getProperty("sun.boot.class.path"));
		System.out.println(System.getProperty("java.ext.dirs"));
		System.out.println(System.getProperty("java.class.path"));
		System.out.println("----------------------------");
		for(String p : System.getProperty("java.class.path").split(";")){
			System.out.println(p);
		}
		System.out.println();
		//线程的classloder是为了区分加载环境，比如web服务器上的不同项目，不同用户，有不同线程时。加载器实例不同，也不同。
		//web的加载器则相反，先用户的类
		//osgi每个bundle有一个自己的clasloader-加载自己内部的。对于依赖的则交给相应的模块，也有父类加载器。
		//每个模块的加载器可以用当前线程加载器获取和设置
		//2.classpath环境下的jar包中的lib下的jar，不能直接加载，运行jar中代码时也不能加载--因为classpath变了。只能专门统一lib
		//3.jar加载顺序重要，需要调整。防止同类而不同方法实现，而加载了错误的类报错。
		System.out.println(Thread.currentThread().getContextClassLoader());//输出是appclassloader
	}
	
	/**
	 * Object -- ClassLoader -- SecureClassLoader--UrlClassLoader--ExtClassLoader
	 * Object -- ClassLoader -- SecureClassLoader--UrlClassLoader--AppClassLoader
	 * 一个类的class加载到内存中时，其class到了内存，其classLoader,和该classLoader的parent()之类的已经确定。---即已经绑定。所以直接输出的。
	 * 何时开始查找，即java启动运行时--参数中。在解释字节码时---生成class字节码到jvm，在运行时字符串为类的全名路径
	 * 
	 * 1.加载一个类:需要：类名字符串--类class文件的字节数组
	 * @author Administrator
	 * 2017-8-30
	 * com.gongsi.test
	 * GSTest
	 */
	class MyClassLoader extends ClassLoader{
		/**
		 * 1。findLoadedClass --是否已经加载
		 * 2.loadClass  ---交父类
		 * 3.findClass ---自己去找，并加载为Class对象
		 */
		@Override
		protected Package definePackage(String name, String specTitle,
				String specVersion, String specVendor, String implTitle,
				String implVersion, String implVendor, URL sealBase)
				throws IllegalArgumentException {
			// TODO Auto-generated method stub
			return super.definePackage(name, specTitle, specVersion, specVendor, implTitle,
					implVersion, implVendor, sealBase);
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
