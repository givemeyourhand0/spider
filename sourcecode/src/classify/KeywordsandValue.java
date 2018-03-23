package classify;

/**
 * 分类中的关键字和值对
 * @author Administrator
 *
 */
public class KeywordsandValue
{
	private String keywords;
	private int value;
	
	/**
	 * Default constructor
	 */
	public KeywordsandValue()
	{
		//Do nothing
	}
	
	/**
	 * Constructor
	 */
	public KeywordsandValue(String keywords,int value)
	{
		this.keywords = keywords;
		this.value = value;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public String getKeywords()
	{
		return this.keywords;
	}
}