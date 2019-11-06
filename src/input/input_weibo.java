package input;

import java.util.ArrayList;

import model.weibo;

public class input_weibo extends input_general {
	private static String table = "weibo";

	public static boolean add(ArrayList<weibo> tweets) {
		String sql = "INSERT INTO " + table
				+ "(wid, uid, content,zan,zf,pl,date,isoriginal,"
				+ "pre_uid,pre_wid) VALUES";
		for (int i = 0; i < tweets.size(); i++) {
			sql += "('" + tweets.get(i).wid + "','" + tweets.get(i).uid + "','" +
					tweets.get(i).content +"'," + tweets.get(i).zan + "," +
					tweets.get(i).zf + "," + tweets.get(i).pl + ",'" + 
					tweets.get(i).date + "'," + tweets.get(i).isoriginal + ",'" +
					tweets.get(i).pre_uid + "','" + tweets.get(i).pre_wid + "'),";

		}
		sql = sql.substring(0, sql.length() - 1);
		return execute(sql); 
	}
}
