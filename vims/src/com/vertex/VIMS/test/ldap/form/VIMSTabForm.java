package com.vertex.VIMS.test.ldap.form;

import org.apache.struts.action.ActionForm;

public class VIMSTabForm extends ActionForm {
  
	  private String userType;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
