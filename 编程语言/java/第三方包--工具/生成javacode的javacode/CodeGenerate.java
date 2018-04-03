package com.bj58.fang.codeGenerate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CodeGenerate {

	public static String path = "D:\\test\\createClass";
	
	public static void main(String[] args) throws IOException {
		String path2 = "";
		new CodeGenerate().test(path, path2);
		//加进去，反过来写
	}
	
	public void test(String targetDir, String filePath) throws IOException {
		System.out.println(CodeGenerate.class.getResource("/").getPath());
		System.out.println(new File("").getCanonicalPath());
		System.out.println(new File("").getAbsolutePath());
		System.out.println(System.getProperty("user.dir"));
		System.out.println(this.getClass().getResource("").getPath());
		filePath = this.getClass().getResource("").getPath();
		filePath = filePath.replace("/target/classes", "/src/main/java") + "/json.txt";
//		System.out.println(this.getClass().getClassLoader().getResource("json.txt"));
//		System.out.println(System.getProperty("java.class.path"));
//		System.out.println(CodeGenerate.class.getClassLoader().getResource("json.txt"));
		String content = FileUtils.readFileToString(new File(filePath), "UTF-8");
		JSONObject object = JSONObject.parseObject(content);
		parseObject(object, "", "a");
		StringBuilder builder = new StringBuilder();
		builder.append("public class Aconstruct{ \r\n\r\n\t public static void main(String[] args) throws IOException {\r\n");
		for(String str : buildNList) {
			builder.append(str);
		}
		for(String str : buildList) {
			builder.append(str);
		}
		builder.append("\t}\r\n}");
		File file = new File(path + "\\Aconstruct" + ".java");
		if(!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter writer = new FileWriter(file);
		writer.write(builder.toString());
		
		writer.close();
	}
	
	public List<String> buildNList = new ArrayList<String>();
	public List<String> buildList = new ArrayList<String>();
	
	public void parseObject(JSONObject object, String className, String variableName) throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append("public class A" + className + " {\r\n");
		Map<String, JSONObject> jsMap = new HashMap<String, JSONObject>();
		int index = 1;
		Map<String,String> attrMap = new HashMap<String,String>();
		Map<String,String> variaMap = new HashMap<String,String>();
		buildNList.add("\t\t A" + className + " " + variableName + " = new " + "A" + className + "();\r\n");
		for(Entry<String, Object>  entry: object.entrySet()) {
//			System.out.println(entry.getKey() + "----" + entry.getValue().getClass());
			//输出
			String vClass =	entry.getValue().getClass().toString();
			
			String attrName = null;
			if(vClass.endsWith(".JSONObject")) {
				attrName = className + "_" + index++;
				jsMap.put(attrName, (JSONObject) entry.getValue());
				variaMap.put(attrName, entry.getKey());
				attrName ="A" + attrName;
//				builder.append("\tpublic A" + attrName + "\t" + entry.getKey() + ";\r\n");
			}else if(vClass.endsWith(".JSONArray")) {
				attrName = className + "_" + index++;
				jsMap.put(attrName, ((JSONArray)entry.getValue()).getJSONObject(0));
				variaMap.put(attrName, entry.getKey());
				attrName = "A" + attrName + "[]";
//				builder.append("\tpublic A" + attrName + "[]\t" + entry.getKey() + ";\r\n");
			}else {
				attrName = vClass.split(" ")[1].split("\\.")[2];
//				builder.append("\tpublic " + vClass.split(" ")[1].split("\\.")[2] + "\t" + entry.getKey() + ";\r\n");
			}
			builder.append("\tpublic " + attrName + "\t" + entry.getKey() + ";\r\n");
			attrMap.put(attrName, entry.getKey());
			String name = entry.getKey();
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			buildList.add("\t\t" + variableName + ".set" + name + "(" + entry.getKey() + ");\r\n");
		}
		builder.append("\r\n");
		for(Entry<String,String> attr: attrMap.entrySet()) {
			String name = attr.getValue();
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			builder.append("\tpublic " + attr.getKey() + " get" + name + "(){\r\n \t\treturn this." + attr.getValue() + ";\r\n\t}\r\n\r\n" );
			builder.append("\tpublic void set" + name + "( " + attr.getKey() + " " + attr.getValue() + "){\r\n \t\tthis." + attr.getValue() + "=" + attr.getValue() + ";\r\n\t}\r\n\r\n");
		}
		builder.append("}\r\n");
		System.out.println(builder.toString());
		File file = new File(path + "\\A" + className + ".java");
		if(!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file);
		writer.write(builder.toString());
		writer.close();
		//递归
		for(Entry<String, JSONObject> obj : jsMap.entrySet()) {
			parseObject(obj.getValue(), obj.getKey(), variaMap.get(obj.getKey()));
		}
		
	}
}
