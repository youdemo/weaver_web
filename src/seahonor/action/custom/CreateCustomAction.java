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

public class CreateCustomAction implements Action {
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
		String table_name_1 = "uf_custom";
		String customId = "";
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
		String jyfw = "";
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
			cjrq = Util.null2String(rs.getString("sjrq"));
			cjsj = Util.null2String(rs.getString("cjjs"));
			cjxq = Util.null2String(rs.getString("cjxq"));
			sqr = Util.null2String(rs.getString("sqr"));
			sqrq = Util.null2String(rs.getString("sqrq"));
			sqsj = Util.null2String(rs.getString("sqsj"));
			sqxq = Util.null2String(rs.getString("sqxq"));
		}
		sql = "select * from " + tableName + "_dt1 where mainid=" + mainid;
		rs.executeSql(sql);
		while (rs.next()) {
			sql_dt = "select MAX(isnull(seqNO,0))+1 as seqNO from UF_CUSTOM  where seqNO !=100000";
			rs_dt.executeSql(sql_dt);
			if (rs_dt.next()) {
				seqNO = Util.null2String(rs_dt.getString("seqNO"));
			}
			String gkh = Util.null2String(rs.getString("ke"));
			String ggys = Util.null2String(rs.getString("gys"));
			String ghzhb = Util.null2String(rs.getString("hzhb"));
			jyfw = Util.null2String(rs.getString("jyfw")).replaceAll("&nbsp;", " ").replaceAll("&nbsp", " ");
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
			log.writeLog("ccccsql"+sql_dt);
			rs_dt.executeSql(sql_dt);
			if(rs_dt.next()){
				ybgsgx = Util.null2String(rs_dt.getString("selectvalue"));
			}
			String sysNo = sns.getNum("CTL", table_name_1, 4);
			Map<String, String> mapStr = new HashMap<String, String>();
			mapStr.put("seqNO", seqNO);// ��˾����
			mapStr.put("customCode", sysNo);// ��˾����
			mapStr.put("cjr", cjr);// ������
			mapStr.put("cjrq", cjrq);// ��������
			mapStr.put("cjsj", cjsj);// ����ʱ��
			mapStr.put("cjxq", cjxq);// ��������
			mapStr.put("sqr", sqr);// ������
			mapStr.put("sqrq", sqrq);// ��������
			mapStr.put("sqsj", sqsj);// ����ʱ��
			mapStr.put("sqxq", sqxq);// ��������
			mapStr.put("customName", Util.null2String(rs.getString("gsmc")));// ��˾����(����)
			mapStr.put("gswwm", Util.null2String(rs.getString("gswwm")));
			// mapStr.put("khmcyr", Util.null2String(rs.getString("gsmcy")));//
			// ��˾����(Ӣ/��)
			mapStr.put("gsjczw", Util.null2String(rs.getString("gsjc")));// ��˾���(����)
			mapStr.put("dm", Util.null2String(rs.getString("qym")));// ����1
			mapStr.put("zh", Util.null2String(rs.getString("zh")));// �ֺ�
			mapStr.put("hy", Util.null2String(rs.getString("hy")));// ��ҵ
			mapStr.put("gjz", Util.null2String(rs.getString("gjz")));// ��ҵ
			mapStr.put("px1", Util.null2String(rs.getString("px1")));// ����1
			mapStr.put("px2", Util.null2String(rs.getString("px2")));// �ֺ�
			mapStr.put("px3", Util.null2String(rs.getString("px3")));// ��ҵ
			mapStr.put("Address", Util.null2String(rs.getString("gsdz")));// ��˾��ַ(����)
			// mapStr.put("gsdz", Util.null2String(rs.getString("gsdzyr1")));//
			// ��˾��ַ(Ӣ/��)
			mapStr.put("Telphone", Util.null2String(rs.getString("gsdh")));// ��˾�绰
			// mapStr.put("Email", Util.null2String(rs.getString("gsyx")));//
			// ��˾����
			mapStr.put("Website", Util.null2String(rs.getString("gswz")));// ��˾��վ
			mapStr.put("Fax", Util.null2String(rs.getString("gscz")));// ��˾����
			mapStr.put("zcdz", Util.null2String(rs.getString("zcdz")));// ע���ַ
			mapStr.put("Postcode", Util.null2String(rs.getString("yzbm")));// ��������
			mapStr.put("gswxh", Util.null2String(rs.getString("gzwxh")));// ��˾΢�ź�
			mapStr.put("gszt", Util.null2String(rs.getString("gszt")));// ��˾״̬
			mapStr.put("clrq", Util.null2String(rs.getString("clrq")));// ��������
			mapStr.put("zcrq", Util.null2String(rs.getString("zxrq")));// ע������
			// mapStr.put("Country", Util.null2String(rs.getString("ssgg")));//
			// ��������
			mapStr.put("CustomGroup", Util.null2String(rs.getString("ssjt")));// ��������

			// mapStr.put("industryType",
			// Util.null2String(rs.getString("hylx")));// ��ҵ����
			mapStr.put("ybgsgx", ybgsgx);// �뱾��˾��ϵ
			mapStr.put("kh", gkh);// �ͻ�
			mapStr.put("gys", ggys);// ��Ӧ��
			mapStr.put("hzhb", ghzhb);// �������
			mapStr.put("zf", zf);// �������
			mapStr.put("yh", yh);// �������
			mapStr.put("Provider", Util.null2String(rs.getString("gsxxtgr")));// ��˾��Ϣ�ṩ��
			mapStr.put("khyh", Util.null2String(rs.getString("khyh")));// ��������
			mapStr.put("yhzh", Util.null2String(rs.getString("yhzh")));// ��������
			mapStr.put("zczb", Util.null2String(rs.getString("zczb")));// ��������
			mapStr.put("sszb", Util.null2String(rs.getString("sszb")));// ��������
			mapStr.put("bzh", Util.null2String(rs.getString("bzh")));// ����
			mapStr.put("bzh1", Util.null2String(rs.getString("bzh1")));// ����
			mapStr.put("jyfw", jyfw);// ��Ӫ��Χ
			mapStr.put("xxtgfs", Util.null2String(rs.getString("xxtgfs")));// ��Ϣ�ṩ��ʽ
			mapStr.put("sh", Util.null2String(rs.getString("sh")));// ˰��
			mapStr.put("CutomStatus", "0");// ��˾״̬
			mapStr.put("zxwd", Util.null2String(rs.getString("zxwd")));// �����ĵ�
			mapStr.put("scfj", Util.null2String(rs.getString("fjsc")));// �ϴ�����
			mapStr.put("Version", "0");// ��Ϣ�汾
			mapStr.put("ModifyTime", now);// ��Ϣ����ʱ��
			mapStr.put("Remark", Util.null2String(rs.getString("bz")));// ��ע
			mapStr.put("modedatacreater", sqr);
			mapStr.put("modedatacreatertype", "0");
			mapStr.put("formmodeid", "52");
			iu.insert(mapStr, table_name_1);
			sql_dt = "select * from uf_custom where customCode='" + sysNo + "'";
			log.writeLog("��ʼ����ͻ�1" + sql_dt);
			rs_dt.executeSql(sql_dt);

			if (rs_dt.next()) {
				customId = Util.null2String(rs_dt.getString("id"));
			}
			ModeRightInfo ModeRightInfo = new ModeRightInfo();
			ModeRightInfo.editModeDataShare(Integer.valueOf(sqr), 52,
					Integer.valueOf(customId));

		}
		return SUCCESS;
	}

}
