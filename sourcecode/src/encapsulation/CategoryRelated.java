package encapsulation;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.LinkedList;

import classify.CityInvestment;
import classify.CityManageLevel;
import classify.CityTraffic;
import classify.KeywordsandValue;
import classify.PolicyLaw;

public class CategoryRelated {
	
	/**
	 * 获取文本内容分类
	 * @param content
	 * @return
	 */
	/*public static String getCategoryResult(String content)
	{
		String[] category={"政策法律法规","城市投资环境","城市交通","城市综合管理水平提升"};
	   	double[] similarity=new double[4];
	   	String categoryResult="";
	   	for(int i=0;i<category.length;i++)
	   	{
	   		try 
	   		{
				similarity[i]=CosineSimilarity.cosineSimilarity(category[i], content);
				if(similarity[i]>0)
				{
					//categoryResult=categoryResult+category[i]+" ";
					categoryResult=categoryResult+(i+1);
				}	
			} 
	   		catch (UnsupportedEncodingException e) 
	   		{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   	}
	   	return categoryResult;
	}*/
  
	/**
	 * 获取文本内容所属分类
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getCategoryResult(String content) throws UnsupportedEncodingException
	{
	   	double[] similarity=new double[4];
	   	similarity[0]=PolicyLaw.getScore(content);
	   	similarity[1]=CityInvestment.getScore(content);
	   	similarity[2]=CityTraffic.getScore(content);
	   	similarity[3]=CityManageLevel.getScore(content);		
	   	String categoryResult="";
	   	for(int i=0;i<4;i++)
	   	{
	   		if(similarity[i]>0)
			{
				categoryResult=categoryResult+(i+1) + "\\";
			}	
	   	}
	   	return categoryResult;
	}
	
	/**
	 * 获取文本内容岁对应
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getCategoryValue(String content) throws UnsupportedEncodingException
	{
		String ret="";
		DecimalFormat df = new DecimalFormat("0.00");
		double[] similarity=new double[4];
	   	similarity[0]=PolicyLaw.getScore(content);
	   	similarity[1]=CityInvestment.getScore(content);
	   	similarity[2]=CityTraffic.getScore(content);
	   	similarity[3]=CityManageLevel.getScore(content);
	   	for(int i=0;i<4;i++)
	   	{
		 	ret=ret+df.format(similarity[i])+"\\";
	   	}
		return ret;
		
	}
	
	/**
	 * 获取文本中的关键词
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getCategoryKeywords(String content) throws UnsupportedEncodingException
	{
		String ret="";
		LinkedList<KeywordsandValue> policyLawKeywordsList=new LinkedList<KeywordsandValue>();
		LinkedList<KeywordsandValue> cityInvestmentKeywordsList=new LinkedList<KeywordsandValue>();
		LinkedList<KeywordsandValue> cityTrafficKeywordsList=new LinkedList<KeywordsandValue>();
		LinkedList<KeywordsandValue> cityManageLevelKeywordsList=new LinkedList<KeywordsandValue>();
				
		String[] keywords=new String[4];
		keywords[0]="";
		keywords[1]="";
		keywords[2]="";
		keywords[3]="";
		
		double[] similarity=new double[4];
	   	similarity[0]=PolicyLaw.getScore(content);
	   	similarity[1]=CityInvestment.getScore(content);
	   	similarity[2]=CityTraffic.getScore(content);
	   	similarity[3]=CityManageLevel.getScore(content);
	   	
	   	policyLawKeywordsList=PolicyLaw.getKeywordsandCount(content);
	   	for(int i=0;i<policyLawKeywordsList.size();i++)
	   	{
	   		keywords[0]=keywords[0]+policyLawKeywordsList.get(i).getKeywords()+"("+policyLawKeywordsList.get(i).getValue()+")"+"_";
	   	}
	   	
	   	cityInvestmentKeywordsList=CityInvestment.getKeywordsandCount(content);
	   	for(int i=0;i<cityInvestmentKeywordsList.size();i++)
	   	{
	   		//System.out.println("第几个keywords:"+cityInvestmentKeywordsList.get(i));
	   		keywords[1]=keywords[1]+cityInvestmentKeywordsList.get(i).getKeywords()+"("+cityInvestmentKeywordsList.get(i).getValue()+")"+"_";
	   	}
	   	
	   	cityTrafficKeywordsList=CityTraffic.getKeywordsandCount(content);
	   	for(int i=0;i<cityTrafficKeywordsList.size();i++)
	   	{
	   		keywords[2]=keywords[2]+cityTrafficKeywordsList.get(i).getKeywords()+"("+cityTrafficKeywordsList.get(i).getValue()+")"+"_";
	   	}
	   	
	   	cityManageLevelKeywordsList=CityManageLevel.getKeywordsandCount(content);
	   	for(int i=0;i<cityManageLevelKeywordsList.size();i++)
	   	{
	   		keywords[3]=keywords[3]+cityManageLevelKeywordsList.get(i).getKeywords()+"("+cityManageLevelKeywordsList.get(i).getValue()+")"+"_";
	   	}
	   	
	   	
	   	for(int i=0;i<4;i++)
	   	{
	   		if(similarity[i]>0)
			{
	   			//System.out.println("keywords:"+keywords[i]);
				ret=ret+keywords[i]+"\\";
			}	
	   	}
		return ret;
	}

	
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		String category=getCategoryResult("城市投资环境和招商引资第二部交通管理");
		String value=getCategoryValue("城市投资环境和招商引资第二部交通管理");
		String keywords=getCategoryKeywords("城市投资环境和招商引资第二部交通管理");
		System.out.println("category的值:"+category);
		System.out.println("value的值："+value);
		System.out.println("keywords的值："+keywords);
	}

}
