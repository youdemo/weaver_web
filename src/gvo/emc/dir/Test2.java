package gvo.emc.dir;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.json.JSONObject;

import gvo.emc.dir.DfcWsCallServiceStub.Base64BinaryArray;
import gvo.emc.dir.DfcWsCallServiceStub.CallService;
import gvo.emc.dir.DfcWsCallServiceStub.CallServiceResponse;
import gvo.emc.dir.DfcWsCallServiceStub.FilesUpload;
import gvo.emc.dir.DfcWsCallServiceStub.StringArray;

public class Test2 {
	public static String os(String sendJson) throws Exception{
		  System.out.println(sendJson);
		  DfcWsCallServiceStub dcs = new DfcWsCallServiceStub();
		  FilesUpload param = new FilesUpload();
		  JSONObject param2 = new JSONObject();
		  JSONObject ws = new JSONObject();
		    ws.put("wsid", 101103);	
			ws.put("userId", "dmadmin");
			ws.put("path", "/昆山国显光电有限公司");
			param2.put("ws", ws);
			System.out.println(param2.toString());
		param.setSendJsonString(param2.toString());
		StringArray sa = new StringArray();
		sa.setItem(new String[]{"log4j"});	
		param.setNames(sa);
		String[] path=new String[]{"D:/axis2-1.4.1/aa.txt"};
		DataHandler[] param3 = new DataHandler[path.length];
		for(int i=0;i<path.length;i++){
			DataHandler tmp_datahs = new DataHandler(new FileDataSource(path[i]));
			param3[i] = tmp_datahs;
		}
		Base64BinaryArray bba = new Base64BinaryArray();
		bba.setItem(param3);
		
		param.setDataHandlers(bba);
		String res = dcs.filesUpload(param).getEntrance();
		System.out.println(res);
		  CallService callService = new DfcWsCallServiceStub.CallService();
		  callService.setSendJsonString(sendJson);
		  CallServiceResponse  callServiceResponse = dcs.callService(callService);
		  String json= callServiceResponse.getEntrance();
		  return json;
	  }
	public static void main(String[] args) throws Exception{
		  String sendJson = "{\"ws\":{\"wsid\":101103,\"userId\":\"dmadmin\",\"path\":\"/昆山国显光电有限公司\"}}";
	      String json=os(sendJson);
	      System.out.println(json);

	  }
}
