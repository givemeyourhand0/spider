package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperate 
{
	private static final String DRIVER = "org.postgresql.Driver";
	private static String URL="jdbc:postgresql://localhost:5432/webdatatest";
	//private static String URL="jdbc:postgresql://222.25.176.34:5432/webdatatest";  //222.25.176.34:5432
	private static String username="postgres";  //postgresql
	private static String sec="postgres";
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	@SuppressWarnings("unused")
	private PreparedStatement p=null; 
	
	@SuppressWarnings("static-access")
	public DatabaseOperate(String url,String username,String sec)
	{
		this.setUrl(url);
		this.setUsername(username);
		this.setSec(sec);
		this.conn=this.getConnection();
	}
	
	@SuppressWarnings("static-access")
	public DatabaseOperate()
	{
		this.conn=this.getConnection();
	}
	
	/**
	 * 数据库连接
	 * @return
	 */
	public static Connection getConnection()
	{
		Connection conn=null;
		try
		{
			Class.forName(DRIVER);
			conn=DriverManager.getConnection(URL,username,sec);
			//System.out.println("连接postgresql成功");
		}
		catch(Exception e)
		{
			System.out.println("postgresql连接失败");
			e.printStackTrace();
			return conn;
		}
		return conn;
	}
	
	/**
	 * 关闭数据库连接
	 * @param conn
	 */
	public static void closeConn(Connection conn)
	{
		try
		{
			if(conn!=null)
			{
				conn.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭数据库结果集
	 * @param rs
	 */
	public static void closeResult(ResultSet rs)
	{
		try
		{
			if(rs!=null)
			{
				rs.close();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭数据库Statement
	 * @param state
	 */
	public static void closeState(Statement state)
	{
		try
		{
			if(state!=null)
			{
				state.close();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭数据库PreparedStatement
	 * @param pstate
	 */
	public static void closePstate(PreparedStatement pstate)
	{
		try
		{
			if(pstate!=null)
			{
				pstate.close();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行查询操作
	 * @param sql
	 * @return
	 */
	public ResultSet executeQuery(String sql)
	{
		this.rs=null;
		try 
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 执行更新操作
	 * @param sql
	 * @return
	 */
	public void executeUpdate(String sql)
	{
		try 
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
		}
	}
	
	
	public String getUrl() {
		return URL;
	}

	@SuppressWarnings("static-access")
	public void setUrl(String url) {
		this.URL = url;
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
}