package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import SubLinkFetch.ShaanXiGov;
import outputWithXML.ReadFromFile;

/**
 * 进度条显示程序运行进度
 * @author Administrator
 */

public class TestProgressBar implements ActionListener, ChangeListener
{
	JFrame frame = null;
	JProgressBar progressbar;
	JLabel label;
	Timer timer;
	JButton button;
	
	/**
	 * Constructor
	 */
	public TestProgressBar()
	{
		frame = new JFrame("进度条简单示例");
		frame.setBounds(400, 100, 400, 130);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		Container contentPanel = frame.getContentPane();
		label = new JLabel("点击开始运行",JLabel.CENTER);
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
		button = new JButton("运行");
		button.setForeground(Color.blue);
		button.addActionListener(this);
		
		panel.add(button);
		
		timer = new Timer(100,this);
		contentPanel.add(panel, BorderLayout.NORTH);
		contentPanel.add(label,BorderLayout.CENTER);
		contentPanel.add(progressbar,BorderLayout.SOUTH);
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
			
			new Thread(new Runnable()
			{
				@SuppressWarnings("unused")
				@Override
				public void run()
				{
					/**
					 * 开始运行爬虫程序，代码放在此处，那么开始运行之后怎么知道已经运行了多少个门户网站
					 * 
					 * 以下代码只是测试进度条是否会被阻塞以及能否正确显示
					 */
					LinkedList<LinkedList<String>> result = ReadFromFile.getInfo("file.txt");
					for(int i=0;i<result.size();i++)
					{
						int temp = (i+1) * (100/result.size());
						String crawlerPath=result.get(i).get(0);
						String govName=result.get(i).get(1);
						String seed=result.get(i).get(2);
						String regrex=result.get(i).get(3);
						String subLinkRegrex=result.get(i).get(4);
						String imagePath=result.get(i).get(5);
						String htmlSourcePath=result.get(i).get(6);
						try 
						{
							ShaanXiGov crawler=new ShaanXiGov(crawlerPath, true, govName, seed, regrex, subLinkRegrex,imagePath,htmlSourcePath);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
						progressbar.setValue(temp);
					}
					
					try
					{
						Thread.sleep(100000);
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
		new TestProgressBar();
	}
}