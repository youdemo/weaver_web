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
		log.writeLog("����ѯ��SaveQueryPriceWorkflowAction ... ");
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		
		String QueryPrice = "";///ѯ��
		String val = "";//��ֵ
		String id = request.getRequestid();//���Ǳ���ģ�е�id
		
		String table_name = "uf_SupplierQuery";//��Ӧ��ѯ�۱�
		
		String sql = "select * from " + table_name + " where id = "+id;
		rs.executeSql(sql);
		log.writeLog("sql = " + sql);
	
		if(rs.next()){
			val = Util.null2String(rs.getString("val"));//��ֵ����ϸid��
			
			String Supplier1 = Util.null2String(rs.getString("Supplier1"));//��Ӧ��1
			String Supplier2 = Util.null2String(rs.getString("Supplier2"));//��Ӧ��2
			String Supplier3 = Util.null2String(rs.getString("Supplier3"));//��Ӧ��3
			
			String goodsprice1 = Util.null2String(rs.getString("goodsprice1"));//��Ӧ�̵���1
			String goodsprice2 = Util.null2String(rs.getString("goodsprice2"));//��Ӧ�̵���2
			String goodsprice3 = Util.null2String(rs.getString("goodsprice3"));//��Ӧ�̵���3
			
			String SelectGoodsName1 = Util.null2String(rs.getString("SelectGoodsName1"));//ѡ��Ӧ��1
			String SelectGoodsName2 = Util.null2String(rs.getString("SelectGoodsName2"));//ѡ��Ӧ��2
			String SelectGoodsName3 = Util.null2String(rs.getString("SelectGoodsName3"));//ѡ��Ӧ��3
			
			if (SelectGoodsName1.equals("1")) {
				QueryPrice = "<a href=\''/formmode/view/AddFormMode.jsp?isfromTab=0&modeId=104&formId=-155"+
				"&type=0&iscreate=1&messageid=&viewfrom=&opentype=0&customid=0&isopenbyself=&isdialog=&"+
						"isclose=1&templateid=0&billid="+id+"\'' target=\''_blank\''>���ѯ��</a>";
				//�����¶Ȳɹ�����������ѯ�۱���ķ���
				//String sql_1 = "update formtable_main_73_dt1 set Supplier='"+Supplier1+"',SupplierPrice='"+goodsprice1+"',QueryPrice='"+QueryPrice+"' where id="+val;
				String sql_1 = "update formtable_main_73_dt1 set Supplier='"+Supplier1+"',SupplierPrice='"+goodsprice1+"',QueryPrice='"+QueryPrice+"' where val_1="+val;
				rs1.execute(sql_1);
				log.writeLog("����ѯ��1QueryPrice="+QueryPrice);
				log.writeLog("����ѯ��1sql_1="+sql_1);
			} else if(SelectGoodsName2.equals("1")){
				QueryPrice = "<a href=\''/formmode/view/AddFormMode.jsp?isfromTab=0&modeId=104&formId=-155"+
						"&type=0&iscreate=1&messageid=&viewfrom=&opentype=0&customid=0&isopenbyself=&isdialog=&"+
								"isclose=1&templateid=0&billid="+id+"\'' target=\''_blank\''>���ѯ��</a>";
				//String sql_1 = "update formtable_main_73_dt1 set Supplier='"+Supplier2+"',SupplierPrice='"+goodsprice2+"',QueryPrice='"+QueryPrice+"' where id="+val;
				String sql_1 = "update formtable_main_73_dt1 set Supplier='"+Supplier2+"',SupplierPrice='"+goodsprice2+"',QueryPrice='"+QueryPrice+"' where val_1="+val;
						rs1.execute(sql_1);
						log.writeLog("����ѯ��2QueryPrice="+QueryPrice);
						log.writeLog("����ѯ��2sql_1="+sql_1);
			} else if(SelectGoodsName3.equals("1")){
				QueryPrice = "<a href=\''/formmode/view/AddFormMode.jsp?isfromTab=0&modeId=104&formId=-155"+
						"&type=0&iscreate=1&messageid=&viewfrom=&opentype=0&customid=0&isopenbyself=&isdialog=&"+
								"isclose=1&templateid=0&billid="+id+"\'' target=\''_blank\''>���ѯ��</a>";
				//String sql_1 = "update formtable_main_73_dt1 set Supplier='"+Supplier3+"',SupplierPrice='"+goodsprice3+"',QueryPrice='"+QueryPrice+"' where id="+val;
				String sql_1 = "update formtable_main_73_dt1 set Supplier='"+Supplier3+"',SupplierPrice='"+goodsprice3+"',QueryPrice='"+QueryPrice+"' where val_1="+val;
						rs1.execute(sql_1);
						log.writeLog("����ѯ��3QueryPrice="+QueryPrice);
						log.writeLog("����ѯ��3sql_1="+sql_1);
			} else{
				QueryPrice = "<a href=\''/formmode/view/AddFormMode.jsp?isfromTab=0&modeId=104&formId=-155"+
						"&type=0&iscreate=1&messageid=&viewfrom=&opentype=0&customid=0&isopenbyself=&isdialog=&"+
								"isclose=1&templateid=0&billid="+id+"\'' target=\''_blank\''>���ѯ��</a>";
				//String sql_1 = "update formtable_main_73_dt1 set QueryPrice='"+QueryPrice+"' where id="+val;
				String sql_1 = "update formtable_main_73_dt1 set QueryPrice='"+QueryPrice+"' where val_1="+val;
						rs1.execute(sql_1);
						log.writeLog("����ѯ������QueryPrice="+QueryPrice);
						log.writeLog("����ѯ������sql_1="+sql_1);
			}
			
		}
		return SUCCESS;
	}
	
}
