package seahonor.action.express;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信快递公司待结算审批流程归档更新待结算为已结算
public class UpdateForTheCostWorkfAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("海之信快递公司待结算审批流程归档更新待结算为已结算UpdateForTheCostWorkfAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();
		String mainid ="";
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String tableName = "";

		String val = "";//页面隐藏的传值ids	
		String Money ="";
		String sql = "";
		sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID
				+ ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		
		
		
		if (!"".equals(tableName)) {
			// 查询主表
			sql = "select * from "+tableName + " where requestid="+requestid;
			rs.execute(sql);
			if (rs.next()) {			
				mainid = Util.null2String(rs.getString("id"));	
				
			}
			sql="select * from "+tableName+"_dt1 where mainid="+mainid;
			 rs.execute(sql);
			while(rs.next()){
				val = Util.null2String(rs.getString("val"));//页面隐藏的传值id
				Money = Util.null2String(rs.getString("Money"));
				//记得加0这个数字把(24,25,)改为(24,25,0)
				String sql_up = "update formtable_main_76_dt1 set State = '1',Money='"+Money+"' where id in (" + val + ")";
				rs1.execute(sql_up);
				
			}											
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}

