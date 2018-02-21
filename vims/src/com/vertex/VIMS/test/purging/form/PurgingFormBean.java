/*
 FileName	    : PurgingFormBean.java


 Description	: This  PurgingFormBean Object class is the FormBean class,it has attributes , getter/setters methods and validate/reset(not mandatory) methods.
  
 Developed by  : Vertex Computer Systems.
 copyright (c) 2008 Vertex.
 All rights reserved.

 Change History:

 Revision No.	:		Date		  @author		Description
 1.0				Friday 21,2008	  Sudhir.D	   Initial Version.
 */
package com.vertex.VIMS.test.purging.form;



import org.apache.struts.action.*;

import javax.servlet.http.*;

import java.util.regex.Pattern;


public class PurgingFormBean extends ActionForm{

String operation;
String issueStatus;
String purge;
int prevMonths;
int prevDays;
String incidentID;
String fromDate;
String toDate;

public String getOperation() {
	return operation;
}
public void setOperation(String operation) {
	this.operation = operation;
}
public String getIssueStatus() {
	return issueStatus;
}
public void setIssueStatus(String issueStatus) {
	this.issueStatus = issueStatus;
}
public String getPurge() {
	return purge;
}
public void setPurge(String purge) {
	this.purge = purge;
}
public int getPrevMonths() {
	return prevMonths;
}
public void setPrevMonths(int prevMonths) {
	this.prevMonths = prevMonths;
}
public int getPrevDays() {
	return prevDays;
}
public void setPrevDays(int prevDays) {
	this.prevDays = prevDays;
}
public String getIncidentID() {
	return incidentID;
}
public void setIncidentID(String incidentID) {
	this.incidentID = incidentID;
}

public String getFromDate() {
	return fromDate;
}
public void setFromDate(String fromDate) {
	this.fromDate = fromDate;
}
public String getToDate() {
	return toDate;
}
public void setToDate(String toDate) {
	this.toDate = toDate;
}


/* 
 * Description: This is a built in method and this is used to validate values submitted in the form (ie purging.jsp).
 * (non-Javadoc)
 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
 */
public ActionErrors validate(ActionMapping actionMapping,HttpServletRequest request)	
{
	
	ActionErrors actionErrors=new ActionErrors();
		
	
	
	     //for check box validation
	 
		 System.out.println("------entering into PurgingFormBean.java validation()--------------");
		 if(operation==null||operation.equals(""))
		 {
		   actionErrors.add("Purging Operation Error",new ActionMessage("PurgingOperationButton.Empty")); 
		  // System.out.println("-------Error generated------");
		   
		 }	
		 if(issueStatus==null||issueStatus.equals(""))
		 {
		   actionErrors.add("Purging Operation Error",new ActionMessage("PurgingStatusButton.Empty")); 
		  // System.out.println("-------Error generated------");
		   
		 }	
		 if(purge==null||purge.equals(""))
		 {
		   actionErrors.add("Purging Operation Error",new ActionMessage("PurgingpurgeButton.Empty")); 
		  // System.out.println("-------Error generated------");
		   
		 }	
		 return actionErrors;


}


}
