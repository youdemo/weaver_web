package seahonor.util;

import weaver.general.Util;

public class DeptUtil {
	public String getSubDet(String dept_id){
		StringBuffer buff = new StringBuffer();
		weaver.conn.RecordSet rs = new weaver.conn.RecordSet();
		String sql = "";
		
		buff.append(dept_id);
		int count_cc = 1;
		while(count_cc > 0){
			count_cc = 0;
			sql = "select id from HrmDepartment where supdepid in("+buff.toString()+") "
			+"and ISNULL(canceled,0)=0 order by showorder";
			rs.executeSql(sql);
			while(rs.next()){
				String tmp_id = Util.null2String(rs.getString("id"));
				if(tmp_id.length() > 0 && !(","+buff.toString()+",").contains(","+tmp_id+",")){
					buff.append(",");buff.append(tmp_id);
					count_cc++;
				}
			}
		}
		return buff.toString();
	}
	
	public String getAllSub(String emp_id){
		StringBuffer buff = new StringBuffer();
		StringBuffer old_buff = new StringBuffer();
		old_buff.append(emp_id);
		String split = "";
		weaver.conn.RecordSet rs = new weaver.conn.RecordSet();
		//RecordSet rs = new RecordSet();
		int count_cc = 1;
		while(count_cc>0){
			count_cc = 0;
			String sql = "select id from HrmResource where status in(0,1,2,3) and  managerid in("
					+old_buff.toString()+")";
			buff.append(split);buff.append(old_buff.toString());split=",";
		//	System.out.println("sql = " + sql);
			old_buff = new StringBuffer();
			String tmp_split = "";
			rs.execute(sql);
			while(rs.next()){
				String tmp_id = Util.null2String(rs.getString("ID"));
				if(tmp_id.length() > 0 && !(","+buff.toString()+",").contains(","+tmp_id+",")){
					old_buff.append(tmp_split);old_buff.append(tmp_id);tmp_split = ",";
					count_cc++;
				}
			}
		}
		buff.append(old_buff.toString());
		return buff.toString();
	}

}
