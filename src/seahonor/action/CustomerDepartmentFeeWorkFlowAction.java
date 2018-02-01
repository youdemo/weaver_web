package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信寄快递action
public class CustomerDepartmentFeeWorkFlowAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("进入借出印章CustomerDepartmentFeeWorkFlowAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();

		String tableName = "";
		String Money = "";// 费用金额
		String SenderDepartment = "";// 部门
		String ExpenseParty = "";// 费用承担方 0代表客户承担;1代表公司承担
		String Customer = "";// 客户
		String custom = "";//表单建模中的客户
		String Department = "";//表单建模中的部门
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
			sql = "select * from " + tableName + " where requestid="
					+ requestid;
			rs.execute(sql);
			if (rs.next()) {
				SenderDepartment = Util.null2String(rs.getString("SenderDepartment"));//寄快递人部门
				Money = Util.null2String(rs.getString("Money"));//金额
				ExpenseParty = Util.null2String(rs.getString("ExpenseParty"));//费用承担方
				Customer = Util.null2String(rs.getString("Customer"));//客户

				if (ExpenseParty.equals("0")) {
					// 插入到客户里面
					/*
					 * sql_1 = "insert into uf_custom(CustomerAmount,custom) "
					 * +"values('"+ Money+"','"+Customer+"')";
					 */
					// sql_1 =
					// "update uf_custom set CustomerAmount +='"+Money+"' where custom='"+Customer+"'";
					String sql_2 = "select * from uf_CustomeAmount where custom = '"+Customer+"'";
					
					rs1.execute(sql_2);
					if (rs1.next()) {
						custom = Util.null2String(rs1.getString("custom"));
					}
					log.writeLog("客户ID" + custom);
					if (!"".equals(custom)) {
						sql_1 = "update  uf_CustomeAmount set CustomerAmount = isnull(CustomerAmount,0) +"
								+ Money + " where custom='" + Customer + "'";
						rs1.execute(sql_1);
						
					}else{
						sql_1 = "insert into uf_CustomeAmount(custom,CustomerAmount) values('"
								+ Customer + "','" + Money + "')";
						rs1.execute(sql_1);
						// 拿取权限表ID
						String sql_Datashare = "select * from uf_CustomeAmount where id = (select  MAX(id) from uf_CustomeAmount)";
						rs1.execute(sql_Datashare);
						log.writeLog("客户费用ID1" + sql_Datashare);
						if (rs1.next()) {
							String ID = Util.null2String(rs1.getString("id"));
							sql_1 = "exec sh_custome_right " + ID;
							rs2.execute(sql_1);
							log.writeLog("客户费用ID2" + sql_1);
						}
					
					}
				}else{
					// 插入到公司部门金额里面
					// sql_1 =
					// "update uf_CompanyDepartFee set CostAmount +='"+Money+"' where Department='"+SenderDepartment+"'";
					String sql_2 = "select * from uf_CompanyDepartFee where department = '"+SenderDepartment+"'";
					rs1.execute(sql_2);
					if (rs1.next()) {
						Department = Util.null2String(rs1.getString("Department"));
					}
					if (Department.equals("")) {
						sql_1 = "insert into uf_CompanyDepartFee(Department,CostAmount) values('"
								+ SenderDepartment + "','" + Money + "')";
						rs1.execute(sql_1);

						// 拿取权限表ID
						String sql_Datashare = "select * from uf_CompanyDepartFee where id = (select  MAX(id) from uf_CompanyDepartFee)";
						rs1.execute(sql_Datashare);
						log.writeLog("公司部门费用ID1" + sql_Datashare);
						if (rs1.next()) {
							String ID = Util.null2String(rs1.getString("id"));
							sql_1 = "exec sh_replace_right " + ID;
							rs2.execute(sql_1);
							log.writeLog("公司部门费用ID2" + sql_1);
						}
					}else{
						sql_1 = "update uf_CompanyDepartFee set CostAmount = isnull(CostAmount,0) +"
								+ Money+ " where Department='"+ SenderDepartment + "'";
						/*
						 * sql_1 =
						 * "insert into uf_CompanyDepartFee(Department,CostAmount) values('"
						 * + SenderDepartment + "','" + Money + "')";
						 */
						rs1.execute(sql_1);
					}
				}
			}
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}