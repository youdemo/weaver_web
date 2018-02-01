package seahonor.action.report;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信报告作废后状态变为已作废状态
public class ReportInvalidWorkflowAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("进入报告作废后ReportModifyWorkflowAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		String tableName = "";
		String DocumentId = "";//文档编号
			
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
				DocumentId = Util.null2String(rs.getString("DocumentId"));
		}
			//0、未定稿 1、已定稿 2、已作废
			//报告状态：0为正常，1为报废
			//如果China有的话，就是流程check中就是1，没有就是空	
			String sql_up = "update uf_report set DocumentStatus1 = '2',DocumentStatus2 = '2',DocumentStatus3 = '2'"
						+",ReportState = '1' where DocumentId = '"+DocumentId+"'";
			rs.execute(sql_up);
			log.writeLog("报告作废状态更新sql_up=" + sql_up);
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}