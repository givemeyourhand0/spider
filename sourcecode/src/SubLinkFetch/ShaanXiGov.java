package SubLinkFetch;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.jcraft.jsch.ChannelSftp;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import common.function.CommonFunction;
import db.DatabaseOperate;
import encapsulation.CategoryRelated;
import encapsulation.ContentRelated;
import encapsulation.VideoRelated;
import finalSpider.Crawler;
import imageDownload.URLConnectionDownloader;
import matchDate.DateSource;
import outputWithXML.OutputXML;
import transformUrlToFileName.TransStringToDate;
import transformUrlToFileName.TransUrlToFileName;

public class ShaanXiGov extends BreadthCrawler
{
	//种子url
	private String seed;
	//向下爬取的规则
	private String regrex;
	//获取子链接的规则
	private String subLinkRegrex;
	
	//用于保存图片的文件夹
    File downloadDir;
    String downloadPath;
    //保存网页源代码的文件夹
    File htmlSourceDir;
    String htmlSourcePath;
    //政府网站名称
    String govName;
	
	/**
	 * 构造函数，完成种子url和限制规则的添加
	 * @param crawlPath URL信息存放路径（BerkeleyDB）
	 * @param autoParse 为true，开启自动解析机制，爬虫会自动抽取网页中符合正则规则的URL
	 * @param govName 爬取的政府网站的名称，例如“陕西省政府”
	 * @param seed 种子url
	 * @param regrex 向下爬取的规则（满足regrex规则，进行深度爬取，否则不进行爬取）
	 * @param subLinkRegrex 获取子链接的规则（满足该规则，就提取该url）
	 * @param downloadPath 下载图片的保存路径
	 * @param htmlSourcePath html源代码的保存路径
	 * @throws Exception 
	 */
    public ShaanXiGov(String crawlPath, boolean autoParse, String govName, String seed, String regrex, String subLinkRegrex, String downloadPath,String htmlSourcePath) throws Exception 
    {
    	super(crawlPath, autoParse); 
		this.govName=govName;
		this.downloadPath=downloadPath;
        downloadDir = new File(downloadPath);
        if(!downloadDir.exists()){
            downloadDir.mkdirs();
        }
        
        
        this.htmlSourcePath=htmlSourcePath;
        htmlSourceDir = new File(htmlSourcePath);
        if(!htmlSourceDir.exists()){
            htmlSourceDir.mkdirs();
        }
        
		this.seed=seed;
		this.regrex=regrex;
		this.subLinkRegrex=subLinkRegrex;
		//添加种子url
		this.addSeed(seed);
		//添加向下爬取的规则
		String[] arrays=regrex.split("----");
	   	for(int i=0;i<arrays.length;i++)
		{
		    this.addRegex(arrays[i]);
		}
		//不爬取 jpg|png|gif
		this.addRegex("-.*\\.(jpg|png|gif).*");
		//不要爬取包含 # 的URL
		this.addRegex("-.*#.*");
		//开启的线程数目
		this.setThreads(10);
		//每次最多爬取url的个数
    	this.setTopN(40000);
    	/*
    	 * 设置是否为断点爬取，如果设置为false，任务启动前会清空历史数据。
    	 * 如果设置为true，会在已有crawlPath的基础上继续爬取。对于耗时较长的任务，
    	 * 很可能需要中途中断，也有可能遇到死机、断电等异常情况，使用断电爬取模式，可以保证爬虫
    	 * 不受这些因素的影响，爬虫可以在人为中断、死机、断电等情况出现后，继续以前的任务进行爬取。
    	 * 断点爬取默认为false
    	 */
    	this.setResumable(true);    	
    	this.start(3);
	}
    
    public void setSeed(String seed)
    {
    	this.seed=seed;
    }
    
    public String getSeed()
    {
    	return this.seed;
    }
    
    public void setRegrex(String regrex)
    {
    	this.regrex=regrex;
    }
    
    public String getRegrex()
    {
    	return this.regrex;
    }
    
    public void setSubLinkRegrex(String subLinkRegrex)
    {
    	this.subLinkRegrex=subLinkRegrex;
    }
    
    public String getSubLinkRegrex()
    {
    	return this.subLinkRegrex;
    }
    
    public static HttpResponse getResponseT(CrawlDatum crawlDatum) throws Exception 
    {
        HttpRequest request = new HttpRequest(crawlDatum);
        return request.getResponse();
    }
	
