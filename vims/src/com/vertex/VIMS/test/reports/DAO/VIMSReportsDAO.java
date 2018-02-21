/*
 * Author: santhosh.k
 * Creation date: 11/27/2008
 * File Name : VIMSReportsDAO.java
 * Description: This is a data Access Object for the Reports Page and all the methods are called from the Reports Business Delegate Class Methodss.
 * 			
*/
package com.vertex.VIMS.test.reports.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.struts.action.ActionForm;

import com.vertex.VIMS.test.VIMSSQLQueryInterface.VIMSQueryInterface;
import com.vertex.VIMS.test.common.DBConnection;
import com.vertex.VIMS.test.reports.form.CustomReportsActionForm;
 
public class VIMSReportsDAO {
	static Connection connection=null;				//static class variable connection of type Connection
	static ResultSet resultSet=null;				//static class variable resultSet of type ResultSet
	static Statement statement=null;				//static class variable statement of type Statement
	static PreparedStatement preparedStatement=null;//static class variable preparedStatement of type PreparedStatement	
	static String strCritical="cri";				//declaration and initialization of static class variable strCritical of type String
	static String strMajor="maj";					//declaration and initialization of static class variable strMajor of type String
	static String strMinor="min";					//declaration and initialization of static class variable strMinor of type String
	static int criticalSLA=0;						//declaration and initialization of instance variable createCritical of type int
	static int majorSLA=0;							//declaration and initialization of instance variable createMajor of type int
	static int minorSLA=0;
	
