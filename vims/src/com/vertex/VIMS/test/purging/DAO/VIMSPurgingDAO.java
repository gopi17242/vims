/*
 FileName	    : VIMSPurgingDAO.java


 Description	: This  VIMSPurgingDAO Object class is has methods which interacts with the database. It executes queries in the query interface and returns results to the BD (BusinessDelegate) class, which inturn return the value to Contoller which inturn fulfills the View part of the application.
 				  It has methods which call the DAO class Object.
  
 Developed by  : Vertex Computer Systems.
 copyright (c) 2008 Vertex.
 All rights reserved.

 Change History:

 Revision No.	:		Date		  @author		Description
 1.0				Friday 21,2008	  Sudhir.D	   Initial Version.*/

package com.vertex.VIMS.test.purging.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.vertex.VIMS.test.common.DBConnection;
import com.vertex.VIMS.test.purging.form.PurgingFormBean;

public class VIMSPurgingDAO {

	/**
	 * Description: This static method returns executes different queries/stored procedure to archive the issues based on the form field values, which were submitted by the user.
	 * @param form
	 * @param strUserId
	 * @return  Status Message
	 */
	public static String purgingOperation(PurgingFormBean form, String strUserId) {
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		Connection connection = DBConnection.createConnection();
		String strOperation = (String) form.getOperation();
		String strIncidenttype = (String) form.getIssueStatus();
		String purge=(String)form.getPurge();
		String strFromDate=(String) form.getFromDate();
		String strToDate=(String) form.getToDate();
		String strPrevMonths = "" + form.getPrevMonths();
		String strPrevDays = "" + form.getPrevDays();
		String strIncidentID = (String) form.getIncidentID();
		
		System.out.println("strOperation "+strOperation); 
		System.out.println("strIncidenttype"+strIncidenttype);
		System.out.println("purge"+purge);
		System.out.println("strFromDate"+strFromDate);
		System.out.println("strToDate"+strToDate);
		System.out.println("strPrevMonths"+strPrevMonths);
		System.out.println("strPrevDays"+strPrevDays);
		System.out.println("strIncidentID"+strIncidentID);
		
		String Result = null;			
		String strQuery="EXEC vims_user.incident_archive_insert ";
		try {
			if (purge.equals("all")) //if radio button "all " is selected
			{
				System.out.println("====strOperation===="+strOperation); 
				strQuery=strQuery+"@ARC_TYPE='"+strOperation+"',@INCT_TYPE='"+strIncidenttype+"',@ALL_INCIDENTS='"+purge+"',@STARTDATE=null,@ENDDATE=null,@PREVIOUS_MONTHS =null,@PREVIOUS_DAYS =null,@INCIDENT_ID =null,@OUT='@OUT'";
				System.out.println("========= "+strQuery+" =========="); 
				preparedStatement=connection.prepareStatement(strQuery);
				System.out.println("Prepared Statement is : "+preparedStatement);
				resultSet=preparedStatement.executeQuery();
				System.out.println("ResultSet is : "+resultSet); 
				while(resultSet.next())
				{
					Result=resultSet.getString(1);
				}
				System.out.println("======all===="+Result);
		    }	
			else if (purge.equals("betweendates"))//if between dates radio button is selected , then this part is executed
			{
				System.out.println("====strOperation===="+strOperation); 
				strQuery=strQuery+"@ARC_TYPE='"+strOperation+"',@INCT_TYPE='"+strIncidenttype+"',@ALL_INCIDENTS=null,@STARTDATE='"+strFromDate+"',@ENDDATE='"+strToDate+"',@PREVIOUS_MONTHS =null,@PREVIOUS_DAYS =null,@INCIDENT_ID =null,@OUT='@OUT'";
				System.out.println("========= "+strQuery+" =========="); 
				preparedStatement=connection.prepareStatement(strQuery);
				System.out.println("Prepared Statement is : "+preparedStatement);
				resultSet=preparedStatement.executeQuery();
				System.out.println("ResultSet is : "+resultSet); 
				while(resultSet.next())
				{
					Result=resultSet.getString(1);
				}
				System.out.println("======betweendates===="+Result);
			} 
			else if (purge.equals("PrevMonths"))//if  Months radio button is selected, then this part is executed
			{
				System.out.println("====strOperation===="+strOperation); 
				strQuery=strQuery+"@ARC_TYPE='"+strOperation+"',@INCT_TYPE='"+strIncidenttype+"',@ALL_INCIDENTS=null,@STARTDATE=null,@ENDDATE=null,@PREVIOUS_MONTHS ='"+strPrevMonths+"',@PREVIOUS_DAYS =null,@INCIDENT_ID =null,@OUT='@OUT'";
				System.out.println("========= "+strQuery+" =========="); 
				preparedStatement=connection.prepareStatement(strQuery);	
				System.out.println("Prepared Statement is : "+preparedStatement);
				resultSet=preparedStatement.executeQuery();
				System.out.println("ResultSet is : "+resultSet); 
				while(resultSet.next())
				{
					Result=resultSet.getString(1);
				}
				System.out.println("======prevmonths===="+Result);
			} 
			else if (purge.equals("PrevDays"))//if DaysBefore radio button is selected ,then this part is executed
			{
				System.out.println("====strOperation===="+strOperation); 
				strQuery=strQuery+"@ARC_TYPE='"+strOperation+"',@INCT_TYPE='"+strIncidenttype+"',@ALL_INCIDENTS=null,@STARTDATE=null,@ENDDATE=null,@PREVIOUS_MONTHS =null,@PREVIOUS_DAYS='"+strPrevDays+"',@INCIDENT_ID =null,@OUT='@OUT'";
				System.out.println("========= "+strQuery+" =========="); 
				preparedStatement=connection.prepareStatement(strQuery);
				System.out.println("Prepared Statement is : "+preparedStatement);
				resultSet=preparedStatement.executeQuery();
				System.out.println("ResultSet is : "+resultSet); 
				while(resultSet.next())
				{
					Result=resultSet.getString(1);
				}				
				System.out.println("======prevdays===="+Result);
			} 
			else if (purge.equals("incid"))//If incident id radio button is selected ,then this part is executed. 
			{
				System.out.println("====strOperation===="+strOperation); 
				strQuery=strQuery+"@ARC_TYPE='"+strOperation+"',@INCT_TYPE='"+strIncidenttype+"',@ALL_INCIDENTS=null,@STARTDATE=null,@ENDDATE=null,@PREVIOUS_MONTHS =null,@PREVIOUS_DAYS=null,@INCIDENT_ID='"+strIncidentID+"',@OUT='@OUT'";
				System.out.println("========= "+strQuery+" =========="); 
				preparedStatement=connection.prepareStatement(strQuery);
				System.out.println("Prepared Statement is : "+preparedStatement);
				resultSet=preparedStatement.executeQuery();
				System.out.println("ResultSet is : "+resultSet); 
				while(resultSet.next())
				{
					Result=resultSet.getString(1);
				}
				System.out.println("======incident id===="+Result);
			}
			return Result;
		}//end of try
		catch (SQLException sqlException) 
		{
			sqlException.printStackTrace();
			return null;
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
			return null;
		}
		/*finally
		{
			try
			{
				DBConnection.closeConnection();
				preparedStatement.close();
				resultSet.close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
			
		}*/
	}
}//end of class
