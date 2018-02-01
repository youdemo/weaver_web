package seahonor.loadJob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import seahonor.util.GeneralNowInsert;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class Custom2MainLoadJob implements Action {
	BaseBean log = new BaseBean();
	@Override
	public String execute(RequestInfo info) {
		RecordSet rs = new RecordSet();
		log.writeLog("Custom2MainLoadJob start!! ");
		
		GeneralNowInsert gni = new GeneralNowInsert();
		String mid = info.getRequestid();
		List<String> list = getList();
		// id更新
		Map<String,String> mapStr = new HashMap<String,String>();
		// tableName   fieldName
		//  联系人
		mapStr.put("uf_Contacts", "customName");
		//  客户动态表
		mapStr.put("uf_custom_dynamic", "custom");
		//  客户项目表
	//	mapStr.put("uf_project ", "custom"); 
		//  客户资质
		mapStr.put("uf_CustomCapacity ", "custom"); 

		RecordSet rs1 = new RecordSet();
		String sql = "select * from  uf_custom2Main where id="+mid;
		rs.executeSql(sql);
		while(rs.next()){
			String id = Util.null2String(rs.getString("id"));
			String mainCustom = Util.null2String(rs.getString("MainCustom"));
			String ToCustom = Util.null2String(rs.getString("ToCustom"));
			
			Iterator<String> it =	mapStr.keySet().iterator();
			
			while(it.hasNext()){
				String tableName = it.next();
				String fieldName = mapStr.get(tableName);
				sql = "update " + tableName + " set " +fieldName + "=" 
						+ mainCustom +" where "+fieldName + " = " + ToCustom;
				rs1.executeSql(sql);
			}
			sql = "update uf_custom2Main set status=1,operDate=CONVERT(varchar(10), GETDATE(), 23),operTime=CONVERT(varchar(5), GETDATE(), 8) where id="+id;
			rs1.executeSql(sql);
			
			StringBuffer buff = new StringBuffer();
			StringBuffer buff1 = new StringBuffer();
			
			buff.append("update uf_custom set ModifyTime=CONVERT(varchar(100), GETDATE(), 21)");
			buff1.append(" from  (select requestId");
			
			// 客户信息修改
			for(String tmp : list){
				String tmp3 = tmp + 3;
				String tmp3_val = Util.null2String(rs.getString(tmp3));
				if("1".equals(tmp3_val)){
					buff.append(",");buff.append(tmp);buff.append("=x.");buff.append(tmp);
					
					buff1.append(",");buff1.append(tmp);
				}
			}
			
		//	buff.append(" where id=");buff.append(mainCustom);
			
			buff1.append(" from uf_custom u  where u.id=");buff1.append(ToCustom);
			buff1.append(") x where id=");buff1.append(mainCustom);
			
			sql = buff.toString() + buff1.toString();
			
			log.writeLog(" sql = " + sql);
			rs1.executeSql(sql);
			
			// 主信息 保存被修改信息
			gni.insertNow("-59", "uf_custom", "id", mainCustom);
			
			// 被合并信息 信息状态改为被合并
			sql = "update uf_custom set CutomStatus=3 where id=" + ToCustom;
			rs1.executeSql(sql);

		}
		
		return SUCCESS;
	}
	
	// 客户被选中字段
	public List<String> getList(){
		List<String> list = new ArrayList<String>();
		list.add("customName");list.add("customCode");list.add("Country");
		list.add("enterpriseType");list.add("industryType");list.add("Telphone");
		list.add("Fax");list.add("Address");list.add("Postcode");
		list.add("Email");list.add("Website");list.add("EnQuickLook");
		list.add("JpQuickLook");list.add("Remark");list.add("Provider");
		list.add("CutomStatus");list.add("CustomGroup");
		
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
