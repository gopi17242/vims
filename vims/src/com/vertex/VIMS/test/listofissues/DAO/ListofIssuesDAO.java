/*  File Name : ListofIssuesDAO.java
 *  
 *  copyright (c) 2008 Vertex.All rights reserved.
 *  
 *  Created By : Aditya.p
 *  
 *  Description : This is the DAO(DataAccessObject) class which contains some functions which will be called by the BD Class.
 *  			  By using this we can collect the information about the issues,we can assign the issue to a particular person,
 *                we can change the issue status. We are using this file to assign an issue and also to change the issue status.
 *
 *   Change History:
 * 	  Revision No.			Date		 @author			Description
 *		1.0					16-11-2008   Aditya.p			Initial version
 *
 *   
 *  
 *  			
 * 
 */

package com.vertex.VIMS.test.listofissues.DAO;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.vertex.VIMS.test.VIMSSQLQueryInterface.VIMSQueryInterface;
import com.vertex.VIMS.test.common.DBConnection;
import com.vertex.VIMS.test.listofissues.form.ListofIssuesForm;
import com.vertex.VIMS.test.newissue.IssueScheduler.NewIssueScheduler;
import com.vertex.VIMS.test.common.VIMSMail;
import com.vertex.VIMS.test.escalation.EscalatedScheduler.EscalatedIssueScheduler;
import com.vertex.VIMS.test.applications.DAO.VIMSApplicationDAO;


/**
 * @author aditya.p
 *
 */
public class ListofIssuesDAO
{	//class ListofIssuesDAO Start
	
