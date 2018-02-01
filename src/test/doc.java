package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.axis.encoding.Base64;

public class doc {
	
	public static void main(String[] args){
		String aa="";
		try {
			aa = getXmlString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(aa);
	}
	public static String getXmlString() throws Exception {

		String xmlString="";


		int    flen = 0;

		File xmlfile = new File("D:/aaaa/111.docx");  

		byte[] strBuffer = null;

		 try {

			InputStream in = new FileInputStream(xmlfile);

			flen = (int)xmlfile.length();

			strBuffer = new byte[flen];

			in.read(strBuffer, 0, flen);

		} catch (FileNotFoundException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		xmlString = new   String(strBuffer);

		InputStream in = new BufferedInputStream(new ByteArrayInputStream(strBuffer));

		

			

		
		 createFile("D:/aaaa/newfile11.docx","");
		  File file;
	  file = new File("D:/aaaa/newfile11.docx");
	  FileOutputStream fos = new FileOutputStream("D:/aaaa/newfile11.docx");
		int ch = 0;
		try {
			while ((ch = in.read()) != -1) {
				fos.write(ch);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			// 关闭输入流等（略）
			fos.close();
			in.close();
		}
   //构建String时，可用byte[]类型，

	 

		return xmlString;

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

}

