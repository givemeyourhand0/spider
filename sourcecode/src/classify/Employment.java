package classify;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

/**
 * 就业创业 分类得分计算
 * 阈值：
 * @author maningning
 */
public class Employment extends AbstractClassify{
	
	public LinkedList<KeywordsandValue> getDictionary()
	{
		
		LinkedList<KeywordsandValue> standard = new LinkedList<KeywordsandValue>();
		standard.add(new KeywordsandValue("就业环境",5));
		standard.add(new KeywordsandValue("工作岗位",9));
		standard.add(new KeywordsandValue("学生就业",9));
		standard.add(new KeywordsandValue("带动就业",9));
		standard.add(new KeywordsandValue("就业",5));
		standard.add(new KeywordsandValue("自主创业",9));
		standard.add(new KeywordsandValue("双创",9));
		standard.add(new KeywordsandValue("万众创业",8));
		standard.add(new KeywordsandValue("创业环境",5));
		standard.add(new KeywordsandValue("创业",5));
		
		return standard;
	}
	
	public static double getScore(String content) throws UnsupportedEncodingException{
		return new Employment().score(content);
	}
	
	public static LinkedList<KeywordsandValue> getKeywordsandCount(String content) throws UnsupportedEncodingException{
		return new Employment().keywordsandCount(content);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		System.out.println(new Employment().score("22"));
	}

}
