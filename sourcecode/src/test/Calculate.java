package test;

@SuppressWarnings("rawtypes")
public class Calculate implements Comparable
{
	private Content content;
	private double consine;
	
	public Calculate()
	{
		//Do nothing!
	}
	
	public Calculate(Content content,double consine)
	{
		this.content = content;
		this.consine = consine;
	}
	
	public Calculate(String url,String title,String content,double consine)
	{
		this.content = new Content(url,title,content);
		this.consine = consine;
	}
	
	public Content getContent()
	{
		return content;
	}
	
	public double getConsine()
	{
		return consine;
	}

	@Override
	public int compareTo(Object arg0)
	{
		Calculate para = (Calculate) arg0;
		
		if(this.consine - para.consine > 0)
		{
			return -1;
		}
		else if(this.consine - para.consine < 0)
		{
			return 1;
		}
		
		return 0;
	}
	
	public String toString()
	{
		return content.getURL()+ ", " + this.consine + ", " + content.getTitle() + ", " + content.getContent();
	}
}