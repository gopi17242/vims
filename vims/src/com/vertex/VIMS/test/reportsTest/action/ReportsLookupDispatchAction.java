package com.vertex.VIMS.test.reportsTest.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

public class ReportsLookupDispatchAction extends LookupDispatchAction{

	static Logger logger;
	public ActionForward getIssueReportHomePage(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		        
      HttpSession session=null;
      ArrayList statusTypes=null;
      ArrayList issueSeverity=null;
      ArrayList issuePriority=null;
      HashMap hashMap=null;
		          
		 try {
               		session=request.getSession(false);
               		if(session!=null) {  // If the request is from a valid session 
               			
               			
               		}
               		else {
               			
               			logger.info("------Invalid session from getIssueReportHomePage action=========");
               			actionMapping.findForward("sessionExpirePage");
               		}
		  } 
		   catch(Exception exception) {
			 
			  logger.info("--------Exception in getIssueReportHomePage action------------");
			  logger.error(exception);
			  exception.printStackTrace();
		  }
		   return null;
	}
	
	@Override
	protected Map getKeyMethodMap() {

		HashMap map=null;
		
		map=new HashMap();
		
		map.put("VIMS.getIssueReportHomePage","getIssueReportHomePage");
		return map;
	}

}
