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

public class SureModifyCustom implements Action {
	BaseBean log = new BaseBean();

	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		RecordSet rs_dt = new RecordSet();
		String sql_dt = "";
		String tableName = "";
		String tableName1 = "uf_custom";
		String mainid = "";
		String billid = "";
		String sqr = "";
		String lx = "";
		String now = "";
		String jyfw = "";
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
			billid = Util.null2String(rs.getString("jdgs"));
			String xgseq = Util.null2String(rs.getString("xgseq"));
			String njdzt = Util.null2String(rs.getString("njdzt"));
			String bjzt = Util.null2String(rs.getString("bjzt"));
			if (!"1".equals(bjzt)) {
				continue;
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
			
			updateCustomInfo(lx, now, billid, sqr, njdzt, tableName1, xgseq);

		}

		return SUCCESS;
	}

	public void updateCustomInfo(String lx, String now, String billid,
			String sqr, String njdzt, String tableName1, String xgseq) {
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		RecordSet rs_dt = new RecordSet();
		String sql_dt = "";
		String sql = "select * from uf_edit_custom where xgid='" + billid
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

			
			String gkh = Util.null2String(rs.getString("kh"));
			String ggys = Util.null2String(rs.getString("gys"));
			String ghzhb = Util.null2String(rs.getString("hzhb"));
			String ybgsgx = null;
			String zf=Util.null2String(rs.getString("zf"));
			String yh=Util.null2String(rs.getString("yh"));
			String ybgsgxstr="";
			String flag="";
			if("1".equals(gkh)){
				ybgsgxstr=ybgsgxstr+flag+"�ͻ�";
				flag="/";
			}
			if("1".equals(ggys)){
				ybgsgxstr=ybgsgxstr+flag+"��Ӧ��";
				flag="/";
			}
			if("1".equals(ghzhb)){
				ybgsgxstr=ybgsgxstr+flag+"�������";
				flag="/";
			}
			if("1".equals(zf)){
				ybgsgxstr=ybgsgxstr+flag+"����";
				flag="/";
			}
			if("1".equals(yh)){
				ybgsgxstr=ybgsgxstr+flag+"����";
				flag="/";
			}
			sql_dt="select selectvalue from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='uf_custom' and a.fieldname='ybgsgx' and upper(c.selectname)=upper('"+ybgsgxstr+"')";
			
			rs_dt.executeSql(sql_dt);
			if(rs_dt.next()){
				ybgsgx = Util.null2String(rs_dt.getString("selectvalue"));
			}
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("customName", Util.null2String(rs.getString("customName")));// ��˾����(����)
			mapStr.put("gswwm", Util.null2String(rs.getString("gswwm")));
			mapStr.put("gsjczw", Util.null2String(rs.getString("gsjczw")));// ��˾���(����)
			mapStr.put("dm", Util.null2String(rs.getString("dm")));// ����1
			mapStr.put("zh", Util.null2String(rs.getString("zh")));// �ֺ�
			mapStr.put("hy", Util.null2String(rs.getString("hy")));// ��ҵ
			mapStr.put("px1", Util.null2String(rs.getString("px1")));// ����1
			mapStr.put("px2", Util.null2String(rs.getString("px2")));// �ֺ�
			mapStr.put("px3", Util.null2String(rs.getString("px3")));// ��ҵ
			mapStr.put("gjz", Util.null2String(rs.getString("gjz")));// ��ҵ
			mapStr.put("Address", Util.null2String(rs.getString("Address")));// ��˾��ַ(����)
			mapStr.put("Telphone", Util.null2String(rs.getString("Telphone")));// ��˾�绰
			mapStr.put("Website", Util.null2String(rs.getString("Website")));// ��˾��վ
			mapStr.put("Fax", Util.null2String(rs.getString("Fax")));// ��˾����
			mapStr.put("zcdz", Util.null2String(rs.getString("zcdz")));// ע���ַ
			mapStr.put("Postcode", Util.null2String(rs.getString("Postcode")));// ��������
			mapStr.put("gswxh", Util.null2String(rs.getString("gswxh")));// ��˾΢�ź�
			mapStr.put("gszt", Util.null2String(rs.getString("gszt")));// ��˾״̬
			mapStr.put("clrq", Util.null2String(rs.getString("clrq")));// ��������
			mapStr.put("zcrq", Util.null2String(rs.getString("zcrq")));// ע������
			mapStr.put("CustomGroup", Util.null2String(rs.getString("CustomGroup")));// ��������
			mapStr.put("ybgsgx", ybgsgx);// �뱾��˾��ϵ
			mapStr.put("kh", gkh);// �ͻ�
			mapStr.put("gys", ggys);// ��Ӧ��
			mapStr.put("hzhb", ghzhb);// �������Ƚ�
			mapStr.put("zf", zf);// �������Ƚ�
			mapStr.put("yh", yh);// �������Ƚ�
			mapStr.put("Provider", Util.null2String(rs.getString("Provider")));// ��˾��Ϣ�ṩ��
			mapStr.put("khyh", Util.null2String(rs.getString("khyh")));// ��������
			mapStr.put("yhzh", Util.null2String(rs.getString("yhzh")));// �����˺�
			mapStr.put("zczb", Util.null2String(rs.getString("zczb")));// ע���ʱ�
			mapStr.put("sszb", Util.null2String(rs.getString("sszb")));// ʵ���ʱ�
			mapStr.put("bzh", Util.null2String(rs.getString("bzh")));// ����
			mapStr.put("bzh1", Util.null2String(rs.getString("bzh1")));// ����
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// ��Ϣ�ṩ��ʽ
			mapStr.put("sh", Util.null2String(rs.getString("sh")));// ˰��
			mapStr.put("jyfw", Util.null2String(rs.getString("jyfw"))
					.replaceAll("&nbsp;", " ").replaceAll("&nbsp", " "));// ��Ӫ��Χ
			mapStr.put("Version", version);// ��Ϣ�汾
			if ("3".equals(lx)) {
				
			} else {
				mapStr.put("CutomStatus", njdzt);// ��˾״̬
			}

			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// �����ĵ�
			mapStr.put("scfj", Util.null2String(rs.getString("scfj")));// �ϴ�����
			mapStr.put("bj", Util.null2String(rs.getString("bj")));// �ϴ�����
			mapStr.put("czr", Util.null2String(rs.getString("czr")));// �ϴ�����

			mapStr.put("ModifyTime", now);// ��Ϣ����ʱ��
			mapStr.put("Modifier", sqr);
			mapStr.put("cjr", Util.null2String(rs.getString("cjr")));
			mapStr.put("sqr", Util.null2String(rs.getString("sqr")));
			mapStr.put("Remark", Util.null2String(rs.getString("Remark")));// ��ע
			iu.updateGen(mapStr, "uf_custom", "id", billid);
		}
	}
}
