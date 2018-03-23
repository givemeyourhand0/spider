package SinaWeiboSpider;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 匹配微博的时间和来源
 * 时间：由于微博时间的显示一年之内和一年之外是有区别的，所以需要分别进行处理
 * 一年之内的显示方式为：xx月xx日 xx:xx
 * 一年以外的显示方式为：xxxx-xx-xx xx:xx:xx
 * 这里的时间是字符串形式的，在将时间存入到数据库中时，需要调用TransStringToDate类
 * 中的方法将其转换为时间类型再进行存储
 * 来源：通过关键字“来自”进行匹配，知道遇到空格时结束
 * 文本内容：通过文本内容前面的空格(\\s)和文本后面的  评论[xxx] 收藏  进行匹配得到微博
 * 的正文内容
 * @author Administrator
 */

public class Microblog_DateSourceContent
{
	private static String getDate(String htmlContent)
	{
		//([10-12]|(0[1-9]))月([10-31]|(0[1-9]))日((0[1-9])|(1[0-2]))
		Pattern pattern = Pattern.compile("(10|11|12|(0[1-9]))月((0[1-9])|([1-2][0-9])|(3[0-1]))日\\s?((0[1-9])|(1[0-9])|(2[0-3])):([0-5][0-9])");
		Matcher matcher = pattern.matcher(htmlContent);
		
		if(matcher.find())
		{
			return Calendar.getInstance().get(Calendar.YEAR) + "年" + matcher.group();
		}
		else
		{
			Pattern pattern1 = Pattern.compile("\\d{4}-((0[1-9])|(1[0-2]))-(([0-2][1-9])|(3[0-1]))\\s?(([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])");
			Matcher matcher1 = pattern1.matcher(htmlContent);
			
			if(matcher1.find())
			{
				new GregorianCalendar();
				return matcher1.group();
			}
			
			return null;
		}
	}
	
	private static String getSouce(String htmlContent)
	{
		Pattern pattern = Pattern.compile("来自.*?($|\\s)");
		Matcher matcher = pattern.matcher(htmlContent);
		
		if(matcher.find())
		{
			return matcher.group().replace("来自", "").trim();
		}
		else
		{
//			System.out.println("No matched date!");
			return null;
		}
	}
	
	private static String getContent(String htmlContent)
	{
		String result = null;
		
		Pattern pattern = Pattern.compile("\\s.*?评论\\[\\d+\\]((\\s*?)|[(\\s\\p{Zs})]+)收藏");
		Matcher matcher = pattern.matcher(htmlContent);
		
		if(matcher.find())
		{
			result = matcher.group().trim();
		}
		
		return result;
	}
	
	public static LinkedList<String> getMicroblogDate(String htmlContent)
	{
		LinkedList<String> result = new LinkedList<String>();
		
		result.add(Microblog_DateSourceContent.getDate(htmlContent));
		result.add(Microblog_DateSourceContent.getSouce(htmlContent));
		result.add(Microblog_DateSourceContent.getContent(htmlContent));
		
		return result;
	}
	
	public static void main(String[] args)
	{
		//03月26日 13:37
		String content = "第1页	【260路公交今天起临调】受武进区童嬉路封闭施工的影响，9月3日起，常州公交将对260路公交做临时调整。调整后的260路：由湖塘乐购始发，按原线行驶至“牛塘张家村”站点后，调整行驶村间道路至童嬉路路口后恢复原行驶路线。武进区童嬉路封闭施工 260路公交今天起临调 赞[1] 转发[0] 评论[0] 收藏 56分钟前 来自皮皮时光机";
		if(Microblog_DateSourceContent.getDate(content) == null)
		{
			System.out.println("no matched date!");
		}
		else
		{
			System.out.println("date is " + Microblog_DateSourceContent.getDate(content));
		}
		
//		System.out.println(Microblog_DateandSource.getSouce(content));
		if(Microblog_DateSourceContent.getSouce(content) == null)
		{
			System.out.println("no matched source");
		}
		else
		{
			System.out.println("source is " + Microblog_DateSourceContent.getSouce(content));
		}
		
		if(Microblog_DateSourceContent.getContent(content) == null)
		{
			System.out.println("no content found!");
		}
		else
		{
			System.out.println("content is " + Microblog_DateSourceContent.getContent(content));
		}
	}
}