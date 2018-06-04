package com.withyou.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DatabaseUtil {
	static String url = "jdbc:mysql://47.93.185.32/withyou";
	static String username = "root";
	static String password= "sunny1994";
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();// 注意，mysql驱动的版本必须要5.0以上
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static Statement getStat(Connection conn) {
		Statement stat  = null;
		try {
			stat = (Statement) conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
		return stat;
	}
}
