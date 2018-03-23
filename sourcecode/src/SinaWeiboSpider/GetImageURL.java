package SinaWeiboSpider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetImageURL
{
	/**
	 * 在源码中通过<div class="c" id=xxxx>.*?<div class="s"></div>匹配
	 * 表示一条微博的源码
	 * @param htmlSource
	 * @return
	 */
	public static LinkedList<String> getMicroblogCode(String htmlSource)
	{
		LinkedList<String> result = new LinkedList<String>();
		
		Pattern pattern = Pattern.compile("<div\\s+class=\"c\"\\s+id=.*?>.*?<div class=\"s\"></div>");
		Matcher matcher = pattern.matcher(htmlSource);
		
		while(matcher.find())
		{
			result.add(matcher.group());
		}
		
		return result;
	}
	
	/**
	 * 从每一条微博的源码中通过<image>匹配图片并获取图片链接
	 * @param args
	 */
	public static LinkedList<String> getImageUrl(String microblogCode)
	{
		LinkedList<String> imageURL = new LinkedList<String>();
		
		Pattern pattern = Pattern.compile("<img\\s+src=\".*?\".*?/>");
		Matcher matcher = pattern.matcher(microblogCode);
		
		while(matcher.find())
		{
			Pattern pattern1 = Pattern.compile("src=\".*?\"");
			Matcher matcher1 = pattern1.matcher(matcher.group());
			if(matcher1.find())
			{
				String tmp = matcher1.group();
				tmp = tmp.replace("src=\"", "").replace("\"", "");
				imageURL.add(tmp);
			}
		}
		
		if(imageURL.isEmpty())
		{
			imageURL.add(null);
		}
		
		return imageURL;
	}
	
	public static void main(String[] args) throws IOException
	{
		LinkedList<String> test = new LinkedList<String>();
		BufferedReader in = new BufferedReader(new FileReader("微博源码.txt"));
		String htmlSource = new String("");
		String temp = new String("");
		
		while((temp = in.readLine()) != null)
		{
			htmlSource += temp;
		}
		
		in.close();
		
		test = GetImageURL.getMicroblogCode(htmlSource);
		
		for(String ele : test)
		{
			System.out.println("ele is :" + ele);
			
			LinkedList<String> imageURL = GetImageURL.getImageUrl(ele);
		
			System.out.println("image url:");
			for(String element : imageURL)
			{
				System.out.println(element);
			}
			System.out.println("\n");
		}
	}
}