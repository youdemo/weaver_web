package seahonor.action.custom;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.GeneralNowInsert;
import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class ModifyGroupAction implements Action {
	BaseBean log = new BaseBean();

	@Override
	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String tableName = "";
		String tableName1 = "uf_custom_group";
		String xhjt = "";
		String billid = "";
		String sqr = "";
		String now = "";
		String sql = "Select tablename,id From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id=" + workflow_id + ")";
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		sql = "select CONVERT(varchar(30),getdate(),21) as nowTime ";
		rs.executeSql(sql);
		if (rs.next()) {
			now = Util.null2String(rs.getString("nowTime"));
		}
		sql = "select xhjt,xgsqr from " + tableName + " where requestid= "
				+ requestid;
		log.writeLog("开始更新集团sql " + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			xhjt = Util.null2String(rs.getString("xhjt"));
			sqr = Util.null2String(rs.getString("xgsqr"));
		}
		if (!"".equals(xhjt)) {
			ModifyCustomAction gni = new ModifyCustomAction();
			gni.insertHistory("-68", tableName1, "id", xhjt);
			sql = "select Max(id) as billid from " + tableName1
					+ " where superid = " + xhjt;
			log.writeLog("开始插入历史记录sql " + sql);
			rs.executeSql(sql);
			if (rs.next()) {
				billid = Util.null2String(rs.getString("billid"));
				ModeRightInfo ModeRightInfo = new ModeRightInfo();
				ModeRightInfo.editModeDataShare(Integer.valueOf(sqr), 61,
						Integer.valueOf(billid));
			}
		}
		String version = "0";
		sql = "select Version+1 as Version from " + tableName1 + " where id="
				+ xhjt;
		rs.executeSql(sql);
		if (rs.next()) {
			version = Util.null2String(rs.getString("Version"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		rs.executeSql(sql);
		if (rs.next()) {
			billid = Util.null2String(rs.getString("xhjt"));
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("GroupName", Util.null2String(rs.getString("GroupName")));// 集团名称(中文)
			mapStr.put("jtmcyr", Util.null2String(rs.getString("jtmcyr")));// 集团名称(英/日)
			mapStr.put("GroupAdd", Util.null2String(rs.getString("GroupAdd")));// 集团地址(中文)
			mapStr.put("jtdzyr", Util.null2String(rs.getString("jtdzyr")));// 集团地址(英/日)
			mapStr.put("GroupTel", Util.null2String(rs.getString("GroupTel")));// 集团电话
			mapStr.put("GroupEmail",
					Util.null2String(rs.getString("GroupEmail")));// 集团邮箱
			mapStr.put("GroupWebsit",
					Util.null2String(rs.getString("GroupWebsit")));// 集团网站
			mapStr.put("GroupZip", Util.null2String(rs.getString("GroupZip")));// 邮政编码
			mapStr.put("qywxh", Util.null2String(rs.getString("qywxh")));// 企业微信号
			mapStr.put("GroupFax", Util.null2String(rs.getString("GroupFax")));// 集团传真
			mapStr.put("GroupGiver",
					Util.null2String(rs.getString("GroupGiver")));// 集团信息提供者

			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// 信息提供方式
			mapStr.put("Version", version);// 信息版本
			mapStr.put("ModifyTime", now);// 信息更新时间
			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// 在线文档
			mapStr.put("scfj", Util.null2String(rs.getString("scfj")));// 上传附件
			mapStr.put("remark", Util.null2String(rs.getString("remark")));// 备注
			iu.updateGen(mapStr, "uf_custom_group", "id", billid);

		}

		// TODO Auto-generated method stub
		return SUCCESS;
	}
}
