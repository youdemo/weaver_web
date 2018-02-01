package seahonor.beifen;

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

public class SureModifyCustom implements Action {
	BaseBean log = new BaseBean();

	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		String sql_dt = "";
		InsertUtil iu = new InsertUtil();
		String tableName = "";
		String tableName1 = "uf_custom";
		String mainid = "";
		String billid = "";
		String sqr = "";
		String lx = "";
		String now = "";
		String jyfw="";
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
		log.writeLog("开始公司校对合并sql " + sql);
		rs.executeSql(sql);
		while (rs.next()) {
			jyfw = Util.null2String(rs.getString("jyfw")).replaceAll("&nbsp;", " ").replaceAll("&nbsp", " ");
			String sfcl = Util.null2String(rs.getString("sfjd"));
//			if (!"1".equals(sfcl)) {
//				continue;
//			}
			String version = "0";
			billid = Util.null2String(rs.getString("jdgs"));
			if ("3".equals(lx)) {
				sql_dt = "select Version+1 as Version from " + tableName1
						+ " where id=" + billid;
				rs_dt.executeSql(sql_dt);
				if (rs_dt.next()) {
					version = Util.null2String(rs_dt.getString("Version"));
				}
				if (!"".equals(billid)) {
					new SureCustomAction().insertHistory("-59", tableName1,
							"id", billid);
					sql_dt = "select Max(id) as billid from " + tableName1
							+ " where superid = " + billid;
					log.writeLog("开始茶如历史记录sql " + sql_dt);
					rs_dt.executeSql(sql_dt);
					String historyId = "";
					if (rs_dt.next()) {
						historyId = Util.null2String(rs_dt.getString("billid"));
						ModeRightInfo ModeRightInfo = new ModeRightInfo();
						ModeRightInfo.editModeDataShare(1, 52,
								Integer.valueOf(historyId));
					}
				}
			}
			String gkh = Util.null2String(rs.getString("ke"));
			String ggys = Util.null2String(rs.getString("gys"));
			String ghzhb = Util.null2String(rs.getString("hzhb"));
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
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("customName", Util.null2String(rs.getString("gsmc")));// 公司名称(中文)
			mapStr.put("gsjczw", Util.null2String(rs.getString("gsjc")));// 公司简称(中文)
			mapStr.put("dm", Util.null2String(rs.getString("qym")));// 地名1
			mapStr.put("zh", Util.null2String(rs.getString("zh")));// 字号
			mapStr.put("hy", Util.null2String(rs.getString("hy")));// 行业
			mapStr.put("Address", Util.null2String(rs.getString("gsdz")));// 公司地址(中文)
			mapStr.put("Telphone", Util.null2String(rs.getString("gsdh")));// 公司电话
			mapStr.put("Website", Util.null2String(rs.getString("gswz")));// 公司网站
			mapStr.put("Fax", Util.null2String(rs.getString("gscz")));// 公司传真
			mapStr.put("zcdz", Util.null2String(rs.getString("zcdz")));// 注册地址
			mapStr.put("Postcode", Util.null2String(rs.getString("yzbm")));// 邮政编码
			mapStr.put("gswxh", Util.null2String(rs.getString("gzwxh")));// 公司微信号
			mapStr.put("gszt", Util.null2String(rs.getString("gszt")));// 公司状态
			mapStr.put("clrq", Util.null2String(rs.getString("clrq")));// 成立日期
			mapStr.put("zcrq", Util.null2String(rs.getString("zxrq")));// 注销日期
			mapStr.put("CustomGroup", Util.null2String(rs.getString("ssjt")));// 所属集团
			mapStr.put("ybgsgx", ybgsgx);// 与本公司关系
			mapStr.put("kh", gkh);// 客户
			mapStr.put("gys", ggys);// 供应商
			mapStr.put("hzhb", ghzhb);// 合作伙伴
			mapStr.put("Provider", Util.null2String(rs.getString("gsxxtgr")));// 公司信息提供人
			mapStr.put("khyh", Util.null2String(rs.getString("khyh")));// 开户银行
			mapStr.put("yhzh", Util.null2String(rs.getString("yhzh")));// 银行账号
			mapStr.put("zczb", Util.null2String(rs.getString("zczb")));// 注册资本
			mapStr.put("sszb", Util.null2String(rs.getString("sszb")));// 实收资本
			mapStr.put("bzh", Util.null2String(rs.getString("bzh")));// 币种
			mapStr.put("bzh1", Util.null2String(rs.getString("bzh1")));// 币种
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// 信息提供方式
			mapStr.put("sh", Util.null2String(rs.getString("sh")));// 税号
			mapStr.put("jyfw", jyfw);// 经营范围
			if ("3".equals(lx)) {
				mapStr.put("Version", version);// 信息版本
			}else{
				mapStr.put("CutomStatus",
						Util.null2String(rs.getString("njdzt")));// 公司状态
			}

			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// 在线文档
			mapStr.put("scfj", Util.null2String(rs.getString("fjsc")));// 上传附件

			mapStr.put("ModifyTime", now);// 信息更新时间
			mapStr.put("Modifier", sqr);
			mapStr.put("Remark", Util.null2String(rs.getString("bz")));// 备注
			iu.updateGen(mapStr, "uf_custom", "id", billid);

		}

		return SUCCESS;
	}
}
