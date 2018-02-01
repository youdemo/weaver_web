package seahonor.action.badge;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//海之信证章入库申请action这个是有关主从表的，就是名细表的！
public class CustomerEntrustWorkflowAction implements Action {

	BaseBean log = new BaseBean();// 定义写入日志的对象

	public String execute(RequestInfo info) {
		log.writeLog("证章入库申请CustomerEntrustWorkflowAction——————");

		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();


		String tableName = "";
		String tableNamedt = "";
		String mainID = "";

		//int Application = 0;//客户申请人（主表）	
		String Application = "";//客户申请人（主表）
		String 	Customer = "";//所属客户（主表）
		String CustomerBasic = "";//客户委托依据
		String attachment = "";//上传附件
		String ApplicationDate = "";//上传附件
		
		String 	Name = "" ;//证章名称（明细）
		String 	StartDate = "" ;//入库日期（明细）
		String 	EndDate = "" ;//有效截止日期（明细）
		String 	Borrowed = "" ;//借用（明细）
		String 	ChooseClassification = "" ;//选择分类（明细）
		String 	Remark = "";//备注（明细）
		String 	Numbers = "";//数量（明细）
		String 	unit = "";//单位（明细）
		String zp="";
		String zxwd = "";
		String fjsc="";
		

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
	
				//Month = Util.null2String(rs1.getString("Month"));//获取主表中的月份
				//Year = Util.null2String(rs1.getString("Year"));//获取主表中的年份
				
				//Application = rs1.getInt("Application");//获取主表中申请人
				Application = Util.null2String(rs1.getString("Application"));//获取主表中申请人
				Customer = Util.null2String(rs1.getString("Customer"));//获取主表中的客户
				CustomerBasic = Util.null2String(rs1.getString("CustomerBasic"));//客户委托依据
				attachment = Util.null2String(rs1.getString("AttachmentUpload"));//上传附件
				ApplicationDate = Util.null2String(rs1.getString("ApplicationDate"));//申请日期
				
				log.writeLog("申请日期ApplicationDate=" + ApplicationDate);
				log.writeLog("上传附件attachment=" + attachment);
				
			}			
			//查询明细表
			sql = "select * from " + tableNamedt + " where mainid="+mainID;
			rs.execute(sql);
			while(rs.next()){
			//	String dtID = Util.null2String(rs.getString("id"));//获取明细表中id
				//Name = Util.null2String(rs.getString("Name"));//获取明细表中的名称
				//Numbers = rs.getInt("Numbers");//获取明细表中的数量
				
				Name = Util.null2String(rs.getString("Name"));//获取明细表中的名称				
				StartDate = Util.null2String(rs.getString("StartDate"));//获取有效开始日期（明细）
				EndDate = Util.null2String(rs.getString("EndDate"));//获取有效结束日期（明细）
				Borrowed = Util.null2String(rs.getString("Borrowed"));//获取借用（明细）
				ChooseClassification = Util.null2String(rs.getString("ChooseClassification"));//获取选择分类（明细）
				Remark = Util.null2String(rs.getString("Remark"));//备注（明细）
				Numbers = Util.null2String(rs.getString("Numbers"));//数量（明细）
				unit = Util.null2String(rs.getString("unit"));//单位（明细）
				zp = Util.null2String(rs.getString("zp"));
				zxwd = Util.null2String(rs.getString("zxwd"));
				fjsc = Util.null2String(rs.getString("shcj"));
				sql = "select type from uf_BadgesClassify where id="+ChooseClassification;
				String type = "";
				rs1.executeSql(sql);
				while(rs1.next()){
					type = Util.null2String(rs1.getString("type"));
				}
				
				Map<String, String> mapStr = new HashMap<String, String>();
				
				mapStr.put("creater",Application);
				mapStr.put("modedatacreater", "1");//1代表是系统管理员创建
				mapStr.put("formmodeid", "69");
				mapStr.put("Classify", "1");//这个记得要加入，1代表是客户证章。0,代表内部证章
				mapStr.put("Customer", Customer);
				mapStr.put("State", "1");
				mapStr.put("CustomerBasic", CustomerBasic);//证章委托依据
				//mapStr.put("Attachment", attachment);//上传附件
				mapStr.put("CreateDate", ApplicationDate);//上传附件
				mapStr.put("type", type);
				
				mapStr.put("Name", Name);
				mapStr.put("BadgeType", ChooseClassification);
				mapStr.put("StartDate", StartDate);
				mapStr.put("ValidTerm", EndDate);
				//mapStr.put("Borrow", Borrowed);
				mapStr.put("Remark", Remark);
				mapStr.put("Numbers", Numbers);
				mapStr.put("unit", unit);
				mapStr.put("zp", zp);
				mapStr.put("zxwd", zxwd);
				String att="";
				if(!"".equals(attachment)){
					att=attachment;
					if(!"".equals(fjsc)){
						att=att+","+fjsc;
					}
				}else{
					att=fjsc;
				}
				mapStr.put("Attachment", att);
				String tableNamex = "uf_badges";
				InsertUtil it = new InsertUtil();
				it.insert(mapStr, tableNamex);
				// 拿取权限表IDs
				
				
				String sql_Datashare = "select * from uf_badges where id = (select  MAX(id) from uf_badges)";
				rs1.execute(sql_Datashare);
				log.writeLog("客户委托sql" + sql_Datashare);
				if (rs1.next()) {
					int ID =rs1.getInt("id");
					log.writeLog("客户委托1111ID" + ID);
					ModeRightInfo ModeRightInfo = new ModeRightInfo();
					ModeRightInfo.editModeDataShare(1,69,ID);
					log.writeLog("客户委托2222ID" + ID);
				}
							
			}										
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}
