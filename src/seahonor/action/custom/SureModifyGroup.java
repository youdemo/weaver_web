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

public class SureModifyGroup implements Action {
	BaseBean log = new BaseBean();

	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String tableName = "";
		String tableName1 = "uf_custom_group";
		String billid = "";
		String sqr = "";
		String lx = "";
		String jt = "";
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
		sql = "select * from " + tableName + " where requestid= " + requestid;
		rs.executeSql(sql);
		if (rs.next()) {
			sqr = Util.null2String(rs.getString("sqr"));
			lx = Util.null2String(rs.getString("lx"));
			jt = Util.null2String(rs.getString("jt"));
		}
		String version = "0";
	
			sql = "select Version+1 as Version from " + tableName1
					+ " where id=" + jt;
			rs.executeSql(sql);
			if (rs.next()) {
				version = Util.null2String(rs.getString("Version"));
			}
			if (!"".equals(jt)) {
				GeneralNowInsert gni = new GeneralNowInsert();
				new SureCustomAction().insertHistory("-68", tableName1, "id",
						jt);
				sql = "select Max(id) as billid from " + tableName1
						+ " where superid = " + jt;
				log.writeLog("开始茶如历史记录sql " + sql);
				rs.executeSql(sql);
				if (rs.next()) {
					billid = Util.null2String(rs.getString("billid"));
					ModeRightInfo ModeRightInfo = new ModeRightInfo();
					ModeRightInfo.editModeDataShare(1, 61,
							Integer.valueOf(billid));
				}
			}
		
		sql = "select * from " + tableName + " where requestid= " + requestid;
		log.writeLog("开始校对集团sql " + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			billid = Util.null2String(rs.getString("jt"));
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("GroupName", Util.null2String(rs.getString("jtmc")));// 集团名称(中文)
			mapStr.put("jtwwm", Util.null2String(rs.getString("jtwwm")));// 集团外文名
			mapStr.put("GroupAdd", Util.null2String(rs.getString("jtdz")));// 集团地址(中文)
			mapStr.put("GroupTel", Util.null2String(rs.getString("jtdh")));// 集团电话
			mapStr.put("GroupWebsit", Util.null2String(rs.getString("jtwz")));// 集团网站
			mapStr.put("GroupZip", Util.null2String(rs.getString("yzbm")));// 邮政编码
			mapStr.put("qywxh", Util.null2String(rs.getString("wxgzh")));// 企业微信号
			mapStr.put("GroupFax", Util.null2String(rs.getString("jtcz")));// 集团传真
			mapStr.put("cxzt", Util.null2String(rs.getString("cxzt")));// 存续状态
			mapStr.put("GroupGiver", Util.null2String(rs.getString("jtxxtgz")));// 集团信息提供者
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// 信息提供方式
			mapStr.put("Version", version);// 信息版本
			if ("3".equals(lx)) {
				
			}else{
				mapStr.put("GroupStatus",
						Util.null2String(rs.getString("njdzt")));// 集团状态
			}
			mapStr.put("ModifyTime", now);// 信息更新时间
			mapStr.put("Modifier", sqr);
			mapStr.put("zxwd", Util.null2String(rs.getString("xzwd1")));// 在线文档
			mapStr.put("scfj", Util.null2String(rs.getString("fjsc1")));// 上传附件
			mapStr.put("remark", Util.null2String(rs.getString("bz1")));// 备注
			iu.updateGen(mapStr, "uf_custom_group", "id", billid);
		}

		return SUCCESS;
	}

}
