package SinaWeiboSpider;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import matchDate.DateSource;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import outputWithXML.OutputXML;
import outputWithXML.ReadFromFile;
import transformUrlToFileName.TransStringToDate;
import transformUrlToFileName.TransUrlToFileName;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import db.DatabaseOperate;
import encapsulation.CategoryRelated;
import encapsulation.ContentRelated;
import encapsulation.VideoRelated;
import finalSpider.Crawler;

/**
 * 利用WebCollector和获取的cookie爬取新浪微博并抽取数据
 * @author hu
 */
public class WeiboCrawler extends BreadthCrawler 
{

    String cookie;
    String firstPageUrl;
    int i=1;
    String publisher;
    
    /**
     * 构造函数，完成微博用户总页数的获取和种子url的添加
     * @param crawlPath URL信息存放路径（BerkeleyDB）
     * @param autoParse 为true，开启自动解析机制，爬虫会自动抽取网页中符合正则规则的URL
     * @param firstPageUrl 要爬取微博用户第一页的url，用来获取微博页面总页数
     * @throws Exception
     */
    @SuppressWarnings({ "unused", "deprecation" })
	public WeiboCrawler(String crawlPath, boolean autoParse, String firstPageUrl,String publisher) throws Exception 
    {
        super(crawlPath, autoParse);
        /*获取新浪微博的cookie，账号密码以明文形式传输，请使用小号*/
        //cookie = WeiboCN.getSinaCookie("你的用户名", "你的密码");
        cookie = WeiboCN.getSinaCookie("15239122017", "199402140129li");
        this.publisher=publisher;
   		this.firstPageUrl=firstPageUrl;
        int totalNumber=0;
        CrawlDatum c=new CrawlDatum(firstPageUrl);
        Page p=new Page(c,getResponse(c));
        String htmlSource=p.getHtml();
        //System.out.println("源代码："+htmlSource);
    	//System.out.println(htmlSource);
    	Matcher m=Pattern.compile("[0-9]+/[0-9]+页").matcher(htmlSource);
        while(m.find())
        {
        	//System.out.println("总页数的查找："+m.group());
        	int length=m.group().length();
        	String[] str=m.group().split("/");
        	String totalNumberStr=str[1].substring(0,str[1].length()-1);
        	System.out.println("字符串类型："+totalNumberStr);
        	totalNumber=Integer.parseInt(totalNumberStr);
        	System.out.println("Int类型："+totalNumber);
        	
        } 
        //对某人微博前totalNumber页进行爬取
        for (int i=1;i<=totalNumber;i++) 
        {//添加种子页面
         	this.addSeed(new CrawlDatum(firstPageUrl.substring(0,firstPageUrl.length()-1)+ i)
                    .putMetaData("pageNum", i + ""));
        } //开启的线程数
        this.setThreads(1);
        }   


    @Override
    public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception 
    {
        HttpRequest request = new HttpRequest(crawlDatum);
        request.setCookie(cookie);
        return request.getResponse();
    }
    
    public void setFirstPageUrl(String firstPageUrl)
    {
    	this.firstPageUrl=firstPageUrl;
    }
    
    public String getFirstPageUrl()
    {
    	return this.firstPageUrl;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void visit(Page page, CrawlDatums next) 
    {	
        int pageNum = Integer.valueOf(page.getMetaData("pageNum"));
        LinkedList<String> result=new LinkedList<String>();//////
        //存储每条微博的源代码
        LinkedList<String> weiboSource=new LinkedList<String>();
        //存储每条微博的图片链接
        LinkedList<String> imageURL=new LinkedList<String>();
        String finalContent="设置:皮肤.图片.条数.隐私";
        //获取单条微博源码
    	CrawlDatum c=new CrawlDatum(this.firstPageUrl);
    	Page p=null;
		try 
		{
			p=new Page(c,this.getResponse(c));
		} 
		catch (Exception e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        String htmlSource=p.getHtml();//整个页面的源代码
        weiboSource=GetImageURL.getMicroblogCode(htmlSource);
		//获取单条微博源码结束 
        //System.out.println("weiboSource的个数为："+weiboSource.size());
        //抽取微博
        Elements weibos = page.select("div.c");
        int weiboNo=0;
        for (Element weibo : weibos) 
        {
        	System.out.println("第" + pageNum + "页\t" + weibo.text());
        	result=Microblog_DateSourceContent.getMicroblogDate("第" + pageNum + "页\t"+weibo.text());
        	if(weibo.text().equals(finalContent))
        		break;
        	String date=result.get(0);
        	String source=result.get(1);
        	System.out.println("来源："+source);
        	String content=result.get(2);
        	String imageU="";
        	//System.out.println("weiboNo的值为："+weiboNo);
        	imageURL=GetImageURL.getImageUrl(weiboSource.get(weiboNo));
        	
        	for(String element : imageURL)
				imageU=imageU+element+" ";
        	String fileName = "F:\\Weibo\\" + TransUrlToFileName.transUrlToFileName("weibo"+i) + ".xml";
     		
        	try 
     		{
        		System.out.println("imageURL:"+imageU);
     			OutputXML.outputXML(content,date,source,imageU,fileName);
     		} 
     		catch (ParserConfigurationException | TransformerFactoryConfigurationError | TransformerException
     					| IOException e) 
     		{
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     		}
     		i++;
     		weiboNo++;
     		Connection conn = null;
    		Statement stmt = null;
    		ResultSet rs = null;
    		PreparedStatement p2=null;
    		try
            {
            	conn = DatabaseOperate.getConnection();
    			stmt = conn.createStatement(); 
     			   	int locationId=27;
     			   	String insertSql1 = "insert into weibo (publisher,content,time,source,imageurl,locationid) values(?,?,?,?,?,?)";
     		 	   int num=-1;
     		 	   try 
     		 	   {
     		 		   p2=conn.prepareStatement(insertSql1,Statement.RETURN_GENERATED_KEYS);
    					p2.setString(1, publisher);
    					p2.setString(2, content);
    					p2.setString(3, date);
    					p2.setString(4, source);
    					p2.setString(5, imageU);  
    					p2.setInt(6, locationId); 
     		 		   p2.executeUpdate();
     		 		   rs=p2.getGeneratedKeys();
     		 		   rs.next();
     		 		   		num=rs.getInt(1);
     		 			   System.out.println("自动增长列为:"+num);
     		 	   }
     		 	   catch (SQLException e2) 
     		 	   {
     		 		   e2.printStackTrace();
     		 	   }
            }
    		catch(Exception e)
            {
            	e.printStackTrace();
            }
            finally
            {
            	DatabaseOperate.closeResult(rs);
    			DatabaseOperate.closeState(stmt);
    			DatabaseOperate.closePstate(p2);;
    			DatabaseOperate.closeConn(conn);
            }
        	
        }
    }
    
    
    
    
    public static void getSubLink(String firstPageUrl,String publisher) throws Exception
    {
    	WeiboCrawler crawler=new WeiboCrawler("weibo_crawler",false,firstPageUrl,publisher);
        crawler.start(1);
    }
    
    
    public static void main(String[] args) throws Exception 
    {
    	LinkedList<LinkedList<String>> result = ReadFromFile.getInfo("weiboFile.txt");
    	 for( int i=0;i<result.size();i++){
       		 String firstPageURL=result.get(i).get(0);
       		 String publisher=result.get(i).get(1);
       		 getSubLink(firstPageURL,publisher);
       		 }
    	//String firstPageURL="http://weibo.cn/shaanxigov?page=1";
    	//String publisher="陕西发布";
    	
    }

}