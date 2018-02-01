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

public class UpdateBrrowByGSKCX implements Action{
	BaseBean log = new BaseBean();
	@Override
	public String execute(RequestInfo info) {
		String workflowID = info.getWorkflowid();
		String requestid = info.getRequestid();
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormate = new SimpleDateFormat("HH:mm:ss");
		String nowDate = dateFormate.format(new Date());
		String nowTime = timeFormate.format(new Date());
		RecordSet rs = new RecordSet();
		String tableName = "";
		String mainid = "";
		String jkr = "";
		String pzhm="";
		String jklcdh3 = "";
		String gwkmx = "";
		String sqrq = "";
		String jkid_dt2="";
		String cxjkje_dt2="";
		String jkye_dt2="";
		String sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID
				+ ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		sql="select * from "+tableName+" where requestid="+requestid;
		rs.executeSql(sql);
		if(rs.next()){
			mainid = Util.null2String(rs.getString("id"));
			jkr = Util.null2String(rs.getString("jkr"));
			jklcdh3 = Util.null2String(rs.getString("jklcdh3"));
			gwkmx = Util.null2String(rs.getString("gwkmx"));
			sqrq = Util.null2String(rs.getString("sqrq"));
			pzhm = Util.null2String(rs.getString("pzhm"));
		}
		sql="select * from "+tableName+"_dt2 where mainid="+mainid;
		rs.executeSql(sql);
		while(rs.next()){
			jkid_dt2 =  Util.null2String(rs.getString("jkid"));
			cxjkje_dt2 =  Util.null2String(rs.getString("cxjkje")).replaceAll(",", "");;
			jkye_dt2 =  Util.null2String(rs.getString("jkye")).replaceAll(",", "");;
			if(!"".equals(jkye_dt2)){
				insertRepayment(jkid_dt2,jkr,jkye_dt2,sqrq,requestid,nowDate,nowTime,"0","2",pzhm);
			}
		}
		String sqtkjehz = doDelaymoney(mainid,tableName,jkr,sqrq,requestid,nowDate,nowTime,pzhm);
		sql="update "+tableName+" set sqtkjehz='"+sqtkjehz+"' where requestid="+requestid;
		rs.executeSql(sql);
		
		return SUCCESS;
	}
	
	public String doDelaymoney(String mainId,String tableName,String jkr,String sqrq,String requestid,String nowDate,String nowTime,String pzhm ){
		RecordSet rs = new RecordSet();
		String ye="";
		String id="";
		String jkye="";
		String hke="0";//还款额
		String sql="select isnull(sum(isnull(cast(REPLACE(cxjkje,',','') as decimal(10,2) ),0)),0)-isnull(sum(isnull(cast(REPLACE(jkye,',','') as decimal(10,2) ),0)),0) as ye from "+tableName+"_dt2 where mainid="+mainId;
		rs.executeSql(sql);
		if(rs.next()){
			ye=Util.null2String(rs.getString("ye"));
		}
		if(Util.getFloatValue(ye,0)>Util.getFloatValue("0",0)){
			hke=ye;
			sql="select id,jkye from uf_personal_borrow where jkr='"+jkr+"' and bz='0' and isnull(jkye,0)>0 and sfyq='0' order by jkdqr asc";
			rs.executeSql(sql);
			while(rs.next()){
				if("0".equals(hke)){
					break;
				}
				id= Util.null2String(rs.getString("id"));
				jkye= Util.null2String(rs.getString("jkye")).replaceAll(",", "");
				
				if(Util.getFloatValue(hke,0)>Util.getFloatValue(jkye,0)){
					hke=sub(hke, jkye);
					insertRepayment(id,jkr,jkye,nowDate,requestid,nowDate,nowTime,"0","2",pzhm);
				}else{
					insertRepayment(id,jkr,hke,nowDate,requestid,nowDate,nowTime,"0","2",pzhm);
					hke = "0";
				}
				
			}
			
		}

		return hke;
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
}
