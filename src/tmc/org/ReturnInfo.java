package tmc.org;

public class ReturnInfo {

	// �Ƿ���ȷ  Ĭ������ȷ��
	private boolean isTure = true;
	// ����
	private String code = "0";
	// ��Ϣ����
	private String remark = "";

	public void setMessage(String code,String remark) {
		this.code = code;
		this.remark = remark;
	}
	
	public void setMessage(boolean isTure,String code,String remark) {
		this.isTure = isTure;
		this.code = code;
		this.remark = remark;
	}
	
	public boolean isTure() {
		return isTure;
	}

	public void setTure(boolean isTure) {
		this.isTure = isTure;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString(){
		return "���ִ�� " + (isTure?"�ɹ�!":"ʧ��!") + " ������룺" + getCode() + ",��ϸ:" + getRemark();
	}
	
	
}
