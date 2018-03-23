package test;

import java.util.Collections;
import java.util.LinkedList;

public class OrderByConsine
{
	@SuppressWarnings("unchecked")
	public static LinkedList<Calculate> order(LinkedList<Calculate> original)
	{
		Collections.sort(original);
		return original;
		
	}
	
	public static void main(String[] args)
	{
		LinkedList<Calculate> result = new LinkedList<Calculate>();
		
		result.add(new Calculate("http://llllllllllllllllll","考古新发现","陕西省出土大量文物",0.934));
		result.add(new Calculate("http://22222222222222222","北京禽流感疫情","北京市人民政府确认没有禽流感发生",0.999999));
		result.add(new Calculate("http://333333333333333333","西安再增两条地铁线","西安市今年将新增两条地铁线",0.5443));
		result.add(new Calculate("http://444444444444444444","西安电子科技大学研究生院开始招生啦","西安电子科技大学今年新招研究生500人",0.872302));
		
		OrderByConsine.order(result);
		
		for(Calculate element : result)
		{
			System.out.println(element);
		}
	}
}