	 static Connection connection=null;
	 static Statement statement=null;
	 static PreparedStatement preparedStatement=null;
	 static ResultSet resultset=null;
	 static CallableStatement callableStatement=null;
	 static Logger logger=Logger.getLogger("Admin");
	 enum enumIssueType {open,rejected,closed,accepted,reopened,assigned,escalated,completed};	 
	 static HashMap hashmap=null;

	
 /**
 * @param resultset
 * @return
 */
/*public static ArrayList listOfIssues(ResultSet resultset,String strRoleType)
{	//function listOfIssues start
	
	 System.out.println("-----Role Type in listOfIssues--------"+strRoleType);
	 ArrayList IssuesList= new ArrayList();
	 String FromDate = null;
	 String ClosedDate = null;
	 String TargetDate=null;
	 String strPostedDate=null;
	 String strDueDate=null;
	 String strClosedDate=null;
	 String linkView=null;
	 
	 try
	  {  //try start
		 
		 while(resultset.next())
		 {	//while start
			 
			hashmap = new HashMap();
			
		if((strRoleType.equalsIgnoreCase("Admin"))||(strRoleType.equalsIgnoreCase("Customer")))
		{
			linkView="<a href='./ListofIssues.do?methodname=IssueDetails&id="+(String)resultset.getString(1)+"'>"+resultset.getString(1)+"</a>";
		}
		else
		{
			linkView="<a href='./ListofIssues.do?methodname=IssueDetails1&param=Details&id="+(String)resultset.getString(1)+"'>"+resultset.getString(1)+"</a>";
			
		}
			hashmap.put("id", linkView);
			
			strPostedDate=resultset.getString(4);
			strDueDate=resultset.getString(9);
			strClosedDate=resultset.getString(10);
			
			if(strPostedDate!=null)
			{	//if start
				
				strPostedDate=ConvertDate(strPostedDate);
			}	//if end
			else
			{	//else start
				strPostedDate=strPostedDate;
			}	//else end
			if(strDueDate!=null)
			{	//if start
				strDueDate=ConvertDate(strDueDate);
			}	//if end
			else
			{	//else start
				strDueDate=strDueDate;
			}	//else end
			if(strClosedDate!=null)
			{	//if start
				strClosedDate=ConvertDate(strClosedDate);
			}	//if end
			else
			{	//else start
				strClosedDate=strClosedDate;
			}	//else end
			
			hashmap.put("applicationName", resultset.getString(3));
			hashmap.put("title", resultset.getString(2));
			hashmap.put("status", resultset.getString(6));
			hashmap.put("severity", resultset.getString(5));
			hashmap.put("assignedto", resultset.getString(7));
			hashmap.put("postedby", resultset.getString(8));
			hashmap.put("posteddate",strPostedDate);
			//hashmap.put("duedate",strDueDate);
			hashmap.put("closeddate",strClosedDate);
				
			IssuesList.add(hashmap);
			
		}//while end
	  } //try end
	 
	  catch(Exception e)   
	  {	//catch start
		  logger.error(e);
	  }	//catch end
	   return IssuesList;
	}//function listOfIssues end
*/ 
  /**
 * @param issuesform
 * @param strUserID
 * @param strRoleType
 * @return
 */
public static ArrayList IssuesList(ListofIssuesForm issuesform, String strUserID, String strRoleType)throws Exception
{	//function IssuesList start
	
		 ArrayList IssuesList= new ArrayList();
		 ResultSet resultset=null;
		 String strAdmin;
		 String strCustomer;
		 String strInternal;
		 String FromDate = null;
		 String ClosedDate = null;
		 String TargetDate=null;
		 String strPostedDate=null;
		 String strDueDate=null;
		 String strClosedDate=null;
		 String linkView=null;
	
			
		//String strSelectedIssueType=issuesform.getTypeofIssue();
		//System.out.println("===========strSelectedIssueType==========="+strSelectedIssueType);
		//System.out.println("==========strUserID==========="+strUserID);
		//System.out.println("==========strRoleType==========="+strRoleType);
	   
		//System.out.println("-------strRoleType--------"+strRoleType);
		
		//System.out.println("-------strSelectedIssueType--------"+strSelectedIssueType);
		
		if(strRoleType.equalsIgnoreCase("admin"))
		{	//if start
			 
			 strAdmin=strUserID;
			 strCustomer=null;
			 strInternal=null;CallableStatement cstmt=null;
			 Connection con =null;
			  //System.out.println("==============strSelectedIssueType============"+strSelectedIssueType);
					try 
					 {//try start
						
						  	con = DBConnection.createConnection();
						    cstmt=con.prepareCall("{CALL vims_user.USP_Report_Issues(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						    cstmt.setString(1,null);
						    cstmt.setString(2,null);
						    cstmt.setString(3,null);
						    cstmt.setString(4,null);
						    cstmt.setString(5,null);
						    cstmt.setString(6,null);
						    cstmt.setString(7,null);
						    cstmt.setString(8,null);
						    cstmt.setString(9,null);
						    cstmt.setString(10,strCustomer);
						    cstmt.setString(11,strInternal);
						    cstmt.setString(12,strAdmin);
						    cstmt.setString(13,"0");
						    cstmt.execute();
							//resultset=callableStatement.getResultSet();
						 /*callableStatement=connection.prepareCall("{CALL USP_Get_Incident_Dtls(?,?,?)}");
						 callableStatement.setString(1,null);
						 callableStatement.setString(2,null);
						 callableStatement.setString(3,null);*/
						    cstmt.execute();
						 resultset=cstmt.getResultSet();
						 //IssuesList=listOfIssues(resultset,strRoleType);
						 
						 while(resultset.next())
						 {	//while start
							 
							HashMap hashmap = new HashMap();
						
							linkView="<a href='./ListofIssues.do?methodname=IssueDetails&id="+(String)resultset.getString(1)+"'>"+resultset.getString(1)+"</a>";
							hashmap.put("id", linkView);
							
							strPostedDate=resultset.getString(4);
							strDueDate=resultset.getString(9);
							strClosedDate=resultset.getString(10);
							
							if(strPostedDate!=null)
							{	//if start
								
								strPostedDate=ConvertDate(strPostedDate);
							}	//if end
							else
							{	//else start
								strPostedDate=strPostedDate;
							}	//else end
							if(strDueDate!=null)
							{	//if start
								strDueDate=ConvertDate(strDueDate);
							}	//if end
							else
							{	//else start
								strDueDate=strDueDate;
							}	//else end
							if(strClosedDate!=null)
							{	//if start
								strClosedDate=ConvertDate(strClosedDate);
							}	//if end
							else
							{	//else start
								strClosedDate=strClosedDate;
							}	//else end
							
							hashmap.put("applicationName", resultset.getString(3));
							hashmap.put("title", resultset.getString(2));
							hashmap.put("status", resultset.getString(6));
							hashmap.put("severity", resultset.getString(5));
							hashmap.put("assignedto", resultset.getString(7));
							hashmap.put("postedby", resultset.getString(8));
							hashmap.put("posteddate",strPostedDate);
							//hashmap.put("duedate",strDueDate);
							hashmap.put("closeddate",strClosedDate);
							hashmap.put("customer", resultset.getString(13));
							
							IssuesList.add(hashmap);
							
						}//while end
					 }	//try end
					catch (Exception e)
					 {	//catch start
						e.printStackTrace();
						logger.error(e);
					 }	//catch end
					finally{
						if(con!=null){con.close();}
						if(cstmt!=null){cstmt.close();}
						if(resultset!=null){resultset.close();}
					}
			    
			 }//closing of admin if		
	
	  else if(strRoleType.equals("User"))
	  {	//else if start
		    strAdmin=null;
			strCustomer=null;
			strInternal=strUserID;
        			try 
					{	//try start
						
						connection = DBConnection.createConnection();
						//statement = connection.createStatement();
						callableStatement=connection.prepareCall("{CALL vims_user.USP_Report_Issues(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						callableStatement.setString(1,null);
						callableStatement.setString(2,null);
						callableStatement.setString(3,null);
						callableStatement.setString(4,null);
						callableStatement.setString(5,null);
						callableStatement.setString(6,null);
						callableStatement.setString(7,null);
						callableStatement.setString(8,null);
						callableStatement.setString(9,null);
						callableStatement.setString(10,strCustomer);
						callableStatement.setString(11,strInternal);
						callableStatement.setString(12,strAdmin);
						callableStatement.setString(13,"0");
						/*callableStatement=connection.prepareCall("{CALL USP_Get_Incident_Dtls(?,?,?)}");
						 callableStatement.setString(1,null);
						 callableStatement.setString(2,strUserID);
						 callableStatement.setString(3,null);*/
						 callableStatement.execute();
						 resultset=callableStatement.getResultSet();
						 //IssuesList=listOfIssues(resultset,strRoleType);
		                //resultset = statement.executeQuery("SELECT DISTINCT inc.INCIDENT_ID,app.APPLICATION_NAME,inc.INCIDENT_TITLE,inc.INCIDENT_POSTED_DATE,inc.INCIDENT_TYPE,inc.DUE_DATE,inc.INCT_STATUS,inc.CLOSED_DATE,inc.CUSTOMER_ID	FROM Incident inc INNER join vims_user.GROUP_APPLICATION ga ON inc.APPLICATION_ID=ga.APPLICATION_ID LEFT join vims_user.APPLICATION_SPECIALISTS asp ON asp.APPLICATION_ID=inc.APPLICATION_ID  INNER join vims_user.GROUP_MEMBERS gm ON gm.USRGROUP_ID=ga.USRGROUP_ID INNER join vims_user.EMPLOYEE e ON gm.EMPLOYEE_ID=e.EMPLOYEE_ID LEFT join vims_user.APPLICATION_SPECIALISTS asp1 ON asp1.EMPLOYEE_ID=e.EMPLOYEE_ID INNER join vims_user.APPLICATION app ON inc.APPLICATION_ID=app.APPLICATION_ID WHERE e.EMPLOYEE_ID='"+strUserID+"'");
		                
						//preparedStatement=connection.prepareStatement(VIMSQueryInterface.employeeAllIssues);
		                //preparedStatement.setString(1,strUserID);
		                
		                //resultset = preparedStatement.executeQuery();
		                
		                //IssuesList=listOfIssues(resultset);
						 while(resultset.next())
						 {	//while start
							 
							hashmap = new HashMap();
							if(resultset.getString(12).equalsIgnoreCase("Yes"))
							{
							 linkView="<a href='./ListofIssues.do?methodname=IssueDetails&param=Details&id="+(String)resultset.getString(1)+"'>"+resultset.getString(1)+"</a>";
							}
							else
							{
							 linkView="<a href='./ListofIssues.do?methodname=IssueDetails1&param=Details&id="+(String)resultset.getString(1)+"'>"+resultset.getString(1)+"</a>";
							}
							hashmap.put("id", linkView);
							
							strPostedDate=resultset.getString(4);
							strDueDate=resultset.getString(9);
							strClosedDate=resultset.getString(10);
							
							if(strPostedDate!=null)
							{	//if start
								
								strPostedDate=ConvertDate(strPostedDate);
							}	//if end
							else
							{	//else start
								strPostedDate=strPostedDate;
							}	//else end
							if(strDueDate!=null)
							{	//if start
								strDueDate=ConvertDate(strDueDate);
							}	//if end
							else
							{	//else start
								strDueDate=strDueDate;
							}	//else end
							if(strClosedDate!=null)
							{	//if start
								strClosedDate=ConvertDate(strClosedDate);
							}	//if end
							else
							{	//else start
								strClosedDate=strClosedDate;
							}	//else end
							
							hashmap.put("applicationName", resultset.getString(3));
							hashmap.put("title", resultset.getString(2));
							hashmap.put("status", resultset.getString(6));
							hashmap.put("severity", resultset.getString(5));
							hashmap.put("assignedto", resultset.getString(7));
							hashmap.put("postedby", resultset.getString(8));
							hashmap.put("posteddate",strPostedDate);
							//hashmap.put("duedate",strDueDate);
							hashmap.put("closeddate",strClosedDate);
							hashmap.put("customer", resultset.getString(11));	
							
							IssuesList.add(hashmap);
							
						}//while end
						
					}//try end
				   catch (Exception e)
					{	//catch start
					   logger.error(e);
					}	//catch end
				   
			  }//else if end
		
				
		 else if(strRoleType.equals("Customer"))
         { //else if start
			 //System.out.println("===========User ID in Customer ============"+strUserID);
			    strAdmin=null;
				strCustomer=strUserID;
				strInternal=null;
					try 
						{	//try start
							connection = DBConnection.createConnection();
							callableStatement=connection.prepareCall("{CALL vims_user.USP_Report_Issues(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
							callableStatement.setString(1,null);
							callableStatement.setString(2,null);
							callableStatement.setString(3,null);
							callableStatement.setString(4,null);
							callableStatement.setString(5,null);
							callableStatement.setString(6,null);
							callableStatement.setString(7,null);
							callableStatement.setString(8,null);
							callableStatement.setString(9,null);
							callableStatement.setString(10,strCustomer);
							callableStatement.setString(11,strInternal);
							callableStatement.setString(12,strAdmin);
							callableStatement.setString(13,"0");
							 /*statement = connection.createStatement();
							 callableStatement=connection.prepareCall("{CALL USP_Get_Incident_Dtls(?,?,?)}");
							 callableStatement.setString(1,strUserID);
							 callableStatement.setString(2,null);
							 callableStatement.setString(3,null);*/
							 callableStatement.execute();
							 resultset=callableStatement.getResultSet();
							 //IssuesList=listOfIssues(resultset,strRoleType);
													
							 while(resultset.next())
							 {	//while start
								 
								hashmap = new HashMap();
								
								linkView="<a href='./ListofIssues.do?methodname=IssueDetails&id="+(String)resultset.getString(1)+"'>"+resultset.getString(1)+"</a>";
							
								hashmap.put("id", linkView);
								
								strPostedDate=resultset.getString(4);
								strDueDate=resultset.getString(9);
								strClosedDate=resultset.getString(10);
								
								if(strPostedDate!=null)
								{	//if start
									
									strPostedDate=ConvertDate(strPostedDate);
								}	//if end
								else
								{	//else start
									strPostedDate=strPostedDate;
								}	//else end
								if(strDueDate!=null)
								{	//if start
									strDueDate=ConvertDate(strDueDate);
								}	//if end
								else
								{	//else start
									strDueDate=strDueDate;
								}	//else end
								if(strClosedDate!=null)
								{	//if start
									strClosedDate=ConvertDate(strClosedDate);
								}	//if end
								else
								{	//else start
									strClosedDate=strClosedDate;
								}	//else end
								
								hashmap.put("applicationName", resultset.getString(3));
								hashmap.put("title", resultset.getString(2));
								hashmap.put("status", resultset.getString(6));
								hashmap.put("severity", resultset.getString(5));
								hashmap.put("assignedto", resultset.getString(7));
								hashmap.put("postedby", resultset.getString(8));
								hashmap.put("posteddate",strPostedDate);
								//hashmap.put("duedate",strDueDate);
								hashmap.put("closeddate",strClosedDate);
								hashmap.put("customer", resultset.getString(11));
								
								IssuesList.add(hashmap);
								
							}//while end
						}	//try end
					   catch (Exception e)
						{	//catch start
						   logger.error(e);
						}	//catch end
		        }//else if end
		return IssuesList;
   }//function IssuesList end
  
              
	
	/**
	 * @param request
	 * @param strID
	 * @return
	 */
	public static ArrayList IssueDetails1(HttpServletRequest request,String strID)
	{	//function IssueDetails1 start
		
			ArrayList IssueDetails1=new ArrayList();
			ArrayList IssuesList= new ArrayList();
			Connection connection=null;
			ResultSet resultset=null;
			PreparedStatement preparedStatement=null;
			HashMap hashmap=null;
			HttpSession session;
			session=request.getSession();
			
			String strDueDate=null;
			String strFromDate=null;
			
			try
			{	//try start
				
				System.out.println("==========IssueID in DAO============"+strID);
				connection = DBConnection.createConnection();
				
				callableStatement=connection.prepareCall("{CALL vims_user.USP_Get_Incident_Dtls_by_Incident(?)}");
				callableStatement.setString(1,strID);
				callableStatement.execute();
				resultset=callableStatement.getResultSet();
				
				while (resultset.next())
				{	//while start
					System.out.println("=======In Resultset loop==============");
					hashmap = new HashMap();
					hashmap.put("IssueId", strID);
					hashmap.put("ApplicationName", resultset.getString(1));
					hashmap.put("IncidentType", resultset.getString(2));
					hashmap.put("CustomerID", resultset.getString(3));
					
					if(resultset.getString(4)!=null)
					{	//if start
						strDueDate=ConvertDate(resultset.getString(4));
					}	//if end
					
					if(resultset.getString(5)!=null)
					{	//if start
						strFromDate=ConvertDate(resultset.getString(5));
					}	//if end
					hashmap.put("DueDate",strDueDate);
					hashmap.put("FromDate",strFromDate);
					hashmap.put("title", resultset.getString(6));
					hashmap.put("status", resultset.getString(7));
					hashmap.put("appsubcategory", resultset.getString(11));
					hashmap.put("Issueverifiedstatus",resultset.getString(12));
					System.out.println("==========resultset.getInt(12)========="+resultset.getInt(12));
					IssueDetails1.add(hashmap);
					
					System.out.println("======IssueDetails1==in====DAO======"+IssueDetails1);
				}//while end
				
				callableStatement.close();
				resultset.close();
			} //try end
			catch (Exception e)
			{	//catch start
				logger.error(e);
				e.printStackTrace();
			}	//catch end
			finally
			{//finally start
				DBConnection.closeConnection();
			}	//finally end
			System.out.println("======end of resultset loop===========");
			return IssueDetails1;
		}//function IssueDetails1 end

		/**
		 * @param strID
		 * @return
		 */
		public static ArrayList IssueDetails2(String strID)
		{	//function IssueDetails2 start
			ArrayList IssueDetails2=new ArrayList();
			ArrayList IssuesList= new ArrayList();
			Connection connection=null;
			ResultSet resultset=null;
			
			PreparedStatement preparedStatement=null;
			
			HashMap hashmap=null;
			
			try
			{	//try start
				connection = DBConnection.createConnection();
				preparedStatement=connection.prepareStatement(VIMSQueryInterface.IssueDetails2);
				preparedStatement.setString(1,strID);
				resultset=preparedStatement.executeQuery();
				while (resultset.next())
				{	//while start
					hashmap = new HashMap();
					hashmap.put("assignedBy", resultset.getString(1));
					hashmap.put("statusFrom", resultset.getString(3));
					hashmap.put("statusTo", resultset.getString(4));
					hashmap.put("onDate", resultset.getString(5));
					hashmap.put("comments", resultset.getString(7));
					IssueDetails2.add(hashmap);
				}	//while end
			}//try end
			catch (Exception e)
			{//catch start
				logger.error(e);
			}//catch end
			finally
			{//finally start
				DBConnection.closeConnection();
			}//finally end
			return IssueDetails2;
			
		}//function IssueDetails2 end
		/**
		 * @param strID
		 * @return
		 */
		public static ArrayList IssueDetails3(String strID)
		{//function IssueDetails3 start
			
			ArrayList IssuesList= new ArrayList();
			ArrayList IssueDetails3=new ArrayList();
			
			HashMap hashmap=null;
			
			try
			{	//try start
				
				connection = DBConnection.createConnection();
				callableStatement=connection.prepareCall("{CALL vims_user.USP_Get_Issue_History(?)}");
				callableStatement.setString(1,strID);
				callableStatement.execute();
				resultset=callableStatement.getResultSet();
				//preparedStatement = connection.prepareStatement(VIMSQueryInterface.IssueDetails3);
				//preparedStatement.setString(1,strID);
				//resultset = preparedStatement.executeQuery();
				while (resultset.next())
				{	//while start
					hashmap = new HashMap();
					hashmap.put("assignedby", resultset.getString(1));
					hashmap.put("assigndate", resultset.getString(2));
					hashmap.put("statusfrom", resultset.getString(6));
					hashmap.put("statusto", resultset.getString(7));
					hashmap.put("assignedto", resultset.getString(3));
					hashmap.put("comments", resultset.getString(8));
					hashmap.put("date", resultset.getString(11));
					//hashmap.put("mailedfrom", resultset.getString(7));
					//hashmap.put("mailedto", resultset.getString(8));
					//hashmap.put("reason", resultset.getString(7));
					IssueDetails3.add(hashmap);
				}	//while end
				preparedStatement.close();
				resultset.close();
			} //try end
			catch (Exception e)
			{	//catch start
				logger.error(e);
			}	//catch end
			finally
			{	//finally start
				DBConnection.closeConnection();
			}	//finally end
			return IssueDetails3;
		}//function IssueDetails3 end

		/**
		 * @param strID
		 * @return
		 */
		public static ArrayList IssueDetails4(String strID)
		{	//function IssueDetails4 start
	        
			ArrayList IssueDetails4=new ArrayList();
			ArrayList IssuesList= new ArrayList();		
			HashMap hashmap=null;
			
			try
			{	//try start
				connection = DBConnection.createConnection();
				preparedStatement = connection.prepareStatement(VIMSQueryInterface.IssueDescription);
				preparedStatement.setString(1, strID);
				resultset = preparedStatement.executeQuery();
				while (resultset.next())
				{	//while start
					hashmap = new HashMap();
					hashmap.put("description", resultset.getString(1));
					IssueDetails4.add(hashmap);
				}	//while end
				preparedStatement.close();
				resultset.close();
			} //try start
			catch (Exception e)
			{	//catch start
				logger.error(e);
			}	//catch end
			finally
			{	//finally start
				DBConnection.closeConnection();
			}	//finally end
			return IssueDetails4;
		}//function IssueDetails4 end

		/**
		 * @param strID
		 * @return
		 */
		public static ArrayList getFileAttachment(String strID)
		{	//function getFileAttachment start
			
			String strFileName = null;
			ArrayList FileName=new ArrayList();		
			try
			{	//try start
				connection = DBConnection.createConnection();
				preparedStatement = connection.prepareStatement(VIMSQueryInterface.getFileName);
				preparedStatement.setString(1, strID);
				resultset = preparedStatement.executeQuery();
				
				while (resultset.next())
				{	//while start
					FileName.add(resultset.getString(1));
					//strFileName = resultset.getString(1);
				}	//while end
				preparedStatement.close();
				resultset.close();
			} 	//try end
			catch (Exception e)
			{	//catch start
				logger.error(e);
			}	//catch end
			finally
			{	//finally start
				DBConnection.closeConnection();
			}	//finally end
			//returning the ArrayList
			return FileName;
		}//function getFileAttachment end

		/**
		 * @return
		 */
		public static ArrayList getGroups()
		{	//function getGroups start
			ArrayList groups=new ArrayList();
			ArrayList IssuesList= new ArrayList();
			HashMap hashmap=null;
			
			try
			{	//try start
				connection = DBConnection.createConnection();
				preparedStatement = connection.prepareStatement(VIMSQueryInterface.getGroupsSQL);
				resultset = preparedStatement.executeQuery();
				
				while (resultset.next())
				{	//while start
					hashmap = new HashMap();
					hashmap.put("groupid", resultset.getString(1));
					hashmap.put("groupname", resultset.getString(2));
					groups.add(hashmap);
				}	//while end
				preparedStatement.close();
				resultset.close();
			} //try end
			catch (Exception e)
			{	//catch start
				logger.error(e);
			}	//catch end
			finally
			{	//finally start
				DBConnection.closeConnection();
			}	//finally end
			return groups;
		}//function getGroups end

		/**
		 * @param strID
		 * @return
		 */
		public static ArrayList getGroupEmployees(String strID)
		{	//function getGroupEmployees start
			
			ArrayList groupemployees=new ArrayList();
			HashMap hashmap=null;
			
			try
			{	//try start
				
				connection = DBConnection.createConnection();
				callableStatement=connection.prepareCall("{CALL vims_user.USP_Get_Grp_Mbrs_By_Issue(?,?)}");
				callableStatement.setString(1,strID);
				callableStatement.setString(2,null);
				callableStatement.execute();
				resultset = callableStatement.getResultSet();
				
				while (resultset.next())
				{	//while start
					
					hashmap=new HashMap();
					
					//Placing the values into HashMap
					hashmap.put("EmployeeId", resultset.getString(1));
					hashmap.put("EmployeeName", resultset.getString(2));
					
					//groupemployees.add(resultset.getString(1));
					
					//Placing the HashMap into ArrayList
					groupemployees.add(hashmap);

				}//while end
				callableStatement.close();
				preparedStatement.close();
				resultset.close();
				
			} //try end
			catch (Exception e)
			{	//catch start
				logger.error(e);
			}	//catch end
			finally
			{//finally
				DBConnection.closeConnection();
			}//finally end
			return groupemployees;
		}//function getGroupEmployees end

		/**
		 * @param strIssuesID
		 * @param strAssignedBy
		 * @param strIssueStatusFrom
		 * @param strIssuesStatusTo
		 * @param strAssignTo
		 * @param strComments
		 * @return
		 */
		public static int AssignIssue(String strIssuesID, String strAssignedBy,
				String strIssueStatusFrom, String strIssuesStatusTo,
				String strAssignTo, String strComments)
				{ //function AssignIssue start
			ArrayList IssuesList= new ArrayList();
			String strIsuueStatusID;
			String strAssignDate = null;
			String resultsetIssueStatusID = null;
			
			CallableStatement callableStatement;
			int intResult = 0;
			int intResponse1;
			int intResponse2 = 0;
			int intIssueStatusID=0;
			try
			{   //try start
				connection = DBConnection.createConnection();
				System.out.println("=============Before Assign Function===in DAO=========1/27/2009===========");
				if(strIssueStatusFrom.equalsIgnoreCase("Escalated"))
				{	//if start
					System.out.println("=============Before Escalated Operation====in DAO in ESCALATED========1/27/2009===========");
					callableStatement=connection.prepareCall("{?=CALL vims_user.USP_Save_Inc_Due_DT(?)}");
					callableStatement.registerOutParameter(1,Types.OTHER);
					callableStatement.setString(2,strIssuesID);
					System.out.println("=============After Escalated Operation====in DAO in ESCALATED========1/27/2009===========");
					callableStatement.execute();
					
					int intResponse=callableStatement.getInt(1);
					
					callableStatement.close();
				}	//if end			
				preparedStatement = connection.prepareStatement(VIMSQueryInterface.getDateSQL);
				resultset = preparedStatement.executeQuery();
				
				while (resultset.next())
				{	//while start
					strAssignDate = resultset.getString(1);
				}	//while end
				resultset.close();
				preparedStatement.close();
				
				preparedStatement = connection.prepareStatement(VIMSQueryInterface.getIssueStatusIDSQL);
				preparedStatement.setString(1,strIssuesID);
				resultset=preparedStatement.executeQuery();
				
				while(resultset.next())
				{//while start
					resultsetIssueStatusID=resultset.getString(1);
				}//while end
				if(resultsetIssueStatusID.equals(0))
				{//if start
					intIssueStatusID=1;
				}//if end
				else
				{//else start
					intIssueStatusID=Integer.parseInt(resultsetIssueStatusID)+1;
					System.out.println("==============intIssueStatusID============="+intIssueStatusID);
				}//else end
				resultset.close();
				preparedStatement.close();
				
				System.out.println("==============strAssignTo==in DAO==========="+strAssignTo);
				preparedStatement=connection.prepareStatement(VIMSQueryInterface.AssignIssueSQL);
				
				preparedStatement.setString(1,strIssuesID);
				preparedStatement.setInt(2,intIssueStatusID);
				preparedStatement.setString(3,strAssignedBy);
				preparedStatement.setString(4,strAssignDate);
				preparedStatement.setString(5,strAssignTo);
				preparedStatement.setString(6,strIssueStatusFrom);
				preparedStatement.setString(7,strIssuesStatusTo);
				preparedStatement.setString(8,strAssignDate);
				preparedStatement.setString(9,strComments);
							
				intResponse1=preparedStatement.executeUpdate();
				
				preparedStatement.close();
				
				if(intResponse1==1)
				{	//if start
					preparedStatement=connection.prepareStatement(VIMSQueryInterface.Incident_Table_StatusChangeSQL);
					
					preparedStatement.setString(1,strIssuesStatusTo);
					preparedStatement.setString(2,strIssuesID);
					
					intResponse2=preparedStatement.executeUpdate();
					
				}   //if end
				preparedStatement.close();
				if(intResponse2==0)
				{	//if start
					intResult=1;
				}//if end
				else
				{//else start
					intResult=0;
				}//else end
			} //try end
			catch (Exception e)
			{//catch start
				logger.error(e);
			}//catch end
			return intResult;
		}//function AssignIssue end

		/**
		 * @param strIssuesID
		 * @param strAssignedBy
		 * @param strIssueStatusFrom
		 * @param strIssuesStatusTo
		 * @param strReason
		 * @param strRoleType
		 * @param strAssignTo 
		 * @return
		 */
		public static int ChangeStatus(String strIssuesID, String strAssignedBy,
				String strIssueStatusFrom, String strIssuesStatusTo,
				String strReason,String strRoleType, String strAssignTo)
		{	//function ChangeStatus start
			
			String strIsuueStatusID;
			String strCurrentDate = null;
			String resultsetIssueStatusID = null;
			//String strAssignTo = null;
			ArrayList IssuesList= new ArrayList();
			int intResult = 0;
			int intResponse1;
			int intResponse2 = 0;
			int intIssueStatusID=0;

			if(strRoleType.equals("Admin"))	
	    	{	//if start
			try
			{	//try start
				connection = DBConnection.createConnection();
				preparedStatement = connection.prepareStatement(VIMSQueryInterface.getDateSQL);
				resultset = preparedStatement.executeQuery();
				
				while (resultset.next())
				{//while start
					strCurrentDate = resultset.getString(1);
				}//while end
				resultset.close();
				preparedStatement.close();
				
				preparedStatement=connection.prepareStatement(VIMSQueryInterface.getIssueAssignedtoSQL);
				
				preparedStatement.setString(1,strIssuesID);
				preparedStatement.setString(2,strIssuesID);
				
				resultset=preparedStatement.executeQuery();
				
				while(resultset.next())
				{//while start
					strAssignTo=resultset.getString(1);
				}//while end
				preparedStatement.close();
				resultset.close();
				
				preparedStatement = connection.prepareStatement(VIMSQueryInterface.getIssueStatusIDSQL);
				preparedStatement.setString(1,strIssuesID);
				resultset=preparedStatement.executeQuery();
				
				while(resultset.next())
				{//while start
					resultsetIssueStatusID=resultset.getString(1);
				}//while end
				if(resultsetIssueStatusID.equals(0))
				{//if start
					intIssueStatusID=1;
				}//if end
				else
				{//else start
					intIssueStatusID=Integer.parseInt(resultsetIssueStatusID)+1;
				}//else end
				resultset.close();
				preparedStatement.close();
				
				preparedStatement=connection.prepareStatement(VIMSQueryInterface.ChangeIssueStatusSQL);
				preparedStatement.setString(1,strIssuesID);
				preparedStatement.setInt(2,intIssueStatusID);
				preparedStatement.setString(3,strAssignedBy);
				preparedStatement.setString(4,strCurrentDate);
				System.out.println("========strCurrentDate===admin========"+strCurrentDate);
				preparedStatement.setString(5,strAssignTo);
				System.out.println("------------strAssignTo in Admin----------"+strAssignTo);
				preparedStatement.setString(6,strIssueStatusFrom);
				preparedStatement.setString(7,strIssuesStatusTo);
				preparedStatement.setString(8,strCurrentDate);
				System.out.println("========strCurrentDate====admin======="+strCurrentDate);
				preparedStatement.setString(9,strReason);
				
				intResponse1=preparedStatement.executeUpdate();
				
				preparedStatement.close();
				
				if(intResponse1==1)
				{//if start
					//System.out.println("============In DAO Before Status change===================");
					preparedStatement=connection.prepareStatement(VIMSQueryInterface.Incident_Table_StatusChangeSQL);
					preparedStatement.setString(1,strIssuesStatusTo);
					preparedStatement.setString(2,strIssuesID);
					intResponse2=preparedStatement.executeUpdate();
					//System.out.println("============In DAO After Status change=intResponse2=================="+intResponse2);
					//System.out.println("============In DAO After Status change===================");
				}//if end
				preparedStatement.close();
			
				if(intResponse2==0)
				{//if start
					intResult=1;
				}//if end
				else
				{//else start
					intResult=0;
				}//else end
			} //try end
			catch (Exception e)
			{//catch start
				logger.error(e);
			}//catch end
				
			return intResult;
		}//if end
		
		else if(strRoleType.equals("User") || strRoleType.equals("Customer") )	
		{//else id start
			
			String AsignedBy=null;
			
			if(strRoleType.equals("Customer"))
			{
				strAssignedBy=null;
				strAssignTo=null;
			}
			try
			{//try start
					connection=DBConnection.createConnection();
					preparedStatement = connection.prepareStatement(VIMSQueryInterface.getDateSQL);
					resultset = preparedStatement.executeQuery();
					
					while (resultset.next())
					{//while start
						strCurrentDate = resultset.getString(1);
					}//while end
					resultset.close();
					preparedStatement.close();
					
					preparedStatement = connection.prepareStatement(VIMSQueryInterface.getIssueStatusIDSQL);
					preparedStatement.setString(1,strIssuesID);
					resultset=preparedStatement.executeQuery();
					
					while(resultset.next())
					{//while start
						resultsetIssueStatusID=resultset.getString(1);
					}//while end
					if(resultsetIssueStatusID.equals(0))
					{//if start
						intIssueStatusID=1;
					}//if end
					else
					{//else start
						intIssueStatusID=Integer.parseInt(resultsetIssueStatusID)+1;
					}//else end
					resultset.close();
					preparedStatement.close();
					
					preparedStatement=connection.prepareStatement("SELECT assigned_by from vims_user.Incident_Status (NOLOCK)WHERE Inct_Statusid = (SELECT MAX(Inct_StatusID) from vims_user.Incident_Status (NOLOCK) WHERE Incident_ID =? AND Assigned_To=?) AND Incident_ID =? AND Assigned_To=?");
					preparedStatement.setString(1,strIssuesID);
					preparedStatement.setString(2,strAssignTo);
					preparedStatement.setString(3,strIssuesID);
					preparedStatement.setString(4,strAssignTo);
					
					resultset=preparedStatement.executeQuery();
					while(resultset.next())
					{
						AsignedBy=resultset.getString(1);
						System.out.println("==========Assigned By============"+AsignedBy);
					}
					preparedStatement.close();
					resultset.close();
					
					preparedStatement=connection.prepareStatement(VIMSQueryInterface.ChangeIssueStatusSQL);
					preparedStatement.setString(1,strIssuesID);
					preparedStatement.setInt(2,intIssueStatusID);
					preparedStatement.setString(3,AsignedBy);
					preparedStatement.setString(4,strCurrentDate);
					System.out.println("========strCurrentDate====Internal/Client======="+strCurrentDate);
					preparedStatement.setString(5,strAssignTo);
					//System.out.println("------------strAssignTo in User or Customer----------"+strAssignTo);
					preparedStatement.setString(6,strIssueStatusFrom);
					preparedStatement.setString(7,strIssuesStatusTo);
					System.out.println("========strCurrentDate====Internal/Client======="+strCurrentDate);
					preparedStatement.setString(8,strCurrentDate);
					preparedStatement.setString(9,strReason);
					
					intResponse1=preparedStatement.executeUpdate();
					
					preparedStatement.close();
					
					if(intResponse1==1)
					{//if start
						preparedStatement=connection.prepareStatement(VIMSQueryInterface.Incident_Table_StatusChangeSQL);
						preparedStatement.setString(1,strIssuesStatusTo);
						preparedStatement.setString(2,strIssuesID);
						
						intResponse2=preparedStatement.executeUpdate();
						
						preparedStatement.close();
					}//if end
					if(intResponse2==0)
					{//if start
						intResult=1;						
					}//if end
					else
					{//else start
						intResult=0;
					}//else end
				} //try end
			
		catch (Exception e)
		{//catch start
			logger.error(e);
		}//catch end
			
       }//else if end
		return intResult;		
	}	//function ChangeStatus end
	 /**
	 * @param request
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request) 
	{	  //function  getContextPath start
		
	   			String strPath=null;
	   			ArrayList IssuesList= new ArrayList();
				try 
				   {	//try start			  
					  strPath=request.getServerName();	
					  strPath="http://"+strPath;
					  strPath=strPath+":"+request.getServerPort();
					  strPath=strPath+request.getContextPath();
					  return strPath;    
				   } //try end
			  catch(Exception exception)
				 {	//catch start			
					logger.error(exception); 
					 return strPath;
				 }//catch end
			 
		 }//function getContextPath end

	 /**
	 * @param strContextPath
	 * @param strIssuesID
	 * @param strAssignTo
	 * @param strComments
	 * @param strGroupSelected
	 * @param strAssignedBy
	 * @param strSeverity
	 * @param strApplicationName
	 * @param strIssueTitle 
	 * @return
	 */
	public static boolean sendMail(String strContextPath, String strIssuesID, String strAssignTo, String strComments, String strGroupSelected, String strAssignedBy, String strSeverity, String strApplicationName, String strIssueTitle)
	{//function sendMail start
		
			String strSupervisor = null;
			String strMailid = null;
			String strSender=null;
			String strAssignedEmployee=null;
			String strAction="assign";
			String strSupervisorName=null;
			String strEmployeeName=null;
			ArrayList IssuesList= new ArrayList();
			String strAssigneeID=null;
			String strPriority=null;
			
			boolean MailResponse = false;
			
			try
			{// try start
				
				connection=DBConnection.createConnection();
				preparedStatement=connection.prepareStatement("select user_nm,work_email_address from vims_user.employee where user_nm='"+strAssignedBy+"'");
				resultset = preparedStatement.executeQuery();
				
				while (resultset.next())
				{//while start
					strAssigneeID=resultset.getString(1);
					strSender=resultset.getString(2);
				}//while end
				resultset.close();
				preparedStatement.close();
				
				preparedStatement=connection.prepareStatement("select user_nm,work_email_address from vims_user.employee where user_nm='"+strAssignTo+"'");
				resultset = preparedStatement.executeQuery();
				while(resultset.next())
				{//while start
					strEmployeeName=resultset.getString(1);
					strAssignedEmployee=resultset.getString(2);
				}//while end
				resultset.close();
				preparedStatement.close();
				
				preparedStatement=connection.prepareStatement("select incident_priority from vims_user.incident where incident_id='"+strIssuesID+"'");
				resultset = preparedStatement.executeQuery();
				while(resultset.next())
				{//while start
					strPriority=resultset.getString(1);
				}//while end
				resultset.close();
				preparedStatement.close();
			} //try end
			catch (SQLException e)
			{//catch start
				logger.error(e);
			}//catch end
			finally
			{//finally start
				DBConnection.closeConnection();
			}//finally end
			
			if(strMailid==null && "".equalsIgnoreCase(strMailid)&& strSender==null&& "".equalsIgnoreCase(strSender)&& strAssignedEmployee==null && "".equalsIgnoreCase(strAssignedEmployee))
			{//if start
				MailResponse=false;
				return MailResponse;
			}//if end
			else
			{//else start
		        HashMap details=new HashMap();
		        details.put("reciever",strAssignedEmployee);
		        //System.out.println("-----------strAssignedEmployee in admin--------"+strAssignedEmployee);
		        details.put("sender", "vims@vertexcs.com");
		        //System.out.println("-----------strSender in admin--------"+strSender);
		        details.put("name", strEmployeeName);
		        details.put("severity",strSeverity);
				details.put("priority",strPriority);
				details.put("applicationname", strApplicationName);
				details.put("ID",strIssuesID);
				details.put("assignto",strAssignTo);
				details.put("description",strComments);
				details.put("title",strIssueTitle);
				details.put("applicationName", strApplicationName);
		        MailResponse=VIMSMail.sendMail(details,strContextPath,strAction);
		        if(MailResponse==true)
		        {
		        	String Mailed="true";
		        	String strMailedDate = null;
		        	String strSenderID;
		        	String strReceiverID;
		        	
		        	int intReturnValue;
		        	try
		        	{
						connection = DBConnection.createConnection();
						preparedStatement = connection.prepareStatement(VIMSQueryInterface.getDateSQL);
						resultset = preparedStatement.executeQuery();
						while (resultset.next())
						{//while start
							strMailedDate = resultset.getString(1);
						}//while end
						if(resultset!=null)
						{
						 resultset.close();
						}
						if(preparedStatement!=null)
						{
						 preparedStatement.close();
						}
						strSenderID=strSender;
						strReceiverID=strAssignedEmployee;
						
						callableStatement=connection.prepareCall("{?=CALL vims_user.vims_user.USP_Save_Issue_Mail_Sts(?,?,?,?,?,?)}");
						callableStatement.registerOutParameter(1,Types.OTHER);
						callableStatement.setString(2,strIssuesID);
						callableStatement.setString(3,Mailed);
						callableStatement.setString(4,strMailedDate);
						callableStatement.setString(5,strSenderID);
						callableStatement.setString(6,strReceiverID);
						callableStatement.setString(7,null);
						callableStatement.execute();
						
						intReturnValue=callableStatement.getInt(1);
						
						if(callableStatement!=null)
						{
						callableStatement.close();
						}
						
					}
		        	catch (Exception e)
		        	{
						logger.error(e);
					}
		        	finally
		        	{
		        		DBConnection.closeConnection();
		        	}
		        }
		        return MailResponse;
			}  //else end
	}//function sendMail end

		/**
		 * @param strContextPath
		 * @param strIssuesID
		 * @param strApplicationName
		 * @param strIssueStatusFrom
		 * @param strIssuesStatusTo
		 * @param strReason
		 * @param strAssignedBy
		 * @param strUserID
		 * @param strRoleType
		 * @param strIssueTitle 
		 * @param strPostedBy 
		 * @return
		 */
		public static boolean changeStatusSendMail(String strContextPath,String strIssuesID, String strApplicationName,String strIssueStatusFrom, String strIssuesStatusTo,String strReason, String strAssignedBy,String strUserID, String strRoleType, String strIssueTitle, String strPostedBy)
		{	//function changeStatusSendMail start
			
			ArrayList IssuesList= new ArrayList();
			String strSupervisor = null;
			String strMailid = null;
			String strGroupID = null;
			String strSupervisorName = null;
			String strReceiverEmail = null;
			String strApplicationID = null;
			String strSenderEmail=null;
			String strEmployeeEmail=null;
			String strEmployeeWorkedName=null;
			String strAction="changestatus";
			String strAssigneeID=null;
			
			String strApplicationOwnerMailID=null;
			String strGroupSupervisorMailID=null;
			String strApplicationOwnerName=null;
			
			String strIssueStatusID=null;
			String strReceiverID=null;
			String strSenderID=null;
			
			boolean MailResponse = true;
		//System.out.println("------------strIssuesID in changeStatusSendMail---------------"+strIssuesID);
			
		if(strRoleType.equals("User") && (strIssuesStatusTo.equalsIgnoreCase("Completed")|| strIssuesStatusTo.equalsIgnoreCase("Rejected")))
		   {	//if start
			  try
				{//try start
				  String strSql = null;
				  int intTemp=0;
				    //System.out.println("---------------In strRoleType User-------------");
				    connection=DBConnection.createConnection();
				    
				    preparedStatement = connection.prepareStatement(VIMSQueryInterface.getIssueStatusIDSQL);
					preparedStatement.setString(1,strIssuesID);
					resultset=preparedStatement.executeQuery();
					
					while(resultset.next())
					{//while start
						strIssueStatusID=resultset.getString(1);
						System.out.println("========Status ID========="+strIssueStatusID);
					}//while end
					
				    if(strIssuesStatusTo.equalsIgnoreCase("Completed"))
				     {
				    	/*if(strPostedBy.equalsIgnoreCase("Customer"))
				    	{
				    	 strSql="Select app.App_Owner_Email,app.app_owner from vims_user.Application app INNER JOIN Incident inc ON app.Application_ID = inc.Application_ID WHERE inc.Incident_ID ='"+strIssuesID+"'";
				    	 statement= connection.createStatement();
						 resultset = statement.executeQuery(strSql);
						 //System.out.println("------------Out of Query------------");
						 while (resultset.next())
						 {//while start
							strApplicationOwnerMailID = resultset.getString(1);
							System.out.println("------------strApplicationOwnerMailID----------"+strApplicationOwnerMailID);
							strApplicationOwnerName=resultset.getString(2);
							System.out.println("------------strApplicationOwnerName----------"+strApplicationOwnerName);
							
							strReceiverID=strApplicationOwnerMailID;
				         }

						 //strSql="SELECT DISTINCT e.WORK_EMAIL_ADDRESS,e.First_Name+' '+e.Last_Name AS Name from vims_user.EMPLOYEE e1 INNER JOIN Incident_Status incs ON e1.User_NM = incs.Assigned_To INNER JOIN Incident inc ON incs.Incident_ID = inc.Incident_Id INNER JOIN Group_Application ga ON inc.Application_ID = ga.Application_ID INNER JOIN SUPP_CENTER_GROUP c ON ga.USRGROUP_ID =c.USRGROUP_ID INNER JOIN Employee e ON c.GROUP_SUPERVISOR =e.User_NM WHERE e1.User_NM='"+strUserID+"' AND incs.Incident_ID='"+strIssuesID+"'";

				    	} //if end*/
						 strSql="SELECT DISTINCT e.WORK_EMAIL_ADDRESS,e.First_Name+' '+e.Last_Name AS Name from vims_user.EMPLOYEE e1 INNER join vims_user.Incident_Status incs ON e1.User_NM = incs.Assigned_To INNER join vims_user.Incident inc ON incs.Incident_ID = inc.Incident_Id INNER join vims_user.Group_Application ga ON inc.Application_ID = ga.Application_ID INNER join vims_user.SUPP_CENTER_GROUP c ON ga.USRGROUP_ID =c.USRGROUP_ID INNER join vims_user.Employee e ON c.GROUP_SUPERVISOR =e.User_NM WHERE e1.User_NM='"+strUserID+"' AND incs.Incident_ID='"+strIssuesID+"'";

						 statement= connection.createStatement();
						 resultset = statement.executeQuery(strSql);
						 //System.out.println("------------Out of Query------------");
						 while (resultset.next())
						 {//while start
							strGroupSupervisorMailID = resultset.getString(1);
							System.out.println("------------strGroupSupervisorMailID----------"+strGroupSupervisorMailID);
							strSupervisorName=resultset.getString(2);
							System.out.println("------------strSupervisorName----------"+strSupervisorName);
				         }
				     }
				    if(strIssuesStatusTo.equalsIgnoreCase("Rejected"))
				     {
				      strSql="SELECT DISTINCT e.WORK_EMAIL_ADDRESS,e.First_Name+' '+e.Last_Name AS Name from vims_user.EMPLOYEE e1 INNER join vims_user.Incident_Status incs ON e1.User_NM = incs.Assigned_To INNER join vims_user.Incident inc ON incs.Incident_ID = inc.Incident_Id INNER join vims_user.Group_Application ga ON inc.Application_ID = ga.Application_ID INNER join vims_user.SUPP_CENTER_GROUP c ON ga.USRGROUP_ID =c.USRGROUP_ID INNER join vims_user.Employee e ON c.GROUP_SUPERVISOR =e.User_NM WHERE e1.User_NM='"+strUserID+"' AND incs.Incident_ID='"+strIssuesID+"'";
				      statement= connection.createStatement();
						 resultset = statement.executeQuery(strSql);
						 //System.out.println("------------Out of Query------------");
						 while (resultset.next())
						 {//while start
							 strGroupSupervisorMailID = resultset.getString(1);
							System.out.println("------------strGroupSupervisorMailID----------"+strGroupSupervisorMailID);
							strSupervisorName=resultset.getString(2);
							System.out.println("------------strSupervisorName----------"+strSupervisorName);
							
							strReceiverID=strGroupSupervisorMailID;
				         }
				     }
					//System.out.println("------------Before Query------------");
				     /*if(strIssuesStatusTo.equalsIgnoreCase("Completed"))
				     {
				    	 strSql="Select app.App_Owner_Email From Application app INNER JOIN Incident inc ON app.Application_ID = inc.Application_ID WHERE inc.Incident_ID ='"+strIssuesID+"'";
				     }
				     if(strIssuesStatusTo.equalsIgnoreCase("Rejected"))
				     {
				      strSql="SELECT DISTINCT e.WORK_EMAIL_ADDRESS,e.User_NM FROM EMPLOYEE e1 INNER JOIN Incident_Status incs ON e1.User_NM = incs.Assigned_To INNER JOIN Incident inc ON incs.Incident_ID = inc.Incident_Id INNER JOIN Group_Application ga ON inc.Application_ID = ga.Application_ID INNER JOIN SUPP_CENTER_GROUP c ON ga.USRGROUP_ID =c.USRGROUP_ID INNER JOIN Employee e ON c.GROUP_SUPERVISOR =e.User_NM WHERE e1.User_NM='"+strUserID+"' AND incs.Incident_ID='"+strIssuesID+"'";			    
				     }
				      //String strSql="SELECT DISTINCT e.WORK_EMAIL_ADDRESS,e.FIRST_NAME+' '+ISNULL(e.MIDDLE_INITIAL,'')+' '+ISNULL(e.LAST_NAME,'') AS [Employee_Name] FROM EMPLOYEE e INNER JOIN SUPP_CENTER_GROUP c ON e.User_NM=c.GROUP_SUPERVISOR INNER JOIN GROUP_MEMBERS m ON c.USRGROUP_ID=m.USRGROUP_ID INNER JOIN EMPLOYEE e1 ON m.User_NM=e1.User_NM WHERE e1.User_NM='"+strUserID+"'";
					 statement= connection.createStatement();
					 resultset = statement.executeQuery(strSql);
					 //System.out.println("------------Out of Query------------");
					while (resultset.next())
					{//while start
						strReceiverEmail = resultset.getString(1);
						System.out.println("------------strReceiverEmail----------"+strReceiverEmail);
						strSupervisorName=resultset.getString(2);
						System.out.println("------------strSupervisorName----------"+strSupervisorName);
						
					} //while end*/
					
					String strSqlUser="select work_email_address from vims_user.employee where User_NM='"+strUserID+"'";
					//System.out.println("---------------strSqlUser----------"+strSqlUser);
					statement= connection.createStatement();
					resultset = statement.executeQuery(strSqlUser);
						
						while (resultset.next())
						{//while start
							strSenderEmail = resultset.getString(1);
							strSenderID=strSenderEmail;
							//System.out.println("-----------strSenderEmail----------"+strSenderEmail);
						} //while end
						if(strApplicationOwnerMailID==null && "".equalsIgnoreCase(strApplicationOwnerMailID) && strGroupSupervisorMailID==null && "".equalsIgnoreCase(strGroupSupervisorMailID)&&strSenderEmail==null && "".equalsIgnoreCase(strSenderEmail) )
						{//if start
							MailResponse=false;
							return MailResponse;
						} //if end
						else if(strIssuesStatusTo.equalsIgnoreCase("Completed"))
						{//else start
					   HashMap details1=new HashMap();
					   HashMap details2=new HashMap();
					   /*if(strPostedBy.equalsIgnoreCase("Customer"))
					   {
					   details1.put("reciever",strApplicationOwnerMailID);
					   //System.out.println("---------------strReceiverEmail in details1 user----------"+strReceiverEmail);
					   details1.put("sender", strSenderEmail);
					  //System.out.println("---------------strSenderEmail in details1 user----------"+strSenderEmail);
					   details1.put("person", "Internal");
					   details1.put("name",strApplicationOwnerName);
				       details1.put("status",strIssuesStatusTo);
				       details1.put("ID",strIssuesID);
				       details1.put("description",strReason);
				       details1.put("title",strIssueTitle);
				       details1.put("applicationName", strApplicationName);
				       System.out.println("--------title when mail sent to application owner-------"+strIssueTitle);
				       
				       MailResponse=VIMSMail.sendMail(details1,strContextPath, strAction);
				       
				       if(MailResponse==true)
				       {
				    	   intTemp=1;
				        }
					   }*/
				       
				       details2.put("reciever",strGroupSupervisorMailID);
					   //System.out.println("---------------strReceiverEmail in details1 user----------"+strReceiverEmail);
					   
				       //details2.put("sender", strSenderEmail);
				       details2.put("sender", "VIMS@vertexcs.com");
					  //System.out.println("---------------strSenderEmail in details1 user----------"+strSenderEmail);
					   details2.put("person", "Internal");
					   details2.put("name",strSupervisorName+",Group Supervisor");
				       details2.put("status",strIssuesStatusTo);
				       details2.put("ID",strIssuesID);
				       details2.put("description",strReason);
				       details2.put("title",strIssueTitle);
				       details2.put("applicationName", strApplicationName);
				       System.out.println("--------title when mail sent to supervisor-------"+strIssueTitle);
				       
				       MailResponse=VIMSMail.sendMail(details2,strContextPath, strAction);
				       
				       if((MailResponse==true))
				       {
				    	   System.out.println("========Before DATABASE============");
				    	   //int intResponse=SaveMailDetails(strIssuesID,strIssueStatusID,strSenderEmail,strApplicationOwnerMailID+","+strGroupSupervisorMailID);
				    	   int intResponse=SaveMailDetails(strIssuesID,strIssueStatusID,strSenderEmail,strGroupSupervisorMailID);
				       }
					} //else end
						else if(strIssuesStatusTo.equalsIgnoreCase("Rejected"))
						{//else start
							 HashMap details1=new HashMap();
							   details1.put("reciever",strGroupSupervisorMailID);
							   //System.out.println("---------------strReceiverEmail in details1 user----------"+strReceiverEmail);
							   
							   //details1.put("sender", strSenderEmail);
							   details1.put("sender", "VIMS@vertexcs.com");
							  //System.out.println("---------------strSenderEmail in details1 user----------"+strSenderEmail);
							   details1.put("person", "Internal");
							   details1.put("name",strSupervisorName+",Group Supervisor");
						       details1.put("status",strIssuesStatusTo);
						       details1.put("ID",strIssuesID);
						       details1.put("description",strReason);
						       details1.put("title",strIssueTitle);
						       details1.put("applicationName", strApplicationName);
						       MailResponse=VIMSMail.sendMail(details1,strContextPath, strAction);
						       if(MailResponse==true)
						       {
						    	   int intResponse=SaveMailDetails(strIssuesID,strIssueStatusID,strSenderEmail,strGroupSupervisorMailID);
						       }
						}
				} //try end
			   catch (SQLException e)
				 {//catch start
					logger.error(e);
				 }//catch end
			 } //if end
			 else if(strRoleType.equals("Customer"))
			   {//else if start
				
					String strSupervisorEmail=null;
					String strSupportManagerEmail=null;
					String strSupportManagerName=null;
					ArrayList arrayList=null;
					arrayList=new ArrayList();
				 try
					{//try start
						connection = DBConnection.createConnection();
						
						preparedStatement = connection.prepareStatement(VIMSQueryInterface.getIssueStatusIDSQL);
						preparedStatement.setString(1,strIssuesID);
						resultset=preparedStatement.executeQuery();
						
						while(resultset.next())
						{//while start
							strIssueStatusID=resultset.getString(1);
							System.out.println("========Status ID========="+strIssueStatusID);
						}//while end
						
						//preparedStatement = connection.prepareStatement(VIMSQueryInterface.getSupervisormailidSQL);
						//preparedStatement.setString(1,strIssuesID);
						//resultset = preparedStatement.executeQuery();
						//while(resultset.next())
						//{//while start
							//strReceiverEmail=resultset.getString(2);
							//System.out.println("-----------------strReceiverEmail----------"+strReceiverEmail);
							//strSupervisorName=resultset.getString(3);
							//System.out.println("-----------------strSupervisorName----------"+strSupervisorName);
						//}//while end
						//resultset.close();
						//preparedStatement.close();
						
						 String strSql="select ISNULL(max(ist.inct_statusid),'') as Inct_statusid,e.WORK_EMAIL_ADDRESS,e.FIRST_NAME+' '+ISNULL(e.MIDDLE_INITIAL,'')+' '+ISNULL(e.LAST_NAME,'') AS [Supervisor_Name],sm.Work_Email_Address,sm.FIRST_NAME+' '+ISNULL(sm.MIDDLE_INITIAL,'')+' '+ISNULL(sm.LAST_NAME,'') AS [Manager_Name] from vims_user.incident_status ist INNER join vims_user.INCIDENT i ON i.INCIDENT_ID=ist.INCIDENT_ID INNER join vims_user.GROUP_APPLICATION ga ON i.APPLICATION_ID=ga.APPLICATION_ID INNER join vims_user.SUPP_CENTER_GROUP scg ON ga.USRGROUP_ID=scg.USRGROUP_ID INNER join vims_user.EMPLOYEE e ON scg.GROUP_SUPERVISOR=e.User_NM INNER join vims_user.SUPPORT_CNTR_MANAGER scm ON scg.SUPPORT_CENTER_ID =scm.SUPPORT_CENTER_ID INNER join vims_user.Employee sm ON scm.SUP_CNTR_MANAGER	=	sm.User_NM  WHERE ist.incident_id='"+strIssuesID+"' GROUP BY e.WORK_EMAIL_ADDRESS,e.User_NM,e.FIRST_NAME,e.MIDDLE_INITIAL,e.LAST_NAME,sm.WORK_EMAIL_ADDRESS,sm.FIRST_NAME,sm.MIDDLE_INITIAL,sm.LAST_NAME ";
						 statement= connection.createStatement();
						 resultset = statement.executeQuery(strSql);
						 
						 	while (resultset.next())
							{//while start
								
								strSupervisorEmail=resultset.getString(2);
							      //System.out.println("-----------strSupervisorEmail----------"+strSupervisorEmail);
								
								strSupervisorName=resultset.getString(3);
								  //System.out.println("-----------strSupervisorName----------"+strSupervisorName);
									
								strSupportManagerEmail=resultset.getString(4);
								  //System.out.println("-----------strSupportManagerEmail----------"+strSupportManagerEmail);
									
								strSupportManagerName=resultset.getString(5);
								  //System.out.println("-----------strSupportManagerName----------"+strSupportManagerName);
							}			
						 	strSenderEmail=strUserID;
						/*preparedStatement=connection.prepareStatement("select App_Owner_Email from Application where APPLICATION_ID='"+strUserID+"'");
						resultset = preparedStatement.executeQuery();
						while(resultset.next())
						{//while start
							strSenderEmail=resultset.getString(1);
							//System.out.println("----------strSenderEmail---------"+strSenderEmail);
						}//while end*/
						if(strSupervisorEmail==null && "".equalsIgnoreCase(strSupervisorEmail)&& strSenderEmail==null && "".equalsIgnoreCase(strSenderEmail)&& strSupportManagerEmail==null && "".equalsIgnoreCase(strSupportManagerEmail) )
						{//if start
							MailResponse=false;
							return MailResponse;
						}//if end
						else
						{//else start
					       HashMap details1=new HashMap();
					        
					       int intTemp = 0;
						   details1.put("reciever",strSupervisorEmail);
						   //System.out.println("---------------strSupervisorEmail in details1 customer----------"+strSupervisorEmail);
						   
						   //details1.put("sender", strSenderEmail);
						   details1.put("sender", "VIMS@vertexcs.com");
						   //System.out.println("---------------strSenderEmail in details1 customer----------"+strSenderEmail);
						   details1.put("person", "Customer");
						   details1.put("name",strSupervisorName+",Group Supervisor");
					       details1.put("status",strIssuesStatusTo);
					       details1.put("ID",strIssuesID);
					       details1.put("description",strReason);
					       details1.put("title",strIssueTitle);
					       details1.put("applicationName", strApplicationName);
					       MailResponse=VIMSMail.sendMail(details1,strContextPath, strAction);
					       
					       if(MailResponse==true)
					       {
					    	   intTemp=1;  
					       }
					       HashMap details2=new HashMap();
					       details2.put("reciever",strSupportManagerEmail);
					       //System.out.println("---------------strSupportManagerEmail in details2 customer----------"+strSupportManagerEmail);
					       
					       //details2.put("sender", strSenderEmail);
					       details2.put("sender", "VIMS@vertexcs.com");
					       //System.out.println("---------------strSenderEmail in details2 customer----------"+strSenderEmail);
					       details2.put("person", "Customer");
					       details2.put("name",strSupportManagerName+",Support Manager");
					       details2.put("status",strIssuesStatusTo);
					       details2.put("ID",strIssuesID);
					       details2.put("description",strReason);
					       details2.put("title",strIssueTitle);
					       details2.put("applicationName", strApplicationName);
					       MailResponse=VIMSMail.sendMail(details2,strContextPath, strAction);
					       
					       if((MailResponse==true)&&(intTemp==1))
					       {
					    	   int intResponse=SaveMailDetails(strIssuesID,strIssueStatusID,strSenderEmail,strSupervisorEmail+","+strSupportManagerEmail);  
					       }
						}//else end
					}//try end
				 catch (SQLException e)
				 {//catch start
					logger.error(e);
				 }//catch end
			   }
			 else
			 { 
				 MailResponse=false;
			   //else if end
			 }
		return MailResponse;
			}//function changeStatusSendMail end

		/**
		 * @param strIssuesID
		 * @param filePath
		 * @throws IOException
		 */
		public static void writeDataToFile(String strIssuesID, String filePath) throws IOException
		{  // function writeDataToFile start
			String strFileName = null;
			String FileData;
			String strHeader;
			//String strDetails1[] = null;
			String strDetails2[] = null;
			
			ArrayList IssueDetails1=new ArrayList();
			ArrayList IssueDetails2=new ArrayList();
			
			int i=0;
			int j=1;
			try
			{	//try start
				connection = DBConnection.createConnection();
				preparedStatement = connection.prepareStatement(VIMSQueryInterface.IssueDetails2);
				preparedStatement.setString(1,strIssuesID);
				resultset = preparedStatement.executeQuery();
				while (resultset.next())
				{   //while start
					
					i=0;
					String strDetails1[]=new String[5];
					strDetails1[i++]=resultset.getString(1);
					strDetails1[i++]=resultset.getString(3);
					strDetails1[i++]=resultset.getString(4);
					strDetails1[i++]=resultset.getString(5);
					strDetails1[i++]=resultset.getString(7);
					
					IssueDetails1.add(strDetails1);
				}//while end
			
				preparedStatement = connection.prepareStatement(VIMSQueryInterface.IssueDetails3);
				preparedStatement.setString(1,strIssuesID);
				resultset = preparedStatement.executeQuery();
				while(resultset.next())
				{//while start
					IssueDetails2.add(resultset.getString(1));
					IssueDetails2.add(resultset.getString(2));
					IssueDetails2.add(resultset.getString(3));
					IssueDetails2.add(resultset.getString(4));
					IssueDetails2.add(resultset.getString(5));
					IssueDetails2.add(resultset.getString(6));
				}//while end
				 preparedStatement.close();
				 resultset.close();
			} //try end
			catch (Exception e)
			{//catch start
				logger.error(e);
			}//catch end
					
			strFileName=strIssuesID+".doc";
			strHeader="AssignedBy	StatusFrom	StatusTo	OnDate			     Comments";
			String strDivider="======================================================================================";
			
			strHeader=strHeader+"\n";
			strDivider=strDivider+"\n";
			if(!strFileName.equals(""))
			{//if start
				 File filetoCreate=new File(filePath,strFileName);
				 if(!filetoCreate.exists())
				 {//if start
					 try
					 {//try start
						FileOutputStream fileoutputstream=new FileOutputStream(filetoCreate);
						fileoutputstream.write(strHeader.getBytes());
						fileoutputstream.write(strDivider.getBytes());
						for(i=0;i<IssueDetails1.size();i++)
						{//for loop start
							String temp[]=(String []) IssueDetails1.get(i);
							
							String tempStr=new String();
							for(j=0;j<temp.length;j++)
							{//for loop start
								
								   tempStr=tempStr+temp[j]+"	           ";
							}//for loop end
							  tempStr=tempStr+"\n";
							fileoutputstream.write(tempStr.getBytes()); 
						}//for loop end
						fileoutputstream.close();    
					 } //try end
					 catch (FileNotFoundException e)
					 {//catch start
						logger.error(e);
					}//catch end
					 
				 }//if end
			}//if end
			
		}//function writeDataToFile end
		
		/**
		 * @param strDate
		 * @return
		 * @throws Exception
		 */
		public static String ConvertDate(String strDate) throws Exception
		{//function ConvertDate start
			 Calendar calendar = Calendar.getInstance () ; 
			 DateFormat dateFormat = new SimpleDateFormat ( "MM/dd/yyyy" ) ;
			//String str="11/20/2008 17:00:37";
			 Date date = dateFormat.parse (strDate) ;     
			 calendar.setTime ( date ) ; 
			 String strConvertedDate=dateFormat.format (calendar.getTime ());
			 //returning ConvertedDate
			 return strConvertedDate;
			
		}//function ConvertDate end
		
		/**
		 * @return
		 */
		public static HashMap getCountofNewIssuesAddedDAO(String strLoginUser)
		{//function getCountofNewIssuesAddedDAO start
			   
		    ArrayList openIssueList=null;
		    ArrayList closedIssueList=null;
		    ArrayList assignedIssueList=null;
		    ArrayList rejectedIssueList=null;
		    ArrayList escalatedIssueList=null;
		    ArrayList completedIssueList=null;
		    ArrayList reOpenIssueList=null;
		    ArrayList acceptedIssueList=null;
		    ArrayList list=null;
		    
		    HashMap hashMap=null;
		    HashMap hashMap1=null;
		    HashMap tempRecord=null;
		    
		    int recordCount=0;
		    int differenceInDates;
		    int openIssueCount=0;
		    int closedIssueCount=0;
		    int escalatedIssueCount=0;
		    int assignedIssueCount=0;
		    int rejectedIssueCount=0;
		    int reOpenIssueCount=0;
		    int completedIssueCount=0;
		    int acceptedIssueCount=0;
		    
		    String strPostedDate=null;
		    String strSqlQuery=null;
		    
		   
		try
		{//try start
			   hashMap=new HashMap();
			   hashMap1=new HashMap();
			   
			   openIssueList=new ArrayList();
			   closedIssueList=new ArrayList();
			   assignedIssueList=new ArrayList();
			   rejectedIssueList=new ArrayList();
			   escalatedIssueList=new ArrayList();
			   completedIssueList=new ArrayList();
			   reOpenIssueList=new ArrayList();
			   acceptedIssueList=new ArrayList();
			   
			   
			   strSqlQuery="SELECT DISTINCT i.incident_id as incidentId,a.application_name as applName,i.incident_title as title,convert(varchar,i.incident_posted_date,101)+' '+convert(varchar,i.incident_posted_date,108) as postedDate,i1.incident_type_desc as applDesc,convert(varchar,i.due_date,101)+' '+convert(varchar,i.due_date,108) as dueDate,i.inct_status as issueStatus,convert(varchar,i.closed_date,101)+' '+convert(varchar,i.closed_date,108) as closedDate,i.view_fg as flagValue from vims_user.incident i INNER join vims_user.application a ON i.application_id=a.application_id INNER join vims_user.incident_type i1 ON i.incident_type=i1.incident_type WHERE i.view_fg=0";
			   connection=DBConnection.createConnection();
			   //statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			   CallableStatement callableStatement=connection.prepareCall("{ CALL vims_user.USP_Get_Issue_View(?)}");
			   CallableStatement callableStatement1=connection.prepareCall("{ CALL vims_user.USP_Get_Issue_View(?)}");
			   
			   callableStatement.setString(1,strLoginUser);
			   callableStatement1.setString(1,strLoginUser);
			   
		        resultset=callableStatement.executeQuery();
		        ResultSet resultSet1=callableStatement1.executeQuery();
			   if(resultSet1!=null)
			   {//if start
				   while(resultSet1.next())
				   {//while start
        			   recordCount++;
			      }//while end
		      // resultset.beforeFirst();
				   System.out.println("========recordCount================"+recordCount);
			    if(recordCount>0)
			    {
				   while(resultset.next()) {
					   System.out.println("===entered into while loop==============");
					   strPostedDate=resultset.getString(4);
					   strPostedDate=ConvertDate(strPostedDate);
				       //differenceInDates=getDifferenceInDates(strPostedDate);
					//if(differenceInDates<=15) {
					     tempRecord=new HashMap();
						 String linkView="<a href='./ListofIssues.do?methodname=IssueDetails&id="+resultset.getString("incidentId")+"'>"+resultset.getString("incidentId")+"</a>";
						 tempRecord.put("incident_id",linkView);
						 tempRecord.put("application_name",resultset.getString("applName"));
						 tempRecord.put("incident_title",resultset.getString("title"));
						 tempRecord.put("incident_posted_date",strPostedDate);
						 tempRecord.put("incident_type_desc",resultset.getString("applDesc"));
						 tempRecord.put("due_date",resultset.getString("dueDate"));
						 tempRecord.put("inct_status",resultset.getString("issueStatus"));
						 tempRecord.put("closed_date",resultset.getString("closedDate"));
						 
						 
						 String strIssueStatus=resultset.getString(7);
						 System.out.println("----------------strIssueStatus-------------"+strIssueStatus);
						 
					     if(strIssueStatus.equalsIgnoreCase("Open")) {
						     openIssueList.add(tempRecord);				 
							 openIssueCount++;
						 }		 
						 if(strIssueStatus.equalsIgnoreCase("Closed")) {
							 closedIssueList.add(tempRecord);
							 closedIssueCount++;
						 }	 
						 if(strIssueStatus.equalsIgnoreCase("Assigned")) {
							 assignedIssueList.add(tempRecord);
							 assignedIssueCount++;
						 }	 
					     if(strIssueStatus.equalsIgnoreCase("escalated")) {
					    	 escalatedIssueList.add(tempRecord);
					    	 escalatedIssueCount++;				 
					     }
					     
					     if(strIssueStatus.equalsIgnoreCase("Rejected")) {
					    	 rejectedIssueList.add(tempRecord);
					    	 rejectedIssueCount++;				 
					     }
					     
					     if(strIssueStatus.equalsIgnoreCase("completed")) {
					    	 completedIssueList.add(tempRecord);
					    	 completedIssueCount++;				 
					     }
					     
					     if(strIssueStatus.equalsIgnoreCase("reopened")) {
					    	 reOpenIssueList.add(tempRecord);
					    	 reOpenIssueCount++;				 
					     }
					     if(strIssueStatus.equalsIgnoreCase("accepted")) {
					    	 acceptedIssueList.add(tempRecord);
					    	 acceptedIssueCount++;				 
					     }
					  } 
				     if(openIssueCount>0) {
				    	 hashMap.put("openIssueCount",openIssueCount);
				    	 hashMap1.put("openIssueCountList",openIssueList);
				     }
				     if(closedIssueCount>0) {
				    	hashMap1.put("closedIssueCountList",closedIssueList);
				    	 hashMap.put("closedIssueCount",closedIssueCount);
				     }	 
				     if(assignedIssueCount>0) {
				    	 hashMap1.put("assignedIssueCountList",assignedIssueList);
				    	 hashMap.put("assignedIssueCount",assignedIssueCount);
				     }	 
				     if(escalatedIssueCount>0) {
				         hashMap1.put("escalatedIssueCountList",escalatedIssueList);
				    	 hashMap.put("escalatedIssueCount",escalatedIssueCount);
				     }
				     if(rejectedIssueCount>0) {
					    	hashMap1.put("rejectedIssueCountList",rejectedIssueList);
					    	hashMap.put("rejectedIssueCount",rejectedIssueCount);
					   }
				     if(completedIssueCount>0) {
					    	hashMap1.put("completedIssueCountList",completedIssueList);
					    	hashMap.put("completedIssueCount",completedIssueCount);
					   }
				     if(reOpenIssueCount>0) {
					    	hashMap1.put("reOpenIssueCountList",reOpenIssueList);
					    	hashMap.put("reOpenIssueCount",reOpenIssueCount);
					  }
				     if(acceptedIssueCount>0) {
					    	hashMap1.put("acceptedIssueCountList",acceptedIssueList);
					    	hashMap.put("acceptedIssueCount",acceptedIssueCount);
					  }
				     }
				}
		} catch(Exception exception) {
		   logger.error(exception);
		   System.out.println("---------Exception in getNeIssuesAdded- DAO--------"+exception);
		}
		list=new ArrayList();
		
		if(hashMap1.size()>0)
		list.add(hashMap1);
		
		if(list.size()>0)				
		hashMap.put("listSet",list);
		
		System.out.println("====hashMap----------------"+hashMap);
		return hashMap;
		
	}		         
	

		/**
		 * @param strIssuesID
		 * @return
		 */
		public static String getIssueSeverity(String strIssuesID)
		{//function getIssueSeverity start
			String strSeverity =null;
			try
			{//try start
				connection = DBConnection.createConnection();
				preparedStatement = connection.prepareStatement(VIMSQueryInterface.getIssueSeveritySQL);
				preparedStatement.setString(1, strIssuesID);
				resultset = preparedStatement.executeQuery();
				while (resultset.next())
				{//while start
					strSeverity=resultset.getString(1);	
				}//while end
			}//try end
			catch (Exception exception)
			{//catch start
				logger.equals(exception);
			}//catch end
			return strSeverity;
		}//function getIssueSeverity end

		/**
		 * @param strIssuesID
		 * @param issueAssignDetails
		 * @param strContextPath
		 */
		public static void generateIssueScheduler(String strIssuesID,HashMap issueAssignDetails, String strContextPath) 
		{ //function generateIssueScheduler start
			try
			{	//try start
				EscalatedIssueScheduler.initilizeIssueScheduler(strIssuesID,strIssuesID,issueAssignDetails,strContextPath);			
			}	//try end
			catch(JobExecutionException jobExecutionException)
			{//catch start
				jobExecutionException.printStackTrace();
			} //catch end
			catch (SchedulerException schedulerException) 
			{	//catch start		
				schedulerException.printStackTrace();
			}	//catch end	
		}//function generateIssueScheduler end

		/**
		 * @param strContextPath
		 * @param strIssuesID
		 * @param strAssignTo
		 * @param strComments
		 * @param strGroupSelected
		 * @param strAssignedBy
		 * @param strSeverity
		 * @param strApplicationId
		 * @return
		 */
		public static HashMap EscalatedMailDetailsDAO(String strContextPath,String strIssuesID, String strSeverity, String strApplicationId) 
		{//function EscalatedMailDetailsDAO start
						
			String strGroupID=null;
			String strSenderMailID=null;			
			String strSupportMgr = null;
			String strSupportMgrMailid = null;
			String strGroupMgrName=null;
			String strGroupMgrMailID=null;
			String strEmployeeName=null;
			String strAssignedEmployeeMailID=null;
			Timestamp strPostedDate=null;
			Timestamp strDueDate=null;
			Timestamp strWarningDate=null;			
			String strApplicationName=null;
			boolean SendWarningMail = false; 
			
			HashMap IssueAssignDetails=new HashMap();
			//String strAction="assign";
			try
			{//try start
				connection=DBConnection.createConnection();	
				preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Get_Grp_NM @Incident_ID=?");
				preparedStatement.setString(1,strIssuesID);
				resultset=preparedStatement.executeQuery();
				while(resultset.next())
				{//while start
					strGroupID=resultset.getString(2);					
				}//while end				
				
				
				preparedStatement=connection.prepareStatement(VIMSQueryInterface.getEscalatedIssueDetails);
				preparedStatement.setString(1,"admin");
				preparedStatement.setString(2,strGroupID);
				preparedStatement.setString(3,"");
				preparedStatement.setString(4,strIssuesID);
				resultset=preparedStatement.executeQuery();
				while(resultset.next())
				{//while start
					strSenderMailID=resultset.getString(1);
					//IssueAssignDetails.put("SenderMailID", strSenderMailID);
				}//while end
				IssueAssignDetails.put("SenderMailID", "VIMS@vertexcs.com");
				

				if(preparedStatement.getMoreResults())
				{
					resultset=preparedStatement.getResultSet();
					if(resultset!=null)
					{
						while (resultset.next())
						{								
							strGroupMgrName=resultset.getString(1);
							strGroupMgrMailID=resultset.getString(2);
							strSupportMgr = resultset.getString(3);
							strSupportMgrMailid = resultset.getString(4);
							
							IssueAssignDetails.put("SupportMgrName", strSupportMgr);
							IssueAssignDetails.put("SupportMgrMailId", strSupportMgrMailid);
							IssueAssignDetails.put("GroupManagerName", strGroupMgrName);
							IssueAssignDetails.put("GroupManagerMailId", strGroupMgrMailID);
						}
					}
				}
				if(preparedStatement.getMoreResults())
				{
					resultset=preparedStatement.getResultSet();
					if(resultset!=null)
					{
						while (resultset.next())
						{
							strEmployeeName=resultset.getString(1);
							strAssignedEmployeeMailID=resultset.getString(2);
							
							IssueAssignDetails.put("EmployeeName", strEmployeeName);
							IssueAssignDetails.put("EmployeeMailId", strAssignedEmployeeMailID);
						}
					}
				}
				if(preparedStatement.getMoreResults())
				{
					resultset=preparedStatement.getResultSet();
					if(resultset!=null)
					{
						while (resultset.next())
						{
							strPostedDate=resultset.getTimestamp(1);	
							strDueDate=resultset.getTimestamp(2);
							strWarningDate=resultset.getTimestamp(3); 
							strApplicationName=resultset.getString(4);
							
							if(resultset.getInt(5)==1)
							{						
								SendWarningMail=true;
							}
							else
							{
								SendWarningMail=false;
							}							
							
							IssueAssignDetails.put("PostedDate", strPostedDate);
							IssueAssignDetails.put("DueDate", strDueDate);
							IssueAssignDetails.put("WarningDate", strWarningDate);
							IssueAssignDetails.put("ApplicationName", strApplicationName);
							IssueAssignDetails.put("SendWarningMail", SendWarningMail);
							IssueAssignDetails.put("FormatedPostedDate", resultset.getString(6));
							IssueAssignDetails.put("FormatedDueDate", resultset.getString(7));
							IssueAssignDetails.put("FormatedWarningDate", resultset.getString(8));
							IssueAssignDetails.put("title", resultset.getString(9));
						}
					}
				}
				resultset.close();
				preparedStatement.close();
			} //try start
			catch (Exception e)
			{//catch start
				logger.error(e);
				
			}//catch end
			finally
			{//finally start
				DBConnection.closeConnection();
			}//finally end
			
			
	         System.out.println("IssueAssignDetails "+IssueAssignDetails);
			return IssueAssignDetails;
			
		}
		/**
		 * @param strIssueId
		 * @return
		 */
		public static String getIssuePostedBy(String strIssueId)
		{//function getIssuePostedBy start
			String strIssuePosted_BY = null;
			CallableStatement callableStatement;
			
			connection=DBConnection.createConnection();
			try
			{//try start
				callableStatement=connection.prepareCall("{CALL vims_user.USP_Get_ID_Type(?)}");
				callableStatement.setString(1,strIssueId);
				callableStatement.execute();
				
				resultset=callableStatement.getResultSet();
				
				while(resultset.next())
				{//while start
					strIssuePosted_BY=resultset.getString(1);
				}//while end
				callableStatement.close();
				resultset.close();
				return strIssuePosted_BY;
				
			} //try end
			catch (SQLException e)
			{//catch start
				logger.error(e);
				e.printStackTrace();
			}//catch end
			finally
			{//finally start
				DBConnection.closeConnection();
			}//finally end
			return strIssuePosted_BY;
		}//function getIssuePostedBy end

		/**
		 * @param strIssueId
		 * @return
		 */
		public static String getGroupName(String strIssueId)
		{ //function getGroupName start
			String strGroupname = null;
			try
			{//try start
				connection = DBConnection.createConnection();
				callableStatement = connection.prepareCall("{CALL vims_user.USP_Get_Grp_NM(?)}");
				callableStatement.setString(1, strIssueId);
				callableStatement.execute();
				
				resultset=callableStatement.getResultSet();
				
				while(resultset.next())
				{//while start
					strGroupname=resultset.getString(1);
				}//while end
				resultset.close();
				callableStatement.close();				
			} //try end
			catch (Exception e)
			{//catch start
				logger.error(e);
			}//catch end
			finally
			{//finally start
				DBConnection.closeConnection();
			}//finally end
			return strGroupname;
		}//function getGroupName end

		/**
		 * @param strIssueId
		 * @return
		 */
		public static ArrayList getAssignedEmployeeDetails(String strIssueId)
		{	//function getAssignedEmployeeDetails start
			ArrayList AssignedEmployeeDetails=new ArrayList();
			connection=DBConnection.createConnection();
			try
			{	//try start
				preparedStatement=connection.prepareStatement("SELECT DISTINCT inc.Assigned_To ,e.FIRST_NAME+' '+ISNULL(e.MIDDLE_INITIAL,'')+' '+ISNULL(e.LAST_NAME,'') AS [Employee_Name] from vims_user.Incident_status inc INNER join vims_user.Employee e ON inc.Assigned_To = e.User_NM WHERE inc.Inct_StatusID = (SELECT MAX(Inct_StatusID) from vims_user.Incident_Status WHERE Incident_ID= inc.Incident_ID AND Assigned_To IS NOT NULL) AND inc.Incident_ID ='"+strIssueId+"'");
				resultset=preparedStatement.executeQuery();
				while(resultset.next())
				{	//while start
					AssignedEmployeeDetails.add(resultset.getString(1));
					AssignedEmployeeDetails.add(resultset.getString(2));
				}//while end
				resultset.close();
				preparedStatement.close();
			} //try end
			catch (SQLException e)
			{//catch start
				logger.error(e);
				e.printStackTrace();
			}//catch end
			
			return AssignedEmployeeDetails;
		}	//function getAssignedEmployeeDetails end
	
		/**
		 * @param strIssueId
		 */
		public static void changeIssueFlagValueDAO(String strIssueId) 
	    {//function changeIssueFlagValueDAO start
	    	 try
	    	 {//try start
	    		     logger=Logger.getLogger("Admin");
	    		     connection=DBConnection.createConnection();
	    		     preparedStatement=connection.prepareStatement("update vims_user.incident set view_fg=1 where incident_id=?");
	    		     preparedStatement.setString(1,strIssueId);
	    		     preparedStatement.executeUpdate();
	    		     
	    		     //System.out.println("in Change Issue Flag method in DAO class after executing the query");
	    		     preparedStatement.close();
	    		     
	    		     
	    	 }//try end 
	    	 catch(Exception exception)
	    	 {//catch start
	    		 logger.error(exception);
	    	 }//catch end
	    }//function changeIssueFlagValueDAO end

		/**
		 * @param strAssignTo
		 * @return
		 */
		public static String getMailReceiver(String strAssignTo)
		{//function getMailReceiver start
			String strMailReceiver=null;
			
			try
			{//try start
				preparedStatement = connection.prepareStatement("select FIRST_NAME+' '+ISNULL(LAST_NAME,'') AS [Employee_Name] from vims_user.employee where user_nm='"+ strAssignTo + "'");
				resultset = preparedStatement.executeQuery();
				while (resultset.next())
				{//while start
					strMailReceiver = resultset.getString(1);
				}//while end
				resultset.close();
				preparedStatement.close();
			} //try end
			catch (Exception e)
			{//catch start
				logger.error(e);
			}//catch end
			return strMailReceiver;
		}//function getMailReceiver end	
		
		/**
		 * @param strUserID
		 * @param strRoleType
		 * @param strIssuesID
		 * @param strIssuesStatusTo 
		 * @return
		 */
		public static String[] getSupervisorMailReceiverDAO(String strUserID, String strRoleType, String strIssuesID, String strIssuesStatusTo)
		{//function getSupervisorMailReceiverDAO start
		
			String strSupervisorName = null;
			String strSupervisorEmail=null;
			String strSupportManagerEmail=null;
			String strSupportManagerName=null;
			
			String strApplicationOwnerName=null;
			
			String strMailReveiverNames[]=new String[2];
			try
			{//try start
				 if(strRoleType.equals("User"))
				 {	//if start
					 
					 connection=DBConnection.createConnection();
					 String strSql="SELECT DISTINCT e.FIRST_NAME+' '+ISNULL(e.MIDDLE_INITIAL,'')+' '+ISNULL(e.LAST_NAME,'') AS [Name] from vims_user.EMPLOYEE e INNER join vims_user.SUPP_CENTER_GROUP c ON e.User_NM=c.GROUP_SUPERVISOR INNER join vims_user.GROUP_MEMBERS m ON c.USRGROUP_ID=m.USRGROUP_ID INNER join vims_user.EMPLOYEE e1 ON m.User_NM=e1.User_NM INNER join vims_user.Group_Application ga ON  c.USRGROUP_ID=ga.USRGROUP_ID INNER join vims_user.Incident inc ON ga.Application_ID = inc.Application_ID WHERE e1.User_NM='"+strUserID+"' AND inc.Incident_ID ='"+strIssuesID+"'";
                
					 //String strSql="SELECT DISTINCT e.FIRST_NAME+' '+ISNULL(e.MIDDLE_INITIAL,'')+' '+ISNULL(e.LAST_NAME,'') AS [Name] FROM EMPLOYEE e INNER JOIN SUPP_CENTER_GROUP c ON e.User_NM=c.GROUP_SUPERVISOR INNER JOIN GROUP_MEMBERS m ON c.USRGROUP_ID=m.USRGROUP_ID INNER JOIN EMPLOYEE e1 ON m.User_NM=e1.User_NM WHERE e1.User_NM='"+strUserID+"'";
					 statement= connection.createStatement();
					 resultset = statement.executeQuery(strSql);
					 while (resultset.next())
					  {//while start
						 strSupervisorName=resultset.getString(1);
					  }//while end
					 
					 strMailReveiverNames[0]=strSupervisorName;
					 
					 if(strIssuesStatusTo.equalsIgnoreCase("Completed"))
					 {
					 strSql="Select app.app_owner from vims_user.Application app INNER join vims_user.Incident inc ON app.Application_ID = inc.Application_ID WHERE inc.Incident_ID ='"+strIssuesID+"'";
					 statement= connection.createStatement();
					 resultset = statement.executeQuery(strSql);
					 while (resultset.next())
					  {//while start
						 strApplicationOwnerName=resultset.getString(1);
					  }//while end
					 strMailReveiverNames[1]=strApplicationOwnerName;
					 }
					 
				    //System.out.println("-----------strSupervisorName in User----------"+strSupervisorName);
					
				 }//if end
			}//try end
				 catch (Exception e)
					{//catch start
						logger.error(e);
					}//catch end
							
					return strMailReveiverNames;
			}//function getSupervisorMailReceiverDAO end
		/**
		 * @param strUserID
		 * @param strRoleType
		 * @param strIssuesID
		 * @return
		 */
		public static ArrayList getCustomerSendMailDAO(String strUserID, String strRoleType, String strIssuesID)
		 {//function getCustomerSendMailDAO start
					
			String strSupervisorName = null;
			String strSupervisorEmail=null;
			String strSupportManagerEmail=null;
			String strSupportManagerName=null;
			ArrayList arrayList=null;
			arrayList=new ArrayList();
		try
		{	//try start
			if(strRoleType.equals("Customer"))
			 {//if start
				 //System.out.println("---------In getCustomerSendMail DAO-----------"); 
				 connection=DBConnection.createConnection();
				 String strSql="select ISNULL(max(ist.inct_statusid),'') as Inct_statusid,e.WORK_EMAIL_ADDRESS,e.FIRST_NAME+' '+ISNULL(e.MIDDLE_INITIAL,'')+' '+ISNULL(e.LAST_NAME,'') AS [Supervisor_Name],sm.Work_Email_Address,sm.FIRST_NAME+' '+ISNULL(sm.MIDDLE_INITIAL,'')+' '+ISNULL(sm.LAST_NAME,'') AS [Manager_Name] from vims_user.incident_status ist INNER join vims_user.INCIDENT i ON i.INCIDENT_ID=ist.INCIDENT_ID INNER join vims_user.GROUP_APPLICATION ga ON i.APPLICATION_ID=ga.APPLICATION_ID INNER join vims_user.SUPP_CENTER_GROUP scg ON ga.USRGROUP_ID=scg.USRGROUP_ID INNER join vims_user.EMPLOYEE e ON scg.GROUP_SUPERVISOR=e.User_NM INNER join vims_user.SUPPORT_CNTR_MANAGER scm ON scg.SUPPORT_CENTER_ID =scm.SUPPORT_CENTER_ID INNER join vims_user.Employee sm ON scm.SUP_CNTR_MANAGER	=	sm.User_NM  WHERE ist.incident_id='"+strIssuesID+"' GROUP BY e.WORK_EMAIL_ADDRESS,e.User_NM,e.FIRST_NAME,e.MIDDLE_INITIAL,e.LAST_NAME,sm.WORK_EMAIL_ADDRESS,sm.FIRST_NAME,sm.MIDDLE_INITIAL,sm.LAST_NAME ";
				 statement= connection.createStatement();
				 resultset = statement.executeQuery(strSql);
				 
				 	while (resultset.next())
					{//while start
						
						strSupervisorEmail=resultset.getString(2);
					      //System.out.println("-----------strSupervisorEmail----------"+strSupervisorEmail);
						
						strSupervisorName=resultset.getString(3);
						  //System.out.println("-----------strSupervisorName----------"+strSupervisorName);
							
						strSupportManagerEmail=resultset.getString(4);
						  //System.out.println("-----------strSupportManagerEmail----------"+strSupportManagerEmail);
							
						strSupportManagerName=resultset.getString(5);
						  //System.out.println("-----------strSupportManagerName----------"+strSupportManagerName);
								
	                    arrayList.add(0,strSupervisorEmail);
	                    arrayList.add(1,strSupervisorName);
	                    //System.out.println("-------------strSupervisorName for customer-----------"+strSupervisorName);
	                    arrayList.add(2,strSupportManagerEmail);
	                    arrayList.add(3,strSupportManagerName);
	                    //System.out.println("-------------strSupportManagerName for customer-----------"+strSupportManagerName);
		                   
					}//while end
			   }//if end
			
		   }//try end
			catch (Exception e)
			{//catch start
				logger.error(e);
			}//catch end
		//System.out.println("---------arrayList----------"+arrayList);
		return arrayList;
		}//function getCustomerSendMailDAO end
		
	 // Added by Rajashekar	
		public static ArrayList getSpecificIssueList(String strIssueType,HttpSession session) {
	    	
			switch(enumIssueType.valueOf(strIssueType)) {
  	      
	    	  case open:
	    		          return (ArrayList)session.getAttribute("openIssueCountList");
	    		           
	    	  case assigned:
	    		          return (ArrayList)session.getAttribute("assignedIssueCountList");
	    		           
	    	  case rejected:
	    		          return (ArrayList)session.getAttribute("rejectedIssueCountList");
	    		          
	    	  case accepted:
	    		          return (ArrayList)session.getAttribute("acceptedIssueCountList");
	    		          
	    	  case completed:
	    		          return (ArrayList)session.getAttribute("completedIssueCountList");
	    		          
	    	  case escalated:
	    		          return (ArrayList)session.getAttribute("escalatedIssueCountList");
	    		          
	    	  case reopened:
	    		          return (ArrayList)session.getAttribute("reOpenIssueCountList");
	    		          
	    	  case closed:
	    		          return (ArrayList)session.getAttribute("closedIssueCountList");
	    		          
	          }
			   return null;
	    }

		public static ArrayList ListofIssuesSearch(ListofIssuesForm form, String strLoginUser, String strLoginRole) 
		{
			logger=Logger.getLogger("admin");
			
			String strIssueID=null;
			String strIssuePriority=null;
			String strFromDate=null;
			String strToDate=null;
			
			String strSearchCustomerName=null;
			String strSearchApplicationName=null;
			String strSearchEmployeeName=null;
			String strSearchSeverity=null;
			String strSearchStatus=null;
			
			String strAdmin=null;
			String strCustomer=null;
			String strInternal=null;
			
			 String FromDate = null;
			 String ClosedDate = null;
			 String TargetDate=null;
			 String strPostedDate=null;
			 String strDueDate=null;
			 String strClosedDate=null;
			 String linkView=null;
			 
			ArrayList IssuesList=new ArrayList();
			
			strSearchCustomerName=form.getStrSearchCustomer();
			strSearchApplicationName=form.getStrSearchApplicationName();
			strSearchEmployeeName=form.getStrSearchEmployee();
			strSearchStatus=form.getStrSearchStatus();
			strSearchSeverity=form.getStrSearchSeverity();
			
			System.out.println("========strSearchCustomerName=========="+strSearchCustomerName);
			System.out.println("========strSearchApplicationName=========="+strSearchApplicationName);
			System.out.println("========strSearchEmployeeName=========="+strSearchEmployeeName);
			System.out.println("========strSearchStatus=========="+strSearchStatus);
			System.out.println("========strSearchSeverity=========="+strSearchSeverity);
			
			if("".equalsIgnoreCase(strSearchCustomerName))
			{
				strSearchCustomerName=null;
			}
			if("".equalsIgnoreCase(strSearchApplicationName))
			{
				strSearchApplicationName=null;
			}
			if("".equalsIgnoreCase(strSearchEmployeeName))
			{
				strSearchEmployeeName=null;
			}
			if("".equalsIgnoreCase(strSearchStatus))
			{
				strSearchStatus=null;
			}
			if("".equalsIgnoreCase(strSearchSeverity))
			{
				strSearchSeverity=null;
			}
			if(strLoginRole.equalsIgnoreCase("Admin"))
			{
				strAdmin=strLoginUser;
				strCustomer=null;
				strInternal=null;
			}
			if(strLoginRole.equalsIgnoreCase("Customer"))
			{
				strAdmin=null;
				strCustomer=strLoginUser;
				strInternal=null;
			}
			if(strLoginRole.equalsIgnoreCase("User"))
			{
				strAdmin=null;
				strCustomer=null;
				strInternal=strLoginUser;
			}
			if((strSearchStatus!=null) && (strSearchStatus.equalsIgnoreCase("all")))
			{
				System.out.println("==========Search Status============="+strSearchStatus);
				strSearchStatus=null;
			}
			 connection=DBConnection.createConnection();
			try
			{
				System.out.println("--------^^^^^^^^^^^^------"+connection); 
				CallableStatement callableStatement=connection.prepareCall("{CALL vims_user.USP_Report_Issues(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				callableStatement.setString(1,strIssueID);
				callableStatement.setString(2,strSearchCustomerName);
				callableStatement.setString(3,strSearchEmployeeName);
				callableStatement.setString(4,strSearchStatus);
				callableStatement.setString(5,strIssuePriority);
				callableStatement.setString(6,strSearchApplicationName);
				callableStatement.setString(7,strFromDate);
				callableStatement.setString(8,strToDate);
				callableStatement.setString(9,strSearchSeverity);
				callableStatement.setString(10,strCustomer);
				callableStatement.setString(11,strInternal);
				callableStatement.setString(12,strAdmin);
				callableStatement.setString(13,"0");
				callableStatement.execute();
				ResultSet resultset=callableStatement.getResultSet();
				
				while(resultset.next())
				{
					HashMap hashmap=new HashMap();
					if((strLoginRole.equalsIgnoreCase("Admin"))||(strLoginRole.equalsIgnoreCase("Customer")))
					{
						linkView="<a href='./ListofIssues.do?methodname=IssueDetails&id="+(String)resultset.getString(1)+"'>"+resultset.getString(1)+"</a>";
					}
					else
					{
						linkView="<a href='./ListofIssues.do?methodname=IssueDetails1&param=Details&id="+(String)resultset.getString(1)+"'>"+resultset.getString(1)+"</a>";
						
					}
						hashmap.put("id", linkView);
						
						strPostedDate=resultset.getString(4);
						strDueDate=resultset.getString(9);
						strClosedDate=resultset.getString(10);
						
						if(strPostedDate!=null)
						{	//if start
							
							strPostedDate=ConvertDate(strPostedDate);
						}	//if end
						else
						{	//else start
							strPostedDate=strPostedDate;
						}	//else end
						if(strDueDate!=null)
						{	//if start
							strDueDate=ConvertDate(strDueDate);
						}	//if end
						else
						{	//else start
							strDueDate=strDueDate;
						}	//else end
						if(strClosedDate!=null)
						{	//if start
							strClosedDate=ConvertDate(strClosedDate);
						}	//if end
						else
						{	//else start
							strClosedDate=strClosedDate;
						}	//else end
						
						hashmap.put("applicationName", resultset.getString(3));
						hashmap.put("title", resultset.getString(2));
						hashmap.put("status", resultset.getString(6));
						hashmap.put("severity", resultset.getString(5));
						hashmap.put("assignedto", resultset.getString(7));
						hashmap.put("postedby", resultset.getString(8));
						hashmap.put("posteddate",strPostedDate);
						//hashmap.put("duedate",strDueDate);
						hashmap.put("closeddate",strClosedDate);
						if(strLoginRole.equalsIgnoreCase("Admin"))
						{
						hashmap.put("customer", resultset.getString(13));
						}
						if(strLoginRole.equalsIgnoreCase("User"))
						{
						hashmap.put("customer", resultset.getString(11));
						}
						if(strLoginRole.equalsIgnoreCase("Customer"))
						{
						hashmap.put("customer", resultset.getString(11));
						}
						IssuesList.add(hashmap);
						
					}//while end
				  } //try end
				  catch(Exception e)   
				  {	//catch start
					  e.printStackTrace();
					  logger.error(e);
				  }	//catch end
				  finally{
					  
				  }
			return IssuesList;
		}

		public static int getIssueSearchPageDAO(String strUserID, String strIssueID,String strRoleType)
		{
			
			System.out.println("----------In getIssueSearchPageDAO---------");
			Logger logger = null;
	    	ArrayList IssueSearchList = new ArrayList();
			logger=Logger.getLogger("Admin");
			ResultSet resultSet=null;
			HashMap hashMap=null;
			CallableStatement callablestmt=null;
			Statement statement=null;
			try
			{			
				 Connection connection=DBConnection.createConnection();
				if(strRoleType.equalsIgnoreCase("Customer"))
				{
				 callablestmt=connection.prepareCall("{CALL vims_user.USP_Report_Issues(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				 
				 callablestmt.setString(1,null);
				 callablestmt.setString(2,null);
				 callablestmt.setString(3,null);
				 callablestmt.setString(4,null);
				 callablestmt.setString(5,null);
				 callablestmt.setString(6,null);
				 callablestmt.setString(7,null);
				 callablestmt.setString(8,null);
				 callablestmt.setString(9,null);
				 callablestmt.setString(10,strUserID);
				 callablestmt.setString(11,null);
				 callablestmt.setString(12,null);
				 callablestmt.setString(13,"0");
				 callablestmt.executeQuery();
				 
				 resultSet=callablestmt.getResultSet();
				}
				else if(strRoleType.equalsIgnoreCase("User"))
				{
					 callablestmt=connection.prepareCall("{CALL vims_user.USP_Report_Issues(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
					 
					 callablestmt.setString(1,null);
					 callablestmt.setString(2,null);
					 callablestmt.setString(3,null);
					 callablestmt.setString(4,null);
					 callablestmt.setString(5,null);
					 callablestmt.setString(6,null);
					 callablestmt.setString(7,null);
					 callablestmt.setString(8,null);
					 callablestmt.setString(9,null);
					 callablestmt.setString(10,null);
					 callablestmt.setString(11,strUserID);
					 callablestmt.setString(12,null);
					 callablestmt.setString(13,"0");
					 callablestmt.executeQuery();
					 
					 resultSet=callablestmt.getResultSet();
					
				}
			      //System.out.println("========Resultset========="+resultSet);
			      //System.out.println("=========resultSet.next()========="+resultSet.next());
		         while(resultSet.next())
		   			{
		        	 System.out.println("=========In While============");   
		        	 System.out.println("========resultset.getString(1)==========="+resultSet.getString(1));
		   				if(strIssueID.equals((String)resultSet.getString(1)))
			   			{
			   				return 1;
			   			}
			   			
		     	    }
		         
			}  
				
			catch(SQLException sqlException)
			{
				logger.info("VIMSApplicationDAO.searchApplicationDAO()");
				logger.error(sqlException);
			}	
			catch(Exception exception)
			{
				logger.info("VIMSApplicationDAO.searchApplicationDAO()");
				logger.error(exception);
			}
			/*finally
			{
				try{
				statement.close();
				DBConnection.closeConnection();
				}
				catch(Exception e)
				{
					
				}
			}*/
			return 0;
			
		}
public static int SaveMailDetails(String IssueID,String IssueStatusID,String SenderID,String ReceiverID)
{
	String Mailed="true";
	String strMailedDate = null;
					        	
	int intReturnValue = 0;
	
	System.out.println("=========Issue ID=====in Function====="+IssueID);
	System.out.println("=========Issue status ID=====in Function====="+IssueStatusID);
	System.out.println("=========Issue SenderID=====in Function====="+SenderID);
	System.out.println("=========Issue status Receiver ID=====in Function====="+ReceiverID);
	try
	{
		connection = DBConnection.createConnection();
		preparedStatement = connection.prepareStatement(VIMSQueryInterface.getDateSQL);
		resultset = preparedStatement.executeQuery();
		while (resultset.next())
		{//while start
			strMailedDate = resultset.getString(1);
		}//while end
		if(resultset!=null)
		{
		 resultset.close();
		}
		if(preparedStatement!=null)
		{
		 preparedStatement.close();
		}
		//strSenderID=strSender;
		//strReceiverID=strAssignedEmployee;
		
		callableStatement=connection.prepareCall("{?=CALL vims_user.USP_Save_Issue_Mail_Sts(?,?,?,?,?,?)}");
		callableStatement.registerOutParameter(1,Types.OTHER);
		callableStatement.setString(2,IssueID);
		callableStatement.setString(3,Mailed);
		callableStatement.setString(4,strMailedDate);
		callableStatement.setString(5,SenderID);
		callableStatement.setString(6,ReceiverID);
		callableStatement.setString(7,IssueStatusID);
		callableStatement.execute();
		
		intReturnValue=callableStatement.getInt(1);
		
		if(callableStatement!=null)
		{
		callableStatement.close();
		}
		
	}
	catch (Exception e)
	{
		logger.error(e);
	}
	finally
	{
		DBConnection.closeConnection();
	}  
	return intReturnValue;
	
}



public static boolean IssueVerified(String strContextPath,
		String strIssueTitle, String strApplicationName,
		String strIssueStatusFrom, String strAssignedBy, String strIssuesID, String strUserID)
{
	boolean MailResponse = false;
	
	String strSql;
	String strApplicationOwnerMailID = null;
	String strApplicationOwnerName = null;
	String strReceiverID;
	String strSender = null;
	String strSenderID;
	String strAction="verified";
	String strMailedDate=null;
	String Mailed="yes";
	
	Connection conn=null;
	Statement stmt=null;
	ResultSet rset=null;
	ResultSet reset=null;
	
	PreparedStatement PreStatement=null;
	CallableStatement callStatement=null;
	
	int intReturnValue;
	
	try
	{
		conn=DBConnection.createConnection();
		strSql="Select app.App_Owner_Email,app.app_owner from vims_user.Application app INNER join vims_user.Incident inc ON app.Application_ID = inc.Application_ID WHERE inc.Incident_ID ='"+strIssuesID+"'";
		stmt= conn.createStatement();
		rset = stmt.executeQuery(strSql);
		 //System.out.println("------------Out of Query------------");
		 while (rset.next())
		 {//while start
			strApplicationOwnerMailID = rset.getString(1);
			System.out.println("------------strApplicationOwnerMailID----------"+strApplicationOwnerMailID);
			strApplicationOwnerName=rset.getString(2);
			System.out.println("------------strApplicationOwnerName----------"+strApplicationOwnerName);
			//strReceiverID=strApplicationOwnerMailID;
		 }
		 
		 PreStatement=conn.prepareStatement("select user_nm,work_email_address from vims_user.employee where user_nm='"+strAssignedBy+"'");
		 reset = PreStatement.executeQuery();
			
			while (reset.next())
			{//while start
				strSenderID=reset.getString(1);
				strSender=reset.getString(2);
			}//while end
			
			if(conn!=null){conn.close();}
			if(stmt!=null){stmt.close();}
			if(rset!=null){rset.close();}
			if(PreStatement!=null){PreStatement.close();}
			if(reset!=null){rset.close();}
			
	} 
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if((strApplicationOwnerMailID==null && "".equalsIgnoreCase(strApplicationOwnerMailID)) ||(strSender==null && "".equalsIgnoreCase(strSender)))
	{
		MailResponse=false;
		return MailResponse;
	}
	else
	{
		HashMap details1=new HashMap();
		
		  details1.put("reciever",strApplicationOwnerMailID);
		   //System.out.println("---------------strReceiverEmail in details1 user----------"+strReceiverEmail);
		   details1.put("sender", strSender);
		  //System.out.println("---------------strSenderEmail in details1 user----------"+strSenderEmail);
		   details1.put("person", "Admin");
		   details1.put("name",strApplicationOwnerName);
	       //details1.put("status",strIssuesStatusTo);
	       details1.put("ID",strIssuesID);
	       //details1.put("description",strReason);
	       details1.put("title",strIssueTitle);
	       details1.put("applicationName", strApplicationName);
	       System.out.println("--------title when mail sent to application owner-------"+strIssueTitle);
	       
	       MailResponse=VIMSMail.sendMail(details1,strContextPath, strAction);
	       
	       if(MailResponse==true)
	       {
	    	   try
	    	   {
				 conn=DBConnection.createConnection();
				 PreStatement = conn.prepareStatement(VIMSQueryInterface.getDateSQL);
				 rset = PreStatement.executeQuery();
				 while (rset.next())
				 {//while start
				   strMailedDate = rset.getString(1);
				   System.out.println("==========Mailed date in Verify=========="+strMailedDate);
				 }//while end
				 
				 callStatement=conn.prepareCall("{?=CALL vims_user.USP_Save_Incident_Verifcation_Sts(?,?,?,?,?,?)}");
				 callStatement.registerOutParameter(1, Types.OTHER);
				 callStatement.setString(2,strIssuesID);
				 callStatement.setString(3,"1");
				 callStatement.setString(4,strSender);
				 callStatement.setString(5,strApplicationOwnerMailID);
				 callStatement.setString(6,strMailedDate);
				 callStatement.setString(7,Mailed);
				 callStatement.execute();
				 
				 intReturnValue=callStatement.getInt(1);
				 
				 System.out.println("==========strIssuesID in Verify=========="+strIssuesID);
				 System.out.println("==========strSender in Verify=========="+strSender);
				 System.out.println("==========strApplicationOwnerMailID in Verify=========="+strApplicationOwnerMailID);
				 System.out.println("==========Mailed  in Verify=========="+Mailed);
				 
				 if(PreStatement!=null){
					 PreStatement.close();}
				 if(rset!=null){
					 rset.close();}
				 if(callStatement!=null){
					 callStatement.close();}
				 if(conn!=null){
					 conn.close();}
				 
		       } 
	    	   catch (SQLException e)
	    	   {
				e.printStackTrace();
    		    }
	    	  
	    	   return MailResponse;
	       }
		
	}
		
	return MailResponse;
   }



public static String getApplicationOwnerName(String strIssuesID) 
{
	String strApplicationOwnerName=null;
	String strSql;
	
	Connection con=null;
	Statement stmt=null;
	ResultSet rset=null;
	
	strSql="Select app.app_owner from vims_user.Application app INNER join vims_user.Incident inc ON app.Application_ID = inc.Application_ID WHERE inc.Incident_ID ='"+strIssuesID+"'";
	
	 try
	 {
		con=DBConnection.createConnection();
		 stmt= con.createStatement();
		 rset = stmt.executeQuery(strSql);
		 while (rset.next())
		  {//while start
			 strApplicationOwnerName=rset.getString(1);
		  }//while end
		 if(con!=null){con.close();}
		 if(stmt!=null){stmt.close();}
		 if(rset!=null){rset.close();}
		 
	} 
	catch (SQLException e) {
		e.printStackTrace();
	}
	return strApplicationOwnerName;
}
		

}
 
