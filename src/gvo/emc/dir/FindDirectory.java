package gvo.emc.dir;

import gvo.emc.dir.DfcWsCallServiceStub.CallService;
import gvo.emc.dir.DfcWsCallServiceStub.CallServiceResponse;

import java.util.ArrayList;

import net.sf.json.JSONArray;

import org.json.JSONObject;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;

public class FindDirectory {
	BaseBean log = new BaseBean();//定义写入日志的对象
//	public static int a =2;
	public  String os(String sendJson) throws Exception {
//		log.writeLog("发送json:" +sendJson);
		DfcWsCallServiceStub dcs = new DfcWsCallServiceStub();
		CallService callService = new DfcWsCallServiceStub.CallService();
		callService.setSendJsonString(sendJson);
		CallServiceResponse callServiceResponse = dcs.callService(callService);
		String json = callServiceResponse.getEntrance();
//		log.writeLog("返回json:" );
//		Thread.sleep(10);
		return json;
	}
	
	public  ArrayList findPersonDirectory(String userId,String Path,boolean flag) throws Exception{
		String sendJson = "{\"ws\":{\"wsId\":101103,\"userId\":\""+userId+"\",\"path\":\""+Path+"\",\"childrenFull\":"+flag+",\"supplierId\":\"weaver-oa\"},\"cond\":{}}";
		String json = os(sendJson);
//		  log.writeLog("返回json:" +json);
		ArrayList<MLObject> list= new ArrayList<MLObject>();
         JSONObject ss=new JSONObject(json);
         org.json.JSONArray arr = new org.json.JSONArray();
         try{
         arr=ss.getJSONObject("list").getJSONArray("list");
         }catch(Exception e){
          log.writeLog("发送json:" +sendJson);
  		  log.writeLog("返回json:" +json);  		  
        	 return list;
         }
		 JSONArray array = JSONArray.fromObject(arr.toString()); 
		for(int i=0;i<array.size();i++){
			net.sf.json.JSONObject jo=array.getJSONObject(i);
			MLObject ml= (MLObject)net.sf.json.JSONObject.toBean(jo,MLObject.class);
			list.add(ml);
			
		}
		return list;
	}
	
    public  void findUserDr(int id,String userId,String path)throws Exception{
    	
    	path=path.replace("\\", "\\\\\\\\");
		ArrayList<MLObject> list= (ArrayList<MLObject>) findPersonDirectory(userId,path,false);
		if(list.size() != 0){
			insertData(id,list);
			for(MLObject ml :list){	 
				if("昆山维信诺科技有限公司".equals(ml.getName())){
					findUserDr(id,userId,ml.getObjectId());
				}else{
				findDetailDri(id,userId,ml.getObjectId(),true);
				}
			}
		}
	}
    public void findDetailDri(int id,String userId,String path,boolean flag) throws Exception{
    	try{
    	ArrayList<MLObject> list= (ArrayList<MLObject>) findPersonDirectory(userId,path,flag);
    	if(list.size() != 0){
			insertData(id,list);
		}
    	}catch(Exception e){
    		ArrayList<MLObject> drlist= (ArrayList<MLObject>) findPersonDirectory(userId,path,false);
    		if(drlist.size() != 0){
    			insertData(id,drlist);
    		
	    		for(MLObject ml:drlist){
	    			findDetailDri(id,userId,ml.getObjectId(),true);
	    		}
    		}
    	}
		
    }
    
    public  void insertData(int id,ArrayList<MLObject> list){
    	RecordSet rs=new RecordSet();
        String sql="";
	    for(MLObject ml:list){
		  sql="select 1 from gvo_emp_emc_dir where empid = "+id+" and dir_for = '"+ml.getFolderPath()+"'";
		  rs.execute(sql);
		  if(rs.next()){
			  sql="update gvo_emp_emc_dir set status = 1 where empid ="+id+" and dir_for = '"+ml.getFolderPath()+"'";
			  rs.execute(sql);
			  continue;
		  }
		 
		  sql= "insert into gvo_emp_emc_dir values(gvo_emp_emc_dir_seqno.nextval,"+id+", '"+ml.getName()+"', '"+ml.getFolderPath()+
			   "','"+ml.getObjectId()+"','1',to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss'),to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss'),1)";
//		  a=a+1;
//		  sql= "insert into gvo_emp_emc_dir values("+a+","+id+", '"+ml.getName()+"', '"+ml.getFolderPath()+
//				   "','"+ml.getObjectId()+"','1',GETDATE(),GETDATE())";
//		  log.writeLog("插入数据:" +sql);
		  rs.execute(sql);
		 
	    }
    }
    
    public String findUser(){
    	RecordSet rs_all = new RecordSet();
    	RecordSet rs= new RecordSet();
    	
    	try {

    	String sql="select id,loginid from hrmresource where status in(0,1,2,3) and loginid is not null order by id asc";
//    	log.writeLog("开始检索目录:" +sql);
    	System.out.println("开始检索目录");
    
	    	rs_all.execute(sql);
	    	ArrayList<User> users= new ArrayList<User>();
	    	while(rs_all.next()){
	    		
	    			int empID = rs_all.getInt("id");
	    			String loginID=rs_all.getString("loginid");
	    			User user=new User();
	    			user.setId(empID);
	    			user.setLoginId(loginID);
//	    			log.writeLog("执行人员目录同步：" +empID +";emc同步账号"+loginID);
					users.add(user);
				
	    	}
	    	findAll(users);
    	} catch (Exception e) {
			log.writeLog("异常信息：" +e.getMessage());
			e.printStackTrace();
		}
    	
//    	log.writeLog("检索结束:");
    	return "success";
    }
    public void findAll(ArrayList<User> users) throws Exception{
    	String sql="";
    	RecordSet rs= new RecordSet();
    	for(User user:users){
    		sql="update gvo_emp_emc_dir set status =0 where empid = "+user.getId();
    		rs.execute(sql);
    		findUserDr(user.getId(),user.getLoginId(),"/");
    		sql="update gvo_emp_emc_dir set ACTIVE_FLAG = '0' where empid = "+user.getId()+" and status = 0";
    		
    	}
    }
}
