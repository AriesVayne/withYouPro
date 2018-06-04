package com.withyou.dao;
import java.util.List;

public interface HibernateDAO {
	public abstract boolean save(Object obj);// ���ݴ�����󱣴�

	public abstract boolean delete(Object obj);// ���ݴ������ɾ��

	public abstract boolean update(Object obj);// ���ݴ���������

	public Object getObjectByHql(String hql);// ���ݴ���sql����������
	
	public List<Object> getObjectListByHql(String hql);// ���ݴ���sql����������
	
	public abstract int updateByHql(String hql);//���ݴ����sql����
}
