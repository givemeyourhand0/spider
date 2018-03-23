package matchDate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateSource
{
	public static String getDate(String htmlContent)
	{
		String date = "";
		int flag = 0;
		try
		{
			Pattern pattern1 = Pattern.compile("((时间：)|(时间:)|(时间)\\s*)(19)|(20)\\d{2}(年|-|/)((0?[1-9])|(1[0-2]))(月|-|/)(0?[1-9]|[1-2][0-9]|3[0-1])(日|[0-9]| |	|)");
			Matcher matcher1 = pattern1.matcher(htmlContent);
			if(matcher1.find())
			{
				date = matcher1.group().replace("时间：", "").replace("时间:", "").replace("时间", "").trim();
			}
			else
			{
				Pattern pattern = Pattern.compile("(19)|(20)\\d{2}(年|-|/)((0?[1-9])|(1[0-2]))(月|-|/)(0?[1-9]|[1-2][0-9]|3[0-1])(日|[0-9]| |	|)");
				Matcher matcher = pattern.matcher(htmlContent);
				if(matcher.find())
				{
					date = matcher.group();
				}
			}
		}
		catch(NullPointerException e)
		{
			flag = 1;
		}
		finally
		{
			if(flag == 0)
				    return date;
		}
		return date;
	}
	
	@SuppressWarnings("finally")
	public static String getSource(String htmlContent)
	{
		int flag = 0;
		String source = null;
		
		try
		{
			//(来源：)|(来源:)|(来源)|(发布机构：)|(发布机构:)|(发布机构)这些必须要用括号括起来
			Pattern pattern = Pattern.compile("((来源：)|(来源:)|(来源)|(发布机构：)|(发布机构:)|(发布机构)) *.*?(\\s|(\\p{Zs})|【|\\[)");
			Matcher matcher = pattern.matcher(htmlContent);
			
			if(matcher.find())
			{
				source = matcher.group().trim();
			}
			
			if(source.contains("来源："))
			{
				source = source.replace("来源：", "").trim();
			}
			else if(source.contains("来源:"))
			{
				source = source.replace("来源:", "").trim();
			}
			else if(source.contains("来源"))
			{
				source = source.replace("来源", "").trim();
			}
			else if(source.contains("发布机构："))
			{
				source = source.replace("发布机构：", "").trim();
			}
			else if(source.contains("发布机构:"))
			{
				source = source.replace("发布机构:", "").trim();
			}
			else if(source.contains("发布机构"))
			{
				source = source.replace("发布机构", "");
			}
		}
		catch(NullPointerException e)
		{
			//Do nothing!
			flag = 1;
		}
		finally
		{
			if(flag == 0)
				return source;
			else
				return null;
		}
	}
	
	public static void main(String[] args)
	{
		String date = DateSource.getDate("职业介绍许可证初审办理指南-西安市灞桥区人民政府     首 页 | 新闻动态 | 政策法规 | 劳动权益 | 培训机构 | 服务机构 | 自主创业 | 互动交流                                      当前位置: 首页 办事指南 公共服务平台 就业创业 自主创业 自主创业服务机构简介 正文                                   职业介绍许可证初审办理指南      索引号：01341316-2/58032 发布时间：2008-12-03 00:00  0 (点击： )             项目名称    职业介绍许可证初审    项目类型   非行政许可项目     受理单位   西安市灞桥区劳动力市场     办公地点   新医路87号灞桥区劳动和社会保障局     联系电话   86524413   投诉电话   83524621     办结时限   20个工作日     办事流程   报送开办书面申请——领取并填写申办表格——提交申办资料——区劳动保障主管部门初审——做出批复——向市劳动保障局申报——市劳动报障局批准并发放《职业介绍许可证》     所需资料   一、申请（性质、名称、地址、负责人和业务范围）  二、开办章程  三、管理制度  四、十万元以上的注册资金（会计师事务所验资报告书）  五、办公场地证明（产权证明或租赁合同）  六、负责人和三名工作人员的身份证复印件、学历证、  二寸彩照三张。  七、填写三种表格（一式三份）  ①西安市申请开办职业介绍所服务机构基本情况登记表；  ②灞桥区职业介绍服务机构负责人登记表; ③职业介绍工作人员登记表。                             上一条：职业介绍机构年审指南   下一条：劳动力市场（劳动监察大队）工作职责    【关闭窗口】                   读取内容中,请等待...                                           西安市灞桥区人民政府版权所有 地址：西安市灞桥区纺一路169号 ICP备案号： 陕ICP备05008157号      您是本站第  位访问者");
//		String source = DateSource.getSource("保障性住房                首页 保障性住房            安居工程项目建设 更多        铜川创新保障房管模式解14万困难群众住房之需 2016-08-08    2016年棚改中省补助资金85亿元全部拨付市县 2016-08-08    宝鸡市扎实做好住房保障工作多项指标名列全省第一 2016-07-14    西安放宽保障性住房门槛家庭人均月入3250可申请 2016-07-01    武功县保障性安居工程配套基础设施项目获中央支持 2016-06-20         分配对象     保障性住房供应对象为城镇低收入、中等偏下收入、中等收入住房困难家庭。低收入家庭收入线,根据《陕西省城市低收入家庭认定实施办法》（陕民发〔2009〕57号）的规定，按照当地低保家庭收入线的1.5倍以下确定。中等偏下收入家庭收入线按照当地城镇居民家庭人均可支配收入的80%以下确定。中等收入家庭的收入线按照当地城镇居民家庭人均可支配收入为上");
		System.out.println("date is " + date);
//		System.out.println("source is " + source);
	}
}