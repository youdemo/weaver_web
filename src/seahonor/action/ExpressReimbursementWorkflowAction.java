
package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信快递报销Action
public class ExpressReimbursementWorkflowAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("进入快递报销ExpressReimbursementWorkflowAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();

		String tableName = "";
		String RemainingLoan = "";//剩余借款项目借款）
		String project = "";//项目名称

		String sql_1 = "";
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
				RemainingLoan = Util.null2String(rs.getString("RemainingLoan"));//剩余借款项目借款）
				project = Util.null2String(rs.getString("project"));//抵消金额（项目借款）
				
				sql_1 = "update  uf_ProjectCost set BorrowingAmount ='"+ RemainingLoan + "' where ProjectName='" + project + "'";
				rs1.execute(sql_1);		
				log.writeLog("快递报销sql_1" + sql_1);
			}
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}