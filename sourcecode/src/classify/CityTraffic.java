package classify;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

/**
 * 城市交通 分类得分计算
 * 阈值：0.17
 * @author Administrator
 */
public class CityTraffic extends AbstractClassify
{
	protected LinkedList<KeywordsandValue> getDictionary()
	{
		LinkedList<KeywordsandValue> standard = new LinkedList<KeywordsandValue>();
		standard.add(new KeywordsandValue("城市交通规划",9));
		standard.add(new KeywordsandValue("城市交通设施",9));
		standard.add(new KeywordsandValue("城市交通体系",9));
		standard.add(new KeywordsandValue("城市交通建设",9));
		standard.add(new KeywordsandValue("城市交通运输",9));
		standard.add(new KeywordsandValue("城市轨道交通",7));
		standard.add(new KeywordsandValue("城市交通网",7));
		standard.add(new KeywordsandValue("城市交通",10));
		standard.add(new KeywordsandValue("公共交通",5));
		standard.add(new KeywordsandValue("交通",6));
		
		return standard;
	}
	
	public static double getScore(String content) throws UnsupportedEncodingException{
		return new CityTraffic().score(content);
	}
	
	public static LinkedList<KeywordsandValue> getKeywordsandCount(String content) throws UnsupportedEncodingException{
		return new CityTraffic().keywordsandCount(content);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		System.out.println(new CityTraffic().score("强化绩效考核，加强社会监督。各城市人民政府要结合实际，研究制定公共交通绩效考核办法，加强对相关部门的绩效评价与考核，定期发布考评结果，并与政府目标考核挂钩，形成长效推进机制。交通运输部门要加强对企业服务质量和运营安全的监督考核，将考核结果作为衡量企业运营绩效、发放补贴、市场准入退出的重要依据。建立公共交通企业工资增长机制，切实提高职工待遇。健全公众意见征集和反馈机制，实行线网规划公示和运价听证制度，建立信息公开制度，实现公众参与的制度化和常态化。"));
	}
}