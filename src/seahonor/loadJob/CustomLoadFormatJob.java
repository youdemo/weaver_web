package seahonor.loadJob;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

public class CustomLoadFormatJob extends BaseCronJob {
	
	@Override
	public void execute() {
		RecordSet rs = new RecordSet();
		BaseBean log = new BaseBean();
		log.writeLog("CustomLoadFormatJob ... ");
		
		String sql = "";
		
	}

}
