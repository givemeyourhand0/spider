package transformUrlToFileName;

public class TransUrlToFileName
{
	/**
	 * 由于网页链接中可能存在不能用于文件名的字符，所以，在将
	 * 链接作为文件名时，首先需要将链接中的无法作为文件名的字符
	 * 去掉，在此处我们的规则是：将链接中无法作为文件名的字符
	 * 去掉。
	 * 在Windows系统中，不能作为文件名的字符如下(共九个)：
	 * ? * \ / < > : " |
	 * @param url
	 * @return
	 */
	public static String transUrlToFileName(String url)
	{
		String fileName = url;
		fileName = fileName.replaceAll("\\?", "").replaceAll("\\*", "").replaceAll("/", "")
				.replaceAll("<", "").replaceAll(">", "").replaceAll(":", "").replaceAll("\"", "").replaceAll("|", "");
		return fileName;
	}
}
