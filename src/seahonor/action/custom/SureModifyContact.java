package seahonor.action.custom;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class SureModifyContact implements Action {
	BaseBean log = new BaseBean();

	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		String sql_dt = "";
		InsertUtil iu = new InsertUtil();
		String tableName = "";
		String tableName1 = "uf_Contacts";
		String mainid = "";
		String billid = "";
		String sqr = "";
		String lx = "";
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
			lx = Util.null2String(rs.getString("lx"));
			mainid = Util.null2String(rs.getString("id"));
			sqr = Util.null2String(rs.getString("sqr"));
		}
		sql = "select * from " + tableName + "_dt1 where mainid=" + mainid;
		rs.executeSql(sql);
		while (rs.next()) {

			billid = Util.null2String(rs.getString("lxr"));

			String xgseq = Util.null2String(rs.getString("xgseq"));
			String njdzt = Util.null2String(rs.getString("xjdzt"));
			String bjzt = Util.null2String(rs.getString("bjzt"));
			if (!"1".equals(bjzt)) {
				continue;
			}
			

				if (!"".equals(billid)) {
					new SureCustomAction().insertHistory("-63", tableName1,
							"id", billid);
					sql_dt = "select Max(id) as billid from " + tableName1
							+ " where superid = " + billid;
					log.writeLog("开始茶如历史记录sql " + sql_dt);
					rs_dt.executeSql(sql_dt);
					String historyId = "";
					if (rs_dt.next()) {
						historyId = Util.null2String(rs_dt.getString("billid"));
						ModeRightInfo ModeRightInfo = new ModeRightInfo();
						ModeRightInfo.editModeDataShare(1, 56,
								Integer.valueOf(historyId));
					}
				}
		

			updateContactInfo(lx, now, billid, sqr, njdzt, tableName1, xgseq);
		}

		return SUCCESS;
	}

	public void updateContactInfo(String lx, String now, String billid,
			String sqr, String njdzt, String tableName1, String xgseq) {
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		RecordSet rs_dt = new RecordSet();
		String sql_dt = "";
		String sql = "select * from  uf_edit_contact  where xgid='" + billid
				+ "' and xgseq='" + xgseq + "'";
		rs.executeSql(sql);
		if (rs.next()) {
			String version = "0";
			
				sql_dt = "select Version+1 as Version from " + tableName1
						+ " where id=" + billid;
				rs_dt.executeSql(sql_dt);
				if (rs_dt.next()) {
					version = Util.null2String(rs_dt.getString("Version"));
				}
			
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("Name", Util.null2String(rs.getString("Name")));// 姓名(中文)
			// mapStr.put("xmyr", Util.null2String(rs.getString("xmyr3")));//
			// 姓名(英/日)
			mapStr.put("wwm", Util.null2String(rs.getString("wwm")));
			String lxrcm = Util.null2String(rs.getString("lxrcm"));
			mapStr.put("qz", Util.null2String(rs.getString("qz")));// 群组
			if ("0".equals(lxrcm)) {
				mapStr.put("GroupName", Util.null2String(rs.getString("GroupName")));// 所属集团
				mapStr.put("lxrcm", lxrcm);// 联系人层面
			}
			if ("1".equals(lxrcm)) {
				mapStr.put("customName", Util.null2String(rs.getString("customName")));// 所属公司
				mapStr.put("lxrcm", lxrcm);// 联系人层面
			}
			// mapStr.put("khmcyr",
			// Util.null2String(rs.getString("gsmcyr3")));// 公司名称(英/日)

			// mapStr.put("jtmcyr",
			// Util.null2String(rs.getString("jtmcyr3")));// 集团名称(英/日)

			mapStr.put("dept", Util.null2String(rs.getString("dept")));// 部门(中文)
			// mapStr.put("bmyr", Util.null2String(rs.getString("bmyr3")));//
			// 部门(英/日)
			mapStr.put("Position", Util.null2String(rs.getString("Position")));// 职位(中文)
			// mapStr.put("zwyr", Util.null2String(rs.getString("zwyr3")));//
			// 职位(英/日)
			mapStr.put("lxrsr", Util.null2String(rs.getString("lxrsr")));// 联系人生日
			mapStr.put("lzsj", Util.null2String(rs.getString("lzsj")));// 离职时间
			mapStr.put("tel", Util.null2String(rs.getString("tel")));// 办公电话
			mapStr.put("mobile", Util.null2String(rs.getString("mobile")));// 移动电话
			mapStr.put("zzdh", Util.null2String(rs.getString("zzdh")));// 住宅电话
			mapStr.put("Email", Util.null2String(rs.getString("Email")));// 电子邮箱
			mapStr.put("gjz", Util.null2String(rs.getString("gjz")));
			mapStr.put("grwxh", Util.null2String(rs.getString("grwxh")));// 个人微信号
			mapStr.put("status", Util.null2String(rs.getString("status")));// 在职状态
			mapStr.put("Fax", Util.null2String(rs.getString("Fax")));// 商务传真
			mapStr.put("Postcode", Util.null2String(rs.getString("Postcode")));// 邮政编码
			mapStr.put("bgdz", Util.null2String(rs.getString("bgdz")));// 办公地址(中文)
			// mapStr.put("bgdzyr",
			// Util.null2String(rs.getString("bgdzyr3")));// 办公地址(英/日)
			mapStr.put("jtdz", Util.null2String(rs.getString("jtdz")));// 家庭地址

			mapStr.put("xxtgr", Util.null2String(rs.getString("xxtgr")));// 信息提供人
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// 信息提供方式
			mapStr.put("picHead", Util.null2String(rs.getString("picHead")));// 名片正面
			mapStr.put("picEnd", Util.null2String(rs.getString("picEnd")));// 名片反面
			mapStr.put("version", version);// 信息版本
			if ("3".equals(lx)) {
				
			} else {
				mapStr.put("dealStatus",njdzt);// 公司状态
			}

			mapStr.put("scfj", Util.null2String(rs.getString("scfj")));// 上传附件
			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// 在线文档
			mapStr.put("bz", Util.null2String(rs.getString("bz")));// 备注
			mapStr.put("bj", Util.null2String(rs.getString("bj")));// 上传附件
			mapStr.put("czr", Util.null2String(rs.getString("czr")));// 上传附件
			mapStr.put("modifier", sqr);
			mapStr.put("ModifyTime", now);
			mapStr.put("cjr", Util.null2String(rs.getString("cjr")));
			mapStr.put("sqr", Util.null2String(rs.getString("sqr")));
			iu.updateGen(mapStr, "uf_Contacts", "id", billid);
		}
	}
}
