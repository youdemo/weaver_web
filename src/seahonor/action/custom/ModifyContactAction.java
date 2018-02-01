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

public class ModifyContactAction implements Action{
	BaseBean log = new BaseBean();
	@Override
	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String tableName = "";
		String tableName1="uf_Contacts";
		String xglxr = "";
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
		sql = "select xglxr,xgsqr from " + tableName + " where requestid= " + requestid;
		log.writeLog("开始更新联系人sql " + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			xglxr = Util.null2String(rs.getString("xglxr"));	
			sqr =  Util.null2String(rs.getString("xgsqr"));	
		}
		if(!"".equals(xglxr)){
			ModifyCustomAction gni = new ModifyCustomAction();
			gni.insertHistory("-63",tableName1, "id", xglxr);
			sql="select Max(id) as billid from "+tableName1+" where superid = "+xglxr;
			log.writeLog("开始插入历史记录sql " + sql);
			rs.executeSql(sql);
			if(rs.next()){
				billid= Util.null2String(rs.getString("billid"));	
				ModeRightInfo ModeRightInfo = new ModeRightInfo();
				ModeRightInfo.editModeDataShare(Integer.valueOf(sqr),56,Integer.valueOf(billid));			
			}
		}
		String version ="0";
		sql="select Version+1 as Version from "+tableName1+" where id="+xglxr;
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
			billid = Util.null2String(rs.getString("xglxr"));
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
			mapStr.put("Name", Util.null2String(rs.getString("Name")));// 姓名(中文)
			mapStr.put("xmyr", Util.null2String(rs.getString("xmyr")));// 姓名(英/日)
			mapStr.put("lxrcm", Util.null2String(rs.getString("lxrcm")));// 联系人层面
			mapStr.put("qz", Util.null2String(rs.getString("qz")));// 群组
			mapStr.put("customName", Util.null2String(rs.getString("customName")));// 所属公司
			mapStr.put("khmcyr", Util.null2String(rs.getString("khmcyr")));// 公司名称(英/日)
			mapStr.put("GroupName", Util.null2String(rs.getString("GroupName")));// 所属集团
			mapStr.put("jtmcyr", Util.null2String(rs.getString("jtmcyr")));// 集团名称(英/日)
			mapStr.put("dept", Util.null2String(rs.getString("dept")));// 部门(中文)
			mapStr.put("bmyr", Util.null2String(rs.getString("bmyr")));// 部门(英/日)
			mapStr.put("Position", Util.null2String(rs.getString("Position")));// 职位(中文)
			mapStr.put("zwyr", Util.null2String(rs.getString("zwyr")));// 职位(英/日)
			mapStr.put("lxrsr", Util.null2String(rs.getString("lxrsr")));// 联系人生日
			mapStr.put("lzsj", Util.null2String(rs.getString("lzsj")));// 离职时间
			mapStr.put("tel", Util.null2String(rs.getString("tel")));// 办公电话
			mapStr.put("mobile", Util.null2String(rs.getString("mobile")));// 移动电话
			mapStr.put("zzdh", Util.null2String(rs.getString("zzdh")));// 住宅电话
			mapStr.put("Email", Util.null2String(rs.getString("Email")));// 电子邮箱
			mapStr.put("grwxh", Util.null2String(rs.getString("grwxh")));// 个人微信号
			mapStr.put("status", Util.null2String(rs.getString("status")));// 在职状态
			mapStr.put("Fax", Util.null2String(rs.getString("Fax")));// 商务传真
			mapStr.put("Postcode", Util.null2String(rs.getString("Postcode")));// 邮政编码
			mapStr.put("bgdz", Util.null2String(rs.getString("bgdz")));// 办公地址(中文)
			mapStr.put("bgdzyr", Util.null2String(rs.getString("bgdzyr")));// 办公地址(英/日)
			mapStr.put("jtdz", Util.null2String(rs.getString("jtdz")));// 家庭地址
			mapStr.put("ybgsdgx", ybgsgx);// 与本公司的关系
			mapStr.put("kh", gkh);// 客户
			mapStr.put("gys", ggys);// 供应商
			mapStr.put("hzhb", ghzhb);// 合作伙伴
			mapStr.put("xxtgr", Util.null2String(rs.getString("xxtgr")));// 信息提供人
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// 信息提供方式
			mapStr.put("picHead", Util.null2String(rs.getString("picHead")));// 名片正面
			mapStr.put("picEnd", Util.null2String(rs.getString("picEnd")));// 名片反面
			mapStr.put("Version", version);//信息版本
			mapStr.put("scfj", Util.null2String(rs.getString("scfj")));// 上传附件
			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// 在线文档
			mapStr.put("bz", Util.null2String(rs.getString("bz")));// 备注
			iu.updateGen(mapStr,tableName1, "id", billid);
		}
		
		
		// TODO Auto-generated method stub
		return SUCCESS;
	}
}
