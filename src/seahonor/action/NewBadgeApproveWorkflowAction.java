package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮���½�֤�����������б���ģ״̬�ı�
public class NewBadgeApproveWorkflowAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("��֮���½�֤������������NewBadgeApproveWorkflowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		String tableName = "";
		String Name = "";//֤������
			
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
				Name = Util.null2String(rs.getString("Name"));//֤������
			}
				//4�Ǵ����˻�״̬
				 //1��������2��ʧЧ��3���ѽ����4�����˻���5������У�6���˻��С�7δ������
				String sql_up = "update uf_badges set State = '1' where Name in ('" + Name + "')";
				rs.execute(sql_up);
				log.writeLog("��֮���½�֤������������sql_up=" + sql_up);
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}