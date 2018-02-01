package seahonor.loadJob;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

public class RemindInfoLoadJob extends BaseCronJob {

	// 每天凌晨2点执行 循环提醒信息
	private int normalHour = 2;
	private int normalMinute = 0;
	
	@Override
	public void execute() {
		RecordSet rs = new RecordSet();
		RecordSet rs_1 = new RecordSet();
		BaseBean log = new BaseBean();
		log.writeLog("RemindInfoLoadJob ... ");

	//	System.out.println("RemindInfoLoadJob ... ");
		// 更新掉 已经过滤中已经关闭的流程
		String sql = "exec tmc_remind_close_from_filter ";
		rs.executeSql(sql);

		// 一次提醒
		sql = "exec tmc_select_remind_now @remindHZ=1";
		rs.executeSql(sql);
		while (rs.next()) {
			String tmp_id = Util.null2String(rs.getString("id"));
			String tmp_title = Util.null2String(rs.getString("title"));
			
			// <a href='javascript:openNewInfo(1);'>test</a>
			tmp_title = "<a href='javascript:openNewInfo(1);'>" + tmp_title +"</a>";
			
			tmp_title = tmp_title.replace("'", "''");
			String tmp_titleUrl = Util.null2String(rs.getString("titleUrl"));
			tmp_titleUrl = tmp_titleUrl.replace("'", "''");
			String tmp_areaType = Util.null2String(rs.getString("areaType"));
			String tmp_areaVal = Util.null2String(rs.getString("areaVal"));
			String tmp_level = Util.null2String(rs.getString("level"));
			String tmp_LeadType = Util.null2String(rs.getString("LeadType"));
			String tmp_creater = Util.null2String(rs.getString("creater"));
			String tmp_reDate = Util.null2String(rs.getString("reDate"));
			String tmp_reTime = Util.null2String(rs.getString("reTime"));
			String tmp_remarks = Util.null2String(rs.getString("remarks"));
			tmp_remarks = tmp_remarks.replace("'", "''");
			// 查询需要提醒的人
			String empids = getEmps(tmp_areaType, tmp_areaVal, tmp_level,tmp_LeadType);

			// System.out.println("empids = " + empids +
			// "&tmp_areaType="+tmp_areaType
			// +"&tmp_areaVal="+tmp_areaVal+"&tmp_level="+tmp_level);

			// 插入提醒信息表
			StringBuffer buff = new StringBuffer();
			buff.append("insert into uf_remindRecordDetail(remindID,creater,created_time,reDate,reTime,");
			buff.append("waySys,remindEmp,title,titleUrl,remarks)");
			buff.append("select ");buff.append(tmp_id);buff.append(",");
			buff.append(tmp_creater);buff.append(",CONVERT(varchar(100),GETDATE(),21),'");
			buff.append(tmp_reDate);buff.append("','");buff.append(tmp_reTime);buff.append("','0',id,'");
			buff.append(tmp_title);buff.append("','");buff.append(tmp_titleUrl);buff.append("','");
			buff.append(tmp_remarks);buff.append("' from HrmResource where id in(");
			buff.append(empids);
			buff.append(" ) and id not in(select remindid from uf_remindFilter where is_active=0) ");
			
			log.writeLog("@71@ = " + buff.toString());
			rs_1.executeSql(buff.toString());

			// 更新掉提醒记录标示已经提醒
			String updt = "update uf_remindRecord set over_active=9 where id=" + tmp_id;
			log.writeLog("@76@ = " + updt);
			rs_1.executeSql(updt);
		}

		// 处理到期加循环提醒
		sql = "exec tmc_select_remind_now @remindHZ=2";
		rs.executeSql(sql);
		while (rs.next()) {
			String tmp_id = Util.null2String(rs.getString("id"));
			String tmp_title = Util.null2String(rs.getString("title"));
			String tmp_titleUrl = Util.null2String(rs.getString("titleUrl"));
			String tmp_areaType = Util.null2String(rs.getString("areaType"));
			String tmp_areaVal = Util.null2String(rs.getString("areaVal"));
			String tmp_level = Util.null2String(rs.getString("level"));
			String tmp_LeadType = Util.null2String(rs.getString("LeadType"));
			String tmp_creater = Util.null2String(rs.getString("creater"));
			String tmp_reDate = Util.null2String(rs.getString("reDate"));
			String tmp_reTime = Util.null2String(rs.getString("reTime"));
			String tmp_remarks = Util.null2String(rs.getString("remarks"));
			
			//String tmp_remindHz = Util.null2String(rs.getString("remindHz"));
			// f.triggertype,f.triggercycletime,f.weeks,f.months,f.days
			String tmp_triggertype = Util.null2String(rs.getString("triggertype"));
			String tmp_triggercycletime = Util.null2String(rs.getString("triggercycletime"));
			String tmp_weeks = Util.null2String(rs.getString("weeks"));
			String tmp_months = Util.null2String(rs.getString("months"));
			String tmp_days = Util.null2String(rs.getString("days"));
			//  常量/ 字段
			String tmp_remindtimetype = Util.null2String(rs.getString("remindtimetype"));
			
			// 条件停止
			String tmp_stopID = Util.null2String(rs.getString("stopID"));
			String tmp_stopUqID = Util.null2String(rs.getString("stopUqID"));
			String tmp_selStopInfo = Util.null2String(rs.getString("selStopInfo"));	
			// 不为字段值时      常量值
			if(!"2".equals(tmp_remindtimetype)){
				boolean isGoon = isGoonRemind(tmp_stopID,tmp_stopUqID);
			//	System.out.println("tmp_id = " + tmp_id + " & isGoon = " + isGoon);
				// 条件中止
				if(!isGoon){
					// 更新掉提醒记录标示已经提醒
					String updt = "update uf_remindRecord set over_active=9 where id=" + tmp_id;
					log.writeLog("@118@ = " + updt);
					rs_1.executeSql(updt);
					continue;
				}
				
				boolean isTrigger = isNowTrigger(tmp_reDate,tmp_reTime,tmp_triggertype,tmp_triggercycletime,
						tmp_weeks,tmp_months,tmp_days);
				
				if(isTrigger){
					// 查询需要提醒的人
					String empids = getEmps(tmp_areaType, tmp_areaVal, tmp_level,tmp_LeadType);
					// 插入提醒信息表
					StringBuffer buff = new StringBuffer();
					buff.append("insert into uf_remindRecordDetail(remindID,creater,created_time,reDate,reTime,");
					buff.append("waySys,remindEmp,title,titleUrl,remarks)");
					buff.append("select ");buff.append(tmp_id);buff.append(",");
					buff.append(tmp_creater);buff.append(",CONVERT(varchar(100),GETDATE(),21),'");
					buff.append(tmp_reDate);buff.append("','");buff.append(tmp_reTime);buff.append("','0',id,'");
					buff.append(tmp_title);buff.append("','");buff.append(tmp_titleUrl);buff.append("','");
					buff.append(tmp_remarks);buff.append("' from HrmResource where id in(");
					buff.append(empids);
					buff.append(" ) and id not in(select remindEmp from uf_remindFilter where is_active=0) ");
					
					log.writeLog("@141@ = " + buff.toString());
					rs_1.executeSql(buff.toString());
				}
				
				if("6".equals(tmp_triggertype)){
					// 更新掉提醒记录标示已经提醒
					String updt = "update uf_remindRecord set over_active=9 where id=" + tmp_id;
					log.writeLog("@148@ = " + updt);
					rs_1.executeSql(updt);
				}
			}else{
				// 字段属性
				
				// 日期字段
				String tmp_dateField = Util.null2String(rs.getString("dateField"));
				// 时间字段
				String tmp_timeField = Util.null2String(rs.getString("timeField"));
				// 表名
				String tmp_tableName = Util.null2String(rs.getString("tableName"));
				// 条件
				String tmp_infowhere = Util.null2String(rs.getString("infowhere"));
				
				if("1".equals(tmp_selStopInfo)){
					boolean isGoon = isGoonRemind(tmp_stopID,tmp_stopUqID);
					//	System.out.println("tmp_id = " + tmp_id + " & isGoon = " + isGoon);
						// 条件中止
					if(!isGoon){
						// 更新掉提醒记录标示已经提醒
						String updt = "update uf_remindRecord set over_active=9 where id=" + tmp_id;
						log.writeLog("@170@ = " + updt);
						rs_1.executeSql(updt);
						continue;
					}
				}
				
				String tmp_incrementnum = Util.null2String(rs.getString("incrementnum")).replace(".00", "");
				String tmp_incrementunit = Util.null2String(rs.getString("incrementunit"));
				String tmp_incrementway = Util.null2String(rs.getString("incrementway"));
				// 遍历sql     常量过滤器的无数据
				String x_sql = "select 1,";
				if(!"".equals(tmp_dateField)){
					x_sql = x_sql + tmp_dateField + " as tmpDate,";
				}
				if(!"".equals(tmp_timeField)){
					x_sql = x_sql + tmp_timeField + " as tmpTime,";
				}
				if(!"".equals(tmp_stopUqID)&&!"1".equals(tmp_selStopInfo)){
					x_sql = x_sql + tmp_stopUqID + " as tmpID,";
				}
				x_sql = x_sql + "2 from " + tmp_tableName + " "  + tmp_infowhere;
				log.writeLog("@191@ = " + x_sql);
				rs_1.executeSql(x_sql);
				while(rs_1.next()){
					String tmp_date = Util.null2String(rs_1.getString("tmpDate"));
					if("".equals(tmp_date)) continue;
					String tmp_time = Util.null2String(rs_1.getString("tmpTime"));
					if("".equals(tmp_time)){
						tmp_time = "00:00:00";
					}
					
					if(tmp_time.length() == 5) tmp_time = tmp_time + ":00";
					if(!"1".equals(tmp_selStopInfo)){
						String tmp_stopid = Util.null2String(rs_1.getString("tmpID"));
						boolean isGoon = isGoonRemind(tmp_stopID,tmp_stopid);
						if(!isGoon) continue;
					}
					
					String tmp_arr[] = getRes(tmp_date,tmp_time,tmp_incrementnum,tmp_incrementunit,tmp_incrementway);
					
					if(tmp_arr == null || tmp_arr.length !=2 ) continue;
					String tmp_x_reDate = tmp_arr[0];
					String tmp_x_reTime = tmp_arr[0];
					
					boolean isTrigger_dt = isNowTrigger(tmp_x_reDate,tmp_x_reTime,tmp_triggertype,tmp_triggercycletime,
							tmp_weeks,tmp_months,tmp_days);
					if(isTrigger_dt){
						// 查询需要提醒的人
						String empids = getEmps(tmp_areaType, tmp_areaVal, tmp_level,tmp_LeadType);
						// 插入提醒信息表
						StringBuffer buff = new StringBuffer();
						buff.append("insert into uf_remindRecordDetail(remindID,creater,created_time,reDate,reTime,");
						buff.append("waySys,remindEmp,title,titleUrl,remarks)");
						buff.append("select ");buff.append(tmp_id);buff.append(",");
						buff.append(tmp_creater);buff.append(",CONVERT(varchar(100),GETDATE(),21),'");
						buff.append(tmp_x_reDate);buff.append("','");buff.append(tmp_x_reDate);buff.append("','0',id,'");
						buff.append(tmp_title);buff.append("','");buff.append(tmp_titleUrl);buff.append("','");
						buff.append(tmp_remarks);buff.append("' from HrmResource where id in(");
						buff.append(empids);
						buff.append(" ) and id not in(select remindEmp from uf_remindFilter where is_active=0) ");
						
						log.writeLog("@231@ = " + buff.toString());
						rs_1.executeSql(buff.toString());
					}
					
				}
			}
			
			
		}
		/*
		// 处理循环提醒
		sql = "exec tmc_select_remind_now @remindHZ=3";
		rs.executeSql(sql);
		while (rs.next()) {
			String tmp_id = Util.null2String(rs.getString("id"));
			String tmp_title = Util.null2String(rs.getString("title"));
			String tmp_titleUrl = Util.null2String(rs.getString("titleUrl"));
			String tmp_areaType = Util.null2String(rs.getString("areaType"));
			String tmp_areaVal = Util.null2String(rs.getString("areaVal"));
			String tmp_level = Util.null2String(rs.getString("level"));
			String tmp_creater = Util.null2String(rs.getString("creater"));
			String tmp_reDate = Util.null2String(rs.getString("reDate"));
			String tmp_reTime = Util.null2String(rs.getString("reTime"));
			String tmp_remarks = Util.null2String(rs.getString("remarks"));

			String tmp_remindHz = Util.null2String(rs.getString("remindHz"));
			// f.triggertype,f.triggercycletime,f.weeks,f.months,f.days
			String tmp_triggertype = Util.null2String(rs.getString("triggertype"));
			String tmp_triggercycletime = Util.null2String(rs.getString("triggercycletime"));
			String tmp_weeks = Util.null2String(rs.getString("weeks"));
			String tmp_months = Util.null2String(rs.getString("months"));
			String tmp_days = Util.null2String(rs.getString("days"));

			boolean isTrigger = isNowTrigger(tmp_reDate,tmp_reTime,tmp_triggertype,tmp_triggercycletime,
					tmp_weeks,tmp_months,tmp_days);
			
		//	System.out.println("tmp_id = " + tmp_id + " & isTrigger = " + isTrigger);
			if(isTrigger){
				// 查询需要提醒的人
				String empids = getEmps(tmp_areaType, tmp_areaVal, tmp_level);
				// 插入提醒信息表
				StringBuffer buff = new StringBuffer();
				buff.append("insert into uf_remindRecordDetail(remindID,creater,created_time,reDate,reTime,");
				buff.append("waySys,remindEmp,title,titleUrl,remarks)");
				buff.append("select ");buff.append(tmp_id);buff.append(",");
				buff.append(tmp_creater);buff.append(",CONVERT(varchar(100),GETDATE(),21),'");
				buff.append(tmp_reDate);buff.append("','");buff.append(tmp_reTime);buff.append("','0',id,'");
				buff.append(tmp_title);buff.append("','");buff.append(tmp_titleUrl);buff.append("','");
				buff.append(tmp_remarks);buff.append("' from HrmResource where id in(");
				buff.append(empids);
				buff.append(" ) and id not in(select remindEmp from uf_remindFilter where is_active=0) ");
				
				rs_1.executeSql(buff.toString());
			}
		
		}
		*/
	}

