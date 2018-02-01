package seahonor.action.supplier;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class SupplierAssessDtAction implements Action{

	public String execute(RequestInfo request) {
		String requestid = request.getRequestid();
		
		// 获取添加信息
		String sql = "select * from uf_empAppraise where id="+requestid;
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.next()){
			InsertUtil iu = new InsertUtil();
			
			String rid = Util.null2String(rs.getString("rid"));
			String starLevel = Util.null2String(rs.getString("starLevel"));
			String assEmp = Util.null2String(rs.getString("assEmp"));
			String remark = Util.null2String(rs.getString("remark"));
			
			Map<String,String> mapStr = new HashMap<String,String>();
			mapStr.put("mainid", rid);
			mapStr.put("starLevel", starLevel);
			mapStr.put("creater", assEmp);
			mapStr.put("assessment", remark);
			mapStr.put("createrTime", "##CONVERT(varchar(30),getdate(),21)");
			
			iu.insert(mapStr, "uf_TradeRecord_dt1");
		}
		
		return SUCCESS;
	}
}
