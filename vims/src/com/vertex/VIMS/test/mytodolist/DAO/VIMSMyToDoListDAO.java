/*
FileName	    : VIMSMyToDoListDAO.java


Description	: This  VIMSMyToDoListDAO Object class is has methods which interacts with the database. It executes queries in the query interface and returns results to the BD (BusinessDelegate) class, which inturn return the value to Contoller which inturn fulfills the View part of the application.
				  It has methods which call the DAO class Object.
 
Developed by  : Vertex Computer Systems.
copyright (c) 2008 Vertex.
All rights reserved.

Change History:

Revision No.	:		Date		  @author		Description
1.0				Friday 26,2008	  Sudhir.D	   Initial Version.*/

package com.vertex.VIMS.test.mytodolist.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;


import com.vertex.VIMS.test.VIMSSQLQueryInterface.VIMSQueryInterface;
import com.vertex.VIMS.test.common.DBConnection;

/**
 * @author sudhir.d
 *
 */

public class VIMSMyToDoListDAO {
	static Logger logger=null;
	static CallableStatement callableStatement=null;
	/**
	 * Description: This method is used to get all the issues pertaining to a employee/user.
	 * @param strUserId
	 * @return Arraylist of issues.
	 */
	
	public static ArrayList getMyToDoListOfIssues( String strUserId) {
		Connection connection = null;
		Statement stmt=null;
		ResultSet resultSet=null;
		String strUserType;
		String strStatus;
		logger=Logger.getLogger("Employee");
						
		try
		{
			connection=DBConnection.createConnection();
		
		/*PreparedStatement preparedStatement = conn.prepareStatement(VIMSQueryInterface.MyToList);
		
		preparedStatement.setString(1, strUserId);
		
		resultSet= preparedStatement.executeQuery();*/
		
		ArrayList arrayList=new ArrayList();
		 
		 callableStatement=connection.prepareCall("{CALL vims_user.USP_Get_Assigned_Issue(?)}");
		 callableStatement.setString(1,strUserId);
		 callableStatement.execute();
		 resultSet=callableStatement.getResultSet();
	
		while(resultSet.next())
		{
		HashMap hashMap=new HashMap();
		String linkView="<a href='./ListofIssues.do?methodname=IssueDetails&id="+(String)resultSet.getString(1)+"'>"+resultSet.getString(1)+"</a>";
		hashMap.put("strIncidentId",linkView);
		hashMap.put("strApplicationName", resultSet.getString(2));
		hashMap.put("strTitle",resultSet.getString(3));
		hashMap.put("strAssignedBy", resultSet.getString(4));
		hashMap.put("strPostedDate", resultSet.getString(5));
		hashMap.put("strDueDate", resultSet.getString(6));
		hashMap.put("strSeverity",resultSet.getString(7));
		hashMap.put("strStatus", resultSet.getString(8));
			
		arrayList.add(hashMap);
		//System.out.println("==========ArrayList====in DAO============="+alist);
		}
		
		
		return arrayList;
		
		}
		catch(SQLException sqlException)
		{	
			logger.info("VIMSMyToDoListDAO.getMyToDoListOfIssues");
			logger.error(sqlException);
			//System.out.println("========= SQL Exception ocured in the application========="+sqlException.toString());
			return null;
		}
		catch(Exception exception)
		{	logger.info("VIMSMyToDoListDAO.getMyToDoListOfIssues");
			logger.error(exception);
			System.out.println("============Exception occured in DBConnection============"+exception.toString());
			return null;
		}
		finally
		{
			
				DBConnection.closeConnection();
			
		}
	}//end of method
	
}//end of class
