/*  File Name : VIMSNewIssue.java
 *  
 *  copyright (c) 2008 Vertex.All rights reserved.
 *  
 *  Created By : Aditya.p
 *  
 *  Description : This is the action class that will be executed when the user clicks the NewIssue tab from the menu.
 *  			  By using this we can collect the List of Application names,Application sub categories and issue types.
 *  			  We are using this file to insert the issue in to the database as an issue entry.
 *  
 *   Change History:
 * 	  Revision No.			Date		 @author			Description
 *		1.0					11-11-2008   Aditya.p			Initial version
 *
 *   
 *  
 *  			
 * 
 */

package com.vertex.VIMS.test.newissue.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertex.VIMS.test.listofissues.BD.ListofIssuesBD;
import com.vertex.VIMS.test.newissue.BD.*;
import com.vertex.VIMS.test.newissue.form.NewIssueForm;

public class VIMSNewIssue extends Action 
{	//class VIMSNewIssueStart
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException
	{	//execute method start
		
		NewIssueForm newIssueForm=(NewIssueForm)form;
		
		HttpSession session;
		session=request.getSession(true);
		session=request.getSession();
		
		session.removeAttribute("Hours");
		session.removeAttribute("strHourString");
		session.removeAttribute("AppSubCategory");
		String strUserID=(String) session.getAttribute("user");//Getting the UserID from the session 
		
		System.out.println("==========UserID=========="+strUserID);
		
		String strRoleType=(String) session.getAttribute("Role");
		System.out.println("===========Role=============="+strRoleType);
		String strChangeType=request.getParameter("ChangeType");	//Getting the hidden field status from request
		
		//System.out.println("==========strChangeType=========="+strChangeType);
		
		if(strChangeType==null)
		{	//if start
		saveToken(request);	
		//System.out.println("======entering into the===========");
		ArrayList ApplicationNames=new ArrayList();
		ArrayList ApplicationsubCategory=new ArrayList();
		ArrayList IssueType=new ArrayList();
		ArrayList IssuePriority=new ArrayList();
		ApplicationNames=NewIssueBD.ApplicationNames(strRoleType,strUserID);//calling the method ApplicationNames() which is in the in BD(BusinessDeligate)class.
		
		//System.out.println("=========ApplicationNames========="+ApplicationNames); 
		
		session.setAttribute("applicationNames", ApplicationNames);//Placing the returned arraylist in the session to retrieve in the jsp page.
		
		//ApplicationsubCategory=NewIssueBD.ApplicationsubCategory();//calling the method ApplicationSubCategory() which is in the in BD(BusinessDeligate)class.
		
		//session.setAttribute("AppSubCategory",ApplicationsubCategory); //Placing the returned arraylist in the session to retrieve in the jsp page.
				
		IssueType=NewIssueBD.IssueTypes(); //calling the method IssueTypes() which is in the in BD(BusinessDeligate)class.
		
		session.setAttribute("Issues", IssueType);//Placing the returned arraylist in the session to retrieve in the jsp page.
		
		IssuePriority=NewIssueBD.getIssuePriority();
		
		session.setAttribute("IssuePriority", IssuePriority);
		
		request.setAttribute("nosubcateg",null);
		
		//System.out.println("=====completed===========");
		
		return mapping.findForward("newIssuepage");//return statement to go to the IssuePage
		}
		else if(strChangeType.equalsIgnoreCase("changed"))//Checking whether the hidden field value is turned to 'changed' or not
		{	//else-if start
			
			//System.out.println("---Entering into the Onchange loop----");
			String strSelectedApplication;
			newIssueForm=(NewIssueForm)form;
			
			strSelectedApplication=newIssueForm.getapplicationName();
			//System.out.println("========================extered=======================");
			//System.out.println("---strSelectedApplication----"+strSelectedApplication);
			ArrayList ApplicationNames=new ArrayList();
			newIssueForm.setIssueType("");
			ApplicationNames=NewIssueBD.ApplicationNames(strRoleType,strUserID); //calling the ApplicationNames() method which is in the BD(BusinessDeligate) class.
			
			session.setAttribute("applicationNames", ApplicationNames);//placing the returned arraylist in the request to retrieve in the jsp page.
			
			//System.out.println("-----inside onchange---ApplicationNames---"+ApplicationNames); 
			
			ArrayList SubCategoryNames=new ArrayList();
			
			SubCategoryNames=NewIssueBD.subCategories(strSelectedApplication);//calling the subCategories() method which is in the BD(BusinessDeligate) class with the selected applicationname as an argument.
			
			//System.out.println("-----inside onchange---SubCategoryNames---"+SubCategoryNames);
			
			if(SubCategoryNames==null && SubCategoryNames.size()==0)
			{	//if start
				session.setAttribute("nosubcateg","No SubCategory's Found!");
			}	//if end
			else
			{
				session.setAttribute("AppSubCategory",SubCategoryNames);//placing the returned arraylist in the request to retrieve in the jsp page.
			
			//System.out.println("-----inside onchange---SubCategoryNames---"+SubCategoryNames); 
			}
			
			ArrayList IssueType=new ArrayList();
			IssueType=NewIssueBD.IssueTypes();//calling the method IssueTypes() which is in the in BD(BusinessDeligate)class.
			
			session.removeAttribute("Issues");
			session.setAttribute("Issues", IssueType);//placing the returned arraylist in the request to retrieve in the jsp page.
			
			
			return mapping.findForward("newIssuepage");//return statement to go to the IssuePage
			
		}	//else-if end
		else if(strChangeType.equals("Completed"))//Checkkingwhether the hidden field value is turned to 'Completed' or not
		{	//else-if start
			if(isTokenValid(request)){
				resetToken(request);
			newIssueForm=(NewIssueForm)form;
			String strApplicationName;
			
			strApplicationName=newIssueForm.getapplicationName();
			
			
			
			int intResult;
			String strNewIssueID;
			
			String strMailReceivers[];
			String strGroupSupervisorName;
			String strSupportManagername;
			
			int intMailServerCheck;
			
			//String strPropertiesFile=(getServlet().getServletContext().getRealPath("/")).replace(".","")+"WEB-INF/classes/resources/VIMSApplication.properties";
			
			//System.out.println("=========Properties file path==========="+strPropertiesFile);
			
			//ResourceBundle bundle=ResourceBundle.getBundle("D:/NewVIMS-WorkFolder/VIMS_DEV/jboss/server/default/deploy/vims-test.war/WEB-INF/classes/resources/VIMSApplication.properties");
			
			//System.out.println("=========Bundle========"+bundle);
			
			//String filePath=bundle.getString("VIMS.Attachments");
			
			
			String filePath=getServlet().getServletContext().getRealPath("/")+"VIMSUPLOAD";
			
			//String filePath="c:/VIMSAttachments";
			
			System.out.println("==========filePath========="+filePath);
			
			String strPath;
			
			//strPath=filePath.substring(0,filePath.indexOf(".")-1);
			
			//strPath=strPath+filePath.substring(filePath.indexOf(".")+1, filePath.indexOf("deploy")-1);
			
			intResult=NewIssueBD.AddNewIssue(newIssueForm,filePath,strUserID);//Calling the AddNewIssue() function which is in the NewIssueBD class with formobject,filepath,userID as arguments
		
			//System.out.println("===========intResult==========="+intResult);
			
		   if(intResult==0)//checking whether the new Issue is posted successfully or not 
			{	//if start
			   request.setAttribute("error","error");
			   
			   return mapping.findForward("failure");//forwarding to the failure page.
			}	//if end
			else
			{	//else start				
				
				strNewIssueID=NewIssueBD.getNewIssueID();//calling the getNewIssueID() function which is in the NewIssueBD class
				session.removeAttribute("NewIssueID");
				session.setAttribute("NewIssueID", strNewIssueID);//placing the issueid in the request inorder to get it in the jsp page
				
				//request.setAttribute("message","Issue was Added Successfully");
				
				String strContextPath=NewIssueBD.getContext(request);
				
				intMailServerCheck=NewIssueBD.CheckMailServer();
				System.out.println("==========intMailServerCheck=========="+intMailServerCheck);
				if(intMailServerCheck==0)
				{
				boolean Response=NewIssueBD.NewIssueMail(newIssueForm,strUserID,strContextPath,strNewIssueID,strRoleType);
				String strSeverity=(String)newIssueForm.getIssueType();
				System.out.println("=========strSeverity=============="+strSeverity);
				
				if(Response==false)
				{
					request.setAttribute("mailerror","mailerror");
					return mapping.findForward("failure");
				}
				else
				{
					//System.out.println("===========strApplicationName============"+strApplicationName);
					strMailReceivers=NewIssueBD.getNewIssueMailReceivers(strApplicationName);
					
					/*for(int i=0;i<strMailReceivers.length;i++)
					{
						System.out.println("++++++++++++++"+strMailReceivers[i]+"================");
					}*/
					
					
					strGroupSupervisorName=strMailReceivers[0];
					strSupportManagername=strMailReceivers[1];
					session.removeAttribute("strGroupSupervisorName");
					session.removeAttribute("strSupportManagername");
					session.setAttribute("strGroupSupervisorName", strGroupSupervisorName);
					session.setAttribute("strSupportManagername", strSupportManagername);
					
					/* Scheduler initialization  starts here 
					 * created : 02/09/2009
					*/
					
					HashMap IssueAssignDetails=ListofIssuesBD.EscalatedMailDetailsBD(strContextPath,strNewIssueID,strSeverity,strApplicationName);

					String Severity=ListofIssuesBD.getIssueSeverity(strNewIssueID);
					
						ListofIssuesBD.generateIssueScheduler(strNewIssueID,IssueAssignDetails,strContextPath);
					return mapping.findForward("success");	//forwarding to the success page	
				}
				}
				else
				{
					request.setAttribute("MailServerError","MailServerError");
					return mapping.findForward("failure");
				}
				
				//NewIssueBD.generateIssueScheduler(strNewIssueID,strNewIssueID,request);
				
				//Calling the Quartz Method
			
			}	//else end
			}else{
				return mapping.findForward("success");
			}
			
//			strApplicationName=newIssueForm.getapplicationName();
//			strApplicationSubCategories=newIssueForm.getApplicationSubCategory();
//			strTitle=newIssueForm.getTitle();
//			strReference=newIssueForm.getreference();
//			strIssueSeverity=newIssueForm.getIssueType();
//			strDescription=newIssueForm.getDescription();
//			fileUpload=newIssueForm.getFileUpload();
//			
//			String filePath=getServlet().getServletContext().getRealPath("/")+"upload";
//			
//			int intResult=NewIssueBD.AddNewIssue(strApplicationName,strApplicationSubCategories,strTitle,strReference,strIssueSeverity,strDescription,fileUpload,filePath);
			  
		}	//else-if end
		
		else if(strChangeType.equalsIgnoreCase("GetSLAHours"))
		{
			String strApplicationSelected;
			String strIssueSeverity;
			
			ArrayList SubCategoryNames=new ArrayList();
			
			int Hours;
			String strHourString;
			strApplicationSelected=newIssueForm.getapplicationName();
			strIssueSeverity=newIssueForm.getIssueType();
						
            /*ArrayList ApplicationNames=new ArrayList();
			ApplicationNames=NewIssueBD.ApplicationNames(strRoleType,strUserID);
			
            ArrayList SubCategoryNames=new ArrayList();
			
			SubCategoryNames=NewIssueBD.subCategories(strApplicationSelected);
			
			if(SubCategoryNames==null && SubCategoryNames.size()==0)
			{	
				session.setAttribute("nosubcateg","No SubCategory's Found!");
			}	
			else
			{
				session.setAttribute("AppSubCategory",SubCategoryNames);
			}*/
			SubCategoryNames=NewIssueBD.subCategories(strApplicationSelected);
			
			if(SubCategoryNames==null && SubCategoryNames.size()==0)
			{	//if start
				session.setAttribute("nosubcateg","No SubCategory's Found!");
			}	//if end
			else
			{
				session.setAttribute("AppSubCategory",SubCategoryNames);//placing the returned arraylist in the request to retrieve in the jsp page.
			
			//System.out.println("-----inside onchange---SubCategoryNames---"+SubCategoryNames); 
			}
			
			if((strChangeType==null) || ("".equalsIgnoreCase(strChangeType)))
			{
				session.removeAttribute("Hours");
				session.removeAttribute("strHoursString");
			}
			else
			{
			Hours=NewIssueBD.getApplicationSLA(strApplicationSelected,strIssueSeverity);
			System.out.println("==========Hours=========="+Hours);
			if(Hours==0)
			{
				session.removeAttribute("Hours");
				session.removeAttribute("strHoursString");
			}
			else
			{
			   if(Hours<10)
			   {
				strHourString="hr";	
			   }
			   else
			   {
				strHourString="hrs";
			   }
			   session.setAttribute("Hours", Hours);
			   session.setAttribute("strHourString", strHourString);
			}
			}
			return mapping.findForward("newIssuepage");
			
		}
		else if(strChangeType.equalsIgnoreCase("Reset"))
		{
			session.removeAttribute("Hours");
			session.removeAttribute("strHoursString");
			session.removeAttribute("AppSubCategory");
			return mapping.findForward("newIssuepage");
		}
		
		return mapping.findForward("newIssuepage");//forwarding to the newIssuePage.
	}//execute() method end
} //Class VIMSNewIssue class end
