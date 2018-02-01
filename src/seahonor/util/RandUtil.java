package seahonor.util;

import java.util.Random;

public class RandUtil {
	
	/*
	 * 生成Num位随机数字
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
	 * 生成Num位随机数字或字母
	 */
	public static String ranStr(int num){
		Random rd = new Random();
		StringBuffer buff = new StringBuffer();
		for(int index = 0 ; index < num ; index++){
			
			if(rd.nextInt(2)%2==0){
				// 生成数字
				buff.append(rd.nextInt(10));
			}else{
				// 生成字母
				int tmp_1 = 65+rd.nextInt(26);
				buff.append((char)tmp_1);
			}
		}
		
		return buff.toString();
	}
}
