package com.withyou.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.withyou.bean.engineer;
import com.withyou.bean.student;
import com.withyou.bean.teacher;
import com.withyou.imp.HibernateDaoImp;

import com.withyou.bean.managers;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class doAccount
 */
@WebServlet("/doAccount")
public class doAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String act = "";
	String username = "";
	String password = "";
	String realname = "";
	String grade = "";
	String college = "";
	String major = "";
	String dormitory = "";
	private HttpSession userSession = null;
	/**
	 * Default constructor.
	 */
	public doAccount() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		
		System.out.println("----doAccount----");
		act = request.getParameter("act") != null ? request.getParameter("act") : null;
		username = request.getParameter("username") != null ? request.getParameter("username") : null;
		password = request.getParameter("password") != null ? request.getParameter("password") : null;
		realname = request.getParameter("realname") != null ? request.getParameter("realname") : null;
		grade	 = request.getParameter("grade") != null ? request.getParameter("grade") : null;
		college  = request.getParameter("college") != null ? request.getParameter("college") : null;
		major 	 = request.getParameter("major") != null ? request.getParameter("major") : null;
		dormitory = request.getParameter("dormitory") != null ? request.getParameter("dormitory") : null;
		System.out.println("----act:"+act+"----");
		if(act!=null & !act.equals("")){
			//学生登录
			if(act.equals("login_stu")) {
				System.out.println("----login_stu----incoming");
				JSONObject rtnCode =  new JSONObject();
				String hql = "from com.withyou.bean.student where username = " + username;
				student st = (student) new HibernateDaoImp().getObjectByHql(hql);
				if (st == null) {
					rtnCode.put("rtnCode", "1000");
					pw.write(rtnCode.toString());
					System.out.println("Query failre");
					return;
				}
				if(password.equals(st.getPassword())) {
					userSession = request.getSession();
					userSession.setAttribute("user_student", username);
					rtnCode.put("rtnCode", "0");
				}else {
					rtnCode.put("rtnCode", "1001");
				}
				System.out.println("Query success" + st.getPassword());
				pw.write(rtnCode.toString());
			}
			//学生注册
			if(act.equals("regis_stu")) {
				System.out.println("----regis_stu----incoming");
				JSONObject rtnCode =  new JSONObject();
				String hql = "from com.withyou.bean.student where username = " + username;
				student st = (student) new HibernateDaoImp().getObjectByHql(hql);
				if (st != null) {
					rtnCode.put("rtnCode", "10002");//帐号已经存在
					System.out.println("Account Exits");
				}else {
					student regstu= new student();
					regstu.setUsername(username);
					regstu.setPassword(password);
					regstu.setRealname(realname);
					regstu.setGrade(grade);
					regstu.setCollege(college);
					regstu.setMajor(major);
					regstu.setDormitory(dormitory);
					if(new HibernateDaoImp().save(regstu)) {
						rtnCode.put("rtnCode", "0");
						System.out.println("Regis Succ");
					}else {
						rtnCode.put("rtnCode", "10003");//注册失败
						System.out.println("Regis fail");
					}
				}
				pw.write(rtnCode.toString());
			}
			//工程师登录
			if (act.equals("login_engineer")) {
				System.out.println("doAccount----login_engineer");
				JSONObject rtnCode =  new JSONObject();
				String hql = "from com.withyou.bean.engineer where username = '" + username + "'";
				engineer engi = new engineer();
				engi = (engineer) new HibernateDaoImp().getObjectByHql(hql);
				if (engi == null) {
					rtnCode.put("rtnCode", "1000");
					pw.write(rtnCode.toString());
					System.out.println("Query failre");
					return;
				} else {
					if (password.equals(engi.getPassword())) {
						userSession = request.getSession();
						userSession.setAttribute("user_engineer", username);
						rtnCode.put("rtnCode", "0");
					} else {
						rtnCode.put("rtnCode", "1001");
					}
					System.out.println("Query success" + engi.getPassword());
					pw.write(rtnCode.toString());
				}
			}
			//工程师注册
			if (act.equals("reg_engineer")) {
				System.out.println("doAccount----reg_engineer");
				JSONObject rtnCode =  new JSONObject();
				String hql = "from com.withyou.bean.engineer where username = '"+username+"'";
				engineer engi = new engineer();
				engi = (engineer)new HibernateDaoImp().getObjectByHql(hql);
				if(engi != null) {
					rtnCode.put("rtnCode", "10002");//帐号已经存在
					System.out.println("Account Exits");
				}else {
					engi = new engineer();
					engi.setUsername(username);
					engi.setPassword(password);
					engi.setRealname(realname);
					if(new HibernateDaoImp().save(engi)) {
						rtnCode.put("rtnCode", "0");
						System.out.println("Regis Succ");
					}else {
						rtnCode.put("rtnCode", "10003");//注册失败
						System.out.println("Regis fail");
					}
				}
				pw.write(rtnCode.toString());
			}
			//后台登录
			if (act.equals("login_manager")) {
				System.out.println("doAccount----login_manager");
				JSONObject rtnCode = new JSONObject();
				userSession  = request.getSession();
				String hql = "from com.withyou.bean.managers where username = '"+username+"'";
				System.out.println(hql);
				managers man = (managers) new HibernateDaoImp().getObjectByHql(hql);
				if(man != null ) {
					if(password.equals(man.getPassword())) {
						rtnCode.put("rtnCode", "0");
						userSession.setAttribute("user_manager", username);
						System.out.println("login_manager succ");
					}else {
						rtnCode.put("rtnCode", "1001");
						System.out.println("login_admin wrong pwd");
					}
				}else {
					System.out.println("login_manager account null");
					rtnCode.put("rtnCode", "1000");
				}
				pw.write(rtnCode.toString());
			}
			//教师登录
			if (act.equals("login_teac")) {
				System.out.println("doAccount----login_teac");
				JSONObject rtnCode = new JSONObject();
				userSession  = request.getSession();
				String hql = "from com.withyou.bean.teacher where username = '"+username+"'";
				System.out.println(hql);
				teacher tea = (teacher) new HibernateDaoImp().getObjectByHql(hql);
				if(tea != null ) {
					if(password.equals(tea.getPassword())) {
						rtnCode.put("rtnCode", "0");
						userSession.setAttribute("user_teacher", username);
						System.out.println("login_teac succ");
					}else {
						rtnCode.put("rtnCode", "1001");
						System.out.println("login_teac wrong pwd");
					}
				}else {
					System.out.println("login_teac account null");
					rtnCode.put("rtnCode", "1000");
				}
				pw.write(rtnCode.toString());
			}
		}else {
			System.out.println("----传入参数为空，程序停止----");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
