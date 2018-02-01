package seahonor.action.expenses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seahonor.util.InsertUtil;
import sun.util.logging.resources.logging;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateBankAccoutInfo  implements Action {
	BaseBean log = new BaseBean();
	@Override
	public String execute(RequestInfo info) {
		
		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		String sql_dt = "";
		String tableName = "";
		String mainid = "";
		String lx = "";
		String sql = "Select tablename,id From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id=" + workflowID + ")";
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		rs.executeSql(sql);
		if (rs.next()) {
			mainid = Util.null2String(rs.getString("id"));
			lx = Util.null2String(rs.getString("lx"));
		}
		log.writeLog("startupdate lx"+lx);
		if("0".equals(lx)||"4".equals(lx)||"5".equals(lx)||"7".equals(lx)){
			updateBankAcount(tableName,requestid,mainid);
		}
		if("1".equals(lx)||"5".equals(lx)||"6".equals(lx)||"7".equals(lx)){
			updateGSLC(tableName,requestid,mainid);
		}
		if("2".equals(lx)||"4".equals(lx)||"6".equals(lx)||"7".equals(lx)){
			updateGWK(tableName,requestid,mainid);
		}
		if("3".equals(lx)||"7".equals(lx)){
			updateXJK(tableName,requestid,mainid);
		}
		return SUCCESS;
	}
	
	public void updateBankAcount(String tableName,String requestid,String mainid){
		RecordSet rs = new RecordSet();
		log.writeLog("startupdate updateBankAcount");
		String yzhxx = "";//原账户信息
		String zxwd = "";//在线文档
		String scfj = "";//上传附件
		String bgkhhmc = "";//变更开户行名称
		String id = "";
		String sql="select * from "+tableName+"_dt1 where mainid="+mainid;
		rs.executeSql(sql);
		while(rs.next()){
			id = Util.null2String(rs.getString("id"));
			yzhxx = Util.null2String(rs.getString("yzhxx"));
			zxwd = Util.null2String(rs.getString("zxwd"));
			scfj = Util.null2String(rs.getString("scfj"));
			bgkhhmc = Util.null2String(rs.getString("bgkhhmc"));
			modifyBankAccount(yzhxx,zxwd,scfj,bgkhhmc);
		}
	}
	
	public void modifyBankAccount(String yzhxx,String zxwd,String scfj,String bgkhhmc){
		log.writeLog("startupdate modifyBankAccount");
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String formid="";
		String version="";
		String modeid="";
		String sql = "select id from workflow_bill where tablename='uf_bank_account'";
		rs.executeSql(sql);
		if (rs.next()) {
			formid = Util.null2String(rs.getString("id"));
		}
		sql = "update uf_bank_account set version='1.0' where id="
				+ yzhxx + " and (version is null or version = '')";
		rs.executeSql(sql);
		sql = "select isnull(version,1)+1 as version,formmodeid from uf_bank_account where id="
				+ yzhxx;
		rs.executeSql(sql);
		if (rs.next()) {
			version = Util.null2String(rs.getString("version"));
			modeid = Util.null2String(rs.getString("formmodeid"));
		}
		String createrid = insertHistory(formid, "uf_bank_account",
				"id", yzhxx);
		sql = "select Max(id) as billid from uf_bank_account where superid = "
				+ yzhxx;
		rs.executeSql(sql);
		String historyId = "";
		if (rs.next()) {
			historyId = Util.null2String(rs.getString("billid"));
			ModeRightInfo ModeRightInfo = new ModeRightInfo();
			 
			ModeRightInfo.editModeDataShare(Integer.valueOf(createrid),
					Integer.valueOf(modeid), Integer.valueOf(historyId));
		}
		Map<String, String> mapStr = new HashMap<String, String>();
		sql="select * from uf_bank_account_bg where id="+bgkhhmc;
		rs.executeSql(sql);
		if(rs.next()){
			mapStr.put("khhmc", Util.null2String(rs.getString("khhmc")));//开户行名称
			mapStr.put("yhzh", Util.null2String(rs.getString("yhzh")));//银行账号
			mapStr.put("zhlx", Util.null2String(rs.getString("zhlx")));//账户类型
			mapStr.put("yjmc", Util.null2String(rs.getString("yjmc")));//印鉴名称
			mapStr.put("zhbz", Util.null2String(rs.getString("zhbz")));//币种
			mapStr.put("khr", Util.null2String(rs.getString("khr")));//开户人
			mapStr.put("khrq", Util.null2String(rs.getString("khrq")));//开户日期
			mapStr.put("zhgly", Util.null2String(rs.getString("zhgly")));//账户管理员
			mapStr.put("khhdh", Util.null2String(rs.getString("khhdh")));//开户行电话
			mapStr.put("khhdz", Util.null2String(rs.getString("khhdz")));//开户行地址
			mapStr.put("scfj", scfj);//上传附件
			mapStr.put("zxwd", zxwd);//在线文档
			mapStr.put("bz", Util.null2String(rs.getString("bz")));//备注
			mapStr.put("khgs", Util.null2String(rs.getString("khgs")));//开户公司名称
			mapStr.put("khhlx", Util.null2String(rs.getString("khhlx")));//开户行联系人
			mapStr.put("cqje", Util.null2String(rs.getString("cqje")));//初期金额
			mapStr.put("requestid", Util.null2String(rs.getString("rqid")));//
			mapStr.put("version", version);
			iu.updateGen(mapStr, "uf_bank_account", "id", yzhxx);
		}
		
	}
	
	public void updateGSLC(String tableName,String requestid,String mainid){
		log.writeLog("startupdate updateGSLC");
		RecordSet rs = new RecordSet();
		String ylccpxx = "";//原理财产品信息
		String zxwd = "";//在线文档
		String scfj = "";//上传附件
		String bglccp = "";//变更理财产品
		String id = "";
		String sql="select * from "+tableName+"_dt2 where mainid="+mainid;
		rs.executeSql(sql);
		while(rs.next()){
			id = Util.null2String(rs.getString("id"));
			ylccpxx = Util.null2String(rs.getString("ylccpxx"));
			zxwd = Util.null2String(rs.getString("zxwd"));
			scfj = Util.null2String(rs.getString("scfj"));
			bglccp = Util.null2String(rs.getString("bglccp"));
			modifyGSLC(ylccpxx,zxwd,scfj,bglccp);
		}
	}
	public void modifyGSLC(String ylccpxx,String zxwd,String scfj,String bglccp){
		log.writeLog("startupdate modifyGSLC");
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String formid="";
		String version="";
		String modeid="";
		String sql = "select id from workflow_bill where tablename='uf_CorporateFinanc'";
		rs.executeSql(sql);
		if (rs.next()) {
			formid = Util.null2String(rs.getString("id"));
		}
		sql = "update uf_CorporateFinanc set version='1.0' where id="
				+ ylccpxx + " and (version is null or version = '')";
		rs.executeSql(sql);
		sql = "select isnull(version,1)+1 as version,formmodeid from uf_CorporateFinanc where id="
				+ ylccpxx;
		rs.executeSql(sql);
		if (rs.next()) {
			version = Util.null2String(rs.getString("version"));
			modeid = Util.null2String(rs.getString("formmodeid"));
		}
		String createrid = insertHistory(formid, "uf_CorporateFinanc",
				"id", ylccpxx);
		sql = "select Max(id) as billid from uf_CorporateFinanc where superid = "
				+ ylccpxx;
		rs.executeSql(sql);
		String historyId = "";
		if (rs.next()) {
			historyId = Util.null2String(rs.getString("billid"));
			ModeRightInfo ModeRightInfo = new ModeRightInfo();
			 
			ModeRightInfo.editModeDataShare(Integer.valueOf(createrid),
					Integer.valueOf(modeid), Integer.valueOf(historyId));
		}
		Map<String, String> mapStr = new HashMap<String, String>();
		sql="select * from uf_CorporateFina_bg where id="+bglccp;
		rs.executeSql(sql);
		if(rs.next()){
			mapStr.put("cpmc", Util.null2String(rs.getString("cpmc")));//产品名称
			mapStr.put("cpdm", Util.null2String(rs.getString("cpdm")));//产品代码
			mapStr.put("bz", Util.null2String(rs.getString("bz")));//币种
			mapStr.put("gmje", Util.null2String(rs.getString("gmje")));//购买金额
			mapStr.put("cplx", Util.null2String(rs.getString("cplx")));//产品类型
			mapStr.put("yqnhtz", Util.null2String(rs.getString("yqnhtz")));//预期年化投资收益率
			mapStr.put("tzksrq", Util.null2String(rs.getString("tzksrq")));//投资开始日期
			mapStr.put("tzjsrq", Util.null2String(rs.getString("tzjsrq")));//投资结束日期
			mapStr.put("qx", Util.null2String(rs.getString("qx")));//期限
			mapStr.put("cpfl", Util.null2String(rs.getString("cpfl")));//产品费率
			mapStr.put("fxjb", Util.null2String(rs.getString("fxjb")));//风险级别
			mapStr.put("sfktq", Util.null2String(rs.getString("sfktq")));//是否可提前赎回
			mapStr.put("lcjh", Util.null2String(rs.getString("lcjh")));//理财计划托管人
			mapStr.put("lccp", Util.null2String(rs.getString("lccp")));//理财产品管理员
			mapStr.put("zjhczhmc", Util.null2String(rs.getString("zjhczhmc")));//资金划出账户名称
			mapStr.put("zjhczhzh", Util.null2String(rs.getString("zjhczhzh")));//资金划出账户账号
			mapStr.put("scfj",scfj);//上传附件
			mapStr.put("zxwd", zxwd);//在线文档
			mapStr.put("beiz", Util.null2String(rs.getString("beiz")));//备注
			mapStr.put("gly", Util.null2String(rs.getString("gly")));//管理员
			mapStr.put("requestid", Util.null2String(rs.getString("rqid")));//
			mapStr.put("version", version);
			iu.updateGen(mapStr, "uf_CorporateFinanc", "id", ylccpxx);
		}
		
	}
	
	public void updateGWK(String tableName,String requestid,String mainid){
		log.writeLog("startupdate updateGWK");
		RecordSet rs = new RecordSet();
		String ygwkxx = "";//原理财产品信息
		String xzwd = "";//在线文档
		String scfj = "";//上传附件
		String bggwk = "";//变更理财产品
		String id = "";
		String sql="select * from "+tableName+"_dt3 where mainid="+mainid;
		rs.executeSql(sql);
		while(rs.next()){
			id = Util.null2String(rs.getString("id"));
			ygwkxx = Util.null2String(rs.getString("ygwkxx"));
			xzwd = Util.null2String(rs.getString("xzwd"));
			scfj = Util.null2String(rs.getString("scfj"));
			bggwk = Util.null2String(rs.getString("bggwk"));
			modifyGWK(ygwkxx,xzwd,scfj,bggwk);
		}
	}
	
	public void modifyGWK(String ygwkxx,String zxwd,String scfj,String bggwk){
		log.writeLog("startupdate modifyGWK");
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String formid="";
		String version="";
		String modeid="";
		String sql = "select id from workflow_bill where tablename='uf_NewBusiness'";
		rs.executeSql(sql);
		if (rs.next()) {
			formid = Util.null2String(rs.getString("id"));
		}
		sql = "update uf_NewBusiness set version='1.0' where id="
				+ ygwkxx + " and (version is null or version = '')";
		rs.executeSql(sql);
		sql = "select isnull(version,1)+1 as version,formmodeid from uf_NewBusiness where id="
				+ ygwkxx;
		rs.executeSql(sql);
		if (rs.next()) {
			version = Util.null2String(rs.getString("version"));
			modeid = Util.null2String(rs.getString("formmodeid"));
		}
		String createrid = insertHistory(formid, "uf_NewBusiness",
				"id", ygwkxx);
		sql = "select Max(id) as billid from uf_NewBusiness where superid = "
				+ ygwkxx;
		rs.executeSql(sql);
		String historyId = "";
		if (rs.next()) {
			historyId = Util.null2String(rs.getString("billid"));
			ModeRightInfo ModeRightInfo = new ModeRightInfo();
			 
			ModeRightInfo.editModeDataShare(Integer.valueOf(createrid),
					Integer.valueOf(modeid), Integer.valueOf(historyId));
		}
		Map<String, String> mapStr = new HashMap<String, String>();
		sql="select * from uf_NewBusiness_bg where id="+bggwk;
		rs.executeSql(sql);
		if(rs.next()){
			mapStr.put("kkyhmc", Util.null2String(rs.getString("kkyhmc")));//开卡银行名称
			mapStr.put("gwkckr", Util.null2String(rs.getString("gwkckr")));//公务卡持卡人
			mapStr.put("kh", Util.null2String(rs.getString("kh")));//卡号
			mapStr.put("gsjsh", Util.null2String(rs.getString("gsjsh")));//公司结算号
			mapStr.put("kkrq", Util.null2String(rs.getString("kkrq")));//开卡日期
			mapStr.put("bz", Util.null2String(rs.getString("bz")));//币种
			mapStr.put("cqje", Util.null2String(rs.getString("cqje")));//期初金额
			mapStr.put("myzdrq", Util.null2String(rs.getString("myzdrq")));//每月账单日期
			mapStr.put("mydqhk", Util.null2String(rs.getString("mydqhk")));//每月到期还款日期
			mapStr.put("gwkgly", Util.null2String(rs.getString("gwkgly")));//公务卡管理员
			mapStr.put("kkhlxdh", Util.null2String(rs.getString("kkhlxdh")));//开卡行联系电话
			mapStr.put("scfj",scfj);//上传附件
			mapStr.put("zxwd", zxwd);//在线文档
			mapStr.put("beiz", Util.null2String(rs.getString("beiz")));//备注
			mapStr.put("requestid", Util.null2String(rs.getString("rqid")));//
			mapStr.put("version", version);
			iu.updateGen(mapStr, "uf_NewBusiness", "id", ygwkxx);
		}
		
	}
	public void updateXJK(String tableName,String requestid,String mainid){
		log.writeLog("startupdate updateXJK");
		RecordSet rs = new RecordSet();
		String yxjkxx = "";//原理现金卡
		String xzwd = "";//在线文档
		String scfj = "";//上传附件
		String bgxjk = "";//变更现金卡
		String id = "";
		String sql="select * from "+tableName+"_dt4 where mainid="+mainid;
		rs.executeSql(sql);
		while(rs.next()){
			id = Util.null2String(rs.getString("id"));
			yxjkxx = Util.null2String(rs.getString("yxjkxx"));
			xzwd = Util.null2String(rs.getString("xzwd"));
			scfj = Util.null2String(rs.getString("scfj"));
			bgxjk = Util.null2String(rs.getString("bgxjk"));
			modifyXJK(yxjkxx,xzwd,scfj,bgxjk);
		}
	}
	public void modifyXJK(String yxjkxx,String zxwd,String scfj,String bgxjk){
		log.writeLog("startupdate modifyXJK");
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		String sql_dt = "";
		InsertUtil iu = new InsertUtil();
		String formid="";
		String version="";
		String modeid="";
		String sql = "select id from workflow_bill where tablename='uf_cash'";
		rs.executeSql(sql);
		if (rs.next()) {
			formid = Util.null2String(rs.getString("id"));
		}
		sql = "update uf_cash set version='1.0' where id="
				+ yxjkxx + " and (version is null or version = '')";
		rs.executeSql(sql);
		sql = "select isnull(version,1)+1 as version,formmodeid from uf_cash where id="
				+ yxjkxx;
		rs.executeSql(sql);
		if (rs.next()) {
			version = Util.null2String(rs.getString("version"));
			modeid = Util.null2String(rs.getString("formmodeid"));
		}
		String createrid = insertHistory(formid, "uf_cash",
				"id", yxjkxx);
		sql = "select Max(id) as billid from uf_cash where superid = "
				+ yxjkxx;
		rs.executeSql(sql);
		String historyId = "";
		if (rs.next()) {
			historyId = Util.null2String(rs.getString("billid"));
			ModeRightInfo ModeRightInfo = new ModeRightInfo();
			 
			ModeRightInfo.editModeDataShare(Integer.valueOf(createrid),
					Integer.valueOf(modeid), Integer.valueOf(historyId));
		}
		if (!"".equals(historyId)) {
			sql = "insert into uf_cash_dt1(mainid,mz,kh,zxwd,scfj,bz,sl) (select "
					+ historyId
					+ ",mz,kh,zxwd,scfj,bz,sl from uf_cash_dt1 where mainid="
					+ yxjkxx + " order by id asc )";
			rs.executeSql(sql);
		}
		Map<String, String> mapStr = new HashMap<String, String>();
		sql="select * from uf_cash_bg where id="+bgxjk;
		rs.executeSql(sql);
		if(rs.next()){
			mapStr.put("xjkmc", Util.null2String(rs.getString("xjkmc")));//现金卡名称
			mapStr.put("lb", Util.null2String(rs.getString("lb")));//类别
			mapStr.put("fkdw", Util.null2String(rs.getString("fkdw")));//发卡单位
			mapStr.put("hczjje", Util.null2String(rs.getString("hczjje")));//划出资金金额
			mapStr.put("hcgmzj", Util.null2String(rs.getString("hcgmzj")));//划出购买资金账户名称
			mapStr.put("hcgmzizh", Util.null2String(rs.getString("hcgmzizh")));//划出购买资金账户账号
			mapStr.put("yxjzrq", Util.null2String(rs.getString("yxjzrq")));//有效截止日期
			mapStr.put("gmrq", Util.null2String(rs.getString("gmrq")));//购买日期
			mapStr.put("kshm", Util.null2String(rs.getString("kshm")));//卡号（开始号码）
			mapStr.put("jshm", Util.null2String(rs.getString("jshm")));//卡号（结束号码）
			mapStr.put("scfj",scfj);//上传附件
			mapStr.put("zxwd", zxwd);//在线文档
			mapStr.put("bz", Util.null2String(rs.getString("bz")));//备注
			mapStr.put("bgy", Util.null2String(rs.getString("bgy")));//保管员
			mapStr.put("hjje", Util.null2String(rs.getString("hjje")));//合计金额
			mapStr.put("mz", Util.null2String(rs.getString("mz")));//面值
			mapStr.put("sl", Util.null2String(rs.getString("sl")));//数量
			mapStr.put("requestid", Util.null2String(rs.getString("rqid")));//
			mapStr.put("version", version);
			iu.updateGen(mapStr, "uf_cash", "id", yxjkxx);
			sql_dt = "delete from uf_cash_dt1 where mainid="
					+ yxjkxx;
			rs_dt.executeSql(sql_dt);
			
			sql_dt = "insert into uf_cash_dt1(mainid,mz,kh,zxwd,scfj,bz,sl) (select "
					+ yxjkxx
					+ ",mz,kh,zxwd,scfj,bz,sl from uf_cash_bg_dt1 where mainid="
					+ bgxjk+")";
			rs_dt.executeSql(sql_dt);
		}
		
	}
	public String insertHistory(String billid, String table_name,
			String uqField, String uqVal) {

		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String createrid = "";

		// 存放 表的字段
		List<String> list = new ArrayList<String>();
		String sql = "select fieldname from workflow_billfield where billid="
				+ billid + " order by dsporder";
		// log.writeLog("insertNow(1) = " + sql);
		rs.executeSql(sql);
		while (rs.next()) {
			String tmp_1 = Util.null2String(rs.getString("fieldname"));

			// 关联父类排除
			if (!"".equals(tmp_1) && !"superid".equalsIgnoreCase(tmp_1)) {
				list.add(tmp_1);
			}
		}
		if (!"".equals(table_name)) {

			Map<String, String> mapStr = new HashMap<String, String>();

			sql = "select * from " + table_name + "  where " + uqField + "='"
					+ uqVal + "'";
			// log.writeLog("insertNow(2) = " + sql);
			rs.execute(sql);
			if (rs.next()) {
				// 循环获取 不为空值的组合成sql
				for (String field : list) {
					String tmp_x = Util.null2String(rs.getString(field));
					if (tmp_x.length() > 0)
						mapStr.put(field, tmp_x);
				}
			}
			createrid = rs.getString("modedatacreater");
			// 最后需要补充关联父id
			if (mapStr.size() > 0) {
				mapStr.put("superid", Util.null2String(rs.getString("ID")));
				// 增加请求的id
				mapStr.put("requestid",
						Util.null2String(rs.getString("requestid")));
				mapStr.put("modedatacreater",
						Util.null2String(rs.getString("modedatacreater")));
				mapStr.put("modedatacreatertype",
						Util.null2String(rs.getString("modedatacreatertype")));
				mapStr.put("formmodeid",
						Util.null2String(rs.getString("formmodeid")));
				iu.insert(mapStr, table_name);
			}
		}

		return createrid;
	}
}
