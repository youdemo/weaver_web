package tmc.org;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tmc.util.TmcDBUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;

public class HrmResourceBean {
	
	// ����
	private String workcode;
	// ��¼��
	private String loginid;
	// ״̬  0������   1����ʽ    2����ʱ   3����������    4����Ƹ   5����ְ   6������    7����Ч
	private String status = "1";
	// ����
	private String lastname;
	// �Ա�   0 ����   1Ů��
	private String sex;
	// ��������  ��ʽ��2000-01-01
	private String birthday;
	// ��ȫ����
	private int seclevel;
	
	// ��λ��ϵ    0ͨ��ID  1��ͨ������  
	private int jobIdOrCode;
 	// ��Ա��λ  ID
	private String jobtitle;
 	// ��Ա��λ  Code
	private String jobtitleCode;
	
	// ��������    0ͨ��ID  1��ͨ������  
	private int deptIdOrCode;
	// ����  ID
	private String departmentid;
	// ����  Code
	private String departmentCode;

	// ֱ������   ����ͨ����λʶ�����ͨ�������idʶ��  -1��ͨ�������ʶ��  0ͨ��ID  1��ͨ������    2ͨ����λʶ��
	private int managerIdOrCode;
	// ֱ���ϼ� ID  
	private String managerid;
	// ֱ���ϼ�   ����ͨ����λʶ�����ͨ�������idʶ��
	private String managerCode;
	
	// �������ʺ�  ͨ��id��Codeʶ��   0ͨ��id  1ͨ������   -1�ǲ����á����˺š�
	private int belongIdOrCode = -1;
	// �������ʺ�  ��������ֵ�����Ǵ��˺�   �˺���Ҫ���
	private String belongto;
	// �������ʺ�  ��������ֵ�����Ǵ��˺�   �˺���Ҫ���
	private String belongtoCode;
	
	// �����˺�����     0 ���˺�  1���˺�
	private int accounttype = 0;
	
	// ���� ID     HrmCountry����  û��Ĭ��1   �й�     ������������ƥ��
	private String nationality = "1";
	// ϵͳ����  7Ϊ��������  ����ֶ�û��ֵ�ǻ�ֵ����ʱ��Ĭ��Ϊ7
	private String systemlanguage = "7";	
	// ��¼����   ϵͳ�Զ����ܻ�ȡ   ����ֵĬ������Ϊ1
	private String password;  
	// ����״�� ID   0 δ�� 1�ѻ�  2����
	private String maritalstatus;
	// �绰����
	private String telephone;
	// �ֻ�����
	private String mobile;
	// �����绰
	private String mobilecall;
	// ����
	private String email;
	// ��ʾ˳�� 
	private int dsporder = 0;
	// ������id  ϵͳ��ԱID  Ĭ��ϵͳ����Ա 1
	private String createrid = "1";
	// ��������   ��ʽ��2000-01-01
	private String createdate;
	// �����ص� ID   HrmLocations����  ͨ����������ֱ��ʶ��
	private String locationid = "1";
	// �칫��
	private String workroom;
	// ��ͥסַ
	private String homeaddress;
	// ��ͬ��ʼ����    ��ʽ��2000-01-01
	private String startdate;
	// ��ͬ��������    ��ʽ��2000-01-01   ע������ÿ�����ϻ��飬�����������Ѿ���������û��ᱻ�����Ч��
 	private String enddate;
 	
	// ���������ֶ�
	private String datefield1;
	private String datefield2;
	private String datefield3;
	private String datefield4;
	private String datefield5;
	
	// ���������ֶ�
	private String numberfield1;
	private String numberfield2;
	private String numberfield3;
	private String numberfield4;
	private String numberfield5;
	
	// �����ı��ֶ�
	private String textfield1;
	private String textfield2;
	private String textfield3;
	private String textfield4;
	private String textfield5;
	
	// �����ж��ֶ�
	private String tinyintfield1;
	private String tinyintfield2;
	private String tinyintfield3;
	private String tinyintfield4;
	private String tinyintfield5;
	
