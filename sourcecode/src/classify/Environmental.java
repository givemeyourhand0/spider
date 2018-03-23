package classify;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

/**
 * 环境保护 分类得分计算
 * 阈值：
 * @author maningning
 */
public class Environmental extends AbstractClassify{
	public LinkedList<KeywordsandValue> getDictionary()
	{
		
		LinkedList<KeywordsandValue> standard = new LinkedList<KeywordsandValue>();
		standard.add(new KeywordsandValue("保护环境",10));
		standard.add(new KeywordsandValue("环境保护",10));
		standard.add(new KeywordsandValue("环保",9));
		standard.add(new KeywordsandValue("绿色发展",9));
		standard.add(new KeywordsandValue("自然保护",9));
		standard.add(new KeywordsandValue("生态保护",9));
		standard.add(new KeywordsandValue("生态环境",7));
		standard.add(new KeywordsandValue("污染防治",6));
		standard.add(new KeywordsandValue("可持续发展",2));
		
		return standard;
	}
	
	public static double getScore(String content) throws UnsupportedEncodingException{
		return new Environmental().score(content);
	}
	
	public static LinkedList<KeywordsandValue> getKeywordsandCount(String content) throws UnsupportedEncodingException{
		return new Environmental().keywordsandCount(content);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		System.out.println(new Environmental().score("22"));
	}
}
