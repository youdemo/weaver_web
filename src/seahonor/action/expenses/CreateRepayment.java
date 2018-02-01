package seahonor.action.expenses;

import java.text.SimpleDateFormat;
import java.util.Date;
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

public class CreateRepayment  implements Action {
	BaseBean log = new BaseBean();
	@Override
	public String execute(RequestInfo info) {
		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();
		RecordSet rs = new RecordSet();
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormate = new SimpleDateFormat("HH:mm:ss");
		String nowDate = dateFormate.format(new Date());
		String nowTime = timeFormate.format(new Date());
		String tableName = "";
		String mainid = "";
		String hkr = "";// 借款人
		String sqrq = "";// 申请日期
		String hkbz = "";// 借款币种
		String hkfs_main = "";
		String bz_dt2="";
		String skje_dt2="";
		String pzhm_dt2="";
		String bz_dt3="";
		String skje_dt3="";
		String pzhm_dt3="";
		String hkfs="";
		String sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID
				+ ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		
		rs.executeSql(sql);
		if (rs.next()) {
			mainid = Util.null2String(rs.getString("id"));
			hkr = Util.null2String(rs.getString("hkr"));
			sqrq = Util.null2String(rs.getString("sqrq"));

			hkfs_main = Util.null2String(rs.getString("hkfs"));
		}
		if ("2".equals(hkfs_main)) {
			sql="select hkbz,cnskje,hkfs,pzhm from "+tableName+"_dt2 where mainid="+mainid;		
			rs.executeSql(sql);
			while(rs.next()){
				hkfs = Util.null2String(rs.getString("hkfs"));
				bz_dt2 = Util.null2String(rs.getString("hkbz"));
				pzhm_dt2 = Util.null2String(rs.getString("pzhm"));
				skje_dt2 = Util.null2String(rs.getString("cnskje")).replaceAll(",", "");
				createDTCard(requestid, tableName, mainid, hkr, sqrq, nowDate, nowTime,bz_dt2,skje_dt2,hkfs,pzhm_dt2);
			}
			sql="select hkbz,cnskje,hkfs,pzhm from "+tableName+"_dt3 where mainid="+mainid;
			rs.executeSql(sql);
			while(rs.next()){
				hkfs = Util.null2String(rs.getString("hkfs"));
				pzhm_dt3 = Util.null2String(rs.getString("pzhm"));
				bz_dt3 = Util.null2String(rs.getString("hkbz"));
				skje_dt3 = Util.null2String(rs.getString("cnskje")).replaceAll(",", "");
				createDTCard(requestid, tableName, mainid, hkr, sqrq, nowDate, nowTime,bz_dt3,skje_dt3,hkfs,pzhm_dt3);
			}
			
		} else if ("0".equals(hkfs_main)) {
			
			sql="select hkbz,cnskje,hkfs,pzhm from "+tableName+"_dt2 where mainid="+mainid;
			rs.executeSql(sql);
			while(rs.next()){
				hkfs = Util.null2String(rs.getString("hkfs"));
				bz_dt2 = Util.null2String(rs.getString("hkbz"));
				pzhm_dt2 = Util.null2String(rs.getString("pzhm"));
				skje_dt2 = Util.null2String(rs.getString("cnskje")).replaceAll(",", "");
				createDTCard(requestid, tableName, mainid, hkr, sqrq, nowDate, nowTime,bz_dt2,skje_dt2,hkfs,pzhm_dt2);
			}
		} else if ("1".equals(hkfs_main)) {
			sql="select hkbz,cnskje,hkfs,pzhm from "+tableName+"_dt3 where mainid="+mainid;			
			rs.executeSql(sql);
			while(rs.next()){
				hkfs = Util.null2String(rs.getString("hkfs"));
				bz_dt3 = Util.null2String(rs.getString("hkbz"));
				pzhm_dt3 = Util.null2String(rs.getString("pzhm"));
				skje_dt3 = Util.null2String(rs.getString("cnskje")).replaceAll(",", "");
				createDTCard(requestid, tableName, mainid, hkr, sqrq, nowDate, nowTime,bz_dt3,skje_dt3,hkfs,pzhm_dt3);
			}
		}
		return SUCCESS;
	}
	public void createDTCard(String requestid, String tableName,
			String mainid, String jkr, String sqrq, String nowDate,  String nowTime,String bz,String skje,String hkfs,String pzhm) {
		RecordSet rs = new RecordSet();
		String id="";
		String jkye="";
		String hke=skje;//还款额
		String sql="select id,jkye from uf_personal_borrow where jkr='"+jkr+"' and bz='"+bz+"' and isnull(jkye,0)>0 and sfyq='0' order by jkdqr asc";
		rs.executeSql(sql);
		while(rs.next()){
			if("0".equals(hke)){
				break;
			}
			id= Util.null2String(rs.getString("id"));
			jkye= Util.null2String(rs.getString("jkye")).replaceAll(",", "");
			if(Util.getFloatValue(hke,0)>Util.getFloatValue(jkye,0)){
				hke=sub(hke, jkye);
				insertRepayment(id,jkr,jkye,sqrq,requestid,nowDate,nowTime,bz,hkfs,pzhm);
			}else{
				insertRepayment(id,jkr,hke,sqrq,requestid,nowDate,nowTime,bz,hkfs,pzhm);
				hke = "0";
			}
			
		}
		sql="select id,jkye from uf_personal_borrow where jkr='"+jkr+"' and bz='"+bz+"' and isnull(jkye,0)>0 and sfyq <>'0' order by jkdqr asc";
		rs.executeSql(sql);
		while(rs.next()){
			if("0".equals(hke)){
				break;
			}
			id= Util.null2String(rs.getString("id"));
			jkye= Util.null2String(rs.getString("jkye")).replaceAll(",", "");
			if(Util.getFloatValue(hke,0)>Util.getFloatValue(jkye,0)){
				hke=sub(hke, jkye);
				insertRepayment(id,jkr,jkye,sqrq,requestid,nowDate,nowTime,bz,hkfs,pzhm);
			}else{		
				insertRepayment(id,jkr,hke,sqrq,requestid,nowDate,nowTime,bz,hkfs,pzhm);
				hke = "0";
			}
			
		}

	}

	
	public String sub(String a,String b){
    	RecordSet rs = new RecordSet();
    	String result="";
    	String sql="select cast(cast('"+a+"' as numeric(18,4))-cast('"+b+"' as numeric(18,4))as numeric(18,2))as je  ";
		rs.executeSql(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("je"));
		}
		return result;
		
    }
	public void insertRepayment(String jkid,String hkr,String hkje,String hkrq,String xglc,String nowDate,String nowTime,String bz,String hkfs,String pzhm){
		String table_name_1 = "uf_person_repayment";
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		InsertUtil iu = new InsertUtil();
		SysNoForSelf sns = new SysNoForSelf();
		String sysNo = sns.getNum("HK", table_name_1, 4);
		Map<String, String> mapStr = new HashMap<String, String>();
		mapStr.put("hkr", hkr);// 
		mapStr.put("jkid", jkid);
		mapStr.put("hkje", hkje);
		mapStr.put("hkrq", hkrq);
		mapStr.put("xglc", xglc);
		mapStr.put("hkxh", sysNo);
		mapStr.put("bz", bz);
		mapStr.put("hkfs", hkfs);
		mapStr.put("pzhm", pzhm);
		mapStr.put("modedatacreater", hkr);
		mapStr.put("modedatacreatertype", "0");
		mapStr.put("modedatacreatedate", nowDate);
		mapStr.put("modedatacreatetime", nowTime);
		mapStr.put("formmodeid", "239");
		iu.insert(mapStr, table_name_1);
		String hkid = "";
		String sql = "select id from " + table_name_1 + " where hkxh='" + sysNo
				+ "'";
		rs.executeSql(sql);
		if (rs.next()) {
			hkid = Util.null2String(rs.getString("id"));
		}
		ModeRightInfo ModeRightInfo = new ModeRightInfo();
		ModeRightInfo.editModeDataShare(Integer.valueOf(hkr), 239,
				Integer.valueOf(hkid));
		sql = "update uf_personal_borrow set jkye=jkye-"+hkje+",hkje=hkje+"+hkje+" where id="+jkid;
		rs.executeSql(sql);
	}
}
