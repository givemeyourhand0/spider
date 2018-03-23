package classify;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import org.junit.experimental.theories.Theories;

import kevin.zhang.Split2;

public abstract class AbstractClassify {
	
	protected abstract LinkedList<KeywordsandValue> getDictionary();

	protected double[] getCount(String content) throws UnsupportedEncodingException
	{
		String[] contentArray = Split2.contentSplit(content);
		
		if(contentArray.length == 0)
			return null;
		
		double[] keywordScore = new double[10];
		LinkedList<KeywordsandValue> standard = this.getDictionary();
		
		for(String element : contentArray)
		{
			for(int i = 0;i < standard.size();i ++)
			{
				if(standard.get(i).getKeywords().equals(element.trim()))
				{
					keywordScore[i] += 1;
				}
			}
		}
		
		return keywordScore;
	}
	
	protected double score(String content) throws UnsupportedEncodingException
	{
		double[] keywordScore = getCount(content);
		LinkedList<KeywordsandValue> standard = this.getDictionary();
		int count = Split2.contentSplit(content).length;
		
		if(count == 0)
			return 0;
		
		double score = 0;
		for(int i = 0;i < standard.size();i ++)
		{
			score += keywordScore[i] * standard.get(i).getValue() / count;
		}
		
		if(score < 0.04)
			return 0.0;
		
		return score;
	}
	
	protected LinkedList<KeywordsandValue> keywordsandCount(String content) throws UnsupportedEncodingException
	{
		double[] keywordScore = getCount(content);
		LinkedList<KeywordsandValue> standard = this.getDictionary();
		LinkedList<KeywordsandValue> result = new LinkedList<KeywordsandValue>();
		
		for(int i = 0;i < standard.size();i ++)
		{
			result.add(new KeywordsandValue(standard.get(i).getKeywords(),(int) keywordScore[i]));
		}
		
		return result;
	}
}
