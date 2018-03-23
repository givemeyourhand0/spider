package WeiXinSpider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import common.function.CommonFunction;
import db.DatabaseOperate;
import matchDate.DateSource;
import outputWithXML.OutputXML;
import transformUrlToFileName.TransUrlToFileName;

public class SimulateWeiXin
{	
	/**
	 * 模拟浏览器点击
	 * @param accountName 微信公众号的名称是，例如“浙江旅游”等
	 * @throws InterruptedException
	 */
	public static ArrayList<String> testSelenium(String accountName) throws InterruptedException
	{
		//System.getProperties().setProperty("webdriver.chrome.driver", "F:\\chrome\\chromedriver.exe");
		//System.setProperty("webdriver.chrome.bin","C:\\Program Files (x86)\\Google\\Chrome\\chrome.exe");
		System.setProperty("webdriver.firefox.bin", "D:\\ProgramFiles\\firefox\\firefox.exe");
		//WebDriver webDriver = new ChromeDriver();
		WebDriver webDriver = new FirefoxDriver();
		webDriver.get("http://weixin.sogou.com/");
		webDriver.findElement(By.id("upquery")).sendKeys(accountName);
		
		Thread.sleep(1000);
		
		webDriver.findElement(By.className("swz2")).click();
		//webDriver.findElement(By.xpath("/html/body/div[2]/div[3]/div[1]/div[1]/div/div[2]/div/div[1]")).click();
		
		webDriver.findElement(By.xpath("/html/body/div[2]/div/div[8]/ul/li[1]/div/div[2]/p[1]/a/em")).click();
		
		Thread.sleep(1000);
		
		webDriver=switchWindow(webDriver,accountName);
		String currentUrl=webDriver.getCurrentUrl();
		System.out.println("新网页的标题为：" + webDriver.getTitle());
		System.out.println("新网页的url为："+currentUrl);
		ArrayList<String> result = LoadJS(currentUrl);//模拟浏览器行为从而加载得到JavaScript运行之后的结果
		
		//quit关闭所有页面,close关闭本次执行打开的页面
		//webDriver.close();
		webDriver.quit();
		
		return result;
	}
	
	/**
	 * 获取转向特定浏览器窗口标识的webDriver
	 * @param dr 返回的webDriver
	 * @param winTitle 所在页的浏览器窗口标识，这里相当于是微信公众号的名称，例如“浙江旅游”
	 * @return
	 */
	private static WebDriver switchWindow(WebDriver dr,String winTitle)
	{
        String currentHandle =dr.getWindowHandle();//获取当前浏览器窗口标识
        
        Set<String> handles=dr.getWindowHandles();//获取所有浏览器窗口标识
        
        for(String handle:handles)
        {
            if(handle.equals(currentHandle))
                continue;
            else
            {
                dr.switchTo().window(handle);
                if(dr.getTitle().contains(winTitle))
                    break;
                else
                    continue;
            }
        }
        return dr;
    }
	
	/**
	 * 获取公众号所在url下显示出的所有微信链接
	 * @param url 特定微信号的url
	 */
	public static ArrayList<String> LoadJS(String url)
	{
		try
	    {
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
			
	        //设置webClient的相关参数
	        //启动JS
	        webClient.getOptions().setJavaScriptEnabled(true);
	        webClient.getOptions().setCssEnabled(false);
	        
	        //启动客户端重定向
	        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	        
	        //设置超时时间
	        webClient.getOptions().setTimeout(50000);
	        
	        //JS运行出错时是否抛出异常//false
	        webClient.getOptions().setThrowExceptionOnScriptError(true);
	        
	        //模拟浏览器打开一个目标网址ַ
	        HtmlPage rootPage = webClient.getPage(url);
	        System.out.println("为了获取js执行的数据 线程开始沉睡等待");
	        Thread.sleep(5000);//主要是这个线程的等待 因为JavaScript加载也是需要时间的
	        System.out.println("线程结束沉睡");
	        
	        String htmlContent = rootPage.asXml();
	        String[] hrefs=new String[300];
	        ArrayList<String> hrefResult=new ArrayList<String>();//存储微信下的子链接
	        int i=0;
	        
	        String preStr="http://mp.weixin.qq.com";
	        Matcher m=Pattern.compile("hrefs=.*?\\>").matcher(htmlContent);
	        
	        while(m.find())
	        {
	            int length=m.group().length();
		       	hrefs[i]=m.group().substring(7, length-2);
		       	hrefs[i]=hrefs[i].replaceAll("amp;","");
		       	hrefs[i]=preStr+hrefs[i];
		       	
		        hrefResult.add(hrefs[i]);
	            i++;
	        }
	        
	        
	        HashSet<String> hashset=new HashSet<String>();
	        hashset.addAll(hrefResult);
	        hrefResult.clear();
		    hrefResult.addAll(hashset);
		    
		    for(int j=0;j<hrefResult.size();j++)
		   	{
		    	System.out.println("j的值为："+j);
		    	System.out.println("链接"+(j+1)+":"+hrefResult.get(j));
		    	CommonFunction.writeFile("E:\\result.txt", hrefResult.get(j));
		   	}
		    
		    return hrefResult;
	        
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    	return null;
	    }
	}
	
