 package com.vertex.VIMS.test.VIMSPrivacyPolicy.BD;

 import java.util.*;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import com.vertex.VIMS.test.VIMSPrivacyPolicy.DAO.VimsPrivacyPolicyDAO;

 

public class VimsPrivacyPolicyBD 
 {
   public static ArrayList getVimsPrivacyPolicyBD() 
	  {
	    
		 //Adding all the exception to the getLogger method for admin module	  
		   Logger logger=Logger.getLogger("Admin"); 
		   //System.out.println("-----------In BD class------------");
		 //Start of try block 
		   try
   	         {
			      ArrayList arrayList=VimsPrivacyPolicyDAO.getPrivacyPolicyDetails();
		          return arrayList;
   	         }
		   catch(Exception exception)
	        {
	    	  logger.info("VimsPrivacyPolicyBD.getVimsPrivacyPolicyBD");
	    	  logger.error(exception);
	    	  System.out.println("The Exception is:"+exception);
	    	  return null;
            }
	  }
 }
 