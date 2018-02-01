package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.axis.encoding.Base64;
import sun.misc.BASE64Decoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.Integer;

import weaver.conn.RecordSet;
import weaver.docs.webservices.DocAttachment;
import weaver.docs.webservices.DocInfo;
import weaver.general.Util;

public class Test {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String xh="111,222";
		System.out.println(2!=xh.split(",").length);
		String khid = "";//�ͻ�id
		String sbno = "";//�豸���
		String sbname = "";//�豸����
		String sbxlh ="";//�豸���к�
		String bz ="";//��ע
		String wxddate = "";//ά�޵��ύ����
		String wxaddress = "";//ά�޵�ַ
		String wxddid = "";//ά�޶���id
		String wxtel = "";//ά����ϵ��ʽ
		String mdid ="";//�ŵ�id
		String mdname = "";//�ŵ�����
		String quid = "";//����ID
		String qumc = "";//��������
		String qyjlygh = "";//������Ա����
		String qyjl = "";//������
		String wxlsgh = "";//ά����ʦ����
		String wxlsxm = "";//ά����ʦ����
		String wxlslxfs = "";//άϵ��ʦ��ϵ��ʽ
		String zzjjsm = "";//���ս��˵��
		JSONObject json = new JSONObject();
		JSONObject BOM = new JSONObject();
		JSONObject BO = new JSONObject();
		JSONObject AdmInfo = new JSONObject();
		JSONObject OSCL = new JSONObject();
		JSONObject row = new JSONObject();
		json.put("BOM", BOM);
		BOM.put("BO", BO);
		BO.put("AdmInfo", AdmInfo);
		AdmInfo.put("Object", "191");
		BO.put("OSCL", OSCL);
		OSCL.put("row", row);
		row.put("subject", "�ۺ���");
		row.put("customer", khid);
		row.put("itemCode", sbno);
		row.put("itemName", sbname);
		row.put("internalSN", sbxlh);
		row.put("problemTyp", "");
		row.put("assignee", "1");
		row.put("descrption", bz);
		row.put("objType", "191");
		row.put("createDate", wxddate);
		row.put("U_Address", wxaddress);
		row.put("U_POSORID", wxddid);
		row.put("U_Phone", wxtel);
		row.put("U_StoreID", mdid);
		row.put("U_StoreName", mdname);
		row.put("U_areaid", quid);
		row.put("U_areaname", qumc);
		row.put("U_areamanagerid", qyjlygh);
		row.put("U_Sareamname", qyjl);
		row.put("U_EmpID", wxlsgh);
		row.put("U_EmpName", wxlsxm);
		row.put("U_EmpPhone", wxlslxfs);
		row.put("resolution", zzjjsm);
		System.out.println(json.toString());
		
//		JSONObject json = new JSONObject();
//		JSONObject header = new JSONObject();
//		json.put("HEADER", header);
//		header.put("wheresystem" , "1");
//		header.put("aramid" , "2");
//		header.put("remark" , "3");
//		header.put("gxbh" , "4");
//		header.put("pc" , "5");
//		header.put("lh" , "6");
//		header.put("sbbh" , "7");
//		header.put("ygxm" , "8");
		
//		RecordSet rs = new RecordSet();
//		String mainid=1;
//		String sql="select cc from  aa_dt1 where mainid="+mainid;
//		rs.executeSql(sql);
//		String ccs="";
//		String flag="";//�ָ�����
//		while(rs.next()){
//			String cc=Util.null2String(rs.getString("cc"));
//			ccs = ccs+flag+cc;
//			flag=",";
//		}
//		sql="update aa set cc='"+ccs+"' where id="+mainid;
//		rs.executeSql(sql);
//		String aa="{\"BOM\":{\"BO\":{\"AdmInfo\":{\"Object\":\"191\"},\"OSCL\":{\"row\":{\"U_Address\":\"½���컷·100��\",\"U_POSORID\":\"AS20161123124654\",\"U_Phone\":\"18801628035\",\"U_StoreID\":\"4\",\"U_StoreName\":\"½�����\",\"assignee\":\"1\",\"createDate\":\"20161123\",\"customer\":\"10003504\",\"descrption\":\"11-23�Ų���byhm\",\"itemCode\":\"1010048\",\"itemName\":\"ȫ�Զ���Ƶʯ�͸�ϴ��K100QAB��˫��˫���ˣ�\",\"objType\":\"191\",\"problemTyp\":\"1\",\"subject\":\"�ۺ���\"}}}}}";
//		JSONObject json =new JSONObject(aa).getJSONObject("BOM").getJSONObject("BO").getJSONObject("OSCL").getJSONObject("row");
//        String khid = json.getString("customer");//�ͻ�ID 
//        String sbno = json.getString("itemCode");//�豸���
//        String sbname = json.getString("itemName");//�豸����
//        //String sbxlh = json.getString("internalSN");//�豸���к�
//        String bz = json.getString("descrption");//��ע
//        String wxddate = json.getString("createDate");//ά�޵��ύ���ڣ�
//        String wxaddress = json.getString("U_Address");//ά�޵�ַ��
//       String wxddid = json.getString("U_POSORID");//ά�޶���ID 
//        String wxtel = json.getString("U_Phone");//ά������ʽ
//        String mdid = json.getString("U_Phone");//�ŵ�ID 
//        String mdname = json.getString("U_Phone");//�ŵ�����
//        JSONObject json1 = new JSONObject();
//		JSONObject header = new JSONObject();
//		JSONArray detail = new JSONArray();
//		json1.put("HEADER", header);
//		json1.put("DETAILS", detail);
//		header.put("khid", khid);
//		header.put("sbno", sbno);
//		header.put("sbname", sbname);
//		//header.put("sbxlh", sbxlh);
//		header.put("bz", bz);
//		header.put("wxddate", wxddate);
//		header.put("wxaddress", wxaddress);
//		header.put("wxddid", wxddid);
//		header.put("wxtel", wxtel);
//		header.put("mdid", mdid);
//		header.put("mdname", mdname);
//        
//		System.out.println(json1.toString());
		
//		String a=" ";
//		SimpleDateF
//		System.out.println(a.length());
		
	  
//     	SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd H:m:s");
//		String now =sf.format(new Date());
//		System.out.println(now);
//		String name="123.123.jsp";
//	    name =name.substring(0, name.lastIndexOf("."))+"cc"+name.substring( name.lastIndexOf("."),name.length());
//	    System.out.println(name);
		
