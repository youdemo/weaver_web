package seahonor.action.expenses;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdatePzhmForRepayment implements Action{

	@Override
	public String execute(RequestInfo info) {
		BaseBean log = new BaseBean();
		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();
		RecordSet rs = new RecordSet();
		String tableName = "";
		String mainid = "";
		String pzhm = "";
		String hkbz = "";
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
		sql="select pzhm,hkbz from "+tableName+"_dt2 where mainid="+mainid;
		rs.executeSql(sql);
		while(rs.next()){
			pzhm = Util.null2String(rs.getString("pzhm"));
			hkbz = Util.null2String(rs.getString("hkbz"));
			updatePzhm(pzhm,hkbz,requestid);
		}
		
		sql="select pzhm,hkbz from "+tableName+"_dt3 where mainid="+mainid;
		rs.executeSql(sql);
		while(rs.next()){
			pzhm = Util.null2String(rs.getString("pzhm"));
			hkbz = Util.null2String(rs.getString("hkbz"));
			updatePzhm(pzhm,hkbz,requestid);
		}
		return SUCCESS;
	}
	
	public void updatePzhm(String pzhm,String hkbz,String xglc){
		RecordSet rs = new RecordSet();
		String sql="";
		sql="update uf_person_repayment set pzhm='"+pzhm+"' where xglc="+xglc+" and bz="+hkbz;
		rs.executeSql(sql);
	}
	

}
