package seahonor.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

public class GeneralNowInsert {
	
	public boolean insertNow(String billid,String table_name,String uqField,String uqVal) {
		
		BaseBean log  = new BaseBean();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();

		// ��� ����ֶ�
		List<String> list = new ArrayList<String>();
 		String sql = "select fieldname from workflow_billfield where billid="+billid+" order by dsporder";
 	//	log.writeLog("insertNow(1) = " + sql);
		rs.executeSql(sql);
		while(rs.next()){
			String tmp_1 = Util.null2String(rs.getString("fieldname"));
			
			// ���������ų�
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
				// ѭ����ȡ   ��Ϊ��ֵ����ϳ�sql
				for(String field : list){
					String tmp_x = Util.null2String(rs.getString(field));
					if(tmp_x.length() > 0)
						mapStr.put(field, tmp_x);
				}
			}
			
			// �����Ҫ���������id
			if(mapStr.size() > 0){
				mapStr.put("SuperID", Util.null2String(rs.getString("ID")));
				// ���������id
				mapStr.put("requestid", Util.null2String(rs.getString("requestid")));
				
				iu.insert(mapStr, table_name);
			}
		}
		
		return true;
	}
}
