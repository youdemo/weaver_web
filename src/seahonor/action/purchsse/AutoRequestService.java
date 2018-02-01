package seahonor.action.purchsse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.soa.workflow.request.Cell;
import weaver.soa.workflow.request.DetailTable;
import weaver.soa.workflow.request.DetailTableInfo;
import weaver.soa.workflow.request.MainTableInfo;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.RequestService;
import weaver.soa.workflow.request.Row;

/*
 *   金红叶 流程创建和状态查看接口
 */
public class AutoRequestService extends BaseBean {

	public AutoRequestService() {

	}

	BaseBean log = new BaseBean();

	public String createRequest(String workflowCode, String strJson,
			String createrid,String mainid) {

		Map<String, String> retMap = new HashMap<String, String>();
		RecordSet rs = new RecordSet();
		log.writeLog("json" + strJson);
		// 获取触发哪条流程
		String workflowID = workflowCode;
		String sql = "select count(1) as count from workflow_base where id="
				+ workflowID;
		rs.executeSql(sql);
		int count = 0;
		if (rs.next()) {
			count = rs.getInt("count");
		}
		if (count <= 0) {
			retMap.put("MSG_TYPE", "E");
			retMap.put("MSG_CONTENT", "流程号错误！");
			retMap.put("OA_ID", "0");

			return getJsonStr(retMap);
		}

		// 解析 json 获取人员的编号 REQ_BP
		String creater = createrid;

		if (creater.length() > 0 && !"1".equals(creater)) {
			sql = "select count(1) as count_cc from hrmresource "
					+ "where id='" + creater + "' and status in(0,1,2,3)";
			rs.executeSql(sql);
			if (rs.next()) {
				int count_cc = rs.getInt("count_cc");
				if (count_cc == 0) {
					creater = "";
				}
			}
		}

		if (creater.length() < 1) {
			retMap.put("MSG_TYPE", "E");
			retMap.put("MSG_CONTENT", "人员编号无法匹配！");
			retMap.put("OA_ID", "0");

			return getJsonStr(retMap);
		}

		String requestLevel = "0";
		String remindType = "0";
		String requestid = "";

		// 根据 workflowCode 查询实际的流程

		RequestInfo requestinfo = new RequestInfo();

		// 放主表数据
		MainTableInfo mti = new MainTableInfo();
		try {
			mti = getMainTableInfo(strJson);
		} catch (Exception e2) {
			retMap.put("MSG_TYPE", "E");
			retMap.put("MSG_CONTENT", "主表Json格式错误或者匹配配置表错误！");
			retMap.put("OA_ID", "0");
			e2.printStackTrace();
			return getJsonStr(retMap);
		}
		if (mti == null) {
			retMap.put("MSG_TYPE", "E");
			retMap.put("MSG_CONTENT", "主表Json格式错误或者匹配配置表错误！");
			retMap.put("OA_ID", "0");

			return getJsonStr(retMap);
		}

		DetailTableInfo dti = new DetailTableInfo();
		try {
			dti = getDetailTableInfo(workflowCode, strJson);
		} catch (Exception e1) {
			e1.printStackTrace();
			retMap.put("MSG_TYPE", "E");
			retMap.put("MSG_CONTENT", "明细Json格式错误或者匹配配置表错误！");
			retMap.put("OA_ID", "0");
			return getJsonStr(retMap);
		}
	
		requestinfo.setDetailTableInfo(dti);
		requestinfo.setMainTableInfo(mti);
		requestinfo.setCreatorid(creater);
		requestinfo.setDescription(getRequestName(workflowID, creater));
		requestinfo.setWorkflowid(workflowID);
		requestinfo.setRequestlevel(requestLevel);
		requestinfo.setRemindtype(remindType);
		RequestService res = new RequestService();
		try {
			requestid = res.createRequest(requestinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String return_type = "S";
		String return_message = "";

		int s_requestid = Integer.parseInt(requestid);
		if (s_requestid < 1) {
			return_type = "E";
		}else{
			updatePurchaseStatus(mainid);
		}
		// else{
		//
		// // 更新流程名称
		// String requestName = getRequestName(workflowID,creater);
		// sql =
		// "update WORKFLOW_REQUESTBASE set requestname='"+requestName+"' where REQUESTID="+requestid;
		// rs.executeSql(sql);
		// }

		// 返回值处理

		retMap.put("MSG_TYPE", return_type);
		retMap.put("MSG_CONTENT", return_message);
		retMap.put("OA_ID", requestid);

		return getJsonStr(retMap);
	}

	private String getRequestName(String workflowID, String creater) {
		String requestName = "";
		String sql = "select workflowname+'-'+CONVERT(varchar(20) , getdate(), 23 )  as r_name " +
				"from workflow_base where id=" + workflowID;
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		if (rs.next()) {
			requestName = Util.null2String(rs.getString("r_name"));
		}
		return requestName;
	}

	private DetailTableInfo getDetailTableInfo(String workflowCode,
			String jsonStr) throws Exception {
		DetailTableInfo details = new DetailTableInfo();
		RecordSet rs = new RecordSet();
		RecordSet rs_detail = new RecordSet();
		JSONObject json = null;
		String dtFlag = "";
		String sql_detail = "";
		int id = -1;

		json = new JSONObject(jsonStr);

		List<DetailTable> list_detail = new ArrayList<DetailTable>();

		JSONArray arr = json.getJSONArray("DETAILS");
		List<Row> list_row = new ArrayList<Row>();
		DetailTable dt = new DetailTable();
		for (int i = 0; i < arr.length(); i++) {
			JSONObject jo = arr.getJSONObject(i);
			
			Row row = new Row();
			List<Cell> list_cell = new ArrayList<Cell>();	
			        String val ="";
					Cell cel = new Cell();
					cel.setName("Name");

					val = jo.getString("Name");
					if (Util.null2String(val).length() > 0) {
						cel.setValue(val);
						list_cell.add(cel);
					}
					Cell cel1 = new Cell();
					cel1.setName("val_1");

					val = jo.getString("val_1");
					if (Util.null2String(val).length() > 0) {
						cel1.setValue(val);
						list_cell.add(cel1);
					}
					Cell cel2 = new Cell();
					cel2.setName("Numbers");

					val = jo.getString("Numbers");
					if (Util.null2String(val).length() > 0) {
						cel2.setValue(val);
						list_cell.add(cel2);
					}
					Cell cel3 = new Cell();
					cel3.setName("QueryPrice");

					val = jo.getString("QueryPrice");
					if (Util.null2String(val).length() > 0) {
						cel3.setValue(val);
						list_cell.add(cel3);
					}
					Cell cel4 = new Cell();
					cel4.setName("Remark");
					val = jo.getString("Remark");
					if (Util.null2String(val).length() > 0) {
						cel4.setValue(val);
						list_cell.add(cel4);
					}
					
					Cell cel5 = new Cell();
					cel5.setName("ActualNumbers");

					val = jo.getString("ActualNumbers");
					if (Util.null2String(val).length() > 0) {
						cel5.setValue(val);
						list_cell.add(cel5);
					}
					Cell cel6 = new Cell();
					cel6.setName("Stock");

					val = jo.getString("Stock");
					if (Util.null2String(val).length() > 0) {
						cel6.setValue(val);
						list_cell.add(cel6);
					}
					Cell cel7 = new Cell();
					cel7.setName("AvgNum");

					val = jo.getString("AvgNum");
					if (Util.null2String(val).length() > 0) {
						cel7.setValue(val);
						list_cell.add(cel7);
					}
					Cell cel8 = new Cell();
					cel8.setName("AvgPrice");

					val = jo.getString("AvgPrice");
					if (Util.null2String(val).length() > 0) {
						cel8.setValue(val);
						list_cell.add(cel8);
					}
					Cell cel9 = new Cell();
					cel9.setName("lastPrice");

					val = jo.getString("lastPrice");
					if (Util.null2String(val).length() > 0) {
						cel9.setValue(val);
						list_cell.add(cel9);
					}
		
			int size = list_cell.size();
			Cell cells[] = new Cell[size];
			for (int index = 0; index < list_cell.size(); index++) {
				cells[index] = list_cell.get(index);
			}
			row.setCell(cells);
			row.setId("" + i);
			list_row.add(row);
		}
		int size = list_row.size();
		// if(size == 0) break;
		Row rows[] = new Row[size];
		for (int index = 0; index < list_row.size(); index++) {

			rows[index] = list_row.get(index);
		}
		dt.setRow(rows);
		dt.setId("1");
		list_detail.add(dt);

		 size = list_detail.size();
		DetailTable detailtables[] = new DetailTable[size];
		for (int index = 0; index < list_detail.size(); index++) {
			detailtables[index] = list_detail.get(index);
		}
		details.setDetailTable(detailtables);
		return details;

	}

	// 获取主表的 Property
	private MainTableInfo getMainTableInfo(String jsonStr) throws Exception {
		MainTableInfo mti = new MainTableInfo();
		RecordSet rs = new RecordSet();

		List<Property> list = new ArrayList<Property>();
		JSONObject json = null;
		try {
			json = new JSONObject(jsonStr).getJSONObject("HEADER");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (json == null)
			return null;

		Property pro = new Property();
		pro.setName("Year");
		String val = "";
		val = json.getString("Year");

		if (Util.null2String(val).length() > 0) {
			pro.setValue(val);
			list.add(pro);

		}
		Property pro1 = new Property();
		pro1.setName("Month");
		val = json.getString("Month");

		if (Util.null2String(val).length() > 0) { 
			pro1.setValue(val);
			list.add(pro1);

		}
		Property pro2 = new Property();
		pro2.setName("Creater");
		val = json.getString("Creater");

		if (Util.null2String(val).length() > 0) {
			pro2.setValue(val);
			list.add(pro2);

		}
		
		Property pro3 = new Property();
		pro3.setName("CreateDate");
		val = json.getString("CreateDate");

		if (Util.null2String(val).length() > 0) {
			pro3.setValue(val);
			list.add(pro3);

		}
		
		Property pro4 = new Property();
		pro4.setName("ApplicationDate");
		val = json.getString("ApplicationDate");

		if (Util.null2String(val).length() > 0) {
			pro4.setValue(val);
			list.add(pro4);

		}
		
		Property pro5 = new Property();
		pro5.setName("Application");
		val = json.getString("Application");

		if (Util.null2String(val).length() > 0) {
			pro5.setValue(val);
			list.add(pro5);

		}
		
		Property pro6 = new Property();
		pro6.setName("appTime");
		val = json.getString("appTime");

		if (Util.null2String(val).length() > 0) {
			pro6.setValue(val);
			list.add(pro6);

		}
		Property pro7 = new Property();
		pro7.setName("CreateTime");
		val = json.getString("CreateTime");

		if (Util.null2String(val).length() > 0) {
			pro7.setValue(val);
			list.add(pro7);

		}
		Property pro8 = new Property();
		pro8.setName("sffcgcg");
		val = json.getString("sffcgcg");

		if (Util.null2String(val).length() > 0) {
			pro8.setValue(val);
			list.add(pro8);

		}
		Property pro9 = new Property();
		pro9.setName("xqsqr");
		val = json.getString("xqsqr");

		if (Util.null2String(val).length() > 0) {
			pro9.setValue(val);
			list.add(pro9);

		}

		int size = list.size();
		if (size == 0)
			return null;

		Property properties[] = new Property[size];
		for (int index = 0; index < list.size(); index++) {
			properties[index] = list.get(index);
		}
		mti.setProperty(properties);
		return mti;
	}

	// map转json格式
	private String getJsonStr(Map<String, String> map) {
		JSONObject json = new JSONObject();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = map.get(key);
			try {
				json.put(key, value);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return json.toString();
	}
    
	private void updatePurchaseStatus(String mainid){
		String sql="update uf_Purchase set status=1 where id = "+mainid;
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
	}
}
