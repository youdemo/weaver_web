package seahonor.util;

import java.util.HashMap;
import java.util.Map;


/*
 * ������Ϣ
 */
public class RemindUtil {
	/*
	 *  ���ѷ���     
	 *  @creater   ������
	 *  @reDate    ��������       �������ѭ�����ǿ�ʼ���ڡ�
	 *  @reTime    ����ʱ��
	 *  @title     ���ѱ���
	 *  @Remarks    ��������
	 *  @type      ��������        0  һ������          1 ��������   
	 *  @waySys    ���ѷ�ʽ        0  ϵͳ��������  1 ����Ҫϵͳϵͳ
	 *  @wayEmail  ���ѷ�ʽ     0  �ʼ�����          1 ����Ҫ�ʼ�����
	 *  @waySms    ���ѷ�ʽ        0  ��������          1 ����Ҫ��������   
	 *  @reName    ������������   [����,ͨ�������ʾ����]
	 *  @other     ����[���̡��ͻ�����Ŀ����ͬ]��    Jsonƴ��    ��չ��ʱ���ǲ�ͬ�Ժ�ҳ��չʾ��Ϣ��
	 *  @notifier  ֪ͨ��
	 *  @is_active ��Ч��     0 ��Ч    1ʧЧ  
	 */
	public boolean remind(String creater,String reDate,String reTime,String title,
				String remarks,String type,String waySys,String wayEmail,String waySms,String reName,
				String other,String notifier,String is_active){
		//   �����м��  remindRecord
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
	
	//way��   ��һλ   ��ϵͳ��������   �ڶ�λ ���ʼ�����  ����λ ��������    0�ǿ���   1�ǹر�
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
		new RemindUtil().remind("1", "2015-08-06", "09:40", "��Ϣ���ѱ���", "��Ϣ��������", 
					"0", "0", "1", "1","TCY001", "{\"projectid\":12}", "12,2,312,43", "0");	
	}
	
}
