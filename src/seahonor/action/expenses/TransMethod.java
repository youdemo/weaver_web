package seahonor.action.expenses;
/**
 * 
 * 转换方法
 * @author tangjianyong
 * @version 1.0 2017-10-26
 *
 */
public class TransMethod {
	/**
	 * 金额千分位
	 * @param value 传入值
	 * @return 返回值
	 */
	public String getTransNum(String value) {
		if (value == null || "".equals(value)) {
			value = "0";
		}
		double num = Double.valueOf(value);
		return String.format("%,.2f ", num);

	}
	
	  public String getLinkType(String jkje,String jkr, String bz)
	  {
	    String str = "";
	    str = "<a href=javaScript:showInfo('"+jkr+"','"+bz+"')>" + getTransNum(jkje) + "</a>";
	    return str;
	  }

}
