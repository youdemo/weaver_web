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
	
	BaseBean log = new BaseBean();//定义写入日志的对象
	public String execute(RequestInfo info) {
		log.writeLog("进入资产入库AssetsGet——————");
		
		String workflowID = info.getWorkflowid();
		String requestid = info.getRequestid();//获取工作流程ID与requestID值
		ModeRightInfo ModeRightInfo = new ModeRightInfo();
		CnToEn langChg = new CnToEn(); 
		
		InsertUtil ui = new  InsertUtil();
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		int temp_num = 0;//商品数量
		
		String tableName = "";
		String tableNamedt = "";
		String mainID = "";
		int ID = 0;
		String IDx = "";
		int ID2 = 0;
		String Datex = "";
		int SerialNumber = 0;
		//主表
		String applicant = "";//申请人
		String applicantDate = "";//申请日期
		String sysNo = "";//系统编号
		int applicantx = 0;
		//明细表
		String goodsNameID = "";//商品名称ID
		String goodsName = "";//商品名称
		String price = "";//商品单价
		String goodsNameX = "";//拼接商品名称
		String goodsCate = "";//所属分类
		String purchaseNum = "";//采购数量
		String validityPeriod = "";//有效期
		String brand = "";//品牌
		String goodsSize = "";//型号
		String specifications ="";//规格
		String configuration ="";//配置
		String layPlace = "";//放置位置
		String remark = "";//备注
		String goodStatus ="";//物品状态
		String layplace ="";//放置库房
		String library ="";//库位
		String origin ="";//来源
		String guaranteestart ="";//保修开始日期
		String guaranteeend ="";//保修截止日期
		String guaranteecost ="";//续保年费
		String supplier ="";//保修供应商名称
		String suppliercontacts ="";//保修供应商联系人
		String suppliertel ="";//保修供应商联系电话
		String customer ="";//客户
		String equipmentnum ="";
		String condition ="";
		
		//父属性
		String isIndependent ="";//是否独立核算
		String useAtt ="";//领用属性
		String repairAtt ="";//是否可维修
		String infoId ="";//父属性id
		String goodsAtt ="";//资产属性
		String repairAccount ="";//维修基数金额
		String repairRate ="";//每年递增的维修费率
		String picture ="";//产品图片
		String unit = "";//单位
		
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
			tableNamedt = tableName + "_dt1";//这个方法是为了有多个明细做的准备
			//tableNamedt = tableName + "_dt2";
			
			// 查询主表
			sql = "select * from "+tableName + " where requestid="+requestid;
			rs.execute(sql);
			if(rs.next()){
				
				mainID = Util.null2String(rs.getString("ID"));//获取主表中的ID，作为明细表中的mainID
				applicant = Util.null2String(rs.getString("applicant"));
				applicantDate = Util.null2String(rs.getString("applicantdate"));
				sysNo = Util.null2String(rs.getString("Sysno"));
				
				try {
				     applicantx = Integer.parseInt(applicant);
				} catch (NumberFormatException e) {
				    e.printStackTrace();
				}
				
			}
			
			//查询明细表
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
	           
				//是否为独立核算，是否可维修，使用属性     父属性
				sql= "select * from uf_goodsinfo  where id = '"+goodsNameID+"'";
				rs.execute(sql);
				log.writeLog("资产列表查找资产独立核算属性:" +sql);
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
				log.writeLog("资产列表查找资产独立核算属性:" +sql_s);
				if(rs.next()){
					superid = Util.null2String(rs.getString("superid"));
				}
				
				
				while(!"".equals(superid)){
					String sql_x = "select * from uf_goodscate where id = "+superid+"";
					rs.execute(sql_x);
					log.writeLog("资产列表查找资产独立核算属性:" +sql_x);
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

	           //获取流程商品名ID所对应的商品名并将其与品牌型号拼接      父属性
				String sql_info = "select * from uf_goodsinfo where id = '"+goodsNameID+"'";
				rs.executeSql(sql_info);
				log.writeLog("获取流程商品名ID所对应的商品名" +sql_info);
				if (rs.next()){
					goodsName = Util.null2String(rs.getString("goodsname"));	
				}
				 goodsNameX = goodsName;
				 String goodsNameY =""+goodsName+""+brand+""+goodsSize+"" ;
				 
			   
				 //数量String转int类型
				 try {
					    temp_num = Integer.parseInt(purchaseNum);
					} catch (NumberFormatException e) {
					    e.printStackTrace();
					}
				 
				 
				 //判断独立预案插入还是非独立核算插入
			if(isIndependent.equals("1")){
				 //独立核算
				for (int index=0;index<temp_num;index++){
					
					//取流水号
					String sql_assetsno = "select * from AssetsNo where Datex = '"+App_temp+"'";
					rs2.executeSql(sql_assetsno);
					log.writeLog("取出流水号" +sql_assetsno);
					if (rs2.next()){
						SerialNumber = rs2.getInt("SerialNumber");
						String sql_upassetsno = "update AssetsNo set SerialNumber  = SerialNumber +1 where Datex = '"+App_temp+"'";
						rs2.executeSql(sql_upassetsno);
						log.writeLog("更新流水号" +sql_upassetsno);
					}else{
						
						String sql_inassetsno = "insert into AssetsNo (Datex,SerialNumber) values('"+App_temp+"',0)";
						rs2.executeSql(sql_inassetsno);
						log.writeLog("插入流水号" +sql_inassetsno);
						
						SerialNumber = 0;
					    String sql_upassetsno = "update AssetsNo set SerialNumber  = SerialNumber +1 where Datex = '"+applicantDate+"'";
						rs2.executeSql(sql_upassetsno);
						log.writeLog("更新流水号" +sql_upassetsno);
						
					}
					
					//商品编号生成
					String temp_index = String.format("%04d",SerialNumber);//Int转四位流水
					String Lan_temp = langChg.getFirstLetter(goodsName);
					String Cate_temp = langChg.getFirstLetter(catename);
					String goodsNum = ""+Title_temp+""+"-"+""+Cate_temp+""+"-"+""+Lan_temp+""+"-"+App_temp+"-"+""+temp_index+"";
					
					//流程数据插入资产列表表单建模
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
			           log.writeLog("独立核算流程数据插入资产入库表单建模");
			           
			           //拿取权限表ID
			           String  sql_Datashare = "select * from uf_goodsinforecord where id = (select  MAX(id) from uf_goodsinforecord)";
			           rs2.executeSql(sql_Datashare);
						log.writeLog("拿取自残列表权限表ID" +sql_Datashare);
						if (rs2.next()){
						  ID = rs2.getInt("id");
						}
						
						ModeRightInfo.editModeDataShare(applicantx,82,ID);						
						
						//流程数据插入出入库记录
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
						log.writeLog("独立核算流程数据插入出入库记录");
						
				           String  sql_Datashare2 = "select * from uf_outinrecord where id = (select  MAX(id) from uf_outinrecord) ";
				           rs2.executeSql(sql_Datashare2);
							log.writeLog("拿取出入库权限表ID" +sql_Datashare2);
							if (rs2.next()){
							  ID2 = rs2.getInt("id");
							}
							ModeRightInfo.editModeDataShare(applicantx,78,ID2);	
						
						
						
					
				}
			  }else{//非独立核算
				  
					//流程数据插入资产入库表单建模
		          /* String sql_goodsin = "insert into  uf_goodsinforecord (goodsname,goodscate,brand,cate,goodsnum,num,unit,isIndependent,goodstatus,status,remark，layplace) " +
	           		"values('"+goodsNameX+"','"+goodsCate+"','"+brand+"','"+goodsSize+"','"+goodsNum+"','"+purchaseNum+"'," +
	           				"'"+unit+"','"+isIndependent+"','"+goodStatus+"',0,'"+remark+"','"+layplace+"')"; */
				  //判断表中是否已有改商品
				  String sql_judg = "select * from uf_goodsinforecord where goodsname = '"+goodsNameY+"'";
				  rs2.executeSql(sql_judg);
					log.writeLog("判断表中是否已有改商品" +sql_judg);
					  if(rs2.next()){
						  IDx =Util.null2String(rs2.getString("id"));
						  //当商品列表中存在改商品时对其数量进行更信
						  String sql_goodsin = "update uf_goodsinforecord set num = num+'"+purchaseNum+"' where id = "+IDx+"";
						  rs2.executeSql(sql_goodsin);
						  log.writeLog("更新成功" +sql_goodsin);
						  
						  
							//流程数据插入出入库记录
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
							log.writeLog("非独立核算流程数据插入出入库记录");
							
					           String  sql_Datashare2 = "select * from uf_outinrecord where id = (select  MAX(id) from uf_outinrecord) ";
					           rs2.executeSql(sql_Datashare2);
								log.writeLog("拿取出入库记录权限表ID" +sql_Datashare2);
								if (rs2.next()){
								  ID2 = rs2.getInt("id");
								}
								ModeRightInfo.editModeDataShare(applicantx,78,ID2);	
						 
					  }else{
                          //流程数据插入资产列表表单建模
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
				           log.writeLog("非独立核算流程数据插入资产入库表单建模");
				           
				           //拿取权限表ID
				           String  sql_Datashare = "select * from uf_goodsinforecord where id = (select  MAX(id) from uf_goodsinforecord)";
				           rs2.executeSql(sql_Datashare);
							log.writeLog("拿取资产列表权限表ID" +sql_Datashare);
							if (rs2.next()){
							  ID = rs2.getInt("id");
							}
							ModeRightInfo.editModeDataShare(applicantx,82,ID);  
							
							
							//流程数据插入出入库记录
						    
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
							log.writeLog("非独立核算流程数据插入出入库记录");
							
					           String  sql_Datashare2 = "select * from uf_outinrecord where id = (select  MAX(id) from uf_outinrecord) ";
					           rs2.executeSql(sql_Datashare2);
								log.writeLog("拿取出入库记录权限表ID" +sql_Datashare2);
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

