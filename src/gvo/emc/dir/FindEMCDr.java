package gvo.emc.dir;

import weaver.general.BaseBean;
import weaver.interfaces.schedule.BaseCronJob;

/**

 * 定时任务
 */

public class FindEMCDr extends BaseCronJob {

    public FindEMCDr() {
    }
    public void execute() {  
    	BaseBean log = new BaseBean();
        log.writeLog("查询目录开始!");
        FindDirectory fd = new FindDirectory();
        String su=fd.findUser();
        if(su.equals("success")){
        	log.writeLog("查询目录结束!");
        }
                
    }
}
