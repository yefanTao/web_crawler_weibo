package input;

import java.util.ArrayList;

import model.pl;

public class input_pl extends input_general {
	private static String table = "pl";
	
	public static boolean add(ArrayList<pl> zz) {
		String sql = "INSERT INTO " + table
				+ "(wid, uid, pl_i,pl,pl_uid,pl_context) VALUES";
		for (int i = 0; i < zz.size(); i++) {
			sql += "('" + zz.get(i).wid + "','" + zz.get(i).uid + "'," +
					zz.get(i).pl_i +"," + zz.get(i).pl + ",'" +
					zz.get(i).pl_uid + "','"+zz.get(i).pl_context+"'),";

		}
		sql = sql.substring(0, sql.length() - 1);
		return execute(sql); 
	}
}
