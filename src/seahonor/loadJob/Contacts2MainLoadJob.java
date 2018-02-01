package seahonor.loadJob;

import java.util.ArrayList;
import java.util.List;

import seahonor.util.GeneralNowInsert;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class Contacts2MainLoadJob implements Action {
	BaseBean log = new BaseBean();
	@Override
	public String execute(RequestInfo info) {
		RecordSet rs = new RecordSet();
		log.writeLog("Contacts2MainLoadJob start!! ");
		
		GeneralNowInsert gni = new GeneralNowInsert();
		String mid = info.getRequestid();
		List<String> list = getList();


		RecordSet rs1 = new RecordSet();
		String sql = "select * from  uf_ContactsInfo where id="+mid;
		rs.executeSql(sql);
		while(rs.next()){
			String id = Util.null2String(rs.getString("id"));
			String mainCustom = Util.null2String(rs.getString("mainInfo"));
			String ToCustom = Util.null2String(rs.getString("otherInfo"));

			
			sql = "update uf_ContactsInfo set status=1,operDate=CONVERT(varchar(10), GETDATE(), 23),operTime=CONVERT(varchar(5), GETDATE(), 8) where id="+id;
			rs1.executeSql(sql);
			
			StringBuffer buff = new StringBuffer();
			StringBuffer buff1 = new StringBuffer();
			
			buff.append("update uf_Contacts set ModifyTime=CONVERT(varchar(100), GETDATE(), 21)");
			buff1.append(" from  (select requestId");
			
			// 客户信息修改
			for(String tmp : list){
				String tmp3 = tmp + "_" + 3;
				String tmp3_val = Util.null2String(rs.getString(tmp3));
				if("1".equals(tmp3_val)){
					buff.append(",");buff.append(tmp);buff.append("=x.");buff.append(tmp);
					
					buff1.append(",");buff1.append(tmp);
				}
			}
			
		//	buff.append(" where id=");buff.append(mainCustom);
			
			buff1.append(" from uf_Contacts u  where u.id=");buff1.append(ToCustom);
			buff1.append(") x where id=");buff1.append(mainCustom);
			
			sql = buff.toString() + buff1.toString();
			log.writeLog(" sql= " + sql);
			rs1.executeSql(sql);
			
			// 主信息 保存被修改信息
			gni.insertNow("-63", "uf_Contacts", "id", mainCustom);
			
			// 被合并信息 信息状态改为被合并
			sql = "update uf_Contacts set dealstatus=3 where id=" + ToCustom;
			rs1.executeSql(sql);

		}
		
		return SUCCESS;
	}
	
	// 客户被选中字段
	public List<String> getList(){
		List<String> list = new ArrayList<String>();
		list.add("customName");list.add("name");list.add("dept");
		list.add("Position");list.add("status");list.add("tel");
		list.add("mobile");list.add("fax");list.add("Postcode");
		list.add("dealStatus");list.add("email");list.add("address");
		list.add("picHead");list.add("picEnd");list.add("SysNo");
		
		return list;
	}
	
	//  拼接字段
	public String getStr(List<String> list,int flag){
		StringBuffer buff = new StringBuffer();
		
		for(int index=0;index<list.size();index++){
			String tmp = list.get(index)+flag;
			buff.append(",");buff.append(tmp);
		}
		
		return buff.toString();
	}

}
