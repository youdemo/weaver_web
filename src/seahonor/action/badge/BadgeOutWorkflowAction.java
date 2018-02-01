
package seahonor.action.badge;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信申请借出印章流程中印章借出中表单建模状态改变
public class BadgeOutWorkflowAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("申请借出印章流程中印章借出中BadgeFailureWorkflowAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String tableName = "";
		String LendSeal = "";//借出印章
		String Application = "";//流程申请人
			
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
		/*	while (rs.next()) {		
				//分割其中“，”的字符串
				String[] LendSeal1 = Util.null2String(rs.getString("LendSeal")).split(",");//借出印章
				Application = Util.null2String(rs.getString("Application"));
				log.writeLog("借出印章流程状态为借出中更新LendSeal1=" + LendSeal1);
				for (int i = 0; i < LendSeal1.length; i++) {   
					//截取其中的数据，是从0开始算到最后。如32_13
					LendSeal = LendSeal1[i].substring(3);
					log.writeLog("借出印章流程状态为借出中更新LendSeal=" + LendSeal);
					String sql_up = "update uf_badges set State = '5' , ProcessApplicant ='"+Application+"' where id in (" + LendSeal + ")";
					rs.execute(sql_up);
					log.writeLog("借出印章流程状态为借出中更新sql_up=" + sql_up);
				}
			}*/
			//1、正常；2、失效；3、已借出；4、已退还；5、借出中
			while (rs.next()) {		
				 LendSeal = Util.null2String(rs.getString("LendSeal1"));//借出印章
				 Application = Util.null2String(rs.getString("Application"));
				 String sql_up = "update uf_badges set State = '5' , ProcessApplicant ='"+Application+"' where id in (" + LendSeal + ")";
					rs1.execute(sql_up);
					log.writeLog("借出印章流程状态为借出中更新sql_up=" + sql_up);
			}
		
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}