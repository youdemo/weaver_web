package seahonor.action.custom;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class SaveEditCustom implements Action{
  BaseBean log = new BaseBean();

  public String execute(RequestInfo info) {
	RecordSet rs= new RecordSet();
    String id = info.getRequestid();
    String sql="update uf_edit_custom set sfbj='1' where id="+id;
    rs.executeSql(sql);
    return SUCCESS;
  }
}