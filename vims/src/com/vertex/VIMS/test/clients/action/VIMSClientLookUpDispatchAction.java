/*  
  FileName	    : VIMSClientLookUpDispatchAction.java
  
                  
  
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


//Package structure followed for this class.
  package com.vertex.VIMS.test.clients.action;

//import statements  
  import java.util.*;

  import javax.servlet.http.*; 
  import org.apache.struts.action.*;
  import org.apache.struts.validator.*;
  import org.apache.struts.actions.DispatchAction;
  import org.apache.struts.actions.LookupDispatchAction;
  import com.vertex.VIMS.test.clients.BD.VIMSClientBD;
  import com.vertex.VIMS.test.clients.form.VIMSCustomerForm;
  import com.vertex.VIMS.test.common.ContryStateList;
  import com.vertex.VIMS.test.employee.BD.VIMSEmployeeBD; 
  import com.vertex.VIMS.test.employee.form.NewEmployeeFormBean;
import org.apache.log4j.*;

//Start of VIMSClientLookUpDispatchAction class
/**
 * @author geeta.m
 *
 */
public class VIMSClientLookUpDispatchAction extends LookupDispatchAction 
 {
	
   //initializing Logger 
	 Logger logger=null;
	
    /********Method------------>addCustomer***********/
	/********This method returns a boolean type calling a method from BD by sending form object as parameter*******/
	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addCustomer(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
	{
      
	  //logger is used to save our exceptions in the logger file 
		logger=Logger.getLogger("Admin"); 
	    String[] strValues=null;
	    String strContextPath=null;
		 
	  //Start of try block	
	 /*if(isTokenValid(request))
	  {
		 resetToken(request);*/
		 try
         {
 	    	//Initialising form bean object
	    	  VIMSCustomerForm customerForm=(VIMSCustomerForm)actionForm;
			   
	       //calling a method in BD by sending form object as parameter
	    	  String strRandomGnrtPassword=RandomGeneration.getUniqueID();
	    	  String strSelState = request.getParameter("selected_state");
	    	  //System.out.println("-------State in Action--------"+strSelState);
	    	  customerForm.setState(strSelState);
	    	  
	    	  strValues=VIMSClientBD.addClientDetailsBD(customerForm,strRandomGnrtPassword);
	    	  HttpSession session;
	  		  session=request.getSession();
	  		  String strcustID=strValues[0];
	  		  session.setAttribute("custID",strcustID);
	  		  String strLoginID=strValues[3];		
	  		  session.setAttribute("LoginID",strLoginID);
	  		  String strUserID=(String) session.getAttribute("user");	
	     
		     //Start of if condition  
		    if(strValues!=null)
		      {
			    //When insertion success this message is displayed 
		    	  request.setAttribute("MSG","Customer added successfully");
	          } 	  	
		    //End of if condition
		     else
	    	  {
		    	//When insertion fails this message is displayed 
		    	  request.setAttribute("MSG","Customer addition failed");
	    	  }
		  }
	   //End of try block
       
	  //Start of catch block	
	    catch(Exception exception)
         {
    	   logger.info("VIMSClientLookUpDispatchAction.addCustomer");
    	   logger.error(exception);
    	   System.out.println("The Exception is:"+exception);
         }
	 
	 //End of catch block
	   return baseCustomer( actionMapping, actionForm, request, response);//actionMapping.findForward("homeClients");
    }
	//End of addCustomer method 
	 
	/********Method------------>displayCustomer***********/	
	
	/********This method is used to populate the values in the page before updating and returns 
	 *       an arrayList calling a method in DB by sending parameter as checkboxID*********/
	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward displayCustomer(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
	{
	  //logger is used to save our exceptions in the logger file 
		logger=Logger.getLogger("Admin"); 
	  
	  //Start of try block	
	   try
	       {
			 //Initialising a bean object
		       VIMSCustomerForm customerForm=(VIMSCustomerForm)actionForm;
			   String strCustID=request.getParameter("custID");
			   //System.out.println("---------Customer ID---------"+strCustID);
		       HttpSession session=null;
		       session=request.getSession(false);
		       session.setAttribute("custID",strCustID);
		      
		       HashMap hashMap=VIMSClientBD.displayClientDetailsBD(strCustID);
			  
		      customerForm.setCustomerID((String)hashMap.get("customerID"));
		      customerForm.setCustomerName((String)hashMap.get("customerName"));
		      customerForm.setFirstName((String)hashMap.get("firstName"));
		      customerForm.setLastName((String)hashMap.get("lastName"));
		      customerForm.setAddress1((String)hashMap.get("address1"));
		      customerForm.setAddress2((String)hashMap.get("address2"));
		      customerForm.setCity((String)hashMap.get("city"));
		      customerForm.setCountry((String)hashMap.get("countryNo"));
		      //System.out.println("-----------Country in modifyCustomer---------"+(String)hashMap.get("countryNo"));
		      customerForm.setState((String)hashMap.get("stateNo"));
		      //System.out.println("-----------State in modifyCustomer---------"+(String)hashMap.get("stateNo"));
		     
		      customerForm.setMobileNO((String)hashMap.get("mobileNO"));
		      customerForm.setPhoneNumber((String)hashMap.get("phoneNumber"));
		      customerForm.setEmailID((String)hashMap.get("emailID"));
		      customerForm.setFaxNo((String)hashMap.get("faxNo"));
		      customerForm.setStatus((String)hashMap.get("status"));
		      customerForm.setCompanyName((String)hashMap.get("companyName"));
		      customerForm.setExtension((String)hashMap.get("extensionNo"));
		      customerForm.setZipCode((String)hashMap.get("zipCode"));
		      
		      customerForm.setContactFirstName((String)hashMap.get("contactFirstName"));
		      //System.out.println("-------contactFirstName--------"+(String)hashMap.get("contactFirstName"));
		      customerForm.setContactMiddleName((String)hashMap.get("contactLastName"));
		      //System.out.println("-------contactLastName--------"+(String)hashMap.get("contactLastName"));
			    
		      customerForm.setContactLastName((String)hashMap.get("contactMiddleName"));
		      //System.out.println("-------contactMiddleName--------"+(String)hashMap.get("contactMiddleName"));
			    
		      customerForm.setContactEmailID((String)hashMap.get("contactEmailID"));
		      //System.out.println("-------contactEmailID--------"+(String)hashMap.get("contactEmailID"));
		      
		      //session = request.getSession(false);
      		  //session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
              
		        //String []stateList=null;
	       	    //String strSelCountry1 = request.getParameter("country");
	            //System.out.println("----------strSelCountry1--------"+strSelCountry1);
	           
	           //Ajax Code without sink with DataBase
	       	    /* if((strSelCountry1!=null)&&(!strSelCountry1.equals(""))) {
	            	
	            	session=request.getSession(false);
	            	stateList=ContryStateList.getStatesList(strSelCountry1);
	            	session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
	                if(((String)hashMap.get("country")).equals("United States of America"))
	                    session.setAttribute("statesList", Arrays.asList(ContryStateList.usstates));
	                if(((String)hashMap.get("country")).equals("India"))
	                    session.setAttribute("statesList", Arrays.asList(ContryStateList.indstates));
	            	
	            }    */
	       	    //Ajax Code in sink with DataBase
	       	    session = request.getSession(false);
	            ArrayList strCountries=(ArrayList)VIMSEmployeeBD.getCountryListBD();
	            session.removeAttribute("countriesList");
	    		session.setAttribute("countriesList", strCountries);
	            
	            if((String)hashMap.get("country")!=null && (!((String)hashMap.get("country")).equals("")))
	            {
	            	 //System.out.println("--------In !=null condition------");
	            	 session=request.getSession(false);
	            	 String strStatesNames=null;
	            	 String strSelCountry=(String)hashMap.get("countryNo");
	            	 //System.out.println("--------strCountry in if-------"+strSelCountry);
	             	 session.removeAttribute("countriesList");
	                 session.setAttribute("countriesList",strCountries);
	                 //System.out.println("==========Countries========="+strCountries);
	                 //System.out.println("==========Before calling States=========");
	            	 ArrayList strStates=(ArrayList)VIMSEmployeeBD.getStateListBD(strSelCountry);
	            	 //System.out.println("==========After calling States=========");
	             	 session.removeAttribute("statesList");
	                 session.setAttribute("statesList", strStates);
	                 //System.out.println("---------States in display--------"+strStates);
	            } 
	      
		   /*String strSelCountry=request.getParameter("sel_count");
			    
			    if(((hashMap.get("state")==null)||(((String)hashMap.get("state")).equals("")))
			    	&&((hashMap.get("country")==null)||((String)hashMap.get("country")).equals(""))) {
			    	 session.setAttribute("countriesList",Arrays.asList(ContryStateList.countries));
			     }
			     else if(!((String)hashMap.get("country")).equals("")) 
			     {
			    	  session.setAttribute("countriesList",Arrays.asList(ContryStateList.countries));
			    	  if(((String)hashMap.get("country")).equals("United States of America"))
			    		  session.setAttribute("statesList",Arrays.asList(ContryStateList.usstates));
			    	  if(((String)hashMap.get("country")).equals("India")) 
			    		  session.setAttribute("statesList",Arrays.asList(ContryStateList.indstates));
			     }*/
	       }
	   //End of try block
	   //Start of catch block 
         catch(Exception exception)
          {
	    	   System.out.println("The Exception is:"+exception);
	    	   logger.info("VIMSClientLookUpDispatchAction.displayCustomer");
	    	   logger.error(exception);
          }	
	  //End of catch block
        return actionMapping.findForward("clientDisplay");
	 }
    //End of displayCustomer method
	
	/********Method------------>modifyCustomer***********/
	
	/********This method is used to update the details of the customer and returns boolean type variable 
	 * calling a method in DB by sending form object as parameter*******/
	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward modifyCustomer(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
	{
		//logger is used to save our exceptions in the logger file 
		  logger=Logger.getLogger("Admin"); 
		  ArrayList arrayList=null;
		//Start of try block
		  try
	        {
			  //Creating a bean object
			    VIMSCustomerForm customerForm=(VIMSCustomerForm)actionForm;
			    HttpSession session=null;
			    session=request.getSession(false);
			    String strCustID=(String)session.getAttribute("custID");		  
			    String strSelState = request.getParameter("selected_state");
			    //System.out.println("---------State in modify-------"+strSelState);
			    customerForm.setState(strSelState);
		  	   
			  //Calling a method in BD by sending form object as parameter  
		        boolean result=VIMSClientBD.modifyClientDetailsBD(customerForm,strCustID);
			      
		        if(result==true)
				  {
			    	 request.setAttribute("MSG","Customer details updated successfully");
		    	     request.setAttribute("status",request.getParameter("status"));
				  }
		        else
		          {
		        	request.setAttribute("MSG","Customer details updation failed");
		          }
		     //This path is called when this method is accessed  
		       return actionMapping.findForward("vimsCustomerAction");		
			 }
        //End of try block		 
		//Start of catch block  
		  catch(Exception exception)
	       {
	    	   logger.info("VIMSClientLookUpDispatchAction.modifyCustomer");
	    	   logger.error(exception);
	    	   System.out.println("The Exception is:"+exception);
	       }	
		//End of catch block
	    //Returns a null value  
		  return null;
	 }
	//End of modifyCustomer method
	
	/********Method------------>deleteCustomer***********/
	
	/********This method returns a integer type calling a method in DB by sending the parameter as checkboxID******/
	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteCustomer(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
	{
		
	   //logger is used to save our exceptions in the logger file 
		 logger=Logger.getLogger("Admin"); 
	  //Start of try block	
		try
	       {
			 //Creating a form bean object
			   String strCustID=request.getParameter("custID");
			   VIMSCustomerForm customerForm=(VIMSCustomerForm)actionForm;
				 
			 //Calling a method from BD by sending checkbox id as parameter which returns an int value in return
			   int  intDel=VIMSClientBD.deleteClientDetailsBD(strCustID);
			    
			 //It displays this message when when this attribute is set 
			   request.setAttribute("status", "Inactive");
			 //Returns this path when all conditions are satisfied   
			   return actionMapping.findForward("vimsCustomerAction");		
	       }
	   //End of try block
	  //Start of catch block	
		catch(Exception exception)
	       {
	    	   logger.info("VIMSClientLookUpDispatchAction.deleteCustomer");
	    	   logger.error(exception);
	    	   System.out.println("The Exception is:"+exception);
	       }
	  //End of catch block
	 //Returns a null value  	
	   return null;
	 }
  //End of deleteCustomer method
	
	
	/********Method------------>testCustomer***********/
	
	/********This method is to load the add customer page********/
    /**
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
public ActionForward testCustomer(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
	{
	  //logger is used to save our exceptions in the logger file   
	    logger=Logger.getLogger("Admin"); 
	    HttpSession session=null;
	    //Start of try block	
	     try
	        {
	    	 //System.out.println("---------In testCustomer method-----------");
	    	 /*saveToken(request);
	    	  session=request.getSession(false);
	    	  String strSelCountry=request.getParameter("selected_country");
	    	  session.removeAttribute("countriesList");
	  		  session.setAttribute("countriesList",Arrays.asList(ContryStateList.countries));
	  		  session.removeAttribute("statesList");
	  		  if(strSelCountry!=null && strSelCountry.equalsIgnoreCase("India")){
	  		  session.setAttribute("statesList",Arrays.asList(ContryStateList.indstates));
	  		  }else if(strSelCountry!=null && strSelCountry.equalsIgnoreCase("United States of America")){
	  		  session.setAttribute("statesList",Arrays.asList(ContryStateList.usstates));
	  		  }else{
	  		  session.setAttribute("statesList",null);
	  		  }*/
	    	 
	    	 //Ajax Functionality before dumping with DataBase  
	    	  /*session=request.getSession(false);
	    	  session.removeAttribute("countriesList");
	          session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
	          String strSelCountry = request.getParameter("strSelCountry");
	    	  String []stateList=null;
	    	  StringBuffer sbuf=new StringBuffer();
	         
	    	  if(strSelCountry!=null) {
	         	stateList=ContryStateList.getStatesList(strSelCountry);
	         	if(stateList!=null){
	 	        	for(int i=0;i<stateList.length;i++)
	 	    	
	 	        	{
	 	            	 if(i!=stateList.length-1)
	 	           		 {
	 	           			 sbuf=sbuf.append(stateList[i]+";");
	 	           		 }
	 	           		 else
	 	           		 { 
	 	           			 sbuf=sbuf.append(stateList[i]);
	 	           		 }
	 	    		  }
	         	 }
	         	 response.setContentType("text/xml");
	      		 response.getWriter().println(sbuf.toString());
	         	 
	     		 if(request.getParameter("request_from")!=null && request.getParameter("request_from").equalsIgnoreCase("ajaxcall")){
	      	        	return null;
	     	     }
	         }*/
	    	 session=request.getSession(false);
	    	 ArrayList strCountries=(ArrayList)VIMSEmployeeBD.getCountryListBD();
	         session.removeAttribute("countriesList");
	         session.setAttribute("countriesList", strCountries);
	    	    String strSelCountry = request.getParameter("strSelCountry");
	    	    String []stateList=null;
	    	    HashMap hashMap=null;
	    	    StringBuffer sbuf=new StringBuffer();
	         if(strSelCountry!=null) {
	         ArrayList strStates=(ArrayList)VIMSEmployeeBD.getStateListBD(strSelCountry);
	         
	         	if(strStates!=null && strStates.size()>0){
	 	        	for(int i=0;i<strStates.size();i++)
	 	        	{
	 	            	  hashMap=(HashMap)strStates.get(i); 
	 	            	  String strStatesNames=(String)hashMap.get("stateNames");
	 	            	  String strStateNO=(String)hashMap.get("stateNo");
	 	            	 /* if(i!=strStates.size()-1)
	 	           		   {*/
	 	           			 sbuf=sbuf.append(strStateNO+":"+strStatesNames+";");
	 	           	//	   }
	 	           		  /*else
	 	           		   { 
	 	           			 sbuf=sbuf.append(strStateNO+":"+strStatesNames);
	 	           		   }*/
	 	    		  }
	 	        	  session.removeAttribute("statesList");
	                  session.setAttribute("statesList", strStates);
	                  //System.out.println("--------Stateslist---------"+strStates);
	         	 }
	         	 response.setContentType("text/xml");
	      		 response.getWriter().println(sbuf.toString());
	         	 
	     		 if(request.getParameter("request_from")!=null && request.getParameter("request_from").equalsIgnoreCase("ajaxcall")){
	     	        	return null;
	     	     }
	          }
	        }
	    //End of try block   
	   //Start of catch block    
	     catch(Exception exception)
	      {
	    	   logger.info("VIMSClientLookUpDispatchAction.testCustomer");
	    	   logger.error(exception);
	    	   System.out.println("The Exception is:"+exception);
	      }
	   //End of catch block
	   //Returns to this path  
	     return actionMapping.findForward("page");
	}
   //End of testCustomer method
   
   /********Method------------>viewCustomer***********/
   
   /********This method returns arrayList by calling a method in DB by sending the parameter as customerID*******/
   /**
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
public ActionForward viewCustomer(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
	{
	 //logger is used to save our exceptions in the logger file   
	   logger=Logger.getLogger("Admin"); 
		
	 //Start of try block  
	   try
	      {
		       //Initializing form bean object  
		    	 VIMSCustomerForm customerForm=(VIMSCustomerForm)actionForm;
		    	 String customerID=request.getParameter("custID");
				  
		       //Calling a method from BD class by sending customerID as parameter
		    	 ArrayList arrayList=VIMSClientBD.viewClientDetailsBD(customerID);
				 //System.out.println("-------ArrayList in View---------"+arrayList); 
			   
		    	 //Initiazing session object
		    	 HttpSession session=request.getSession(true);
				 session.setAttribute("Display",arrayList);
		
			   //When this method is executed it is forwarded to this path 
				VIMSClientBD.setClientFlagBD(customerID);
			   //When this method is executed it is forwarded to this path    
			    return actionMapping.findForward("clientView");		
	       }
	  //End of try block 
	 //Start of catch block  
	    catch(Exception exception)
	       {
	    	   logger.info("VIMSClientLookUpDispatchAction.viewCustomer");
	    	   logger.error(exception);
	    	   System.out.println("The Exception is:"+exception);
	       }
	 //End of catch block
	     
	 //Returns null value   
	   return null;
	     
	}
  //End of viewCustomer method
   
   /********Method------------>baseCustomer***********/
   
   /********This method loads the page when clicked on clients tab********/
   /**
	 * @param actionmapping
	 * @param actionform
	 * @param request
	 * @param response
	 * @return
	 */
public ActionForward baseCustomer(ActionMapping actionmapping,ActionForm actionform,HttpServletRequest request,HttpServletResponse response) 
	{
	 //logger is used to save our exceptions in the logger file    
	   logger=Logger.getLogger("Admin"); 
	  
	 //Start of try block  
	   try
		 {
		    /*String strIssueTypeSelected="Active";
		    VIMSCustomerForm customerForm=(VIMSCustomerForm)actionform;
		    if(customerForm.getTypeofIssue()!=null)
		    {
		    	strIssueTypeSelected= customerForm.getTypeofIssue();
		    	customerForm.setTypeofIssue(strIssueTypeSelected);
		    }			  
		    else if(request.getParameter("status")!=null)
			{
				strIssueTypeSelected=(String)request.getParameter("status");
				customerForm.setTypeofIssue(strIssueTypeSelected);
			}
		    else if(request.getAttribute("status")!=null)
		    {
		    	strIssueTypeSelected=(String)request.getAttribute("status");
		    	customerForm.setTypeofIssue(strIssueTypeSelected);
		    }
			else
			{
				customerForm.setTypeofIssue(strIssueTypeSelected);
			}*/
		       ArrayList arrayList=VIMSClientBD.getClientDetailsBD();
		    //Creating HttpSession object
			  HttpSession session=request.getSession(false);
			  session.setAttribute("List",arrayList);
		  
		   //When this method is executed it is forwarded to this path    
		     return actionmapping.findForward("homeClients");
		}
	   //End of try block
	   
	   //Start of catch block  
	     catch(Exception exception)
	      {
			  logger.info("VIMSClientLookUpDispatchAction.baseCustomer");
	   	      logger.error(exception);
			  System.out.println("The Exception is:"+exception);
			  return null;
	      }
	   //End of catch block
	 }
  //End of baseCustomer method  
   
  /**
   * 
   *  The action is for retrieving the newly added clients information
   *  
   * 	@param actionMapping
   *	@param actionForm
   * 	@param request
   * 	@param resoponse
   */
public ActionForward getNewCustomersList(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse resoponse)
   {
         Logger logger=null;
         HttpSession session=null;
         ArrayList customerList=null;
         HashMap customerSet=null;
         
         //try block starts
          try 
	         {
    	       session=request.getSession(false);
    	       if(session!=null) 
    	        { // if block starts
    	          customerSet=VIMSClientBD.getNewCustomerDetailsBD();
    	          customerList=(ArrayList)customerSet.get("newCustomerList");
	    	    	 if(customerList!=null)
	    	    		session.setAttribute("newCustomerList",customerList); 
	    	    	    return actionMapping.getInputForward();
	    	    } // End of If
	    	    else 
	    	    { //Else block starts
	    	       return actionMapping.findForward("sessionExpirePage");
	    	    } // End of Else block
	         } // try block ends 
	      catch(Exception exception)
	      { // catch block starts
	    	 logger.info("exception raised in getNewCustomersList action ");
	    	 logger.error(exception);
	    	 System.out.println("-------Exception in getNewCustomerList-----action-----"+exception);
	      } // End of catch block
	       return actionMapping.getInputForward();
   }
  //End of getNewCustomersList action
  
  /********Method------------>checkCustomerEmailID***********/
  
  /********This method is used to check whether the email ID of that particular customer already exists or not using AJAX functionality..*********/ 
  
  public ActionForward checkCustomerEmailID(ActionMapping actionmapping,ActionForm actionform,HttpServletRequest request,HttpServletResponse response) 
	{
	 //Start of try block  
	   try
		 {
		   VIMSCustomerForm customerForm=(VIMSCustomerForm)actionform;
		   String strEmailID=request.getParameter("Email");
		   boolean result=VIMSClientBD.checkCustomerEmailIdBD(strEmailID);
		   String strMsg=""; 
		   if(result==true)
		   {
			   strMsg="E-mail ID already exists";
		   }
		   else
		   {
			   strMsg="ok";
		   }
		   //Setting response object 
		     request.setAttribute("MSG","E-mail ID already exists");   
			 response.setContentType("text/xml");        
	         response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			 response.getOutputStream().println("<response>");
			 response.getOutputStream().println("<result>" + strMsg + "</result>");
			 response.getOutputStream().println("</response>");
		  return null;
		 }
	 //End of try block
	   
	 //Start of catch block  
	  catch(Exception exception)
	    {
		  System.out.println("The Exception is:"+exception);
		  return null;
	    }
	 //End of catch block
	}

 /********Method------------>changeCountry***********/
  
 /********This method is used to dynamically change the states according to the country selected..*********/ 
 /**
   * @param actionmapping
   * @param actionform
   * @param request
   * @param response
   * @return
   */
public ActionForward changeCountry(ActionMapping actionmapping,ActionForm actionform,HttpServletRequest request,HttpServletResponse response)  
	{ 
	   HttpSession session=null;
	   HashMap hashMap=null; 
	 //Start of try block  
	   try
		 {   
		   /*session=request.getSession(false);
		   String strSelCountry=request.getParameter("country");
		   if(strSelCountry==null)
		    	strSelCountry=(String)request.getAttribute("country");
		    	 session.setAttribute("countriesList",Arrays.asList(ContryStateList.countries));
		      if(!strSelCountry.equals("")) {
		    	  session.setAttribute("countriesList",Arrays.asList(ContryStateList.countries));
		    	  if(strSelCountry.equals("United States of America")) {
		    		  session.setAttribute("statesList",Arrays.asList(ContryStateList.usstates));
		    	    }  
		    	  if(strSelCountry.equals("India"))  {
		    		  session.setAttribute("statesList",Arrays.asList(ContryStateList.indstates));
		    	  }	 
		      }*/
		  /*HashMap hashMap=null;
		  
		  session = request.getSession(false);
   		  session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
           
		        String []stateList=null;
	       	    StringBuffer sbuf=new StringBuffer();
	            String strSelCountry1 = request.getParameter("country");
	            System.out.println("----------strSelCountry1--------"+strSelCountry1);
	            System.out.println("----------222--------");
		          
	           
	            if((strSelCountry1!=null)&&(!strSelCountry1.equals(""))) {
	            	
	            	session=request.getSession(false);
	            	stateList=ContryStateList.getStatesList(strSelCountry1);
	       
		   if(stateList!=null)
       	{
	        	for(int i=0;i<stateList.length;i++)
	    	
	        	{
	            	System.out.println("-----------stateList----------"+stateList[i]);
	            	 if(i!=stateList.length-1)
	           		 {
	           			 sbuf=sbuf.append(stateList[i]+";");
	           		 }
	           		 else
	           		 { 
	           			 sbuf=sbuf.append(stateList[i]);
	           		 }
	    		  }
       	 }
       
       	 System.out.println("-------------strStates in Buffer---------"+sbuf.toString());
       	 response.setContentType("text/xml");
    		 response.getWriter().println(sbuf.toString());
       
   		 if(request.getParameter("request_from")!=null && request.getParameter("request_from").equalsIgnoreCase("ajaxcall")){
   	        System.out.println("--------------------------Before returning null-----------");
   	        
   	        	return null;
   		 }
       }
	       
	   else if((hashMap.get("state") == null || ((String)hashMap.get("state")).equals("")) 
      		&& (hashMap.get("country") == null || ((String)hashMap.get("country")).equals(""))){
      
	    System.out.println("---------State-------"+(String)hashMap.get("state"));
        System.out.println("---------Country-------"+(String)hashMap.get("country"));
        session=request.getSession(false);
        System.out.println("---------In Country null & empty block-----------");
   	    session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
  	    strSelCountry1 = request.getParameter("country");
  	    System.out.println("----------strSelCountry1-----"+strSelCountry1);
  	    System.out.println("-----------333-------");
  	    
  	    
       if(strSelCountry1!=null)
       {
       	stateList=ContryStateList.getStatesList(strSelCountry1);
       	if(stateList!=null)
       	{
	        	for(int i=0;i<stateList.length;i++)
	    	
	        	{
	            	System.out.println("-----------stateList----------"+stateList[i]);
	            	 if(i!=stateList.length-1)
	           		 {
	           			 sbuf=sbuf.append(stateList[i]+";");
	           		 }
	           		 else
	           		 { 
	           			 sbuf=sbuf.append(stateList[i]);
	           		 }
	    		  }
       	 }
       
       	 System.out.println("-------------strStates in Buffer---------"+sbuf.toString());
       	 response.setContentType("text/xml");
    		 response.getWriter().println(sbuf.toString());
       
   		 if(request.getParameter("request_from")!=null && request.getParameter("request_from").equalsIgnoreCase("ajaxcall")){
   	        System.out.println("--------------------------Before returning null-----------");
   	        
   	        	return null;
   		    }
           }
          }*/
		   //Code for Ajax Funtionality
       	   ArrayList strCountries=(ArrayList)VIMSEmployeeBD.getCountryListBD();
           session = request.getSession(false);
           session.setAttribute("countriesList", strCountries);
       	
   	      String strSelCountry1 = request.getParameter("strSelCountry");
      	  	StringBuffer sbuf=new StringBuffer();
            
            if((strSelCountry1!=null)&&(!strSelCountry1.equals(""))) {
            	
            ArrayList strStates=(ArrayList)VIMSEmployeeBD.getStateListBD(strSelCountry1);
         	 if(strStates!=null && strStates.size()>0){
         		 System.out.println("========In IF Action=========");
	        	for(int i=0;i<strStates.size();i++)
	        	{
	        		   System.out.println("==========In Loop DAo==============");
	        		  hashMap=(HashMap)strStates.get(i); 
	        		  String strStateNO=(String)hashMap.get("stateNo");
	            	  String strStatesNames=(String)hashMap.get("stateNames");
	                  System.out.println("====size======"+(strStates.size()-1));
	            	  /*if(i!=(strStates.size()-1))
	           		   {*/
	           			 sbuf=sbuf.append(strStateNO+":"+strStatesNames+";");
	           		  /* }
	           		  else
	           		   { 
	           			 sbuf=sbuf.append(strStateNO+":"+strStatesNames);
	           		   }*/
            	 }
            
            	 response.setContentType("text/xml");
         		 response.getWriter().println(sbuf.toString());
            
        		 if(request.getParameter("request_from")!=null && request.getParameter("request_from").equalsIgnoreCase("ajaxcall")){
        	       return null;
        		 }
            }
            else if((hashMap.get("state") == null || ((String)hashMap.get("state")).equals("")) 
           		&& (hashMap.get("country") == null || ((String)hashMap.get("country")).equals(""))){
        	   
            	  strCountries=(ArrayList)VIMSEmployeeBD.getCountryListBD();
                  session = request.getSession(false);
                  session.setAttribute("countriesList", strCountries);
              	
            	//session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
       	    strSelCountry1 = request.getParameter("strSelCountry");
       	    
            if(strSelCountry1!=null)
            {
           	 strStates=(ArrayList)VIMSEmployeeBD.getStateListBD(strSelCountry1);
             System.out.println("======States========"+strStates);
           	 if(strStates!=null && strStates.size()>0)
            	  {
            		for(int i=0;i<strStates.size();i++)
   	        	   {
   	        		  hashMap=(HashMap)strStates.get(i); 
   	            	  String strStatesNames=(String)hashMap.get("stateNames");
   		            	 
   	            	  if(i!=strStates.size()-1)
   	           		   {
   	           			 sbuf=sbuf.append(strStatesNames+";");
   	           		   }
   	           		  else
   	           		   { 
   	           			 sbuf=sbuf.append(strStatesNames);
   	           		   }
                	 }
            	 }
            
            	 response.setContentType("text/xml");
         		 response.getWriter().println(sbuf.toString());
            
        		 if(request.getParameter("request_from")!=null && request.getParameter("request_from").equalsIgnoreCase("ajaxcall")){
         	         System.out.println("testing --------------------");
        			 return null;
        		 }
            }
           }          
         }
		 }
	   catch(Exception exception)
	    {
		  System.out.println("The Exception is:"+exception);
		  return actionmapping.getInputForward();
	    }
	   //Returns to the same page after this method is executed.
	     return actionmapping.getInputForward();
	}
  public ActionForward customerSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
	    HttpSession session = request.getSession(false);
	    VIMSCustomerForm customerForm=(VIMSCustomerForm)form;
	    
	    String strCustSearch=customerForm.getCustomerSearch(); 
	    if(strCustSearch==null || "".equals(strCustSearch)){
	    	strCustSearch=null;   
	       } 
	    
	    //String strStatus=(String)customerForm.getTypeofIssue();
	    ArrayList customerSearchList = VIMSClientBD.searchCustomerBD(strCustSearch);
    
	    if((customerSearchList!=null)&&(customerSearchList.size()>0))
	    {
	        session.removeAttribute("List");
	    	session.setAttribute("List", customerSearchList);
	    }
	    else
	    {
	    	session.removeAttribute("List"); 
	    	session.setAttribute("SearchResult", "No Records Found");
	    }
	     return mapping.findForward("homeClients");
  }

  /********Method------------>getKeyMethodMap***********/
   
  public Map getKeyMethodMap()
   {
	//Creating a map object 
	  Map map=new HashMap();

  /***********Here the key is specified to which method it should call.*************/
	  
	  map.put("VIMSApplication.addCustomer","testCustomer");
	  map.put("VIMSApplication.addSubmit","addCustomer");
	  map.put("VIMSApplication.modify","displayCustomer");
	  map.put("VIMSApplication.edit","modifyCustomer");
	  map.put("VIMSApplication.delete","deleteCustomer");
	  map.put("VIMSApplication.viewLink","viewCustomer");
	  map.put("VIMSApplication.viewBase","baseCustomer");
	  map.put("VIMSApplication.viewAddCustomerIDCheck","checkCustomerID");	
	  map.put("VIMSApplication.viewNewCustomers","getNewCustomersList");
	  map.put("Customer.changeCountry","changeCountry");
	  map.put("VIMSApplication.checkCustomerEmailID","checkCustomerEmailID");
	  map.put("VIMSApplication.customerSearch", "customerSearch");
	  
   //Returns map 
	 return map;
   }
   //End of getKeyMethodMap method
  }
 //End of VIMSClientLookUpDispatchAction class
