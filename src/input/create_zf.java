package input;

public class create_zf extends input_general {

	public static boolean createtable(String name)
	{
		String sql = "create table " + name +" ("
				+"wid char(64),"
				+"uid char(64),"
				+"zf_i smallint(6),"
				+"zf smallint(6),"
				+"zf_uid char(64),"
				+"zf_context char(255)"
				+");";
				input_general.execute(sql);
				return true;
	}
}
