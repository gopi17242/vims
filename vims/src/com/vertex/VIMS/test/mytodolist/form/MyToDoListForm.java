
/*
FileName	    : MyToDoListForm.java


Description	: This  MyToDoListForm Object class is a POJO class that  has different getter and setter methods. This class is to for capturing form data to and from the JSP and the Controller.
Developed by  : Vertex Computer Systems.
copyright (c) 2008 Vertex.
All rights reserved.

Change History:

Revision No.	:		Date		  @author		Description
1.0				Friday 26,2008	  Sudhir.D	   Initial Version.*/

package com.vertex.VIMS.test.mytodolist.form;


import org.apache.struts.action.*;
import org.apache.struts.action.ActionErrors;
import javax.servlet.http.*;
import java.util.regex.Pattern;


public class MyToDoListForm extends ActionForm {

	String strIncidentId;
	String strApplicationName;
	String strTitle;
	String strAssignedBy;
	String strPostedDate;
	String strDueDate;
	String strSeverity;
	String	strStatus;

/*public ActionErrors validate(ActionMapping actionMapping,HttpServletRequest request)	
{
	
	ActionErrors actionErrors=new ActionErrors();
		
	
	String strHidden=(String)request.getParameter("subType");
	//for check box valication
	 if(((strHidden)!=null)&&(strHidden.equalsIgnoreCase("Display")))	
		{
		 System.out.println("------entering into Display--------------");
		
		   }
	 return actionErrors; 
}	 */


public String getStrIncidentId() {
	return strIncidentId;
}

public void setStrIncidentId(String strIncidentId) {
	this.strIncidentId = strIncidentId;
}

public String getStrApplicationName() {
	return strApplicationName;
}

public void setStrApplicationName(String strApplicationName) {
	this.strApplicationName = strApplicationName;
}

public String getStrTitle() {
	return strTitle;
}

public void setStrTitle(String strTitle) {
	this.strTitle = strTitle;
}

public String getStrAssignedBy() {
	return strAssignedBy;
}

public void setStrAssignedBy(String strAssignedBy) {
	this.strAssignedBy = strAssignedBy;
}

public String getStrPostedDate() {
	return strPostedDate;
}

public void setStrPostedDate(String strPostedDate) {
	this.strPostedDate = strPostedDate;
}

public String getStrDueDate() {
	return strDueDate;
}

public void setStrDueDate(String strDueDate) {
	this.strDueDate = strDueDate;
}

public String getStrSeverity() {
	return strSeverity;
}

public void setStrSeverity(String strSeverity) {
	this.strSeverity = strSeverity;
}

public String getStrStatus() {
	return strStatus;
}

public void setStrStatus(String strStatus) {
	this.strStatus = strStatus;
}
}
