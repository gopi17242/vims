/*
 * Author: santhosh.k
 * Creation date: 11/19/2008
 * File Name : VIMSHomeDAO.java
 * Description: 
 * 			This is the Data Access Object class of the home page module.This is called by the VIMSBD class based on the request.
 * 			In this page connecting to the database and retrieving records and inserting records.
 * 
*/

package com.vertex.VIMS.test.home.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.vertex.VIMS.test.VIMSSQLQueryInterface.VIMSQueryInterface;
import com.vertex.VIMS.test.common.DBConnection;

public class VIMSHomeDAO {
	static Connection connection=null;
	static PreparedStatement preparedStatement=null;
	static ResultSet resultSet=null;
	
	/*
	 * This method is used to retrieve SLA details of an application from the data base
	 * 
	*/
	public static HashMap getHomeApplicationOpenAndSLADAO(String strApplicationId) {		
		HashMap HomeSLADetailsDAO=new HashMap();//declaration of local variable HomeSLADetailsDAO of type HashMap	
		try
		{
			connection = DBConnection.createConnection();// connecting to the data base and retrieving Connection object
			preparedStatement = connection.prepareStatement(VIMSQueryInterface.getHomeApplicationOpenAndSLAdetails);// retrieving PreparedStatement object and assigning to the local variable 	preparedStatement		
			preparedStatement.setString(1, strApplicationId);//setting values in to the query through PreparedStatement reference
			resultSet = preparedStatement.executeQuery();// executing query and retrieving ResultSet Object assigned to class variable resultSet
			while (resultSet.next())//if the resultSet has records then this condition satisfies
			{
				HomeSLADetailsDAO.put("CriticalValue",resultSet.getString(1));
				HomeSLADetailsDAO.put("MajorValue",resultSet.getString(2));
				HomeSLADetailsDAO.put("MinorValue",resultSet.getString(3));
				HomeSLADetailsDAO.put("OpenIssuesCount",resultSet.getString(4));
				HomeSLADetailsDAO.put("CriticalOpenIssuesCount",resultSet.getString(5));
				HomeSLADetailsDAO.put("MajorOpenIssuesCount",resultSet.getString(6));
				HomeSLADetailsDAO.put("MinorOpenIssuesCount",resultSet.getString(7));
			}
		} 
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			DBConnection.closeConnection();//connection closed
			try
			{
				preparedStatement.close();// Prepared Statement closed
				resultSet.close();//ResultSet closed
			}
			catch(SQLException sqlException)
			{
				sqlException.printStackTrace();
			}
			catch (Exception exception)
			{
				exception.printStackTrace(); 
			}
		}		
		
