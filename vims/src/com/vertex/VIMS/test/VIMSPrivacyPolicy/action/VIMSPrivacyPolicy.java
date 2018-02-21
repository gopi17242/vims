package com.vertex.VIMS.test.VIMSPrivacyPolicy.action;

import java.util.*;
import javax.servlet.http.*; 
import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import com.vertex.VIMS.test.VIMSPrivacyPolicy.BD.VimsPrivacyPolicyBD;


public class VIMSPrivacyPolicy extends Action 
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
  			ArrayList arrayList=VimsPrivacyPolicyBD.getVimsPrivacyPolicyBD();
  		    session.setAttribute("List",arrayList);
  			return actionMapping.findForward("privacyPolicy");
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