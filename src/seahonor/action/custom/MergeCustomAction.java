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

public class MergeCustomAction implements Action {
	BaseBean log = new BaseBean();
	@Override
	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String tableName = "";
		String tableName1 = "uf_custom";
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
		log.writeLog("开始公司合并sql"+sql);
		rs.executeSql(sql);
		if (rs.next()) {
			String zgs = Util.null2String(rs.getString("zgs"));
			;// 主公司
			String bhbgs = Util.null2String(rs.getString("bhbgs"));// 被合并公司
			Map<String, String> mapStr = new HashMap<String, String>();
			// mapStr.put("seqNO", seqNO);// 公司代码

			
			if ("1".equals(Util.null2String(rs.getString("check1")))) {
				mapStr.put("customName",
						Util.null2String(rs.getString("mcustomName")));// 公司名称(中文)
			}
			if ("1".equals(Util.null2String(rs.getString("check2")))) {
				mapStr.put("gsjczw", Util.null2String(rs.getString("mgsjczw")));// 公司简称
				mapStr.put("dm", Util.null2String(rs.getString("mdm")));// 地名1
				mapStr.put("zh", Util.null2String(rs.getString("mzh")));// 字号
				mapStr.put("hy", Util.null2String(rs.getString("mhy")));// 行业
				mapStr.put("px1", Util.null2String(rs.getString("mpx1")));// 地名1
				mapStr.put("px2", Util.null2String(rs.getString("mpx2")));// 字号
				mapStr.put("px3", Util.null2String(rs.getString("mpx3")));// 行业
			}
			if ("1".equals(Util.null2String(rs.getString("check3")))) {
				mapStr.put("CustomGroup",
						Util.null2String(rs.getString("mCustomGroup")));// 所属集团
			}
			if ("1".equals(Util.null2String(rs.getString("check4")))) {
				mapStr.put("gjz",
						Util.null2String(rs.getString("mgjz")));// 所属集团
			}
			if ("1".equals(Util.null2String(rs.getString("check5")))) {
				mapStr.put("gswwm",
						Util.null2String(rs.getString("mgswwm")));// 所属集团
			}
									
			if ("1".equals(Util.null2String(rs.getString("check7")))) {
				mapStr.put("Telphone",
						Util.null2String(rs.getString("mTelphone")));// 公司电话
			}
			
			if ("1".equals(Util.null2String(rs.getString("check29")))) {
				mapStr.put("Address",
						Util.null2String(rs.getString("mAddress")));// 公司地址(中文)
			}
			if ("1".equals(Util.null2String(rs.getString("check8")))) {
				mapStr.put("Fax", Util.null2String(rs.getString("mFax")));// 公司传真
			}
			
			if ("1".equals(Util.null2String(rs.getString("check9")))) {
				mapStr.put("Website",
						Util.null2String(rs.getString("mWebsite")));// 公司网站
			}
			if ("1".equals(Util.null2String(rs.getString("check10")))) {
				mapStr.put("Postcode",
						Util.null2String(rs.getString("mPostcode")));// 邮政编码
			}
			if ("1".equals(Util.null2String(rs.getString("check11")))) {
				mapStr.put("gswxh", Util.null2String(rs.getString("mgswxh")));// 公司微信号
			}
			
			if ("1".equals(Util.null2String(rs.getString("check9")))) {
				mapStr.put("gsdz", Util.null2String(rs.getString("mgsdz")));// 公司地址(英/日)
			}
			if ("1".equals(Util.null2String(rs.getString("check10")))) {
				
			}
			if ("1".equals(Util.null2String(rs.getString("check11")))) {
				mapStr.put("Email", Util.null2String(rs.getString("mEmail")));// 公司邮箱
			}
			
			
			if ("1".equals(Util.null2String(rs.getString("check12")))) {
				mapStr.put("zcdz", Util.null2String(rs.getString("mzcdz")));// 注册地址
			}
			if ("1".equals(Util.null2String(rs.getString("check13")))) {
				mapStr.put("gszt", Util.null2String(rs.getString("xcxzt")));// 存续状态
			}
			if ("1".equals(Util.null2String(rs.getString("check14")))) {
				String gkh = "";
				String ggys = "";
				String ghzhb = "";
				String zf="";
				String yh="";
				String ybgsgx = Util.null2String(rs.getString("mybgsgx"));
				if ("0".equals(ybgsgx) || "3".equals(ybgsgx)
						|| "4".equals(ybgsgx) || "6".equals(ybgsgx)|| "9".equals(ybgsgx)|| "10".equals(ybgsgx)
						|| "16".equals(ybgsgx)|| "17".equals(ybgsgx)|| "18".equals(ybgsgx)|| "19".equals(ybgsgx)|| "20".equals(ybgsgx)
						|| "25".equals(ybgsgx)|| "26".equals(ybgsgx)|| "27".equals(ybgsgx)|| "28".equals(ybgsgx)|| "30".equals(ybgsgx)) {
					gkh = "1";
				}
				if ("1".equals(ybgsgx) || "3".equals(ybgsgx)
						|| "5".equals(ybgsgx) || "6".equals(ybgsgx)|| "11".equals(ybgsgx)|| "12".equals(ybgsgx)
						|| "16".equals(ybgsgx) || "17".equals(ybgsgx)|| "21".equals(ybgsgx)|| "22".equals(ybgsgx)
						|| "23".equals(ybgsgx)|| "25".equals(ybgsgx)|| "26".equals(ybgsgx)|| "27".equals(ybgsgx)|| "29".equals(ybgsgx)|| "30".equals(ybgsgx)) {
					ggys = "1";

				}
				if ("2".equals(ybgsgx) || "3".equals(ybgsgx)
						|| "5".equals(ybgsgx) || "6".equals(ybgsgx)|| "13".equals(ybgsgx)|| "14".equals(ybgsgx)
						|| "18".equals(ybgsgx) || "19".equals(ybgsgx)|| "21".equals(ybgsgx)|| "22".equals(ybgsgx)
						|| "24".equals(ybgsgx)|| "25".equals(ybgsgx)|| "26".equals(ybgsgx)|| "28".equals(ybgsgx)|| "29".equals(ybgsgx)|| "30".equals(ybgsgx)) {
					ghzhb = "1";
				}
				if ("7".equals(ybgsgx) || "9".equals(ybgsgx)
						|| "11".equals(ybgsgx) || "13".equals(ybgsgx)|| "15".equals(ybgsgx)|| "16".equals(ybgsgx)
						|| "18".equals(ybgsgx) || "20".equals(ybgsgx)|| "21".equals(ybgsgx)|| "23".equals(ybgsgx)
						|| "24".equals(ybgsgx)|| "25".equals(ybgsgx)|| "27".equals(ybgsgx)|| "28".equals(ybgsgx)|| "29".equals(ybgsgx)|| "30".equals(ybgsgx)) {
					zf = "1";
				}
				if ("8".equals(ybgsgx) || "10".equals(ybgsgx)
						|| "12".equals(ybgsgx) || "14".equals(ybgsgx)|| "15".equals(ybgsgx)|| "17".equals(ybgsgx)
						|| "19".equals(ybgsgx) || "21".equals(ybgsgx)|| "22".equals(ybgsgx)|| "23".equals(ybgsgx)
						|| "24".equals(ybgsgx)|| "26".equals(ybgsgx)|| "27".equals(ybgsgx)|| "28".equals(ybgsgx)|| "29".equals(ybgsgx)|| "30".equals(ybgsgx)) {
					yh = "1";
				}
				mapStr.put("ybgsgx", ybgsgx);// 与本公司关系
				mapStr.put("kh", gkh);// 客户
				mapStr.put("gys", ggys);// 供应商
				mapStr.put("hzhb", ghzhb);// 合作伙伴
				mapStr.put("zf", zf);// 合作伙伴
				mapStr.put("yh", yh);// 合作伙伴
			}
			
			if ("1".equals(Util.null2String(rs.getString("check15")))) {
				mapStr.put("clrq",
						Util.null2String(rs.getString("xclrq")));// 成立日期
			}
			if ("1".equals(Util.null2String(rs.getString("check16")))) {
				mapStr.put("zcrq",
						Util.null2String(rs.getString("xzxrq")));// 注销日期
			}
			
			if ("1".equals(Util.null2String(rs.getString("check17")))) {
				mapStr.put("zczb", Util.null2String(rs.getString("mzczb")));// 注册资本
				mapStr.put("bzh", Util.null2String(rs.getString("mbzh")));// 实收资本
			}
			if ("1".equals(Util.null2String(rs.getString("check18")))) {
				mapStr.put("sszb", Util.null2String(rs.getString("msszb")));// 实收资本
				mapStr.put("bzh1", Util.null2String(rs.getString("mbzh1")));// 实收资本
			}
			if ("1".equals(Util.null2String(rs.getString("check19")))) {
				mapStr.put("khyh", Util.null2String(rs.getString("mkhyh")));// 开户银行
			}
			if ("1".equals(Util.null2String(rs.getString("check20")))) {
				mapStr.put("sh", Util.null2String(rs.getString("msh")));// 税号
			}
			
			if ("1".equals(Util.null2String(rs.getString("check21")))) {
				mapStr.put("yhzh", Util.null2String(rs.getString("myhzh")));// 银行账号
			}
			if ("1".equals(Util.null2String(rs.getString("check6")))) {
				mapStr.put("jyfw", Util.null2String(rs.getString("mjyfw")).replaceAll("&nbsp;", " ").replaceAll("&nbsp", " "));// 经营范围
			}
			if ("1".equals(Util.null2String(rs.getString("check22")))) {
				mapStr.put("Provider", Util.null2String(rs.getString("mProvider")));// 公司信息提供人
			}
			if ("1".equals(Util.null2String(rs.getString("chec30")))) {
				mapStr.put("xxtgfs", Util.null2String(rs.getString("mxxtgfs")));// 信息提供方式
			}
							
			
			
			mapStr.put("ModifyTime", now);// 信息更新时间
			if ("1".equals(Util.null2String(rs.getString("check31")))) {
				mapStr.put("zxwd", Util.null2String(rs.getString("mzxwd")));// 在线文档
			}
			//if ("1".equals(Util.null2String(rs.getString("check32")))) {
			//	mapStr.put("scfj", Util.null2String(rs.getString("mscfj")));// 上传附件
			//}
			if ("1".equals(Util.null2String(rs.getString("check23")))) {
				mapStr.put("Remark", Util.null2String(rs.getString("mRemark")));// 备注
			}				
			
			iu.updateGen(mapStr, "uf_custom", "id", zgs);
			SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd H:m:s");
			 now=sf.format(new Date());
			RecordSet rs1 = new RecordSet();
			String sql1="insert into uf_cm_planinfo(requestid,hblb,newid,oldid,inserttime,status) values("+requestid+",'0','"+zgs+"','"+bhbgs+"','"+now+"','0')";
			log.writeLog("插入公司合并记录sql"+sql1);
			rs1.executeSql(sql1);		
			sql1 = "update uf_custom set CutomStatus=3 where id="+bhbgs; 
			rs1.executeSql(sql1);	

		}

		return SUCCESS;
	}

}
