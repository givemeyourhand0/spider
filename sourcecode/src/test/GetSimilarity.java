package test;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import kevin.zhang.Split2;


public class GetSimilarity 
{
	public static final String DRIVER = "org.postgresql.Driver";
	private static String url="jdbc:postgresql://10.170.58.226:5432/webdatatest";
	private static String username="postgres";
	private static String sec="love";

	/**
	 * 获取“城市综合管理水平提升”与文本内容的相似度
	 * @param content 文本内容
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static double getSimilarity(String content) throws UnsupportedEncodingException
	{
		String category1="城市交通";
		//double similarity=CosineSimilarity.cosineSimilarity(category1, content);
		double similarity=Similarity(category1, content);
	   	return similarity;
	}
	
	
	public static double Similarity(String keywords,String contents) throws UnsupportedEncodingException
	{
		int totalNumber=0;
		String[] keyword = Split2.keywordsSplit(keywords);
		String[] content = Split2.contentSplit(contents);
		
		int[] frequency = new int[keyword.length];
		
		
		for(int i = 0;i < content.length;i ++)
		{
			for(int j = 0;j < keyword.length;j ++)
			{
				if(content[i].equals(keyword[j]))
				{
					frequency[j] += 1;
				}			
			}
		}
		for(int i=0;i<keyword.length;i++)
			totalNumber=totalNumber+frequency[i];
		return totalNumber;
	}
	
	
	 /**
		 * 往文件filename中写入字符串output
		 * @param filename 文件的名称
		 * @param output 字符串的名称
		 */
	    public static void writeFile(String filename,String output)
	    {
			FileWriter writer=null;
			try 
			{
				writer=new FileWriter(filename, true);
				writer.write(output+"\r\n");
				writer.flush();
			} 
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			finally 
			{
				if (writer!= null) {
					try 
					{
						writer.close();
					} 
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws SQLException, UnsupportedEncodingException
	{
		List<Content> contentList=new ArrayList<Content>();
		LinkedList<Calculate> calculateList=new LinkedList<Calculate>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String tableName="tbl_main";
		String selectSql = "select * from "+tableName;
		try
		{
			Class.forName(DRIVER);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		try 
		{
			conn=DriverManager.getConnection(url,username,sec);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try 
		{
			stmt=conn.createStatement();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try 
		{
			rs=stmt.executeQuery(selectSql);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	   	while(rs.next())
	   	{
	   		String url=rs.getString("url");
	   		String title=rs.getString("title");
	   		String content=rs.getString("content");
	   		Content allContent=new Content(url,title,content);
	   		double cosSimilarity=getSimilarity(content);
	   		//contentList.add(allContent);
	   		Calculate allCalculate=new Calculate(allContent,cosSimilarity);
	   		calculateList.add(allCalculate);
	   		
	   	}
	   /*	System.out.println("calculateList表的大小为："+calculateList.size());
 	    for(int i=0;i<calculateList.size();i++)
 	    {
 	    	System.out.println("第"+i+"个url："+calculateList.get(i).getContent().getURL());
 	    	System.out.println("第"+i+"个title："+calculateList.get(i).getContent().getTitle());
 	    	System.out.println("第"+i+"个content："+calculateList.get(i).getContent().getContent());
 	    	System.out.println("第"+i+"个cosSimilarity："+calculateList.get(i).getConsine());
 	    }*/
 	   LinkedList<Calculate> result = new LinkedList<Calculate>();
 	   result=OrderByConsine.order(calculateList);
 	   for(int i=0;i<200;i++)
 	   {
 		   String all=result.get(i).toString();
		   writeFile("F:\\城市交通相似度排序2.txt",all);
 	   }
 	   
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}