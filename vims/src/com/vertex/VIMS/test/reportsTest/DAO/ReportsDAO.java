package com.vertex.VIMS.test.reportsTest.DAO;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.vertex.VIMS.test.common.DBConnection;
import com.vertex.VIMS.test.newissue.DAO.NewIssueDAO;
import com.vertex.VIMS.test.reportsTest.form.ApplicationReportsForm;
import com.vertex.VIMS.test.reportsTest.form.IssueReportForm;
import com.vertex.VIMS.test.reportsTest.excelFiles.ExcelGenerator;
public class ReportsDAO {
       
	 /*static Connection connection=null;
	 static ResultSet resultSet=null;
	 static Statement statement=null;
	 static PreparedStatement preparedStatement=null;
	 static CallableStatement callableStatement=null;*/
	 
	 static Logger logger=null;
	 
	  public static HashMap getIssueSettingsDAO() {
		        Logger logger=null;
		        
		        ArrayList statusTypes=null;
		        ArrayList issueSeverity=null;
		        ArrayList issuePriority=null;
		        HashMap hashMap=null;
		         Connection connection=null;
		         ResultSet resultSet=null;
		   	  	 Statement statement=null;
		   	  	 PreparedStatement preparedStatement=null;
		   	     CallableStatement callableStatement=null;
		    try {
			      logger=Logger.getLogger("Admin");
			      
			      // Severities
			      issueSeverity=NewIssueDAO.IssueTypes();
			      
			      // priorities
			      issuePriority=NewIssueDAO.getIssuePriority();
			      
			      // Issue Status types
			      connection=DBConnection.createConnection();
			      preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Get_Issue_Status");
			      resultSet=preparedStatement.executeQuery();
			      //System.out.println("=======resultset============"+resultSet);
			      if(resultSet!=null) {
			    	  
			    	  statusTypes=new ArrayList();
			    	  while(resultSet.next()) {
			    		  
			    		  hashMap=new HashMap();
			    		  
			    		  hashMap.put("issueId",resultSet.getString(2));
			    		  hashMap.put("issueLabel",resultSet.getString(2));
			    		  
			    		  // add to list
			    		  statusTypes.add(hashMap);
			    	  }
			      }
			   
			      // Now build the set
			      hashMap=null;
			      hashMap=new HashMap();
			      
			      hashMap.put("statusTypes",statusTypes);
			      hashMap.put("issueSeverity",issueSeverity);
			      hashMap.put("issuePriority",issuePriority);
			      
		   } catch(Exception exception) {
			   
			   logger.info("====Exception in getIssueSettingsDAO======");
			   logger.error(exception);
			   exception.printStackTrace();
		   }
		       // return the list
		     return hashMap;
	  }
	  
	  public static ArrayList genereteIssueReport(HashMap map) {
		  
		         Connection connection=null;
			     ResultSet resultSet=null;
			     Statement statement=null;
			     PreparedStatement preparedStatement=null;
			     CallableStatement callableStatement=null;
		         ArrayList list=null;
		         String sqlQuery=null;
		         HashMap hashMap=null;
		         String detailLink=null;
		         IssueReportForm form=null;
		         String strUserId=null;
		         HttpSession session=null;
		         String strSearchCriteria=null;
		   try {
			      session=(HttpSession)map.get("session");
			      form=(IssueReportForm)map.get("form");
			      strUserId=(String)map.get("userId");
			      
			      logger=Logger.getLogger("Admin");
			      list=new ArrayList();
			      connection=DBConnection.createConnection();
			      int temp=0;
			      int iTemp=0;
			      if(form!=null) {
			    	  sqlQuery="EXEC vims_user.USP_Report_Issues ";
			    	  
			    	  if((form.getIssueId()!=null)&&(!((String)form.getIssueId()).equals(""))) {
			    		 sqlQuery=sqlQuery+"@Incident_ID='"+form.getIssueId()+"'";
			    	  	 sqlQuery=sqlQuery+",@Customer=null,@Emp_Name=null,@INCT_STATUS=null,@INCIDENT_PRIORITY=null,@Application_Name=null,@From_DT=null,@To_DT=null,@INCIDENT_TYPE=null,@Cust_User_NM=null,@Emp_User_NM=null,@User_NM='"+strUserId+"',@Purged_flag=null";
			    	     strSearchCriteria="Report based on: Issue Id--"+form.getIssueId();
			    	  }
			    	  else {
			    		    strSearchCriteria=new String();
			    		    strSearchCriteria="Report based on:";
			    		    
			    		    sqlQuery=sqlQuery+"@Incident_ID=null,@Customer=null,@Emp_Name=null,";
			    		    if((form.getIssueStatus()!=null)&&(!((String)form.getIssueStatus()).equals(""))) {
			    		   	  sqlQuery=sqlQuery+"@INCT_STATUS='"+form.getIssueStatus()+"',";
			    		   	  //System.out.println("========status========="+form.getIssueStatus());
			    		   	  if(iTemp==0) 
			    		   	    strSearchCriteria=strSearchCriteria+"Status--"+form.getIssueStatus();
			    		   	  else
			    		   		strSearchCriteria=strSearchCriteria+", Status--"+form.getIssueStatus();
			    		   	  iTemp=1;
			    		    }  
			    		    else
			    		    	sqlQuery=sqlQuery+"@INCT_STATUS=null,";	
			    		    if((form.getIssuePriority()!=null)&&(!((String)form.getIssuePriority()).equals(""))) {
			    		    	sqlQuery=sqlQuery+"@INCIDENT_PRIORITY='"+form.getIssuePriority()+"',";
			    		    	//System.out.println("========Priority========="+form.getIssuePriority());
			    		    	if(iTemp==0)
			    		    	  strSearchCriteria=strSearchCriteria+"Priority--"+getFullValue(form.getIssuePriority());
			    		    	else
			    		    	  strSearchCriteria=strSearchCriteria+", Priority--"+getFullValue(form.getIssuePriority());
			    		    	iTemp=1;
			    		    }
			    		    else
			    		    	sqlQuery=sqlQuery+"@INCIDENT_PRIORITY=null,";
			    		    
			    		    if((form.getIssueSeverity()!=null)&&(!((String)form.getIssueSeverity()).equals(""))) {
			    		    	sqlQuery=sqlQuery+"@INCIDENT_TYPE='"+form.getIssueSeverity()+"',";
			    		    	//System.out.println("========severity========="+form.getIssueSeverity());
			    		    	if(iTemp==0)
			    		    	  strSearchCriteria=strSearchCriteria+"Severity--"+getFullValue(form.getIssueSeverity());
			    		    	else
			    		    		strSearchCriteria=strSearchCriteria+", Severity--"+getFullValue(form.getIssueSeverity());
			    		    	iTemp=1;
			    		    }
			    		    else
			    		    	sqlQuery=sqlQuery+"@INCIDENT_TYPE=null,";
			    		    
			    		    if((form.getFromDate()!=null)&&(!((String)form.getFromDate()).equals(""))) { 
			    		    	sqlQuery=sqlQuery+"@From_DT='"+form.getFromDate()+"',";
			    		    	if(iTemp==0) 
			    		    	 strSearchCriteria=strSearchCriteria+"From--"+form.getFromDate();
			    		    	else
			    		    	 strSearchCriteria=strSearchCriteria+", From--"+form.getFromDate();
			    		    	iTemp=1;
			    		    }	
      		    		    else
			    		    	sqlQuery=sqlQuery+"@From_DT=null,";
			    		    
			    		    if((form.getToDate()!=null)&&(!((String)form.getToDate()).equals(""))) {
			    		    	sqlQuery=sqlQuery+"@To_DT='"+form.getToDate()+"',";
			    		    	if(iTemp==0)
			    		    		strSearchCriteria=strSearchCriteria+"To--"+form.getToDate();
			    		    	else
			    		    		strSearchCriteria=strSearchCriteria+", To--"+form.getToDate();
			    		    	iTemp=1;
			    		    }	
			    		    else
			    		    	sqlQuery=sqlQuery+"@To_DT=null,";
			    		    
			    		    sqlQuery=sqlQuery+"@Application_Name=null,@Cust_User_NM=null,@Emp_User_NM=null,@User_NM='"+strUserId+"',@Purged_flag=null";
			    	  }
			    	  
			      }
			       if(strSearchCriteria.equals("Report based on:"))
			    	   strSearchCriteria="Report based on: Status--All, Priority--All, Severity--All";
			       else {
			    	 //  System.out.println("===criteria======="+strSearchCriteria);
			    	   strSearchCriteria.replace(" ",",");
			    	   strSearchCriteria.replace(":,",": ");
			       }
			    	   
			      System.out.println("====sql query========"+sqlQuery);
			      preparedStatement=connection.prepareStatement(sqlQuery);
			      
			      resultSet=preparedStatement.executeQuery();
			      
			      if(resultSet!=null) {
			    	session.setAttribute("searchCriteria",strSearchCriteria);
			    	  while(resultSet.next()) {
			     		  
			               hashMap=new HashMap();
			               temp=1;
			               detailLink="<a href=\"./getIssueDetailView.do?param=getIssueDetailView&issueId="+resultSet.getString("Incident_ID")+"\">Detail View</a>";
			               hashMap.put("Issue_Id",resultSet.getString("Incident_ID"));
			               hashMap.put("Title",resultSet.getString("Incident_title"));
			               hashMap.put("Application_Name",resultSet.getString("Application_Name"));
			               hashMap.put("Issue_Type",resultSet.getString("incident_type_desc"));
			               hashMap.put("Status",resultSet.getString("inct_status"));
			              // hashMap.put("Assigned_To",resultSet.getString("Assigned_To"));
			               if(resultSet.getString("Due_Date")!=null)
			            	   hashMap.put("Due_Date",resultSet.getString("Due_Date").substring(0,10));
			               else
			            	   hashMap.put("Due_Date",resultSet.getString("Due_Date"));
			               
			               if(resultSet.getString("Closed_Date")!=null) 
			            	   hashMap.put("Closed_Date",resultSet.getString("Closed_Date").substring(0,10));
			               else
			            	   hashMap.put("Closed_Date",resultSet.getString("Closed_Date"));
			               
			               if(resultSet.getString("Issue_Completion_Date")!=null)
			            	   hashMap.put("Issue_Completion_Date",resultSet.getString("Issue_Completion_Date").substring(0,10));
			               else
			            	   hashMap.put("Issue_Completion_Date",resultSet.getString("Issue_Completion_Date"));
			               
			               hashMap.put("Description",resultSet.getString("Incident_Text"));
			               hashMap.put("Priority",resultSet.getString("INCIDENT_PRIORITY_DESC"));
			               hashMap.put("Posted_By",resultSet.getString("Posted_By"));
			               
			               if(resultSet.getString("Posted_Date")!=null)			               
			                   hashMap.put("Posted_Date",resultSet.getString("Posted_Date").substring(0,10));
			               else
			            	   hashMap.put("Posted_Date",resultSet.getString("Posted_Date"));
			              
			               hashMap.put("DetailView",detailLink); 
			               list.add(hashMap);
			    	  }
			      }
			       //System.out.println("=========temp value========"+temp);
			        if(temp==0)
			        session.setAttribute("noRecords","No Record Found");	
		   }
		    catch(Exception exception) {
		    	
		    	 logger.info("========Exception in generateIssueReportDAO============");
		    	 logger.error(exception);
		    	 exception.printStackTrace();
		    }
		    
		     return list;
	  }
	  
