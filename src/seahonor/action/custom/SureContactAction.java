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

public class SureContactAction implements Action {
	BaseBean log = new BaseBean();

	@Override
	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String tableName = "";
		String tableName1="uf_Contacts";
		String billid = "";
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
		String lb = "";
		String xglxr ="";
		String jdlxr = "";
		String mcjr = "";
		if(rs.next()){
			lb = Util.null2String(rs.getString("lb"));
			xglxr = Util.null2String(rs.getString("xglxr"));
			jdlxr = Util.null2String(rs.getString("jdlxr"));
			mcjr = Util.null2String(rs.getString("mcjr"));
		}
		String version ="0";		
		
		if("1".equals(lb)){
			sql="select Version+1 as Version from "+tableName1+" where id="+xglxr;
			rs.executeSql(sql);
			if(rs.next()){
				version =  Util.null2String(rs.getString("Version"));
			}
			if(!"".equals(xglxr)){
				GeneralNowInsert gni = new GeneralNowInsert();
				new SureCustomAction().insertHistory("-63",tableName1, "id", xglxr);
				sql="select Max(id) as billid from "+tableName1+" where superid = "+xglxr;
				log.writeLog("��ʼ������ʷ��¼sql " + sql+"mcjr"+mcjr);
				rs.executeSql(sql);
				if(rs.next()){
					billid= Util.null2String(rs.getString("billid"));	
					ModeRightInfo ModeRightInfo = new ModeRightInfo();
					ModeRightInfo.editModeDataShare(Integer.valueOf(mcjr),56,Integer.valueOf(billid));			
				}
			}
		}
		
		sql = "select * from " + tableName + " where requestid= " + requestid;
		log.writeLog("��ʼУ����ϵ��sql " + sql);
		rs.executeSql(sql);
		if (rs.next()) {
			if("0".equals(lb)){
				  billid = Util.null2String(rs.getString("jdlxr"));
			}else{
				  billid = Util.null2String(rs.getString("xglxr"));
			}
			String gkh = Util.null2String(rs.getString("mkh"));
			String ggys = Util.null2String(rs.getString("mgys"));
			String ghzhb = Util.null2String(rs.getString("mhzhb"));
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
			mapStr.put("cjr", Util.null2String(rs.getString("mcjr")));// ������
			mapStr.put("cjrq", Util.null2String(rs.getString("mcjrq")));// ��������
			//mapStr.put("cjsj", Util.null2String(rs.getString("cjsj")));// ����ʱ��
			//mapStr.put("cjxq", Util.null2String(rs.getString("cjxq")));// ��������
			mapStr.put("sqr", Util.null2String(rs.getString("msqr")));// ������
			mapStr.put("sqrq", Util.null2String(rs.getString("msqrq")));// ��������
			//mapStr.put("sqsj", Util.null2String(rs.getString("sqsj")));// ����ʱ��
			//mapStr.put("sqxq", Util.null2String(rs.getString("sqxq")));// ��������
			mapStr.put("Name", Util.null2String(rs.getString("mxmzw")));// ����(����)
			mapStr.put("xmyr", Util.null2String(rs.getString("mxmyr")));// ����(Ӣ/��)
			mapStr.put("lxrcm", Util.null2String(rs.getString("mlxrcm")));// ��ϵ�˲���
			mapStr.put("qz", Util.null2String(rs.getString("xqz")));// Ⱥ��
			mapStr.put("customName", Util.null2String(rs.getString("mssgs")));// ������˾
			mapStr.put("khmcyr", Util.null2String(rs.getString("mgsmcyr")));// ��˾����(Ӣ/��)
			mapStr.put("GroupName", Util.null2String(rs.getString("mssjt")));// ��������
			mapStr.put("jtmcyr", Util.null2String(rs.getString("mjtmcyr")));// ��������(Ӣ/��)
			mapStr.put("dept", Util.null2String(rs.getString("mbmzw")));// ����(����)
			mapStr.put("bmyr", Util.null2String(rs.getString("mbmyr")));// ����(Ӣ/��)
			mapStr.put("Position", Util.null2String(rs.getString("mzwzw")));// ְλ(����)
			mapStr.put("zwyr", Util.null2String(rs.getString("mzwyr")));// ְλ(Ӣ/��)
			mapStr.put("lxrsr", Util.null2String(rs.getString("mlxrsr")));// ��ϵ������
			mapStr.put("lzsj", Util.null2String(rs.getString("mlzsj")));// ��ְʱ��
			mapStr.put("tel", Util.null2String(rs.getString("mbgdh")));// �칫�绰
			mapStr.put("mobile", Util.null2String(rs.getString("myddh")));// �ƶ��绰
			mapStr.put("zzdh", Util.null2String(rs.getString("mzzdh")));// סլ�绰
			mapStr.put("Email", Util.null2String(rs.getString("mddyx")));// ��������
			mapStr.put("grwxh", Util.null2String(rs.getString("mgrwxh")));// ����΢�ź�
			mapStr.put("status", Util.null2String(rs.getString("mzzzt")));// ��ְ״̬
			mapStr.put("Fax", Util.null2String(rs.getString("mswcz")));// ������
			mapStr.put("Postcode", Util.null2String(rs.getString("myzbm")));// ��������
			mapStr.put("bgdz", Util.null2String(rs.getString("mbgdzzw")));// �칫��ַ(����)
			mapStr.put("bgdzyr", Util.null2String(rs.getString("mbgdzyr")));// �칫��ַ(Ӣ/��)
			mapStr.put("jtdz", Util.null2String(rs.getString("mjtdz")));// ��ͥ��ַ
			mapStr.put("ybgsdgx", ybgsgx);// �뱾��˾�Ĺ�ϵ
			mapStr.put("kh", gkh);// �ͻ�
			mapStr.put("gys", ggys);// ��Ӧ��
			mapStr.put("hzhb", ghzhb);// �������
			mapStr.put("xxtgr", Util.null2String(rs.getString("mxxtgr")));// ��Ϣ�ṩ��
			mapStr.put("xxtgfs", Util.null2String(rs.getString("mxxtgfs")));// ��Ϣ�ṩ��ʽ
			mapStr.put("picHead", Util.null2String(rs.getString("mmpzm")));// ��Ƭ����
			mapStr.put("picEnd", Util.null2String(rs.getString("mmpfm")));// ��Ƭ����			
			if("0".equals(lb)){
				mapStr.put("Version", "0");//��Ϣ�汾
				mapStr.put("dealStatus", "1");// ��Ϣ״̬
			}else{
				mapStr.put("Version", version);//��Ϣ�汾
			}
			mapStr.put("scfj", Util.null2String(rs.getString("mscfj")));// �ϴ�����
			mapStr.put("zxwd", Util.null2String(rs.getString("mzxwd")));// �����ĵ�
			mapStr.put("bz", Util.null2String(rs.getString("mbz")));// ��ע
			
			iu.updateGen(mapStr, "uf_Contacts", "id", billid);

		}
		return SUCCESS;
	}

}