	// 查询
	public String[] getRes(String dates,String times,String num,String unit,String type ){
		int num_1 = 0;
		if("1".equals(type)){
			 num_1 = 0 - Integer.parseInt(num);
		}else{
			num_1 = Integer.parseInt(num);
		}
		
		String[] arr = {"",""};
		String tmp_1 = dates + "@" + times;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
		try {
			Date date = sdf.parse(tmp_1);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if("1".equals(unit)){
				calendar.add(Calendar.MINUTE,num_1);
			}else if("2".equals(unit)){
				calendar.add(Calendar.HOUR,num_1);
			}else if("3".equals(unit)){
				calendar.add(Calendar.DATE,num_1);
			}else if("4".equals(unit)){
				calendar.add(Calendar.MONTH,num_1);
			}
			
			tmp_1 = sdf.format(calendar.getTime());
			arr = tmp_1.split("@");
			return arr;
		} catch (ParseException e) {
			return arr;
		}
	}
	
	// 遍历  循环提醒中止条件表
	public boolean isGoonRemind(String stopID,String stopUqID){
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		boolean isGoon = true;
		if("".equals(stopID)){
			return isGoon;
		}
		String sql = "select * from uf_remindOtherStop where id in("+stopID+")";
		rs.executeSql(sql);
		while(rs.next()){
			String stopSql = Util.null2String(rs.getString("stopSql")).replace("$ID$","'"+stopUqID+"'");
			String stopInfo = Util.null2String(rs.getString("stopInfo"));
			// 执行条件的数据
			rs1.executeSql(stopSql);
			String res = "";
			if(rs1.next()){
				res = Util.null2String(rs1.getString(1));
			}
		//	System.out.println("stopSql = " + stopSql + " & stopInfo = " + stopInfo + " & res = " + res);
			if(res.equalsIgnoreCase(stopInfo)){
				isGoon = false;
				return isGoon;
			}
		}
		return isGoon;
	}
	/*
	 * 是否现在触发 triggertype 触发类型 1：每隔？分钟 2：每隔？小时 3：每隔？天 4：周 5：月 （月、天） 6：仅一次
	 */
	public boolean isNowTrigger(String reDate, String reTime,String triggertype, String triggercycletime, 
			String weeks,String months, String days) {
		boolean isTrigger = false;
		if ("6".equals(triggertype)) {
			isTrigger = true;
		} else if ("1".equals(triggertype)) {
			int res = getHowMi(reDate, reTime);
			if ("".equals(triggercycletime)) {
				return isTrigger;
			}
			int num = Integer.parseInt(triggercycletime);
			
		//	System.out.println("res = " + res + " & num = " + num );
			if (res > 0 && num > 0 && res % num == 0) {
				isTrigger = true;
			}
		} else if ("2".equals(triggertype)) {
			int res = getHowMi(reDate, reTime);
			if ("".equals(triggercycletime)) {
				return isTrigger;
			}
			// 小时转换分钟
			int num = Integer.parseInt(triggercycletime) * 60;
			if (res > 0 && num > 0 && res % num == 0) {
				isTrigger = true;
			}
		} else if ("3".equals(triggertype)) {
			int res = getHowMi(reDate, reTime);
			if ("".equals(triggercycletime)) {
				return isTrigger;
			}
			// 小时转换分钟
			int num = Integer.parseInt(triggercycletime) * 60 * 24;
			if (res > 0 && num > 0 && res % num == 0) {
				isTrigger = true;
			}
		} else if ("4".equals(triggertype)) {
			Calendar cal = Calendar.getInstance();
			int hour = cal.get(Calendar.HOUR);
			int minute = cal.get(Calendar.MINUTE);
			// 每天标准时间触发
			if(hour==normalHour && minute == normalMinute){
				int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
				if (w < 0) w = 0;
				if (weeks.contains(String.valueOf(w))) {
				//	System.out.println("triggertype = " + 4 + " & w=" + w);
					isTrigger = true;
				} else {
					String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" };
					String how_week = weekDays[w];
			//		System.out.println("triggertype = " + 4 + " & how_week=" + how_week);
					weeks = weeks.replace(" ", "");
					if (weeks.contains(how_week)) {
						isTrigger = true;
					}
				}
			}
		} else if ("5".equals(triggertype)) {
			months = "," + months.replace(" ", "") + ",";
			days = "," + days.replace(" ", "") + ",";
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DATE);
			int hour = cal.get(Calendar.HOUR);
			int minute = cal.get(Calendar.MINUTE);
			// 每天标准时间触发
			if(hour==normalHour && minute == normalMinute){
				if(days.contains(","+String.valueOf(day)+",")){
				//	System.out.println("triggertype = " + 5 + " & days = " + days + " & month = " + month);
					if (months.contains(","+String.valueOf(month)+",")) {
						isTrigger = true;
					}else{
						String[] monthArr = {"","一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
						String month_str = monthArr[month];
			//			System.out.println("triggertype = " + 5 + " & month_str = " + month_str);
						if (months.contains(","+month_str+",")) {
							isTrigger = true;
						}
					}
				}
			}
		}

		return isTrigger;
	}