	  /**************************employees*************************************/
	  public static ArrayList getEmployeesDAO(String iPosition)
	  {
		      Connection connection=null;
			  ResultSet resultSet=null;
			  Statement statement=null;
			  PreparedStatement preparedStatement=null;
			  CallableStatement callableStatement=null;
		  	logger=Logger.getLogger("Admin");
		  	int positionNBR;
		  	ArrayList arrayList=new ArrayList();
			HashMap hashMap=null;
			try
			{
				connection=DBConnection.createConnection();			
				String strQuery="{CALL vims_user.USP_Get_Employee_Dtls(?,NULL,NULL,NULL)}";
				callableStatement=connection.prepareCall(strQuery);
				//System.out.println("=====strQuery========"+strQuery);
				if(iPosition.equalsIgnoreCase("All"))
				{
					//callableStatement.setString(1,null);
					callableStatement.setString(1,"");
				}
				else
				{
					positionNBR=Integer.parseInt(iPosition);
					callableStatement.setInt(1, positionNBR); 
					positionNBR++;
				}
				
				//System.out.println("=========iRole===="+iPosition); 
				ResultSet resultSet1=callableStatement.executeQuery();
				
					
					while(resultSet1.next())
					{			
						hashMap =new HashMap();
						hashMap.put("empId",resultSet1.getString(7));
						hashMap.put("empName", resultSet1.getString(2));
						arrayList.add(hashMap);					
					}					
					//System.out.println("======empList in DAO===="+arrayList);
					return arrayList;
			}
			catch(SQLException sqlException)
			{
				logger.info("ReportsDAO.getEmployeesDAO()");
				logger.error(sqlException);	
				return null;
			}	
			catch(Exception exception)
			{
				logger.info("ReportsDAO.getEmployeesDAO()");
				logger.error(exception);	
				return null;
			}
			/*finally
			{
				DBConnection.closeConnection();
			}*/
	  }
	  public static ArrayList generateEmployeeReportsDAO(ApplicationReportsForm form,String strUserId)
	  {
		  logger=Logger.getLogger("Admin");
		  String strRoleName=null;
		    Connection connection=null;
			ResultSet resultSet=null;
			Statement statement=null;
			PreparedStatement preparedStatement=null;
			CallableStatement callableStatement=null;
		  
		  
			try
			{
				connection=DBConnection.createConnection();	
				String strEmpId=form.getEmpId();
				String strRoleType=form.getRole();
				String strFromDate=form.getFromDate();
				String strToDate=form.getToDate();
				String strRoleId=form.getRole();
				String searchCriteria="Report based on: Assigned resources under Groups ";
				if("".equals(strEmpId))
				{
					strEmpId=null;
				}
				if("".equals(strFromDate) || "".equals(strToDate))
				{
					strFromDate=null;
					strToDate=null;
				}
				
				String strQuery="{CALL vims_user.USP_Report_Emp_Issue_Cnt(?,?,?,?,?,?)}";
				//System.out.println("=====strQuery========"+strQuery);
				//callableStatement=connection.prepareCall(strQuery); 
				callableStatement=connection.prepareCall(strQuery);
				if(strEmpId.equalsIgnoreCase("All"))
				{
					callableStatement.setString(1, null);
				}
				else
				{
					callableStatement.setString(1, strEmpId);
				}
				
				callableStatement.setString(2, strFromDate);
				callableStatement.setString(3, strToDate);
				callableStatement.setString(4, "Employee"); 
				callableStatement.setString(5, strUserId);
				
				if((strRoleId!=null)&&(strRoleId.equalsIgnoreCase(""))) {
					callableStatement.setString(6,null);
				}
				else if(strRoleId!=null){
					callableStatement.setString(6,strRoleId);
				}
				
				resultSet=callableStatement.executeQuery();
				
					ArrayList arrayList=new ArrayList();
					HashMap<String,String> hashMap=null;
					while(resultSet.next())
					{	
						strRoleName=resultSet.getString("Designation_NM");
						hashMap =new HashMap<String, String>();
						hashMap.put("Assigned",resultSet.getString(1));
						hashMap.put("Rejected", resultSet.getString(2));
						hashMap.put("Completed", resultSet.getString(3));
						hashMap.put("Escalated", resultSet.getString(4));
						hashMap.put("Closed", resultSet.getString(5));
						hashMap.put("Application_Name", resultSet.getString(6));
						hashMap.put("Employee_Name", resultSet.getString(7));
						hashMap.put("Group_Supervisor", resultSet.getString(8));
						arrayList.add(hashMap);					
					}
					
					if(arrayList.size()>0) {
						
						
						if(form.getRole()!=null) {
							
							searchCriteria=searchCriteria+", Role--"+strRoleName+",";
						}
						if(strEmpId!=null) {
							
							 hashMap=(HashMap)arrayList.get(0);
							 if(strEmpId.equalsIgnoreCase("All"))
							 {
								 searchCriteria=searchCriteria+", Employee--All";
							 }
							 else
							 {
								 searchCriteria=searchCriteria+", Employee --"+(String)hashMap.get("Employee_Name");
							 }

						}
						if((strFromDate!=null)&&!("".equals(strFromDate))) {
							searchCriteria=searchCriteria+", From  Date--"+strFromDate;
						}
						if((strToDate!=null)&&!("".equals(strToDate))) {
							searchCriteria=searchCriteria+", To Date--"+strToDate;
						}
						
						arrayList.add(searchCriteria);
					}
					//System.out.println("======arrayList in DAO===="+arrayList);
					return arrayList;
			}
			catch(SQLException sqlException)
			{
				logger.info("ReportsDAO.generateEmployeeReportsDAO()");
				logger.error(sqlException);	
				return null;
			}	
			catch(Exception exception)
			{
				logger.info("ReportsDAO.generateEmployeeReportsDAO()");
				logger.error(exception);	
				return null;
			}
			/*finally
			{
				DBConnection.closeConnection();
			}*/
	  }
	  public static ArrayList getCustomersDAO(String strStatus)
	  {
		      Connection connection=null;
			  ResultSet resultSet=null;
			  Statement statement=null;
			  PreparedStatement preparedStatement=null;
			  CallableStatement callableStatement=null;
		   if("All".equalsIgnoreCase(strStatus) || "".equalsIgnoreCase(strStatus)) 
		  {
			  strStatus=null;
		  }
			logger=Logger.getLogger("Admin");
			try
			{
				connection=DBConnection.createConnection();			
				String strQuery="{CALL vims_user.USP_Get_Customer(NULL,NULL,?)}";
				//System.out.println("=====strQuery========"+strQuery);
				callableStatement=connection.prepareCall(strQuery);
				callableStatement.setString(1, strStatus); 
				
				 resultSet=callableStatement.executeQuery();
				
					ArrayList arrayList=new ArrayList();
					HashMap hashMap=null;
					while(resultSet.next())
					{			
						hashMap =new HashMap();
						hashMap.put("custId",resultSet.getString("CUSTOMER_ID"));
						hashMap.put("custName", resultSet.getString("CUSTOMER_NAME"));
						arrayList.add(hashMap);					
					}					
					//System.out.println("======custList in DAO===="+arrayList);
					return arrayList;
			}
			catch(SQLException sqlException)
			{
				logger.info("ReportsDAO.getCustomersDAO()");
				logger.error(sqlException);	
				return null;
			}	
			catch(Exception exception)
			{
				logger.info("ReportsDAO.getCustomersDAO()");
				logger.error(exception);	
				return null;
			}
			/*finally
			{
				DBConnection.closeConnection();
			}*/
	  }
	 
