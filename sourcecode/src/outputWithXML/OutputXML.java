package outputWithXML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 将从网页中获取的标题、时间、内容、来源、所属地、链接以
 * XML文件的形式存储起来，各元素表示内容如下：
 * title:标题
 * time:时间
 * content:内容
 * source:来源
 * location:所属地
 * url:链接
 * @author ZY
 */
public class OutputXML
{
	public static void outputXML(String title,String time,String content,String source,String location,String url,String fileName,String category,String categoryValue,String categoryKeywords) throws ParserConfigurationException, TransformerFactoryConfigurationError, FileNotFoundException, TransformerException
	{
		//注意下面DocumentBuilder实例的建立方法：通过DocumentBuilderFactory.newInstance().newDocumentBuilder()方法建立
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		
		Element rootElement = doc.createElement("information");
		
		Element childNode_URL = doc.createElement("url");
		Element childNode_Title = doc.createElement("title");
		Element childNode_Content = doc.createElement("content");
		Element childNode_Source = doc.createElement("source");
		Element childNode_Location = doc.createElement("location");
		Element childNode_Time = doc.createElement("time");
		Element childNode_Category = doc.createElement("category");
		Element childNode_CategoryValue = doc.createElement("categoryValue");
		Element childNode_CategoryKeywords = doc.createElement("categoryKeywords");
		
		childNode_URL.setTextContent(url);
		childNode_Title.setTextContent(title);
		childNode_Content.setTextContent(content);
		childNode_Source.setTextContent(source);
		childNode_Location.setTextContent(location);
		childNode_Time.setTextContent(time);
		childNode_Category.setTextContent(category);
		childNode_CategoryValue.setTextContent(categoryValue);
		childNode_CategoryKeywords.setTextContent(categoryKeywords);
		
		doc.appendChild(rootElement);
		
		rootElement.appendChild(childNode_URL);
		rootElement.appendChild(childNode_Title);
		rootElement.appendChild(childNode_Content);
		rootElement.appendChild(childNode_Source);
		rootElement.appendChild(childNode_Location);
		rootElement.appendChild(childNode_Time);
		rootElement.appendChild(childNode_Category);
		rootElement.appendChild(childNode_CategoryValue);
		rootElement.appendChild(childNode_CategoryKeywords);
		
		Transformer transfer = TransformerFactory.newInstance().newTransformer();
		transfer.setOutputProperty(OutputKeys.INDENT, "yes");
		transfer.setOutputProperty(OutputKeys.METHOD, "xml");
		transfer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(new File(fileName))));
	}
	
	
	/**
	 * 将获取微信的内容保存成xml格式，add by lyj
	 * @param source 微信公众号名称
	 * @param content 文本内容
	 * @param time 发布时间
	 * @param title 文章标题
	 * @param image 图片链接
	 * @param fileName 存入的文件名称
	 * @throws ParserConfigurationException
	 * @throws TransformerFactoryConfigurationError
	 * @throws FileNotFoundException
	 * @throws TransformerException
	 */
	public static void outputXML(String source,String content,String time,String title,String image,String fileName) throws ParserConfigurationException, TransformerFactoryConfigurationError, FileNotFoundException, TransformerException
	{
		//注意下面DocumentBuilder实例的建立方法：通过DocumentBuilderFactory.newInstance().newDocumentBuilder()方法建立
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		
		Element rootElement = doc.createElement("information");
		
		Element childNode_Source = doc.createElement("source");
		Element childNode_Time = doc.createElement("time");
		Element childNode_Title = doc.createElement("title");
		Element childNode_Content = doc.createElement("content");
		Element childNode_Image = doc.createElement("image");
		
		childNode_Source.setTextContent(source);
		childNode_Time.setTextContent(time);
		childNode_Title.setTextContent(title);
		childNode_Content.setTextContent(content);
		childNode_Image.setTextContent(image);
		
		
		doc.appendChild(rootElement);
		
		rootElement.appendChild(childNode_Source);
		rootElement.appendChild(childNode_Time);
		rootElement.appendChild(childNode_Title);
		rootElement.appendChild(childNode_Content);
		rootElement.appendChild(childNode_Image);
		
		Transformer transfer = TransformerFactory.newInstance().newTransformer();
		transfer.setOutputProperty(OutputKeys.INDENT, "yes");
		transfer.setOutputProperty(OutputKeys.METHOD, "xml");
		transfer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(new File(fileName))));
	}
	
	
	public static void outputXML(String imageNumber,String image,String videoResource,String location,String fileName) throws ParserConfigurationException, TransformerFactoryConfigurationError, FileNotFoundException, TransformerException
	{
		//注意下面DocumentBuilder实例的建立方法：通过DocumentBuilderFactory.newInstance().newDocumentBuilder()方法建立
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		
		Element rootElement = doc.createElement("information");
		
		Element childNode_ImageNumber = doc.createElement("imageNumber");
		Element childNode_Image = doc.createElement("image");
		Element childNode_VideoResource = doc.createElement("videoResource");
		
		childNode_ImageNumber.setTextContent(imageNumber);
		childNode_Image.setTextContent(image);
		childNode_VideoResource.setTextContent(videoResource);
		
		
		doc.appendChild(rootElement);
		
		rootElement.appendChild(childNode_ImageNumber);
		rootElement.appendChild(childNode_Image);
		rootElement.appendChild(childNode_VideoResource);
		
		Transformer transfer = TransformerFactory.newInstance().newTransformer();
		transfer.setOutputProperty(OutputKeys.INDENT, "yes");
		transfer.setOutputProperty(OutputKeys.METHOD, "xml");
		transfer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(new File(fileName))));
	}	
}