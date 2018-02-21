 package com.vertex.VIMS.test.VIMSFAQ.action;
 
 
 import java.util.*;
 import javax.servlet.http.*; 
 import org.apache.log4j.Logger;
 import org.apache.struts.action.*;
 import com.vertex.VIMS.test.VIMSFAQ.BD.VimsFaqBD;

 
 public class VimsFaq extends Action 
	{
	    Logger logger=null;
		
	    //start of execute method
	    public ActionForward execute(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
		{
	    	  logger=Logger.getLogger("Admin"); 
	    	  HttpSession session;
	  		  session=request.getSession();
	    	  //System.out.println("-----------In Action class------------");
	    	  try
				{
	    		    ArrayList arrayList=VimsFaqBD.getFaqDetailsBD();
				    session.setAttribute("List",arrayList);
				    //System.out.println("-----------List----------"+arrayList);	    			     
				    return actionMapping.findForward("FAQ");
				}
	    	  
	    	  catch(Exception exception)
				{
				   
					logger.info("VimsFaq.execute");
			    	logger.error(exception);
					System.out.println("==========exception Occured in action class==============="+exception);
				    exception.printStackTrace();
				}
			return null;
	    	  
		
		}
	}
	    