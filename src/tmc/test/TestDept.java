package tmc.test;

public class TestDept {

	public static void main(String[] args) {
		tmc.org.HrmDepartmentBean hdb = new tmc.org.HrmDepartmentBean();
		hdb.setDepartmentcode("");
		hdb.setDepartmentname("");
		hdb.setDepartmentark("");
		hdb.setComIdOrCode(0);
		hdb.setSubcompanyid1("");
		hdb.setSubcompanyCode("");
		hdb.setIdOrCode(0);
		hdb.setSuperID("");
		hdb.setSuperCode("");
		hdb.setOrderBy(0);
		hdb.setStatus(0);
		hdb.addCusMap("t1", "12312s");
		hdb.addCusMap("t2", "ads1231");
	}
}
