package gvo.emc.dir;

import weaver.general.BaseBean;
import weaver.interfaces.schedule.BaseCronJob;

/**

 * ��ʱ����
 */

public class FindEMCDr extends BaseCronJob {

    public FindEMCDr() {
    }
    public void execute() {  
    	BaseBean log = new BaseBean();
        log.writeLog("��ѯĿ¼��ʼ!");
        FindDirectory fd = new FindDirectory();
        String su=fd.findUser();
        if(su.equals("success")){
        	log.writeLog("��ѯĿ¼����!");
        }
                
    }
}
