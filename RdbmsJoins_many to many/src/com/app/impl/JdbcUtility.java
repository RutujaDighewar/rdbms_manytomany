package com.app.impl;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcUtility {

	private static Connection con;
	private static String URL="jdbc:mysql://localhost:3306/rdbmsjoiinmany";
	private static String USERNAME="root";
	private static String PASSWORD="root";
	
	static {
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection(URL,USERNAME,PASSWORD);
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	public static Connection getConnection() {
		return con;
	}

	
}