	/*
	 * This method is used to retrieve list of applications  from Database	which possess issues
	*/
	public static HashMap getIssuesInApplicationDAO(String strStatus)
	{
		System.out.println("In VIMSReportsDAO getIssuesInApplication Method ");			
		HashMap IssuesAndApplicationList=null;	//declaration of local variable SpecificIssuesInAllApplications  of type HashMap
		String [] Applications=null;
		String []ApplicationNames=null;
		int [] issuesCount=null;
		 int index=0;
		try
		{
			connection = DBConnection.createConnection();// connecting to the data base and retrieving Connection object
			preparedStatement = connection.prepareStatement(VIMSQueryInterface.getIssuesInApplicationName,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);// retrieving Statement object and assigning to the local variable Statement
			preparedStatement.setString(1, strStatus);
			resultSet = preparedStatement.executeQuery();// executing query and retrieving ResultSet Object assigned to class variable resultSet
			resultSet.last();
			Applications= new String[resultSet.getRow()];
			issuesCount= new int[resultSet.getRow()];
			ApplicationNames=new String[resultSet.getRow()];
			resultSet.beforeFirst(); 
			while (resultSet.next())
			{//while started				
				Applications[index]=resultSet.getString(1);     		    
				issuesCount[index]=Integer.parseInt(resultSet.getString(2));  	
				ApplicationNames[index]=resultSet.getString(3);    
				index++;
			}//while closed
			IssuesAndApplicationList = new HashMap();//initialization of local variable SpecificIssuesInAllApplications
			IssuesAndApplicationList.put("ApplicationList", Applications);//putting values in to the HashMap SpecificIssuesInAllApplications
			IssuesAndApplicationList.put("IssuesList", issuesCount);//putting values in to the HashMap SpecificIssuesInAllApplications
			IssuesAndApplicationList.put("ApplicationNames", ApplicationNames);			
		}//try closed 
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				preparedStatement.close();//closing of Statement
				DBConnection.closeConnection();//connection closed
				resultSet.close();//resultSet closed
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}
		return IssuesAndApplicationList;		
	}//closing of getIssuesInApplication
	
	
	
	
	public static ArrayList getApplicationNamesDAO(String Status)
	{
		ArrayList ApplicationNames=new ArrayList();
		HashMap hashMap=null;		
		try 
		{
			connection = DBConnection.createConnection(); 
			preparedStatement = connection.prepareStatement(VIMSQueryInterface.getIssuesInApplicationName);
			preparedStatement.setString(1, Status);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
			{//while started
				hashMap = new HashMap();
				hashMap.put("id", resultSet.getString(1));
			    hashMap.put("ApplicationName", resultSet.getString(3));
				ApplicationNames.add(hashMap);

			}
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
				preparedStatement.close();
				resultSet.close();
			} 
			catch (SQLException sqlException)
			{				
				sqlException.printStackTrace();
			}			
		}
		return ApplicationNames;			
	}//closing of getIssuesInApplication
	
	
	
	
	
	/*
	 * This method is used to retrieve specific issues in application(s).
	*/
	public static HashMap getSpecificIssuesInAllApplication(String strApplication, String strIssueType, String applicationStatus)
	{
		System.out.println("In VIMSReportsDAO getSpecificIssuesInAllApplication Method ");			
		HashMap SpecificIssuesInAllApplications=null;	//declaration of local variable SpecificIssuesInAllApplications  of type HashMap
		String [] Applications=null;
		int [] specificIssues=null;
		String []ApplicationNames=null;
		String ApplicationName=null;		

		int [] allIssues=new int[10];		
		String[] IssueTypes={"open","assigned","accepted","rejected","escalated","closed","completed"};	
		
		int index=0;
		try
		{
			connection = DBConnection.createConnection();// connecting to the data base and retrieving Connection object
			if(strApplication.equalsIgnoreCase("allApps") && strIssueType!=null)
			{
				preparedStatement = connection.prepareStatement(VIMSQueryInterface.getSpecificIssuesInAllApplication,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);// retrieving PreparedStatement object and assigning to the local variable preparedStatement
				preparedStatement.setString(1,strIssueType);
				preparedStatement.setString(2,applicationStatus);
				resultSet = preparedStatement.executeQuery();// executing query and retrieving ResultSet Object assigned to class variable resultSet
				resultSet.last();
				Applications= new String[resultSet.getRow()];  
				specificIssues= new int[resultSet.getRow()];
				ApplicationNames=new String[resultSet.getRow()];  
				resultSet.beforeFirst(); 	
				while (resultSet.next())
				{//while started					
					Applications[index]=resultSet.getString(1);     		    
					specificIssues[index]=Integer.parseInt(resultSet.getString(2));
					ApplicationNames[index]=resultSet.getString(3);     
					index++;
				}//while closed
				SpecificIssuesInAllApplications = new HashMap();//initialization of local variable SpecificIssuesInAllApplications
				SpecificIssuesInAllApplications.put("ApplicationList", Applications);//putting values in to the HashMap SpecificIssuesInAllApplications
				SpecificIssuesInAllApplications.put("IssuesList", specificIssues);//putting values in to the HashMap SpecificIssuesInAllApplications
				SpecificIssuesInAllApplications.put("ApplicationNames", ApplicationNames);
			}
			else if((!strIssueType.equalsIgnoreCase("all")) && strApplication!=null)
			{
				preparedStatement = connection.prepareStatement(VIMSQueryInterface.getSpecificIssuesInApplication,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);// retrieving PreparedStatement object and assigning to the local variable preparedStatement
				preparedStatement.setString(1, strIssueType);
				preparedStatement.setString(2, strApplication);					
				resultSet = preparedStatement.executeQuery();// executing query and retrieving ResultSet Object assigned to class variable resultSet
				resultSet.last();
				Applications= new String[resultSet.getRow()];
				specificIssues= new int[resultSet.getRow()];
				resultSet.beforeFirst(); 
				while (resultSet.next())
				{//while started					
					Applications[index]=resultSet.getString(1);     		    
					specificIssues[index]=Integer.parseInt(resultSet.getString(2));  		    
					index++;
				}//while closed
				SpecificIssuesInAllApplications = new HashMap();//initialization of local variable SpecificIssuesInAllApplications
				SpecificIssuesInAllApplications.put("ApplicationList", Applications);//putting values in to the HashMap SpecificIssuesInAllApplications
				SpecificIssuesInAllApplications.put("IssuesList", specificIssues);//putting values in to the HashMap SpecificIssuesInAllApplications
			}
			
			else if(strIssueType.trim().equalsIgnoreCase("all") && strApplication!=null)
			{

				preparedStatement = connection.prepareStatement("exec vims_user.USP_Get_App_Issue_Status  @Application_ID=?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);// retrieving PreparedStatement object and assigning to the local variable preparedStatement
				preparedStatement.setString(1, strApplication);
				resultSet = preparedStatement.executeQuery();// executing query and retrieving ResultSet Object assigned to class variable resultSet	
				while (resultSet.next())
				{//while started
					allIssues[0]=Integer.parseInt(resultSet.getString(2)); 
					allIssues[1]=Integer.parseInt(resultSet.getString(3)); 
					allIssues[2]=Integer.parseInt(resultSet.getString(4)); 
					allIssues[3]=Integer.parseInt(resultSet.getString(5)); 
					allIssues[4]=Integer.parseInt(resultSet.getString(6)); 
					allIssues[5]=Integer.parseInt(resultSet.getString(7)); 
					allIssues[6]=Integer.parseInt(resultSet.getString(8)); 
					ApplicationName=resultSet.getString(9);

					index++;
				}//while closed
				SpecificIssuesInAllApplications = new HashMap();//initialization of local variable AllIssuesReports
				SpecificIssuesInAllApplications.put(IssueTypes[0], allIssues[0]);//putting values in to the HashMap AllIssuesReports
				SpecificIssuesInAllApplications.put(IssueTypes[1], allIssues[1]);//putting values in to the HashMap SpecificIssuesInAllApplications
				SpecificIssuesInAllApplications.put(IssueTypes[2], allIssues[2]);//putting values in to the HashMap SpecificIssuesInAllApplications
				SpecificIssuesInAllApplications.put(IssueTypes[3], allIssues[3]);//putting values in to the HashMap SpecificIssuesInAllApplications
				SpecificIssuesInAllApplications.put(IssueTypes[4], allIssues[4]);//putting values in to the HashMap SpecificIssuesInAllApplications
				SpecificIssuesInAllApplications.put(IssueTypes[5], allIssues[5]);//putting values in to the HashMap SpecificIssuesInAllApplications
				SpecificIssuesInAllApplications.put(IssueTypes[6], allIssues[6]);//putting values in to the HashMap SpecificIssuesInAllApplications
				SpecificIssuesInAllApplications.put("ApplicationName", ApplicationName);
			}				
			
		}//try closed 
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				preparedStatement.close();//closing of PreparedStatement
				DBConnection.closeConnection();//connection closed
				resultSet.close();//resultSet closed
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
			
		}
		return SpecificIssuesInAllApplications;	
	}
	
	/*
	 * This method is used to retrieve all issues details in an application.
	*/
	public static HashMap getAllIssuesReports(String strApplication, String strIssueType)
	{
		System.out.println("In VIMSReportsDAO getAllIssuesReports Method ");			
		HashMap AllIssuesReports=null;	//declaration of local variable AllIssuesReports  of type HashMap		
		String ApplicationName=null;
		int [] allIssues=new int[10];		
		String[] IssueTypes={"open","assigned","accepted","rejected","escalated","closed","completed"};		
		int index=0;
		try
		{
			connection = DBConnection.createConnection();// connecting to the data base and retrieving Connection object
			if(strIssueType.trim().equalsIgnoreCase("all"))
			{
				
				preparedStatement = connection.prepareStatement("exec vims_user.USP_Get_App_Issue_Status  @Application_ID=?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);// retrieving PreparedStatement object and assigning to the local variable preparedStatement
				preparedStatement.setString(1, strApplication);
				resultSet = preparedStatement.executeQuery();// executing query and retrieving ResultSet Object assigned to class variable resultSet	
				while (resultSet.next())
				{//while started
					allIssues[0]=Integer.parseInt(resultSet.getString(2)); 
					allIssues[1]=Integer.parseInt(resultSet.getString(3)); 
					allIssues[2]=Integer.parseInt(resultSet.getString(4)); 
					allIssues[3]=Integer.parseInt(resultSet.getString(5)); 
					allIssues[4]=Integer.parseInt(resultSet.getString(6)); 
					allIssues[5]=Integer.parseInt(resultSet.getString(7)); 
					allIssues[6]=Integer.parseInt(resultSet.getString(8)); 
					ApplicationName=resultSet.getString(9);

					index++;
				}//while closed
				AllIssuesReports = new HashMap();//initialization of local variable AllIssuesReports
				AllIssuesReports.put(IssueTypes[0], allIssues[0]);//putting values in to the HashMap AllIssuesReports
				AllIssuesReports.put(IssueTypes[1], allIssues[1]);//putting values in to the HashMap AllIssuesReports
				AllIssuesReports.put(IssueTypes[2], allIssues[2]);//putting values in to the HashMap AllIssuesReports
				AllIssuesReports.put(IssueTypes[3], allIssues[3]);//putting values in to the HashMap AllIssuesReports
				AllIssuesReports.put(IssueTypes[4], allIssues[4]);//putting values in to the HashMap AllIssuesReports
				AllIssuesReports.put(IssueTypes[5], allIssues[5]);//putting values in to the HashMap AllIssuesReports
				AllIssuesReports.put(IssueTypes[6], allIssues[6]);//putting values in to the HashMap AllIssuesReports
				AllIssuesReports.put("ApplicationName", ApplicationName);
			}
		}//try closed 
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				preparedStatement.close();//closing of PreparedStatement
				DBConnection.closeConnection();//connection closed
				resultSet.close();//resultSet closed
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
			
		}
		return AllIssuesReports;	
	}
	
	/*
	 * This method is used to retrieve SLA information of all applications
	*/
	public static HashMap getSLAInformationReportsDAO(String Status)
	{
		HashMap IssuesAndApplicationList=null;	//declaration of local variable SpecificIssuesInAllApplications  of type HashMap
		String[] SLAType={"Applications out of SLA","Applications within SLA"};	
		int [] SLACount=new int[2];
		try
		{
			connection = DBConnection.createConnection();// connecting to the data base and retrieving Connection object
			preparedStatement = connection.prepareStatement(VIMSQueryInterface.getSLAInformationReports);// retrieving Statement object and assigning to the local variable Statement
			preparedStatement.setString(1, Status); 
			resultSet = preparedStatement.executeQuery();// executing query and retrieving ResultSet Object assigned to class variable resultSet
			
			while (resultSet.next())
			{//while started					    
				SLACount[0]=Integer.parseInt(resultSet.getString(2));				
				SLACount[1]=Integer.parseInt(resultSet.getString(1));				
			}//while closed		
			
			IssuesAndApplicationList = new HashMap();//initialization of local variable SpecificIssuesInAllApplications
			IssuesAndApplicationList.put("ApplicationList",SLAType);//putting values in to the HashMap SpecificIssuesInAllApplications
			IssuesAndApplicationList.put("IssuesList",SLACount);//putting values in to the HashMap SpecificIssuesInAllApplications
			
		}//try closed 
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				preparedStatement.close();//closing of Statement
				DBConnection.closeConnection();//connection closed
				resultSet.close();//resultSet closed
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}
		return IssuesAndApplicationList;		
	}
	
	/*
	 * This method is used to retrieve all issues details of all applications
	*/
	
	public static ArrayList displayAllApplcationDetailsForSLAInformation()
	{
		ArrayList IssuesAndApplicationList=new ArrayList();
		HashMap hashMap=null;
		try
		{
			connection = DBConnection.createConnection();// connecting to the data base and retrieving Connection object
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);// retrieving Statement object and assigning to the local variable Statement
			resultSet = statement.executeQuery(VIMSQueryInterface.displayAllApplcationDetails);// executing query and retrieving ResultSet Object assigned to class variable resultSet
			
			while (resultSet.next())
			{//while started
				hashMap = new HashMap();//initialization of local variable SpecificIssuesInAllApplications
				
				hashMap.put("APPLICATION_NAME", resultSet.getString(1));//putting values in to the HashMap SpecificIssuesInAllApplications
				hashMap.put("INCIDENT_ID", resultSet.getString(2));//putting values in to the HashMap SpecificIssuesInAllApplications
				hashMap.put("INCIDENT_TITLE", resultSet.getString(3));//putting values in to the HashMap SpecificIssuesInAllApplications
				hashMap.put("INCT_STATUS", resultSet.getString(4));//putting values in to the HashMap SpecificIssuesInAllApplications
				hashMap.put("POSTED_DATE", resultSet.getString(5));//putting values in to the HashMap SpecificIssuesInAllApplications
				hashMap.put("DUE_DATE", resultSet.getString(6));//putting values in to the HashMap SpecificIssuesInAllApplications
				hashMap.put("CLOSED_DATE", resultSet.getString(7));//putting values in to the HashMap SpecificIssuesInAllApplications
				hashMap.put("SEVERITY", resultSet.getString(8));//putting values in to the HashMap SpecificIssuesInAllApplications
								
				IssuesAndApplicationList.add(hashMap);		
			}//while closed					
			
		}//try closed 
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				statement.close();//closing of Statement
				DBConnection.closeConnection();//connection closed
				resultSet.close();//resultSet closed
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		} 
		return IssuesAndApplicationList;
	}
	
	/*
	 * This method is used to retrieve all applications  witghin SLA and out of SLA
	*/
	public static ArrayList[] getApplicationByIncident(String Status)
	{
		HashMap hashMap=null;
		ArrayList[] applications=new ArrayList[2];
		applications[0]=new ArrayList();
		applications[1]=new ArrayList();
		try 
		{
			connection = DBConnection.createConnection();// connecting to the data base and retrieving Connection object
			preparedStatement = connection.prepareStatement(VIMSQueryInterface.getApplicationByIncident);
			preparedStatement.setString(1,Status);
			resultSet = preparedStatement.executeQuery();// executing query and retrieving ResultSet Object assigned to class variable resultSet
			
			while (resultSet.next())
			{//while started
				applications[0].add(resultSet.getString(1));
			}//while closed
			if(preparedStatement.getMoreResults())
			{
				resultSet=preparedStatement.getResultSet();
				while (resultSet.next())
				{//while started
					applications[1].add(resultSet.getString(1));
				}//while closed
			}
		}//try closed
		catch (Exception exception)
		{			
			exception.printStackTrace();
		}// retrieving PreparedStatement object and assigning to the local variable preparedStatement	
		
		finally
		{
			try
			{
				preparedStatement.close();//closing of PreparedStatement
				DBConnection.closeConnection();//connection closed
				resultSet.close();//resultSet closed
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			} 
		}
		return applications;
	}	
	
	/*
	 * This method is used to retrieve all application names and ID's
	*/
	public static ArrayList getApplicationsIdAndName()
	{
		ArrayList ApplicationsIdAndNames= new ArrayList();
		HashMap hashMap=null;//
		try 
		{
			connection = DBConnection.createConnection();// connecting to the data base and retrieving Connection object
			preparedStatement = connection.prepareStatement(VIMSQueryInterface.getIssuesInApplication);			
			resultSet = preparedStatement.executeQuery();// executing query and retrieving ResultSet Object assigned to class variable resultSet
			
			while (resultSet.next())
			{//while started
				hashMap= new HashMap();
				hashMap.put("Application_Id", resultSet.getString(1));
				hashMap.put("Application_Name", resultSet.getString(2));
				hashMap.put("IssuesCount",resultSet.getString(3));
				ApplicationsIdAndNames.add(hashMap);
			}//while closed	
		}//try closed
		catch (SQLException sqlException)
		{			
			sqlException.printStackTrace();
		}// 
		catch (Exception exception)
		{			
			exception.printStackTrace();
		}//	
		
		finally
		{
			try
			{
				preparedStatement.close();//closing of PreparedStatement
				DBConnection.closeConnection();//connection closed
				resultSet.close();//resultSet closed
			}
			catch(SQLException sqlException)
			{				
				sqlException.printStackTrace();
			}
			catch (Exception exception)
			{			
				exception.printStackTrace();
			}//			
		}
		return ApplicationsIdAndNames;
	}
	
	/*
	 * This method is used to retrieve list of applications names,groups ,supervisors,managers,employees etc
	*/
	public static ArrayList[] getCustomHomeDetailsDAO() {
		ArrayList[] arrayList = new ArrayList[8];		
		HashMap hashMap;		
		ResultSet resultSet=null;
		try
		{
			connection = DBConnection.createConnection();// connecting to the data base and retrieving Connection object
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);// retrieving Statement object and assigning to the local variable Statement
			resultSet=statement.executeQuery(VIMSQueryInterface.getCustomReportsCriteria);// executing query and retrieving ResultSet Object assigned to class variable resultSet
			arrayList[0]=new ArrayList();
			while (resultSet.next())
			{//while started 
				hashMap=new HashMap();
				hashMap.put("APPLICATION_ID", resultSet.getString(1));
				hashMap.put("APPLICATION_NAME", resultSet.getString(2));
				
				arrayList[0].add(hashMap);				
			}//while closed			
			
			int  index=1;
			while(statement.getMoreResults())
			{
				resultSet=statement.getResultSet();				
				if(resultSet!=null)
				{
					String[] keyNames=getKeyNames(index);
					arrayList[index]=new ArrayList();
					if((keyNames!=null) && (keyNames.length==2))
					{
						while (resultSet.next())
						{//while started
							hashMap=new HashMap();
							hashMap.put(keyNames[0], resultSet.getString(1));
							hashMap.put(keyNames[1], resultSet.getString(2));
												
							arrayList[index].add(hashMap);
							
						}//while closed
					}					
				}
				index++;
			}
		}
		catch (Exception exception)
		{			
			exception.printStackTrace();
		}// retrieving PreparedStatement object and assigning to the local variable preparedStatement	
		
		finally
		{
			try
			{
				statement.close();//closing of Statement
				DBConnection.closeConnection();//connection closed				
				resultSet.close();//ResultSet closed		
				
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			} 
		} 
		return arrayList;
	}
	
	/*
	 * This method is used to generate Reports in Custom Reports based on the selected criteria.
	*/
	public static ArrayList generateCustomReportsDAO(ActionForm actionForm, ArrayList arrayList) {
		CustomReportsActionForm  customReportsActionForm=(CustomReportsActionForm) actionForm;
		
		 String InctStatus=customReportsActionForm.getIssueStatus();
		 String InctPriority=customReportsActionForm.getIssuePriority();
		 String InctType=customReportsActionForm.getIssueSeverity();
		 String InctPostedFrom=customReportsActionForm.getCreatedFromDate();
		 String InctPostedTo=customReportsActionForm.getCreatedToDate(); 
		 String InctClosedFrom=customReportsActionForm.getClosedFromDate();
		 String InctCLosedTo=customReportsActionForm.getClosedToDate();
		 String ApplicationId=customReportsActionForm.getApplicationName(); 
		 String CustomerId=customReportsActionForm.getCustomerName();
		 String EmployeeId=customReportsActionForm.getEmployeeName();
		 String SupportCtrId=customReportsActionForm.getSupportCenter();
		 String SupportMgrId=customReportsActionForm.getSupportManager();
		 String GroupId=customReportsActionForm.getGroups();
		 String GroupSprId=customReportsActionForm.getGroupSupervisor();
		 String GroupMbrId=customReportsActionForm.getGroupMember();
		 String PurgingFlag=customReportsActionForm.getPurgingFlag();		
		
		ArrayList CustomReportsDAO=new ArrayList();
		HashMap hashMap;
		String SQLQuery="Exec vims_user.USP_Get_Custom_Report ";
		
		try 
		{
			connection = DBConnection.createConnection();// connecting to the data base and retrieving Connection object
			 if(InctStatus!=null &&(!InctStatus.equals("")))
			 {
				 SQLQuery=SQLQuery+"@INCT_STATUS='"+InctStatus+"',";				 
			 }
			 else 
			 {
				 SQLQuery=SQLQuery+"@INCT_STATUS="+getQueryBuilder(arrayList,"Status")+",";				 
			 }
			 if(InctPriority!=null &&(!InctPriority.equals("")))
			 {
				 SQLQuery=SQLQuery+"@INCIDENT_PRIORITY_TYPE='"+InctPriority+"',";				 
			 }
			 else
			 {
				 SQLQuery=SQLQuery+"@INCIDENT_PRIORITY_TYPE="+getQueryBuilder(arrayList,"Priority")+",";				
			 }
			 if(InctType!=null &&(!InctType.equals("")))
			 {
				  SQLQuery=SQLQuery+"@INCIDENT_TYPE='"+InctType+"',";				 
			 }
			 else
			 {
				 SQLQuery=SQLQuery+"@INCIDENT_TYPE="+getQueryBuilder(arrayList,"Severity")+",";				
			 }
			 if(InctPostedFrom!=null &&(!InctPostedFrom.equals("")))
			 {
				  SQLQuery=SQLQuery+"@INCIDENT_POSTED_DATE_From='"+InctPostedFrom+"',";				
			 }
			 else
			 {				 
				SQLQuery=SQLQuery+"@INCIDENT_POSTED_DATE_From="+getQueryBuilder(arrayList,"Created_Date")+",";
			 }
			 if(InctPostedTo!=null &&(!InctPostedTo.equals("")))
			 {
				  SQLQuery=SQLQuery+"@INCIDENT_POSTED_DATE_To='"+InctPostedTo+"',";
			 }
			 else
			 {
				 SQLQuery=SQLQuery+"@INCIDENT_POSTED_DATE_To="+getQueryBuilder(arrayList,"Created_Date")+",";
			 }
			 if(InctClosedFrom!=null &&(!InctClosedFrom.equals("")))
			 {
				  SQLQuery=SQLQuery+"@CLOSED_DATE_From='"+InctClosedFrom+"',";
			 }
			 else
			 {
				 SQLQuery=SQLQuery+"@CLOSED_DATE_From="+getQueryBuilder(arrayList,"Closed_Date")+",";
			 }
			 if(InctCLosedTo!=null &&(!InctCLosedTo.equals("")))
			 {
				  SQLQuery=SQLQuery+"@CLOSED_DATE_To='"+InctCLosedTo+"',";
			 }
			 else
			 {
				 SQLQuery=SQLQuery+"@CLOSED_DATE_To="+getQueryBuilder(arrayList,"Closed_Date")+",";
			 }
			 if(ApplicationId!=null &&(!ApplicationId.equals("")))
			 {
				  SQLQuery=SQLQuery+"@APPLICATION_ID='"+ApplicationId+"',";
			 }
			 else
			 {
				 SQLQuery=SQLQuery+"@APPLICATION_ID="+getQueryBuilder(arrayList,"Application")+",";
			 }
			 if(CustomerId!=null &&(!CustomerId.equals("")))
			 {
				  SQLQuery=SQLQuery+"@CUSTOMER_ID='"+CustomerId+"',";
			 }
			 else
			 {
				 SQLQuery=SQLQuery+"@CUSTOMER_ID="+getQueryBuilder(arrayList,"Customer")+",";
			 }
			 if(EmployeeId!=null &&(!EmployeeId.equals("")))
			 {
				  SQLQuery=SQLQuery+"@User_NM='"+EmployeeId+"',";
			 }
			 else
			 {
				 SQLQuery=SQLQuery+"@User_NM="+getQueryBuilder(arrayList,"Employee")+",";
			 }
			 if(SupportCtrId!=null &&(!SupportCtrId.equals("")))
			 {
				  SQLQuery=SQLQuery+"@SUPPORT_CENTER_ID='"+SupportCtrId+"',";
			 }
			 else
			 {
				 SQLQuery=SQLQuery+"@SUPPORT_CENTER_ID="+getQueryBuilder(arrayList,"Support_Center")+",";
			 }
			 if(SupportMgrId!=null &&(!SupportMgrId.equals("")))
			 {
				  SQLQuery=SQLQuery+"@SUP_CNTR_MANAGER='"+SupportMgrId+"',";
			 }
			 else
			 {
				 SQLQuery=SQLQuery+"@SUP_CNTR_MANAGER="+getQueryBuilder(arrayList,"Manager")+",";
			 }
			 if(GroupId!=null &&(!GroupId.equals("")))
			 {
				  SQLQuery=SQLQuery+"@USRGROUP_ID='"+GroupId+"',";
			 }
			 else
			 {
				 SQLQuery=SQLQuery+"@USRGROUP_ID="+getQueryBuilder(arrayList,"Group")+",";
			 }
			 if(GroupSprId!=null &&(!GroupSprId.equals("")))
			 {
				  SQLQuery=SQLQuery+"@GROUP_SUPERVISOR='"+GroupSprId+"',";
			 }
			 else
			 {
				 SQLQuery=SQLQuery+"@GROUP_SUPERVISOR="+getQueryBuilder(arrayList,"Supervisor")+",";
			 }
			 if(GroupMbrId!=null &&(!GroupMbrId.equals("")))
			 {
				  SQLQuery=SQLQuery+"@Group_User_NM='"+GroupMbrId+"',";
			 }
			 else
			 {
				 SQLQuery=SQLQuery+"@Group_User_NM="+getQueryBuilder(arrayList,"Assigned_To")+",";
			 }
			 if(PurgingFlag!=null &&(!PurgingFlag.equals("")))
			 {
				  SQLQuery=SQLQuery+"@PURGING_FLAG='"+PurgingFlag+"'";
			 }
			 else
			 {
				 SQLQuery=SQLQuery+"@PURGING_FLAG="+getQueryBuilder(arrayList,"Purging_Status");
			 }
			 
			 System.out.println("SQL Query in DAO class is "+SQLQuery);
			 preparedStatement = connection.prepareStatement(SQLQuery);
	
			 boolean Result = preparedStatement.execute();// executing query and retrieving ResultSet Object assigned to class variable resultSet
			
			 resultSet=preparedStatement.getResultSet();
			while (resultSet.next())
			{//while started
				hashMap=new HashMap();
				
				for(int index=0;index<arrayList.size();index++)
				{
					hashMap.put((String)arrayList.get(index),resultSet.getString((String)arrayList.get(index)));
				}					
				CustomReportsDAO.add(hashMap);
			}//while closed				
		}//try closed
		catch (Exception exception)
		{			
			exception.printStackTrace();
		}// retrieving PreparedStatement object and assigning to the local variable preparedStatement	
		
		finally
		{
			try
			{
				preparedStatement.close();//closing of PreparedStatement
				DBConnection.closeConnection();//connection closed
				resultSet.close();//resultSet closed
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			} 
		}
		return CustomReportsDAO;
	}	
	/*
	 * This method is used to retrieve column name corresponding to the selected item in selected criteria.
	*/
	public static String[] getKeyNames(int index)
	{
		String[][] ApplicationKeyNames={
				{"APPLICATION_ID","APPLICATION_NAME"},
				{"CUSTOMER_ID","CUSTOMER_NAME"},
				{"SUPPORT_CENTER_ID","SUPP_CENTER_NAME"},
				{"USRGROUP_ID","GROUP_NAME"},
				{"User_NM","Member_Name"},
				{"User_NM","Employee_Name"},
				{"GROUP_SUPERVISOR_ID","Supervisor_Name"},
				{"SUPP_CENTER_MANAGER_ID","Manager_Name"}				
				};
		
		return ApplicationKeyNames[index];
	}
	public static String getQueryBuilder(ArrayList arrayList,String ColumnName)
	{
		for(int index=0;index<arrayList.size();index++)
		{
			if(ColumnName.equalsIgnoreCase((String)arrayList.get(index)))
			{
				if(ColumnName.equalsIgnoreCase("Created_Date") || ColumnName.equalsIgnoreCase("Closed_Date"))
				{
					return "''";
				}
				else
				{
					return "'"+(String)arrayList.get(index)+"'";
				}
			}
		}
		return "null";
	}




	public static ArrayList getSelectedAndSelectCriteriaDAO(String[] selectedCriteria, String[] customIdList) {		
		ArrayList CustomReportsList=new ArrayList();
		boolean found=false;
		
		for(int cnt=0;cnt<customIdList.length;cnt++)
		{
    	    found=false; 
    	    for(int count=0;count<selectedCriteria.length;count++)
             {	            	 
            	    if((customIdList[cnt].equalsIgnoreCase((String)selectedCriteria[count])))
            	    {
            	    	 found=true;
            	         break;
            	    }
            	    else
            	    {
            	    	found=false; 
            	    }
            	        
             } 
             if(found==false)
             {
            	 CustomReportsList.add(customIdList[cnt]);	
             } 
		}
		return CustomReportsList;
	}
}

