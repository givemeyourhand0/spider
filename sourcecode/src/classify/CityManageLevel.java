package classify;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 城市综合管理水平提升 分类得分计算
 * 阈值：0.22
 * @author Administrator
 *
 */
public class CityManageLevel extends AbstractClassify
{
	protected LinkedList<KeywordsandValue> getDictionary()
	{
		LinkedList<KeywordsandValue> standard = new LinkedList<KeywordsandValue>();
		standard.add(new KeywordsandValue("城市综合管理水平提升",10));
		standard.add(new KeywordsandValue("城市综合管理",9));
		standard.add(new KeywordsandValue("城市管理水平",9));
		standard.add(new KeywordsandValue("综合管理水平提升",8));
		standard.add(new KeywordsandValue("城市建设水平",7));
		standard.add(new KeywordsandValue("综合管理",6));
		standard.add(new KeywordsandValue("城市管理",6));
		standard.add(new KeywordsandValue("城市建设",5));
		standard.add(new KeywordsandValue("城市综合治理",5));
		standard.add(new KeywordsandValue("城市治理",5));
		
		return standard;
	}
	
	public static double getScore(String content) throws UnsupportedEncodingException{
		return new CityManageLevel().score(content);
	}
	
	public static LinkedList<KeywordsandValue> getKeywordsandCount(String content) throws UnsupportedEncodingException{
		return new CityManageLevel().keywordsandCount(content);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		System.out.println(new CityManageLevel().score("杨凌通过不断建立健全城市综合管理标准体系，以“利民、惠民”为目标，实现城市综合管理标准化、人性化。一是持续加大城市管理服务保障投入。购置道路清扫车、高压清洗车、吸污车、洒水车、高空作业车、电动保洁车等市政、环卫专用车辆，机械化清扫率达到80%。建立健全恶劣天气应急处置机制，确保了雨、雪、雾等灾害天气中城区街道环境干净整洁，安全畅通。二是规范临时市场和便民摊点管理。设置临时市场和早、夜市及节庆日、季节性等临时便民摊点，通过限定时间、规范地点等方式解决城市管理中长期困扰的“马路市场”和“流动摊贩”两大难题，规范城市服务功能。三是健全完善城管执法信访工作制度。以城管服务热线、主任信箱为载体，推行双时服务、一站式服务，及时接处各类信访案件，不断优化审批服务流程。责任编辑：殷誉玮　　审核：秋雨"));
	}
}