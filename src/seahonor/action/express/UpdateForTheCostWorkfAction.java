package seahonor.action.express;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮�ſ�ݹ�˾�������������̹鵵���´�����Ϊ�ѽ���
public class UpdateForTheCostWorkfAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("��֮�ſ�ݹ�˾�������������̹鵵���´�����Ϊ�ѽ���UpdateForTheCostWorkfAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();
		String mainid ="";
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String tableName = "";

		String val = "";//ҳ�����صĴ�ֵids	
		String Money ="";
		String sql = "";
		sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID
				+ ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		
		
		
		if (!"".equals(tableName)) {
			// ��ѯ����
			sql = "select * from "+tableName + " where requestid="+requestid;
			rs.execute(sql);
			if (rs.next()) {			
				mainid = Util.null2String(rs.getString("id"));	
				
			}
			sql="select * from "+tableName+"_dt1 where mainid="+mainid;
			 rs.execute(sql);
			while(rs.next()){
				val = Util.null2String(rs.getString("val"));//ҳ�����صĴ�ֵid
				Money = Util.null2String(rs.getString("Money"));
				//�ǵü�0������ְ�(24,25,)��Ϊ(24,25,0)
				String sql_up = "update formtable_main_76_dt1 set State = '1',Money='"+Money+"' where id in (" + val + ")";
				rs1.execute(sql_up);
				
			}											
		} else {
			return "-1";
		}
		return SUCCESS;
	}

}

