package seahonor.action.badge;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮��֤���������action������й����ӱ�ģ�������ϸ��ģ�
public class CustomerEntrustWorkflowAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("֤���������CustomerEntrustWorkflowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();


		String tableName = "";
		String tableNamedt = "";
		String mainID = "";

		//int Application = 0;//�ͻ������ˣ�����	
		String Application = "";//�ͻ������ˣ�����
		String 	Customer = "";//�����ͻ�������
		String CustomerBasic = "";//�ͻ�ί������
		String attachment = "";//�ϴ�����
		String ApplicationDate = "";//�ϴ�����
		
		String 	Name = "" ;//֤�����ƣ���ϸ��
		String 	StartDate = "" ;//������ڣ���ϸ��
		String 	EndDate = "" ;//��Ч��ֹ���ڣ���ϸ��
		String 	Borrowed = "" ;//���ã���ϸ��
		String 	ChooseClassification = "" ;//ѡ����ࣨ��ϸ��
		String 	Remark = "";//��ע����ϸ��
		String 	Numbers = "";//��������ϸ��
		String 	unit = "";//��λ����ϸ��
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
	
				//Month = Util.null2String(rs1.getString("Month"));//��ȡ�����е��·�
				//Year = Util.null2String(rs1.getString("Year"));//��ȡ�����е����
				
				//Application = rs1.getInt("Application");//��ȡ������������
				Application = Util.null2String(rs1.getString("Application"));//��ȡ������������
				Customer = Util.null2String(rs1.getString("Customer"));//��ȡ�����еĿͻ�
				CustomerBasic = Util.null2String(rs1.getString("CustomerBasic"));//�ͻ�ί������
				attachment = Util.null2String(rs1.getString("AttachmentUpload"));//�ϴ�����
				ApplicationDate = Util.null2String(rs1.getString("ApplicationDate"));//��������
				
				log.writeLog("��������ApplicationDate=" + ApplicationDate);
				log.writeLog("�ϴ�����attachment=" + attachment);
				
			}			
			//��ѯ��ϸ��
			sql = "select * from " + tableNamedt + " where mainid="+mainID;
			rs.execute(sql);
			while(rs.next()){
			//	String dtID = Util.null2String(rs.getString("id"));//��ȡ��ϸ����id
				//Name = Util.null2String(rs.getString("Name"));//��ȡ��ϸ���е�����
				//Numbers = rs.getInt("Numbers");//��ȡ��ϸ���е�����
				
				Name = Util.null2String(rs.getString("Name"));//��ȡ��ϸ���е�����				
				StartDate = Util.null2String(rs.getString("StartDate"));//��ȡ��Ч��ʼ���ڣ���ϸ��
				EndDate = Util.null2String(rs.getString("EndDate"));//��ȡ��Ч�������ڣ���ϸ��
				Borrowed = Util.null2String(rs.getString("Borrowed"));//��ȡ���ã���ϸ��
				ChooseClassification = Util.null2String(rs.getString("ChooseClassification"));//��ȡѡ����ࣨ��ϸ��
				Remark = Util.null2String(rs.getString("Remark"));//��ע����ϸ��
				Numbers = Util.null2String(rs.getString("Numbers"));//��������ϸ��
				unit = Util.null2String(rs.getString("unit"));//��λ����ϸ��
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
				
				mapStr.put("creater",Application);
				mapStr.put("modedatacreater", "1");//1������ϵͳ����Ա����
				mapStr.put("formmodeid", "69");
				mapStr.put("Classify", "1");//����ǵ�Ҫ���룬1�����ǿͻ�֤�¡�0,�����ڲ�֤��
				mapStr.put("Customer", Customer);
				mapStr.put("State", "1");
				mapStr.put("CustomerBasic", CustomerBasic);//֤��ί������
				//mapStr.put("Attachment", attachment);//�ϴ�����
				mapStr.put("CreateDate", ApplicationDate);//�ϴ�����
				mapStr.put("type", type);
				
				mapStr.put("Name", Name);
				mapStr.put("BadgeType", ChooseClassification);
				mapStr.put("StartDate", StartDate);
				mapStr.put("ValidTerm", EndDate);
				//mapStr.put("Borrow", Borrowed);
				mapStr.put("Remark", Remark);
				mapStr.put("Numbers", Numbers);
				mapStr.put("unit", unit);
				mapStr.put("zp", zp);
				mapStr.put("zxwd", zxwd);
				String att="";
				if(!"".equals(attachment)){
					att=attachment;
					if(!"".equals(fjsc)){
						att=att+","+fjsc;
					}
				}else{
					att=fjsc;
				}
				mapStr.put("Attachment", att);
				String tableNamex = "uf_badges";
				InsertUtil it = new InsertUtil();
				it.insert(mapStr, tableNamex);
				// ��ȡȨ�ޱ�IDs
				
				
				String sql_Datashare = "select * from uf_badges where id = (select  MAX(id) from uf_badges)";
				rs1.execute(sql_Datashare);
				log.writeLog("�ͻ�ί��sql" + sql_Datashare);
				if (rs1.next()) {
					int ID =rs1.getInt("id");
					log.writeLog("�ͻ�ί��1111ID" + ID);
					ModeRightInfo ModeRightInfo = new ModeRightInfo();
					ModeRightInfo.editModeDataShare(1,69,ID);
					log.writeLog("�ͻ�ί��2222ID" + ID);
				}
							
			}										
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}
