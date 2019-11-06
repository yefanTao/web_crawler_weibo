package input;

import model.stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class input_stock extends input_general {
	private static String table = "stock";
	
	public static Vector<stock> getAll(){
		Vector<stock> stocks = new Vector<stock>();
		String sql = "SELECT id,name,date,val FROM weibo." + table;
		ResultSet rs = executeQuery(sql);
		try{
			while(rs.next()){
				stock stock = new stock();
				stock.id = rs.getInt("id");
				stock.name =rs.getString("name");
				stock.date = rs.getString("date");
				stocks.add(stock);
			}
			rs.close();	
		}catch (SQLException e){
			System.out.println("error in input_stock");
		}
		return stocks;
	}
}
