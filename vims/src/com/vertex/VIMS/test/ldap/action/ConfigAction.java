package com.vertex.VIMS.test.ldap.action;
/**
 * @author Rajashekar.B
 * 
 */

import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;


import com.vertex.VIMS.test.common.ContryStateList;
import com.vertex.VIMS.test.ldap.form.ConfigForm;
import com.vertex.VIMS.test.ldap.form.VIMSTabForm;
import com.vertex.VIMS.test.ldap.BD.ConfigBD;
import com.vertex.VIMS.test.login.BD.LoginBD;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

public class ConfigAction extends LookupDispatchAction {
	
	 static Logger logger=null;
     static ConfigForm configForm;
     static String strServerId=null;
     static String strOperationType=null;
     static String strUserId=null;
  /*  
   * The ldapConfig action is for storing the LDAP server details.
   *
   * @param mapping
   * @param form
   * @param request
   * @param response
   * 
   */   
  public ActionForward ldapConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce)
  {

   String strServerName;
   String strPortNumber;
   String status="Active";
   String strServerId="0";
   String result=null;
   HttpSession session=null;
   
   try
   {
	   session=request.getSession(false);
	   
	   if(session!=null) 
	   {  // If starts
		   configForm=(ConfigForm)form;
		   strServerName=configForm.getServerName();
		   strPortNumber=configForm.getPortNumber();
		   
		 
		   if(session.getAttribute("serverId")!=null)
		   { // If starts
			   status=configForm.getServerStatus();
			   strServerId=(String)session.getAttribute("serverId");
			   // Remove server id from session
			   session.removeAttribute("serverId");
			   result=ConfigBD.storeLdapConfig(strServerName,strPortNumber,status,strServerId);
		   } //If ends
		   else
		   {  // Else starts
			   status=configForm.getServerStatus();
               result=ConfigBD.storeLdapConfig(strServerName,strPortNumber,status,strServerId);			   
		   } // Else ends
		 } // If Ends
	     else {
		        return mapping.findForward("sessionExpirePage");   
	   }
	   
    } // try block ends
      catch(Exception exception)
     { 
    	  logger.info("=======Exception in ldapConfig action----------"); 
    	  logger.error(exception);
     } // catch block ends
        
		   if(result!=null) 
		   { // If starts   
			   session.setAttribute("serverList",ConfigBD.getServerList("LdapServer"));
			   if(result.equals("Server Details Added Successfully")) {
			    request.setAttribute("successMsg",result);
			    System.out.println("=====successMsg==========="+result);
			   } 
			   else if(result.equals("Server Name already exists")) {
				   System.out.println("---------failureMsg------------"+result);
				   request.setAttribute("failureMsg", result);
			   } else if(result.equals("Server Details Modified Successfully")) {
				   System.out.println("=====successMsg==========="+result);
				   request.setAttribute("successMsg",result);
			   }
		   }
		   else
		   {  // Else starts
		      request.setAttribute("failureMsg","Internal Problem");
		   } // If ends
		  
		   request.setAttribute("optStatus","1");
		   return mapping.findForward("ldap");
  }
  
  /*
   * The mailConfig action is used for storing the Mail server details and also to modify the details. 
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   *  
   */
  public ActionForward mailConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce) {

	      String strServerName=null;
          String result=null;
          HttpSession session=null;
          String strStatus="Active";
          
      try { 
    	   	session=request.getSession(false);
    	   	if(session!=null) {
    		
    	   		logger=Logger.getLogger("Admin");
    	   		configForm=(ConfigForm)form;
    	   		strServerName=configForm.getServerName();
    	   		if(session.getAttribute("serverId")!=null)
    	   		{
    	   			strStatus=configForm.getServerStatus();
    	   			strServerId=(String)session.getAttribute("serverId");
    	   			// Remove server id from session
     	    		   session.removeAttribute("serverId");
    	   			result=ConfigBD.storeMailConfig(strServerName,strStatus,strServerId);
    	   		}
    	   		else {
    	   			
    	   			strServerId="0";
    	   			strStatus=configForm.getServerStatus();
    	   			result=ConfigBD.storeMailConfig(strServerName,strStatus,strServerId);			   
    	   		}	   
           
    	   			if(result!=null) { 
    	   				if(session.getAttribute("serverId")!=null)
         	    		   session.removeAttribute("serverId");
    	   				
    	   				ConfigBD.getServerList("MailServer");
    	   				session.setAttribute("serverList",ConfigBD.getServerList("MailServer"));
    	   				if(result.equals("Server Details Added Successfully")) {
    	   					System.out.println("=====successMsg==========="+result);
    	   					request.setAttribute("successMsg",result);
    	   				   } 
    	   				   else if(result.equals("Server Name already exists")) {
    	   					System.out.println("=====failureMsg==========="+result);
    	   					   request.setAttribute("failureMsg", result);
    	   				   } else if(result.equals("Server Details Modified Successfully")) {
    	   					System.out.println("=====successMsg==========="+result);
    	   					   request.setAttribute("successMsg",result);
    	   				   }
    	   			    
    	   			} else {
    	   				request.setAttribute("failureMsg","Internal Problem");
    	   			}
    	   			  request.setAttribute("optStatus","1");
    	   		      return mapping.findForward("mail");	 
    	   		}
    	   		else {
    	   		      return mapping.findForward("sessionExpirePage");
    	   			}
    	 	} 
          	 catch(Exception exception) {
	
          			logger.info("=======Exception raised in mail action========");
          			logger.error(exception);
          			return mapping.findForward("errorPage");
          	 }
      }
        // End of mailConfig action
      
   /*
    * The getHomeEnv action is to get the configuration settings details to initially display in the page.
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * 
    */
      	public ActionForward getHomeEnv(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce) {

                HashMap customOptions=null;
                HttpSession session=null;
                String strUserId=null;
                
            try {
        	     if(request.getSession(false)!=null) {
	              logger=Logger.getLogger("Admin");
   	              session=request.getSession(false); 
	              strUserId=(String)session.getAttribute("user");
	              customOptions=ConfigBD.getCustomOptions(strUserId);
                  if(customOptions!=null)
                  session.setAttribute("customOptions",customOptions);	 
             }
			   else {
				   return mapping.findForward("sessionExpirePage");
			   }
        } catch(Exception exception) {
			
			   logger.info("==Exception raised in getHomeEnv action========"+exception);
			   logger.error(exception);
			   return mapping.findForward("errorPage");
			}
               return mapping.getInputForward();
   }
  
    /*
     * The getLdapEnv action to get LDAP server details,it retrieves all the LDAP servers available.
     * 
     * @param mapping 
     * @param form
     * @param request
     * @param response
     * 
     */     	
      	public ActionForward getLdapEnv(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce) {
                  
	                       ArrayList serverList=null;
	                       HttpSession session=request.getSession(false);
	              try  {
	            	      if(request.getSession(false)!=null) {
	            	    	  // If starts
	            	    	   logger.getLogger("Admin");
	            	    	   
	            	    	   if(session.getAttribute("serverId")!=null)
	            	    		   session.removeAttribute("serverId");
	            	      // Get the Ldap server list from the database
	            	    	  serverList=ConfigBD.getServerList("LdapServer");
	            	        if(serverList!=null) { // If server list is not empty
	            	    	   // If starts
	            	    	   session.setAttribute("serverList",serverList);
	            	    	   return mapping.getInputForward();
	            	    	} // End of If
	            	     }
	            	      else { // Else starts
	            	    	  return mapping.findForward("sessionExpirePage");
	            	      }
	            	    // End of If block  
	            	  } // try block ends 
	              catch(Exception exception) {
	            		  logger.info("--------Exception in getLdapEnv action=======");
	            		  logger.error(exception);
	            	     }
	     	    	      return mapping.findForward("errorPage"); 
        }
    // End of getLdapEnv action
      	
      	/*
         * The getmailEnv action to get Mail server details,it retrieves all the Mail servers available.
         * 
         * @param mapping 
         * @param form
         * @param request
         * @param response
         * 
         */   	
  public ActionForward getMailEnv(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce) {
		      
	      ArrayList serverList=null;
	      HttpSession session=null;
  try  {
	      session=request.getSession(false);
	     if(session!=null) { // If starts
	  	   logger.getLogger("Admin");
		   	   
		   // Get the Ldap server list from the database
	       serverList=ConfigBD.getServerList("MailServer");
	   	   if(serverList!=null) { // If starts
	   	     session.setAttribute("serverList",serverList);
	   	    return mapping.getInputForward();
		  } // If ends
		 } // End of If
	     else {
	    	 return mapping.findForward("sessionExpirePage");
	     }
	   }  // try block closes
        catch(Exception exception) {
		   logger.info("--------Exception in getLdapEnv action=======");
		   logger.error(exception);
		  
		}
		  return mapping.findForward("errorPage"); 
  }
   // End of getMailEnv action
  
  /*
   * The getLdapDetails action is to get a single LDAP server details.This action will be invoked when 
   * user wants to modify a single LDAP server details.
   * 
   * @param mapping 
   * @param form
   * @param request
   * @param response
   * 
   */
  
 public ActionForward getLdapDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce) 
 {
		      
      HashMap serverRecord=null;
      String strServerName=null;
      HttpSession session=null;
      
      try
      {   session=request.getSession(false);
      
          if(session!=null) { // If block starts
          logger=Logger.getLogger("Admin");	  
    	  configForm=(ConfigForm)form;
    	  
    	  strServerId=request.getParameter("serverId");
    	  serverRecord=ConfigBD.getServerDetails("LdapServer",strServerId,"0");
    	  strServerName=(String)serverRecord.get("Server_Name");
    	  
    	  configForm.setServerName(strServerName.substring(7,(strServerName.length()-5)));		   		   
   		  configForm.setPortNumber(strServerName.substring((strServerName.length()-4)));		   		   
   		  configForm.setServerStatus((String)serverRecord.get("Server_Status"));		   		  
   		  session.setAttribute("serverId", strServerId);
         } // If ends
          else { // Else block starts
        	  return mapping.findForward("sessionExpirePage");
          }// End of Else block
          
   	  }// End of try block
       catch(Exception exception)
       { 
    	  logger.info("-----EXCEPTION IN getLdapDetails action-------");  
    	  logger.error(exception);
	   } // End of catch block
         return mapping.getInputForward();      
 }
   // End of getLdapDetails action
 
 /*
  * The deleteLdapDetails action is to delete a single LDAP server details.This action will be invoked when 
  * user wants to delete a single LDAP server details.
  * 
  * @param mapping 
  * @param form
  * @param request
  * @param response
  * 
  */
		
 public ActionForward deleteLdapDetails(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request, HttpServletResponse response)
 {
	 String strServerId=null;
	 int serverStatus=0;
	 HttpSession session=null;
	 
	 try { session=request.getSession(false);
           if(session!=null) { // If block starts
			 strServerId=(String)request.getParameter("serverId");
			 serverStatus=ConfigBD.deleteServerDetails("ldap",strServerId);
			 // The serverStatus holds the operation result 
			 if(serverStatus!=0)
			 { // If starts 
				  session.setAttribute("serverList",ConfigBD.getServerList("LdapServer"));
				 request.setAttribute("successMsg","Sussessfully deleted");
				 if(session.getAttribute("serverId")!=null)
				 session.removeAttribute("serverId");
			 }// End of If
			 else
			 { // Else block starts	
				 request.setAttribute("failureMsg","failed to delete");
			 } // Else block ends
           } //If block ends 
           else {
        	   return actionMapping.findForward("sessionExpirePage");
           } // End of Else block
        	   
		} catch(Exception exception) {
				logger.info("----Exception in deleteLdapDetails action----------");
				logger.error(exception);
			}
			return actionMapping.findForward("ldap");
 }
  // End of deleteLdapDetails action 
 
 /*
  * The getMailDetails action is to get a single Mail server details.This action will be invoked when 
  * user wants to modify a single Mail server details.
  * 
  * @param mapping 
  * @param form
  * @param request
  * @param response
  * 
  */
 public ActionForward getMailDetails(ActionMapping actionMapping,ActionForm form,HttpServletRequest request,HttpServletResponse responce) 
 {
		      
      HashMap serverRecord=null;
      String strServerName=null;
      HttpSession session=null;
      String strDefaultId=null;
      
      try { 
    	    session=request.getSession(false);
    	    if(session!=null) { // If starts
		    	  configForm=(ConfigForm)form;
		    	  strServerId=request.getParameter("serverId");
		          strDefaultId=request.getParameter("default_Id");	 
		    	  // Get the server details
		    	  serverRecord=ConfigBD.getServerDetails("MailServer",strServerId,strDefaultId);
		    	  
		    	  strServerName=(String)serverRecord.get("Server_Name");
		    	  configForm.setServerName(strServerName);		   		   
		   		  configForm.setServerStatus((String)serverRecord.get("Server_Status"));		   		  
		   		  session.setAttribute("serverId", strServerId);
    	    } // End of If
    	    else { // Else block starts
    	    	return actionMapping.findForward("sessionExpirePage");
    	    } // End of Else block
   	  } // End of try block
      catch(Exception exception)
      { // catch block starts
    	  logger.info("-------Exception getMailDetails action---------" ); 
    	  logger.error(exception);
	  } // catch block ends
         return actionMapping.getInputForward();      
 }
  // end of getMailDetails action	      
	
 /*
  * The deleteMailDetails action is to delete a single Mail server details.This action will be invoked when 
  * user wants to delete a single Mail server details.
  * 
  * @param mapping 
  * @param form
  * @param request
  * @param response
  * 
  */
 
 public ActionForward deleteMailDetails(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request, HttpServletResponse response)
 {
	 String strServerId=null;
	 int serverStatus=0;
	 HttpSession session=null;
	 
	 try {
		    session=request.getSession(false); 
		    if(session!=null) { // If starts
			 strServerId=(String)request.getParameter("serverId");
			 serverStatus=ConfigBD.deleteServerDetails("mail",strServerId);
			 if(serverStatus!=0)
			 { // If block starts
                 session.setAttribute("serverList",ConfigBD.getServerList("MailServer"));
				 request.setAttribute("successMsg","Sussessfully deleted");
				 if(session.getAttribute("serverId")!=null)
					 session.removeAttribute("serverId");
				 return actionMapping.findForward("mail");
			 } // End of If block
			 else
			 { // Else block starts
				 request.setAttribute("failureMsg","failed to delete");
			 }
		 } // End of If  
		  else { // Else starts
		    	return actionMapping.findForward("sessionExpirePage");
		    } // End of Else
		    
	 } // End of try block
	  catch(Exception exception) {
		  logger.info("----Exception in deleteMailDetails action-------- ");
		  logger.error(exception);
	  }
	     return actionMapping.findForward("mail");
  }
   // End of deleteMailDetails action.   
 
 /*
  * The persistHomePageSettings action is to persist the home page settings approved by logged in user.
  * 
  * @param mapping 
  * @param form
  * @param request
  * @param response
  * 
  */ 
   public ActionForward persistHomePageSettings(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
    	 
    	       HttpSession session=null;
    	       ConfigForm configForm=null;
    	       String[] selectedOptions=null;
    	       String selectedDuration=null;
    	       String strUserId=null;
    	       int result=1;
    	       HashMap customOptions=null;
    	  try { // try block starts
    		      session=request.getSession(false);
    		      if(session!=null) { // If  starts
  		               logger=Logger.getLogger("Admin");	
    		           configForm=(ConfigForm)actionForm;
    		    	  // Get the list of selected options
    		    	  selectedOptions=configForm.getSelectedOptions();
    		    	  selectedDuration=configForm.getDurationDays();
    		    	  if(selectedOptions!=null) { // If block starts
    		    		   strUserId=(String)session.getAttribute("user");
    		    		   result=ConfigBD.persistSettings(selectedOptions,selectedDuration,strUserId);
    		    		} // End of If block
    		    	  
    		    	  if(selectedOptions==null) { // If starts
  	    	 	    	  strUserId=(String)session.getAttribute("user");
	    		    	  result=ConfigBD.persistSettings(selectedOptions,selectedDuration,strUserId);
    		       	   } // End of If
  	               
			    	  } else {
			    		   return actionMapping.findForward("sessionExpirePage");
			    	   }
    		          } // End of try block
    		             catch(Exception exception) {
			    		   logger.info("------Exception in persistHomePageSettings action---------");
			    		   logger.error(exception);
			    	   }
			    	   
    		           // Get the latest settings details and place in session object  
     		           customOptions = ConfigBD.getCustomOptions(strUserId);
		    		   session.setAttribute("customOptions",customOptions);
			    	  
		    		   if(result==1) { // If starts
			    		    request.setAttribute("MSG","Settings have been saved.");
			    	    } // If ends 
		    		     else { // Else starts
			    		     request.setAttribute("MSG","Settings Not Apporved");
			    	     } // End of Else
		    		   return actionMapping.findForward("homeBase");
			    	   
    }
   // End of persistHomePageSettings action
   
   /*
    * The getFieldConfigurationSettings() action is for getting the available field options which can be configured for total Application.
    * 
    * @param actionMapping 
    * @param actionForm
    * @param request
    * @param response
    * 
    */
   public ActionForward getFieldConfigurationSettings(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
	   
	            HttpSession session=null;
	             
	     try {
	    	   logger=Logger.getLogger("Admin");
	    	   session=request.getSession(false);
	    	   if(session!=null) {
	    		   
	    		   if(session.getAttribute("PositionId")!=null)
	    			   session.removeAttribute("PositionId");
	    		   // Push the available field options into session from DAO
	    		   ConfigBD.getCustomFieldOptionsBD(session);
	    	   }
	    	   else {
	    		    return actionMapping.findForward("sessionExpirePage");
	    	   }
		     }
	      catch(Exception exception) {
	    	  
	    	   logger.info("------Excepion in getFieldConfigurationSettings action-------");
	    	   logger.error(exception);
	    	   return actionMapping.findForward("errorPage");
	      }
	   return actionMapping.getInputForward();
   }
     // End of getFieldConfigurationSettings action
   
   
   /*
    * The getFieldConfigurationSettings() action is for getting the available field options which can be configured for total Application.
    * 
    * @param actionMapping 
    * @param actionForm
    * @param request
    * @param response
    * 
    */
   public ActionForward addSpecificKeywordOptionValue(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
	   
	         HttpSession session=null;
	         String strKeywordType=null;
	         String strKeywordValue=null;
	         String strPositionId=null;
	         String strUserId=null;
	         String strPositionStatus=null;
	         String strRoleType=null;
	             
	     try {
	    	   logger=Logger.getLogger("Admin");
	    	   session=request.getSession(false);
	    	   if(session!=null) {
	    		  
	    		   // Get the user id from session
	    		   strUserId=(String)session.getAttribute("user");
	    		   // Get the Form
	    		    configForm=(ConfigForm)actionForm;
	    		   // Get the keyword type of whose value to be inserted
	    		    strKeywordType=configForm.getKeywordType();
	    		    strKeywordValue=configForm.getKeywordValue();
	    		    strPositionStatus=configForm.getPositionStatus();
	    		    strRoleType=configForm.getRoleType();
	    		    
	    		    if((strKeywordType!=null)&&(strKeywordValue!=null)) {
	    		    	
	    		    	System.out.println("======strKeywordType==========="+strKeywordType);
	    		    	System.out.println("======strKeywordValue==========="+strKeywordValue);
	    		    	
	    		    	if(strKeywordType.equals("Employee Position")) {
	    		    		
	    		    		if(session.getAttribute("PositionId")!=null) {
	    		    			strPositionId=(String)session.getAttribute("PositionId");
	    		    			// remove from the session
	    		    			session.removeAttribute("PositionId");
	    		    		}	
	    		    		else {
	    		    			strPositionId="0";
	    		    		}
	    		    		
	    		    		String strResult=ConfigBD.addPositionBD(strPositionId,strKeywordValue,strPositionStatus,strRoleType,strUserId);
	    		    		System.out.println("======result========="+strResult);
	    		    		request.setAttribute("successMsg",strResult);
	    		    	}
	    		    }
	    		      
                       return actionMapping.findForward("fieldHome"); 	    		     
	    	   }
	    	   else {
	    		    return actionMapping.findForward("sessionExpirePage");
	    	   }
		     }
	      catch(Exception exception) {
	    	  
	    	   logger.info("------Excepion in addSpecificKeywordOptionValue action-------");
	    	   logger.error(exception);
	    	   return actionMapping.findForward("errorPage");
	      }
	 //  return actionMapping.getInputForward();
   }
     // End of addSpecificKeywordOptionValue action
   
   
   /*
    * The getPositionList() action is for getting the available Position list in VIMS application.
    * 
    * @param actionMapping 
    * @param actionForm
    * @param request
    * @param response
    * 
    */
   public ActionForward getPositionList(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
	   
	         HttpSession session=null;
	         ArrayList positionList=null;
	         ArrayList roleTypes=null;
	         
	                      
	     try {
	    	   logger=Logger.getLogger("Admin");
	    	   session=request.getSession(false);
	    	   
	    	   if(session!=null) { // if block starts
	    	  		
	    		   strUserId=(String)session.getAttribute("user");
	    		   positionList=ConfigBD.getPositionListBD("0",strUserId);
	    		   roleTypes=ConfigBD.getRoleTypesBD();
	    		   
	    		   // Role types setting into the session
	    		   if(roleTypes!=null)
	    			   session.setAttribute("roleTypes",roleTypes);
	    		   
	    		   if((positionList!=null)&&(positionList.size()>0)) {  // If block starts
	    			   
	    			   session.setAttribute("positionList",positionList);
	    		   } // If block ends
	    		   else { // else block starts
	    			   request.setAttribute("NoPostion","No Postions Currently available");
	    		   } // End of elese block
	    		   
	    		    // return to the display page
	    			return actionMapping.getInputForward();
	    	    } // if block ends
	    	   else { // else block starts
	    		    return actionMapping.findForward("sessionExpirePage");
	    	   } // end of else block
	    	   
		     } // try block closes
	      catch(Exception exception) {  // catch block starts
	    	  
	    	   logger.info("------Excepion in getPositionList action-------");
	    	   logger.error(exception);
	    	   return actionMapping.findForward("errorPage");
	      } // catch block ends
	      
	 //  return actionMapping.getInputForward();
   }
     // End of getPositionList action
   
   
   /*
    * The deletePosition() action is for deleting a Position role  from VIMS application.
    * 
    * @param actionMapping 
    * @param actionForm
    * @param request
    * @param response
    * 
    */
   public ActionForward deletePosition(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
	   
	         HttpSession session=null;
	         ArrayList positionList=null;
	         String strPositionId=null;
	         String strResult=null;
	                      
	     try {
	    	   logger=Logger.getLogger("Admin");
	    	   session=request.getSession(false);
	    	   if(session!=null) { // if block starts
	    		   strUserId=(String)session.getAttribute("user");
	    		   if(request.getParameter("positionNumber")!=null) 
	   	    	      strPositionId=request.getParameter("positionNumber");
	   	    	   	    		   
	    		   if(strPositionId!=null)
	    		 	strResult=ConfigBD.deletePositionBD(strPositionId,strUserId);
	    		   
	    		  if(strResult!=null) {
	    			  positionList=ConfigBD.getPositionListBD("0",strUserId);
	    		   if((positionList!=null)&&(positionList.size()>0)) {  // If block starts
	    			  session.setAttribute("postionList",positionList);
	    		   } // If block ends
	    		  }     		   
	    		   // return to the display page
	    		  request.setAttribute("successMsg",strResult);
	    		  return actionMapping.findForward("positionHome");
	    	    } // if block ends
	    	   else { // else block starts
	    		    return actionMapping.findForward("sessionExpirePage");
	    	   } // end of else block
	    	   
		     } // try block closes
	      catch(Exception exception) {  // catch block starts
	    	  
	    	   logger.info("------Excepion in deletePosotion action-------");
	    	   logger.error(exception);
	    	   return actionMapping.findForward("errorPage");
	      } // catch block ends
	      
	 //  return actionMapping.getInputForward();
   }
     // End of deletePosition action
   
    
   /*
    * The modifyPosotion() action is for modifying a Position role  from VIMS application.
    * 
    * @param actionMapping 
    * @param actionForm
    * @param request
    * @param response
    * 
    */
   public ActionForward modifyPosition(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
	   
	         HttpSession session=null;
	         ArrayList positionList=null;
	         String strPositionId=null;
	         String strResult=null;
	         ArrayList postionList=null;
	         HashMap tempRecord;
	                      
	     try {
	    	   logger=Logger.getLogger("Admin");
	    	   session=request.getSession(false);
	    	   	   
	    	   if(session!=null) { // if block starts
	    		   
	    		   System.out.println("======action called============");
	    		   strUserId=(String)session.getAttribute("user");
	    		   
	    		   // Get the form bean object
	    		   configForm=(ConfigForm)actionForm;
	    		   
	    		   if(request.getParameter("positionNumber")!=null) {
	    			   System.out.println("====position number as parameter========="+request.getParameter("positionNumber"));
	    			   strPositionId=request.getParameter("positionNumber");
	    		   }   
	   	    	   else if(request.getAttribute("positionNumber")!=null) {
	   	    		   System.out.println("====position number as attribute=========");
	   	    		   strPositionId=(String)request.getAttribute("positionNumber");
	   	    	   }
	    		   if(strPositionId!=null) {
	    		       positionList=ConfigBD.getPositionListBD(strPositionId,strUserId);
	    		       System.out.println(positionList);
	    		   }  
	    		    if((positionList!=null)&&(positionList.size()>0)) {
	    		    	
	    		    	tempRecord=(HashMap)positionList.get(0);
	    		    	configForm.setKeywordValue((String)tempRecord.get("positionTitle"));
	    		    	System.out.println("======record name========="+(String)tempRecord.get("positionTitle"));
	    		    	if(((String)tempRecord.get("positionFlag")).equals("Active"))
	    		    	 configForm.setPositionStatus("0");
	    		    	else
	    		    		configForm.setPositionStatus("1");
	    		    	String t=LoginBD.getRoleIdBD((String)tempRecord.get("roleType"));
	    		    	configForm.setRoleType(t);
	    		    	// set the position id in session
	    		    	session.setAttribute("PositionId",(String)tempRecord.get("positionId"));
	    		    }
	    		    // return to the display page
	    		    return actionMapping.getInputForward();
	    		    //return actionMapping.findForward("positionHome");
	    	    } // if block ends
	    	   else { // else block starts
	    		    return actionMapping.findForward("sessionExpirePage");
	    	   } // end of else block
	    	   
		     } // try block closes
	      catch(Exception exception) {  // catch block starts
	    	  
	    	   logger.info("------Exception in modifyPosition action-------");
	    	   logger.error(exception);
	    	   exception.printStackTrace();
	    	   return actionMapping.findForward("errorPage");
	      } // catch block ends
      }
   // End of modifyPosition action
   
   
   /*
    * The getLocations() action is for deleting a Position role  from VIMS application.
    * 
    * @param actionMapping 
    * @param actionForm
    * @param request
    * @param response
    * 
    */
   public ActionForward getLocations(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
	   
	         HttpSession session=null;
	         ArrayList locationList=null;
	     try {
	    	   logger=Logger.getLogger("Admin");
	    	   session=request.getSession(false);
	    	   	   
	    	   if(session!=null) { // if block starts
	    	  		strUserId=(String)session.getAttribute("user");
	    		   /*String strSelCountry = request.getParameter("selected_country");
	    	        session.removeAttribute("countriesList");
	    	        session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
	    	        session.removeAttribute("statesList");
	    	        if(strSelCountry != null && strSelCountry.equalsIgnoreCase("India"))
	    	            session.setAttribute("statesList", Arrays.asList(ContryStateList.indstates));
	    	        else
	    	        if(strSelCountry != null && strSelCountry.equalsIgnoreCase("United States of America"))
	    	            session.setAttribute("statesList", Arrays.asList(ContryStateList.usstates));
	    	        else
	    	            session.setAttribute("statesList", null);*/
	    		   
	    	        locationList=ConfigBD.getLocationDetailsBD("0","null", strUserId);
	    	        if((locationList!=null)&&(locationList.size()>0)) {
	    	        	session.setAttribute("LocationList",locationList);
	    	        	System.out.println("===location objects found");
	    	        }
	    	   }
	    	   else { // else block starts
	    		    return actionMapping.findForward("sessionExpirePage");
	    	   }
		     } // try block closes
	      catch(Exception exception) {  // catch block starts
	    	  
	    	   logger.info("------Excepion in getLocations action-------");
	    	   logger.error(exception);
	    	   return actionMapping.findForward("errorPage");
	      } // catch block ends
	      
	   return actionMapping.getInputForward();
   }
     // End of getLocations action
   
   
   /*public ActionForward insertCountryDetails(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
	         
	           HttpSession session=null;
	           String strResult=null;
	           String strUserId=null;
	           String strCountryId=null;
	    try {
	    	   session=request.getSession(false);
	           logger=Logger.getLogger("Admin");
	           if(session!=null) {
	        	    strUserId=(String)session.getAttribute("user");
	        	    configForm=(ConfigForm)actionForm;
	        	    logger.info("====Country action started");
	        	    //strResult=ConfigBD.insertCountryDetailsBD(strCountryId, strCountry, strCountryStatus);
	        	    System.out.println("===strResult======="+strResult);
	        	    
	        	    if(strResult!=null) {
	        	    	request.setAttribute("successMsg","Test Action Performed");
	        	    }
	        	    else {
	        	    	request.setAttribute("successMsg","Test Action Failed");
	        	    }
	            }
	           else { // else block starts
	    		    return actionMapping.findForward("sessionExpirePage");
	    	   }
	    } catch(Exception exception) {
	    	
	    	logger.info("========Exception in insertCountryDetails action============");
	    	logger.error(exception);
	    	exception.printStackTrace();
	    }
	    return actionMapping.getInputForward();
   }*/
   
   
    public ActionForward setServerStatus(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
    	        
    	        HttpSession session=null;
    	        String strServerType=null;
    	        String strServerId=null;
    	        String strResult=null;
    	        ServletOutputStream outputStream=null;
    	 try {
    		     session=request.getSession(false);
    		     logger=Logger.getLogger("Admin");
    		     if(session!=null) {
    		    	   System.out.println("========jaax action called============");  
    		    	  if(request.getParameter("type")!=null) {
    		    		  strServerType=request.getParameter("type");
    		    	  }
    		    	  if(request.getParameter("serverId")!=null) {
    		    		  strServerId=request.getParameter("serverId");
    		    	  }
    		    	  System.out.println("=====server type========="+strServerType);
    		    	  System.out.println("=====server Id========="+strServerId);
    		    	  
    		    	  if((strServerId!=null)&&(strServerType!=null)) {
    		    		  
    		    		    strResult=ConfigBD.setServerStatusBD(strServerType, strServerId);
    		    		    System.out.println("=========status changed successfully======");
    		    		    request.setAttribute("successMsg",strResult);
    		       		    response.setContentType("text/text");
    		    		    outputStream=response.getOutputStream();
    		    		    response.getOutputStream().println(strResult);
    		    	  }
    		    	  else
    		    		  request.setAttribute("msg","Internal Problem");
    		     }
    	 } catch(Exception exception) {
    		 
    		 logger.info("---------Exception in setServerStatus action---------");
    		 logger.error(exception);
    		 exception.printStackTrace();
    	 }
               return null;	
     }
    
    
    public ActionForward getTabTreeAction(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
    	        HttpSession session=null;
    	        HashMap tabList=null;
    	 try {
    		      session=request.getSession(false);
    		      
    		      if(session!=null) {
    		    	  
    		    	   logger=Logger.getLogger("Admin");
    		    	   
    		    	   tabList=ConfigBD.getTabTreeBD();
    		    	   
    		    	   if(tabList!=null) {
    		    		   session.setAttribute("tabList",(ArrayList)tabList.get("tabList"));
    		    		   session.setAttribute("roleTypes",(ArrayList)tabList.get("roleTypes"));
    		    		   System.out.println("=========tablist set into session"+tabList);
    		    		}
    		    		   
    		      }
    		      else
    		      {
    		    	  return actionMapping.findForward("sessionExpirePage");
    		      }
    	 }
    	  catch(Exception exception) {
    		  logger.info("======= getTabTreeAction=============");
    		  logger.error(exception);
    		  exception.printStackTrace();
    	  }
    	    return actionMapping.getInputForward();
    }

    public ActionForward configureTabList(ActionMapping actionMapping,ActionForm actionFrom,HttpServletRequest request,HttpServletResponse response) {
               Logger logger=null;
               Enumeration paramNames=null;
               String[] paramValues=null;
               String strTemp=null;
               String strUserId=null;
               HttpSession session=null;
               int result;
               String strRoleId=null;
               String strUserType=null;
               String strRoleName=null;
    	 try { 
    		   logger=Logger.getLogger("Admin");
    		   session=request.getSession(false);
    		   if(session!=null) {
    			   strUserId=(String)session.getAttribute("user");
    			   result=ConfigBD.storeRoleNavigSettingsBD(request, strUserId);
    			   
    			   strRoleId=request.getParameter("roleSelected");
    			   System.out.println("strRoleId========="+strRoleId);
    			   ArrayList list=ConfigBD.getNavigationSetBD(strRoleId, strUserId);
    			   session.setAttribute("tabList",list);
    		   }
    		   else {
    			   return actionMapping.findForward("sessionExpirePage"); 
    		   }
    			 System.out.println("====result from action========="+result);  
    		   /*paramNames=request.getParameterNames();
    		   
    		   while(paramNames.hasMoreElements()) {
    			   strTemp=(String)paramNames.nextElement();
    			   System.out.println("====strTemp======"+strTemp);
    			   if(strTemp.equals("roleSelected")||(strTemp.equals("param"))) {
    				   System.out.println("continued--------");
    				   continue;
    			   }	
    			   paramValues=request.getParameterValues(strTemp);
    			   if(paramValues!=null) {
    				    for(int count=0;count<paramValues.length;count++) {
    					   System.out.println("====value======="+paramValues[count]);
    				   }
    			   }
    		   }*/
    		        
    	 }
    	  catch(Exception exception) {
    		  logger.info("Exception in configureTabList action============");
    		  logger.error(exception);
    		  exception.printStackTrace();
    	  }
    	  //strUserType=LoginBD.
    	  /*if(((String)session.getAttribute("Role")).equalsIgnoreCase("user"))
    		  strUserType="Internal";
    	  else 
    		  strUserType=(String)session.getAttribute("Role");*/
    	     ArrayList tempList=(ArrayList)session.getAttribute("roleTypes");
    	     HashMap map=null;
    	      for(int cnt=0;cnt<tempList.size();cnt++) {
    	    	  map=(HashMap)tempList.get(cnt);
    	    	  if(((String)map.get("roleId")).equalsIgnoreCase(strRoleId)) {
    	    		  strRoleName=(String)map.get("roleName");
    	    		  System.out.println((String)map.get("roleName"));
    	    	  }	  
    	      }
    	    //strRoleName=ConfigBD.getRoleNameBD(strRoleId);	  
           request.setAttribute("configMsg","The Settings for <i>Role : "+strRoleName+"</i> updated Successfully");

    	  return actionMapping.findForward("privilegeHome");
    }
    
    public ActionForward insertRoleDetails(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
                 Logger logger=null;
                 String strRoleId=null;
                 String strRoleName=null;
                 String strDescription=null;
                 String strFlag=null;
                 String executeResult=null;
                 HttpSession session=null;
                 ConfigForm configForm=null;
                 ArrayList list=null;
    	  try {
    		    session=request.getSession(false);
    		    if(session!=null) {
    		       logger=Logger.getLogger("Admin");
    		       configForm=(ConfigForm)actionForm;
    		       
    		       if(configForm.getRoleName()!=null) {
    		    	   strRoleName=configForm.getRoleName();
    		       }
    		       
    		       if(configForm.getPositionStatus()!=null)
    		    	   strFlag=configForm.getPositionStatus();
    		       
    		       if(configForm.getDescription()!=null) {
    		    	   strDescription=configForm.getDescription();
    		       }
    		       else 
    		    	   strDescription="";
    		       
    		       if(session.getAttribute("RoleId")!=null) {
    		    	   strRoleId=(String)session.getAttribute("RoleId");
    		    	   System.out.println("----raja--------");
    		    	   session.removeAttribute("RoleId");
    		       }
    		       else 
    		    	   strRoleId="0";
    		       System.out.println("======Role Name========"+strRoleName);
    		       System.out.println("======Role Description========"+strDescription);
    		       System.out.println("======Role Id========"+strRoleId);
    		     
    		       executeResult=ConfigBD.insertRoleTypeBD(strRoleId, strRoleName, strDescription,strFlag);
    		       
    		       if(executeResult!=null)
    		    	   session.setAttribute("msg",executeResult);
    		       else
    		    	   session.setAttribute("msg","Problem Occured");
    		       
    		    }
    		    else {
    		    	return actionMapping.findForward("sessionExpirePage");
    		    }
    		    
    	  }
    	   catch(Exception exception) {
    		    logger.info("====Exception in insertRoleDetails action==========");
    		    logger.error(exception);
    		    exception.printStackTrace();
    	   }
    	    return actionMapping.findForward("roleHome");
    }
    
    public ActionForward roleHome(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
                  Logger logger=null;
                  HttpSession session=null;
                  ArrayList list=null;
    	  try {
    		     logger=Logger.getLogger("Admin");
    		     session=request.getSession(false);
    		     if(session!=null) {
    		    	 list=ConfigBD.getRoleListBD("0");
    		    	 System.out.println("=========role list==========="+list);
    		    	 if((list!=null)&&(list.size()>0)) {
    		    		session.setAttribute("RoleList",list);
    		    	}
    		     }
    		     else {
    		    	 return actionMapping.findForward("sessionExpirePage");
    		     }
    	    }
    	      catch(Exception exception) {
    		   logger.info("-----Exception in roleHome action------");
    		   logger.error(exception);
    		   exception.printStackTrace();
    	   }
    	       return actionMapping.getInputForward();
    }
   
    public ActionForward modifyRole(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
 	   
        HttpSession session=null;
        ArrayList positionList=null;
        String strRoleId=null;
        String strResult=null;
        ArrayList RoleList=null;
        HashMap tempRecord;
                     
    try {
   	      logger=Logger.getLogger("Admin");
   	      session=request.getSession(false);
   	   	   
   	      if(session!=null) { // if block starts
   		   
   		  System.out.println("======action called============");
   		  strUserId=(String)session.getAttribute("user");
   		   
   		  // Get the form bean object
   		  configForm=(ConfigForm)actionForm;
   		   
   		   if(request.getParameter("roleNumber")!=null) {
   			   System.out.println("====role number as parameter========="+request.getParameter("roleNumber"));
   			   strRoleId=request.getParameter("roleNumber");
   		   }   
  	    	if(strRoleId!=null) {
   		       RoleList=ConfigBD.getRoleListBD(strRoleId);
   		       System.out.println("my test---^&^*^*^*"+RoleList);
   		   }  
   		    if((RoleList!=null)&&(RoleList.size()>0)) {
   		    	
   		    	tempRecord=(HashMap)RoleList.get(0);
   		    	configForm.setRoleName((String)tempRecord.get("roleName"));
   		    	if(((String)tempRecord.get("roleStatus")).equalsIgnoreCase("Active"))
   		    	 configForm.setPositionStatus("0");
   		    	else
   		    	 configForm.setPositionStatus("1");	
   		    	
   		    	// set the role id in session
   		    	System.out.println("before setting into session");
   		    	session.setAttribute("RoleId",strRoleId);
   		    }
   		    // return to the display page
   		    return actionMapping.getInputForward();
   	    } // if block ends
   	   else { // else block starts
   		    return actionMapping.findForward("sessionExpirePage");
   	   } // end of else block
   	   
	     } // try block closes
     catch(Exception exception) {  // catch block starts
   	  
   	   logger.info("------Exception in getPositionList action-------");
   	   logger.error(exception);
   	   return actionMapping.findForward("errorPage");
     } // catch block ends
 }

    /*
     * The deletePosition() action is for deleting a Position role  from VIMS application.
     * 
     * @param actionMapping 
     * @param actionForm
     * @param request
     * @param response
     * 
     */
    public ActionForward deleteRole(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
 	   
 	         HttpSession session=null;
 	         ArrayList RoleList=null;
 	         String strRoleId=null;
 	         String strResult=null;
 	                      
 	     try {
 	    	   logger=Logger.getLogger("Admin");
 	    	   session=request.getSession(false);
 	    	   if(session!=null) { // if block starts
 	    		   strUserId=(String)session.getAttribute("user");
 	    		   if(request.getParameter("roleNumber")!=null) 
 	   	    	      strRoleId=request.getParameter("roleNumber");
 	   	    	     		   
 	    		   if(strRoleId!=null)
 	    			strResult=ConfigBD.deleteRoleTypeBD(strRoleId);
 	    		   
 	    		  if(strResult!=null) {
 	    			  RoleList=ConfigBD.getRoleListBD("0");
 	    		   if((RoleList!=null)&&(RoleList.size()>0)) {  // If block starts
 	    			  session.setAttribute("RoleList",RoleList);
 	    		   } // If block ends
 	    		  }     		   
 	    		   // return to the display page
 	    		  request.setAttribute("msg",strResult);
 	    		  return actionMapping.findForward("roleHome");
 	    	    } // if block ends
 	    	   else { // else block starts
 	    		    return actionMapping.findForward("sessionExpirePage");
 	    	   } // end of else block
 	    	   
 		     } // try block closes
 	      catch(Exception exception) {  // catch block starts
 	    	  
 	    	   logger.info("------Excepion in deleteRole action-------");
 	    	   logger.error(exception);
 	    	   return actionMapping.findForward("errorPage");
 	      } // catch block ends
 	      
 	 //  return actionMapping.getInputForward();
    }
      // End of deleteRole action
    
    public ActionForward tabHomeSettings(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
                Logger logger=null;
                ArrayList roleTypes=null;
                HttpSession session=null;
    	  try {
    		    logger=Logger.getLogger("Admin");
    		    session=request.getSession(false);
    		    
    		    if(session!=null) {
    		    	if(session.getAttribute("configMsg")!=null)
        		    	session.removeAttribute("configMsg");
    		    	
    		    	if(request.getAttribute("configMsg")!=null) {
    		    		String strTemp=(String)request.getAttribute("configMsg");
    		    		request.setAttribute("configMsg",strTemp);
    		    	}
    		    		
    		    	roleTypes=ConfigBD.getRoleTypesBD();
    		    	if(session.getAttribute("tabList")!=null) {
    		    		session.removeAttribute("tabList");
    		    	}
    		    	if(session.getAttribute("selectedRoleId")!=null){
    		    		session.removeAttribute("selectedRoleId");
    		    	}
    		    	if(roleTypes!=null) {
    		    		session.setAttribute("roleTypes",roleTypes);
    		    	}
    		    	 System.out.println("====role types==="+roleTypes);
    		    }
    		     else {
    		    	 return actionMapping.findForward("sessionExpirePage");
    		     }
    	  }
    	   catch(Exception exception) {
    		   logger.info("-----Exception in tabHomeSettings action-------");
    		   logger.error(exception);
    		   exception.printStackTrace();
    		   return actionMapping.findForward("errorPage");
    	   }
    	    return actionMapping.getInputForward();
    }
    
    public ActionForward getNavigSettings(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
    	      HttpSession session=null;
    	      VIMSTabForm form=null;
    	      String strRoleId=null;
    	      String strUserId=null;
    	      ArrayList list=null;
    	try {
    		    session=request.getSession(false);
    		    if(session!=null) {
    		    if(session.getAttribute("configMsg")!=null)
    		    	session.removeAttribute("configMsg");
    		    
    		    form=(VIMSTabForm)actionForm;
    		    strUserId=(String)session.getAttribute("user");
    		    strRoleId=form.getUserType();
    		    System.out.println("------User Role Type---------"+strRoleId);
    		    System.out.println("------Logged in user---------"+strUserId);
    		    form.setUserType(strRoleId);
    		    list=ConfigBD.getNavigationSetBD(strRoleId, strUserId);
    		    
    		    if(list!=null) {
    		    	session.setAttribute("tabList",list);
    		    	System.out.println("----list is not null-----"+list);
    		    }
    		  }
    		  else {
    		     return actionMapping.findForward("sessionExpirePage");   	
    		  }
    	 }
    	  catch(Exception exception) {
    		  
    		  logger.info("-----Exception in tabHomeSettings action-------");
   		   	  logger.error(exception);
   		   	  exception.printStackTrace();
   		   	  return actionMapping.findForward("errorPage"); 
    	  }
    	    session.setAttribute("selectedRoleId",strRoleId);
    	    return actionMapping.getInputForward();
    }
    
     public ActionForward addLocationDetails(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
                Logger logger=null;
                HttpSession session=null;
                String strUserId=null;
                String strKeywordType=null;
                String strKeywordValue=null;
                String strLocationStatus=null;
                String strLocationId=null;
                
                ArrayList LocationList=new ArrayList();
                
                try {
                	logger=Logger.getLogger("Admin");
     	    	   session=request.getSession(false);
     	    	   if(session!=null) {
     	    		   System.out.println("=====Action of location called");
     	    		   // Get the user id from session
     	    		   strUserId=(String)session.getAttribute("user");
     	    		   // Get the Form
     	    		    configForm=(ConfigForm)actionForm;
     	    		   // Get the keyword type of whose value to be inserted
     	    		    strKeywordType=configForm.getKeywordType();
     	    		    strKeywordValue=configForm.getKeywordValue();
     	    		    strLocationStatus=configForm.getPositionStatus();
     	    		    
     	    		    
     	    		    if((strKeywordType!=null)&&(strKeywordValue!=null)) {
     	    		    	
     	    		    	System.out.println("======strKeywordType==========="+strKeywordType);
     	    		    	System.out.println("======strKeywordValue==========="+strKeywordValue);
     	    		    	
     	    		    	if(strKeywordType.equals("Work Location")) {
     	    		    		
     	    		    		if(session.getAttribute("LocationId")!=null) {
     	    		    			strLocationId=(String)session.getAttribute("LocationId");
     	    		    			// remove from the session
     	    		    			session.removeAttribute("LocationId");
     	    		    		}	
     	    		    		else {
     	    		    			strLocationId="0";
     	    		    		}
     	    		    		System.out.println("====strLocationId-=========="+strLocationId);
     	    		    		String strResult=ConfigBD.insertLocationDetailsBD(strLocationId, strKeywordValue, strLocationStatus, strUserId);
     	    		    		System.out.println("======result========="+strResult);
     	    		    		request.setAttribute("successMsg",strResult);
     	    		    		configForm.setKeywordValue("");
     	    		    	}
     	    		    }
     	    		   LocationList=ConfigBD.getLocationDetailsBD(strLocationId,"null", strUserId);
     	    		   session.setAttribute("LocationList", LocationList);
                            return actionMapping.findForward("WorkLocationHome"); 	    		     
     	    	   }
     	    	   else {
     	    		    return actionMapping.findForward("sessionExpirePage");
     	    	   }
                }
                 catch(Exception exception) {
                	 logger.info("------Excepion in addLocationDetails action-------");
      	    	   	 logger.error(exception);
      	    	   	 exception.printStackTrace();
      	    	   	 return actionMapping.findForward("errorPage");
                 }
     }

     public ActionForward modifyLocation(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
    	 
    	 HttpSession session=null;
         ArrayList LocationList=null;
         String strLocationId=null;
         String strResult=null;
         //ArrayList LocatioList=null;
         HashMap tempRecord;
                      
     try {
    	   logger=Logger.getLogger("Admin");
    	   session=request.getSession(false);
    	   	   
    	   if(session!=null) { // if block starts
    		   
    		   System.out.println("====== location action called============");
    		   strUserId=(String)session.getAttribute("user");
    		   
    		   // Get the form bean object
    		   configForm=(ConfigForm)actionForm;
    		   
    		   if(request.getParameter("locationId")!=null) {
    			   System.out.println("====location number as parameter========="+request.getParameter("locationId"));
    			   strLocationId=request.getParameter("locationId");
    		   }   
   	    	   else if(request.getAttribute("locationId")!=null) {
   	    		   System.out.println("====location number as attribute=========");
   	    		   strLocationId=(String)request.getAttribute("locationId");
   	    	   }
    		   if(strLocationId!=null) {
    		       LocationList=ConfigBD.getLocationDetailsBD(strLocationId,"null", strUserId);
    		       System.out.println(LocationList);
    		   }  
    		    if((LocationList!=null)&&(LocationList.size()>0)) {
    		    	
    		    	tempRecord=(HashMap)LocationList.get(0);
    		    	configForm.setKeywordValue((String)tempRecord.get("locationName"));
    		    	if(((String)tempRecord.get("locationStatus")).equals("Active"))
    		    		configForm.setPositionStatus("0");
    		    	else
    		    		configForm.setPositionStatus("1");
    		    	// set the position id in session
    		    	session.setAttribute("LocationId",(String)tempRecord.get("locationId"));
    		    }
    		    // return to the display page
    		    return actionMapping.getInputForward();
    	    } // if block ends
    	   else { // else block starts
    		    return actionMapping.findForward("sessionExpirePage");
    	   } // end of else block
    	   
	     } // try block closes
      catch(Exception exception) {  // catch block starts
    	  
    	   logger.info("------Exception in modifyLocation action-------");
    	   logger.error(exception);
    	   exception.printStackTrace();
    	   return actionMapping.findForward("errorPage");
      } // catch block ends
     }
     
   public ActionForward getCountryAction(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
             Logger logger=null;
            // ConfigForm configForm=null;
             HttpSession session=null;
             ArrayList countryList=null;
             
         try {
        	    logger=Logger.getLogger("Admin");
        	    session=request.getSession(false);
        	    if(session!=null) {
                     countryList=ConfigBD.getCountryListBD("0","0");
                     if((countryList!=null)&&(countryList.size()>0)) {
                    	 session.setAttribute("countryList",countryList);
                     }
        	    }
        	    else {
        	    	return actionMapping.findForward("sessionExpirePage");
        	    }
        	    	
         }
          catch(Exception exception) {
        	  logger.info("-----Exception in getCountryAction-----------");
        	  logger.error(exception);
        	  exception.printStackTrace();
          }
            return actionMapping.getInputForward();
   }
   
   
   public ActionForward addCountry(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
       Logger logger=null;
       HttpSession session=null;
       String strUserId=null;
       String strKeywordType=null;
       String strKeywordValue=null;
       String strLocationStatus=null;
       String strCountryId=null;
       
       
       try {
       	logger=Logger.getLogger("Admin");
    	   session=request.getSession(false);
    	   if(session!=null) {
    		   System.out.println("=====Action of Country insertion called");
    		   // Get the user id from session
    		   strUserId=(String)session.getAttribute("user");
    		   // Get the Form
    		    configForm=(ConfigForm)actionForm;
    		   // Get the keyword type of whose value to be inserted
    		    strKeywordType=configForm.getKeywordType();
    		    strKeywordValue=configForm.getKeywordValue();
    		    strLocationStatus=configForm.getPositionStatus();
    		    
    		    
    		    if((strKeywordType!=null)&&(strKeywordValue!=null)) {
    		    	
    		    	System.out.println("======strKeywordType==========="+strKeywordType);
    		    	System.out.println("======strKeywordValue==========="+strKeywordValue);
    		    	
    		    	if(strKeywordType.equals("Country")) {
    		    		
    		    		if(session.getAttribute("CountryId")!=null) {
    		    			strCountryId=(String)session.getAttribute("CountryId");
    		    			System.out.println("===========strCountryId==========="+strCountryId);
    		    			// remove from the session
    		    			session.removeAttribute("CountryId");
    		    		}	
    		    		else {
    		    			strCountryId="0";
    		    		}
    		    		System.out.println("====strLocationId-=========="+strCountryId);
    		    		String strResult=ConfigBD.insertCountryDetailsBD(strCountryId, strKeywordValue,strLocationStatus);
    		    		System.out.println("======result========="+strResult);
    		    		request.setAttribute("successMsg",strResult);
    		    		configForm.setKeywordValue("");
    		    		configForm.setPositionStatus("");
    		    	}
    		    }
    		      return getCountryAction(actionMapping,actionForm,request,response);
    		      
                   //return actionMapping.findForward("WorkLocationHome"); 	    		     
    	   }
    	   else {
    		    return actionMapping.findForward("sessionExpirePage");
    	   }
       }
        catch(Exception exception) {
       	 logger.info("------Excepion in addCountryDetails action-------");
	    	   	 logger.error(exception);
	    	   	 exception.printStackTrace();
	    	   	 return actionMapping.findForward("errorPage");
        }
}
   
   public ActionForward modifyCountryDetails(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
  	 
  	   HttpSession session=null;
       ArrayList CountryList=null;
       String strCountryId=null;
       String strResult=null;
       //ArrayList LocatioList=null;
       HashMap tempRecord;
       String strStatus=null;
       
                    
   try {
  	   logger=Logger.getLogger("Admin");
  	   session=request.getSession(false);
  	   	   
  	   if(session!=null) { // if block starts
  		   
  		   System.out.println("====== Country modify action called============");
  		   strUserId=(String)session.getAttribute("user");
  		   
  		   // Get the form bean object
  		   configForm=(ConfigForm)actionForm;
  		   
  		   if(request.getParameter("CountryId")!=null) {
  			   System.out.println("====Country number as parameter========="+request.getParameter("CountryId"));
  			   strCountryId=request.getParameter("CountryId");
  		   }   
 	    	   else if(request.getAttribute("CountryId")!=null) {
 	    		   System.out.println("====Country number as attribute=========");
 	    		   strCountryId=(String)request.getAttribute("CountryId");
 	    	   }
  		   if(strCountryId!=null) {
  		       CountryList=ConfigBD.getCountryListBD(strCountryId,"0");
  		       System.out.println(CountryList);
  		   }  
  		    if((CountryList!=null)&&(CountryList.size()>0)) {
  		    	
  		    	tempRecord=(HashMap)CountryList.get(0);
  		    	configForm.setKeywordValue((String)tempRecord.get("countryName"));
  		    	
  		    	strStatus=(String)tempRecord.get("status");
  		    	if(strStatus.equalsIgnoreCase("Active"))
  		    	{
  		    	configForm.setPositionStatus("0");
  		    	}
  		    	else
  		    	{
  		    		configForm.setPositionStatus("1");	
  		    	}
  		    	/*if(((String)tempRecord.get("locationStatus")).equals("Active"))
  		    		configForm.setPositionStatus("0");
  		    	else
  		    		configForm.setPositionStatus("1");*/
  		    	// set the country id in session
  		    	session.setAttribute("CountryId",(String)tempRecord.get("countryId"));
  		    }
  		    // return to the display page
  		    return actionMapping.getInputForward();
  	    } // if block ends
  	   else { // else block starts
  		    return actionMapping.findForward("sessionExpirePage");
  	   } // end of else block
  	   
	     } // try block closes
    catch(Exception exception) {  // catch block starts
  	  
  	   logger.info("------Exception in modifyCountry action-------");
  	   logger.error(exception);
  	   exception.printStackTrace();
  	   return actionMapping.findForward("errorPage");
    } // catch block ends
   }

   public ActionForward getStateAction(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
   {
	   HttpSession session=null;
	   session=request.getSession(false);
	   
	   ArrayList CountryList=new ArrayList();
	   CountryList=ConfigBD.getCountryList();
	   
	   session.removeAttribute("stateList");
	   if(session!=null)
	   {
		   session.setAttribute("countryList", CountryList);
	   }
	   return actionMapping.findForward("StatePage");   
   }
 
   public ActionForward getStates(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
   {
	   HttpSession session=null;
	   session=request.getSession(false);
	   String strSateID="0";
	  // Get the form bean object
	   configForm=(ConfigForm)actionForm;
	   
	   ArrayList StateList=new ArrayList();
	   
	   String strCountryID=null;
	   strCountryID=configForm.getCountryValue();
	   
	   System.out.println("==========strCountryID============"+strCountryID);
	   
	   StateList=ConfigBD.getStateList(strCountryID,strSateID);
	   System.out.println("=========StateList========="+StateList);
	  
	   session.setAttribute("stateList", StateList); 
	   session.setAttribute("CountryID", strCountryID);
	   
	   configForm.setKeywordValue("");
	   configForm.setPositionStatus("");
	   
	   return actionMapping.findForward("StatePage");
	}
   public ActionForward insertStateDetails(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
   {
	   HttpSession session;
	   session=request.getSession(false);
	   
	   String strResponse;
	   String strCountryID=null;
	   String strStateName=null;
	   String strStateStatus=null;
	   String strStateID=null;
	   String strKeywordType=null;
	   String strResult=null;
	   
	   // Get the form bean object
	   configForm=(ConfigForm)actionForm;
	   strCountryID=configForm.getCountryValue();
	   System.out.println("========Country ID=========="+strCountryID);
	   strStateName=configForm.getKeywordValue();
	   strStateStatus=configForm.getPositionStatus();
	   strKeywordType=configForm.getKeywordType();
	   
	   if((strKeywordType!=null)&&(strStateName!=null))
	   {
	    	
	    	System.out.println("======strKeywordType==========="+strKeywordType);
	    	System.out.println("======strKeywordValue==========="+strStateName);
	    	
	    	if(strKeywordType.equals("State"))
	    	{
	    		
	    		if(session.getAttribute("stateID")!=null)
	    		{
	    			strStateID=(String)session.getAttribute("stateID");
	    			System.out.println("===========stateID==========="+strStateID);
	    			// remove from the session
	    			session.removeAttribute("stateID");
	    		}	
	    		else
	    		{
	    			strStateID="0";
	    		}
	    	}
	   }
	   strResult=ConfigBD.SaveStateDetails(strCountryID,strStateID,strStateName,strStateStatus);
	   
	   request.setAttribute("successMsg", strResult);
	   
	   return getStates(actionMapping,actionForm,request,response);
	//return actionMapping.findForward("StatePage");
		   
   }
 
   public ActionForward modifyStateDetails(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
   {
	   HttpSession session;
	   session=request.getSession(false);
	   
	   ArrayList StateDetails=new ArrayList();
	   
	   String strStateID=null;
	   String strCountryID=null;
	   String strStatus=null;
	   
	   HashMap tempRecord;
	   
	   // Get the form bean object
	   configForm=(ConfigForm)actionForm;
	   
	   strCountryID=(String) session.getAttribute("CountryID");
	   
	   System.out.println("========Country ID==========="+strCountryID);
	   
	   if(request.getParameter("stateID")!=null)
	   {
		System.out.println("====Country number as parameter========="+request.getParameter("CountryId"));
		strStateID=request.getParameter("stateID");
		System.out.println("==========strStateID==========="+strStateID);
	   }   
	   else if(request.getAttribute("stateID")!=null)
	   {
	    System.out.println("====Country number as attribute=========");
	    strStateID=(String)request.getAttribute("stateID");
	    System.out.println("==========State ID============="+strStateID);
	   }
		   if(strStateID!=null)
		   {
			   StateDetails=ConfigBD.getStateList(strCountryID,strStateID);
		       System.out.println(StateDetails);
		   }  
		    if((StateDetails!=null)&&(StateDetails.size()>0))
		    {
		    	tempRecord=(HashMap)StateDetails.get(0);
		    	configForm.setKeywordValue((String)tempRecord.get("stateName"));
		    	
		    	strStatus=(String)tempRecord.get("stateStatus");
		    	if(strStatus.equalsIgnoreCase("Active"))
		    	{
		    	configForm.setPositionStatus("0");
		    	}
		    	else
		    	{
		    		configForm.setPositionStatus("1");	
		    	}
		    	configForm.setCountryValue(strCountryID);
		    	
		    	session.setAttribute("stateID",(String)tempRecord.get("stateID"));
		    	System.out.println("=========Placing the Value in Session ============"+session.getAttribute("stateID"));
		    }
	   
	   return actionMapping.findForward("StatePage");
   }
   
   public ActionForward getFooterOptions(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
           Logger logger=null;
           HttpSession session=null;
           ArrayList footerOptions=null;
           
           try {
        	     logger=Logger.getLogger("Admin");
        	     session=request.getSession(false);
        	     if(session!=null) {
                    footerOptions=ConfigBD.getFooterOptionsBD("0");   	    	 
                    if(footerOptions!=null) {
                    	session.setAttribute("footerOptions",footerOptions);
                    }
        	     }
        	     else {
        	    	 return actionMapping.findForward("sessionExpirePage");
        	     }
           }
            catch(Exception exception) {
            	logger.info("-----Exception in  getFooterOptions------");
            	logger.error(exception);
            	exception.printStackTrace();
            	return actionMapping.findForward("errorPage");
            }
              return actionMapping.getInputForward();
   }
   
    public ActionForward storeContent(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
               Logger logger=null;
               HttpSession session=null;
               String strOptionId=null;
               String strContent=null;
               
          try { 
        	     logger=Logger.getLogger("Admin");
                 session=request.getSession(false);
        	     if(session!=null) {
        	    	System.out.println("===param 1--------"+request.getParameter("type"));
        	    	System.out.println("===param 2--------"+request.getParameter("value"));
        	    	strOptionId=request.getParameter("type");
        	    	strContent=request.getParameter("value");
        	    	
        	    	String strResult=ConfigBD.storeContentBD(strOptionId, strContent);
        	    	System.out.println(strResult);
        	     }
        	     else {
        	    	 return actionMapping.findForward("sessionExpirePage");
        	     }
          }
           catch(Exception exception) {
        	   logger.info("----Exception in storeContent action");
        	   logger.error(exception);
        	   exception.printStackTrace();
        	   return actionMapping.findForward("errorPage");
           }
             return null;
      }
    
      public ActionForward getOptionContent(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
               Logger logger=null;
               HttpSession session=null;
               ArrayList list=null;
               HashMap record=null;
               String strOptionId=null;
               try {
            	      session=request.getSession(false);
            	      if(session!=null) {
            	    	  logger=Logger.getLogger("Admin");
            	    	  strOptionId=request.getParameter("optionId");
            	    	  list=ConfigBD.getFooterOptionsBD(strOptionId);
            	    	  
            	    	  if((list!=null)&&(list.size()>0)) {
            	    		  record=(HashMap)list.get(0);
            	    		  System.out.println("====got record object");
            	    	  }
            	      }
            	      else {
            	    	  return actionMapping.findForward("sessionExpirePage");
            	      }
            	       response.setContentType("text/text");
            	       ServletOutputStream outputStream=response.getOutputStream();
            	       System.out.println("===data-----"+(String)record.get("content"));
            	       outputStream.write(((String)record.get("content")).getBytes());
            	       outputStream.close();
               }
                catch(Exception exception) {
                      logger.info("----Exception in getOptionContent action-----");
                      logger.error(exception);
                      exception.printStackTrace();
                }
                 return null;
      }
     @Override
		protected Map getKeyMethodMap() {
		      
			 HashMap map;
			 map=new HashMap();
			 
			 map.put("VIMS.ldapConfig","ldapConfig");
			 map.put("VIMS.mailConfig","mailConfig");
			 map.put("VIMS.customHomePage","getHomeEnv");
			 
			 map.put("VIMS.approve","persistHomePageSettings");
			 
			 map.put("VIMS.ldapEnv","getLdapEnv");
			 map.put("VIMS.getLdapDetails","getLdapDetails");
			 map.put("VIMS.deleteLdapDetails","deleteLdapDetails");
			 
			 map.put("VIMS.mailEnv","getMailEnv");
		     map.put("VIMS.getMailDetails","getMailDetails");
			 map.put("VIMS.deleteMailDetails","deleteMailDetails");
			 
			 // Field Configuration Environment Action
			 map.put("VIMS.getFieldConfigurationSettings","getFieldConfigurationSettings");
			 map.put("VIMS.addSpecificKeywordOptionValue","addSpecificKeywordOptionValue");
			 map.put("VIMS.getPositionList","getPositionList");
			 
			 map.put("VIMS.deletePosition","deletePosition");
			 map.put("VIMS.getLocations","getLocations");
			 
			 map.put("VIMS.modifyPosition","modifyPosition");
			 
			// map.put("VIMS.insertCountryDetails","insertCountryDetails");
			 map.put("VIMS.setServerStatus","setServerStatus");
			 
			 map.put("VIMS.getTabTreeAction","getTabTreeAction");
			// map.put("VIMS.locationBasePage","locationBasePage");
			 map.put("VIMS.configureTabList","configureTabList");
			 
			 map.put("VIMS.roleHome","roleHome");
			 map.put("VIMS.insertRoleDetails","insertRoleDetails");
			 map.put("VIMS.modifyRole","modifyRole");
			 map.put("VIMS.deleteRoleType","deleteRole");

		   // Tab Customization Mappings
			 map.put("VIMS.tabHomeSettings","tabHomeSettings");
			 map.put("VIMS.getNavigSettings","getNavigSettings");
			 
			 
			 map.put("VIMS.addLocationDetails","addLocationDetails");
			 map.put("VIMS.modifyLocation","modifyLocation");
			 
			 map.put("VIMS.getCountryAction","getCountryAction");
			 map.put("VIMS.insertCountryDetails","addCountry");
			 map.put("VIMS.modifyCountry","modifyCountryDetails");
			 
			 map.put("VIMS.getStateAction","getStateAction");
			 map.put("VIMS.getStates","getStates");
			 map.put("VIMS.insertStateDetails","insertStateDetails");
			 map.put("VIMS.insertStateDetails","insertStateDetails");
			 map.put("VIMS.modifyStateDetails","modifyStateDetails");
			 
			 map.put("VIMS.getFooterOptions","getFooterOptions");
			 map.put("VIMS.storeContent","storeContent");
			 map.put("VIMS.getOptionContent","getOptionContent");
			 return map;
		}


} 