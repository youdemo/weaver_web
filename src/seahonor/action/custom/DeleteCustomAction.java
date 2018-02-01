package seahonor.action.custom;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class DeleteCustomAction implements Action {
	public String execute(RequestInfo info) {
		BaseBean log = new BaseBean();
		String requestid = info.getRequestid();
		String workflowID = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
        String sql = "";
        String sql_detail = "";
        String tableName = "";
        String mainid="";
        sql = " Select tablename From Workflow_bill Where id in (Select formid From workflow_base Where id= " + workflowID + ")";
        rs.execute(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }
        sql = "select * from " + tableName + " where requestid= " + requestid;
		rs.executeSql(sql);
		if (rs.next()) {
			mainid = Util.null2String(rs.getString("id"));
		}
		String gs="";
		sql = "select * from "+tableName+"_dt1 where mainid="+mainid;
		//log.writeLog("deletecustom:sql"+sql);
		rs.executeSql(sql);
		while(rs.next()){
			gs = Util.null2String(rs.getString("gs"));
			sql_detail="update uf_custom set CutomStatus='4' where id="+gs;
			//log.writeLog("deletecustom:sql_detail"+sql_detail);
			rs_dt.executeSql(sql_detail);
		}
		return SUCCESS;
	}
}
