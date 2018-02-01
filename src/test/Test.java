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
		String khid = "";//客户id
		String sbno = "";//设备编号
		String sbname = "";//设备名称
		String sbxlh ="";//设备序列号
		String bz ="";//备注
		String wxddate = "";//维修单提交日期
		String wxaddress = "";//维修地址
		String wxddid = "";//维修订单id
		String wxtel = "";//维修联系方式
		String mdid ="";//门店id
		String mdname = "";//门店名称
		String quid = "";//区域ID
		String qumc = "";//区域名称
		String qyjlygh = "";//区域经理员工号
		String qyjl = "";//区域经理
		String wxlsgh = "";//维修老师工号
		String wxlsxm = "";//维修老师姓名
		String wxlslxfs = "";//维系老师联系方式
		String zzjjsm = "";//最终解决说明
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
		row.put("subject", "售后报销");
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
//		String flag="";//分隔符号
//		while(rs.next()){
//			String cc=Util.null2String(rs.getString("cc"));
//			ccs = ccs+flag+cc;
//			flag=",";
//		}
//		sql="update aa set cc='"+ccs+"' where id="+mainid;
//		rs.executeSql(sql);
//		String aa="{\"BOM\":{\"BO\":{\"AdmInfo\":{\"Object\":\"191\"},\"OSCL\":{\"row\":{\"U_Address\":\"陆家嘴环路100号\",\"U_POSORID\":\"AS20161123124654\",\"U_Phone\":\"18801628035\",\"U_StoreID\":\"4\",\"U_StoreName\":\"陆家嘴店\",\"assignee\":\"1\",\"createDate\":\"20161123\",\"customer\":\"10003504\",\"descrption\":\"11-23号测试byhm\",\"itemCode\":\"1010048\",\"itemName\":\"全自动变频石油干洗机K100QAB（双缸双过滤）\",\"objType\":\"191\",\"problemTyp\":\"1\",\"subject\":\"售后报修\"}}}}}";
//		JSONObject json =new JSONObject(aa).getJSONObject("BOM").getJSONObject("BO").getJSONObject("OSCL").getJSONObject("row");
//        String khid = json.getString("customer");//客户ID 
//        String sbno = json.getString("itemCode");//设备编号
//        String sbname = json.getString("itemName");//设备名称
//        //String sbxlh = json.getString("internalSN");//设备序列号
//        String bz = json.getString("descrption");//备注
//        String wxddate = json.getString("createDate");//维修单提交日期：
//        String wxaddress = json.getString("U_Address");//维修地址：
//       String wxddid = json.getString("U_POSORID");//维修订单ID 
//        String wxtel = json.getString("U_Phone");//维修联方式
//        String mdid = json.getString("U_Phone");//门店ID 
//        String mdname = json.getString("U_Phone");//门店名称
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
		 // HTML文档内容		
		//di.setDoccontent("");	
		 //文档标题		
		//di.setDocSubject("aa");		
		 // 关键字		
		//di.setKeyword("");		
		 // 是否回复文档		 
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
			node.put("desc1", "可疑车辆" + i);
			node.put("desc2", "");
			node.put("desc3", "");
			node.put("desc4", "");
			node.put("title", "hello");
			node.put("upload_time", (new java.util.Date()).toString());
			node.put("uploader", "王二");
			detail.put(node);
			JSONArray detail1 = new JSONArray();
			json.put("DETAIL"+i, detail1);
		}
		
		return json.toString();
	}

	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建目录
		if (dir.mkdirs()) {
			System.out.println("创建目录" + destDirName + "成功！");
			return true;
		} else {
			System.out.println("创建目录" + destDirName + "失败！");
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
		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				return false;
			}
		}
		// 创建目标文件
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
		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				return false;
			}
		}
		// 创建目标文件
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