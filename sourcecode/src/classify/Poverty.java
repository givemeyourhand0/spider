package classify;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

/**
 * 经济 分类得分计算
 * 阈值：
 * @author maningning
 */
public class Poverty extends AbstractClassify {
	public LinkedList<KeywordsandValue> getDictionary()
	{
		
		LinkedList<KeywordsandValue> standard = new LinkedList<KeywordsandValue>();
		standard.add(new KeywordsandValue("贫困",7));
		standard.add(new KeywordsandValue("社会保障",5));
		standard.add(new KeywordsandValue("脱贫",9));
		standard.add(new KeywordsandValue("减贫",9));
		standard.add(new KeywordsandValue("脱贫攻坚",9));
		standard.add(new KeywordsandValue("扶贫",9));
		standard.add(new KeywordsandValue("共同致富",7));
		standard.add(new KeywordsandValue("小康",2));
		standard.add(new KeywordsandValue("贫困人口",7));
		standard.add(new KeywordsandValue("专项扶贫",9));
		
		return standard;
	}
	
	public static double getScore(String content) throws UnsupportedEncodingException{
		return new Poverty().score(content);
	}
	
	public static LinkedList<KeywordsandValue> getKeywordsandCount(String content) throws UnsupportedEncodingException{
		return new Poverty().keywordsandCount(content);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		System.out.println(new Poverty().score("22"));
	}
}
