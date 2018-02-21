/*
 FileName	    : VIMSMyToDoListLookupDispatchAction.java


 Description	: This  VIMSMyToDoListLookupDispatchAction Object class has different methods that are called based on mapping in the getKeyMap() of the same  class. The main purpose of this class is to act as controller class for all the urls, for getting list of issues addressed by this internal user(i.e. My-To-Do List) activity. 
 Developed by  : Vertex Computer Systems.
 copyright (c) 2008 Vertex.
 All rights reserved.

 Change History:

 Revision No.	:		Date		  @author		Description
 1.0				Friday 26,2008	  Sudhir.D	   Initial Version.*/

package com.vertex.VIMS.test.mytodolist.action;

import org.apache.*;
import org.apache.log4j.*;
import org.apache.struts.actions.*;
import org.apache.struts.action.*;
import com.vertex.VIMS.test.mytodolist.BD.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;

public class VIMSMyToDoListLookupDispatchAction extends LookupDispatchAction {

	/*
	 *  @see org.apache.struts.actions.LookupDispatchAction#getKeyMethodMap()
	 * 
	 * Purpose: This method is a built-in method of LookupDispatchAction,
	 *      and is used to map a method in this class to a particular url(submit
	 *      button value) based label value in the VIMSApplication.properties
	 *      file.
	 */
	protected Map getKeyMethodMap()
	{
		Map map = new HashMap();
	  //To display MyToDoList page with initial list of issues.
		map.put("VIMSApplication.mytodolistgetPage", "GetMyToDoList"); 
		return map;
	}

	/**
	 * @purpose :This method is used to get the list of Issues assigned to a
	 *          Employee and populate it in a ArrayList and set it in session
	 *          object which is used in the EmployeeMyToDoList.jsp.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return MyToDoListForward in the tiles-definition to Display the  EmployeeMyToDoList.jsp.
	 */
	public ActionForward GetMyToDoList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	{ 
		// EmployeeForm
		HttpSession session = request.getSession(true);
		String strUserId = (String) session.getAttribute("user");
		ArrayList ListOfEmployeIssue = VIMSMyToDoListBD.getMyToDoListOfIssues(strUserId);
		//System.out.println("===========In Action class Array List ============="+ListOfEmployeIssue);
		session.removeAttribute("ListOfEmployeIssue");
		session.setAttribute("ListOfEmployeIssue", ListOfEmployeIssue);
		//System.out.println("=====================In action session attribute GetMyToDoList is set======================="+ ListOfEmployeIssue);
		return mapping.findForward("MyToDoListForward");
	}
}
