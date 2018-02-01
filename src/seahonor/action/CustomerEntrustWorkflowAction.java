package seahonor.action;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮�ſͻ�ί�б�������action������й����ӱ�ģ�������ϸ��ģ�
public class CustomerEntrustWorkflowAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("�ͻ�ί�б�������CustomerEntrustWorkflowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		InsertUtil it = new InsertUtil();

		String tableName = "";
		String tableNamedt = "";
		String mainID = "";

		String Application = "";//�ͻ������ˣ�����
		String 	Customer = "";//�����ͻ�������
		
		String 	Name = "" ;//���ƣ���ϸ��
		String 	StartDate = "" ;//��Ч��ʼ���ڣ���ϸ��
		String 	EndDate = "" ;//��Ч�������ڣ���ϸ��
		String 	Borrowed = "" ;//���ã���ϸ��
		String 	ChooseClassification = "" ;//ѡ����ࣨ��ϸ��
		String 	Remark = "";//��ע����ϸ��
		String  unit ="";
		String numbers = "";
		String zp="";
		String zxwd = "";
		String fjsc="";

		String sql_1 = "";
		String sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID
				+ ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		if (!"".equals(tableName)) {
			tableNamedt = tableName + "_dt1";//���������Ϊ���ж����ϸ����׼��
			// ��ѯ����
			sql = "select * from "+tableName + " where requestid="+requestid;
			rs1.execute(sql);
			if(rs1.next()){
				mainID = Util.null2String(rs1.getString("id"));//��ȡ�����е�id����Ϊ��ϸ���е�mainid
				
				Application = Util.null2String(rs1.getString("Application"));//��ȡ������������
				Customer = Util.null2String(rs1.getString("Customer"));//��ȡ�����еĿͻ�
			}			
			//��ѯ��ϸ��
			sql = "select * from " + tableNamedt + " where mainid="+mainID;
			rs.execute(sql);
			
			while(rs.next()){
				
				Name = Util.null2String(rs.getString("Name"));//��ȡ��ϸ���е�����				
				StartDate = Util.null2String(rs.getString("StartDate"));//��ȡ��Ч��ʼ���ڣ���ϸ��
				EndDate = Util.null2String(rs.getString("EndDate"));//��ȡ��Ч�������ڣ���ϸ��
				Borrowed = Util.null2String(rs.getString("Borrowed"));//��ȡ���ã���ϸ��
				ChooseClassification = Util.null2String(rs.getString("ChooseClassification"));//��ȡѡ����ࣨ��ϸ��
				Remark = Util.null2String(rs.getString("Remark"));//��ע����ϸ��
				unit = Util.null2String(rs.getString("unit"));
				numbers = Util.null2String(rs.getString("numbers"));
				zp = Util.null2String(rs.getString("zp"));
				zxwd = Util.null2String(rs.getString("zxwd"));
				fjsc = Util.null2String(rs.getString("shcj"));
				sql = "select type from uf_BadgesClassify where id="+ChooseClassification;
				String type = "";
				rs1.executeSql(sql);
				while(rs1.next()){
					type = Util.null2String(rs1.getString("type"));
				}
				
				Map<String, String> mapStr = new HashMap<String, String>();
				
				mapStr.put("creater", Application);
				mapStr.put("modedatacreater", "1");//1������ϵͳ����Ա����
				mapStr.put("Classify", "1");//����ǵ�Ҫ���룬1�����ǿͻ�֤�¡�
				mapStr.put("Customer", Customer);
				mapStr.put("type", type);
				mapStr.put("unit", unit);
				mapStr.put("Numbers", numbers);
				mapStr.put("zp", zp);
				mapStr.put("zxwd", zxwd);
				mapStr.put("Attachment", fjsc);
				mapStr.put("Name", Name);
				mapStr.put("BadgeType", ChooseClassification);
				mapStr.put("StartDate", StartDate);
				mapStr.put("ValidTerm", EndDate);
				mapStr.put("Borrow", Borrowed);
				mapStr.put("Remark", Remark);
				mapStr.put("modedatacreatertype", "0");
				mapStr.put("formmodeid", "69");
				
				String tableNamex = "uf_badges";
				
				it.insert(mapStr, tableNamex);
				// ��ȡȨ�ޱ�IDs
				String sql_Datashare = "select * from uf_badges where id = (select  MAX(id) from uf_badges)";
				rs1.execute(sql_Datashare);
				log.writeLog("�ͻ�ί��ID" + sql_Datashare);
				if (rs1.next()) {
					int ID = rs1.getInt("id");
					sql_1 = "exec CustomerEntrust_right " + ID;
					rs2.execute(sql_1);
					log.writeLog("�ͻ�ί��ID2" + sql_1);
					
					insertGs(Integer.parseInt(Application),69,ID);
				}
			}										
		} else {
			return "-1";
		}
		return SUCCESS;
	}

	private boolean insertGs(int creater,int modeid,int m_billid){
		ModeRightInfo ModeRightInfo = new ModeRightInfo();
		ModeRightInfo.editModeDataShare(creater,modeid,m_billid);//�½���ʱ����ӹ���
		return true;
	}
}
