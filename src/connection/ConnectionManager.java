package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author Administrator
 */
public class ConnectionManager {
	private Connection con = null;

	private final boolean autoCommit = true;
	
	public ConnectionManager() {

	}

	/**
	 * 获得数据库连接
	 */
	public Connection getConnection(String database) {
		return getConnection("localhost", database);
	}

	/**
	 * 获得数据库连接
	 */
	public Connection getConnection(String server, String database) {
		if (con != null) {
			return con;
		}
		try {

			String url = "jdbc:mysql://" + server + ":3306/" + database
					+ "?characterEncoding=utf8";
			String usr = "root";
			String pwd = "root";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, usr, pwd);
			con.setAutoCommit(autoCommit);
			// logger.error("this is a test.");
		} catch (SQLException ex) {

		} catch (InstantiationException ex) {

		} catch (IllegalAccessException ex) {

		} catch (ClassNotFoundException ex) {

		}
		return con;
	}

	/**
	 * 关闭数据库连接
	 */
	public void close() {
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				
			} finally {
				con = null;
			}
		}
	}

	public static void main(String[] args) {
		ConnectionManager conn = new ConnectionManager();
		Connection c = conn.getConnection("weibo");
		System.out.println(c);
		ConnectionManager manager = null; // 数据库连接管理器。
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO `stock`(`name`, `date`, `val`) VALUES ('chenxi',2013,1),('cdaf',2014,2),('adb',2015,3)";
		manager = new ConnectionManager();
		try {
			pstmt = manager.getConnection("weibo").prepareStatement(sql);

			pstmt.execute();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
				manager.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}

	}
}
