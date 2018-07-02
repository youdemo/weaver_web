package seahonor.action.expenses;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdatePzhmForBorrow implements Action{

	@Override
	public String execute(RequestInfo info) {
		BaseBean log = new BaseBean();
		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();
		RecordSet rs = new RecordSet();
		String tableName = "";
		String mainid = "";
		String pzhm = "";
		String jkxxid = "";
		String sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID
				+ ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;	
		rs.executeSql(sql);
		if (rs.next()) {
			mainid = Util.null2String(rs.getString("id"));
		}
		sql="select pzhm,jkxxid from "+tableName+"_dt2 where mainid="+mainid+" and (jkxxid is not null and jkxxid <> '')";
		rs.executeSql(sql);
		while(rs.next()){
			pzhm = Util.null2String(rs.getString("pzhm"));
			jkxxid = Util.null2String(rs.getString("jkxxid"));
			updatePzhm(pzhm,jkxxid);
		}
		
		sql="select pzhm,jkxxid from "+tableName+"_dt3 where mainid="+mainid+" and (jkxxid is not null and jkxxid <> '')";
		rs.executeSql(sql);
		while(rs.next()){
			pzhm = Util.null2String(rs.getString("pzhm"));
			jkxxid = Util.null2String(rs.getString("jkxxid"));
			updatePzhm(pzhm,jkxxid);
		}
		return SUCCESS;
	}
	
	public void updatePzhm(String pzhm,String jkxxid){
		RecordSet rs = new RecordSet();
		String sql="";
		sql="update uf_personal_borrow set pzhm='"+pzhm+"' where id="+jkxxid;
		rs.executeSql(sql);
	}
	

}
