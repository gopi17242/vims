package com.vertex.VIMS.test.login.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VIMSMailLoginAction extends Action{

	/*
	 * The method is for logging in using the link sent in the mails 
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 *  
	 */
	 public ActionForward execute(ActionMapping  actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		       
		       HttpSession session=null;
		       Logger logger=null;
		       String strIssueId=null;
		 try { // try block starts
			    session=request.getSession(true);
			    logger=Logger.getLogger("Admin");
			    
			    if(session!=null) {  // If starts
			     
			       	 strIssueId=request.getParameter("issueId");
			       	 session.setAttribute("IssueId",strIssueId);
			       	 return actionMapping.findForward("VIMSMailLogin");
			    } // End of If
		 } // try block Ends
		  catch(Exception exception) { // catch block starts
			 logger.info("======Exception in VIMSMailLoginAction----------");
			 logger.error(exception);
	      } // catch block ends
	     return null;
	}
	 // End of execute() method 
}