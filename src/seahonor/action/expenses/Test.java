package seahonor.action.expenses;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import COM.rsa.jsafe.aa;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date date=new Date();
		SimpleDateFormat s=new SimpleDateFormat("yyyy'年'MM'月'dd'日'");
		System.out.println(s.format(date));
		String str1=s.format(date);//当前的时间
		String keyid="123_0";
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 30);//计算30天后的时间
		String str2=s.format(c.getTime());
		System.out.println("30天后的时间是："+str2);
		TransMethod aaa= new TransMethod();
		System.out.println(aaa.getLinkType("1234", "12","13"));
		System.out.println("0.1".compareTo("0"));

	}

}
