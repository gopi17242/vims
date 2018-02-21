package com.vertex.VIMS.test.login.DAO;
 
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.vertex.VIMS.test.VIMSSQLQueryInterface.VIMSQueryInterface;
import com.vertex.VIMS.test.applications.BD.VIMSApplicationBD;
import com.vertex.VIMS.test.clients.BD.VIMSClientBD;
import com.vertex.VIMS.test.common.*;
import com.vertex.VIMS.test.ldap.BD.ConfigBD;
import com.vertex.VIMS.test.listofissues.BD.ListofIssuesBD;

public class LoginDAO
{  
	public static String getUserType(String strUserId, String strPassword)
	{
		Connection connection = null;
		Statement stmt=null;
		ResultSet rs=null;
		CallableStatement callableStatement=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultset=null;
		
		String strUserType;
		String strStatus;
		
		String strQuery="EXEC vims_user.USP_Check_Login @User_ID='"+strUserId+"',@Password='"+strPassword+"'";
		System.out.println(strQuery); 	
		try
		{
			connection=DBConnection.createConnection();
		
		//System.out.println("===============Connection Object created============"+conn);
		
		stmt = connection.createStatement();
		rs=stmt.executeQuery(strQuery);
		
		if (!rs.next())
		{
			 System.out.println("=======1=====");
			 rs=stmt.executeQuery("select * FROM vims_user.USER_TEST where USER_ID='"+strUserId+"'");
			 if(!rs.next())
			 {
				// System.out.println("========2========="); 
				 return "Invalid User Id";
			 }
			 else
			 {
				 //System.out.println("=========3=========");
				 return "Invalid password";
			 }		
		}
		else 
		{
			strUserType=rs.getString(1);
			strStatus=rs.getString(2);
			
			 
			
			//System.out.println("---------strUserType in loginDAO-------"+strUserType);
			//System.out.println("---------strStatus  in loginDAO-------"+strStatus);
			
			if (strUserType.equalsIgnoreCase("admin") && strStatus.equalsIgnoreCase("active"))
			{
				return "Admin";
			} 
			else if (strUserType.equalsIgnoreCase("internal")&& strStatus.equalsIgnoreCase("active"))
			{
				return "User";
			} 
			else if (strUserType.equalsIgnoreCase("customer") && strStatus.equalsIgnoreCase("active"))
			{
				return "Customer";
			}
			else if (strUserType.equalsIgnoreCase("customer") && strStatus.equalsIgnoreCase("InProcess"))
			{
				return "Customer";
			}
			else if (strUserType.equalsIgnoreCase("supervisor") && strStatus.equalsIgnoreCase("active"))
			{
				return "Supervisor";
			}
			else  if (strUserType.equalsIgnoreCase("Manager") && strStatus.equalsIgnoreCase("active"))
			{
				return "Manager";
			}
			else 
			{
				return "loginerror";
			}
		}
		}
		catch(SQLException e1)
		{
			e1.printStackTrace();
			//System.out.println("========= SQL Exception ocured in the application========="+e1.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//System.out.println("============Exception occured in DBConnection============"+e.toString());
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return "errorpage";
	}
	 public static String viewLoginClientStatusDAO(String strUserId,String strPassword) 
		{
	      Logger logger=Logger.getLogger("Admin"); 
	      String strStatus=null;
	   //Start of try block 
		 try
		  {
			PreparedStatement preparedStatement=null;
			Connection connection=DBConnection.createConnection();
		    //System.out.println("----------------strUserId----------"+strUserId);
		    //System.out.println("----------------strPassword----------"+strPassword);
		    
		    preparedStatement = connection.prepareStatement(VIMSQueryInterface.statusClientDetail);
         
		    preparedStatement.setString(1,strUserId);
			preparedStatement.setString(2,strPassword);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			   
	        
			  while(resultSet.next())
			  {
				  strStatus=resultSet.getString(1);
			  }
		  }
		 catch(Exception exception)
		 {				
			  logger.info("LoginDAO.viewLoginClientStatusDAO");
	    	  logger.error(exception);
			  System.out.println("The Exception is:"+exception);
		 }
		return strStatus;
		 
		}
	public static String getAppOwnerNameDAO(String strUserId)
	{
		 Logger logger=Logger.getLogger("Admin");
		 String strName=null;
		 try
		  {
			PreparedStatement preparedStatement=null;
			 Connection connection=DBConnection.createConnection();
			  preparedStatement = connection.prepareStatement(VIMSQueryInterface.applicationOwnerName);
		         
			    preparedStatement.setString(1,strUserId);
				
				 ResultSet resultSet = preparedStatement.executeQuery();
				   
		        
				  while(resultSet.next())
				  {
					  strName=resultSet.getString(1);
				  }
			  }
			 catch(Exception exception)
			 {				
				  logger.info("LoginDAO.viewLoginClientStatusDAO");
		    	  logger.error(exception);
				  System.out.println("The Exception is:"+exception);
			 }
			return strName;
		
	}

	public static void applyCustomeSettings(HttpServletRequest request,HttpServletResponse response,String strUserId) {
	            
		   Logger logger=null;
		   HttpSession session=null;
		   HashMap userOptions=null;
		   HashMap newApplicationList=null;
		   ArrayList strOptions=null;
		   String strLoginId=null;
		   Set keys=null;
		   Object issueCountNames[]=null;
		   HashMap issueCount=null;
		   int temp;
		 try {
			   logger=Logger.getLogger("Admin");
			    session=request.getSession(false);
			    strLoginId=(String)session.getAttribute("user");
			    if(session!=null) {
			    	
			    	if(session.getAttribute("tableDisplayFlag")!=null)
			    		session.removeAttribute("tableDisplayFlag");
			    	
			    	if(session.getAttribute("applicationCount")!=null)
			    	session.removeAttribute("applicationCount");
			    	
			      	if(session.getAttribute("applicationDetails")!=null)
				   	session.removeAttribute("applicationDetails");
			    	
			    	if(session.getAttribute("newCustomerList")!=null)
					   	session.removeAttribute("newCustomerList");
			    	
			    	if(session.getAttribute("newCustomerCount")!=null)
					   	session.removeAttribute("newCustomerCount");
			    	
			    	if(session.getAttribute("openIssueCount")!=null)
					 session.removeAttribute("openIssueCount");
			    	
			    	if(session.getAttribute("closedIssueCount")!=null)
						 session.removeAttribute("closedIssueCount");
			    	
			    	if(session.getAttribute("escalatedIssueCount")!=null)
						 session.removeAttribute("escalatedIssueCount");
			    	
			    	if(session.getAttribute("assignedIssueCount")!=null)
						 session.removeAttribute("assignedIssueCount");
			    	
			    	if(session.getAttribute("rejectedIssueCount")!=null)
						 session.removeAttribute("rejectedIssueCount");
			    	
			    	if(session.getAttribute("reOpenIssueCount")!=null) 
						session.removeAttribute("reOpenIssueCount");
			    		 
			    	
			    	if(session.getAttribute("completedIssueCount")!=null)
						 session.removeAttribute("completedIssueCount");
			    	
			    	if(session.getAttribute("acceptedIssueCount")!=null)
						 session.removeAttribute("acceptedIssueCount");
			    				    	
			    	userOptions=ConfigBD.getCustomOptions(strUserId);
                    if(userOptions!=null) { 
					if(userOptions.get("existOptions")!=null)
						strOptions=(ArrayList)userOptions.get("existOptions");
        			    if(strOptions!=null&&strOptions.size()>0) {
						for(int optionCount=0;optionCount<strOptions.size();optionCount++) {
							session.setAttribute("tableDisplayFlag","1");
							System.out.println("====(String)strOptions.get(optionCount)====="+(String)strOptions.get(optionCount));
						if(((String)strOptions.get(optionCount)).equalsIgnoreCase("Applications")) {
								newApplicationList=VIMSApplicationBD.getNewApplicationsAddedBD(strLoginId);
						} 
  							if(((String)strOptions.get(optionCount)).equalsIgnoreCase("Issues")) {
	    						issueCount=ListofIssuesBD.getCountofNewIssuesAddedBD(strLoginId);
		    					if(issueCount!=null) {
			    				if(issueCount.size()>0) {
								keys=issueCount.keySet();
							    issueCountNames=(Object[])keys.toArray();
								 for(int count=0;count<issueCountNames.length-1;count++) {
				                  temp=(Integer)issueCount.get(issueCountNames[count].toString());
				                 
				 				 if(temp>0) {
				            	  session.setAttribute((String)issueCountNames[count],temp);
					        	  //HashMap t=(HashMap)issueCount.get(issueCountNames[issueCountNames.length-1]);
					         	  ArrayList lis=(ArrayList)issueCount.get("listSet");
						       	  HashMap t1=(HashMap)lis.get(0);
						     	  session.setAttribute(issueCountNames[count]+"List",t1.get(issueCountNames[count]+"List"));
							 	 // session.setAttribute((ArrayList)(issueCount.get(issueCountNames[(issueCountNames.length-1)]).get((String)issueCountNames[count]+"List"))));
							}	  
						 }  
						} 
					} 
							 }
								
								if(((String)strOptions.get(optionCount)).equalsIgnoreCase("Customers")) {
									
									HashMap customerSet=VIMSClientBD.getNewCustomerDetailsBD();
									
									if((customerSet!=null)&&(customerSet.size()>0)) {
										session.setAttribute("newCustomerCount",customerSet.get("newCustomerCount"));
										session.setAttribute("newCustomerList",customerSet.get("newCustomerList"));
									}
								}
							 } 
						  if(newApplicationList!=null) {
							
							session.setAttribute("applicationCount",newApplicationList.get("applicationCount"));
							 session.setAttribute("applicationDetails",newApplicationList.get("applicationList"));
						  }
						} 
			         }
			    }	
    		    	else {
			        logger.info("-=-----------from applyCusotmSettings---------");
			        logger.info("----Session does not exist--------");
			     }
			    
			   
		 } catch(Exception exception) {
			 
			  logger.info("---Exception in applyCustomSettingDAO--------");
			  logger.error(exception);
		 }
	}
	
	public static String getVIMSApplicationVersionDAO() {
	              
		        Connection connection=null;
		        CallableStatement callableStatement=null;
		        ResultSet resultSet=null;
		        String strVersion=null;
		        Logger logger=null;
		  try {
			    logger=Logger.getLogger("Admin");
			    connection=DBConnection.createConnection();
			   /* callableStatement=connection.prepareCall("{call Get_VIMS_Version}");
			    resultSet=callableStatement.executeQuery();  
			    
			    strVersion=resultSet.getString(1); */
			    strVersion="0.0.2";
			    
		  } catch(Exception exception) {
			   logger.info("----Exception raised in getVIMSApplicationVersionDAO------------");
			   logger.error(exception);
		  }
		     return strVersion;
	}  

public static String getUserName(String strUserId)
{
	Connection connection = null;
	Statement stmt=null;
	ResultSet rs=null;
	String UserName=null;	
	String strQuery="select first_name+' '+last_name from vims_user.employee where user_nm='"+strUserId+"'";
			
	try
	{
		connection=DBConnection.createConnection();
	    stmt = connection.createStatement();
	    rs=stmt.executeQuery(strQuery);
	    while(rs.next())
	    {
	    	UserName=rs.getString(1);
	    }
	}
	catch(Exception exception)
	{
		exception.printStackTrace();
	}
	finally
	{
		DBConnection.closeConnection();
	}
	return UserName;
}

  public static int validateViewPermissionDAO(String strUserId,String strIssueId) {
	             
	            Logger logger=null;
	            Connection connection=null;
	            PreparedStatement preparedStatement=null;
	            ResultSet resultSet=null;
	            int result;
	      try {
	    	      logger=Logger.getLogger("Admin");
	    	      connection=DBConnection.createConnection();
	    	      preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Chck_Login_Issue @User_NM='"+strUserId+"',@incident_ID='"+strIssueId+"'");
	    	      logger.info("==========before executing the query================");
	    	      logger.info("statement result================="+preparedStatement.execute());
	    	      resultSet=preparedStatement.getResultSet();
	    	      logger.info("==========result set object got================");
	    	      if(resultSet!=null) {
	    	    	  
	    	    	   if(resultSet.next()) {
	    	    		   result=resultSet.getInt(1);
	    	    		   return result;
	    	    	   }
	    	      }
	    	      logger.info("============success======================");
	    	  
	      } catch(Exception exception) {
	    	  
	    	   logger.info("-----Exception in validateViewPermissionDAO-------");
	    	   logger.error(exception);
	      }
	        return 0;
  }
public static int getLoginIDNumber(String strUserId, String strPassword)
{
	Connection connection = null;
	Statement stmt=null;
	ResultSet rs=null;
	CallableStatement callableStatement=null;
	PreparedStatement preparedStatement=null;
	ResultSet resultset=null;
	

	String strLoginTime=null;
	
	int intLoginIDNumber = 0;
	
		try
		{
		connection=DBConnection.createConnection();
		
		//connection=DBConnection.createConnection();
		preparedStatement = connection.prepareStatement(VIMSQueryInterface.getDateSQL);
		 resultset = preparedStatement.executeQuery();
		 while (resultset.next())
		 {//while start
			 strLoginTime = resultset.getString(1);
		 }//while end
		 
		callableStatement=connection.prepareCall("{CALL vims_user.USP_Save_User_Log_Tm(?,?,?,?)}");
		callableStatement.setString(1,"0");
		callableStatement.setString(2,strUserId);
		callableStatement.setString(3,strLoginTime);
		callableStatement.setString(4,null);
		
		callableStatement.execute();
		
		resultset=callableStatement.getResultSet();
		
		while(resultset.next())
		{
			intLoginIDNumber=resultset.getInt(1);
			System.out.println("=========LoginID Number============="+intLoginIDNumber);
		} 
	}
	catch (SQLException e)
	{
		e.printStackTrace();
	}
	finally
	{
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(resultset!=null)
		{
			try {
				resultset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(callableStatement!=null)
		{
			try {
				callableStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(preparedStatement!=null)
		{
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	return intLoginIDNumber;
 }

   public static String getRoleIdDAO(String strUserType) {
	          Connection connection=null;
	          Statement statement=null;
	          ResultSet resultSet=null;
	          String strRoleId=null;
	          String sqlQuery="select * from vims_user.TRole where Role_NM='"+strUserType+"'";
	   try {
	    	   connection=DBConnection.createConnection();
	    	   statement=connection.createStatement();
	    	   resultSet=statement.executeQuery(sqlQuery);
	    	   
	    	   if(resultSet!=null) {
	    		    if(resultSet.next()) {
	    		    	strRoleId=resultSet.getString("Role_NBR"); 
	    		    }
	    	   }
	    }
	     catch(Exception exception) {
	    	  System.out.println("=======Exception in getRoleIDDAO method========="+exception);
	    	  exception.printStackTrace();
	     }
	        return strRoleId;
   }
   
   public static ArrayList getUserNavigSettingsDAO(String strUserId) {
	   Logger logger=null;
       
       ArrayList list=null;
       ArrayList tempList=null;
       Connection connection=null;
       PreparedStatement preparedStatement=null;
       PreparedStatement preparedStatement1=null;
       ResultSet resultSet=null;
       ResultSet loopResultSet=null;
       
       HashMap tempRecord=null;
       HashMap record=null;
       
       String sqlQuery=null;
       int iPageId;
       int iParentId;
       int iPermissionFlag;
       try {
      	   logger=Logger.getLogger("Admin");
      	   connection=DBConnection.createConnection();
      	   
      	   sqlQuery="EXEC vims_user.USP_User_Navigation_Menu @user_nm='"+strUserId+"'";
      	   System.out.println("----Sql Query---"+sqlQuery);
      	   
      	   preparedStatement=connection.prepareStatement(sqlQuery,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      	   preparedStatement1=connection.prepareStatement(sqlQuery,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      	   
      	   System.out.println(preparedStatement.execute());
      	   
      	   resultSet=preparedStatement.getResultSet();
      	   
      	   System.out.println("----ResultSet type----"+preparedStatement.getResultSetType());
      	   System.out.println("----ResultSet concurrency----"+preparedStatement.getResultSetConcurrency());
      	   int count=0;
      	   if(resultSet!=null) {
      		   System.out.println("-----ResultSet object got======"+resultSet);
      		   list=new ArrayList();
      		  
      		   while(resultSet.next()) {
      			  
      			   iPageId=resultSet.getInt("Application_NBR");
      			   iParentId=resultSet.getInt("Parent_NBR");
      			   iPermissionFlag=resultSet.getInt("Permission_FG");
      			   preparedStatement1=connection.prepareStatement(sqlQuery);              			   
                    record=new HashMap();
                    System.out.println(preparedStatement1.execute());
         		   	  loopResultSet=preparedStatement1.getResultSet();
         		   tempList=new ArrayList();
                   if(iParentId==0) {
                  	 while(loopResultSet.next()) {
      				  if(iPageId==loopResultSet.getInt("Parent_NBR")) {
      					tempRecord=new HashMap();
      					
      					tempRecord.put("name",loopResultSet.getString("Application_NM"));
      					tempRecord.put("id", loopResultSet.getString("Application_NBR"));
      					tempRecord.put("path",loopResultSet.getString("Menu_Path"));
      					/*if(loopResultSet.getString("Permission_FG").equals("1"))
      					  tempRecord.put("permissionFlag","checked");
      					else
      						tempRecord.put("permissionFlag","");*/
      					
      					tempList.add(tempRecord);
      					count++;
      				  }
      			   }
                  	   record.put("tabName",resultSet.getString("Application_NM"));
                       record.put("keyName",resultSet.getString("Key_Name"));
                       record.put("tabId",iPageId);
                       if(tempList.size()==0){tempList=null; }
                       record.put("subMenu",tempList);
                       record.put("path",resultSet.getString("Menu_Path"));
                       /*if(iPermissionFlag==1)
                        record.put("permissionFlag","checked");
                       else
                       	record.put("permissionFlag","");*/	
                       // Set to before first record 
                       //loopResultSet.beforeFirst();
                       preparedStatement1.close();
                       preparedStatement1=null;
                       // Add the menu to list
                       list.add(record);
                   }
                      
      		   }
      	   }
       }
        catch(Exception exception) {
      	 logger.info("------Exception in getUserNavigSettingsDAO-----------");
			 logger.error(exception);
			 exception.printStackTrace();
			 return null;
        }
        System.out.println("====Main List========"+list);
        return list;
   }
}

