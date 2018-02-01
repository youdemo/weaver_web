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

public class CreateContactAction implements Action {
	BaseBean log = new BaseBean();

	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		SysNoForSelf sns = new SysNoForSelf();
		InsertUtil iu = new InsertUtil();
		String sql_dt = "";
		String tableName = "";
		String table_name_1 = "uf_Contacts";
		String contactId = "";
		String mainid = "";
		String cjr = "";
		String cjrq = "";
		String cjsj = "";
		String cjxq = "";
		String sqr = "";
		String sqrq = "";
		String sqsj = "";
		String sqxq = "";
		String now = "";
		String seqNO = "";
		String lxrcm = "";
		String sql = "select CONVERT(varchar(30),getdate(),21) as nowTime ";
		rs.executeSql(sql);
		if (rs.next()) {
			now = Util.null2String(rs.getString("nowTime"));
		}
		sql = "Select tablename,id From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id=" + workflow_id + ")";
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		rs.executeSql(sql);
		if (rs.next()) {
			mainid = Util.null2String(rs.getString("id"));
			cjr = Util.null2String(rs.getString("cjr"));
			cjrq = Util.null2String(rs.getString("cjrq"));
			cjsj = Util.null2String(rs.getString("cjsj"));
			cjxq = Util.null2String(rs.getString("cjxq"));
			sqr = Util.null2String(rs.getString("sqr"));
			sqrq = Util.null2String(rs.getString("sqrq"));
			sqsj = Util.null2String(rs.getString("sqsj"));
			sqxq = Util.null2String(rs.getString("sqxq"));
		}
		sql = "select * from " + tableName + "_dt1 where mainid=" + mainid;
		rs.executeSql(sql);
		while (rs.next()) {

			String sysNo = sns.getNum("CCDL", table_name_1, 4);
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("SysNo", sysNo);// 联系人代码
			mapStr.put("cjr", cjr);// 创建人
			mapStr.put("cjrq", cjrq);// 创建日期
			mapStr.put("cjsj", cjsj);// 创建时间
			mapStr.put("cjxq", cjxq);// 创建星期
			mapStr.put("sqr", sqr);// 申请人
			mapStr.put("sqrq", sqrq);// 申请日期
			mapStr.put("sqsj", sqsj);// 申请时间
			mapStr.put("sqxq", sqxq);// 申请星期
			mapStr.put("Name", Util.null2String(rs.getString("xmzw")));// 姓名(中文)
			// mapStr.put("xmyr", Util.null2String(rs.getString("xmyr3")));//
			// 姓名(英/日)
			mapStr.put("wwm", Util.null2String(rs.getString("wwm")));
			lxrcm = Util.null2String(rs.getString("lxrcm"));
			mapStr.put("qz", Util.null2String(rs.getString("qz")));// 群组
			if ("0".equals(lxrcm)) {
				mapStr.put("GroupName", Util.null2String(rs.getString("ssjt")));// 所属集团
				mapStr.put("lxrcm", lxrcm);// 联系人层面
			}
			if ("1".equals(lxrcm)) {
				mapStr.put("customName", Util.null2String(rs.getString("ssgs")));// 所属公司
				mapStr.put("lxrcm", lxrcm);// 联系人层面
			}
			// mapStr.put("khmcyr",
			// Util.null2String(rs.getString("gsmcyr3")));// 公司名称(英/日)

			// mapStr.put("jtmcyr",
			// Util.null2String(rs.getString("jtmcyr3")));// 集团名称(英/日)

			mapStr.put("dept", Util.null2String(rs.getString("bm")));// 部门(中文)
			// mapStr.put("bmyr", Util.null2String(rs.getString("bmyr3")));//
			// 部门(英/日)
			mapStr.put("Position", Util.null2String(rs.getString("zw")));// 职位(中文)
			// mapStr.put("zwyr", Util.null2String(rs.getString("zwyr3")));//
			// 职位(英/日)
			mapStr.put("lxrsr", Util.null2String(rs.getString("sr")));// 联系人生日
			mapStr.put("lzsj", Util.null2String(rs.getString("zzsj")));// 离职时间
			mapStr.put("tel", Util.null2String(rs.getString("bgdh")));// 办公电话
			mapStr.put("mobile", Util.null2String(rs.getString("yddh")));// 移动电话
			mapStr.put("zzdh", Util.null2String(rs.getString("zzdh")));// 住宅电话
			mapStr.put("Email", Util.null2String(rs.getString("dzyx")));// 电子邮箱
			mapStr.put("grwxh", Util.null2String(rs.getString("wxh")));// 个人微信号
			mapStr.put("gjz", Util.null2String(rs.getString("gjz")));// 个人微信号
			mapStr.put("status", Util.null2String(rs.getString("zzzt")));// 在职状态
			mapStr.put("Fax", Util.null2String(rs.getString("swcz")));// 商务传真
			mapStr.put("Postcode", Util.null2String(rs.getString("yzbm")));// 邮政编码
			mapStr.put("bgdz", Util.null2String(rs.getString("bgdz")));// 办公地址(中文)
			// mapStr.put("bgdzyr",
			// Util.null2String(rs.getString("bgdzyr3")));// 办公地址(英/日)
			mapStr.put("jtdz", Util.null2String(rs.getString("jtdz")));// 家庭地址

			mapStr.put("xxtgr", Util.null2String(rs.getString("xxtgr")));// 信息提供人
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// 信息提供方式
			mapStr.put("picHead", Util.null2String(rs.getString("mpzm")));// 名片正面
			mapStr.put("picEnd", Util.null2String(rs.getString("mpfm")));// 名片反面

			mapStr.put("version", "0");// 信息版本
			mapStr.put("dealStatus", "0");// 信息状态
			mapStr.put("modedatacreater", sqr);
			mapStr.put("modedatacreatertype", "0");
			mapStr.put("formmodeid", "56");
			mapStr.put("scfj", Util.null2String(rs.getString("scfj")));// 上传附件
			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// 在线文档
			mapStr.put("bz", Util.null2String(rs.getString("bz")));// 备注
			iu.insert(mapStr, table_name_1);
			sql_dt = "select * from uf_Contacts where SysNo='" + sysNo + "'";
			log.writeLog("开始插入联系人1" + sql_dt);
			rs_dt.executeSql(sql_dt);
			if (rs_dt.next()) {
				contactId = Util.null2String(rs_dt.getString("id"));
			}
			ModeRightInfo ModeRightInfo = new ModeRightInfo();
			ModeRightInfo.editModeDataShare(Integer.valueOf(sqr), 56,
					Integer.valueOf(contactId));

		}
		return SUCCESS;
	}

}