		return HomeSLADetailsDAO;// returns HashMap reference
	}
	
	/*
	 * This method is used to display OpenIssues of an application of particular incident type from the data base
	 * 
	*/ 
	public static ArrayList displayHomeOpenissuesDAO(String strApplicationId,String strIncidentType) {		
		
		HashMap hashMap=null; //declaration of local variable of type HashMap			
		ArrayList IssuesList= new ArrayList();//declaration of local variable of type ArrayList				
		String strSelectedIssueType=strIncidentType;//declaration and initialization of local variable strSelectedIssueType of type String			
		try 
			{
				connection = DBConnection.createConnection();// connecting to the data base and retrieving Connection object

				if(strSelectedIssueType.equals("all"))
				{
					preparedStatement=connection.prepareStatement("EXEC vims_user.Usp_Get_Application_Issue @USER_NM=NULL, @APPLICATION_ID='"+strApplicationId+"',@INCT_STATUS=NULL,@INCIDENT_TYPE=NULL");// retrieving PreparedStatement object and assigning to the local variable 	preparedStatement						
				}
				else
				{
					preparedStatement=connection.prepareStatement("EXEC vims_user.Usp_Get_Application_Issue @USER_NM=NULL, @APPLICATION_ID='"+strApplicationId+"',@INCT_STATUS=NULL,@INCIDENT_TYPE='"+strSelectedIssueType+"'");// retrieving PreparedStatement object and assigning to the local variable 	preparedStatement						
				}				
				resultSet = preparedStatement.executeQuery();// executing query and retrieving ResultSet Object assigned to local variable resultSet
				
				IssuesList=getIssuesList(resultSet);				
			}
			catch (SQLException sqlException)
			{				
				sqlException.printStackTrace(); 
			}
			catch (Exception exception)
			{
				exception.printStackTrace(); 
			}
			finally
			{
				DBConnection.closeConnection();//connection closed
				try
				{
					preparedStatement.close();// Prepared Statement closed
					resultSet.close();//ResultSet closed
				}
				catch(SQLException sqlException)
				{
					sqlException.printStackTrace();
				}
				catch (Exception exception)
				{
					exception.printStackTrace(); 
				}
			}		 
		return IssuesList;
	}
	public static ArrayList getIssuesList(ResultSet resultSet)
	{
		HashMap hashMap=null; //declaration of local variable of type HashMap			
		ArrayList IssuesList= new ArrayList();//declaration of local variable of type ArrayList				
		
		try {
			
			while(resultSet.next())//if the resultSet has records then this condition satisfies
			{				
				hashMap = new HashMap();//initialization of local variable hashMap
				String linkView="<a href='./ListofIssues.do?methodname=IssueDetails&id="+(String)resultSet.getString(1)+"'>"+resultSet.getString(1)+"</a>";
				hashMap.put("ID", linkView);//putting id in to the HashMap
				hashMap.put("Incident_ID", resultSet.getString(1));
				hashMap.put("Application_Name", resultSet.getString(2));//putting applicationName in to the HashMap
				hashMap.put("Title", resultSet.getString(3));//putting open issues in to the HashMap
				hashMap.put("Created_Date", resultSet.getString(4));//putting title in to the HashMap
				hashMap.put("Severity", resultSet.getString(5));//putting type in to the HashMap
				hashMap.put("Due_Date", resultSet.getString(6));//putting toDate in to the HashMap
				hashMap.put("Status", resultSet.getString(7));//putting status in to the HashMap
				hashMap.put("Closed_Date", resultSet.getString(8));//putting closedDate in to the HashMap
				hashMap.put("Hours", resultSet.getString(9));//putting hours in to the HashMap
				IssuesList.add(hashMap);					
			}
		} 
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}		
		return IssuesList;
	}
	
	public static ArrayList getMytoList(ResultSet resultSet)
	{
		HashMap hashMap=null; //declaration of local variable of type HashMap			
		ArrayList alist= new ArrayList();//declaration of local variable of type ArrayList				
		
		try {
	while(resultSet.next())
	{
		hashMap=new HashMap();
		String linkView="<a href='./ListofIssues.do?methodname=IssueDetails&id="+(String)resultSet.getString(1)+"'>"+resultSet.getString(1)+"</a>";
		hashMap.put("strIncidentId",linkView);
		hashMap.put("strApplicationName", resultSet.getString(2));
		hashMap.put("strTitle",resultSet.getString(3));
		hashMap.put("strAssignedBy", resultSet.getString(4));
		hashMap.put("strPostedDate", resultSet.getString(5));
		hashMap.put("strDueDate", resultSet.getString(6));
		hashMap.put("strSeverity",resultSet.getString(7));
		hashMap.put("strStatus", resultSet.getString(8));
			
		alist.add(hashMap);
	 }
	}
	 
	catch (SQLException sqlException)
	{
		sqlException.printStackTrace();
	}
	catch (Exception exception)
	{
		exception.printStackTrace();
	}		
	return alist;
}

	public static ArrayList displayUserIssuesDAO(String strUserID)
	{		
		
		  HashMap hashmap=null; //declaration of local variable of type HashMap			
		  ArrayList alist= new ArrayList();//declaration of local variable of type ArrayList				
		  ArrayList assignedArrayList=null;
		  ArrayList openArrayList=null;
		  
		try 
			{
			    connection = DBConnection.createConnection();// connecting to the data base and retrieving Connection object
				preparedStatement=connection.prepareStatement(VIMSQueryInterface.MyToList1);// retrieving PreparedStatement object and assigning to the local variable 	preparedStatement						
				preparedStatement.setString(1,strUserID);//setting values in to the query through PreparedStatement reference			
				
				resultSet = preparedStatement.executeQuery();// executing query and retrieving ResultSet Object assigned to local variable resultSet
				
				openArrayList=getMytoList(resultSet);
				resultSet.close();
				preparedStatement.close();
				preparedStatement=connection.prepareStatement(VIMSQueryInterface.MyToList);// retrieving PreparedStatement object and assigning to the local variable 	preparedStatement						
				preparedStatement.setString(1,strUserID);//setting values in to the query through PreparedStatement reference			
				resultSet = preparedStatement.executeQuery();// executing query and retrieving ResultSet Object assigned to local variable resultSet
				
				assignedArrayList=getMytoList(resultSet);
				
				alist.add(0,openArrayList);
				alist.add(1,assignedArrayList);
				
			}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}	
		finally
		{
			DBConnection.closeConnection();//connection closed
			try
			{
				preparedStatement.close();// Prepared Statement closed
				resultSet.close();//ResultSet closed
			}
			catch(SQLException sqlException)
			{
				sqlException.printStackTrace();
			}
			catch (Exception exception)
			{
				exception.printStackTrace(); 
			}
		}	
		return alist;
	}
	public static HashMap displayUserAssignedissuesDAO(String strUserId)
	{		
		HashMap hashmap =null;
		try 
			{
			   connection = DBConnection.createConnection();// connecting to the data base and retrieving Connection object
			   preparedStatement=connection.prepareStatement(VIMSQueryInterface.MyToList);// retrieving PreparedStatement object and assigning to the local variable 	preparedStatement						
			   preparedStatement.setString(1,strUserId);//setting values in to the query through PreparedStatement reference			
			   resultSet = preparedStatement.executeQuery();//executing query and retrieving ResultSet Object assigned to local variable resultSet
		
				int assgisscount=0;
				while(resultSet.next())
				{
					assgisscount++;
				}
				hashmap = new HashMap();
				hashmap.put("assignedIssuesCount",assgisscount+"");
				
				resultSet.close();
				preparedStatement.close();
				
				connection = DBConnection.createConnection();// connecting to the data base and retrieving Connection object
				preparedStatement=connection.prepareStatement(VIMSQueryInterface.MyToList1);// retrieving PreparedStatement object and assigning to the local variable 	preparedStatement						
				preparedStatement.setString(1,strUserId);//setting values in to the query through PreparedStatement reference			
				resultSet = preparedStatement.executeQuery();//executing query and retrieving ResultSet Object assigned to local variable resultSet
		
				int openIsscount=0;
				while(resultSet.next())
				{
					openIsscount++;
				}
				hashmap.put("openIssuesCount",openIsscount+"");
			}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}	
		finally
		{
			DBConnection.closeConnection();//connection closed
			try
			{
				preparedStatement.close();// Prepared Statement closed
				resultSet.close();//ResultSet closed
			}
			catch(SQLException sqlException)
			{
				sqlException.printStackTrace();
			}
			catch (Exception exception)
			{
				exception.printStackTrace(); 
			}
		}		 
		return hashmap;	  
	}
 }		


