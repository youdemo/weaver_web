package gvo.emc.dir;

import java.util.Date;

import javax.activation.DataHandler;

import net.sf.json.JSONArray;

import org.json.JSONObject;

import gvo.emc.dir.DfcWsCallServiceStub.Base64BinaryArray;
import gvo.emc.dir.DfcWsCallServiceStub.FilesUpload;
import gvo.emc.dir.DfcWsCallServiceStub.StringArray;

public class Rss {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
FilesUpload param = new FilesUpload();

		
		JSONObject param2 = new JSONObject();
		JSONObject ws = new JSONObject();
		JSONObject cond = new JSONObject();
		
		/*
			wsId	String	强制	值为：101101
			userId	String	强制	登录员工工号
			supplierId	String	强制	供应商ID【weaver-oa】
			callDatetime	String	强制	调用方服务器调用时间【yyyy-MM-dd HH24:mi:ss】
			logId	String	强制	为日志记录使用的LOGID

		 */
		ws.put("wsId", 101103);	
		ws.put("userId", "dmadmin");
		ws.put("supplierId", "weaver-oa");
		ws.put("callDatetime", sf.format(new Date()));
		ws.put("logId", "111");
		/*
			wfType	String	可选.存在时代表流程文档,归档在该流程下,其目录结构为"/国显办公文档/流程文档/流程名称x.doc";不存在时代表非流程文档,归档在非流程文档下,其目录结构为"/国显办公文档/非流程文档x.doc"	流程类型
			wfName	String	可选	流程名称
			wfId	String	可选	流程实例Id
			author	String	强制	创建者编号
			party	String	可选,多值以英文","组合	参与者编号	
			
			folderPath	String	可选.存在时在归档在该文件目录下,其目录结构未"/国显办公文档/归档文档/当前目录x.doc";当该值不存在时,归档在默认文件夹下,其目录结构为"/国显办公文档/归档文档/非指定x.doc"	文件夹路径
		 */
		
		
		cond.put("wfType", "/国显办公文档/流程文档/流程名称x.doc");
		cond.put("wfName", "流程名称");
//		if(wfId == null || "".equals(wfId) || !wfId.matches("^[0-9]+$")){
//			wfId = "0";
//		}
		cond.put("wfId", 111);
		cond.put("author", 111);
		cond.put("party", 1112);
		cond.put("folderPath", "/国显办公文档/归档文档/当前目录x.doc");
		cond.put("isEncrypt", false);
		
		param2.put("ws", ws);
		param2.put("cond", cond);
		
		param.setSendJsonString(param2.toJSONString());
		
		buff.append("参数:[ ");
		buff.append(param2.toJSONString());
		buff.append("] ");
		String[] params1 = new String[filename.length];
		for(int i=0;i<filename.length;i++){
			params1[i] = filename[i];
		}
		
		StringArray sa = new StringArray();
		sa.setItem(params1);	
		param.setNames(sa);
		
		buff.append(" 文件名:");
		buff.append(Arrays.toString(filename));
		buff.append(" ");
		
		DataHandler[] param3 = new DataHandler[path.length];
		for(int i=0;i<path.length;i++){
			DataHandler tmp_datahs = new DataHandler(new FileDataSource(path[i]));
			param3[i] = tmp_datahs;
		}
		
		buff.append(" 文件路径:");
		buff.append(Arrays.toString(path));
		buff.append(" ");
		
		Base64BinaryArray bba = new Base64BinaryArray();
		bba.setItem(param3);
		
		param.setDataHandlers(bba);
		
//		filesUpload.setFilesUpload(param);
		
		String oper_desc = buff.toString();
		 DfcWsCallServiceStub dwcss = new DfcWsCallServiceStub();
		String res = dwcss.filesUpload(param).getEntrance();
		
		org.json.JSONObject res_json = null;
		
		String is_success = "";
		String return_info = res;
		try {
			res_json = new org.json.JSONObject(res);
			is_success = (String) res_json.getJSONObject("ms").get("code");
		} catch (JSONException e) {
			is_success = "1";
			e.printStackTrace();
			return_info = "返回的json格式不正确！";
		}  

	}
	public static Object[] getDTOArray(String jsonString, Class clazz){  
        setDataFormat2JAVA();  
        JSONArray array = JSONArray.fromObject(jsonString);  
       Object[] obj = new Object[array.size()];  
            for(int i = 0; i < array.size(); i++){  
                 net.sf.json.JSONObject jsonObject = array.getJSONObject(i);  
                obj[i] = JSONObject.toBean(jsonObject, clazz);  
            }  
            return obj;  
  }  
}
