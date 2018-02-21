/*
  FileName	    : VIMSSearchDAO.java
  
 
  Description	: This Data Access Object class is used to call different queries 
  
                  from query interface class and access those Queries where needed. 
    
  Developed by  : Vertex Computer Systems.
                  copyright (c) 2008 Vertex.
                  All rights reserved.
  
  Change History:
 
  Revision No.	:		Date		  @author		Description
    1.0				Friday 21,2008	  Geeta.M	   Initial Version.*/


//Package structure followed for this class.
  package com.vertex.VIMS.test.search.DAO;

//import statements
  import java.io.File;
  import java.io.IOException;
  import java.sql.*;
  import java.util.*;

import javax.servlet.http.HttpSession;

  import jxl.Workbook;
  import jxl.WorkbookSettings;
  import jxl.write.Label;
	import jxl.write.WritableCellFormat;
	import jxl.write.WritableFont;
	import jxl.write.WritableImage;
	import jxl.write.WritableSheet;
	import jxl.write.WritableWorkbook;

  import org.apache.log4j.Logger;
  import com.vertex.VIMS.test.VIMSSQLQueryInterface.VIMSQueryInterface;
  import com.vertex.VIMS.test.common.DBConnection;
import com.vertex.VIMS.test.search.form.VIMSSearchForm;

