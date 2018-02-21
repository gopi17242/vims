/*
 * @author: santhosh.k
 * @creation date: November 13, 2008
 * @file name : VIMSSLADAO.java
 * @description: 
 * 			It is the data Access Object  of the SLA page.It is called when the user clicks on the SLA Tab.
*/
package com.vertex.VIMS.test.SLA.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.vertex.VIMS.test.common.DBConnection;
import com.vertex.VIMS.test.VIMSSQLQueryInterface.*;

public class VIMSSLADAO
{	
	static Connection connection=null;
	static ResultSet resultset=null;
	static Statement statement=null;	
	static PreparedStatement preparedStatement=null;
	static CallableStatement cstmt=null;
	/*
	 * This is the Data Access Object method to get list of applications
	*/
	public static ArrayList getApplicationNames(String strLoginUser)
	{
		ArrayList ApplicationNames=new ArrayList();
		HashMap hashMap=null;		
		try 
		{
			//connection = DBConnection.createConnection();
			//statement = connection.createStatement();
			//resultset = statement.executeQuery(VIMSQueryInterface.SLACretaedApplicationNamesSql);
			System.out.println("====1=================");
			connection=DBConnection.createConnection();
			System.out.println("====2=================");
			cstmt=connection.prepareCall("{Call vims_user.USP_Get_Application_Dtls(?,?,?,?,?,?,?)}");
			System.out.println("====3=================");
			//System.out.println("======ststus in DAO======"+strStatus);
			cstmt.setString(1,null);
			cstmt.setString(2,null);
			cstmt.setString(3,null);
			cstmt.setString(4,null);
			cstmt.setString(5,null);
			cstmt.setString(6,null);
			cstmt.setString(7,strLoginUser);
			System.out.println("====4=================");
			resultset=cstmt.executeQuery();
			System.out.println("====5=================");
			while (resultset.next())
			{//while started
				hashMap = new HashMap();
				hashMap.put("id", resultset.getString("APPLICATION_ID"));
			    hashMap.put("ApplicationName", resultset.getString("APPLICATION_NAME"));
				ApplicationNames.add(hashMap);

			}
			System.out.println("====6=================");
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{			
			try 
			{
				DBConnection.closeConnection();
				//statement.close();
				resultset.close();
			} 
			catch (SQLException sqlException)
			{				
				sqlException.printStackTrace();
			}			
		}
		return ApplicationNames;		
	}
	/*
	 * This is the Data Access Object method to get SLA details of a selected  application
	*/
	public static HashMap getApplicationSLADetailsDAO(String ApplName)
	{
		HashMap hashMap=null;		
		try
		{
			connection = DBConnection.createConnection();
			preparedStatement = connection.prepareStatement(VIMSQueryInterface.getApplicationSLADetails);
			preparedStatement.setString(1, ApplName);
			resultset = preparedStatement.executeQuery();
			System.out.println("result set in reports DAo is "+resultset);
			while (resultset.next())
			{//while started
				hashMap=new HashMap();
				if(resultset.getString(1)!=null)
				{
					hashMap.put("CRITICAL_RESPONSE_TIME", resultset.getString(1));
				}
				
				if(resultset.getString(1)!=null)
				{
					hashMap.put("CRITICAL_WARNING_INTERVAL", resultset.getString(2));
				}
				
				if(resultset.getString(1)!=null)
				{
					hashMap.put("MAJOR_RESPONSE_TIME", resultset.getString(3));
				}
				
				if(resultset.getString(1)!=null)
				{
					hashMap.put("MAJOR_WARNING_INTERVAL", resultset.getString(4));
				}
				
				if(resultset.getString(1)!=null)
				{
					hashMap.put("MINOR_RESPONSE_TIME", resultset.getString(5));
				}
				
				if(resultset.getString(1)!=null)
				{
					hashMap.put("MINOR_WARNING_INTERVAL", resultset.getString(6));
				}
				
			}	 		
		} 
		catch(Exception exception)
		{
			exception.printStackTrace();			
		}
		finally
		{			
			try 
			{
				DBConnection.closeConnection();
				preparedStatement.close();
				resultset.close();
			} 
			catch (SQLException sqlException) 
			{				
				sqlException.printStackTrace();
			}
			catch (Exception exception) 
			{				
				exception.printStackTrace();
			}
			
		}
		return hashMap;		
	}
	/*
	 * This is the Data Access Object method to set or create  SLA details of a selected  application
	*/
	public static boolean setApplicationSLADetailsDAO(String strApplicationId,String strcriticalResponseTime, String strcriticalWarningTime,String strmajorResponseTime, String strmajorWarningTime, String strminorResponseTime, String strminorWarningTime)
	{			
		int rows=0;
		try
		{
			connection = DBConnection.createConnection();			
			
			preparedStatement = connection.prepareStatement(VIMSQueryInterface.SLA_Details);
			preparedStatement.setString(1, strApplicationId);
			preparedStatement.setString(2, "insert");
			preparedStatement.setInt(3,Integer.parseInt(strcriticalResponseTime) );
			preparedStatement.setInt(4,Integer.parseInt(strcriticalWarningTime) );
			preparedStatement.setInt(5,Integer.parseInt(strmajorResponseTime) );
			preparedStatement.setInt(6,Integer.parseInt(strmajorWarningTime) );
			preparedStatement.setInt(7,Integer.parseInt(strminorResponseTime) );
			preparedStatement.setInt(8,Integer.parseInt(strminorWarningTime) );
			rows=preparedStatement.executeUpdate();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{			
			try 
			{
				DBConnection.closeConnection();
				preparedStatement.close();
				resultset.close();
			} 
			catch (SQLException sqlException) 
			{				
				sqlException.printStackTrace();
			}			
		}
		
		if(rows>0 )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/*
	 * This is the Data Access Object method to delete SLA details of a selected  application
	*/
	public static boolean deleteApplicationSLADetailsDAO(String strApplicationId)
	{		
		int deleteApplicationSLADetails=0;		
		try
		{
			connection = DBConnection.createConnection(); 
			preparedStatement = connection.prepareStatement(VIMSQueryInterface.deleteSLADetails);
			preparedStatement.setString(1, strApplicationId);
			deleteApplicationSLADetails=preparedStatement.executeUpdate();	
		} 
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{			
			try
			{
				DBConnection.closeConnection();
				preparedStatement.close();
				resultset.close();
			} 
			catch (SQLException sqlException) 
			{				
				sqlException.printStackTrace();
			}
			
		}
		if(deleteApplicationSLADetails!=0)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	/*
	 * This is the Data Access Object method to modify SLA details of a selected  application
	*/
	public static boolean modifyApplicationSLADetailsDAO(String strApplicationId,String strcriticalResponseTime, String strcriticalWarningTime,String strmajorResponseTime, String strmajorWarningTime, String strminorResponseTime, String strminorWarningTime)
	{		
		boolean result=false;		
		try
		{
			connection = DBConnection.createConnection();
			preparedStatement = connection.prepareStatement(VIMSQueryInterface.SLA_Details);
			preparedStatement.setString(1, strApplicationId);
			preparedStatement.setString(2, "update");
			preparedStatement.setInt(3,Integer.parseInt(strcriticalResponseTime) );
			preparedStatement.setInt(4,Integer.parseInt(strcriticalWarningTime) );
			preparedStatement.setInt(5,Integer.parseInt(strmajorResponseTime) );
			preparedStatement.setInt(6,Integer.parseInt(strmajorWarningTime) );
			preparedStatement.setInt(7,Integer.parseInt(strminorResponseTime) );
			preparedStatement.setInt(8,Integer.parseInt(strminorWarningTime) );
			result=preparedStatement.execute();			
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{			
			try 
			{
				DBConnection.closeConnection();
				preparedStatement.close();
				resultset.close();
			}
			catch (SQLException sqlException)
			{				
				sqlException.printStackTrace();
			}			
		}
		
		return result;
	}
	/*
	 * This is the Data Access Object method to get SLA existing applications
	*/
	public static ArrayList getSLAcreatedApplicationsDAO() {
		ArrayList SLAcreatedApplicationsDAO=new ArrayList();		
		HashMap hashMap=null;
		try
		{
			connection = DBConnection.createConnection();
			preparedStatement = connection.prepareStatement(VIMSQueryInterface.SLACretaedApplicationNamesSql);			
			resultset = preparedStatement.executeQuery();
			while (resultset.next())
			{//while started
				hashMap = new HashMap();
				hashMap.put("id", resultset.getString(1));
			    hashMap.put("ApplicationName", resultset.getString(2));
			    SLAcreatedApplicationsDAO.add(hashMap);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{			
			try 
			{
				DBConnection.closeConnection();
				preparedStatement.close();
				resultset.close();
			}
			catch (SQLException sqlException)
			{				
				sqlException.printStackTrace();
			}			
		}
		return SLAcreatedApplicationsDAO;
	}
	public static String getApplicationNameDAo(String strApplicationId)
	{
		String ApplicationName=null;
		try
		{
			connection = DBConnection.createConnection();
			preparedStatement = connection.prepareStatement("select application_name from vims_user.application where application_id='"+strApplicationId+"'");			
			resultset = preparedStatement.executeQuery();
			while (resultset.next())
			{//while started
				ApplicationName=resultset.getString(1);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{			
			try 
			{
				DBConnection.closeConnection();
				preparedStatement.close();
				resultset.close();
			}
			catch (SQLException sqlException)
			{				
				sqlException.printStackTrace();
			}			
		}
		return ApplicationName;
	}
}


