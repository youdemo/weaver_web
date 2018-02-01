package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮������黹ӡ�������б���ģ״̬�ı�
public class BadgeReturnWorkflowAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("����黹ӡ��BadgeReturnWorkflowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		String tableName = "";
		String ReturnSeal = "";//�黹ӡ��
			
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
				ReturnSeal = Util.null2String(rs.getString("ReturnSeal"));//�黹ӡ��			
				/*  
				 * �������εĴ���
				 * //�ָ����С��������ַ���
				String[] ReturnSeal1 = Util.null2String(rs.getString("ReturnSeal")).split(",");//�黹ӡ��	
				log.writeLog("��֮������黹ӡ��״̬Ϊ����ReturnSeal=" + ReturnSeal);
				for (int i = 0; i < ReturnSeal1.length; i++) {
					//��ȡ���е����ݣ��Ǵ�0��ʼ�㵽�����32_13
					ReturnSeal = ReturnSeal1[i].substring(3);
					log.writeLog("��֮������黹ӡ��״̬Ϊ����sql_up=ReturnSeal=" + ReturnSeal);
					String sql_up = "update uf_badges set State = '1' where id in (" + ReturnSeal + ")";
					rs.execute(sql_up);
					log.writeLog("��֮������黹ӡ��״̬����sql_up=" + sql_up);
				}
*/
			}
		
				String sql_up = "update uf_badges set State = '1' where id in (" + ReturnSeal + ")";
				rs.execute(sql_up);
				log.writeLog("�黹ӡ��״̬����sql_up=" + sql_up);
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}