package seahonor.loadJob;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

public class Remind01InfoLoadJob extends BaseCronJob {

	// 每天凌晨2点执行 循环提醒信息
	private int normalHour = 2;
	private int normalMinute = 0;
	
	// 每周一
	private int normalWeek = 2 ;
	
	@Override
	public void execute() {
		RecordSet rs = new RecordSet();
		RecordSet rs_1 = new RecordSet();
		BaseBean log = new BaseBean();
		log.writeLog("Remind01InfoLoadJob ... ");
		
		String uq = "";
		String sql = "select NEWID() as uq";
		rs.executeSql(sql);
		if(rs.next()){
			uq = Util.null2String(rs.getString("uq"));
		}
		
		sql = "select u.id,startDate,startTime,endDate,endTime,remindEmp,Application,remind," 
			+" num1,DW from uf_remind01_dt1 u1 join uf_remind01 u on u1.mainid=u.id "
			+" where startDate+' ' +startDate+':00'<=CONVERT(varchar(100), GETDATE(), 20)";
		rs.executeSql(sql);
		log.writeLog("sql = " + sql);
		while(rs.next()){
			//  检查本次是否 已经提醒 @ 通过id 和 uq认证   表： tmc_is_remind  id,uq
			String tmp_id = Util.null2String(rs.getString("id"));
			sql  = "select count(id) as ct from tmc_is_remind where id="+ tmp_id + " and uq='"+uq+"'";
			rs_1.executeSql(sql);
			int is_go_on = 0;
			if(rs_1.next()){
				is_go_on = rs_1.getInt("ct");
			}
			if(is_go_on > 0 ) continue;
			
			// 提醒时间  0:准时  1:十分钟前  2:30分钟之前   3:1小时前  4:1天前  5:自定义
			String tmp_remind = Util.null2String(rs.getString("remind"));
			// 自定义数量
			String tmp_num = Util.null2String(rs.getString("num1"));
			// 单位 0:分钟前 1:小时前 2:天前 3:周前
			String tmp_dw = Util.null2String(rs.getString("DW"));
			String tmp_start_1 = Util.null2String(rs.getString("startDate"));
			String tmp_start_2 = Util.null2String(rs.getString("startTime"));
			boolean isRun = false;
			if(tmp_start_1.length() > 0 && tmp_start_2.length() > 0){
				String startTime = tmp_start_1 + " " + tmp_start_2;
				isRun = isNowRun(startTime,tmp_remind,tmp_num,tmp_dw);
			}
			if(isRun){
				sql = "insert into tmc_is_remind(id,uq) values("+tmp_id+",'"+uq+"')";
				rs_1.executeSql(sql);
				log.writeLog("sql = " + sql);
				String tmp_creater = Util.null2String(rs.getString("Application"));
				String tmp_title = Util.null2String(rs.getString("titleName"));
				String tmp_remarks = Util.null2String(rs.getString("remark"));
				String tmp_titleUrl = "";
				String empids = Util.null2String(rs.getString("remindEmp"));
				
				StringBuffer buff = new StringBuffer();
				buff.append("insert into uf_remindRecordDetail(remindID,creater,created_time,reDate,reTime,");
				buff.append("waySys,remindEmp,title,titleUrl,remarks,FtriggerFlag)");
				buff.append("select ");buff.append(tmp_id);buff.append(",");
				buff.append(tmp_creater);buff.append(",CONVERT(varchar(100),GETDATE(),21),");
				buff.append("CONVERT(varchar(10), GETDATE(), 23)");buff.append(",");
				buff.append("CONVERT(varchar(8), GETDATE(), 24)");buff.append(",'0',id,'");
				buff.append(tmp_title);buff.append("','");buff.append(tmp_titleUrl);buff.append("','");
				buff.append(tmp_remarks);buff.append("',0 from HrmResource where id in(");
				buff.append(empids);
				buff.append(" ) and id not in(select remindid from uf_remindFilter where is_active=0) ");
				log.writeLog("sql = " + buff.toString());
				rs_1.executeSql(buff.toString());
				
			}
		}
		
		sql = "select u.id,u2.id as aid,titleName,remark,startDate,startTime,endDate,endTime,remindEmp,"
			+" Application,same,more,date1,samed,u2.month as month_u2,u2.week sa week_u2,"
			+"u2.day1 as day1_u2,modedatacreatedate "
			+"from uf_remind01_dt2 u2 join uf_remind01 u on u2.mainid=u.id "
			+" where endDate+' ' +endTime+':00'>=CONVERT(varchar(100), GETDATE(), 20)";
		rs.executeSql(sql);
		log.writeLog("sql = " + sql);
		while(rs.next()){
			//  检查本次是否 已经提醒 @ 通过id 和 uq认证   表： tmc_is_remind  id,uq
			String tmp_id = Util.null2String(rs.getString("id"));
			String tmp_id_1 = Util.null2String(rs.getString("aid"));
			sql  = "select count(id) as ct from tmc_is_remind where id="+ tmp_id + " and uq='"+uq+"'";
			log.writeLog("sql = " + sql);
			rs_1.executeSql(sql);
			int is_go_on = 0;
			if(rs_1.next()){
				is_go_on = rs_1.getInt("ct");
			}
			if(is_go_on > 0 ) continue;
			
			// 发起日期
			String now_datey = Util.null2String(rs.getString("modedatacreatedate"));
			// 提醒时间  0:每日  1:每周  2:每月 3:每年 4:自定义
			String tmp_same = Util.null2String(rs.getString("same"));
			// 自定义数量  0:一直 1:直至
			String tmp_more = Util.null2String(rs.getString("more"));
			// 日期
			String tmp_date1 = Util.null2String(rs.getString("date1"));
			// 重复
			String tmp_sameD = Util.null2String(rs.getString("sameD"));
			// 月份
			String tmp_month = Util.null2String(rs.getString("month_u2"));
			// 哪天
			String tmp_day1 = Util.null2String(rs.getString("day1_u2"));
			// 星期
			String tmp_week = Util.null2String(rs.getString("week_u2"));
			
			boolean isRun = false;
			if(tmp_same.length() > 0 && tmp_more.length() > 0){
				String s_tmp_id = tmp_id + "-" + tmp_id_1;
				isRun = isNowRunGi(now_datey,s_tmp_id,tmp_same,tmp_more,tmp_date1,tmp_sameD,tmp_month,tmp_week,tmp_day1);
				
				if(isRun){
					sql = "insert into tmc_is_remind(id,uq) values("+tmp_id+",'"+uq+"')";
					rs_1.executeSql(sql);
					log.writeLog("sql = " + sql);
					String tmp_creater = Util.null2String(rs.getString("Application"));
					String tmp_title = Util.null2String(rs.getString("titleName"));
					String tmp_remarks = Util.null2String(rs.getString("remark"));
					String tmp_titleUrl = "";
					String empids = Util.null2String(rs.getString("remindEmp"));
					
					StringBuffer buff = new StringBuffer();
					buff.append("insert into uf_remindRecordDetail(remindID,creater,created_time,reDate,reTime,");
					buff.append("waySys,remindEmp,title,titleUrl,remarks,FtriggerFlag,uqid)");
					buff.append("select ");buff.append(tmp_id);buff.append(",");
					buff.append(tmp_creater);buff.append(",CONVERT(varchar(100),GETDATE(),21),");
					buff.append("CONVERT(varchar(10), GETDATE(), 23)");buff.append(",");
					buff.append("CONVERT(varchar(8), GETDATE(), 24)");buff.append(",'0',id,'");
					buff.append(tmp_title);buff.append("','");buff.append(tmp_titleUrl);buff.append("','");
					buff.append(tmp_remarks);buff.append("',0,'");buff.append(s_tmp_id);
					buff.append("' from HrmResource where id in(");
					buff.append(empids);
					buff.append(" ) and id not in(select remindid from uf_remindFilter where is_active=0) ");
					log.writeLog("sql = " + buff.toString());
					rs_1.executeSql(buff.toString());
					
				}
			}
		}
	}
	
