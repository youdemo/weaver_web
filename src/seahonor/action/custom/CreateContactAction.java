package seahonor.action.custom;

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

public class CreateContactAction implements Action {
	BaseBean log = new BaseBean();

	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		SysNoForSelf sns = new SysNoForSelf();
		InsertUtil iu = new InsertUtil();
		String sql_dt = "";
		String tableName = "";
		String table_name_1 = "uf_Contacts";
		String contactId = "";
		String mainid = "";
		String cjr = "";
		String cjrq = "";
		String cjsj = "";
		String cjxq = "";
		String sqr = "";
		String sqrq = "";
		String sqsj = "";
		String sqxq = "";
		String now = "";
		String seqNO = "";
		String lxrcm = "";
		String sql = "select CONVERT(varchar(30),getdate(),21) as nowTime ";
		rs.executeSql(sql);
		if (rs.next()) {
			now = Util.null2String(rs.getString("nowTime"));
		}
		sql = "Select tablename,id From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id=" + workflow_id + ")";
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		rs.executeSql(sql);
		if (rs.next()) {
			mainid = Util.null2String(rs.getString("id"));
			cjr = Util.null2String(rs.getString("cjr"));
			cjrq = Util.null2String(rs.getString("cjrq"));
			cjsj = Util.null2String(rs.getString("cjsj"));
			cjxq = Util.null2String(rs.getString("cjxq"));
			sqr = Util.null2String(rs.getString("sqr"));
			sqrq = Util.null2String(rs.getString("sqrq"));
			sqsj = Util.null2String(rs.getString("sqsj"));
			sqxq = Util.null2String(rs.getString("sqxq"));
		}
		sql = "select * from " + tableName + "_dt1 where mainid=" + mainid;
		rs.executeSql(sql);
		while (rs.next()) {

			String sysNo = sns.getNum("CCDL", table_name_1, 4);
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("SysNo", sysNo);// ��ϵ�˴���
			mapStr.put("cjr", cjr);// ������
			mapStr.put("cjrq", cjrq);// ��������
			mapStr.put("cjsj", cjsj);// ����ʱ��
			mapStr.put("cjxq", cjxq);// ��������
			mapStr.put("sqr", sqr);// ������
			mapStr.put("sqrq", sqrq);// ��������
			mapStr.put("sqsj", sqsj);// ����ʱ��
			mapStr.put("sqxq", sqxq);// ��������
			mapStr.put("Name", Util.null2String(rs.getString("xmzw")));// ����(����)
			// mapStr.put("xmyr", Util.null2String(rs.getString("xmyr3")));//
			// ����(Ӣ/��)
			mapStr.put("wwm", Util.null2String(rs.getString("wwm")));
			lxrcm = Util.null2String(rs.getString("lxrcm"));
			mapStr.put("qz", Util.null2String(rs.getString("qz")));// Ⱥ��
			if ("0".equals(lxrcm)) {
				mapStr.put("GroupName", Util.null2String(rs.getString("ssjt")));// ��������
				mapStr.put("lxrcm", lxrcm);// ��ϵ�˲���
			}
			if ("1".equals(lxrcm)) {
				mapStr.put("customName", Util.null2String(rs.getString("ssgs")));// ������˾
				mapStr.put("lxrcm", lxrcm);// ��ϵ�˲���
			}
			// mapStr.put("khmcyr",
			// Util.null2String(rs.getString("gsmcyr3")));// ��˾����(Ӣ/��)

			// mapStr.put("jtmcyr",
			// Util.null2String(rs.getString("jtmcyr3")));// ��������(Ӣ/��)

			mapStr.put("dept", Util.null2String(rs.getString("bm")));// ����(����)
			// mapStr.put("bmyr", Util.null2String(rs.getString("bmyr3")));//
			// ����(Ӣ/��)
			mapStr.put("Position", Util.null2String(rs.getString("zw")));// ְλ(����)
			// mapStr.put("zwyr", Util.null2String(rs.getString("zwyr3")));//
			// ְλ(Ӣ/��)
			mapStr.put("lxrsr", Util.null2String(rs.getString("sr")));// ��ϵ������
			mapStr.put("lzsj", Util.null2String(rs.getString("zzsj")));// ��ְʱ��
			mapStr.put("tel", Util.null2String(rs.getString("bgdh")));// �칫�绰
			mapStr.put("mobile", Util.null2String(rs.getString("yddh")));// �ƶ��绰
			mapStr.put("zzdh", Util.null2String(rs.getString("zzdh")));// סլ�绰
			mapStr.put("Email", Util.null2String(rs.getString("dzyx")));// ��������
			mapStr.put("grwxh", Util.null2String(rs.getString("wxh")));// ����΢�ź�
			mapStr.put("gjz", Util.null2String(rs.getString("gjz")));// ����΢�ź�
			mapStr.put("status", Util.null2String(rs.getString("zzzt")));// ��ְ״̬
			mapStr.put("Fax", Util.null2String(rs.getString("swcz")));// ������
			mapStr.put("Postcode", Util.null2String(rs.getString("yzbm")));// ��������
			mapStr.put("bgdz", Util.null2String(rs.getString("bgdz")));// �칫��ַ(����)
			// mapStr.put("bgdzyr",
			// Util.null2String(rs.getString("bgdzyr3")));// �칫��ַ(Ӣ/��)
			mapStr.put("jtdz", Util.null2String(rs.getString("jtdz")));// ��ͥ��ַ

			mapStr.put("xxtgr", Util.null2String(rs.getString("xxtgr")));// ��Ϣ�ṩ��
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// ��Ϣ�ṩ��ʽ
			mapStr.put("picHead", Util.null2String(rs.getString("mpzm")));// ��Ƭ����
			mapStr.put("picEnd", Util.null2String(rs.getString("mpfm")));// ��Ƭ����

			mapStr.put("version", "0");// ��Ϣ�汾
			mapStr.put("dealStatus", "0");// ��Ϣ״̬
			mapStr.put("modedatacreater", sqr);
			mapStr.put("modedatacreatertype", "0");
			mapStr.put("formmodeid", "56");
			mapStr.put("scfj", Util.null2String(rs.getString("scfj")));// �ϴ�����
			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// �����ĵ�
			mapStr.put("bz", Util.null2String(rs.getString("bz")));// ��ע
			iu.insert(mapStr, table_name_1);
			sql_dt = "select * from uf_Contacts where SysNo='" + sysNo + "'";
			log.writeLog("��ʼ������ϵ��1" + sql_dt);
			rs_dt.executeSql(sql_dt);
			if (rs_dt.next()) {
				contactId = Util.null2String(rs_dt.getString("id"));
			}
			ModeRightInfo ModeRightInfo = new ModeRightInfo();
			ModeRightInfo.editModeDataShare(Integer.valueOf(sqr), 56,
					Integer.valueOf(contactId));

		}
		return SUCCESS;
	}

}
