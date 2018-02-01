package seahonor.util;

import weaver.conn.RecordSet;
import weaver.interfaces.schedule.BaseCronJob;
import java.text.SimpleDateFormat;
import java.util.*;
import weaver.general.BaseBean;
import weaver.general.Util;

public class ControlDate extends BaseCronJob {
	private RecordSet rs = new RecordSet();
	BaseBean log = new BaseBean();// 定义写入日志的对象
	private RecordSet temprs = new RecordSet();

	Date dt = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String dateNowStr = sdf.format(dt);

	public void execute() {
		getNeedDelEmlName();
	}

	public void getNeedDelEmlName() {
		this.rs.executeSql("select * from uf_badges");
		String ValidTerm;

		while (this.rs.next()) {
			ValidTerm = Util.null2String(this.rs.getString("ValidTerm"));
			//state=2为失效状态
			if (!"".equals(ValidTerm)&&ValidTerm.compareTo(dateNowStr) < 0) {
				String sql_1 = "update uf_badges set state = 2 where ValidTerm in( '"
						+ ValidTerm + "' )";
				this.temprs.executeSql(sql_1);
				//log.writeLog("测试sql_1" + sql_1);
			}
		}
	}
	/*
	 * public void getNeedDelEmlName() {
	 * this.rs.executeSql("select * from MailSetting"); int i; String str2;
	 * String str3; while (this.rs.next()) { i = this.rs.getInt("userid"); int j
	 * = this.rs.getInt("emlsavedays"); str2 = getNeedDeleteDate(j); str3 =
	 * "select * from mailresource where haseml > -1 and resourceid = " + i +
	 * " and emltime <  '" + str2 + "'";
	 * 
	 * this.rs1.executeSql(str3); while (this.rs1.next()) { String str4 =
	 * this.rs1.getString("emlName"); int m = this.rs1.getInt("id");
	 * deleteeml(m, str4); } }
	 * 
	 * this.rs.executeSql("select * from SystemSet"); while (this.rs.next()) { i
	 * = this.rs.getInt("emlsavedays"); String str1 = getNeedDeleteDate(i); str2
	 * =
	 * "select * from mailresource where resourceid not in (select userid from mailsetting) and haseml > -1 and emltime < '"
	 * + str1 + "'";
	 * 
	 * this.rs1.execute(str2); while (this.rs1.next()) { str3 =
	 * this.rs1.getString("emlName"); int k = this.rs1.getInt("id");
	 * deleteeml(k, str3); } } }
	 * 
	 * public void deleteeml(int paramInt, String paramString) { Object
	 * localObject = getCurrPath() + "/email/eml/" + paramString + ".eml";
	 * String str1 = ""; String str2 = "select * from mailresource where id = "
	 * + paramInt; this.temprs.executeSql(str2); if (this.temprs.next()) str1 =
	 * Util.null2String(this.temprs.getString("emlpath")); if (!str1.equals(""))
	 * localObject = str1; File localFile = new File((String)localObject); if
	 * (localFile.exists()) { boolean bool = localFile.delete(); if (bool)
	 * updateHasEML(paramInt); } else { updateHasEML(paramInt); } }
	 * 
	 * public String getNeedDeleteDate(int paramInt) { long l = 86400000L; Date
	 * localDate = new Date(System.currentTimeMillis() - l * (paramInt - 1));
	 * 
	 * return localDate.toString(); }
	 * 
	 * public void updateHasEML(int paramInt) { String str =
	 * "update mailresource set haseml = -1 where id = " + paramInt;
	 * this.temprs.executeSql(str); }
	 * 
	 * public String getCurrPath() { String str = GCONST.getPropertyPath(); str
	 * = str.substring(0, str.indexOf("WEB-INF")); str = Util.StringReplace(str,
	 * "\\", "/"); return str; }
	 */
}