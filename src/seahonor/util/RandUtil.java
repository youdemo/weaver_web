package seahonor.util;

import java.util.Random;

public class RandUtil {
	
	/*
	 * ����Numλ�������
	 */
	public static String ranNum(int num){
		Random rd = new Random();
		StringBuffer buff = new StringBuffer();
		for(int index = 0 ; index < num ; index++){
			buff.append(rd.nextInt(10));
		}
		
		return buff.toString();
	}
	
	/*
	 * ����Numλ������ֻ���ĸ
	 */
	public static String ranStr(int num){
		Random rd = new Random();
		StringBuffer buff = new StringBuffer();
		for(int index = 0 ; index < num ; index++){
			
			if(rd.nextInt(2)%2==0){
				// ��������
				buff.append(rd.nextInt(10));
			}else{
				// ������ĸ
				int tmp_1 = 65+rd.nextInt(26);
				buff.append((char)tmp_1);
			}
		}
		
		return buff.toString();
	}
}
