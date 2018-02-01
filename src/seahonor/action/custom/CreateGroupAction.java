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

public class CreateGroupAction implements Action {
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
		String table_name_1 = "uf_custom_group";
		String groupId = "";
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
			sql_dt = "select MAX(isnull(seqNO,0))+1 as seqNO from uf_custom_group";
			rs_dt.executeSql(sql_dt);
			if (rs_dt.next()) {
				seqNO = Util.null2String(rs_dt.getString("seqNO"));
			}
			String sysNo = sns.getNum("CGL", table_name_1, 4);
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("seqNO", seqNO);
			mapStr.put("cjr", cjr);// ������
			mapStr.put("cjrq", cjrq);// ��������
			mapStr.put("cjsj", cjsj);// ����ʱ��
			mapStr.put("cjxq", cjxq);// ��������
			mapStr.put("sqr", sqr);// ������
			mapStr.put("sqrq", sqrq);// ��������
			mapStr.put("sqsj", sqsj);// ����ʱ��
			mapStr.put("sqxq", sqxq);// ��������
			mapStr.put("GroupName", Util.null2String(rs.getString("jtmc")));// ��������(����)
			mapStr.put("jtwwm", Util.null2String(rs.getString("jtwwm")));// ����������
			// mapStr.put("jtmcyr",
			// Util.null2String(rs.getString("jtmcyr2")));// ��������(Ӣ/��)
			mapStr.put("GroupAdd", Util.null2String(rs.getString("jtdz")));// ���ŵ�ַ(����)
			// mapStr.put("jtdzyr",
			// Util.null2String(rs.getString("jtdzyr2")));// ���ŵ�ַ(Ӣ/��)
			mapStr.put("GroupTel", Util.null2String(rs.getString("jtdh")));// ���ŵ绰
			// mapStr.put("GroupEmail",
			// Util.null2String(rs.getString("jtyx2")));// ��������
			mapStr.put("GroupWebsit", Util.null2String(rs.getString("jtwz")));// ������վ
			mapStr.put("GroupZip", Util.null2String(rs.getString("yzbm")));// ��������
			mapStr.put("qywxh", Util.null2String(rs.getString("gzwxh")));// ��ҵ΢�ź�
			mapStr.put("GroupFax", Util.null2String(rs.getString("jtcz")));// ���Ŵ���
			mapStr.put("GroupGiver", Util.null2String(rs.getString("jtxztgz")));// ������Ϣ�ṩ��
			mapStr.put("GroupCode", sysNo);// ���Ŵ���
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// ��Ϣ�ṩ��ʽ
			mapStr.put("GroupStatus", "0");// ����״̬
			mapStr.put("cxzt", Util.null2String(rs.getString("cxzt")));// ����״̬
			mapStr.put("Version", "0");// ��Ϣ�汾
			mapStr.put("ModifyTime", now);// ��Ϣ����ʱ��
			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// �����ĵ�
			mapStr.put("scfj", Util.null2String(rs.getString("fjsc")));// �ϴ�����
			mapStr.put("remark", Util.null2String(rs.getString("bz")));// ��ע
			mapStr.put("ssjt", Util.null2String(rs.getString("ssjt")));// ��������
			mapStr.put("modedatacreater", sqr);
			mapStr.put("modedatacreatertype", "0");
			mapStr.put("formmodeid", "61");
			iu.insert(mapStr, table_name_1);
			sql_dt = "select * from uf_custom_group where GroupCode='" + sysNo
					+ "'";
			log.writeLog("��ʼ���뼯��1" + sql_dt);
			rs_dt.executeSql(sql_dt);
			if (rs_dt.next()) {
				groupId = Util.null2String(rs_dt.getString("id"));
			}
			ModeRightInfo ModeRightInfo = new ModeRightInfo();
			ModeRightInfo.editModeDataShare(Integer.valueOf(sqr), 61,
					Integer.valueOf(groupId));
		}
		return SUCCESS;
	}
}
