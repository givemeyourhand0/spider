package kevin.zhang;

import java.io.UnsupportedEncodingException;

import kevin.zhang.NlpirTest.CLibrary;

public class Split2
{
	private static String split(String original) throws UnsupportedEncodingException
	{
		String result = null;
		//Data文件的路径
		String argu = "./file/";
//		String system_charset = "UTF-8";
		int charset_type = 1;
		
		int init_flag = CLibrary.Instance.NLPIR_Init(argu, charset_type, "0");
		String nativeBytes = null;
		
		if (0 == init_flag)
		{
			nativeBytes = CLibrary.Instance.NLPIR_GetLastErrorMsg();
			System.err.println("初始化失败！fail reason is "+nativeBytes);
			return result;
		}
		
		// 要统一编码, 否则分词结果会产生乱码
		/**
		 * NLPIR_ParagraphProcess(Arg1,Arg2)
		 * 若Arg2==1,则分词结果中包含词的词性；
		 * 反之，若Arg2==0,则分词结果中不包含词性
		 */
		
		/**
		 * 政策法律法规的关键词
		 */
		CLibrary.Instance.NLPIR_AddUserWord("政策法律法规 n");
		CLibrary.Instance.NLPIR_AddUserWord("政策法律 n");
		CLibrary.Instance.NLPIR_AddUserWord("法律法规 n");
		CLibrary.Instance.NLPIR_AddUserWord("政策法规 n");
		CLibrary.Instance.NLPIR_AddUserWord("政策 n");
		CLibrary.Instance.NLPIR_AddUserWord("法律 n");
		CLibrary.Instance.NLPIR_AddUserWord("法规 n");
		CLibrary.Instance.NLPIR_AddUserWord("条例 n");
		CLibrary.Instance.NLPIR_AddUserWord("规定 n");
		CLibrary.Instance.NLPIR_AddUserWord("管理办法 n");
		
		/**
		 * 城市投资环境的关键词
		 */
		CLibrary.Instance.NLPIR_AddUserWord("城市投资环境 n");
		CLibrary.Instance.NLPIR_AddUserWord("投资环境 n");
		CLibrary.Instance.NLPIR_AddUserWord("投资效益 n");
		CLibrary.Instance.NLPIR_AddUserWord("市场竞争 n");
		CLibrary.Instance.NLPIR_AddUserWord("投资项目 n");
		CLibrary.Instance.NLPIR_AddUserWord("投资 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市竞争力 n");
		CLibrary.Instance.NLPIR_AddUserWord("招商引资 n");
		CLibrary.Instance.NLPIR_AddUserWord("产业发展 n");
		CLibrary.Instance.NLPIR_AddUserWord("市场 n");
		
		/**
		 * 城市交通
		 */
		CLibrary.Instance.NLPIR_AddUserWord("城市交通规划 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市交通设施 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市交通体系 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市交通建设 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市交通运输 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市轨道交通 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市交通网 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市交通 n");
		CLibrary.Instance.NLPIR_AddUserWord("公共交通 n");
		CLibrary.Instance.NLPIR_AddUserWord("交通 n");
		
		/**
		 * 城市综合管理水平提升的关键词
		 */
		CLibrary.Instance.NLPIR_AddUserWord("城市综合管理水平提升 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市综合管理 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市管理水平 n");
		CLibrary.Instance.NLPIR_AddUserWord("综合管理水平提升 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市建设水平 n");
		CLibrary.Instance.NLPIR_AddUserWord("综合管理 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市管理 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市建设 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市综合治理 n");
		CLibrary.Instance.NLPIR_AddUserWord("城市治理 n");
		
		if(original != null)
		result = CLibrary.Instance.NLPIR_ParagraphProcess(original, 0);
		//System.out.println("分词结果: " + result);
		
		// 退出, 释放资源
		NLPIR.NLPIR_Exit();
		return result;
	}
	
