package seahonor.action.atten;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 15/11/19.
 * 查询有效日期action
 */
public class selectmoney implements Action{
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        RecordSet rs = new RecordSet();

        String sql = "";
        String tableName = "";
        // String dataID = "";
        // String editDate = "";
		String salary = "";
		String workcord = "";
        String requestid = info.getRequestid();

        sql = "Select tablename From Workflow_bill Where id in ("+ "Select formid From workflow_base Where id="+ info.getWorkflowid() + ")";
        //log.writeLog("sql1---------" + sql);
        rs.executeSql(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }

        if (!"".equals(tableName)) {

            sql = "select * from " + tableName + " where requestid = "+ requestid;
            //log.writeLog("sql2---------" + sql);
            rs.executeSql(sql);
            if (rs.next()) {
                workcord = Util.null2String(rs.getString("workcode"));
            //    editDate = Util.null2String(rs.getString("editDate"));
            }

            if(!"".equals(workcord)) {
                // sql = " update uf_YearAdjust_leave set ValidDate =(select day_info from self_overtime_info where id=" + editDate + ") where id= " + dataID;
				sql="select a.loginid,b.salary from  Hrmresource a left join  HrmSalaryPersonality b on a.id=b.hrmid where loginid="+workcord+"";
                rs.executeSql(sql);
                //log.writeLog("sql3=" + sql);
				if(rs.next()){
					 salary = Util.null2String(rs.getString("salary"));
					 if(salary ==null || "".equals(salary)){
						  log.writeLog("请填写基本工资");
                           return "-1";
					 }
				}else{
                    log.writeLog("没有该员工信息");
                       return  "-1";
                     }

                // if (!rs.executeSql(sql)) {
                //     log.writeLog("有效期更新失败！");
                //     return "-1";
                // }
            }else{
                log.writeLog("工号获取失败");
                return "-1";
            }
        } else {
            log.writeLog("流程信息表读取错误");
            return "-1";
        }
        return SUCCESS;
    }
}

