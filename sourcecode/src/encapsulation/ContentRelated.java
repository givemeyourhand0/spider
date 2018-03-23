package encapsulation;

import java.io.IOException;

import matchDate.DateSource;
import matchDate.MatchReporter;

public class ContentRelated 
{
	/**
	 * 获取文本内容来源，包括例如“陕西日报”的来源以及新闻报道的记者和编辑
	 * @param content 
	 * @return
	 * @throws IOException 
	 */
	public static String getAllSource(String content) throws IOException
	{
		String ret=""; 
		//firstSource是例如“陕西日报”的来源
		 String firstSource=DateSource.getSource(content);
		 //reporter是新闻报道的记者
		 String reporter=MatchReporter.getReporter(content);
		 //checker是新闻的审核人
		 String checker=MatchReporter.getChecker(content);
		 //editor是新闻的编辑
		 String editor=MatchReporter.getEditor(content);
		 ret=firstSource+"\\"+"记者："+reporter+"\\"+"审核："+checker+"\\"+"编辑："+editor;
		return ret;
	}
	
	public static void main(String[] args) throws IOException
	{
		String url="陕西省系统推进西安市全面创新改革试验工作 领导小组第一次会议召开            浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。   浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。         陕西省系统推进西安市全面创新改革试验工作领导小组第一次会议召开 胡和平出席并讲话   2016年09月27日 07:02 来源：省政府办公厅 【字体：大 中 小】     　　9月26日，陕西省系统推进西安市全面创新改革试验工作领导小组召开第一次会议，贯彻落实党中央、国务院决策部署，研究安排下一步西安市全面创新改革试验工作。省长、领导小组组长胡和平出席并讲话，副省长、领导小组副组长张道宏主持，领导小组副组长、西安市市长上官吉庆参加。　　在听取工作汇报后胡和平指出，系统推进西安市全面创新改革试验，是党中央、国务院赋予陕西的重大战略使命，对推进供给侧结构性改革、打造内陆改革开放新高地、建设“三个陕西”具有重要意义。我们要认真贯彻落实国务院《批复》精神，进一步增强改革的使命感和紧迫感，努力形成在全国立得住、可推广的经验。要突出工作的系统性、针对性，统筹中央改革任务与我省改革实际，进一步理清全面创新改革试验的重点任务，确保各项改革取得实效。要把需求导向和问题导向有机结合起来，以实现创新驱动发展转型为目标，以军民深度融合发展和统筹科技资源改革为主攻方向，建立西安自主改革任务、省级层面的支持政策、争取国家授权“三个清单”，找准改革突破口，聚焦重点任务，集中力量予以推进。要明确责任分工，加强统筹协调，以只争朝夕的作风抓好各项工作落实。　　会上，省政府副秘书长夏晓中宣读了《国务院关于西安市系统推进全面创新改革试验方案的批复》，省发改委、省科技厅、西安市政府分别作了汇报。　　省政府秘书长陈国强及领导小组成员单位负责同志参加会议。    责任编辑：殷誉玮　　审核：雪花 ";
		String allSource=getAllSource(url);
		System.out.println(allSource);
		
	}

}
