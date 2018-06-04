package com.withyou.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.withyou.bean.repairinfo;
import com.withyou.imp.HibernateDaoImp;

import com.withyou.bean.engineer;

import com.withyou.imp.DatabaseDaoImp;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class doRepair
 */
@WebServlet("/doRepair")
public class doRepair extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String varRepairName = null;
	private String varConnPhone = null;
	private String varRepairAdd = null;
	private String varRepairDev = null;
	private String varRepairDesc = null;
	private String varMethod = null;
	private String devicePicture = null;
	private String FileDir = null;
	private String varFileDir = null;
	private String operateObj = null;
	private String varStatus = null;
	private String varUuid = null;
	private String varEdt = null;
	private String varBdt = null;
	private String repairflag = null;
	private String feedback = null;
	private String varRepairid = null;
	private HttpSession userSession = null;
	private JSONObject entity = null;
	private JSONObject entitys = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public doRepair() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("---doRepair---");
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		varMethod = request.getParameter("act") != null ? request.getParameter("act") : null;
		FileDir = request.getParameter("FileDir") != null ? request.getParameter("FileDir") : null;
		varUuid = request.getParameter("varUuid") != null ? request.getParameter("varUuid") : null;
		varRepairName = request.getParameter("varRepairName") != null ? request.getParameter("varRepairName") : null;
		varConnPhone = request.getParameter("varConnPhone") != null ? request.getParameter("varConnPhone") : null;
		varRepairAdd = request.getParameter("varRepairAdd") != null ? request.getParameter("varRepairAdd") : null;
		varRepairDev = request.getParameter("varRepairDev") != null ? request.getParameter("varRepairDev") : null;
		varRepairDesc = request.getParameter("varRepairDesc") != null ? request.getParameter("varRepairDesc") : null;
		varFileDir = request.getParameter("varFileDir") != null ? request.getParameter("varFileDir") : null;
		// System.out.println("varMethod:" + varMethod + "--varRepairName:" +
		// varRepairName);
		// 返回一个JSONArray对象  
		if(varMethod == null) {
        	return;
        }else {
        	System.out.println("-----Start------传入方法："+varMethod);      	
        	userSession = request.getSession();
			if (varMethod.equals("handleRepair")) {
				System.out.println("---handleRepair-withyou--");
				JSONArray jsonArray = new JSONArray();
				String repairUUID = UUID.randomUUID().toString();
				String path = getServletContext().getRealPath("/") + "FileUpload\\" + varFileDir;
				String devicePicture = saveFile(request, response, repairUUID, path);
				if (devicePicture != null) {
					repairinfo ne = new repairinfo();
					if (userSession.getAttribute("user_student") != null) {
						ne.setUploadid(userSession.getAttribute("user_student").toString());
					}
					ne.setUuid(repairUUID);
					ne.setRealname(varRepairName);
					ne.setConnphone(varConnPhone);
					ne.setLocation(varRepairAdd);
					ne.setDevice(varRepairDev);
					ne.setRepairdesc(varRepairDesc);
					ne.setPicture(devicePicture);
					ne.setRepairflag("1");
					if (new HibernateDaoImp().save(ne)) {
						pw.print("<script>alert('信息上传成功');window.history.back();</script>");
					} else {
						pw.print("<script>alert('信息上传失败，请稍后重试');window.history.back();</script>");
					}
				} else {
					pw.print("<script>alert('图片上传失败，请稍后重试');window.history.back();</script>");
					return;
				}
			}
			//查询报修信息列表，操作对象学生，工程师
			if (varMethod.equals("getRepairList")) {
				System.out.println("---getRepairList---");
				JSONArray jsonArray = new JSONArray();
				entity = new JSONObject();
				entitys = new JSONObject();
				operateObj = request.getParameter("varObject") != null ? request.getParameter("varObject") : null;
				varStatus = request.getParameter("varStatus") != null ? request.getParameter("varStatus") : null;
				varBdt = request.getParameter("BDT") != null ? request.getParameter("BDT") : null;
				varEdt = request.getParameter("EDT") != null ? request.getParameter("EDT") : null;
				if(operateObj.equals("stu")) {
					String stuid = userSession.getAttribute("user_student").toString();
					String hql ="from repairinfo where uploadid = " + stuid ;
					if(varStatus != null&& !varStatus.equals("")) {
						if(varStatus.equals("1")) {//查询未解决的
							hql+=" and repairflag != 0 and repairflag !=2";
						}else {
							hql+=" and repairflag = " + varStatus;
						}
					}
					System.out.println(hql);
					ArrayList<repairinfo> list = (ArrayList) new HibernateDaoImp().getObjectListByHql(hql);
					if(list == null) {
						entity.put("rtnCode","1002");
					}else {
						entity.put("rtnCode","0");
						for(Object t:list) {
							repairinfo temp = (repairinfo) t;
							entitys.put("uuid",temp.getUuid());
							entitys.put("realname",temp.getRealname());
							entitys.put("connphone",temp.getConnphone());
							entitys.put("location",temp.getLocation());
							entitys.put("device",temp.getDevice());
							entitys.put("repairdesc",temp.getRepairdesc());
							entitys.put("picture",temp.getPicture());
							entitys.put("repairid",temp.getRepairid());
							entitys.put("uploadid",temp.getUploadid());
							entitys.put("uploadtime",temp.getUploadtime());
							entitys.put("repairpeople",temp.getRepairpeople());
							entitys.put("repairflag",temp.getRepairflag());
							entitys.put("feedback",temp.getFeedback());
							jsonArray.add(entitys);
						}
						entity.put("rs", jsonArray);
					}
				}
				if(operateObj.equals("engineer")) {
					String engineerid = userSession.getAttribute("user_engineer").toString();
					String hql ="from repairinfo where repairid = " + engineerid ;
					if(varStatus != null&& !varStatus.equals("")) {
						hql+=" and repairflag = " + varStatus;
					}
					if(varBdt != null && varEdt == null) {
						hql+=" and  uploadtime >='" +varBdt+"'";
					}
					if(varBdt == null && varEdt != null) {
						hql+=" and  uploadtime <='" +varBdt+"'";
					}
					if(varBdt != null && varEdt != null) {
						hql+=" and uploadtime <= '"+varEdt+"' and uploadtime >='" +varBdt+"'";
					}
					
					System.out.println(hql);
					ArrayList<repairinfo> list = (ArrayList) new HibernateDaoImp().getObjectListByHql(hql);
					if(list == null) {
						entity.put("rtnCode","1002");
					}else {
						entity.put("rtnCode","0");
						for(Object t:list) {
							repairinfo temp = (repairinfo) t;
							entitys.put("uuid",temp.getUuid());
							entitys.put("realname",temp.getRealname());
							entitys.put("connphone",temp.getConnphone());
							entitys.put("location",temp.getLocation());
							entitys.put("device",temp.getDevice());
							entitys.put("repairdesc",temp.getRepairdesc());
							entitys.put("picture",temp.getPicture());
							entitys.put("repairid",temp.getRepairid());
							entitys.put("uploadid",temp.getUploadid());
							entitys.put("uploadtime",temp.getUploadtime());
							entitys.put("repairpeople",temp.getRepairpeople());
							entitys.put("repairflag",temp.getRepairflag());
							entitys.put("feedback",temp.getFeedback());
							jsonArray.add(entitys);
						}
						entity.put("rs", jsonArray);
					}
				}
				if(operateObj.equals("manager")) {
					String hql ="from repairinfo " ;
					System.out.println(hql);
					ArrayList<repairinfo> list = (ArrayList) new HibernateDaoImp().getObjectListByHql(hql);
					if(list == null) {
						entity.put("rtnCode","1002");
					}else {
						entity.put("rtnCode","0");
						for(Object t:list) {
							repairinfo temp = (repairinfo) t;
							entitys.put("uuid",temp.getUuid());
							entitys.put("realname",temp.getRealname());
							entitys.put("connphone",temp.getConnphone());
							entitys.put("location",temp.getLocation());
							entitys.put("device",temp.getDevice());
							entitys.put("repairdesc",temp.getRepairdesc());
							entitys.put("picture",temp.getPicture());
							entitys.put("repairid",temp.getRepairid());
							entitys.put("uploadid",temp.getUploadid());
							entitys.put("uploadtime",temp.getUploadtime());
							entitys.put("repairpeople",temp.getRepairpeople());
							entitys.put("repairflag",temp.getRepairflag());
							entitys.put("feedback",temp.getFeedback());
							jsonArray.add(entitys);
						}
						entity.put("rs", jsonArray);
					}
				}
				pw.write(entity.toString());
				System.out.println(entity.toString());
			}
			//通过uuid查询报修信息详情，操作对象学生和工程师
			if (varMethod.equals("repairDetail")) {
				System.out.println("---repairDetail---");
				JSONArray jsonArray = new JSONArray();
				if(varUuid!=null) {
					entity = new JSONObject();
					entitys = new JSONObject();
					String hql="from repairinfo where uuid = '" + varUuid+"'";
					repairinfo temp = new repairinfo();
					temp = (repairinfo) new HibernateDaoImp().getObjectByHql(hql);
					if(temp!=null) {
						entity.put("rtnCode", "0");
						entitys.put("uuid",temp.getUuid());
						entitys.put("realname",temp.getRealname());
						entitys.put("connphone",temp.getConnphone());
						entitys.put("location",temp.getLocation());
						entitys.put("device",temp.getDevice());
						entitys.put("repairdesc",temp.getRepairdesc());
						entitys.put("picture",temp.getPicture());
						entitys.put("repairid",temp.getRepairid());
						entitys.put("uploadid",temp.getUploadid());
						entitys.put("repairpeople",temp.getRepairpeople());
						entitys.put("repairflag",temp.getRepairflag());
						entitys.put("feedback",temp.getFeedback());
						jsonArray.add(entitys);
					}else {
						System.out.println("---noObject---");
						entity.put("rtnCode", "1000");
					}
					entity.put("rs", jsonArray);
					pw.write(entity.toString());
					System.out.println(entity.toString());
				}else {
					System.out.println("---varUuid!=null---");
					return;
				}
			}
			//查询工程师工单信息，操作对象工程师
			if (varMethod.equals("engiSnapInfo")) {
				System.out.println("---engiSnapInfo---");
				String engineerid = userSession.getAttribute("user_engineer").toString();
				JSONArray jsonArray = new JSONArray();
				entity = new JSONObject();
				entitys = new JSONObject();
				String all = null;
				String done = null;
				String undone = null;
				if(engineerid!=null && !engineerid.equals("")) {
					String sql ="select count(*) from t_repairinfo where repairid = " + engineerid +" and repairflag = '0'";//已处理
					System.out.println(sql);
					try {
						ResultSet rs = new DatabaseDaoImp().getResultSetBySql(sql);
						if(rs.next()) {
							entity.put("rtnCode", "0");
							done = rs.getString(1);
						}
						sql ="select count(*) from t_repairinfo where repairid = " + engineerid +" and repairflag = '1' or repairflag= '3'";//未处理
						rs = new DatabaseDaoImp().getResultSetBySql(sql);
						if(rs.next()) {
							undone = rs.getString(1);
						}
						sql ="select count(*) from t_repairinfo where repairid = " + engineerid ;//一共
						rs = new DatabaseDaoImp().getResultSetBySql(sql);
						if(rs.next()) {
							all = rs.getString(1);
						}
						entitys.put("all",all);
						entitys.put("done",done);
						entitys.put("undone",undone);
						jsonArray.add(entitys);
						entity.put("rs", jsonArray);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					entity.put("rtnCode", "1009");
				}
				pw.write(entity.toString());
				System.out.println(entity.toString());
			}
			//更新报修，操作对象工程师，后台管理
			if (varMethod.equals("updateInfo")) {
				JSONArray jsonArray = new JSONArray();
				entity = new JSONObject();
				entitys = new JSONObject();
				operateObj = request.getParameter("varObject") != null ? request.getParameter("varObject") : null;
				repairflag = request.getParameter("repairflag") != null ? request.getParameter("repairflag") : null;
				feedback = request.getParameter("feedback") != null ? request.getParameter("feedback") : null;
				varRepairid = request.getParameter("varRepairid") != null ? request.getParameter("varRepairid") : null;
				if(operateObj.equals("engineer")) {
					String sql ="update t_repairinfo set repairflag = '"+repairflag+"',feedback = '"+feedback+"' where uuid = '"+varUuid+"'"; 
					System.out.println(sql);
					int a = new DatabaseDaoImp().updateObjBySql(sql);
					if(a>0) {
						System.out.println("----updateInfo--更新对象成功"+feedback+"----sql"+sql);
						entity.put("rtnCode","0");
					}else {
						entity.put("rtnCode","1000");
					}
					pw.write(entity.toString());
					System.out.println(entity.toString());
				}
				if(operateObj.equals("manager")) {
					String varRepairfalg = request.getParameter("varRepairfalg") != null ? request.getParameter("varRepairfalg") : null;
					String sql ="update t_repairinfo set repairid = '"+varRepairid+"',repairflag = '3"+"' where uuid ='"+varUuid+"'" ; 
					if(varRepairfalg!=null && !varRepairfalg.equals("")) {
						sql ="update t_repairinfo set repairflag = '2"+"' where uuid ='"+varUuid+"'" ; 
					}
					System.out.println(sql);
					int a = new DatabaseDaoImp().updateObjBySql(sql);
					if(a>0) {
						System.out.println("----updateInfo--更新对象成功----sql"+sql);
						entity.put("rtnCode","0");
					}else {
						entity.put("rtnCode","1000");
					}
					pw.write(entity.toString());
					System.out.println(entity.toString());
				}
			}
			//获取工程师列表，操作对象后台管理
			if (varMethod.equals("getEngineer")) {
				System.out.println("---getEngineer---");
				JSONArray jsonArray = new JSONArray();
				entity =  new JSONObject();
				entitys = new JSONObject();
				String hql ="from com.withyou.bean.engineer";
				ArrayList<engineer> listEngin= (ArrayList) new HibernateDaoImp().getObjectListByHql(hql);
				System.out.println("length::"+listEngin.size());
				if(listEngin != null) {
					entity.put("rtnCode", "0");
					engineer engin = null;
					for(Object t:listEngin) {
						engin = (engineer) t;
						entitys.put("enginname", engin.getUsername());
						entitys.put("enginrealname", engin.getRealname());
						jsonArray.add(entitys);
					}
				}
				entity.put("rs", jsonArray);
				pw.write(entity.toString());
				System.out.println(entity.toString());
			}
        }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public static String saveFile(HttpServletRequest request, HttpServletResponse response, String fileName,
			String path) {
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		if (!new File(path).exists()) {
			new File(path).mkdirs();
			System.out.println("创建:" + path);
		}

		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		/**
		 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
		 * 然后再将其真正写到 对应目录的硬盘上
		 */
		factory.setRepository(new File(path));
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);
		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		String filename = null;
		try {
			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>) upload.parseRequest(new ServletRequestContext(request));
			for (FileItem item : list) {
				// 获取表单的属性名字
				String name = item.getFieldName();
				System.out.println("name:--" + name);
				// 如果获取的 表单信息是普通的 文本 信息
				if (item.isFormField()) {
					// 获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
					String value = item.getString();
					request.setAttribute(name, value);
					System.out.println("value:--" + value);
				} else {// 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
					/**
					 * 以下三步，主要获取 上传文件的名字
					 */
					// 获取路径名
					String value = item.getName();
					System.out.println("value:--" + value);
					// 索引到最后一个反斜杠
					int start = value.lastIndexOf("\\");
					// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
					filename = value.substring(start + 1);
					System.out.println("filename" + filename);
					request.setAttribute(name, filename);

					// 修改文件名
					filename = fileName + "." + filename.substring(filename.lastIndexOf(".") + 1);
					System.out.println(filename);

					// 真正写到磁盘上
					// 手动写的
					OutputStream out = new FileOutputStream(new File(path, filename));
					InputStream in = item.getInputStream();
					int length = 0;
					byte[] buf = new byte[1024];
					// in.read(buf) 每次读到的数据存放在 buf 数组中
					while ((length = in.read(buf)) != -1) {
						// 在 buf 数组中 取出数据 写到 （输出流）磁盘上
						out.write(buf, 0, length);
					}
					System.out.println("Filename:" + filename + "\nPath:" + path);
					in.close();
					out.flush();
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return filename;
	}
}
