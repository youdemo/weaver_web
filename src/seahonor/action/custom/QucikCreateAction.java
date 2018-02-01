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

public class QucikCreateAction implements Action {
	BaseBean log = new BaseBean();

	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		String tableName = "";
		String customId = "";
		String groupId = "";
		String contactId = "";
		String cjlx = "";

		String sql = "Select tablename,id From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id=" + workflow_id + ")";
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}

		sql = "select cjlx from " + tableName + " where requestid= "
				+ requestid;
		log.writeLog("��ʼ����sql" + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			cjlx = Util.null2String(rs.getString("cjlx"));
		}
		log.writeLog("��ʼ����cjlx" + cjlx);
		if ("0".equals(cjlx)) {
			customId = insertCustom(requestid, tableName, "");
		} else if ("1".equals(cjlx)) {
			groupId = insertGroup(requestid, tableName);
		} else if ("2".equals(cjlx)) {
			contactId = insertContact(requestid, tableName, "", "");
		} else if ("3".equals(cjlx)) {
			groupId = insertGroup(requestid, tableName);
			customId = insertCustom(requestid, tableName, groupId);

		} else if ("4".equals(cjlx)) {
			customId = insertCustom(requestid, tableName, groupId);
			contactId = insertContact(requestid, tableName, customId, "");

		} else if ("5".equals(cjlx)) {
			groupId = insertGroup(requestid, tableName);
			customId = insertCustom(requestid, tableName, groupId);
			contactId = insertContact(requestid, tableName, customId, groupId);
		} else if ("6".equals(cjlx)) {
			groupId = insertGroup(requestid, tableName);
			contactId = insertContact(requestid, tableName, "", groupId);
		}
		return SUCCESS;
	}

	public String insertGroup(String requestid, String tableName) {

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		SysNoForSelf sns = new SysNoForSelf();
		InsertUtil iu = new InsertUtil();
		String table_name_1 = "uf_custom_group";
		String groupId = "";
		String now = "";
		String sql1 = "";
		String sqr = "";
		String sql = "select CONVERT(varchar(30),getdate(),21) as nowTime ";
		rs.executeSql(sql);
		if (rs.next()) {
			now = Util.null2String(rs.getString("nowTime"));
		}
		String seqNO = "";
		sql = "select MAX(isnull(seqNO,0))+1 as seqNO from uf_custom_group";
		rs.executeSql(sql);
		if (rs.next()) {
			seqNO = Util.null2String(rs.getString("seqNO"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		rs.executeSql(sql);
		log.writeLog("��ʼ���뼯��" + sql);
		if (rs.next()) {
			String sysNo = sns.getNum("CGL", table_name_1, 4);
			sqr = Util.null2String(rs.getString("sqr"));
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("seqNO", seqNO);// ������
			mapStr.put("cjr", Util.null2String(rs.getString("cjr")));// ������
			mapStr.put("cjrq", Util.null2String(rs.getString("cjrq")));// ��������
			mapStr.put("cjsj", Util.null2String(rs.getString("cjsj")));// ����ʱ��
			mapStr.put("cjxq", Util.null2String(rs.getString("cjxq")));// ��������
			mapStr.put("sqr", Util.null2String(rs.getString("sqr")));// ������
			mapStr.put("sqrq", Util.null2String(rs.getString("sqrq")));// ��������
			mapStr.put("sqsj", Util.null2String(rs.getString("sqsj")));// ����ʱ��
			mapStr.put("sqxq", Util.null2String(rs.getString("sqxq")));// ��������
			mapStr.put("GroupName", Util.null2String(rs.getString("jtmczw2")));// ��������(����)
			mapStr.put("jtmcyr", Util.null2String(rs.getString("jtmcyr2")));// ��������(Ӣ/��)
			mapStr.put("GroupAdd", Util.null2String(rs.getString("jtdzzw2")));// ���ŵ�ַ(����)
			mapStr.put("jtdzyr", Util.null2String(rs.getString("jtdzyr2")));// ���ŵ�ַ(Ӣ/��)
			mapStr.put("GroupTel", Util.null2String(rs.getString("jtdh2")));// ���ŵ绰
			mapStr.put("GroupEmail", Util.null2String(rs.getString("jtyx2")));// ��������
			mapStr.put("GroupWebsit", Util.null2String(rs.getString("jtwz2")));// ������վ
			mapStr.put("GroupZip", Util.null2String(rs.getString("yzbm2")));// ��������
			mapStr.put("qywxh", Util.null2String(rs.getString("qywxh2")));// ��ҵ΢�ź�
			mapStr.put("GroupFax", Util.null2String(rs.getString("jtcz2")));// ���Ŵ���
			mapStr.put("GroupGiver", Util.null2String(rs.getString("jtxxtgz2")));// ������Ϣ�ṩ��
			mapStr.put("GroupCode", sysNo);// ���Ŵ���
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs2")));// ��Ϣ�ṩ��ʽ
			mapStr.put("GroupStatus", "0");// ����״̬
			mapStr.put("Version", "0");// ��Ϣ�汾
			mapStr.put("ModifyTime", now);// ��Ϣ����ʱ��
			// mapStr.put("zxwd",
			// Util.null2String(rs.getString("CustomFill")));//�����ĵ�
			// mapStr.put("scfj",
			// Util.null2String(rs.getString("CustomFill")));//�ϴ�����
			// mapStr.put("remark",
			// Util.null2String(rs.getString("CustomFill")));//��ע
			mapStr.put("modedatacreater", sqr);
			mapStr.put("modedatacreatertype", "0");
			mapStr.put("formmodeid", "61");
			iu.insert(mapStr, table_name_1);
			sql1 = "select * from uf_custom_group where GroupCode='" + sysNo
					+ "'";
			log.writeLog("��ʼ���뼯��1" + sql1);
			rs1.executeSql(sql1);
			if (rs1.next()) {
				groupId = Util.null2String(rs1.getString("id"));
			}

		}
		insertGs(Integer.valueOf(sqr), 61, Integer.valueOf(groupId));
		return groupId;

	}

	public String insertCustom(String requestid, String tableName,
			String groupid) {

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		SysNoForSelf sns = new SysNoForSelf();
		InsertUtil iu = new InsertUtil();
		String table_name_1 = "UF_CUSTOM";
		String customId = "";
		String now = "";
		String sql1 = "";
		String sqr = "";
		String sql = "select CONVERT(varchar(30),getdate(),21) as nowTime ";
		rs.executeSql(sql);
		if (rs.next()) {
			now = Util.null2String(rs.getString("nowTime"));
		}
		String seqNO = "";
		sql = "select MAX(isnull(seqNO,0))+1 as seqNO from UF_CUSTOM  where seqNO !=100000";
		rs.executeSql(sql);
		if (rs.next()) {
			seqNO = Util.null2String(rs.getString("seqNO"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		log.writeLog("��ʼ����ͻ�" + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			String gkh = Util.null2String(rs.getString("kh1"));
			String ggys = Util.null2String(rs.getString("gys1"));
			String ghzhb = Util.null2String(rs.getString("hzhb1"));
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
			sqr = Util.null2String(rs.getString("sqr"));
			String sysNo = sns.getNum("CTL", table_name_1, 4);
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("seqNO", seqNO);// ��˾����
			mapStr.put("customCode", sysNo);// ��˾����
			mapStr.put("cjr", Util.null2String(rs.getString("cjr")));// ������
			mapStr.put("cjrq", Util.null2String(rs.getString("cjrq")));// ��������
			mapStr.put("cjsj", Util.null2String(rs.getString("cjsj")));// ����ʱ��
			mapStr.put("cjxq", Util.null2String(rs.getString("cjxq")));// ��������
			mapStr.put("sqr", sqr);// ������
			mapStr.put("sqrq", Util.null2String(rs.getString("sqrq")));// ��������
			mapStr.put("sqsj", Util.null2String(rs.getString("sqsj")));// ����ʱ��
			mapStr.put("sqxq", Util.null2String(rs.getString("sqxq")));// ��������
			mapStr.put("customName", Util.null2String(rs.getString("gsmczw1")));// ��˾����(����)
			mapStr.put("khmcyr", Util.null2String(rs.getString("gsmcyr1")));// ��˾����(Ӣ/��)
			mapStr.put("gsjczw", Util.null2String(rs.getString("gsjczw1")));// ��˾���(����)
			mapStr.put("dm", Util.null2String(rs.getString("dm1")));// ����1
			mapStr.put("zh", Util.null2String(rs.getString("zh1")));// �ֺ�
			mapStr.put("hy", Util.null2String(rs.getString("hy1")));// ��ҵ
			mapStr.put("Address", Util.null2String(rs.getString("gsdzzw1")));// ��˾��ַ(����)
			mapStr.put("gsdz", Util.null2String(rs.getString("gsdzyr1")));// ��˾��ַ(Ӣ/��)
			mapStr.put("Telphone", Util.null2String(rs.getString("gsdh1")));// ��˾�绰
			mapStr.put("Email", Util.null2String(rs.getString("gsyx1")));// ��˾����
			mapStr.put("Website", Util.null2String(rs.getString("gswz1")));// ��˾��վ
			mapStr.put("Fax", Util.null2String(rs.getString("gscz1")));// ��˾����
			mapStr.put("zcdz", Util.null2String(rs.getString("zcdz1")));// ע���ַ
			mapStr.put("Postcode", Util.null2String(rs.getString("yzbm1")));// ��������
			mapStr.put("gswxh", Util.null2String(rs.getString("gswxh1")));// ��˾΢�ź�
			mapStr.put("Country", Util.null2String(rs.getString("ssgj1")));// ��������
			if (!"".equals(groupid)) {
				mapStr.put("CustomGroup", groupid);// ��������
			} else {
				mapStr.put("CustomGroup",
						Util.null2String(rs.getString("ssjt1")));// ��������
			}
			mapStr.put("industryType", Util.null2String(rs.getString("hylx1")));// ��ҵ����
			mapStr.put("ybgsgx", ybgsgx);// �뱾��˾��ϵ
			mapStr.put("kh", gkh);// �ͻ�
			mapStr.put("gys", ggys);// ��Ӧ��
			mapStr.put("hzhb", ghzhb);// �������
			mapStr.put("Provider", Util.null2String(rs.getString("gsxxtgr1")));// ��˾��Ϣ�ṩ��
			mapStr.put("khyh", Util.null2String(rs.getString("khyh1")));// ��������
			mapStr.put("yhzh", Util.null2String(rs.getString("yhzh1")));// ��������
			mapStr.put("zczb", Util.null2String(rs.getString("zczb1")));// ��������
			mapStr.put("sszb", Util.null2String(rs.getString("sszb1")));// ��������
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs1")));// ��Ϣ�ṩ��ʽ
			mapStr.put("sh", Util.null2String(rs.getString("sh1")));// ˰��
			mapStr.put("CutomStatus", "0");// ��˾״̬
			// mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));//�����ĵ�
			// mapStr.put("scfj", Util.null2String(rs.getString("scfj")));//�ϴ�����
			mapStr.put("Version", "0");// ��Ϣ�汾
			mapStr.put("ModifyTime", now);// ��Ϣ����ʱ��
			// mapStr.put("Remark",
			// Util.null2String(rs.getString("CustomFill")));//��ע
			mapStr.put("modedatacreater", sqr);
			mapStr.put("modedatacreatertype", "0");
			mapStr.put("formmodeid", "52");
			iu.insert(mapStr, table_name_1);
			sql1 = "select * from uf_custom where customCode='" + sysNo + "'";
			log.writeLog("��ʼ����ͻ�1" + sql1);
			rs1.executeSql(sql1);
			if (rs1.next()) {
				customId = Util.null2String(rs1.getString("id"));
			}

		}
		insertGs(Integer.valueOf(sqr), 52, Integer.valueOf(customId));

		return customId;
	}

	public String insertContact(String requestid, String tableName,
			String customid, String groupid) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		SysNoForSelf sns = new SysNoForSelf();
		InsertUtil iu = new InsertUtil();
		String table_name_1 = "uf_Contacts";
		String contactId = "";
		String now = "";
		String sql1 = "";
		String sqr = "";
		String sql = "select CONVERT(varchar(30),getdate(),21) as nowTime ";
		rs.executeSql(sql);
		if (rs.next()) {
			now = Util.null2String(rs.getString("nowTime"));
		}
		sql = "select * from " + tableName + " where requestid= " + requestid;
		log.writeLog("��ʼ������ϵ��" + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			String gkh = Util.null2String(rs.getString("kh3"));
			String ggys = Util.null2String(rs.getString("gys3"));
			String ghzhb = Util.null2String(rs.getString("hzhb3"));
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
			String sysNo = sns.getNum("CCDL", table_name_1, 4);
			sqr = Util.null2String(rs.getString("sqr"));
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("SysNo", sysNo);// ��ϵ�˴���
			mapStr.put("cjr", Util.null2String(rs.getString("cjr")));// ������
			mapStr.put("cjrq", Util.null2String(rs.getString("cjrq")));// ��������
			mapStr.put("cjsj", Util.null2String(rs.getString("cjsj")));// ����ʱ��
			mapStr.put("cjxq", Util.null2String(rs.getString("cjxq")));// ��������
			mapStr.put("sqr", Util.null2String(rs.getString("sqr")));// ������
			mapStr.put("sqrq", Util.null2String(rs.getString("sqrq")));// ��������
			mapStr.put("sqsj", Util.null2String(rs.getString("sqsj")));// ����ʱ��
			mapStr.put("sqxq", Util.null2String(rs.getString("sqxq")));// ��������
			mapStr.put("Name", Util.null2String(rs.getString("xmzw3")));// ����(����)
			mapStr.put("xmyr", Util.null2String(rs.getString("xmyr3")));// ����(Ӣ/��)
			mapStr.put("lxrcm", Util.null2String(rs.getString("lxrcm3")));// ��ϵ�˲���
			mapStr.put("qz", Util.null2String(rs.getString("qz3")));// Ⱥ��
			if (!"".equals(customid) && "".equals(groupid)) {
				mapStr.put("customName", customid);// ������˾
				mapStr.put("khmcyr", Util.null2String(rs.getString("gsmcyr1")));// ��˾����(Ӣ/��)
			} else if (!"".equals(groupid) && "".equals(customid)) {
				mapStr.put("GroupName", groupid);// ��������
				mapStr.put("jtmcyr", Util.null2String(rs.getString("jtmcyr2")));// ��������(Ӣ/��)
			} else if (!"".equals(groupid) && !"".equals(customid)) {
				String lxrcm3 = Util.null2String(rs.getString("lxrcm3"));
				if ("0".equals(lxrcm3)) {
					mapStr.put("GroupName", groupid);// ��������
					mapStr.put("jtmcyr",
							Util.null2String(rs.getString("jtmcyr2")));// ��������(Ӣ/��)
				} else {
					mapStr.put("customName", customid);// ������˾
					mapStr.put("khmcyr",
							Util.null2String(rs.getString("gsmcyr1")));// ��˾����(Ӣ/��)
				}

			} else {
				mapStr.put("customName",
						Util.null2String(rs.getString("ssgs3")));// ������˾
				mapStr.put("khmcyr", Util.null2String(rs.getString("gsmcyr3")));// ��˾����(Ӣ/��)
				mapStr.put("GroupName", Util.null2String(rs.getString("ssjt3")));// ��������
				mapStr.put("jtmcyr", Util.null2String(rs.getString("jtmcyr3")));// ��������(Ӣ/��)
			}
			mapStr.put("dept", Util.null2String(rs.getString("bmzw3")));// ����(����)
			mapStr.put("bmyr", Util.null2String(rs.getString("bmyr3")));// ����(Ӣ/��)
			mapStr.put("Position", Util.null2String(rs.getString("zwzw3")));// ְλ(����)
			mapStr.put("zwyr", Util.null2String(rs.getString("zwyr3")));// ְλ(Ӣ/��)
			mapStr.put("lxrsr", Util.null2String(rs.getString("lxrsr3")));// ��ϵ������
			mapStr.put("lzsj", Util.null2String(rs.getString("lzsj3")));// ��ְʱ��
			mapStr.put("tel", Util.null2String(rs.getString("bgdh3")));// �칫�绰
			mapStr.put("mobile", Util.null2String(rs.getString("yddh3")));// �ƶ��绰
			mapStr.put("zzdh", Util.null2String(rs.getString("zzdh3")));// סլ�绰
			mapStr.put("Email", Util.null2String(rs.getString("ddyx3")));// ��������
			mapStr.put("grwxh", Util.null2String(rs.getString("grwxh3")));// ����΢�ź�
			mapStr.put("status", Util.null2String(rs.getString("zzzt3")));// ��ְ״̬
			mapStr.put("Fax", Util.null2String(rs.getString("swcz3")));// ������
			mapStr.put("Postcode", Util.null2String(rs.getString("yzbm3")));// ��������
			mapStr.put("bgdz", Util.null2String(rs.getString("bgdzzw3")));// �칫��ַ(����)
			mapStr.put("bgdzyr", Util.null2String(rs.getString("bgdzyr3")));// �칫��ַ(Ӣ/��)
			mapStr.put("jtdz", Util.null2String(rs.getString("jtdz3")));// ��ͥ��ַ
			mapStr.put("ybgsdgx", ybgsgx);// �뱾��˾�Ĺ�ϵ
			mapStr.put("kh", gkh);// �ͻ�
			mapStr.put("gys", ggys);// ��Ӧ��
			mapStr.put("hzhb", ghzhb);// �������
			mapStr.put("xxtgr", Util.null2String(rs.getString("xxtgr3")));// ��Ϣ�ṩ��
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs3")));// ��Ϣ�ṩ��ʽ
			mapStr.put("picHead", Util.null2String(rs.getString("mpzm3")));// ��Ƭ����
			mapStr.put("picEnd", Util.null2String(rs.getString("mpfm3")));// ��Ƭ����

			mapStr.put("version", "0");// ��Ϣ�汾
			mapStr.put("dealStatus", "0");// ��Ϣ״̬
			mapStr.put("modedatacreater", sqr);
			mapStr.put("modedatacreatertype", "0");
			mapStr.put("formmodeid", "56");
			// mapStr.put("scfj",
			// Util.null2String(rs.getString("CustomFill")));//�ϴ�����
			// mapStr.put("zxwd",
			// Util.null2String(rs.getString("CustomFill")));//�����ĵ�
			// mapStr.put("bz",
			// Util.null2String(rs.getString("CustomFill")));//��ע
			iu.insert(mapStr, table_name_1);
			sql1 = "select * from uf_Contacts where SysNo='" + sysNo + "'";
			log.writeLog("��ʼ������ϵ��1" + sql1);
			rs1.executeSql(sql1);
			if (rs1.next()) {
				contactId = Util.null2String(rs1.getString("id"));
			}

		}
		insertGs(Integer.valueOf(sqr), 56, Integer.valueOf(contactId));
		return contactId;
	}

	private boolean insertGs(int creater, int modeid, int m_billid) {
		ModeRightInfo ModeRightInfo = new ModeRightInfo();
		log.writeLog("creater" + creater + "modeid" + modeid + "m_billid"
				+ m_billid);
		ModeRightInfo.editModeDataShare(creater, modeid, m_billid);// �½���ʱ����ӹ���
		// --------------���ĵ���Ȩ------------------------
		// ModeRightInfo modeRightInfo = new ModeRightInfo();
		// modeRightInfo.addDocShare(modecreater,modeid,m_billid);
		return true;
	}

}
