package seahonor.action.purchsse;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class PurchaseUpdateAction implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		RecordSet rs = new RecordSet();
		String table_name = "";
		String requestid = info.getRequestid();
		// ����ģѰ�ұ���
		String sql = "Select tablename From Workflow_bill Where id in( "
							+"select formid from modeinfo where id="+info.getWorkflowid()+")";
		rs.executeSql(sql);
		if(rs.next()){
			table_name = Util.null2String(rs.getString("tablename"));
		}
		
		sql = "update " + table_name +" set status=1 where id="+requestid;
		rs.executeSql(sql);
		
		return SUCCESS;
	}

}