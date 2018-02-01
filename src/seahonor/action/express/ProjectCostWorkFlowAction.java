package seahonor.action.express;

import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信寄快递中项目费用action
public class ProjectCostWorkFlowAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("进入寄快递ProjectCostWorkFlowAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();

		String tableName = "";
		String Money = "";// 快递费用金额
		String ProjectName = "";//表单建模中的项目名称
		String BelongsToProject = "";//所属项目名称
		int Signed = 0;//填写人
		/*String SenderDepartment = "";// 部门
		String ExpenseParty = "";// 费用承担方 0代表客户承担;1代表公司承担
		String Customer = "";// 客户
		String custom = "";//表单建模中的客户
		
		String Department = "";//表单建模中的部门*/

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
				Signed =rs.getInt("Signed");
				BelongsToProject = Util.null2String(rs.getString("BelongsToProject"));//所属项目名称
				Money = Util.null2String(rs.getString("Money"));//快递费用金额
				//ExpenseParty = Util.null2String(rs.getString("ExpenseParty"));//费用承担方
				//Customer = Util.null2String(rs.getString("Customer"));//客户
				//查询项目费用表单建模
				String sql_2 = "select * from uf_ProjectCost where ProjectName = '"+BelongsToProject+"'";
					
					rs1.execute(sql_2);
					if (rs1.next()) {
						ProjectName = Util.null2String(rs1.getString("ProjectName"));//表单建模中的项目名称
					}
					log.writeLog("客户ID" + ProjectName);
					if (!"".equals(ProjectName)) {
						sql_1 = "update  uf_ProjectCost set ProjectAmount = isnull(ProjectAmount,0) +"
								+ Money + " where ProjectName='" + BelongsToProject + "'";
						rs1.execute(sql_1);
						
					}else{
						//要插入formmodeid这个字段，为了在前台表单建模中可以看的到，想到于在数据库中更新了formmodeid
						sql_1 = "insert into uf_ProjectCost(formmodeid,ProjectName,ProjectAmount) values(74,'"
								+ BelongsToProject + "','" + Money + "')";
						rs1.execute(sql_1);
						// 拿取权限表ID
						String sql_Datashare = "select * from uf_ProjectCost where id = (select  MAX(id) from uf_ProjectCost)";
						rs1.execute(sql_Datashare);
						log.writeLog("客户费用sql" + sql_Datashare);
						if (rs1.next()) {
							int ID =rs1.getInt("id");
							log.writeLog("客户费用1111ID" + ID);
							ModeRightInfo ModeRightInfo = new ModeRightInfo();
							//ModeRightInfo.editModeDataShare(creater,modeid,m_billid);
							ModeRightInfo.editModeDataShare(Signed,74,ID);
							log.writeLog("客户费用2222ID" + ID);
						}
						
						
						/*String sql_Datashare = "select * from uf_ProjectCost where id = (select  MAX(id) from uf_ProjectCost)";
						rs1.execute(sql_Datashare);
						log.writeLog("客户费用ID" + sql_Datashare);
						if (rs1.next()) {
							String ID = Util.null2String(rs1.getString("id"));
							sql_1 = "exec ProjectCost_right " + ID;
							rs2.execute(sql_1);
							log.writeLog("客户费用ID2" + sql_1);
					
					}*/
				}
			}
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}