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

public class SureGroupAction implements Action {
	BaseBean log = new BaseBean();

	@Override
	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String tableName = "";
		String tableName1 = "uf_custom_group";
		String billid = "";
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
		String lb = "";
		String xgjt ="";
		String jdjt = "";
		String mcjr = "";
		if(rs.next()){
			lb = Util.null2String(rs.getString("lb"));
			xgjt = Util.null2String(rs.getString("xgjt"));
			jdjt = Util.null2String(rs.getString("jdjt"));
			mcjr = Util.null2String(rs.getString("mcjr"));
		}
		String version ="0";		
		
		if("1".equals(lb)){
			sql="select Version+1 as Version from "+tableName1+" where id="+xgjt;
			rs.executeSql(sql);
			if(rs.next()){
				version =  Util.null2String(rs.getString("Version"));
			}
			if(!"".equals(xgjt)){
				GeneralNowInsert gni = new GeneralNowInsert();
				new SureCustomAction().insertHistory("-68",tableName1, "id", xgjt);
				sql="select Max(id) as billid from "+tableName1+" where superid = "+xgjt;
				log.writeLog("开始茶如历史记录sql " + sql+"mcjr"+mcjr);
				rs.executeSql(sql);
				if(rs.next()){
					billid= Util.null2String(rs.getString("billid"));	
					ModeRightInfo ModeRightInfo = new ModeRightInfo();
					ModeRightInfo.editModeDataShare(Integer.valueOf(mcjr),61,Integer.valueOf(billid));			
				}
			}
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		log.writeLog("开始校对集团sql " + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			if("0".equals(lb)){
				  billid = Util.null2String(rs.getString("jdjt"));
			}else{
				  billid = Util.null2String(rs.getString("xgjt"));
			}
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("cjr", Util.null2String(rs.getString("mcjr")));// 创建人
			mapStr.put("cjrq", Util.null2String(rs.getString("mcjrq")));// 创建日期
			// mapStr.put("cjsj", Util.null2String(rs.getString("cjsj")));//创建时间
			// mapStr.put("cjxq", Util.null2String(rs.getString("cjxq")));//创建星期
			mapStr.put("sqr", Util.null2String(rs.getString("msqr")));// 申请人
			mapStr.put("sqrq", Util.null2String(rs.getString("msqrq")));// 申请日期
			// mapStr.put("sqsj", Util.null2String(rs.getString("sqsj")));//申请时间
			// mapStr.put("sqxq", Util.null2String(rs.getString("sqxq")));//申请星期
			mapStr.put("GroupName", Util.null2String(rs.getString("mjtmczw")));// 集团名称(中文)
			mapStr.put("jtmcyr", Util.null2String(rs.getString("mjtmcyr")));// 集团名称(英/日)
			mapStr.put("GroupAdd", Util.null2String(rs.getString("mjtdzzw")));// 集团地址(中文)
			mapStr.put("jtdzyr", Util.null2String(rs.getString("mjtdzyr")));// 集团地址(英/日)
			mapStr.put("GroupTel", Util.null2String(rs.getString("mjtdh")));// 集团电话
			mapStr.put("GroupEmail", Util.null2String(rs.getString("mjtyx")));// 集团邮箱
			mapStr.put("GroupWebsit", Util.null2String(rs.getString("mjtwz")));// 集团网站
			mapStr.put("GroupZip", Util.null2String(rs.getString("myzbm")));// 邮政编码
			mapStr.put("qywxh", Util.null2String(rs.getString("mqywxh")));// 企业微信号
			mapStr.put("GroupFax", Util.null2String(rs.getString("mjtcz")));// 集团传真
			mapStr.put("GroupGiver", Util.null2String(rs.getString("mjtxxtgz")));// 集团信息提供者

			mapStr.put("xxtgfs", Util.null2String(rs.getString("mxxtgfs")));// 信息提供方式
			
			if("0".equals(lb)){
				mapStr.put("Version", "0");//信息版本
				mapStr.put("GroupStatus", "1");// 集团状态
			}else{
				mapStr.put("Version", version);//信息版本
			}
			mapStr.put("ModifyTime", now);// 信息更新时间
			mapStr.put("zxwd", Util.null2String(rs.getString("mzxwd")));// 在线文档
			mapStr.put("scfj", Util.null2String(rs.getString("mscfj")));// 上传附件
			mapStr.put("remark", Util.null2String(rs.getString("mbz")));// 备注

			iu.updateGen(mapStr, "uf_custom_group", "id", billid);
		}

		return SUCCESS;
	}

}
