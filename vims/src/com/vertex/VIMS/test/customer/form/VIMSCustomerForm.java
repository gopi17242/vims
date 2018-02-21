package com.vertex.VIMS.test.customer.form;
/**
 * @author saikumar.m
 *
 */
import org.apache.struts.action.ActionForm;

public class VIMSCustomerForm extends ActionForm {
	
	String issueType;

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

}
