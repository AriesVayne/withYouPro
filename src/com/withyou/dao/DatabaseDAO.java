package com.withyou.dao;

import java.sql.ResultSet;

public interface DatabaseDAO {
	public abstract String getJsonByPro(String proName);// ���ݴ���洢���̷��ز���
	public abstract ResultSet getResultSetBySql(String sql);// ���ݴ���sql���ؽ����
	public abstract int updateObjBySql(String sql);//���ݴ���sql�������ݿ����
}
