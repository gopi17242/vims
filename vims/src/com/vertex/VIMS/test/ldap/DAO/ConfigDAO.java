package com.vertex.VIMS.test.ldap.DAO;

/**
 * @author Rajashekar.B
 * 
 */
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.vertex.VIMS.test.common.DBConnection;
import com.vertex.VIMS.test.ldap.form.ConfigForm;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class ConfigDAO {
	
	static int result=0;
	static Connection connection=null;
	static Statement statement=null;
    static PreparedStatement preparedStatement=null;
	static ResultSet resultSet=null;
	static Logger logger=null;
	static String executeResult;
	
	/*
	 *  storeLdapConfig method is to store LDAP server details
	 *  
	 *  @param Server Name
	 *  @param Server Status
	 *  @param Server ID
	 *  
	 *  @returns operation result
	 */
	public static String storeLdapConfig(String serverName,String strStatus,String serverId) {
		 
		  result=1;
		  try {
			     logger=Logger.getLogger("Admin");
			     connection=DBConnection.createConnection();			     
			     preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Save_Ldap_Server @Server_Ldap_NBR=?,@Server_NM=?,@Status =?");	
			     preparedStatement.setInt(1,Integer.parseInt(serverId));
			     preparedStatement.setString(2,serverName);
			     preparedStatement.setString(3,strStatus);
			     preparedStatement.execute();
			     resultSet=preparedStatement.getResultSet();
			     if(resultSet!=null) {
			    	 resultSet.next();
			    	 executeResult=resultSet.getString(1);
			     }	 
			     
			     //System.out.println("-------executeResult---from DAO---------"+executeResult);
			  } catch(Exception exception) {
				  logger.info("======Exception in storeLdapConfig DAO-------");
				  logger.error(exception);
			  }
		        /* if(executeResult==0) 
			      return result;
		         else
		          return 0;*/	 
			  return executeResult;
     }
	
	/*
	 *  storeMailConfig method is to store Mail server details
	 *  
	 *  @param Server Name
	 *  @param Server Status
	 *  @param Server ID
	 *  
	 *  @returns operation result
	 */
	
	 public static String storeMailCongif(String serverName,String strStatus,String serverId) {

		      result=1;
		 try { // try block starts
			 
			  logger=Logger.getLogger("Admin");
		      connection=DBConnection.createConnection();
		      preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Save_Mail_Server @Server_Mail_NBR=?,@Server_NM=?,@Status =?");
		      preparedStatement.setInt(1,Integer.parseInt(serverId));
		      preparedStatement.setString(2,serverName);
		      preparedStatement.setString(3,strStatus);
		      
		      preparedStatement.execute();
			  resultSet=preparedStatement.getResultSet();
			  if(resultSet!=null) { // If starts
			    	 resultSet.next();
			    	 executeResult=resultSet.getString(1);
			     } // If ends	 
		    } // try block closes
		    catch(Exception exception) {
		    	// catch block starts
		    	 logger.info("=====Exception in storeMailConfig DAO========");
		    	 logger.error(exception);
		    } // catch block ends
		    
		    return executeResult;
	 }
	// end of storeMailCOnfig method 
	 
	 /*
      *  getCustomOptions method is to get home page configuration details
	  *
	  *  @param user id 
	  *  @returns settings options as hashmap
	  */	 
	 
	 public static HashMap getCustomOptions(String strUserId) {
		 
		           ArrayList customOptions=null;
		           ArrayList existOptions=null;
		           ArrayList duration=null;
		           
		           HashMap optionSet=null;
		           String sqlQuery=null;
		           String strTemp=null;
		           String[] options={"Applications","Customers","Issues"};
		           
		           int cnt=0;
		           int count;
		           boolean found=false;
		           
		       try { // try block starts
			         logger=Logger.getLogger("Admin");
		    	     customOptions=new ArrayList();
			         existOptions=new ArrayList();
			         duration=new ArrayList();
			         optionSet=new HashMap();
			         
			         connection=DBConnection.createConnection();
			         sqlQuery="select CUST_OPTIONS from vims_user.USER_TEST where USER_ID='"+strUserId+"'";
			         statement=connection.createStatement();
			         resultSet=statement.executeQuery(sqlQuery);
			         
			         if(resultSet!=null) { // If starts
			        	 if(resultSet.next()) { // If block starts
			        		 
			        		 strTemp=resultSet.getString("CUST_OPTIONS");
			        		 if(strTemp!=null&&strTemp.length()>0) { // If block starts
			        		 StringTokenizer strTokens=new StringTokenizer(strTemp);
			        		 
			        		 // Divide the options into separate strings
			        		 while(strTokens.hasMoreElements()) { // while loop starts
			        		  	 existOptions.add(strTokens.nextToken(","));  
			        		 } // end of while loop
			        		 
			        		 for(cnt=0;cnt<options.length;cnt++) {
					        	    found=false;
					        	    if((existOptions!=null)&&(existOptions.size()>0))
					        	    {
					        	    	for(count=0;count<existOptions.size();count++)
					        	    	{ 
					        	    		if((options[cnt].equalsIgnoreCase((String)existOptions.get(count)))) 
					        	    		{
					        	    			found=true;
			          	            	         break;	
			          	            	     } 	   
			          	            	    else
			          	            	    {
			          	            	    	found=false; //customOptions.add(options[cnt]);
			          	            	    }
					        	    	} 
					        	     }
					        	    
					        	    if(found==false)
					        	    {
					        	    	customOptions.add(options[cnt]);
					        	    }
			          	              	 
			          	         }
			        			 
			          		 }else {
			        			      for(cnt=0;cnt<options.length;cnt++) {
					        		  customOptions.add(options[cnt]);
			        			    } 
			        		 } // end of Else 
			             } //End of If block 
			         } // If ends
			         
			          else { // Else block starts
			        	     // Enters into this block when user has no settings 
			            	  for(cnt=0;cnt<options.length;cnt++) 
			            	 {
			        		   customOptions.add(options[cnt]);
			        	     }
			        	 } // Else block ends 
			          
			            // Set the details into hashmap
			            optionSet.put("defaultOptions",customOptions);
                        optionSet.put("existOptions",existOptions);
                } // try block ends
		         catch(Exception exception) {
		        	 logger.info("========Exception in getCustomOptions() DAO=========");
		        	 logger.error(exception);
		        	 return null;
		        }
		            // return the settings as hashmap
	                return optionSet;	 
	 }
	 // end of getCustomOptions method
	 
	 /*
      *  getServerList method is to get either LDAP or Mail server details
	  *
	  *  @param server type 
	  *  @returns servers details
	  */
	  public static ArrayList getServerList(String strServerType) {
		           
		         ArrayList serverList=null;
		         String strServer=null;
		         String optString=null;
		         HashMap record=null;
		         HashMap list=null;
		         
		    try { // try block starts
		     	    logger=Logger.getLogger("Admin");   
		    	    connection=DBConnection.createConnection();
		     	    if(strServerType.equalsIgnoreCase("LdapServer")) { //If starts
		    	     	preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Get_Ldap_Server @Server_Ldap_NBR=0");
		    	     	strServer="Ldap";
		    	    } // End of If
		    	    else { // Else block starts
		    	     	preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Get_Mail_Server @Server_Mail_NBR=0,@default_fg=null");
		    	     	strServer="Mail";
		    	    } //End of Else
		    	     resultSet=preparedStatement.executeQuery();
		    	     serverList=new ArrayList();
    	           if(resultSet!=null) { // If block starts
    	    	  	while(resultSet.next()) { // while loop starts
 	    	     		/*  optString="<a href='./modify"+server+"ServerDetails.do?param=get"+server+"Details&serverId="+resultSet.getString(1)+
	    	    		   "&type=modify'> Modify </a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='./modify"+server+"ServerDetails.do?param=delete"+server+"Details&serverId="+
	    	    		   resultSet.getString(1)+"&type=delete'>Delete</a>";   */
   	             
 	    		  optString="<a href=\"#\" onclick=\"modify"+strServer+"Detail('"+resultSet.getString(1)+"','"+resultSet.getString(4)+"');\">Modify</a>|<a href=\"#\" onclick=\"delete"+strServer+"Detail('"+resultSet.getString(1)+"');\"> Delete </a>";
 	    		     
 	    		       record=new HashMap();
   	                   record.put("Server_Id",resultSet.getString(1));
    	               record.put("Server_Name",resultSet.getString(2));
    	               record.put("Server_Status",resultSet.getString(3));
    	               record.put("optLink",optString);
    	               
    	    	       // Each server details set list
    	               if((resultSet.getString(4)).equals("1"))  {
    	            	  record.put("defaultStatus","<input type=\"checkbox\" id='"+strServer+"' value=\""+resultSet.getString(1)+"\" onclick=\"setAsDefault(this,"+resultSet.getString(1)+")\" checked>");
		    	      } 
    	    	       else
    	    	    	   record.put("defaultStatus","<input type=\"checkbox\" id='"+strServer+"' value=\""+resultSet.getString(1)+"\" onclick=\"setAsDefault(this,"+resultSet.getString(1)+")\">");
    	               
    	               serverList.add(record);
    	           } 
    	    	 } // end of if block
    	    
		    	 } // end of try block
		      catch(Exception exception) { //catch block starts
		    	  		logger.info("-----Exception raised in getServerList DAO-------");   
		    	  		logger.error(exception);
		    	        return null;
		    	 } // end of catch block
		      System.out.println("=========7============================");     
		       return serverList;
	 } 
	   // End of getServerList method
	 
	  /*
	   *  getServerDetails method is to get a single server details, server can be either LDAP or Mail
	   *
	   *  @param Server type
	   *  @param Server ID 
	   *  
	   *  @returns server details 
	   */
	    public static HashMap getServerDetails(String strServerType,String strServerId,String strDefaultId) {
                    HashMap serverRecord=null;
                     
                    try {  // try block starts
                    	   logger=Logger.getLogger("Admin");
                    	   connection=DBConnection.createConnection();
                    	   // 
                    	   if(strServerType.equalsIgnoreCase("LdapServer")) { // If block starts
             		    	preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Get_Ldap_Server @Server_Ldap_NBR="+strServerId);
       		    	     } //End of if block
       		    	    else {
       		   	  	    	preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Get_Mail_Server @Server_Mail_NBR="+strServerId+",@default_fg="+strDefaultId);
       		    	    }
       		    	      resultSet=preparedStatement.executeQuery();
           	        	    if(resultSet!=null) { // If result set not null 
           	          	if(resultSet.next()) { // If result set is not empty
           	          		 // If block starts
           	          		 serverRecord=new HashMap();
           	                 serverRecord.put("Server_Id",resultSet.getString(1));
		           	         serverRecord.put("Server_Name",resultSet.getString(2));
		           	         serverRecord.put("Server_Status",resultSet.getString(3));
           	          	} // End of If block     
           	          } // End of If	   
                    } // try block ends
                     catch(Exception exception) {
                    	logger.info("=======Exception in  getServerDetails Dao=========");
                    	logger.error(exception);
                    	return null;
                    }
                      return serverRecord;
	    }

	    /*
		 *  The deleteServerDetails()  method is to get a single server details, server can be either LDAP or Mail
		 *
		 *  @param Server type
		 *  @param Server ID 
		 *  
		 *  @returns operation result 
		 */    
	    
	    public static int deleteServerDetails(String strServerType,String strServerId) {
	                         int iServerId;
	                         result=1;
	    	        try { // try block starts
	    	        	     logger=Logger.getLogger("Admin");
                 	         connection=DBConnection.createConnection();
                 	         iServerId=Integer.parseInt(strServerId);
                 	         if(strServerType.equalsIgnoreCase("ldap")) { // If block starts
                 	          preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Delete_Ldap_Server @Server_Ldap_NBR="+iServerId);
                 	         } // end of If block
                 	         else if(strServerType.equalsIgnoreCase("mail")){ // Else block starts
                 	        	preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Delete_Mail_Server @Server_Mail_NBR="+iServerId);
                 	         } //End of Else block
                 	         
                 	          result=preparedStatement.executeUpdate();
                 	         
                  	         if(result==0)
                 	        	 return 1;
                 	   
	    	        } // try block Ends
	    	        catch(Exception exception) {
	    	        	logger.info("======Exception in deleteServerDetails DAO======");  
	    	        	logger.error(exception);
	    	       	    return 0;
	    	        } // catch block ends
	    	          return result;
	    }
	
	 /*
	  *  The persistSettings()  method is to persist home page configuration details
	  *
	  *  @param options
	  *  @param user id 
	  *  @param duration
	  *  
	  *  @returns operation result 
	  */    
	    public static int persistSettings(String[] strOptions,String strDuration,String strUserId) {
	              
	                  String sqlQuery=null;
	                  int count;
	                  int executeResult=0;
	             try { // try block starts
	            	   logger=Logger.getLogger("Admin");
	            	   connection=DBConnection.createConnection();
	            	  System.out.println("===== in persistSettings----------------");
	            	  if(strOptions==null){ // If starts
	            		  
	            		 System.out.println("=====strOptions is null----------------");
	            		 sqlQuery="update USER_TEST set CUST_OPTIONS=null where USER_ID='"+strUserId+"'";
	            	  } // If ends
	            	  else{ //Else starts 
		            		 System.out.println("=====strOptions is not null----------------");
	            	   sqlQuery="update USER_TEST set CUST_OPTIONS='";
	            	   for(count=0;count<(strOptions.length);count++) {
	            		   sqlQuery=sqlQuery+strOptions[count]+",";
	            	   }
	            	    // sqlQuery=sqlQuery+strOptions[count];
	            	   sqlQuery=sqlQuery+"' where USER_ID='"+strUserId+"'";
	            	  } // End of Else   
	            	   
	              	   statement=connection.createStatement();
	              	   System.out.println("==========statement=================="+sqlQuery);
	            	   executeResult=statement.executeUpdate(sqlQuery);
                    } // End of try block
	                 catch(Exception exception) { // catch block starts
                    	 logger.info("------exception in persistSettings DAO------");  
                    	 logger.error(exception);
                     } // End of catch block
	                 
	                  System.out.println("=========ExecuteResult=-========"+executeResult);
	                   return executeResult;
	    }
	    
	  //End of persistSettings() method
	    
	  /*
	   *   The getCustomFieldOptions method is for getting the fields option for customizing in the VIMS application
	   *   
	   *   @param session
	   */  
	    public static void getCustomFieldOptionsDAO(HttpSession session) {
	    	            
	    	           ArrayList fieldList=null;
	    	 try { // try block starts
	    		   logger=Logger.getLogger("Admin");
	    	       fieldList=new ArrayList();
	    	       
	    		   // Following are the fields which are seed data in the VIMS application and can be configurable
	    		   fieldList.add(0,"Country");
	    		   //fieldList.add(2,"State");
	    		   fieldList.add(1,"Employee Position");
	    		   fieldList.add(2,"Role based access");
	    		   fieldList.add(3,"State");
	    		   fieldList.add(4,"Work Location");
	    		   
	    	/*	   connection=DBConnection.createConnection();
	    		   preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Get_Designation @Designation_NBR=0,@Active_fg=0");
	    		   resultSet=preparedStatement.executeQuery();
	    		   
	    		   if(resultSet!=null) {
	    			    while(resultSet.next()) {
	    			          	
	    			    }
	    		   }*/
	    		   System.out.println("field set======"+fieldList);
	    		   // Set the fieldList into session 
	    		   session.setAttribute("fieldList",fieldList);
	    		   
	    	 } //try block ends 
	    	 catch(Exception exception) { // catch block starts
	    		 
	    		  logger.info("-------Exception raised in getCustomFieldOptionsDAO--------");
	    		  logger.error(exception);
	    	 } //End of catch block
	    	
	    }  // End of getCustomFieldOptionsDAO method
	    
	    
	 /*
	  *  The addPositionDAO method is for storing a new Position to VIMS Application
	  *
	  * @param strPosition as String
	  * 
	  * @return result as String   
	  */   
	    
	 public static String addPositionDAO(String strPositionId,String strPosition,String strPositionStatus,String strRoleType,String strUserId) {
		 
		         Logger logger=null; 
		 try { // try block starts
			   logger=Logger.getLogger("Admin");
			   
			   connection=DBConnection.createConnection();
			   preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Save_Designation @Designation_NBR="+strPositionId+",@Designation_NM='"+strPosition+"',@Designation_DESC=null,@user_nm='"+strUserId+"',@active_fg="+strPositionStatus+",@Role_NBR="+strRoleType);
			   preparedStatement.execute();
			   
			   resultSet=preparedStatement.getResultSet();
			   if(resultSet.next()) {
			   //get the operation result
			   executeResult=resultSet.getString(1);
			   } 
			   else
				executeResult="Error while Inserting into Database";   
			}  // try block ends
		  catch(Exception exception) { //catch block starts 
			  
			  logger.info("------Exception addPositionDAO-----------");
			  logger.error(exception);
			  return null;
		  } // catch block ends
		    return executeResult;
	 }
	   // End of addPositionDAO method
	
	 
	 
	/*
	 *  The getPositionListDAO method is to get all the Positions in VIMS application
	 * 
	 *  @returns position list as ArrayList 
	 */
	 
	  public static ArrayList getPositionListDAO(String strPositionId,String strUserId) {
		
		       ArrayList positionList=null;
		       HashMap record=null;
		       String optLink=null;
		  try {
			     logger=Logger.getLogger("Admin");
			     connection=DBConnection.createConnection();
			     // get the record from datatbase
			     preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Get_Designation @Designation_NBR="+strPositionId+",@Active_FG=null,@user_nm=null");
			     System.out.println(preparedStatement.execute());
			     
			     // get the resultset
			     resultSet=preparedStatement.getResultSet();
			     
			     // If block starts
			     if(resultSet!=null) {
                     positionList=new ArrayList();
                     System.out.println("========result set is not null=======");
                     while(resultSet.next()) {  //while loop starts
                      	 record=new HashMap();
                      	System.out.println("========records exist=======");
                      	 if(resultSet.getString("Active_FG").equals("0")) {
                      		 record.put("positionFlag","Active");
                      	 }
                      	 else if(resultSet.getString("Active_FG").equals("1")) {
                      		 record.put("positionFlag","Inactive");
                      	}
                      	 optLink="<a href='./modifyPositionDetails.do?param=modifyPosition&positionNumber="+resultSet.getString("Designation_NBR")+"'> Modify </a>";
                      	 optLink=optLink+" | <a href='./deletePositionDetails.do?param=deletePosition&positionNumber="+resultSet.getString("Designation_NBR")+"'> Delete </a>";
                      	 
                    	 record.put("positionTitle",resultSet.getString("Designation_NM"));
                    	 record.put("positionId",resultSet.getString("Designation_NBR"));
                    	 record.put("roleType",resultSet.getString("Role_NM"));
                    	 record.put("optLink",optLink);
                    	 
                    	 positionList.add(record);
                    } // while loop ends
			     } // If block ends
			     else  {
			    	 return null;
			     } 	 
		  } // try block ends
		   catch(Exception exception) {  // catch block starts
			
			   logger.info("------Exception in getPositionListDAO---------");
			   logger.error(exception);
			   exception.printStackTrace();
		   } // catch block ends
		   
		     // return the Postion List
		     return positionList;
	  }
	    // End of getPositionListDAO method
	  
	  
	  public static String deletePositionDAO(String strPositionId,String strUserId) {
		           
		  try {
			     logger=Logger.getLogger("Admin");
			     connection=DBConnection.createConnection();
			    
			     // get the record from datatbase
			     preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Delete_Designation @Designation_NBR="+strPositionId+",@user_nm='"+strUserId+"'");
			     System.out.println(preparedStatement.execute());
			     
			     // get the resultset
			     resultSet=preparedStatement.getResultSet();
			     
			     // If block starts
			     if(resultSet!=null) {
			    	 
			    	 if(resultSet.next()) {
			    	 executeResult=resultSet.getString(1);
			      }
			    	 else {
			    		 executeResult="Delete Action Problem";
			    	 }
			     }
			    
		  } catch(Exception exception) { // catch block starts
			  
			 logger.info("------Exception in deletePositionDAO-----------");
			 logger.error(exception);
			 return null;
		  }
		   return executeResult;
	  }
	  
	public static String insertCountryDetailsDAO(ConfigForm form,String strUserId,String strCountryId)   {
	                
		           String strResult=null;
		           String strCountryName=null;
		           String strStatus=null;
		           String[] strStateList=null;
		           
		    try {
		    	   connection=DBConnection.createConnection();
		    	   System.out.println("----testing this---------"+form.getCountryName());
		    	   if(form.getCountryName()!=null) {
		    		   strCountryName=form.getCountryName();
		    		   System.out.println("=====Country Name========="+strCountryName);
		    		}
		    	   if(form.getPositionStatus()!=null) {
		    		   strStatus=form.getPositionStatus();
		    		   System.out.println("=====Country Status========="+strStatus);
		    		}
		    	   if((form.getStatetList()!=null)&&(((String[])form.getStatetList()).length>0)) {
		    		   strStateList=form.getStatetList();
		    		   System.out.println("=====State List========="+strStateList);
		          }
		    	   //preparedStatement=connection.prepareStatement("");
		    	   
		    } catch(Exception exception) {
		    	  
		    	    logger.info("====Exception in insertCountryDeatailsDAO=-=========");
		    	    logger.error(exception);
		    	    exception.printStackTrace();
		       }
		    		return "test";
	}
	public static String setServerStatusDAO(String strServerType,String strServerId) {
		           
		            String strResult=null;
		 try {
                  connection=DBConnection.createConnection();
                  String query="EXEC vims_user.USP_Save_Dflt_Server @ID="+strServerId+",@Type='"+strServerType+"'";
                  System.out.println(query);
                  preparedStatement=connection.prepareStatement(query);
                  System.out.println(preparedStatement.executeQuery());
                  
                  resultSet=preparedStatement.getResultSet();
                  
                  if(resultSet!=null) {
                	  
                	   if(resultSet.next()) {
                		  strResult=resultSet.getString(1);
                		  System.out.println("=========strResult==========="+strResult);
                	   }
                	}
		      }
                  catch(Exception exception) {
			  logger.info("-----Exception in setServerStatusDAo method----------");
			  logger.error(exception);
			  exception.printStackTrace();
		  }
                   return strResult;
	} 
	
	
	public static HashMap getTabTreeDAO() {
		        
		        ArrayList tabList=null;
		        HashMap tabRecord=null;
		        ArrayList tempList=null;
		        
		        HashMap targetList=null;
	    try { 
	    	   logger=Logger.getLogger("Admin");
	    	  
	    	   tabList=new ArrayList();
	    	   tempList=new ArrayList();
	    	 // Support Center  
	    	  HashMap t=new HashMap();
	    	     t.put("name","Add Support Center");
	    	     t.put("id","10");
	    	     tempList.add(t);
	    	     
	    	     t=null;
	    	     t=new HashMap();
	    	     t.put("name","Edit Support Center");
	    	     t.put("id","11");
	    	     tempList.add(t);
	    	     
	    	     t=null;
	    	     t=new HashMap();
	    	     t.put("name","Add Group");
	    	     t.put("id","12");
	    	     tempList.add(t);
	    	     
	    	     t=null;
	    	     t=new HashMap();
	    	     t.put("name","Edit Group");
	    	     t.put("id","13");
	    	     tempList.add(t);
	    	     
	    	     
	    	     
	    	     
	    	     
	    	    /* tempList.add("Add Support Center");
	    	     tempList.add("Edit Support Center");
	    	     tempList.add("Add Group");
	    	     tempList.add("Edit Group");
	    	 */
	    	  tabRecord=new HashMap();
	    	  tabRecord.put("tabName","SupportCenter");
	    	  tabRecord.put("subMenu",tempList);
	    	  tabRecord.put("keyName","support");
	    	  tabList.add(tabRecord);
	    	 // End of Support Center  
	    	  
	    	  // Applications
	    	  
	    	    tempList=null;
	    	    tabRecord=null;
	    	  
	    	    tempList=new ArrayList();
	    	    tabRecord=new HashMap();
	    	  
	    	     t=null;
	    	     t=new HashMap();
	    	     t.put("name","Add Application");
	    	     t.put("id","14");
	    	     tempList.add(t);
	    	     
	    	  /*  tempList.add("Add Application");*/
	    	    
	    	    tabRecord.put("tabName","Applications");
	    	    tabRecord.put("subMenu",tempList);
	    	    tabRecord.put("keyName","application");
	    	    tabList.add(tabRecord);
	    	  // End of Applications
	    	    
	    	  // SLA
	    	    
	    	    tempList=null;
	    	    tabRecord=null;
	    	    
	    	    tempList=new ArrayList();
	    	    tabRecord=new HashMap();
	    	    
	    	    tabRecord.put("tabName","SLA");
	    	    tabRecord.put("subMenu",tempList);
	    	     
	    	    
	    	    tabList.add(tabRecord);
	    	    
	    	    // End of SLA
	    	   
	    	   // Employees
	    	    
	    	    tempList=null;
	    	    tabRecord=null;
	    	    
	    	    tempList=new ArrayList();
	    	    tabRecord=new HashMap();
	    	    
	    	     t=null;
	    	     t=new HashMap();
	    	     t.put("name","Add Employee");
	    	     t.put("id","15");
	    	     tempList.add(t);
	    	    /*tempList.add("Add Employee");*/
	    	    
	    	    tabRecord.put("tabName","Employees");
	    	    tabRecord.put("subMenu",tempList);
	    	    tabRecord.put("keyName","employee");
	    	    tabList.add(tabRecord);
	    	    
	    	    // End of Employees
	    	    
	    	    //	Customers
	    	    
	    	    tempList=null;
	    	    tabRecord=null;
	    	    
	    	    tempList=new ArrayList();
	    	    tabRecord=new HashMap();
	    	    
	    	     t=null;
	    	     t=new HashMap();
	    	     t.put("name","Add Customer");
	    	     t.put("id","16");
	    	     tempList.add(t);
	    	    /*tempList.add("Add Customer");*/
	    	    
	    	    tabRecord.put("tabName","Customers");
	    	    tabRecord.put("subMenu",tempList);
	    	    tabRecord.put("keyName","customer");
	    	    
	    	    tabList.add(tabRecord);
	    	    
	    	    // End of Customers
	    	    
	    	    
	    	    //	Issues
	    	    
	    	    tempList=null;
	    	    tabRecord=null;
	    	    
	    	    tempList=new ArrayList();
	    	    tabRecord=new HashMap();
	    	    
	    	     t=null;
	    	     t=new HashMap();
	    	     t.put("name","New Issue");
	    	     t.put("id","17");
	    	     tempList.add(t);
	    	     
	    	     t=null;
	    	     t=new HashMap();
	    	     t.put("name","List of Issues");
	    	     t.put("id","18");
	    	     tempList.add(t);
	    	     
	    	     t=null;
	    	     t=new HashMap();
	    	     t.put("name","Escalation");
	    	     t.put("id","19");
	    	     tempList.add(t);
	    	     
	    	     t=null;
	    	     t=new HashMap();
	    	     t.put("name","Purging");
	    	     t.put("id","20");
	    	     tempList.add(t);
	    	     
	    	    /*tempList.add("New Issue");
	    	    tempList.add("List of Issues");
	    	    tempList.add("Escalation");
	    	    tempList.add("Purging");*/
	    	    
	    	    tabRecord.put("tabName","Issues");
	    	    tabRecord.put("subMenu",tempList);
	    	    tabRecord.put("keyName","issue");
	    	    
	    	    tabList.add(tabRecord);
	    	    
	    	    // End of Issues
	    	    
	    	    //	Reports
	    	    
	    	    tempList=null;
	    	    tabRecord=null;
	    	    
	    	    tempList=new ArrayList();
	    	    tabRecord=new HashMap();
	    	    
	    	     t=null;
	    	     t=new HashMap();
	    	     t.put("name","Issues in Application Pool");
	    	     t.put("id","21");
	    	     tempList.add(t);
	    	     
	    	     t=null;
	    	     t=new HashMap();
	    	     t.put("name","Issues in Specific Status");
	    	     t.put("id","22");
	    	     tempList.add(t);
	    	     
	    	     t=null;
	    	     t=new HashMap();
	    	     t.put("name","SLA Information");
	    	     t.put("id","23");
	    	     tempList.add(t);
	    	     
	    	     t=null;
	    	     t=new HashMap();
	    	     t.put("name","Custom Reports");
	    	     t.put("id","24");
	    	     tempList.add(t);
	    	     
	    	    /*tempList.add("Issues in Application Pool");
	    	    tempList.add("Issues in specific Status");
	    	    tempList.add("SLA Information");
	    	    tempList.add("Custom Reports");*/
	    	    
	    	    tabRecord.put("tabName","Reports");
	    	    tabRecord.put("subMenu",tempList);
	    	    tabRecord.put("keyName","report");
	    	    
	    	    tabList.add(tabRecord);
	    	    
	    	    // End of Reports
	    	    
	    	    //	Search
	    	    
	    	    tempList=null;
	    	    tabRecord=null;
	    	    
	    	    tempList=new ArrayList();
	    	    tabRecord=new HashMap();
	    	    
	    	    tabRecord.put("tabName","Search");
	    	    tabRecord.put("subMenu",tempList);
	    	    
	    	    tabList.add(tabRecord);
	    	    
	    	    // End of Search

	    	    ArrayList list=new ArrayList();
	    	    
	    	    /*list.add("Support Manager");
	    	    list.add("Group Supervisor");
	    	    list.add("Employee");
	    	    list.add("Customer");*/
	    	    list=getRoleTypesDAO();
	    	    
	    	    targetList=new HashMap();
	    	    
	    	    targetList.put("tabList",tabList);
	    	    targetList.put("roleTypes",list);
	    	    
	    }
	     catch(Exception exception) {
	    	 logger.info("--------Exception in getTabTreeDAO-----------");
	    	 logger.error(exception);
	    	 exception.printStackTrace();
	     }
	     
	       return targetList;
	}
	
	/*
	 * The getRoleTypesDAO() method is for retrieving the role types of VIMS
	 * 
	 * @return arrayList
	 * 
	 */
	  public static ArrayList getRoleTypesDAO() {
		           Logger logger=null;
		           Connection connection=null;
		           PreparedStatement preparedStatement=null;
		           ResultSet resultSet=null;
		           HashMap hashMap=null;
		           
		           ArrayList roleTypes=null;
		   try { // try block starts
			      logger=Logger.getLogger("Admin");
			      connection=DBConnection.createConnection();
			      preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Get_Role @Role_NBR=0,@Active_FG=0");
			      resultSet=preparedStatement.executeQuery();
			      System.out.println("===========");
			      if(resultSet!=null) {  // if block starts
			    	  // create a list of role type
			    	  roleTypes=new ArrayList();  
			    	   while(resultSet.next()) { // while block starts
			    		    hashMap=new HashMap();
			    		    
			    		    hashMap.put("roleId",resultSet.getString("Role_NBR"));
			    		    hashMap.put("roleName",resultSet.getString("Role_NM"));
			    		    
			    		    // add a role type to list
			    		    roleTypes.add(hashMap);
			    	   }// while block ends
			      }// if block ends
			   } // try block ends
		        catch(Exception exception) {  //catch block starts
			    	logger.info("-----Exception in getRoleTypesDAO method----------");
			    	logger.error(exception);
			    	exception.printStackTrace();
		      }// catch block ends
		         return roleTypes;
	   }
	  
	     // End of getRoleTypesDAO() method
	  
	public static String insertRoleTypeDAO(String strRoleId,String strRoleName,String strDescription,String strFlag) {
		       Logger logger=null;
		       Connection connection=null;
		       PreparedStatement preparedStatement=null;
		       ResultSet resultSet=null;
		       String executeResult=null;
         try {
        	   logger=Logger.getLogger("Admin");
        	   connection=DBConnection.createConnection();
        	   preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Save_Role @Role_NBR="+strRoleId+",@Role_NM='"+strRoleName+"',@Role_DESC=null,@Active_FG="+strFlag);
        	   System.out.println("role result====="+preparedStatement.execute());
        	   
        	   resultSet=preparedStatement.getResultSet();
        	   
        	   if(resultSet!=null) {
        		   if(resultSet.next()) {
        			   executeResult=resultSet.getString(1);
        		   }
        	   }
        	   else
        		   logger.info("Internal problem in insertRoleTypeDAO=====");
         }
          catch(Exception exception) {
        	  logger.info("----Exception in insertRoleTypeDAO method--------");
        	  logger.error(exception);
        	  exception.printStackTrace();
          }
            return executeResult;
	}
	
	public static ArrayList getRoleListDAO(String roleId) {
	            Logger logger=null;
	            Connection connection=null;
	            PreparedStatement preparedStatement=null;
	            ResultSet resultSet=null;
	            ArrayList list=null;
	            HashMap record=null;
	            String actionLink=null;
		  try {
			    logger=Logger.getLogger("Admin");
			    connection=DBConnection.createConnection();
			    preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Get_Role @Role_NBR="+roleId+",@Active_FG=null");
			    resultSet=preparedStatement.executeQuery();
			  
			    if(resultSet!=null) {
			    	list=new ArrayList();
			    	while(resultSet.next()) {
			    		record=new HashMap();
			    		//actionLink="<a href=\"./modifyRoleDetails.do?param=modifyRole&roleNumber="+resultSet.getString("Role_NBR")+"\">Modify</a> | <a href=\"./deleteRoleType.do?param=deleteRole&roleNumber="+resultSet.getString("Role_NBR")+"\">Delete</a>";
			    		actionLink="<a href=\"./modifyRoleDetails.do?param=modifyRole&roleNumber="+resultSet.getString("Role_NBR")+"\">Modify</a>";
			    		record.put("roleId",resultSet.getString("Role_NBR"));
			    		record.put("roleName",resultSet.getString("Role_NM"));
			    		if(resultSet.getString("Active_FG").equals("0"))
			    		    record.put("roleStatus","Active");
			    		else
			    	    	record.put("roleStatus","Inactive");
			    		
			    		 record.put("actionLink",actionLink);
			    		 list.add(record);
			    	}
			    }
		  }
		   catch(Exception exception) {
			   logger.info("Exception in getRoleListDAO method========");
			   logger.error(exception);
			   exception.printStackTrace();
		   }
		    return list;
	}

	public static String deleteRoleTypeDAO(String strRoleId) {
        
		  try {
			     logger=Logger.getLogger("Admin");
			     connection=DBConnection.createConnection();
			    
			     // get the record from datatbase
			     preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Delete_Role @Role_NBR="+strRoleId);
			     System.out.println(preparedStatement.execute());
			     
			     // get the resultset
			     resultSet=preparedStatement.getResultSet();
			     
			     // If block starts
			     if(resultSet!=null) {
			    	 
			    	 if(resultSet.next()) {
			    	 executeResult=resultSet.getString(1);
			      }
			    	 else {
			    		 executeResult="Delete Action Problem";
			    	 }
			     }
			    
		  } catch(Exception exception) { // catch block starts
			  
			 logger.info("------Exception in deleteRoleDAO-----------");
			 logger.error(exception);
			 return null;
		  }
		   return executeResult;
    }
	
	public static ArrayList getNavigationSetDAO(String strRoleId,String strUserId) {
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
            	   
            	   sqlQuery="EXEC vims_user.USP_Get_Role_Permission @Role_NBR="+strRoleId+",@User_NM='"+strUserId+"'";
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
            					if(loopResultSet.getString("Permission_FG").equals("1"))
            					  tempRecord.put("permissionFlag","checked");
            					else
            						tempRecord.put("permissionFlag","");
            					
            					tempList.add(tempRecord);
            					count++;
            				  }
            			   }
                        	 record.put("tabName",resultSet.getString("Application_NM"));
                             record.put("keyName",resultSet.getString("Key_Name"));
                             record.put("tabId",iPageId);
                             record.put("subMenu",tempList);
                             if(iPermissionFlag==1)
                              record.put("permissionFlag","checked");
                             else
                             	record.put("permissionFlag","");	
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
            	 logger.info("------Exception in getNavigationSetDAO-----------");
     			 logger.error(exception);
     			 exception.printStackTrace();
     			 return null;
              }
              System.out.println("====Main List========"+list);
              return list;
	}

    public static int storeRoleNavigSettingsDAP(HttpServletRequest request,String strUserId) {
                int result=1;
                Logger logger=null;
                Enumeration paramNames=null;
                String[] paramValues=null;
                String strParameter=null;
                String strSelectedRole=null;
                String sqlQuery=null;
                String strPageIdSet=null;                   
                Connection connection=null;
                PreparedStatement preparedStatement=null;
                ResultSet resultSet=null;
    	   try {
    		      logger=Logger.getLogger("Admin");
    		      connection=DBConnection.createConnection();
    		      paramNames=request.getParameterNames();
       		     
    		      strSelectedRole=request.getParameter("roleSelected");
    		      System.out.println("====Selected Role======="+strSelectedRole);
    		      strPageIdSet=new String();
       		   while(paramNames.hasMoreElements()) {
       			strParameter=(String)paramNames.nextElement();
       			   System.out.println("====strTemp======"+strParameter);
       			   if(strParameter.equals("roleSelected")||(strParameter.equals("param"))) {
       				   System.out.println("continued--------");
       				   continue;
       			   }	
       			   paramValues=request.getParameterValues(strParameter);
       			   if(paramValues!=null) {
       				    for(int count=0;count<paramValues.length;count++) {
       				       System.out.println("====value======="+paramValues[count]);
       				       strPageIdSet=strPageIdSet+","+paramValues[count];
       				       
       				   }
       			   }
       		   }
       		    sqlQuery="EXEC vims_user.USP_Save_Role_Permission @Role_Permission_NBR=1,@Role_NBR="+strSelectedRole+",@Application_NBR='"+strPageIdSet+"',@User_NM='"+strUserId+"'";
       		    System.out.println("==sql query==="+sqlQuery);
       		    preparedStatement=connection.prepareStatement(sqlQuery);
       		 System.out.println(preparedStatement.execute());
       		    preparedStatement.close();
			    preparedStatement=null;
    	   }
    	    catch(Exception exception) {
    	    	logger.info("----Exception in storeRoleNavigSettings---------");
    	    	logger.error(exception);
    	    	exception.printStackTrace();
    	    	return 0;
    	    }
    	     return result;
    }


    public static ArrayList getLocationDetailsDAO(String strLocationId,String strStatus,String strUserId) {
                 Logger logger=null;
                 Connection connection=null;
                 PreparedStatement preparedStatement=null;
                 ResultSet resultSet=null;
                 String executeResult=null;
                 ArrayList list=null;
                 HashMap record=null;
                 
                 String actionLink=null;
    	   try {
    		      logger=Logger.getLogger("Admin");
    		      connection=DBConnection.createConnection();
    		      preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Get_Location @Office_Loc_NBR="+strLocationId+",@Active_FG="+strStatus+",@User_NM='"+strUserId+"'");
    		      System.out.println("EXEC vims_user.USP_Get_Location @Office_Loc_NBR="+strLocationId+",@Active_FG="+strStatus+",@User_NM='"+strUserId+"'");
    		      System.out.println(preparedStatement.execute());
    		      
    		      resultSet=preparedStatement.getResultSet();
    		      if(resultSet==null)
    		    	  System.out.println("====resultset is nulll");
    		      if(resultSet!=null) {
    		    	  list=new ArrayList();      		    	   
    		    	   while(resultSet.next()) {
    		                record=new HashMap();
    		                actionLink="<a href=\"./modifyLocationDetails.do?param=modifyLocation&locationId="+resultSet.getString("Office_Loc_NBR")+"\">Modify</a>";
    		                record.put("locationId",resultSet.getString("Office_Loc_NBR"));
    		                record.put("locationName",resultSet.getString("Office_Loc_NM"));
    		                if(resultSet.getString("Active_Fg").equals("0"))
    		                 record.put("locationStatus","Active");
    		                else
    		                	record.put("locationStatus","Inactive");
    		                
    		                record.put("actionLink",actionLink);
    		                list.add(record);
    		    	   }
    		      }
    		            resultSet.close();
    		            preparedStatement.close();
    		            connection.close();
    		           if(list==null)
    		        	   System.out.println("--list is null");
    		             		            
    		          if(list.size()>0) {
    		        	  System.out.println("list size==========="+list.size());
    		        	  return list;
    		          }  	  
    	   }
    	    catch(Exception exception) {
    	    	   logger.info("-------Exception in getLocationDetailsDAO-----");
    	    	   logger.error(exception);
    	    	   exception.printStackTrace();
    	    	   return null;
    	    }
    	          System.out.println("===RETURNING NULL");
    	             return null;
     }
   
    public static String insertLocationDetailsDAO(String strLocationId,String strLocation,String strLocationStatus,String strUserId) {
    	      
    	      Logger logger=null;
    	      String executeResult=null;
    	      Connection connection=null;
    	      PreparedStatement preparedStatement=null;
    	      ResultSet resultSet=null;
    	      
    	try { // try block starts
			   logger=Logger.getLogger("Admin");
			   
			   connection=DBConnection.createConnection();
			   preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Save_Location @Office_Loc_NBR="+strLocationId+",@Office_Loc_NM='"+strLocation+"',@Devl_Ctr_NM=null,@Addr_TXT=null,@State_NM=null,@Pincode_TXT=null,@Contact_NBR=null,@Fax_NBR=null,@Ofc_Email_Addr=null,@Active_FG="+strLocationStatus+",@user_nm='"+strUserId+"'");
			   preparedStatement.execute();
			   System.out.println("EXEC vims_user.USP_Save_Location @Office_Loc_NBR="+strLocationId+",@Office_Loc_NM='"+strLocation+"',@Devl_Ctr_NM=null,@Addr_TXT=null,@State_NM=null,@Pincode_TXT=null,@Contact_NBR=null,@Fax_NBR=null,@Ofc_Email_Addr=null,@Active_FG="+strLocationStatus+",@user_nm='"+strUserId+"'");
			   resultSet=preparedStatement.getResultSet();
			   if(resultSet.next()) {
			   //get the operation result
			   executeResult=resultSet.getString(1);
			   } 
			   else
				executeResult="Error while Inserting into Database";   
			}  // try block ends
		  catch(Exception exception) { //catch block starts 
			  
			  logger.info("------Exception insertLocationDetailsDAO-----------");
			  logger.error(exception);
			  exception.printStackTrace();
			  return null;
		  } // catch block ends
		    return executeResult;
    }
    
		    public static String getRoleNameDAO(String strRoleId) {
		        Connection connection=null;
		        Statement statement=null;
		        ResultSet resultSet=null;
		        String strRoleName=null;
		        String sqlQuery="select * from vims_user.TRole where Role_NBR='"+strRoleId+"'";
		 try {
		  	   connection=DBConnection.createConnection();
		  	   statement=connection.createStatement();
		  	   resultSet=statement.executeQuery(sqlQuery);
		  	   
		  	   if(resultSet!=null) {
		  		    if(resultSet.next()) {
		  		    	strRoleName=resultSet.getString("Role_NM"); 
		  		    }
		  	   }
		  }
		   catch(Exception exception) {
		  	  System.out.println("=======Exception in getRoleNameDAO method========="+exception);
		  	  exception.printStackTrace();
		   }
		      return strRoleName;
		}
		    
		public static ArrayList getCountryListDAO(String strCountryCode,String strCountryStatus) {
			       Logger logger=null;
			       Connection connection=null;
			       PreparedStatement preparedStatement=null;
			       ResultSet resultSet=null;
			       String sqlQuery=null;
			       ArrayList list=null;
			       HashMap record=null;
			       
			       String strStatus=null;
			try {
				   logger=Logger.getLogger("Admin");
				   sqlQuery="EXEC vims_user.USP_Get_Country @Country_NBR='"+strCountryCode+"',@Country_NM=null,@Active_FG=null";
				    connection=DBConnection.createConnection();
				    preparedStatement=connection.prepareCall(sqlQuery);
				    System.out.println(preparedStatement.execute());
				    resultSet=preparedStatement.getResultSet();
				    
				    if(resultSet!=null) {
				         list=new ArrayList();   	
				    	 while(resultSet.next()) {
				    		 record=new HashMap();
				    		 
				    		 record.put("countryName",resultSet.getString("Country_NM"));
				    		 record.put("countryId",resultSet.getString("Country_NBR"));
				    		 
				    		 strStatus=resultSet.getString("Active_FG");
				    		 if(strStatus.equalsIgnoreCase("0"))
				    		 {
				    			 strStatus="Active";
				    			 record.put("status",strStatus);
					    		 record.put("actionLink","<a href=\"./modifyCountry.do?param=modifyCountryDetails&CountryId="+resultSet.getString("Country_NBR")+"\">Modify</a>");
					    		 
				    		 }
				    		 else
				    		 {
				    			 strStatus="Inactive";
				    			 record.put("status",strStatus);
					    		 record.put("actionLink","<a href=\"./modifyCountry.do?param=modifyCountryDetails&CountryId="+resultSet.getString("Country_NBR")+"\">Modify</a> | <a href=\"#\" onclick=\"fnDelete('"+(String)resultSet.getString("Country_NBR")+"')\"/>Delete</a>");
					    		 
				    		 }
				    		 list.add(record);
				    	 }  
				    }
				    
			}
			 catch(Exception exception) {
				  logger.info("-----Exception in getCountryListDAO---------");
				  logger.error(exception);
				  exception.printStackTrace();
				  return null;
			 }
			     return list;
		}
		
	public static String insertCountryDetailsDAO(String strCountryId,String strCountry,String strCountryStatus) {
  	      
  	      Logger logger=null;
  	      String executeResult=null;
  	      Connection connection=null;
  	      PreparedStatement preparedStatement=null;
  	      ResultSet resultSet=null;
  	      
  	try { // try block starts
			   logger=Logger.getLogger("Admin");
			   
			   System.out.println("========Country ID=========="+strCountryId);
			   System.out.println("========strCountry=========="+strCountry);
			   System.out.println("========strCountryStatus=========="+strCountryStatus);
			   
			   connection=DBConnection.createConnection();
			   preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Save_Country @Country_NBR="+strCountryId+",@Country_NM='"+strCountry+"',@Active_FG="+strCountryStatus);
			   System.out.println(preparedStatement.execute());
			   System.out.println("EXEC vims_user.USP_Save_Country @Country_NBR="+strCountryId+",@Country_NM='"+strCountry+"',@Active_FG="+strCountryStatus);
			   resultSet=preparedStatement.getResultSet();
			   if(resultSet.next()) {
			   //get the operation result
			   executeResult=resultSet.getString(1);
			   } 
			   else
				executeResult="Error while Inserting into Database";   
			}  // try block ends
		  catch(Exception exception) { //catch block starts 
			  
			  logger.info("------Exception insertCountryDetailsDAO-----------");
			  logger.error(exception);
			  exception.printStackTrace();
			  return null;
		  } // catch block ends
		    return executeResult;
  }

	public static ArrayList getCountryList()
	{
		Connection connection=null;
	     PreparedStatement preparedStatement=null;
	     ResultSet resultSet=null;
	     String sqlQuery=null;
	     ArrayList list=null;
	     HashMap record=null;
	     
	     try 
	     {
			   logger=Logger.getLogger("Admin");
			   sqlQuery="EXEC vims_user.USP_Get_Country @Country_NBR=0,@Country_NM=null,@Active_FG=0";
			   connection=DBConnection.createConnection();
			    preparedStatement=connection.prepareCall(sqlQuery);
			    System.out.println(preparedStatement.execute());
			    resultSet=preparedStatement.getResultSet();
			    
			    if(resultSet!=null)
			    {
			         list=new ArrayList();   	
			    	 while(resultSet.next())
			    	 {
			    		 record=new HashMap();
			    		 
			    		 record.put("countryName",resultSet.getString("Country_NM"));
			    		 record.put("countryId",resultSet.getString("Country_NBR"));
			    		 
			    		 list.add(record);
			    	 }
			    }
			    if(resultSet!=null){resultSet.close();}
			    if(preparedStatement!=null){preparedStatement.close();}
			    if(connection!=null){connection.close();}
	     }
	     catch(Exception e)
	     {
	    	 e.printStackTrace();
	    	 logger.error(e);
	     }
		return list;
	}

	public static ArrayList getStateList(String strCountryID, String strSateID)
	{
		logger=Logger.getLogger("Admin");
		
		ArrayList StateList=new ArrayList();
		
		Connection connection=null;
		CallableStatement callableStatement=null;
		ResultSet resultset=null;
		HashMap record=null;
		
		try
		{
			connection=DBConnection.createConnection();
			callableStatement=connection.prepareCall("{CALL USP_Get_State(?,?,?,?)}");
			callableStatement.setString(1,strCountryID);
			callableStatement.setString(2,strSateID);
			callableStatement.setString(3,null);
			callableStatement.setString(4,null);
			callableStatement.execute();
			
			resultset=callableStatement.getResultSet();
			
			if(resultset!=null)
			{
				while(resultset.next())
				{
					record=new HashMap();
					record.put("countryID",resultset.getString(1));
					record.put("countryName",resultset.getString(2));
					record.put("stateID",resultset.getString(3));
					record.put("stateName",resultset.getString(4));
					if(resultset.getString(5).equalsIgnoreCase("0"))
					{
					 record.put("stateStatus","Active");
					 record.put("actionLink","<a href=\"./StateAction.do?param=modifyStateDetails&stateID="+resultset.getString(3)+"\">Modify</a>");
					}
					else
					{
						record.put("stateStatus","Inactive");
						record.put("actionLink","<a href=\"./StateAction.do?param=modifyStateDetails&stateID="+resultset.getString(3)+"\">Modify</a> | <a href=\"#\" onclick=\"fnDelete('"+(String)resultset.getString(3)+"')\"/>Delete</a>");
					}
					System.out.println("=========Record Values============"+record);
					StateList.add(record);
				}
			}
			if(connection!=null){connection.close();}
			if(callableStatement!=null){callableStatement.close();}
			if(resultset!=null){resultset.close();}
		} 
		catch (SQLException e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		return StateList;
	}

	public static String SaveStateDetails(String strCountryID,String strStateID, String strStateName, String strStateStatus)
	{
		logger=Logger.getLogger("Admin");
		
		Connection connection=null;
     	CallableStatement callableStatement=null;
     	ResultSet resultset=null;
		
     	String strResponse=null;
     	try 
     	{
			connection=DBConnection.createConnection();
			callableStatement=connection.prepareCall("{CALL USP_Save_State(?,?,?,?)}");
			callableStatement.setString(1,strCountryID);
			callableStatement.setString(2,strStateID);
			callableStatement.setString(3,strStateName);
			callableStatement.setString(4,strStateStatus);
			callableStatement.execute();
			resultset=callableStatement.getResultSet();
			
			if(resultset!=null)
			{
			 while(resultset.next())
			 {
				strResponse=resultset.getString(1);
			 }
			}
			if(connection!=null){connection.close();}
			if(callableStatement!=null){callableStatement.close();}
			if(resultset!=null){resultset.close();}
		} 
     	catch (SQLException e)
     	{
			logger.error(e);
			e.printStackTrace();
		}
     	return strResponse;
	}
	
	public static ArrayList getFooterOptionsDAO(String strOptionId) {
		 Logger logger=null;
		 ArrayList list=null;
		 HashMap record=null;
		 Connection connection=null;
		 PreparedStatement preparedStatement=null;
		 ResultSet resultSet=null;
	
		 try {
			    logger=Logger.getLogger("Admin"); 
			    connection=DBConnection.createConnection();
			    preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Get_Vims_Contnt @Vims_Contnt_NBR="+strOptionId+",@Active_FG=0");
			    
			    System.out.println(preparedStatement.execute());
			    resultSet=preparedStatement.getResultSet();
			    list=new ArrayList();
			    
			    if(resultSet!=null) {
			    	
			    	while(resultSet.next()) {
			             record=new HashMap();
			             
			             record.put("id",resultSet.getString("Vims_Contnt_NBR"));
			             record.put("name",resultSet.getString("Name"));
			             record.put("flag",resultSet.getString("Active_FG"));
			             record.put("content",resultSet.getString("Contnt_TXT"));
			             
			             list.add(record);
			    	}
			    }
			      resultSet.close();
			      preparedStatement.close();
			      connection.close();
			    /*list=new ArrayList();
			    
			    record=new HashMap();
			    record.put("id","faq");
			    record.put("name","FAQ");
			    list.add(record);
			    
			    record=null;
			    record=new HashMap();
			    record.put("id","Office Locations");
			    record.put("name","Office Locations");
			    list.add(record);
			    
			    record=null;
			    record=new HashMap();
			    record.put("id","About Us");
			    record.put("name","About Us");
			    list.add(record);
			    
			    record=null;
			    record=new HashMap();
			    record.put("id","Terms and Conditions");
			    record.put("name","Terms and Conditions");
			    list.add(record);
			    
			    record=null;
			    record=new HashMap();
			    record.put("id","Privacy Policy");
			    record.put("name","Privacy Policy");
			    list.add(record);*/
			 }  
		  catch(Exception exception) {
			  logger.info("---Exception in getFooterOptionsDAO method----");
			  logger.error(exception);
			  exception.printStackTrace();
			  return null;
		  }
		   return list;
	}
	
	public static String storeContentDAO(String strOptionId,String strContent) {
                Logger logger=null;
                Connection connection=null;
                PreparedStatement preparedStatement=null;
                ResultSet resultSet=null;
                String executeResult=null;
		   try {
			      logger=Logger.getLogger("Admin");
			      connection=DBConnection.createConnection();
			      preparedStatement=connection.prepareStatement("EXEC vims_user.USP_Save_Vims_Contnt @Vims_Contnt_NBR="+strOptionId+",@Contnt_TXT='"+strContent+"',@Active_FG=0");
			      System.out.println(preparedStatement.execute());
			      
			      resultSet=preparedStatement.getResultSet();
			      
			      if(resultSet!=null) {
			    	  if(resultSet.next()) {
			    		  return resultSet.getString(1);
			    	  }
			      }
		     }
		      catch(Exception exception) {
		    	 logger.info("Exception in storeContentDAO======");
		    	 logger.error(exception);
		    	 exception.printStackTrace();
		    }
		    return null;
	}
}