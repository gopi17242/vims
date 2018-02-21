package com.vertex.VIMS.test.login.BD;
  
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.vertex.VIMS.test.clients.DAO.VIMSClientDAO;
import com.vertex.VIMS.test.login.DAO.*;
 
public class LoginBD
{ 

	public static String UserType(String strUserId, String strPassword)
	{
		String strUserType;
		
		strUserType=LoginDAO.getUserType(strUserId,strPassword);
		System.out.println("======bd===="+strUserType); 
		
		if(strUserType.equals("Admin"))
		{
			return "Admin";
		}
		else if(strUserType.equals("User"))
		{
			return "User";
		}
		else if(strUserType.equals("Customer"))
		{
			return "Customer";
		}
		else if(strUserType.equals("loginerror"))
		{
			return "loginerror";
		}
		else
		{
			return strUserType;
		}
	}
	public static String viewLoginClientStatusBD(String strUserId,String strPassword) 
	  {
		 Logger logger=Logger.getLogger("Admin");  
		try
	      {
			String strStatus=LoginDAO.viewLoginClientStatusDAO(strUserId,strPassword);
	        return strStatus;
		  }
     //End of try block
	
     //Start of catch block	
	     catch(Exception exception)
	      {
	    	  logger.info("LoginBD.viewLoginClientStatusBD");
	    	  logger.error(exception);      
	    	  System.out.println("The Exception is:"+exception);
	    	  return null;
        }		
	  }
	public static String getAppOwnerNameBD(String strUserId) 
	{
		Logger logger=Logger.getLogger("Admin");  
		try
	      {
			String strName=LoginDAO.getAppOwnerNameDAO(strUserId);
			return strName;
		  }
		 catch(Exception exception)
	      {
	    	  logger.info("LoginBD.viewLoginClientStatusBD");
	    	  logger.error(exception);      
	    	  System.out.println("The Exception is:"+exception);
	    	  return null;
          }		
		// TODO Auto-generated method stub
		 
	}

	public static void applyCustomSettings(HttpServletRequest request,HttpServletResponse response,String strUserId) {
		 
		   Logger logger=null;
		   try {
			     logger=Logger.getLogger("Admin");
			     LoginDAO.applyCustomeSettings(request, response, strUserId);
		   } catch(Exception exception) {
			   
			    logger.info("------applyCustomSettings-------BD---------");
			    logger.error(exception);
		   }
	}
	
	public static String getVIMSApplicationVersionBD() {
		      Logger logger=null;
		      String strVersion=null;
		try {
			  logger=Logger.getLogger("Admin");
			  strVersion=LoginDAO.getVIMSApplicationVersionDAO();
			  
		} catch(Exception exception) {
			   logger.info("----Exception raised in getVIMSApplicationVersionBD------------");
			   logger.error(exception);
		}
		  return strVersion;
	}  
	
	public static int validateViewPermissionBD(String strUserId,String strIssueId) {
	           
		    return LoginDAO.validateViewPermissionDAO(strUserId,strIssueId);
	}
	public static int getLoginIDNumber(String strUserId, String strPassword)
	{
		
		return LoginDAO.getLoginIDNumber(strUserId,strPassword);
	}
	
	public static String getRoleIdBD(String strUserType) {
		  return LoginDAO.getRoleIdDAO(strUserType);
	}
	
	public static ArrayList getUserNavigSettingsBD(String strUserId) {
	        return LoginDAO.getUserNavigSettingsDAO(strUserId);	
	}
}
