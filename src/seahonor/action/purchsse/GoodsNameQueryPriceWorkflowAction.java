package seahonor.action.purchsse;

import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信月度采购消耗品供应商询价action
public class GoodsNameQueryPriceWorkflowAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("海之信月度采购消耗品供应商询价GoodsNameQueryPriceWorkflowAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		RecordSet rs3 = new RecordSet();

		String tableName = "";
		String tableNamedt = "";
		String mainID = "";
		
		String id = "";//明细表中的id
		String QueryPrice = "";//询价
		
		String sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID
				+ ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		if (!"".equals(tableName)) {
			tableNamedt = tableName + "_dt1";//这个方法是为了有多个明细做的准备
			// 查询主表
			sql = "select * from "+tableName + " where requestid="+requestid;
			rs1.execute(sql);
			if(rs1.next()){
				mainID = Util.null2String(rs1.getString("id"));//获取主表中的id，作为明细表中的mainid
			}			
			//查询明细表
			sql = "select * from " + tableNamedt + " where mainid="+mainID;
			rs.execute(sql);
			while(rs.next()){
					id = Util.null2String(rs.getString("id"));//获取明细表中id
				
					QueryPrice = "<a href=\''/formmode/view/AddFormMode.jsp?modeId=104&formId=-155&type=1&field8336="+id+"\'' target=\''_blank\''>点击询价</a>";
					String sql_1 = "update formtable_main_73_dt1 set QueryPrice='"+QueryPrice+"' where id="+id;
					rs1.execute(sql_1);
					log.writeLog("查询供应商QueryPrice="+QueryPrice);
					log.writeLog("查询供应商sql_1="+sql_1);
				 
			}										
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}
