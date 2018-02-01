package gvo.hrImport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
import weaver.general.Util;

public class UtilForGvo {

	public String getWorkcode(String emp_id){
		if(emp_id == null || emp_id.length() < 1)  return "";
		String code = "";
		String sql = "select workcode from HrmResource where id="+emp_id;
		RecordSet tmp_rs = new RecordSet();
		tmp_rs.executeSql(sql);
		if(tmp_rs.next()){
			code = Util.null2String(tmp_rs.getString("workcode"));
		}
		
		return code;
	}
	
	public String[] getArr(String emp_id){
		String[] bef_arr = new String[8];
		for(int index = 0; index < bef_arr.length;index++){
			bef_arr[index] = "";
		}
		List<String> list = new ArrayList<String>();
		RecordSet rs_1 = new RecordSet();
		// 公司解析  rs_1
		String sql = "select id,subcompanyname,supsubcomid from HrmSubCompany where id in(select subcompanyid1 from HrmResource where id="
				+emp_id+") ";
		rs_1.executeSql(sql);
		String tmp_id = "";
		int flag = 0;
		if(rs_1.next()){
			tmp_id = Util.null2String(rs_1.getString("supsubcomid")); 
			list.add(Util.null2String(rs_1.getString("subcompanyname")));
		}
		int flag_1 = 1;
		while(flag_1 > 0){
			flag_1 = 0;
			sql = "select supsubcomid,subcompanyname from HrmSubCompany where id = "+ tmp_id;
			rs_1.executeSql(sql);
			if(rs_1.next()){
				tmp_id = Util.null2String(rs_1.getString("supsubcomid")); 
				list.add(Util.null2String(rs_1.getString("subcompanyname")));
				flag_1++;
			}
		}
		
		for(int index=list.size()-1;index>=0;index--){
			bef_arr[flag++] = list.get(index);
		}
		
		tmp_id = "";
		list = new ArrayList<String>();
		//  部门解析
		sql = "select id,departmentname,supdepid from HrmDepartment where id in(select departmentid from HrmResource where id="+emp_id+") ";
		rs_1.executeSql(sql);
		if(rs_1.next()){
			tmp_id = Util.null2String(rs_1.getString("supdepid")); 
			list.add(Util.null2String(rs_1.getString("departmentname")));
		}
		flag_1 = 1;
		while(flag_1 > 0){
			flag_1 = 0;
			sql = "select supdepid,departmentname from HrmDepartment where id = "+ tmp_id;
			rs_1.executeSql(sql);
			if(rs_1.next()){
				tmp_id = Util.null2String(rs_1.getString("supdepid")); 
				list.add(Util.null2String(rs_1.getString("departmentname")));
				flag_1++;
			}
		}
		for(int index=list.size()-1;index>=0;index--){
			bef_arr[flag++] = list.get(index);
		}
		
		return bef_arr;
	}
	
	public String[] getArr_dept(String dept_id){
		String[] bef_arr = new String[8];
		for(int index = 0; index < bef_arr.length;index++){
			bef_arr[index] = "";
		}
		List<String> list = new ArrayList<String>();
		RecordSet rs_1 = new RecordSet();
		// 公司解析  rs_1
		String sql = "select supsubcomid,subcompanyname from HrmSubCompany where id in(select subcompanyid1 from HrmDepartment where id="
				+dept_id+") ";
		rs_1.executeSql(sql);
		String tmp_id = "";
		int flag = 0;
		if(rs_1.next()){
			tmp_id = Util.null2String(rs_1.getString("supsubcomid")); 
			list.add(Util.null2String(rs_1.getString("subcompanyname")));
		}
		int flag_1 = 1;
		while(flag_1 > 0){
			flag_1 = 0;
			sql = "select supsubcomid,subcompanyname from HrmSubCompany where id = "+ tmp_id;
			rs_1.executeSql(sql);
			
			if(rs_1.next()){
				tmp_id = Util.null2String(rs_1.getString("supsubcomid")); 
				list.add(Util.null2String(rs_1.getString("subcompanyname")));
				flag_1++;
			}
		}
		
		for(int index=list.size()-1;index>=0;index--){
			bef_arr[flag++] = list.get(index);
		}
		
		list = new ArrayList<String>();
		//  部门解析
		tmp_id = dept_id;
		flag_1 = 1;
		while(flag_1 > 0){
			flag_1 = 0;
			sql = "select supdepid,departmentname from HrmDepartment where id = "+ tmp_id;
			rs_1.executeSql(sql);
			new BaseBean().writeLog("$2 sql = " + sql);
			if(rs_1.next()){
				tmp_id = Util.null2String(rs_1.getString("supdepid")); 
				list.add(Util.null2String(rs_1.getString("departmentname")));
				flag_1++;
			}
		}
		new BaseBean().writeLog("list[dept] = " + list);
		for(int index=list.size()-1;index>=0;index--){
			bef_arr[flag++] = list.get(index);
		}
		
		return bef_arr;
	}
	
	public String getJob(String job_id){
		if(job_id == null || job_id.length() < 1)  return "";
		String code = "";
		String sql = "select jobtitlename from HrmJobTitles where id="+job_id;
		RecordSet tmp_rs = new RecordSet();
		tmp_rs.executeSql(sql);
		if(tmp_rs.next()){
			code = Util.null2String(tmp_rs.getString("jobtitlename"));
		}
		return code;
	}
	
	public boolean allApp(Map<String,String> mapStr,String table,RecordSetDataSource rsx){
		if(mapStr == null) return false;
		if(mapStr.isEmpty()) return false;
		
		String sql_0 = "insert into "+table+"(";
		StringBuffer sql_1 = new StringBuffer();
		String sql_2 = ") values(";
		StringBuffer sql_3 = new StringBuffer();
		String sql_4 = ")";
		
		Iterator<String> it = mapStr.keySet().iterator();
		String flag = "";
		while(it.hasNext()){
			String tmp_1 = it.next();
			String tmp_1_str = Util.null2String(mapStr.get(tmp_1));		
			if(tmp_1_str.length() > 0){
				sql_1.append(flag);sql_1.append(tmp_1);
				
				if(tmp_1_str.contains("##")){
					sql_3.append(flag);sql_3.append(tmp_1_str.replace("##", ""));
				}else{
					sql_3.append(flag);sql_3.append("'");sql_3.append(tmp_1_str);sql_3.append("'");
				}
			
				flag = ",";
			}
		}
		
		String now_sql_1 = sql_1.toString();

		String now_sql_3 = sql_3.toString();

		String sql = sql_0 + now_sql_1 + sql_2 + now_sql_3 + sql_4;
		
		new BaseBean().writeLog("sql = " + sql);
		return rsx.executeSql(sql);
	//	return true;
	}
	
}
