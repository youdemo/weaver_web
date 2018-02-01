package seahonor.action.purchsse;

import weaver.interfaces.schedule.BaseCronJob;

public class AutoCreatePurchaseforMonth extends BaseCronJob {
   public String createForMonth(){
	   AutoCreatePurchase acp= new AutoCreatePurchase();
	   String result ="";
	   try {
		   result =acp.AutoCreateRequest("0","",""); 
	   } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	   }
	   return result;
   }
   
   public void execute() {  
	  this.createForMonth();
   }
}
