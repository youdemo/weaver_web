package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮��������ӡ��������״̬��Ϊ"�ѽ��"
public class BadgeFailureWorkflowAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("������ӡ��BadgeFailureWorkflowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		String tableName = "";
		String LendSeal = "";//���ӡ��
		String Application = "";//����������
			
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
				//�ָ����С��������ַ���
				String[] LendSeal1 = Util.null2String(rs.getString("LendSeal")).split(",");//���ӡ��
				Application = Util.null2String(rs.getString("Application"));
				log.writeLog("��֮��������ӡ��������״̬��Ϊ�ѽ��LendSeal1=" + LendSeal1);
				for (int i = 0; i < LendSeal1.length; i++) {
					//��ȡ���е����ݣ��Ǵ�0��ʼ�㵽�����32_13
					LendSeal = LendSeal1[i].substring(3);
					log.writeLog("��֮��������ӡ��������״̬��Ϊ�ѽ��LendSeal=" + LendSeal);
					String sql_up = "update uf_badges set State = '3' , ProcessApplicant ='"+Application+"' where id in (" + LendSeal + ")";
					rs.execute(sql_up);
					log.writeLog("��֮��������ӡ��������״̬��Ϊ�ѽ��sql_up=" + sql_up);
				}
			}
	
			//1��������2��ʧЧ��3���ѽ����4�����˻���5�������
			//String sql_up = "update uf_badges set State = '3' where id ='" + LendSeal + "'";
				/*String sql_up = "update uf_badges set State = '3' where id in (" + LendSeal + ")";
				rs.execute(sql_up);
				log.writeLog("���ӡ��״̬����sql_up=" + sql_up);*/
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}