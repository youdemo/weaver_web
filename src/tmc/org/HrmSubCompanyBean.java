package tmc.org;

import java.util.HashMap;
import java.util.Map;

public class HrmSubCompanyBean {
	
	// �ֲ�����  ���ֱ�ʾ
	private String subCompanyCode;
	// �ֲ����
	private String subCompanyName;
	// �ֲ�ȫ��
	private String subCompanyDesc;
	// �ϼ���ϵ      0ͨ��ID  1��ͨ������
	private int idOrCode;
	// �ϼ�id   ���û���ϼ�����Ϊ0 
	private String superID;
	// �ϼ�Ψһ����   ���û���ϼ�����Ϊ�� 
	private String superCode;
	// ״̬   1 ���   0����
	private int status = 0;
	// ����
	private int orderBy = 0;
	// �Զ����ֶ�  
	private Map<String,String> cusMap;
	
	public int getIdOrCode() {
		return idOrCode;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getSubCompanyCode() {
		return subCompanyCode;
	}
	
	public void setSubCompanyCode(String subCompanyCode) {
		this.subCompanyCode = subCompanyCode;
	}
	
	public String getSubCompanyName() {
		return subCompanyName;
	}
	
	public void setSubCompanyName(String subCompanyName) {
		this.subCompanyName = subCompanyName;
	}
	public String getSubCompanyDesc() {
		return subCompanyDesc;
	}
	
	public void setSubCompanyDesc(String subCompanyDesc) {
		this.subCompanyDesc = subCompanyDesc;
	}
	
	public String getSuperID() {
		return superID;
	}
	
	public void setSuperID(String superID) {
		this.superID = superID;
	}
	
	public int getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}
	public Map<String, String> getCusMap() {
		return cusMap;
	}
	
	public void setCusMap(Map<String, String> cusMap) {
		this.cusMap = cusMap;
	}	
	
	public void addCusMap(String key,String value){
		if(cusMap == null)  cusMap = new HashMap<String, String>();
		cusMap.put(key,value);
	}
}
