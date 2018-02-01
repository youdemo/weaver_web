package seahonor.action.expenses;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateMoneyForGZHKDT1 implements Action{

	@Override
	public String execute(RequestInfo info) {
		BaseBean log = new BaseBean();
		String workflowID = info.getWorkflowid();
		String requestid = info.getRequestid();
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		String tableName = "";
		String mainid = "";
		String sql_dt = "";
		String id_dt1="";
		String gzkkje = "";
		String sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID
				+ ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		
		rs.executeSql(sql);
		if (rs.next()) {
			mainid = Util.null2String(rs.getString("id"));
		}
		sql="select id,(select isnull(sum(cast(REPLACE(isnull(kcgzje,0),',','') as decimal(10,2) )),0) from "+tableName+"_dt2 where mainid="+mainid+" and jkrxm=ryid)+(select isnull(sum(cast(REPLACE(isnull(kcgzje,0),',','') as decimal(10,2) )),0) from "+tableName+"_dt3 where mainid="+mainid+" and jkrxm=ryid) as gzkkje from "+tableName+"_dt1 where mainid="+mainid;
		log.writeLog("gzhksql"+sql);
		rs.executeSql(sql);
		while(rs.next()){
			id_dt1=Util.null2String(rs.getString("id"));
			gzkkje=Util.null2String(rs.getString("gzkkje")); 
			sql_dt="update "+tableName+"_dt1 set gzkkje="+gzkkje+",rskcje="+gzkkje+" where id="+id_dt1; 
			log.writeLog("gzhksql_dt"+sql_dt);
			rs_dt.executeSql(sql_dt);
		}
		sql="delete from "+tableName+"_dt1 where mainid="+mainid+" and cast(REPLACE(isnull(rskcje,0),',','') as decimal(10,2) )=0";
		rs.executeSql(sql);
		return SUCCESS;
	}

}
