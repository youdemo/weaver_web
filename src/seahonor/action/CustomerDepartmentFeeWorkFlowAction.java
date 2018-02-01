package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮�żĿ��action
public class CustomerDepartmentFeeWorkFlowAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("������ӡ��CustomerDepartmentFeeWorkFlowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();

		String tableName = "";
		String Money = "";// ���ý��
		String SenderDepartment = "";// ����
		String ExpenseParty = "";// ���óе��� 0����ͻ��е�;1����˾�е�
		String Customer = "";// �ͻ�
		String custom = "";//����ģ�еĿͻ�
		String Department = "";//����ģ�еĲ���
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
			sql = "select * from " + tableName + " where requestid="
					+ requestid;
			rs.execute(sql);
			if (rs.next()) {
				SenderDepartment = Util.null2String(rs.getString("SenderDepartment"));//�Ŀ���˲���
				Money = Util.null2String(rs.getString("Money"));//���
				ExpenseParty = Util.null2String(rs.getString("ExpenseParty"));//���óе���
				Customer = Util.null2String(rs.getString("Customer"));//�ͻ�

				if (ExpenseParty.equals("0")) {
					// ���뵽�ͻ�����
					/*
					 * sql_1 = "insert into uf_custom(CustomerAmount,custom) "
					 * +"values('"+ Money+"','"+Customer+"')";
					 */
					// sql_1 =
					// "update uf_custom set CustomerAmount +='"+Money+"' where custom='"+Customer+"'";
					String sql_2 = "select * from uf_CustomeAmount where custom = '"+Customer+"'";
					
					rs1.execute(sql_2);
					if (rs1.next()) {
						custom = Util.null2String(rs1.getString("custom"));
					}
					log.writeLog("�ͻ�ID" + custom);
					if (!"".equals(custom)) {
						sql_1 = "update  uf_CustomeAmount set CustomerAmount = isnull(CustomerAmount,0) +"
								+ Money + " where custom='" + Customer + "'";
						rs1.execute(sql_1);
						
					}else{
						sql_1 = "insert into uf_CustomeAmount(custom,CustomerAmount) values('"
								+ Customer + "','" + Money + "')";
						rs1.execute(sql_1);
						// ��ȡȨ�ޱ�ID
						String sql_Datashare = "select * from uf_CustomeAmount where id = (select  MAX(id) from uf_CustomeAmount)";
						rs1.execute(sql_Datashare);
						log.writeLog("�ͻ�����ID1" + sql_Datashare);
						if (rs1.next()) {
							String ID = Util.null2String(rs1.getString("id"));
							sql_1 = "exec sh_custome_right " + ID;
							rs2.execute(sql_1);
							log.writeLog("�ͻ�����ID2" + sql_1);
						}
					
					}
				}else{
					// ���뵽��˾���Ž������
					// sql_1 =
					// "update uf_CompanyDepartFee set CostAmount +='"+Money+"' where Department='"+SenderDepartment+"'";
					String sql_2 = "select * from uf_CompanyDepartFee where department = '"+SenderDepartment+"'";
					rs1.execute(sql_2);
					if (rs1.next()) {
						Department = Util.null2String(rs1.getString("Department"));
					}
					if (Department.equals("")) {
						sql_1 = "insert into uf_CompanyDepartFee(Department,CostAmount) values('"
								+ SenderDepartment + "','" + Money + "')";
						rs1.execute(sql_1);

						// ��ȡȨ�ޱ�ID
						String sql_Datashare = "select * from uf_CompanyDepartFee where id = (select  MAX(id) from uf_CompanyDepartFee)";
						rs1.execute(sql_Datashare);
						log.writeLog("��˾���ŷ���ID1" + sql_Datashare);
						if (rs1.next()) {
							String ID = Util.null2String(rs1.getString("id"));
							sql_1 = "exec sh_replace_right " + ID;
							rs2.execute(sql_1);
							log.writeLog("��˾���ŷ���ID2" + sql_1);
						}
					}else{
						sql_1 = "update uf_CompanyDepartFee set CostAmount = isnull(CostAmount,0) +"
								+ Money+ " where Department='"+ SenderDepartment + "'";
						/*
						 * sql_1 =
						 * "insert into uf_CompanyDepartFee(Department,CostAmount) values('"
						 * + SenderDepartment + "','" + Money + "')";
						 */
						rs1.execute(sql_1);
					}
				}
			}
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}