	// ְ������
	private String jobactivitydesc;
	// ���֤����
	private String certificatenum;
	// ����
	private String nativeplace;
	// ѧ�� ID   HrmEducationLevel  �������Խ�  ��Ϊ��
	private String educationlevel;
	// ����
	private String regresidentplace;
	// ����״�� ID   0���� 1����  2һ��  3�ϲ�
	private String healthinfo;
	// ��ס��
	private String residentplace;
	// ������ò
	private String policy;
	// ѧλ
	private String degree;
	// ���
	private String height;
	// ְ�� ID   HrmJobCall���У�  �ַ���ֱ�ӶԱ�
	private String jobcall = "0";
	// �������ʺ�
	private String accumfundaccount;
	// ������
	private String birthplace;
	// ����
	private String folk;
	// �ֻ�
	private String extphone;
	// ����
	private String fax;
	// ����
	private String weight;
	// ��ס֤����
	private String tempresidentnumber;
	// �����ڽ�������   ��ʽ��2000-01-01
	private String probationenddate;
	// ��������1
	private String bankid1;
	// �����ʺ�1
	private String accountid1;
	// 
	private String joblevel = "0";
	// 
	private String costcenterid = "1";
	// 
	private String assistantid = "0";
	
	// �Զ����ֶ�  
	private Map<String,String> cusMap;
	
	public Map<String,String> getCusMap(){
		return cusMap;
	}
	
	public int getAccounttype(){
		if(belongIdOrCode >= 0)
			accounttype = 1;
		return accounttype; 
	}
	
	
	
	public String getJoblevel() {
		return joblevel;
	}

	public void setJoblevel(String joblevel) {
		this.joblevel = joblevel;
	}

	public String getCostcenterid() {
		return costcenterid;
	}

	public void setCostcenterid(String costcenterid) {
		this.costcenterid = costcenterid;
	}

	public String getAssistantid() {
		return assistantid;
	}

	public void setAssistantid(String assistantid) {
		this.assistantid = assistantid;
	}

	public int getBelongIdOrCode() {
		return belongIdOrCode;
	}

	public void setBelongIdOrCode(int belongIdOrCode) {
		this.belongIdOrCode = belongIdOrCode;
	}

	public String getBelongtoCode() {
		return belongtoCode;
	}

	public void setBelongtoCode(String belongtoCode) {
		this.belongtoCode = belongtoCode;
	}

	public int getManagerIdOrCode() {
		return managerIdOrCode;
	}

	public void setManagerIdOrCode(int managerIdOrCode) {
		this.managerIdOrCode = managerIdOrCode;
	}

