package input;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionManager;


public class input_general {

	public static ConnectionManager connectionManager = new ConnectionManager();
	protected final static String LOCALHOSTSERVER = "localhost";
	protected final static String CUR_SERVER = LOCALHOSTSERVER;
	protected final static String DATABASE_NAME = "weibo";

	/**
	 * @param sql
	 * @return
	 */
	//只是执行
	public static boolean execute(String sql) {
		boolean result = false;
		PreparedStatement pstmt = null;
		try {
			pstmt = connectionManager.getConnection(CUR_SERVER, DATABASE_NAME)
					.prepareStatement(sql);
			result = pstmt.execute();
			System.out.println("connection to mysql.");
		} catch (SQLException e) {
			System.out.println("error in input_general");
		}
		return result;
	}

	/**
	 * 插入后，返回自增id
	 * 
	 * @param sql
	 * @return
	 */
	protected static int executeInsertReturningKey(String sql) {
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		int id = -1;
		try {
			pstmt = connectionManager.getConnection(CUR_SERVER, DATABASE_NAME)
					.prepareStatement(sql,
							PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			resultSet = pstmt.getGeneratedKeys();
			resultSet.next();
			id = resultSet.getInt(1);
		} catch (SQLException e) {
		}
		return id;
	}

	//返回结果
	protected static ResultSet executeQuery(String sql) {
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			pstmt = connectionManager.getConnection(CUR_SERVER, DATABASE_NAME)
					.prepareStatement(sql);
			resultSet = pstmt.executeQuery();
		} catch (SQLException e) {
		}
		return resultSet;
	}

	public input_general() {
		super();
	}

}