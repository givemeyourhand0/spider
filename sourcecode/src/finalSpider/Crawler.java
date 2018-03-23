package finalSpider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Crawler
{	
	/**
	 * 通过链接获取网页的全部内容
	 * @param htmlurl 网页的链接
	 * @return 返回字符串形式的网页脚本源码，且去除了换行符
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
	 * 以HTML文件的形式保存网页
	 * @param htmlurl 网页链接
	 * @param fileName 存储网页源码的文件名称，字符串形式
	 * @throws IOException
	 */
	public static void saveAsHTML(String htmlurl,String fileName) throws IOException
	{
        String htmlContent = null;
        Document doc = null;
        doc = Jsoup.connect(htmlurl).timeout(20000).get();
        fileName = fileName + ".html";
        htmlContent = doc.toString();
        
        /**
         * Get charset
         */
        Elements charsetElements = doc.getElementsByAttribute("charset");
        String charset = "";
        
        if(charsetElements.size() > 0)
        {
        	org.jsoup.nodes.Element element = charsetElements.get(0);
        	charset = element.attr("charset");
        }
        
        System.out.println("charset = " + charset);
        
        if(charset.isEmpty())
        {
        	charset = new String("GB2312");
        }
        System.out.println("charset = " + charset);
        
        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(fileName),charset);
        os.write(htmlContent);
        os.flush();
        os.close();
	}
	
	
	/**
	 * 视频链接
	 * @throws IOException 
	 */
	public static LinkedList<String> getVedioURL(String htmlurl) throws IOException
	{
		System.out.println("Running getVedioURL() function......");
        Document doc = null;
        doc = Jsoup.connect(htmlurl).timeout(20000).get();
        /**
         * 视频的链接
         */
        Elements href = doc.select("embed");
        
        LinkedList<String> result = new LinkedList<String>();
        System.out.println("href.size() = " + href.size());
        for(int i = 0;i < href.size();i ++)
        {	
        	if(!href.get(i).attr("src").equals("") && !href.get(i).attr("src").equals(null))
        	{
        		result.add(href.get(i).absUrl("src"));
        	}
        }
        
        return result;
	}
	
	/**
	 * Get vedio urls
	 * @throws IOException 
	 */
	public static LinkedList<String> getVedioURL2(String htmlurl) throws IOException
	{
		URL url = new URL(htmlurl);
		LinkedList<String> result = new LinkedList<String>();
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line = null;
		while((line = bf.readLine()) != null)
		{
			result.add(line);
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * @param s 字符串形式表示的网页文本内容
	 * @return 页面的标题：通过<title>...</title>标签获取
	 */
	public static String getTitle(String s)
	{
        String regex; 
        String title = ""; 
        List<String> list = new ArrayList<String>(); 
        regex = "<title>.*?</title>"; 
        Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
        Matcher ma = pa.matcher(s);
        while(ma.find())
        {
            list.add(ma.group()); 
        } 
        for(int i = 0; i < list.size(); i++)
        { 
            title = title + list.get(i); 
        }
        title = title.replace("<title>","").replace("</title>","").replace("&lt;","").replace("br","").replace("&gt","");
        return title;
    } 
	
	/**
	 * 获取网页正文内容，并以字符串的形式返回
	 * @param htmlUrl 网页链接
	 * @return 网页正文内容：通过<p>...</p>标签获取
	 * @throws IOException
	 */
	public static String getContent(String htmlUrl) throws IOException
	{
		Document doc = null;
		doc = Jsoup.connect(htmlUrl).timeout(10000).get();
		Elements content_p = doc.getElementsByTag("P");
		return content_p.text();
	}
	
	/**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
	public static LinkedList<String> readFileByLines(String fileName)
    {
        File file = new File(fileName);
        BufferedReader reader = null;
        LinkedList<String> list = new LinkedList<String>();
        try
        {
        	//以行为单位读取文件，一次读取一整行
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
//            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while((tempString = reader.readLine()) != null)
            {
                // 显示行号
                list.add(tempString);
//                line++;
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e1)
                {
                	//Do nothing!
                }
            }
        }
        
        return list;
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

			textStr = htmlStr.trim();
			textStr = textStr.replaceAll("&lt;br","");
			textStr = textStr.replaceAll(";newline;","");//Changed by ZY 2016-7-31:It's "" not "\n" to instead ";newline;"
			
			textStr = textStr.replaceAll("\"", "");
		}
		catch(Exception e)
		{
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;
	}
	
	public static void main(String arg[]) throws IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
	{
//		Crawler.saveAsHTML("http://hudong.xa.gov.cn/usl/hdjl/interviewInfo.aspx?id=1632","file");
//		LinkedList<String> result = Crawler.getVedioURL("http://politics.cntv.cn/special/gwyvideo/likeqiang/201609/2016092503/index.shtml");
//		
//		System.out.println("video url:");
//		for(String element : result)
//			System.out.println(element);
//		
//		//
//		//http://www.shaanxi.gov.cn/0/1/9/40/218681.htm
//		String url = "http://www.shaanxi.gov.cn/0/1/9/39/218754.htm";
//		System.out.println("文本：\n" + Crawler.stripHtml(Crawler.getHtmlContent(url)));
		
//		Crawler.saveAsHTML("http://www.baqiao.gov.cn/info/news/111jiandang90/nr_hj/70367.htm", "baqiao");
		
		System.out.println(Crawler.stripHtml(Crawler.getHtmlContent("http://www.baqiao.gov.cn/info/news/ajiudafuwu/jiuye/nr_jiuye/58032.htm")));;
		
//		System.out.println(Crawler.getHtmlContent("http://www.gov.cn/premier/2016-09/30/content_5114368.htm"));
//		Crawler.saveAsHTML("http://www.gov.cn/premier/2016-09/30/content_5114368.htm", "1");
	}
}