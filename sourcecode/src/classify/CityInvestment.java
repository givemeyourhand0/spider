package classify;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

/**
 * 城市投资环境 分类得分计算
 * 阈值：0.25
 * @author Administrator
 */
public class CityInvestment extends AbstractClassify
{
	protected  LinkedList<KeywordsandValue> getDictionary()
	{
		LinkedList<KeywordsandValue> standard = new LinkedList<KeywordsandValue>();
		standard.add(new KeywordsandValue("城市投资环境",10));
		standard.add(new KeywordsandValue("投资环境",9));
		standard.add(new KeywordsandValue("投资效益",8));
		standard.add(new KeywordsandValue("市场竞争",7));
		standard.add(new KeywordsandValue("投资项目",6));
		standard.add(new KeywordsandValue("投资",5));
		standard.add(new KeywordsandValue("城市竞争力",4));
		standard.add(new KeywordsandValue("招商引资",3));
		standard.add(new KeywordsandValue("产业发展",2));
		standard.add(new KeywordsandValue("市场",1));

		return standard;
	}
	
	public static double getScore(String content) throws UnsupportedEncodingException{
		return new CityInvestment().score(content);
	}
	
	public static LinkedList<KeywordsandValue> getKeywordsandCount(String content) throws UnsupportedEncodingException{
		return new CityInvestment().keywordsandCount(content);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		CityInvestment cityInvestment = new CityInvestment();
		System.out.println(cityInvestment.score("投资决策是一项动态过程，既要着眼于现有市场规模，也要放眼潜在市场空间，综合衡量静态动态投资环境，实现投资效益最大化。《中国主要城市投资环境排名》不仅直观地分析了35个主要大城市的综合实力与竞争力，也反映了其所在区域的投资环境静态现状和动态变化，为政府、企业和个人投资者等不同类型的市场主体提供多层次的决策参考和依据。对政府部门而言，必须清晰地了解自身的优劣势所在，以扬长补短，促进城市的均衡发展，不断提升综合实力和竞争力。对投资者而言，要依据自身投资行业性质和要求进行相应筛选，比如，投资高薪技术产业，无疑北京、上海、广州、深圳、西安、武汉等高新区实力雄厚、高校生人数众多、人才云集的城市是更优的选择。.然而，排名靠后的城市尽管现阶段综合实力与竞争力较弱，依然有不可忽视的投资价值，如随着一带一路战略的深入推进实施，西部地区将迎来巨大的历史性发展机遇，地区生产力和消费实力不断上升，新的投资项目和机会层出不穷，长期而言，是良好的投资选择。"));
	}
}