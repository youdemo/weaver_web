package seahonor.action.doc;

import weaver.conn.RecordSet;
import weaver.docs.category.CategoryManager;
import weaver.docs.category.DocTreelistComInfo;
import weaver.docs.category.MainCategoryComInfo;
import weaver.docs.category.SecCategoryComInfo;
import weaver.docs.category.SecCategoryCustomSearchComInfo;
import weaver.docs.category.SecCategoryDocPropertiesComInfo;
import weaver.docs.category.SecCategoryManager;
import weaver.docs.category.SubCategoryComInfo;
import weaver.docs.category.security.MultiAclManager;
import weaver.docs.docs.SecShareableCominfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.HrmUserVarify;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.systeminfo.SysMaintenanceLog;

public class CreateDocDRAction implements Action{

	public String execute(RequestInfo info) {
		String requestid = info.getRequestid();
		String workflow_id = info.getWorkflowid();
		RecordSet rs = new RecordSet();
		String tableName = "";
		String mainid = "";
		String sqr="";
		String sjml = "";
		String mnmc = "";

		BaseBean log = new BaseBean();
		String sql = "Select tablename From Workflow_bill Where id=(";
		sql += "Select formid From workflow_base Where id="+workflow_id+")";
		rs.executeSql(sql);
		
		//	rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		
		sql="select * from "+tableName+" where requestid="+requestid;
		rs.executeSql(sql);
		if(rs.next()){
			mainid = Util.null2String(rs.getString("id"));
			sqr = Util.null2String(rs.getString("sqr"));
		}
		sql="select * from "+tableName+"_dt1 where mainid="+mainid;
		rs.executeSql(sql);
		while(rs.next()){
			sjml = Util.null2String(rs.getString("sjml"));
			mnmc = Util.null2String(rs.getString("mnmc"));
			String  secid="";
			try {
				secid= CreateDR(Integer.valueOf(sqr),sjml,mnmc);
			} catch (Exception e) {
				log.writeLog(mnmc+"目录创建失败 requestid="+requestid);
				e.printStackTrace();
			}
			log.writeLog(mnmc+"目录创建成功 secid="+secid+"requestid="+requestid);
		}
		
		return SUCCESS;
	}
	
