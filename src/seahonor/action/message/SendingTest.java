package seahonor.action.message;

import java.io.IOException;
import org.apache.commons.httpclient.HttpException; 
import org.apache.commons.httpclient.HttpClient; 
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager; 
import org.apache.commons.httpclient.methods.PostMethod;
 

public class SendingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SendingTest t=new SendingTest();
		
		String mobile="15261175783";    
		String content="2017-05-04 16:00 �㽫������飺aaa,�뼰ʱ�μӣ�лл�������� ����Ա";

		//������Ϣ
		String urlstr="http://api.esms100.com:8080/mt/?cpid=3869&cppwd=24BE3D9C6D7A75485D59EC2556D2F261&phone="
			            +mobile+"&msgtext="+java.net.URLEncoder.encode(content);
		String str=t.doGetRequest(urlstr);
		System.out.println("������Ӧֵ:"+str);  
		 mobile="18913268242";    
		 content="2017-05-04 16:00 �㽫������飺aaa,�뼰ʱ�μӣ�лл�������� ����Ա";

		//������Ϣ
	 urlstr="http://api.esms100.com:8080/mt/?cpid=3869&cppwd=24BE3D9C6D7A75485D59EC2556D2F261&phone="
			            +mobile+"&msgtext="+java.net.URLEncoder.encode(content);
	 str=t.doGetRequest(urlstr);
		System.out.println("������Ӧֵ:"+str);  
		mobile="15618790371";    
		 content="2017-05-04 16:00 �㽫������飺aaa,�뼰ʱ�μӣ�лл�������� ����Ա";

		//������Ϣ
	 urlstr="http://api.esms100.com:8080/mt/?cpid=3869&cppwd=24BE3D9C6D7A75485D59EC2556D2F261&phone="
			            +mobile+"&msgtext="+java.net.URLEncoder.encode(content);
	 str=t.doGetRequest(urlstr);
		System.out.println("������Ӧֵ:"+str);  
	  
		
	}
	
	public String doGetRequest(String urlstr) {
		String res = null;
		HttpClient client = new HttpClient(
				new MultiThreadedHttpConnectionManager());
		client.getParams().setIntParameter("http.socket.timeout", 10000);
		client.getParams().setIntParameter("http.connection.timeout", 5000);

		HttpMethod httpmethod = new PostMethod(urlstr);
		try {
			int statusCode = client.executeMethod(httpmethod);
			if (statusCode == HttpStatus.SC_OK) {
				res = httpmethod.getResponseBodyAsString();
			}
		} catch (HttpException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		} finally {
			httpmethod.releaseConnection();
		}
		return res;
	} 
}
