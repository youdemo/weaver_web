package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信申请客户证章退还进行中表单建模状态改变
public class CustomerBadgeReturnGoingWorkflowAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("进入客户证章退还进行中CustomerBadgeReturnGoingWorkflowAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		String tableName = "";
		String CustomerBadgeRet = "";//客户证章退还
			
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
				//分割其中“，”的字符串CustomerBadgeReturn是客户印章字段，
				String[] CustomerBadgeRet1 = Util.null2String(rs.getString("CustomerBadgeReturn")).split(",");//客户证章退还		
				//String[] LendSeal1 = Util.null2String(rs.getString("LendSeal")).split(",");//借出印章
				log.writeLog("海之信申请退还印章流程中状态变为退还中CustomerBadgeRet1=" + CustomerBadgeRet1);
				for (int i = 0; i < CustomerBadgeRet1.length; i++) {
					//截取其中的数据，是从0开始算到最后。如32_13
					CustomerBadgeRet = CustomerBadgeRet1[i].substring(3);
					log.writeLog("海之信申请退还印章流程中状态变为退还中LendSeal=" + CustomerBadgeRet);
					String sql_up = "update uf_badges set State = '6' where id in (" + CustomerBadgeRet + ")";
					rs.execute(sql_up);
					log.writeLog("客户证章退还状态更新sql_up=" + sql_up);
				}
			}
				/*//4是代表退还状态	//1、正常；2、失效；3、已借出；4、已退还；5、借出中；6、退还中。
				String sql_up = "update uf_badges set State = '4' where id in (" + CustomerBadgeRet1 + ")";
				rs.execute(sql_up);
				log.writeLog("客户证章退还状态更新sql_up=" + sql_up);*/
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}