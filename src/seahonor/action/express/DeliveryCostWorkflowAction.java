package seahonor.action.express;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
	//海之信寄快递中快递费用action
	public class DeliveryCostWorkflowAction implements Action {

		BaseBean log = new BaseBean();// 定义写入日志的对象

		public String execute(RequestInfo info) {
			log.writeLog("寄快递中快递费用DeliveryCostWorkflowAction——————");

			String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
			String requestid = info.getRequestid();

			RecordSet rs = new RecordSet();
			RecordSet rs1 = new RecordSet();
			RecordSet rs2 = new RecordSet();

			String tableName = "";
			String tableNamedt = "";
			String mainID = "";
			
			String 	CourierCompany = "" ;//快递公司
			String 	DateTime = "" ;//寄件日期
			String 	Sender = "" ;//寄件人
			String 	ReceiverAdd = "" ;//收件地址
			String 	PayMethod = "" ;//支付方式
			String 	Money = "";//快递费用
			String 	BelongsToProject = "" ;//所属项目

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
					//mainID = Util.null2String(rs1.getString("id"));//获取主表中的id，作为明细表中的mainid					
					CourierCompany = Util.null2String(rs1.getString("CourierCompany"));//获取主表中快递公司
					DateTime = Util.null2String(rs1.getString("DateTime"));//获取主表中寄件日期
					Sender = Util.null2String(rs1.getString("Sender"));//获取主表中寄件人
					ReceiverAdd = Util.null2String(rs1.getString("ReceiverAdd"));//获取主表中收件地址
					PayMethod = Util.null2String(rs1.getString("PayMethod"));//获取主表中支付方式
					Money = Util.null2String(rs1.getString("Money"));//获取主表中快递费用
					BelongsToProject = Util.null2String(rs1.getString("BelongsToProject"));//获取主表中所属项目
					
					Map<String, String> mapStr = new HashMap<String, String>();
					
					mapStr.put("CourierCompany", CourierCompany);
					mapStr.put("modedatacreater", "1");//1代表是系统管理员创建
					mapStr.put("Classify", "1");//这个记得要加入，1代表是客户证章。
					mapStr.put("DateTime", DateTime);
					
					mapStr.put("Sender", Sender);
					mapStr.put("ReceiverAdd", ReceiverAdd);
					mapStr.put("PayMethod", PayMethod);
					mapStr.put("Money", Money);
					mapStr.put("BelongsToProject", BelongsToProject);
					/*mapStr.put("waySys", waySys);
					mapStr.put("wayEmail", wayEmail);
					mapStr.put("waySms", waySms);
					mapStr.put("reName", reName);
					mapStr.put("other", other);
					mapStr.put("notifier", notifier);
					mapStr.put("is_active", is_active);*/
					
					String tableNamex = "uf_badges";
					InsertUtil it = new InsertUtil();
					it.insert(mapStr, tableNamex);
					// 拿取权限表IDs
					String sql_Datashare = "select * from uf_badges where id = (select  MAX(id) from uf_badges)";
					rs1.execute(sql_Datashare);
					log.writeLog("客户委托ID" + sql_Datashare);
					if (rs1.next()) {
						String ID = Util.null2String(rs1.getString("id"));
						sql_1 = "exec CustomerEntrust_right " + ID;
						rs2.execute(sql_1);
						log.writeLog("客户委托ID2" + sql_1);
					}
				}													
			} else {
				return "-1";
			}
			return SUCCESS;
		}

	}
