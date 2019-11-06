package input;

import java.util.ArrayList;

import model.zf;

public class input_zf extends input_general {
	private static String table = "zf";
	
	public static boolean add(ArrayList<zf> zz) {
		String sql = "INSERT INTO " + table
				+ "(wid, uid, zf_i,zf,zf_uid,zf_context) VALUES";
		for (int i = 0; i < zz.size(); i++) {
			sql += "('" + zz.get(i).wid + "','" + zz.get(i).uid + "'," +
					zz.get(i).zf_i +"," + zz.get(i).zf + ",'" +
					zz.get(i).zf_uid + "','"+zz.get(i).zf_context+"'),";

		}
		sql = sql.substring(0, sql.length() - 1);
		return execute(sql); 
	}
}
