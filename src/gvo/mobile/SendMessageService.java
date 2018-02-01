package gvo.mobile;

import weaver.general.BaseBean;
import weaver.sms.SmsService;

public class SendMessageService extends BaseBean implements SmsService{

	public boolean sendSMS(String smsId, String number, String msg) {
		BaseBean log = new BaseBean();
		
		log.writeLog("SendMessageService ... ...!!!");
		
        SendResult sr = new SendResult();
        boolean isF = sr.sendSMS(smsId,number,msg);

		return isF;
	}

}
