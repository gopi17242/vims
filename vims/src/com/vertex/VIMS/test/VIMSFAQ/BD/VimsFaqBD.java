 package com.vertex.VIMS.test.VIMSFAQ.BD;

 import java.util.*;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import com.vertex.VIMS.test.VIMSFAQ.DAO.VimsFaqDAO;
 

public class VimsFaqBD 
 {
   public static ArrayList getFaqDetailsBD() 
	  {
	    
		 //Adding all the exception to the getLogger method for admin module	  
		   Logger logger=Logger.getLogger("Admin"); 
		   //System.out.println("-----------In BD class------------");
		 //Start of try block 
		   try
   	         {
			      ArrayList arrayList=VimsFaqDAO.getFaqDetailsDAO();
		          return arrayList;
   	         }
		   catch(Exception exception)
	        {
	    	  logger.info("VimsFaqBD.getFaqDetailsBD");
	    	  logger.error(exception);
	    	  System.out.println("The Exception is:"+exception);
	    	  return null;
            }
	  }
 }
 