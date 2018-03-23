package SubLinkFetch;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.LinkedList;
import java.util.Timer;  
import java.util.TimerTask;

import com.jcraft.jsch.ChannelSftp;

import outputWithXML.ReadFromFile;
public class TimerFetch extends TimerTask{  
		 private Timer timer = new Timer();   
	   //  private ProgressBar progressBar;
			    @Override  
		 public void run() {  
			    	//执行任务
			        // TODO Auto-generated method stub  
			     // System.out.println("备份程序运行……"); 
			     //   progressBar=new ProgressBar(); 
			    	 SFTPUtils sf = new SFTPUtils("222.25.176.34", 22, "root","111111");
//			         String host = "222.25.176.34";
//			         int port = 22;
//			         String username = "root";
//			         String password = "111111";
			        // ChannelSftp sftp =sf.connect(host, port, username, password); 
			    	LinkedList<LinkedList<String>> result = ReadFromFile.getInfo("file123.txt");
				  for( int i=0;i<result.size();i++)
					{    
					     int temp = (i+1) * (100/result.size());
					     String crawlerPath=result.get(i).get(0); 
					     String govName=result.get(i).get(1); 
					     String seed=result.get(i).get(2);
					     String regrex=result.get(i).get(3);
					     String subLinkRegrex=result.get(i).get(4);
					     String imagePath=result.get(i).get(5);
					     String htmlSourcePath=result.get(i).get(6);							
//				String govName="陕西省政府";
//				String seed="http://www.shaanxi.gov.cn/";
//				String regrex="http://www.shaanxi.gov.cn/.*\\http://knews.shaanxi.gov.cn/.*\\http://www.gov.cn/.*";
//				String subLinkRegrex="http://(www.shaanxi.gov.cn/0|knews.shaanxi.gov.cn/0|www.gov.cn/xinwen|www.gov.cn/zhengce|www.gov.cn/guowuyuan|www.gov.cn/premier).*";
//				
				       try {
					      ShaanXiGov crawler=new ShaanXiGov(crawlerPath, true, govName, seed, regrex, subLinkRegrex,imagePath,htmlSourcePath);
					      sf.bacthUploadFile("/opt/"+imagePath+"/",System.getProperty("user.dir")+"\\"+imagePath+"\\",true);
					      sf.bacthUploadFile("/opt/"+htmlSourcePath+"/",System.getProperty("user.dir")+"\\"+htmlSourcePath+"\\",true);
					      Thread.sleep(1000);
				       } catch (Exception e2) {
					     // TODO Auto-generated catch block
					    e2.printStackTrace();
				       }
				    	}
			         printchar();  
			    }  
			public void start()  
			    {  
				 //在Date指定的特定时刻之后,每隔1小时执行TimerTask的任务一次 
				Date d2 = new Date(System.currentTimeMillis()+1000);
				  timer.schedule(this,d2,1*1000*60*60);
 
			    }  
			 public void printchar()  
			    {  
				   // progressBar=new ProgressBar(); 
			        Date date = new Date();  
			        System.out.println("定时执行程序:"+new SimpleDateFormat().format(date.getTime()));  
			    }
			 public static void main(String[] args) {  
			        // TODO Auto-generated method stub  
				        TimerFetch t = new TimerFetch();  
				        t.start();  
				    }
			}  
