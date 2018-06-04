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
		// ����һ��JSONArray����  
		if(varMethod == null) {
        	return;
        }else {
        	System.out.println("-----Start------���뷽����"+varMethod);      	
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
						pw.print("<script>alert('��Ϣ�ϴ��ɹ�');window.history.back();</script>");
					} else {
						pw.print("<script>alert('��Ϣ�ϴ�ʧ�ܣ����Ժ�����');window.history.back();</script>");
					}
				} else {
					pw.print("<script>alert('ͼƬ�ϴ�ʧ�ܣ����Ժ�����');window.history.back();</script>");
					return;
				}
			}
			//��ѯ������Ϣ�б���������ѧ��������ʦ
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
						if(varStatus.equals("1")) {//��ѯδ�����
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
			//ͨ��uuid��ѯ������Ϣ���飬��������ѧ���͹���ʦ
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
			//��ѯ����ʦ������Ϣ���������󹤳�ʦ
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
					String sql ="select count(*) from t_repairinfo where repairid = " + engineerid +" and repairflag = '0'";//�Ѵ���
					System.out.println(sql);
					try {
						ResultSet rs = new DatabaseDaoImp().getResultSetBySql(sql);
						if(rs.next()) {
							entity.put("rtnCode", "0");
							done = rs.getString(1);
						}
						sql ="select count(*) from t_repairinfo where repairid = " + engineerid +" and repairflag = '1' or repairflag= '3'";//δ����
						rs = new DatabaseDaoImp().getResultSetBySql(sql);
						if(rs.next()) {
							undone = rs.getString(1);
						}
						sql ="select count(*) from t_repairinfo where repairid = " + engineerid ;//һ��
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
			//���±��ޣ��������󹤳�ʦ����̨����
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
						System.out.println("----updateInfo--���¶���ɹ�"+feedback+"----sql"+sql);
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
						System.out.println("----updateInfo--���¶���ɹ�----sql"+sql);
						entity.put("rtnCode","0");
					}else {
						entity.put("rtnCode","1000");
					}
					pw.write(entity.toString());
					System.out.println(entity.toString());
				}
			}
			//��ȡ����ʦ�б����������̨����
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
