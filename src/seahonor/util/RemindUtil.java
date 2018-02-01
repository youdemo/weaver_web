package seahonor.util;

import java.util.HashMap;
import java.util.Map;


/*
 * 提醒信息
 */
public class RemindUtil {
	/*
	 *  提醒方法     
	 *  @creater   发起人
	 *  @reDate    提醒日期       【如果是循环就是开始日期】
	 *  @reTime    提醒时间
	 *  @title     提醒标题
	 *  @Remarks    提醒内容
	 *  @type      提醒类型        0  一次提醒          1 周期提醒   
	 *  @waySys    提醒方式        0  系统流程提醒  1 不需要系统系统
	 *  @wayEmail  提醒方式     0  邮件提醒          1 不需要邮件提醒
	 *  @waySms    提醒方式        0  短信提醒          1 不需要短信提醒   
	 *  @reName    提醒内容类型   [表创建,通过编码标示区分]
	 *  @other     其他[流程、客户、项目、合同]？    Json拼接    【展现时考虑不同性和页面展示信息】
	 *  @notifier  通知人
	 *  @is_active 有效性     0 有效    1失效  
	 */
	public boolean remind(String creater,String reDate,String reTime,String title,
				String remarks,String type,String waySys,String wayEmail,String waySms,String reName,
				String other,String notifier,String is_active){
		//   插入中间表  remindRecord
		Map<String, String> mapStr = new HashMap<String, String>();
		
		String tableName = "uf_remind";
		SysNoForSelf sns = new SysNoForSelf();
		String id = sns.getMaxNum(tableName);
		mapStr.put("id", "##newId()");
		mapStr.put("creater", creater);
		mapStr.put("created_time", "##getdate()");
		mapStr.put("reDate", reDate);
		mapStr.put("reTime", reTime);
		mapStr.put("title", title);
		mapStr.put("Remarks", remarks);
		mapStr.put("type", type);
		mapStr.put("waySys", waySys);
		mapStr.put("wayEmail", wayEmail);
		mapStr.put("waySms", waySms);
		mapStr.put("reName", reName);
		mapStr.put("other", other);
		mapStr.put("notifier", notifier);
		mapStr.put("is_active", is_active);
		
		return new InsertUtil().insert(mapStr, tableName);
	}
	
	//way：   第一位   是系统流程提醒   第二位 是邮件提醒  第三位 短信提醒    0是开启   1是关闭
	public boolean remind(String creater,String reDate,String reTime,String title,
			String remarks,String type,String way,String reName,String other,String notifier){
		if(null == way || way.length() !=3){
			return remind(creater,reDate,reTime,title,remarks,type,"1","1","1",reName,other,notifier,"0");
		}else{
			String way1 = way.substring(0,1);
			String way2 = way.substring(1,2);
			String way3 = way.substring(2,3);
			return remind(creater,reDate,reTime,title,remarks,type,way1,way2,way3,reName,other,notifier,"0");
		}
	}
	
	public static void main(String[] args) {
		new RemindUtil().remind("1", "2015-08-06", "09:40", "信息提醒标题", "信息提醒内容", 
					"0", "0", "1", "1","TCY001", "{\"projectid\":12}", "12,2,312,43", "0");	
	}
	
}
