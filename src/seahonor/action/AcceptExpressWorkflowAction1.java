package seahonor.action;

import com.greenpineyu.fel.parser.FelParser.integerLiteral_return;

import seahonor.util.RandUtil;
import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
//��֮���տ������action
public class AcceptExpressWorkflowAction1 implements Action {
	public String execute(RequestInfo info) {
//		new BaseBean().writeLog("�����տ��AcceptExpressWorkflowAction������������");
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		
		String sql1 = "";
		String sql2 = "";
		String tableName = "";
		String tableNamedt = "";
		String djrxm = "";//��ȡ�����еĵǼ�������
 		String kddh= "";//��ȡ��ϸ���ݵ���
		String kdxx = "";//��ȡ��ϸ������Ϣ
		String jsr = "";//��ȡ��ϸ�������
		String yzm = "";//��ȡ��ϸ����֤��
		//��ϸ��Ļ�ȡ
		sql1 = " Select tablename From Workflow_billdetailtable  Where billid in ("
				+ "Select formid From workflow_base Where id="
				+ info.getWorkflowid() + ")";
		new BaseBean().writeLog("sql1---------" + sql1);
		rs.executeSql(sql1);
		if (rs.next()) {
			tableNamedt = Util.null2String(rs.getString("tablename"));
		}
		if (!" ".equals(tableNamedt)) {
			//����Ļ�ȡ
			sql2 = " Select tablename From Workflow_bill Where id in ("
					+ " Select formid From workflow_base Where id= "
					+ info.getWorkflowid() + ")";
			new BaseBean().writeLog("sql2---------" + sql2);
			rs.executeSql(sql2);
			if (rs.next()) {
				tableName = Util.null2String(rs.getString("tablename"));
				//��ȡ��ϸ����ֶ�
				String sql_1 = " select * from " + tableNamedt
						+ " where mainid in (" + " Select id From " + tableName
						+ " where requestid = " + info.getRequestid() + ")";
				new BaseBean().writeLog("sql_1---------" + sql_1);
				rs1.executeSql(sql_1);
				while (rs1.next()) {
					kddh  = Util.null2String(rs1.getString("kddh"));	//��ȡ��ϸ���ݵ���
					kdxx = Util.null2String(rs1.getString("kdxx"));	//��ȡ��ϸ������Ϣ
					jsr = Util.null2String(rs1.getString("jsr"));	//��ȡ��ϸ�������
					yzm = RandUtil.ranNum(4);//��ȡ�����֤��
				}
					if (!" ".equals(tableName)) {
						//��ȡ���ֶ���Ϣ
						String sql_3 = "select * from " + tableName + " where "
						+"requestid = "+ info.getRequestid();
						new BaseBean().writeLog("sql_3---------" + sql_3);
						rs1.executeSql(sql_3);
						/*if (rs1.next()) {
							djrxm = Util.null2String(rs1.getString("djrxm"));	//��ȡ�����еǼ�������
						}*/
					}
					
					int flag = 0;
					StringBuffer buff_sql = new StringBuffer();
					
					buff_sql.append("update ");buff_sql.append(tableName);
					buff_sql.append(" set ");
					
					if (!"".equals(kddh)) {
						buff_sql.append(" kddh = '");buff_sql.append(kddh);buff_sql.append("' ");
						flag++;
					}
					if (!"".equals(kdxx)) {
						buff_sql.append(" kdxx = '");buff_sql.append(kdxx);buff_sql.append("' ");
						flag++;
					}
					if (!"".equals(jsr)) {
						buff_sql.append(" jsr = '");buff_sql.append(kdxx);buff_sql.append("' ");
						flag++;
					}
					if (!"".equals(yzm)) {
						buff_sql.append(" yzm = '");buff_sql.append(yzm);buff_sql.append("' ");
						flag++;
					}
					
					if(flag > 0){
						buff_sql.append(" where id=");buff_sql.append(" where id=");
					}
				} 
			} 
		return SUCCESS;
	}

}

