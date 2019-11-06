package input;

public class create_pl extends input_general {

	public static boolean createtable(String name)
	{
		String sql = "create table " + name +" ("
				+"wid char(64),"
				+"uid char(64),"
				+"pl_i smallint(6),"
				+"pl smallint(6),"
				+"pl_uid char(64),"
				+"pl_context char(255)"
				+");";
				input_general.execute(sql);
				return true;
	}
}
