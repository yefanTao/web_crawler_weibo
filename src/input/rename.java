package input;

public class rename extends input_general {
	
	public static boolean re_name(String pre_name, String new_name)
	{
		String sql = "alter table " + pre_name + " rename to " + new_name;
		input_general.execute(sql);
		return true;
	}
	
}
