package seahonor.action.express;

import seahonor.util.RandUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
//��֮���տ������action������д�ı�׼��action��
public class AcceptExpressWorkflowAction implements Action {
	
	BaseBean log = new BaseBean();//����д����־�Ķ���
	public String execute(RequestInfo info) {
		log.writeLog("�����տ��AcceptExpressWorkflowAction������������");
		
		String workflowID = info.getWorkflowid();//��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();//��ȡrequestid��ֵ
		
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		
		String tableName = "";
		String tableNamedt = "";
		String mainID = "";
		String sql_1 = "";
		String sql  = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= "
				+ workflowID + ")";
		
		rs.execute(sql);
		if(rs.next()){
			tableName = Util.null2String(rs.getString("tablename"));
		
			String ranNum = RandUtil.ranNum(4);//����֤��������һ��ֵ
			//����������Ҫ�ǻ�ȡ�����е��ֶΣ������@��update
			sql_1 = "update " + tableName + " set VerCode='"+ranNum+"' where requestid="+requestid;
			rs1.execute(sql_1);
		}
		
		/*if(!"".equals(tableName)){
			tableNamedt = tableName + "_dt1";//���������Ϊ���ж����ϸ����׼��
			//tableNamedt = tableName + "_dt2";
			
			// ��ѯ����
			sql = "select * from "+tableName + " where requestid="+requestid;
			rs.execute(sql);
			if(rs.next()){
				mainID = Util.null2String(rs.getString("id"));//��ȡ�����е�id����Ϊ��ϸ���е�mainid
			}
			
			//��ѯ��ϸ��
			sql = "select * from " + tableNamedt + " where mainid="+mainID;
			rs.execute(sql);
			while(rs.next()){
				String dtID = Util.null2String(rs.getString("id"));//��ȡ��ϸ����id
				String ranNum = RandUtil.ranNum(4);//����֤��������һ��ֵ
				
				sql = "update " + tableNamedt + " set VerCode='"+ranNum+"' where id="+dtID;
				rs1.execute(sql);
			}
		}*/else{
			return "-1";
		}
		return SUCCESS;
	}

}

