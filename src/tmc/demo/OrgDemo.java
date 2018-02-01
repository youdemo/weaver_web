package tmc.demo;

import tmc.org.HrmDepartmentBean;
import tmc.org.HrmJobTitleBean;
import tmc.org.HrmOrgAction;
import tmc.org.HrmResourceBean;
import tmc.org.HrmSubCompanyBean;
import tmc.org.ReturnInfo;

public class OrgDemo {
	
	public static void main(String[] args) {
		// ִ����  
		HrmOrgAction  hoa = new HrmOrgAction();  
		
		/**
		 	�����ֲ�ͬ��
		 */
		// �ֲ���
		HrmSubCompanyBean hsb = new HrmSubCompanyBean();
		hsb.setSubCompanyCode("003");
		hsb.setSubCompanyName("com1");
		hsb.setSubCompanyDesc("com1");
		// �ϼ��Ĳ�����ʽ     0 ��ͨ��id��ȡ  1��ͨ��code��ȡ
		hsb.setIdOrCode(0);
		hsb.setSuperID("");
		hsb.setSuperCode("");
		//�����ֶ�
		hsb.setOrderBy(0);
		// ״̬    0����  1��װ
		hsb.setStatus(0);
		// �Զ�������    ����:tt1��tt2���Զ�����ֶ���     TEst1��TEst2 ���Զ����ֶε�ֵ       HrmSubcompanyDefined��¼
		hsb.addCusMap("tt1", "TEst1");
		hsb.addCusMap("tt2", "TEst2");
		// ִ�н��  ����ֱ�Ӵ�ӡresult �鿴ֱ�ӽ��   
		ReturnInfo result = hoa.operSubCompany(hsb);
		// 
		if(result.isTure()){
			System.out.println("ִ�гɹ����������ݣ�"+result.getRemark());
		}else{
			System.out.println("ִ��ʧ�ܣ�ʧ����ϸ��"+result.getRemark());
		}
		
		/**
	 		��������ͬ��
		 */
		// ������
		HrmDepartmentBean hdb = new HrmDepartmentBean();
		hdb.setDepartmentcode("10013");
		hdb.setDepartmentname("testDept1");
		hdb.setDepartmentark("deptTest21");
		// �ֲ��Ļ�ȡ������ʽ     0 ��ͨ��id��ȡ  1��ͨ��code��ȡ
		hdb.setComIdOrCode(1);
		hdb.setSubcompanyid1("0");
		hdb.setSubcompanyCode("001");
		// �ϼ��Ĳ�����ʽ     0 ��ͨ��id��ȡ  1��ͨ��code��ȡ
		hdb.setIdOrCode(1);
		hdb.setSuperID("0");
		hdb.setSuperCode("10012");
		//�����ֶ�
		hdb.setOrderBy(0);
		// ״̬    0����  1��װ
		hdb.setStatus(0);
		// �Զ�������    ����:t1��t2���Զ�����ֶ���     123��456 ���Զ����ֶε�ֵ       HrmDepartmentDefined��¼
		hdb.addCusMap("t1", "123");
		hdb.addCusMap("t2", "456");
		// ִ�н��  ����ֱ�Ӵ�ӡresult �鿴ֱ�ӽ��   
		result = hoa.operDept(hdb);
		if(result.isTure()){
			System.out.println("ִ�гɹ����������ݣ�"+result.getRemark());			
		}else{
			System.out.println("ִ��ʧ�ܣ�ʧ����ϸ��"+result.getRemark());
		}
		
		/**
 			������λͬ��
		 */		
		// ��λ��
		HrmJobTitleBean hjt = new HrmJobTitleBean();
		hjt.setJobtitlecode("80001020");
		hjt.setJobtitlename("�����������");
		hjt.setJobtitlemark("�����������");
		hjt.setJobtitleremark("�����������");
		// ��������  0 ��ͨ��id��ȡ  1��ͨ��code��ȡ
		hjt.setDeptIdOrCode(1);
		hjt.setJobdepartmentid("");
		hjt.setJobdepartmentCode("10013");
		hjt.setSuperJobCode("");
		// ְλ ֱ��ͨ���ֶ�ȥ��ѯ��û�о���ӣ��о�ֱ�ӻ�ȡ
		hjt.setJobactivityName("������Ա");
		// ְλ ֱ��ͨ���ֶ�ȥ��ѯ��û�о���ӣ��о�ֱ�ӻ�ȡ
		hjt.setJobGroupName("����������");
		// ִ�н��  ����ֱ�Ӵ�ӡresult �鿴ֱ�ӽ��   
		result = hoa.operJobtitle(hjt);
		if(result.isTure()){
			System.out.println("ִ�гɹ����������ݣ�"+result.getRemark());			
		}else{
			System.out.println("ִ��ʧ�ܣ�ʧ����ϸ��"+result.getRemark());
		}
		
		/**
			������Աͬ��
		 */	
		// ��Ա��Ϣ��
		HrmResourceBean hrb = new HrmResourceBean();
		hrb.setWorkcode("5003");
		hrb.setLoginid("5003");
		hrb.setLastname("������");
		hrb.setPassword("1234");
		// �����ֲ�   ��������Ӧ�ķֲ�   ʡ��
		// ��������  0 ��ͨ��id��ȡ  1��ͨ��code��ȡ   
		hrb.setDeptIdOrCode(1);
		hrb.setDepartmentid("");
		hrb.setDepartmentCode("10012");
		// ������λ  0 ��ͨ��id��ȡ  1��ͨ��code��ȡ     
		hrb.setJobIdOrCode(1);
		hrb.setJobtitle("");
		hrb.setJobtitleCode("80001021");
		// �ϼ��쵼  0 ��ͨ��id��ȡ  1��ͨ��code��ȡ      2��ͨ����λ��ȡ
		hrb.setManagerIdOrCode(1);
		hrb.setManagerid("");
		hrb.setManagerCode("5002");
		hrb.setSeclevel(10);
		
		/**
		 *  ��Ȼ�����»��� ����������  ��HrmResourceBean�У�����setֵ
		 */
		
		// ִ�н��  ����ֱ�Ӵ�ӡresult �鿴ֱ�ӽ��   
		result = hoa.operResource(hrb);
		if(result.isTure()){
			System.out.println("ִ�гɹ����������ݣ�"+result.getRemark());			
		}else{
			System.out.println("ִ��ʧ�ܣ�ʧ����ϸ��"+result.getRemark());
		}
	}
}