	public String  CreateDR(int sqr,String sjml,String mlmc) throws Exception{
		DocTreelistComInfo DocTreelistComInfo = new DocTreelistComInfo();
		MainCategoryComInfo MainCategoryComInfo = new MainCategoryComInfo();
		SubCategoryComInfo SubCategoryComInfo = new SubCategoryComInfo();
		SecCategoryComInfo SecCategoryComInfo = new SecCategoryComInfo();
		SecCategoryManager scm = new SecCategoryManager();
		RecordSet RecordSet = new RecordSet();
		RecordSet RecordSet1 = new RecordSet();
		SysMaintenanceLog log = new SysMaintenanceLog();
		SecCategoryDocPropertiesComInfo SecCategoryDocPropertiesComInfo = new SecCategoryDocPropertiesComInfo();
		SecCategoryCustomSearchComInfo SecCategoryCustomSearchComInfo = new SecCategoryCustomSearchComInfo();
		SecShareableCominfo SecShareableCominfo = new SecShareableCominfo();
		
		
		 char flag=Util.getSeparator();
		  int userid=sqr;
		  MultiAclManager am = new MultiAclManager();
		  CategoryManager cm = new CategoryManager();
		  String isDialog = "1";
		  String isEntryDetail = "0";
		  String from = "subedit";
		  int fromTab = 0;
		  String OriSubId = "";//来时页面的subId

		int secid = -1;

		  	String subcategoryid="-1";
		    String parentid = Util.null2String(sjml);//父目录
		  	int subid = Util.getIntValue(subcategoryid,-1);
		    int mainid=Util.getIntValue(SubCategoryComInfo.getMainCategoryid(""+subid),0);
			String categoryname=mlmc.trim();//目录名称
			String coder="";//目录编码
			String srccategoryname="";
			String docmouldid="0";
				
			String 	wordmouldid="0";
			String publishable="0";
			String replyable="0";
			String shareable="1";

			String cusertype="0";
			String cuserseclevel="0";//用户安全级别
			String cdepartmentid1="0";
			String cdepseclevel1="0";
			String cdepartmentid2="0";
			String cdepseclevel2="0";
			String croleid1="0";
			String crolelevel1="";
			String croleid2="0";
			String crolelevel2="";
			String croleid3="0";
			String crolelevel3="";
			String approvewfid="24";
			String hasaccessory="0";
			String accessorynum="";
			String hasasset="";
			String assetlabel="";
			String hasitems="";
			String itemlabel="";
			String hashrmres="";
			String hrmreslabel="";
			String hascrm="";
			String crmlabel="";
			String hasproject="";
			String projectlabel="";
			String hasfinance="";
			String financelabel="";

		  //增加是否此目录打分，以及是否匿名打分等字段
		    int markable=0;
		    int markAnonymity=0;
		    int orderable=0;
		    int defaultLockedDoc=0;
		    int isSetShare=0;

		    int allownModiMShareL=0;
		    int allownModiMShareW=0;
		    String allowShareTypeStrs = "";

//		    String[] allowAddSharers = request.getParameterValues("allowAddSharer");
//		    if (allowAddSharers!=null) {
//		        for (int i=0;i<allowAddSharers.length;i++){
//		            allowShareTypeStrs+=allowAddSharers[i]+",";
//		        }
//		    }

		    int maxOfficeDocFileSize = 8;
		    int maxUploadFileSize = 20;
		  
			
		    int noDownload = 0;
			int noRepeatedName = 0;
			int bacthDownload = 0;
			int isControledByDir = 0;
			int pubOperation = 0;
			int childDocReadRemind = 0;

			String isPrintControl = "";
			int printApplyWorkflowId = 0;

		    String isLogControl = "";

			int readOpterCanPrint = 0;
			
			int logviewtype = 0;

		    //TD2858 新的需求: 添加与文档创建人相关的默认共享  
		    int PCreater=0;
		    int PCreaterManager=0;
		    int PCreaterJmanager=0;
		    int PCreaterDownOwner=0;
		    int PCreaterSubComp=0;
		    int PCreaterDepart=0;
		    
		    int PCreaterDownOwnerLS=0;
		    int PCreaterSubCompLS=0;
		    int PCreaterDepartLS=0; 

		    int PDocCreaterW=0;
		    int PCreaterManagerW=0;
		    int PCreaterJmanagerW=0;

			String defaultDummyCata="";
			 int relationable =0;
			
			float secorder = 0;//目录顺序
			int dirmouldid = 0;

			int appointedWorkflowId = 0;	

		/** =========TD12005 文档下载控制权限   开始=========*/
			int PCreaterDL = 0;
			int PCreaterManagerDL = 0;
			int PCreaterSubCompDL = 0;
			int PCreaterDepartDL = 0;
			int PCreaterWDL = 0;
			int PCreaterManagerWDL = 0;
		/** =========TD12005 文档下载控制权限   结束=========*/

//		    String isUseFTPOfSystem=Util.null2String(request.getParameter("isUseFTPOfSystem"));//ecology系统使用FTP服务器设置功能  true:启用   false:不使用
//		    String isUseFTP=Util.null2String(request.getParameter("isUseFTP"));//指定文档子目录是否启用FTP服务器设置
//		    int FTPConfigId=Util.getIntValue(request.getParameter("FTPConfigId"));//FTP服务器
		    
		    int isOpenAttachment = 0;
		    
		    int isAutoExtendInfo = 0;
		    
		    int subcompanyId = -1;
		    
		    if(Util.getIntValue(parentid)>0){
				subcompanyId = 0;
			}
		    
		    int level = 0;
		    
		    level = SecCategoryComInfo.getLevel(parentid,true);
 
			    String extendParentAttr = "1";  
		        String checkSql = "select count(id) from DocSecCategory where categoryname = '"+categoryname+"'";
		        if(Util.getIntValue(parentid)>0){
		        	checkSql = checkSql + " and parentid="+parentid; 
		        }else{
		        	checkSql = checkSql + " and (parentid is null or parentid<=0) "; 
				}
		        RecordSet.executeSql(checkSql);
		        if(RecordSet.next()){
		        	if(RecordSet.getInt(1)>0){
		          return "false";	
		        	}
		        }
				String ParaStr=subcategoryid+flag+categoryname+flag+docmouldid+flag+publishable+flag+replyable+flag+shareable+flag+
							cusertype+flag+cuserseclevel+flag+cdepartmentid1+flag+cdepseclevel1+flag+cdepartmentid2+flag+
							cdepseclevel2+flag+croleid1+flag+crolelevel1+flag+croleid2+flag+crolelevel2+flag+croleid3+flag+
							crolelevel3+flag+hasaccessory+flag+accessorynum+flag+
							hasasset+flag+assetlabel+flag+hasitems+flag+itemlabel+flag+hashrmres+flag+hrmreslabel+flag+hascrm+flag+
							crmlabel+flag+hasproject+flag+projectlabel+flag+hasfinance+flag+financelabel+flag+approvewfid+flag+markable+flag+markAnonymity+flag+orderable+flag+defaultLockedDoc+flag+""+allownModiMShareL+flag+""+allownModiMShareW+flag+
							maxUploadFileSize+flag+wordmouldid+flag+isSetShare+flag+
							noDownload+flag+noRepeatedName+flag+isControledByDir+flag+pubOperation+flag+childDocReadRemind+flag+readOpterCanPrint+flag+isLogControl;
				if(extendParentAttr.equals("1")){
					ParaStr = scm.copyAttrFromParent(ParaStr,parentid,categoryname,noRepeatedName);
				}
				
				ParaStr = ParaStr + flag + subcompanyId+flag+level+flag+parentid+flag+secorder;
				RecordSet.executeProc("Doc_SecCategory_Insert_New",ParaStr);
				
				if(!RecordSet.next()){
				
		            return "false";
				}
		        int id=RecordSet.getInt(1);
				int newid=RecordSet.getInt(1);
		         /*是否允许订阅的处理 start*/
		        if (orderable ==1) {
		            RecordSet1.executeSql("update docdetail set orderable='1' where seccategory = "+id ); 
		        }
		        //RecordSet1.executeSql("update DocSecCategory set secorder="+secorder+",defaultDummyCata='"+defaultDummyCata+"',logviewtype="+logviewtype+",appliedTemplateId="+dirmouldid+",coder='"+coder+"',appointedWorkflowId="+appointedWorkflowId+",isPrintControl='"+isPrintControl+"',printApplyWorkflowId="+printApplyWorkflowId+",relationable='"+relationable+"',isOpenAttachment="+isOpenAttachment+",isAutoExtendInfo="+isAutoExtendInfo+",maxOfficeDocFileSize="+maxOfficeDocFileSize+",bacthDownload="+bacthDownload+" where id = "+id );
		        if(extendParentAttr.equals("1")){
					boolean result = scm.copyOtherInfoFromParent(id,parentid,level);
		        }
		        RecordSet1.executeSql("update DocSecCategory set secorder="+secorder+",coder='"+coder+"' where id = "+id );
		        SecShareableCominfo.addSecShareInfoCache(""+id);    
		        secid = newid;
				cm.addSecidToSuperiorSubCategory(newid);
				log.resetParameter();
		        log.setRelatedId(newid);
		        log.setRelatedName(categoryname);
		        log.setOperateType("1");
		        log.setOperateDesc("Doc_SecCategory_Insert");
		        log.setOperateItem("3");
		        log.setOperateUserid(userid);
		        log.setClientAddress("127.0.0.1");
		        log.setSysLogInfo();
		        
		        //TD2858 新的需求: 添加与文档创建人相关的默认共享  开始    
				if(!extendParentAttr.equals("1")){
				String strSqlInsert ="insert into DocSecCategoryShare (seccategoryid,sharetype,sharelevel,downloadlevel,operategroup)values("+newid+",1,3,1,1)";        
		        RecordSet.executeSql(strSqlInsert);  
				strSqlInsert="insert into DocSecCategoryShare (seccategoryid,sharetype,sharelevel,downloadlevel,operategroup)values("+newid+",2,1,1,1)";
			    RecordSet.executeSql(strSqlInsert);
				strSqlInsert="insert into DocSecCategoryShare (seccategoryid,sharetype,sharelevel,downloadlevel,operategroup)values("+newid+",1,3,1,2)";
			    RecordSet.executeSql(strSqlInsert);
				strSqlInsert="insert into DocSecCategoryShare (seccategoryid,sharetype,sharelevel,downloadlevel,operategroup)values("+newid+",2,1,1,2)";
			    RecordSet.executeSql(strSqlInsert);

		        //System.out.println(strSqlInsert);   
		        //TD2858 新的需求: 添加与文档创建人相关的默认共享  结束

				
				}
				MainCategoryComInfo.removeMainCategoryCache();
		        SubCategoryComInfo.removeMainCategoryCache();
		    	SecCategoryComInfo.removeMainCategoryCache();
		    	SecCategoryDocPropertiesComInfo.removeCache();
		    	SecCategoryDocPropertiesComInfo.addDefaultDocProperties(secid);
		    	SecCategoryCustomSearchComInfo.checkDefaultCustomSearch(secid);
				DocTreelistComInfo.removeGetDocListInfordCache();
				
				return String.valueOf(secid);
				
		    
	}
}
