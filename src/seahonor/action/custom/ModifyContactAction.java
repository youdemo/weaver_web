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
		log.writeLog("��ʼ������ϵ��sql " + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			xglxr = Util.null2String(rs.getString("xglxr"));	
			sqr =  Util.null2String(rs.getString("xgsqr"));	
		}
		if(!"".equals(xglxr)){
			ModifyCustomAction gni = new ModifyCustomAction();
			gni.insertHistory("-63",tableName1, "id", xglxr);
			sql="select Max(id) as billid from "+tableName1+" where superid = "+xglxr;
			log.writeLog("��ʼ������ʷ��¼sql " + sql);
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
			mapStr.put("Name", Util.null2String(rs.getString("Name")));// ����(����)
			mapStr.put("xmyr", Util.null2String(rs.getString("xmyr")));// ����(Ӣ/��)
			mapStr.put("lxrcm", Util.null2String(rs.getString("lxrcm")));// ��ϵ�˲���
			mapStr.put("qz", Util.null2String(rs.getString("qz")));// Ⱥ��
			mapStr.put("customName", Util.null2String(rs.getString("customName")));// ������˾
			mapStr.put("khmcyr", Util.null2String(rs.getString("khmcyr")));// ��˾����(Ӣ/��)
			mapStr.put("GroupName", Util.null2String(rs.getString("GroupName")));// ��������
			mapStr.put("jtmcyr", Util.null2String(rs.getString("jtmcyr")));// ��������(Ӣ/��)
			mapStr.put("dept", Util.null2String(rs.getString("dept")));// ����(����)
			mapStr.put("bmyr", Util.null2String(rs.getString("bmyr")));// ����(Ӣ/��)
			mapStr.put("Position", Util.null2String(rs.getString("Position")));// ְλ(����)
			mapStr.put("zwyr", Util.null2String(rs.getString("zwyr")));// ְλ(Ӣ/��)
			mapStr.put("lxrsr", Util.null2String(rs.getString("lxrsr")));// ��ϵ������
			mapStr.put("lzsj", Util.null2String(rs.getString("lzsj")));// ��ְʱ��
			mapStr.put("tel", Util.null2String(rs.getString("tel")));// �칫�绰
			mapStr.put("mobile", Util.null2String(rs.getString("mobile")));// �ƶ��绰
			mapStr.put("zzdh", Util.null2String(rs.getString("zzdh")));// סլ�绰
			mapStr.put("Email", Util.null2String(rs.getString("Email")));// ��������
			mapStr.put("grwxh", Util.null2String(rs.getString("grwxh")));// ����΢�ź�
			mapStr.put("status", Util.null2String(rs.getString("status")));// ��ְ״̬
			mapStr.put("Fax", Util.null2String(rs.getString("Fax")));// ������
			mapStr.put("Postcode", Util.null2String(rs.getString("Postcode")));// ��������
			mapStr.put("bgdz", Util.null2String(rs.getString("bgdz")));// �칫��ַ(����)
			mapStr.put("bgdzyr", Util.null2String(rs.getString("bgdzyr")));// �칫��ַ(Ӣ/��)
			mapStr.put("jtdz", Util.null2String(rs.getString("jtdz")));// ��ͥ��ַ
			mapStr.put("ybgsdgx", ybgsgx);// �뱾��˾�Ĺ�ϵ
			mapStr.put("kh", gkh);// �ͻ�
			mapStr.put("gys", ggys);// ��Ӧ��
			mapStr.put("hzhb", ghzhb);// �������
			mapStr.put("xxtgr", Util.null2String(rs.getString("xxtgr")));// ��Ϣ�ṩ��
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// ��Ϣ�ṩ��ʽ
			mapStr.put("picHead", Util.null2String(rs.getString("picHead")));// ��Ƭ����
			mapStr.put("picEnd", Util.null2String(rs.getString("picEnd")));// ��Ƭ����
			mapStr.put("Version", version);//��Ϣ�汾
			mapStr.put("scfj", Util.null2String(rs.getString("scfj")));// �ϴ�����
			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// �����ĵ�
			mapStr.put("bz", Util.null2String(rs.getString("bz")));// ��ע
			iu.updateGen(mapStr,tableName1, "id", billid);
		}
		
		
		// TODO Auto-generated method stub
		return SUCCESS;
	}
}
