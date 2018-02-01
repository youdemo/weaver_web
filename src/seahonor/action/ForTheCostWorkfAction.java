package seahonor.action;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信寄快递待结算action
public class ForTheCostWorkfAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("寄快递待结算ForTheCostWorkfAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();


		String tableName = "";
		String tableNamedt = "";
		String mainID = "";

		String BelongsToProject = "";//所属项目
		String 	ExpenseParty = "";//费用承担方
		//String 	LetterType = "";//信件类型
		String 	Category = "";//类别
		String 	Recipient = "" ;//收件人
		String 	ReceiverOrg = "" ;//收件单位
		String 	ReceiverTel = "" ;//收件电话
		String 	ReceiverAdd = "" ;//收件地址
		String 	Content = "" ;//收件内容
		String 	ExpressInformation = "";//快递信息补充
		String Customer = "";//客户
		String 	Money = "";//快递费用
		String 	Signed = "";//填写人
		String 	Sender = "";//寄件人
		String 	SenderDepartment = "" ;//寄件部门
		String 	DateTime = "" ;//到件日期
		String 	Budget = "" ;//预算
		String 	ExpressCompany = "" ;//快递公司
		String 	PayMethod = "" ;//支付方式
		

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
			sql = "select * from "+tableName + " where requestid="+requestid;
			rs1.execute(sql);
			if(rs1.next()){
				BelongsToProject = Util.null2String(rs1.getString("BelongsToProject"));//所属项目
				ExpenseParty = Util.null2String(rs1.getString("ExpenseParty"));//费用承担方
				//LetterType = Util.null2String(rs1.getString("LetterType"));//信件类型
				Category = Util.null2String(rs1.getString("Category"));//类别
				Recipient = Util.null2String(rs1.getString("Recipient"));//收件人
				
				ReceiverOrg = Util.null2String(rs1.getString("ReceiverOrg"));//收件单位
				ReceiverTel = Util.null2String(rs1.getString("ReceiverTel"));//收件电话
				ReceiverAdd = Util.null2String(rs1.getString("ReceiverAdd"));//收件地址
				Content = Util.null2String(rs1.getString("Content"));//收件内容
				ExpressInformation = Util.null2String(rs1.getString("ExpressInformation"));//快递信息补充
				
				Customer = Util.null2String(rs1.getString("Customer"));//客户
				Money = Util.null2String(rs1.getString("Money"));//快递费用
				Signed = Util.null2String(rs1.getString("Signed"));//填写人
				Sender = Util.null2String(rs1.getString("Sender"));//寄件人
				SenderDepartment = Util.null2String(rs1.getString("SenderDepartment"));//寄件部门
				
				DateTime = Util.null2String(rs1.getString("DateTime"));//到件日期
				Budget = Util.null2String(rs1.getString("Budget"));//预算
				ExpressCompany = Util.null2String(rs1.getString("ExpressCompany"));//快递公司
				PayMethod = Util.null2String(rs1.getString("PayMethod"));//支付方式
				
				Map<String, String> mapStr = new HashMap<String, String>();
				
				mapStr.put("BelongsToProject", BelongsToProject);
				mapStr.put("ExpenseParty", ExpenseParty);//1代表是系统管理员创建
				//mapStr.put("LetterType", "LetterType");//这个记得要加入，1代表是客户证章。
				mapStr.put("Category", Category);
				mapStr.put("Recipient", Recipient);
				
				mapStr.put("ReceiverOrg", ReceiverOrg);
				mapStr.put("ReceiverTel", ReceiverTel);
				mapStr.put("ReceiverAdd", ReceiverAdd);
				mapStr.put("Content", Content);
				mapStr.put("ExpressInformation", ExpressInformation);
				
				mapStr.put("Customer", Customer);
				mapStr.put("Money", Money);
				mapStr.put("Signed", Signed);
				mapStr.put("Sender", Sender);
				mapStr.put("SenderDepartment", SenderDepartment);
				
				mapStr.put("DateTime", DateTime);
				mapStr.put("Budget", Budget);
				mapStr.put("ExpressCompany", ExpressCompany);
				mapStr.put("PayMethod", PayMethod);
				mapStr.put("State", "0");
				
				String tableNamex = "formtable_main_39";
				InsertUtil it = new InsertUtil();
				it.insert(mapStr, tableNamex);
			}											
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}

