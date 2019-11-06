package input;

public class create_zan extends input_general{

	public static boolean createtable(String name)
	{
		String sql = "create table " + name +" ("
		+"wid char(64),"
		+"uid char(64),"
		+"zan_i smallint(6),"
		+"zan smallint(6),"
		+"zan_uid char(64)"
		+");";
		input_general.execute(sql);
		return true;
	}
}
