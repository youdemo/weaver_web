package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信新建证章审批流程中表单建模状态改变
public class NewBadgeApproveWorkflowAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("海之信新建证章审批流程中NewBadgeApproveWorkflowAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		String tableName = "";
		String Name = "";//证章名称
			
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
			while (rs.next()) {	
				Name = Util.null2String(rs.getString("Name"));//证章名称
			}
				//4是代表退还状态
				 //1、正常；2、失效；3、已借出；4、已退还；5、借出中；6、退还中、7未审批。
				String sql_up = "update uf_badges set State = '1' where Name in ('" + Name + "')";
				rs.execute(sql_up);
				log.writeLog("海之信新建证章审批流程中sql_up=" + sql_up);
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}