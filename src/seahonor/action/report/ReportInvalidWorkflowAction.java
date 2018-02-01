package seahonor.action.report;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮�ű������Ϻ�״̬��Ϊ������״̬
public class ReportInvalidWorkflowAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("���뱨�����Ϻ�ReportModifyWorkflowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		String tableName = "";
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
				DocumentId = Util.null2String(rs.getString("DocumentId"));
		}
			//0��δ���� 1���Ѷ��� 2��������
			//����״̬��0Ϊ������1Ϊ����
			//���China�еĻ�����������check�о���1��û�о��ǿ�	
			String sql_up = "update uf_report set DocumentStatus1 = '2',DocumentStatus2 = '2',DocumentStatus3 = '2'"
						+",ReportState = '1' where DocumentId = '"+DocumentId+"'";
			rs.execute(sql_up);
			log.writeLog("��������״̬����sql_up=" + sql_up);
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}