	/**
	 * 划分主题
	 * @param original 主题
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String[] keywordsSplit(String original) throws UnsupportedEncodingException
	{
		return split(original).split("\\s");
	}
	
	/**
	 * 划分文本内容
	 * @param original 文本内容
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String[] contentSplit(String original) throws UnsupportedEncodingException
	{
		return split(original).split("\\s");
	}
	
	/**
	 * 将字符串String按照空格划分后转换成字节数组byte[]形式
	 * @param args
	 * @throws UnsupportedEncodingException
	 */	
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		String input = "中华人民共和国劳动合同法实施条例_政府信息公开专栏            首页 政策 政府信息公开专栏          索 引 号： 000014349/2008-00141 主题分类： 劳动、人事、监察\\劳动就业   发文机关： 国务院 成文日期： 2008年09月18日   标　　题： 中华人民共和国劳动合同法实施条例   发文字号： 国令第535号 发布日期： 2008年09月19日       主 题 词： 劳动 合同制 法律 条例              66302008-09-19 13:01:50.0中华人民共和国劳动合同法实施条例2278劳动就业/enpproperty-->      中华人民共和国国务院令 第　535　号 　　《中华人民共和国劳动合同法实施条例》已经2008年9月3日国务院第25次常务会议通过，现予公布，自公布之日起施行。　　　　　　　　　　　　　　　　　　　　　　　　 总　　理　温家宝 　　　　　　　　　　　　　　　　　　　　　　　　 二○○八年九月十八日　　 中华人民共和国劳动合同法实施条例   第一章　总　　则 　　第一条　为了贯彻实施《中华人民共和国劳动合同法》（以下简称劳动合同法），制定本条例。 　　第二条　各级人民政府和县级以上人民政府劳动行政等有关部门以及工会等组织，应当采取措施，推动劳动合同法的贯彻实施，促进劳动关系的和谐。 　　第三条　依法成立的会计师事务所、律师事务所等合伙组织和基金会，属于劳动合同法规定的用人单位。 第二章　劳动合同的订立 　　第四条　劳动合同法规定的用人单位设立的分支机构，依法取得营业执照或者登记证书的，可以作为用人单位与劳动者订立劳动合同；未依法取得营业执照或者登记证书的，受用人单位委托可以与劳动者订立劳动合同。 　　第五条　自用工之日起一个月内，经用人单位书面通知后，劳动者不与用人单位订立书面劳动合同的，用人单位应当书面通知劳动者终止劳动关系，无需向劳动者支付经济补偿，但是应当依法向劳动者支付其实际工作时间的劳动报酬。 　　第六条　用人单位自用工之日起超过一个月不满一年未与劳动者订立书面劳动合同的，应当依照劳动合同法第八十二条的规定向劳动者每月支付两倍的工资，并与劳动者补订书面劳动合同；劳动者不与用人单位订立书面劳动合同的，用人单位应当书面通知劳动者终止劳动关系，并依照劳动合同法第四十七条的规定支付经济补偿。 　　前款规定的用人单位向劳动者每月支付两倍工资的起算时间为用工之日起满一个月的次日，截止时间为补订书面劳动合同的前一日。 　　第七条　用人单位自用工之日起满一年未与劳动者订立书面劳动合同的，自用工之日起满一个月的次日至满一年的前一日应当依照劳动合同法第八十二条的规定向劳动者每月支付两倍的工资，并视为自用工之日起满一年的当日已经与劳动者订立无固定期限劳动合同，应当立即与劳动者补订书面劳动合同。 　　第八条　劳动合同法第七条规定的职工名册，应当包括劳动者姓名、性别、公民身份号码、户籍地址及现住址、联系方式、用工形式、用工起始时间、劳动合同期限等内容。 　　第九条　劳动合同法第十四条第二款规定的连续工作满10年的起始时间，应当自用人单位用工之日起计算，包括劳动合同法施行前的工作年限。 　　第十条　劳动者非因本人原因从原用人单位被安排到新用人单位工作的，劳动者在原用人单位的工作年限合并计算为新用人单位的工作年限。原用人单位已经向劳动者支付经济补偿的，新用人单位在依法解除、终止劳动合同计算支付经济补偿的工作年限时，不再计算劳动者在原用人单位的工作年限。 　　第十一条　除劳动者与用人单位协商一致的情形外，劳动者依照劳动合同法第十四条第二款的规定，提出订立无固定期限劳动合同的，用人单位应当与其订立无固定期限劳动合同。对劳动合同的内容，双方应当按照合法、公平、平等自愿、协商一致、诚实信用的原则协商确定；对协商不一致的内容，依照劳动合同法第十八条的规定执行。 　　第十二条　地方各级人民政府及县级以上地方人民政府有关部门为安置就业困难人员提供的给予岗位补贴和社会保险补贴的公益性岗位，其劳动合同不适用劳动合同法有关无固定期限劳动合同的规定以及支付经济补偿的规定。 　　第十三条　用人单位与劳动者不得在劳动合同法第四十四条规定的劳动合同终止情形之外约定其他的劳动合同终止条件。 　　第十四条　劳动合同履行地与用人单位注册地不一致的，有关劳动者的最低工资标准、劳动保护、劳动条件、职业危害防护和本地区上年度职工月平均工资标准等事项，按照劳动合同履行地的有关规定执行；用人单位注册地的有关标准高于劳动合同履行地的有关标准，且用人单位与劳动者约定按照用人单位注册地的有关规定执行的，从其约定。 　　第十五条　劳动者在试用期的工资不得低于本单位相同岗位最低档工资的80%或者不得低于劳动合同约定工资的80%，并不得低于用人单位所在地的最低工资标准。 　　第十六条　劳动合同法第二十二条第二款规定的培训费用，包括用人单位为了对劳动者进行专业技术培训而支付的有凭证的培训费用、培训期间的差旅费用以及因培训产生的用于该劳动者的其他直接费用。 　　第十七条　劳动合同期满，但是用人单位与劳动者依照劳动合同法第二十二条的规定约定的服务期尚未到期的，劳动合同应当续延至服务期满；双方另有约定的，从其约定。 第三章　劳动合同的解除和终止 　　第十八条　有下列情形之一的，依照劳动合同法规定的条件、程序，劳动者可以与用人单位解除固定期限劳动合同、无固定期限劳动合同或者以完成一定工作任务为期限的劳动合同： 　　（一）劳动者与用人单位协商一致的； 　　（二）劳动者提前30日以书面形式通知用人单位的； 　　（三）劳动者在试用期内提前3日通知用人单位的； 　　（四）用人单位未按照劳动合同约定提供劳动保护或者劳动条件的； 　　（五）用人单位未及时足额支付劳动报酬的； 　　（六）用人单位未依法为劳动者缴纳社会保险费的； 　　（七）用人单位的规章制度违反法律、法规的规定，损害劳动者权益的； 　　（八）用人单位以欺诈、胁迫的手段或者乘人之危，使劳动者在违背真实意思的情况下订立或者变更劳动合同的； 　　（九）用人单位在劳动合同中免除自己的法定责任、排除劳动者权利的； 　　（十）用人单位违反法律、行政法规强制性规定的； 　　（十一）用人单位以暴力、威胁或者非法限制人身自由的手段强迫劳动者劳动的； 　　（十二）用人单位违章指挥、强令冒险作业危及劳动者人身安全的； 　　（十三）法律、行政法规规定劳动者可以解除劳动合同的其他情形。 　　第十九条　有下列情形之一的，依照劳动合同法规定的条件、程序，用人单位可以与劳动者解除固定期限劳动合同、无固定期限劳动合同或者以完成一定工作任务为期限的劳动合同： 　　（一）用人单位与劳动者协商一致的； 　　（二）劳动者在试用期间被证明不符合录用条件的； 　　（三）劳动者严重违反用人单位的规章制度的； 　　（四）劳动者严重失职，营私舞弊，给用人单位造成重大损害的； 　　（五）劳动者同时与其他用人单位建立劳动关系，对完成本单位的工作任务造成严重影响，或者经用人单位提出，拒不改正的； 　　（六）劳动者以欺诈、胁迫的手段或者乘人之危，使用人单位在违背真实意思的情况下订立或者变更劳动合同的； 　　（七）劳动者被依法追究刑事责任的； 　　（八）劳动者患病或者非因工负伤，在规定的医疗期满后不能从事原工作，也不能从事由用人单位另行安排的工作的； 　　（九）劳动者不能胜任工作，经过培训或者调整工作岗位，仍不能胜任工作的； 　　（十）劳动合同订立时所依据的客观情况发生重大变化，致使劳动合同无法履行，经用人单位与劳动者协商，未能就变更劳动合同内容达成协议的； 　　（十一）用人单位依照企业破产法规定进行重整的； 　　（十二）用人单位生产经营发生严重困难的； 　　（十三）企业转产、重大技术革新或者经营方式调整，经变更劳动合同后，仍需裁减人员的； 　　（十四）其他因劳动合同订立时所依据的客观经济情况发生重大变化，致使劳动合同无法履行的。 　　第二十条　用人单位依照劳动合同法第四十条的规定，选择额外支付劳动者一个月工资解除劳动合同的，其额外支付的工资应当按照该劳动者上一个月的工资标准确定。 　　第二十一条　劳动者达到法定退休年龄的，劳动合同终止。 　　第二十二条　以完成一定工作任务为期限的劳动合同因任务完成而终止的，用人单位应当依照劳动合同法第四十七条的规定向劳动者支付经济补偿。 　　第二十三条　用人单位依法终止工伤职工的劳动合同的，除依照劳动合同法第四十七条的规定支付经济补偿外，还应当依照国家有关工伤保险的规定支付一次性工伤医疗补助金和伤残就业补助金。 　　第二十四条　用人单位出具的解除、终止劳动合同的证明，应当写明劳动合同期限、解除或者终止劳动合同的日期、工作岗位、在本单位的工作年限。 　　第二十五条　用人单位违反劳动合同法的规定解除或者终止劳动合同，依照劳动合同法第八十七条的规定支付了赔偿金的，不再支付经济补偿。赔偿金的计算年限自用工之日起计算。 　　第二十六条　用人单位与劳动者约定了服务期，劳动者依照劳动合同法第三十八条的规定解除劳动合同的，不属于违反服务期的约定，用人单位不得要求劳动者支付违约金。 　　有下列情形之一，用人单位与劳动者解除约定服务期的劳动合同的，劳动者应当按照劳动合同的约定向用人单位支付违约金： 　　（一）劳动者严重违反用人单位的规章制度的； 　　（二）劳动者严重失职，营私舞弊，给用人单位造成重大损害的； 　　（三）劳动者同时与其他用人单位建立劳动关系，对完成本单位的工作任务造成严重影响，或者经用人单位提出，拒不改正的； 　　（四）劳动者以欺诈、胁迫的手段或者乘人之危，使用人单位在违背真实意思的情况下订立或者变更劳动合同的； 　　（五）劳动者被依法追究刑事责任的。 　　第二十七条　劳动合同法第四十七条规定的经济补偿的月工资按照劳动者应得工资计算，包括计时工资或者计件工资以及奖金、津贴和补贴等货币性收入。劳动者在劳动合同解除或者终止前12个月的平均工资低于当地最低工资标准的，按照当地最低工资标准计算。劳动者工作不满12个月的，按照实际工作的月数计算平均工资。 第四章　劳务派遣特别规定 　　第二十八条　用人单位或者其所属单位出资或者合伙设立的劳务派遣单位，向本单位或者所属单位派遣劳动者的，属于劳动合同法第六十七条规定的不得设立的劳务派遣单位。 　　第二十九条　用工单位应当履行劳动合同法第六十二条规定的义务，维护被派遣劳动者的合法权益。 　　第三十条　劳务派遣单位不得以非全日制用工形式招用被派遣劳动者。 　　第三十一条　劳务派遣单位或者被派遣劳动者依法解除、终止劳动合同的经济补偿，依照劳动合同法第四十六条、第四十七条的规定执行。 　　第三十二条　劳务派遣单位违法解除或者终止被派遣劳动者的劳动合同的，依照劳动合同法第四十八条的规定执行。 第五章　法律责任 　　第三十三条　用人单位违反劳动合同法有关建立职工名册规定的，由劳动行政部门责令限期改正；逾期不改正的，由劳动行政部门处2000元以上2万元以下的罚款。 　　第三十四条　用人单位依照劳动合同法的规定应当向劳动者每月支付两倍的工资或者应当向劳动者支付赔偿金而未支付的，劳动行政部门应当责令用人单位支付。 　　第三十五条　用工单位违反劳动合同法和本条例有关劳务派遣规定的，由劳动行政部门和其他有关主管部门责令改正；情节严重的，以每位被派遣劳动者1000元以上5000元以下的标准处以罚款；给被派遣劳动者造成损害的，劳务派遣单位和用工单位承担连带赔偿责任。 第六章　附　　则 　　第三十六条　对违反劳动合同法和本条例的行为的投诉、举报，县级以上地方人民政府劳动行政部门依照《劳动保障监察条例》的规定处理。 　　第三十七条　劳动者与用人单位因订立、履行、变更、解除或者终止劳动合同发生争议的，依照《中华人民共和国劳动争议调解仲裁法》的规定处理。 　　第三十八条　本条例自公布之日起施行。  ";
		String[]result = Split2.contentSplit(input);
		System.out.println(result.length);
		for(int i=0;i<result.length;i++){
			System.out.println(result[i]);
		}
	}
}