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
				log.writeLog("��ʼ������ʷ��¼sql " + sql+"mcjr"+mcjr);
				rs.executeSql(sql);
				if(rs.next()){
					billid= Util.null2String(rs.getString("billid"));	
					ModeRightInfo ModeRightInfo = new ModeRightInfo();
					ModeRightInfo.editModeDataShare(Integer.valueOf(mcjr),52,Integer.valueOf(billid));			
				}
			}
		}
		
		sql = "select * from " + tableName + " where requestid= " + requestid;
		log.writeLog("��ʼУ�Թ�˾sql " + sql);
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
			// mapStr.put("customCode", sysNo);//��˾����
			mapStr.put("cjr", Util.null2String(rs.getString("mcjr")));// ������
			mapStr.put("cjrq", Util.null2String(rs.getString("mcjrq")));// ��������
			// mapStr.put("cjsj", Util.null2String(rs.getString("cjsj")));//����ʱ��
			// mapStr.put("cjxq", Util.null2String(rs.getString("cjxq")));//��������
			mapStr.put("sqr", Util.null2String(rs.getString("msqr")));// ������
			mapStr.put("sqrq", Util.null2String(rs.getString("msqrq")));// ��������
			// mapStr.put("sqsj", Util.null2String(rs.getString("sqsj")));//����ʱ��
			// mapStr.put("sqxq", Util.null2String(rs.getString("sqxq")));//��������
			mapStr.put("customName",
					Util.null2String(rs.getString("mcustomName")));// ��˾����(����)
			mapStr.put("khmcyr", Util.null2String(rs.getString("mkhmcyr")));// ��˾����(Ӣ/��)
			mapStr.put("gsjczw",
					Util.null2String(rs.getString("mgsjczw")));// ��˾���(����)
			mapStr.put("dm", Util.null2String(rs.getString("mdm")));
			mapStr.put("zh", Util.null2String(rs.getString("mzh")));
			mapStr.put("hy", Util.null2String(rs.getString("mhy")));
			mapStr.put("Address", Util.null2String(rs.getString("mAddress")));// ��˾��ַ(����)
			mapStr.put("gsdz", Util.null2String(rs.getString("mgsdz")));// ��˾��ַ(Ӣ/��)
			mapStr.put("Telphone", Util.null2String(rs.getString("mTelphone")));// ��˾�绰
			mapStr.put("Email", Util.null2String(rs.getString("mEmail")));// ��˾����
			mapStr.put("Website", Util.null2String(rs.getString("mWebsite")));// ��˾��վ
			mapStr.put("Fax", Util.null2String(rs.getString("mFax")));// ��˾����
			mapStr.put("zcdz", Util.null2String(rs.getString("mzcdz")));// ע���ַ
			mapStr.put("Postcode", Util.null2String(rs.getString("mPostcode")));// ��������
			mapStr.put("gswxh", Util.null2String(rs.getString("mgswxh")));// ��˾΢�ź�
			mapStr.put("Country", Util.null2String(rs.getString("mCountry")));// ��������
			mapStr.put("CustomGroup",
					Util.null2String(rs.getString("mCustomGroup")));// ��������
			mapStr.put("industryType",
					Util.null2String(rs.getString("mindustryType")));// ��ҵ����
			mapStr.put("ybgsgx", ybgsgx);// �뱾��˾��ϵ
			mapStr.put("kh", gkh);// �ͻ�
			mapStr.put("gys", ggys);// ��Ӧ��
			mapStr.put("hzhb", ghzhb);// �������
			mapStr.put("Provider", Util.null2String(rs.getString("mProvider")));// ��˾��Ϣ�ṩ��
			mapStr.put("khyh", Util.null2String(rs.getString("mkhyh")));// ��������
			mapStr.put("xxtgfs", Util.null2String(rs.getString("mxxtgfs")));// ��Ϣ�ṩ��ʽ
			mapStr.put("sh", Util.null2String(rs.getString("msh")));// ˰��
			mapStr.put("yhzh", Util.null2String(rs.getString("myhzh")));// �����˺�
			mapStr.put("zczb", Util.null2String(rs.getString("mzczb")));// ע���ʱ�
			mapStr.put("sszb", Util.null2String(rs.getString("msszb")));// ʵ���ʱ�
			
			mapStr.put("zxwd", Util.null2String(rs.getString("mzxwd")));// �����ĵ�
			mapStr.put("scfj", Util.null2String(rs.getString("mscfj")));// �ϴ�����
			if("0".equals(lb)){
				mapStr.put("CutomStatus", "1");// ��˾״̬
				mapStr.put("Version", "0");//��Ϣ�汾
			}else{
				mapStr.put("Version", version);//��Ϣ�汾
			}
			
			mapStr.put("ModifyTime", now);// ��Ϣ����ʱ��
			mapStr.put("Remark", Util.null2String(rs.getString("mRemark")));// ��ע
			iu.updateGen(mapStr, "uf_custom", "id", billid);
		}

		return SUCCESS;
	}
public boolean insertHistory(String billid,String table_name,String uqField,String uqVal) {
		
		BaseBean log  = new BaseBean();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();

		// ��� ����ֶ�
		List<String> list = new ArrayList<String>();
 		String sql = "select fieldname from workflow_billfield where billid="+billid+" order by dsporder";
 	//	log.writeLog("insertNow(1) = " + sql);
		rs.executeSql(sql);
		while(rs.next()){
			String tmp_1 = Util.null2String(rs.getString("fieldname"));
			
			// ���������ų�
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
				// ѭ����ȡ   ��Ϊ��ֵ����ϳ�sql
				for(String field : list){
					String tmp_x = Util.null2String(rs.getString(field));
					if(tmp_x.length() > 0)
						mapStr.put(field, tmp_x);
				}
			}
			
			// �����Ҫ���������id
			if(mapStr.size() > 0){
				mapStr.put("SuperID", Util.null2String(rs.getString("ID")));
				// ���������id
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
