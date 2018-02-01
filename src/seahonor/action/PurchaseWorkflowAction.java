package seahonor.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮�Ų����ճ��ɹ�����Ʒaction������й����ӱ�ģ�������ϸ��ģ�
public class PurchaseWorkflowAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("�����ճ��ɹ�����ƷPurchaseWorkflowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		RecordSet rs3 = new RecordSet();

		String tableName = "";
		String tableNamedt = "";
		String mainID = "";
		int Numbers = 0;// ��ϸ�� �е�����
		String Name ="";//��ϸ���е�����
		String Month = "";//�����е��·�
		String Year = "";//�����е����
		String id_1 = "";
		int	count_cc =0;

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
				mainID = Util.null2String(rs1.getString("id"));//��ȡ�����е�id����Ϊ��ϸ���е�mainid
				Month = Util.null2String(rs1.getString("Month"));//��ȡ�����е��·�
				Year = Util.null2String(rs1.getString("Year"));//��ȡ�����е����
			}			
			//��ѯ��ϸ��
			sql = "select * from " + tableNamedt + " where mainid="+mainID;
			rs.execute(sql);
			while(rs.next()){
			//	String dtID = Util.null2String(rs.getString("id"));//��ȡ��ϸ����id
				Name = Util.null2String(rs.getString("Name"));//��ȡ��ϸ���е�����
				Numbers = rs.getInt("Numbers");//��ȡ��ϸ���е�����
						
				String sql_2 = "select id from uf_Purchase where Month = '"+Month+"' and Year = '"+Year+"'";	
				log.writeLog("sql_2=" + sql_2);
				log.writeLog("ִ�н��=" + rs3.execute(sql_2));
//				rs3.execute(sql_2);
				if (rs3.next()) {
					log.writeLog("in");
					id_1 = Util.null2String(rs3.getString("id"));//
					log.writeLog("id_1=" + id_1);
				}
				log.writeLog("����ID" + id_1);
				
				if ("".equals(id_1)) {
					String sql_3 = "insert into uf_Purchase(Year,Month) values('" + Year +"','" + Month + "')";
					rs1.execute(sql_3);
					
					sql_3 = "select id from uf_Purchase order by id desc";
					rs1.execute(sql_3);
					if (rs1.next()) {
						id_1 = Util.null2String(rs1.getString("id"));//����ģ�е�����
					}					
					// ��ȡȨ�ޱ�IDs
					String sql_Datashare = "select * from uf_Purchase where id = (select  MAX(id) from uf_Purchase)";
					rs1.execute(sql_Datashare);
					log.writeLog("�ɹ�ID" + sql_Datashare);
					if (rs1.next()) {
						String ID = Util.null2String(rs1.getString("id"));
						sql_1 = "exec Purchase_right " + ID;
						rs2.execute(sql_1);
						log.writeLog("�ɹ�ID2" + sql_1);
					}
				}				
				String sql_3 = "select count(id) count_cc from uf_Purchase_dt1 where mainid = '"+id_1+"' and Name = '"+Name+"'";	
				rs1.execute(sql_3);
				if (rs1.next()) {
					//id_1 = Util.null2String(rs1.getString("id"));//����ģ�е�����
						count_cc = rs1.getInt("count_cc");//
				}
				
				if (count_cc>0) {
					sql_1 = "update  uf_Purchase_dt1 set  Numbers = isnull(Numbers,0) +"
							+ Numbers + " where Name='" + Name + "'and mainid = '"+id_1+"'";
					rs1.execute(sql_1);
					
				}else{
					sql_1 = "insert into uf_Purchase_dt1(mainid,Name,Numbers) values('"+id_1+"','"
							+ Name + "','" + Numbers + "')";
					rs1.execute(sql_1);
					
				}
			}										
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}