//Start of DAO class
  public class VIMSSearchDAO 
   {
	 
	 //Currently initializing the objects to null values and as static variables as all the methods are static  
	   static Connection connection=null;
	   static ResultSet resultset=null;
	   static Statement statement=null;
	   static HashMap hashmap=null;
	   static ArrayList arrayList=null;
	   static PreparedStatement preparedStatement=null;
	   static CallableStatement callablestmt=null;
	
  //Initialising Logger 
    static Logger logger=null;
    
  //Start of getSearchApplicationNamesDAO() and is used to get the APPLICATION_NAME from APPLICATION table
    
    public static ArrayList getSearchApplicationNamesDAO(String strUserID, String strRoleType,String strCustomerID)
	 {
    	//Adding all the exception to the getLogger method for admin module 
    	  logger=Logger.getLogger("Admin"); 
    	
        //Creating a new arrayList
          arrayList=new ArrayList();
          //System.out.println("--------Customer ID in DAO---------"+strCustomerID);
      if(strRoleType.equals("Admin"))
        {
          //Start of try block
    	 try
		   {
			 //Getting the connection object
    		   connection = DBConnection.createConnection();
			 //Creating a statement object
    		   statement = connection.createStatement();
			 
    		   if(strCustomerID==null)
    		   {
    		   //Calling an execute query method which results a resultset
    		       //System.out.println("---In Customer null condition-----");
    			   resultset = statement.executeQuery(VIMSQueryInterface.SearchApplicationNamesSql);
			   //resultset = statement.executeQuery("select APPLICATION_NAME FROM APPLICATION");
    		   }
    		   else if(strCustomerID!=null)
    		   {
    			    preparedStatement=connection.prepareStatement(VIMSQueryInterface.SearchCustApplicationNamesSql);						
    				preparedStatement.setString(1,strCustomerID);							
    	  		    resultset = preparedStatement.executeQuery();
    			    //System.out.println("---In Customer notNull condition-----");
    			   //resultset = statement.executeQuery(VIMSQueryInterface.SearchCustApplicationNamesSql);
    		   }
    	  //start of while loop
    	    while(resultset.next())
			 {
				
			  //creating a new hashmap object
				hashmap = new HashMap();
				hashmap.put("applicationID", resultset.getString(1));
				hashmap.put("applicationName", resultset.getString(2));
				 //arrayList.add(resultset.getString(1));
              //Adding the hashmap to arrayList object
			  arrayList.add(hashmap);				 
			}
			//end of while loop
		} 
		//end of try block 
		
    	//start of catch block
		  catch (Exception exception)
		   {
			 logger.info("VIMSSearchDAO.getSearchApplicationNamesDAO");
	    	 logger.error(exception);
			 System.out.println("==========Exception Occured=========="+exception);
			 exception.printStackTrace();
		   }
		//end of catch block
        
		//start of finally block
		  finally
		   {
			//start of try block
			 try
			    {
				//closing the DBConnection
				  DBConnection.closeConnection();
			    } 
			//end of try block 
			
		    //start of catch block
			catch (Exception exception)
			 {
				System.out.println("==========exception Occured while closing the connection==============="+exception);
				exception.printStackTrace();
			 }
			//end of catch block
		  }
		//end of finally block
		  return arrayList;
        }
		//Returns an arrayList	  
 
  //This if condition is executed if the logged in is user(employee)...
	
    else if(strRoleType.equals("User"))
     {
		 
		//System.out.println("--------In User-----------");
		try
		   {
			 //Getting the connection object
  		    connection = DBConnection.createConnection();
		  //Calling an execute query method which results a resultset
  		    preparedStatement=connection.prepareStatement(VIMSQueryInterface.UserApplicationNamesSql);						
			preparedStatement.setString(1,strUserID);							
			
  		    resultset = preparedStatement.executeQuery();
			
  	  //start of while loop
  	    while(resultset.next())
			 {
				
			  //creating a new hashmap object
				hashmap = new HashMap();
				hashmap.put("applicationID", resultset.getString(1));
				hashmap.put("applicationName", resultset.getString(2));
				//System.out.println("------------applicationName in User--------"+resultset.getString(2));
            //Adding the hashmap to arrayList object
			  arrayList.add(hashmap);				 
			}
			//end of while loop
		} 
		//end of try block 
		
  	//start of catch block
		  catch (Exception exception)
		   {
			 logger.info("VIMSSearchDAO.getSearchApplicationNamesDAO");
	    	 logger.error(exception);
			 System.out.println("==========Exception Occured=========="+exception);
			 exception.printStackTrace();
		   }
		  //System.out.println("-------arrayList---------"+arrayList);
		  return arrayList;
		 
     }
      
  //This if condition is executed if the logged in user is Customer...	
	
    else if(strRoleType.equals("Customer"))
     {
		//System.out.println("--------In Customer-----------");
		try
		   {
			 //Getting the connection object
		    connection = DBConnection.createConnection();
		    /*preparedStatement=connection.prepareStatement(VIMSQueryInterface.CustomerNewIssue);						
			preparedStatement.setString(1,strUserID);							
			
 		    resultset = preparedStatement.executeQuery();*/
		    CallableStatement callableStatement=null;
			 
		     callableStatement=connection.prepareCall("{CALL vims_user.USP_Get_Application_Dtls(?,?,?,?,?,?,?)}");
			 callableStatement.setString(1,null);
			 callableStatement.setString(2,"Active");
			 callableStatement.setString(3,null);
			 callableStatement.setString(4,strUserID);
			 callableStatement.setString(5,null);
			 callableStatement.setString(6,null);
			 callablestmt.setString(7,strUserID); 
			 callableStatement.execute();
			 resultset=callableStatement.getResultSet();
			
		    
	  //start of while loop
	    while(resultset.next())
			 {
				
			  //creating a new hashmap object
				hashmap = new HashMap();
				hashmap.put("applicationID", resultset.getString(1));
				hashmap.put("applicationName", resultset.getString(2));
				 
				//System.out.println("------------applicationName in Customer--------"+resultset.getString(2));
		   //Adding the hashmap to arrayList object
			  arrayList.add(hashmap);				 
			}
			//end of while loop
		} 
		//end of try block 
		
	//start of catch block
		  catch (Exception exception)
		   {
			 logger.info("VIMSSearchDAO.getSearchApplicationNamesDAO");
	    	 logger.error(exception);
			 System.out.println("==========Exception Occured=========="+exception);
			 exception.printStackTrace();
		   }
		 
     }
      //System.out.println("-------arrayList---------"+arrayList);
	return arrayList;
  }
	
 //End of getSearchApplicationNamesDAO() method.

 /*Start of getSearchCustomerNamesDAO() method.
   This method is used to get the CUSTOMER_NAMES from CUSTOMER table*/	
   
   public static ArrayList getSearchCustomerNamesDAO()
	{
		//initialising the logger object
	      logger=Logger.getLogger("Admin"); 
		  
	    //Creating a new ArrayList object
		  arrayList=new ArrayList();
		  
	   //start of try block	
	     try
		   {
			
			//Getting the connection object
		      connection = DBConnection.createConnection();
		    //Creating a statement object  
		      statement = connection.createStatement();
		    //Calling an execute query method which results a resultset  
		      resultset = statement.executeQuery(VIMSQueryInterface.SearchCustomerNamesSql);
			  //resultset = statement.executeQuery("select CUSTOMER_NAME FROM CUSTOMER");
			
		   //start of while loop
		    while(resultset.next())
			 {
				
		       //creating a new hashmap object
				 hashmap = new HashMap();
				 hashmap.put("customerName", resultset.getString(1));
				 hashmap.put("customerID", resultset.getString(2));
					
			  //Adding hashMap to arrayList object	
				arrayList.add(hashmap);
								 
			 }
		  //end of while loop
		} 
		//end of try block 
		
	    //start of catch block
		catch (Exception exception)
		{
			//Adding an info to the logger 
			logger.info("VIMSSearchDAO.getSearchCustomerNamesDAO");
	    	logger.error(exception);
			System.out.println("==========Exception Occured=========="+exception);
			exception.printStackTrace();
		}
		//end of catch block
		
		//start of finally block
		  finally
		  {
		   //start of try block
			 try
			   {
				//closing the DBConnection
				  DBConnection.closeConnection();
			   } 
		  //end of try block 
			
		 //start of catch block	
		   catch (Exception exception)
			 {
			    System.out.println("==========exception Occured while closing the connection==============="+exception);
				exception.printStackTrace();
			 }
		 //end of catch block
		}
		//end of finally block
		
	   //Returns an arrayList	    
	     return arrayList;
	}
   //End of getSearchCustomerNamesDAO() method

  /*Start of getSearchStatusDAO() method.
   This method is used to get the status from INC_STATUS table*/	
   public static ArrayList getSearchStatusDAO()
	{
		
	    //initialising the logger object
	      logger=Logger.getLogger("Admin"); 
		
	   //creating a new ArrayList Object	 
	     arrayList=new ArrayList();
		 
	   //start of try block
		 try
		   {
			 //Getting the connection object
			  connection = DBConnection.createConnection();
			
		    //Creating a statement object 
			  statement = connection.createStatement();
		    
		    //Calling an execute query method which results a resultset    
			  resultset = statement.executeQuery(VIMSQueryInterface.SearchStatusSql);
			
		  //start of while loop	  
		    while(resultset.next())
			 {
				
		      //creating a new hashmap object
				hashmap = new HashMap();
				hashmap.put("statusID", resultset.getString(1));
				hashmap.put("status", resultset.getString(2));
				
			  //Adding hashMap to arrayList object
				arrayList.add(hashmap);
								 
			 }
		   //end of while loop
		} 
		//end of try block 
		
		//start of catch block	 
	   catch (Exception exception)
		{
			
		  //Adding errors in this method to logger	
		    logger.info("VIMSSearchDAO.getSearchStatusDAO");
	    	logger.error(exception);
			System.out.println("==========Exception Occured=========="+exception);
			exception.printStackTrace();
		}
		//end of catch block
		
	   //start of finally block
	     finally
		    {
	    	 //start of try block
	    	 try
			     {
	    		 //closing the DBConnection
	    		   DBConnection.closeConnection();
			     } 
			 //end of try block 
			
	         //start of catch block	 
	         catch (Exception exception)
			   {
				 System.out.println("==========exception Occured while closing the connection==============="+exception);
				 exception.printStackTrace();
			   }
		      //end of catch block
		    }
		//end of finally block
		
	    //Adding hashMap to arrayList object 
	     return arrayList;
	}
 //End of getSearchStatusDAO() method
 
  /*Start of getSearchSeverityDAO() method.
   This method is used to get the severity from INCIDENT_TYPE table*/	
	
   public static ArrayList getSearchSeverityDAO()
	{
	  
	  //initialising the logger object
	    logger=Logger.getLogger("Admin"); 
		
	  //creating a new ArrayList Object	
        arrayList=new ArrayList();
		
      //start of try block
		try
		{
			
			connection = DBConnection.createConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery(VIMSQueryInterface.SearchSeveritySql);
			//resultset = statement.executeQuery("select INCIDENT_TYPE_DESC from INCIDENT_TYPE");
			
		  //start of while loop	
			while(resultset.next())
			 {
				
		      //creating a new hashmap object
				hashmap = new HashMap();
				hashmap.put("severityID", resultset.getString(1));
				hashmap.put("severity", resultset.getString(2));
				
			  //Adding hashMap to arrayList object	
				arrayList.add(hashmap);
								 
			}
		 //end of while loop
		} 
		//end of try block 
		
	   //start of catch block 	
	     catch (Exception exception)
		  {
			logger.info("VIMSSearchDAO.getSearchSeverityDAO");
	    	logger.error(exception);
			System.out.println("==========Exception Occured=========="+exception);
			exception.printStackTrace();
		  }
	  //end of catch block
		
	  //start of finally block   
	    finally
		   {
			  //start of try block  
			    try
			      {
			      //closing the DBConnection
			    	DBConnection.closeConnection();
			      } 
			  //end of try block 
			  
			  //start of catch block
                catch (Exception exception)
			     {
				   System.out.println("==========exception Occured while closing the connection==============="+exception);
				   exception.printStackTrace();
			     }
			 //end of catch block
		   }
	  //end of finally block
		
	  //Adding hashMap to arrayList object
	    return arrayList;
    }
  //End of getSearchSeverityDAO() method
   
 /*Start of searchRecordDAO() method.
   This method is used to get the records from INCIDENT,CUSTOMER and APPLICATION tables by passing form bean object as parameter*/	 
	
   public static ArrayList searchRecordDAO(VIMSSearchForm searchForm, String strUserID, String strRoleType)
	{
		
	   //initialising the logger object
	    Logger logger=Logger.getLogger("Admin"); 
		PreparedStatement preparedStatement=null;
		String strID=null;
		String applicationName=null;
		String severity=null;
		String customerName=null;
		String status=null;
		int flag=0;
		String strIssueID=null;
	    String strApplicationName=null;
	    String strSeverity=null;
	    String strStatus=null;
	    String strCustomerName=null;
	    String strTitle=null;
		  
		 
	    CallableStatement callablestmt=null;
	    ResultSet resultSet =null;	
	    
	    String strSearchCriteria="Search based on :";
	  //creating a new ArrayList Object
		arrayList=new ArrayList();
		
		//HttpSession session=;
		//System.out.println("---------In searchRecordDAO---------");
	 //start of try block	
		try
		{
			  connection = DBConnection.createConnection();
			  statement = connection.createStatement();
			
			//Getting the values from Form Bean
			  String custName=searchForm.getCustomerName();
			 
			  if(custName==null || "".equals(custName)){
				  custName=null;   
		       } 
			  //System.out.println("-----------Customer Id---------"+custName);
			  
			  String issueID=searchForm.getIssueID();
			  if(issueID==null || "".equals(issueID)){
				  issueID=null;   
		       } 
			  String appName=searchForm.getApplicationName();
			  if(appName==null || "".equals(appName)){
				  appName=null;   
		       } 
			  String strstatus= searchForm.getStatus();
			  if(strstatus==null || "".equals(strstatus)){
				  strstatus=null;   
		       } 
			  String strseverity=searchForm.getSeverity();
			  if(strseverity==null || "".equals(strseverity)){
				  strseverity=null;   
		       } 
			  String fromDate=searchForm.getFromDate();

			  if(fromDate==null || "".equals(fromDate)){
				  fromDate=null;   
		       }

			  //System.out.println("===========fromDate========"+fromDate);

			  String toDate=searchForm.getToDate();

			  if(toDate==null || "".equals(toDate)){
				  toDate=null;   
		       }

			  //System.out.println("===========toDate========"+toDate);

			  String title=searchForm.getTitle();
			  if(title==null || "".equals(title)){
				  title=null;   
		       }
			  
			  
			  
			//Query to get records from DB using different if conditions
	  String strQuery=null;
	  if(strRoleType.equals("Admin"))
		{
		    //System.out.println("--------In RoleType Admin-------------"); 
		    callablestmt=connection.prepareCall("{CALL vims_user.USP_Search_Issue(?,?,?,?,?,?,?,?,?,?,?)}");//preparing to call the stored procedure
			
		    //callablestmt.setString(1,custName);
			
		    callablestmt.setString(1,appName);
			callablestmt.setString(2,custName);
			callablestmt.setString(3,strstatus);
			callablestmt.setString(4,strseverity);
			callablestmt.setString(5,issueID);
			callablestmt.setString(6,title);
			callablestmt.setString(7,fromDate);
			callablestmt.setString(8,toDate);
			callablestmt.setString(9,null);
			callablestmt.setString(10,null);
			callablestmt.setString(11,strUserID);
			
			resultSet = callablestmt.executeQuery();	
			
		  //strQuery="select DISTINCT inc.INCIDENT_ID,app.APPLICATION_NAME,inct.INCIDENT_TYPE_DESC,inc.INCT_STATUS,cust.CUSTOMER_NAME,inc.DUE_DATE,inc.CLOSED_DATE,inc.INCIDENT_TITLE FROM INCIDENT inc INNER JOIN APPLICATION app ON inc.APPLICATION_ID=app.APPLICATION_ID INNER JOIN INCIDENT_TYPE inct ON inc.INCIDENT_TYPE= inct.INCIDENT_TYPE inner join APP_SOLD_TO APPS on inc.Application_ID = apps.Application_ID inner join Customer cust ON apps.Customer_id= cust.Customer_ID";
		  //System.out.println("--------strQuery in Admin---------"+strQuery);
		}
	   else if(strRoleType.equals("User"))
	   {
			  
		    callablestmt=connection.prepareCall("{CALL vims_user.USP_Search_Issue(?,?,?,?,?,?,?,?,?,?,?)}");//preparing to call the stored procedure
		   
		    //callablestmt.setString(1,custName);
			callablestmt.setString(1,appName);
			callablestmt.setString(2,null);
			callablestmt.setString(3,strstatus);
			callablestmt.setString(4,strseverity);
			callablestmt.setString(5,issueID);
			callablestmt.setString(6,title);
			callablestmt.setString(7,fromDate);
			callablestmt.setString(8,toDate);
			callablestmt.setString(9,strUserID);
			callablestmt.setString(10,null);
			callablestmt.setString(11,strUserID);
				
			resultSet = callablestmt.executeQuery();	
		
		   //strQuery="SELECT DISTINCT inc.INCIDENT_ID,app.APPLICATION_NAME,inct.INCIDENT_TYPE_DESC,inc.INCT_STATUS,CONVERT(VARCHAR(20),inc.DUE_DATE,101) AS [DUE_DATE],CONVERT(VARCHAR(20),inc.CLOSED_DATE,101) AS [CLOSED_DATE],inc.INCIDENT_TITLE FROM GROUP_MEMBERS gm INNER JOIN GROUP_APPLICATION ga ON gm.USRGROUP_ID = ga.USRGROUP_ID INNER JOIN APPLICATION app ON ga.APPLICATION_ID=app.APPLICATION_ID INNER JOIN INCIDENT inc ON app.APPLICATION_ID = inc.APPLICATION_ID INNER JOIN INCIDENT_TYPE inct ON inc.Incident_TYPE=inct.INCIDENT_TYPE AND gm.USER_NM='"+strUserID+"'";
		   //System.out.println("--------strQuery in User---------"+strQuery);
			   
	   }
	   else if(strRoleType.equals("Customer"))
	   {

		    callablestmt=connection.prepareCall("{CALL vims_user.USP_Search_Issue(?,?,?,?,?,?,?,?,?,?,?)}");//preparing to call the stored procedure
			callablestmt.setString(1,appName);
			callablestmt.setString(2,custName);
			callablestmt.setString(3,strstatus);
			callablestmt.setString(4,strseverity);
			callablestmt.setString(5,issueID);
			callablestmt.setString(6,title);
			callablestmt.setString(7,fromDate);
			callablestmt.setString(8,toDate);
			callablestmt.setString(9,null);
			//System.out.println("---------String UserId in Customer Elseif---------"+strUserID);
			callablestmt.setString(10,strUserID);
			callablestmt.setString(11,strUserID);
				
			resultSet = callablestmt.executeQuery();	
		 
		      //strQuery="SELECT DISTINCT inc.INCIDENT_ID,app.APPLICATION_NAME,inct.INCIDENT_TYPE_DESC,inc.INCT_STATUS,CONVERT(VARCHAR(10),inc.DUE_DATE,101) AS [DUE_DATE],CONVERT(VARCHAR(10),inc.CLOSED_DATE,101) AS [CLOSED_DATE],inc.INCIDENT_TITLE	FROM INCIDENT inc INNER JOIN APPLICATION app ON inc.APPLICATION_ID=app.APPLICATION_ID INNER JOIN APP_SOLD_TO apps ON app.APPLICATION_ID = apps.APPLICATION_ID INNER JOIN INCIDENT_TYPE inct ON inc.INCIDENT_TYPE= inct.INCIDENT_TYPE WHERE apps.Application_ID='"+strUserID+"'";
			  //System.out.println("--------strQuery in Customer---------"+strQuery);
			  
		    }
	  
	   /* if(custName!=null &&(!custName.equals("")))
		 {
		    strQuery=strQuery+" and cust.CUSTOMER_ID='"+custName+"'";
		 }
		if(appName!=null &&(!appName.equals("")))
		 {
		   strQuery=strQuery+" and app.APPLICATION_NAME='"+appName+"'";
		 }
		if(strstatus!=null &&(!strstatus.equals("")))
		 {
		   strQuery=strQuery+" and inc.INCT_STATUS='"+strstatus+"'";
		 }
		if(strseverity!=null &&(!strseverity.equals("")))
		 {
		   strQuery=strQuery+" and inc.INCIDENT_TYPE='"+strseverity+"'";
		 }
		if(issueID!=null &&(!issueID.equals("")))
		 {
		   strQuery=strQuery+" and inc.INCIDENT_ID like '%"+issueID+"%'";
		 }
		if(fromDate!=null &&(!fromDate.equals("")))
		 {
			
		   strQuery=strQuery+" and inc.DUE_DATE='"+fromDate+"'";
		   System.out.println("============FromDate===in fromdate========="+fromDate);
		   System.out.println("============strQuery====in fromdate========"+strQuery);
		   
		 }
		if(toDate!=null &&(!toDate.equals("")))
		 {
		   strQuery=strQuery+" and inc.CLOSED_DATE='"+toDate+"'";
		   System.out.println("============ToDate===in toDate========="+toDate);
		   System.out.println("============strQuery====in todate========"+strQuery);
		 }
		if(title!=null &&(!title.equals("")))
		 {
		   strQuery=strQuery+" and inc.INCIDENT_TITLE like '%"+title+"%'";
		 }*/
	  
    //ResultSet resultSet = statement.executeQuery(strQuery);
   //System.out.println("===========strQuery============="+strQuery);
   //start of while loop
		  while(resultSet.next())
		   {
			  //creating a new hashmap object
			    hashmap = new HashMap();
			    
			    //String linkView="<a href='./ListofIssues.do?methodname=IssueDetails&id="+(String)resultSet.getString(1)+"'>"+resultSet.getString(1)+"</a>";


				hashmap.put("Issue_ID", resultSet.getString(1));
				hashmap.put("Application_Name", resultSet.getString(2));
				hashmap.put("Severity", resultSet.getString(3));				
				hashmap.put("Status", resultSet.getString(4));
				hashmap.put("Customer_Name", resultSet.getString(5));

				
			     strIssueID=(String)resultSet.getString(1);
			     
			     strApplicationName=(String)resultSet.getString(2);
			     strSeverity=(String)resultSet.getString(3);
			     strStatus=(String)resultSet.getString(4);
			     strCustomerName=(String)resultSet.getString(5);
			     strTitle=(String)resultSet.getString(8);
			    
		    //System.out.println("----------arrayList after Search---------"+arrayList);
		    
		  //Adding hashMap to arrayList object	
		   arrayList.add(hashmap);
		   }
		  if(strRoleType.equals("Admin"))
			{
			  if((searchForm.getCustomerName()!=null)&&(!(searchForm.getCustomerName()).equals(""))) {
				if(flag==0)
				 strSearchCriteria=strSearchCriteria+"Customer Name--"+strCustomerName;
				else
				 strSearchCriteria=strSearchCriteria+",Customer Name--"+strCustomerName;
			  flag=1;
			  //System.out.println("-------Flag---------"+flag);
		    }
			}
			  if((searchForm.getIssueID()!=null)&&(!(searchForm.getIssueID()).equals(""))) {
					if(flag==0)
					 strSearchCriteria=strSearchCriteria+"Issue ID--"+strIssueID;
					else
					 strSearchCriteria=strSearchCriteria+",Issue ID--"+strIssueID;
				  flag=1;
				  //System.out.println("-------Flag---------"+flag);
			    }
			  if((searchForm.getApplicationName()!=null)&&(!(searchForm.getApplicationName()).equals(""))) {
					if(flag==0)
					 strSearchCriteria=strSearchCriteria+"Application Name--"+strApplicationName;
					else
					 strSearchCriteria=strSearchCriteria+",Application Name--"+strApplicationName;
				  flag=1;
				  //System.out.println("-------Flag---------"+flag);
			    }
			  if((searchForm.getStatus()!=null)&&(!(searchForm.getStatus()).equals(""))) {
					if(flag==0)
					 strSearchCriteria=strSearchCriteria+"Status--"+strStatus;
					else
					 strSearchCriteria=strSearchCriteria+",Status--"+strStatus;
				  flag=1;
				  //System.out.println("-------Flag---------"+flag);
			    }
			  if((searchForm.getSeverity()!=null)&&(!(searchForm.getSeverity()).equals(""))) {
					if(flag==0)
					 strSearchCriteria=strSearchCriteria+"Severity--"+strSeverity;
					else
					 strSearchCriteria=strSearchCriteria+",Severity--"+strSeverity;
				  flag=1;
				  //System.out.println("-------Flag---------"+flag);
			    } 
			  if((searchForm.getFromDate()!=null)&&(!(searchForm.getFromDate()).equals(""))) {
					if(flag==0)
					 strSearchCriteria=strSearchCriteria+"From Date--"+fromDate;
					else
					 strSearchCriteria=strSearchCriteria+",From Date--"+fromDate;
				  flag=1;
				  //System.out.println("-------Flag---------"+flag);
			    } 
			  if((searchForm.getToDate()!=null)&&(!(searchForm.getToDate()).equals(""))) {
					if(flag==0)
					 strSearchCriteria=strSearchCriteria+"To Date--"+toDate;
					else
					 strSearchCriteria=strSearchCriteria+",To Date--"+toDate;
				  flag=1;
				  //System.out.println("-------Flag---------"+flag);
			    } 
			  if((searchForm.getTitle()!=null)&&(!(searchForm.getTitle()).equals(""))) {
					if(flag==0)
					 strSearchCriteria=strSearchCriteria+"Title--"+strTitle;
					else
					 strSearchCriteria=strSearchCriteria+",Title--"+strTitle;
				  flag=1;
				  //System.out.println("-------Flag---------"+flag);
			    } 
			  if(flag==0)
				  if(strRoleType.equals("Admin"))
					{	 
				   strSearchCriteria="Search based on: Customer Name--All, Title-All, Issue ID--All, Application--All, Status--All, Severity--All";
					}
				  else
				  {
				   strSearchCriteria="Search based on: Title-All, Issue ID--All, Application--All, Status--All, Severity--All";
				  }
			  //System.out.println("-------Flag---------"+flag);
		 } 
		//end of try block 
		
		//start of catch block
		  catch (Exception exception)
		    {
			    logger.info("VIMSSearchDAO.searchRecordDAO");
		    	logger.error(exception);
				System.out.println("==========Exception Occured=========="+exception);
				exception.printStackTrace();
    	    }
		  //System.out.println("----------SearchCriteria---------"+strSearchCriteria);
		  
		  if(arrayList.size()>0)
		  {
			/*  if(strSearchCriteria.equalsIgnoreCase("Search based on: "))
				{
				  System.out.println("-------In Search based on: All-----");
				  session.setAttribute("searchCriteria",strSearchCriteria);
				}
				else
				{
					System.out.println("-------Else block of strSearchCriteria-----"+strSearchCriteria);
					session.setAttribute("searchCriteria", strSearchCriteria);
					System.out.println("===VIMS----------------");
					System.out.println(arrayList);
				}*/
		  }
		  
			
	  //end of catch block

	 //System.out.println("============arrayList============="+arrayList);	  

		  arrayList.add(strSearchCriteria); 

	  return arrayList;
	 }


   
   }
