package input;

import java.util.ArrayList;

import model.zan;

public class input_zan extends input_general {
	private static String table = "zan";
	
	public static boolean add(ArrayList<zan> zz) {
		String sql = "INSERT INTO " + table
				+ "(wid, uid, zan_i,zan,zan_uid) VALUES";
		for (int i = 0; i < zz.size(); i++) {
			sql += "('" + zz.get(i).wid + "','" + zz.get(i).uid + "'," +
					zz.get(i).zan_i +"," + zz.get(i).zan + ",'" +
					zz.get(i).zan_uid + "'),";

		}
		sql = sql.substring(0, sql.length() - 1);
		return execute(sql); 
	}
}
