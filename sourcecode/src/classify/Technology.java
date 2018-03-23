package classify;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

/**
 * 经济 分类得分计算
 * 阈值：
 * @author maningning
 */
public class Technology extends AbstractClassify{
	public LinkedList<KeywordsandValue> getDictionary()
	{
		
		LinkedList<KeywordsandValue> standard = new LinkedList<KeywordsandValue>();
		standard.add(new KeywordsandValue("高新技术",8));
		standard.add(new KeywordsandValue("高科技",8));
		standard.add(new KeywordsandValue("高新技术发展",9));
		standard.add(new KeywordsandValue("高新技术企业",7));
		standard.add(new KeywordsandValue("信息化建设",9));
		standard.add(new KeywordsandValue("互联网",4));
		standard.add(new KeywordsandValue("智慧城市",7));
		standard.add(new KeywordsandValue("电子政务",7));
		standard.add(new KeywordsandValue("科技",6));
		standard.add(new KeywordsandValue("创新",2));
		
		return standard;
	}
	
	public static double getScore(String content) throws UnsupportedEncodingException{
		return new Technology().score(content);
	}
	
	public static LinkedList<KeywordsandValue> getKeywordsandCount(String content) throws UnsupportedEncodingException{
		return new Technology().keywordsandCount(content);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		System.out.println(new Technology().score("22"));
	}
}
