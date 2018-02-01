package seahonor.action.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seahonor.util.GeneralNowInsert;
import seahonor.util.InsertUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class GroupInfoUpdateAction implements Action{

	/*
	 *   ����Ϊ�µİ汾  ���ȸ���Ϊ�µİ汾,�ٴ洢Ŀǰ�汾����
	 */
	public String execute(RequestInfo request) {
		BaseBean log = new BaseBean();
		
		log.writeLog("GroupInfoUpdateAction Start!!");
		
		String requestid = request.getRequestid();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		
		RecordSet rs1 = new RecordSet();
		String table_name = "";
		
		// billd 
		String billid = "-12";
		// ���ű���Ψһ
		String sysField = "GroupCode";
		// ���ű�
		String tableName = "uf_custom_group";
		// �汾�ֶ�
		String versinField = "version";
		
		
		// ��� ����ֶ�
		List<String> list = new ArrayList<String>();
		String sql = "select fieldname from workflow_billfield  where billid="+billid+" order by dsporder";
		rs.executeSql(sql);
		while(rs.next()){
			String tmp_1 = Util.null2String(rs.getString("fieldname"));
			list.add(tmp_1);
		}
		log.writeLog("list = " + list);
		// ���̼�¼Ϊ����  �ĸ���Ϣ��
		sql = "Select tablename From Workflow_bill Where id=(" 
				+ "Select formid From workflow_base Where id="+request.getWorkflowid()+")";
		rs.executeSql(sql);
		if(rs.next()){
			table_name = Util.null2String(rs.getString("tablename"));
		}
		
		Map<String, String> mapStr = new HashMap<String, String>();
		
		sql = "select * from "+table_name+"  where requestId="+requestid;
		
		log.writeLog("@@sql=" + sql);
		
		rs.execute(sql);
		
		if(rs.next()){
			
			for(String name : list){
				if(!name.equalsIgnoreCase(sysField)){
					mapStr.put(name, Util.null2String(rs.getString(name)));
				}
			}
			mapStr.put("requestid", Util.null2String(rs.getString("requestid")));
			
			String sysNo =  Util.null2String(rs.getString(sysField));
			
			//  �ȸ���
			iu.update(mapStr, tableName, sysField, sysNo);
			
			// ���°汾
			sql = "update "+tableName+" set "+ versinField + "=" +versinField +"+1.0 where "+sysField + "='"+ sysNo 
					+ "' and superid is null";
			rs1.executeSql(sql);
			
			// �ٴ洢Ŀǰ�汾
			GeneralNowInsert gni = new GeneralNowInsert();
			gni.insertNow(billid,tableName, sysField, sysNo);
			
			
		}
		
		return SUCCESS;
	}
}
