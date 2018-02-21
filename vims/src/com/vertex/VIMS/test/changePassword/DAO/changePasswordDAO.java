/*
  FileName	    : changePasswordDAO.java
  
 
  Description	: This Data Access Object class is used to call different queries 
  
                  from query interface class and access those Queries where needed. 
    
  Developed by  : Vertex Computer Systems.
                  copyright (c) 2008 Vertex.
                  All rights reserved.
  
  Change History:
 
  Revision No.	:		Date		  @author		Description
    1.0				Friday 15,2008	  Geeta.M	   Initial Version.*/


//Package structure followed for this class.
package com.vertex.VIMS.test.changePassword.DAO;

//import statements
import java.sql.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.vertex.VIMS.test.VIMSSQLQueryInterface.VIMSQueryInterface;
import com.vertex.VIMS.test.changePassword.form.changePasswordForm;
import com.vertex.VIMS.test.common.DBConnection;
import com.vertex.VIMS.test.common.VIMSMail;

//Start of changePasswordDAO class
public class changePasswordDAO 
{
	
   /**********Method Name:-------->changePwdDAO ****************/
	      
   /**********In this method the integer value is retrieved from database to check the flag  *********/    
	      
	public static int  changePwdDAO(String getNewPassword,String strUserID)
	{
	        int intValue=0;
				  
	           try
	               {
                       Connection connection=DBConnection.createConnection();
					  
					 
					   CallableStatement callablestmt=null;
					   
					   //String stroldPassword=changePwdForm.getOldPassword();
					  // System.out.println("-----stroldPassword----"+stroldPassword);
					  
					   //String strNewPassword=changePwdForm.getNewPassword();
					   //System.out.println("-----strNewPassword----"+strNewPassword);
					  
					   callablestmt=connection.prepareCall("{CALL vims_user.USP_Save_Pwd(?,?)}");	
					  // callablestmt.registerOutParameter(1,Types.OTHER);
					 
					   callablestmt.setString(1,getNewPassword);
					   callablestmt.setString(2,strUserID);
					   
					   callablestmt.executeUpdate();
				       //System.out.println("------Out of changePwdDAO-------");
					   //intValue=callablestmt.getInt(1);
					   //System.out.println("--------intValue---------"+intValue);
					
				    }
				   catch (Exception exception)
					   {
						 System.out.println("IN Exception============>"+exception);
						 exception.printStackTrace();
					   }
				return intValue;
			
         }
	
  /**********Method Name:-------->checkOldPasswordDAO ****************/
    
  /**********In this method the boolean value is retrieved from database to be display whether the old password entered matches or not*********/    
		
  public static boolean checkOldPasswordDAO(String getOldPassword,String strUserID) 
	{
	 
//Adding all the exception to the getLogger method for admin module  
   Logger logger=Logger.getLogger("Admin"); 
   boolean isFind=false;
	 //Start of try block 
	   try
	    {
		 Connection connection=DBConnection.createConnection();
	     CallableStatement callablestmt=null;
	     ResultSet resultSet=null;
	     HashMap hashMap=null;
	     String strValue=null;
	  	     
	     callablestmt=connection.prepareCall("{CALL vims_user.USP_Check_Login(?,?)}");
		 callablestmt.setString(1,strUserID);
		 callablestmt.setString(2,getOldPassword);
			 
		 resultSet = callablestmt.executeQuery();
			 
		 while (resultSet.next())
	   	 {
			 strValue=resultSet.getString(3);
	   	 }  
	         if(strValue.equalsIgnoreCase(getOldPassword))
	         {
	        	 return true;
	         }
	    }
	   //start of catch block  
		  catch(Exception exception)
		  {
			  System.out.println("The Exception is:"+exception);
			  return false;
		  }
	return isFind;
		
	//end of catch block
   }
}