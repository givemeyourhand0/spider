package transformUrlToFileName;

import java.text.DateFormat;
import java.util.Date;

public class TransStringToDate
{
	/**
	 * 将字符串形式表示的日期转换成Date类型以方便数据库存储
	 * @param dt
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static java.sql.Date transStringToDate(String dt)
	{
		if(dt==null||dt.isEmpty())
			return null;
		
		String[] result = dt.split("(年|月|日|/|-)");
//		GregorianCalendar date = null;
		if(result.length > 2)
		{			
			if(result[2].startsWith("0"))
			{
				result[2] = result[2].replaceFirst("0", "").trim();
			}
			
//			date = new GregorianCalendar();
		}
		return new java.sql.Date(Integer.parseInt(result[0])-1900,Integer.parseInt(result[1]) - 1,Integer.parseInt(result[2].trim()));
	}
	
	/**
	 * 统一按照YYYY-MM-DD的形式显示日期
	 * @param dt
	 * @return
	 */
	public static String uniformDateFormat(String dt)
	{
		Date date = transStringToDate(dt);
		return DateFormat.getDateInstance().format(date);
	}
	
	public static void main(String[] args)
	{
		System.out.println(TransStringToDate.uniformDateFormat("2018-09-01"));
	}
}