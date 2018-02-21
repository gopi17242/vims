/*  File Name : VIMSApplicationAction.java
 *  
 *  copyright (c) 2008 Vertex.All rights reserved.
 *  
 *  Created By : Saikumar>M.P
 *  
 *  Description : This is the action class that will be executed when the user clicks the List of Issues tab from the menu.
 *  			  After clicking this we will see the page which contains all list of issues . If you click the issueid then
 *   			  you will be navigated to the Issue Assignement page where you can view the history of the page and you can
 *   			  assign that issue to another group member,In this page you can also change the status of the issue.  
 *  
 *   Change History:
 * 	  Revision No.			Date		 @author			Description
 *		1.0					16-11-2008   Saikumar.M.P			Initial version
 *
 *   
 *  
 *  			
 * 
 */

package com.vertex.VIMS.test.applications.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertex.VIMS.test.applications.BD.VIMSApplicationBD;
//import com.vertex.VIMS.test.applications.form.VIMSApplicationForm1;
/**
 * @author saikumar.m
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
  
public class VIMSApplicationAction extends Action
{
	/*
	 * @param ActionMapping,ActionForm,HttpServletRequest,HttpServletResponse
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{			
		try
		{						
			int sla[]=VIMSApplicationBD.getStndrdSLABD();
			/*for(int i=0;i<sla.length;i++)
			{ 
				System.out.println("=====sla======"+sla[i]);
			}*/
	        response.setContentType("text/xml");        
	        response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			response.getOutputStream().println("<response>");
			response.getOutputStream().println("<result>" + sla[0] + "</result>");
			response.getOutputStream().println("<result>" + sla[1] + "</result>");
			response.getOutputStream().println("<result>" + sla[2] + "</result>");
			response.getOutputStream().println("<result>" + sla[3] + "</result>");
			response.getOutputStream().println("<result>" + sla[4] + "</result>");
			response.getOutputStream().println("<result>" + sla[5] + "</result>");
			response.getOutputStream().println("</response>");			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
