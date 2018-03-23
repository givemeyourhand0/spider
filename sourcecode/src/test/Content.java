package test;

public class Content
{
	private String url;
	private String title;
	private String content;
	
	/**
	 * default construction
	 */
	public Content()
	{
		//Do nothing!
	}
	
	/**
	 * construction with parameters
	 */
	public Content(String url,String title,String content)
	{
		this.url = url;
		this.title = title;
		this.content = content;
	}
	
	public String getURL()
	{
		return this.url;
	}
	
	public String getContent()
	{
		return this.content;
	}
	
	public String getTitle()
	{
		return this.title;
	}
}