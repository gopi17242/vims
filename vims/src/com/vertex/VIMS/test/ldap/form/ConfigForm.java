package com.vertex.VIMS.test.ldap.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ConfigForm extends ActionForm {
	
	private String strServerName;
	private String strPortNumber;
	private String strConfigType;
	private String[] strSelectOptions;
	private String[] strSelectedOptions;
	private String strServerStatus;
	private String strServerId;
	private String strDurationDays;
	private String keywordType;
	private String keywordValue;
	private String positionStatus;
	private String headName;
	private String address;
	private String countryName;
	private String stateName;
	private String phoneNumber;
	private String faxNumber;
	private String email;
	private String[] statetList=null;
	private String roleType;
	private String roleName;
	private String description;
	private String roles;
	private String countryValue;
	
	public String getServerName() {
		return strServerName;
	}
	public void setServerName(String serverName) {
		this.strServerName = serverName;
	}
	public String getPortNumber() {
		return strPortNumber;
	}
	public void setPortNumber(String portNumber) {
		this.strPortNumber = portNumber;
	}
	
	public String getConfigType() {
		return strConfigType;
	}
	public void setConfigType(String configType) {
		
		this.strConfigType = configType;
	}
	
	public String[] getSelectOptions() {
		return strSelectOptions;
	}
	public void setSelectOptions(String[] selectOptions) {
		this.strSelectOptions = selectOptions;
	}
	public String[] getSelectedOptions() {
		return strSelectedOptions;
	}
	public void setSelectedOptions(String[] selectedOptions) {
		this.strSelectedOptions = selectedOptions;
	}
	public String getServerStatus() {
		return strServerStatus;
	}
	public void setServerStatus(String serverStatus) {
		this.strServerStatus = serverStatus;
	}
	public String getServerId() {
		return strServerId;
	}
	public void setServerId(String serverId) {
		this.strServerId = serverId;
	}
	public void setDurationDays(String durationDays) {
		this.strDurationDays=durationDays;
	}
	public String getDurationDays() {
		return this.strDurationDays;
	}

  // Following setter and getter method are form Keyword customization   	
	public String getKeywordType() {
		return keywordType;
	}
	public void setKeywordType(String keywordType) {
		this.keywordType = keywordType;
	}
	public String getKeywordValue() {
		return keywordValue;
	}
	public void setKeywordValue(String keywordValue) {
		this.keywordValue = keywordValue;
	}
	
	public String getPositionStatus() {
		return positionStatus;
	}
	public void setPositionStatus(String positionStatus) {
		this.positionStatus = positionStatus;
	}
	
	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String[] getStatetList() {
		return statetList;
	}
	public void setStatetList(String[] statetList) {
		this.statetList = statetList;
	}
	
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getCountryValue() {
		return countryValue;
	}
	public void setCountryValue(String countryValue) {
		this.countryValue = countryValue;
	}
	/*
	 * validate method to validate forms of LDAP server, Mail Server and Home Page configuration
	 *
	 * @param mapping
	 * @param request
	 *  
	 */
    public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
    	
    	 ActionErrors errors=null;
         String strOperationType=null;
       	 
    	try {
    		  errors=new ActionErrors();
    		  
    		  // validation for role filed configuration
    		   if(roles!=null) {
    			   
    				  if(roleName!=null) {  
   	    			   if((roleName==null)||(roleName.equals(""))) {
   	     				   errors.add("errors.roleName",new ActionMessage("Role name is required",false));
   	    			    }
   	    			    if((positionStatus==null)||(positionStatus.equals(""))) {
   	        				   errors.add("errors.positionStatus",new ActionMessage("Role Status is required",false));
   	    			   }
   	    			   return errors;
    		     }
    		   }
    		  
    		  // End of Role filed configuration
    		   
    		  // Validation for Field customization
    		  	if(keywordType!=null) {
    			   if(keywordValue.equals("")) {
    				  errors.add("errors.keywordType",new ActionMessage(keywordType+" is required",false));
    				  request.setAttribute("formValidation",keywordType);
    				  //return errors;
    			  }
    		 
    			   if(positionStatus==null||positionStatus.equals(""))
    				   errors.add("errors.positionStatus",new ActionMessage("Position Status is required",false));
    			   /*else {
    				   return null;
    			   }*/
    			   if((keywordType!=null)&&(keywordType.equals("Position"))) {
    			   if((roleType==null)||(roleType.equals("")))
    				   errors.add("errors.roleType",new ActionMessage("Role Type is required",false));
    			   } 
    		       return errors;  		  
    		  }	   
    			  
    		  // End of field Customization validation	  
    		  
    		  if(strConfigType!=null) { // If starts
    			  // The strConfigType hold the type of action
    		   
    			  if(strConfigType.equalsIgnoreCase("home")){ 
    				 
    				 // Validation block for home page configuration
    				 return null;
    			  } 
    		  }
    		//[^?><:;'|}{!@#$~`-+*%&($\\\"//)]
    	       
    		   // Server Name validation
    		   if((strServerName==null)||(strServerName.equals(""))) {
 			      errors.add("error.serverName",new ActionMessage(" Server Name is required",false));
    		   }
    		   else if((strServerName!=null)&&(!strServerName.matches("^[a-zA-Z0-9][a-zA-Z0-9.]+$"))) {
    			   errors.add("error.serverName",new ActionMessage(" Server Name should contain only alphabets or numerics and (.)",false));
    		   }
    		   // Server status validation
    		   if((strServerStatus==null)||(strServerStatus.equals(""))) {
    			   errors.add("error.serverStatus",new ActionMessage("Server Status is required",false));
  			    }
    		   
    	        if((strConfigType!=null)&&(strConfigType.equalsIgnoreCase("ldap"))) {
    		  	   if((strPortNumber==null)||(strPortNumber.equals("")))
	    			   errors.add("error.portNumber",new ActionMessage("Port Number is required",false));
	    		   else  if((!strPortNumber.equals(""))&&(!strPortNumber.matches("^[0-9]{4}$"))&&(!strPortNumber.matches("^[0-9]{2}$"))&&(!strPortNumber.matches("^[0-9]{3}$"))) {
	    			   errors.add("error.portNumber",new ActionMessage(" Port Number should not be more than 4 digit number",false));
	    		   }
    	        }
    		} // End of try block
    	     catch(Exception exception) {
    	    	  	  System.out.println("====Exception in validate method  of ConfigForm======="+exception);
    	     }
    	      // return validation errors if any   
    	      return errors;
      }
    
    public void reset(ActionMapping mapping,HttpServletRequest request) {
        strServerName="";
    	strPortNumber="";
    	strConfigType="";
    	keywordValue=null;
    	keywordType=null;
    }

   }