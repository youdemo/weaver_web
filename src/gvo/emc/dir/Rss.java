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
			wsId	String	ǿ��	ֵΪ��101101
			userId	String	ǿ��	��¼Ա������
			supplierId	String	ǿ��	��Ӧ��ID��weaver-oa��
			callDatetime	String	ǿ��	���÷�����������ʱ�䡾yyyy-MM-dd HH24:mi:ss��
			logId	String	ǿ��	Ϊ��־��¼ʹ�õ�LOGID

		 */
		ws.put("wsId", 101103);	
		ws.put("userId", "dmadmin");
		ws.put("supplierId", "weaver-oa");
		ws.put("callDatetime", sf.format(new Date()));
		ws.put("logId", "111");
		/*
			wfType	String	��ѡ.����ʱ���������ĵ�,�鵵�ڸ�������,��Ŀ¼�ṹΪ"/���԰칫�ĵ�/�����ĵ�/��������x.doc";������ʱ����������ĵ�,�鵵�ڷ������ĵ���,��Ŀ¼�ṹΪ"/���԰칫�ĵ�/�������ĵ�x.doc"	��������
			wfName	String	��ѡ	��������
			wfId	String	��ѡ	����ʵ��Id
			author	String	ǿ��	�����߱��
			party	String	��ѡ,��ֵ��Ӣ��","���	�����߱��	
			
			folderPath	String	��ѡ.����ʱ�ڹ鵵�ڸ��ļ�Ŀ¼��,��Ŀ¼�ṹδ"/���԰칫�ĵ�/�鵵�ĵ�/��ǰĿ¼x.doc";����ֵ������ʱ,�鵵��Ĭ���ļ�����,��Ŀ¼�ṹΪ"/���԰칫�ĵ�/�鵵�ĵ�/��ָ��x.doc"	�ļ���·��
		 */
		
		
		cond.put("wfType", "/���԰칫�ĵ�/�����ĵ�/��������x.doc");
		cond.put("wfName", "��������");
//		if(wfId == null || "".equals(wfId) || !wfId.matches("^[0-9]+$")){
//			wfId = "0";
//		}
		cond.put("wfId", 111);
		cond.put("author", 111);
		cond.put("party", 1112);
		cond.put("folderPath", "/���԰칫�ĵ�/�鵵�ĵ�/��ǰĿ¼x.doc");
		cond.put("isEncrypt", false);
		
		param2.put("ws", ws);
		param2.put("cond", cond);
		
		param.setSendJsonString(param2.toJSONString());
		
		buff.append("����:[ ");
		buff.append(param2.toJSONString());
		buff.append("] ");
		String[] params1 = new String[filename.length];
		for(int i=0;i<filename.length;i++){
			params1[i] = filename[i];
		}
		
		StringArray sa = new StringArray();
		sa.setItem(params1);	
		param.setNames(sa);
		
		buff.append(" �ļ���:");
		buff.append(Arrays.toString(filename));
		buff.append(" ");
		
		DataHandler[] param3 = new DataHandler[path.length];
		for(int i=0;i<path.length;i++){
			DataHandler tmp_datahs = new DataHandler(new FileDataSource(path[i]));
			param3[i] = tmp_datahs;
		}
		
		buff.append(" �ļ�·��:");
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
			return_info = "���ص�json��ʽ����ȷ��";
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
