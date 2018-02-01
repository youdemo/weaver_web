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
				// ��ְ��
				String emp_id = Util.null2String(rs.getString("dzr")); 
				if("".equals(emp_id))  return "-1";
				
				// ��������
				String type = "";
				String type_1 = Util.null2String(rs.getString("pd"));    // ƽ��
				String type_2 = Util.null2String(rs.getString("js"));    //  ����
				String type_3 = Util.null2String(rs.getString("jz"));    // ��ְ
				String type_4 = Util.null2String(rs.getString("jd"));    //  ���
				if("1".equals(type_1)){
					type = "1001";
				}else if("1".equals(type_2)){
					type = "1002";
				}else if("1".equals(type_3)){
					type = "1003";
				}else if("1".equals(type_4)){
					type = "1004";
				}
				
				// ������ʽ
				String res_type = getTypeID(Util.null2String(rs.getString("sfkdw")));    
				
				// �������ԭ�� 
				String reason = Util.null2String(rs.getString("sqddyy"));   
				// ������
				String moveDate = Util.null2String(rs.getString("jyddr")); 
				
				// ����ǰ��� dqsf
				// ���������   dhsf 
				String before = Util.null2String(rs.getString("dqsf"));  
				String after = Util.null2String(rs.getString("dhsf"));  
				
				// ����ǰ��λ  dhgw 
				String befor_job = Util.null2String(rs.getString("dqgw")); 
				// �������λ  dhgw 
				String after_job = Util.null2String(rs.getString("dhgw")); 
				
				// ���������
				String after_over_end = Util.null2String(rs.getString("kcjsr")); 

				// ddqzd ����ǰְ��
				String class_before = Util.null2String(rs.getString("ddqzd"));
 
				// ddhzd  ������ְ��
				String class_after = Util.null2String(rs.getString("ddhzd")); 

				// ddqzj  ����ǰְ��
				String lv_before = Util.null2String(rs.getString("ddqzj"));
 
				// ddhzj  ������ְ��
				String lv_after = Util.null2String(rs.getString("ddhzj")); 
				
				mapStr.put("key_id",info.getRequestid());	//�ؼ���     -->  ����requestid
				mapStr.put("employee_code",ug.getWorkcode(emp_id));	//Ա������
				mapStr.put("transfer_type",type);	//��������
				mapStr.put("transfer_way",res_type);	//������ʽ
				
				//  ����ǰ (Ŀǰ�Ĳ���)
				String[] bef_arr =  ug.getArr(emp_id);
				
				mapStr.put("transfer_org1_desc_bef",bef_arr[0]);	//����ǰ��֯�ṹ1����
				mapStr.put("transfer_org2_desc_bef",bef_arr[1]);	//����ǰ��֯�ṹ2����
				mapStr.put("transfer_org3_desc_bef",bef_arr[2]);	//����ǰ��֯�ṹ3����
				mapStr.put("transfer_org4_desc_bef",bef_arr[3]);	//����ǰ��֯�ṹ4����
				mapStr.put("transfer_org5_desc_bef",bef_arr[4]);	//����ǰ��֯�ṹ5����
				mapStr.put("transfer_org6_desc_bef",bef_arr[5]);	//����ǰ��֯�ṹ6����
				mapStr.put("transfer_org7_desc_bef",bef_arr[6]);	//����ǰ��֯�ṹ7����
				mapStr.put("transfer_org8_desc_bef",bef_arr[7]);	//����ǰ��֯�ṹ8����
				
				//  ���ں� (ѡ��Ĳ���)
				String dept_id = Util.null2String(rs.getString("dhbm")); 
				String[] aft_arr = ug.getArr_dept(dept_id);
				
				mapStr.put("transfer_org1_desc_after",aft_arr[0]);	//��������֯�ṹ1����
				mapStr.put("transfer_org2_desc_after",aft_arr[1]);	//��������֯�ṹ2����
				mapStr.put("transfer_org3_desc_after",aft_arr[2]);	//��������֯�ṹ3����
				mapStr.put("transfer_org4_desc_after",aft_arr[3]);	//��������֯�ṹ4����
				mapStr.put("transfer_org5_desc_after",aft_arr[4]);	//��������֯�ṹ5����
				mapStr.put("transfer_org6_desc_after",aft_arr[5]);	//��������֯�ṹ6����
				mapStr.put("transfer_org7_desc_after",aft_arr[6]);	//��������֯�ṹ7����
				mapStr.put("transfer_org8_desc_after",aft_arr[7]);	//��������֯�ṹ8����
				
				mapStr.put("transfer_jobs_before",ug.getJob(befor_job));//����ǰ��λ����
				mapStr.put("transfer_jobs_after",ug.getJob(after_job));	//�������λ����
				
				mapStr.put("transfer_identity_before",getID(before));	//����ǰ���
				mapStr.put("transfer_identity_after",getID(after));	//���������

				mapStr.put("transfer_class_before",getID(class_before));//����ǰְλ�㼶
				mapStr.put("transfer_class_after",getID(class_after));	//������ְλ�㼶

				mapStr.put("transfer_lv_before",getID(lv_before));	//����ǰְλ�ȼ�
				mapStr.put("transfer_lv_after",getID(lv_after));	//������ְλ�ȼ�
				
				mapStr.put("transfer_date",moveDate.replace("-", "/"));	//��������
				mapStr.put("transfer_reason",reason);	//����ԭ��
				
				String dt = Util.null2String(rs.getString("jykcq2"));
				mapStr.put("Investigate_dt",dt);	    //���쿪ʼ����
				mapStr.put("actual_effect_date",after_over_end);	//�����������
				mapStr.put("create_dt","##getdate()");	//��������    -->��ǰʱ��
				
				RecordSetDataSource rsx = new RecordSetDataSource(dataid);
				ug.allApp(mapStr, "cus_Personnel_change_info", rsx);
				
				// ������Ϣ
				String oldjobtitleid =  ""; 
				String oldjoblevel =  "0"; 
				String newjobtitleid =  Util.null2String(rs.getString("dhgw")); 
				String newjoblevel =  "0"; 
				String infoman = ""; 
				String ischangesalary = "0";
				//  �������ϼ�
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
				
				String para = emp_id+separator+moveDate+separator+"��������"+separator+oldjobtitleid+separator
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
				
				// ���ε���Ϣ��������
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
						OrganisationCom.editUser(Integer.parseInt(emp_id));//�༭
						
						ResourceComInfo ResourceComInfo = new ResourceComInfo();
						ResourceComInfo.addResourceInfoCache(emp_id);
					}catch(Exception e){
						rst.rollback();
						e.printStackTrace();
					}
	            }
				
			} 
		
		} else {
			new BaseBean().writeLog("����ȡ����Ϣ����");
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
