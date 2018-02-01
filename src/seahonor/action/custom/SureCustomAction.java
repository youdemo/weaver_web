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

public class SureCustomAction implements Action {
	BaseBean log = new BaseBean();

	@Override
	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String tableName = "";
        String tableName1 = "uf_custom";
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
		String xggs ="";
		String djdgs = "";
		String mcjr = "";
		if(rs.next()){
			lb = Util.null2String(rs.getString("lb"));
			xggs = Util.null2String(rs.getString("xggs"));
			djdgs = Util.null2String(rs.getString("djdgs"));
			mcjr = Util.null2String(rs.getString("mcjr"));
		}
		String version ="0";		
		
		if("1".equals(lb)){
			sql="select Version+1 as Version from "+tableName1+" where id="+xggs;
			rs.executeSql(sql);
			if(rs.next()){
				version =  Util.null2String(rs.getString("Version"));
			}
			if(!"".equals(xggs)){
				GeneralNowInsert gni = new GeneralNowInsert();
				insertHistory("-59",tableName1, "id", xggs);
				sql="select Max(id) as billid from "+tableName1+" where superid = "+xggs;
				log.writeLog("开始茶如历史记录sql " + sql+"mcjr"+mcjr);
				rs.executeSql(sql);
				if(rs.next()){
					billid= Util.null2String(rs.getString("billid"));	
					ModeRightInfo ModeRightInfo = new ModeRightInfo();
					ModeRightInfo.editModeDataShare(Integer.valueOf(mcjr),52,Integer.valueOf(billid));			
				}
			}
		}
		
		sql = "select * from " + tableName + " where requestid= " + requestid;
		log.writeLog("开始校对公司sql " + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			String gkh = Util.null2String(rs.getString("mkh"));
			String ggys = Util.null2String(rs.getString("mgys"));
			String ghzhb = Util.null2String(rs.getString("mhzhb"));
			if("0".equals(lb)){
			  billid = Util.null2String(rs.getString("djdgs"));
			}else{
			  billid = Util.null2String(rs.getString("xggs"));
			}
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
			mapStr.put("cjr", Util.null2String(rs.getString("mcjr")));// 创建人
			mapStr.put("cjrq", Util.null2String(rs.getString("mcjrq")));// 创建日期
			// mapStr.put("cjsj", Util.null2String(rs.getString("cjsj")));//创建时间
			// mapStr.put("cjxq", Util.null2String(rs.getString("cjxq")));//创建星期
			mapStr.put("sqr", Util.null2String(rs.getString("msqr")));// 申请人
			mapStr.put("sqrq", Util.null2String(rs.getString("msqrq")));// 申请日期
			// mapStr.put("sqsj", Util.null2String(rs.getString("sqsj")));//申请时间
			// mapStr.put("sqxq", Util.null2String(rs.getString("sqxq")));//申请星期
			mapStr.put("customName",
					Util.null2String(rs.getString("mcustomName")));// 公司名称(中文)
			mapStr.put("khmcyr", Util.null2String(rs.getString("mkhmcyr")));// 公司名称(英/日)
			mapStr.put("gsjczw",
					Util.null2String(rs.getString("mgsjczw")));// 公司简称(中文)
			mapStr.put("dm", Util.null2String(rs.getString("mdm")));
			mapStr.put("zh", Util.null2String(rs.getString("mzh")));
			mapStr.put("hy", Util.null2String(rs.getString("mhy")));
			mapStr.put("Address", Util.null2String(rs.getString("mAddress")));// 公司地址(中文)
			mapStr.put("gsdz", Util.null2String(rs.getString("mgsdz")));// 公司地址(英/日)
			mapStr.put("Telphone", Util.null2String(rs.getString("mTelphone")));// 公司电话
			mapStr.put("Email", Util.null2String(rs.getString("mEmail")));// 公司邮箱
			mapStr.put("Website", Util.null2String(rs.getString("mWebsite")));// 公司网站
			mapStr.put("Fax", Util.null2String(rs.getString("mFax")));// 公司传真
			mapStr.put("zcdz", Util.null2String(rs.getString("mzcdz")));// 注册地址
			mapStr.put("Postcode", Util.null2String(rs.getString("mPostcode")));// 邮政编码
			mapStr.put("gswxh", Util.null2String(rs.getString("mgswxh")));// 公司微信号
			mapStr.put("Country", Util.null2String(rs.getString("mCountry")));// 所属国家
			mapStr.put("CustomGroup",
					Util.null2String(rs.getString("mCustomGroup")));// 所属集团
			mapStr.put("industryType",
					Util.null2String(rs.getString("mindustryType")));// 行业类型
			mapStr.put("ybgsgx", ybgsgx);// 与本公司关系
			mapStr.put("kh", gkh);// 客户
			mapStr.put("gys", ggys);// 供应商
			mapStr.put("hzhb", ghzhb);// 合作伙伴
			mapStr.put("Provider", Util.null2String(rs.getString("mProvider")));// 公司信息提供人
			mapStr.put("khyh", Util.null2String(rs.getString("mkhyh")));// 开户银行
			mapStr.put("xxtgfs", Util.null2String(rs.getString("mxxtgfs")));// 信息提供方式
			mapStr.put("sh", Util.null2String(rs.getString("msh")));// 税号
			mapStr.put("yhzh", Util.null2String(rs.getString("myhzh")));// 银行账号
			mapStr.put("zczb", Util.null2String(rs.getString("mzczb")));// 注册资本
			mapStr.put("sszb", Util.null2String(rs.getString("msszb")));// 实收资本
			
			mapStr.put("zxwd", Util.null2String(rs.getString("mzxwd")));// 在线文档
			mapStr.put("scfj", Util.null2String(rs.getString("mscfj")));// 上传附件
			if("0".equals(lb)){
				mapStr.put("CutomStatus", "1");// 公司状态
				mapStr.put("Version", "0");//信息版本
			}else{
				mapStr.put("Version", version);//信息版本
			}
			
			mapStr.put("ModifyTime", now);// 信息更新时间
			mapStr.put("Remark", Util.null2String(rs.getString("mRemark")));// 备注
			iu.updateGen(mapStr, "uf_custom", "id", billid);
		}