	  public static ArrayList generateCustomerReportsDAO(ApplicationReportsForm form,String strLoginId)
	  {
		  logger=Logger.getLogger("Admin");
	      Connection connection=null;
		  ResultSet resultSet=null;
		  Statement statement=null;
		  PreparedStatement preparedStatement=null;
		  CallableStatement callableStatement=null;
		  
		 System.out.println("=========from dao today============="+strLoginId);
		   
			try
			{
				connection=DBConnection.createConnection();
				String strStatus=form.getStatus();
				String strCustId=form.getCustName();
				String strFromDate=form.getFromDate();
				String strToDate=form.getToDate();
				String searchCriteria="Report based on: ";
				if("".equals(strStatus))
				{
					strStatus=null;
				}
				else if("All".equalsIgnoreCase(strStatus))
				{
					strStatus=null;
				}					
				if("".equals(strCustId))
				{
					strCustId=null;
				}
				if("".equals(strFromDate) || "".equals(strToDate))
				{
					strFromDate=null;
					strToDate=null;
				}
				//System.out.println("====param values===="+strStatus+" "+" "+strCustId+" "+strFromDate+" "+strToDate);
				String strQuery="{CALL vims_user.USP_Report_Application(NULL,?,NULL,NULL,NULL,NULL,?,?,?,?)}";
				//System.out.println("=====strQuery========"+strQuery);
				//preparedStatement=connection.prepareStatement(strQuery); 
				callableStatement=connection.prepareCall(strQuery);
				callableStatement.setString(1, strStatus);
				callableStatement.setString(2, strCustId);
				callableStatement.setString(3, strFromDate);
				callableStatement.setString(4, strToDate);
				callableStatement.setString(5,strLoginId);
				System.out.println("======1======================="+strLoginId);
				resultSet=callableStatement.executeQuery();
				
					ArrayList arrayList=new ArrayList();
					HashMap<String,String> hashMap=null;
					int custRename=0;
					String strCustName=null;
					while(resultSet.next())
					{	
						custRename=0;
						hashMap =new HashMap<String, String>();
						/*if(strCustName==null)
						{
							strCustName=resultSet.getString("Customer_name");
							hashMap.put("Customer_Name",resultSet.getString("Customer_name"));
							custRename=1;
						}
						if(strCustName!=null)
						{
							if(custRename!=1)
							{
								
							}
						}*/
						hashMap.put("Customer_Name",resultSet.getString("Customer_name"));
						hashMap.put("Application_Name", resultSet.getString("APPLICATION_NAME"));
						hashMap.put("Status", resultSet.getString("APP_STATUS"));
						hashMap.put("Application_Owner", resultSet.getString("APP_OWNER"));
						hashMap.put("No_of_Issues", resultSet.getString("Num_Issues"));
						hashMap.put("Start_Date", resultSet.getString("Support_Begin_Date"));
						hashMap.put("End_Date", resultSet.getString("Support_End_Date"));
						hashMap.put("Group_Supervisor", resultSet.getString("Group_Manager_Name"));
						hashMap.put("Support_Manager", resultSet.getString("Support_Center_Manager_Name"));
						hashMap.put("Group", resultSet.getString("Group_Name"));
						hashMap.put("Members", resultSet.getString("Employees"));
						
						/*if(strCustName!=null)
						{
							if(!custRename)
							{
								if(strCustName.equalsIgnoreCase(resultSet.getString("Customer_name")))
								{
									
								}
							}
							
						}*/
						
						arrayList.add(hashMap);					
					}	
					
					if(arrayList.size()>0) {
						
					 	 int flag=0;
						 if((strStatus!=null)&&(!strStatus.equals(""))) {
							 if(flag==0)
							  searchCriteria=searchCriteria+"Status--"+strStatus;
							 else
							  searchCriteria=searchCriteria+", Status--"+strStatus;
							 flag=1;
						 }
						 if((strCustId!=null)&&(!strCustId.equals(""))) {
							 if(flag==0)
								 searchCriteria=searchCriteria+"Customer Name--"+(String)((HashMap)arrayList.get(0)).get("Customer_Name");
							 else
								 searchCriteria=searchCriteria+", Customer Name--"+(String)((HashMap)arrayList.get(0)).get("Customer_Name");
							 flag=1;
						 }
						 if((strFromDate!=null)&&(!strFromDate.equals(""))) {
							  if(flag==0) 
								searchCriteria=searchCriteria+"From--"+strFromDate;  
							  else
								searchCriteria=searchCriteria+", From--"+strFromDate;
							  flag=1;
						 }
						 if((strToDate!=null)&&(!strToDate.equals(""))) {
							  if(flag==0) 
								searchCriteria=searchCriteria+"To--"+strToDate;  
							  else
								searchCriteria=searchCriteria+", To--"+strToDate;
							  flag=1;
						 }
						 if(flag==0) 
							 searchCriteria="Report based on :Status--All, Customers--All";
						 
						 arrayList.add(searchCriteria);
					}
					//System.out.println("======customer reports in DAO===="+arrayList);
					return arrayList;
			}
			catch(SQLException sqlException)
			{
				logger.info("ReportsDAO.generateCustomerReportsDAO()");
				logger.error(sqlException);	
				return null;
			}	
			catch(Exception exception)
			{
				logger.info("ReportsDAO.generateCustomerReportsDAO()");
				logger.error(exception);	
				return null;
			}
			/*finally
			{
				DBConnection.closeConnection();
			}*/
	  }
	  
	  public static ArrayList getOwnersDAO(String strCustomerId)
	  {
		  logger=Logger.getLogger("Admin");
		  Connection connection=null;
		  ResultSet resultSet=null;
		  Statement statement=null;
		  PreparedStatement preparedStatement=null;
		  CallableStatement callableStatement=null;
			try
			{
				connection=DBConnection.createConnection();			
				String strQuery="{CALL vims_user.USP_Get_App_Owner(?)}";
				//System.out.println("=====strQuery========"+strQuery);
				callableStatement=connection.prepareCall(strQuery);
				callableStatement.setString(1, strCustomerId); 
				
				resultSet=callableStatement.executeQuery();
				
					ArrayList arrayList=new ArrayList();
					HashMap hashMap=null;
					while(resultSet.next())
					{			
						hashMap =new HashMap();
						hashMap.put("ownerId",resultSet.getString("App_Owner_Email"));
						hashMap.put("ownerName", resultSet.getString("APP_OWNER"));
						arrayList.add(hashMap);					
					}					
					//System.out.println("======ownerList in DAO===="+arrayList);
					return arrayList;
			}
			catch(SQLException sqlException)
			{
				logger.info("ReportsDAO.getOwnersDAO()");
				logger.error(sqlException);	
				return null;
			}	
			catch(Exception exception)
			{
				logger.info("ReportsDAO.getOwnersDAO()");
				logger.error(exception);	
				return null;
			}
			/*finally
			{
				DBConnection.closeConnection();
			}*/
	  }
	  public static ArrayList getApplicationsBD(String strStatus)
	  {
		  logger=Logger.getLogger("Admin");
		   Connection connection=null;
		   ResultSet resultSet=null;
		   Statement statement=null;
		   PreparedStatement preparedStatement=null;
		   CallableStatement callableStatement=null;
			try
			{
				connection=DBConnection.createConnection();			
				String strQuery="{call vims_user.USP_Get_Application_Dtls('"+strStatus+"',NULL,NULL,NULL)}";
				//System.out.println("=====strQuery========"+strQuery);
				callableStatement=connection.prepareCall(strQuery);
				//callableStatement.setString(1, strStatus); 
				
			 resultSet=callableStatement.executeQuery();
				
					ArrayList arrayList=new ArrayList();
					HashMap hashMap=null;
					while(resultSet.next())
					{			
						hashMap =new HashMap();
						hashMap.put("custId",resultSet.getString(1));
						hashMap.put("custName", resultSet.getString(2));
						arrayList.add(hashMap);					
					}					
					//System.out.println("======custList in DAO===="+arrayList);
					return arrayList;
			}
			catch(SQLException sqlException)
			{
				logger.info("ReportsDAO.getCustomersDAO()");
				logger.error(sqlException);	
				return null;
			}	
			catch(Exception exception)
			{
				logger.info("ReportsDAO.getCustomersDAO()");
				logger.error(exception);	
				return null;
			}
			/*finally
			{
				DBConnection.closeConnection();
			}*/
	  }
	  
