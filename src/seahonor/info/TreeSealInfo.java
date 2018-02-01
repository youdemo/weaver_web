package seahonor.info;

import weaver.conn.RecordSet;
import weaver.general.Util;

public class TreeSealInfo {
	public String getTreeSealName(String ids){
		StringBuffer buff = new StringBuffer();
		RecordSet rs = new RecordSet();
		
		String idArr[] = ids.split(","); 
		String flag = "";
		for(int index=0;index<idArr.length;index++){
			String	LendSeal = idArr[index].substring(3);
			String sql = "select Name from uf_badges where id="+LendSeal;
			rs.executeSql(sql);
			if(rs.next()){
				String tmp_name = Util.null2String(rs.getString("name"));
				buff.append(flag);buff.append(tmp_name);flag=",";
			}
		}
		
		return buff.toString() ;
	}
}
