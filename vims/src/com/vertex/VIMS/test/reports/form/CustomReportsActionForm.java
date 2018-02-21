/*
 * @author: santhosh.k
 * @creation date: 
 * @file name : CustomReportsActionForm.java
 * @description: 
 * 			It is the Action form bean for the Custom Reports  Page.
*/
package com.vertex.VIMS.test.reports.form;

import org.apache.struts.action.ActionForm;

public class CustomReportsActionForm extends ActionForm
{		 
	String[] orginalList;
	String[] selectedList;	
	String issueStatus;
	String issuePriority;
	String issueSeverity;
	String createdFromDate;
	String createdToDate;	
	String closedFromDate;
	String closedToDate;	
	String applicationName;	
	String customerName;
	String employeeName;
	String supportCenter;	
	String supportManager;
	String groups;
	String groupSupervisor;
	String groupMember;
	String purgingFlag;	
	String [] columList;
	
	public String[] getOrginalList() {
		return orginalList;
	}
	public void setOrginalList(String[] orginalList) {
		this.orginalList = orginalList;
	}
	public String[] getSelectedList() {
		return selectedList;
	}
	public void setSelectedList(String[] selectedList) {		
		this.selectedList = selectedList;
	}	
	public String getIssueStatus() {
		return issueStatus;
	}
	public void setIssueStatus(String issueStatus) {
		this.issueStatus = issueStatus;
	}
	public String getIssuePriority() {
		return issuePriority;
	}
	public void setIssuePriority(String issuePriority) {
		this.issuePriority = issuePriority;
	}
	public String getIssueSeverity() {
		return issueSeverity;
	}
	public void setIssueSeverity(String issueSeverity) {
		this.issueSeverity = issueSeverity;
	}	
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {		
		this.employeeName = employeeName;
	}
	public String getSupportCenter() {
		return supportCenter;
	}
	public void setSupportCenter(String supportCenter) {
		this.supportCenter = supportCenter;
	}
	public String getSupportManager() {
		return supportManager;
	}
	public void setSupportManager(String supportManager) {
		this.supportManager = supportManager;
	}
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	public String getGroupSupervisor() {
		return groupSupervisor;
	}
	public void setGroupSupervisor(String groupSupervisor) {
		this.groupSupervisor = groupSupervisor;
	}
	public String getGroupMember() {
		return groupMember;
	}
	public void setGroupMember(String groupMember) {
		this.groupMember = groupMember;
	}
	public String getPurgingFlag() {
		return purgingFlag;
	}
	public void setPurgingFlag(String purgingFlag) {
		this.purgingFlag = purgingFlag;
	}	
	public String getCreatedFromDate() {
		return createdFromDate;
	}
	public void setCreatedFromDate(String createdFromDate) {
		this.createdFromDate = createdFromDate;
	}
	public String getCreatedToDate() {
		return createdToDate;
	}
	public void setCreatedToDate(String createdToDate) {
		this.createdToDate = createdToDate;
	}
	public String getClosedFromDate() {
		return closedFromDate;
	}
	public void setClosedFromDate(String closedFromDate) {
		this.closedFromDate = closedFromDate;
	}
	public String getClosedToDate() {
		return closedToDate;
	}
	public void setClosedToDate(String closedToDate) {
		this.closedToDate = closedToDate;
	}
	public String[] getColumList() {		
		return columList;
	}
	public void setColumList(String[] columList) {
		this.columList = columList;
	}
}
	