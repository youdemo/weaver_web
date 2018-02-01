
package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮�ſ�ݱ���Action
public class ExpressReimbursementWorkflowAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("�����ݱ���ExpressReimbursementWorkflowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();

		String tableName = "";
		String RemainingLoan = "";//ʣ������Ŀ��
		String project = "";//��Ŀ����

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
			sql = "select * from " + tableName + " where requestid="+ requestid;
			rs.execute(sql);
			if (rs.next()) {
				RemainingLoan = Util.null2String(rs.getString("RemainingLoan"));//ʣ������Ŀ��
				project = Util.null2String(rs.getString("project"));//��������Ŀ��
				
				sql_1 = "update  uf_ProjectCost set BorrowingAmount ='"+ RemainingLoan + "' where ProjectName='" + project + "'";
				rs1.execute(sql_1);		
				log.writeLog("��ݱ���sql_1" + sql_1);
			}
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}