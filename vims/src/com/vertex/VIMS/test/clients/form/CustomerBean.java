package com.vertex.VIMS.test.clients.form;

public class CustomerBean {
	
	String strCustId;
	String strCustName;
	
	public CustomerBean(String strCustId, String strCustName)
	 {
        this.strCustId = strCustId;
        this.strCustName = strCustName;
     }
    public String getStrCustId() {
		return strCustId;
	}
	public String getStrCustName() {
		return strCustName;
	}
}
