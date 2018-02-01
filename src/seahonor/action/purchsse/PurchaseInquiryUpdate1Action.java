package seahonor.action.purchsse;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class PurchaseInquiryUpdate1Action implements Action {

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
		
		sql = "select * from " + table_name + " where id="+requestid;
		rs.executeSql(sql);
		if(rs.next()){
			String who = "";
			String price = "";
			// ��ϸ���ֶ�
			String tmp_id = Util.null2String(rs.getString("deRemark"));
			if(tmp_id.length() > 0 ){
				//  ѡ��Ӧ��1 selCheck1    ѡ��Ӧ��2 selCheck2   ѡ��Ӧ��3 selCheck3 
				String selCheck1 = Util.null2String(rs.getString("selCheck1"));
				String selCheck2 = Util.null2String(rs.getString("selCheck2"));
				String selCheck3 = Util.null2String(rs.getString("selCheck3"));
				if("1".equals(selCheck1)){
					who = Util.null2String(rs.getString("supplier1"));
					price = Util.null2String(rs.getString("price1"));
				}else if("1".equals(selCheck2)){
					who = Util.null2String(rs.getString("supplier2"));
					price = Util.null2String(rs.getString("price2"));
				}else if("1".equals(selCheck3)){
					who = Util.null2String(rs.getString("supplier3"));
					price = Util.null2String(rs.getString("price3"));
				}
				
				if(who.length() > 0 && price.length() > 0){
					sql = "update formtable_main_73_dt1 set Supplier=" + who 
							+ ",SupplierPrice=" + price + " where id="+tmp_id;
					rs.executeSql(sql);
				}
			}
			
		}

		return SUCCESS;
	}

}