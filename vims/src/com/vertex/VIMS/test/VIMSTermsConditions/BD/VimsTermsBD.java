 package com.vertex.VIMS.test.VIMSTermsConditions.BD;

 import java.util.*;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import com.vertex.VIMS.test.VIMSTermsConditions.DAO.VimsTermsDAO;

 

public class VimsTermsBD 
 {
   public static ArrayList getVimsTermsBD() 
	  {
	    
		 //Adding all the exception to the getLogger method for admin module	  
		   Logger logger=Logger.getLogger("Admin"); 
		   //System.out.println("-----------In BD class------------");
		 //Start of try block 
		   try
   	         {
			      ArrayList arrayList=VimsTermsDAO.getTermsDetailsDAO();
		          return arrayList;
   	         }
		   catch(Exception exception)
	        {
	    	  logger.info("VimsTermsBD.getVimsTermsBD");
	    	  logger.error(exception);
	    	  System.out.println("The Exception is:"+exception);
	    	  return null;
            }
	  }
 }
 