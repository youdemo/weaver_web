package seahonor.action.purchsse;

import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮���¶Ȳɹ�����Ʒ��Ӧ��ѯ��action
public class GoodsNameQueryPriceWorkflowAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("��֮���¶Ȳɹ�����Ʒ��Ӧ��ѯ��GoodsNameQueryPriceWorkflowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		RecordSet rs3 = new RecordSet();

		String tableName = "";
		String tableNamedt = "";
		String mainID = "";
		
		String id = "";//��ϸ���е�id
		String QueryPrice = "";//ѯ��
		
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
			}			
			//��ѯ��ϸ��
			sql = "select * from " + tableNamedt + " where mainid="+mainID;
			rs.execute(sql);
			while(rs.next()){
					id = Util.null2String(rs.getString("id"));//��ȡ��ϸ����id
				
					QueryPrice = "<a href=\''/formmode/view/AddFormMode.jsp?modeId=104&formId=-155&type=1&field8336="+id+"\'' target=\''_blank\''>���ѯ��</a>";
					String sql_1 = "update formtable_main_73_dt1 set QueryPrice='"+QueryPrice+"' where id="+id;
					rs1.execute(sql_1);
					log.writeLog("��ѯ��Ӧ��QueryPrice="+QueryPrice);
					log.writeLog("��ѯ��Ӧ��sql_1="+sql_1);
				 
			}										
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}
