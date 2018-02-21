/**
 * @author geeta.m
 *
 */
/**Name of the form : VIMSCustomerForm.
 * Methods Used     : getter & setter methods.
 * Variables        : All the above mentioned strings.
 */

package com.vertex.VIMS.test.clients.form;


import org.apache.struts.action.*;
import javax.servlet.http.*;
import java.util.regex.Pattern;

//ActionForm

public class VIMSCustomerForm extends ActionForm
 {
     String customerID;
     String customerName;
     String firstName;
     String lastName;
     String address1;
     String address2;
     String city;
     String state;
     String country;
     String mobileNO;
     String pagerNumber;
     String phoneNumber;
     String emailID;
     String faxNo;
     String zipCode;
     String status;
     String custPassword;
     String[] checkBox;
     String Submit;
     String Reset;
     String extension;
     String companyName;
     String typeofIssue;
     String customerSearch;
     String contactFirstName;
     String contactMiddleName;
     String contactLastName;
     String contactEmailID;
   
     
     
     /**
      * @return the customerID
      */
     public String getCustomerID() {
     	return customerID;
     }
     /**
      * @param customerID the customerID to set
      */
     public void setCustomerID(String customerID) {
     	this.customerID = customerID;
     }
     /**
      * @return the customerName
      */
     public String getCustomerName() {
     	return customerName;
     }
     /**
      * @param customerName the customerName to set
      */
     public void setCustomerName(String customerName) {
     	this.customerName = customerName;
     }
     /**
      * @return the firstName
      */
     public String getFirstName() {
     	return firstName;
     }
     /**
      * @param firstName the firstName to set
      */
     public void setFirstName(String firstName) {
     	this.firstName = firstName;
     }
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMobileNO() {
		return mobileNO;
	}
	public void setMobileNO(String mobileNO) {
		this.mobileNO = mobileNO;
	}
	public String getPagerNumber() {
		return pagerNumber;
	}
	public void setPagerNumber(String pagerNumber) {
		this.pagerNumber = pagerNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustPassword() {
		return custPassword;
	}
	public void setCustPassword(String custPassword) {
		this.custPassword = custPassword;
	}
	public String[] getCheckBox() {
		return checkBox;
	}
	public void setCheckBox(String[] checkBox) {
		
		this.checkBox = checkBox;
		//System.out.println("The value of the checkbox is:"+this.checkBox);
	}
	public String getSubmit() {
		return Submit;
	}
	public void setSubmit(String submit) {
		Submit = submit;
	}
	public String getReset() {
		return Reset;
	}
	public void setReset(String reset) {
		Reset = reset;
	}
	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTypeofIssue() {
		return typeofIssue;
	}
	public void setTypeofIssue(String typeofIssue) {
		this.typeofIssue = typeofIssue;
	}
	public String getCustomerSearch() {
		return customerSearch;
	}
	public void setCustomerSearch(String customerSearch) {
		this.customerSearch = customerSearch;
	}
	public String getContactFirstName() {
		return contactFirstName;
	}
	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}
	public String getContactMiddleName() {
		return contactMiddleName;
	}
	public void setContactMiddleName(String contactMiddleName) {
		this.contactMiddleName = contactMiddleName;
	}
	public String getContactLastName() {
		return contactLastName;
	}
	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}
	public String getContactEmailID() {
		return contactEmailID;
	}
	public void setContactEmailID(String contactEmailID) {
		this.contactEmailID = contactEmailID;
	}
	