	  /**************************end of Employees*****************************/  
	public static HashMap getIssueDetailViewDAO(String strIssueId,HttpSession session) {
        	   
		         HashMap hashMap=null;
		         HashMap temp=null;
		         ArrayList list=null;
		          Connection connection=null;
		    	  ResultSet resultSet=null;
		    	  Statement statement=null;
		    	  PreparedStatement preparedStatement=null;
		    	  CallableStatement callableStatement=null;
		  try {
			    logger=Logger.getLogger("Admin");
			    hashMap=new HashMap();
			    if(session!=null) {
			    	
			    	list=(ArrayList)session.getAttribute("IssueReportList");
			             	
			        if(list.size()>0) {
			        	
			        	 for(int cnt=0;cnt<list.size();cnt++) {
			        		  temp=(HashMap)list.get(cnt);
			        		  if(((String)temp.get("Issue_Id")).equals(strIssueId)) {
			        			  
			        			   hashMap.put("Issue_Id",(String)temp.get("Issue_Id"));
			        			   hashMap.put("Title",(String)temp.get("Title"));
			        			   hashMap.put("Application_Name",(String)temp.get("Application_Name"));
			        			   hashMap.put("Issue_Type",(String)temp.get("Issue_Type"));
			        			   hashMap.put("Status",(String)temp.get("Status"));
			        			   hashMap.put("Due_Date",(String)temp.get("Due_Date"));
			        			   hashMap.put("Closed_Date",(String)temp.get("Closed_Date"));
			        			   hashMap.put("Issue_Completion_Date",(String)temp.get("Issue_Completion_Date"));
			        			   hashMap.put("Description",(String)temp.get("Description"));
			        			   hashMap.put("Priority",(String)temp.get("Priority"));
			        			   hashMap.put("Posted_By",(String)temp.get("Posted_By"));
			        			   hashMap.put("Posted_Date",(String)temp.get("Posted_Date"));
			        			   break;
			        		  }
			        	 }
			        }
			        else {
			        	
			        	 return null;
			        }
			     }
			    connection=DBConnection.createConnection();
			    preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Report_Issue_Dtld_View @Incident_ID='"+strIssueId+"'");
			    //System.out.println("EXEC USP_Report_Issue_Dtld_View @Incident_ID="+strIssueId);
			    resultSet=preparedStatement.executeQuery();
			    ArrayList tempList=null;
			    temp=null;
			    if(resultSet!=null) {
			          tempList=new ArrayList();
			    	while(resultSet.next()) {
			    		temp=new HashMap();
			             
			             temp.put("Assigned_By",resultSet.getString("Assigned_By"));
			             temp.put("Assigned_To",resultSet.getString("Assigned_To"));
			             temp.put("status_From",resultSet.getString("Inct_status_From"));
			             temp.put("Status_To",resultSet.getString("Inct_Status_To"));
			             temp.put("Status_Change_on_Date",resultSet.getString("INCT_STATUS_DATE"));
			             temp.put("Comments",resultSet.getString("Comments"));
	          
			             tempList.add(temp);
			         }
			    }
			     hashMap.put("issueLife",tempList);
		  }
		   catch(Exception exception) {
			    
			    logger.info("========Exception in getIssueDetailViewDAO============");
			    logger.error(exception);
			    exception.printStackTrace();
		   }
		   
		     return hashMap;
	}
	public static ArrayList getApplicationNamesDAO(String strStatus,String strLoginId) {
		ArrayList ApplicationNames=new ArrayList();
		HashMap hashMap=null;
		 Connection connection=null;
		  ResultSet resultSet=null;
		  Statement statement=null;
		  PreparedStatement preparedStatement=null;
		  CallableStatement callableStatement=null;
		//System.out.println("Status of Application is : "+strStatus); 
		  System.out.println("======2======================="+strLoginId);
		String strQuery="EXEC vims_user.USP_Report_Application @user_nm1='"+strLoginId+"',@Cust_Status=null,@User_NM=null,@App_Owner_Email=null,@Application_Name=null,@Customer_ID=null,@APPLICATION_ID=null,@From_DT=null,@To_DT=null";
		try 
		{
			connection = DBConnection.createConnection();			
			if(!(strStatus.equalsIgnoreCase("All")))
			{
				strQuery=strQuery+",@APP_STATUS='"+strStatus+"'";
				preparedStatement = connection.prepareStatement(strQuery);
		 	}
			else
			{
				strQuery=strQuery+",@APP_STATUS=null";				
				preparedStatement = connection.prepareStatement(strQuery);
			}
			System.out.println("SQL Query in ApplicationnamesDAo is "+strQuery);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
			{//while started
				hashMap = new HashMap();
				hashMap.put("id", resultSet.getString(1));
			    hashMap.put("name", resultSet.getString(2));
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
	}

	public static ArrayList displayApplicationReportsDAO(String strStatus,	String strApplicationSelected, String strFromDate, String strToDate, HttpSession session, String strViewBy,String strLoginId) 
	{
		ArrayList ApplicationReports=new ArrayList();
		 Connection connection=null;
		  ResultSet resultSet=null;
		  Statement statement=null;
		  PreparedStatement preparedStatement=null;
		  CallableStatement callableStatement=null;
		HashMap hashMap=null;		
		String strSearchCriteria="Report based on: ";
		System.out.println("======3======================="+strLoginId);
		String strQuery="EXEC vims_user.USP_Report_Application @user_nm1='"+strLoginId+"',@Cust_Status=null,@User_NM=null,@App_Owner_Email=null,@Application_Name=null,@Customer_ID=null";
		int flag=0;
		try 
		{
			connection = DBConnection.createConnection();
			if(strApplicationSelected.equalsIgnoreCase("ALL") || strApplicationSelected.equalsIgnoreCase("selAppl"))
			{
				if(strApplicationSelected.equalsIgnoreCase("ALL"))
				{
					strSearchCriteria=strSearchCriteria+"Application : All,";
				}
				strQuery=strQuery+",@APPLICATION_ID=null";
				if(strFromDate.equalsIgnoreCase(""))
				{
					strQuery=strQuery+",@From_DT=null";					
				}
				else
				{
					strQuery=strQuery+",@From_DT='"+strFromDate+"'";	
					strSearchCriteria=strSearchCriteria+"From Date :"+strFromDate+",";
				}
				if(strToDate.equalsIgnoreCase(""))
				{
					strQuery=strQuery+",@To_DT=null";
				}
				else
				{
					strQuery=strQuery+",@To_DT='"+strToDate+"'";
					strSearchCriteria=strSearchCriteria+"To Date :"+strToDate+",";
				}
			}
			else
			{
				strQuery=strQuery+",@APPLICATION_ID='"+strApplicationSelected+"'";
				strQuery=strQuery+",@From_DT=null";
				strQuery=strQuery+",@To_DT=null";
				
			}
			if(strStatus.equalsIgnoreCase("ALL"))
			{
				strQuery=strQuery+",@APP_STATUS=null";
				strSearchCriteria=strSearchCriteria+"Status : All,";
			}
			else if(strStatus.equalsIgnoreCase("selStatus"))
			{
				strQuery=strQuery+",@APP_STATUS=null";
				//strSearchCriteria=strSearchCriteria+"Status :"+strStatus+",";
			}
			else
			{
				strQuery=strQuery+",@APP_STATUS='"+strStatus+"'";
				strSearchCriteria=strSearchCriteria+"Status :"+strStatus+",";
			}	
			System.out.println("Query String in DAO class is "+strQuery); 
			
			preparedStatement = connection.prepareStatement(strQuery);
			//System.out.println("%%%%%%%%%%%%"+preparedStatement);
			resultSet = preparedStatement.executeQuery();
			//System.out.println("$$$$$$$$$$$$$$$$$$$4"+resultSet);
			while (resultSet.next())
			{//while started
				hashMap = new HashMap();
				if(strViewBy.equalsIgnoreCase("summary"))
				{
					hashMap.put("Application_Name", resultSet.getString(2));
					hashMap.put("Status", resultSet.getString(3));
					hashMap.put("Application_Owner", resultSet.getString(4));
					hashMap.put("Start_Date", resultSet.getString(8));
					hashMap.put("End_Date", resultSet.getString(9));
					hashMap.put("Support_Manager", resultSet.getString(13));					
					hashMap.put("Customer_Name", resultSet.getString(27));					
				}
				else
				{
					hashMap.put("Application_Name", resultSet.getString(2));
					hashMap.put("Status", resultSet.getString(3));
					hashMap.put("Application_Owner", resultSet.getString(4));
					hashMap.put("Start_Date", resultSet.getString(8));
					hashMap.put("End_Date", resultSet.getString(9));
					hashMap.put("Support_Manager", resultSet.getString(13));
					hashMap.put("Critical_Response_Time_in_Hrs", resultSet.getString(15));
					hashMap.put("Critical_Warning_Before_in_Hrs", resultSet.getString(16));
					hashMap.put("Major_Response_Time_in_Hrs", resultSet.getString(17));
					hashMap.put("Major_Warning_Before_in_Hrs", resultSet.getString(18));
					hashMap.put("Minor_Response_Time_in_Hrs", resultSet.getString(19));
					hashMap.put("Minor_Warning_Before_in_Hrs", resultSet.getString(20));
					hashMap.put("Customer_Name", resultSet.getString(27));
					hashMap.put("Group_Members", resultSet.getString(30));
				}
				
				
		
				ApplicationReports.add(hashMap);
			}
			
			strSearchCriteria="Report based on: ";
			if((strStatus!=null)&&(!strStatus.equals(""))) {
				if(flag==0)
				 strSearchCriteria=strSearchCriteria+"Status--"+strStatus;
				else
				 strSearchCriteria=strSearchCriteria+", Status--"+strStatus;
			  flag=1;
		    }
		    if((strApplicationSelected!=null)&&(!strApplicationSelected.equals(""))) {
		    	String strTemp;
		    	if(!(strApplicationSelected.equalsIgnoreCase("All"))) 
		    		strTemp=(String)hashMap.get("Application_Name");
		    	else
		    		strTemp="All";
		    	if(flag==0)
		    		 strSearchCriteria=strSearchCriteria+"Application--"+strTemp;
		    	 else
		    		 strSearchCriteria=strSearchCriteria+", Application--"+strTemp;
		      	 flag=1;
		    }
		    if((strFromDate!=null)&&(!strFromDate.equals(""))) {
		    	if(flag==0)
		    		 strSearchCriteria=strSearchCriteria+"From Date --"+strFromDate;
		    	 else
		    		 strSearchCriteria=strSearchCriteria+", From Date --"+strFromDate;
		    	flag=1;
		    }
		    if((strToDate!=null)&&(!strToDate.equals(""))) {
		    	if(flag==0)
		    		 strSearchCriteria=strSearchCriteria+"To Date --"+strToDate;
		    	 else
		    		 strSearchCriteria=strSearchCriteria+", To Date--"+strToDate;
		    	flag=1;
		    }
		     if(flag==0)
		    	 strSearchCriteria="Report based on: Status--All, Application--All";
			
		     /*if(!(strApplicationSelected.equalsIgnoreCase("All")) && !(strApplicationSelected.equalsIgnoreCase("selAppl")))
			{
				strSearchCriteria=strSearchCriteria+"Application : "+hashMap.get("Application_Name"); 
			}*/
			
			
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
		if(strSearchCriteria.equalsIgnoreCase("Report based on: "))
		{
			session.setAttribute("searchCriteria", "Report based on : All");
		}
		else
		{
			session.setAttribute("searchCriteria", strSearchCriteria);
		}
		
		return ApplicationReports;	
	}
	
	public static byte[] generateIssueExcelFileDAO(ArrayList issueList,String strTargetPath,String strSearchCriteria,String reportType) {

		HashMap details=null;
		 String[] columnNames={"Issue_Id","Title","Application_Name","Issue_Type","Status","Description","Priority","Posted_By","Posted_Date","Due_Date","Closed_Date","Issue_completion_Date"};
         String[] columnSizes={"15","35","35","15","15","15","15","23","15","15","15","15","25"};
         
         String[] summaryIssuesColumnNames={"Issue_Id","Title","Application_Name","Issue_Type","Status","Posted_Date","Due_Date"};
         String [] summaryIssuesColumnSizes={"15","35","35","15","15","15","15"};
        	 
         String[] customerColumnNames={"Customer_Name","Application_Name","Status","Start_Date","End_Date","Application_Owner","No_of_Issues","Support_Manager","Group","Group_Supervisor","Members"};
         String[] customerColumnSizes={"25","35","15","15","15","25","15","25","25","25","50"};
         
         String[] customerSummaryColumnNames={"Customer_Name","Application_Name","Status","Start_Date","End_Date","Application_Owner","No_of_Issues"};
         String[] customerSummaryColumnSizes={"25","35","15","15","15","25","15"};
          
         
         String[] applicationColumnNames={"Application_Name","Customer_Name","Application_Owner","Support_Manager","Status","Start_Date","End_Date","Critical_Response_Time_in_Hrs","Critical_Warning_Before_in_Hrs","Major_Response_Time_in_Hrs","Major_Warning_Before_in_Hrs","Minor_Response_Time_in_Hrs","Minor_Warning_Before_in_Hrs","Group_Members"};
         String[] applicationColumnSizes={"35","25","25","25","15","15","15","30","30","30","30","30","30","50"};
         
         String[] applicationSummaryColumnNames={"Application_Name","Customer_Name","Application_Owner","Support_Manager","Status","Start_Date","End_Date"};
         String[] applicationSummaryColumnSizes={"35","25","25","25","15","15","15"};
         
         
         String[] slaColumnNames={"Application_Name","Total_Issues","Issues_within_SLA","Issues_out_of__SLA","Indicator"};
         String[] slaColumnSizes={"35","25","25","25","9"};
         
         String[] detailViewColumnNames={"Assigned_By","Assigned_To","Inct_status_From","Inct_Status_To","Comments"};
         String[] detailViewColumnSizes={"25","25","25","25","50"};
         
         String[] ProgrammerColumnNames={"Employee_Name","Application_Name","Group_Supervisor","Assigned","Rejected","Completed","Escalated","Closed"};
         String[] ProgrammerColumnSizes={"25","50","20","15","15","15","15","15"};
         	               
         String[] SupervisorColumnNames={"Group_Supervisor","Group_Name","Group_Members","Application_Name","No_of_Issues","Status","Begin_Date","End_Date"};
         String[] SupervisorColumnSizes={"25","25","75","35","15","25","15","15"};
         
         String[] ManagerColumnNames={"Support_Manager","Support_Center_Name","Group_Supervisors","Application_Names"};
         String[] ManagerColumnSizes={"25","25","75","75"};

         String[] AvailableingroupsColumnNames={"Group_Name","Employee_ID","Employee_Name","E_mail","Position"};
         String[] AvailableingroupsColumnSizes={"75","15","25","25","20"};
         
         String[] AvailableEmployeesColumnNames={"Employee_ID","Employee_Name","E_mail","Phone_Number","Position"};
         String[] AvailableEmployeesColumnSizes={"15","25","25","15","25"};

         String[] HomeIssuesColumnNames={"Incident_ID","Application_Name","Title","Severity","Status","Created_Date","Due_Date","Closed_Date"};
         String[] HomeIssuesColumnSizes={"15","35","35","15","15","15","15","15"};
         
         String[] SearchColumnNames={"Issue_ID","Application_Name","Severity","Status","Customer_Name"};
         String[] SearchColumnSizes={"15","35","15","15","25"};
         
		 String[] AllPositionsAssgEmpColumnNames={"Support_Manager","Group_Supervisor","Group_Members","No_of_Applications","No_of_Issues"};
		 String[] AllPositionsAssgEmpColumnSizes={"25","25","35","25","25"};
		 
		 byte[] targetData=null;
	               try {
			       details=new HashMap();
			       
			       details.put("path",strTargetPath);
			       details.put("dataset",issueList);
			       details.put("searchCriteria",strSearchCriteria);
			       if(reportType.equalsIgnoreCase("detailedCustomer")) {
			    	   
			    	   details.put("reportTitle","Customer Report");
				       details.put("columnSizes",customerColumnSizes);
				       details.put("ColumnNames",customerColumnNames);
			       }
			       
			       else if(reportType.equalsIgnoreCase("summaryCustomer")) {
			    	   
			    	   details.put("reportTitle","Customer Report");
				       details.put("columnSizes",customerSummaryColumnSizes);
				       details.put("ColumnNames",customerSummaryColumnNames);
			       }
			       else if(reportType.equalsIgnoreCase("detailedApplication"))
			       {			    	   
			    	   details.put("reportTitle","Application Report");
				       details.put("columnSizes",applicationColumnSizes);
				       details.put("ColumnNames",applicationColumnNames);
			       }
			       
			       else if(reportType.equalsIgnoreCase("summaryApplication"))
			       {			    	   
			    	   details.put("reportTitle","Application Report");
				       details.put("columnSizes",applicationSummaryColumnSizes);
				       details.put("ColumnNames",applicationSummaryColumnNames);
			       }
			       
			       else if(reportType.equalsIgnoreCase("summaryIssues")) {
			       details.put("reportTitle","Issues Report");
			       details.put("columnSizes",summaryIssuesColumnSizes);
			       details.put("ColumnNames",summaryIssuesColumnNames);
			       } 
			       else if(reportType.equalsIgnoreCase("detailedIssues")) {
				       details.put("reportTitle","Issues Report");
				       details.put("columnSizes",columnSizes);
				       details.put("ColumnNames",columnNames);
				    } 
			       
			       else if(reportType.equalsIgnoreCase("DetailView"))
			       {			    	   
			    	   details.put("reportTitle","Issue Detail View Report");
				       details.put("columnSizes",detailViewColumnSizes);
				       details.put("ColumnNames",detailViewColumnNames);
			       }
			       else if(reportType.equalsIgnoreCase("SLA"))
			       {			    	   
			    	   details.put("reportTitle","SLA Report");
				       details.put("columnSizes",slaColumnSizes);
				       details.put("ColumnNames",slaColumnNames);
			       }
			       
			       else if(reportType.equalsIgnoreCase("Programmer"))
		            {			    	   
			    	   details.put("reportTitle","Employee Report");
				       details.put("columnSizes",ProgrammerColumnSizes);
				       details.put("ColumnNames",ProgrammerColumnNames);
			       }
			       else if(reportType.equalsIgnoreCase("Supervisor"))
		            {			    	   
			    	   details.put("reportTitle","Employee Report");
				       details.put("columnSizes",SupervisorColumnSizes);
				       details.put("ColumnNames",SupervisorColumnNames);
			       }
			       else if(reportType.equalsIgnoreCase("Manager"))
		            {			    	   
			    	   details.put("reportTitle","Employee Report");
				       details.put("columnSizes",ManagerColumnSizes);
				       details.put("ColumnNames",ManagerColumnNames);
			       }
			       else if(reportType.equalsIgnoreCase("Availableingroups")) {			    	   
			    	   details.put("reportTitle","Employee Report");
				       details.put("columnSizes",AvailableingroupsColumnSizes);
				       details.put("ColumnNames",AvailableingroupsColumnNames);
			       }
			       else if(reportType.equalsIgnoreCase("AvailableEmployees")) {			    	   
			    	   details.put("reportTitle","Employee Report");
				       details.put("columnSizes",AvailableEmployeesColumnSizes);
				       details.put("ColumnNames",AvailableEmployeesColumnNames);
			       }
			       else if(reportType.equalsIgnoreCase("AllEmployees")) {			    	   
			    	   details.put("reportTitle","Employee Report");
				       details.put("columnSizes",AvailableEmployeesColumnSizes);
				       details.put("ColumnNames",AvailableEmployeesColumnNames);
			       }
			       else if(reportType.equalsIgnoreCase("HomeIssues")) {
			    	   HashMap hashMap=(HashMap)issueList.get(0);
			    	   //System.out.println("-----------hashMap.get('Application_Name')-----------------"+hashMap.get("Application_Name")); 
			    	   details.put("reportTitle","Issues in "+hashMap.get("Application_Name"));  
				       details.put("columnSizes",HomeIssuesColumnSizes);
				       details.put("ColumnNames",HomeIssuesColumnNames);
				      // System.out.println("-------generateIssueExcelFileDAO------------");
			       }
			       
			       else if(reportType.equalsIgnoreCase("SearchPage")) {			    	   
			    	   details.put("reportTitle","Search Report");
				       details.put("columnSizes",SearchColumnSizes);
				       details.put("ColumnNames",SearchColumnNames);
				      // System.out.println("-------generateIssueExcelFileDAO------------");
			       }
			       
			       else if(reportType.equalsIgnoreCase("AllPositions")) {			    	   
			    	   details.put("reportTitle","Employee Report");
				       details.put("columnSizes",AllPositionsAssgEmpColumnSizes);
				       details.put("ColumnNames",AllPositionsAssgEmpColumnNames);
				       //System.out.println("-------generateIssueExcelFileDAO------------");
			       }
			       
			       System.out.println("details in DAO class is "+details);
			       targetData=ExcelGenerator.generateExcelFile(details);
		   }
		    catch(Exception exception) {
		    	
		    	  logger.info("----Exception in generateIssueExcelFileDAO-----");
		    	  logger.error(exception);
		    	  exception.printStackTrace();
		    }
		     return targetData;
	}
	
	// Added by santhosh.k  on 02/12/2009 for excel sheet generation of Detail Issue View
	public static byte[] generateDetailIssueExcelFileDAO(HashMap issueList,String strTargetPath,String strSearchCriteria,String reportType)
	{
		HashMap details=null;
		String[] detailViewColumnNames={"Assigned_By","Assigned_To","status_From","Status_To","Status_Change_on_Date","Comments"};
        String[] detailViewColumnSizes={"20","20","20","20","25","50"};
        
        byte[] targetData=null;
        try {
	       details=new HashMap();
	       
	       details.put("path",strTargetPath);
	       details.put("issueDetails",issueList);
	       details.put("searchCriteria",strSearchCriteria);
	       if(reportType.equalsIgnoreCase("DetailView"))
	       {			    	   
	    	   details.put("reportTitle","Issue Detail View Report");
		       details.put("columnSizes",detailViewColumnSizes);
		       details.put("ColumnNames",detailViewColumnNames);
	       }
	       
           
	       System.out.println("details in DAO class is "+details);
	       targetData=ExcelGenerator.generateIssueDetailExcelFile(details);
        }
        catch (Exception exception)
        {
    	  logger.info("----Exception in generateIssueExcelFileDAO-----");
    	  logger.error(exception);
    	  exception.printStackTrace();
        }
		return targetData;
	}
	public static String getFullValue(String strKey) {
	    	   
		         if(strKey.equalsIgnoreCase("cri")) 
		        	 return "Critical";
		         if(strKey.equalsIgnoreCase("min"))
		        	 return "Minor";
		         if(strKey.equalsIgnoreCase("maj"))
		        	 return "Major";
		         if(strKey.equalsIgnoreCase("p1"))
		        	 return "Priority1";
		         if(strKey.equalsIgnoreCase("p2"))
		        	 return "Priority2";
		         if(strKey.equalsIgnoreCase("p3"))
		        	 return "Priority3";
		         else
		        	 return strKey;
	   }

	
	public static ArrayList getSLAReportsDAO(String strstatus,String strSLA, String strFromDate,	String strToDate, HttpSession session)
	{
		ArrayList SLAReports=new ArrayList();
		HashMap hashMap=null;	
		
		 Connection connection=null;
		  ResultSet resultSet=null;
		  Statement statement=null;
		  PreparedStatement preparedStatement=null;
		  CallableStatement callableStatement=null;
		  String strLoginId=null;

		int totalIssues=0;
		int isuesWithin=0;
		int issuesOutof=0;
		
		//System.out.println("SLA Type in DAO class is "+strSLA); 
		float percentIssuesOutof=0;
		String strGreen="<img src='./images/Green.gif'></img>";
		String strAmber="<img src='./images/Yellow.gif'></img>";
		String strRed="<img src='./images/Red.gif'></img>";
		
		String strSearchCriteria="Report based on: ";
        strLoginId=(String)session.getAttribute("user");
        System.out.println("======4======================="+strLoginId);
		String strQuery="EXEC vims_user.USP_Report_Application @user_nm1='"+strLoginId+"',@Cust_Status=null,@APPLICATION_ID=null,@User_NM=null,@App_Owner_Email=null,@Application_Name=null,@Customer_ID=null";
		//String
		try 
		{
			connection = DBConnection.createConnection();
			
			
			if(strstatus.equalsIgnoreCase("ALL"))
			{
				strQuery=strQuery+",@APP_STATUS=null";
				strSearchCriteria=strSearchCriteria+"Status--All, ";
			}
			else if(strstatus.equalsIgnoreCase("selStatus"))
			{
				strQuery=strQuery+",@APP_STATUS=null";
				//strSearchCriteria=strSearchCriteria+"Status : All,";
			}
			else
			{
				strQuery=strQuery+",@APP_STATUS='"+strstatus+"'";
				strSearchCriteria=strSearchCriteria+"Status--"+strstatus+", ";				
			}
			
			
			if(strSLA.equalsIgnoreCase("ALL"))
			{
				strSearchCriteria=strSearchCriteria+"SLA Type--All";
			}
			else if(strSLA.equalsIgnoreCase("selSLA"))
			{				
				//strSearchCriteria=strSearchCriteria+"SLA Type : All";
			}
			else if(strSLA.equalsIgnoreCase("outofSLA"))
			{
				strSearchCriteria=strSearchCriteria+"SLA Type--Applications out of SLA";
			}
			else if(strSLA.equalsIgnoreCase("withinSLA"))
			{				
				strSearchCriteria=strSearchCriteria+"SLA Type--Applications within SLA";
			}
			if(strFromDate.equalsIgnoreCase(""))
			{
				strQuery=strQuery+",@From_DT=null";		
				//strSearchCriteria=strSearchCriteria+",From Date : ";
			}
			else
			{
				strQuery=strQuery+",@From_DT='"+strFromDate+"'";	
				strSearchCriteria=strSearchCriteria+", From Date--"+strFromDate;
			}
			if(strToDate.equalsIgnoreCase(""))
			{
				strQuery=strQuery+", @To_DT=null";
				//strSearchCriteria=strSearchCriteria+",To Date :";
			}
			else
			{
				strQuery=strQuery+",@To_DT='"+strToDate+"'";	
				strSearchCriteria=strSearchCriteria+",To Date--"+strToDate;
			}
					
			System.out.println("Query String in DAO class is "+strQuery); 
			
			preparedStatement = connection.prepareStatement(strQuery);
			//System.out.println("%%%%%%%%%%%%"+preparedStatement);
			resultSet = preparedStatement.executeQuery();
			//System.out.println("$$$$$$$$$$$$$$$$$$$4"+resultSet);
			while (resultSet.next())
			{//while started
				hashMap = new HashMap();
				if((strSLA.equalsIgnoreCase("ALL")) || (strSLA.equalsIgnoreCase("selSLA")))
				{					
					hashMap.put("Application_Name", resultSet.getString(2));				
					totalIssues=resultSet.getInt(31);				
					isuesWithin=resultSet.getInt(33);
					issuesOutof=resultSet.getInt(32);
								
					hashMap.put("Total_Issues", totalIssues);
					hashMap.put("Issues_within_SLA", isuesWithin);
					hashMap.put("Issues_out_of__SLA", issuesOutof);
					
					//System.out.println("----totalIssues-----"+totalIssues); 
					
					if(totalIssues>0)
					{
						//System.out.println("--inside if---issuesOutof----"+issuesOutof); 
						//System.out.println("--inside if---totalIssues----"+totalIssues); 
						percentIssuesOutof=new Float(Math.abs((issuesOutof*100)/totalIssues));
						hashMap.put("Percent_of_Issues_within_SLA", new Float(Math.abs((isuesWithin*100)/totalIssues)));
						hashMap.put("Percent_of_Issues_out_of__SLA", new Float(Math.abs((issuesOutof*100)/totalIssues)));
					}
					//System.out.println("-------percentIssuesOutof--------"+percentIssuesOutof); 
					if(percentIssuesOutof<=10)
					{
						hashMap.put("Percent_of_Issues", strGreen);	
						hashMap.put("Indicator", "green");	
						
					}
					else if(percentIssuesOutof>10 && percentIssuesOutof<=30)
					{
						hashMap.put("Percent_of_Issues", strAmber);	
						hashMap.put("Indicator", "yellow");	
					}
					else if(percentIssuesOutof>30)
					{
						hashMap.put("Percent_of_Issues", strRed);	
						hashMap.put("Indicator", "red");	
					}
					if(totalIssues>0){
						SLAReports.add(hashMap);
					}
				}
				else if(strSLA.equalsIgnoreCase("withinSLA"))
				{
					
					if(resultSet.getInt(32)==0)
					{
						hashMap.put("Application_Name", resultSet.getString(2));				
						totalIssues=resultSet.getInt(31);				
						isuesWithin=resultSet.getInt(33);
						issuesOutof=resultSet.getInt(32);
									
						hashMap.put("Total_Issues", totalIssues);
						hashMap.put("Issues_within_SLA", isuesWithin);
						hashMap.put("Issues_out_of__SLA", issuesOutof);
						hashMap.put("Percent_of_Issues", "");
						
						if(totalIssues>0)
						{
							percentIssuesOutof=new Float(Math.abs((issuesOutof*100)/totalIssues));
							hashMap.put("Percent_of_Issues_within_SLA", new Float(Math.abs((isuesWithin*100)/totalIssues)));
							hashMap.put("Percent_of_Issues_out_of__SLA", new Float(Math.abs((issuesOutof*100)/totalIssues)));
						}
						
						if(totalIssues>0){
							SLAReports.add(hashMap);
						}
					}
				}
				else if(strSLA.equalsIgnoreCase("outofSLA"))
				{
					//System.out.println("Inside out of SLA block");
					if(resultSet.getInt(32)>0)
					{
						//System.out.println("Inside out of SLA block of else statement");
						hashMap.put("Application_Name", resultSet.getString(2));				
						totalIssues=resultSet.getInt(31);				
						isuesWithin=resultSet.getInt(33);
						issuesOutof=resultSet.getInt(32);
									
						hashMap.put("Total_Issues", totalIssues);
						hashMap.put("Issues_within_SLA", isuesWithin);
						hashMap.put("Issues_out_of__SLA", issuesOutof);
						
						if(totalIssues>0)
						{
							percentIssuesOutof=new Float(Math.abs((issuesOutof*100)/totalIssues));
							hashMap.put("Percent_of_Issues_within_SLA", new Float(Math.abs((isuesWithin*100)/totalIssues)));
	 						hashMap.put("Percent_of_Issues_out_of__SLA", new Float(Math.abs((issuesOutof*100)/totalIssues)));
						}				
						if(percentIssuesOutof<=10)
						{
							hashMap.put("Percent_of_Issues", strGreen);	
							hashMap.put("Indicator", "green");	
							
						}
						else if(percentIssuesOutof>10 && percentIssuesOutof<=30)
						{
							hashMap.put("Percent_of_Issues", strAmber);	
							hashMap.put("Indicator", "yellow");	
						}
						else if(percentIssuesOutof>30)
						{
							hashMap.put("Percent_of_Issues", strRed);	
							hashMap.put("Indicator", "red");	
						}
						
						if(totalIssues>0){
							SLAReports.add(hashMap);
						}
					}
				}
					
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
	//session.setAttribute("searchCriteria", strSearchCriteria);
	if(strSearchCriteria.equalsIgnoreCase("Report based on: "))
	{
		session.setAttribute("searchCriteria", "Report based on : SLA in all Applications");
	}
	else
	{
		session.setAttribute("searchCriteria", strSearchCriteria);
	}
	return SLAReports;	
	}

	public static ArrayList generateManagerReportsDAO(ApplicationReportsForm form, String strUserId)
	{
		 Connection connection=null;
		 ResultSet resultSet=null;
		 Statement statement=null;
		 HashMap ResourceDetails=null;
		 PreparedStatement preparedStatement=null;
		 CallableStatement callableStatement=null;
		 ArrayList arrayList=new ArrayList();
		 String strEmpId=form.getEmpId();
		 String strFromDate=form.getFromDate();
		 String strToDate=form.getToDate();
		 String strMgrName="";
		 String strSptctrName="";
		 String strRoleId=form.getRole();
		 String searchCriteria="Report based on:Assigned resources under Groups, Position--Support Manager";
		 String[] ManagerColNames={"Support_Manager","Support_Center_Name","Group_Supervisors","Application_Names"};
		 
		 String strEmpName=null;
		try  
		{
			connection=DBConnection.createConnection();	
			connection=DBConnection.createConnection();	
			preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Report_Emp_Issue_Cnt @Emp_User_NM=?,@From_DT=?,@To_DT=?,@Type='Support Manager',@User_NM=?,@Designation_NBR=?");
			
			//System.out.println("-----strEmpId-------------"+strEmpId); 
			if(strEmpId.equalsIgnoreCase("All"))
			{
				preparedStatement.setString(1, null);
				searchCriteria=searchCriteria+", Employee--All";
			}
			else
			{ 
				preparedStatement.setString(1, strEmpId);
			}
			
			if(form.getFromDate().equalsIgnoreCase(""))
			{
				preparedStatement.setString(2,null);
			}
			else
			{
				preparedStatement.setString(2, strFromDate);
				searchCriteria=searchCriteria+", From Date--"+strFromDate;
			}
			if(form.getToDate().equalsIgnoreCase(""))
			{
				preparedStatement.setString(3,null);
			}
			else
			{
				preparedStatement.setString(3, form.getToDate());
				searchCriteria=searchCriteria+", To Date--"+strToDate;
			}
			
			if((strRoleId!=null)&&(!form.getRole().equalsIgnoreCase(""))) {
				preparedStatement.setString(5,strRoleId);
			}
			else {
				preparedStatement.setString(5,null);
			}
			
			preparedStatement.setString(4, strUserId);			
			
			
			resultSet=preparedStatement.executeQuery();
			int counter=0;
			 while(resultSet.next())
			 {
				 ResourceDetails=new HashMap();
				 strMgrName=resultSet.getString(3);
				 //if(resultSet.getString(7).eq)
				 ResourceDetails.put(ManagerColNames[0], resultSet.getString(3).replace(",","")); 
				 ResourceDetails.put(ManagerColNames[1], resultSet.getString(1).replace(",",""));
				 ResourceDetails.put(ManagerColNames[2], resultSet.getString(4).replace(",",""));
				 ResourceDetails.put(ManagerColNames[3], resultSet.getString(5).replace(",",""));					
				 
				 arrayList.add(ResourceDetails); 
			 }
			 if(!(strEmpId.equalsIgnoreCase("All"))) 
				{				
					//searchCriteria=searchCriteria+",Employee--All";
					
					preparedStatement = connection.prepareStatement("select FIRST_NAME+' '+LAST_NAME from vims_user.employee where user_nm=?");
					preparedStatement.setString(1, strEmpId);
			        resultSet = preparedStatement.executeQuery();
			        while(resultSet.next())
					 {
			        	strEmpName=resultSet.getString(1);
					 }
					
					searchCriteria=searchCriteria+", Employee--"+strEmpName;
				}
			 if(arrayList.size()>0) 
			 {
				 arrayList.add(searchCriteria);
			 }
			
		}
		catch(Exception exception)
		{
			logger.info("ReportsDAO.generateManagerReportsDAO()");
			logger.error(exception);	
			return null;
		}
		return arrayList;
	}

	public static ArrayList generateSupervisorReportsDAO(ApplicationReportsForm form, String strUserId)
	{
		 Connection connection=null;
		 ResultSet resultSet=null;
		 Statement statement=null;
		 PreparedStatement preparedStatement=null;
		 CallableStatement callableStatement=null;
		 ArrayList arrayList=new ArrayList();
		 String strEmpId=form.getEmpId();
		 String strFromDate=form.getFromDate();
		 String strToDate=form.getToDate();
		 String strRoleId= form.getRole();
		 String searchCriteria="Report based on:Assigned resources under Groups, Position--Group Supervisor";
		 String[] SupervisorColNames={"Group_Supervisor","Group_Name","Group_Members","Application_Name","No_of_Issues","Status","Begin_Date","End_Date"};
		 
		 String strEmpName=null;
		 HashMap ResourceDetails=null;
		try
		{
			connection=DBConnection.createConnection();	
			preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Report_Emp_Issue_Cnt @Emp_User_NM=?,@From_DT=?,@To_DT=?,@Type='Group Manager',@User_NM=?,@Designation_NBR=?");
			
			if(strEmpId.equalsIgnoreCase("All")) 
			{
				preparedStatement.setString(1, null);
				searchCriteria=searchCriteria+", Employee--All";
			}
			else
			{
				preparedStatement.setString(1, strEmpId);
			}
			
			if(form.getFromDate().equalsIgnoreCase(""))
			{
				preparedStatement.setString(2,null);
			}
			else
			{
				preparedStatement.setString(2, strFromDate);
				searchCriteria=searchCriteria+", From Date--"+strFromDate;
			}
			if(form.getToDate().equalsIgnoreCase(""))
			{
				preparedStatement.setString(3,null);
			}
			else
			{
				preparedStatement.setString(3, strToDate);
				searchCriteria=searchCriteria+", To Date--"+strToDate;
			}
			if((strRoleId!=null)&&(!form.getRole().equalsIgnoreCase(""))) {
				preparedStatement.setString(5,strRoleId);
			}
			else {
				preparedStatement.setString(5,null);
			}
				
			preparedStatement.setString(4, strUserId);			
			
			
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next())
			 {
				 ResourceDetails=new HashMap();
				 ResourceDetails.put(SupervisorColNames[0], resultSet.getString(6));
				 ResourceDetails.put(SupervisorColNames[1], resultSet.getString(5));
				 ResourceDetails.put(SupervisorColNames[2], resultSet.getString(7));
				 ResourceDetails.put(SupervisorColNames[3], resultSet.getString(1));
				 ResourceDetails.put(SupervisorColNames[4], resultSet.getString(8));
				 ResourceDetails.put(SupervisorColNames[5], resultSet.getString(2));
				 ResourceDetails.put(SupervisorColNames[6], resultSet.getString(3));
				 ResourceDetails.put(SupervisorColNames[7], resultSet.getString(4));
				 
				 arrayList.add(ResourceDetails); 
			 }
			
			if(!(strEmpId.equalsIgnoreCase("All"))) 
			{				
				//searchCriteria=searchCriteria+",Employee--All";
				
				preparedStatement = connection.prepareStatement("select FIRST_NAME+' '+LAST_NAME from vims_user.employee where user_nm=?");
				preparedStatement.setString(1, strEmpId);
		        resultSet = preparedStatement.executeQuery();
		        while(resultSet.next())
				 {
		        	strEmpName=resultSet.getString(1);
				 }
				
				searchCriteria=searchCriteria+", Employee--"+strEmpName;
			}
			 if(arrayList.size()>0) 
			 {
				 arrayList.add(searchCriteria);
			 }
		}
		catch(Exception exception)
		{
			logger.info("ReportsDAO.generateSupervisorReportsDAO()");
			logger.error(exception);	
			return null;
		}
		return arrayList;
	}

	public static ArrayList generateAvailableResourcesReports(String strResourceType,String strRoleType)
	{		
		 Connection connection=null;
		 ResultSet resultSet=null;
		 Statement statement=null;
		 PreparedStatement preparedStatement=null;
		 CallableStatement callableStatement=null;		 
		 int role=0;
		 ArrayList arrayList=new ArrayList();
		 HashMap ResourceDetails=null;
		 String searchCriteria="Report based on: ";
		 String[] strAvailableResourcesColNames={"Employee_ID","Employee_Name","E_mail","Phone_Number","Position"};
		 String[] strAvailableResourcesinGrpColNames={"Group_Name","Employee_ID","Employee_Name","E_mail","Position"};
		 String strRoleName="";
		 String strQuery="EXEC vims_user.USP_Get_Employee_Dtls @Status='active',@Emp_Name=null,@User_NM=null,";
		 try
		{
			connection=DBConnection.createConnection();	

			 if(strResourceType.equalsIgnoreCase("All"))
			 {
				searchCriteria=searchCriteria+"All Resources";
				if(strRoleType.equalsIgnoreCase("All")) 
				{					
					searchCriteria=searchCriteria+", Position--All";
					strQuery=strQuery+"@Designation_NBR=null";
				}
				else
				{					
					
					strQuery=strQuery+"@Designation_NBR="+Integer.parseInt(strRoleType); 					
					
					preparedStatement = connection.prepareStatement("EXEC vims_user.USP_Get_Designation @Designation_NBR=?,@Active_FG=null,@User_NM=null");
					preparedStatement.setInt(1, Integer.parseInt(strRoleType));
			        resultSet = preparedStatement.executeQuery();
			        while(resultSet.next())
					 {
			        	strRoleName=resultSet.getString(2);
					 }
					
					searchCriteria=searchCriteria+", Position--"+strRoleName;
				}
				 //System.out.println("--------------strQuery-------------"+strQuery); 
				 
				 preparedStatement=connection.prepareStatement(strQuery);
				 resultSet=preparedStatement.executeQuery();
				 while(resultSet.next())
				 {
					 ResourceDetails=new HashMap();
					 ResourceDetails.put(strAvailableResourcesColNames[0], resultSet.getString(1));
					 ResourceDetails.put(strAvailableResourcesColNames[1], resultSet.getString(2));
					 ResourceDetails.put(strAvailableResourcesColNames[2], resultSet.getString(5));
					 ResourceDetails.put(strAvailableResourcesColNames[3], resultSet.getString(4));
					 ResourceDetails.put(strAvailableResourcesColNames[4], resultSet.getString(12));
					 
					 arrayList.add(ResourceDetails); 
				 }
			 }
			 
			 else if(strResourceType.equalsIgnoreCase("availableRes"))
			 {
				// System.out.println("-----------availableRes----------------"+strResourceType); 
				 searchCriteria=searchCriteria+"Available resources in VIMS";
				 preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Report_Available_Resource @Designation_NBR=?");
				 if(strRoleType.equalsIgnoreCase("All")) 
					{	
						preparedStatement.setString(1,null);
						//searchCriteria=searchCriteria+",Position--All";
					}
					else
					{
						preparedStatement.setString(1,strRoleType);	
						//searchCriteria=searchCriteria+",Position--"+strRoleType;
					}
				 resultSet=preparedStatement.executeQuery();
				 while(resultSet.next())
				 {
					 ResourceDetails=new HashMap();
					 ResourceDetails.put(strAvailableResourcesColNames[0], resultSet.getString(1));
					 ResourceDetails.put(strAvailableResourcesColNames[1], resultSet.getString(2));
					 ResourceDetails.put(strAvailableResourcesColNames[2], resultSet.getString(3));
					 ResourceDetails.put(strAvailableResourcesColNames[3], resultSet.getString(5));
					 ResourceDetails.put(strAvailableResourcesColNames[4], resultSet.getString(7));
					 
					 arrayList.add(ResourceDetails); 
				 }
				 
				 if(strRoleType.equalsIgnoreCase("All")) 
				 {
					 searchCriteria=searchCriteria+", Position--All";
				 }
				 else
				 {
					 	preparedStatement = connection.prepareStatement("EXEC vims_user.USP_Get_Designation @Designation_NBR=?,@Active_FG=null,@User_NM=null");
						preparedStatement.setInt(1, Integer.parseInt(strRoleType));
				        resultSet = preparedStatement.executeQuery();
				        while(resultSet.next())
						 {
				        	strRoleName=resultSet.getString(2);
						 }
						
						searchCriteria=searchCriteria+", Position--"+strRoleName;
				 }
			 }
			 
			 
			 else if(strResourceType.equalsIgnoreCase("availableResinGrps"))
			 {
				 searchCriteria=searchCriteria+"Unassigned resources under Groups";
				 preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Report_Grp_Available_Resource @Designation_NBR=?");
				 
				 if(strRoleType.equalsIgnoreCase("All")) 
					{	
						preparedStatement.setString(1,null);
					}
					else
					{
						preparedStatement.setString(1,strRoleType);						
					}
				
				 resultSet=preparedStatement.executeQuery();
				 
				 while(resultSet.next())
				 {
					 ResourceDetails=new HashMap();
					 ResourceDetails.put(strAvailableResourcesinGrpColNames[0], resultSet.getString(5));
					 ResourceDetails.put(strAvailableResourcesinGrpColNames[1], resultSet.getString(2));
					 ResourceDetails.put(strAvailableResourcesinGrpColNames[2], resultSet.getString(1));
					 ResourceDetails.put(strAvailableResourcesinGrpColNames[3], resultSet.getString(3));	
					 ResourceDetails.put(strAvailableResourcesinGrpColNames[4], resultSet.getString(7));
					 
					 arrayList.add(ResourceDetails); 
				 }
				 
				 if(strRoleType.equalsIgnoreCase("All")) 
				 {
					 searchCriteria=searchCriteria+", Position--All";
				 }
				 else
				 {
					 	preparedStatement = connection.prepareStatement("EXEC vims_user.USP_Get_Designation @Designation_NBR=?,@Active_FG=null,@User_NM=null");
						preparedStatement.setInt(1, Integer.parseInt(strRoleType));
				        resultSet = preparedStatement.executeQuery();
				        while(resultSet.next())
						 {
				        	strRoleName=resultSet.getString(2);
						 }
						
						searchCriteria=searchCriteria+", Position--"+strRoleName;
				 }
			 }
			 if(arrayList.size()>0) 
			 {
				 arrayList.add(searchCriteria);
			 }
			
		}
		catch(Exception exception)
		{
			logger.info("ReportsDAO.generateSupervisorReportsDAO()");
			logger.error(exception);	
			return null;
		}
		return arrayList;
	}

	public static byte[] getIssuePDFReportDAO(ArrayList issueList,String strTargetPath, String strSearchCriteria, String reportType)
	{	
		 HashMap details=null;
		 String[] HomeIssuesColumnNames={"Incident_ID","Application_Name","Title","Severity","Status","Created_Date","Due_Date","Closed_Date"};
         String[] HomeIssuesColumnSizes={"15","35","50","15","15","15","15","15"};

         byte[] targetData=null;
         try 
         {
           details=new HashMap();  	       
  	       details.put("path",strTargetPath);
  	       details.put("dataset",issueList);
  	       details.put("searchCriteria",strSearchCriteria);
           if(reportType.equalsIgnoreCase("HomeIssues"))
           {	
        	   HashMap hashMap=(HashMap)issueList.get(0);
	    	  // System.out.println("-----------hashMap.get('Application_Name')-----------------"+hashMap.get("Application_Name")); 
	    	   details.put("reportTitle","Issues in "+hashMap.get("Application_Name"));
  		       details.put("columnSizes",HomeIssuesColumnSizes);
  		       details.put("ColumnNames",HomeIssuesColumnNames);
  		      // System.out.println("------------getIssuePDFReportDAO---------------------");
  	       }  	       
  	      // System.out.println("details in DAO class is "+details);
  	       targetData=ExcelGenerator.generatePdfFile(details);
  	       //System.out.println("--------targetData-----------------------"+targetData.length);
         }
  catch(Exception exception)
  {
  	
  	  logger.info("----Exception in generateIssueExcelFileDAO-----");
  	  logger.error(exception);
  	  exception.printStackTrace();
  }
   return targetData;
	}

	public static ArrayList generateAllPositionReportsDAO(ApplicationReportsForm form, String strUserId)
	{	
		 Connection connection=null;
		 ResultSet resultSet=null;
		 Statement statement=null;
		 PreparedStatement preparedStatement=null;
		 CallableStatement callableStatement=null;		 
		 int role=0;
		 ArrayList arrayList=new ArrayList();
		 HashMap ResourceDetails=null;
		 String searchCriteria="Report based on:Assigned resources under Groups, Position--All, Employee--All";
		 String[] strAllPositionsColNames={"Support_Manager","Group_Supervisor","Group_Members","No_of_Applications","No_of_Issues"};
		 
		 String strEmpId=form.getEmpId();
		 String strFromDate=form.getFromDate();
		 String strToDate=form.getToDate();
		 
		 try 
			{
				connection=DBConnection.createConnection();	
				connection=DBConnection.createConnection();	
				preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Report_Employee_All @User_NM=?,@From_DT=?,@To_DT=?");
				
				preparedStatement.setString(1, strUserId);
				if(form.getFromDate().equalsIgnoreCase(""))
				{
					preparedStatement.setString(2,null);
				}
				else
				{
					preparedStatement.setString(2, form.getFromDate());
					searchCriteria=searchCriteria+", From Date--"+form.getFromDate();
				}
				if(form.getToDate().equalsIgnoreCase(""))
				{
					preparedStatement.setString(3,null);
				}
				else
				{
					preparedStatement.setString(3, form.getToDate());
					searchCriteria=searchCriteria+", To Date--"+form.getToDate();
				}			
				resultSet=preparedStatement.executeQuery();
				int counter=0;
				 while(resultSet.next())
				 {
					 ResourceDetails=new HashMap();
					
					 ResourceDetails.put(strAllPositionsColNames[0], resultSet.getString(4));
					 ResourceDetails.put(strAllPositionsColNames[1], resultSet.getString(6));
					 ResourceDetails.put(strAllPositionsColNames[2], resultSet.getString(7));
					 ResourceDetails.put(strAllPositionsColNames[3], resultSet.getString(9));
					 ResourceDetails.put(strAllPositionsColNames[4], resultSet.getString(8));
					 
					 arrayList.add(ResourceDetails); 
				 }
				 if(arrayList.size()>0) 
				 {
					 arrayList.add(searchCriteria);
				 }
				
			}
			catch(Exception exception)
			{
				logger.info("ReportsDAO.generateSupervisorReportsDAO()");
				logger.error(exception);	
				return null;
			}
			return arrayList;
		}
	}

