package tmc.org;

import java.util.HashMap;
import java.util.Map;

public class HrmDepartmentBean {
	
	// ���ű��
	public String departmentcode;
	// ���ż��
	public String departmentname;
	// ����ȫ��
	public String departmentark;
	// �ϼ���ϵ      0ͨ��ID  1��ͨ������
	private int idOrCode;
	// �ϼ�id   ���û���ϼ�����Ϊ0 
	private String superID;
	// �ϼ�Ψһ����   ���û���ϼ�����Ϊ�� 
	private String superCode;
	// ������˾  0ͨ��ID  1ͨ������
	public int comIdOrCode;
	// ������˾ID
	public String subcompanyid1;
	// ������˾����
	public String subcompanyCode;
	// ״̬   0����  1���
	public int status = 0;
	// ���� 
	public int orderBy = 0;
	// �Զ����ֶ�     ����ֶ����ƺ��ֶ�ֵ
	private Map<String,String> cusMap;
	
	public Map<String,String> getCusMap(){
		return cusMap;
	}
	
	public void addCusMap(String key,String value){
		if(cusMap == null)
			cusMap = new HashMap<String,String>();
		cusMap.put(key, value);
	}
	
	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setCusMap(Map<String, String> cusMap) {
		this.cusMap = cusMap;
	}

	public String getSuperID() {
		return superID;
	}

	public void setSuperID(String superID) {
		this.superID = superID;
	}

	public int getComIdOrCode() {
		return comIdOrCode;
	}

	public void setComIdOrCode(int comIdOrCode) {
		this.comIdOrCode = comIdOrCode;
	}

	public String getSubcompanyCode() {
		return subcompanyCode;
	}

	public void setSubcompanyCode(String subcompanyCode) {
		this.subcompanyCode = subcompanyCode;
	}

	public int getIdOrCode() {
		return idOrCode;
	}

	public void setIdOrCode(int idOrCode) {
		this.idOrCode = idOrCode;
	}

	public String getSuperCode() {
		return superCode;
	}

	public void setSuperCode(String superCode) {
		this.superCode = superCode;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getDepartmentark() {
		return departmentark;
	}

	public void setDepartmentark(String departmentark) {
		this.departmentark = departmentark;
	}
	
	public String getDepartmentcode() {
		return departmentcode;
	}

	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}
	
	public String getSubcompanyid1() {
		return subcompanyid1;
	}

	public void setSubcompanyid1(String subcompanyid1) {
		this.subcompanyid1 = subcompanyid1;
	}

}
