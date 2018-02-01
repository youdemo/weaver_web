package seahonor.action.custom.wcc;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.encoding.Base64;

import seahonor.util.InsertUtil;
import seahonor.util.SysNoForSelf;
import sun.misc.BASE64Decoder;
import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.docs.webservices.DocAttachment;
import weaver.docs.webservices.DocInfo;
import weaver.docs.webservices.DocServiceImpl;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.StaticObj;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.interfaces.datasource.DataSource;

public class GetWCCImplAction {
	BaseBean log = new BaseBean();

	/**
	 * ��ȡWCC��ϵ����Ϣ
	 */
	public void GetWCCContactDataInfo() {
		log.writeLog("��ʼɨ����Ƭ");
		String Guid = "";
		List<WCCContactObject> list = new ArrayList<WCCContactObject>();
		RecordSetDataSource rsd = new RecordSetDataSource("WCC");
		String sql = "select distinct b.* from WCCContactImage a ,WCCContactaid b where a.ContactGuid=b.Guid and a.IsDelete=0 and b.Name is not null and b.Name <>'' ";
		rsd.executeSql(sql);
		while (rsd.next()) {
			WCCContactObject wco = new WCCContactObject();
			Guid = Util.null2String(rsd.getString("Guid"));
			if (hasCreate(Guid)) {
				continue;
			}
			wco.setGuid(Guid);
			wco.setIsCJKName(Util.null2String(rsd.getString("IsCJKName")));
			wco.setName(Util.null2String(rsd.getString("Name")));
			wco.setCompany(Util.null2String(rsd.getString("Company")));
			wco.setPhone(Util.null2String(rsd.getString("Phone")));
			wco.setEmail(Util.null2String(rsd.getString("Email")));
			wco.setDepartment(Util.null2String(rsd.getString("Department")));
			wco.setJob(Util.null2String(rsd.getString("Job")));
			wco.setAddress(Util.null2String(rsd.getString("Address")));
			wco.setFax(Util.null2String(rsd.getString("Fax")));
			wco.setCellPhone(Util.null2String(rsd.getString("CellPhone")));
			wco.setUrl(Util.null2String(rsd.getString("Url")));
			log.writeLog("wccContactCreate:Guid:" + wco.getGuid()
					+ " IsCJKName:" + wco.getIsCJKName() + " Name:"
					+ wco.getName() + " Company:" + wco.getCompany()
					+ " Phone:" + wco.getPhone() + " Email:" + wco.getEmail()
					+ " Department:" + wco.getDepartment() + " Job:"
					+ wco.getJob() + " Address:" + wco.getAddress() + " Fax:"
					+ wco.getFax() + " CellPhone:" + wco.getCellPhone()
					+ " Url:" + wco.getUrl());
			list.add(wco);

		}
		log.writeLog("��ʼɨ����Ƭ length"+list.size());
		for (WCCContactObject wco : list) {
			String customId = getCustomId(wco);
			wco.setCustomId(customId);
			String[] docid = getDocIds(wco);
			String zps = "";
			String flag = "";
			for (int i = 0; i < docid.length; i++) {
				if (i == 0)
					wco.setPichead(docid[i]);
				if (i == 1)
					wco.setPicEnd(docid[i]);
				if (!"".equals(docid[i])) {
					zps = zps + flag + docid[i];
					flag = ",";
				}
			}
			wco.setZps(zps);
			log.writeLog(wco.getName());
			createContact(wco);

		}

	}

