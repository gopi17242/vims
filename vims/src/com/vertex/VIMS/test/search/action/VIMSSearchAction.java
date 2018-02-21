/*  
  FileName	    : VIMSSearchAction.java
  
                  
  
  Description	: This action class is used to call different methods 
                  in BD(Business Delegate)class and set the return values 
                  in attributes and access different if conditions as needed 
                  and forwarding them to the concern paths.	
  
  Developed by  : Vertex Computer Systems.
                  copyright (c) 2008 Vertex.
                  All rights reserved.
                   
  Change History:
 
  Revision No.	:		Date		  @author		Description
    1.0				Friday 21,2008	  Geeta.M	   Initial Version.*/


//Package structure followed for this class.
package com.vertex.VIMS.test.search.action;

//import statements
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;

import com.vertex.VIMS.test.common.ContryStateList;
import com.vertex.VIMS.test.employee.BD.VIMSEmployeeBD;
import com.vertex.VIMS.test.search.BD.VIMSSearchBD;
import com.vertex.VIMS.test.search.form.VIMSSearchForm;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.*;

//Start of Action class
 public class VIMSSearchAction extends Action 
	{
	    //initialising Logger 
	      Logger logger=null;
		
	    //start of execute method
	    public ActionForward execute(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
		{
			
			//creating a Form Bean object
	    	  VIMSSearchForm searchForm=(VIMSSearchForm)actionForm;
	    	  
	    	//Creating a session object
	    	  HttpSession session;
	  		  session=request.getSession();
	    	  String strUserID=(String) session.getAttribute("user");
	    	  String strRoleType=(String) session.getAttribute("Role");
	    	  
	        //logger is used to save our exceptions in the logger file 
	    	  logger=Logger.getLogger("Admin"); 
			
			//start of try block
	    	try
			{
				  //Getting the change type after search button is clicked
					  String strChangeType=request.getParameter("ChangeType");
				    //System.out.println("==========strChangeType=========="+strChangeType);
				 
				//Start of if condition
				if(strChangeType==null)
				{
 
					String strCustomerID=searchForm.getCustomerName();
					//System.out.println("----CustomerID in null Type---------"+strCustomerID);	
					
				  /*This method is used to call a method in BD(Business Delegate)to get all the Application Names which are
		            in database.This returns arrayList*/   
		            ArrayList applicationNames=VIMSSearchBD.getSearchApplicationNamesBD(strUserID,strRoleType,strCustomerID);
				    session.setAttribute("appNames",applicationNames);
				    System.out.println("The applicationNames are:"+applicationNames);
				  
				  /*This method is used to call a method in BD(Business Delegate)to get all the Customer Names which are
		            in database.This returns arrayList*/   
				    ArrayList customerNames=VIMSSearchBD.getSearchCustomerNamesBD();

				    session.setAttribute("custNames",customerNames);
				    
				    //System.out.println("The customerNames are:"+customerNames);
				  
				  /*This method is used to call a method in BD(Business Delegate)to get all the status which are
		            in database.This returns arrayList*/     
				    ArrayList status=VIMSSearchBD.getSearchStatusBD();
				    session.setAttribute("status",status);
				    //System.out.println("The status are:"+status);
				  
				  /*This method is used to call a method in BD(Business Delegate)to get all the severity which are
		            in database.This returns arrayList*/     
				    ArrayList severity=VIMSSearchBD.getSearchSeverityBD();
				    session.setAttribute("severity",severity);
				    //System.out.println("The severity are:"+severity);
				  
				    //String strUserID=(String)session.getAttribute("user");  
				    //System.out.println("--------strUserID---------"+strUserID);
				  
				  //forwarding this to a path once satisfied this if condition 
				    session.removeAttribute("Record");
				    return actionMapping.findForward("page");
	        	 }
				//end of if condition
				
				else if(strChangeType.equalsIgnoreCase("Changed"))
				{
					 String strCustomerID=searchForm.getCustomerName();
					 //System.out.println("---------strCustomerID in Changed Type---------"+strCustomerID);
					 
					 ArrayList applicationNames=VIMSSearchBD.getSearchApplicationNamesBD(strUserID,strRoleType,strCustomerID);
					 session.setAttribute("appNames",applicationNames);
					 //System.out.println("The applicationNames are:"+applicationNames);
					  
					 /*//session.setAttribute("appNames", Arrays.asList(VIMSSearchBD.getSearchApplicationNamesBD(strUserID, strRoleType,strCustomerID)));
				
					  session.removeAttribute("custNames");
				      session.setAttribute("custNames", Arrays.asList(VIMSSearchBD.getSearchCustomerNamesBD()));
				        
				        String strCustomer = request.getParameter("Customer");
				   	    System.out.println("----------strCustomer-----"+strCustomer);
				   	    
				   	    String []appNames=null;
				   	    StringBuffer sbuf=new StringBuffer();
				        if(strCustomer!=null) {
				        	//appNames=Arrays.asList(VIMSSearchBD.getSearchApplicationNamesBD(strUserID, strRoleType,strCustomer));
				        	if(appNames!=null){
					        	for(int i=0;i<appNames.length;i++)
					    	
					        	{
					            	System.out.println("-----------stateList----------"+appNames[i]);
					            	 if(i!=appNames.length-1)
					           		 {
					           			 sbuf=sbuf.append(appNames[i]+";");
					           		 }
					           		 else
					           		 { 
					           			 sbuf=sbuf.append(appNames[i]);
					           		 }
					    		  }
				        	 }
				        	 System.out.println("-------------strStates in Buffer---------"+sbuf.toString());
				        	 response.setContentType("text/xml");
				     		 response.getWriter().println(sbuf.toString());
				        	 
//				             response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
//				             response.getOutputStream().println("<response>");
//				             response.getOutputStream().println("<result>" +strStates+ "</result>");
//				    		   response.getOutputStream().println("</response>");
//				    		   response.getOutputStream().close();
				    		 if(request.getParameter("request_from")!=null && request.getParameter("request_from").equalsIgnoreCase("ajaxcall")){
				    	        System.out.println("--------------------------Before returning null-----------");
				    	        
				    	        	return null;
				    	     }
				        	//System.out.println("-----------stateList----------"+stateList);
				        }
				        */
				}
				
				//Start of else if condition
				else if(strChangeType.equalsIgnoreCase("Submitted"))
				 {
				    //System.out.println("--------In Action class----------");
				    //System.out.println("----searchForm.getCustomerName()--------"+searchForm.getCustomerName());
				    //System.out.println("----searchForm.getApplicationName()--------"+searchForm.getApplicationName());
					
				    String strCustomerID=searchForm.getCustomerName();
				    //System.out.println("---------strCustomerID in Action---------"+strCustomerID); 
					
				    ArrayList strRecords=VIMSSearchBD.searchRecordBD(searchForm,strUserID,strRoleType);
					String strSearch=(String)strRecords.get((strRecords.size()-1));  
					//System.out.println("---------strRecords.size()---------"+strRecords.size()); 
					strRecords.remove((strRecords.size()-1));
					//System.out.println("---------strRecords.size()---------"+strRecords.size()); 
				    //System.out.println("----------strRecords in arrayList-------"+strRecords);
				    
						if(strRecords==null||strRecords.size()==0)
						{
							//If the records are null it returns the above message

							  session.removeAttribute("Record");
							  request.setAttribute("MSG","No Records Found");

							  //System.out.println("======MSG==========");
						}
						
							if(session.getAttribute("Record")!=null) {
								
							    //System.out.println("=======removed frm session=========");
							   session.removeAttribute("Record");
							}
						
							if(strRecords.size()>0) 
                    		{
								session.setAttribute("Record",strRecords);
								session.setAttribute("searchCriteria",strSearch);
								//System.out.println("--------Search Criteria--------"+(strRecords.get((strRecords.size()-1))));
								//strRecords.remove((strRecords.size()-1));

							}
								
					//If you have some records it returns the records setting an attribute and getting the attribute in the jsp page.
					 }
					//Once this if condition is satisfied it returns to the above path
						//System.out.println("========before forwarding===========");
				       //System.out.println("----searchForm.getCustomerName()--------"+searchForm.getCustomerName());
				       //System.out.println("----searchForm.getApplicationName()--------"+searchForm.getApplicationName());
				
					  return actionMapping.findForward("searchResult");
				 
				//End of else if block
			 }
	    	//End of try block
			
	    	//Start of catch block
			catch(Exception exception)
			{
			   
				logger.info("VIMSSearchAction.execute");
		    	logger.error(exception);
				System.out.println("==========exception Occured in action class==============="+exception);
			    exception.printStackTrace();
			}
			//end of catch block
			
			return null;
	    }//End of execute method
	}//End of Action class
