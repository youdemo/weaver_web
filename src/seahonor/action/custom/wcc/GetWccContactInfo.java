package seahonor.action.custom.wcc;

import weaver.interfaces.schedule.BaseCronJob;

/**
 * Created by adore on 16/3/9.
 * ��ʱ����
 */

public class GetWccContactInfo extends BaseCronJob {

    public void execute() {  
    	GetWCCImplAction gwi= new GetWCCImplAction();
    	gwi.GetWCCContactDataInfo(); 
    }
}
