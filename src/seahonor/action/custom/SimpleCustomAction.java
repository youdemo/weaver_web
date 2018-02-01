package seahonor.action.custom;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.GeneralNowInsert;
import seahonor.util.InsertUtil;
import seahonor.util.SysNoForSelf;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class SimpleCustomAction  implements Action{

	/*
	 *   记录当前客户信息 和 联系人   快速创建
	 */
	public String execute(RequestInfo request) {
		
		BaseBean log = new BaseBean();
		log.writeLog("SimpleCustomAction Start!!");
		
		SysNoForSelf sns = new SysNoForSelf();
		
		String requestid = request.getRequestid();
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		InsertUtil iu = new InsertUtil();
		
		GeneralNowInsert gni = new GeneralNowInsert();
		
		String table_name = "";
		String table_name_1 = "uf_custom";
		String table_name_2 = "uf_Contacts";
		
		String sql = "Select tablename From Workflow_bill Where id=(" 
				+ "Select formid From workflow_base Where id="+request.getWorkflowid()+")";
		rs.executeSql(sql);
		if(rs.next()){
			table_name = Util.null2String(rs.getString("tablename"));
		}
		int customFormID = 52;
		int contactFormID = 56;
		String userid = "";
		if(!"".equals(table_name)){
			String sysNo = sns.getNum("CTS",table_name_1,4);
			sql = "select * from " + table_name + " where requestid="+requestid;
			rs.executeSql(sql);
			if(rs.next()){
				String customID = Util.null2String(rs.getString("customName"));
				if("".equals(customID)){
				
					Map<String, String> mapStr = new HashMap<String, String>();
					mapStr.put("customName", Util.null2String(rs.getString("CustomFill")));
					mapStr.put("customCode", sysNo);
					mapStr.put("Country", Util.null2String(rs.getString("Country")));
					mapStr.put("enterpriseType", Util.null2String(rs.getString("enterpriseType")));
					mapStr.put("industryType", Util.null2String(rs.getString("industryType")));
					mapStr.put("Telphone", Util.null2String(rs.getString("Telphone")));
					mapStr.put("Fax", Util.null2String(rs.getString("Fax")));
					mapStr.put("Address", Util.null2String(rs.getString("Address")));
					mapStr.put("Postcode", Util.null2String(rs.getString("Postcode")));
					mapStr.put("email", Util.null2String(rs.getString("email")));
					mapStr.put("webSite", Util.null2String(rs.getString("webSite")));
					mapStr.put("EnQuickLook", Util.null2String(rs.getString("EnQuickLook")));
					mapStr.put("JpQuickLook", Util.null2String(rs.getString("JpQuickLook")));
					mapStr.put("Remark", Util.null2String(rs.getString("Remark")));
				//	mapStr.put("formmodeid", "1");
					
					// 提供者
					userid = Util.null2String(rs.getString("Provider"));
					mapStr.put("Provider", userid);
					// 默认为未校队状态
					mapStr.put("CutomStatus", "0");
					mapStr.put("Modifier", userid);
					mapStr.put("ModifyTime", "##CONVERT(varchar(20),getdate(),120)");
		//			mapStr.put("UpdateTime", "##CONVERT(varchar(20),getdate(),120)");
					// 版本为0  为未校队状态
					mapStr.put("version","0");
					mapStr.put("modedatacreater", userid);
					mapStr.put("modedatacreatertype", "0");
					mapStr.put("formmodeid", String.valueOf(customFormID));
					
					iu.insert(mapStr, table_name_1);
					
					sql = "select * from uf_custom where customCode='"+sysNo+"'";
					rs1.executeSql(sql);
					if(rs1.next()){
						customID = Util.null2String(rs1.getString("id"));
					}
					
					gni.insertNow("-59", table_name_1, "id", customID);
					
					// 权限表
				//	insertGs(1,customID,"1","22");
					
					insertGs(Integer.parseInt(userid),customFormID,Integer.parseInt(customID));
					
					sql = "update "+ table_name + " set customName="+customID + " where requestid="+requestid;
					rs.executeSql(sql);
				}else{
					
				}
				
				String sysNo1 = sns.getNum("CTS",table_name_2,4);
				Map<String, String> mapStr = new HashMap<String, String>();
				mapStr.put("SysNo", sysNo1);
				mapStr.put("customName", customID);
				mapStr.put("Name", Util.null2String(rs.getString("CusName")));
				mapStr.put("dept",Util.null2String(rs.getString("Cusdept")));
				mapStr.put("Position", Util.null2String(rs.getString("CusPosition")));
				mapStr.put("status", Util.null2String(rs.getString("Cusstatus")));
				mapStr.put("tel", Util.null2String(rs.getString("Custel")));
				mapStr.put("mobile", Util.null2String(rs.getString("Cusmobile")));
				mapStr.put("Fax", Util.null2String(rs.getString("CusFax")));
				mapStr.put("Email", Util.null2String(rs.getString("CusEmail")));
				mapStr.put("Postcode", Util.null2String(rs.getString("CusPostcode")));
				mapStr.put("Address", Util.null2String(rs.getString("CusAddress")));
				mapStr.put("picHead", Util.null2String(rs.getString("CuspicHead")));
				mapStr.put("picEnd", Util.null2String(rs.getString("CuspicEnd")));  
			//	mapStr.put("formmodeid", "5");
				// 默认为未校队状态
				mapStr.put("dealStatus", "0");
				
				mapStr.put("modedatacreater", userid);
				mapStr.put("modedatacreatertype", "0");
				mapStr.put("formmodeid", String.valueOf(contactFormID));
				
				iu.insert(mapStr, table_name_2);
				
				int contactID = 0;
				sql = "select * from uf_Contacts where SysNo='"+sysNo1+"'";
				rs1.executeSql(sql);
				if(rs1.next()){
					contactID = rs1.getInt("id");
				}
				gni.insertNow("-63", table_name_2, "id", String.valueOf(contactID));
				
				// 权限表
			//	insertGs(5,contactID,"29","42");
				
				insertGs(Integer.parseInt(userid),contactFormID,contactID);
			}
			
			
		}
		return SUCCESS;
	}
	
	private boolean insertGs(int creater,int modeid,int m_billid){
		ModeRightInfo ModeRightInfo = new ModeRightInfo();
		ModeRightInfo.editModeDataShare(creater,modeid,m_billid);//新建的时候添加共享
		//--------------给文档赋权------------------------
   //     ModeRightInfo modeRightInfo = new ModeRightInfo();
    //    modeRightInfo.addDocShare(modecreater,modeid,m_billid);
		return true;
	}
	
}
