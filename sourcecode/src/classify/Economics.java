package classify;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
/**
 * 经济 分类得分计算
 * 阈值：
 * @author maningning
 */
public class Economics extends AbstractClassify{

	public LinkedList<KeywordsandValue> getDictionary()
	{
		
		LinkedList<KeywordsandValue> standard = new LinkedList<KeywordsandValue>();
		standard.add(new KeywordsandValue("经济发展",9));
		standard.add(new KeywordsandValue("经济社会发展",9));
		standard.add(new KeywordsandValue("经济结构",9));
		standard.add(new KeywordsandValue("经济增长",6));
		standard.add(new KeywordsandValue("经济制度",6));
		standard.add(new KeywordsandValue("市场经济",5));
		standard.add(new KeywordsandValue("非公有制经济",7));
		standard.add(new KeywordsandValue("企业",1));
		standard.add(new KeywordsandValue("资本",2));
		standard.add(new KeywordsandValue("经济",2));
		
		return standard;
	}
	
	public static double getScore(String content) throws UnsupportedEncodingException{
		return new Economics().score(content);
	}
	
	public static LinkedList<KeywordsandValue> getKeywordsandCount(String content) throws UnsupportedEncodingException{
		return new Economics().keywordsandCount(content);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		System.out.println(new Economics().score("22"));
	}
	
}
