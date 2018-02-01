package seahonor.action.custom;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateRequestIDAction  implements Action{

	@Override
	public String execute(RequestInfo info) {
		// TODO Auto-generated method stub
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		String tableName = "";
		String sql = "Select tablename,id From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id=" + workflow_id + ")";
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		sql="update "+tableName+" set rqid=requestid where requestid="+requestid;
		rs.executeSql(sql);
		return SUCCESS;
	}

}
