package seahonor.util;

import weaver.conn.RecordSet;
import weaver.general.Util;

public class VersionWorkflowConf {

	private String billID = "";
	private String sysField = "";
	private String versionField = "";
	private String tableName = "";
	private String sysWorkflowField = "";
	
	public VersionWorkflowConf(String workflowID){
		String sql = "select mIName,sysField,versionField,mi.formid,sysWorkflowField,"
				+"(select tablename from workflow_bill wb where wb.id=mi.formid) as tableName from  uf_configBase uc "
				+"join modeinfo mi on mi.id=uc.mIName where wfName="+workflowID + " order by mi.id desc ";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		if(rs.next()){
			this.billID = Util.null2String(rs.getString("formid"));
			this.sysField = Util.null2String(rs.getString("sysField"));
			this.versionField = Util.null2String(rs.getString("versionField"));
			this.tableName = Util.null2String(rs.getString("tableName"));
			this.sysWorkflowField = Util.null2String(rs.getString("sysWorkflowField"));
		}
	}

	public String getSysWorkflowField() {
		return sysWorkflowField;
	}

	public String getBillID() {
		return billID;
	}

	public String getSysField() {
		return sysField;
	}

	public String getVersionField() {
		return versionField;
	}

	public String getTableName() {
		return tableName;
	}
	
	
}
