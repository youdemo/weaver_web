package seahonor.info;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.general.BaseBean;

public class CustomerInfo {
	public String getCustomerName(String ids){
		StringBuffer buff = new StringBuffer();
		RecordSet rs = new RecordSet();
		BaseBean log = new BaseBean();
	
		//String sql = "select custom from uf_custom where id="+ids;//�����Լ������ϵ�sql
		String sql = "select customName from uf_custom where id="+ids;//�����Լ���֮���ϵ�sql
		rs.executeSql(sql);
		if(rs.next()){
			//String custom_name = Util.null2String(rs.getString("custom"));//�����Լ������ϵ�sql
			String custom_name = Util.null2String(rs.getString("customName"));//�����Լ���֮���ϵ�sql
			log.writeLog("custom_name="+custom_name);
			buff.append(custom_name);
		}	
			return buff.toString();	
	}
}
