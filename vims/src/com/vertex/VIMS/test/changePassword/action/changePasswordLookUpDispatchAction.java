/*  
  FileName	    : changePasswordLookUpDispatchAction.java
  
                  
  
  Description	: This action class is used to call different methods 
                  in BD(Business Delegate)class and set the return values 
                  in attributes and access different methods present by using 
                  method called getKeyMethodMap.
                  	
  Developed by  : Vertex Computer Systems.
                  copyright (c) 2008 Vertex.
                  All rights reserved.
                   
  Change History:
 
  Revision No.	:		Date		  @author		Description
    1.0				Friday 15,2008	  Geeta.M	   Initial Version.*/


/**
 * @author geeta.m
 *
 */

//Package structure followed for this class.
package com.vertex.VIMS.test.changePassword.action;

//import statements  
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.struts.actions.LookupDispatchAction;
import com.vertex.VIMS.test.changePassword.BD.changePasswordBD;
import com.vertex.VIMS.test.changePassword.form.changePasswordForm;

//Start of changePasswordLookUpDispatchAction class
	public class changePasswordLookUpDispatchAction extends LookupDispatchAction 
	 {
      
	 /********Method------------>updatePasswordBasePage***********/
	 /********This method returns to the home page of the Change Password..*******/
			
		public ActionForward updatePasswordBasePage(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
		{
		    return actionMapping.findForward("home1");  	
		}
	
	/********Method------------>updatePassword***********/
	/********This method is used to change the password with the given old password*******/
			
		public ActionForward updatePassword(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
		{
		  try  
		          {
				     changePasswordForm changePwdForm=(changePasswordForm)actionForm;
				     HttpSession session = request.getSession(false);
				     //String getOldPassword=request.getParameter("oldPassword");
                     String getNewPassword=request.getParameter("NewPassword");
				     //System.out.println("---------getNewPassword--------"+getNewPassword);
                     
                     String strUserID=(String)session.getAttribute("user");
				     //System.out.println("----------strUserID--------"+strUserID);
				     //System.out.println("------In updatePassword----------");
				     int intValue=changePasswordBD.changePwdBD(getNewPassword,strUserID);
				     
				     
                     //System.out.println("--------intValue-------"+intValue);
				     session.removeAttribute("status");
                     
				     String strMsg=""; 
	          		   
	          		   if(intValue==0)
	          		   {
	          			   strMsg="Password updated successfully";
	      			   }
	          		   else
	      		       {
	      			    strMsg="ok";
	      		       }
			          		request.setAttribute("MSG","Password Updated Successfully");   
			     			response.setContentType("text/xml");        
			     	        response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			     			response.getOutputStream().println("<response>");
			     			response.getOutputStream().println("<result>" + strMsg + "</result>");
			     			response.getOutputStream().println("</response>");
		     		    
	     			   
				     
				     
				     //request.setAttribute("UPDTMSG","Password Updated Successfully.");	
                     //return actionMapping.findForward("home");  	
                  }
		          catch (Exception exception)
	              {
                     System.out.println("IN Exception============>"+exception);
                  }
		       return null;
	     }
	
	/********Method------------>checkOldPassword***********/
	/********This method is used to check whether the password matches with the password which is already present in the DB using Ajax Fuctionality.*******/
	
   public ActionForward checkOldPassword(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
	{
		  try
		          {
				     changePasswordForm changePwdForm=(changePasswordForm)actionForm;
				     HttpSession session = request.getSession(false);
				     
				     String getOldPassword=request.getParameter("Email");
						  
				     String strUserID=(String)session.getAttribute("user");
				     //System.out.println("----------strUserID---------"+strUserID);
				     //String getOldPassword=request.getParameter("oldPassword");
				     //System.out.println("----------getOldPassword---------"+getOldPassword);
				     //String getNewPassword=request.getParameter("newPassword");
				     //System.out.println("----------getNewPassword---------"+getNewPassword);
				     //System.out.println("----------In checkOldPassword---------");
                     boolean result=changePasswordBD.checkOldPasswordBD(getOldPassword,strUserID);
                     String strMsg=""; 
          		   
          		   if(result==false)
          		   {
          			   strMsg="Incorrect Password";
      			   }
          		   else
      		       {
      			    strMsg="ok";
      		       }
		          		request.setAttribute("MSG","Incorrect Password");   
		     			response.setContentType("text/xml");        
		     	        response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		     			response.getOutputStream().println("<response>");
		     			response.getOutputStream().println("<result>" + strMsg + "</result>");
		     			response.getOutputStream().println("</response>");
	     		    
     			   return null;
                     
		     }
		  catch (Exception exception)
          {
             System.out.println("IN Exception============>"+exception);
			 return null;
          }
	}
   
   /********Method------------>getKeyMethodMap***********/
	 public Map getKeyMethodMap()
	   {
		  
		//Creating a map object 
		  Map map=new HashMap();

	  /***********Here the key is specified to which method it should call.*************/
		  map.put("VIMSApplication.UpdateBase","updatePasswordBasePage");
		  map.put("VIMSApplication.Update","updatePassword");
		  map.put("VIMSApplication.checkOldPassword","checkOldPassword");
		 
	   //Returns map 
		  return map;
	   }
}