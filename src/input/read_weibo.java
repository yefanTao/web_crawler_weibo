package input;

import java.sql.ResultSet;
import java.sql.SQLException;

public class read_weibo extends input_general {
	
	public static ResultSet get(String table) throws SQLException
	{
		String sql = "select wid,uid,zan,pl,zf from " + table
				+ " where zan>0;";
		 ResultSet rs = executeQuery(sql);
		 return rs;
		/* int i=0;
		 int j=0;
		 while(rs.next())
		 {
		 String value = rs.getString("uid");
		 int zan = rs.getInt("zan");
		 System.out.println(value);
		 j+=zan;
		 i++;
		 }
		 System.out.println("totally we have " + i + " records");
		 System.out.println("and we have " + j + " pls");
		 */
	}
	public static void main(String[] args) throws SQLException 
	{
		String sql = "select * from " + "кудЧтфил"
				+ " where zan>0;";
		ResultSet rs = executeQuery(sql);
		//rs.absolute(0);
		int i=0;
		while(rs.next())
		 {
		 String value = rs.getString("wid");
		 int zan = rs.getInt("zan");
		 i++;
		 //System.out.println(value+" "+zan);

		 }
		System.out.println(i);
		
	}
	

}