		//DocInfo di= new DocInfo();
		//di.setMaincategory(0);
		//di.setSubcategory(0);
		//di.setSeccategory(86);		
		 // HTML�ĵ�����		
		//di.setDoccontent("");	
		 //�ĵ�����		
		//di.setDocSubject("aa");		
		 // �ؼ���		
		//di.setKeyword("");		
		 // �Ƿ�ظ��ĵ�		 
		//di.setIsreply("");
		//di.setIsreply(0);
		//DocAttachment doca = new DocAttachment();
		//doca.setFilename();
		
	
//doca.setFilecontent(arg0);
		//DocAttachment[] docs= new DocAttachment[1];
		//docs[0]=doca;
		//di.setAttachments(docs);
		// String json = aa();
		// System.out.println(json);
		// JSONObject jsonName = new JSONObject(json);
		// JSONObject header = jsonName.getJSONObject("HEADER");
		// Iterator it = header.keys();
		// while(it.hasNext()){
		// String key = it.next().toString();
		// String value = header.getString(key);
		// System.out.println("key:"+key+"value:"+value);
		// }
		//String dirName = "D:/aaaa";
		// createDir(dirName);
		//String fileName = dirName + "/temp2/tempFile.txt";
		//createFile(fileName,json);
		
//		 FileOutputStream fop = null;
//		  String content = "This is the text content";
	  //File file;
		  //file = new File("D:/aaaa/newfile.doc");
		 // System.out.println(file.getPath());
		  //System.out.println(file.getName());
		 /// createFile("D:/aaaa/newfile.doc","");
//		   fop = new FileOutputStream(file);
//		   byte[] contentInBytes = content.getBytes();
//		   for(int i=0;i<contentInBytes.length;i++){
//			   System.out.print(contentInBytes[i]);
//		   }
//		   System.out.println(contentInBytes.toString());
//		   fop.write(contentInBytes);
//		   fop.flush();
//		   fop.close();
//		
		

	}

	public static String aa() throws Exception {
		JSONObject header = new JSONObject();
		JSONArray detail = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("HEADER", header);
		json.put("DETAIL", detail);
		header.put("IA", 11);
		header.put("haha", "cc");
		for (int i = 0; i < 2; i++) {
			JSONObject node = new JSONObject();
			node.put("thumb_path", "./Image/" + i + ".gif");
			node.put("flash_path", "./Image/" + i + ".gif");
			node.put("desc1", "���ɳ���" + i);
			node.put("desc2", "");
			node.put("desc3", "");
			node.put("desc4", "");
			node.put("title", "hello");
			node.put("upload_time", (new java.util.Date()).toString());
			node.put("uploader", "����");
			detail.put(node);
			JSONArray detail1 = new JSONArray();
			json.put("DETAIL"+i, detail1);
		}
		
		return json.toString();
	}

	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			System.out.println("����Ŀ¼" + destDirName + "ʧ�ܣ�Ŀ��Ŀ¼�Ѿ�����");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// ����Ŀ¼
		if (dir.mkdirs()) {
			System.out.println("����Ŀ¼" + destDirName + "�ɹ���");
			return true;
		} else {
			System.out.println("����Ŀ¼" + destDirName + "ʧ�ܣ�");
			return false;
		}
	}

	public static boolean createFile(String destFileName,String json) throws IOException {
		File file = new File(destFileName);
		
		  
		if (file.exists()) {
			file.delete();
		}
		if (destFileName.endsWith(File.separator)) {
			return false;
		}
		// �ж�Ŀ���ļ����ڵ�Ŀ¼�Ƿ����
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				return false;
			}
		}
		// ����Ŀ���ļ�
		try {
			if (file.createNewFile()) {
//				 FileWriter fw = new FileWriter(file, true);
//				   BufferedWriter bw = new BufferedWriter(fw);
//				 bw.write(json);
//				   bw.flush();
//				   bw.close();
//				   fw.close();
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean createDOC(String destFileName,String str) throws IOException {
		File file = new File(destFileName);
		
		  
		if (file.exists()) {
			return false;
		}
		if (destFileName.endsWith(File.separator)) {
			return false;
		}
		// �ж�Ŀ���ļ����ڵ�Ŀ¼�Ƿ����
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				return false;
			}
		}
		// ����Ŀ���ļ�
		try {
			if (file.createNewFile()) {
				FileOutputStream fop =  new FileOutputStream(file);
				fop.write(str);
				   fop.flush();
				   fop.close();
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static String InputStreamTOString(InputStream in,String encoding) throws Exception{
		 ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
		 byte[] data = new  byte[4096];
		 int count = -1; 
		 while((count =  in.read(data,0,4096)) != -1) {
			 outStream.write(data, 0, count);  
                  

		 }
		 data= null;  
		 return new String(outStream.toByteArray(),"ISO-8859-1");  


	}
	public static byte[] getFileToByte(File file) { 

        byte[] by = new byte[(int) file.length()]; 

        try { 

            InputStream is = new FileInputStream(file); 

            ByteArrayOutputStream bytestream = new ByteArrayOutputStream(); 

            byte[] bb = new byte[2048]; 

            int ch; 

            ch = is.read(bb); 

            while (ch != -1) { 

                bytestream.write(bb, 0, ch); 

                ch = is.read(bb); 

            } 

            by = bytestream.toByteArray(); 

        } catch (Exception ex) { 

            ex.printStackTrace(); 

        } 

        return by; 

    }
	  
}