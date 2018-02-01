package seahonor.action.purchsse;

import java.util.Date;

import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信部门日常采购消耗品action这个是有关主从表的，就是名细表的！
public class PurchaseWorkflowOtherAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("部门日常采购消耗品PurchaseWorkflowOtherAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		
		String tableName = "";
		String tableNamedt = "";
		String mainID = "";
		int Numbers = 0;// 明细表 中的数量
		String Name ="";//明细表中的名称
		String Month = "";//主表中的月份
		String Year = "";//主表中的年份
		String id_1 = "";
		int	count_cc =0;
		int ID = 0;//表单建模中的id
		
		ModeRightInfo ModeRightInfo = new ModeRightInfo();
		String sql_1 = "";
		String sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID+ ")";
		rs.execute(sql);
		if(rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		if(!"".equals(tableName)) {
			tableNamedt = tableName + "_dt1";//这个方法是为了有多个明细做的准备
			// 查询主表
			sql = "select * from "+tableName + " where requestid="+requestid;
			rs.execute(sql);
			if(rs.next()){
				mainID = Util.null2String(rs.getString("id"));//获取主表中的id，作为明细表中的mainid
				Month = Util.null2String(rs.getString("Month"));//获取主表中的月份
				Year = Util.null2String(rs.getString("Year"));//获取主表中的年份
			}			
			//查询明细表
			sql = "select * from " + tableNamedt + " where mainid="+mainID;
			rs.execute(sql);
			while(rs.next()){
				ID = 0;
				id_1 = "";
				Name = Util.null2String(rs.getString("Name"));//获取明细表中的名称
				Numbers = rs.getInt("Numbers");//获取明细表中的数量
				
				if ("".equals(id_1)) {
					
					long times = new Date().getTime();
					String sysNo = String.valueOf(times);
					// sysNo
					
					String sql_3 = "insert into uf_Purchase(formmodeid,status,sysNo,type) values(94,0,'"+sysNo+"',1)";
					rs1.execute(sql_3);
					
					// 拿取权限表IDs  formmodeid=94控制只有这表单建模的值，其他引用这个表单的表单建模的值过滤掉了
					String sql_Datashare = "select max(id) as xid from uf_Purchase where formmodeid=94 and status =0 and type=1";
					rs1.execute(sql_Datashare);
					if (rs1.next()) {
						ID = rs1.getInt("xid");
						ModeRightInfo.editModeDataShare(1,94,ID);
					}
					
					id_1 = String.valueOf(ID);//把int类型转为String类型
				}				
				String sql_3 = "select count(id) count_cc from uf_Purchase_dt1 where mainid = "+id_1+" and Name = '"+Name+"'";	
				rs1.execute(sql_3);
				if (rs1.next()) {
						count_cc = rs1.getInt("count_cc");//
				}
				if (count_cc>0) {
					sql_1 = "update  uf_Purchase_dt1 set  Numbers = isnull(Numbers,0) +"
							+ Numbers + " where Name='" + Name + "' and mainid = "+id_1+"";
					rs1.execute(sql_1);
					
				}else{
					sql_1 = "insert into uf_Purchase_dt1(mainid,Name,Numbers) values("+id_1+",'"
							+ Name + "','" + Numbers + "')";
					rs1.execute(sql_1);
				}
				
				String sql_4 = "select id from uf_Purchase_dt1 where mainid = "+id_1+"";	
				rs1.execute(sql_4);
				while(rs1.next()) {
					String tmp_id = Util.null2String(rs1.getString("id"));//表单建模中的明细id 字段
					String QueryPrice = "<a href=\''/seahonor/purchase/supplierPrice.jsp?deRemark="+tmp_id+"\''>点击进入询价</a>";
					String sql_5 = "update uf_Purchase_dt1 set QueryPrice='"+QueryPrice+"',val='"+tmp_id+"' where id='"+tmp_id+"' and mainid = '"+id_1+"'";
					rs2.execute(sql_5);
				}
			}										
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}