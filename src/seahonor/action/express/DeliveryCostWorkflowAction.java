package seahonor.action.express;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
	//��֮�żĿ���п�ݷ���action
	public class DeliveryCostWorkflowAction implements Action {

		BaseBean log = new BaseBean();// ����д����־�Ķ���

		public String execute(RequestInfo info) {
			log.writeLog("�Ŀ���п�ݷ���DeliveryCostWorkflowAction������������");

			String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
			String requestid = info.getRequestid();

			RecordSet rs = new RecordSet();
			RecordSet rs1 = new RecordSet();
			RecordSet rs2 = new RecordSet();

			String tableName = "";
			String tableNamedt = "";
			String mainID = "";
			
			String 	CourierCompany = "" ;//��ݹ�˾
			String 	DateTime = "" ;//�ļ�����
			String 	Sender = "" ;//�ļ���
			String 	ReceiverAdd = "" ;//�ռ���ַ
			String 	PayMethod = "" ;//֧����ʽ
			String 	Money = "";//��ݷ���
			String 	BelongsToProject = "" ;//������Ŀ

			String sql_1 = "";
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
					//mainID = Util.null2String(rs1.getString("id"));//��ȡ�����е�id����Ϊ��ϸ���е�mainid					
					CourierCompany = Util.null2String(rs1.getString("CourierCompany"));//��ȡ�����п�ݹ�˾
					DateTime = Util.null2String(rs1.getString("DateTime"));//��ȡ�����мļ�����
					Sender = Util.null2String(rs1.getString("Sender"));//��ȡ�����мļ���
					ReceiverAdd = Util.null2String(rs1.getString("ReceiverAdd"));//��ȡ�������ռ���ַ
					PayMethod = Util.null2String(rs1.getString("PayMethod"));//��ȡ������֧����ʽ
					Money = Util.null2String(rs1.getString("Money"));//��ȡ�����п�ݷ���
					BelongsToProject = Util.null2String(rs1.getString("BelongsToProject"));//��ȡ������������Ŀ
					
					Map<String, String> mapStr = new HashMap<String, String>();
					
					mapStr.put("CourierCompany", CourierCompany);
					mapStr.put("modedatacreater", "1");//1������ϵͳ����Ա����
					mapStr.put("Classify", "1");//����ǵ�Ҫ���룬1�����ǿͻ�֤�¡�
					mapStr.put("DateTime", DateTime);
					
					mapStr.put("Sender", Sender);
					mapStr.put("ReceiverAdd", ReceiverAdd);
					mapStr.put("PayMethod", PayMethod);
					mapStr.put("Money", Money);
					mapStr.put("BelongsToProject", BelongsToProject);
					/*mapStr.put("waySys", waySys);
					mapStr.put("wayEmail", wayEmail);
					mapStr.put("waySms", waySms);
					mapStr.put("reName", reName);
					mapStr.put("other", other);
					mapStr.put("notifier", notifier);
					mapStr.put("is_active", is_active);*/
					
					String tableNamex = "uf_badges";
					InsertUtil it = new InsertUtil();
					it.insert(mapStr, tableNamex);
					// ��ȡȨ�ޱ�IDs
					String sql_Datashare = "select * from uf_badges where id = (select  MAX(id) from uf_badges)";
					rs1.execute(sql_Datashare);
					log.writeLog("�ͻ�ί��ID" + sql_Datashare);
					if (rs1.next()) {
						String ID = Util.null2String(rs1.getString("id"));
						sql_1 = "exec CustomerEntrust_right " + ID;
						rs2.execute(sql_1);
						log.writeLog("�ͻ�ί��ID2" + sql_1);
					}
				}													
			} else {
				return "-1";
			}
			return SUCCESS;
		}

	}
