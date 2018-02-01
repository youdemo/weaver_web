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
		log.writeLog("��ʼ��ϵ�˺ϲ�sql"+sql);
		rs.executeSql(sql);
		if (rs.next()) {
			String zlxr = Util.null2String(rs.getString("zlxr"));
			;// ����˾
			String bhblxr = Util.null2String(rs.getString("bhblxr"));// ���ϲ���˾
			Map<String, String> mapStr = new HashMap<String, String>();
			// mapStr.put("seqNO", seqNO);// ��˾����

			if ("1".equals(Util.null2String(rs.getString("check1")))) {
				mapStr.put("Name", Util.null2String(rs.getString("mxmzw")));// ����(����)
			}
			if ("1".equals(Util.null2String(rs.getString("check28")))) {
				mapStr.put("wwm", Util.null2String(rs.getString("mwwm")));// ����(����)
			}
			if ("1".equals(Util.null2String(rs.getString("check2")))) {
				mapStr.put("qz", Util.null2String(rs.getString("xqz")));// Ⱥ��
			}
			if ("1".equals(Util.null2String(rs.getString("check3")))) {
				mapStr.put("mobile", Util.null2String(rs.getString("myddh")));// �ƶ��绰
			}
			if ("1".equals(Util.null2String(rs.getString("check4")))) {
				mapStr.put("Email", Util.null2String(rs.getString("mdzyx")));// ��������
			}
			if ("1".equals(Util.null2String(rs.getString("check5")))) {
				mapStr.put("tel", Util.null2String(rs.getString("mbgdh")));// �칫�绰
			}
			if ("1".equals(Util.null2String(rs.getString("check6")))) {
				mapStr.put("Fax", Util.null2String(rs.getString("mswcz")));// ������
			}
			if ("1".equals(Util.null2String(rs.getString("check7")))) {
				mapStr.put("grwxh", Util.null2String(rs.getString("mgrwxh")));// ����΢�ź�
			}
			if ("1".equals(Util.null2String(rs.getString("check8")))) {
				mapStr.put("Postcode", Util.null2String(rs.getString("myzbm")));// ��������
			}
			if ("1".equals(Util.null2String(rs.getString("check9")))) {
				mapStr.put("lxrcm", Util.null2String(rs.getString("mlxrcm")));// ��ϵ�˲���
			}
			if ("1".equals(Util.null2String(rs.getString("check10")))) {
				mapStr.put("GroupName", Util.null2String(rs.getString("mssjt")));// ��������
			}
			
			if ("1".equals(Util.null2String(rs.getString("check11")))) {
				mapStr.put("customName", Util.null2String(rs.getString("mssgs")));// ������˾
			}
			if ("1".equals(Util.null2String(rs.getString("check12")))) {
				mapStr.put("bgdz", Util.null2String(rs.getString("mbgdzzw")));// �칫��ַ(����)
			}
			if ("1".equals(Util.null2String(rs.getString("check13")))) {
				mapStr.put("Position", Util.null2String(rs.getString("mzwzw")));// ְλ(����)
			}
			if ("1".equals(Util.null2String(rs.getString("check14")))) {
				mapStr.put("dept", Util.null2String(rs.getString("mbmzw")));// ����(����)
			}
			if ("1".equals(Util.null2String(rs.getString("check15")))) {
				mapStr.put("zzdh", Util.null2String(rs.getString("mzzdh")));// סլ�绰
			}
			if ("1".equals(Util.null2String(rs.getString("check16")))) {
				mapStr.put("jtdz", Util.null2String(rs.getString("mjtdz")));// ��ͥ��ַ
			}
			if ("1".equals(Util.null2String(rs.getString("check17")))) {
				mapStr.put("lxrsr", Util.null2String(rs.getString("mlxrsr")));// ��ϵ������
			}
			if ("1".equals(Util.null2String(rs.getString("check18")))) {
				mapStr.put("status", Util.null2String(rs.getString("mzzzt")));// ��ְ״̬
			}

			if ("1".equals(Util.null2String(rs.getString("check19")))) {
				mapStr.put("lzsj", Util.null2String(rs.getString("mlzsj")));// ��ְʱ��
			}
	
			if ("1".equals(Util.null2String(rs.getString("check20")))) {
				mapStr.put("xxtgr", Util.null2String(rs.getString("mxxtgr")));// ��Ϣ�ṩ��
			}
			
			if ("1".equals(Util.null2String(rs.getString("check21")))) {
				mapStr.put("xxtgfs", Util.null2String(rs.getString("mxxtgfs")));// ��Ϣ�ṩ��ʽ
			}
				
			if ("1".equals(Util.null2String(rs.getString("check22")))) {
				mapStr.put("picHead", Util.null2String(rs.getString("mmpzm")));// ��Ƭ����
			}
			if ("1".equals(Util.null2String(rs.getString("check23")))) {
				mapStr.put("picEnd", Util.null2String(rs.getString("mmpfm")));// ��Ƭ����
			}
			
			mapStr.put("ModifyTime", now);// ��Ϣ����ʱ��
			//if ("1".equals(Util.null2String(rs.getString("check24")))) {
			//	mapStr.put("scfj", Util.null2String(rs.getString("mscfj")));// �ϴ�����
			//}
		
			
			if ("1".equals(Util.null2String(rs.getString("check25")))) {
				mapStr.put("zxwd", Util.null2String(rs.getString("mzxwd")));// �����ĵ�
			}
			if ("1".equals(Util.null2String(rs.getString("check26")))) {
				mapStr.put("bz", Util.null2String(rs.getString("mbz")));// ��ע
			}
			if ("1".equals(Util.null2String(rs.getString("check27")))) {
				mapStr.put("gjz", Util.null2String(rs.getString("mgjz")));// ��ע
			}
			iu.updateGen(mapStr, "uf_Contacts", "id", zlxr);
			SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd H:m:s");
			 now=sf.format(new Date());
			RecordSet rs1 = new RecordSet();
			String sql1="insert into uf_cm_planinfo(requestid,hblb,newid,oldid,inserttime,status) values("+requestid+",'1','"+zlxr+"','"+bhblxr+"','"+now+"','0')";
			log.writeLog("���빫˾�ϲ���¼sql"+sql1);
			rs1.executeSql(sql1);		
			sql1 = "update uf_Contacts set dealStatus=3 where id="+bhblxr; 
			rs1.executeSql(sql1);	

		}

		return SUCCESS;
	}

}
