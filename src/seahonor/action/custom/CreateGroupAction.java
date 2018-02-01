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

public class CreateGroupAction implements Action {
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
		String table_name_1 = "uf_custom_group";
		String groupId = "";
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
			sql_dt = "select MAX(isnull(seqNO,0))+1 as seqNO from uf_custom_group";
			rs_dt.executeSql(sql_dt);
			if (rs_dt.next()) {
				seqNO = Util.null2String(rs_dt.getString("seqNO"));
			}
			String sysNo = sns.getNum("CGL", table_name_1, 4);
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("seqNO", seqNO);
			mapStr.put("cjr", cjr);// 创建人
			mapStr.put("cjrq", cjrq);// 创建日期
			mapStr.put("cjsj", cjsj);// 创建时间
			mapStr.put("cjxq", cjxq);// 创建星期
			mapStr.put("sqr", sqr);// 申请人
			mapStr.put("sqrq", sqrq);// 申请日期
			mapStr.put("sqsj", sqsj);// 申请时间
			mapStr.put("sqxq", sqxq);// 申请星期
			mapStr.put("GroupName", Util.null2String(rs.getString("jtmc")));// 集团名称(中文)
			mapStr.put("jtwwm", Util.null2String(rs.getString("jtwwm")));// 集团外文名
			// mapStr.put("jtmcyr",
			// Util.null2String(rs.getString("jtmcyr2")));// 集团名称(英/日)
			mapStr.put("GroupAdd", Util.null2String(rs.getString("jtdz")));// 集团地址(中文)
			// mapStr.put("jtdzyr",
			// Util.null2String(rs.getString("jtdzyr2")));// 集团地址(英/日)
			mapStr.put("GroupTel", Util.null2String(rs.getString("jtdh")));// 集团电话
			// mapStr.put("GroupEmail",
			// Util.null2String(rs.getString("jtyx2")));// 集团邮箱
			mapStr.put("GroupWebsit", Util.null2String(rs.getString("jtwz")));// 集团网站
			mapStr.put("GroupZip", Util.null2String(rs.getString("yzbm")));// 邮政编码
			mapStr.put("qywxh", Util.null2String(rs.getString("gzwxh")));// 企业微信号
			mapStr.put("GroupFax", Util.null2String(rs.getString("jtcz")));// 集团传真
			mapStr.put("GroupGiver", Util.null2String(rs.getString("jtxztgz")));// 集团信息提供者
			mapStr.put("GroupCode", sysNo);// 集团代码
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// 信息提供方式
			mapStr.put("GroupStatus", "0");// 集团状态
			mapStr.put("cxzt", Util.null2String(rs.getString("cxzt")));// 集团状态
			mapStr.put("Version", "0");// 信息版本
			mapStr.put("ModifyTime", now);// 信息更新时间
			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// 在线文档
			mapStr.put("scfj", Util.null2String(rs.getString("fjsc")));// 上传附件
			mapStr.put("remark", Util.null2String(rs.getString("bz")));// 备注
			mapStr.put("ssjt", Util.null2String(rs.getString("ssjt")));// 所属集团
			mapStr.put("modedatacreater", sqr);
			mapStr.put("modedatacreatertype", "0");
			mapStr.put("formmodeid", "61");
			iu.insert(mapStr, table_name_1);
			sql_dt = "select * from uf_custom_group where GroupCode='" + sysNo
					+ "'";
			log.writeLog("开始插入集团1" + sql_dt);
			rs_dt.executeSql(sql_dt);
			if (rs_dt.next()) {
				groupId = Util.null2String(rs_dt.getString("id"));
			}
			ModeRightInfo ModeRightInfo = new ModeRightInfo();
			ModeRightInfo.editModeDataShare(Integer.valueOf(sqr), 61,
					Integer.valueOf(groupId));
		}
		return SUCCESS;
	}
}
