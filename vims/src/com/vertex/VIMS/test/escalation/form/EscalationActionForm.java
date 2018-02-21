/*
 * @author: santhosh.k
 * @creation date: 
 * @file name : EscalationActionForm.java
 * @description: 
 * 			It is the Action form Bean  of the Escalation page.It is called when the user clicks on the Escalation link under Issues.
*/
package com.vertex.VIMS.test.escalation.form;

import org.apache.struts.action.ActionForm;

public class EscalationActionForm extends ActionForm
{
  String issueType;
  String groups;
  String employee;
  String groupname;
  String comments;
  String issueststus;
  String reason;
  
public String getIssueType() {
	return issueType;
}
public void setIssueType(String issueType) {
	this.issueType = issueType;
}
public String getGroups() {
	return groups;
}
public void setGroups(String groups) {
	this.groups = groups;
}
public String getEmployee() {
	return employee;
}
public void setEmployee(String employee) {
	this.employee = employee;
}
public String getGroupname() {
	return groupname;
}
public void setGroupname(String groupname) {
	this.groupname = groupname;
}
public String getComments() {
	return comments;
}
public void setComments(String comments) {
	this.comments = comments;
}
public String getIssueststus() {
	return issueststus;
}
public void setIssueststus(String issueststus) {
	this.issueststus = issueststus;
}
public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}
}