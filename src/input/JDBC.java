package input;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC {
	public static void main(String[] args){
		String server = "localhost";
		String database = "weibo";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://" + server + ":3306/" + database
				+ "?characterEncoding=utf8";
		String user = "root";
		String pwd = "root";
		
		try{
			Class.forName(driver).newInstance();
			Connection conn = DriverManager.getConnection(url,user,pwd);
			
			if(!conn.isClosed())
				System.out.println("成功连接到数据库");
			
			Statement statement = conn.createStatement();
			//String sql = "select * from stock";
			//ResultSet rs = statement.executeQuery(sql);
			String sql = "insert into 'stock' ('name','date') values ("+"chenxi,"+"2014/12/6"+")";
			ResultSet rs = statement.executeQuery(sql);
			
            System.out.println("-----------------");
            System.out.println("执行结果如下所示:");
            System.out.println("-----------------");
            System.out.println(" kid" + "\t" + " NAME"+"\t"+"release_date");
            System.out.println("-----------------");
			
            String name = null;
            String date = null;
			/*while(rs.next()){
				//name = rs.getString("NAME");
				//date = rs.getString("release_date");
				//name = new String(name.getBytes("ISO-8859-1"),"GB2312");
				 System.out.println(rs.getString("name") /*+ "\t" + name +"    " + date);
			}
			 rs.close();*/
	         conn.close();
		}catch(SQLException e) {
            e.printStackTrace();
           } catch(Exception e) {
            e.printStackTrace();
           } 
	}
}
