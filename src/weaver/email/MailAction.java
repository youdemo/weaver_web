package weaver.email;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import oracle.sql.CLOB;
import weaver.WorkPlan.WorkPlanViewer;
import weaver.conn.ConnStatement;
import weaver.conn.RecordSet;
import weaver.docs.category.SecCategoryComInfo;
import weaver.docs.category.SubCategoryComInfo;
import weaver.docs.docs.DocComInfo;
import weaver.docs.docs.DocExtUtil;
import weaver.docs.docs.DocManager;
import weaver.docs.docs.DocViewer;
import weaver.docs.docs.ImageFileIdUpdate;
import weaver.docs.docs.VersionIdUpdate;
import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.systeminfo.SystemEnv;

public class MailAction
  extends BaseBean
{
  private static ImageFileIdUpdate imageFileIdUpdate = new ImageFileIdUpdate();
  private static VersionIdUpdate versionIdUpdate = new VersionIdUpdate();
  
  public void exportToCRMContract(String paramString1, User paramUser, String paramString2, HttpServletRequest paramHttpServletRequest)
  {
    WorkPlanViewer localWorkPlanViewer = new WorkPlanViewer();
    RecordSet localRecordSet = new RecordSet();
    String str1 = "";String str2 = "";String str3 = "";String str4 = "";
    String str5 = "";String str6 = "";
    String str7 = "";
    String[] arrayOfString1 = Util.TokenizerString2(paramString1, ",");
    String[] arrayOfString2 = Util.TokenizerString2(paramString2, ",");
    int i = paramUser.getUID();
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = Util.getSeparator();
    String str8 = "";
    int i2 = 0;
    str7 = "SELECT crmSecId FROM MailSetting WHERE userId=" + i + "";
    localRecordSet.executeSql(str7);
    if (localRecordSet.next()) {
      i2 = localRecordSet.getInt("crmSecId");
    }
    try
    {
      localRecordSet.executeSql("SELECT MAX(id) AS maxid FROM WorkPlan");
      if (localRecordSet.next()) {
        k = localRecordSet.getInt("maxid");
      }
      for (int i3 = 0; i3 < arrayOfString1.length; i3++)
      {
        j = Util.getIntValue(arrayOfString1[i3]);
        if (j > 0) {
          for (int i4 = 0; i4 < arrayOfString2.length; i4++)
          {
            localRecordSet.executeSql("SELECT senddate,subject,content,hasHtmlImage FROM MailResource WHERE id=" + arrayOfString2[i4] + "");
            if (localRecordSet.next())
            {
              str1 = Util.null2String(localRecordSet.getString("senddate"));
              str2 = Util.null2String(localRecordSet.getString("subject"));
              str3 = Util.null2String(localRecordSet.getString("content"));
              str4 = Util.null2String(localRecordSet.getString("hasHtmlImage"));
              str5 = str1.substring(0, 10);
              str6 = str1.substring(11, 16);
            }
            Object localObject1;
            String str10;
            Object localObject2;
            Object localObject3;
            if ("oracle".equals(localRecordSet.getDBType()))
            {
              ConnStatement localConnStatement = new ConnStatement();
              try
              {
                localConnStatement.setStatementSql("SELECT mailcontent FROM MailContent WHERE mailid=" + arrayOfString2[i4] + "");
                localConnStatement.executeQuery();
                if (localConnStatement.next())
                {
                  localObject1 = localConnStatement.getClob("mailcontent");
                  str10 = "";
                  localObject2 = new StringBuffer("");
                  localObject3 = new BufferedReader(((CLOB)localObject1).getCharacterStream());
                  while ((str10 = ((BufferedReader)localObject3).readLine()) != null) {
                    localObject2 = ((StringBuffer)localObject2).append(str10);
                  }
                  ((BufferedReader)localObject3).close();
                  str3 = ((StringBuffer)localObject2).toString();
                }
              }
              finally
              {
                localConnStatement.close();
              }
            }
            str3 = Util.replace(str3, "==br==", "\n", 0);
            if ("1".equals(str4))
            {
              localRecordSet.executeSql("select id ,isfileattrachment,fileContentId from MailResourceFile where mailid=" + arrayOfString2[i4] + " and isfileattrachment=0");
              int i5 = 0;
              while (localRecordSet.next())
              {
                localObject1 = localRecordSet.getString("isfileattrachment");
                i5++;
                str10 = localRecordSet.getString("id");
                localObject2 = localRecordSet.getString("fileContentId");
                localObject3 = "cid:" + (String)localObject2;
                String str11 = null;
                if (null == paramHttpServletRequest) {
                  str11 = "http://" + Util.getRequestHost(paramHttpServletRequest) + "/weaver/weaver.email.FileDownloadLocation?fileid=" + str10;
                } else {
                  str11 = "/weaver/weaver.email.FileDownloadLocation?fileid=" + str10;
                }
                str3 = Util.StringReplaceOnce(str3, (String)localObject3, str11);
              }
            }
            String str9 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            str7 = "INSERT INTO WorkPlan (type_n,begindate,begintime,resourceid,description,name,status,urgentLevel,createrid,createrType,crmid,createdate,createtime) VALUES ('3','" + str5 + "','" + str6 + "'," + i + ",'" + str3 + "','" + str2 + "','2','1'," + i + ",'1'," + j + ",'" + str9.substring(0, 10) + "','" + str9.substring(11) + "')";
            localRecordSet.executeSql(str7);
            
            localRecordSet.executeSql("SELECT MAX(id) FROM WorkPlan");
            localRecordSet.next();
            k = localRecordSet.getInt(1);
            localWorkPlanViewer.setWorkPlanShareById(String.valueOf(k));
            if (i2 > 0)
            {
              str7 = "SELECT attachmentNumber FROM MailResource WHERE id=" + arrayOfString2[i4] + "";
              localRecordSet.executeSql(str7);
              if (localRecordSet.next()) {
                n = localRecordSet.getInt("attachmentNumber");
              }
              if (n > 0)
              {
                m = exportToDoc(Util.getIntValue(arrayOfString2[i4]), i2, paramUser, paramHttpServletRequest);
                str7 = "UPDATE WorkPlan SET docid='" + m + "' WHERE id=" + k + "";
                localRecordSet.executeSql(str7);
              }
            }
          }
        }
      }
    }
    catch (Exception localException)
    {
      writeLog(localException);
    }
  }
  
  public int exportToDoc(int paramInt1, int paramInt2, User paramUser, HttpServletRequest paramHttpServletRequest)
    throws Exception
  {
    RecordSet localRecordSet = new RecordSet();
    DocExtUtil localDocExtUtil = new DocExtUtil();
    SecCategoryComInfo localSecCategoryComInfo = new SecCategoryComInfo();
    SubCategoryComInfo localSubCategoryComInfo = new SubCategoryComInfo();
    DocComInfo localDocComInfo = new DocComInfo();
    DocManager localDocManager = new DocManager();
    DocViewer localDocViewer = new DocViewer();
    int i = 0;int j = 0;int k = 0;
    String str1 = "";String str2 = "";String str3 = "";
    String str4 = "";String str5 = "";String str6 = "";
    int m = 0;
    int n = 0;
    String str7 = "";
    int i1 = 0;
    String str8 = "";
    

    k = Util.getIntValue(localSecCategoryComInfo.getSubCategoryid(String.valueOf(paramInt2)));
    j = Util.getIntValue(localSubCategoryComInfo.getMainCategoryid(String.valueOf(k)));
    if (paramInt2 == -1)
    {
      localRecordSet.executeSql("SELECT mainId, subId, secId FROM MailSetting WHERE userId=" + paramUser.getUID() + "");
      localRecordSet.next();
      j = localRecordSet.getInt("mainId");
      k = localRecordSet.getInt("subId");
      paramInt2 = localRecordSet.getInt("secId");
    }
    localRecordSet.executeSql("SELECT subject, content, hasHtmlImage FROM MailResource WHERE id=" + paramInt1 + "");
    localRecordSet.next();
    str1 = SystemEnv.getHtmlLabelName(71, paramUser.getLanguage()) + "-" + Util.null2String(localRecordSet.getString("subject"));
    str2 = Util.null2String(localRecordSet.getString("content"));
    String str9;
    Object localObject2;
    Object localObject3;
    if ("oracle".equals(localRecordSet.getDBType()))
    {
      ConnStatement localConnStatement = new ConnStatement();
      try
      {
        localConnStatement.setStatementSql("SELECT mailcontent FROM MailContent WHERE mailid=" + paramInt1 + "");
        localConnStatement.executeQuery();
        if (localConnStatement.next())
        {
          localObject1 = localConnStatement.getClob("mailcontent");
          str9 = "";
          localObject2 = new StringBuffer("");
          localObject3 = new BufferedReader(((CLOB)localObject1).getCharacterStream());
          while ((str9 = ((BufferedReader)localObject3).readLine()) != null) {
            localObject2 = ((StringBuffer)localObject2).append(str9);
          }
          ((BufferedReader)localObject3).close();
          str2 = ((StringBuffer)localObject2).toString();
        }
      }
      finally
      {
        localConnStatement.close();
      }
    }
    str2 = Util.replace(str2, "==br==", "\n", 0);
    
    str3 = Util.null2String(localRecordSet.getString("hasHtmlImage"));
    if ("1".equals(str3))
    {
      localRecordSet.executeSql("select id ,isfileattrachment,fileContentId from MailResourceFile where mailid=" + paramInt1 + " and isfileattrachment=0");
      i2 = 0;
      while (localRecordSet.next())
      {
        localObject1 = localRecordSet.getString("isfileattrachment");
        i2++;
        str9 = localRecordSet.getString("id");
        localObject2 = localRecordSet.getString("fileContentId");
        localObject3 = "cid:" + (String)localObject2;
        String str10 = "http://" + Util.getRequestHost(paramHttpServletRequest) + "/weaver/weaver.email.FileDownloadLocation?fileid=" + str9;
        str2 = Util.StringReplaceOnce(str2, (String)localObject3, str10);
      }
    }
    str4 = TimeUtil.getCurrentTimeString();
    int i2 = str4.indexOf(" ");
    if (i2 != -1)
    {
      str5 = str4.substring(0, i2);
      str6 = str4.substring(i2 + 1, str4.length());
    }
    i = localDocManager.getNextDocId(localRecordSet);
    localDocManager.setId(i);
    localDocManager.setMaincategory(j);
    localDocManager.setSubcategory(k);
    localDocManager.setSeccategory(paramInt2);
    localDocManager.setLanguageid(paramUser.getLanguage());
    localDocManager.setDoccontent(str2);
    localDocManager.setDocstatus("1");
    localDocManager.setDocsubject(str1);
    localDocManager.setDoccreaterid(paramUser.getUID());
    localDocManager.setUsertype(paramUser.getLogintype());
    localDocManager.setOwnerid(paramUser.getUID());
    localDocManager.setDoclastmoduserid(paramUser.getUID());
    localDocManager.setDoccreatedate(str5);
    localDocManager.setDoclastmoddate(str5);
    localDocManager.setDoccreatetime(str6);
    localDocManager.setDoclastmodtime(str6);
    localDocManager.setDoclangurage(paramUser.getLanguage());
    localDocManager.setKeyword(str1);
    localDocManager.setIsapprover("0");
    localDocManager.setIsreply("");
    localDocManager.setDocdepartmentid(paramUser.getUserDepartment());
    localDocManager.setDocreplyable("1");
    localDocManager.setAccessorycount(1);
    localDocManager.setParentids("" + i);
    localDocManager.setOrderable("" + localSecCategoryComInfo.getSecOrderable(paramInt2));
    localDocManager.setClientAddress(paramHttpServletRequest.getRemoteAddr());
    localDocManager.setUserid(paramUser.getUID());
    localDocManager.AddDocInfo();
    

    Object localObject1 = new RecordSet();
    if ("oracle".equals(localRecordSet.getDBType()))
    {
      str8 = "UPDATE DocDetailContent SET doccontent='" + str2 + "' WHERE docid=" + i + "";
      ((RecordSet)localObject1).executeSql(str8);
    }
    localRecordSet.executeSql("SELECT * FROM MailResourceFile WHERE mailId=" + paramInt1 + " AND isfileattrachment='1'");
    while (localRecordSet.next())
    {
      m = imageFileIdUpdate.getImageFileNewId();
      

      str8 = "INSERT INTO ImageFile (imagefileid, imagefilename, imagefiletype, imagefile, imagefileused, filerealpath, iszip, isencrypt, fileSize, downloads,isaesencrypt,aescode) VALUES ";
      str8 = str8 + "(" + m + ", '" + localRecordSet.getString("filename") + "', '" + localRecordSet.getString("filetype") + "', '" + localRecordSet.getString("attachfile") + "', 1, '" + localRecordSet.getString("filerealpath") + "', '" + localRecordSet.getString("iszip") + "', '" + localRecordSet.getString("isencrypt") + "', '" + localRecordSet.getString("filesize") + "' , 0,'" + localRecordSet.getString("isaesencrypt") + "','" + localRecordSet.getString("aescode") + "')";
      ((RecordSet)localObject1).executeSql(str8);
      


      n = versionIdUpdate.getVersionNewId();
      

      str7 = localDocExtUtil.getFileExt(localRecordSet.getString("filename"));
      if (str7.equalsIgnoreCase("doc")) {
        str7 = "3";
      } else if (str7.equalsIgnoreCase("xls")) {
        str7 = "4";
      } else {
        str7 = "2";
      }
      ((RecordSet)localObject1).executeProc("SequenceIndex_SelectDocImageId", "");
      if (((RecordSet)localObject1).next()) {
        i1 = Util.getIntValue(((RecordSet)localObject1).getString(1));
      }
      str8 = "INSERT INTO DocImageFile (id, docid, imagefileid, imagefilename, docfiletype, versionId) VALUES";
      str8 = str8 + "(" + i1 + ", " + i + ", " + m + ", '" + localRecordSet.getString("filename") + "', '" + str7 + "', " + n + ")";
      ((RecordSet)localObject1).executeSql(str8);
    }
    localDocManager.AddShareInfo();
    localDocComInfo.addDocInfoCache("" + i);
    localDocViewer.setDocShareByDoc("" + i);
    
    return i;
  }
}