	/**
	 * 该部分代码和finalSpider.Crawler.getHtmlContent(...)完全相同
	 * @param htmlurl
	 * @return
	 * @throws IOException
	 */
	public static String getHtmlContent(String htmlurl) throws IOException
	{
        String htmlContent = null;
        Document doc = null;
        doc = Jsoup.connect(htmlurl).timeout(20000).get();
        htmlContent = doc.toString();
        return htmlContent.replaceAll("\n", "");//Changed by ZY:from replace() to replaceAll()
    }
	
	/**
	 * 获取微信中的图片链接
	 */
	public static String getImageURL(String htmlSource)
	{
		String result = "";
		Pattern pattern = Pattern.compile("<img.*?>",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(htmlSource);
		
		while(matcher.find())
		{
//			System.out.println("image label: " + matcher.group());
			Matcher matcher1 = Pattern.compile("src=\".*?\"").matcher(matcher.group());
			if(matcher1.find())
			{
				String tmp = matcher1.group();
				tmp = tmp.replace("src=\"", "").replace("\"", "");
				if(!tmp.isEmpty() && tmp.contains("http:"))
				{
					//System.out.println("Image URL: " + tmp);
					result = result + tmp + " ";
				}
			}
		}
		return result;
	}
	
	/**
	 * 在下面的基础上可进一步去除jsp文件
	 * @param HTMLStr
	 * @return
	 */
	public static String stripHtml(String HTMLStr)
	{
		String htmlStr = HTMLStr;
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		
		java.util.regex.Pattern p_o;
		java.util.regex.Matcher m_o;
		
		try
		{
			htmlStr = htmlStr.replaceAll("<\\/br>", "");
			htmlStr = htmlStr.replaceAll("<style.*?/style>", "");//<style>......</style>标签中的CSS样式
			
			String regEx_o = "&nbsp;|&gt;";// 空格
			p_o = Pattern.compile(regEx_o, Pattern.CASE_INSENSITIVE);
			m_o = p_o.matcher(htmlStr);
			htmlStr = m_o.replaceAll(" ");
			
			htmlStr = htmlStr.replaceAll("\\s+", " ");//Added by ZY:用空格代替所有的空白
			
			regEx_o = "<\\/p>";// 换行符
			p_o = Pattern.compile(regEx_o, Pattern.CASE_INSENSITIVE);
			m_o = p_o.matcher(htmlStr);
			htmlStr = m_o.replaceAll("</p>;newline;");
			
			regEx_o = "<script.*?</script>";
			p_o = Pattern.compile(regEx_o, Pattern.CASE_INSENSITIVE);
			m_o = p_o.matcher(htmlStr);
			htmlStr = m_o.replaceAll("");
			
			htmlStr = htmlStr.replaceAll("<.*?>", "");
			
			regEx_o = "<\\!--.*-->";// html注释
			p_o = Pattern.compile(regEx_o, Pattern.CASE_INSENSITIVE);
			m_o = p_o.matcher(htmlStr);
			htmlStr = m_o.replaceAll("");
			
			String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 脚本
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll("");

			String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 样式
			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll("");
			
			regEx_o = ";nbsp|&amp;";// 空格
			p_o = Pattern.compile(regEx_o, Pattern.CASE_INSENSITIVE);
			m_o = p_o.matcher(htmlStr);
			htmlStr = m_o.replaceAll(" ");
			
			
			regEx_o = "<\\/p>";// 换行符
			p_o = Pattern.compile(regEx_o, Pattern.CASE_INSENSITIVE);
			m_o = p_o.matcher(htmlStr);
			htmlStr = m_o.replaceAll("</p>;newline;");
			
			String regEx_html = "<[^>]+>"; // 其他html标签
			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll("");
			
//			textStr = htmlStr.replaceAll(" ", "");
//			textStr = textStr.replaceAll(";newline;", "\n");
			textStr = htmlStr.trim();
			textStr = textStr.replaceAll("&lt;br","");
			textStr = textStr.replaceAll(";newline;","");//Changed by ZY 2016-7-31:It's "" not "\n" to instead ";newline;"
		}
		catch(Exception e)
		{
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;
	}
	
	/**
	 * 获取网页标题
	 */
	public static String getTitle(String htmlSource)
	{
		Matcher matcher = Pattern.compile("<title.*?/title>").matcher(htmlSource);
		if(matcher.find())
		{
			return matcher.group().replace("<title>", "").replace("</title>", "");
		}
		
		return null;
	}
	
	public static void getAllContent(String accountName) throws IOException, InterruptedException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
	{
		String name="习水县政府";
		int i=0;
		ArrayList<String> result = testSelenium(accountName);
		
		for(String ele : result)
		{
			String htmlContent = SimulateWeiXin.getHtmlContent(ele);
			String textContent = SimulateWeiXin.stripHtml(htmlContent);
			String date = DateSource.getDate(htmlContent);
			String title = SimulateWeiXin.getTitle(htmlContent);
			String image = SimulateWeiXin.getImageURL(htmlContent);
			String fileName = "E:\\WeiXin1\\" + TransUrlToFileName.transUrlToFileName(accountName+i) + ".xml";
			//System.out.println("Text content: " + textContent);
			//System.out.println("date: " + DateSource.getDate(htmlContent));
			//System.out.println("Title: " + SimulateWeiXin.getTitle(htmlContent));
			OutputXML.outputXML(accountName, textContent, date, title, image, fileName);
			i++;
			Connection conn = null;
    		Statement stmt = null;
    		ResultSet rs = null;
    		PreparedStatement p3=null;
    		try
            {
            	conn = DatabaseOperate.getConnection();
    			stmt = conn.createStatement(); 
     			   	int locationId=27;
     			   	String insertSql1 = "insert into wechat (name,textcontent,date,imgurl,title,locationid) values(?,?,?,?,?,?)";
     		 	   int num=-1;
     		 	   try 
     		 	   {
     		 		   p3=conn.prepareStatement(insertSql1,Statement.RETURN_GENERATED_KEYS);
    					p3.setString(1, name);
    					p3.setString(2, textContent);
    					p3.setString(3, date);
    					p3.setString(4, image);
    					p3.setString(5, title);
    					p3.setInt(6,locationId); 
     		 		   p3.executeUpdate();
     		 		   rs=p3.getGeneratedKeys();
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
    			DatabaseOperate.closePstate(p3);;
    			DatabaseOperate.closeConn(conn);
            }
        	
        }
	}
	
	public static void main(String[] args) throws InterruptedException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
	{
		
		SimulateWeiXin.getAllContent("习水县政府");
//		String accountName="习水县政府";
//		
////		testSelenium(accountName);
//		
//		ArrayList<String> result = SimulateWeiXin.LoadJS(url)
		
//		String htmlContent = SimulateWeiXin.getHtmlContent("http://mp.weixin.qq.com/s?timestamp=1472892379&src=3&ver=1&signature=*vdD7RTCMj7Czn-4QEykZPJIibcS2NPs0utGydwCyxsAHT5wdaog4FDv3WJR5NQ9Gjsuy92lFk6rzjC*HKzvGzi4sHt3ePLZ*8gsoO88TfvHc5IEoy2Wy2PSM1QG7QXObkn1rS7VmjMXxXwmzkP08mJHzDWp08K1x*bxDO-jg4I=");
//		String textContent = SimulateWeiXin.stripHtml(htmlContent);
////		System.out.println("html content: \n" + htmlContent);
//		System.out.println("text content: " + textContent);
//		System.out.println("date: " + DateSource.getDate(textContent));
//		System.out.println("title: " + SimulateWeiXin.getTitle(htmlContent));
//		SimulateWeiXin.getImageURL(htmlContent);
	}
}