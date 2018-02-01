package seahonor.action.custom.wcc;

public class WCCContactObject {
	String Guid="";//关键id
	String IsCJKName="";//是否中文名 1是 0 不是
	String Name="";//姓名
	String Company="";//公司名
	String Phone="";//办公电话
	String Email="";//邮箱
	String Department="";//部门
	String Job="";//职务
	String Address="";//办公地址
	String Fax="";//传真
	String CellPhone="";//个人电话
	String Url="";//公司网站
	String customId="";//公司ID
	String pichead="";
	String picEnd="";
	String zps="";
	
	
	
	public String getZps() {
		return zps;
	}
	public void setZps(String zps) {
		this.zps = zps;
	}
	public String getPichead() {
		return pichead;
	}
	public void setPichead(String pichead) {
		this.pichead = pichead;
	}
	public String getPicEnd() {
		return picEnd;
	}
	public void setPicEnd(String picEnd) {
		this.picEnd = picEnd;
	}
	
	public String getGuid() {
		return Guid;
	}
	public void setGuid(String guid) {
		Guid = guid;
	}
	public String getIsCJKName() {
		return IsCJKName;
	}
	public void setIsCJKName(String isCJKName) {
		IsCJKName = isCJKName;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCustomId() {
		return customId;
	}
	public void setCustomId(String customId) {
		this.customId = customId;
	}
	public String getCompany() {
		return Company;
	}
	public void setCompany(String company) {
		Company = company;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	public String getJob() {
		return Job;
	}
	public void setJob(String job) {
		Job = job;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getFax() {
		return Fax;
	}
	public void setFax(String fax) {
		Fax = fax;
	}
	public String getCellPhone() {
		return CellPhone;
	}
	public void setCellPhone(String cellPhone) {
		CellPhone = cellPhone;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	
}
