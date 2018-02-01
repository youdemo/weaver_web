package gvo.emc.dir;

import java.util.ArrayList;

import weaver.conn.RecordSet;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class Ha implements Action {
	public String execute(RequestInfo info) {
		FindDirectory aa=new FindDirectory();
		try {
			aa.test();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      return SUCCESS;
    }

}
