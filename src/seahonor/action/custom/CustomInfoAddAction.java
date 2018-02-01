package seahonor.action.custom;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;

import weaver.conn.RecordSet;
//import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class CustomInfoAddAction implements Action{

	/*
	 *   记录当前客户信息(新增时)
	 */
//	private BaseBean log = new BaseBean();
	public String execute(RequestInfo request) {
		String requestid = request.getRequestid();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		
		System.out.println("getWorkflowid = " + request.getWorkflowid());
		System.out.println("requestid = " + requestid);
		
		String table_name = "uf_custom";
		
		Map<String, String> mapStr = new HashMap<String, String>();
		
		String sql = "select customName,customCode,Country,enterpriseType,industryType,Telphone,Fax,"
			+"Address,Postcode,email,webSite,EnQuickLook,JpQuickLook,Remark,Provider,CutomStatus,"
			+"Modifier,ModifyTime,UpdateTime,version from "+table_name+"  where id="+requestid;
		rs.execute(sql);
		if(rs.next()){
			mapStr.put("customName", Util.null2String(rs.getString("customName")));
			mapStr.put("customCode", Util.null2String(rs.getString("customCode")));
			mapStr.put("Country", Util.null2String(rs.getString("Country")));
			mapStr.put("enterpriseType", Util.null2String(rs.getString("enterpriseType")));
			mapStr.put("industryType", Util.null2String(rs.getString("industryType")));
			mapStr.put("Telphone", Util.null2String(rs.getString("Telphone")));
			mapStr.put("Fax", Util.null2String(rs.getString("Fax")));
			mapStr.put("Address", Util.null2String(rs.getString("Address")));
			mapStr.put("Postcode", Util.null2String(rs.getString("Postcode")));
			mapStr.put("email", Util.null2String(rs.getString("email")));
			mapStr.put("webSite", Util.null2String(rs.getString("webSite")));
			mapStr.put("EnQuickLook", Util.null2String(rs.getString("EnQuickLook")));
			mapStr.put("JpQuickLook", Util.null2String(rs.getString("JpQuickLook")));
			mapStr.put("Remark", Util.null2String(rs.getString("Remark")));
			mapStr.put("Provider", Util.null2String(rs.getString("Provider")));
			mapStr.put("CutomStatus", Util.null2String(rs.getString("CutomStatus")));
			mapStr.put("Modifier", Util.null2String(rs.getString("Modifier")));
			mapStr.put("ModifyTime", Util.null2String(rs.getString("ModifyTime")));
			mapStr.put("UpdateTime", Util.null2String(rs.getString("UpdateTime")));
			mapStr.put("version", Util.null2String(rs.getString("version")));
		}
		
		if(mapStr.size() > 0){
			mapStr.put("SuperID", requestid);
			iu.insert(mapStr, table_name);
		}
		
		return SUCCESS;
	}
}
