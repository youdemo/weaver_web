package seahonor.action.express;

import seahonor.util.RandUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
//海之信收快递流程action（宝哥写的标准的action）
public class AcceptExpressWorkflowAction implements Action {
	
	BaseBean log = new BaseBean();//定义写入日志的对象
	public String execute(RequestInfo info) {
		log.writeLog("进入收快递AcceptExpressWorkflowAction——————");
		
		String workflowID = info.getWorkflowid();//获取工作流程Workflowid的值
		String requestid = info.getRequestid();//获取requestid的值
		
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
		
			String ranNum = RandUtil.ranNum(4);//给验证码随机添加一个值
			//由于这是主要是获取主表中的字段，所以這样update
			sql_1 = "update " + tableName + " set VerCode='"+ranNum+"' where requestid="+requestid;
			rs1.execute(sql_1);
		}
		
		/*if(!"".equals(tableName)){
			tableNamedt = tableName + "_dt1";//这个方法是为了有多个明细做的准备
			//tableNamedt = tableName + "_dt2";
			
			// 查询主表
			sql = "select * from "+tableName + " where requestid="+requestid;
			rs.execute(sql);
			if(rs.next()){
				mainID = Util.null2String(rs.getString("id"));//获取主表中的id，作为明细表中的mainid
			}
			
			//查询明细表
			sql = "select * from " + tableNamedt + " where mainid="+mainID;
			rs.execute(sql);
			while(rs.next()){
				String dtID = Util.null2String(rs.getString("id"));//获取明细表中id
				String ranNum = RandUtil.ranNum(4);//给验证码随机添加一个值
				
				sql = "update " + tableNamedt + " set VerCode='"+ranNum+"' where id="+dtID;
				rs1.execute(sql);
			}
		}*/else{
			return "-1";
		}
		return SUCCESS;
	}

}

