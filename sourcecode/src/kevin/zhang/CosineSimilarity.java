package kevin.zhang;

import java.io.UnsupportedEncodingException;

public class CosineSimilarity
{
	/**
	 * 计算主题和文本内容相似性
	 * @param keywords 主题
	 * @param contents 文本内容
	 * @return 二者相似度
	 * @throws UnsupportedEncodingException
	 */
	public static double cosineSimilarity(String keywords,String contents) throws UnsupportedEncodingException
	{
		double cos = 0.0;
		
		String[] keyword = Split2.keywordsSplit(keywords);
		String[] content = Split2.contentSplit(contents);
		int[] frequency = new int[keyword.length];
		
		for(int i = 0;i < frequency.length;i ++)
		{
			frequency[i] = 0;
		}
		
		for(int i = 0;i < content.length;i ++)
		{
			for(int j = 0;j < keyword.length;j ++)
			{
				if(content[i].equals(keyword[j]))
				{
					frequency[j] += 1;
				}
			}
		}
		
		double numerator = 0.0;//分子
		double denominator = 0.0;//分母
		
		for(int i = 0;i < frequency.length;i ++)
		{
			numerator += frequency[i];
			denominator += frequency[i] * frequency[i];
		}
		
		denominator = Math.sqrt(denominator) * Math.sqrt(frequency.length);
		
		if(denominator == 0)
		{
			return 0.0;
		}
		
		cos = numerator / denominator;
		
		return cos;
	}
	
	/**
	 * 添加主题，主题个数不确定
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		double result = CosineSimilarity.cosineSimilarity("城市交通", "城市综合管理水平提升");
		System.out.println("result is " + result);
	}
}