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

public class CreateCustomAction implements Action {
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
		String table_name_1 = "uf_custom";
		String customId = "";
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
		String jyfw = "";
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
			cjrq = Util.null2String(rs.getString("sjrq"));
			cjsj = Util.null2String(rs.getString("cjjs"));
			cjxq = Util.null2String(rs.getString("cjxq"));
			sqr = Util.null2String(rs.getString("sqr"));
			sqrq = Util.null2String(rs.getString("sqrq"));
			sqsj = Util.null2String(rs.getString("sqsj"));
			sqxq = Util.null2String(rs.getString("sqxq"));
		}
		sql = "select * from " + tableName + "_dt1 where mainid=" + mainid;
		rs.executeSql(sql);
		while (rs.next()) {
			sql_dt = "select MAX(isnull(seqNO,0))+1 as seqNO from UF_CUSTOM  where seqNO !=100000";
			rs_dt.executeSql(sql_dt);
			if (rs_dt.next()) {
				seqNO = Util.null2String(rs_dt.getString("seqNO"));
			}
			String gkh = Util.null2String(rs.getString("ke"));
			String ggys = Util.null2String(rs.getString("gys"));
			String ghzhb = Util.null2String(rs.getString("hzhb"));
			jyfw = Util.null2String(rs.getString("jyfw")).replaceAll("&nbsp;", " ").replaceAll("&nbsp", " ");
			String ybgsgx = null;
			String zf=Util.null2String(rs.getString("zf"));
			String yh=Util.null2String(rs.getString("yh"));
			String ybgsgxstr="";
			String flag="";
			if("1".equals(gkh)){
				ybgsgxstr=ybgsgxstr+flag+"客户";
				flag="/";
			}
			if("1".equals(ggys)){
				ybgsgxstr=ybgsgxstr+flag+"供应商";
				flag="/";
			}
			if("1".equals(ghzhb)){
				ybgsgxstr=ybgsgxstr+flag+"合作伙伴";
				flag="/";
			}
			if("1".equals(zf)){
				ybgsgxstr=ybgsgxstr+flag+"政府";
				flag="/";
			}
			if("1".equals(yh)){
				ybgsgxstr=ybgsgxstr+flag+"银行";
				flag="/";
			}
			sql_dt="select selectvalue from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='uf_custom' and a.fieldname='ybgsgx' and upper(c.selectname)=upper('"+ybgsgxstr+"')";
			log.writeLog("ccccsql"+sql_dt);
			rs_dt.executeSql(sql_dt);
			if(rs_dt.next()){
				ybgsgx = Util.null2String(rs_dt.getString("selectvalue"));
			}
			String sysNo = sns.getNum("CTL", table_name_1, 4);
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("seqNO", seqNO);// 公司代码
			mapStr.put("customCode", sysNo);// 公司代码
			mapStr.put("cjr", cjr);// 创建人
			mapStr.put("cjrq", cjrq);// 创建日期
			mapStr.put("cjsj", cjsj);// 创建时间
			mapStr.put("cjxq", cjxq);// 创建星期
			mapStr.put("sqr", sqr);// 申请人
			mapStr.put("sqrq", sqrq);// 申请日期
			mapStr.put("sqsj", sqsj);// 申请时间
			mapStr.put("sqxq", sqxq);// 申请星期
			mapStr.put("customName", Util.null2String(rs.getString("gsmc")));// 公司名称(中文)
			mapStr.put("gswwm", Util.null2String(rs.getString("gswwm")));
			// mapStr.put("khmcyr", Util.null2String(rs.getString("gsmcy")));//
			// 公司名称(英/日)
			mapStr.put("gsjczw", Util.null2String(rs.getString("gsjc")));// 公司简称(中文)
			mapStr.put("dm", Util.null2String(rs.getString("qym")));// 地名1
			mapStr.put("zh", Util.null2String(rs.getString("zh")));// 字号
			mapStr.put("hy", Util.null2String(rs.getString("hy")));// 行业
			mapStr.put("gjz", Util.null2String(rs.getString("gjz")));// 行业
			mapStr.put("px1", Util.null2String(rs.getString("px1")));// 地名1
			mapStr.put("px2", Util.null2String(rs.getString("px2")));// 字号
			mapStr.put("px3", Util.null2String(rs.getString("px3")));// 行业
			mapStr.put("Address", Util.null2String(rs.getString("gsdz")));// 公司地址(中文)
			// mapStr.put("gsdz", Util.null2String(rs.getString("gsdzyr1")));//
			// 公司地址(英/日)
			mapStr.put("Telphone", Util.null2String(rs.getString("gsdh")));// 公司电话
			// mapStr.put("Email", Util.null2String(rs.getString("gsyx")));//
			// 公司邮箱
			mapStr.put("Website", Util.null2String(rs.getString("gswz")));// 公司网站
			mapStr.put("Fax", Util.null2String(rs.getString("gscz")));// 公司传真
			mapStr.put("zcdz", Util.null2String(rs.getString("zcdz")));// 注册地址
			mapStr.put("Postcode", Util.null2String(rs.getString("yzbm")));// 邮政编码
			mapStr.put("gswxh", Util.null2String(rs.getString("gzwxh")));// 公司微信号
			mapStr.put("gszt", Util.null2String(rs.getString("gszt")));// 公司状态
			mapStr.put("clrq", Util.null2String(rs.getString("clrq")));// 成立日期
			mapStr.put("zcrq", Util.null2String(rs.getString("zxrq")));// 注销日期
			// mapStr.put("Country", Util.null2String(rs.getString("ssgg")));//
			// 所属国家
			mapStr.put("CustomGroup", Util.null2String(rs.getString("ssjt")));// 所属集团

			// mapStr.put("industryType",
			// Util.null2String(rs.getString("hylx")));// 行业类型
			mapStr.put("ybgsgx", ybgsgx);// 与本公司关系
			mapStr.put("kh", gkh);// 客户
			mapStr.put("gys", ggys);// 供应商
			mapStr.put("hzhb", ghzhb);// 合作伙伴
			mapStr.put("zf", zf);// 合作伙伴
			mapStr.put("yh", yh);// 合作伙伴
			mapStr.put("Provider", Util.null2String(rs.getString("gsxxtgr")));// 公司信息提供人
			mapStr.put("khyh", Util.null2String(rs.getString("khyh")));// 开户银行
			mapStr.put("yhzh", Util.null2String(rs.getString("yhzh")));// 开户银行
			mapStr.put("zczb", Util.null2String(rs.getString("zczb")));// 开户银行
			mapStr.put("sszb", Util.null2String(rs.getString("sszb")));// 开户银行
			mapStr.put("bzh", Util.null2String(rs.getString("bzh")));// 币种
			mapStr.put("bzh1", Util.null2String(rs.getString("bzh1")));// 币种
			mapStr.put("jyfw", jyfw);// 经营范围
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// 信息提供方式
			mapStr.put("sh", Util.null2String(rs.getString("sh")));// 税号
			mapStr.put("CutomStatus", "0");// 公司状态
			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// 在线文档
			mapStr.put("scfj", Util.null2String(rs.getString("fjsc")));// 上传附件
			mapStr.put("Version", "0");// 信息版本
			mapStr.put("ModifyTime", now);// 信息更新时间
			mapStr.put("Remark", Util.null2String(rs.getString("bz")));// 备注
			mapStr.put("modedatacreater", sqr);
			mapStr.put("modedatacreatertype", "0");
			mapStr.put("formmodeid", "52");
			iu.insert(mapStr, table_name_1);
			sql_dt = "select * from uf_custom where customCode='" + sysNo + "'";
			log.writeLog("开始插入客户1" + sql_dt);
			rs_dt.executeSql(sql_dt);

			if (rs_dt.next()) {
				customId = Util.null2String(rs_dt.getString("id"));
			}
			ModeRightInfo ModeRightInfo = new ModeRightInfo();
			ModeRightInfo.editModeDataShare(Integer.valueOf(sqr), 52,
					Integer.valueOf(customId));

		}
		return SUCCESS;
	}

}
