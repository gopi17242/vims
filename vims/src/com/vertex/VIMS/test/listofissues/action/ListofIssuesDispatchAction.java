/*  File Name : ListofIssuesDispatchAction.java
 *  
 *  copyright (c) 2008 Vertex.All rights reserved.
 *  
 *  Created By : Aditya.p
 *  
 *  Description : This is the action class that will be executed when the user clicks the List of Issues tab from the menu.
 *  			  After clicking this we will see the page which contains all list of issues . If you click the issueid then
 *   			  you will be navigated to the Issue Assignement page where you can view the history of the page and you can
 *   			  assign that issue to another group member,In this page you can also change the status of the issue.  
 *  
 *   Change History:
 * 	  Revision No.			Date		 @author			Description
 *		1.0					16-11-2008   Aditya.p			Initial version
 *
 *   
 *  
 *  			
 * 
 */

package com.vertex.VIMS.test.listofissues.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import com.vertex.VIMS.test.listofissues.form.ListofIssuesForm;
import com.vertex.VIMS.test.listofissues.BD.*;
import com.vertex.VIMS.test.login.BD.LoginBD;

import org.apache.log4j.Logger;

/**
 * @author aditya.p
 *
 */
public class ListofIssuesDispatchAction extends LookupDispatchAction
{	// Class ListofIssuesDispatchAction Start
	
	/**
	 * @param mapping
	 * @param af
	 * @param request
	 * @param response
	 * @return
	 */
	
	/* This function is used to display the list of issues when the list of issues link is clicked */
	
