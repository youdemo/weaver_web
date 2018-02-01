package seahonor.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import weaver.conn.RecordSet;

public class SysNoForSelf {

	public String getMaxNum(String tableName){
		if(tableName == null) return "";
		tableName = tableName.toUpperCase();
		InsertUtil iu = new InsertUtil();
		RecordSet rs = new RecordSet();
		
		int count_cc = 0;
		String sql = "select count(id) as count_cc from uf_SysNo where strsys=null and " 
			 +" datesys=null and tableName='"+tableName+"'"; 
		rs.executeSql(sql);
		if(rs.next()){
			count_cc = rs.getInt("count_cc");
		}
		
		int num = 1;
		if(count_cc > 0){
			sql = "select nextseq from uf_SysNo where strsys=null and " 
					 +" datesys=null and tableName='"+tableName+"'";  
			rs.executeSql(sql);
			if(rs.next()){
				num = rs.getInt("nextseq");
			}
			
			sql = "update uf_SysNo set nextseq=nextseq+1 where strsys=null and " 
					 +" datesys=null and tableName='"+tableName+"'";
			rs.executeSql(sql);
			
		}else{
			Map<String,String> mapStr = new HashMap<String,String>();
			mapStr.put("nextseq", "2");
			mapStr.put("tablename", tableName);
			
			iu.insert(mapStr, "uf_SysNo");
		}		
		
		return String.valueOf(num);
	}
	
	public String getNum(String str,String tableName,int index){
		if(tableName == null) return "";
		tableName = tableName.toUpperCase();
		InsertUtil iu = new InsertUtil();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String now = sdf.format(new Date());
		RecordSet rs = new RecordSet();
		
		int count_cc = 0;
		String sql = "select count(id) as count_cc from uf_SysNo where strsys='"+str+"' and " 
			 +" datesys='"+now+"' and tableName='"+tableName+"'"; 
		rs.executeSql(sql);
		if(rs.next()){
			count_cc = rs.getInt("count_cc");
		}
		
		int num  = 1;
		
		if(count_cc > 0){
			sql = "select nextseq from uf_SysNo where strsys='"+str+"' and " 
					 +" datesys='"+now+"' and tableName='"+tableName+"'";  
			rs.executeSql(sql);
			if(rs.next()){
				num = rs.getInt("nextseq");
			}
			
			sql = "update uf_SysNo set nextseq=nextseq+1 where strsys='"+str+"' and " 
					 +" datesys='"+now+"' and tableName='"+tableName+"'";
			rs.executeSql(sql);
			
		}else{
			Map<String,String> mapStr = new HashMap<String,String>();
			mapStr.put("strsys", str);
			mapStr.put("datesys", now);
			mapStr.put("nextseq", "2");
			mapStr.put("tablename", tableName);
			
			iu.insert(mapStr, "uf_SysNo");
		}		
		String tmp = str + now + getStrNum(num,index);
		
		return tmp;
	}
	
	public String getStrNum(int num,int len){
		String buff = String.valueOf(num);
		int max = len - buff.length();
		for(int index = 0; index < max;index++){
			buff = "0" + buff;
		}
		return buff;
	}
	
	public static void main(String[] args) {
	//	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	//	String now = sdf.format(new Date());
		
		System.out.println(new SysNoForSelf().getStrNum(10200, 4));
	}
}
