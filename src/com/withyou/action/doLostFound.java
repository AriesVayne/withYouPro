package com.withyou.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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

import com.withyou.bean.lostres;
import com.withyou.imp.DatabaseDaoImp;
import com.withyou.imp.HibernateDaoImp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class doLostFound
 */
@WebServlet("/doLostFound")
public class doLostFound extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String act = "";
	private HttpSession session = null;
	JSONObject entity = null;
	JSONObject entitys = null;	
	JSONArray jsonarray = null;
	//提交失物所需要的信息
	private String varLostResType = "";
	private String varLostResName = "";
	private String varFoundTime = "";
	private String varFoundName = "";
	private String varFoundLocation = "";
	private String varFoundPhone = "";
	private String varLostResFeature = "";
	private String varFileDir = "";
	
	private String varObject = "";
	private String varUuid = "";
	private String varResFlag = "";
	private String varResType = "";
	private String varOwerName = "";
	private String varOwerPhone = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public doLostFound() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		act = request.getParameter("act") != null ? request.getParameter("act"): null;
		session = request.getSession();
		varLostResType = request.getParameter("varLostResType") != null ? request.getParameter("varLostResType"): null;
		varLostResName = request.getParameter("varLostResName") != null ? request.getParameter("varLostResName"): null;
		varFoundTime = request.getParameter("varFoundTime") != null ? request.getParameter("varFoundTime"): null;
		varFoundName = request.getParameter("varFoundName") != null ? request.getParameter("varFoundName"): null;
		varFoundLocation = request.getParameter("varFoundLocation") != null ? request.getParameter("varFoundLocation"): null;
		varFoundPhone = request.getParameter("varFoundPhone") != null ? request.getParameter("varFoundPhone"): null;
		varLostResFeature = request.getParameter("varLostResFeature") != null ? request.getParameter("varLostResFeature"): null;
		varFileDir = request.getParameter("varFileDir") != null ? request.getParameter("varFileDir"): null;
		varObject = request.getParameter("varObject") != null ? request.getParameter("varObject"): null;
		varUuid = request.getParameter("varUuid") != null ? request.getParameter("varUuid"): null;
		varResFlag = request.getParameter("varResFlag") != null ? request.getParameter("varResFlag"): null;
		varResType = request.getParameter("varResType") != null ? request.getParameter("varResType"): null;
		varOwerName = request.getParameter("varOwerName") != null ? request.getParameter("varOwerName"): null;
		varOwerPhone = request.getParameter("varOwerPhone") != null ? request.getParameter("varOwerPhone"): null;
		PrintWriter pw = response.getWriter();
		if(act == null || act.equals("")) {
			System.out.println("----act参数为空，程序停止");
		}else {
			System.out.println("----doLostFound----");
			if(act.equals("handleLostFound")) {
				System.out.println("----handleLostFound---incoming");
				String lostUUID = UUID.randomUUID().toString();
				String path = getServletContext().getRealPath("/") + "FileUpload\\" +varFileDir;
				String devicePicture = saveFile(request, response, lostUUID, path);
				if (devicePicture != null) {
					lostres losthand = new lostres();
					if(session.getAttribute("user_student")!=null) {
						losthand.setUploadid(session.getAttribute("user_student").toString());
						System.out.println("学生失物招领登记session不为空");
					}
					losthand.setUuid(lostUUID);
					losthand.setRestype(varLostResType);
					losthand.setResname(varLostResName);
					losthand.setPicktime(varFoundTime);
					losthand.setPickname(varFoundName);
					losthand.setPicklocale(varFoundLocation);
					losthand.setPickphone(varFoundPhone);
					losthand.setResflag("1");
					losthand.setResfeature(varLostResFeature);
					losthand.setRespicpath(devicePicture);
					if(new HibernateDaoImp().save(losthand)) {
						pw.print("<script>alert('报修信息上传成功');window.history.back();</script>");
					}else {
						pw.print("<script>alert('报修信息上传失败，请稍后重试');window.history.back();</script>");
					}
				}else {
					pw.print("<script>alert('图片上传失败，请稍后重试');window.history.back();</script>");
				}
				return;
			}
			//获取失物招领列表
			if(act.equals("getLostList")) {
				String hql = "";
				String username_stu = "";
				entity = new JSONObject();
				
				jsonarray = new JSONArray();
				if(session.getAttribute("user_student")!=null) {
					username_stu = session.getAttribute("user_student").toString();
				}
				if(varObject.equals("stu")) {
					if(varLostResType.equals("all")) {
						 hql = "from com.withyou.bean.lostres where resflag !='0'";
					}
					if(varLostResType.equals("lost")) {
						 hql = "from com.withyou.bean.lostres where uploadid = "+username_stu +" and restype = 'lost'";
					}
					if(varLostResType.equals("found")) {
						 hql = "from com.withyou.bean.lostres where uploadid = "+username_stu +" and restype = 'found'";
					}
					System.out.println(hql);
					ArrayList lostList =  (ArrayList) new HibernateDaoImp().getObjectListByHql(hql);
					if(lostList != null) {
						entity.put("rtnCode", "0");
						for(Object b : lostList) {
							lostres losttemp  = (lostres) b;
							entitys = new JSONObject();
							entitys.put("uuid", losttemp.getUuid());
							entitys.put("owerrname", losttemp.getOwername());
							entitys.put("owerphone", losttemp.getOwerphone());
							entitys.put("picklocale", losttemp.getPicklocale());
							entitys.put("pickname", losttemp.getPickname());
							entitys.put("pickphone", losttemp.getPickphone());
							entitys.put("picktime", losttemp.getPicktime());
							entitys.put("resfeature", losttemp.getResfeature());
							entitys.put("resflag", losttemp.getResflag());
							entitys.put("resname", losttemp.getResname());
							entitys.put("respicpath", losttemp.getRespicpath());
							entitys.put("restype", losttemp.getRestype());
							jsonarray.add(entitys);
						}
						entity.put("rs", jsonarray);
					}else {
						entity.put("rtnCode", "1004");
					}
					pw.println(entity.toString());
					System.out.println(entity.toString());
					return;
				}
			}
			//获取失物招领详情
			if(act.equals("lostDetail")) {
				String hql ="from com.withyou.bean.lostres where uuid = '"+varUuid+"'";
				lostres lt = new lostres();
				entity = new JSONObject();
				if(new HibernateDaoImp().getObjectByHql(hql) != null) {
					entitys = new JSONObject();
					jsonarray = new JSONArray();
					entity.put("rtnCode", "0");
					lt = (lostres) new HibernateDaoImp().getObjectByHql(hql);
					entitys.put("uuid", lt.getUuid());
					entitys.put("owername", lt.getOwername());
					entitys.put("owerphone", lt.getOwerphone());
					entitys.put("picklocale", lt.getPicklocale());
					entitys.put("pickname", lt.getPickname());
					entitys.put("pickphone", lt.getPickphone());
					entitys.put("picktime", lt.getPicktime());
					entitys.put("resfeature", lt.getResfeature());
					entitys.put("resflag", lt.getResflag());
					entitys.put("resname", lt.getResname());
					entitys.put("respicpath", lt.getRespicpath());
					entitys.put("restype", lt.getRestype());
					entitys.put("uploadid", lt.getUploadid());
					jsonarray.add(entitys);
					entity.put("rs", jsonarray);
				}else {
					entity.put("rtnCode", "1004");
				}
				pw.print(entity);
			}
			//更新失物招领详情
			if(act.equals("updateLost")) {
				String sql ="";
				entity = new JSONObject();
				if(varResType.equals("lost")) {
					sql ="update t_lostres set resflag ='0' where uuid = '"+varUuid+"'";
				}else {
					sql ="update t_lostres set resflag ='0',owername = '"+varOwerName+"',owerphone = '"+varOwerPhone+"' where uuid = '"+varUuid+"'";
				}
				System.out.println("sql:---"+sql);
				if(new DatabaseDaoImp().updateObjBySql(sql)>0) {
					entity.put("rtnCode", "0");
				}else {
					entity.put("rtnCode", "1006");
				}
				pw.print(entity);
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
