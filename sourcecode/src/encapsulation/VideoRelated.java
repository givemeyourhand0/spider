package encapsulation;

import java.io.IOException;
import java.util.LinkedList;

import finalSpider.Crawler;

public class VideoRelated 
{
	/**
	 * 获取网页中存在的视频链接
	 * @param url 网页的url
	 * @return
	 * @throws IOException
	 */
	public static String getVideoUrl(String url) throws IOException
	{
		LinkedList<String> videoUrlList=new LinkedList<String>();
		videoUrlList=Crawler.getVedioURL(url);
		String ret="";
		for(int i=0;i<videoUrlList.size();i++)
		{
			ret=ret+videoUrlList.get(i)+"\\";
		}
		return ret;
		
	}

}
