package seahonor.action;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮�żĿ�ݴ�����action
public class ForTheCostWorkfAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("�Ŀ�ݴ�����ForTheCostWorkfAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();


		String tableName = "";
		String tableNamedt = "";
		String mainID = "";

		String BelongsToProject = "";//������Ŀ
		String 	ExpenseParty = "";//���óе���
		//String 	LetterType = "";//�ż�����
		String 	Category = "";//���
		String 	Recipient = "" ;//�ռ���
		String 	ReceiverOrg = "" ;//�ռ���λ
		String 	ReceiverTel = "" ;//�ռ��绰
		String 	ReceiverAdd = "" ;//�ռ���ַ
		String 	Content = "" ;//�ռ�����
		String 	ExpressInformation = "";//�����Ϣ����
		String Customer = "";//�ͻ�
		String 	Money = "";//��ݷ���
		String 	Signed = "";//��д��
		String 	Sender = "";//�ļ���
		String 	SenderDepartment = "" ;//�ļ�����
		String 	DateTime = "" ;//��������
		String 	Budget = "" ;//Ԥ��
		String 	ExpressCompany = "" ;//��ݹ�˾
		String 	PayMethod = "" ;//֧����ʽ
		

		String sql_1 = "";
		String sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID
				+ ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		
		if (!"".equals(tableName)) {
			// ��ѯ����
			sql = "select * from "+tableName + " where requestid="+requestid;
			rs1.execute(sql);
			if(rs1.next()){
				BelongsToProject = Util.null2String(rs1.getString("BelongsToProject"));//������Ŀ
				ExpenseParty = Util.null2String(rs1.getString("ExpenseParty"));//���óе���
				//LetterType = Util.null2String(rs1.getString("LetterType"));//�ż�����
				Category = Util.null2String(rs1.getString("Category"));//���
				Recipient = Util.null2String(rs1.getString("Recipient"));//�ռ���
				
				ReceiverOrg = Util.null2String(rs1.getString("ReceiverOrg"));//�ռ���λ
				ReceiverTel = Util.null2String(rs1.getString("ReceiverTel"));//�ռ��绰
				ReceiverAdd = Util.null2String(rs1.getString("ReceiverAdd"));//�ռ���ַ
				Content = Util.null2String(rs1.getString("Content"));//�ռ�����
				ExpressInformation = Util.null2String(rs1.getString("ExpressInformation"));//�����Ϣ����
				
				Customer = Util.null2String(rs1.getString("Customer"));//�ͻ�
				Money = Util.null2String(rs1.getString("Money"));//��ݷ���
				Signed = Util.null2String(rs1.getString("Signed"));//��д��
				Sender = Util.null2String(rs1.getString("Sender"));//�ļ���
				SenderDepartment = Util.null2String(rs1.getString("SenderDepartment"));//�ļ�����
				
				DateTime = Util.null2String(rs1.getString("DateTime"));//��������
				Budget = Util.null2String(rs1.getString("Budget"));//Ԥ��
				ExpressCompany = Util.null2String(rs1.getString("ExpressCompany"));//��ݹ�˾
				PayMethod = Util.null2String(rs1.getString("PayMethod"));//֧����ʽ
				
				Map<String, String> mapStr = new HashMap<String, String>();
				
				mapStr.put("BelongsToProject", BelongsToProject);
				mapStr.put("ExpenseParty", ExpenseParty);//1������ϵͳ����Ա����
				//mapStr.put("LetterType", "LetterType");//����ǵ�Ҫ���룬1�����ǿͻ�֤�¡�
				mapStr.put("Category", Category);
				mapStr.put("Recipient", Recipient);
				
				mapStr.put("ReceiverOrg", ReceiverOrg);
				mapStr.put("ReceiverTel", ReceiverTel);
				mapStr.put("ReceiverAdd", ReceiverAdd);
				mapStr.put("Content", Content);
				mapStr.put("ExpressInformation", ExpressInformation);
				
				mapStr.put("Customer", Customer);
				mapStr.put("Money", Money);
				mapStr.put("Signed", Signed);
				mapStr.put("Sender", Sender);
				mapStr.put("SenderDepartment", SenderDepartment);
				
				mapStr.put("DateTime", DateTime);
				mapStr.put("Budget", Budget);
				mapStr.put("ExpressCompany", ExpressCompany);
				mapStr.put("PayMethod", PayMethod);
				mapStr.put("State", "0");
				
				String tableNamex = "formtable_main_39";
				InsertUtil it = new InsertUtil();
				it.insert(mapStr, tableNamex);
			}											
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}

