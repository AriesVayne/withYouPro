package com.withyou.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import com.withyou.dao.DatabaseDAO;
import com.withyou.dao.DatabaseUtil;

public class DatabaseDaoImp implements DatabaseDAO {
	private Connection conn = null;
	private Statement stat = null;
	private ResultSet rs = null;

	@Override
	public String getJsonByPro(String proName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getResultSetBySql(String sql) {
		// TODO Auto-generated method stub
		new DatabaseUtil();
		conn = (Connection) DatabaseUtil.getConnection();
		try {
			stat = (Statement) conn.createStatement();
			rs = stat.executeQuery(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
		return rs;
	}

	@Override
	public int updateObjBySql(String sql) {
		// TODO Auto-generated method stub
		int count = 0;
		new DatabaseUtil();
		conn = (Connection) DatabaseUtil.getConnection();
		try {
			stat = (Statement) conn.createStatement();
			count = stat.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;

	}

}
