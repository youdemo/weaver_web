package seahonor.action.custom;

import java.text.SimpleDateFormat;
import java.util.Date;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

public class UpdateCMInfoAction {
	BaseBean lo = new BaseBean();
   public void updateTableInfoALL(){
	   RecordSet rs = new RecordSet();
	   RecordSet rs_d = new RecordSet();
	   String sql = "";
	   String sql_d = "";
	   String planId = "";
	   String oldId = "";
	   String newId = "";
	   String now ="";
	   String hblb ="";
	   sql="select * from uf_cm_planinfo where status= '0' and hblb='0'";	 
	   SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd H:m:s");
	   rs.executeSql(sql);
	   while (rs.next()){
		   planId = Util.null2String(rs.getString("id"));
		   oldId = Util.null2String(rs.getString("oldId"));
		   newId = Util.null2String(rs.getString("newId"));
		   hblb = Util.null2String(rs.getString("hblb"));
		   updateTableInfo(planId,oldId,newId,hblb); 
		   now=sf.format(new Date());
		   sql_d = "update uf_cm_planinfo set status = '1',updatetime='"+now+"' where id="+planId;
		   rs_d.executeSql(sql_d);
	   }
	   
   }
   
   public void updateTableInfo(String planId,String oldId,String newId,String hblb){
	   RecordSet rs = new RecordSet();
	   RecordSet rs_d = new RecordSet();
	   BaseBean log = new BaseBean();
	   String sql = "";
	   String sql_d = "";
	   String tableName = "";
	   String columnName = "";
	   String keyName = "";
	   int count=0;
	   sql = "select * from uf_cm_tableinfo where lb='"+hblb+"'";	 
	   rs.executeSql(sql);
	   while (rs.next()){
		   tableName = Util.null2String(rs.getString("tableName"));
		   columnName = Util.null2String(rs.getString("columnName"));
		   keyName = Util.null2String(rs.getString("keyName"));		 
		   if(!"".equals(tableName)&&!"".equals(columnName)&&!"".equals(keyName)){
			   sql_d = "select "+keyName+" from "+tableName+" where "+columnName+"="+oldId;		
			   count=0;
			   try{
				   rs_d.executeSql(sql_d);
				   if(rs_d.next()){
					   count=1;
				   }
			   }catch(Exception e){
				   
				   log.writeLog("±í»ò×Ö¶Î´íÎó:"+sql_d);
				   log.writeLog(e.getMessage());
				   continue;
			   }
			   if(count >0){
				   sql_d = "insert into uf_cm_histroy(tablename,columnname,keyid,planid,oldvalue,newvalue) " +
				   		"select '"+tableName+"','"+columnName+"',"+keyName+",'"+planId+"','"+oldId+"','"+newId+"' from "+tableName+" where "+columnName+"="+oldId;				  
				   rs_d.executeSql(sql_d);
				   sql_d = "update "+tableName+" set "+columnName+"='"+newId+"' where "+columnName+" = '"+oldId+"'";				
				   rs_d.executeSql(sql_d);
			   }
		   }
	   }
	   
   }
   
   public void insertCMPlanInfo(String oldId,String newId,String requestId){
	   RecordSet rs = new RecordSet();
	   SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd H:m:s");
	   String now=sf.format(new Date());
	   String sql = "";	   
	   sql = "insert into uf_cm_planinfo(requestId,oldid,newid,status,inserttime) values('"+requestId+"','"+oldId+"','"+newId+"','0','"+now+"')";
	   rs.executeSql(sql);
   }
   
}
