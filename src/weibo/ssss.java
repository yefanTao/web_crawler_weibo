package weibo;
import input.read_weibo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ssss {

	public static void main(String[] args) throws SQLException
	{
		String name = "中国石化";
		ResultSet rs = read_weibo.get(name);
		System.out.println("here");
		int i=2;
		while(rs.next())
		{
			if(rs.getString("wid").equals("AlzMdD92I"))
				System.out.println(i);
			i++;
		}
		
	
		
		
	}
	
}
