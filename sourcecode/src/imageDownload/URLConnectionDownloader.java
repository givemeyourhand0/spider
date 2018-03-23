package imageDownload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.Page;
import SubLinkFetch.ShaanXiGov;

/**
* 使用URLConnection下载文件或图片并保存到本地。
*
* @author lyj
*/
public class URLConnectionDownloader 
{
	
	/**
	 * 下载文件到本地
	 * @param urlString 被下载的文件地址
	 * @param filename 本地文件名
	 * @throws Exception 各种异常
	 */
	public static void download(String urlString, String filename) throws Exception 
	{
	    // 构造URL
	    URL url = new URL(urlString);
	    // 打开连接
	    URLConnection con = url.openConnection();
	    // 输入流
	    InputStream is = con.getInputStream();
	    // 1K的数据缓冲
	    byte[] bs = new byte[1024*10];
	    // 读取到的数据长度
	    int len;
	    // 输出的文件流
	    OutputStream os = new FileOutputStream(filename);
	    // 开始读取
	    while ((len = is.read(bs)) != -1) {
	      os.write(bs, 0, len);
	    }
	    // 完毕，关闭所有链接
	    os.close();
	    is.close();
	   } 
	
	/**
	 * 下载文件到本地
	 * @param page Page类型获取图片格式
	 * @param urlString 被下载的文件地址
	 * @param contentId 网页主码编号
	 * @param imaId 图片编号
	 * @throws Exception 各种异常
	 */
	@SuppressWarnings("unused")
	public static void downloadImage(Page page,String url,int contentId,int imgId,String fileName) throws Exception 
	{
		CrawlDatum c=new CrawlDatum(url);
   		Page imgPage=null;
		imgPage=new Page(c,ShaanXiGov.getResponseT(c));
   	    //下载图片
   		String contentType = imgPage.getResponse().getContentType();
        String extensionName=contentType.split("/")[1];
        String imageFileName=null;
        if(extensionName.equalsIgnoreCase("jpeg")||extensionName.equalsIgnoreCase("bmp")
        		||extensionName.equalsIgnoreCase("gif")||extensionName.equalsIgnoreCase("jpeg2000")
        		||extensionName.equalsIgnoreCase("png")||extensionName.equalsIgnoreCase("svg")
        		||extensionName.equalsIgnoreCase("tiff")||extensionName.equalsIgnoreCase("psd")
        		||extensionName.equalsIgnoreCase("pcx")||extensionName.equalsIgnoreCase("dxf")
        		||extensionName.equalsIgnoreCase("wmf")||extensionName.equalsIgnoreCase("emf")
        		||extensionName.equalsIgnoreCase("flic")||extensionName.equalsIgnoreCase("eps")
        		||extensionName.equalsIgnoreCase("tga"))
        {
        	try 
	            {
        			download(url, "./"+fileName+"/"+contentId+"-"+imgId+"."+extensionName);
	                System.out.println("保存图片 "+imgPage.getUrl()+" 到 "+"./download/"+contentId+"-"+imgId+"."+extensionName);
	            } 
	            catch (Exception ex) 
	            {
	                throw new RuntimeException(ex);
	            }
        }
		
		
	}
	
	@SuppressWarnings("unused")
	public static void downloadImage(Page page,String url, int imgId) throws Exception 
	{
		CrawlDatum c=new CrawlDatum(url);
   		Page imgPage=null;
		imgPage=new Page(c,ShaanXiGov.getResponseT(c));
   	    //下载图片
   		String contentType = imgPage.getResponse().getContentType();
        String extensionName=contentType.split("/")[1];
        String imageFileName=null;
        if(extensionName.equalsIgnoreCase("jpeg")||extensionName.equalsIgnoreCase("bmp")
        		||extensionName.equalsIgnoreCase("gif")||extensionName.equalsIgnoreCase("jpeg2000")
        		||extensionName.equalsIgnoreCase("png")||extensionName.equalsIgnoreCase("svg")
        		||extensionName.equalsIgnoreCase("tiff")||extensionName.equalsIgnoreCase("psd")
        		||extensionName.equalsIgnoreCase("pcx")||extensionName.equalsIgnoreCase("dxf")
        		||extensionName.equalsIgnoreCase("wmf")||extensionName.equalsIgnoreCase("emf")
        		||extensionName.equalsIgnoreCase("flic")||extensionName.equalsIgnoreCase("eps")
        		||extensionName.equalsIgnoreCase("tga"))
        {
        	//imageFileName=imageId.incrementAndGet()+"."+extensionName;
        	try 
	            {
	            	download(url, "./download/"+imgId+"."+extensionName);
	                System.out.println("保存图片 "+imgPage.getUrl()+" 到 "+"./download/"+imgId+"."+extensionName);
	            } 
	            catch (Exception ex) 
	            {
	                throw new RuntimeException(ex);
	            }
        }
		
		
	}
	
	public static void saveAsHtml(String url,int contentId,String fileName) throws IOException
	{
		 String htmlContent = null;
	        Document doc = null;
	        doc = Jsoup.connect(url).timeout(20000).get();
	        htmlContent = doc.toString();
	        
	        String saveFileName="./"+fileName+"/"+contentId+".html";
	        
	        /**
	         * Get charset
	         */
	        //Elements charsetElements = doc.select("meta");
	        Elements charsetElements = doc.getElementsByAttribute("charset");
	        String charset = "";
	        
	        if(charsetElements.size() > 0)
	        {
	        	org.jsoup.nodes.Element element = charsetElements.get(0);
	        	charset = element.attr("charset");
	        }
	        
	        if(charset.equals(""))
	        {
	        	charset = "GB2312";
	        }
	        
	        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(saveFileName),charset);
	        os.write(htmlContent);
	        os.flush();
	        os.close();
	}
	
	
	public static void main(String[] args) throws Exception
	{
		String url="http://www.baqiao.gov.cn/info/news/111jiandang90/nr_hj/70401.htm";
		saveAsHtml(url,1,"111");		
	}
}