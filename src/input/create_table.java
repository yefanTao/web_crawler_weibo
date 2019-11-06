package input;

public class create_table extends input_general {
	
	
	public static boolean createtable(String name)
	{
		String sql = "create table " + name +" ("
		+"wid char(64),"
		+"uid char(64),"
		+"content char(255),"
		+"zan smallint(6),"
		+"zf smallint(6),"
		+"pl smallint(6),"
		+"date datetime,"
		+"isoriginal tinyint(4),"
		+"pre_uid char(64),"
		+"pre_wid char(64)"
		+");";
		input_general.execute(sql);
		return true;
	}
	
	
}
