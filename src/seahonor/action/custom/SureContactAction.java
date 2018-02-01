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

public class SureContactAction implements Action {
	BaseBean log = new BaseBean();

	@Override
	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String tableName = "";
		String tableName1="uf_Contacts";
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
		String xglxr ="";
		String jdlxr = "";
		String mcjr = "";
		if(rs.next()){
			lb = Util.null2String(rs.getString("lb"));
			xglxr = Util.null2String(rs.getString("xglxr"));
			jdlxr = Util.null2String(rs.getString("jdlxr"));
			mcjr = Util.null2String(rs.getString("mcjr"));
		}
		String version ="0";		
		
		if("1".equals(lb)){
			sql="select Version+1 as Version from "+tableName1+" where id="+xglxr;
			rs.executeSql(sql);
			if(rs.next()){
				version =  Util.null2String(rs.getString("Version"));
			}
			if(!"".equals(xglxr)){
				GeneralNowInsert gni = new GeneralNowInsert();
				new SureCustomAction().insertHistory("-63",tableName1, "id", xglxr);
				sql="select Max(id) as billid from "+tableName1+" where superid = "+xglxr;
				log.writeLog("开始茶如历史记录sql " + sql+"mcjr"+mcjr);
				rs.executeSql(sql);
				if(rs.next()){
					billid= Util.null2String(rs.getString("billid"));	
					ModeRightInfo ModeRightInfo = new ModeRightInfo();
					ModeRightInfo.editModeDataShare(Integer.valueOf(mcjr),56,Integer.valueOf(billid));			
				}
			}
		}
		
		sql = "select * from " + tableName + " where requestid= " + requestid;
		log.writeLog("开始校对联系人sql " + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			if("0".equals(lb)){
				  billid = Util.null2String(rs.getString("jdlxr"));
			}else{
				  billid = Util.null2String(rs.getString("xglxr"));
			}
			String gkh = Util.null2String(rs.getString("mkh"));
			String ggys = Util.null2String(rs.getString("mgys"));
			String ghzhb = Util.null2String(rs.getString("mhzhb"));
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
			mapStr.put("cjr", Util.null2String(rs.getString("mcjr")));// 创建人
			mapStr.put("cjrq", Util.null2String(rs.getString("mcjrq")));// 创建日期
			//mapStr.put("cjsj", Util.null2String(rs.getString("cjsj")));// 创建时间
			//mapStr.put("cjxq", Util.null2String(rs.getString("cjxq")));// 创建星期
			mapStr.put("sqr", Util.null2String(rs.getString("msqr")));// 申请人
			mapStr.put("sqrq", Util.null2String(rs.getString("msqrq")));// 申请日期
			//mapStr.put("sqsj", Util.null2String(rs.getString("sqsj")));// 申请时间
			//mapStr.put("sqxq", Util.null2String(rs.getString("sqxq")));// 申请星期
			mapStr.put("Name", Util.null2String(rs.getString("mxmzw")));// 姓名(中文)
			mapStr.put("xmyr", Util.null2String(rs.getString("mxmyr")));// 姓名(英/日)
			mapStr.put("lxrcm", Util.null2String(rs.getString("mlxrcm")));// 联系人层面
			mapStr.put("qz", Util.null2String(rs.getString("xqz")));// 群组
			mapStr.put("customName", Util.null2String(rs.getString("mssgs")));// 所属公司
			mapStr.put("khmcyr", Util.null2String(rs.getString("mgsmcyr")));// 公司名称(英/日)
			mapStr.put("GroupName", Util.null2String(rs.getString("mssjt")));// 所属集团
			mapStr.put("jtmcyr", Util.null2String(rs.getString("mjtmcyr")));// 集团名称(英/日)
			mapStr.put("dept", Util.null2String(rs.getString("mbmzw")));// 部门(中文)
			mapStr.put("bmyr", Util.null2String(rs.getString("mbmyr")));// 部门(英/日)
			mapStr.put("Position", Util.null2String(rs.getString("mzwzw")));// 职位(中文)
			mapStr.put("zwyr", Util.null2String(rs.getString("mzwyr")));// 职位(英/日)
			mapStr.put("lxrsr", Util.null2String(rs.getString("mlxrsr")));// 联系人生日
			mapStr.put("lzsj", Util.null2String(rs.getString("mlzsj")));// 离职时间
			mapStr.put("tel", Util.null2String(rs.getString("mbgdh")));// 办公电话
			mapStr.put("mobile", Util.null2String(rs.getString("myddh")));// 移动电话
			mapStr.put("zzdh", Util.null2String(rs.getString("mzzdh")));// 住宅电话
			mapStr.put("Email", Util.null2String(rs.getString("mddyx")));// 电子邮箱
			mapStr.put("grwxh", Util.null2String(rs.getString("mgrwxh")));// 个人微信号
			mapStr.put("status", Util.null2String(rs.getString("mzzzt")));// 在职状态
			mapStr.put("Fax", Util.null2String(rs.getString("mswcz")));// 商务传真
			mapStr.put("Postcode", Util.null2String(rs.getString("myzbm")));// 邮政编码
			mapStr.put("bgdz", Util.null2String(rs.getString("mbgdzzw")));// 办公地址(中文)
			mapStr.put("bgdzyr", Util.null2String(rs.getString("mbgdzyr")));// 办公地址(英/日)
			mapStr.put("jtdz", Util.null2String(rs.getString("mjtdz")));// 家庭地址
			mapStr.put("ybgsdgx", ybgsgx);// 与本公司的关系
			mapStr.put("kh", gkh);// 客户
			mapStr.put("gys", ggys);// 供应商
			mapStr.put("hzhb", ghzhb);// 合作伙伴
			mapStr.put("xxtgr", Util.null2String(rs.getString("mxxtgr")));// 信息提供人
			mapStr.put("xxtgfs", Util.null2String(rs.getString("mxxtgfs")));// 信息提供方式
			mapStr.put("picHead", Util.null2String(rs.getString("mmpzm")));// 名片正面
			mapStr.put("picEnd", Util.null2String(rs.getString("mmpfm")));// 名片反面			
			if("0".equals(lb)){
				mapStr.put("Version", "0");//信息版本
				mapStr.put("dealStatus", "1");// 信息状态
			}else{
				mapStr.put("Version", version);//信息版本
			}
			mapStr.put("scfj", Util.null2String(rs.getString("mscfj")));// 上传附件
			mapStr.put("zxwd", Util.null2String(rs.getString("mzxwd")));// 在线文档
			mapStr.put("bz", Util.null2String(rs.getString("mbz")));// 备注
			
			iu.updateGen(mapStr, "uf_Contacts", "id", billid);

		}
		return SUCCESS;
	}

}
