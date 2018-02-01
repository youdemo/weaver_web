package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import com.sap.tc.logging.RemoveLogEvent;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int aa[]={1,2,3,4};
	    char[] aa1=new char[10];
	    int test[]=new int[100];
	    for(int i=0;i<test.length;i++){
	    	test[i]=i+1;
	    }
	    int cc[] = new int[50];
	    cc[0]=1;
	    cc[1]=1;
	    for(int i=2;i<cc.length;i++){
	    	cc[i]=cc[i-1]+cc[i-2];
	    }
	   // System.out.println(Arrays.toString(cc));
	    

	    int aac[]={1,3,54,1,23,41,3213,41};
	    for(int i=0;i<aac.length;i++){
	    	
	    	for(int j=i+1;j<aac.length;j++){
	    		if(aac[j]<aac[i]){
	    			int temp=aac[i];
	    			aac[i]=aac[j];
	    			aac[j]=temp;
	    		}
	    	}
	    }
		//System.out.println(Arrays.toString(aac));
		
		List<String> aa122 = new ArrayList<String>();
		aa122.add("123");
		aa122.add("222");
		aa122.add("2223");
		aa122.add("222");
		ListIterator a=aa122.listIterator();
		while(a.hasNext()){
			String a1=(String)a.next();

		}
		for(int i=0;i<aa122.size();i++){
		//System.out.println(aa122.get(i));
		}
		List<String> test2 = new ArrayList<String>();
		test2.add("1");
		test2.add("2");
		test2.add("3");
		test2.add("4");
		test2.add("5");
		test2.add("6");
		test2.add("7");
		test2.add("8");
		test2.add("9");
		
		//test2.set(test2.indexOf("8"), "10");
		
//		for(int i=0;i<test2.size();i++){
//			if("8".equals(test2.get(i))){
//				test2.set(i, "10");
//			}
//		}
		
		ListIterator  li = test2.listIterator();
		while(li.hasNext()){
			if("8".equals(li.next())){
				li.set("10");
			}
		}
		for(int i=0;i<test2.size();i++){
			System.out.println(test2.get(i));
	}
		

	}
	

}
