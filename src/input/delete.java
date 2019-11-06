package input;

import java.util.ArrayList;

import model.weibo;

public class delete extends input_general {
	private static String table = "weibo";
	
		public static void delete(String last_date){
		String sql = "DELETE FROM " + table
				+ "WHERE ";

		sql = sql.substring(0, sql.length() - 1);
		return execute(sql); 
	}
	
}
