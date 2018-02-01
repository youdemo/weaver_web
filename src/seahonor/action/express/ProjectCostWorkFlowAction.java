package seahonor.action.express;

import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮�żĿ������Ŀ����action
public class ProjectCostWorkFlowAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("����Ŀ��ProjectCostWorkFlowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();

		String tableName = "";
		String Money = "";// ��ݷ��ý��
		String ProjectName = "";//����ģ�е���Ŀ����
		String BelongsToProject = "";//������Ŀ����
		int Signed = 0;//��д��
		/*String SenderDepartment = "";// ����
		String ExpenseParty = "";// ���óе��� 0����ͻ��е�;1����˾�е�
		String Customer = "";// �ͻ�
		String custom = "";//����ģ�еĿͻ�
		
		String Department = "";//����ģ�еĲ���*/

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
				Signed =rs.getInt("Signed");
				BelongsToProject = Util.null2String(rs.getString("BelongsToProject"));//������Ŀ����
				Money = Util.null2String(rs.getString("Money"));//��ݷ��ý��
				//ExpenseParty = Util.null2String(rs.getString("ExpenseParty"));//���óе���
				//Customer = Util.null2String(rs.getString("Customer"));//�ͻ�
				//��ѯ��Ŀ���ñ���ģ
				String sql_2 = "select * from uf_ProjectCost where ProjectName = '"+BelongsToProject+"'";
					
					rs1.execute(sql_2);
					if (rs1.next()) {
						ProjectName = Util.null2String(rs1.getString("ProjectName"));//����ģ�е���Ŀ����
					}
					log.writeLog("�ͻ�ID" + ProjectName);
					if (!"".equals(ProjectName)) {
						sql_1 = "update  uf_ProjectCost set ProjectAmount = isnull(ProjectAmount,0) +"
								+ Money + " where ProjectName='" + BelongsToProject + "'";
						rs1.execute(sql_1);
						
					}else{
						//Ҫ����formmodeid����ֶΣ�Ϊ����ǰ̨����ģ�п��Կ��ĵ����뵽�������ݿ��и�����formmodeid
						sql_1 = "insert into uf_ProjectCost(formmodeid,ProjectName,ProjectAmount) values(74,'"
								+ BelongsToProject + "','" + Money + "')";
						rs1.execute(sql_1);
						// ��ȡȨ�ޱ�ID
						String sql_Datashare = "select * from uf_ProjectCost where id = (select  MAX(id) from uf_ProjectCost)";
						rs1.execute(sql_Datashare);
						log.writeLog("�ͻ�����sql" + sql_Datashare);
						if (rs1.next()) {
							int ID =rs1.getInt("id");
							log.writeLog("�ͻ�����1111ID" + ID);
							ModeRightInfo ModeRightInfo = new ModeRightInfo();
							//ModeRightInfo.editModeDataShare(creater,modeid,m_billid);
							ModeRightInfo.editModeDataShare(Signed,74,ID);
							log.writeLog("�ͻ�����2222ID" + ID);
						}
						
						
						/*String sql_Datashare = "select * from uf_ProjectCost where id = (select  MAX(id) from uf_ProjectCost)";
						rs1.execute(sql_Datashare);
						log.writeLog("�ͻ�����ID" + sql_Datashare);
						if (rs1.next()) {
							String ID = Util.null2String(rs1.getString("id"));
							sql_1 = "exec ProjectCost_right " + ID;
							rs2.execute(sql_1);
							log.writeLog("�ͻ�����ID2" + sql_1);
					
					}*/
				}
			}
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}