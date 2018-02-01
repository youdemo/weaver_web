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

public class ModifyCustomAction implements Action{
	BaseBean log = new BaseBean();
	@Override
	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String tableName = "";
		String tableName1="uf_custom";
		String xggs = "";
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
		sql = "select xggs,xgsqr from " + tableName + " where requestid= " + requestid;
		log.writeLog("��ʼ���¹�˾sql " + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			xggs = Util.null2String(rs.getString("xggs"));	
			sqr =  Util.null2String(rs.getString("xgsqr"));	
		}
		if(!"".equals(xggs)){
			GeneralNowInsert gni = new GeneralNowInsert();
			insertHistory("-59",tableName1, "id", xggs);
			sql="select Max(id) as billid from "+tableName1+" where superid = "+xggs;
			log.writeLog("��ʼ������ʷ��¼sql " + sql+"sqr"+sqr);
			rs.executeSql(sql);
			if(rs.next()){
				billid= Util.null2String(rs.getString("billid"));	
				ModeRightInfo ModeRightInfo = new ModeRightInfo();
				ModeRightInfo.editModeDataShare(Integer.valueOf(sqr),52,Integer.valueOf(billid));			
			}
		}
		String version ="0";
		sql="select Version+1 as Version from "+tableName1+" where id="+xggs;
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
			billid = Util.null2String(rs.getString("xggs"));
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
			//mapStr.put("cjr", Util.null2String(rs.getString("mcjr")));// ������
			//mapStr.put("cjrq", Util.null2String(rs.getString("mcjrq")));// ��������
			// mapStr.put("cjsj", Util.null2String(rs.getString("cjsj")));//����ʱ��
			// mapStr.put("cjxq", Util.null2String(rs.getString("cjxq")));//��������
			//mapStr.put("sqr", Util.null2String(rs.getString("msqr")));// ������
			//mapStr.put("sqrq", Util.null2String(rs.getString("msqrq")));// ��������
			// mapStr.put("sqsj", Util.null2String(rs.getString("sqsj")));//����ʱ��
			// mapStr.put("sqxq", Util.null2String(rs.getString("sqxq")));//��������
			mapStr.put("customName",
					Util.null2String(rs.getString("customName")));// ��˾����(����)
			mapStr.put("khmcyr", Util.null2String(rs.getString("khmcyr")));// ��˾����(Ӣ/��)
			mapStr.put("Address", Util.null2String(rs.getString("Address")));// ��˾��ַ(����)
			mapStr.put("gsdz", Util.null2String(rs.getString("gsdz")));// ��˾��ַ(Ӣ/��)
			mapStr.put("Telphone", Util.null2String(rs.getString("Telphone")));// ��˾�绰
			mapStr.put("Email", Util.null2String(rs.getString("Email")));// ��˾����
			mapStr.put("Website", Util.null2String(rs.getString("Website")));// ��˾��վ
			mapStr.put("Fax", Util.null2String(rs.getString("Fax")));// ��˾����
			mapStr.put("zcdz", Util.null2String(rs.getString("zcdz")));// ע���ַ
			mapStr.put("Postcode", Util.null2String(rs.getString("Postcode")));// ��������
			mapStr.put("gswxh", Util.null2String(rs.getString("gswxh")));// ��˾΢�ź�
			mapStr.put("Country", Util.null2String(rs.getString("Country")));// ��������
			mapStr.put("CustomGroup",
					Util.null2String(rs.getString("CustomGroup")));// ��������
			mapStr.put("industryType",
					Util.null2String(rs.getString("industryType")));// ��ҵ����
			mapStr.put("ybgsgx", ybgsgx);// �뱾��˾��ϵ
			mapStr.put("kh", gkh);// �ͻ�
			mapStr.put("gys", ggys);// ��Ӧ��
			mapStr.put("hzhb", ghzhb);// �������
			mapStr.put("Provider", Util.null2String(rs.getString("Provider")));// ��˾��Ϣ�ṩ��
			mapStr.put("khyh", Util.null2String(rs.getString("khyh")));// ��������
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// ��Ϣ�ṩ��ʽ
			mapStr.put("sh", Util.null2String(rs.getString("sh")));// ˰��
			//mapStr.put("CutomStatus", "1");// ��˾״̬
			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// �����ĵ�
			mapStr.put("scfj", Util.null2String(rs.getString("scfj")));// �ϴ�����
			 mapStr.put("Version", version);//��Ϣ�汾
			mapStr.put("ModifyTime", now);// ��Ϣ����ʱ��
			mapStr.put("Remark", Util.null2String(rs.getString("Remark")));// ��ע
			iu.updateGen(mapStr, "uf_custom", "id", billid);
		}
		
		// TODO Auto-generated method stub
		return SUCCESS;
	}
}
	