	public ActionForward BasePage(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
	{	// Function BasePage Start
		
		HttpSession session;
		String strIssueSearch;
		
		session=request.getSession(false);
		session.removeAttribute("SearchUser");
	
		strIssueSearch=request.getParameter("IssueSearch");
		
		String strUserID=(String) session.getAttribute("user");//Getting the UserID from the session 
		String strRoleType=(String) session.getAttribute("Role");//Getting the User Role from the session 
		
		if(strRoleType.equalsIgnoreCase("admin"))
		{
		  session.setAttribute("SearchUser", "Admin");
		}
		if(strRoleType.equalsIgnoreCase("user"))
		{
		  session.setAttribute("SearchUser", "Internal");
		}
		//System.out.println("============strRoleType============"+strRoleType);
		//request.setAttribute("Role",strRoleType);
		
		ListofIssuesForm form=(ListofIssuesForm)af;
	/*	String strIssueTypeSelected=(String) request.getParameter("pageId");
		
		if(strIssueTypeSelected!=null)
		{
			//System.out.println("========strIssueTypeSelected======="+strIssueTypeSelected);
		    form.setTypeofIssue("all");
		}
		else
		{
			//System.out.println("=========FormBean value=============="+form.getTypeofIssue());
		}
	*/
		ArrayList IssuesList=new ArrayList();
		if((strIssueSearch!=null) && (strIssueSearch.equalsIgnoreCase("IssueSearch")))
		{
				IssuesList=ListofIssuesBD.ListofIssuesSearch(form,strUserID,strRoleType);
				if((IssuesList!=null)&&(IssuesList.size()>0))
				{
					session.removeAttribute("IssueList");
					session.setAttribute("IssueList", IssuesList);//Setting the List of issues in session
				} 
				else
				{
					session.removeAttribute("IssueList");
					request.setAttribute("IsssueSearchMessage", "No Records Found");
				}
		}
		else
		{	 
			IssuesList=ListofIssuesBD.IssuesList(form,strUserID,strRoleType);//Calling the method IssuesList method which is in BD Class
			session.removeAttribute("IssueList");
			session.setAttribute("IssueList", IssuesList);//Setting the List of issues in session
		}
		
		return mapping.findForward("Issuespage");
		
	}// Function BasePage End
	
	/**
	 * @param mapping
	 * @param af
	 * @param request
	 * @param response
	 * @return
	 */
	/*public ActionForward ListIssues(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
	{
		ListofIssuesForm Issuesform=(ListofIssuesForm)af;
		
		ArrayList IssuesList=new ArrayList();
		
		HttpSession session;
		session=request.getSession(false);
		String strUserID=(String) session.getAttribute("user");//Getting the UserID from the session 
		
		String strRoleType=(String) session.getAttribute("Role");
		
		IssuesList=ListofIssuesBD.IssuesList(Issuesform,strUserID,strRoleType);
		
		session.setAttribute("IssueList", IssuesList);
				
		return mapping.findForward("Issuespage");
		
	}*/
	
	/**
	 * @param mapping
	 * @param af
	 * @param request
	 * @param response
	 * @return
	 */
	
	/* This method is used to get the information about a particular issue */
	
	public ActionForward IssueDetails(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
	{	//Method IssueDetails start
		String IssueVerifiedStatus = null;
		
		String strApplicationName;
		String strPosted_BY;
		String strCurrentIssueStatus = null;
		String strIssueDescription = null;
		HttpSession session;
		session=request.getSession(false);
		String roleType=null;
		
		String strGroupName;
		String strAssignedEmployeeID = null;
		String strAssignedEmployeeName = null;
		String strLoginUser=null;
		
		ListofIssuesForm Issuesform=(ListofIssuesForm)af;//Creating form object of List of Issues 
		String strIssueId=(String)Issuesform.getIssueId();//retrieving issue id from the form bean
		System.out.println("=========strIssueId=====from home page========="+strIssueId);
		String strID=(String) request.getParameter("id"); //Getting the issues id from the request
		System.out.println("=========strID=====from List of Issues page========="+strID);
		//String strGroupSelected=Issuesform.getGroupname();
		
		strLoginUser=(String) session.getAttribute("user");
		System.out.println("=======Login User In Lis of Issues Action Class======="+strLoginUser);
		int intResponse;
		
		ArrayList IssueDetails1=new ArrayList();
		ArrayList IssueDetails2=new ArrayList();
		ArrayList IssueDetails3=new ArrayList();
		ArrayList IssueDetails4=new ArrayList();
		ArrayList Groups=new ArrayList();
		ArrayList GroupEmployee=new ArrayList();
		ArrayList FileName=new ArrayList();

		roleType=(String)session.getAttribute("Role");//Getting the role from the session
		
		System.out.println("===========roleType============"+roleType);
		ArrayList AssignedEmployeeDetails=new ArrayList();
		session.removeAttribute("CheckParam");
		session.removeAttribute("InternalClose");
		session.removeAttribute("CustomerClose");
		if(session.getAttribute("StatusPermission")!=null)
		{
			session.removeAttribute("StatusPermission");
		}
		if(strIssueId!=null)
		{	//if start

            /* Getting all information of the particular issue */
			
			IssueDetails1=ListofIssuesBD.IssueDetails1(request,strIssueId);
			
			System.out.println("======IssueDetails1==in====Action Class======"+IssueDetails1);
            //IssueDetails1=ListofIssuesBD.IssueDetails1(request,strIssueId);
			IssueDetails2=ListofIssuesBD.IssueDetails2(strIssueId);
			IssueDetails3=ListofIssuesBD.IssueDetails3(strIssueId);
			IssueDetails4=ListofIssuesBD.IssueDetails4(strIssueId);
			FileName=ListofIssuesBD.getFileAttachment(strIssueId);
			strPosted_BY=ListofIssuesBD.getIssuePostedBy(strIssueId);
			
				if(((IssueDetails1.size()!=0 || IssueDetails2.size()!=0 || IssueDetails3.size()!=0 || IssueDetails4.size()!=0 ) && ((strPosted_BY.equalsIgnoreCase("internal") || strPosted_BY.equalsIgnoreCase("customer"))|| strPosted_BY.equalsIgnoreCase("admin"))|| ((!strPosted_BY.equalsIgnoreCase("internal") || !strPosted_BY.equalsIgnoreCase("customer"))|| !strPosted_BY.equalsIgnoreCase("admin"))&&(!strPosted_BY.equalsIgnoreCase("Invalid")))) 
				{	//if start
					
					//Groups=ListofIssuesBD.getGroups();
					if((session.getAttribute("strAssignedEmployeeID")!=null)&&(session.getAttribute("strAssignedEmployeeName")!=null))
					{	//if start
						session.removeAttribute("strAssignedEmployeeID");
						session.removeAttribute("strAssignedEmployeeName");
					}	//end of if
					
					strGroupName=ListofIssuesBD.getGroupName(strIssueId);
					GroupEmployee=ListofIssuesBD.getGroupEmployees(strIssueId);
					
					if(IssueDetails1.size()>0)
					{
					HashMap hashmap1=(HashMap)IssueDetails1.get(0);
					strCurrentIssueStatus=(String) hashmap1.get("status");
					IssueVerifiedStatus=(String)hashmap1.get("Issueverifiedstatus");
					System.out.println("============IssueVerified Status =============="+IssueVerifiedStatus);
					System.out.println("==========Current Issue Status=========="+strCurrentIssueStatus);
					}
					if(strCurrentIssueStatus!=null)
					{
						if(!strCurrentIssueStatus.equalsIgnoreCase("Open"))
						{	//if start
							
							AssignedEmployeeDetails=ListofIssuesBD.getAssignedEmployeeDetails(strIssueId);
							if(AssignedEmployeeDetails.size()>0)
							{	//if start
								
							strAssignedEmployeeID=(String) AssignedEmployeeDetails.get(0);
							strAssignedEmployeeName=(String) AssignedEmployeeDetails.get(1);
												
							session.setAttribute("strAssignedEmployeeID", strAssignedEmployeeID);
							session.setAttribute("strAssignedEmployeeName", strAssignedEmployeeName);
							}	//if end
					     }	//if end
					}
						if(IssueDetails4.size()>0)
						{
						HashMap hashmap2=(HashMap)IssueDetails4.get(0);
						strIssueDescription=(String)hashmap2.get("description");
						}
						Issuesform.setDescription(strIssueDescription);
						
						session.setAttribute("CurrentIssueStatus", strCurrentIssueStatus);
						session.setAttribute("IssueVerified",IssueVerifiedStatus);
						session.setAttribute("IssueDetails1", IssueDetails1);
						session.setAttribute("IssueDetails2", IssueDetails2);
						session.setAttribute("IssueDetails3", IssueDetails3);
						session.setAttribute("IssueDetails4", IssueDetails4);
						
						session.setAttribute("strAssignedEmployeeID", strAssignedEmployeeID);
						session.setAttribute("strAssignedEmployeeName", strAssignedEmployeeName);
						//session.setAttribute("Groups", Groups);
						session.setAttribute("GroupEmployee", GroupEmployee);
						request.setAttribute("id", strIssueId);		
						session.setAttribute("GroupName",strGroupName);
			
						session.removeAttribute("FirstFile");
						session.removeAttribute("FirstFile");
						session.removeAttribute("SecondFile");
						session.removeAttribute("ThirdFile");
						session.removeAttribute("FourthFile");
						session.removeAttribute("FifthFile");
						
						if(FileName.size()==0)
						{	//if start
						session.setAttribute("NoFile", "None");
						}	//if end					
					//HashMap hashmap2=(HashMap)IssueDetails4.get(0);
					//strIssueDescription=(String)hashmap2.get("description");
					Issuesform.setDescription(strIssueDescription);
					
					session.setAttribute("CurrentIssueStatus", strCurrentIssueStatus);
					session.setAttribute("IssueDetails1", IssueDetails1);
					session.setAttribute("IssueDetails2", IssueDetails2);
					session.setAttribute("IssueDetails3", IssueDetails3);
					session.setAttribute("IssueDetails4", IssueDetails4);
					
					session.setAttribute("strAssignedEmployeeID", strAssignedEmployeeID);
					session.setAttribute("strAssignedEmployeeName", strAssignedEmployeeName);
					//session.setAttribute("Groups", Groups);
					session.setAttribute("GroupEmployee", GroupEmployee);
					request.setAttribute("id", strIssueId);		
					session.setAttribute("GroupName",strGroupName);
										
					
					session.removeAttribute("FirstFile");
					session.removeAttribute("SecondFile");
					session.removeAttribute("ThirdFile");
					session.removeAttribute("FourthFile");
					session.removeAttribute("FifthFile");
					
					if(FileName.size()==0)
					{	//if start
					session.setAttribute("NoFile", "None");
					}	//if end
					else if(FileName.size()<2)
						{	//else start
							if(!FileName.get(0).equals("")){
							session.setAttribute("FirstFile",FileName.get(0));}
							System.out.println("=======FileName.get(0)===="+FileName.get(0));
						}
						else if(FileName.size()<3)
						{	//else start
							if(!FileName.get(0).equals("")){
							session.setAttribute("FirstFile",FileName.get(0));}
							System.out.println("=======FileName.get(0)===="+FileName.get(0));
							if(!FileName.get(1).equals("")){
							session.setAttribute("SecondFile",FileName.get(1));}
							System.out.println("=======FileName.get(1)===="+FileName.get(1));
						}//else end
						else if(FileName.size()<4)
						{	//else start
							if(!FileName.get(0).equals("")){
							session.setAttribute("FirstFile",FileName.get(0));}
							System.out.println("=======FileName.get(0)===="+FileName.get(0));
							if(!FileName.get(1).equals("")){
							session.setAttribute("SecondFile",FileName.get(1));}
							System.out.println("=======FileName.get(1)===="+FileName.get(1));
							if(!FileName.get(2).equals("")){
							session.setAttribute("ThirdFile",FileName.get(2));}
							System.out.println("=======FileName.get(2)===="+FileName.get(2));
						}//else end
						else if(FileName.size()<5)
						{	//else start
							if(!FileName.get(0).equals("")){
							session.setAttribute("FirstFile",FileName.get(0));}
							System.out.println("=======FileName.get(0)===="+FileName.get(0));
							if(!FileName.get(1).equals("")){
							session.setAttribute("SecondFile",FileName.get(1));}
							System.out.println("=======FileName.get(1)===="+FileName.get(1));
							if(!FileName.get(2).equals("")){
							session.setAttribute("ThirdFile",FileName.get(2));}
							System.out.println("=======FileName.get(2)===="+FileName.get(2));
							if(!FileName.get(3).equals("")){
							session.setAttribute("FourthFile",FileName.get(3));}
							System.out.println("=======FileName.get(3)===="+FileName.get(3));
						}//else end
						else
						{	//else start
							if(!FileName.get(0).equals("")){
							session.setAttribute("FirstFile",FileName.get(0));}
							System.out.println("=======FileName.get(0)===="+FileName.get(0));
							if(!FileName.get(1).equals("")){
							session.setAttribute("SecondFile",FileName.get(1));}
							System.out.println("=======FileName.get(1)===="+FileName.get(1));
							if(!FileName.get(2).equals("")){
							session.setAttribute("ThirdFile",FileName.get(2));}
							System.out.println("=======FileName.get(2)===="+FileName.get(2));
							if(!FileName.get(3).equals("")){
							session.setAttribute("FourthFile",FileName.get(3));}
							System.out.println("=======FileName.get(3)===="+FileName.get(3));
							if(!FileName.get(4).equals("")){
							session.setAttribute("FifthFile",FileName.get(4));}
							System.out.println("=======FileName.get(4)===="+FileName.get(4));
						}	//else end
					intResponse=LoginBD.validateViewPermissionBD(strLoginUser,strIssueId);
					if(intResponse==1)
					{
						session.setAttribute("StatusPermission","1");
					}
					System.out.println("==========strPosted_BY==========="+strPosted_BY);
						if(strPosted_BY.equalsIgnoreCase("Internal"))
						{	//if start
							session.setAttribute("InternalClose","Close");
						}	//if end
						if(strPosted_BY.equalsIgnoreCase("Customer"))
						{
							session.setAttribute("CustomerClose","Verify");
						}

						// Set the issue view flag to 1
						if(roleType.equalsIgnoreCase("Admin"))
						{	//if start
							ListofIssuesBD.changeIssueFlagValueBD(request.getParameter("id"));
						}	//if end	
						System.out.println("==========Befor If End===========");
						//return mapping.findForward("AssignmentPage");
					}
				else
				{	//else start
					System.out.println("==========After If End===========");
					System.out.println("===========In Else==============");
					/*session.removeAttribute("IssueDetails1");//removing values from the session as it created in other pages.
					session.removeAttribute("IssueDetails2");*/
					request.setAttribute("InValidIssueId", "Please Enter Valid Issue ID");
					return mapping.findForward("home");// if the IssueDetails1 and IssueDetails2 are empty returning to the home page.
				}	//else end	
				return mapping.findForward("AssignmentPage");// if ArrayList is not empty forwarding to IncidentAssignment.jsp
				//return mapping.getInputForward();   // added by rajashekar	
			
		} //if end
		else if(request.getParameter("id")!=null)
        {	//else if start
			System.out.println("==========In Else Block========456789====");
			
	 	if((session.getAttribute("strAssignedEmployeeID")!=null)&&(session.getAttribute("strAssignedEmployeeName")!=null))
		{	//if start
				session.removeAttribute("strAssignedEmployeeID");
				session.removeAttribute("strAssignedEmployeeName");
		}	//if end
			
	 		System.out.println("========strID from list of issues page==========="+strID);
	 		
			IssueDetails1=ListofIssuesBD.IssueDetails1(request,strID);
			
			System.out.println("=========IssueDetails1=========="+IssueDetails1);
			IssueDetails2=ListofIssuesBD.IssueDetails2(strID);
			System.out.println("=========IssueDetails2=========="+IssueDetails2);
			IssueDetails3=ListofIssuesBD.IssueDetails3(strID);
			System.out.println("=========IssueDetails3=========="+IssueDetails3);
			IssueDetails4=ListofIssuesBD.IssueDetails4(strID);
			System.out.println("=========IssueDetails4=========="+IssueDetails4);
			FileName=ListofIssuesBD.getFileAttachment(strID);
			strPosted_BY=ListofIssuesBD.getIssuePostedBy(strID);
			strGroupName=ListofIssuesBD.getGroupName(strID);
			GroupEmployee=ListofIssuesBD.getGroupEmployees(strID);
			
			if(IssueDetails1.size()>0)
			{
			HashMap hashmap1=(HashMap)IssueDetails1.get(0);
			strCurrentIssueStatus=(String) hashmap1.get("status");
			IssueVerifiedStatus=(String)hashmap1.get("Issueverifiedstatus");
			System.out.println("============IssueVerified Status =============="+IssueVerifiedStatus);
			System.out.println("==========Current Issue Status=========="+strCurrentIssueStatus);
			}
			if(strCurrentIssueStatus!=null)
			{
			if(!strCurrentIssueStatus.equalsIgnoreCase("Open"))
			{	//if start
				AssignedEmployeeDetails=ListofIssuesBD.getAssignedEmployeeDetails(strID);
				if(AssignedEmployeeDetails.size()>0)
				{	//if start
				strAssignedEmployeeID=(String) AssignedEmployeeDetails.get(0);
				strAssignedEmployeeName=(String) AssignedEmployeeDetails.get(1);
				
				session.setAttribute("strAssignedEmployeeID", strAssignedEmployeeID);
				session.setAttribute("strAssignedEmployeeName", strAssignedEmployeeName);
				} //if end
			 }	//if end
			}
			if(IssueDetails4.size()>0)
			{
			HashMap hashmap2=(HashMap)IssueDetails4.get(0);
			strIssueDescription=(String)hashmap2.get("description");
			}
			Issuesform.setDescription(strIssueDescription);
			
			session.setAttribute("CurrentIssueStatus", strCurrentIssueStatus);
			session.setAttribute("IssueVerified",IssueVerifiedStatus);
			session.setAttribute("IssueDetails1", IssueDetails1);
			session.setAttribute("IssueDetails2", IssueDetails2);
			session.setAttribute("IssueDetails3", IssueDetails3);
			session.setAttribute("IssueDetails4", IssueDetails4);
			//session.setAttribute("strFileName", strFileName);
			//session.setAttribute("Groups", Groups);
			
			
			session.setAttribute("GroupName",strGroupName);
			session.setAttribute("GroupEmployee", GroupEmployee);
			request.setAttribute("id", strID);		
			
			//If no Uploaded files found then we will display as no files
			
			session.removeAttribute("FirstFile");
			session.removeAttribute("SecondFile");
			session.removeAttribute("ThirdFile");
			session.removeAttribute("FourthFile");
			session.removeAttribute("FifthFile");
			
			if(FileName.size()==0)
			{	//if start
			session.setAttribute("NoFile", "None");
			}	//if end
			else if(FileName.size()<2)
			{	//else start
				if(!FileName.get(0).equals("")){
				session.setAttribute("FirstFile",FileName.get(0));}
				//System.out.println("=======FileName.get(0)===="+FileName.get(0));
			}
			else if(FileName.size()<3)
			{	//else start
				if(!FileName.get(0).equals("")){
				session.setAttribute("FirstFile",FileName.get(0));}
				//System.out.println("=======FileName.get(0)===="+FileName.get(0));
				if(!FileName.get(1).equals("")){
				session.setAttribute("SecondFile",FileName.get(1));}
				//System.out.println("=======FileName.get(1)===="+FileName.get(1));
			} //else end
			else if(FileName.size()<4)
			{	//else start
				if(!FileName.get(0).equals("")){
				session.setAttribute("FirstFile",FileName.get(0));}
				//System.out.println("=======FileName.get(0)===="+FileName.get(0));
				if(!FileName.get(1).equals("")){
				session.setAttribute("SecondFile",FileName.get(1));}
				//System.out.println("=======FileName.get(1)===="+FileName.get(1));
				if(!FileName.get(2).equals("")){
				session.setAttribute("ThirdFile",FileName.get(2));}
				//System.out.println("=======FileName.get(2)===="+FileName.get(2));
			} //else end
			else if(FileName.size()<5)
			{	//else start
				if(!FileName.get(0).equals("")){
				session.setAttribute("FirstFile",FileName.get(0));}
				//System.out.println("=======FileName.get(0)===="+FileName.get(0));
				if(!FileName.get(1).equals("")){
				session.setAttribute("SecondFile",FileName.get(1));}
				//System.out.println("=======FileName.get(1)===="+FileName.get(1));
				if(!FileName.get(2).equals("")){
				session.setAttribute("ThirdFile",FileName.get(2));}
				//System.out.println("=======FileName.get(2)===="+FileName.get(2));
				if(!FileName.get(3).equals("")){
				session.setAttribute("FourthFile",FileName.get(3));}
				//System.out.println("=======FileName.get(3)===="+FileName.get(3));
			} //else end
			else
			{	//else start
				if(!FileName.get(0).equals("")){
				session.setAttribute("FirstFile",FileName.get(0));}
				//System.out.println("=======FileName.get(0)===="+FileName.get(0));
				if(!FileName.get(1).equals("")){
				session.setAttribute("SecondFile",FileName.get(1));}
				//System.out.println("=======FileName.get(1)===="+FileName.get(1));
				if(!FileName.get(2).equals("")){
				session.setAttribute("ThirdFile",FileName.get(2));}
				//System.out.println("=======FileName.get(2)===="+FileName.get(2));
				if(!FileName.get(3).equals("")){
				session.setAttribute("FourthFile",FileName.get(3));}
				//System.out.println("=======FileName.get(3)===="+FileName.get(3));
				if(!FileName.get(4).equals("")){
				session.setAttribute("FifthFile",FileName.get(4));}
				//System.out.println("=======FileName.get(4)===="+FileName.get(4));
			}	//else end
			
			intResponse=LoginBD.validateViewPermissionBD(strLoginUser,strID);
			
			if(intResponse==1)
			{
				session.setAttribute("StatusPermission","1");
			}
			System.out.println("==========Response from ViewPermission method============"+intResponse);
			
			System.out.println("=========Posted_BY==========="+strPosted_BY);
			if(strPosted_BY.equalsIgnoreCase("Internal"))
			{	//if start
				session.setAttribute("InternalClose","Close");
			}	//if end
			if(strPosted_BY.equalsIgnoreCase("Customer"))
			{
				session.setAttribute("CustomerClose","Verify");
			}
			// Set the issue view flag to 1
			if(roleType.equalsIgnoreCase("Admin"))
			{	//if start
				ListofIssuesBD.changeIssueFlagValueBD(request.getParameter("id"));
			}	//if end
		} //else if end
		else
		{	//else start
			
			/*session.removeAttribute("IssueDetails1");//removing values from the session as it created in other pages.
			session.removeAttribute("IssueDetails2");*/
			request.setAttribute("InValidIssueId", "Please Enter Valid Issue ID");
			return mapping.findForward("home");// if the IssueDetails1 and IssueDetails2 are empty returning to the home page.
		}	//else end
	
			return mapping.findForward("AssignmentPage");
			
	   }//Method IssueDetails end
	
	/* This method is used to get the information about a particular issue */
	
	public ActionForward IssueDetails1(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
	{//Method IssueDetails1 start
		String strApplicationName;
		String strPosted_BY;
		String strCurrentIssueStatus = null;
		String strIssueDescription = null;
		HttpSession session;
		session=request.getSession(false);
		String roleType=null;
		
		String strGroupName = null;
		String strAssignedEmployeeID = null;
		String strAssignedEmployeeName = null;
		
		ListofIssuesForm Issuesform=(ListofIssuesForm)af;//Creating form object of List of Issues 
		String strIssueId=(String)Issuesform.getIssueId();//retrieving issue id from the form bean
		System.out.println("=========strIssueId=====from home page========="+strIssueId);
		String strID=(String) request.getParameter("id"); //Getting the issues id from the request
		System.out.println("=========strID=====from List of Issues page========="+strID);
		//String strGroupSelected=Issuesform.getGroupname();
		
		session.removeAttribute("InternalClose");
		session.removeAttribute("CustomerClose");
		
		String strParameter=(String) request.getParameter("param"); //Getting the issues id from the request
		System.out.println("=========strParameter============"+strParameter);
		session.setAttribute("CheckParam","strParameter");
		
		ArrayList IssueDetails1=new ArrayList();
		ArrayList IssueDetails2=new ArrayList();
		ArrayList IssueDetails3=new ArrayList();
		ArrayList IssueDetails4=new ArrayList();
		ArrayList Groups=new ArrayList();
		ArrayList GroupEmployee=new ArrayList();
		ArrayList FileName=new ArrayList();

		roleType=(String)session.getAttribute("Role");//Getting the role from the session
		
		System.out.println("===========roleType============"+roleType);
		ArrayList AssignedEmployeeDetails=new ArrayList();
		
		if(strIssueId!=null)
		{	//if start

            /* Getting all information of the particular issue */
			
			IssueDetails1=ListofIssuesBD.IssueDetails1(request,strIssueId);

            //IssueDetails1=ListofIssuesBD.IssueDetails1(request,strIssueId);
			IssueDetails2=ListofIssuesBD.IssueDetails2(strIssueId);
			IssueDetails3=ListofIssuesBD.IssueDetails3(strIssueId);
			IssueDetails4=ListofIssuesBD.IssueDetails4(strIssueId);
			FileName=ListofIssuesBD.getFileAttachment(strIssueId);
			strPosted_BY=ListofIssuesBD.getIssuePostedBy(strIssueId);
			
			if(((IssueDetails1.size()!=0 || IssueDetails2.size()!=0 || IssueDetails3.size()!=0 || IssueDetails4.size()!=0 ) && ((strPosted_BY.equalsIgnoreCase("internal") || strPosted_BY.equalsIgnoreCase("customer"))|| strPosted_BY.equalsIgnoreCase("admin"))|| ((!strPosted_BY.equalsIgnoreCase("internal") || !strPosted_BY.equalsIgnoreCase("customer"))|| !strPosted_BY.equalsIgnoreCase("admin"))&&(!strPosted_BY.equalsIgnoreCase("Invalid")))) 
				{	//if start
					
					//Groups=ListofIssuesBD.getGroups();
					if((session.getAttribute("strAssignedEmployeeID")!=null)&&(session.getAttribute("strAssignedEmployeeName")!=null))
					{	//if start
						session.removeAttribute("strAssignedEmployeeID");
						session.removeAttribute("strAssignedEmployeeName");
					}	//end of if
					
					strGroupName=ListofIssuesBD.getGroupName(strIssueId);
					GroupEmployee=ListofIssuesBD.getGroupEmployees(strIssueId);
					
					if(IssueDetails1.size()>0)
					{
					HashMap hashmap1=(HashMap)IssueDetails1.get(0);
					strCurrentIssueStatus=(String) hashmap1.get("status");
					}
					if(strCurrentIssueStatus!=null)
					{
						if(!strCurrentIssueStatus.equalsIgnoreCase("Open"))
						{	//if start
							
							AssignedEmployeeDetails=ListofIssuesBD.getAssignedEmployeeDetails(strIssueId);
							if(AssignedEmployeeDetails.size()>0)
							{	//if start
								
							strAssignedEmployeeID=(String) AssignedEmployeeDetails.get(0);
							strAssignedEmployeeName=(String) AssignedEmployeeDetails.get(1);
												
							session.setAttribute("strAssignedEmployeeID", strAssignedEmployeeID);
							session.setAttribute("strAssignedEmployeeName", strAssignedEmployeeName);
							}	//if end
						}	//if end
					}
						if(IssueDetails4.size()>0)
						{
						HashMap hashmap2=(HashMap)IssueDetails4.get(0);
						strIssueDescription=(String)hashmap2.get("description");
						}
						Issuesform.setDescription(strIssueDescription);
						
						session.setAttribute("CurrentIssueStatus", strCurrentIssueStatus);
						session.setAttribute("IssueDetails1", IssueDetails1);
						session.setAttribute("IssueDetails2", IssueDetails2);
						session.setAttribute("IssueDetails3", IssueDetails3);
						session.setAttribute("IssueDetails4", IssueDetails4);
						
						session.setAttribute("strAssignedEmployeeID", strAssignedEmployeeID);
						session.setAttribute("strAssignedEmployeeName", strAssignedEmployeeName);
						//session.setAttribute("Groups", Groups);
						session.setAttribute("GroupEmployee", GroupEmployee);
						request.setAttribute("id", strIssueId);		
						session.setAttribute("GroupName",strGroupName);
			
						session.removeAttribute("FirstFile");
						session.removeAttribute("FirstFile");
						session.removeAttribute("SecondFile");
						session.removeAttribute("ThirdFile");
						session.removeAttribute("FourthFile");
						session.removeAttribute("FifthFile");
						
						if(FileName.size()==0)
						{	//if start
						session.setAttribute("NoFile", "None");
						}	//if end

					}	//if end
					
					if(IssueDetails4.size()>0)
					{
					HashMap hashmap2=(HashMap)IssueDetails4.get(0);
					strIssueDescription=(String)hashmap2.get("description");
					}
					Issuesform.setDescription(strIssueDescription);
					
					session.setAttribute("CurrentIssueStatus", strCurrentIssueStatus);
					session.setAttribute("IssueDetails1", IssueDetails1);
					session.setAttribute("IssueDetails2", IssueDetails2);
					session.setAttribute("IssueDetails3", IssueDetails3);
					session.setAttribute("IssueDetails4", IssueDetails4);
					
					session.setAttribute("strAssignedEmployeeID", strAssignedEmployeeID);
					session.setAttribute("strAssignedEmployeeName", strAssignedEmployeeName);
					//session.setAttribute("Groups", Groups);
					session.setAttribute("GroupEmployee", GroupEmployee);
					request.setAttribute("id", strIssueId);		
					session.setAttribute("GroupName",strGroupName);
										
					
					session.removeAttribute("FirstFile");
					session.removeAttribute("SecondFile");
					session.removeAttribute("ThirdFile");
					session.removeAttribute("FourthFile");
					session.removeAttribute("FifthFile");
					
					if(FileName.size()==0)
					{	//if start
					session.setAttribute("NoFile", "None");
					}	//if end
					else if(FileName.size()<2)
					{	//else start
						if(!FileName.get(0).equals("")){
						session.setAttribute("FirstFile",FileName.get(0));}
						//System.out.println("=======FileName.get(0)===="+FileName.get(0));

						else if(FileName.size()<2)
						{	//else start
							if(!FileName.get(0).equals("")){
							session.setAttribute("FirstFile",FileName.get(0));}
							System.out.println("=======FileName.get(0)===="+FileName.get(0));
						}
						else if(FileName.size()<3)
						{	//else start
							if(!FileName.get(0).equals("")){
							session.setAttribute("FirstFile",FileName.get(0));}
							System.out.println("=======FileName.get(0)===="+FileName.get(0));
							if(!FileName.get(1).equals("")){
							session.setAttribute("SecondFile",FileName.get(1));}
							System.out.println("=======FileName.get(1)===="+FileName.get(1));
						}//else end
						else if(FileName.size()<4)
						{	//else start
							if(!FileName.get(0).equals("")){
							session.setAttribute("FirstFile",FileName.get(0));}
							System.out.println("=======FileName.get(0)===="+FileName.get(0));
							if(!FileName.get(1).equals("")){
							session.setAttribute("SecondFile",FileName.get(1));}
							System.out.println("=======FileName.get(1)===="+FileName.get(1));
							if(!FileName.get(2).equals("")){
							session.setAttribute("ThirdFile",FileName.get(2));}
							System.out.println("=======FileName.get(2)===="+FileName.get(2));
						}//else end
						else if(FileName.size()<5)
						{	//else start
							if(!FileName.get(0).equals("")){
							session.setAttribute("FirstFile",FileName.get(0));}
							System.out.println("=======FileName.get(0)===="+FileName.get(0));
							if(!FileName.get(1).equals("")){
							session.setAttribute("SecondFile",FileName.get(1));}
							System.out.println("=======FileName.get(1)===="+FileName.get(1));
							if(!FileName.get(2).equals("")){
							session.setAttribute("ThirdFile",FileName.get(2));}
							System.out.println("=======FileName.get(2)===="+FileName.get(2));
							if(!FileName.get(3).equals("")){
							session.setAttribute("FourthFile",FileName.get(3));}
							System.out.println("=======FileName.get(3)===="+FileName.get(3));
						}//else end
						else
						{	//else start
							if(!FileName.get(0).equals("")){
							session.setAttribute("FirstFile",FileName.get(0));}
							System.out.println("=======FileName.get(0)===="+FileName.get(0));
							if(!FileName.get(1).equals("")){
							session.setAttribute("SecondFile",FileName.get(1));}
							System.out.println("=======FileName.get(1)===="+FileName.get(1));
							if(!FileName.get(2).equals("")){
							session.setAttribute("ThirdFile",FileName.get(2));}
							System.out.println("=======FileName.get(2)===="+FileName.get(2));
							if(!FileName.get(3).equals("")){
							session.setAttribute("FourthFile",FileName.get(3));}
							System.out.println("=======FileName.get(3)===="+FileName.get(3));
							if(!FileName.get(4).equals("")){
							session.setAttribute("FifthFile",FileName.get(4));}
							System.out.println("=======FileName.get(4)===="+FileName.get(4));
						}	//else end	
						System.out.println("==========strPosted_BY==========="+strPosted_BY);
						if(strPosted_BY.equalsIgnoreCase("Internal"))
						{	//if start
							session.setAttribute("InternalClose","Close");
						}	//if end
						if(strPosted_BY.equalsIgnoreCase("Customer"))
						{
							session.setAttribute("CustomerClose","Verify");
						}
						// Set the issue view flag to 1
						if(roleType.equalsIgnoreCase("Admin"))
						{	//if start
							ListofIssuesBD.changeIssueFlagValueBD(request.getParameter("id"));
						}	//if end

						return mapping.findForward("AssignmentPage");// if ArrayList is not empty forwarding to IncidentAssignment.jsp
						//return mapping.getInputForward();   // added by rajashekar							

					
				}//if end
				 
				else
				{	//else start
					
					/*session.removeAttribute("IssueDetails1");//removing values from the session as it created in other pages.
					session.removeAttribute("IssueDetails2");*/
					request.setAttribute("InValidIssueId", "Please Enter Valid Issue ID");
					return mapping.findForward("home");// if the IssueDetails1 and IssueDetails2 are empty returning to the home page.
				}	//else end		
			
		}//if end
		else if(request.getParameter("id")!=null)
        {	//else if start

	 	if((session.getAttribute("strAssignedEmployeeID")!=null)&&(session.getAttribute("strAssignedEmployeeName")!=null))
		{	//if start
				session.removeAttribute("strAssignedEmployeeID");
				session.removeAttribute("strAssignedEmployeeName");
		}	//if end
			

			IssueDetails1=ListofIssuesBD.IssueDetails1(request,strID);
			IssueDetails2=ListofIssuesBD.IssueDetails2(strID);
			IssueDetails3=ListofIssuesBD.IssueDetails3(strID);
			IssueDetails4=ListofIssuesBD.IssueDetails4(strID);
			
			FileName=ListofIssuesBD.getFileAttachment(strID);
			strPosted_BY=ListofIssuesBD.getIssuePostedBy(strID);
			strGroupName=ListofIssuesBD.getGroupName(strID);
			GroupEmployee=ListofIssuesBD.getGroupEmployees(strID);
			
			if(IssueDetails1.size()>0)
			{
			HashMap hashmap1=(HashMap)IssueDetails1.get(0);
			strCurrentIssueStatus=(String) hashmap1.get("status");
			}
			if(strCurrentIssueStatus!=null)
			{
			if(!strCurrentIssueStatus.equalsIgnoreCase("Open"))
			{	//if start
				AssignedEmployeeDetails=ListofIssuesBD.getAssignedEmployeeDetails(strID);
				if(AssignedEmployeeDetails.size()>0)
				{	//if start
				strAssignedEmployeeID=(String) AssignedEmployeeDetails.get(0);
				strAssignedEmployeeName=(String) AssignedEmployeeDetails.get(1);
				
				session.setAttribute("strAssignedEmployeeID", strAssignedEmployeeID);
				session.setAttribute("strAssignedEmployeeName", strAssignedEmployeeName);
				}//if end
			}	//if end
		    }
			if(IssueDetails4.size()>0)
			{
			HashMap hashmap2=(HashMap)IssueDetails4.get(0);
			strIssueDescription=(String)hashmap2.get("description");
			}
			Issuesform.setDescription(strIssueDescription);
			
			session.setAttribute("CurrentIssueStatus", strCurrentIssueStatus);
			
			session.setAttribute("IssueDetails1", IssueDetails1);
			session.setAttribute("IssueDetails2", IssueDetails2);
			session.setAttribute("IssueDetails3", IssueDetails3);
			session.setAttribute("IssueDetails4", IssueDetails4);
			//session.setAttribute("strFileName", strFileName);
			//session.setAttribute("Groups", Groups);
			
			
			session.setAttribute("GroupName",strGroupName);
			session.setAttribute("GroupEmployee", GroupEmployee);
			request.setAttribute("id", strID);		
			
			//If no Uploaded files found then we will display as no files
			
			session.removeAttribute("FirstFile");
			session.removeAttribute("SecondFile");
			session.removeAttribute("ThirdFile");
			session.removeAttribute("FourthFile");
			session.removeAttribute("FifthFile");
			
			if(FileName.size()==0)
			{	//if start
			session.setAttribute("NoFile", "None");
			}	//if end
			else if(FileName.size()<2)
			{	//else start
				if(!FileName.get(0).equals("")){
				session.setAttribute("FirstFile",FileName.get(0));}
				//System.out.println("=======FileName.get(0)===="+FileName.get(0));
			}
			else if(FileName.size()<3)
			{	//else start
				if(!FileName.get(0).equals("")){
				session.setAttribute("FirstFile",FileName.get(0));}
				//System.out.println("=======FileName.get(0)===="+FileName.get(0));
				if(!FileName.get(1).equals("")){
				session.setAttribute("SecondFile",FileName.get(1));}
				//System.out.println("=======FileName.get(1)===="+FileName.get(1));
			}//else end
			else if(FileName.size()<4)
			{	//else start
				if(!FileName.get(0).equals("")){
				session.setAttribute("FirstFile",FileName.get(0));}
				//System.out.println("=======FileName.get(0)===="+FileName.get(0));
				if(!FileName.get(1).equals("")){
				session.setAttribute("SecondFile",FileName.get(1));}
				//System.out.println("=======FileName.get(1)===="+FileName.get(1));
				if(!FileName.get(2).equals("")){
				session.setAttribute("ThirdFile",FileName.get(2));}
				//System.out.println("=======FileName.get(2)===="+FileName.get(2));
			}//else end
			else if(FileName.size()<5)
			{	//else start
				if(!FileName.get(0).equals("")){
				session.setAttribute("FirstFile",FileName.get(0));}
				//System.out.println("=======FileName.get(0)===="+FileName.get(0));
				if(!FileName.get(1).equals("")){
				session.setAttribute("SecondFile",FileName.get(1));}
				//System.out.println("=======FileName.get(1)===="+FileName.get(1));
				if(!FileName.get(2).equals("")){
				session.setAttribute("ThirdFile",FileName.get(2));}
				//System.out.println("=======FileName.get(2)===="+FileName.get(2));
				if(!FileName.get(3).equals("")){
				session.setAttribute("FourthFile",FileName.get(3));}
				//System.out.println("=======FileName.get(3)===="+FileName.get(3));
			}//else end
			else
			{	//else start
				if(!FileName.get(0).equals("")){
				session.setAttribute("FirstFile",FileName.get(0));}
				//System.out.println("=======FileName.get(0)===="+FileName.get(0));
				if(!FileName.get(1).equals("")){
				session.setAttribute("SecondFile",FileName.get(1));}
				//System.out.println("=======FileName.get(1)===="+FileName.get(1));
				if(!FileName.get(2).equals("")){
				session.setAttribute("ThirdFile",FileName.get(2));}
				//System.out.println("=======FileName.get(2)===="+FileName.get(2));
				if(!FileName.get(3).equals("")){
				session.setAttribute("FourthFile",FileName.get(3));}
				//System.out.println("=======FileName.get(3)===="+FileName.get(3));
				if(!FileName.get(4).equals("")){
				session.setAttribute("FifthFile",FileName.get(4));}
				//System.out.println("=======FileName.get(4)===="+FileName.get(4));
			}	//else end			
	
			if(strPosted_BY.equalsIgnoreCase("Internal"))
			{	//if start
				session.setAttribute("InternalClose","Close");
			}	//if end
			if(strPosted_BY.equalsIgnoreCase("Customer"))
			{
				session.setAttribute("CustomerClose","Verify");
			}
			
			// Set the issue view flag to 1
			if(roleType.equalsIgnoreCase("Admin"))
			{	//if start
				ListofIssuesBD.changeIssueFlagValueBD(request.getParameter("id"));
			}	//if end
		}//else if end
			return mapping.findForward("AssignmentPage");	
		
	   }//Method IssueDetails end
	/**
	 * @param mapping
	 * @param af
	 * @param request
	 * @param response
	 * @return
	 */
	
	/* This function is used to assign an issue to a particular employee */
	
	public ActionForward AssignIssue(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
	{	//function AssignIssue start
		
		HttpSession session;
		session=request.getSession(false);
		
		ListofIssuesForm Issuesform=(ListofIssuesForm)af;
		
		int intResult;
		
		boolean MailResponse;
		
		String strContextPath=null;
		String strSeverity=null;
		String strApplicationName=null;
		String strMailReveiverName=null;
		String strIssueTitle=null;
		
		String strGroupSelected=Issuesform.getGroupname();
		String strIssuesID=(String) request.getParameter("id");
		
		String strIssueStatusFrom=request.getParameter("IssueStatusFrom");
		strSeverity=request.getParameter("Severity");
		strIssueTitle=request.getParameter("IssueTitle");
		System.out.println("===========strIssueTitle========="+strIssueTitle);
		strApplicationName=request.getParameter("ApplicationName");
		
		String strAssignedBy=(String) session.getAttribute("user");
		String strAssignTo=Issuesform.getEmployee();
		//System.out.println("==============strAssignTo====in Action Class========="+strAssignTo);
		String strIssuesStatusTo="Assigned";
		String strComments=Issuesform.getComments();
		intResult=ListofIssuesBD.AssignIssue(strIssuesID,strAssignedBy,strIssueStatusFrom,strIssuesStatusTo,strAssignTo,strComments);
		strContextPath=ListofIssuesBD.getContextPath(request);
			
		if(intResult==1)
		{	//if start
			request.setAttribute("action", "Assigned");
			
			MailResponse=ListofIssuesBD.sendMail(strContextPath,strIssuesID,strAssignTo,strComments,strGroupSelected,strAssignedBy,strSeverity,strApplicationName,strIssueTitle);
			//HashMap IssueAssignDetails=ListofIssuesBD.EscalatedMailDetailsBD(strContextPath,strIssuesID,strSeverity,strApplicationName);

			String Severity=ListofIssuesBD.getIssueSeverity(strIssuesID);
			/*if(Severity.equalsIgnoreCase("cri"))
			{*/
				//ListofIssuesBD.generateIssueScheduler(strIssuesID,IssueAssignDetails,strContextPath);
			//}
						
			if(MailResponse==true)
			{	//if start
				strMailReveiverName=ListofIssuesBD.getMailReceiver(strAssignTo);
				request.setAttribute("AsignmentReceiver", strMailReveiverName);
				return mapping.findForward("IssueAssignSuccessPage");
			} 	//if end 
		   else
		   {	//else start
			return mapping.findForward("IssueAssignFailurePage");
	       }	//else end
		}//if end
		else
		{	//else start
			request.setAttribute("Error", "Error");
			return mapping.findForward("IssueAssignFailurePage");
		}	//else end
		//return mapping.findForward("AssignmentPage");
		
	}//function AssignIssue end
	
	/**
	 * @param mapping
	 * @param af
	 * @param request
	 * @param response
	 * @return
	 */
	
	/* This method is used to change the status of an issue */
	
	public ActionForward ChangeIssueStatus(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
	{	//function ChangeIssueStatus start
		
		HttpSession session;
		session=request.getSession(false);
		
		request.removeAttribute("AppOwnerName");
		
		String strContextPath;
		String filePath=getServlet().getServletContext().getRealPath("/")+"ISSUEDETAILS";
		
		ArrayList IssueDetails1=new ArrayList();
		
		ListofIssuesForm Issuesform=(ListofIssuesForm)af;
		
		String strApplicationName=null;
		String strIssueTitle=null;
		String strPostedBy=null;
		
		int intResult;
		boolean MailResponse;
		
		String strIssuesID=(String) request.getParameter("id");
		String strUserID=(String) session.getAttribute("user");//Getting the UserID from the session 
		
		String strRoleType=(String) session.getAttribute("Role");
		//strApplicationName=(String) session.getAttribute("ApplicationName");
		
		strPostedBy=ListofIssuesBD.getIssuePostedBy(strIssuesID);
		System.out.println("=========Change Status Issue Posted By=========="+strPostedBy);
		
		strIssueTitle=request.getParameter("IssueTitle");
		System.out.println("===========strIssueTitle========="+strIssueTitle);
		strApplicationName=request.getParameter("ApplicationName");
		String strIssueStatusFrom=request.getParameter("IssueStatusFrom");
		String strAssignedBy=(String) session.getAttribute("user");
		String strIssuesStatusTo=Issuesform.getIssueststus();
		String strReason=Issuesform.getReason();
		String strAssignTo=(String) session.getAttribute("user");
		strContextPath=ListofIssuesBD.getContextPath(request);
		if(strIssuesStatusTo.equalsIgnoreCase("Closed"))
		{	//if start
			ListofIssuesBD.writeDataToFile(strIssuesID,filePath);
			//IssueDetails2=ListofIssuesBD.IssueDetails2(strIssuesID);
			//IssueDetails3=ListofIssuesBD.IssueDetails3(strIssuesID);
			//ListofIssuesBD.writeData(strIssuesID,IssueDetails2,IssueDetails3,filePath);
		}//if end
		System.out.println("=========Before Change============");
		intResult=ListofIssuesBD.ChangeIssue(strIssuesID,strAssignedBy,strIssueStatusFrom,strIssuesStatusTo,strReason,strRoleType,strAssignTo);
		//System.out.println("===========intResult=========="+intResult);		
		
		if(intResult==1)
		{	//if start
			request.setAttribute("action", "status changed");
			request.setAttribute("statusFrom", strIssueStatusFrom);
			request.setAttribute("statusTo", strIssuesStatusTo);
			if(!strRoleType.equalsIgnoreCase("Admin"))
			{
				//System.out.println("------------In changeStatusSendMail---------------");
				MailResponse=ListofIssuesBD.changeStatusSendMail(strContextPath,strIssuesID,strApplicationName,strIssueStatusFrom,strIssuesStatusTo,strReason,strAssignedBy,strUserID,strRoleType,strIssueTitle,strPostedBy);
				//System.out.println("------------MailResponse---------------"+MailResponse);
			
		   if(MailResponse==true)
			{	//if start
				String strChangeStatusMailReceivers[];
				String strSupervisorName=null;
				String strApplicationOwnerName=null;
				
				strChangeStatusMailReceivers=ListofIssuesBD.getSupervisorMailReceiver(strUserID,strRoleType,strIssuesID,strIssuesStatusTo);
				if(strIssuesStatusTo.equalsIgnoreCase("Rejected"))
				{
					strSupervisorName=strChangeStatusMailReceivers[0];
					System.out.println("=======GroupSupervisorName============"+strSupervisorName);
					request.setAttribute("GroupSupervisorName", strSupervisorName);
				}
				if(strIssuesStatusTo.equalsIgnoreCase("Completed"))
				{
					strSupervisorName=strChangeStatusMailReceivers[0];
					System.out.println("=======GroupSupervisorName============"+strSupervisorName);
					request.setAttribute("GroupSupervisorName", strSupervisorName);
					/*if(strPostedBy.equalsIgnoreCase("Customer"))
					{
					strApplicationOwnerName=strChangeStatusMailReceivers[1];
					System.out.println("=======strApplicationOwnerName============"+strApplicationOwnerName);
					request.setAttribute("ApplicationOwnerName", strApplicationOwnerName);
					}*/
				}
				if(strRoleType.equalsIgnoreCase("Customer"))
				{
					ArrayList arrayList=ListofIssuesBD.getCustomerSendMailBD(strUserID, strRoleType, strIssuesID);
					
					//System.out.println("==========ArrayList Size====="+arrayList.size()); 
					
					String strSupervisorname=(String)arrayList.get(1);
					String strSupportManagerName=(String)arrayList.get(3);
					
					request.setAttribute("SupervisorName", strSupervisorname);
					request.setAttribute("SupportManagerName", strSupportManagerName);
				}
				
				
			 }
			 //if end
			/*else
			{	//else start
				request.setAttribute("Error", "Error");
				return mapping.findForward("IssueAssignFailurePage");
			}	//else end
*/		 } //if end
			return mapping.findForward("IssueAssignSuccessPage");
		}	//if end
		else
		{	//else start
			request.setAttribute("Error", "Error");
			request.setAttribute("ChangeError", "ChangeError");
			return mapping.findForward("IssueAssignFailurePage");
		}	//else end
		
	}//function ChangeIssueStatus end
	
	/**
	 * @param mapping
	 * @param af
	 * @param request
	 * @param response
	 * @return
	 */
	
	/* This method is used to retrieve all the information about the issue and display in the home page */
	
	public ActionForward getSelectedTypeIssues(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
	{	//function getSelectedTypeIssues start
                
		        Logger logger=null;
		        HttpSession session=null;
		        ArrayList list=null;
		        String strIssueType=null;

		  try
		 {	//try start

			    session=request.getSession(false);
			    if(session!=null)
			    {	//if start
			    	
			    	  logger=Logger.getLogger("Admin");
			    	  strIssueType=request.getParameter("issueType");
                      list=ListofIssuesBD.getSpecificIssueList(strIssueType,session);
			    	  session.setAttribute("list",list);
			    	  //System.out.println("===testing------"+strIssueType.valueOf(0).toUpperCase());
			    	  if(strIssueType.equals("accepted")) {
			    		  strIssueType="Accepted";
			    	  }
			    	  else
			    		  if(strIssueType.equals("accepted")) {
				    		  strIssueType="Accepted";
				    	  }
				      else
				      if(strIssueType.equals("rejected")) {
					          strIssueType="Rejected";
					  }
					  else
					   if(strIssueType.equals("completed")) {
					 		  strIssueType="completed";
					  }
					  else
					   if(strIssueType.equals("open")) {
					  		  strIssueType="Open";
					  }
					  else
					  if(strIssueType.equals("assigned")) {
					 		  strIssueType="Assigned";
					  }
					  else
					  if(strIssueType.equals("closed")) {
					   		  strIssueType="Closed";
					  }
					  else
						  if(strIssueType.equalsIgnoreCase("reopen")) {
						   		  strIssueType="Reopened";
						  }				    	  
			    	  //strIssueType=strIssueType.valueOf(0).toUpperCase()+strIssueType.substring(1);
			    	  session.setAttribute("issueTypeSelected",strIssueType);
			      }//if end
			       else
			       {	//else start
			    	     mapping.findForward("errorPage");
			       }	//else end
			    	
			    
		 } //try end
		 catch(Exception exception)
		 {	//catch start
			  
		      logger.info("====Exception in getSelectedTypeIssues action============");
			  logger.error(exception);
		}	//catch end
		    return mapping.getInputForward();
		    
	}  //function getSelectedTypeIssues end.
	
	/*public ActionForward GroupEmployeeDetails(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session;
		session=request.getSession(false);
		
		String strID=(String) request.getParameter("id");
		
		System.out.println("===========strID==========="+strID);
		
		ListofIssuesForm Issuesform=(ListofIssuesForm)af;
		
		String strGroupSelected=Issuesform.getGroupname();
		
		System.out.println("===========Selected Group is=============="+strGroupSelected);
		
		ArrayList GroupEmployee=new ArrayList();
		
		GroupEmployee=ListofIssuesBD.getGroupEmployees(strGroupSelected);
		
		session.setAttribute("GroupEmployee", GroupEmployee);
		
		request.setAttribute("id", strID);
		
		return mapping.findForward("AssignmentPage");
		
	}*/
	
	public ActionForward checkChangeStatus(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
	{
		//HttpSession session=null;
		 //session=request.getSession(false);
		 
		 request.setAttribute("changeStatusFlag", "ChangeStatus");
		return mapping.findForward("AssignmentPage");
		
	}
	public ActionForward checkUserHomePageIssueSearch(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
	{
		 HttpSession session=null;
		 session=request.getSession(false);
		 String strissue=null;
		 //String strID=(String) request.getParameter("id"); //Getting the issues id from the request
		 //System.out.println("----------strID----------"+strID);
		 ListofIssuesForm Issuesform=(ListofIssuesForm)af;//Creating form object of List of Issues 
		 String strIssueId=Issuesform.getIssueId();
		 //System.out.println("----------strIssueID----------"+strIssueId);
		 //System.out.println("--------In checkUserHomePageIssueSearch---------");
		 
		 String strUserID=(String)session.getAttribute("user");
		 String strRoleType=(String) session.getAttribute("Role");//Getting the User Role from the session 
			
		 //System.out.println("----------strUserID--------"+strUserID);//Getting the UserID from the session 
		 int i=ListofIssuesBD.getIssueSearchPageBD(strUserID,strIssueId,strRoleType);
		 //System.out.println("==============i============"+i);
		if(i==1)
		{
			//IssueDetails(mapping, af, request, response);
						
			String strApplicationName;
			String strPosted_BY;
			String strCurrentIssueStatus = null;
			String strIssueDescription = null;
			String roleType=null;
			
			String strGroupName;
			String strAssignedEmployeeID = null;
			String strAssignedEmployeeName = null;
								
			ArrayList IssueDetails1=new ArrayList();
			ArrayList IssueDetails2=new ArrayList();
			ArrayList IssueDetails3=new ArrayList();
			ArrayList IssueDetails4=new ArrayList();
			ArrayList Groups=new ArrayList();
			ArrayList GroupEmployee=new ArrayList();
			ArrayList FileName=new ArrayList();

			roleType=(String)session.getAttribute("Role");//Getting the role from the session
			
			//System.out.println("===========roleType============"+roleType);
			ArrayList AssignedEmployeeDetails=new ArrayList();
			session.removeAttribute("CheckParam");
			session.removeAttribute("InternalClose");
			session.removeAttribute("CustomerClose");
			
			if(strIssueId!=null)
			{	//if start

	            /* Getting all information of the particular issue */
				
				IssueDetails1=ListofIssuesBD.IssueDetails1(request,strIssueId);

	            //IssueDetails1=ListofIssuesBD.IssueDetails1(request,strIssueId);
				IssueDetails2=ListofIssuesBD.IssueDetails2(strIssueId);
				IssueDetails3=ListofIssuesBD.IssueDetails3(strIssueId);
				IssueDetails4=ListofIssuesBD.IssueDetails4(strIssueId);
				FileName=ListofIssuesBD.getFileAttachment(strIssueId);
				strPosted_BY=ListofIssuesBD.getIssuePostedBy(strIssueId);
				
					if(((IssueDetails1.size()!=0 || IssueDetails2.size()!=0 || IssueDetails3.size()!=0 || IssueDetails4.size()!=0 ) && ((strPosted_BY.equalsIgnoreCase("internal") || strPosted_BY.equalsIgnoreCase("customer"))|| strPosted_BY.equalsIgnoreCase("admin"))|| ((!strPosted_BY.equalsIgnoreCase("internal") || !strPosted_BY.equalsIgnoreCase("customer"))|| !strPosted_BY.equalsIgnoreCase("admin"))&&(!strPosted_BY.equalsIgnoreCase("Invalid")))) 
					{	//if start
						
						//Groups=ListofIssuesBD.getGroups();
						if((session.getAttribute("strAssignedEmployeeID")!=null)&&(session.getAttribute("strAssignedEmployeeName")!=null))
						{	//if start
							session.removeAttribute("strAssignedEmployeeID");
							session.removeAttribute("strAssignedEmployeeName");
						}	//end of if
						
						strGroupName=ListofIssuesBD.getGroupName(strIssueId);
						GroupEmployee=ListofIssuesBD.getGroupEmployees(strIssueId);
						
						if(IssueDetails1.size()>0)
						{
						HashMap hashmap1=(HashMap)IssueDetails1.get(0);
						strCurrentIssueStatus=(String) hashmap1.get("status");
						}
						if(strCurrentIssueStatus!=null)
						{
							if(!strCurrentIssueStatus.equalsIgnoreCase("Open"))
							{	//if start
								
								AssignedEmployeeDetails=ListofIssuesBD.getAssignedEmployeeDetails(strIssueId);
								if(AssignedEmployeeDetails.size()>0)
								{	//if start
									
								strAssignedEmployeeID=(String) AssignedEmployeeDetails.get(0);
								strAssignedEmployeeName=(String) AssignedEmployeeDetails.get(1);
													
								session.setAttribute("strAssignedEmployeeID", strAssignedEmployeeID);
								session.setAttribute("strAssignedEmployeeName", strAssignedEmployeeName);
								}	//if end
						     }	//if end
						}
							if(IssueDetails4.size()>0)
							{
							HashMap hashmap2=(HashMap)IssueDetails4.get(0);
							strIssueDescription=(String)hashmap2.get("description");
							}
							Issuesform.setDescription(strIssueDescription);
							
							session.setAttribute("CurrentIssueStatus", strCurrentIssueStatus);
							session.setAttribute("IssueDetails1", IssueDetails1);
							session.setAttribute("IssueDetails2", IssueDetails2);
							session.setAttribute("IssueDetails3", IssueDetails3);
							session.setAttribute("IssueDetails4", IssueDetails4);
							
							session.setAttribute("strAssignedEmployeeID", strAssignedEmployeeID);
							session.setAttribute("strAssignedEmployeeName", strAssignedEmployeeName);
							//session.setAttribute("Groups", Groups);
							session.setAttribute("GroupEmployee", GroupEmployee);
							request.setAttribute("id", strIssueId);		
							session.setAttribute("GroupName",strGroupName);
				
							session.removeAttribute("FirstFile");
							session.removeAttribute("FirstFile");
							session.removeAttribute("SecondFile");
							session.removeAttribute("ThirdFile");
							session.removeAttribute("FourthFile");
							session.removeAttribute("FifthFile");
							
							if(FileName.size()==0)
							{	//if start
							session.setAttribute("NoFile", "None");
							}	//if end					
						//HashMap hashmap2=(HashMap)IssueDetails4.get(0);
						//strIssueDescription=(String)hashmap2.get("description");
						Issuesform.setDescription(strIssueDescription);
						
						session.setAttribute("CurrentIssueStatus", strCurrentIssueStatus);
						session.setAttribute("IssueDetails1", IssueDetails1);
						session.setAttribute("IssueDetails2", IssueDetails2);
						session.setAttribute("IssueDetails3", IssueDetails3);
						session.setAttribute("IssueDetails4", IssueDetails4);
						
						session.setAttribute("strAssignedEmployeeID", strAssignedEmployeeID);
						session.setAttribute("strAssignedEmployeeName", strAssignedEmployeeName);
						//session.setAttribute("Groups", Groups);
						session.setAttribute("GroupEmployee", GroupEmployee);
						request.setAttribute("id", strIssueId);		
						session.setAttribute("GroupName",strGroupName);
											
						
						session.removeAttribute("FirstFile");
						session.removeAttribute("SecondFile");
						session.removeAttribute("ThirdFile");
						session.removeAttribute("FourthFile");
						session.removeAttribute("FifthFile");
						
						if(FileName.size()==0)
						{	//if start
						session.setAttribute("NoFile", "None");
						}	//if end
						else if(FileName.size()<2)
							{	//else start
								if(!FileName.get(0).equals("")){
								session.setAttribute("FirstFile",FileName.get(0));}
								System.out.println("=======FileName.get(0)===="+FileName.get(0));
							}
							else if(FileName.size()<3)
							{	//else start
								if(!FileName.get(0).equals("")){
								session.setAttribute("FirstFile",FileName.get(0));}
								System.out.println("=======FileName.get(0)===="+FileName.get(0));
								if(!FileName.get(1).equals("")){
								session.setAttribute("SecondFile",FileName.get(1));}
								System.out.println("=======FileName.get(1)===="+FileName.get(1));
							}//else end
							else if(FileName.size()<4)
							{	//else start
								if(!FileName.get(0).equals("")){
								session.setAttribute("FirstFile",FileName.get(0));}
								System.out.println("=======FileName.get(0)===="+FileName.get(0));
								if(!FileName.get(1).equals("")){
								session.setAttribute("SecondFile",FileName.get(1));}
								System.out.println("=======FileName.get(1)===="+FileName.get(1));
								if(!FileName.get(2).equals("")){
								session.setAttribute("ThirdFile",FileName.get(2));}
								System.out.println("=======FileName.get(2)===="+FileName.get(2));
							}//else end
							else if(FileName.size()<5)
							{	//else start
								if(!FileName.get(0).equals("")){
								session.setAttribute("FirstFile",FileName.get(0));}
								System.out.println("=======FileName.get(0)===="+FileName.get(0));
								if(!FileName.get(1).equals("")){
								session.setAttribute("SecondFile",FileName.get(1));}
								System.out.println("=======FileName.get(1)===="+FileName.get(1));
								if(!FileName.get(2).equals("")){
								session.setAttribute("ThirdFile",FileName.get(2));}
								System.out.println("=======FileName.get(2)===="+FileName.get(2));
								if(!FileName.get(3).equals("")){
								session.setAttribute("FourthFile",FileName.get(3));}
								System.out.println("=======FileName.get(3)===="+FileName.get(3));
							}//else end
							else
							{	//else start
								if(!FileName.get(0).equals("")){
								session.setAttribute("FirstFile",FileName.get(0));}
								System.out.println("=======FileName.get(0)===="+FileName.get(0));
								if(!FileName.get(1).equals("")){
								session.setAttribute("SecondFile",FileName.get(1));}
								System.out.println("=======FileName.get(1)===="+FileName.get(1));
								if(!FileName.get(2).equals("")){
								session.setAttribute("ThirdFile",FileName.get(2));}
								System.out.println("=======FileName.get(2)===="+FileName.get(2));
								if(!FileName.get(3).equals("")){
								session.setAttribute("FourthFile",FileName.get(3));}
								System.out.println("=======FileName.get(3)===="+FileName.get(3));
								if(!FileName.get(4).equals("")){
								session.setAttribute("FifthFile",FileName.get(4));}
								System.out.println("=======FileName.get(4)===="+FileName.get(4));
							}	//else end	
							System.out.println("==========strPosted_BY==========="+strPosted_BY);
							if(strPosted_BY.equalsIgnoreCase("Internal"))
							{	//if start
								session.setAttribute("InternalClose","Close");
							}	//if end
							if(strPosted_BY.equalsIgnoreCase("Customer"))
							{
								session.setAttribute("CustomerClose","Verify");
							}
							// Set the issue view flag to 1
							if(roleType.equalsIgnoreCase("Admin"))
							{	//if start
								ListofIssuesBD.changeIssueFlagValueBD(request.getParameter("id"));
							}	//if end	
							
							System.out.println("==========Befor If End===========");
							return mapping.findForward("AssignmentPage");
						}
			} //if end
		}
			else
			{	//else start
				request.setAttribute("InValidIssueId", "Please enter a valid Issue ID");
				return mapping.findForward("home");// if the IssueDetails1 and IssueDetails2 are empty returning to the home page.
			}	//else end
		
		return mapping.findForward("AssignmentPage");
	}
	public ActionForward IssueVerified(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session;
		session=request.getSession(false);
		String strIssueTitle;
		String strApplicationName;
		String strContextPath;
		String strApplicationOwnerName;
		String strApplicationOwnerMailID;
		
		boolean MailResponse;
		
		String strIssuesID=(String) request.getParameter("id");
		String strUserID=(String) session.getAttribute("user");//Getting the UserID from the session 
		
		String strRoleType=(String) session.getAttribute("Role");
		
		strIssueTitle=request.getParameter("IssueTitle");
		System.out.println("===========strIssueTitle========="+strIssueTitle);
		strApplicationName=request.getParameter("ApplicationName");
		String strIssueStatusFrom=request.getParameter("IssueStatusFrom");
		String strAssignedBy=(String) session.getAttribute("user");
		
		String strAssignTo=(String) session.getAttribute("user");
		strContextPath=ListofIssuesBD.getContextPath(request);

		MailResponse=ListofIssuesBD.IssueVerified(strContextPath,strIssueTitle,strApplicationName,strIssueStatusFrom,strAssignedBy,strIssuesID,strUserID);
		
		if(MailResponse==true)
		{
		  strApplicationOwnerName=ListofIssuesBD.getApplicationOwnerName(strIssuesID);
		  request.setAttribute("AppOwnerName", strApplicationOwnerName);
		}
		return mapping.findForward("IssueAssignSuccessPage");
	}
	/*public ActionForward ListofIssuesSearch(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session;
		session=request.getSession(false);
		
		ListofIssuesForm form=(ListofIssuesForm)af;
		
		String strLoginUser;
		String strLoginRole;
		
		ArrayList IssuesList=new ArrayList();
		
		strLoginUser=(String) session.getAttribute("user");
		System.out.println("=========Login User in Search==========="+strLoginUser);
		
		strLoginRole=(String) session.getAttribute("Role");
		System.out.println("=========Login User Role in Search==========="+strLoginRole);
		
		IssuesList=ListofIssuesBD.ListofIssuesSearch(form,strLoginUser,strLoginRole);
		
		session.removeAttribute("IssueList");
		session.setAttribute("IssueList", IssuesList);
		
		return mapping.findForward("Issuespage");
		
	}*/
	@Override
	protected Map getKeyMethodMap()
	{
		HashMap methodKeyMap = new HashMap();
		methodKeyMap.put("VIMSApplication.BasePage", "BasePage");
		methodKeyMap.put("VIMSApplication.ListIssues", "ListIssues");
		methodKeyMap.put("VIMSApplication.IssueDetails", "IssueDetails");
		
		//Key added by Geeta
		methodKeyMap.put("VIMSApplication.IssueDetails1", "IssueDetails1");
		methodKeyMap.put("VIMSApplication.AssignIssue", "AssignIssue");
		methodKeyMap.put("VIMSApplication.ChangeIssueStatus", "ChangeIssueStatus");
		methodKeyMap.put("VIMSApplication.viewIssues","getSelectedTypeIssues");
		methodKeyMap.put("VIMSApplication.checkChangeStatus","checkChangeStatus");
		methodKeyMap.put("VIMSApplication.IssueVerified","IssueVerified");
		//Key added by Geeta
		methodKeyMap.put("VIMSApplication.checkUserHomePageIssueSearch","checkUserHomePageIssueSearch");
		
		//methodKeyMap.put("VIMSApplication.ListofIssuesSearch","ListofIssuesSearch");
		//methodKeyMap.put("VIMSApplication.GroupEmployeeDetails", "GroupEmployeeDetails");
		return methodKeyMap;
	}
	
}// Class ListofIssuesDispatchAction end
