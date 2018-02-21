 package com.vertex.VIMS.test.VIMSAboutUs.action;

	import java.util.*;
	import javax.servlet.http.*; 

	import org.apache.log4j.Logger;
	import org.apache.struts.action.*;
    import com.vertex.VIMS.test.VIMSAboutUs.BD.VimsAboutUsBD;


public class AboutUS extends Action 
{
    Logger logger=null;
	
    //start of execute method
    public ActionForward execute(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
	{
    	  logger=Logger.getLogger("Admin"); 
    	   HttpSession session;
  		   session=request.getSession();
  		try
		{
		   
  			ArrayList arrayList=VimsAboutUsBD.getVimsAboutUsBD();
  		    session.setAttribute("List",arrayList);
  			return actionMapping.findForward("AboutUs");
		}
	  
	  catch(Exception exception)
		{
		   
			logger.info("AboutUS.execute");
	    	logger.error(exception);
			System.out.println("==========exception Occured in action class==============="+exception);
		    exception.printStackTrace();
		}
	return null;
   }
}