	public boolean isNowRunGi(String now_date,String s_id,String same,String more,String date1,
			String sameD,String remindMonth,String remindWeek,String remindDay) {
		int index1 = Util.getIntValue(same, -1);
		int index2 = Util.getIntValue(more, -1);
		
		if(index1 < 0 || index2 < 0){
			return false;
		}
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR);
		int minute = cal.get(Calendar.MINUTE);
		
		// 每天
		if(index1 == 0){
			if(hour==normalHour && minute == normalMinute){
				int index3 = Util.getIntValue(sameD, -1);
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date now = new Date();
				boolean isG = false;
				if(date1.length() == 10){
					try {
						Date datex = format.parse(date1);
						if(now.getTime() - datex.getTime()<=0){
							isG = true;
						}
					} catch (ParseException e) {
					}
				}else{
					isG = true;
				}
				
				if(index3 > 0){
					RecordSet rs = new RecordSet();
					String sql = "select count(id) as ct from uf_remindRecordDetail where uqid='"+s_id+"'";
					rs.executeSql(sql);
					int index4 = 0;
					if(rs.next()){
						index4 = rs.getInt("ct");
					}
					if(index3 > index4 && isG) return true;
					
				}else{
					return isG;
				}
			}

		}else if(index1 == 1){
			// 每周
			// remindWeek
			// 申请日期
			SimpleDateFormat format_app = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal_app = Calendar.getInstance();
			try {
				cal.setTime(format_app.parse(now_date));
			} catch (ParseException e) {
			}
			
			int week = cal.get(Calendar.DAY_OF_WEEK);
			int week_app = cal_app.get(Calendar.DAY_OF_WEEK);
			
			boolean isWeekRun = false;
			if(remindWeek.length() > 0){
				// 选择了 星期
				String str = "";
				if(week > 1 ) str = String.valueOf(week+1);
				else str="1";
				
				if(remindWeek.contains(str)) isWeekRun = true;
				
			}else{
				if(week == week_app) isWeekRun = true;
			}
			
			
			
			if(hour==normalHour && minute == normalMinute && isWeekRun){
				int index3 = Util.getIntValue(sameD, -1);
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date now = new Date();
				boolean isG = false;
				if(date1.length() == 10){
					try {
						Date datex = format.parse(date1);
						if(now.getTime() - datex.getTime()<=0){
							isG = true;
						}
					} catch (ParseException e) {
					}
				}else{
					isG = true;
				}
				
				if(index3 > 0){
					RecordSet rs = new RecordSet();
					String sql = "select count(id) as ct from uf_remindRecordDetail where uqid='"+s_id+"'";
					rs.executeSql(sql);
					int index4 = 0;
					if(rs.next()){
						index4 = rs.getInt("ct");
					}
					if(index3 > index4 && isG) return true;
					
				}else{
					return isG;
				}
			}
		}else if(index1 == 2){
			// 每月
			int month = cal.get(Calendar.MONTH);
			int week = cal.get(Calendar.DAY_OF_WEEK);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			
			
			boolean isDayRun = false;
			boolean isWeekRun = false;
			boolean isMonthRun = false;
			
			if(remindDay.length() > 0){
				String str = String.valueOf(day);
				if(str.equalsIgnoreCase(remindDay)) isDayRun = true;
			}
			if(remindWeek.length() > 0 ){
				String str = "";
				if(week > 1 ) str = String.valueOf(week+1);
				else str="1";
				
				if(remindWeek.contains(str)) isWeekRun = true;
			}
			if(remindMonth.length() > 0){
				String str = "";
				str = ","+String.valueOf(month+1)+",";
				if((","+remindMonth+",").contains(str)) isMonthRun = true;
			}
			if(remindDay.length() < 1 && remindWeek.length() < 1&&remindMonth.length() <1){
				if(day == 1){
					isDayRun = true;
				}
			}
			
			boolean isRun = isDayRun||isWeekRun||isMonthRun;
			
			if(hour==normalHour && minute == normalMinute && isRun){
				int index3 = Util.getIntValue(sameD, -1);
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date now = new Date();
				boolean isG = false;
				if(date1.length() == 10){
					try {
						Date datex = format.parse(date1);
						if(now.getTime() - datex.getTime()<=0){
							isG = true;
						}
					} catch (ParseException e) {
					}
				}else{
					isG = true;
				}
				
				if(index3 > 0){
					RecordSet rs = new RecordSet();
					String sql = "select count(id) as ct from uf_remindRecordDetail where uqid='"+s_id+"'";
					rs.executeSql(sql);
					int index4 = 0;
					if(rs.next()){
						index4 = rs.getInt("ct");
					}
					if(index3 > index4 && isG) return true;
					
				}else{
					return isG;
				}
			}
		}else if(index1 == 4){
			// 自定义
			int month = cal.get(Calendar.MONTH);
			int week = cal.get(Calendar.DAY_OF_WEEK);
			int day = cal.get(Calendar.DAY_OF_MONTH);
						
			boolean isDayRun = false;
			boolean isWeekRun = false;
			boolean isMonthRun = false;
						
			if(remindDay.length() > 0){
				String str = String.valueOf(day);
				if(str.equalsIgnoreCase(remindDay)) isDayRun = true;
			}
			if(remindWeek.length() > 0 ){
				String str = "";
				if(week > 1 ) str = String.valueOf(week+1);
				else str="1";
							
				if(remindWeek.contains(str)) isWeekRun = true;
			}
			if(remindMonth.length() > 0){
				String str = "";
				str = ","+String.valueOf(month+1)+",";
				if((","+remindMonth+",").contains(str)) isMonthRun = true;
			}
			if(remindDay.length() < 1 && remindWeek.length() < 1&&remindMonth.length() <1){
				if(day == 1){
					isDayRun = true;
				}
			}
						
			boolean isRun = isDayRun||isWeekRun||isMonthRun;

			if(hour==normalHour && minute == normalMinute && week == normalWeek){
				int index3 = Util.getIntValue(sameD, -1);
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date now = new Date();
				boolean isG = false;
				if(date1.length() == 10){
					try {
						Date datex = format.parse(date1);
						if(now.getTime() - datex.getTime()<=0){
							isG = true;
						}
					} catch (ParseException e) {
					}
				}else{
					isG = true;
				}
				
				if(index3 > 0){
					RecordSet rs = new RecordSet();
					String sql = "select count(id) as ct from uf_remindRecordDetail where uqid='"+s_id+"'";
					rs.executeSql(sql);
					int index4 = 0;
					if(rs.next()){
						index4 = rs.getInt("ct");
					}
					if(index3 > index4 && isG) return true;
					
				}else{
					return isG;
				}
			}
		}
		return false;
	}

	public boolean isNowRun(String startTime,String remind,String num,String dw){
		int index = Util.getIntValue(remind, -1);
		if(index < 0)
			return false;
		int minute = 0;
		if(index == 0){
			minute = 0;
		}else if(index == 1){
			minute = 10;
		}else if(index == 2){
			minute = 30;
		}else if(index == 3){
			minute = 60;
		}else if(index == 4){
			minute = 1440;
		}else if(index == 5){
			int index_num = Util.getIntValue(num, -1);
			int index_dw = Util.getIntValue(dw, -1);
			if(index_num < 0 || index_dw < 0){
				return false;
			}
			if(index_dw == 0){
				minute = index_num;
			}else if(index_dw == 1){
				minute = 60 * index_num;
			}else if(index_dw == 2){
				minute = 1440 * index_num;
			}else if(index_dw == 3){
				minute = 7 * 1440 * index_num;
			}else{
				return false;
			}
			
		}else{
			return false;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date date = new Date();
		Date date1 = null;
		try {
			date1 = format.parse(startTime);
			
			long index1 = date1.getTime() - date.getTime();
			
			if(index1 > 0){
				long tmp_index = index1/(1000*60) - minute;
				if(tmp_index < 2&&tmp_index>=0){
					return true;
				}	
			}	
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		
		return false;
	}

}
