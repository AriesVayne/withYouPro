package com.withyou.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.mchange.v2.sql.filter.SynchronizedFilterCallableStatement;
import com.withyou.bean.assignment;
import com.withyou.bean.classinfo;
import com.withyou.bean.classmessage;
import com.withyou.bean.student;
import com.withyou.bean.teacher;
import com.withyou.imp.HibernateDaoImp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class doClass
 */
@WebServlet("/doClass")
public class doClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//��ӿγ�
	private String varMethod = null;
	private String varFileDir = null;
	private String varClassName = null;
	private String varGrade = null;
	private String varMajor = null;
	private String varCollege = null;
	//��ѯ�γ���Ϣ
	private String userType = null;

	//���֪ͨ��Ϣ
	private String varMessTitle = null;
	private String varMessCont = null;
	private String varClassId = null;
	
	//�ϴ��μ�
	private String varResFileName = null;
	
	//ѧ���ϴ���ҵ
	private String varAssid = null;
	
	private HttpSession session = null;
	PrintWriter pw = null;
	private JSONObject entity =null;
	private JSONObject entitys =null;
	private JSONArray jsonarray = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public doClass() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");

		System.out.println("----doClass----");
		varMethod = request.getParameter("act") != null ? request.getParameter("act") : null;
		varFileDir = request.getParameter("varFileDir") != null ? request.getParameter("varFileDir") : null;
		varClassName = request.getParameter("varClassName") != null ? request.getParameter("varClassName") : null;
		varGrade = request.getParameter("varGrade") != null ? request.getParameter("varGrade") : null;
		varMajor = request.getParameter("varMajor") != null ? request.getParameter("varMajor") : null;
		varCollege = request.getParameter("varCollege") != null ? request.getParameter("varCollege") : null;
		userType = request.getParameter("userType") != null ? request.getParameter("userType") : null;
		varMessTitle = request.getParameter("varMessTitle") != null ? request.getParameter("varMessTitle") : null;
		varMessCont = request.getParameter("varMessCont") != null ? request.getParameter("varMessCont") : null;
		varClassId = request.getParameter("varClassId") != null ? request.getParameter("varClassId") : null;
		varResFileName = request.getParameter("varResFileName") != null ? request.getParameter("varResFileName") : null;
		varAssid = request.getParameter("varAssid") != null ? request.getParameter("varAssid") : null;
		session = request.getSession();
		pw = response.getWriter();
		if (varMethod == null) {
			System.out.println("----varMethod==null----");
			return;
		} else {
			if (varMethod.equals("addLesson")) {
				System.out.println("act:addLesson--incoming"+varCollege);
				if (session.getAttribute("user_teacher") == null) {
					System.out.println("��ʦδ��¼��session�û���Ϊ��");
					return;
				}
				String teacUserName = (String) session.getAttribute("user_teacher");
				String hql = "from com.withyou.bean.teacher where username = '"+teacUserName+"'";
				System.out.println(hql);
				teacher teacherAdd = (teacher) new HibernateDaoImp().getObjectByHql(hql);
				if (teacherAdd == null) {
					System.out.println(teacUserName + "������");
					return;
				}
				String uuid = UUID.randomUUID().toString();
				String path = getServletContext().getRealPath("/") + "FileUpload\\" + varFileDir;
				String filename = saveFile(request, response, uuid, path);
				if (filename == null) {
					pw.write("<script>alert('ͼƬ�ϴ�ʧ��');window.history.back();</script>");
					return;
				}
				classinfo addclass = new classinfo();
				addclass.setUuid(uuid);
				addclass.setClassname(varClassName);
				addclass.setCollege(varCollege);
				addclass.setFilepath("FileUpload\\" + varFileDir +"\\" + uuid);
				addclass.setGrade(varGrade);
				addclass.setMajor(varMajor);
				addclass.setTeacherid(teacUserName);
				addclass.setTeachername(teacherAdd.getRealname());
				addclass.setImgpath(filename);
				System.out.println(filename);
				if (new HibernateDaoImp().save(addclass)) {
					path = getServletContext().getRealPath("/")+addclass.getFilepath();
					System.out.println(path);
					if (!new File(path).exists()) {
						new File(path).mkdirs();
						new File(path+"\\ClassRes").mkdirs();
						new File(path+"\\StuAssi").mkdirs();
						System.out.println("����:" + path+"----"+path+"\\ClassRes"+"----"+path+"\\StuAssi");
						pw.print("<script>alert('�γ���ӳɹ�');window.history.back();</script>");
					}else {
						pw.print("<script>alert('�μ��ϴ�Ŀ¼�ϴ�ʧ��');window.history.back();</script>");
					}
					return;
				}
				pw.print("<script>alert('�γ����ʧ��');window.history.back();</script>");
				return;
			}
		}
		if (varMethod.equals("getLessons")) {
			System.out.println("----getLessons----");
			entity = new JSONObject();
			entitys = new JSONObject();
			jsonarray = new JSONArray();
			//��ʦ��ȡ���пγ��б�
			if(userType.equals("teacher")) {
				System.out.println("teacher query");
				String username = (String) session.getAttribute("user_teacher");
				if(username == null) {
					System.out.println("��ȡ��ʦ�γ��ǽ�ʦδ��¼");
					entity.put("rtnCode", "1002");
					entity.put("errDesc", "��ȡ��ʦ�γ��ǽ�ʦδ��¼");
				}
				String hql = "from com.withyou.bean.classinfo where teacherid = '"+ username +"'";
				ArrayList classList = (ArrayList) new HibernateDaoImp().getObjectListByHql(hql);
				if(classList == null) {
					System.out.println("��ѯʧ��");
					entity.put("rtnCode", "1002");
					return;
				}
				entity.put("rtnCode","0");
				classinfo classT = null;
				for(Object t:classList) {
					classT = (classinfo) t;
					entitys.put("classname", classT.getClassname());
					entitys.put("college", classT.getCollege());
					entitys.put("filepath", classT.getFilepath());
					entitys.put("grade", classT.getGrade());
					entitys.put("imgpath", classT.getImgpath());
					entitys.put("major", classT.getMajor());
					entitys.put("teacherid", classT.getTeacherid());
					entitys.put("teachername", classT.getTeachername());
					entitys.put("uuid", classT.getUuid());
					jsonarray.add(entitys);
				}
				entity.put("rs", jsonarray);
				pw.write(entity.toString());
				System.out.println(entity.toString());
				return;
			}
			//��ʦ��ȡ���пγ��б�
			if(userType.equals("student")) {
				System.out.println("student query");
				String username = (String) session.getAttribute("user_student");
				if(username == null) {
					System.out.println("��ȡ�W���γ��ǌW��δ��¼");
					entity.put("rtnCode", "1002");
					entity.put("errDesc", "��ȡ�W���γ��ǌW��δ��¼");
				}
				String hql ="from com.withyou.bean.student where username = '"+ username +"'";
				student stu = (student)new HibernateDaoImp().getObjectByHql(hql);
				if(stu!=null) {
					hql = "from com.withyou.bean.classinfo where major = '"+ stu.getMajor() +"' and college = '"+stu.getCollege() +"' and grade = '"+stu.getGrade()+"'";
					System.out.println(hql);
					ArrayList classList = (ArrayList) new HibernateDaoImp().getObjectListByHql(hql);
					if(classList == null) {
						System.out.println("��ѯʧ��");
						entity.put("rtnCode", "1002");
						return;
					}
					entity.put("rtnCode","0");
					classinfo classT = null;
					for(Object t:classList) {
						classT = (classinfo) t;
						entitys.put("classname", classT.getClassname());
						entitys.put("college", classT.getCollege());
						entitys.put("filepath", classT.getFilepath());
						entitys.put("grade", classT.getGrade());
						entitys.put("imgpath", classT.getImgpath());
						entitys.put("major", classT.getMajor());
						entitys.put("teacherid", classT.getTeacherid());
						entitys.put("teachername", classT.getTeachername());
						entitys.put("uuid", classT.getUuid());
						jsonarray.add(entitys);
					}
					entity.put("rs", jsonarray);
				}
			}
			pw.write(entity.toString());
			System.out.println(entity.toString());
			return;
		}
		if (varMethod.equals("infoadd")) {
			System.out.println("----infoadd----");
			entity = new JSONObject();
			entitys = new JSONObject();
			jsonarray = new JSONArray();
			String username = (String) session.getAttribute("user_teacher");
			if(username == null) {
				System.out.println("��ȡ��ʦ�γ��ǽ�ʦδ��¼");
				entity.put("rtnCode", "1002");
				entity.put("errDesc", "��ȡ��ʦ�γ��ǽ�ʦδ��¼");
			}else {
				String hql2 = "from com.withyou.bean.classinfo where uuid = '"+varClassId+"'";
				classinfo clas = (classinfo) new HibernateDaoImp().getObjectByHql(hql2);
				if(clas!=null) {
					classmessage cm = new classmessage();
					cm.setTitle(varMessTitle);
					cm.setClassid(varClassId);
					cm.setClassname(clas.getClassname());
					cm.setCollege(clas.getCollege());
					cm.setContent(varMessCont.trim());
					cm.setGrade(clas.getGrade());
					cm.setMajor(clas.getMajor());
					cm.setUuid(UUID.randomUUID().toString());
					if(new HibernateDaoImp().save(cm)) {
						System.out.println("����ɹ�");
						entity.put("rtnCode", "0");
					}else {
						System.out.println("����ʧ��");
						entity.put("rtnCode", "1002");
						entity.put("errDesc", "����ʧ��");
					}
				}else {
					System.out.println("��Ӧ�γ�Ϊ��");
					entity.put("rtnCode", "1003");
					entity.put("errDesc", "��Ӧ�γ�Ϊ��");
				}
				pw.write(entity.toString());
			}
			return;
		}
		if (varMethod.equals("getClassInfoList")) {
			entity = new JSONObject();
			entitys = new JSONObject();
			jsonarray = new JSONArray();
			String hql ="from com.withyou.bean.classmessage where classid = '"+varClassId+"'";
			ArrayList melist = (ArrayList) new HibernateDaoImp().getObjectListByHql(hql);
			if(melist != null) {
				entity.put("rtnCode", "0");
				classmessage cms = null;
				for(Object t:melist) {
					cms = (classmessage) t;
					entitys.put("classid", cms.getClassid());
					entitys.put("classname", cms.getClassname());
					entitys.put("college", cms.getCollege());
					entitys.put("content", cms.getContent());
					entitys.put("grade", cms.getGrade());
					entitys.put("major", cms.getMajor());
					entitys.put("title", cms.getTitle());
					entitys.put("uuid", cms.getUuid());
					entitys.put("time", cms.getTime());
					jsonarray.add(entitys);
				}
				entity.put("rs", jsonarray);
			}else {
				entity.put("rtnCode", "1002");
			}
			pw.write(entity.toString());
			return;
		}
		//��ȡ֪ͨ��Ϣ����
		if (varMethod.equals("getClaInDeta")) {
			System.out.println("----getClaInDeta----");
			entity = new JSONObject();
			entitys = new JSONObject();
			jsonarray = new JSONArray();
			String hql = "from com.withyou.bean.classmessage where uuid = '"+varClassId+"'";
			classmessage cms = (classmessage) new HibernateDaoImp().getObjectByHql(hql);
			if(cms!=null) {
				entity.put("rs", "0");
				entitys.put("classid", cms.getClassid());
				entitys.put("classname", cms.getClassname());
				entitys.put("college", cms.getCollege());
				entitys.put("content", cms.getContent());
				entitys.put("grade", cms.getGrade());
				entitys.put("major", cms.getMajor());
				entitys.put("title", cms.getTitle());
				entitys.put("uuid", cms.getUuid());
				entitys.put("time", cms.getTime());
				jsonarray.add(entitys);
				entity.put("rs", jsonarray);
			}else {
				entity.put("rs", "1001");
			}
			pw.write(entity.toString());
			return;
		}
		// ��ȡ�γ�Ŀ¼���ļ�
		if (varMethod.equals("getClassFilePathList")) {
			System.out.println("----getClassFilePathList----");
			entity = new JSONObject();
			entitys = new JSONObject();
			jsonarray = new JSONArray();
			String hql = "from com.withyou.bean.classinfo where uuid = '"+varClassId+"'";
			System.out.println(hql);
			classinfo classT = (classinfo) new HibernateDaoImp().getObjectByHql(hql);
			if(classT!=null) {
				String path = getServletContext().getRealPath("/") + classT.getFilepath()+"\\ClassRes";
				System.out.println(path);
				if(new File(path).exists()) {
					File[] flist = new File(path).listFiles();
					if (flist.length == 0) {
						entity.put("rtnCode", "20001");// �ļ���Ϊ��
					} else {
						for (int i = 0; i < flist.length; i++) {
							System.out.println(flist[i].getName() + "�ļ���С" + flist[i].length());
							entitys.put("filename", flist[i].getName() );
							entitys.put("filepath", classT.getFilepath()+"\\ClassRes\\"+flist[i].getName() );
							entitys.put("length", flist[i].length() );
							jsonarray.add(entitys);
						}
							}
				}
				entity.put("rtnCode", "20002");
				entity.put("rs", jsonarray);
			}else {
				entity.put("rtnCode", "10002");
			}
			pw.write(entity.toString());
			return;
		}
		// ��ʦ�ϴ��μ�
		if (varMethod.equals("teacFileUp")) {
			System.out.println("----teacFileUp----");
			entity = new JSONObject();
			entitys = new JSONObject();
			jsonarray = new JSONArray();
			String path = getServletContext().getRealPath("/")+"\\FileUpload\\classOnline\\"+varClassId+"\\ClassRes";
			String fileName = saveFile(request,response,varResFileName,path);
			if(fileName!=null) {
				System.out.println("����ɹ�");
				pw.print("<script>alert('�ļ��ϴ��ɹ�');window.history.back();</script>");
				return;
			}else {
				pw.print("<script>alert('�ļ��ϴ�ʧ��');window.history.back();</script>");
				System.out.println("shibai ");
			}
		}
		// �����ҵ
		if (varMethod.equals("assignadd")) {
			System.out.println("----assignadd----");
			entity = new JSONObject();
			entitys = new JSONObject();
			jsonarray = new JSONArray();

			assignment asm = new assignment();
			asm.setClassid(varClassId);
			asm.setAssid(UUID.randomUUID().toString());
			asm.setContent(varMessCont.trim());
			asm.setTitle(varMessTitle);
			if(new HibernateDaoImp().save(asm)) {
				String path = getServletContext().getRealPath("/")+"\\FileUpload\\classOnline\\"+varClassId+"\\StuAssi";
				if(new File(path).exists()) {
					System.out.println("�����ҵʱ�����ɹ�");
				}else {
					System.out.println("�����ҵʱ������ӦĿ¼ʧ��");
				}
				entity.put("rtnCode", "0");
			}else {
				entity.put("rtnCode", "1001");
			}
			pw.write(entity.toString());
			return;
		}
		//��ȡ��ҵ����
		if (varMethod.equals("getAssiList")) {
			System.out.println("---getAssiList---");
			entity = new JSONObject();
			entitys = new JSONObject();
			jsonarray = new JSONArray();
			String hql ="from com.withyou.bean.assignment where classid = '"+varClassId+"'";
			ArrayList melist = (ArrayList) new HibernateDaoImp().getObjectListByHql(hql);
			System.out.println(hql);
			if(melist != null) {
				entity.put("rtnCode", "0");
				assignment asi = null;
				for(Object t:melist) {
					asi = (assignment) t;
					entitys.put("assid", asi.getAssid());
					entitys.put("classid", asi.getClassid());
					entitys.put("content", asi.getContent());
					entitys.put("title", asi.getTitle());
					entitys.put("udtime", asi.getUptime());
					jsonarray.add(entitys);
				}
				entity.put("rs", jsonarray);
			}else {
				entity.put("rtnCode", "1002");
			}
			pw.write(entity.toString());
			return;
		}
		// ��ȡ��ҵ����
		if (varMethod.equals("getAssignDeta")) {
			System.out.println("---getAssiList---");
			entity = new JSONObject();
			entitys = new JSONObject();
			jsonarray = new JSONArray();
			String hql = "from com.withyou.bean.assignment where assid = '" + varClassId + "'";
			assignment asi = (assignment) new HibernateDaoImp().getObjectByHql(hql);
			System.out.println(hql);
			if (asi != null) {
				entity.put("rtnCode", "0");
				entitys.put("assid", asi.getAssid());
				entitys.put("classid", asi.getClassid());
				entitys.put("content", asi.getContent());
				entitys.put("title", asi.getTitle());
				entitys.put("udtime", asi.getUptime());
				jsonarray.add(entitys);
				entity.put("rs", jsonarray);
			} else {
				entity.put("rtnCode", "1002");
			}
			pw.write(entity.toString());
			return;
		}
		// ��ʦ�ϴ��μ�
		if (varMethod.equals("stuFileUp")) {
			System.out.println("----stuFileUp----");
			entity = new JSONObject();
			entitys = new JSONObject();
			jsonarray = new JSONArray();
			String path = getServletContext().getRealPath("/")+"\\FileUpload\\classOnline\\"+varClassId+"\\StuAssi\\"+varAssid;
			if(!new File(path).exists()) {
				new File(path).mkdirs();
			}
			String fileName = saveFile(request,response,varResFileName,path);
			if(fileName!=null) {
				System.out.println("����ɹ�"+path);
				pw.print("<script>alert('�ļ��ϴ��ɹ�');window.history.back();</script>");
				return;
			}else {
				pw.print("<script>alert('�ļ��ϴ�ʧ��');window.history.back();</script>");
				System.out.println("shibai ");
			}
		}
		System.out.println("actδ��ִ��");
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

	public static String saveFile(HttpServletRequest request, HttpServletResponse response, String fileName,
			String path) {
		// ��ô����ļ���Ŀ����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		if (!new File(path).exists()) {
			new File(path).mkdirs();
			System.out.println("����:" + path);
		}

		// ���û�����������õĻ����ϴ���� �ļ� ��ռ�� �ܶ��ڴ棬
		// ������ʱ��ŵ� �洢�� , ����洢�ң����Ժ� ���մ洢�ļ� ��Ŀ¼��ͬ
		/**
		 * ԭ�� �����ȴ浽 ��ʱ�洢�ң�Ȼ��������д�� ��ӦĿ¼��Ӳ���ϣ� ������˵ ���ϴ�һ���ļ�ʱ����ʵ���ϴ������ݣ���һ������ .tem ��ʽ��
		 * Ȼ���ٽ�������д�� ��ӦĿ¼��Ӳ����
		 */
		factory.setRepository(new File(path));
		// ���� ����Ĵ�С�����ϴ��ļ������������û���ʱ��ֱ�ӷŵ� ��ʱ�洢��
		factory.setSizeThreshold(1024 * 1024);
		// ��ˮƽ��API�ļ��ϴ�����
		ServletFileUpload upload = new ServletFileUpload(factory);
		String filename = null;
		try {
			// �����ϴ�����ļ�
			List<FileItem> list = (List<FileItem>) upload.parseRequest(new ServletRequestContext(request));
			for (FileItem item : list) {
				// ��ȡ������������
				String name = item.getFieldName();
				System.out.println("name:--" + name);
				// �����ȡ�� ����Ϣ����ͨ�� �ı� ��Ϣ
				if (item.isFormField()) {
					// ��ȡ�û�����������ַ��� ���������ͦ�ã���Ϊ���ύ�������� �ַ������͵�
					String value = item.getString();
					request.setAttribute(name, value);
					System.out.println("value:--" + value);
				} else {// �Դ���ķ� �򵥵��ַ������д��� ������˵�����Ƶ� ͼƬ����Ӱ��Щ
					/**
					 * ������������Ҫ��ȡ �ϴ��ļ�������
					 */
					// ��ȡ·����
					String value = item.getName();
					System.out.println("value:--" + value);
					// ���������һ����б��
					int start = value.lastIndexOf("\\");
					// ��ȡ �ϴ��ļ��� �ַ������֣���1�� ȥ����б�ܣ�
					filename = value.substring(start + 1);
					System.out.println("filename-----" + filename);
					request.setAttribute(name, filename);
					if(!fileName.equals("sourceFile")) {//���޸��ļ���
						// �޸��ļ���
						filename = fileName + "." + filename.substring(filename.lastIndexOf(".") + 1);
						System.out.println(filename);
					}
					// ����д��������
					// �ֶ�д��
					OutputStream out = new FileOutputStream(new File(path, filename));
					InputStream in = item.getInputStream();
					int length = 0;
					byte[] buf = new byte[1024];
					// in.read(buf) ÿ�ζ��������ݴ���� buf ������
					while ((length = in.read(buf)) != -1) {
						// �� buf ������ ȡ������ д�� ���������������
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
