package com.vertex.VIMS.test.applications.form;

public class ApplicationBean {
	
	String strAppId;
	String strAppName;
	
	public ApplicationBean (String strAppId, String strAppName) {
        this.strAppId = strAppId;
        this.strAppName = strAppName;
    }
	public String getStrAppId() {
		return strAppId;
	}
	public String getStrAppName() {
		return strAppName;
	}
}
