package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信申请归还印章流程中表单建模状态改变
public class BadgeReturnWorkflowAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("进入归还印章BadgeReturnWorkflowAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		String tableName = "";
		String ReturnSeal = "";//归还印章
			
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
				ReturnSeal = Util.null2String(rs.getString("ReturnSeal"));//归还印章			
				/*  
				 * 这是树形的代码
				 * //分割其中“，”的字符串
				String[] ReturnSeal1 = Util.null2String(rs.getString("ReturnSeal")).split(",");//归还印章	
				log.writeLog("海之信申请归还印章状态为正常ReturnSeal=" + ReturnSeal);
				for (int i = 0; i < ReturnSeal1.length; i++) {
					//截取其中的数据，是从0开始算到最后。如32_13
					ReturnSeal = ReturnSeal1[i].substring(3);
					log.writeLog("海之信申请归还印章状态为正常sql_up=ReturnSeal=" + ReturnSeal);
					String sql_up = "update uf_badges set State = '1' where id in (" + ReturnSeal + ")";
					rs.execute(sql_up);
					log.writeLog("海之信申请归还印章状态更新sql_up=" + sql_up);
				}
*/
			}
		
				String sql_up = "update uf_badges set State = '1' where id in (" + ReturnSeal + ")";
				rs.execute(sql_up);
				log.writeLog("归还印章状态更新sql_up=" + sql_up);
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}