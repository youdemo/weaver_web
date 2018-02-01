
package seahonor.action.badge;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class BadgeUpdateWorkflowAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("BadgeUpdateWorkflowAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		String tableName = "";
		InsertUtil iu = new InsertUtil();
		
		String sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID
				+ ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		if (!"".equals(tableName)) {
			// 查询主表
			sql = "select * from " + tableName + " where requestid="+ requestid;
			rs.execute(sql);
			if (rs.next()) {		
				String Badge =  Util.null2String(rs.getString("Badge"));
				String Customer =  Util.null2String(rs.getString("Customer"));
				String BadgeType =  Util.null2String(rs.getString("BadgeType"));
				String Name =  Util.null2String(rs.getString("Name"));
				String unit =  Util.null2String(rs.getString("unit"));
				String Numbers =  Util.null2String(rs.getString("Numbers"));
				String StartDate =  Util.null2String(rs.getString("StartDate"));
				String ValidTerm =  Util.null2String(rs.getString("ValidTerm"));
				String Remark =  Util.null2String(rs.getString("Remark"));
				String type =  "";
				
				sql = "select * from uf_BadgesClassify where id="+BadgeType;
				rs.executeSql(sql);
				if(rs.next()){
					type = Util.null2String(rs.getString("type"));
				}
				Map<String, String> mapStr = new HashMap<String, String>();
				mapStr.put("Customer", Customer);mapStr.put("BadgeType", BadgeType);
				mapStr.put("Name", Name);mapStr.put("unit", unit);
				mapStr.put("Numbers", Numbers);mapStr.put("StartDate", StartDate);
				mapStr.put("ValidTerm", ValidTerm);mapStr.put("Remark", Remark);
				mapStr.put("type", type);
				
				iu.update(mapStr, "uf_badges", "id", Badge);
				
			//	log.writeLog("借出印章流程状态为借出中更新sql_up=" );
			}
		
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}