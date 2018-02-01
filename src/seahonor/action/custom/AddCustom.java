package seahonor.action.custom;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class AddCustom
  implements Action
{
  BaseBean log = new BaseBean();

  public String execute(RequestInfo info) {
    String id = info.getRequestid();
    String customGroup = "";
    String kh = "";
    String gys = "";
    String hzhb = "";
    String zf="";
    String yh="";
    String table_name_custom = "uf_custom";
    String sql = "select customGroup,kh,gys,hzhb,zf,yh from " + table_name_custom + " where id = " + id;
    RecordSet rs = new RecordSet();
    this.log.writeLog("查询sql = " + sql);
    rs.executeSql(sql);
    if (rs.next()) {
      customGroup = Util.null2String(rs.getString("customGroup"));
      kh = Util.null2String(rs.getString("kh"));
      gys = Util.null2String(rs.getString("gys"));
      hzhb = Util.null2String(rs.getString("hzhb"));
      zf = Util.null2String(rs.getString("zf"));
      yh = Util.null2String(rs.getString("yh"));
    }
    String ybgsgxstr="";
    String ybgsgx="";
	String flag="";
	if("1".equals(kh)){
		ybgsgxstr=ybgsgxstr+flag+"客户";
		flag="/";
	}
	if("1".equals(gys)){
		ybgsgxstr=ybgsgxstr+flag+"供应商";
		flag="/";
	}
	if("1".equals(hzhb)){
		ybgsgxstr=ybgsgxstr+flag+"合作伙伴";
		flag="/";
	}
	if("1".equals(zf)){
		ybgsgxstr=ybgsgxstr+flag+"政府";
		flag="/";
	}
	if("1".equals(yh)){
		ybgsgxstr=ybgsgxstr+flag+"银行";
		flag="/";
	}
	sql="select selectvalue from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='uf_custom' and a.fieldname='ybgsgx' and upper(c.selectname)=upper('"+ybgsgxstr+"')";
	log.writeLog("ccccsql"+sql);
	rs.executeSql(sql);
	if(rs.next()){
		ybgsgx = Util.null2String(rs.getString("selectvalue"));
	}
      sql = "update " + table_name_custom + " set ybgsgx = " + ybgsgx + " where id = " + id;
 
      rs.executeSql(sql);
    

    return SUCCESS;
  }
}