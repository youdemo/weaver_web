package seahonor.action.custom;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class MergeContactAction implements Action{
	BaseBean log = new BaseBean();
	
	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String tableName = "";
		String tableName1 = "uf_Contacts";
		String xggs = "";
		String billid = "";
		String sqr = "";
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
		log.writeLog("开始联系人合并sql"+sql);
		rs.executeSql(sql);
		if (rs.next()) {
			String zlxr = Util.null2String(rs.getString("zlxr"));
			;// 主公司
			String bhblxr = Util.null2String(rs.getString("bhblxr"));// 被合并公司
			Map<String, String> mapStr = new HashMap<String, String>();
			// mapStr.put("seqNO", seqNO);// 公司代码

			if ("1".equals(Util.null2String(rs.getString("check1")))) {
				mapStr.put("Name", Util.null2String(rs.getString("mxmzw")));// 姓名(中文)
			}
			if ("1".equals(Util.null2String(rs.getString("check28")))) {
				mapStr.put("wwm", Util.null2String(rs.getString("mwwm")));// 姓名(中文)
			}
			if ("1".equals(Util.null2String(rs.getString("check2")))) {
				mapStr.put("qz", Util.null2String(rs.getString("xqz")));// 群组
			}
			if ("1".equals(Util.null2String(rs.getString("check3")))) {
				mapStr.put("mobile", Util.null2String(rs.getString("myddh")));// 移动电话
			}
			if ("1".equals(Util.null2String(rs.getString("check4")))) {
				mapStr.put("Email", Util.null2String(rs.getString("mdzyx")));// 电子邮箱
			}
			if ("1".equals(Util.null2String(rs.getString("check5")))) {
				mapStr.put("tel", Util.null2String(rs.getString("mbgdh")));// 办公电话
			}
			if ("1".equals(Util.null2String(rs.getString("check6")))) {
				mapStr.put("Fax", Util.null2String(rs.getString("mswcz")));// 商务传真
			}
			if ("1".equals(Util.null2String(rs.getString("check7")))) {
				mapStr.put("grwxh", Util.null2String(rs.getString("mgrwxh")));// 个人微信号
			}
			if ("1".equals(Util.null2String(rs.getString("check8")))) {
				mapStr.put("Postcode", Util.null2String(rs.getString("myzbm")));// 邮政编码
			}
			if ("1".equals(Util.null2String(rs.getString("check9")))) {
				mapStr.put("lxrcm", Util.null2String(rs.getString("mlxrcm")));// 联系人层面
			}
			if ("1".equals(Util.null2String(rs.getString("check10")))) {
				mapStr.put("GroupName", Util.null2String(rs.getString("mssjt")));// 所属集团
			}
			
			if ("1".equals(Util.null2String(rs.getString("check11")))) {
				mapStr.put("customName", Util.null2String(rs.getString("mssgs")));// 所属公司
			}
			if ("1".equals(Util.null2String(rs.getString("check12")))) {
				mapStr.put("bgdz", Util.null2String(rs.getString("mbgdzzw")));// 办公地址(中文)
			}
			if ("1".equals(Util.null2String(rs.getString("check13")))) {
				mapStr.put("Position", Util.null2String(rs.getString("mzwzw")));// 职位(中文)
			}
			if ("1".equals(Util.null2String(rs.getString("check14")))) {
				mapStr.put("dept", Util.null2String(rs.getString("mbmzw")));// 部门(中文)
			}
			if ("1".equals(Util.null2String(rs.getString("check15")))) {
				mapStr.put("zzdh", Util.null2String(rs.getString("mzzdh")));// 住宅电话
			}
			if ("1".equals(Util.null2String(rs.getString("check16")))) {
				mapStr.put("jtdz", Util.null2String(rs.getString("mjtdz")));// 家庭地址
			}
			if ("1".equals(Util.null2String(rs.getString("check17")))) {
				mapStr.put("lxrsr", Util.null2String(rs.getString("mlxrsr")));// 联系人生日
			}
			if ("1".equals(Util.null2String(rs.getString("check18")))) {
				mapStr.put("status", Util.null2String(rs.getString("mzzzt")));// 在职状态
			}

			if ("1".equals(Util.null2String(rs.getString("check19")))) {
				mapStr.put("lzsj", Util.null2String(rs.getString("mlzsj")));// 离职时间
			}
	
			if ("1".equals(Util.null2String(rs.getString("check20")))) {
				mapStr.put("xxtgr", Util.null2String(rs.getString("mxxtgr")));// 信息提供人
			}
			
			if ("1".equals(Util.null2String(rs.getString("check21")))) {
				mapStr.put("xxtgfs", Util.null2String(rs.getString("mxxtgfs")));// 信息提供方式
			}
				
			if ("1".equals(Util.null2String(rs.getString("check22")))) {
				mapStr.put("picHead", Util.null2String(rs.getString("mmpzm")));// 名片正面
			}
			if ("1".equals(Util.null2String(rs.getString("check23")))) {
				mapStr.put("picEnd", Util.null2String(rs.getString("mmpfm")));// 名片反面
			}
			
			mapStr.put("ModifyTime", now);// 信息更新时间
			//if ("1".equals(Util.null2String(rs.getString("check24")))) {
			//	mapStr.put("scfj", Util.null2String(rs.getString("mscfj")));// 上传附件
			//}
		
			
			if ("1".equals(Util.null2String(rs.getString("check25")))) {
				mapStr.put("zxwd", Util.null2String(rs.getString("mzxwd")));// 在线文档
			}
			if ("1".equals(Util.null2String(rs.getString("check26")))) {
				mapStr.put("bz", Util.null2String(rs.getString("mbz")));// 备注
			}
			if ("1".equals(Util.null2String(rs.getString("check27")))) {
				mapStr.put("gjz", Util.null2String(rs.getString("mgjz")));// 备注
			}
			iu.updateGen(mapStr, "uf_Contacts", "id", zlxr);
			SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd H:m:s");
			 now=sf.format(new Date());
			RecordSet rs1 = new RecordSet();
			String sql1="insert into uf_cm_planinfo(requestid,hblb,newid,oldid,inserttime,status) values("+requestid+",'1','"+zlxr+"','"+bhblxr+"','"+now+"','0')";
			log.writeLog("插入公司合并记录sql"+sql1);
			rs1.executeSql(sql1);		
			sql1 = "update uf_Contacts set dealStatus=3 where id="+bhblxr; 
			rs1.executeSql(sql1);	

		}

		return SUCCESS;
	}

}
