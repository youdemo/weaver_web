package seahonor.action.custom;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class SureModifyContact implements Action {
	BaseBean log = new BaseBean();

	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		String sql_dt = "";
		InsertUtil iu = new InsertUtil();
		String tableName = "";
		String tableName1 = "uf_Contacts";
		String mainid = "";
		String billid = "";
		String sqr = "";
		String lx = "";
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
		if (rs.next()) {
			lx = Util.null2String(rs.getString("lx"));
			mainid = Util.null2String(rs.getString("id"));
			sqr = Util.null2String(rs.getString("sqr"));
		}
		sql = "select * from " + tableName + "_dt1 where mainid=" + mainid;
		rs.executeSql(sql);
		while (rs.next()) {

			billid = Util.null2String(rs.getString("lxr"));

			String xgseq = Util.null2String(rs.getString("xgseq"));
			String njdzt = Util.null2String(rs.getString("xjdzt"));
			String bjzt = Util.null2String(rs.getString("bjzt"));
			if (!"1".equals(bjzt)) {
				continue;
			}
			

				if (!"".equals(billid)) {
					new SureCustomAction().insertHistory("-63", tableName1,
							"id", billid);
					sql_dt = "select Max(id) as billid from " + tableName1
							+ " where superid = " + billid;
					log.writeLog("��ʼ������ʷ��¼sql " + sql_dt);
					rs_dt.executeSql(sql_dt);
					String historyId = "";
					if (rs_dt.next()) {
						historyId = Util.null2String(rs_dt.getString("billid"));
						ModeRightInfo ModeRightInfo = new ModeRightInfo();
						ModeRightInfo.editModeDataShare(1, 56,
								Integer.valueOf(historyId));
					}
				}
		

			updateContactInfo(lx, now, billid, sqr, njdzt, tableName1, xgseq);
		}

		return SUCCESS;
	}

	public void updateContactInfo(String lx, String now, String billid,
			String sqr, String njdzt, String tableName1, String xgseq) {
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		RecordSet rs_dt = new RecordSet();
		String sql_dt = "";
		String sql = "select * from  uf_edit_contact  where xgid='" + billid
				+ "' and xgseq='" + xgseq + "'";
		rs.executeSql(sql);
		if (rs.next()) {
			String version = "0";
			
				sql_dt = "select Version+1 as Version from " + tableName1
						+ " where id=" + billid;
				rs_dt.executeSql(sql_dt);
				if (rs_dt.next()) {
					version = Util.null2String(rs_dt.getString("Version"));
				}
			
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("Name", Util.null2String(rs.getString("Name")));// ����(����)
			// mapStr.put("xmyr", Util.null2String(rs.getString("xmyr3")));//
			// ����(Ӣ/��)
			mapStr.put("wwm", Util.null2String(rs.getString("wwm")));
			String lxrcm = Util.null2String(rs.getString("lxrcm"));
			mapStr.put("qz", Util.null2String(rs.getString("qz")));// Ⱥ��
			if ("0".equals(lxrcm)) {
				mapStr.put("GroupName", Util.null2String(rs.getString("GroupName")));// ��������
				mapStr.put("lxrcm", lxrcm);// ��ϵ�˲���
			}
			if ("1".equals(lxrcm)) {
				mapStr.put("customName", Util.null2String(rs.getString("customName")));// ������˾
				mapStr.put("lxrcm", lxrcm);// ��ϵ�˲���
			}
			// mapStr.put("khmcyr",
			// Util.null2String(rs.getString("gsmcyr3")));// ��˾����(Ӣ/��)

			// mapStr.put("jtmcyr",
			// Util.null2String(rs.getString("jtmcyr3")));// ��������(Ӣ/��)

			mapStr.put("dept", Util.null2String(rs.getString("dept")));// ����(����)
			// mapStr.put("bmyr", Util.null2String(rs.getString("bmyr3")));//
			// ����(Ӣ/��)
			mapStr.put("Position", Util.null2String(rs.getString("Position")));// ְλ(����)
			// mapStr.put("zwyr", Util.null2String(rs.getString("zwyr3")));//
			// ְλ(Ӣ/��)
			mapStr.put("lxrsr", Util.null2String(rs.getString("lxrsr")));// ��ϵ������
			mapStr.put("lzsj", Util.null2String(rs.getString("lzsj")));// ��ְʱ��
			mapStr.put("tel", Util.null2String(rs.getString("tel")));// �칫�绰
			mapStr.put("mobile", Util.null2String(rs.getString("mobile")));// �ƶ��绰
			mapStr.put("zzdh", Util.null2String(rs.getString("zzdh")));// סլ�绰
			mapStr.put("Email", Util.null2String(rs.getString("Email")));// ��������
			mapStr.put("gjz", Util.null2String(rs.getString("gjz")));
			mapStr.put("grwxh", Util.null2String(rs.getString("grwxh")));// ����΢�ź�
			mapStr.put("status", Util.null2String(rs.getString("status")));// ��ְ״̬
			mapStr.put("Fax", Util.null2String(rs.getString("Fax")));// ������
			mapStr.put("Postcode", Util.null2String(rs.getString("Postcode")));// ��������
			mapStr.put("bgdz", Util.null2String(rs.getString("bgdz")));// �칫��ַ(����)
			// mapStr.put("bgdzyr",
			// Util.null2String(rs.getString("bgdzyr3")));// �칫��ַ(Ӣ/��)
			mapStr.put("jtdz", Util.null2String(rs.getString("jtdz")));// ��ͥ��ַ

			mapStr.put("xxtgr", Util.null2String(rs.getString("xxtgr")));// ��Ϣ�ṩ��
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// ��Ϣ�ṩ��ʽ
			mapStr.put("picHead", Util.null2String(rs.getString("picHead")));// ��Ƭ����
			mapStr.put("picEnd", Util.null2String(rs.getString("picEnd")));// ��Ƭ����
			mapStr.put("version", version);// ��Ϣ�汾
			if ("3".equals(lx)) {
				
			} else {
				mapStr.put("dealStatus",njdzt);// ��˾״̬
			}

			mapStr.put("scfj", Util.null2String(rs.getString("scfj")));// �ϴ�����
			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// �����ĵ�
			mapStr.put("bz", Util.null2String(rs.getString("bz")));// ��ע
			mapStr.put("bj", Util.null2String(rs.getString("bj")));// �ϴ�����
			mapStr.put("czr", Util.null2String(rs.getString("czr")));// �ϴ�����
			mapStr.put("modifier", sqr);
			mapStr.put("ModifyTime", now);
			mapStr.put("cjr", Util.null2String(rs.getString("cjr")));
			mapStr.put("sqr", Util.null2String(rs.getString("sqr")));
			iu.updateGen(mapStr, "uf_Contacts", "id", billid);
		}
	}
}
