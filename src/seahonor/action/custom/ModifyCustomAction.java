package seahonor.action.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seahonor.util.GeneralNowInsert;
import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class ModifyCustomAction implements Action{
	BaseBean log = new BaseBean();
	@Override
	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String tableName = "";
		String tableName1="uf_custom";
		String xggs = "";
		String billid = "";
		String sqr="";
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
		sql = "select xggs,xgsqr from " + tableName + " where requestid= " + requestid;
		log.writeLog("开始更新公司sql " + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			xggs = Util.null2String(rs.getString("xggs"));	
			sqr =  Util.null2String(rs.getString("xgsqr"));	
		}
		if(!"".equals(xggs)){
			GeneralNowInsert gni = new GeneralNowInsert();
			insertHistory("-59",tableName1, "id", xggs);
			sql="select Max(id) as billid from "+tableName1+" where superid = "+xggs;
			log.writeLog("开始茶如历史记录sql " + sql+"sqr"+sqr);
			rs.executeSql(sql);
			if(rs.next()){
				billid= Util.null2String(rs.getString("billid"));	
				ModeRightInfo ModeRightInfo = new ModeRightInfo();
				ModeRightInfo.editModeDataShare(Integer.valueOf(sqr),52,Integer.valueOf(billid));			
			}
		}
		String version ="0";
		sql="select Version+1 as Version from "+tableName1+" where id="+xggs;
		rs.executeSql(sql);
		if(rs.next()){
			version =  Util.null2String(rs.getString("Version"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		
		rs.executeSql(sql);
		if (rs.next()) {
			String gkh = Util.null2String(rs.getString("kh"));
			String ggys = Util.null2String(rs.getString("gys"));
			String ghzhb = Util.null2String(rs.getString("hzhb"));
			billid = Util.null2String(rs.getString("xggs"));
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
			// mapStr.put("customCode", sysNo);//公司代码
			//mapStr.put("cjr", Util.null2String(rs.getString("mcjr")));// 创建人
			//mapStr.put("cjrq", Util.null2String(rs.getString("mcjrq")));// 创建日期
			// mapStr.put("cjsj", Util.null2String(rs.getString("cjsj")));//创建时间
			// mapStr.put("cjxq", Util.null2String(rs.getString("cjxq")));//创建星期
			//mapStr.put("sqr", Util.null2String(rs.getString("msqr")));// 申请人
			//mapStr.put("sqrq", Util.null2String(rs.getString("msqrq")));// 申请日期
			// mapStr.put("sqsj", Util.null2String(rs.getString("sqsj")));//申请时间
			// mapStr.put("sqxq", Util.null2String(rs.getString("sqxq")));//申请星期
			mapStr.put("customName",
					Util.null2String(rs.getString("customName")));// 公司名称(中文)
			mapStr.put("khmcyr", Util.null2String(rs.getString("khmcyr")));// 公司名称(英/日)
			mapStr.put("Address", Util.null2String(rs.getString("Address")));// 公司地址(中文)
			mapStr.put("gsdz", Util.null2String(rs.getString("gsdz")));// 公司地址(英/日)
			mapStr.put("Telphone", Util.null2String(rs.getString("Telphone")));// 公司电话
			mapStr.put("Email", Util.null2String(rs.getString("Email")));// 公司邮箱
			mapStr.put("Website", Util.null2String(rs.getString("Website")));// 公司网站
			mapStr.put("Fax", Util.null2String(rs.getString("Fax")));// 公司传真
			mapStr.put("zcdz", Util.null2String(rs.getString("zcdz")));// 注册地址
			mapStr.put("Postcode", Util.null2String(rs.getString("Postcode")));// 邮政编码
			mapStr.put("gswxh", Util.null2String(rs.getString("gswxh")));// 公司微信号
			mapStr.put("Country", Util.null2String(rs.getString("Country")));// 所属国家
			mapStr.put("CustomGroup",
					Util.null2String(rs.getString("CustomGroup")));// 所属集团
			mapStr.put("industryType",
					Util.null2String(rs.getString("industryType")));// 行业类型
			mapStr.put("ybgsgx", ybgsgx);// 与本公司关系
			mapStr.put("kh", gkh);// 客户
			mapStr.put("gys", ggys);// 供应商
			mapStr.put("hzhb", ghzhb);// 合作伙伴
			mapStr.put("Provider", Util.null2String(rs.getString("Provider")));// 公司信息提供人
			mapStr.put("khyh", Util.null2String(rs.getString("khyh")));// 开户银行
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// 信息提供方式
			mapStr.put("sh", Util.null2String(rs.getString("sh")));// 税号
			//mapStr.put("CutomStatus", "1");// 公司状态
			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// 在线文档
			mapStr.put("scfj", Util.null2String(rs.getString("scfj")));// 上传附件
			 mapStr.put("Version", version);//信息版本
			mapStr.put("ModifyTime", now);// 信息更新时间
			mapStr.put("Remark", Util.null2String(rs.getString("Remark")));// 备注
			iu.updateGen(mapStr, "uf_custom", "id", billid);
		}
		
		// TODO Auto-generated method stub
		return SUCCESS;
	}
}
	
