package tmc.test;

public class TestCom {
	public static void main(String[] args) {
		tmc.org.HrmOrgAction  hoa = new tmc.org.HrmOrgAction();  
		tmc.org.HrmSubCompanyBean hsb = new tmc.org.HrmSubCompanyBean();
		hsb.setSubCompanyCode("001");
		hsb.setSubCompanyName("testName");
		hsb.setSubCompanyDesc("NameTest");
		hsb.setIdOrCode(1);
		hsb.setSuperCode("001");
		hsb.setOrderBy(0);
		hsb.setStatus(0);
		hsb.addCusMap("tt1", "shsTest");
		hoa.operSubCompany(hsb);
	}
}
