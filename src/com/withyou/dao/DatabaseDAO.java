package com.withyou.dao;

import java.sql.ResultSet;

public interface DatabaseDAO {
	public abstract String getJsonByPro(String proName);// 根据传入存储过程返回参数
	public abstract ResultSet getResultSetBySql(String sql);// 根据传入sql返回结果集
	public abstract int updateObjBySql(String sql);//根据传入sql更新数据库对象
}
