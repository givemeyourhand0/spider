package matchDate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取记者、编辑、审核信息；若没有相应的信息，则返回null
 * @author Administrator
 *
 */
public class MatchReporter
{
	/**
	 * 获取记者
	 * @param htmlContent 网页文本
	 * @return
	 */
	public static String getReporter(String htmlContent)
	{
		String result = null;
		
		Pattern pattern = Pattern.compile("(\\(|（|)\\s*记者(:|：|\\s+|).*?(\\s+|\\)|）|[\\s\\p{Zs}]+|$)");
		Matcher matcher = pattern.matcher(htmlContent);
		if(matcher.find())
		{
			result = matcher.group().replace("记者", "").replace(":", "").replace("：", "").replace(")", "").replace("摄", "").replace("摄影","").trim();
		}
		
		return result;
	}
	
	/**
	 * 获取审核
	 * @param htmlContent 网页文本
	 * @return
	 */
	public static String getChecker(String htmlContent)
	{
		String result = null;
		
		Pattern pattern = Pattern.compile("审核：.*?(\\s+|\\)|$)");
		Matcher matcher = pattern.matcher(htmlContent);
		
		if(matcher.find())
		{
			result = matcher.group().replace("审核：", "").replace(")", "").trim();
		}
		
		return result;
	}
	
	/**
	 * 获取编辑
	 * @param htmlContent 网页文本
	 * @return
	 */
	public static String getEditor(String htmlContent)
	{
		String result = null;
		
		Pattern pattern = Pattern.compile("编辑(：|:).*?(\\s+|\\)|([\\s\\p{Zs}]+)|$)");
		Matcher matcher = pattern.matcher(htmlContent);
		
		if(matcher.find())
		{
			result = matcher.group().replace("编辑", "").replace("：", "").replace(":", "").replace(")", "").trim();
		}
		
		return result;
	}
	
	public static void main(String[] args)
	{
		String original = "陕西省系统推进西安市全面创新改革试验工作 领导小组第一次会议召开            浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。   浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。         陕西省系统推进西安市全面创新改革试验工作领导小组第一次会议召开 胡和平出席并讲话   2016年09月27日 07:02 来源：省政府办公厅 【字体：大 中 小】     　　9月26日，陕西省系统推进西安市全面创新改革试验工作领导小组召开第一次会议，贯彻落实党中央、国务院决策部署，研究安排下一步西安市全面创新改革试验工作。省长、领导小组组长胡和平出席并讲话，副省长、领导小组副组长张道宏主持，领导小组副组长、西安市市长上官吉庆参加。　　在听取工作汇报后胡和平指出，系统推进西安市全面创新改革试验，是党中央、国务院赋予陕西的重大战略使命，对推进供给侧结构性改革、打造内陆改革开放新高地、建设“三个陕西”具有重要意义。我们要认真贯彻落实国务院《批复》精神，进一步增强改革的使命感和紧迫感，努力形成在全国立得住、可推广的经验。要突出工作的系统性、针对性，统筹中央改革任务与我省改革实际，进一步理清全面创新改革试验的重点任务，确保各项改革取得实效。要把需求导向和问题导向有机结合起来，以实现创新驱动发展转型为目标，以军民深度融合发展和统筹科技资源改革为主攻方向，建立西安自主改革任务、省级层面的支持政策、争取国家授权“三个清单”，找准改革突破口，聚焦重点任务，集中力量予以推进。要明确责任分工，加强统筹协调，以只争朝夕的作风抓好各项工作落实。　　会上，省政府副秘书长夏晓中宣读了《国务院关于西安市系统推进全面创新改革试验方案的批复》，省发改委、省科技厅、西安市政府分别作了汇报。　　省政府秘书长陈国强及领导小组成员单位负责同志参加会议。    责任编辑：殷誉玮　　审核：雪花 ";
		System.out.println("记者：" + MatchReporter.getReporter(original) );
		System.out.println("审核：" + MatchReporter.getChecker(original));
		System.out.println("编辑：" + MatchReporter.getEditor(original));
	}
}