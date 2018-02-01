package gvo.emc.dir;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;

public class Jishu {
	BaseBean log = new BaseBean();
   public String ji(){
	   RecordSet rs = new RecordSet();
	   String sql1="select num from test_number where id=1";
	   rs.execute(sql1);
	   if(rs.next()){
		   int i=rs.getInt("num");
		   log.writeLog("¼ÆÊý:"+i);
	   }
	   String sql="update test_number set num=0 where id = 1 ";
	   rs.execute(sql);
	   return "success";
   }
}
