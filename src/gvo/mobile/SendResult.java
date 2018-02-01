package gvo.mobile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import weaver.general.BaseBean;

public class SendResult {

	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM:dd HH:mm:ss.SSS");
		System.out.println(format.format(new Date()));
	}

	public boolean sendSMS(String smsId, String number, String msg) {
		BaseBean log = new BaseBean();
		String url = "http://3tong.net/http/sms/Submit";
		String xml = "";
		String account = "dh21000";
        String password = "dh21000.com";
        String msgid = smsId;
        String phones = number;
        String content = msg;
        String subcode = "";
        String sendtime = "";
        String sign = "";
		
        SendResult sr = new SendResult();
        xml = sr.DocXml(account, password, msgid, phones, content, subcode, sendtime, sign);
        
        String desc = sr.post(url, xml);
        log.writeLog("result = " + desc);
		return desc.contains("提交成功");
	}
	
	public String post(String url, String xml) {  
        DefaultHttpClient httpclient = new DefaultHttpClient();  
        String body = null;  
          
        HttpPost post = postForm(url,xml);  
          
        body = invoke(httpclient, post);  
          
        httpclient.getConnectionManager().shutdown();  
          
        return body;  
    }
	
	private String invoke(DefaultHttpClient httpclient,  HttpUriRequest httpost) {  
          
        HttpResponse response = sendRequest(httpclient, httpost);  
        String body = paseResponse(response);  
        String result = "";
        
		try {
			byte[] b = body.getBytes("ISO-8859-1");
			result = new String(b,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
        return result;  
    }  
  
    private String paseResponse(HttpResponse response) {  
        HttpEntity entity = response.getEntity();  
          
    //    String charset = EntityUtils.getContentCharSet(entity);  

        String body = null;  
        try {  
            body = EntityUtils.toString(entity);  
         
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        return body;  
    }  
  
    private HttpResponse sendRequest(DefaultHttpClient httpclient,  
            HttpUriRequest httpost) {   
        HttpResponse response = null;  
          
        try {  
            response = httpclient.execute(httpost);  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return response;  
    }  
  
    private HttpPost postForm(String url, String xml){  
        HttpPost httpost = new HttpPost(url);  
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
          
        nvps.add(new BasicNameValuePair("message", xml)); 
        try {  
         
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
          
        return httpost;  
    }  
    
    public String DocXml(String account, String password, String msgid, 
    			String phones, String content, String subcode, String sendtime, String sign){

    	StringBuffer buff = new StringBuffer();
    	buff.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
    	buff.append("<response>");
    	buff.append("<account>");buff.append(account);buff.append("</account>");
    	buff.append("<password>");buff.append(getMD5Str(password));buff.append("</password>");
    	buff.append("<msgid>");buff.append(msgid);buff.append("</msgid>");
    	buff.append("<phones>");buff.append(phones);buff.append("</phones>");
    	buff.append("<content>");buff.append(content);buff.append("</content>");
    	buff.append("<subcode>");buff.append(subcode);buff.append("</subcode>");
    	buff.append("<sendtime>");buff.append(sendtime);buff.append("</sendtime>");
    	buff.append("<sign>");buff.append(sign);buff.append("</sign>");
    	buff.append("</response>");
        return  buff.toString();
    }
    
    public String DocXmlHtml(String account, String password, String msgid, 
			String phones, String content, String subcode, String sendtime, String sign){

		StringBuffer buff = new StringBuffer();
		buff.append("<?xml version=\"1.0\" encoding=\"utf-8\"?></br>");
		buff.append("&nbsp;<response></br>");
		buff.append("&nbsp;&nbsp;<account>");buff.append(account);buff.append("</account></br>");
		buff.append("&nbsp;&nbsp;<password>");buff.append(getMD5Str(password));buff.append("</password></br>");
		buff.append("&nbsp;&nbsp;<msgid>");buff.append(msgid);buff.append("</msgid></br>");
		buff.append("&nbsp;&nbsp;<phones>");buff.append(phones);buff.append("</phones></br>");
		buff.append("&nbsp;&nbsp;<content>");buff.append(content);buff.append("</content></br>");
		buff.append("&nbsp;&nbsp;<subcode>");buff.append(subcode);buff.append("</subcode></br>");
		buff.append("&nbsp;&nbsp;<sendtime>");buff.append(sendtime);buff.append("</sendtime></br>");
		buff.append("&nbsp;&nbsp;<sign>");buff.append(sign);buff.append("</sign></br>");
		buff.append("</response></br>");
	    return  buff.toString();
    }
    
    public String getMD5Str(String str){
    	StringBuffer buf = new StringBuffer("");
    	byte sy[];
		MessageDigest digest;
		try {
			sy = str.getBytes("UTF-8");
			digest = MessageDigest.getInstance("MD5");
		
    //	String resultString = byteToString(digest.digest(sy));
    	
	    	digest.update(sy);
	        byte b[] = digest.digest();
	
	        int i;
	        
	        for (int offset = 0; offset < b.length; offset++) {
	            i = b[offset];
	            if (i < 0)
	                i += 256;
	            if (i < 16)
	                buf.append("0");
	            buf.append(Integer.toHexString(i));
	        }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	
    	return buf.toString().toLowerCase().replace("-", "");
    }
}
