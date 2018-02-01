package seahonor.action.custom;

import seahonor.util.SysNoForSelf;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class updatecustombzAction implements Action{

	@Override
	public String execute(RequestInfo info) {
		
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		SysNoForSelf sns = new SysNoForSelf();
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		String tableName = "";
		String sql = "Select tablename,id From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id=" + workflow_id + ")";
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		String mainId = "";
		sql = "select *  from " + tableName + " where requestid= " + requestid;
		rs.executeSql(sql);
		if (rs.next()) {
			mainId = Util.null2String(rs.getString("id"));
		}
		String id="";
		String xgseq="";
		sql="select * from "+ tableName +"_dt1 where mainid="+mainId;
		rs.executeSql(sql);
		while(rs.next()){
			id = Util.null2String(rs.getString("id"));
			xgseq = sns.getNum("EDC", "uf_edit_custom", 5);
			String sql_dt = "update "+ tableName +"_dt1 set xgseq='"+xgseq+"' where id="+id;
			rs_dt.executeSql(sql_dt);
			
		}
		return SUCCESS;
	}

}
