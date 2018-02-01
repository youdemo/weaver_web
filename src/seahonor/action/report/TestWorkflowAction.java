package seahonor.action.report;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class TestWorkflowAction implements Action{
	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("������ӡ�������˻ص���һ�ڵ�BadgeFailureWorkflowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();
		//String workflow = info.;

		RecordSet rs = new RecordSet();
		String tableName = "";
		String LendSeal = "";//���ӡ��
			
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
				//LendSeal = Util.null2String(rs.getString("LendSeal"));//���ӡ��	
				//�ָ����С��������ַ���
				String[] LendSeal1 = Util.null2String(rs.getString("LendSeal")).split(",");//���ӡ��
				log.writeLog("���ӡ������״̬Ϊ����и���LendSeal1=" + LendSeal1);
				for (int i = 0; i < LendSeal1.length; i++) {
					//��ȡ���е����ݣ��Ǵ�0��ʼ�㵽�����32_13
					LendSeal = LendSeal1[i].substring(3);
					log.writeLog("���ӡ������״̬Ϊ�˻��и���LendSeal=" + LendSeal);
					String sql_up = "update uf_badges set State = '1' , ProcessApplicant ='" + "' where id in (" + LendSeal + ")";
					rs.execute(sql_up);
					log.writeLog("���ӡ���������˻ص���һ�ڵ�״̬����sql_up=" + sql_up);
				}
			}
			//1��������2��ʧЧ��3���ѽ����4�����˻���5�������
			//String sql_up = "update uf_badges set State = '3' where id ='" + LendSeal + "'";
				/*String sql_up = "update uf_badges set State = '1' where id in (" + LendSeal + ")";
				rs.execute(sql_up);
				log.writeLog("���ӡ���������˻ص���һ�ڵ�״̬����sql_up=" + sql_up);*/
		} else {
			return "-1";
		}
		return SUCCESS;
	}
}