	public String getManagerCode() {
		return managerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	public void addCusMap(String key,String value){
		if(cusMap == null)
			cusMap = new HashMap<String,String>();
		cusMap.put(key, value);
	}
	
	public int getJobIdOrCode() {
		return jobIdOrCode;
	}

	public void setJobIdOrCode(int jobIdOrCode) {
		this.jobIdOrCode = jobIdOrCode;
	}

	public String getJobtitleCode() {
		return jobtitleCode;
	}

	public void setJobtitleCode(String jobtitleCode) {
		this.jobtitleCode = jobtitleCode;
	}

	public int getDeptIdOrCode() {
		return deptIdOrCode;
	}

	public void setDeptIdOrCode(int deptIdOrCode) {
		this.deptIdOrCode = deptIdOrCode;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}


	public int getDsporder() {
		return dsporder;
	}

	public void setDsporder(int dsporder) {
		this.dsporder = dsporder;
	}
	
	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getPassword() {
		if(password == null || password.length() < 1) 
			password = "1";
		
		return Util.getEncrypt(password);
	}
	
	public String getPassword1() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getSex() {
		return sex;
	}
	
	public String getSexID() {
		if("Ů".equals(sex)){
			return "1";
		}
		if("woman".equalsIgnoreCase(sex)){
			return "1";
		}
		if("female".equalsIgnoreCase(sex)){
			return "1";
		}
		if("1".equalsIgnoreCase(sex)){
			return "1";
		}
		return "0";
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNationality() {
		return nationality;
	}
	
	public String getNationalityID() {
		if("".equals(nationality)){
			return "1";
		}
		if("1".equals(nationality)||"2".equals(nationality))
			return nationality;
		if("�й�".equals(nationality)||"�л����񹲺͹�".equals(nationality))
			return "1";
		if("China".equalsIgnoreCase(nationality)||"Chinese".equalsIgnoreCase(nationality)){
			return "1";
		}
		RecordSet rs = new RecordSet();
		int nationalityID = 0;
		rs.executeSql("select  id from HrmCountry where countryname='"+nationality+"'");
		if(rs.next()){
			nationalityID = rs.getInt("id");
		}
		if(nationalityID < 1){
			String sql = "insert into HrmCountry(countryname,countrydesc) values('"+
					nationality+"','"+nationality+"')";
			rs.executeSql(sql);
			
			rs.executeSql("select  id from HrmCountry where countryname='"+nationality+"'");
			if(rs.next()){
				nationalityID = rs.getInt("id");
			}
		}
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getSystemlanguage() {
		if(systemlanguage == null){
			return "7";
		}
		if(!"8".equals(systemlanguage)&!"9".equals(systemlanguage)){
			return "7";
		}
		
		return systemlanguage;
	}

	public void setSystemlanguage(String systemlanguage) {
		this.systemlanguage = systemlanguage;
	}

	public String getMaritalstatus() {
		return maritalstatus;
	}

	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobilecall() {
		return mobilecall;
	}

	public void setMobilecall(String mobilecall) {
		this.mobilecall = mobilecall;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocationid() {
		RecordSet rs = new RecordSet();
		if(locationid == null || "".equals(locationid))
			return "1";
		String sql = "";
		int id = 0;
		// �����ֵ��ID�����ַ������ǿ��Ե�  ������������ͣ��Ͳ�ѯ�Ƿ�id����
		if(locationid.matches("^\\d+$")){
			sql = "select id from HrmLocations where id='"+locationid+"'";
			rs.executeSql(sql);
			if(rs.next()){
				id = rs.getInt("id");
			}
			if(id > 0)  return String.valueOf(id);
			return "1";
 		}
		sql = "select id from HrmLocations where locationname='"+locationid+"'";
		rs.executeSql(sql);
		if(rs.next()){
			id = rs.getInt("id");
		}
		if(id > 0) return String.valueOf(id);
		//  �������Ʋ����ڣ���Ҫ����
		Map<String,String> mapStr = new HashMap<String,String>();
		mapStr.put("locationname", locationid);
		mapStr.put("locationdesc", locationid);
		mapStr.put("locationcity", "1");   // ���а�id 1��ʾ����Ҫ��ϵͳ���ֶ�ȥά��
		mapStr.put("countryid", "1");      // ���Ұ�1 ���й������в�ͬ��Ҫ��ϵͳ�ֶ�ά��
		mapStr.put("showOrder", "0");      // ����Ϊ0����  ���в�ͬ��Ҫ��ϵͳ�ֶ�ά��
		
		TmcDBUtil tdu = new TmcDBUtil();
		tdu.insert(mapStr, "HrmLocations");
		
		sql = "select id from HrmLocations where locationname='"+locationid+"'";
		rs.executeSql(sql);
		if(rs.next()){
			id = rs.getInt("id");
		}
		if(id > 0) return String.valueOf(id);
		return "1";
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getWorkroom() {
		return workroom;
	}

	public void setWorkroom(String workroom) {
		this.workroom = workroom;
	}

	public String getHomeaddress() {
		return homeaddress;
	}

	public void setHomeaddress(String homeaddress) {
		this.homeaddress = homeaddress;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getJobtitle() {
		return jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	public String getJobactivitydesc() {
		return jobactivitydesc;
	}

	public void setJobactivitydesc(String jobactivitydesc) {
		this.jobactivitydesc = jobactivitydesc;
	}

	public int getSeclevel() {
		return seclevel;
	}

	public void setSeclevel(int seclevel) {
		this.seclevel = seclevel;
	}

	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public String getManagerid() {
		return managerid;
	}

	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}

	public String getBankid1() {
		return bankid1;
	}

	public void setBankid1(String bankid1) {
		this.bankid1 = bankid1;
	}

	public String getAccountid1() {
		return accountid1;
	}

	public void setAccountid1(String accountid1) {
		this.accountid1 = accountid1;
	}

	public String getCreaterid() {
		return createrid;
	}

	public void setCreaterid(String createrid) {
		this.createrid = createrid;
	}

	public String getCreatedate() {
		if(createdate == null || "".equals(createdate)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(new Date());
		}
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getDatefield1() {
		return datefield1;
	}

	public void setDatefield1(String datefield1) {
		this.datefield1 = datefield1;
	}

	public String getDatefield2() {
		return datefield2;
	}

	public void setDatefield2(String datefield2) {
		this.datefield2 = datefield2;
	}

	public String getDatefield3() {
		return datefield3;
	}

	public void setDatefield3(String datefield3) {
		this.datefield3 = datefield3;
	}

	public String getDatefield4() {
		return datefield4;
	}

	public void setDatefield4(String datefield4) {
		this.datefield4 = datefield4;
	}

	public String getDatefield5() {
		return datefield5;
	}

	public void setDatefield5(String datefield5) {
		this.datefield5 = datefield5;
	}

	public String getNumberfield1() {
		return numberfield1;
	}

	public void setNumberfield1(String numberfield1) {
		this.numberfield1 = numberfield1;
	}

	public String getNumberfield2() {
		return numberfield2;
	}

	public void setNumberfield2(String numberfield2) {
		this.numberfield2 = numberfield2;
	}

	public String getNumberfield3() {
		return numberfield3;
	}

	public void setNumberfield3(String numberfield3) {
		this.numberfield3 = numberfield3;
	}

	public String getNumberfield4() {
		return numberfield4;
	}

	public void setNumberfield4(String numberfield4) {
		this.numberfield4 = numberfield4;
	}

	public String getNumberfield5() {
		return numberfield5;
	}

	public void setNumberfield5(String numberfield5) {
		this.numberfield5 = numberfield5;
	}

	public String getTextfield1() {
		return textfield1;
	}

	public void setTextfield1(String textfield1) {
		this.textfield1 = textfield1;
	}

	public String getTextfield2() {
		return textfield2;
	}

	public void setTextfield2(String textfield2) {
		this.textfield2 = textfield2;
	}

	public String getTextfield3() {
		return textfield3;
	}

	public void setTextfield3(String textfield3) {
		this.textfield3 = textfield3;
	}

	public String getTextfield4() {
		return textfield4;
	}

	public void setTextfield4(String textfield4) {
		this.textfield4 = textfield4;
	}

	public String getTextfield5() {
		return textfield5;
	}

	public void setTextfield5(String textfield5) {
		this.textfield5 = textfield5;
	}

	public String getTinyintfield1() {
		return tinyintfield1;
	}

	public void setTinyintfield1(String tinyintfield1) {
		this.tinyintfield1 = tinyintfield1;
	}

	public String getTinyintfield2() {
		return tinyintfield2;
	}

	public void setTinyintfield2(String tinyintfield2) {
		this.tinyintfield2 = tinyintfield2;
	}

	public String getTinyintfield3() {
		return tinyintfield3;
	}

	public void setTinyintfield3(String tinyintfield3) {
		this.tinyintfield3 = tinyintfield3;
	}

	public String getTinyintfield4() {
		return tinyintfield4;
	}

	public void setTinyintfield4(String tinyintfield4) {
		this.tinyintfield4 = tinyintfield4;
	}

	public String getTinyintfield5() {
		return tinyintfield5;
	}

	public void setTinyintfield5(String tinyintfield5) {
		this.tinyintfield5 = tinyintfield5;
	}

	public String getCertificatenum() {
		return certificatenum;
	}

	public void setCertificatenum(String certificatenum) {
		this.certificatenum = certificatenum;
	}

	public String getNativeplace() {
		return nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	// ѧ��û�е� ������ ����Ҫ�ֶ�ά��   ֻ��ѯ
	public String getEducationlevel() {
		if(educationlevel == null ||"".equals(educationlevel))
			return "";
		RecordSet rs = new RecordSet();
		String sql = "";
		int id = 0;
		// �����ֵ��ID�����ַ������ǿ��Ե�  ������������ͣ��Ͳ�ѯ�Ƿ�id����
		if(educationlevel.matches("^\\d+$")){
			sql = "select id from HrmEducationLevel where id='"+educationlevel+"'";
			rs.executeSql(sql);
			if(rs.next()){
				id = rs.getInt("id");
			}
			if(id > 0)  return String.valueOf(id);
			return "";
 		}
		sql = "select id from HrmEducationLevel where name='"+educationlevel+"'";
		rs.executeSql(sql);
		if(rs.next()){
			id = rs.getInt("id");
		}
		if(id > 0)  return String.valueOf(id);
		return "";
	}

	public void setEducationlevel(String educationlevel) {
		this.educationlevel = educationlevel;
	}

	public String getWorkcode() {
		return workcode;
	}

	public void setWorkcode(String workcode) {
		this.workcode = workcode;
	}

	public String getRegresidentplace() {
		return regresidentplace;
	}

	public void setRegresidentplace(String regresidentplace) {
		this.regresidentplace = regresidentplace;
	}

	public String getHealthinfo() {
		// 0���� 1����  2һ��  3�ϲ�
		if("����".equals(healthinfo)||"0".equals(healthinfo)){
			return "0";
		}
		if("����".equals(healthinfo)||"1".equals(healthinfo)){
			return "1";
		}
		if("һ��".equals(healthinfo)||"2".equals(healthinfo)){
			return "2";
		}
		if("�ϲ�".equals(healthinfo)||"3".equals(healthinfo)){
			return "3";
		}
		return "";
	}

	public void setHealthinfo(String healthinfo) {
		this.healthinfo = healthinfo;
	}

	public String getResidentplace() {
		return residentplace;
	}

	public void setResidentplace(String residentplace) {
		this.residentplace = residentplace;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getJobcall() {
		if(jobcall == null || "".equals(jobcall)){
			return "0";
		}
		RecordSet rs = new RecordSet();
		String sql = "";
		int id = 0;
		// �����ֵ��ID�����ַ������ǿ��Ե�  ������������ͣ��Ͳ�ѯ�Ƿ�id����
		if(jobcall.matches("^\\d+$")){
			sql = "select id from HrmJobCall where id='"+jobcall+"'";
			rs.executeSql(sql);
			if(rs.next()){
				id = rs.getInt("id");
			}
			if(id > 0)  return String.valueOf(id);
			return "0";
 		}
		sql = "select id from HrmJobCall where name='"+jobcall+"'";
		rs.executeSql(sql);
		if(rs.next()){
			id = rs.getInt("id"); 
		}
		if(id > 0)  return String.valueOf(id);
		
		// û�е���Ҫ������
		Map<String,String> mapStr = new HashMap<String,String>();
		mapStr.put("name", jobcall);
		mapStr.put("description", jobcall);
		
		TmcDBUtil tdu = new TmcDBUtil();
		tdu.insert(mapStr, "HrmJobCall");
		
		sql = "select id from HrmJobCall where name='"+jobcall+"'";
		rs.executeSql(sql);
		if(rs.next()){
			id = rs.getInt("id"); 
		}
		if(id > 0)  return String.valueOf(id);
		
		return "0";
	}

	public void setJobcall(String jobcall) {
		this.jobcall = jobcall;
	}

	public String getAccumfundaccount() {
		return accumfundaccount;
	}

	public void setAccumfundaccount(String accumfundaccount) {
		this.accumfundaccount = accumfundaccount;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getFolk() {
		return folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public String getExtphone() {
		return extphone;
	}

	public void setExtphone(String extphone) {
		this.extphone = extphone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getTempresidentnumber() {
		return tempresidentnumber;
	}

	public void setTempresidentnumber(String tempresidentnumber) {
		this.tempresidentnumber = tempresidentnumber;
	}

	public String getProbationenddate() {
		return probationenddate;
	}

	public void setProbationenddate(String probationenddate) {
		this.probationenddate = probationenddate;
	}

	public String getBelongto() {
		return belongto;
	}

	public void setBelongto(String belongto) {
		this.belongto = belongto;
	}
	
}
