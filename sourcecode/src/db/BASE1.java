package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class BASE1 
{
	public static final String DRIVER = "org.postgresql.Driver";
	private static String url="jdbc:postgresql://10.170.49.19:5432/webdatatest";
	private static String username="postgres";
	private static String sec="love";
	private Connection conn = null;
	private Statement stmt = null;
	public ResultSet rs = null;
	private boolean state=false;
	
	public BASE1(String url,String username,String sec)
	{
		this.setUrl(url);
		this.setUsername(username);
		this.setSec(sec);
		this.conn=this.getConn();
	}
	public BASE1()
	{
		this.conn=this.getConn();
	}
	private Connection getConn()
	{
		Connection conn=null;
		try
		{
			Class.forName(DRIVER);
			
			conn=DriverManager.getConnection(url,username,sec);
			
			this.state=true;
			System.out.println("连接postgresql成功");
		}
		catch(Exception e)
		{
			System.out.println("postgresql连接失败");
			e.printStackTrace();
			return conn;
		}
		return conn;
	}

	public ResultSet executeQuery(String sql)
	{
		this.rs=null;
		if(state==true)
		{
			try
			{
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			try
			{
				if(rs!=null&&rs.next())
				{
					rs.beforeFirst();
					return rs;
				}
				else
				{
					return null;
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				return null;
			}
			finally
			{
				//Do nothing
			}
		}
		else
			System.out.println("Dao");
		return rs;
	}
	
	public int executeUpdate(String sql)
	{
		int re=0;
		if(state==true)
		{
			try
			{
				stmt = conn.createStatement();
				re=stmt.executeUpdate(sql);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				//Do nothing
			}
		}
		else
			System.out.println("Dao");
		return re;
	}

	public void close()
	{
		String success_message="successful";
		String fail_message="failed";
		if (rs != null)
		{
			try
			{
				rs.close();
			}
			catch (SQLException e)
			{
				System.out.println(fail_message);
				e.printStackTrace();

			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				System.out.println(fail_message);
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
				this.state=false;
			} catch (SQLException e) {
				System.out.println(fail_message);
				e.printStackTrace();
			}
		}
		System.out.println(success_message);
	}


	public String getUrl() {
		return url;
	}

	@SuppressWarnings("static-access")
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	@SuppressWarnings("static-access")
	public void setUsername(String username) {
		this.username = username;
	}

	public String getSec() {
		return sec;
	}

	@SuppressWarnings("static-access")
	public void setSec(String sec) {
		this.sec = sec;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	public static void main(String[] args)
	{
		Connection conn = null;
		Statement stmt = null;
		
		String url1="http://www.gov.cn/guowuyuan/2016-08/23/content_5101606.htm";
	   	String title="张高丽与土库曼斯坦副总理卡卡耶夫举行中土合作委员会第四次会议_国务院副总理张高丽_中国政府网";
	   	String date="2016-08-23";
	   	String source="新华社";
	   	String content="8月23日，中共中央政治局常委、国务院副总理张高丽在天津同土库曼斯坦副总理卡卡耶夫举行中土合作委员会第四次会议。新华社记者 王晔 摄 新华社天津8月23日电（记者 王慧慧）中共中央政治局常委、国务院副总理张高丽23日在天津同土库曼斯坦副总理卡卡耶夫举行中土合作委员会第四次会议。 张高丽说，中土是真诚互信的战略伙伴。在习近平主席和别尔德穆哈梅多夫总统的亲自关心和推动下，中土关系始终保持健康稳定的发展势头。两国高层交往密切，政治互信达到新的高度。两国元首每年举行会晤，就发展两国关系、规划各领域合作进行深入交流，为中土战略伙伴关系发展不断注入新动力。 卡卡耶夫表示，土库曼斯坦高度重视对华关系，视中国为土外交的优先方向之一，愿同中方进一步扩大油气、贸易、交通、投资、人文等领域的互利合作，努力将两国关系提升至更高水平。 双方总结了中土合作委员会第三次会议以来的工作成果，一致认为两国各领域务实合作不断深化，战略伙伴关系更加稳固。双方强调，中土合作委员会将继续发挥规划、指导和协调作用，继续认真落实好两国元首达成的共识，共同推进“一带一路”建设，重点加强双方能源、经贸、人文、安全合作，为深化中土战略伙伴关系不断作出新的贡献。 会后，张高丽和卡卡耶夫共同签署了中土合作委员会第四次会议纪要。";
	   	int locationId=27;
	   	String tableName="tbl_main";
	   	
		try
		{
			Class.forName(DRIVER);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}		
	   	
 	    String insertSql = "insert into "+tableName+" (title,content,time,source,url,locationid) values("
			+" '"+ title+"',"
 			+" '"+content+"',"
 			+" '"+date+"',"
 			+" '"+source+"',"
 			+" '"+url1+"',"
 			+locationId+")";
 	    
 	    try
 	    {
			Class.forName(DRIVER);
		}
 	    catch (ClassNotFoundException e)
 	    {
			e.printStackTrace();
		}
 	    
 	    try
 	    {
			conn=DriverManager.getConnection(url,username,sec);
		}
 	    catch (SQLException e)
 	    {
			e.printStackTrace();
		}
 	    
 	    try
 	    {
			stmt=conn.createStatement();
		}
 	    catch (SQLException e)
 	    {
			e.printStackTrace();
		}
 	    
		try
		{
			stmt.executeUpdate(insertSql);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}