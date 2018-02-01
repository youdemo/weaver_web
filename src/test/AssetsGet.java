package seahonor.action.assets;

import java.util.HashMap;
import java.util.Map;

import seahonor.util.InsertUtil;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import seahonor.util.CnToEn;

public class AssetsGet implements Action {
	
	BaseBean log = new BaseBean();//����д����־�Ķ���
	public String execute(RequestInfo info) {
		log.writeLog("�����ʲ����AssetsGet������������");
		
		String workflowID = info.getWorkflowid();
		String requestid = info.getRequestid();//��ȡ��������ID��requestIDֵ
		ModeRightInfo ModeRightInfo = new ModeRightInfo();
		CnToEn langChg = new CnToEn(); 
		
		InsertUtil ui = new  InsertUtil();
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		int temp_num = 0;//��Ʒ����
		
		String tableName = "";
		String tableNamedt = "";
		String mainID = "";
		int ID = 0;
		String IDx = "";
		int ID2 = 0;
		String Datex = "";
		int SerialNumber = 0;
		//����
		String applicant = "";//������
		String applicantDate = "";//��������
		String sysNo = "";//ϵͳ���
		int applicantx = 0;
		//��ϸ��
		String goodsNameID = "";//��Ʒ����ID
		String goodsName = "";//��Ʒ����
		String price = "";//��Ʒ����
		String goodsNameX = "";//ƴ����Ʒ����
		String goodsCate = "";//��������
		String purchaseNum = "";//�ɹ�����
		String validityPeriod = "";//��Ч��
		String brand = "";//Ʒ��
		String goodsSize = "";//�ͺ�
		String specifications ="";//���
		String configuration ="";//����
		String layPlace = "";//����λ��
		String remark = "";//��ע
		String goodStatus ="";//��Ʒ״̬
		String layplace ="";//���ÿⷿ
		String library ="";//��λ
		String origin ="";//��Դ
		String guaranteestart ="";//���޿�ʼ����
		String guaranteeend ="";//���޽�ֹ����
		String guaranteecost ="";//�������
		String supplier ="";//���޹�Ӧ������
		String suppliercontacts ="";//���޹�Ӧ����ϵ��
		String suppliertel ="";//���޹�Ӧ����ϵ�绰
		String customer ="";//�ͻ�
		String equipmentnum ="";
		String condition ="";
		
		//������
		String isIndependent ="";//�Ƿ��������
		String useAtt ="";//��������
		String repairAtt ="";//�Ƿ��ά��
		String infoId ="";//������id
		String goodsAtt ="";//�ʲ�����
		String repairAccount ="";//ά�޻������
		String repairRate ="";//ÿ�������ά�޷���
		String picture ="";//��ƷͼƬ
		String unit = "";//��λ
		
		String goodscateId = "";
		String catename = "";
		String superid = "";
		String Title_temp = "";
		String App_temp = "";
		
		String sql  = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= "
				+ workflowID + ")";
		
		rs.execute(sql);
		if(rs.next()){
			tableName = Util.null2String(rs.getString("tablename"));
		}

		if(!"".equals(tableName)){
			tableNamedt = tableName + "_dt1";//���������Ϊ���ж����ϸ����׼��
			//tableNamedt = tableName + "_dt2";
			
			// ��ѯ����
			sql = "select * from "+tableName + " where requestid="+requestid;
			rs.execute(sql);
			if(rs.next()){
				
				mainID = Util.null2String(rs.getString("ID"));//��ȡ�����е�ID����Ϊ��ϸ���е�mainID
				applicant = Util.null2String(rs.getString("applicant"));
				applicantDate = Util.null2String(rs.getString("applicantdate"));
				sysNo = Util.null2String(rs.getString("Sysno"));
				
				try {
				     applicantx = Integer.parseInt(applicant);
				} catch (NumberFormatException e) {
				    e.printStackTrace();
				}
				
			}
			
			//��ѯ��ϸ��
			sql = "select * from " + tableNamedt + " where mainid="+mainID;
			rs1.execute(sql);
			while(rs1.next()){
				
				goodsNameID = Util.null2String(rs1.getString("goodsname"));
				goodsCate = Util.null2String(rs1.getString("goodscate"));
				price = Util.null2String(rs1.getString("purchaseprice"));
				purchaseNum = Util.null2String(rs1.getString("purchasenum"));
				unit = Util.null2String(rs1.getString("unit"));
				validityPeriod = Util.null2String(rs1.getString("validityperiod"));
				brand = Util.null2String(rs1.getString("brand"));
				goodsSize = Util.null2String(rs1.getString("goodsize"));
				layPlace = Util.null2String(rs1.getString("layplace"));
				library = Util.null2String(rs1.getString("library"));
				remark = Util.null2String(rs1.getString("remark"));
				goodStatus = Util.null2String(rs1.getString("goodstatus"));
				origin = Util.null2String(rs1.getString("origin"));
				configuration   = Util.null2String(rs1.getString("configuration"));
				specifications = Util.null2String(rs1.getString("specifications"));
				customer = Util.null2String(rs1.getString("customer"));
				equipmentnum = Util.null2String(rs1.getString("equipmentnum"));
				condition = Util.null2String(rs1.getString("condition"));
				
				guaranteestart = Util.null2String(rs1.getString("guaranteestart"));
				guaranteeend = Util.null2String(rs1.getString("guaranteeend"));
				guaranteecost = Util.null2String(rs1.getString("guaranteecost"));
				supplier = Util.null2String(rs1.getString("supplier"));
				suppliercontacts = Util.null2String(rs1.getString("suppliercontacts"));
				suppliertel = Util.null2String(rs1.getString("suppliertel"));		
	           
				//�Ƿ�Ϊ�������㣬�Ƿ��ά�ޣ�ʹ������     ������
				sql= "select * from uf_goodsinfo  where id = '"+goodsNameID+"'";
				rs.execute(sql);
				log.writeLog("�ʲ��б�����ʲ�������������:" +sql);
				if(rs.next()){
					isIndependent = Util.null2String(rs.getString("isIndependent"));
					useAtt = Util.null2String(rs.getString("useatt"));
					repairAtt = Util.null2String(rs.getString("repairatt"));
					infoId = Util.null2String(rs.getString("id"));
					goodsAtt = Util.null2String(rs.getString("goodsatt"));
					repairAccount = Util.null2String(rs.getString("repairaccount"));
					repairRate = Util.null2String(rs.getString("repairrate"));
					picture = Util.null2String(rs.getString("picture"));
					goodscateId = Util.null2String(rs.getString("goodscateid"));
				}
				
				
				String sql_s = "select * from uf_goodscate where id = "+goodscateId+"";
				rs.execute(sql_s);
				log.writeLog("�ʲ��б�����ʲ�������������:" +sql_s);
				if(rs.next()){
					superid = Util.null2String(rs.getString("superid"));
				}
				
				
				while(!"".equals(superid)){
					String sql_x = "select * from uf_goodscate where id = "+superid+"";
					rs.execute(sql_x);
					log.writeLog("�ʲ��б�����ʲ�������������:" +sql_x);
					if(rs.next()){
						superid = Util.null2String(rs.getString("superid"));
						catename = Util.null2String(rs.getString("catename"));
					}
				}
				
				if("".equals(customer)){
					Title_temp = "HZX";
				}else{
					Title_temp = "KH";
				}
				
				App_temp = applicantDate.replaceAll("-","").substring(2, 6);

	           //��ȡ������Ʒ��ID����Ӧ����Ʒ����������Ʒ���ͺ�ƴ��      ������
				String sql_info = "select * from uf_goodsinfo where id = '"+goodsNameID+"'";
				rs.executeSql(sql_info);
				log.writeLog("��ȡ������Ʒ��ID����Ӧ����Ʒ��" +sql_info);
				if (rs.next()){
					goodsName = Util.null2String(rs.getString("goodsname"));	
				}
				 goodsNameX = goodsName;
				 String goodsNameY =""+goodsName+""+brand+""+goodsSize+"" ;
				 
			   
				 //����Stringתint����
				 try {
					    temp_num = Integer.parseInt(purchaseNum);
					} catch (NumberFormatException e) {
					    e.printStackTrace();
					}
				 
				 
				 //�ж϶���Ԥ�����뻹�ǷǶ����������
			if(isIndependent.equals("1")){
				 //��������
				for (int index=0;index<temp_num;index++){
					
					//ȡ��ˮ��
					String sql_assetsno = "select * from AssetsNo where Datex = '"+App_temp+"'";
					rs2.executeSql(sql_assetsno);
					log.writeLog("ȡ����ˮ��" +sql_assetsno);
					if (rs2.next()){
						SerialNumber = rs2.getInt("SerialNumber");
						String sql_upassetsno = "update AssetsNo set SerialNumber  = SerialNumber +1 where Datex = '"+App_temp+"'";
						rs2.executeSql(sql_upassetsno);
						log.writeLog("������ˮ��" +sql_upassetsno);
					}else{
						
						String sql_inassetsno = "insert into AssetsNo (Datex,SerialNumber) values('"+App_temp+"',0)";
						rs2.executeSql(sql_inassetsno);
						log.writeLog("������ˮ��" +sql_inassetsno);
						
						SerialNumber = 0;
					    String sql_upassetsno = "update AssetsNo set SerialNumber  = SerialNumber +1 where Datex = '"+applicantDate+"'";
						rs2.executeSql(sql_upassetsno);
						log.writeLog("������ˮ��" +sql_upassetsno);
						
					}
					
					//��Ʒ�������
					String temp_index = String.format("%04d",SerialNumber);//Intת��λ��ˮ
					String Lan_temp = langChg.getFirstLetter(goodsName);
					String Cate_temp = langChg.getFirstLetter(catename);
					String goodsNum = ""+Title_temp+""+"-"+""+Cate_temp+""+"-"+""+Lan_temp+""+"-"+App_temp+"-"+""+temp_index+"";
					
					//�������ݲ����ʲ��б����ģ
			           /*String sql_goodsin = "insert into  uf_goodsinforecord (goodsname,goodscate,brand,cate,goodsnum,num,unit,isIndependent,goodstatus,status,remark,layplace) " +
			           		"values('"+goodsNameX+"','"+goodsCate+"','"+brand+"','"+goodsSize+"','"+goodsNum+"',1," +
			           				"'"+unit+"','"+isIndependent+"','"+goodStatus+"',0,'"+remark+"','"+layplace+"')"; */
			           Map<String,String> mapstr = new HashMap<String,String>();
			           mapstr.put("goodsname", goodsNameX);
			           mapstr.put("goodscate", goodsCate);
			           mapstr.put("price", price);
			           mapstr.put("brand", brand);
			           mapstr.put("cate", goodsSize);
			           mapstr.put("configuration", configuration);
			           mapstr.put("specifications", specifications);
			           mapstr.put("equipmentnum", equipmentnum);
			           mapstr.put("condition", condition);
			           mapstr.put("goodsnum", goodsNum);
			           mapstr.put("num", "1");
			           mapstr.put("unit", unit);
			           mapstr.put("goodstatus", goodStatus);
			           mapstr.put("status", "0");
			           mapstr.put("remark",remark);
			           mapstr.put("layplace",layPlace);
			           mapstr.put("library",library);
			           mapstr.put("validdate",validityPeriod);
			           mapstr.put("origin",origin);
			           
			           mapstr.put("guaranteestart",guaranteestart);
			           mapstr.put("guaranteeend",guaranteeend);
			           mapstr.put("guaranteecost",guaranteecost);
			           mapstr.put("supplier",supplier);
			           mapstr.put("suppliercontacts",suppliercontacts);
			           mapstr.put("suppliertel",suppliertel);
			           mapstr.put("customer",customer);
			           
			           mapstr.put("isIndependent", "1");
			           mapstr.put("useatt", useAtt);
			           mapstr.put("repairatt", repairAtt);
			           mapstr.put("infoid", infoId);
			           mapstr.put("goodsatt", goodsAtt);
			           mapstr.put("repairaccount", repairAccount);
			           mapstr.put("repairrate", repairRate);
			           mapstr.put("goodspicture", picture);
			           
			           mapstr.put("formmodeid","82");
			           mapstr.put("modedatacreater",applicant);
			           mapstr.put("modedatacreatertype","0");
			           mapstr.put("modedatacreatedate",applicantDate);
			           mapstr.put("applicant",applicant);
			           mapstr.put("applicantdate",applicantDate);
			           ui.insert(mapstr, "uf_goodsinforecord");
			           log.writeLog("���������������ݲ����ʲ�������ģ");
			           
			           //��ȡȨ�ޱ�ID
			           String  sql_Datashare = "select * from uf_goodsinforecord where id = (select  MAX(id) from uf_goodsinforecord)";
			           rs2.executeSql(sql_Datashare);
						log.writeLog("��ȡ�Բ��б�Ȩ�ޱ�ID" +sql_Datashare);
						if (rs2.next()){
						  ID = rs2.getInt("id");
						}
						
						ModeRightInfo.editModeDataShare(applicantx,82,ID);						
						
						//�������ݲ��������¼
						Map<String,String> mapstt = new HashMap<String,String>();
						mapstt.put("goodsname", String.valueOf(ID));
						mapstt.put("goodscate", goodsCate);
						mapstt.put("goodsno", goodsNum);
						mapstt.put("num", "1");
						mapstt.put("operaterecord", "0");
						mapstt.put("Sysno",sysNo);
						mapstt.put("stock", "1");
						mapstt.put("price", price);
						mapstt.put("customer", customer);
						
						mapstt.put("formmodeid","78");
						mapstt.put("modedatacreater",applicant);
						mapstt.put("modedatacreatertype","0");
						mapstt.put("applicant",applicant);
						mapstt.put("applydate",applicantDate);
						mapstt.put("validdate",validityPeriod);
						mapstt.put("origin",origin);
						mapstt.put("operatetime","##convert(varchar(30),getDate(),21)");
						mapstt.put("layplace",layPlace);
						ui.insert(mapstt, " uf_outinrecord");
						log.writeLog("���������������ݲ��������¼");
						
				           String  sql_Datashare2 = "select * from uf_outinrecord where id = (select  MAX(id) from uf_outinrecord) ";
				           rs2.executeSql(sql_Datashare2);
							log.writeLog("��ȡ�����Ȩ�ޱ�ID" +sql_Datashare2);
							if (rs2.next()){
							  ID2 = rs2.getInt("id");
							}
							ModeRightInfo.editModeDataShare(applicantx,78,ID2);	
						
						
						
					
				}
			  }else{//�Ƕ�������
				  
					//�������ݲ����ʲ�������ģ
		          /* String sql_goodsin = "insert into  uf_goodsinforecord (goodsname,goodscate,brand,cate,goodsnum,num,unit,isIndependent,goodstatus,status,remark��layplace) " +
	           		"values('"+goodsNameX+"','"+goodsCate+"','"+brand+"','"+goodsSize+"','"+goodsNum+"','"+purchaseNum+"'," +
	           				"'"+unit+"','"+isIndependent+"','"+goodStatus+"',0,'"+remark+"','"+layplace+"')"; */
				  //�жϱ����Ƿ����и���Ʒ
				  String sql_judg = "select * from uf_goodsinforecord where goodsname = '"+goodsNameY+"'";
				  rs2.executeSql(sql_judg);
					log.writeLog("�жϱ����Ƿ����и���Ʒ" +sql_judg);
					  if(rs2.next()){
						  IDx =Util.null2String(rs2.getString("id"));
						  //����Ʒ�б��д��ڸ���Ʒʱ�����������и���
						  String sql_goodsin = "update uf_goodsinforecord set num = num+'"+purchaseNum+"' where id = "+IDx+"";
						  rs2.executeSql(sql_goodsin);
						  log.writeLog("���³ɹ�" +sql_goodsin);
						  
						  
							//�������ݲ��������¼
							Map<String,String> mapstt = new HashMap<String,String>();
							mapstt.put("goodsname", IDx);
							mapstt.put("goodscate", goodsCate);
							mapstt.put("num", purchaseNum);
							mapstt.put("operaterecord", "0");
							mapstt.put("Sysno",sysNo);
							mapstt.put("stock", purchaseNum);
							mapstt.put("price", price);
							
							mapstt.put("formmodeid","78");
							mapstt.put("modedatacreater",applicant);
							mapstt.put("modedatacreatertype","0");
							mapstt.put("applicant",applicant);
							mapstt.put("applydate",applicantDate);
							mapstt.put("validdate",validityPeriod);
							mapstt.put("origin",origin);
							mapstt.put("operatetime","##convert(varchar(30),getDate(),21)");
							mapstt.put("layplace",layPlace);
							ui.insert(mapstt, " uf_outinrecord");
							log.writeLog("�Ƕ��������������ݲ��������¼");
							
					           String  sql_Datashare2 = "select * from uf_outinrecord where id = (select  MAX(id) from uf_outinrecord) ";
					           rs2.executeSql(sql_Datashare2);
								log.writeLog("��ȡ������¼Ȩ�ޱ�ID" +sql_Datashare2);
								if (rs2.next()){
								  ID2 = rs2.getInt("id");
								}
								ModeRightInfo.editModeDataShare(applicantx,78,ID2);	
						 
					  }else{
                          //�������ݲ����ʲ��б����ģ
						  Map<String,String> mapstr = new HashMap<String,String>();
				           mapstr.put("goodsname", goodsNameX);
				           mapstr.put("goodscate", goodsCate);
				           mapstr.put("price", price);
				           mapstr.put("brand", brand);
				           mapstr.put("cate", goodsSize);
				           mapstr.put("specifications", specifications);
				           //mapstr.put("equipmentnum", equipmentnum);
				           mapstr.put("num", purchaseNum);
				           mapstr.put("unit", unit);
				           //mapstr.put("goodstatus", goodStatus);
				           mapstr.put("status", "0");
				           mapstr.put("remark",remark);
				           //mapstr.put("layplace",layPlace);
				           //mapstr.put("library",library);
				           mapstr.put("validdate",validityPeriod);
				           mapstr.put("origin",origin);
				           
				           mapstr.put("isIndependent", "0");
				           mapstr.put("useatt", useAtt);
				           mapstr.put("repairatt", repairAtt);
				           mapstr.put("infoid", infoId);
				           mapstr.put("goodsatt", goodsAtt);
				           mapstr.put("repairaccount", repairAccount);
				           mapstr.put("repairrate", repairRate);
				           mapstr.put("goodspicture", picture);
				           
				           mapstr.put("formmodeid","82");
				           mapstr.put("modedatacreater",applicant);
				           mapstr.put("modedatacreatertype","0");
				           mapstr.put("modedatacreatedate",applicantDate);
				           mapstr.put("applicant",applicant);
				           mapstr.put("applicantdate",applicantDate);
				           ui.insert(mapstr, "uf_goodsinforecord");
				           log.writeLog("�Ƕ��������������ݲ����ʲ�������ģ");
				           
				           //��ȡȨ�ޱ�ID
				           String  sql_Datashare = "select * from uf_goodsinforecord where id = (select  MAX(id) from uf_goodsinforecord)";
				           rs2.executeSql(sql_Datashare);
							log.writeLog("��ȡ�ʲ��б�Ȩ�ޱ�ID" +sql_Datashare);
							if (rs2.next()){
							  ID = rs2.getInt("id");
							}
							ModeRightInfo.editModeDataShare(applicantx,82,ID);  
							
							
							//�������ݲ��������¼
						    
							Map<String,String> mapstt = new HashMap<String,String>();
							mapstt.put("goodsname", String.valueOf(ID));
							mapstt.put("goodscate", goodsCate);
							mapstt.put("num", purchaseNum);
							mapstt.put("operaterecord", "0");
							mapstt.put("Sysno",sysNo);
							mapstt.put("stock", purchaseNum);
							mapstt.put("price", price);
							
							mapstt.put("formmodeid","78");
							mapstt.put("modedatacreater",applicant);
							mapstt.put("modedatacreatertype","0");
							mapstt.put("applicant",applicant);
							mapstt.put("applydate",applicantDate);
							mapstt.put("validdate",validityPeriod);
							mapstt.put("origin",origin);
							mapstt.put("operatetime","##convert(varchar(30),getDate(),21)");
							mapstt.put("layplace",layPlace);
							ui.insert(mapstt, " uf_outinrecord");
							log.writeLog("�Ƕ��������������ݲ��������¼");
							
					           String  sql_Datashare2 = "select * from uf_outinrecord where id = (select  MAX(id) from uf_outinrecord) ";
					           rs2.executeSql(sql_Datashare2);
								log.writeLog("��ȡ������¼Ȩ�ޱ�ID" +sql_Datashare2);
								if (rs2.next()){
								  ID2 = rs2.getInt("id");
								}
								ModeRightInfo.editModeDataShare(applicantx,78,ID2);
					  }
			  }
			    
			}	
			
		}else{
			
			return "-1";
		}
		return SUCCESS;
	}

}

