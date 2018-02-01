package seahonor.action.expenses;

import java.text.SimpleDateFormat;
import java.util.Date;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateBorrowDate  implements Action{

	@Override
	public String execute(RequestInfo info) {
		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();
		RecordSet rs = new RecordSet();
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormate = new SimpleDateFormat("HH:mm:ss");
		String nowDate = dateFormate.format(new Date());
		String nowTime = timeFormate.format(new Date());
		String tableName = "";
		String mainid = "";
		String jkbz="";
		String jkid="";
		String tbyc="";
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
			jkbz = Util.null2String(rs.getString("jkbz"));
		}
		if("0".equals(jkbz)){
			sql="select jkid,tbyc from "+tableName+"_dt2 where (tbyc <>'' and tbyc is not null) and mainid="+mainid;		
			rs.executeSql(sql);
			while(rs.next()){
				jkid = Util.null2String(rs.getString("jkid"));
				tbyc = Util.null2String(rs.getString("tbyc"));
				updateDate(jkid, tbyc);
			}
			sql="select jkid,tbyc from "+tableName+"_dt3 where (tbyc <>'' and tbyc is not null) and mainid="+mainid;		
			rs.executeSql(sql);
			while(rs.next()){
				jkid = Util.null2String(rs.getString("jkid"));
				tbyc = Util.null2String(rs.getString("tbyc"));
				updateDate(jkid, tbyc);
			}
		}else if("1".equals(jkbz)){
			sql="select jkid,tbyc from "+tableName+"_dt2 where (tbyc <>'' and tbyc is not null) and mainid="+mainid;		
			rs.executeSql(sql);
			while(rs.next()){
				jkid = Util.null2String(rs.getString("jkid"));
				tbyc = Util.null2String(rs.getString("tbyc"));
				updateDate(jkid, tbyc);
			}
		}else if("2".equals(jkbz)){
			sql="select jkid,tbyc from "+tableName+"_dt3 where (tbyc <>'' and tbyc is not null) and mainid="+mainid;		
			rs.executeSql(sql);
			while(rs.next()){
				jkid = Util.null2String(rs.getString("jkid"));
				tbyc = Util.null2String(rs.getString("tbyc"));
				updateDate(jkid, tbyc);
			}
		}
		return SUCCESS;
	}
	
	public void updateDate(String jkid,String tbyc){
		RecordSet rs = new RecordSet();
		String sql="update uf_personal_borrow set jkdqr='"+tbyc+"' where id="+jkid;
		rs.executeSql(sql);
		sql = "update uf_personal_borrow  set yqts='' ,"
				+ "sfyq='1' where id in(select id from uf_personal_borrow where ISNULL(jkye,0)>0 and CONVERT(varchar(100), GETDATE(), 23)<=jkdqr and id='"+jkid+"')";
		rs.executeSql(sql);
		sql = "update uf_personal_borrow  set yqts=datediff(day,jkdqr,CONVERT(varchar(100), GETDATE(), 23)) ,"
				+ "sfyq='0' where id in(select id from uf_personal_borrow where ISNULL(jkye,0)>0 and CONVERT(varchar(100), GETDATE(), 23)>jkdqr and id='"+jkid+"')";
		rs.executeSql(sql);
	}
}
