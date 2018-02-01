package seahonor.action.purchsse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

public class AutoCreatePurchase {

	public String AutoCreateRequest(String type,String requestid,String tableName) throws Exception{
		BaseBean log = new BaseBean();
		RecordSet rs = new RecordSet();
		RecordSet rs_detail = new RecordSet();
		 JSONObject header = new JSONObject();   
		 JSONArray detail = new JSONArray(); 
		 JSONObject json = new JSONObject(); 
		 
		 String creater ="1";
		 String sffcgcg = "0";
		 String sql="select resourceid from  HrmRoleMembers where roleid=26";
		 rs.executeSql(sql);
		 if(rs.next()){
			 creater = Util.null2String(rs.getString("resourceid"));
		 }
		 if("1".equals(type)&&!"".equals(tableName)&&!"".equals(requestid)){
			 String ApplicationDepartment="";
			 String Application="";
			 sql="select ApplicationDepartment,Application from "+tableName+" where requestid="+requestid;
			
			 rs.executeSql(sql);
			 if(rs.next()){
				 ApplicationDepartment = Util.null2String(rs.getString("ApplicationDepartment"));
				 Application = Util.null2String(rs.getString("Application"));
			 }
			 if(!"".equals(ApplicationDepartment)&&!"".equals(Application)){
				 int count=0;
				 sql="select COUNT(1) as count from HrmDepartment  where subcompanyid1=10 and supdepid=75 and id="+ApplicationDepartment;
				 
				 rs.executeSql(sql);
			    if(rs.next()){
			    	count = rs.getInt("count");
			    }
			    if(count >0){
			    	creater = Application;
			    	sffcgcg = "1";
			    }
			 }
		 }
		 String workflowCode="47";
		 json.put("HEADER", header);  
		 json.put("DETAILS", detail);  
		 sql="select year(getdate())  Year,month(getdate())-1  Month,id ,CONVERT(varchar(20) ," +
		 		" getdate(), 23 ) date,convert(char(5),getdate(),108) time from uf_Purchase " +
		 		"where formmodeid=94 and status = 0 and type="+type;
		 rs.executeSql(sql);
		 String year = "";
		 String month = "";
		 String mainid = "";
		 String date="";
		 String time="";
		 if(rs.next()){
			 year = Util.null2String(rs.getString("Year"));
			 month = Util.null2String(rs.getString("Month"));
			 mainid = Util.null2String(rs.getString("id")); 
			 date = Util.null2String(rs.getString("date")); 
			 time = Util.null2String(rs.getString("time")); 
		 }else{
			 log.writeLog("无采购数据，流程创建失败： "+sql);
			 return "false";
		 }
		 header.put("Year", year);
		 header.put("Month",month);
		 header.put("Creater",creater);
		 header.put("CreateDate", date);
		 header.put("ApplicationDate", date);
		 header.put("Application", creater);
		 header.put("appTime", time);
		 header.put("CreateTime", time);
		 header.put("sffcgcg", sffcgcg); 
		 header.put("xqsqr", creater); 
		 
		 sql="select name,val,numbers,queryprice,Remark from uf_Purchase_dt1 where mainid="+mainid;
		 rs_detail.executeSql(sql);
		 String name = "";
		 String val = "";
		 int numbers = 0;
		 String queryprice = "";
		 String remark = "";
		 RecordSet rs1 = new RecordSet();
		 String sql1="";
		 String kcl="";
		 String AvgNum="";
		 String AvgPrice = "";
		 String lastPrice="";
		 while(rs_detail.next()){
			 name = Util.null2String(rs_detail.getString("name"));
			 val = Util.null2String(rs_detail.getString("val"));
			 numbers = rs_detail.getInt("numbers");
			 queryprice = Util.null2String(rs_detail.getString("queryprice"));
			 remark = Util.null2String(rs_detail.getString("Remark"));
			 JSONObject node = new JSONObject();  
			 sql1="select sum(num) as sum from uf_goodsinforecord where infoid = '"+name+"' and isnull(status,0) = 0";
			 rs1.executeSql(sql1);
			 if(rs1.next()){
				 kcl = Util.null2String(rs1.getString("sum"));
			 }
			 sql1="select max(num) as sun_num from all_out_goods_view where goodsname in(select id from uf_goodsinforecord where infoid='"+name+"')";
			 rs1.executeSql(sql1);
			 if(rs1.next()){
				 AvgNum  = Util.null2String(rs1.getString("sun_num"));
			 }
			 sql1="select convert(decimal(10,2),avg(price)) price from uf_outinrecord where operaterecord=0 and origin=3  and applydate>=CONVERT(varchar(100), DATEADD(month, -3, getDate()), 23)  and goodsname in(select id from uf_goodsinforecord where infoid='"+name+"')";
			 rs1.executeSql(sql1);
			 if(rs1.next()){
				 AvgPrice  = Util.null2String(rs1.getString("price"));
			 }
			 sql1="select price from uf_outinrecord where id in(select MAX(id) from uf_outinrecord  where  operaterecord=0 and origin=3 and goodsname in(select id from uf_goodsinforecord where infoid='"+name+"') )";
			 rs1.executeSql(sql1);
			 if(rs1.next()){
				 lastPrice  = Util.null2String(rs1.getString("price"));
			 }
			 
			 node.put("Name", name);
			 node.put("val_1", val);
			 node.put("Numbers", numbers);
			 node.put("ActualNumbers", numbers);
			 node.put("QueryPrice", queryprice);
			 node.put("Remark", remark);
			 node.put("Stock", kcl);
			 node.put("AvgNum", AvgNum);
			 node.put("AvgPrice", AvgPrice);
			 node.put("lastPrice", lastPrice);
			 
			 detail.put(node);  
		 }
		 AutoRequestService ars = new AutoRequestService();
		 String result=ars.createRequest(workflowCode, json.toString(), creater,mainid);
		 
		 return result;
	}
			
}
