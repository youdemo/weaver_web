package tmc.org;

public class HrmJobTitleBean {

	// ��λ����
	private String jobtitlecode;
	// ��λȫ��
	private String jobtitlemark;
	// ��λ���
	private String jobtitlename;
	// ��λ����
	private String jobtitleremark;
	// ְλ����   �����ж� �ޱ���
	private String jobactivityName;
	// ְλ��   �����ж� �ޱ���
	private String jobGroupName;
	// ��λ��������  0ͨ��ID  1��ͨ������
	private int deptIdOrCode;
	// ��λ��������ID
	private String jobdepartmentid;
	// ��λ��������Code
	private String jobdepartmentCode;
	// �ϼ���λCode
	private String superJobCode;


	public String getJobactivityName() {
		return jobactivityName;
	}

	public void setJobactivityName(String jobactivityName) {
		this.jobactivityName = jobactivityName;
	}

	public String getJobGroupName() {
		return jobGroupName;
	}



	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}

	public String getSuperJobCode() {
		return superJobCode;
	}

	public void setSuperJobCode(String superJobCode) {
		this.superJobCode = superJobCode;
	}
	
	public String getJobtitlecode() {
		return jobtitlecode;
	}

	public void setJobtitlecode(String jobtitlecode) {
		this.jobtitlecode = jobtitlecode;
	}

	public String getJobtitlemark() {
		return jobtitlemark;
	}

	public void setJobtitlemark(String jobtitlemark) {
		this.jobtitlemark = jobtitlemark;
	}

	public String getJobtitlename() {
		return jobtitlename;
	}

	public void setJobtitlename(String jobtitlename) {
		this.jobtitlename = jobtitlename;
	}

	public String getJobtitleremark() {
		return jobtitleremark;
	}

	public void setJobtitleremark(String jobtitleremark) {
		this.jobtitleremark = jobtitleremark;
	}

	public int getDeptIdOrCode() {
		return deptIdOrCode;
	}

	public void setDeptIdOrCode(int deptIdOrCode) {
		this.deptIdOrCode = deptIdOrCode;
	}

	public String getJobdepartmentid() {
		return jobdepartmentid;
	}

	public void setJobdepartmentid(String jobdepartmentid) {
		this.jobdepartmentid = jobdepartmentid;
	}

	public String getJobdepartmentCode() {
		return jobdepartmentCode;
	}

	public void setJobdepartmentCode(String jobdepartmentCode) {
		this.jobdepartmentCode = jobdepartmentCode;
	}
}
