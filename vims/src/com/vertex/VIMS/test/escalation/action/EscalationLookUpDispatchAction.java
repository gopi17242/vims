/*
 * @author: santhosh.k
 * @creation date: 
 * @file name : EscalationLookUpDispatchAction.java
 * @description: 
 * 			It is the controller of the Escalation page.It is called when the user clicks on the Escalation link under Issues.
*/
package com.vertex.VIMS.test.escalation.action;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import com.vertex.VIMS.test.escalation.BD.*;

public class EscalationLookUpDispatchAction extends LookupDispatchAction
{
	HttpSession session;
	/*
	 * This method is used to retrieve list of escalated issues.
	*/
	public ActionForward escalatedIssuesList(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
	{		
		session=request.getSession(false);
		String strUserID=(String) session.getAttribute("user");
		ArrayList EscalatedIssuesAction=EscalationBD.getEscalatedIssuesListBD(strUserID);
		session.setAttribute("EscalatedIssuesList", EscalatedIssuesAction);
		return actionMapping.findForward("escalationPage"); 
	}
	/*
	 * This method is used to forward to issues information page where escalated issues can be re assigned.
	*/
	public ActionForward escalatedIssueReAssignment(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
	{		
		session=request.getSession(false);
		
		return actionMapping.findForward("issueReAssignment"); 
	}

	
	protected Map getKeyMethodMap() 
	{
		HashMap hashMap = new HashMap();
		hashMap.put("VIMS.Escalation.escalatedIssuesList", "escalatedIssuesList");
		hashMap.put("VIMS.Escalation.escalatedIssueReAssignment", "escalatedIssueReAssignment");
		return hashMap;
	}
	
}