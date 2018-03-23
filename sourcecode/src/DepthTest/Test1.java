package DepthTest;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import common.function.CommonFunction;

public class Test1 extends BreadthCrawler{  
    
    public Test1(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")
	public void visit(Page page,CrawlDatums next) {  
		String question_regex="http://mp.weixin.qq.com/s.*"; 
		if(Pattern.matches(question_regex, page.getUrl()))
        {                
            System.out.println("processing "+page.getUrl());  
            String url = page.getUrl();
            Document doc=null;
            String[] headerStr=new String[]{"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2",
    	    		"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0",
    	    		"Mozilla/5.0 (Macintosh; U; PPC Mac OS X 10.4; en-US; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8",
    	    		"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.2.13; ) Gecko/20101203",
    	    		"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3"};
            Random random=new Random();
    		int index=random.nextInt(headerStr.length);
    		//System.out.println(headerStr[index]);
            try {
    			doc = Jsoup.connect(url).header("User-Agent", headerStr[index]).timeout(20000).get();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
            Elements links=doc.getElementsByTag("a");
            System.out.println("URL is:" + url);
            Elements elem = doc.getElementsByTag("Title");  
     	    System.out.println("Title is:" +elem.text());
            System.out.println();
            CommonFunction f=new CommonFunction();
        }  
    }  
  
    public static void main(String[] args) throws Exception{    
    	Test crawler=new Test("Test1Crawler",true);  
    	//crawler.addSeed("http://www.snds.gov.cn/portal/site/site/portal/snds/index.jsp"); 
    	crawler.addSeed("http://www.newrank.cn/public/info/detail.html?account=dahebao19950801");
    	crawler.addRegex("http://mp.weixin.qq.com/s.*");
    	/*不要爬取 jpg|png|gif*/
 	    crawler.addRegex("-.*\\.(jpg|png|gif).*");
        /*不要爬取包含 # 的URL*/
 	    crawler.addRegex("-.*#.*");
 	    /*线程数*/
        crawler.setThreads(100);
    	crawler.setTopN(40000);
        crawler.start(4);    
    }

	
}  

