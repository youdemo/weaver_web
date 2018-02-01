package seahonor.action.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;

public class EditCustom {
	
	public String getID(String xgid,String xgseq){
		RecordSet rs = new RecordSet();
		String billid="";
		String sql="select id from uf_edit_custom where xgid='"+xgid+"' and xgseq='"+xgseq+"'";
		rs.executeSql(sql);
		if(rs.next()){
			billid = rs.getString("id");
		}else{
			insertHistory("-59","uf_custom","id", xgid,"uf_edit_custom",xgseq);
			sql="select id from uf_edit_custom where xgid='"+xgid+"' and xgseq='"+xgseq+"'";
			rs.executeSql(sql);
			if(rs.next()){
				billid = rs.getString("id");
				ModeRightInfo ModeRightInfo = new ModeRightInfo();
				ModeRightInfo.editModeDataShare(1, 185,
						Integer.valueOf(billid));//测试172 正式185
			}
		}
		return billid;
		
		
	}
	
	public boolean insertHistory(String billid,String table_name,String uqField,String uqVal,String tablename2,String xgseq) {
		
		BaseBean log  = new BaseBean();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();

		// 存放 表的字段
		List<String> list = new ArrayList<String>();
 		String sql = "select fieldname from workflow_billfield where billid="+billid+" order by dsporder";
 	//	log.writeLog("insertNow(1) = " + sql);
		rs.executeSql(sql);
		while(rs.next()){
			String tmp_1 = Util.null2String(rs.getString("fieldname"));
			
			// 关联父类排除
			if(!"".equals(tmp_1)&&!"SuperID".equalsIgnoreCase(tmp_1)){
				list.add(tmp_1);
			}
		}
		if(!"".equals(table_name)){
			
			Map<String, String> mapStr = new HashMap<String, String>();
			
			sql = "select * from "+table_name+"  where "+uqField+"='"+uqVal+"'";
		//	log.writeLog("insertNow(2) = " + sql);
			rs.execute(sql);
			if(rs.next()){
				// 循环获取   不为空值的组合成sql
				for(String field : list){
					String tmp_x = Util.null2String(rs.getString(field));
					if(tmp_x.length() > 0)
						mapStr.put(field, tmp_x);
				}
			}
			
			// 最后需要补充关联父id
			if(mapStr.size() > 0){
				mapStr.put("xgid", uqVal);
				// 增加请求的id
				mapStr.put("xgseq", xgseq);
				mapStr.put("modedatacreater", Util.null2String(rs.getString("modedatacreater")));
				mapStr.put("modedatacreatertype", Util.null2String(rs.getString("modedatacreatertype")));
				mapStr.put("formmodeid", "185");//测试172 正式185
				iu.insert(mapStr, tablename2);
			}
		}
		
		return true;
	}

}
