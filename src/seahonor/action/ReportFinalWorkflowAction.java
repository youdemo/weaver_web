package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信报告定稿后状态变为定稿状态
public class ReportFinalWorkflowAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("进入报告定稿后ReportFinalWorkflowAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		String tableName = "";
		String China = "";//中文报告
		String English = "";//英文报告
		String Japan = "";//日文报告
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
				China = Util.null2String(rs.getString("China"));
				English = Util.null2String(rs.getString("English"));
				Japan = Util.null2String(rs.getString("Japan"));
				DocumentId = Util.null2String(rs.getString("DocumentId"));
		}
			//0、未定稿 1、已定稿 2、已作废
			//如果China有的话，就是流程check中就是1，没有就是空
		if (China.equals("1")) {
			String sql_up = "update uf_report set DocumentStatus1 = '1' where DocumentId = '"+DocumentId+"'";
			rs.execute(sql_up);
			log.writeLog("中文报告定稿状态更新sql_up=" + sql_up);
		}
		if (English.equals("1")) {
			String sql_up = "update uf_report set DocumentStatus2 = '1' where DocumentId = '"+DocumentId+"'";
			rs.execute(sql_up);
			log.writeLog("英文报告定稿状态更新sql_up=" + sql_up);
		}
		if (Japan.equals("1")) {
			String sql_up = "update uf_report set DocumentStatus3 = '1' where DocumentId = '"+DocumentId+"'";
			rs.execute(sql_up);
			log.writeLog("日文报告定稿状态更新sql_up=" + sql_up);
		}
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}