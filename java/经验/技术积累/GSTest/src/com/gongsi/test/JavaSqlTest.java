package com.gongsi.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JavaSqlTest {

	public static void main(String[] args) {
		test();
	}
	
	public static void test(){
		try {
			  Class.forName("org.gjt.mm.mysql.Driver");
			  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sbd", "root", "lsp");
			  Statement state =  con.createStatement();
			  ResultSet s = state.executeQuery("select count(*) from prj_info");
			  while(s.next()){
				  String str = s.getString(1);
				  System.out.println(str);
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