    @SuppressWarnings({ "static-access", "unused" })
	public void visit(Page page, CrawlDatums next)
	{  
		if(Pattern.matches(this.getSubLinkRegrex(), page.getUrl()))
        {                
            System.out.println("processing "+page.getUrl());  
            String url = page.getUrl();
            CommonFunction f=new CommonFunction();
            f.writeFile("D:\\graduSourceCode\\code\\ShaanXiGovNew.txt",url);
            
            
            Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			PreparedStatement p=null;
			PreparedStatement p1=null;
            try
            {
            	conn = DatabaseOperate.getConnection();
				stmt = conn.createStatement(); 
				
            	Crawler crawler1 = new Crawler();
                String elem=null;
                try 
     			{
     				elem = crawler1.getHtmlContent(page.getUrl());
     			} 
     			catch (IOException e2) 
     			{
     				e2.printStackTrace();
     			}
     			String content=null;
     			try 
     			{
     				content=Crawler.stripHtml(Crawler.getHtmlContent(page.getUrl()));
     				System.out.println("content的值为："+content);
     			} 
     			catch (IOException e1) 
     			{
     				e1.printStackTrace();
     			}
     			if(content==null||content.equals(""))
     			{
     				//空操作；
     			}
     			else
     			{
     				//关于图片的操作
     				ArrayList<String> imageResult=new ArrayList<String>();
     				String image="";
     			   	imageResult=page.getAttrs("img[src]","abs:src");
     			   	for(int i=0;i<imageResult.size();i++)
     			   	{
     			   		//将图片的url以“\”分开
     			   		image=image+imageResult.get(i)+"\\";
     			   	}
     			   	
     				String categoryResult="";
     				String categoryValue="";
     				String categoryKeywords="";
     				String url1=page.getUrl();
     				String videoResource=""; 
     				try 
     				{
     					categoryResult=CategoryRelated.getCategoryResult(content);
     					categoryValue=CategoryRelated.getCategoryValue(content);
     					categoryKeywords=CategoryRelated.getCategoryKeywords(content);
     					videoResource=VideoRelated.getVideoUrl(url1);
     				} 
     				catch (IOException e1) 
     				{
     					e1.printStackTrace();
     				}     				
     				
     				/*
     				String fileName1="D:\\graduSourceCode\\code\\ShaanXiGovTbl_main\\" + TransUrlToFileName.transUrlToFileName(page.getUrl()) + ".xml";
     				try 
     				{
     						OutputXML.outputXML(Crawler.getTitle(elem), DateSource.getDate(content),
     						Crawler.getContent(page.getUrl()), ContentRelated.getAllSource(content), 
     								this.govName, page.getUrl(), fileName1,categoryResult,categoryValue,categoryKeywords);
     				}
     				catch (ParserConfigurationException | TransformerFactoryConfigurationError | TransformerException
     							| IOException e) 
     				{
     						e.printStackTrace();
     				}
     				
     				String fileName2="D:\\graduSourceCode\\code\\ShaanXiGovResource\\" + TransUrlToFileName.transUrlToFileName(page.getUrl()) + ".xml";
     				try 
     				{
     					Integer imageNumber=imageResult.size();
     					OutputXML.outputXML(imageNumber.toString(),image,videoResource, this.govName,fileName2);
     				} 
     				catch (ParserConfigurationException | TransformerFactoryConfigurationError | TransformerException
     							| IOException e) 
     				{
     						e.printStackTrace();
     				}
     				*/
     				
     			   	//存到数据库里面
    			   	String title=Crawler.getTitle(elem);
     			   	String date=DateSource.getDate(content);
     			   	String source=null;
    				try 
    				{
    					source = ContentRelated.getAllSource(content);
    				} 
     				catch (IOException e2) 
    				{
     					e2.printStackTrace();
     				}
     			   	String location=this.govName;
     			   	String mainTableName="tbl_main";
     				String resourceTableName="resource";
     				String shaanXiTable="tbl_partition_27shannxi";
    			   	int locationId=27;
     			   	java.sql.Date date2=TransStringToDate.transStringToDate(date);
     			   	String dateStr=null;
     			   	if(date2==null||date2.toString().equals(""))
    			   	{
    			   		dateStr="";
     			   	}
    			   	else
     			   		dateStr=date2.toString();
     			   	String insertSql1 = "insert into tbl_partition_27shannxi (title,content,time,source,url,locationid,classify,value,keywords) values(?,?,?,?,?,?,?,?,?)";
     				//		+" '"+ title+"',"
     			 	//		+" '"+content+"',"
     			 	//		+" '"+dateStr+"',"
     			 	//		+" '"+source+"',"
     			 	//		+" '"+url1+"',"
     			 	//		+locationId+","
     			 	//		+" '"+categoryResult+"',"
     			 	//		+" '"+categoryValue+"',"
    			 	//		+" '"+categoryKeywords+"'"
     			 	//		+")";
    			   
     		 	   int num=-1;
     		 	   try 
     		 	   {
     		 		   p=conn.prepareStatement(insertSql1,Statement.RETURN_GENERATED_KEYS);
						p.setString(1, title);
						p.setString(2, content);
				
						System.out.println(dateStr);
					
						Date date11 = new Date(Integer.valueOf(dateStr.substring(0, 4)).intValue()-1900, Integer.valueOf(dateStr.substring(5, 7)).intValue()-1, Integer.valueOf(dateStr.substring(8)).intValue());
					
						System.out.println(date11.toString()+"-------");
			
						p.setDate(3, date2);
						p.setString(4, source);
						p.setString(5, url1);  
						p.setInt(6, locationId);  
						p.setString(7, categoryResult);
						p.setString(8, categoryValue);
						p.setString(9, categoryKeywords);
						
    		 		   p.executeUpdate();
     		 		   rs=p.getGeneratedKeys();
     		 		   rs.next();
//     		 		   if(rs.next())
//     		 		   {
     		 			   num=rs.getInt(1);
     		 			   System.out.println("自动增长列为:"+num);
//     		 		   }
     		 	   }
    		 	   catch (SQLException e2) 
     		 	   {
     		 		   e2.printStackTrace();
     		 	   }
     				
     				
     		 	   /*
     				//将图片、视频等信息存到resource表中
     		 	   if(num==-1)
     		 	   {
     		 		   //空操作；
     		 	   }
     		 	   else
     		 	   {
     		 		  String insertSql2 = "insert into "+resourceTableName+" (id,imagenumber,imageurl,otherresource,locationid) values(?,?,?,?,?)";
    				//			+num+","
     				//			+imageResult.size()+","
     				// 			+" '"+image+"',"
     				// 			+" '"+videoResource+"',"
     				// 			+locationId
     				// 			+")";
     					try 
     					{
     						p1=conn.prepareStatement(insertSql2);
     						p1.setInt(1, num);
    						p1.setInt(2, imageResult.size());
     						p1.setString(3, image);
     						p1.setString(4, videoResource);
     						p1.setInt(5, locationId);
     						p1.executeUpdate();
     					} 
     					catch (SQLException e) 
     					{
     						e.printStackTrace();
     					}
     					
     					//将图片信息存到文件夹中	
     				   	for(int i=0;i<imageResult.size();i++)
     				   	{
     				   		System.out.println("图片链接"+i+":"+imageResult.get(i));
     				   		CrawlDatum c=new CrawlDatum(imageResult.get(i));
     				   		Page imgPage=null;
     						try 
     						{
     							imgPage=new Page(c,getResponseT(c));
     						} 
     						catch (Exception e) 
     						{
     							e.printStackTrace();
     						}
     						
     						try 
     						{
     							URLConnectionDownloader.downloadImage(imgPage,imageResult.get(i),num,i+1,this.downloadPath);
     						} catch (Exception e) 
     						{
     							e.printStackTrace();
     						}
     				   	}
     				   	
     					//将网页源码信息以html格式存到文件夹中
     					try 
     					{
     						URLConnectionDownloader.saveAsHtml(url1,num,this.htmlSourcePath);
     					} 
     					catch (IOException e1) 
     					{
     						e1.printStackTrace();
     					}
     		 	   }
     		 	   */
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
   			    DatabaseOperate.closePstate(p);;
   			    DatabaseOperate.closeConn(conn);
            }
        }
    }  
	@SuppressWarnings("unused")
	public static void getAllMessage() throws Exception
	{
//		CrawlerBaQiaoGov 西安市灞桥区人民政府 http://www.baqiao.gov.cn/ http://www.baqiao.gov.cn/.* http://www.baqiao.gov.cn/info/news/.*htm BaQiaoGovImage BaQiaoGovHtmlSource
//			CrawlerXiAnGov 西安市人民政府 http://xian.gov.cn/ http://xian.gov.cn/.* http://(xian.gov.cn/ptl/def/def|www.xafgj.gov.cn/bzxzf).* XiAnGovImage XiAnGovHtmlSource
//			CrawlerHanZhongGov 汉中市人民政府 http://www.hanzhong.gov.cn/ http://www.hanzhong.gov.cn/.* http://www.hanzhong.gov.cn/xwzx/.* HanZhongGovImage HanZhongGovHtmlSource
//			CrawlerTongChuanGov 铜川市人民政府 http://www.tongchuan.gov.cn/ http://www.tongchuan.gov.cn/.* http://www.tongchuan.gov.cn/html/zxzx/tcyw.* TongChuanGovImage TongChuanGovHtmlSource
//			CrawlerBaoJiGov 宝鸡市人民政府 http://www.baoji.gov.cn/ http://www.baoji.gov.cn/.* http://www.baoji.gov.cn/site/1/html/51/119/.* BaoJiGovImage BaoJiGovHtmlSource

		String govName="西安市人民政府";
		String seed="http://xian.gov.cn/";
		String regrex="http://xian.gov.cn/.*";
		String subLinkRegrex="http://(xian.gov.cn/ptl/def/def|www.xafgj.gov.cn/bzxzf).*";	
		ShaanXiGov crawler=new ShaanXiGov("CrawlerXiAnGov", true, govName, seed, regrex, subLinkRegrex,"XiAnGovImage","XiAnGovHtmlSource");
        
	}
	
    public static void main(String[] args) throws Exception
    {
    	getAllMessage();
    }
}