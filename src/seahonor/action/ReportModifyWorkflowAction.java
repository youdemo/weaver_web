package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮�ű����޸ĺ�״̬��Ϊδ�����״̬
public class ReportModifyWorkflowAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("���뱨���޸ĺ�ReportModifyWorkflowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		String tableName = "";
		String China = "";//���ı���
		String English = "";//Ӣ�ı���
		String Japan = "";//���ı���
		String DocumentId = "";//�ĵ����
			
		String sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID
				+ ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		if (!"".equals(tableName)) {
			// ��ѯ����
			sql = "select * from " + tableName + " where requestid="+ requestid;
			rs.execute(sql);
			while (rs.next()) {	
				China = Util.null2String(rs.getString("China"));
				English = Util.null2String(rs.getString("English"));
				Japan = Util.null2String(rs.getString("Japan"));
				DocumentId = Util.null2String(rs.getString("DocumentId"));
		}
			//0��δ���� 1���Ѷ��� 2��������
			//���China�еĻ�����������check�о���1��û�о��ǿ�
		if (China.equals("1")) {
			String sql_up = "update uf_report set DocumentStatus1 = '0' where DocumentId = '"+DocumentId+"'";
			rs.execute(sql_up);
			log.writeLog("���ı����޸�״̬����sql_up=" + sql_up);
		}
		if (English.equals("1")) {
			String sql_up = "update uf_report set DocumentStatus2 = '0' where DocumentId = '"+DocumentId+"'";
			rs.execute(sql_up);
			log.writeLog("Ӣ�ı����޸�״̬����sql_up=" + sql_up);
		}
		if (Japan.equals("1")) {
			String sql_up = "update uf_report set DocumentStatus3 = '0' where DocumentId = '"+DocumentId+"'";
			rs.execute(sql_up);
			log.writeLog("���ı����޸�״̬����sql_up=" + sql_up);
		}
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}