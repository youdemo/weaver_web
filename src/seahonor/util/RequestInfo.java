package seahonor.util;

import weaver.conn.RecordSet;
import weaver.general.Util;

public class RequestInfo {

	public String getRequestName(String requestid){
		if(requestid == null) return "";
		String name = "";
		String sql = "select requestname from workflow_requestbase where requestid="+requestid;
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		if(rs.next()){
			name = Util.null2String(rs.getString("requestname"));
		}
		return name;
	}
}
