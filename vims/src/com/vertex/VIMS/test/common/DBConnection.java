package com.vertex.VIMS.test.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.*;
/**
 * @author aditya.p
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 *
 ****** This is the class implemented as a Singleton java class */

public class DBConnection
{
	private static Connection conn = null;
	public static Logger logger=null;
	/*public static Connection createConnection(){
		logger=Logger.getLogger("Admin");
		try
		{
		
			if(conn==null)
			{
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				
				conn = DriverManager.getConnection("jdbc:jtds:sqlserver://HYDPC102:1433/VIMS_Prod", "vims_user","welcome");
				conn = DriverManager.getConnection("jdbc:jtds:sqlserver://INHY2K303:1433/VIMS_TEST", "VIMS_user","welcome");

			}
			else
			{
				System.out.println("========Connection already established=======");
			}
			return conn;
		}
		catch(Exception e)
		{
			logger.info("DBConnection.createConnection()");
			logger.error(e);
			return null;
		}
	}*/
	public static Connection createConnection(){
		Connection con=null;
		try{
			
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
		//	con = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.23:1433;\\\\SQLEXPRESS;databaseName=I_VIMS_PROD", "vims_user1","vims_user");
			con = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.23:1433;\\SQLEXPRESS;databaseName=I_VIMS_PROD", "vims_user1","Vertex@123");
		//	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//	con = DriverManager.getConnection("jdbc:sqlserver://192.168.1.23:1433;DatabaseName=I_VIMS_PROD;","vims_user1","vims_user");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	public static void closeConnection()
	{
		logger=Logger.getLogger("Admin");
		try{
			if(conn!=null){
				conn.close();
				conn=null;
			}
		}
		catch(Exception e)
		{
			logger.info("DBConnection.closeConnection()");
			logger.error(e);
			e.printStackTrace();
		}
			//System.out.println("==========Connection Closed==================");
	}
}