	/**
	 * �ж���ϵ���Ƿ񴴽���
	 * 
	 * @param guid
	 * @return
	 */
	private boolean hasCreate(String guid) {
		int count = 0;
		RecordSet rs = new RecordSet();
		String sql = "select COUNT(1) as count from uf_contacts where guid='"
				+ guid + "'";
		rs.executeSql(sql);
		if (rs.next()) {
			count = rs.getInt("count");
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ��ȡ��˾ID
	 * 
	 * @return
	 */
	private String getCustomId(WCCContactObject wco) {
		RecordSet rs = new RecordSet();
		String customId = "";
		String customName = wco.getCompany();
		if ("".equals(customName)) {
			return "";
		}
		String sql = "select id from uf_custom where customName='" + customName
				+ "' and SuperID is null and CutomStatus in (0,1,2)";
		rs.executeSql(sql);
		if (rs.next()) {
			customId = Util.null2String(rs.getString("id"));
		} else {
			customId = createCustom(wco);
		}
		return customId;

	}

	/**
	 * ������˾
	 * 
	 * @param wco
	 * @return
	 */
	private String createCustom(WCCContactObject wco) {
		RecordSet rs = new RecordSet();
		SysNoForSelf sns = new SysNoForSelf();
		InsertUtil iu = new InsertUtil();
		String table_name_1 = "uf_custom";
		String customId = "";
		String seqNO = "";
		String date = "";
		String time = "";
		String nowTime = "";
		String xq = "";
		String sql = "select MAX(isnull(seqNO,0))+1 as seqNO from UF_CUSTOM  where seqNO !=100000";
		rs.executeSql(sql);
		if (rs.next()) {
			seqNO = Util.null2String(rs.getString("seqNO"));
		}
		sql = "select convert(char(10),GetDate(),120) as Date,convert(char(5),getdate(),108) time,dbo.whatDay(convert(char(10),GetDate(),120)) xq,CONVERT(varchar(30),getdate(),21) as nowTime";
		rs.executeSql(sql);
		if (rs.next()) {
			date = Util.null2String(rs.getString("Date"));
			time = Util.null2String(rs.getString("time"));
			xq = Util.null2String(rs.getString("xq"));
			nowTime = Util.null2String(rs.getString("nowTime"));
		}
		String sysNo = sns.getNum("CTL", table_name_1, 4);
		Map<String, String> mapStr = new HashMap<String, String>();
		mapStr.put("guid", wco.getGuid());
		mapStr.put("seqNO", seqNO);// ��˾����
		mapStr.put("customCode", sysNo);// ��˾����
		mapStr.put("cjr", "1");// ������
		mapStr.put("cjrq", date);// ��������
		mapStr.put("cjsj", time);// ����ʱ��
		mapStr.put("cjxq", xq);// ��������
		mapStr.put("sqr", "1");// ������
		mapStr.put("sqrq", date);// ��������
		mapStr.put("sqsj", time);// ����ʱ��
		mapStr.put("sqxq", xq);// ��������
		mapStr.put("customName", wco.getCompany());// ��˾����(����)
		if ("0".equals(wco.getIsCJKName())) {
			mapStr.put("gswwm", wco.getCompany());
		}
		mapStr.put("Address", wco.getAddress());
		mapStr.put("Website", wco.Url);
		mapStr.put("CutomStatus", "0");// ��˾״̬
		mapStr.put("Version", "0");// ��Ϣ�汾
		mapStr.put("ModifyTime", nowTime);
		mapStr.put("modedatacreater", "1");
		mapStr.put("modedatacreatertype", "0");
		mapStr.put("formmodeid", "52");
		iu.insert(mapStr, table_name_1);
		sql = "select id from uf_custom where customCode='" + sysNo + "'";
		rs.executeSql(sql);
		log.writeLog("��ʼ����ͻ�1" + sql);
		if (rs.next()) {
			customId = Util.null2String(rs.getString("id"));
		}
		ModeRightInfo ModeRightInfo = new ModeRightInfo();
		ModeRightInfo.editModeDataShare(Integer.valueOf("1"), 52,
				Integer.valueOf(customId));

		return customId;

	}

	/**
	 * ��ȡ��Ƭ���id
	 * 
	 * @param wco
	 * @return
	 */
	private String[] getDocIds(WCCContactObject wco) {
		RecordSetDataSource rsd = new RecordSetDataSource("WCC");
		RecordSetDataSource rsd_dt = new RecordSetDataSource("WCC");
		DataSource ds = (DataSource) StaticObj.getServiceByFullname(
				("datasource.WCC"), DataSource.class);
		SysNoForSelf sns = new SysNoForSelf();
		String sql_dt="";
		int count = 0;
		String RawData = "";
		String sql = "select COUNT(1) as count from WCCContactImage where ContactGuid='"
				+ wco.getGuid() + "'";
		rsd.executeSql(sql);
		if (rsd.next()) {
			count = rsd.getInt("count");
		}
		if (count <= 0) {
			return null;
		}
		String[] docIds = new String[count];
		sql="select * from WCCContactImage where ContactGuid='"+wco.getGuid()+"' order by ImageType asc";
		rsd.executeSql(sql);
		int i=0;
		while(rsd.next()){
			String ContactImageGuid = Util.null2String(rsd.getString("Guid"));
			Connection conn = null;
			
			conn = ds.getConnection();
			ResultSet rs;
			String docname=wco.getName()+sns.getNum("ZP", "WCC_ZP", 5)+".jpg";
			String docid="";
			try {
				String uploadBuffer ="";
				rs = conn.createStatement().executeQuery(
						"select rawdata from WCCContactImageRawData  where ContactImageGuid='"+ContactImageGuid+"' and subtype=2");
				InputStream aa = null;
				if(rs.next()){
					aa= rs.getBinaryStream(1);
				}
				if(aa !=null){
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				 byte[] buffer = new byte[1024];
				 int count1 = 0;
				 while((count1 = aa.read(buffer)) >= 0){
				 baos.write(buffer, 0, count1);
				 }
				 uploadBuffer = new String(Base64.encode(baos.toByteArray()));
				}
				docid = getDocId( docname,uploadBuffer,"1");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.writeLog("ɨ�������ͼƬʧ��:name��" + wco.getName());
			}
			if (!"".equals(docid)) {
				docIds[i] = docid;
				i++;
			}
		}
		
		return docIds;
	}

	/**
	 * �����ĵ�
	 * 
	 * @param rawdata
	 * @param name
	 * @return
	 */
//	private String createDocId(String rawdata, String name) {
//		String docName = name + ".jpg";
//		byte[] data = fromHexString(rawdata.replace("0x", ""));
//		String uploadBuffer = new String(Base64.encode(data));
//		String docId = "";
//		try {
//			docId = getDocId(docName, uploadBuffer, "1");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.writeLog("ɨ�������ͼƬʧ��:name��" + name);
//		}
//		return docId;
//	}

	/**
	 * 16����ת2��������
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] fromHexString(String s) {
		int length = s.length() / 2;
		byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++) {
			bytes[i] = (byte) ((Character.digit(s.charAt(i * 2), 16) << 4) | Character
					.digit(s.charAt((i * 2) + 1), 16));
		}
		return bytes;
	}

	/**
	 * �����ĵ��ӿ�
	 * 
	 * @param name
	 * @param value
	 * @param createrid
	 * @return
	 * @throws Exception
	 */
	private String getDocId(String name, String value, String createrid)
			throws Exception {
		String docId = "";
		DocInfo di = new DocInfo();
		di.setMaincategory(0);
		di.setSubcategory(0);
		di.setSeccategory(9);
		di.setDocSubject(name.substring(0, name.lastIndexOf(".")));
		DocAttachment doca = new DocAttachment();
		doca.setFilename(name);
		//byte[] data = fromHexString(value);
		byte[] buffer = new BASE64Decoder().decodeBuffer(value);
		String encode = Base64.encode(buffer);
		doca.setFilecontent(encode);
		DocAttachment[] docs = new DocAttachment[1];
		docs[0] = doca;
		di.setAttachments(docs);
		String departmentId = "-1";

		User user = new User();
		user.setUid(Integer.parseInt(createrid));
		user.setUserDepartment(Integer.parseInt(departmentId));
		user.setLanguage(7);
		user.setLogintype("1");
		user.setLoginip("127.0.0.1");
		DocServiceImpl ds = new DocServiceImpl();
		try {
			docId = String.valueOf(ds.createDocByUser(di, user));
			docShare(docId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// if(docId !=null&&docId.length() > 0){
		// // ���ó��׺�ģ���ĵ�
		// rs.executeSql("update docimagefile set docfiletype=3 where docid="+docId);
		// }
		return docId;
	}

	/**
	 * ������ϵ��
	 * 
	 * @param wco
	 */
	private void createContact(WCCContactObject wco) {
		SysNoForSelf sns = new SysNoForSelf();
		InsertUtil iu = new InsertUtil();
		RecordSet rs = new RecordSet();
		String table_name_1 = "uf_Contacts";
		String contactid = "";
		String date = "";
		String time = "";
		String nowTime = "";
		String xq = "";
		String sql = "select convert(char(10),GetDate(),120) as Date,convert(char(5),getdate(),108) time,dbo.whatDay(convert(char(10),GetDate(),120)) xq,CONVERT(varchar(30),getdate(),21) as nowTime";
		rs.executeSql(sql);
		if (rs.next()) {
			date = Util.null2String(rs.getString("Date"));
			time = Util.null2String(rs.getString("time"));
			xq = Util.null2String(rs.getString("xq"));
			nowTime = Util.null2String(rs.getString("nowTime"));
		}
		String sysNo = sns.getNum("CCDL", table_name_1, 4);
		Map<String, String> mapStr = new HashMap<String, String>();
		mapStr.put("guid", wco.getGuid());
		mapStr.put("zps", wco.getZps());
		mapStr.put("SysNo", sysNo);// ��ϵ�˴���
		mapStr.put("cjr", "1");// ������
		mapStr.put("cjrq", date);// ��������
		mapStr.put("cjsj", time);// ����ʱ��
		mapStr.put("cjxq", xq);// ��������
		mapStr.put("sqr", "1");// ������
		mapStr.put("sqrq", date);// ��������
		mapStr.put("sqsj", time);// ����ʱ��
		mapStr.put("sqxq", xq);// ��������
		mapStr.put("Name", wco.getName());
		if ("0".equals(wco.getIsCJKName())) {
			mapStr.put("wwm", wco.getName());
		}
		mapStr.put("customName", wco.getCustomId());// ������˾
		mapStr.put("lxrcm", "1");
		mapStr.put("dept", wco.getDepartment());// ����(����)
		mapStr.put("Position", wco.getJob());// ְλ(����)
		mapStr.put("tel", wco.getPhone());// �칫�绰
		mapStr.put("mobile", wco.getCellPhone());// �ƶ��绰
		mapStr.put("Email", wco.getEmail());// ��������
		mapStr.put("status", "0");// ��ְ״̬
		mapStr.put("Fax", wco.getFax());// ������
		mapStr.put("bgdz", wco.Address);// �칫��ַ(����)
		mapStr.put("picHead", wco.getPichead());// ��Ƭ����
		mapStr.put("picEnd", wco.getPicEnd());// ��Ƭ����
		mapStr.put("version", "0");// ��Ϣ�汾
		mapStr.put("dealStatus", "0");// ��Ϣ״̬
		mapStr.put("modedatacreater", "1");
		mapStr.put("modedatacreatertype", "0");
		mapStr.put("formmodeid", "56");
		iu.insert(mapStr, table_name_1);
		sql = "select * from uf_Contacts where SysNo='" + sysNo + "'";
		log.writeLog("��ʼ������ϵ��1" + sql);
		rs.executeSql(sql);

		if (rs.next()) {
			contactid = Util.null2String(rs.getString("id"));
		}
		ModeRightInfo ModeRightInfo = new ModeRightInfo();
		ModeRightInfo.editModeDataShare(Integer.valueOf("1"), 56,
				Integer.valueOf(contactid));

	}
	
	private void docShare(String docid){
		RecordSet rs = new RecordSet();
		String sql="insert into docshare(docid,sharetype,seclevel,rolelevel,sharelevel,roleid,foralluser,downloadlevel,seclevelmax) values("+docid+",5,10,0,1,0,1,1,100)";
		rs.executeSql(sql);
		sql="insert into shareinnerdoc(sourceid,type,content,seclevel,sharelevel,srcfrom,opuser,sharesource,downloadlevel,seclevelmax) values("+docid+",5,1,10,1,80,0,0,1,100)";
		rs.executeSql(sql);
	}

}
