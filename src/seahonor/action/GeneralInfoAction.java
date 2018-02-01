package seahonor.action;

import seahonor.util.GeneralNowInsert;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class GeneralInfoAction implements Action{

	/*
	 *   表单建模每次存储备份
	 */
	public String execute(RequestInfo request) {
		String requestid = request.getRequestid();
		RecordSet rs = new RecordSet();
		String table_name = "";
		String billId = "";
		// 表单建模寻找表名
		String sql = "Select id,tablename From Workflow_bill Where id in( "
					+"select formid from modeinfo where id="+request.getWorkflowid()+")";
		rs.executeSql(sql);
		if(rs.next()){
			table_name = Util.null2String(rs.getString("tablename"));
			billId = Util.null2String(rs.getString("id"));
		}
		
		GeneralNowInsert gni = new GeneralNowInsert();
		gni.insertNow(billId, table_name, "id", requestid);
		
		return SUCCESS;
	}

}
