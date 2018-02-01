package seahonor.action.expenses;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateRMBReimbDetail implements Action{

	@Override
	public String execute(RequestInfo info) {
		String modeId = info.getWorkflowid();
		String billId = info.getRequestid();
		RecordSet rs = new RecordSet();
		String tableName="";
		String flowTableName="";
		String rqid = "";//流程id
		String jtxmmc="";//集团项目名称
		String gsxmmc = "";//公司项目名称
		String zqmc="";//周期名称
		String zqfzr="";//周期负责人
		String mxhjje="";//明细合计金额
		String fycdf="";//费用承担方
		String rqMainid = "";
		String lcmxid = "";
		String sql="select b.tablename from modeinfo a,workflow_bill b where a.formid=b.id and a.id="+modeId;
		rs.executeSql(sql);
		if(rs.next()){
			tableName = Util.null2String(rs.getString("tablename"));
		}
		sql="select * from "+tableName+" where id="+billId;
		rs.executeSql(sql);
		if(rs.next()){
			rqid = Util.null2String(rs.getString("rqid"));
			jtxmmc = Util.null2String(rs.getString("jtxmmc"));
			gsxmmc = Util.null2String(rs.getString("gsxmmc"));
			zqmc = Util.null2String(rs.getString("zqmc"));
			zqfzr = Util.null2String(rs.getString("zqfzr"));
			mxhjje = Util.null2String(rs.getString("mxhjje"));
			fycdf = Util.null2String(rs.getString("fycdf"));
			lcmxid= Util.null2String(rs.getString("lcmxid"));
		}
		sql="select c.tablename from workflow_requestbase a,workflow_base b,workflow_bill c where a.workflowid=b.id and b.formid=c.id and a.requestid="+rqid;
		rs.executeSql(sql);
		if(rs.next()){
			flowTableName = Util.null2String(rs.getString("tablename"));
		}
		sql="select * from "+flowTableName+" where requestid="+rqid;
		rs.executeSql(sql);
		if(rs.next()){
			rqMainid = Util.null2String(rs.getString("id"));
		}
		if("0".equals(fycdf)){
			flowTableName = flowTableName+"_dt1";
		}else{
			flowTableName = flowTableName+"_dt2";
		}
		InsertUtil iu = new InsertUtil();
		Map<String, String> mapStr = new HashMap<String, String>();
		mapStr.put("gsxmmc", gsxmmc);// 
		mapStr.put("zqmc", zqmc);// 
		mapStr.put("zqfzr", zqfzr);// 
		mapStr.put("bxje", mxhjje);// 
		mapStr.put("jtxm", jtxmmc);// 
		iu.updateGen(mapStr, flowTableName, "id", lcmxid);
		
		return SUCCESS;
	}

}
