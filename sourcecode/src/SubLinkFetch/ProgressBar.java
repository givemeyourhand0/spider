package SubLinkFetch;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.http.impl.io.SocketOutputBuffer;

import outputWithXML.ReadFromFile;

/**
 * 进度条显示程序运行进度
 * @author Administrator
 *
 */

public class ProgressBar implements ActionListener, ChangeListener
{
	JFrame frame = null;
	JProgressBar progressbar;
	JLabel label;
	Timer timer;
	JButton button;
	JComboBox combobox;
	private String[] govName={"陕西省人民政府","西安市人民政府","咸阳市人民政府","宝鸡市人民政府","汉中市人民政府","渭南市人民政府","安康市人民政府","榆林市人民政府","商洛市人民政府","延安市人民政府","铜川市人民政府"};
	private int govNumber;
	//延安市人民政府 http://yanan.gov.cn/ http://yanan.gov.cn/.* http://yanan.gov.cn/info/1278/.* YanAnGovImage YanAnGovHtmlSource
	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public ProgressBar()
	{
		frame = new JFrame("爬取数据实例");
		frame.setBounds(500, 200, 500, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		Container contentPanel = frame.getContentPane();
		
		label = new JLabel("点击开始爬取",JLabel.CENTER);
		
		progressbar = new JProgressBar();
		progressbar.setOrientation(JProgressBar.HORIZONTAL);
		progressbar.setMinimum(0);
		progressbar.setMaximum(100);
		progressbar.setValue(0);
		progressbar.setStringPainted(true);
		progressbar.addChangeListener(this);
		progressbar.setPreferredSize(new Dimension(300,20));
		progressbar.setBackground(Color.pink);
		
		JPanel panel = new JPanel();
		combobox=new JComboBox<String>(govName);
		combobox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e)
			{
				govNumber=combobox.getSelectedIndex(); //获得政府编号
			}});
		button = new JButton("爬取数据");
		button.setForeground(Color.blue);
		button.addActionListener(this);
		
		panel.add(combobox);
		panel.add(button);
		
		timer = new Timer(100,this);
		frame.setLayout(new BorderLayout());
		 contentPanel.add(panel, BorderLayout.NORTH);
		 contentPanel.add(label,BorderLayout.CENTER);
		 contentPanel.add(progressbar,BorderLayout.SOUTH);
		 frame.setVisible(true);
	}
	
	@Override
	public void stateChanged(ChangeEvent e)
	{
		double value = progressbar.getValue();
		if(e.getSource() == progressbar)
		{
			label.setText("目前已完成进度：" + Double.toString(value) + "%");
			label.setForeground(Color.blue);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == button)
		{
			/**
			 * progressbar.setValue(int): 参数类型必须是int类型
			 * 因为点击运行按钮之后线程一直处于阻塞状态，所以需要将代码放在一个线程里面运行
			 */
//			new Thread(new Runnable(){
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					for(int i = 0;i < 10;i ++)
//					{
//						int temp = i * 10;
//						try
//						{
//							Thread.sleep(5000);
//						}
//						catch (InterruptedException e1)
//						{
//							e1.printStackTrace();
//						}
//						progressbar.setValue(temp);
//					}
//				}
//			}).start();
			new Thread(new Runnable()
			{
				
				@Override
				public void run()
				{
					/**
					 * 开始运行爬虫程序，代码放在此处，那么开始运行之后怎么知道已经运行了多少个门户网站
					 * 
					 * 以下代码只是测试进度条是否会被阻塞以及能否正确显示
					 */
					String Path = "D:\\graduSourceCode\\code\\sourcecode\\file\\";
					String Province = "ShannXiGov";
					String CrawlerPathPrefix = Path + Province + "\\Crawler\\";
					LinkedList<LinkedList<String>> result = ReadFromFile.getInfo(Path + Province + "\\file.txt");
					    int i=govNumber;
						int temp = (i+1) * (100/result.size());
						String crawlerPath=CrawlerPathPrefix + result.get(i).get(0);
						String govName=result.get(i).get(1);
						String seed=result.get(i).get(2);
						String regrex=result.get(i).get(3);
						String subLinkRegrex=result.get(i).get(4);
						String imagePath=result.get(i).get(5);
						String htmlSourcePath=result.get(i).get(6);	
						System.out.println("crawlerPath:" + crawlerPath + ")");
						System.out.println("govName:" + govName + ")");
						System.out.println("seed:" + seed + ")");
						System.out.println("regrex:" + regrex + ")");
						System.out.println("subLinkRegrex:" + subLinkRegrex + ")");
						System.out.println("imagePath:" + imagePath + ")");
						System.out.println("htmlSourcePath:" + htmlSourcePath + ")");	
					try {
						ShaanXiGov crawler=new ShaanXiGov(crawlerPath, true, govName, seed, regrex, subLinkRegrex,imagePath,htmlSourcePath);
						Thread.sleep(1000);
					} catch (Exception e2) {
						 // TODO Auto-generated catch block
						e2.printStackTrace();
					}
//					try
//					{
//						Thread.sleep(1000);
//					}
//					catch (InterruptedException e1)
//					{
//				  	    e1.printStackTrace();
//					}
//					try
//					{
//						Thread.sleep(1000);
//					}
//					catch (InterruptedException e)
//					{
//						e.printStackTrace();
//					}				
					progressbar.setValue(temp);
					try
					{
						Thread.sleep(1000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					frame.dispose();
				}
			}).start();
		}
	}
	
	public static void main(String[] args)
	{
		java.awt.EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new ProgressBar();
			}
		});
	}
}