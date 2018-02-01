package seahonor.action.custom;

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

public class QucikCreateAction implements Action {
	BaseBean log = new BaseBean();

	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		String tableName = "";
		String customId = "";
		String groupId = "";
		String contactId = "";
		String cjlx = "";

		String sql = "Select tablename,id From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id=" + workflow_id + ")";
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}

		sql = "select cjlx from " + tableName + " where requestid= "
				+ requestid;
		log.writeLog("开始插入sql" + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			cjlx = Util.null2String(rs.getString("cjlx"));
		}
		log.writeLog("开始插入cjlx" + cjlx);
		if ("0".equals(cjlx)) {
			customId = insertCustom(requestid, tableName, "");
		} else if ("1".equals(cjlx)) {
			groupId = insertGroup(requestid, tableName);
		} else if ("2".equals(cjlx)) {
			contactId = insertContact(requestid, tableName, "", "");
		} else if ("3".equals(cjlx)) {
			groupId = insertGroup(requestid, tableName);
			customId = insertCustom(requestid, tableName, groupId);

		} else if ("4".equals(cjlx)) {
			customId = insertCustom(requestid, tableName, groupId);
			contactId = insertContact(requestid, tableName, customId, "");

		} else if ("5".equals(cjlx)) {
			groupId = insertGroup(requestid, tableName);
			customId = insertCustom(requestid, tableName, groupId);
			contactId = insertContact(requestid, tableName, customId, groupId);
		} else if ("6".equals(cjlx)) {
			groupId = insertGroup(requestid, tableName);
			contactId = insertContact(requestid, tableName, "", groupId);
		}
		return SUCCESS;
	}

	public String insertGroup(String requestid, String tableName) {

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		SysNoForSelf sns = new SysNoForSelf();
		InsertUtil iu = new InsertUtil();
		String table_name_1 = "uf_custom_group";
		String groupId = "";
		String now = "";
		String sql1 = "";
		String sqr = "";
		String sql = "select CONVERT(varchar(30),getdate(),21) as nowTime ";
		rs.executeSql(sql);
		if (rs.next()) {
			now = Util.null2String(rs.getString("nowTime"));
		}
		String seqNO = "";
		sql = "select MAX(isnull(seqNO,0))+1 as seqNO from uf_custom_group";
		rs.executeSql(sql);
		if (rs.next()) {
			seqNO = Util.null2String(rs.getString("seqNO"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		rs.executeSql(sql);
		log.writeLog("开始插入集团" + sql);
		if (rs.next()) {
			String sysNo = sns.getNum("CGL", table_name_1, 4);
			sqr = Util.null2String(rs.getString("sqr"));
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("seqNO", seqNO);// 创建人
			mapStr.put("cjr", Util.null2String(rs.getString("cjr")));// 创建人
			mapStr.put("cjrq", Util.null2String(rs.getString("cjrq")));// 创建日期
			mapStr.put("cjsj", Util.null2String(rs.getString("cjsj")));// 创建时间
			mapStr.put("cjxq", Util.null2String(rs.getString("cjxq")));// 创建星期
			mapStr.put("sqr", Util.null2String(rs.getString("sqr")));// 申请人
			mapStr.put("sqrq", Util.null2String(rs.getString("sqrq")));// 申请日期
			mapStr.put("sqsj", Util.null2String(rs.getString("sqsj")));// 申请时间
			mapStr.put("sqxq", Util.null2String(rs.getString("sqxq")));// 申请星期
			mapStr.put("GroupName", Util.null2String(rs.getString("jtmczw2")));// 集团名称(中文)
			mapStr.put("jtmcyr", Util.null2String(rs.getString("jtmcyr2")));// 集团名称(英/日)
			mapStr.put("GroupAdd", Util.null2String(rs.getString("jtdzzw2")));// 集团地址(中文)
			mapStr.put("jtdzyr", Util.null2String(rs.getString("jtdzyr2")));// 集团地址(英/日)
			mapStr.put("GroupTel", Util.null2String(rs.getString("jtdh2")));// 集团电话
			mapStr.put("GroupEmail", Util.null2String(rs.getString("jtyx2")));// 集团邮箱
			mapStr.put("GroupWebsit", Util.null2String(rs.getString("jtwz2")));// 集团网站
			mapStr.put("GroupZip", Util.null2String(rs.getString("yzbm2")));// 邮政编码
			mapStr.put("qywxh", Util.null2String(rs.getString("qywxh2")));// 企业微信号
			mapStr.put("GroupFax", Util.null2String(rs.getString("jtcz2")));// 集团传真
			mapStr.put("GroupGiver", Util.null2String(rs.getString("jtxxtgz2")));// 集团信息提供者
			mapStr.put("GroupCode", sysNo);// 集团代码
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs2")));// 信息提供方式
			mapStr.put("GroupStatus", "0");// 集团状态
			mapStr.put("Version", "0");// 信息版本
			mapStr.put("ModifyTime", now);// 信息更新时间
			// mapStr.put("zxwd",
			// Util.null2String(rs.getString("CustomFill")));//在线文档
			// mapStr.put("scfj",
			// Util.null2String(rs.getString("CustomFill")));//上传附件
			// mapStr.put("remark",
			// Util.null2String(rs.getString("CustomFill")));//备注
			mapStr.put("modedatacreater", sqr);
			mapStr.put("modedatacreatertype", "0");
			mapStr.put("formmodeid", "61");
			iu.insert(mapStr, table_name_1);
			sql1 = "select * from uf_custom_group where GroupCode='" + sysNo
					+ "'";
			log.writeLog("开始插入集团1" + sql1);
			rs1.executeSql(sql1);
			if (rs1.next()) {
				groupId = Util.null2String(rs1.getString("id"));
			}

		}
		insertGs(Integer.valueOf(sqr), 61, Integer.valueOf(groupId));
		return groupId;

	}

	public String insertCustom(String requestid, String tableName,
			String groupid) {

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		SysNoForSelf sns = new SysNoForSelf();
		InsertUtil iu = new InsertUtil();
		String table_name_1 = "UF_CUSTOM";
		String customId = "";
		String now = "";
		String sql1 = "";
		String sqr = "";
		String sql = "select CONVERT(varchar(30),getdate(),21) as nowTime ";
		rs.executeSql(sql);
		if (rs.next()) {
			now = Util.null2String(rs.getString("nowTime"));
		}
		String seqNO = "";
		sql = "select MAX(isnull(seqNO,0))+1 as seqNO from UF_CUSTOM  where seqNO !=100000";
		rs.executeSql(sql);
		if (rs.next()) {
			seqNO = Util.null2String(rs.getString("seqNO"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		log.writeLog("开始插入客户" + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			String gkh = Util.null2String(rs.getString("kh1"));
			String ggys = Util.null2String(rs.getString("gys1"));
			String ghzhb = Util.null2String(rs.getString("hzhb1"));
			String ybgsgx = null;
			if ("1".equals(gkh)) {
				if ("1".equals(ggys)) {
					if ("1".equals(ghzhb))
						ybgsgx = "6";
					else
						ybgsgx = "3";
				} else if ("1".equals(ghzhb))
					ybgsgx = "4";
				else
					ybgsgx = "0";
			} else if ("1".equals(ggys)) {
				if ("1".equals(ghzhb))
					ybgsgx = "5";
				else {
					ybgsgx = "1";
				}
			} else if ("1".equals(ghzhb))
				ybgsgx = "2";
			else {
				ybgsgx = null;
			}
			sqr = Util.null2String(rs.getString("sqr"));
			String sysNo = sns.getNum("CTL", table_name_1, 4);
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("seqNO", seqNO);// 公司代码
			mapStr.put("customCode", sysNo);// 公司代码
			mapStr.put("cjr", Util.null2String(rs.getString("cjr")));// 创建人
			mapStr.put("cjrq", Util.null2String(rs.getString("cjrq")));// 创建日期
			mapStr.put("cjsj", Util.null2String(rs.getString("cjsj")));// 创建时间
			mapStr.put("cjxq", Util.null2String(rs.getString("cjxq")));// 创建星期
			mapStr.put("sqr", sqr);// 申请人
			mapStr.put("sqrq", Util.null2String(rs.getString("sqrq")));// 申请日期
			mapStr.put("sqsj", Util.null2String(rs.getString("sqsj")));// 申请时间
			mapStr.put("sqxq", Util.null2String(rs.getString("sqxq")));// 申请星期
			mapStr.put("customName", Util.null2String(rs.getString("gsmczw1")));// 公司名称(中文)
			mapStr.put("khmcyr", Util.null2String(rs.getString("gsmcyr1")));// 公司名称(英/日)
			mapStr.put("gsjczw", Util.null2String(rs.getString("gsjczw1")));// 公司简称(中文)
			mapStr.put("dm", Util.null2String(rs.getString("dm1")));// 地名1
			mapStr.put("zh", Util.null2String(rs.getString("zh1")));// 字号
			mapStr.put("hy", Util.null2String(rs.getString("hy1")));// 行业
			mapStr.put("Address", Util.null2String(rs.getString("gsdzzw1")));// 公司地址(中文)
			mapStr.put("gsdz", Util.null2String(rs.getString("gsdzyr1")));// 公司地址(英/日)
			mapStr.put("Telphone", Util.null2String(rs.getString("gsdh1")));// 公司电话
			mapStr.put("Email", Util.null2String(rs.getString("gsyx1")));// 公司邮箱
			mapStr.put("Website", Util.null2String(rs.getString("gswz1")));// 公司网站
			mapStr.put("Fax", Util.null2String(rs.getString("gscz1")));// 公司传真
			mapStr.put("zcdz", Util.null2String(rs.getString("zcdz1")));// 注册地址
			mapStr.put("Postcode", Util.null2String(rs.getString("yzbm1")));// 邮政编码
			mapStr.put("gswxh", Util.null2String(rs.getString("gswxh1")));// 公司微信号
			mapStr.put("Country", Util.null2String(rs.getString("ssgj1")));// 所属国家
			if (!"".equals(groupid)) {
				mapStr.put("CustomGroup", groupid);// 所属集团
			} else {
				mapStr.put("CustomGroup",
						Util.null2String(rs.getString("ssjt1")));// 所属集团
			}
			mapStr.put("industryType", Util.null2String(rs.getString("hylx1")));// 行业类型
			mapStr.put("ybgsgx", ybgsgx);// 与本公司关系
			mapStr.put("kh", gkh);// 客户
			mapStr.put("gys", ggys);// 供应商
			mapStr.put("hzhb", ghzhb);// 合作伙伴
			mapStr.put("Provider", Util.null2String(rs.getString("gsxxtgr1")));// 公司信息提供人
			mapStr.put("khyh", Util.null2String(rs.getString("khyh1")));// 开户银行
			mapStr.put("yhzh", Util.null2String(rs.getString("yhzh1")));// 开户银行
			mapStr.put("zczb", Util.null2String(rs.getString("zczb1")));// 开户银行
			mapStr.put("sszb", Util.null2String(rs.getString("sszb1")));// 开户银行
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs1")));// 信息提供方式
			mapStr.put("sh", Util.null2String(rs.getString("sh1")));// 税号
			mapStr.put("CutomStatus", "0");// 公司状态
			// mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));//在线文档
			// mapStr.put("scfj", Util.null2String(rs.getString("scfj")));//上传附件
			mapStr.put("Version", "0");// 信息版本
			mapStr.put("ModifyTime", now);// 信息更新时间
			// mapStr.put("Remark",
			// Util.null2String(rs.getString("CustomFill")));//备注
			mapStr.put("modedatacreater", sqr);
			mapStr.put("modedatacreatertype", "0");
			mapStr.put("formmodeid", "52");
			iu.insert(mapStr, table_name_1);
			sql1 = "select * from uf_custom where customCode='" + sysNo + "'";
			log.writeLog("开始插入客户1" + sql1);
			rs1.executeSql(sql1);
			if (rs1.next()) {
				customId = Util.null2String(rs1.getString("id"));
			}

		}
		insertGs(Integer.valueOf(sqr), 52, Integer.valueOf(customId));

		return customId;
	}

	public String insertContact(String requestid, String tableName,
			String customid, String groupid) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		SysNoForSelf sns = new SysNoForSelf();
		InsertUtil iu = new InsertUtil();
		String table_name_1 = "uf_Contacts";
		String contactId = "";
		String now = "";
		String sql1 = "";
		String sqr = "";
		String sql = "select CONVERT(varchar(30),getdate(),21) as nowTime ";
		rs.executeSql(sql);
		if (rs.next()) {
			now = Util.null2String(rs.getString("nowTime"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		log.writeLog("开始插入联系人" + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			String gkh = Util.null2String(rs.getString("kh3"));
			String ggys = Util.null2String(rs.getString("gys3"));
			String ghzhb = Util.null2String(rs.getString("hzhb3"));
			String ybgsgx = null;
			if ("1".equals(gkh)) {
				if ("1".equals(ggys)) {
					if ("1".equals(ghzhb))
						ybgsgx = "6";
					else
						ybgsgx = "3";
				} else if ("1".equals(ghzhb))
					ybgsgx = "4";
				else
					ybgsgx = "0";
			} else if ("1".equals(ggys)) {
				if ("1".equals(ghzhb))
					ybgsgx = "5";
				else {
					ybgsgx = "1";
				}
			} else if ("1".equals(ghzhb))
				ybgsgx = "2";
			else {
				ybgsgx = null;
			}
			String sysNo = sns.getNum("CCDL", table_name_1, 4);
			sqr = Util.null2String(rs.getString("sqr"));
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("SysNo", sysNo);// 联系人代码
			mapStr.put("cjr", Util.null2String(rs.getString("cjr")));// 创建人
			mapStr.put("cjrq", Util.null2String(rs.getString("cjrq")));// 创建日期
			mapStr.put("cjsj", Util.null2String(rs.getString("cjsj")));// 创建时间
			mapStr.put("cjxq", Util.null2String(rs.getString("cjxq")));// 创建星期
			mapStr.put("sqr", Util.null2String(rs.getString("sqr")));// 申请人
			mapStr.put("sqrq", Util.null2String(rs.getString("sqrq")));// 申请日期
			mapStr.put("sqsj", Util.null2String(rs.getString("sqsj")));// 申请时间
			mapStr.put("sqxq", Util.null2String(rs.getString("sqxq")));// 申请星期
			mapStr.put("Name", Util.null2String(rs.getString("xmzw3")));// 姓名(中文)
			mapStr.put("xmyr", Util.null2String(rs.getString("xmyr3")));// 姓名(英/日)
			mapStr.put("lxrcm", Util.null2String(rs.getString("lxrcm3")));// 联系人层面
			mapStr.put("qz", Util.null2String(rs.getString("qz3")));// 群组
			if (!"".equals(customid) && "".equals(groupid)) {
				mapStr.put("customName", customid);// 所属公司
				mapStr.put("khmcyr", Util.null2String(rs.getString("gsmcyr1")));// 公司名称(英/日)
			} else if (!"".equals(groupid) && "".equals(customid)) {
				mapStr.put("GroupName", groupid);// 所属集团
				mapStr.put("jtmcyr", Util.null2String(rs.getString("jtmcyr2")));// 集团名称(英/日)
			} else if (!"".equals(groupid) && !"".equals(customid)) {
				String lxrcm3 = Util.null2String(rs.getString("lxrcm3"));
				if ("0".equals(lxrcm3)) {
					mapStr.put("GroupName", groupid);// 所属集团
					mapStr.put("jtmcyr",
							Util.null2String(rs.getString("jtmcyr2")));// 集团名称(英/日)
				} else {
					mapStr.put("customName", customid);// 所属公司
					mapStr.put("khmcyr",
							Util.null2String(rs.getString("gsmcyr1")));// 公司名称(英/日)
				}

			} else {
				mapStr.put("customName",
						Util.null2String(rs.getString("ssgs3")));// 所属公司
				mapStr.put("khmcyr", Util.null2String(rs.getString("gsmcyr3")));// 公司名称(英/日)
				mapStr.put("GroupName", Util.null2String(rs.getString("ssjt3")));// 所属集团
				mapStr.put("jtmcyr", Util.null2String(rs.getString("jtmcyr3")));// 集团名称(英/日)
			}
			mapStr.put("dept", Util.null2String(rs.getString("bmzw3")));// 部门(中文)
			mapStr.put("bmyr", Util.null2String(rs.getString("bmyr3")));// 部门(英/日)
			mapStr.put("Position", Util.null2String(rs.getString("zwzw3")));// 职位(中文)
			mapStr.put("zwyr", Util.null2String(rs.getString("zwyr3")));// 职位(英/日)
			mapStr.put("lxrsr", Util.null2String(rs.getString("lxrsr3")));// 联系人生日
			mapStr.put("lzsj", Util.null2String(rs.getString("lzsj3")));// 离职时间
			mapStr.put("tel", Util.null2String(rs.getString("bgdh3")));// 办公电话
			mapStr.put("mobile", Util.null2String(rs.getString("yddh3")));// 移动电话
			mapStr.put("zzdh", Util.null2String(rs.getString("zzdh3")));// 住宅电话
			mapStr.put("Email", Util.null2String(rs.getString("ddyx3")));// 电子邮箱
			mapStr.put("grwxh", Util.null2String(rs.getString("grwxh3")));// 个人微信号
			mapStr.put("status", Util.null2String(rs.getString("zzzt3")));// 在职状态
			mapStr.put("Fax", Util.null2String(rs.getString("swcz3")));// 商务传真
			mapStr.put("Postcode", Util.null2String(rs.getString("yzbm3")));// 邮政编码
			mapStr.put("bgdz", Util.null2String(rs.getString("bgdzzw3")));// 办公地址(中文)
			mapStr.put("bgdzyr", Util.null2String(rs.getString("bgdzyr3")));// 办公地址(英/日)
			mapStr.put("jtdz", Util.null2String(rs.getString("jtdz3")));// 家庭地址
			mapStr.put("ybgsdgx", ybgsgx);// 与本公司的关系
			mapStr.put("kh", gkh);// 客户
			mapStr.put("gys", ggys);// 供应商
			mapStr.put("hzhb", ghzhb);// 合作伙伴
			mapStr.put("xxtgr", Util.null2String(rs.getString("xxtgr3")));// 信息提供人
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs3")));// 信息提供方式
			mapStr.put("picHead", Util.null2String(rs.getString("mpzm3")));// 名片正面
			mapStr.put("picEnd", Util.null2String(rs.getString("mpfm3")));// 名片反面

			mapStr.put("version", "0");// 信息版本
			mapStr.put("dealStatus", "0");// 信息状态
			mapStr.put("modedatacreater", sqr);
			mapStr.put("modedatacreatertype", "0");
			mapStr.put("formmodeid", "56");
			// mapStr.put("scfj",
			// Util.null2String(rs.getString("CustomFill")));//上传附件
			// mapStr.put("zxwd",
			// Util.null2String(rs.getString("CustomFill")));//在线文档
			// mapStr.put("bz",
			// Util.null2String(rs.getString("CustomFill")));//备注
			iu.insert(mapStr, table_name_1);
			sql1 = "select * from uf_Contacts where SysNo='" + sysNo + "'";
			log.writeLog("开始插入联系人1" + sql1);
			rs1.executeSql(sql1);
			if (rs1.next()) {
				contactId = Util.null2String(rs1.getString("id"));
			}

		}
		insertGs(Integer.valueOf(sqr), 56, Integer.valueOf(contactId));
		return contactId;
	}

	private boolean insertGs(int creater, int modeid, int m_billid) {
		ModeRightInfo ModeRightInfo = new ModeRightInfo();
		log.writeLog("creater" + creater + "modeid" + modeid + "m_billid"
				+ m_billid);
		ModeRightInfo.editModeDataShare(creater, modeid, m_billid);// 新建的时候添加共享
		// --------------给文档赋权------------------------
		// ModeRightInfo modeRightInfo = new ModeRightInfo();
		// modeRightInfo.addDocShare(modecreater,modeid,m_billid);
		return true;
	}

}
