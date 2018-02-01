package seahonor.action.expenses;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateBorrowPZH implements Action{

	@Override
	public String execute(RequestInfo info) {
		String workflowID = info.getWorkflowid();
		String requestid = info.getRequestid();
		RecordSet rs = new RecordSet();
		String tableName = "";
		String mainid = "";
		String jkbz = "";
		String sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID
				+ ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		sql="select * from "+tableName+" where requestid="+requestid;
		rs.executeSql(sql);
		if(rs.next()){
			mainid = Util.null2String(rs.getString("id"));
			jkbz = Util.null2String(rs.getString("jkbz"));
		}
		if("0".equals(jkbz) || "2".equals(jkbz)){
			updatermb(tableName,mainid);
		}
		if("1".equals(jkbz) || "2".equals(jkbz)){
			updatewb(tableName,mainid);
		}
		return SUCCESS;
	}
	
	public void updatermb(String tableName,String mainId){
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		String jkid = "";
		String pzhm = "";
		String sql_dt = "";
		String sql="select * from "+tableName+"_dt1 where mainId="+mainId;
		rs.executeSql(sql);
		while(rs.next()){
			jkid = Util.null2String(rs.getString("jkid"));
			pzhm = Util.null2String(rs.getString("pzhm"));
			if(!"".equals(jkid)){
				sql_dt = "update uf_personal_borrow set pzhm = '"+pzhm+"' where id="+jkid;
				rs_dt.executeSql(sql_dt);
			}
		}
	}
	
	public void updatewb(String tableName,String mainId){
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		String jkid = "";
		String pzhm = "";
		String sql_dt = "";
		String sql="select * from "+tableName+"_dt2 where mainId="+mainId;
		rs.executeSql(sql);
		while(rs.next()){
			jkid = Util.null2String(rs.getString("jkid"));
			pzhm = Util.null2String(rs.getString("pzhm"));
			if(!"".equals(jkid)){
				sql_dt = "update uf_personal_borrow set pzhm = '"+pzhm+"' where id="+jkid;
				rs_dt.executeSql(sql_dt);
			}
		}
	}
}
