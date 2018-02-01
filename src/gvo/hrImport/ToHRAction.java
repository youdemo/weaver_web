package gvo.hrImport;


import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.conn.RecordSetTrans;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.resource.ResourceComInfo;
import weaver.interfaces.workflow.action.Action;
import weaver.rtx.OrganisationCom;
import weaver.soa.workflow.request.RequestInfo;

public class ToHRAction implements Action {
	
	String dataid = "local_HR";
	
	public String execute(RequestInfo info) {

		char separator = Util.getSeparator();
		RecordSet rs = new RecordSet();
		
		String sql = "";
		String tableName = "";
		
		sql = "Select tablename From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id=" + info.getWorkflowid() + ")";
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}

		if (!"".equals(tableName)) {
			String sql_1 = "select * from " + tableName + " where requestid = " + info.getRequestid();
			new BaseBean().writeLog("sql1---------" + sql_1);
			rs.executeSql(sql_1);
			Map<String, String> mapStr = new HashMap<String, String>();
			UtilForGvo ug = new UtilForGvo();
			if (rs.next()) {
				// 调职人
				String emp_id = Util.null2String(rs.getString("dzr")); 
				if("".equals(emp_id))  return "-1";
				
				// 调动类型
				String type = "";
				String type_1 = Util.null2String(rs.getString("pd"));    // 平调
				String type_2 = Util.null2String(rs.getString("js"));    //  晋升
				String type_3 = Util.null2String(rs.getString("jz"));    // 降职
				String type_4 = Util.null2String(rs.getString("jd"));    //  借调
				if("1".equals(type_1)){
					type = "1001";
				}else if("1".equals(type_2)){
					type = "1002";
				}else if("1".equals(type_3)){
					type = "1003";
				}else if("1".equals(type_4)){
					type = "1004";
				}
				
				// 调动方式
				String res_type = getTypeID(Util.null2String(rs.getString("sfkdw")));    
				
				// 申请调动原因 
				String reason = Util.null2String(rs.getString("sqddyy"));   
				// 调动日
				String moveDate = Util.null2String(rs.getString("jyddr")); 
				
				// 调动前身份 dqsf
				// 调动后身份   dhsf 
				String before = Util.null2String(rs.getString("dqsf"));  
				String after = Util.null2String(rs.getString("dhsf"));  
				
				// 调动前岗位  dhgw 
				String befor_job = Util.null2String(rs.getString("dqgw")); 
				// 调动后岗位  dhgw 
				String after_job = Util.null2String(rs.getString("dhgw")); 
				
				// 考察结束日
				String after_over_end = Util.null2String(rs.getString("kcjsr")); 

				// ddqzd 调动前职等
				String class_before = Util.null2String(rs.getString("ddqzd"));
 
				// ddhzd  调动后职等
				String class_after = Util.null2String(rs.getString("ddhzd")); 

				// ddqzj  调动前职级
				String lv_before = Util.null2String(rs.getString("ddqzj"));
 
				// ddhzj  调动后职级
				String lv_after = Util.null2String(rs.getString("ddhzj")); 
				
				mapStr.put("key_id",info.getRequestid());	//关键字     -->  流程requestid
				mapStr.put("employee_code",ug.getWorkcode(emp_id));	//员工编码
				mapStr.put("transfer_type",type);	//调动类型
				mapStr.put("transfer_way",res_type);	//调动方式
				
				//  调岗前 (目前的部门)
				String[] bef_arr =  ug.getArr(emp_id);
				
				mapStr.put("transfer_org1_desc_bef",bef_arr[0]);	//调动前组织结构1描述
				mapStr.put("transfer_org2_desc_bef",bef_arr[1]);	//调动前组织结构2描述
				mapStr.put("transfer_org3_desc_bef",bef_arr[2]);	//调动前组织结构3描述
				mapStr.put("transfer_org4_desc_bef",bef_arr[3]);	//调动前组织结构4描述
				mapStr.put("transfer_org5_desc_bef",bef_arr[4]);	//调动前组织结构5描述
				mapStr.put("transfer_org6_desc_bef",bef_arr[5]);	//调动前组织结构6描述
				mapStr.put("transfer_org7_desc_bef",bef_arr[6]);	//调动前组织结构7描述
				mapStr.put("transfer_org8_desc_bef",bef_arr[7]);	//调动前组织结构8描述
				
				//  调岗后 (选择的部门)
				String dept_id = Util.null2String(rs.getString("dhbm")); 
				String[] aft_arr = ug.getArr_dept(dept_id);
				
				mapStr.put("transfer_org1_desc_after",aft_arr[0]);	//调动后组织结构1描述
				mapStr.put("transfer_org2_desc_after",aft_arr[1]);	//调动后组织结构2描述
				mapStr.put("transfer_org3_desc_after",aft_arr[2]);	//调动后组织结构3描述
				mapStr.put("transfer_org4_desc_after",aft_arr[3]);	//调动后组织结构4描述
				mapStr.put("transfer_org5_desc_after",aft_arr[4]);	//调动后组织结构5描述
				mapStr.put("transfer_org6_desc_after",aft_arr[5]);	//调动后组织结构6描述
				mapStr.put("transfer_org7_desc_after",aft_arr[6]);	//调动后组织结构7描述
				mapStr.put("transfer_org8_desc_after",aft_arr[7]);	//调动后组织结构8描述
				
				mapStr.put("transfer_jobs_before",ug.getJob(befor_job));//调动前岗位描述
				mapStr.put("transfer_jobs_after",ug.getJob(after_job));	//调动后岗位描述
				
				mapStr.put("transfer_identity_before",getID(before));	//调动前身份
				mapStr.put("transfer_identity_after",getID(after));	//调动后身份

				mapStr.put("transfer_class_before",getID(class_before));//调动前职位层级
				mapStr.put("transfer_class_after",getID(class_after));	//调动后职位层级

				mapStr.put("transfer_lv_before",getID(lv_before));	//调动前职位等级
				mapStr.put("transfer_lv_after",getID(lv_after));	//调动后职位等级
				
				mapStr.put("transfer_date",moveDate.replace("-", "/"));	//调动日期
				mapStr.put("transfer_reason",reason);	//调动原因
				
				String dt = Util.null2String(rs.getString("jykcq2"));
				mapStr.put("Investigate_dt",dt);	    //考察开始日期
				mapStr.put("actual_effect_date",after_over_end);	//考察结束日期
				mapStr.put("create_dt","##getdate()");	//创建日期    -->当前时间
				
				RecordSetDataSource rsx = new RecordSetDataSource(dataid);
				ug.allApp(mapStr, "cus_Personnel_change_info", rsx);
				
				// 调动信息
				String oldjobtitleid =  ""; 
				String oldjoblevel =  "0"; 
				String newjobtitleid =  Util.null2String(rs.getString("dhgw")); 
				String newjoblevel =  "0"; 
				String infoman = ""; 
				String ischangesalary = "0";
				//  调动后上级
				String managerid = Util.null2String(rs.getString("ddhsj")); 
				String oldmanagerid = "";
				String olddepartmentid =  ""; 
				String depid =  Util.null2String(rs.getString("dhbm"));
				String userid =  Util.null2String(rs.getString("sqr"));
				String oldmanagerstr = "";
				String oldsubcompanyid1 = "-1";
				String oldseclevel = "";
				rs.executeSql("select subcompanyid1,departmentid,managerid,seclevel,jobtitle,"
						+"managerstr from HrmResource where id=" + emp_id);
				if(rs.next()){
					olddepartmentid = Util.null2String(rs.getString("departmentid"));
					oldmanagerid = Util.null2String(rs.getString("managerid"));
					oldsubcompanyid1 = Util.null2String(rs.getString("subcompanyid1"));
					oldjobtitleid = Util.null2String(rs.getString("jobtitle"));
					oldmanagerstr = Util.null2String(rs.getString("managerstr"));
					oldseclevel = Util.null2String(rs.getString("seclevel"));
					//managerid = oldmanagerid; 
				}
				
				String subcmpanyid1 = "-1";;
				rs.executeSql("select subcompanyid1 from HrmDepartment where id=" + depid);
				if(rs.next()){
					subcmpanyid1 = rs.getString("subcompanyid1");
				}
				
				String para = emp_id+separator+moveDate+separator+"流程申请"+separator+oldjobtitleid+separator
							+oldjoblevel+separator+newjobtitleid+separator+newjoblevel+separator+ infoman
							+separator+"4"+separator+ischangesalary+separator+oldmanagerid+separator
							+managerid+separator+olddepartmentid+separator+depid+separator+oldsubcompanyid1
							+separator+subcmpanyid1+separator+userid;

				rs.executeProc("HrmResource_Redeploy",para);
				
				Date newdate = new Date() ;
				long datetime = newdate.getTime() ;
				Timestamp timestamp = new Timestamp(datetime) ;
				String CurrentDate = (timestamp.toString()).substring(0,4) + "-" 
						+ (timestamp.toString()).substring(5,7) + "-" +(timestamp.toString()).substring(8,10);
				
				// 本次的信息调动处理
				if(Util.dayDiff(CurrentDate,moveDate)<=1){
	               // depid = Util.getIntValue(JobTitlesComInfo.getDepartmentid(newjobtitleid),0);
	            //    String sql = "" ;
	               /* String sql = "select id from HrmCostcenter where departmentid = "+depid+" order by id";
	                rs.executeSql(sql);
	                rs.next();
	                int  costcenterid = Util.getIntValue(rs.getString("id"),0);*/
	                int  costcenterid = 0;
					RecordSetTrans rst=new RecordSetTrans();
					rst.setAutoCommit(false);
					String managerstr = oldmanagerstr;
					RecordSet rs1= new RecordSet();
					try{
						para = emp_id+separator+""+depid+separator+""+newjoblevel+separator+""
								+costcenterid+separator+""+newjobtitleid+separator+""+managerid;
						rst.executeProc("HrmResource_DepUpdate",para);
					//	subcmpanyid1 = dci.getSubcompanyid1(""+depid);
						para = emp_id+separator+subcmpanyid1;
						rst.executeProc("HrmResource_UpdateSubCom",para);

						String p_para = emp_id + separator + depid + separator + subcmpanyid1 + separator 
								+ managerid + separator + oldseclevel + separator + managerstr + separator 
								+ olddepartmentid + separator + oldsubcompanyid1 + separator + oldmanagerid 
								+ separator + oldseclevel + separator + oldmanagerstr + separator + "1";

						//System.out.println(p_para);
						rst.executeProc("HrmResourceShare", p_para);
						  
						sql = "select max(id) from HrmStatusHistory";
						rst.executeSql(sql);
						rst.next();
						sql = "update HrmStatusHistory set isdispose = 1 where id="+rst.getInt(1);
					
						rst.executeSql(sql);

						rst.commit();
						String sql1="update hrmresource set managerid='"+managerid+"' where id="+emp_id;
						
					
						rs1.executeSql(sql1);
						
						OrganisationCom OrganisationCom = new OrganisationCom();
						OrganisationCom.editUser(Integer.parseInt(emp_id));//编辑
						
						ResourceComInfo ResourceComInfo = new ResourceComInfo();
						ResourceComInfo.addResourceInfoCache(emp_id);
					}catch(Exception e){
						rst.rollback();
						e.printStackTrace();
					}
	            }
				
			} 
		
		} else {
			new BaseBean().writeLog("流程取表信息错误");
			return "-1";
		}
			
		return SUCCESS;
	}
	
	private String getID(String id){
		String code = "";
		if("0".equals(id)) code = "1001";
		if("1".equals(id)) code = "1002";
		return code;
	}
	
	private String getTypeID(String id){
		String code = "";
		if("0".equals(id)) code = "1003";
		if("1".equals(id)) code = "1001";
		return code;
	}
}
