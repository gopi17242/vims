package com.vertex.VIMS.test.changePassword.form;

import org.apache.struts.action.*;

import javax.servlet.http.*;

import java.util.regex.Pattern;


public class changePasswordForm extends ActionForm
{
    String oldPassword;
    String newPassword;
    String confirmPassword;
    
    
	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}
	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
		//System.out.println("----------oldPassword-------"+oldPassword);
	}
	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		
		return newPassword;
	}
	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
		//System.out.println("----------newPassword-------"+newPassword);

	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
		//System.out.println("----------confirmPassword-------"+confirmPassword);

	}
	
	
 public ActionErrors validate(ActionMapping actionMapping,HttpServletRequest request)	
	{
	  //System.out.println("---------In Bean of change Password----------");
	  
	  String strHidden=(String)request.getParameter("subType");
	 
	  //System.out.println("=========strHidden========"+strHidden);
	  
	  ActionErrors actionErrors=new ActionErrors();
	  
  if(((strHidden)!=null)&&(strHidden.equalsIgnoreCase("Submit")))	
	{
	  //System.out.println("--------oldPassword-------"+oldPassword);
	  if(oldPassword!=null)
	   {
	     if(oldPassword.equals("")||oldPassword.length()==0)
	       {
		      actionErrors.add("oldPassword.Error",new ActionMessage("errors.oldPassword"));		
	       }
	   }
	  //System.out.println("--------newPassword-------"+newPassword);
		 
	  if(newPassword!=null)
	   {
	     if(newPassword.equals("")||newPassword.length()==0)
	       {
		      actionErrors.add("newPassword.Error",new ActionMessage("errors.newPassword"));		
	       }
	     
	     }
	  //System.out.println("--------confirmPassword-------"+confirmPassword);
		  
	  if(confirmPassword!=null)
	  {
		  if(confirmPassword.equals("")||confirmPassword.length()==0)
	       {
		      actionErrors.add("confirmPassword.Error",new ActionMessage("errors.confirmPassword"));		
	       }
	  }
			 
	 }
	  return actionErrors;
	}

 }

