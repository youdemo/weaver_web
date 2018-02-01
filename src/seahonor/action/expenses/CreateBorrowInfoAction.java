package seahonor.action.expenses;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import seahonor.util.SysNoForSelf;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class CreateBorrowInfoAction implements Action {

	@Override
	public String execute(RequestInfo info) {
		BaseBean log = new BaseBean();
		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();
		RecordSet rs = new RecordSet();
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormate = new SimpleDateFormat("HH:mm:ss");
		String nowDate = dateFormate.format(new Date());
		String nowTime = timeFormate.format(new Date());
		String tableName = "";
		String mainid = "";
		String jkr = "";// 借款人
		String sqrq = "";// 申请日期
		String sqxq = "";// 申请星期
		String jkbz = "";// 借款币种
		String fkrq = "";//付款日期
		String sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID
				+ ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		
		rs.executeSql(sql);
		if (rs.next()) {
			mainid = Util.null2String(rs.getString("id"));
			jkr = Util.null2String(rs.getString("jkr"));
			sqrq = Util.null2String(rs.getString("sqrq"));
			sqxq = Util.null2String(rs.getString("sqxq"));
			jkbz = Util.null2String(rs.getString("jkbz"));
			fkrq = Util.null2String(rs.getString("fkrq"));
		}
		if ("0".equals(jkbz)) {
			createDT2Card(requestid, tableName, mainid, jkr, fkrq, sqxq, nowDate, nowTime);
			createDT3Card(requestid, tableName, mainid, jkr, fkrq, sqxq, nowDate, nowTime);
		} else if ("1".equals(jkbz)) {
			createDT2Card(requestid, tableName, mainid, jkr, fkrq, sqxq, nowDate, nowTime);
		} else if ("2".equals(jkbz)) {
			createDT3Card(requestid, tableName, mainid, jkr, fkrq, sqxq, nowDate, nowTime);
		}
		return SUCCESS;
	}

	public void createDT2Card(String requestid, String tableName,
			String mainid, String jkr, String sqrq, String sqxq, String nowDate,  String nowTime) {
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		InsertUtil iu = new InsertUtil();
		SysNoForSelf sns = new SysNoForSelf();
		String table_name_1 = "uf_personal_borrow";
		String tbyc = "";// 特别延长至*日期
		String jkyt = "";// 借款用途
		String zffs = "";// 支付方式
		String scfj = "";// 上传附件
		String zzxx = "";// 转账信息
		String cnzfje = "";// 出纳支付金额
		String jkbz = "";// 借款币种
		String pzhm = "";// 凭证号码
		String tbycxq = "";// 特别延长星期
		String zxwd = "";// 在线文档
		String jkxxid = "";// 借款信息id
		String sjskrq = "";//实际刷卡日期
		String skfxx = "";//公务卡
		String dtid = "";
		BaseBean log = new BaseBean();
		String sql = "select * from " + tableName + "_dt2 where mainid="
				+ mainid;
		String sql_dt = "";
		rs.executeSql(sql);
		while (rs.next()) {
			dtid = Util.null2String(rs.getString("id"));
			tbyc = Util.null2String(rs.getString("tbyc"));
			jkyt = Util.null2String(rs.getString("jkyt"));
			zffs = Util.null2String(rs.getString("zffs"));
			scfj = Util.null2String(rs.getString("scfj"));
			zzxx = Util.null2String(rs.getString("zzxx"));
			cnzfje = Util.null2String(rs.getString("cnzfje"));
			jkbz = Util.null2String(rs.getString("jkbz"));
			pzhm = Util.null2String(rs.getString("pzhm"));
			tbycxq = Util.null2String(rs.getString("tbycxq"));
			zxwd = Util.null2String(rs.getString("zxwd"));
			jkxxid = Util.null2String(rs.getString("jkxxid"));
			sjskrq = Util.null2String(rs.getString("sjskrq"));
			skfxx = Util.null2String(rs.getString("skfxx"));
			if (!"".equals(jkxxid)) {
				continue;
			}
			if("".equals(tbyc)){
				sql_dt = "select CONVERT(varchar(100), dateadd(day,30,cast('"+sqrq+"' as datetime)), 23) as tbyc,dbo.whatDay(CONVERT(varchar(100), dateadd(day,30,cast('"+sqrq+"' as datetime)), 23)) as tbycxq";
				//log.writeLog("sql_dt"+sql_dt);
				rs_dt.executeSql(sql_dt);
				if(rs_dt.next()){
					tbyc = Util.null2String(rs_dt.getString("tbyc"));
					tbycxq = Util.null2String(rs_dt.getString("tbycxq"));
				}
			}
			String sysNo = sns.getNum("JK", table_name_1, 4);
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("jkr", jkr);// 借款人
			mapStr.put("jkrq", sqrq);// 借款日期
			mapStr.put("jkdqr", tbyc);// 借款到期日
			mapStr.put("jkxq", "");// 借款星期
			mapStr.put("jkdqxq", "");// 借款到期星期
			mapStr.put("bz", jkbz);// 币种
			mapStr.put("jkje", cnzfje);// 借款金额
			mapStr.put("hkje", "0");// 还款金额
			mapStr.put("jkye", cnzfje);// 借款余额
			mapStr.put("sfyq", "1");// 是否逾期
			mapStr.put("xglc", requestid);// 相关流程
			mapStr.put("zffs", zffs);// 支付方式
			mapStr.put("zxwd", zxwd);// 在线文档
			mapStr.put("scfj", scfj);// 上传附件
			mapStr.put("pzhm", pzhm);// 凭证号码
			mapStr.put("jksm", jkyt);// 借款说明
			mapStr.put("zzxx", zzxx);// 转账信息
			mapStr.put("bh", sysNo);// 编号
			mapStr.put("sjskr", sjskrq);//实际刷卡日期
			mapStr.put("gwk", skfxx);// 公务卡
			mapStr.put("modedatacreater", jkr);
			mapStr.put("modedatacreatertype", "0");
			mapStr.put("modedatacreatedate", nowDate);
			mapStr.put("modedatacreatetime", nowTime);
			mapStr.put("formmodeid", "225");
			iu.insert(mapStr, table_name_1);
			jkxxid = "";
			sql_dt = "select id from " + table_name_1 + " where bh='" + sysNo
					+ "'";
			rs_dt.executeSql(sql_dt);
			if (rs_dt.next()) {
				jkxxid = Util.null2String(rs_dt.getString("id"));
			}
			ModeRightInfo ModeRightInfo = new ModeRightInfo();
			ModeRightInfo.editModeDataShare(Integer.valueOf(jkr), 225,
					Integer.valueOf(jkxxid));
			sql_dt = "update " + tableName + "_dt2 set jkxxid='" + jkxxid
					+ "' where id=" + dtid;
			rs_dt.executeSql(sql_dt);
		}

	}

	public void createDT3Card(String requestid, String tableName,
			String mainid, String jkr, String sqrq, String sqxq, String nowDate,  String nowTime) {
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		InsertUtil iu = new InsertUtil();
		SysNoForSelf sns = new SysNoForSelf();
		String table_name_1 = "uf_personal_borrow";
		String tbyc = "";// 特别延长至*日期
		String jkyt = "";// 借款用途
		String zffs = "";// 支付方式
		String scfj = "";// 上传附件
		String zzxx = "";// 转账信息
		String cnzfje = "";// 出纳支付金额
		String jkbz = "";// 借款币种
		String pzhm = "";// 凭证号码
		String tbycxq = "";// 特别延长星期
		String zxwd = "";// 在线文档
		String jkxxid = "";// 借款信息id
		String sjskrq = "";//实际刷卡日期
		String skfxx = "";//公务卡
		String dtid = "";
		BaseBean log = new BaseBean();
		String sql = "select * from " + tableName + "_dt3 where mainid="
				+ mainid;
		String sql_dt = "";
		rs.executeSql(sql);
		while (rs.next()) {
			dtid = Util.null2String(rs.getString("id"));
			tbyc = Util.null2String(rs.getString("tbyc"));
			jkyt = Util.null2String(rs.getString("jkyt"));
			zffs = Util.null2String(rs.getString("zffs"));
			scfj = Util.null2String(rs.getString("scfj"));
			zzxx = Util.null2String(rs.getString("zzxx"));
			cnzfje = Util.null2String(rs.getString("cnzfje"));
			jkbz = Util.null2String(rs.getString("jkbz"));
			pzhm = Util.null2String(rs.getString("pzhm"));
			tbycxq = Util.null2String(rs.getString("tbycxq"));
			zxwd = Util.null2String(rs.getString("zxwd"));
			jkxxid = Util.null2String(rs.getString("jkxxid"));
			sjskrq = Util.null2String(rs.getString("sjskrq"));
			skfxx = Util.null2String(rs.getString("skfxx"));
			if (!"".equals(jkxxid)) {
				continue;
			}
			if("".equals(tbyc)){
				sql_dt = "select CONVERT(varchar(100), dateadd(day,30,cast('"+sqrq+"' as datetime)), 23) as tbyc,dbo.whatDay(CONVERT(varchar(100), dateadd(day,30,cast('"+sqrq+"' as datetime)), 23)) as tbycxq";
				rs_dt.executeSql(sql_dt);
				if(rs_dt.next()){
					tbyc = Util.null2String(rs_dt.getString("tbyc"));
					tbycxq = Util.null2String(rs_dt.getString("tbycxq"));
				}
			}
			String sysNo = sns.getNum("JK", table_name_1, 4);
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("jkr", jkr);// 借款人
			mapStr.put("jkrq", sqrq);// 借款日期
			mapStr.put("jkdqr", tbyc);// 借款到期日
			mapStr.put("jkxq", "");// 借款星期
			mapStr.put("jkdqxq", "");// 借款到期星期
			mapStr.put("bz", jkbz);// 币种
			mapStr.put("jkje", cnzfje);// 借款金额
			mapStr.put("hkje", "0");// 还款金额
			mapStr.put("jkye", cnzfje);// 借款余额
			mapStr.put("sfyq", "1");// 是否逾期
			mapStr.put("xglc", requestid);// 相关流程
			mapStr.put("zffs", zffs);// 支付方式
			mapStr.put("zxwd", zxwd);// 在线文档
			mapStr.put("scfj", scfj);// 上传附件
			mapStr.put("pzhm", pzhm);// 凭证号码
			mapStr.put("jksm", jkyt);// 借款说明
			mapStr.put("zzxx", zzxx);// 转账信息
			mapStr.put("bh", sysNo);// 编号
			mapStr.put("sjskr", sjskrq);//实际刷卡日期
			mapStr.put("gwk", skfxx);// 公务卡
			mapStr.put("modedatacreater", jkr);
			mapStr.put("modedatacreatertype", "0");
			mapStr.put("modedatacreatedate", nowDate);
			mapStr.put("modedatacreatetime", nowTime);
			mapStr.put("formmodeid", "225");
			iu.insert(mapStr, table_name_1);
			jkxxid = "";
			sql_dt = "select id from " + table_name_1 + " where bh='" + sysNo
					+ "'";
			rs_dt.executeSql(sql_dt);
			if (rs_dt.next()) {
				jkxxid = Util.null2String(rs_dt.getString("id"));
			}
			ModeRightInfo ModeRightInfo = new ModeRightInfo();
			ModeRightInfo.editModeDataShare(Integer.valueOf(jkr), 225,
					Integer.valueOf(jkxxid));
			sql_dt = "update " + tableName + "_dt3 set jkxxid='" + jkxxid
					+ "' where id=" + dtid;
			rs_dt.executeSql(sql_dt);
		}
	}
}
