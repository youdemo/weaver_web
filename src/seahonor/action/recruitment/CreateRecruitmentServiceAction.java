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

public class CreateRecruitmentServiceAction extends BaseBean{
	/**
	 * 
	 * @param xm   姓名
	 * @param xb   性别  男--0 女---1
	 * @param csny  出生年月  格式 2017-01-01
	 * @param hj   户籍
	 * @param zgxl  最高学历
	 * @param ksgzsj  开始工作时间
	 * @param lxdh  联系电话
	 * @param email E-mail 
	 * @param CPA   CPA   有--1，没有--0
	 * @param CTA   CTA   有--1，没有--0
	 * @param ACCA  ACCA  有--1，没有--0
	 * @param zjkjs  中级会计师    有--1，没有--0
	 * @param cjkjs  初级会计师    有--1，没有--0
	 * @param kjsgz 会计上岗证    有--1，没有--0
	 * @param yybj 英语八级    有--1，没有--0
	 * @param yylj 英语六级    有--1，没有--0
	 * @param yysj 英语四级   有--1，没有--0
	 * @param ryyj 日语一级   有--1，没有--0
	 * @param sf   是否有工作经历  是--0 否--1
	 * @param gsmc 公司名称
	 * @param zw  职务
	 * @param jlfjname  简历附件name
	 * @param jlfj  简历附件
	 * @return
	 */
    public String doService(String xm,String xb,String csny,String hj,String zgxl,
    		String ksgzsj,String lxdh,String email,String CPA,String CTA,String ACCA,
    		String zjkjs,String cjkjs,String kjsgz,String yybj,String yylj,String yysj,
    		String ryyj,String sf,String gsmc,String zw,String jlfjname,String jlfj){
    	try {
			createrRecruitmentInfo(xm,xb,csny,hj,zgxl,ksgzsj,lxdh,email,CPA,CTA,ACCA,
					zjkjs,cjkjs,kjsgz,yybj,yylj,yysj,ryyj,sf,gsmc,zw,jlfjname,jlfj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "E";
		}
    	
    	return "S";
    }
    private boolean createrRecruitmentInfo(String xm,String xb,String csny,String hj,String zgxl,
    		String ksgzsj,String lxdh,String email,String CPA,String CTA,String ACCA,
    		String zjkjs,String cjkjs,String kjsgz,String yybj,String yylj,String yysj,
    		String ryyj,String sf,String gsmc,String zw,String jlfjname,String jlfj) throws Exception{
    	RecordSet rs = new RecordSet();
    	SysNoForSelf sns = new SysNoForSelf();
    	InsertUtil iu = new InsertUtil();
    	String recruitmentid="";
    	String modeid="170";
    	String sql="";
    	String sysNo = sns.getNum("RCZP", "uf_recruitment", 4);
    	String docid="";
    	if(jlfj.length()>0){
    	 docid=getDocId(jlfjname,jlfj,"1");
    	}
    	Map<String, String> mapStr = new HashMap<String, String>();
    	mapStr.put("bh", sysNo);
    	mapStr.put("xm", xm);
    	mapStr.put("xb", xb);
    	mapStr.put("csny", csny);
    	mapStr.put("hj", hj);
    	mapStr.put("zgxl", zgxl);
    	mapStr.put("ksgzsj", ksgzsj);
    	mapStr.put("lxdh", lxdh);
    	mapStr.put("email", email);
    	mapStr.put("CPA", CPA);
    	mapStr.put("CTA", CTA);
    	mapStr.put("ACCA", ACCA);
    	mapStr.put("zjkjs", zjkjs);
    	mapStr.put("cjkjs", cjkjs);
    	mapStr.put("kjsgz", kjsgz);
    	mapStr.put("yybj", yybj);
    	mapStr.put("yylj", yylj);
    	mapStr.put("yysj", yysj);
    	mapStr.put("ryyj", ryyj);
    	mapStr.put("sf", sf);
    	mapStr.put("gsmc", gsmc);
    	mapStr.put("zw", zw);
    	
    	mapStr.put("jlfj", docid);
		mapStr.put("modedatacreater", "1");
		mapStr.put("modedatacreatertype", "0");
		mapStr.put("formmodeid", modeid);
		iu.insert(mapStr, "uf_recruitment");
    	
    	
    	sql = "select * from uf_recruitment where bh='" + sysNo + "'";
		rs.executeSql(sql);

		if (rs.next()) {
			recruitmentid = Util.null2String(rs.getString("id"));
		}
		ModeRightInfo ModeRightInfo = new ModeRightInfo();
		ModeRightInfo.editModeDataShare(Integer.valueOf("1"), Integer.valueOf(modeid),
				Integer.valueOf(recruitmentid));
    	return true;
    	
    }
    private String getDocId(String name, String value,String createrid) throws Exception {
		String docId = "";
		DocInfo di= new DocInfo();
		di.setMaincategory(0);
		di.setSubcategory(0);
		di.setSeccategory(165);	
		di.setDocSubject(name.substring(0, name.lastIndexOf(".")));	
		DocAttachment doca = new DocAttachment();
		doca.setFilename(name);
		byte[] buffer = new BASE64Decoder().decodeBuffer(value);
		String encode=Base64.encode(buffer);
		doca.setFilecontent(encode);
		DocAttachment[] docs= new DocAttachment[1];
		docs[0]=doca;
		di.setAttachments(docs);
		String departmentId="-1";
		String sql="select departmentid from hrmresource where id="+createrid;
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		User user = new User();
		if(rs.next()){
			departmentId = Util.null2String(rs.getString("departmentid"));
		}	
		user.setUid(Integer.parseInt(createrid));
		user.setUserDepartment(Integer.parseInt(departmentId));
		user.setLanguage(7);
		user.setLogintype("1");
		user.setLoginip("127.0.0.1");
		DocServiceImpl ds = new DocServiceImpl();
		try {
			docId=String.valueOf(ds.createDocByUser(di, user));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		if(docId !=null&&docId.length() > 0){
//			// 设置成套红模板文档
//			rs.executeSql("update docimagefile set docfiletype=3 where docid="+docId);
//		}
		return docId;
	}
}
