package seahonor.action.expenses;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.schedule.BaseCronJob;

/**
 * 更新借款逾期时间 
 * @author tangjianyong 
 * version 1.0 2017-10-27
 */
public class DoDelayBorrowAction extends BaseCronJob {
	
	public void execute() {  
		 BaseBean log = new BaseBean();
		 log.writeLog("开始定时计算借款逾期时间");
		 dealDelayBorrowData();
		 log.writeLog("定时计算借款逾期时间结束");
	 }
	
	/**
	 *更新借款逾期时间 
	 */
	public void dealDelayBorrowData() {
		RecordSet rs = new RecordSet();
		String sql = "";
		sql = "update uf_personal_borrow  set yqts=datediff(day,jkdqr,CONVERT(varchar(100), GETDATE(), 23)) ,"
				+ "sfyq='0' where id in(select id from uf_personal_borrow where ISNULL(jkye,0)>0 and CONVERT(varchar(100), GETDATE(), 23)>jkdqr)";
		rs.executeSql(sql);
	}
}
