package seahonor.action.purchsse;

import java.util.Date;

import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

//��֮�Ų����ճ��ɹ�����Ʒaction������й����ӱ�ģ�������ϸ��ģ�
public class PurchaseWFActionforRequest implements Action {

	BaseBean log = new BaseBean();// ����д����־�Ķ���

	public String execute(RequestInfo info) {
		log.writeLog("�����ճ��ɹ�����ƷPurchaseWorkflowAction������������");

		String workflowID = info.getWorkflowid();// ��ȡ��������Workflowid��ֵ
		String requestid = info.getRequestid();

		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		
		String tableName = "";
		String tableNamedt = "";
		String mainID = "";
		int Numbers = 0;// ��ϸ�� �е�����
		String Name ="";//��ϸ���е�����
		String Month = "";//�����е��·�
		String Year = "";//�����е����
		String Remark ="";
		String id_1 = "";
		int	count_cc =0;
		int ID = 0;//����ģ�е�id
		
		ModeRightInfo ModeRightInfo = new ModeRightInfo();
		String sql_1 = "";
		String sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID+ ")";
		rs.execute(sql);
		if(rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		if(!"".equals(tableName)) {
			tableNamedt = tableName + "_dt1";//���������Ϊ���ж����ϸ����׼��
			// ��ѯ����
			sql = "select * from "+tableName + " where requestid="+requestid;
			rs.execute(sql);
			if(rs.next()){
				mainID = Util.null2String(rs.getString("id"));//��ȡ�����е�id����Ϊ��ϸ���е�mainid
				Month = Util.null2String(rs.getString("Month"));//��ȡ�����е��·�
				Year = Util.null2String(rs.getString("Year"));//��ȡ�����е����
				
			}			
			//��ѯ��ϸ��
			sql = "select * from " + tableNamedt + " where mainid="+mainID;
			rs.execute(sql);
			while(rs.next()){
				ID = 0;
				id_1 = "";
				Name = Util.null2String(rs.getString("Name"));//��ȡ��ϸ���е�����
				Numbers = rs.getInt("Numbers");//��ȡ��ϸ���е�����
				Remark = Util.null2String(rs.getString("Remark"));		
				String sql_2 = "select id from uf_Purchase where formmodeid=94 and status = 0 and type=1";	
				rs1.execute(sql_2);
				if (rs1.next()) {
					id_1 = Util.null2String(rs1.getString("id"));//
				}
				
				if ("".equals(id_1)) {
					
					long times = new Date().getTime();
					String sysNo = String.valueOf(times);
					// sysNo
					
					String sql_3 = "insert into uf_Purchase(formmodeid,status,sysNo,type) values(94,0,'"+sysNo+"',1)";
					rs1.execute(sql_3);
					
					// ��ȡȨ�ޱ�IDs  formmodeid=94����ֻ�������ģ��ֵ����������������ı���ģ��ֵ���˵���
					String sql_Datashare = "select * from uf_Purchase where formmodeid=94 and status = 0 and type=1";
					rs1.execute(sql_Datashare);
					if (rs1.next()) {
						ID = rs1.getInt("id");
						ModeRightInfo.editModeDataShare(1,94,ID);
					}
					
					id_1 = String.valueOf(ID);//��int����תΪString����
				}				
				String sql_3 = "select count(id) count_cc from uf_Purchase_dt1 where mainid = "+id_1+" and Name = '"+Name+"'";	
				rs1.execute(sql_3);
				if (rs1.next()) {
						count_cc = rs1.getInt("count_cc");//
				}
				if (count_cc>0) {
					sql_1 = "update  uf_Purchase_dt1 set  Numbers = isnull(Numbers,0) +"
							+ Numbers + " where Name='" + Name + "' and mainid = "+id_1+"";
					rs1.execute(sql_1);
					
				}else{
					sql_1 = "insert into uf_Purchase_dt1(mainid,Name,Numbers,Remark) values("+id_1+",'"
							+ Name + "','" + Numbers + "','"+Remark+"')";
					rs1.execute(sql_1);
				}
				
				String sql_4 = "select id from uf_Purchase_dt1 where mainid = "+id_1+"";	
				rs1.execute(sql_4);
				while(rs1.next()) {
					String tmp_id = Util.null2String(rs1.getString("id"));//����ģ�е���ϸid �ֶ�
					String QueryPrice = "<a href=\''/seahonor/purchase/getSupplierPriceUrl.jsp?deRemark="+tmp_id+"\''>�������ѯ��</a>";
					String sql_5 = "update uf_Purchase_dt1 set QueryPrice='"+QueryPrice+"',val='"+tmp_id+"' where id='"+tmp_id+"' and mainid = '"+id_1+"'";
					rs2.execute(sql_5);
				}
			}							 			
		} else {
			return "-1";
		}
		createRequest(requestid,tableName);
		return SUCCESS;
	}
	 
	private void createRequest(String requestid,String tableName){
		  AutoCreatePurchase acp= new AutoCreatePurchase();
		   String result ="";
		   try {
			   result =acp.AutoCreateRequest("1",requestid,tableName);
		   } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		  
	}

}