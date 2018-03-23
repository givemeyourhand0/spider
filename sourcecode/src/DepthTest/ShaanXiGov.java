package DepthTest;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import common.function.CommonFunction;

public class ShaanXiGov extends BreadthCrawler{  
    
    public ShaanXiGov(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings({ "unused", "static-access" })
	public void visit(Page page,CrawlDatums next) {  
		String question_regex="http://(www.shaanxi.gov.cn/0|knews.shaanxi.gov.cn/0|www.gov.cn/xinwen|www.gov.cn/zhengce|www.gov.cn/guowuyuan|www.gov.cn/premier).*"; 
		if(Pattern.matches(question_regex, page.getUrl()))
        {                
            System.out.println("processing "+page.getUrl());  
            String url = page.getUrl();
            Document doc=null;
            try {
    			doc = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").timeout(20000).get();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
            CommonFunction f=new CommonFunction();
            f.writeFile("E:\\5--政府网站链接\\陕西省\\陕西省政府.txt",url);
        }  
    }  
  
    public static void main(String[] args) throws Exception{    
    	ShaanXiGov crawler=new ShaanXiGov("ShaanXiGovDepthCrawler",true);  
    	crawler.addSeed("http://www.shaanxi.gov.cn/"); 
    	crawler.addRegex("http://www.shaanxi.gov.cn/.*");
    	crawler.addRegex("http://knews.shaanxi.gov.cn/.*");
    	crawler.addRegex("http://www.gov.cn/.*");
    	/*不要爬取 jpg|png|gif*/
 	    crawler.addRegex("-.*\\.(jpg|png|gif).*");
         /*不要爬取包含 # 的URL*/
 	    crawler.addRegex("-.*#.*");
 	    crawler.setThreads(100);
    	crawler.setTopN(40000);
        crawler.start(10);    
    }

	
}  

