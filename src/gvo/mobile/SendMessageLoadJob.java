package gvo.mobile;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

public class SendMessageLoadJob extends BaseCronJob {

	
	public void execute() {
		RecordSet rs = new RecordSet();
		
		BaseBean bb = new BaseBean();
		bb.writeLog("SendMessageLoadJob Job Start! "); 
		String sql = "select * from gvo_send_message_info where sendTime is null "
				+" and delaySendtIime<tochar(sysdate,'yyyy-mm-dd HH24:MI:SS')";
		rs.executeSql(sql);
		while(rs.next()){
			
			
			
		}
	}
}
