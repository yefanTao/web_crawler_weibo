package input;

public class create_hottrace_table extends input_general {
	
	
	public static boolean createhottracetable(String name)
	{
		String sql = "create table " + name +" ("
		+"date char(64),"
		+"hot int"
		+");";
		input_general.execute(sql);
		return true;
	}
	
	
}
