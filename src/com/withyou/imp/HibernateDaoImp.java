package com.withyou.imp;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.withyou.dao.HibernateDAO;
import com.withyou.dao.HibernateUtil;

public class HibernateDaoImp implements HibernateDAO {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public boolean save(Object obj) {
		// TODO Auto-generated method stub
		Transaction tran = null;
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			tran = s.beginTransaction();
			s.save(obj);
			tran.commit();
			logger.info("保存成功");
			s.close();
		} catch (Exception e) {
			tran.rollback();
			logger.error("添加失败" + e.getMessage());
			System.out.println("添加失败" + e.getMessage());
			s.close();
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Object obj) {
		// TODO Auto-generated method stub
		Transaction tran = null;
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			tran = s.beginTransaction();
			s.delete(obj);
			tran.commit();
			logger.info("删除成功");
			s.close();
		} catch (Exception e) {
			tran.rollback();
			logger.error("删除失败" + e.getMessage());
			System.out.println("删除失败" + e.getMessage());
			s.close();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Object obj) {
		// TODO Auto-generated method stub
		Transaction tran = null;
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			tran = s.beginTransaction();
			s.update(obj);
			tran.commit();
			logger.info("更新成功");
			s.close();
		} catch (Exception e) {
			tran.rollback();
			logger.error("更新失败" + e.getMessage());
			System.out.println("更新失败" + e.getMessage());
			s.close();
			return false;
		}
		return true;
	}

	@Override
	public List<Object> getObjectListByHql(String hql) {
		// TODO Auto-generated method stub
		Transaction tran = null;
 		Session s = null;
		try {
			s = HibernateUtil.getSession();
			Query q = s.createQuery(hql);
			List<Object> list = q.list();
			return list;
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			s.close();
			return null;
		}
	}

	@Override
	public int updateByHql(String hql) {
		// TODO Auto-generated method stub
		Transaction tran =null;
		Session s = null;
		try{
			s = HibernateUtil.getSession();
			tran = s.beginTransaction();
			Query q = s.createQuery(hql);
			int n = q.executeUpdate();
			tran.commit();
			return n;
		}catch(Exception e){
			tran.rollback();
			s.close();
			return 0;
		}
	}

	@Override
	public Object getObjectByHql(String hql) {
		// TODO Auto-generated method stub
		Transaction tran = null;
		Session s = null;
		Object object =null;
		try {
			s = HibernateUtil.getSession();
			Query q = s.createQuery(hql);
			object = q.uniqueResult();
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			s.close();
			return null;
		}
		if (s != null) {
			s.close();
		}
		return object;
	}
}
