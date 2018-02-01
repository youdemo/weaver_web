package gvo.mobile;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

public class LoginExceptionLoadJob extends BaseCronJob {
	
	
	public void execute() {
		SendResult sr = new SendResult();
		BaseBean log = new BaseBean();
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String sendMessage = "";
		log.writeLog("LoginExceptionLoadJob Job Start! "); 
		String sql_1 = "select * from formtable_main_328 where uq='MobileLogEx'";
		rs.executeSql(sql_1);
		if(rs.next()){
			sendMessage = Util.null2String(rs.getString("remark"));
			sendMessage = sendMessage.replace("<br>", " ").replace("&nbsp;", "");
		}
		
		String sql = " select si.id as fid,si.clientaddress,hr.id as empID,hr.lastname,hr.mobile,"
					+" operatedate||' '||operatetime as time1 from sysmaintenancelog si "
					+" join hrmresource hr on(si.operateuserid=hr.id) "
					+" where operatetype=6 and clientaddress!='mobile' AND si.ID NOT IN "
					+"(SELECT CHECKID FROM gvo_check_data_info WHERE TYPECHECK='LG')order by si.id";
		rs.executeSql(sql);
		while(rs.next()){
			String uid = Util.null2String(rs.getString("fid"));
			
			sql = "insert into gvo_check_data_info(checkid,typecheck) values("+uid+",'LG')";
			rs1.executeSql(sql);
			
			String clientaddress = Util.null2String(rs.getString("clientaddress"));
			
			// ip 判断  172.16.*  ； 106.120.97.22 ； 58.210.121.68； 10.80.*  222.92.108.194	58.240.226.123
			
			String empID = Util.null2String(rs.getString("empID"));
			String lastname = Util.null2String(rs.getString("lastname"));
			String mobile = Util.null2String(rs.getString("mobile"));
			String time1 = Util.null2String(rs.getString("time1"));
			
			if(!"吴春艳".equals(lastname)&&!"李争".equals(lastname)&&!"张盼盼".equals(lastname)){
				continue;
			}
			
			
			String tmp_sql = "select clientaddress,operatedate||' '||operatetime as time2 "
					+" from sysmaintenancelog where id in(select max(id) from sysmaintenancelog "
					+"where operatetype=6 and clientaddress!='mobile' "
					+"AND operateuserid="+empID+" AND ID < "+uid+")";
			rs1.executeSql(tmp_sql);
			String loginTime = "";
			String lastMax_address = "";
			if(rs1.next()){
				lastMax_address = Util.null2String(rs1.getString("clientaddress"));
				loginTime =  Util.null2String(rs1.getString("time2"));
			}
			if(!"".equals(lastMax_address)){
				//  和上次信息登录的IP不一致。
				if(!clientaddress.equalsIgnoreCase(lastMax_address)){
					//  如果当前时间是00:00　~ 07:00   延迟发端  统一到早上07:00 以后发
					if(true){
						// 拿本次登录IP 和  时间
						String tmp_mess = sendMessage.replace("#IP#", lastMax_address)
									.replace("#loginTime#", loginTime);
						
						sql  = "insert into gvo_send_message_info(id,phoneno,sendmessage,whosend,systmwho,"
							  +"createtime,sendtime,fromwhere,active_flag,LastloginTime,loginTime) "
							  +"values(gvo_send_message_info_seqno.nextval,'"+mobile+"','"
							  +tmp_mess+"','"+lastname+"',"+empID+",to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),"
							  +"to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),'LG',2,'"
							  +loginTime+"','"+time1+"')";
						rs1.executeSql(sql);
						log.writeLog("sql = " + sql);
						
						sr.sendSMS("", mobile, tmp_mess);
					}
				}
			}
		}
	}
}
