package gvo.emc.dir;

import gvo.emc.dir.DfcWsCallServiceStub.Base64BinaryArray;
import gvo.emc.dir.DfcWsCallServiceStub.CallService;
import gvo.emc.dir.DfcWsCallServiceStub.CallServiceResponse;
import gvo.emc.dir.DfcWsCallServiceStub.FilesUpload;
import gvo.emc.dir.DfcWsCallServiceStub.StringArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

public class Test {
	public static String os(String sendJson) throws Exception {
		System.out.println(sendJson);
		DfcWsCallServiceStub dcs = new DfcWsCallServiceStub();
		CallService callService = new DfcWsCallServiceStub.CallService();
		callService.setSendJsonString(sendJson);
		CallServiceResponse callServiceResponse = dcs.callService(callService);
		String json = callServiceResponse.getEntrance();
		return json;
	}

	public static String filesupload() throws Exception {
		DfcWsCallServiceStub dcs = new DfcWsCallServiceStub();
		FilesUpload param = new FilesUpload();
		JSONObject param2 = new JSONObject();
		JSONObject ws = new JSONObject();
	    StringBuffer buff = new StringBuffer();
		JSONObject cond = new JSONObject();
		/*
		wsId	String	ǿ��	ֵΪ��101101
		userId	String	ǿ��	��¼Ա������
		supplierId	String	ǿ��	��Ӧ��ID��weaver-oa��
		callDatetime	String	ǿ��	���÷�����������ʱ�䡾yyyy-MM-dd HH24:mi:ss��
		logId	String	ǿ��	Ϊ��־��¼ʹ�õ�LOGID

	 */
		String aa="22";
		aa+="cc";
		String a=Integer.valueOf(aa)%12;
		SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ws.put("wsId", "101101");	
		ws.put("userId", "dmadmin");
		ws.put("supplierId", "weaver-oa");
		ws.put("callDatetime",  sf.format(new Date()));
		ws.put("logId", "111");
		/*
		wfType	String	��ѡ.����ʱ���������ĵ�,�鵵�ڸ�������,��Ŀ¼�ṹΪ"/���԰칫�ĵ�/�����ĵ�/��������x.doc";������ʱ����������ĵ�,�鵵�ڷ������ĵ���,��Ŀ¼�ṹΪ"/���԰칫�ĵ�/�������ĵ�x.doc"	��������
		wfName	String	��ѡ	��������
		wfId	String	��ѡ	����ʵ��Id
		author	String	ǿ��	�����߱��
		party	String	��ѡ,��ֵ��Ӣ��","���	�����߱��	
		
		folderPath	String	��ѡ.����ʱ�ڹ鵵�ڸ��ļ�Ŀ¼��,��Ŀ¼�ṹδ"/���԰칫�ĵ�/�鵵�ĵ�/��ǰĿ¼x.doc";����ֵ������ʱ,�鵵��Ĭ���ļ�����,��Ŀ¼�ṹΪ"/���԰칫�ĵ�/�鵵�ĵ�/��ָ��x.doc"	�ļ���·��
	 */
		cond.put("wfType", "����");
		cond.put("wfName", "����");
//		if(wfId == null || "".equals(wfId) || !wfId.matches("^[0-9]+$")){
//			wfId = "0";
//		}
		cond.put("wfId", "100110101");
		cond.put("author", "Test");
		cond.put("party", "");
		cond.put("folderPath", "����");
		cond.put("isEncrypt", "1");
		
		param2.put("ws", ws);
		param2.put("cond", cond);
			
		System.out.println(param2.toString());
		param.setSendJsonString(param2.toString());
		
		buff.append("����:[ ");
		buff.append(param2.toString());
		buff.append("] ");
		
		String[] filename=new String[]{"aa"};
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
		
		String[] path = new String[] { "D:/axis2-1.4.1/aa.txt" };
		DataHandler[] param3 = new DataHandler[path.length];
		for (int i = 0; i < path.length; i++) {
			DataHandler tmp_datahs = new DataHandler(
					new FileDataSource(path[i]));
			param3[i] = tmp_datahs;
		}
		Base64BinaryArray bba = new Base64BinaryArray();
		bba.setItem(param3);
		param.setDataHandlers(bba);
		
		buff.append(" �ļ�·��:");
		buff.append(Arrays.toString(path));
		buff.append(" ");
		String res = dcs.filesUpload(param).getEntrance();
		return res;
	}

	public static void main(String[] args) throws Exception  {
//		String res=filesupload();
//		System.out.println(res);
		String str ="/��ɽ���Թ�����޹�˾";
		str=str.replace("\\", "\\\\\\\\");
		System.out.println(str);
		String sendJson = "{\"ws\":{\"wsId\":101103,\"userId\":\"gukh\",\"path\":\"/��ɽ���Թ�����޹�˾\",\"supplierId\":\"weaver-oa\"},\"cond\":{}}";
		String json=null;
		try{
		 json = os(sendJson);
		}catch(Exception  e){
			System.out.println("11");
		}
		System.out.println(json);
		ArrayList<MLObject> list= new ArrayList<MLObject>();
         JSONObject ss=new JSONObject(json);
         System.out.println(ss.toString());
         JSONObject arr1=ss.getJSONObject("list");
         System.out.println(arr1.toString());
         JSONObject js = ss.getJSONObject("list");
         System.out.println("ss"+js.toString());
         org.json.JSONArray arr = new org.json.JSONArray();
         try{
          arr=ss.getJSONObject("list").getJSONArray("list");
         }catch(Exception e){
        	 System.out.println("haha");
         }
         System.out.println(arr.toString());
		 JSONArray array = JSONArray.fromObject(arr.toString()); 
		for(int i=0;i<array.size();i++){
			net.sf.json.JSONObject jo=array.getJSONObject(i);
			MLObject ml= (MLObject)net.sf.json.JSONObject.toBean(jo,MLObject.class);
			list.add(ml);
			System.out.println(list.get(i).getName()+","+list.get(i).getFolderPath());
		}
		System.out.println(list.size());	
		for (MLObject ml : list){
			System.out.println("11");
		}
         
	}
}
