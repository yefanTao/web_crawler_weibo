package input;

import model.hot;

public class input_hot extends input_general {
	private static String table = "hot";
	
	public static boolean input(hot ww)
	{
		String sql = "INSERT INTO " + table
				+ "(date, hot) VALUES";
			sql += "('" + ww.date + "','" + ww.hot + "')";
		return execute(sql); 
		
	}
	
}
