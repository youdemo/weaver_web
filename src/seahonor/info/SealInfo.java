package seahonor.info;

import weaver.conn.RecordSet;
import weaver.general.Util;

public class SealInfo {

	public String getSealName(String ids){
		StringBuffer buff = new StringBuffer();
		RecordSet rs = new RecordSet();
		
		String idArr[] = ids.split(",");
		String flag = "";
		for(int index=0;index<idArr.length;index++){
			String sql = "select Name from uf_badges where id="+idArr[index];
			rs.executeSql(sql);
			if(rs.next()){
				String tmp_name = Util.null2String(rs.getString("name"));
				buff.append(flag);buff.append(tmp_name);flag=",";
			}
			
		}
		
		return buff.toString();
	}
	//��ȡ���ε�֤�����ƣ���32_33
	public String getTreeSealName(String ids){
		StringBuffer buff = new StringBuffer();
		RecordSet rs = new RecordSet();
		
		String idArr[] = ids.split(","); 
		String flag = "";
		for(int index=0;index<idArr.length;index++){
			String	LendSeal = idArr[index].substring(3);//��ȡ32_33��33
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
