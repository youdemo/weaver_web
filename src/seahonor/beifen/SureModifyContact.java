package seahonor.beifen;

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
		while (rs.next()){
			String sfcl = Util.null2String(rs.getString("sfjd"));
//			if (!"1".equals(sfcl)) {
//				continue;
//			}
			String version = "0";
			billid = Util.null2String(rs.getString("lxr"));
			if ("3".equals(lx)) {
				sql_dt = "select Version+1 as Version from " + tableName1
						+ " where id=" + billid;
				rs_dt.executeSql(sql_dt);
				if (rs_dt.next()) {
					version = Util.null2String(rs_dt.getString("Version"));
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
			}
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("Name", Util.null2String(rs.getString("xm")));// 姓名(中文)
			// mapStr.put("xmyr", Util.null2String(rs.getString("xmyr3")));//
			// 姓名(英/日)
			String lxrcm = Util.null2String(rs.getString("lxrcm"));
			mapStr.put("qz", Util.null2String(rs.getString("cz")));// 群组
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
			mapStr.put("lzsj", Util.null2String(rs.getString("lzsj")));// 离职时间
			mapStr.put("tel", Util.null2String(rs.getString("bgdh")));// 办公电话
			mapStr.put("mobile", Util.null2String(rs.getString("yddh")));// 移动电话
			mapStr.put("zzdh", Util.null2String(rs.getString("zzdh")));// 住宅电话
			mapStr.put("Email", Util.null2String(rs.getString("dzyx")));// 电子邮箱
			mapStr.put("grwxh", Util.null2String(rs.getString("wxh")));// 个人微信号
			mapStr.put("status", Util.null2String(rs.getString("zzzt")));// 在职状态
			mapStr.put("Fax", Util.null2String(rs.getString("swcz")));// 商务传真
			mapStr.put("Postcode", Util.null2String(rs.getString("yzbm")));// 邮政编码
			mapStr.put("bgdz", Util.null2String(rs.getString("bgdz")));// 办公地址(中文)
			// mapStr.put("bgdzyr",
			// Util.null2String(rs.getString("bgdzyr3")));// 办公地址(英/日)
			mapStr.put("jtdz", Util.null2String(rs.getString("jtzz")));// 家庭地址

			mapStr.put("xxtgr", Util.null2String(rs.getString("xxtgr")));// 信息提供人
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// 信息提供方式
			mapStr.put("picHead", Util.null2String(rs.getString("mpzm")));// 名片正面
			mapStr.put("picEnd", Util.null2String(rs.getString("mpfm")));// 名片反面
			if ("3".equals(lx)) {
				mapStr.put("version", version);// 信息版本
			}else {
				mapStr.put("dealStatus",
						Util.null2String(rs.getString("xjdzt")));// 公司状态
			}

			mapStr.put("scfj", Util.null2String(rs.getString("fjsj")));// 上传附件
			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// 在线文档
			mapStr.put("bz", Util.null2String(rs.getString("bz")));// 备注
			mapStr.put("modifier", sqr);
			mapStr.put("ModifyTime", now);
			iu.updateGen(mapStr, "uf_Contacts", "id", billid);
			
		}
		
		return SUCCESS;
	}
}