public ActionErrors validate(ActionMapping actionMapping,HttpServletRequest request)	
{
	String strHidden=(String)request.getParameter("subType");
	
	 ActionErrors actionErrors=new ActionErrors();
	
   if(((strHidden)!=null)&&(strHidden.equalsIgnoreCase("Submit")))	
	{
	   if(customerName!=null)
	   {
	     if(customerName.equals("")||customerName.length()==0)
	       {
		      actionErrors.add("CustomerName.Error",new ActionMessage("errors.customerName"));		
	       }
//	     else if(customerName.length()>0)
//			{
//				 boolean b = Pattern.matches("^[^0-9!@#$%\\^&*()~+=/.><|{}\\[\\]\\\\?:;\"'][a-z A-Z._]+$",customerName); 
//				 if(b==false)				 
//					actionErrors.add("CustomerName.Error", new ActionMessage("customerName.error"));				 
//			}
	   }
	   if(contactFirstName!=null)
	   {
	     if(contactFirstName.equals("")||contactFirstName.length()==0)
	       {
		      actionErrors.add("contactFirstName.Error",new ActionMessage("errors.contactFirstName"));		
	       }
	   }
	   if(contactLastName!=null)
	   {
	     if(contactLastName.equals("")||contactLastName.length()==0)
	       {
		      actionErrors.add("contactLastName.Error",new ActionMessage("errors.contactLastName"));		
	       }
	   }
		if(emailID!=null)
		{
			
			if(emailID.equals("") || emailID.length()==0)
			{
				actionErrors.add("EmailID.Error",new ActionMessage("errors.emailID"));
			}
			else if(emailID.length()>0)
			{
	        	String strEmail="^[^0-9!@#$%\\^&*()~+=/.><|{}\\[\\]\\\\?:;\"'][a-zA-Z0-9._]+@[a-zA-Z]+\\.[c][o][m]$";
			    boolean b = Pattern.matches(strEmail,emailID); 
			    if(b==false)
				  {
					actionErrors.add("EmailID.Error", new ActionMessage("emailID.error"));
				  }		
			}
		}
		
		if(contactEmailID!=null)
		{
			if(contactEmailID.equals("") || contactEmailID.length()==0)
			{
				actionErrors.add("ContactEmailID.Error",new ActionMessage("errors.emailID"));
			}
			else if(contactEmailID.length()>0)
			{
	        	String strEmail="^[^0-9!@#$%\\^&*()~+=/.><|{}\\[\\]\\\\?:;\"'][a-zA-Z0-9._]+@[a-zA-Z]+\\.[c][o][m]$";
			    boolean b = Pattern.matches(strEmail,contactEmailID); 
			    if(b==false)
				  {
					actionErrors.add("ContactEmailID.Error", new ActionMessage("emailID.error"));
				  }		
			}
		}
			
		/*if(custPassword!=null)
		{
			if(custPassword.equals(""))
			{
				actionErrors.add("CustPwd.Error",new ActionMessage("errors.common"));
			}
			else if(custPassword.length()<4)
			{
				actionErrors.add("CustPwd.Error",new ActionMessage("errors.customerPasswordLength"));
			}
		}*/
		
		if(mobileNO!=null)
		{
			if(mobileNO.equals("") || mobileNO.length()==0)
				actionErrors.add("MobileNO.Error",new ActionMessage("errors.mobileNO"));
		
			else if(mobileNO.length()<10 || mobileNO.length()>12)
			{
				/* boolean b = Pattern.matches("^[0-9]{10}$", mobileNO); 
				 if(b==false)*/				 
					actionErrors.add("MobileNO.Error", new ActionMessage("contactMobile.error"));				 
			}
		}
		/* if(state!=null && state!="" && !"".equals(state)) 
		{
			if(state.equals("") || state.length()==0)
				actionErrors.add("state.Error",new ActionMessage("errors.state"));
			else if(state.length()>0)
			{
				 boolean b = Pattern.matches("^[a-zA-Z]{15}$", state); 
				 if(b==false)				 
					actionErrors.add("state.Error", new ActionMessage("state.error"));				 
			}
		}
		
		if(country!=null && country!="" && !"".equals(country))
		{
			if(country.equals("") || country.length()==0)
				actionErrors.add("country.Error",new ActionMessage("errors.country"));
			else if(country.length()>0)
			{
				 boolean b = Pattern.matches("^[a-zA-Z]{15}$",country); 
				 if(b==false)				 
					actionErrors.add("country.Error", new ActionMessage("country.error"));				 
			}
		}*/
		if(zipCode!=null)
		{ 
		   //System.out.println("------------strZip---------"+strZip);
			if(zipCode.length()>0)
			{
				 //System.out.println("------------strZip1---------"+strZip);
				 boolean b = Pattern.matches("^[0-9]+$",zipCode); 
				 if(b==false)				 
					actionErrors.add("strZip.Error", new ActionMessage("Zip Code format is for ex.,500050",false));				 
			}
		}
		
		if(city!=null)
		{ 
			 if(city.length()>0)
			{
				 boolean b = Pattern.matches("^[a-z A-Z]+$",city); 
				 if(b==false)				 
					actionErrors.add("city.Error", new ActionMessage("City format is for ex.,Hyderabad",false));				 
			}
		}
	   
	   if(phoneNumber!=null)
        {
			//System.out.println("-----------phoneNumber-----------"+phoneNumber);
		    if(phoneNumber.equals("") || phoneNumber.length()==0)
        		actionErrors.add("PhoneNO.Error",new ActionMessage("errors.phoneNumber"));
        	else if(phoneNumber.length()>0)
        	{
    	       boolean b = Pattern.matches("^[0-9]+[-][0-9]+$", phoneNumber); 
               if(b==false)
    		    {
            	   boolean regExp = Pattern.matches("^[0-9]+[-][0-9]+[-][0-9]+$", phoneNumber); 
                   if(regExp==false)
                   {
            	     actionErrors.add("PhoneNO.Error", new ActionMessage("phoneNo2.error"));
                   }
    		   }
               
            }
        }
	   if(faxNo!=null && faxNo!="" && !"".equals(faxNo))
       {
			//System.out.println("-----------phoneNumber-----------"+phoneNumber);
		    if(faxNo.equals("") || faxNo.length()==0)
       		actionErrors.add("faxNo.Error",new ActionMessage("errors.faxNo"));
       	else if(faxNo.length()>0)
       	{
   	       boolean b = Pattern.matches("^[0-9]{3}[-][0-9]{3}[-][0-9]{4}$", faxNo); 
              if(b==false)
   		   {
   			 actionErrors.add("faxNo.Error", new ActionMessage("faxNo.error"));
   		   }
        }
       }
	   
	   if(address1!=null)
	   {
	     if(address1.equals("")||address1.length()==0)
	       {
		      actionErrors.add("strAddress1.size",new ActionMessage("errors.address1"));		
	       }
	   }
	   
	  /* if(extension!=null)
       {
       	if(extension.equals("") || mobileNO.length()==0)
       		actionErrors.add("extension.Error",new ActionMessage("errors.common"));
       	else if(mobileNO.length()>0)
       	{
   	       boolean b = Pattern.matches("^[0-9]{4}$", extension); 
              if(b==false)
   		   {
   			 actionErrors.add("extension.Error", new ActionMessage("extensionNo.error"));
   		   }
        }
       }*/
	   
	  /* if(companyName!=null)
        {
        	if(companyName.equals("")|| companyName.length()==0) 
        		actionErrors.add("CompanyName.Error",new ActionMessage("errors.companyName"));
        }*/
        
    }
	if(((strHidden)!=null)&&(strHidden.equalsIgnoreCase("Modify")))
	   {
		 if(customerName!=null)
		   {
		     if(customerName.equals("")||customerName.length()==0)
		       {
			      actionErrors.add("CustomerName.Error",new ActionMessage("errors.customerName"));		
		       }
//		     else if(customerName.length()>0)
//				{
//					 boolean b = Pattern.matches("^[^0-9!@#$%\\^&*()~+=/.><|{}\\[\\]\\\\?:;\"'][a-z A-Z._]+$",customerName); 
//					 if(b==false)				 
//						actionErrors.add("CustomerName.Error", new ActionMessage("customerName.error"));				 
//				}
		   }
		if(emailID!=null)
		{
			if(emailID.equals("") || emailID.length()==0)
			{
				actionErrors.add("EmailID.Error",new ActionMessage("errors.emailID"));
			}
			else if(emailID.length()>0)
			{
				String strEmail="^[^0-9!@#$%\\^&*()~+=/.><|{}\\[\\]\\\\?:;\"'][a-zA-Z0-9._]+@[a-zA-Z]+\\.[c][o][m]$";
			    boolean b = Pattern.matches(strEmail,emailID); 
			    if(b==false)
				  {
					actionErrors.add("EmailID.Error", new ActionMessage("emailID.error"));
				  }				   
			    }
		     }			
			
	/*	if(custPassword!=null)
		{
			if(custPassword.equals(""))
			{
				actionErrors.add("CustPwd.Error",new ActionMessage("errors.common"));
			}
			else if(custPassword.length()<4)
			{
				actionErrors.add("CustPwd.Error",new ActionMessage("errors.customerPasswordLength"));
			}
		}*/
		
		if(mobileNO!=null)
		{
			if(mobileNO.equals("") || mobileNO.length()==0)
				actionErrors.add("MobileNO.Error",new ActionMessage("errors.mobileNO"));
		
			else if(mobileNO.length()<10 || mobileNO.length()>12)
			{
				/* boolean b = Pattern.matches("^[0-9]{10}$", mobileNO); 
				 if(b==false)*/				 
					actionErrors.add("MobileNO.Error", new ActionMessage("contactMobile.error"));				 
			}
		}
	   
		if(phoneNumber!=null)
        {
			//System.out.println("-----------phoneNumber-----------"+phoneNumber);
		    if(phoneNumber.equals("") || phoneNumber.length()==0)
        		actionErrors.add("PhoneNO.Error",new ActionMessage("errors.phoneNumber"));
        	else if(phoneNumber.length()>0)
        	{
    	       boolean b = Pattern.matches("^[0-9]+[-][0-9]+$", phoneNumber); 
               if(b==false)
    		    {
            	   boolean regExp = Pattern.matches("^[0-9]+[-][0-9]+[-][0-9]+$", phoneNumber); 
                   if(regExp==false)
                   {
            	     actionErrors.add("PhoneNO.Error", new ActionMessage("phoneNo.error"));
                   }
    		   }
               
            }
        }
		   if(contactFirstName!=null)
		   {
		     if(contactFirstName.equals("")||contactFirstName.length()==0)
		       {
			      actionErrors.add("contactFirstName.Error",new ActionMessage("errors.contactFirstName"));		
		       }
		   }
		   if(contactLastName!=null)
		   {
		     if(contactLastName.equals("")||contactLastName.length()==0)
		       {
			      actionErrors.add("contactLastName.Error",new ActionMessage("errors.contactLastName"));		
		       }
		   }
		   
		   if(contactEmailID!=null)
			{
				if(contactEmailID.equals("") || contactEmailID.length()==0)
				{
					actionErrors.add("ContactEmailID.Error",new ActionMessage("errors.emailID"));
				}
				else if(contactEmailID.length()>0)
				{
		        	String strEmail="^[^0-9!@#$%\\^&*()~+=/.><|{}\\[\\]\\\\?:;\"'][a-zA-Z0-9._]+@[a-zA-Z]+\\.[c][o][m]$";
				    boolean b = Pattern.matches(strEmail,contactEmailID); 
				    if(b==false)
					  {
						actionErrors.add("ContactEmailID.Error", new ActionMessage("emailID.error"));
					  }		
				}
			}
	  
	  /* if(companyName!=null)
       {
       	if(companyName.equals("")|| companyName.length()==0) 
       	  {
       		actionErrors.add("CompanyName.Error",new ActionMessage("errors.companyName"));
          }
       }*/
	   
	  /* if(state!=null)
		{
			if(state.equals("") || state.length()==0)
				actionErrors.add("state.Error",new ActionMessage("errors.state"));
			else if(state.length()>0)
			{
				 boolean b = Pattern.matches("^[a-zA-Z]{15}$", state); 
				 if(b==false)				 
					actionErrors.add("state.Error", new ActionMessage("state.error"));				 
			}
		}
		
		if(country!=null)
		{
			if(country.equals("") || country.length()==0)
				actionErrors.add("country.Error",new ActionMessage("errors.country"));
			else if(country.length()>0)
			{
				 boolean b = Pattern.matches("^[a-zA-Z]{15}$",country); 
				 if(b==false)				 
					actionErrors.add("country.Error", new ActionMessage("country.error"));				 
			}
		}*/
		
	   
	  /* if(extension!=null)
       {
       	if(extension.equals("") || mobileNO.length()==0)
       		actionErrors.add("extension.Error",new ActionMessage("errors.common"));
       	else if(mobileNO.length()>0)
       	{
   	       boolean b = Pattern.matches("^[0-9]{4}$", extension); 
              if(b==false)
   		   {
   			 actionErrors.add("extension.Error", new ActionMessage("extensionNo.error"));
   		   }
        }
       }*/
		 if(zipCode!=null)
			{ 
			   //System.out.println("------------strZip---------"+strZip);
				if(zipCode.length()>0)
				{
					 //System.out.println("------------strZip1---------"+strZip);
					 boolean b = Pattern.matches("^[0-9]+$",zipCode); 
					 if(b==false)				 
						actionErrors.add("strZip.Error", new ActionMessage("Zip Code format is for ex.,500050",false));				 
				}
			}
			
			if(city!=null)
			{ 
				 if(city.length()>0)
				{
					 boolean b = Pattern.matches("^[a-z A-Z]+$",city); 
					 if(b==false)				 
						actionErrors.add("city.Error", new ActionMessage("City format is for ex.,Hyderabad",false));				 
				}
			}
		if(faxNo!=null && faxNo!="" && !"".equals(faxNo))
	       {
				//System.out.println("-----------phoneNumber-----------"+phoneNumber);
			    if(faxNo.equals("") || faxNo.length()==0)
	       		actionErrors.add("faxNo.Error",new ActionMessage("errors.faxNo"));
	       	else if(faxNo.length()>0)
	       	{
	   	       boolean b = Pattern.matches("^[0-9]{3}[-][0-9]{3}[-][0-9]{4}$", faxNo); 
	              if(b==false)
	   		   {
	   			 actionErrors.add("faxNo.Error", new ActionMessage("faxNo.error"));
	   		   }
	        }
	       }
		if(address1!=null)
		   {
		     if(address1.equals("")||address1.length()==0)
		       {
			      actionErrors.add("strAddress1.size",new ActionMessage("errors.address1"));		
		       }
		   }   
		
	 }
	return actionErrors;
 }


}