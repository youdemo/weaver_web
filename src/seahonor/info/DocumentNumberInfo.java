package seahonor.info;

import weaver.conn.RecordSet;
import weaver.general.Util;

public class DocumentNumberInfo {
	public int getDocumentNumber(String ids){
		String tmp_all[] = ids.split(",");
		return tmp_all.length;
	}
}
