package com.vertex.VIMS.test.reportsTest.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class IssueReportForm extends ActionForm{
   
	   private String issueId;
	   private String issueStatus;
	   private String issueSeverity;
	   private String issuePriority;
	   private String fromDate;
	   private String toDate;
	   private String viewType;
	public String getIssueId() {
		return issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	public String getIssueStatus() {
		return issueStatus;
	}
	public void setIssueStatus(String issueStatus) {
		this.issueStatus = issueStatus;
	}
	public String getIssueSeverity() {
		return issueSeverity;
	}
	public void setIssueSeverity(String issueSeverity) {
		this.issueSeverity = issueSeverity;
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
	   
	 public ActionErrors validate(ActionMapping actionmapping,HttpServletRequest request) {
		 
		  ActionErrors errors=null;
		  
		  try {
                 //    if((issueId==null))			  
		  }
		   catch(Exception exception) {
			   
			   System.out.println("=======Exception in IssueReportForm validate method=========");
			   exception.printStackTrace();
		   }
		   
		    return errors;
 	 }
	public String getIssuePriority() {
		return issuePriority;
	}
	public void setIssuePriority(String issuePriority) {
		this.issuePriority = issuePriority;
	}
	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
}
