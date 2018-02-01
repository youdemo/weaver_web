package seahonor.action.assets;

import jxl.Cell;
import jxl.Sheet;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

public class ExcelImport {
	public String readAndExceSheet(Sheet sheet) {
		BaseBean log = new BaseBean();
		log.writeLog("Ω¯»ÎExcel");
		RecordSet rs = new RecordSet();
		int rows = sheet.getRows();
		for (int i = 1; i < rows; i++) {
			Cell[] cells = sheet.getRow(i);
			
			String idx = getStr(cells[0].getContents());
			String goodscate = getStr(cells[1].getContents());
			String assets = getStr(cells[2].getContents());
			String goodsno = getStr(cells[3].getContents());
			String actual = getNumStr(cells[4].getContents());
			String remark = getStr(cells[5].getContents());
			

			String sql = "update uf_checkrecord_dt1 set actual = " + actual
					+ ",remark = '" + remark + "' where id = " + idx + "";
			rs.executeSql(sql);
			System.out.println("sql = " + sql);
			log.writeLog("sql = " + sql);
		}
		return "-1";
	}

	private String getStr(String str) {
		if (str == null)
			return "";
		return str.trim();
	}

	private String getNumStr(String str) {
		if (str == null)
			return "0";
		return str.trim();
	}
}
