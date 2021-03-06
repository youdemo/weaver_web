package seahonor.action.custom;

import seahonor.util.GeneralNowInsert;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class CreateSuperCapacity implements Action{
	public String execute(RequestInfo info) {
	    String id = info.getRequestid();
	    RecordSet rs = new RecordSet();
	    String billid="";
	    String tableName1="uf_CustomCapacity";
	    String sql="";
	    SureCustomAction sca = new SureCustomAction();
	    sca.insertHistory("-67",tableName1, "id", id);
		sql="select Max(id) as billid from "+tableName1+" where superid = "+id;
		
		rs.executeSql(sql);
		if(rs.next()){
			billid= Util.null2String(rs.getString("billid"));	
			ModeRightInfo ModeRightInfo = new ModeRightInfo();
			ModeRightInfo.editModeDataShare(1,60,Integer.valueOf(billid));			
		}
		
	    return SUCCESS;
	}
}
