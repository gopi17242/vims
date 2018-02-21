/*
  FileName	    : changePasswordBD.java
  
 
  Description	: This BD class is used to call different methods 
                  in DAO(Data Access Object) and set the return values 
  
                   
  Developed by  : Vertex Computer Systems.
                  copyright (c) 2008 Vertex.
                  All rights reserved.
  
  Change History:
 
  Revision No.	:		Date		  @author		Description
    1.0				Friday 15,2008	  Geeta.M	   Initial Version.*/

//Package structure followed for this class.
package com.vertex.VIMS.test.changePassword.BD;

//import statements 
import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.vertex.VIMS.test.changePassword.DAO.changePasswordDAO;
import com.vertex.VIMS.test.changePassword.form.changePasswordForm;
import com.vertex.VIMS.test.employee.DAO.VIMSEmployeeDAO;

//Start of changePasswordBD class
public class changePasswordBD
{

   /**********Method Name:-------->changePwdBD ****************/
	
	/**********This method returns integer by checking whether the user logs in for the first time or not.*******/	
   
		
   public static int  changePwdBD(String getNewPassword,String strUserID)
	{
	     Logger logger=Logger.getLogger("Admin"); 
				  
	     try
	         {
	    	  //System.out.println("------In changePwdBD-------");
			  int intValue=changePasswordDAO.changePwdDAO(getNewPassword,strUserID);
			  return intValue;
                
			 }
		 catch(Exception exception)
			 {
				  System.out.println("IN Exception============>"+exception);
			      return 0;
			   
			 }
   
     }
 
   /**********Method Name:-------->checkOldPasswordBD ****************/
	
	/**********This method returns boolean value by checking whether the password entered matches with the provided password or not****/	
 
   public static boolean checkOldPasswordBD(String getOldPassword,String strUserID) 
   {
    
 //Start of try block	  
  try
       {
    	 //calling a method in DAO sending form object as parameter
	     //System.out.println("------In checkOldPasswordBD------");
	      boolean result=changePasswordDAO.checkOldPasswordDAO(getOldPassword,strUserID);
 	      return result;
       }
 //End of try block
  
 //Start of catch block  
      catch(Exception exception)
       {
    	 System.out.println("The Exception is:"+exception);
       }
    //End of catch block
     
    //Returns boolean value   
      return false;	
 }
}