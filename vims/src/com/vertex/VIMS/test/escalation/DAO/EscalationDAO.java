/*
 * @author: santhosh.k
 * @creation date: 
 * @file name : EscalationDAO.java
 * @description: 
 * 			It is the data Access Object class  of the Escalation page.It is called when the user clicks on the Escalation link under Issues.
*/
package com.vertex.VIMS.test.escalation.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.vertex.VIMS.test.VIMSSQLQueryInterface.VIMSQueryInterface;
import com.vertex.VIMS.test.common.DBConnection;
public class EscalationDAO
{ 

	 static Connection connection=null;
	 static Statement statement=null;
	 static PreparedStatement preparedStatement=null;
	 static ResultSet resultSet=null;	 
	 static Logger logger=Logger.getLogger("Admin");	 
	 static HashMap hashMap=null;
	 static String IssueStatus="default";
	 static String assignedEmpName=null;
	 static String assignedEmpEmailId=null;
	 /*
	  * This method is used to get list of escalated issues.
	 */
	 public static ArrayList getEscalatedIssuesListDAO(String strUserID)
	{
		 ArrayList EscalatedIssuesListDAO= new ArrayList();
		 String strAdmin=strUserID;
		try
		{	
			 //EXEC USP_Get_Incident_Dtls_Escld
			//EXEC USP_Report_Issues @Incident_ID=?,@Customer=?,@Emp_Name=?,@INCT_STATUS=?,@INCIDENT_PRIORITY=?,@Application_Name=?,@From_DT=?,@To_DT=?,@INCIDENT_TYPE=?,@Cust_User_NM=?,@Emp_User_NM=?,@User_NM=?,@Purged_flag=?";
						
		    connection = DBConnection.createConnection();
		    preparedStatement=connection.prepareStatement(VIMSQueryInterface.EscalatedIssues);		
		    		    
		    preparedStatement.setString(1,null);
			preparedStatement.setString(2,null);
			preparedStatement.setString(3,null);
			preparedStatement.setString(4,"escalated");
			preparedStatement.setString(5,null);
			preparedStatement.setString(6,null);
			preparedStatement.setString(7,null);
			preparedStatement.setString(8,null);
			preparedStatement.setString(9,null);
			preparedStatement.setString(10,null);
			preparedStatement.setString(11,null);
			preparedStatement.setString(12,strAdmin);
			preparedStatement.setString(13,"0");
		    
		    
		    resultSet = preparedStatement.executeQuery();						
			while(resultSet.next())
			{
				hashMap = new HashMap();
				String linkView="<a href='./ListofIssues.do?methodname=IssueDetails&id="+(String)resultSet.getString(1)+"'>"+resultSet.getString(1)+"</a>";
				hashMap.put("id", linkView);
				hashMap.put("applicationName", resultSet.getString(3));
				hashMap.put("title", resultSet.getString(2));
				if(resultSet.getString(4)!=null)
				{
					hashMap.put("fromDate", ConvertDate(resultSet.getString(4)));
				}	
				else
				{
					hashMap.put("fromDate", resultSet.getString(4));
				}
				hashMap.put("type", resultSet.getString(5));
				//hashMap.put("status", resultSet.getString(6));
				hashMap.put("assignedTo", resultSet.getString(7));
				hashMap.put("postedBy", resultSet.getString(8));
				if(resultSet.getString(9)!=null)
				{
					hashMap.put("dueDate", ConvertDate(resultSet.getString(9)));	
				}
				else
				{
					hashMap.put("dueDate", resultSet.getString(9));	
				}
				if(resultSet.getString(10)!=null)
				{
					hashMap.put("closedDate", ConvertDate(resultSet.getString(10)));	
				}
				else
				{
					hashMap.put("closedDate", resultSet.getString(10));	
				}						
				
				EscalatedIssuesListDAO.add(hashMap);
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
				preparedStatement.close();//closing of Statement
				DBConnection.closeConnection();//connection closed
				resultSet.close();//resultSet closed
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}
		return EscalatedIssuesListDAO;
	}
	
	 /*
	  * This method is called in job class, to check whether the issue has been closed or not in order to send the mail.
	 */
	 public static HashMap getIssueCurrentStatus(String IssueID)
	 {	
		 HashMap hashMap=new HashMap();
		 try 
		 {
			
			 connection = DBConnection.createConnection();
			 statement=connection.createStatement();
			 resultSet=statement.executeQuery("EXEC vims_user.USP_Get_Incident_Dtls_by_Incident @incident_id='"+IssueID+"'");  
			 System.out.println(resultSet); 
			 if(resultSet.next())
			 {
				 //IssueStatus=resultSet.getString(7);
				 hashMap.put("IssueStatus", resultSet.getString(7));
				 hashMap.put("EmpName", resultSet.getString(9));
				 hashMap.put("EmpMailID", resultSet.getString(10));
				 //assignedEmpName=resultSet.getString(9);
				 //assignedEmpEmailId=resultSet.getString(10);
			 }
			 System.out.println("HashMap details is :"+hashMap); 
		 }
		 catch (SQLException sqlException )
		 {			
			 sqlException.printStackTrace();
		 }	
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
		 return hashMap;
	 }
	 /*
	  * This method is used to convert date obtained from data base to "mm/dd/yyyy" format
	 */
	 public static String ConvertDate(String strDate) throws Exception
		{
			 Calendar calendar = Calendar.getInstance () ; 
			 DateFormat dateFormat = new SimpleDateFormat ( "MM/dd/yyyy" ) ;			
			 Date date = dateFormat.parse (strDate) ;    
			 calendar.setTime ( date ) ; 
			 String strConvertedDate=dateFormat.format (calendar.getTime ());			
			 return strConvertedDate;
			
		}//function ConvertDate end
	/* public static void main(String[] a)
	 {
		HashMap hmap= getIssueCurrentStatus("0000001");
		System.out.println(hmap.get("IssueStatus"));
		System.out.println(hmap.get("EmpName"));
		System.out.println(hmap.get("EmpMailID"));
	 }*/
}