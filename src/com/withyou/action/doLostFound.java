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
	//�ύʧ������Ҫ����Ϣ
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
			System.out.println("----act����Ϊ�գ�����ֹͣ");
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
						System.out.println("ѧ��ʧ������Ǽ�session��Ϊ��");
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
						pw.print("<script>alert('������Ϣ�ϴ��ɹ�');window.history.back();</script>");
					}else {
						pw.print("<script>alert('������Ϣ�ϴ�ʧ�ܣ����Ժ�����');window.history.back();</script>");
					}
				}else {
					pw.print("<script>alert('ͼƬ�ϴ�ʧ�ܣ����Ժ�����');window.history.back();</script>");
				}
				return;
			}
			//��ȡʧ�������б�
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
			//��ȡʧ����������
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
			//����ʧ����������
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
					System.out.println("filename" + filename);
					request.setAttribute(name, filename);

					// �޸��ļ���
					filename = fileName + "." + filename.substring(filename.lastIndexOf(".") + 1);
					System.out.println(filename);

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
