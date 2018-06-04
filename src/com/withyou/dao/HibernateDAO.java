package com.withyou.dao;
import java.util.List;

public interface HibernateDAO {
	public abstract boolean save(Object obj);// 根据传入对象保存

	public abstract boolean delete(Object obj);// 根据传入对象删除

	public abstract boolean update(Object obj);// 根据传入对象更新

	public Object getObjectByHql(String hql);// 根据传入sql搜索对象结合
	
	public List<Object> getObjectListByHql(String hql);// 根据传入sql搜索对象结合
	
	public abstract int updateByHql(String hql);//根据传入的sql更新
}
