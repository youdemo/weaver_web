package seahonor.action.purchsse;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class SaveQueryPriceWorkflowAction implements Action {

	BaseBean log = new BaseBean();
	public String execute(RequestInfo request) {
		log.writeLog("保存询价SaveQueryPriceWorkflowAction ... ");
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		
		String QueryPrice = "";///询价
		String val = "";//传值
		String id = request.getRequestid();//这是表单建模中的id
		
		String table_name = "uf_SupplierQuery";//供应商询价表单
		
		String sql = "select * from " + table_name + " where id = "+id;
		rs.executeSql(sql);
		log.writeLog("sql = " + sql);
	
		if(rs.next()){
			val = Util.null2String(rs.getString("val"));//传值（明细id）
			
			String Supplier1 = Util.null2String(rs.getString("Supplier1"));//供应商1
			String Supplier2 = Util.null2String(rs.getString("Supplier2"));//供应商2
			String Supplier3 = Util.null2String(rs.getString("Supplier3"));//供应商3
			
			String goodsprice1 = Util.null2String(rs.getString("goodsprice1"));//供应商单价1
			String goodsprice2 = Util.null2String(rs.getString("goodsprice2"));//供应商单价2
			String goodsprice3 = Util.null2String(rs.getString("goodsprice3"));//供应商单价3
			
			String SelectGoodsName1 = Util.null2String(rs.getString("SelectGoodsName1"));//选择供应商1
			String SelectGoodsName2 = Util.null2String(rs.getString("SelectGoodsName2"));//选择供应商2
			String SelectGoodsName3 = Util.null2String(rs.getString("SelectGoodsName3"));//选择供应商3
			
			if (SelectGoodsName1.equals("1")) {
				QueryPrice = "<a href=\''/formmode/view/AddFormMode.jsp?isfromTab=0&modeId=104&formId=-155"+
				"&type=0&iscreate=1&messageid=&viewfrom=&opentype=0&customid=0&isopenbyself=&isdialog=&"+
						"isclose=1&templateid=0&billid="+id+"\'' target=\''_blank\''>点击询价</a>";
				//这是月度采购导入流程中询价保存的方法
				//String sql_1 = "update formtable_main_73_dt1 set Supplier='"+Supplier1+"',SupplierPrice='"+goodsprice1+"',QueryPrice='"+QueryPrice+"' where id="+val;
				String sql_1 = "update formtable_main_73_dt1 set Supplier='"+Supplier1+"',SupplierPrice='"+goodsprice1+"',QueryPrice='"+QueryPrice+"' where val_1="+val;
				rs1.execute(sql_1);
				log.writeLog("报存询价1QueryPrice="+QueryPrice);
				log.writeLog("保存询价1sql_1="+sql_1);
			} else if(SelectGoodsName2.equals("1")){
				QueryPrice = "<a href=\''/formmode/view/AddFormMode.jsp?isfromTab=0&modeId=104&formId=-155"+
						"&type=0&iscreate=1&messageid=&viewfrom=&opentype=0&customid=0&isopenbyself=&isdialog=&"+
								"isclose=1&templateid=0&billid="+id+"\'' target=\''_blank\''>点击询价</a>";
				//String sql_1 = "update formtable_main_73_dt1 set Supplier='"+Supplier2+"',SupplierPrice='"+goodsprice2+"',QueryPrice='"+QueryPrice+"' where id="+val;
				String sql_1 = "update formtable_main_73_dt1 set Supplier='"+Supplier2+"',SupplierPrice='"+goodsprice2+"',QueryPrice='"+QueryPrice+"' where val_1="+val;
						rs1.execute(sql_1);
						log.writeLog("报存询价2QueryPrice="+QueryPrice);
						log.writeLog("保存询价2sql_1="+sql_1);
			} else if(SelectGoodsName3.equals("1")){
				QueryPrice = "<a href=\''/formmode/view/AddFormMode.jsp?isfromTab=0&modeId=104&formId=-155"+
						"&type=0&iscreate=1&messageid=&viewfrom=&opentype=0&customid=0&isopenbyself=&isdialog=&"+
								"isclose=1&templateid=0&billid="+id+"\'' target=\''_blank\''>点击询价</a>";
				//String sql_1 = "update formtable_main_73_dt1 set Supplier='"+Supplier3+"',SupplierPrice='"+goodsprice3+"',QueryPrice='"+QueryPrice+"' where id="+val;
				String sql_1 = "update formtable_main_73_dt1 set Supplier='"+Supplier3+"',SupplierPrice='"+goodsprice3+"',QueryPrice='"+QueryPrice+"' where val_1="+val;
						rs1.execute(sql_1);
						log.writeLog("报存询价3QueryPrice="+QueryPrice);
						log.writeLog("保存询价3sql_1="+sql_1);
			} else{
				QueryPrice = "<a href=\''/formmode/view/AddFormMode.jsp?isfromTab=0&modeId=104&formId=-155"+
						"&type=0&iscreate=1&messageid=&viewfrom=&opentype=0&customid=0&isopenbyself=&isdialog=&"+
								"isclose=1&templateid=0&billid="+id+"\'' target=\''_blank\''>点击询价</a>";
				//String sql_1 = "update formtable_main_73_dt1 set QueryPrice='"+QueryPrice+"' where id="+val;
				String sql_1 = "update formtable_main_73_dt1 set QueryPrice='"+QueryPrice+"' where val_1="+val;
						rs1.execute(sql_1);
						log.writeLog("报存询价其他QueryPrice="+QueryPrice);
						log.writeLog("保存询价其他sql_1="+sql_1);
			}
			
		}
		return SUCCESS;
	}
	
}