	// 获取2个时间的分钟数
	private int getHowMi(String reDate, String reTime) {
		String tmp_1 = reDate + " " + reTime;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int res = 0;
		try {
			Date date = sdf.parse(tmp_1);
			Date now = new Date();

			long ks = now.getTime() - date.getTime();

			res = (int) ks / (1000 * 60);
		//	System.out.println("getHowMi(res) = " + res);
			return res;
		} catch (ParseException e) {
			return res;
		}

	}

	// 获取所有范围的人 areaType为1 人员 2 分部 3部门 4 角色 5所有人 
	// leadType 1 本人  2 上级  3 所有下级   4 上上级   5 上上上级
	public String getEmps(String areaType, String areaVal, String level,String leadType) {
		BaseBean log = new BaseBean();
		StringBuffer buff = new StringBuffer();
		StringBuffer trueBuff = new StringBuffer();
		RecordSet rs = new RecordSet();

		if ("".equals(level))
			level = "10";
		String sql = "";
		log.writeLog("#118# = <areaType>" + areaType + "<areaVal>" + areaVal 
					+ "<level>" + level +"<leadType>" +leadType);
		if ("1".equals(areaType)) {
			buff.append(areaVal);
		} else if ("2".equals(areaType)) {
			sql = "	select id from HrmResource where status in(0,1,2,3) and subcompanyid1 in("
					+ areaVal + ") and seclevel>=" + level;
			log.writeLog("@479@ = " + sql);
			rs.execute(sql);
			String flag = "";
			while (rs.next()) {
				buff.append(flag);
				buff.append(Util.null2String(rs.getString("id")));
				flag = ",";
			}
		} else if ("3".equals(areaType)) {
			sql = "	select id from HrmResource where status in(0,1,2,3) and departmentid in("
					+ areaVal + ") and seclevel>=" + level;
			log.writeLog("@491@ = " + sql);
			rs.execute(sql);
			String flag = "";
			while (rs.next()) {
				buff.append(flag);
				buff.append(Util.null2String(rs.getString("id")));
				flag = ",";
			}
		} else if ("4".equals(areaType)) {
			sql = "	select id from HrmResource where status in(0,1,2,3) and departmentid in("
					+ areaVal + ") and seclevel>=" + level;
			log.writeLog("@502@ = " + sql);
			rs.execute(sql);
			String flag = "";
			while (rs.next()) {
				buff.append(flag);
				buff.append(Util.null2String(rs.getString("id")));
				flag = ",";
			}
		} else if ("5".equals(areaType)) {
			sql = "select resourceid from hrmroleMembers where roleid in( "
					+ " select id from hrmroles where id in("
					+ areaVal
					+ ") ) "
					+ " and resourceid in(select id from HrmResource where seclevel>="
					+ level + " and status in(0,1,2,3))";
			log.writeLog("@517@ = " + sql);
			rs.execute(sql);
			String flag = "";
			while (rs.next()) {
				buff.append(flag);
				buff.append(Util.null2String(rs.getString("resourceid")));
				flag = ",";
			}
		}
		
		if("2".equals(leadType)){
			sql = "select id from HrmResource where managerid in("+buff.toString()+") and status in(0,1,2,3) ";
			log.writeLog("@529@ = " + sql);
			rs.execute(sql);
			String flag = "";
			while(rs.next()){
				trueBuff.append(flag);
				trueBuff.append(Util.null2String(rs.getString("id")));
				flag = ",";
			}
		}else if("3".equals(leadType)){
			sql = "select managerid from HrmResource where id in("+buff.toString()+") and status in(0,1,2,3) ";
			log.writeLog("@539@ = " + sql);
			rs.execute(sql);
			String flag = "";
			while(rs.next()){
				trueBuff.append(flag);
				trueBuff.append(Util.null2String(rs.getString("managerid")));
				flag = ",";
			}
		}else if("4".equals(leadType)){
			sql = "select managerid from HrmResource where id in(select managerid from HrmResource where id in("
					+buff.toString()+") and status in(0,1,2,3)) and status in(0,1,2,3) ";
			log.writeLog("@550@ = " + sql);
			rs.execute(sql);
			String flag = "";
			while(rs.next()){
				trueBuff.append(flag);
				trueBuff.append(Util.null2String(rs.getString("managerid")));
				flag = ",";
			}
		}else if("5".equals(leadType)){
			sql = "select managerid from HrmResource where id in(select managerid from HrmResource "
					+"where id in(select managerid from HrmResource where id in("
					+buff.toString()+") and status in(0,1,2,3)) and status in(0,1,2,3)) and status in(0,1,2,3) ";
			log.writeLog("@562@ = " + sql);
			rs.execute(sql);
			String flag = "";
			while(rs.next()){
				trueBuff.append(flag);
				trueBuff.append(Util.null2String(rs.getString("managerid")));
				flag = ",";
			}
		}else{
			trueBuff = buff;
		}
		
		return trueBuff.toString();
	}

}
