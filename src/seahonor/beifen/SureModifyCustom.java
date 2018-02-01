package seahonor.beifen;

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

public class SureModifyCustom implements Action {
	BaseBean log = new BaseBean();

	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		String sql_dt = "";
		InsertUtil iu = new InsertUtil();
		String tableName = "";
		String tableName1 = "uf_custom";
		String mainid = "";
		String billid = "";
		String sqr = "";
		String lx = "";
		String now = "";
		String jyfw="";
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
		log.writeLog("��ʼ��˾У�Ժϲ�sql " + sql);
		rs.executeSql(sql);
		while (rs.next()) {
			jyfw = Util.null2String(rs.getString("jyfw")).replaceAll("&nbsp;", " ").replaceAll("&nbsp", " ");
			String sfcl = Util.null2String(rs.getString("sfjd"));
//			if (!"1".equals(sfcl)) {
//				continue;
//			}
			String version = "0";
			billid = Util.null2String(rs.getString("jdgs"));
			if ("3".equals(lx)) {
				sql_dt = "select Version+1 as Version from " + tableName1
						+ " where id=" + billid;
				rs_dt.executeSql(sql_dt);
				if (rs_dt.next()) {
					version = Util.null2String(rs_dt.getString("Version"));
				}
				if (!"".equals(billid)) {
					new SureCustomAction().insertHistory("-59", tableName1,
							"id", billid);
					sql_dt = "select Max(id) as billid from " + tableName1
							+ " where superid = " + billid;
					log.writeLog("��ʼ������ʷ��¼sql " + sql_dt);
					rs_dt.executeSql(sql_dt);
					String historyId = "";
					if (rs_dt.next()) {
						historyId = Util.null2String(rs_dt.getString("billid"));
						ModeRightInfo ModeRightInfo = new ModeRightInfo();
						ModeRightInfo.editModeDataShare(1, 52,
								Integer.valueOf(historyId));
					}
				}
			}
			String gkh = Util.null2String(rs.getString("ke"));
			String ggys = Util.null2String(rs.getString("gys"));
			String ghzhb = Util.null2String(rs.getString("hzhb"));
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
			mapStr.put("customName", Util.null2String(rs.getString("gsmc")));// ��˾����(����)
			mapStr.put("gsjczw", Util.null2String(rs.getString("gsjc")));// ��˾���(����)
			mapStr.put("dm", Util.null2String(rs.getString("qym")));// ����1
			mapStr.put("zh", Util.null2String(rs.getString("zh")));// �ֺ�
			mapStr.put("hy", Util.null2String(rs.getString("hy")));// ��ҵ
			mapStr.put("Address", Util.null2String(rs.getString("gsdz")));// ��˾��ַ(����)
			mapStr.put("Telphone", Util.null2String(rs.getString("gsdh")));// ��˾�绰
			mapStr.put("Website", Util.null2String(rs.getString("gswz")));// ��˾��վ
			mapStr.put("Fax", Util.null2String(rs.getString("gscz")));// ��˾����
			mapStr.put("zcdz", Util.null2String(rs.getString("zcdz")));// ע���ַ
			mapStr.put("Postcode", Util.null2String(rs.getString("yzbm")));// ��������
			mapStr.put("gswxh", Util.null2String(rs.getString("gzwxh")));// ��˾΢�ź�
			mapStr.put("gszt", Util.null2String(rs.getString("gszt")));// ��˾״̬
			mapStr.put("clrq", Util.null2String(rs.getString("clrq")));// ��������
			mapStr.put("zcrq", Util.null2String(rs.getString("zxrq")));// ע������
			mapStr.put("CustomGroup", Util.null2String(rs.getString("ssjt")));// ��������
			mapStr.put("ybgsgx", ybgsgx);// �뱾��˾��ϵ
			mapStr.put("kh", gkh);// �ͻ�
			mapStr.put("gys", ggys);// ��Ӧ��
			mapStr.put("hzhb", ghzhb);// �������
			mapStr.put("Provider", Util.null2String(rs.getString("gsxxtgr")));// ��˾��Ϣ�ṩ��
			mapStr.put("khyh", Util.null2String(rs.getString("khyh")));// ��������
			mapStr.put("yhzh", Util.null2String(rs.getString("yhzh")));// �����˺�
			mapStr.put("zczb", Util.null2String(rs.getString("zczb")));// ע���ʱ�
			mapStr.put("sszb", Util.null2String(rs.getString("sszb")));// ʵ���ʱ�
			mapStr.put("bzh", Util.null2String(rs.getString("bzh")));// ����
			mapStr.put("bzh1", Util.null2String(rs.getString("bzh1")));// ����
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// ��Ϣ�ṩ��ʽ
			mapStr.put("sh", Util.null2String(rs.getString("sh")));// ˰��
			mapStr.put("jyfw", jyfw);// ��Ӫ��Χ
			if ("3".equals(lx)) {
				mapStr.put("Version", version);// ��Ϣ�汾
			}else{
				mapStr.put("CutomStatus",
						Util.null2String(rs.getString("njdzt")));// ��˾״̬
			}

			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// �����ĵ�
			mapStr.put("scfj", Util.null2String(rs.getString("fjsc")));// �ϴ�����

			mapStr.put("ModifyTime", now);// ��Ϣ����ʱ��
			mapStr.put("Modifier", sqr);
			mapStr.put("Remark", Util.null2String(rs.getString("bz")));// ��ע
			iu.updateGen(mapStr, "uf_custom", "id", billid);

		}

		return SUCCESS;
	}
}
