package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信部门日常采购消耗品action这个是有关主从表的，就是名细表的！
public class PurchaseWorkflowAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("部门日常采购消耗品PurchaseWorkflowAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		RecordSet rs3 = new RecordSet();

		String tableName = "";
		String tableNamedt = "";
		String mainID = "";
		int Numbers = 0;// 明细表 中的数量
		String Name ="";//明细表中的名称
		String Month = "";//主表中的月份
		String Year = "";//主表中的年份
		String id_1 = "";
		int	count_cc =0;

		String sql_1 = "";
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
				Month = Util.null2String(rs1.getString("Month"));//获取主表中的月份
				Year = Util.null2String(rs1.getString("Year"));//获取主表中的年份
			}			
			//查询明细表
			sql = "select * from " + tableNamedt + " where mainid="+mainID;
			rs.execute(sql);
			while(rs.next()){
			//	String dtID = Util.null2String(rs.getString("id"));//获取明细表中id
				Name = Util.null2String(rs.getString("Name"));//获取明细表中的名称
				Numbers = rs.getInt("Numbers");//获取明细表中的数量
						
				String sql_2 = "select id from uf_Purchase where Month = '"+Month+"' and Year = '"+Year+"'";	
				log.writeLog("sql_2=" + sql_2);
				log.writeLog("执行结果=" + rs3.execute(sql_2));
//				rs3.execute(sql_2);
				if (rs3.next()) {
					log.writeLog("in");
					id_1 = Util.null2String(rs3.getString("id"));//
					log.writeLog("id_1=" + id_1);
				}
				log.writeLog("名称ID" + id_1);
				
				if ("".equals(id_1)) {
					String sql_3 = "insert into uf_Purchase(Year,Month) values('" + Year +"','" + Month + "')";
					rs1.execute(sql_3);
					
					sql_3 = "select id from uf_Purchase order by id desc";
					rs1.execute(sql_3);
					if (rs1.next()) {
						id_1 = Util.null2String(rs1.getString("id"));//表单建模中的名称
					}					
					// 拿取权限表IDs
					String sql_Datashare = "select * from uf_Purchase where id = (select  MAX(id) from uf_Purchase)";
					rs1.execute(sql_Datashare);
					log.writeLog("采购ID" + sql_Datashare);
					if (rs1.next()) {
						String ID = Util.null2String(rs1.getString("id"));
						sql_1 = "exec Purchase_right " + ID;
						rs2.execute(sql_1);
						log.writeLog("采购ID2" + sql_1);
					}
				}				
				String sql_3 = "select count(id) count_cc from uf_Purchase_dt1 where mainid = '"+id_1+"' and Name = '"+Name+"'";	
				rs1.execute(sql_3);
				if (rs1.next()) {
					//id_1 = Util.null2String(rs1.getString("id"));//表单建模中的名称
						count_cc = rs1.getInt("count_cc");//
				}
				
				if (count_cc>0) {
					sql_1 = "update  uf_Purchase_dt1 set  Numbers = isnull(Numbers,0) +"
							+ Numbers + " where Name='" + Name + "'and mainid = '"+id_1+"'";
					rs1.execute(sql_1);
					
				}else{
					sql_1 = "insert into uf_Purchase_dt1(mainid,Name,Numbers) values('"+id_1+"','"
							+ Name + "','" + Numbers + "')";
					rs1.execute(sql_1);
					
				}
			}										
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}