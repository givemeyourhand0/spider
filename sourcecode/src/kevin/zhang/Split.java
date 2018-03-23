//package kevin.zhang;
//
//import java.io.UnsupportedEncodingException;
//
//import code.NlpirTest.CLibrary;
//
//public class Split
//{
//	@SuppressWarnings("unused")
//	private static byte[] split(String original) throws UnsupportedEncodingException
//	{
//		byte[] result = null;
//		// 创建接口实例
//		NLPIR nlpir = new NLPIR();
//				
//		// NLPIR_Init方法第二个参数设置0表示编码为GBK, 1表示UTF8编码(此处结论不够权威)
//		if(!NLPIR.NLPIR_Init("./file/".getBytes("UTF-8"), 1))//utf-8
//		{
//			System.out.println("NLPIR初始化失败...");
//			return result;
//		}
//		
//		String argu = "./file/";//D:\\NLPIR
//		
//		// 要统一编码, 否则分词结果会产生乱码
//		/**
//		 * NLPIR_ParagraphProcess(Arg1,Arg2)
//		 * 若Arg2==1,则分词结果中包含词的词性；
//		 * 反之，若Arg2==0,则分词结果中不包含词性
//		 */
//		CLibrary.Instance.NLPIR_AddUserWord("投资环境 n");
//		CLibrary.Instance.NLPIR_AddUserWord("综合管理 n");
//		CLibrary.Instance.NLPIR_AddUserWord("水平提升 n");
//		
//		result = nlpir.NLPIR_ParagraphProcess(original.getBytes("UTF-8"), 0);
////		CLibrary.Instance.NLPIR_ParagraphProcess(original,0);
////		String result_string = CLibrary.Instance.NLPIR_ParagraphProcess(original, 0);
//		System.out.println("分词结果: " + new String(result, "UTF-8"));
////		System.out.println("分词结果： " + result_string);
//		
//		// 退出, 释放资源
//		NLPIR.NLPIR_Exit();
//		return result;
//	}
//	
//	/**
//	 * 划分主题
//	 * @param original 主题
//	 * @return
//	 * @throws UnsupportedEncodingException
//	 */
//	public static byte[] keywordsSplit(String original) throws UnsupportedEncodingException
//	{
//		return split(original);
//	}
//	
//	/**
//	 * 划分文本内容
//	 * @param original 文本内容
//	 * @return
//	 * @throws UnsupportedEncodingException
//	 */
//	public static byte[] contentSplit(String original) throws UnsupportedEncodingException
//	{
//		return split(original);
//	}
//}