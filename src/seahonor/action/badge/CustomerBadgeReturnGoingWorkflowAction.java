package seahonor.action.badge;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮������ͻ�֤���˻������б���ģ״̬�ı�
public class CustomerBadgeReturnGoingWorkflowAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("����ͻ�֤���˻�������CustomerBadgeReturnGoingWorkflowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String tableName = "";
		String CustomerBadgeRet = "";//�ͻ�֤���˻�
		String mainid="";	
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
			if(rs.next()){
				mainid = Util.null2String(rs.getString("id"));	
			}
			
/*			while (rs.next()) {	
				//�ָ����С��������ַ���CustomerBadgeReturn�ǿͻ�ӡ���ֶΣ�
				String[] CustomerBadgeRet1 = Util.null2String(rs.getString("CustomerBadgeReturn")).split(",");//�ͻ�֤���˻�		
				log.writeLog("��֮�������˻�ӡ��������״̬��Ϊ�˻���CustomerBadgeRet1=" + CustomerBadgeRet1);
				for (int i = 0; i < CustomerBadgeRet1.length; i++) {
					//��ȡ���е����ݣ��Ǵ�0��ʼ�㵽�����32_13
					CustomerBadgeRet = CustomerBadgeRet1[i].substring(3);
					log.writeLog("��֮�������˻�ӡ��������״̬��Ϊ�˻���LendSeal=" + CustomerBadgeRet);
					String sql_up = "update uf_badges set State = '6' where id in (" + CustomerBadgeRet + ")";
					rs.execute(sql_up);
					log.writeLog("�ͻ�֤���˻�״̬����sql_up=" + sql_up);
				}
			}*/
			
			//1��������2��ʧЧ��3���ѽ����4�����˻���5������У�6���˻��С�
			sql="select * from "+tableName+"_dt1 where mainid="+mainid;
			rs.execute(sql);
			while (rs.next()) {		
				CustomerBadgeRet = Util.null2String(rs.getString("CustomerBadgeReturn"));//���ӡ��
			    String sql_up = "update uf_badges set State = '6' where id in (" + CustomerBadgeRet + ")";
				rs1.execute(sql_up);
				log.writeLog("�ͻ�ӡ������״̬Ϊ�˻��и���sql_up=" + sql_up);
			}	
				/*//4�Ǵ����˻�״̬	
				String sql_up = "update uf_badges set State = '6' where id in (" + CustomerBadgeRet1 + ")";
				rs.execute(sql_up);
				log.writeLog("�ͻ�֤���˻�״̬����sql_up=" + sql_up);*/
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}