package util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;



public class Tool {


	/**
	 * 返回当前日期
	 * 
	 * @return
	 */
	private static Calendar calendar = Calendar.getInstance();

	public static String getYear() {
		return calendar.get(Calendar.YEAR) + "";
	}

	public static String getDate() {

		return getYear() + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DATE);
	}

	/**
	 * 返回当前时间
	 * 
	 * @return
	 */
	public static String getTime() {
		return calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ calendar.get(Calendar.MINUTE) + ":"
				+ calendar.get(Calendar.SECOND);
	}

	/**
	 * 日期的加减：在startDate加上offset�?
	 * 
	 * @param startDate
	 * @param offset
	 * @return
	 */
	public static String add(String startDate, int offset) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			calendar.setTime(sdf.parse(startDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.DATE, offset);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 格式化整数串，如把�?123,123”格式化成�?123123�?
	 * 
	 * @param num
	 * @return
	 */
	public static int numberFormat(String num) throws ParseException {
		NumberFormat nf1 = NumberFormat.getInstance();
		int r = nf1.parse(num).intValue();

		return r;
	}
}
