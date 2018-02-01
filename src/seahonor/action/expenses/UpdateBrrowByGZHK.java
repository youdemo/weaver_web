package seahonor.action.expenses;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import seahonor.util.SysNoForSelf;

import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateBrrowByGZHK implements Action{

	@Override
	public String execute(RequestInfo info) {
		String workflowID = info.getWorkflowid();
		String requestid = info.getRequestid();
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormate = new SimpleDateFormat("HH:mm:ss");
		String nowDate = dateFormate.format(new Date());
		String nowTime = timeFormate.format(new Date());
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		String sql_dt = "";
		String tableName = "";
		String mainid = "";
		String gzkkje_dt1="";//工资扣除金额
		String rskcje_dt1="";//人事扣除金额
		String ryid_dt1="";//人员id
		String pzhm_dt1 = "";
		String id_dt1="";
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
		}
		sql="select * from "+tableName+"_dt1 where mainid="+mainid;
		rs.executeSql(sql);
		while(rs.next()){
			id_dt1 = Util.null2String(rs.getString("id"));
			gzkkje_dt1 = Util.null2String(rs.getString("gzkkje")).replaceAll(",", "");
			rskcje_dt1 = Util.null2String(rs.getString("rskcje")).replaceAll(",", "");
			ryid_dt1 = Util.null2String(rs.getString("ryid"));
			pzhm_dt1= Util.null2String(rs.getString("pzhm"));
			if(Util.getFloatValue(rskcje_dt1,0)>Util.getFloatValue("0",0)){
			String syje=offsetBorrow(tableName,mainid,ryid_dt1,rskcje_dt1,nowDate,nowTime,requestid,pzhm_dt1);
			sql_dt="update "+tableName+"_dt1 set syje='"+syje+"' where id="+id_dt1;
			rs_dt.executeSql(sql_dt);
			}
		}
		
		return SUCCESS;
	}
    
	public String  offsetBorrow(String tableName,String mainId,String ryid,String rskcje,String nowDate,String nowTime,String requestid,String pzhm){
		RecordSet rs = new RecordSet();
		String jkid="";
		String jkr="";
		String jkye = "";
		String jkdqr="";
		String bz="";
		String hl="";
		String hke=rskcje;
		String sql="select * from ("+
		" select b.id,b.jkr,b.jkye,b.jkdqr,b.bz,'1' as hl from "+tableName+"_dt2 a,uf_personal_borrow b where  a.jkid=b.id and a.mainid="+mainId+" and a.jkrxm="+ryid+" "+
		" union "+
		" select b.id,b.jkr,b.jkye,b.jkdqr,b.bz,a.hl from "+tableName+"_dt3 a,uf_personal_borrow b where  a.jkid=b.id and a.mainid="+mainId+" and a.jkrxm="+ryid+") a "+
		" order by jkdqr asc,id asc";
		rs.executeSql(sql);
		while(rs.next()){
			if("0".equals(hke)){
				break;
			}
			jkid = Util.null2String(rs.getString("id"));
			jkr = Util.null2String(rs.getString("jkr"));
			jkye = Util.null2String(rs.getString("jkye")).replaceAll(",", "");
			jkdqr = Util.null2String(rs.getString("jkdqr"));
			bz = Util.null2String(rs.getString("bz"));
			hl = Util.null2String(rs.getString("hl"));
			if(Util.getFloatValue(jkye,0)<=Util.getFloatValue("0",0)){
				continue;
			}
			if(Util.getFloatValue(hke,0)>Util.getFloatValue(ride(jkye, hl),0)){
				hke=sub(hke, ride(jkye, hl));
				insertRepayment(jkid,jkr,jkye,nowDate,requestid,nowDate,nowTime,bz,"3",pzhm);
			}else{
				insertRepayment(jkid,jkr,divide(hke, hl),nowDate,requestid,nowDate,nowTime,bz,"3",pzhm);
				hke = "0";
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
	public String ride(String a,String b){
    	RecordSet rs = new RecordSet();
    	String result="";
    	String sql="select cast(cast(REPLACE(isnull('"+a+"',0),',','') as decimal(10,2) )*cast(REPLACE(isnull('"+b+"',0),',','') as decimal(10,4) ) as  decimal(10,2) ) as je";
		rs.executeSql(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("je"));
		}
		return result;
		
    }
	public String divide(String a,String b){
    	RecordSet rs = new RecordSet();
    	String result="";
    	if(Util.getFloatValue(b)<=0){
    		return "0";
    	}
    	String sql="select cast(cast(REPLACE(isnull('"+a+"',0),',','') as decimal(10,2) )/cast(REPLACE(isnull('"+b+"',0),',','') as decimal(10,4) ) as  decimal(10,2) ) as je";
		rs.executeSql(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("je"));
		}
		return result;
		
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
