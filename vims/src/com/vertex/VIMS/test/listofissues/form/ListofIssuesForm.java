package com.vertex.VIMS.test.listofissues.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ListofIssuesForm extends ActionForm
{
  String issueType;
  String groups;
  String employee;
  String groupname;
  String comments;
  String issueststus;
  String reason;
  String changestatus;
  String issueId;
  String description;
  String typeofIssue;
  
  String strSearchCustomer;
  String strSearchEmployee;
  String strSearchStatus;
  String strSearchSeverity;
  String strSearchApplicationName;

  
 public String getIssueType()
 {
	return issueType;
 }

 public void setIssueType(String issueType)
 {
	this.issueType = issueType;
 }

 public String getGroups()
 {
	return groups;
 }

public void setGroups(String groups)
{
	this.groups = groups;
}

public String getEmployee()
{
	return employee;
}

public void setEmployee(String employee)
{
	this.employee = employee;
}

public String getGroupname()
{
	return groupname;
}

public void setGroupname(String groupname)
{
	this.groupname = groupname;
}

public String getComments()
{
	return comments;
}

public void setComments(String comments)
{
	this.comments = comments;
}

public String getIssueststus()
{
	return issueststus;
}

public void setIssueststus(String issueststus)
{
	this.issueststus = issueststus;
}

public String getReason()
{
	return reason;
}

public void setReason(String reason)
{
	this.reason = reason;
}
public String getChangestatus() {
	return changestatus;
}

public void setChangestatus(String changestatus) {
	this.changestatus = changestatus;
}
public String getIssueId() {
	return issueId;
}

public void setIssueId(String issueId) {
	this.issueId = issueId;
}
public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}
public String getTypeofIssue() {
	return typeofIssue;
}

public void setTypeofIssue(String typeofIssue) {
	this.typeofIssue = typeofIssue;
}

public String getStrSearchCustomer() {
	return strSearchCustomer;
}

public void setStrSearchCustomer(String strSearchCustomer) {
	this.strSearchCustomer = strSearchCustomer;
}

public String getStrSearchEmployee() {
	return strSearchEmployee;
}

public void setStrSearchEmployee(String strSearchEmployee) {
	this.strSearchEmployee = strSearchEmployee;
}

public String getStrSearchStatus() {
	return strSearchStatus;
}

public void setStrSearchStatus(String strSearchStatus) {
	this.strSearchStatus = strSearchStatus;
}

public String getStrSearchSeverity() {
	return strSearchSeverity;
}

public void setStrSearchSeverity(String strSearchSeverity) {
	this.strSearchSeverity = strSearchSeverity;
}

public String getStrSearchApplicationName() {
	return strSearchApplicationName;
}

public void setStrSearchApplicationName(String strSearchApplicationName) {
	this.strSearchApplicationName = strSearchApplicationName;
}
public ActionErrors validate(ActionMapping actionMapping,HttpServletRequest request)	
{
   ActionErrors actionErrors=new ActionErrors();
   String hiddenField=null;
   String strIssueAction=null;
	if(actionMapping.getValidate())
	{
		if(request.getParameter("RoleType")!=null)
		{
			hiddenField=request.getParameter("RoleType");
			strIssueAction=request.getParameter("IssueStatus");
			System.out.println("--------hiddenField----"+hiddenField);
			System.out.println("--------strIssueAction----"+strIssueAction);
			System.out.println("=============Before Assign Function============1/27/2009===========");
		}
	if(hiddenField.equals("Admin")&& strIssueAction.equalsIgnoreCase("Assign"))
	  {
		  if(getEmployee()==null||getEmployee().equals(""))
		  {
			actionErrors.add("EmployeeError", new ActionMessage("Please Select an Employee",false));
			System.out.println("================In FORM BEAN   Employee Error======================");
		  }
		  return actionErrors;
	  }
	else if(hiddenField.equals("Admin")&& strIssueAction.equalsIgnoreCase("ChangeStatus"))
	{
	  if(getIssueststus()==null || getIssueststus().equals(""))
	  {
	     actionErrors.add("ChangeStatusError", new ActionMessage("Please Change the Status",false));
		 System.out.println("===========In Form Bean=ADMIN=============Change Status============");
	   }
	  if(getReason()==null || getReason().equals(""))
	   {
		   actionErrors.add("ReasonError", new ActionMessage("Please give the comments",false)); 
	   }
	  return actionErrors;
	}
	if((hiddenField.equals("User"))||(hiddenField.equals("Customer"))&& (strIssueAction.equalsIgnoreCase("ChangeStatus")))
	  {
	  if(getIssueststus()==null||getIssueststus().equals(""))
		{
			actionErrors.add("ChangeStatusError", new ActionMessage("Please Change the Status",false));
			System.out.println("===========In Form Bean===USER======CLIENT=======Change Status============");
		}
	   if(getReason()==null || getReason().equals(""))
	   {
		   actionErrors.add("ReasonError", new ActionMessage("Please give the comments",false)); 
	   }
	   /*if(getChangestatus()==null || getChangestatus().equals(""))
		 {
			 actionErrors.add("ChangeStatusError", new ActionMessage("Please Change the Status",false));
		 }*/
	  }
	}
	
	return actionErrors;
  }
}