		return SUCCESS;
	}
public boolean insertHistory(String billid,String table_name,String uqField,String uqVal) {
		
		BaseBean log  = new BaseBean();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();

		// 存放 表的字段
		List<String> list = new ArrayList<String>();
 		String sql = "select fieldname from workflow_billfield where billid="+billid+" order by dsporder";
 	//	log.writeLog("insertNow(1) = " + sql);
		rs.executeSql(sql);
		while(rs.next()){
			String tmp_1 = Util.null2String(rs.getString("fieldname"));
			
			// 关联父类排除
			if(!"".equals(tmp_1)&&!"SuperID".equalsIgnoreCase(tmp_1)){
				list.add(tmp_1);
			}
		}
		if(!"".equals(table_name)){
			
			Map<String, String> mapStr = new HashMap<String, String>();
			
			sql = "select * from "+table_name+"  where "+uqField+"='"+uqVal+"'";
		//	log.writeLog("insertNow(2) = " + sql);
			rs.execute(sql);
			if(rs.next()){
				// 循环获取   不为空值的组合成sql
				for(String field : list){
					String tmp_x = Util.null2String(rs.getString(field));
					if(tmp_x.length() > 0)
						mapStr.put(field, tmp_x);
				}
			}
			
			// 最后需要补充关联父id
			if(mapStr.size() > 0){
				mapStr.put("SuperID", Util.null2String(rs.getString("ID")));
				// 增加请求的id
				mapStr.put("requestid", Util.null2String(rs.getString("requestid")));
				mapStr.put("modedatacreater", Util.null2String(rs.getString("modedatacreater")));
				mapStr.put("modedatacreatertype", Util.null2String(rs.getString("modedatacreatertype")));
				mapStr.put("formmodeid", Util.null2String(rs.getString("formmodeid")));
				mapStr.put("qf", "1");
				iu.insert(mapStr, table_name);
			}
		}
		
		return true;
	}


}
