/*  
  FileName	:	VIMSSearchForm.java
  
  copyright (c) 2008 Vertex.
  All rights reserved.
  
  Description	: In this form all getter & setter methods are defined 
                  and validations are performed depending on the condition.	
  
  Developed by : Vertex Computer Systems.
  
  Change History:
 
  Revision No.			Date		  @author		Description
    1.0				Friday 21,2008	  Geeta.M	   Initial Version.*/

package com.vertex.VIMS.test.search.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


public class VIMSSearchForm extends ActionForm
{
	 String customerName;
	 String title;
	 String issueID;
	 String applicationName;
	 String status;
	 String severity;
	 String sortBy;
	 String fromDate;
	 String toDate;
	 String submit;
	/**
	 * @return Returns the applicationName.
	 */
	public String getApplicationName() {
		return applicationName;
	}
	/**
	 * @param applicationName The applicationName to set.
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	/**
	 * @return Returns the customerName.
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName The customerName to set.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return Returns the fromDate.
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return Returns the issueID.
	 */
	public String getIssueID() {
		return issueID;
	}
	/**
	 * @param issueID The issueID to set.
	 */
	public void setIssueID(String issueID) {
		this.issueID = issueID;
	}
	/**
	 * @return Returns the severity.
	 */
	public String getSeverity() {
		return severity;
	}
	/**
	 * @param severity The severity to set.
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	/**
	 * @return Returns the sortBy.
	 */
	public String getSortBy() {
		return sortBy;
	}
	/**
	 * @param sortBy The sortBy to set.
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return Returns the toDate.
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the submit
	 */
	public String getSubmit() {
		return submit;
	}
	/**
	 * @param submit the submit to set
	 */
	public void setSubmit(String submit) {
		this.submit = submit;
	}

//public ActionErrors validate(ActionMapping actionMapping,HttpServletRequest request)	
//	{
//		ActionErrors actionErrors=new ActionErrors();
//		System.out.println("========getTitle()========="+getTitle());
//		if(getTitle()==null||getTitle().equals(""))
//		{
//			System.out.println("========Title========="+getTitle());
//			actionErrors.add("TitleError", new ActionMessage("errors.title"));
//		}
//		if(getIssueID()==null||getIssueID().equals(""))
//		{
//			System.out.println("========IssueID========="+getIssueID());
//			actionErrors.add("IssueError", new ActionMessage("errors.issueID"));
//		}
//		return actionErrors;
//
//    }
}
	