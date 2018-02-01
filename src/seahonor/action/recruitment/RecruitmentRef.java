package seahonor.action.recruitment;

import java.util.HashMap;
import java.util.Map;
import org.apache.axis.encoding.Base64;
import seahonor.util.InsertUtil;
import seahonor.util.SysNoForSelf;
import sun.misc.BASE64Decoder;
import weaver.conn.RecordSet;
import weaver.docs.webservices.DocAttachment;
import weaver.docs.webservices.DocInfo;
import weaver.docs.webservices.DocServiceImpl;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;

public class RecruitmentRef extends BaseBean
{
  public String doService(String name, String gender, String birthday, String nativePlace, String education, String startdate, String telphone, String email, String CPA, String CTA, String ACCA, String intermediate, String junior, String post, String brand8, String brand6, String brand4, String BJT, String experience, String company, String jobtile, String attachname, String attach, String icode)
  {
    String res = "S";
    try {
      if (!("02n12WE2X2H56LLUW-0X000110".equals(icode))) {
        return "E:验证错误!";
      }
      res = createrRecruitmentInfo(name, gender, birthday, nativePlace, education, startdate, 
        telphone, email, CPA, CTA, ACCA, intermediate, junior, post, 
        brand8, brand6, brand4, BJT, experience, company, jobtile, attachname, attach);
    } catch (Exception e) {
      e.printStackTrace();
      return "E:系统错误!";
    }
    return res;
  }

  private String createrRecruitmentInfo(String xm, String xb, String csny, String hj, String zgxl, String ksgzsj, String lxdh, String email, String CPA, String CTA, String ACCA, String zjkjs, String cjkjs, String kjsgz, String yybj, String yylj, String yysj, String ryyj, String sf, String gsmc, String zw, String jlfjname, String jlfj)
    throws Exception
  {
    RecordSet rs = new RecordSet();
    SysNoForSelf sns = new SysNoForSelf();
    InsertUtil iu = new InsertUtil();
    String recruitmentid = "";
    String modeid = "182";
    String sql = "";
    String sysNo = sns.getNum("RCZP", "uf_recruitment", 4);
    String docid = "";
    if (jlfj.length() > 0) {
      docid = getDocId(jlfjname, jlfj, "1");
    }
    Map mapStr = new HashMap();
    mapStr.put("bh", sysNo);
    mapStr.put("xm", xm.replace("'", "''"));
    if (("0".equals(xb)) || ("1".equals(xb))) {
      mapStr.put("xb", xb);
    }
    mapStr.put("csny", csny.replace("'", "''"));
    mapStr.put("hj", hj.replace("'", "''"));
    mapStr.put("zgxl", zgxl.replace("'", "''"));
    mapStr.put("ksgzsj", ksgzsj.replace("'", "''"));
    mapStr.put("lxdh", lxdh.replace("'", "''"));
    mapStr.put("email", email.replace("'", "''"));
    if (("0".equals(CPA)) || ("1".equals(CPA))) {
      mapStr.put("CPA", CPA);
    }
    if (("0".equals(CTA)) || ("1".equals(CTA))) {
      mapStr.put("CTA", CTA);
    }
    if (("0".equals(ACCA)) || ("1".equals(ACCA))) {
      mapStr.put("ACCA", ACCA);
    }
    if (("0".equals(zjkjs)) || ("1".equals(zjkjs))) {
      mapStr.put("zjkjs", zjkjs);
    }
    if (("0".equals(cjkjs)) || ("1".equals(zjkjs))) {
      mapStr.put("cjkjs", cjkjs);
    }
    if (("0".equals(kjsgz)) || ("1".equals(kjsgz))) {
      mapStr.put("kjsgz", kjsgz);
    }
    if (("0".equals(yybj)) || ("1".equals(yybj))) {
      mapStr.put("yybj", yybj);
    }
    if (("0".equals(yylj)) || ("1".equals(yylj))) {
      mapStr.put("yylj", yylj);
    }
    if (("0".equals(yysj)) || ("1".equals(yysj))) {
      mapStr.put("yysj", yysj);
    }
    if (("0".equals(ryyj)) || ("1".equals(ryyj))) {
      mapStr.put("ryyj", ryyj);
    }
    if (("0".equals(sf)) || ("1".equals(sf))) {
      mapStr.put("sf", sf);
    }
    mapStr.put("gsmc", gsmc.replace("'", "''"));
    mapStr.put("zw", zw.replace("'", "''"));

    mapStr.put("jlfj", docid);
    mapStr.put("modedatacreater", "1");
    mapStr.put("modedatacreatertype", "0");
    mapStr.put("formmodeid", modeid);
    boolean isRun = iu.insert(mapStr, "uf_recruitment");
    if (!(isRun)) {
      return "E:信息保存错误！";
    }

    sql = "select * from uf_recruitment where bh='" + sysNo + "'";
    rs.executeSql(sql);

    if (rs.next()) {
      recruitmentid = Util.null2String(rs.getString("id"));
    }

    ModeRightInfo ModeRightInfo = new ModeRightInfo();
    ModeRightInfo.editModeDataShare(Integer.valueOf("1").intValue(), Integer.valueOf(modeid).intValue(), 
      Integer.valueOf(recruitmentid).intValue());
    return "S";
  }

  private String getDocId(String name, String value, String createrid) throws Exception {
    String docId = "";
    DocInfo di = new DocInfo();
    di.setMaincategory(0);
    di.setSubcategory(0);
    di.setSeccategory(165);
    di.setDocSubject(name.substring(0, name.lastIndexOf(".")));
    DocAttachment doca = new DocAttachment();
    doca.setFilename(name);
    byte[] buffer = new BASE64Decoder().decodeBuffer(value);
    String encode = Base64.encode(buffer);
    doca.setFilecontent(encode);
    DocAttachment[] docs = new DocAttachment[1];
    docs[0] = doca;
    di.setAttachments(docs);
    String departmentId = "-1";
    String sql = "select departmentid from hrmresource where id=" + createrid;
    RecordSet rs = new RecordSet();
    rs.executeSql(sql);
    User user = new User();
    if (rs.next()) {
      departmentId = Util.null2String(rs.getString("departmentid"));
    }
    user.setUid(Integer.parseInt(createrid));
    user.setUserDepartment(Integer.parseInt(departmentId));
    user.setLanguage(7);
    user.setLogintype("1");
    user.setLoginip("127.0.0.1");
    DocServiceImpl ds = new DocServiceImpl();
    try {
      docId = String.valueOf(ds.createDocByUser(di, user));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return docId;
  }
}