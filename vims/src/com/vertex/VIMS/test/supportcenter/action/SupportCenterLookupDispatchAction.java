/*  File Name : SupportCenterLookupDispatchAction.java
 *  
 *  copyright (c) 2008 Vertex.All rights reserved.
 *  
 *  Created By : Aditya.p
 *  
 *  Description : This is the action class that will be executed when the user clicks the Support Center tab from the menu.
 *  			  After clicking this we will see the page which contains all Support center details and respective
 *                group details .This is the main file for support center tab. In this file we have different methods to add
 *                a new support center, edit the existing support center, add a new group and editing the details of existing group.
 *                  
 *  
 *   Change History:
 * 	  Revision No.			Date		 @author			Description
 *		1.0					23-11-2008   Aditya.p			Initial version
 *
 *   
 *  
 *  			
 * 
 */

package com.vertex.VIMS.test.supportcenter.action;

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

import com.vertex.VIMS.test.ldap.BD.ConfigBD;
import com.vertex.VIMS.test.supportcenter.BD.SupportCenterBD;
import com.vertex.VIMS.test.supportcenter.form.SupportCenterForm;
import com.vertex.VIMS.test.supportcenter.form.SupportCenterNewActionForm;


/**
 * @author aditya.p
 *
 */
public class SupportCenterLookupDispatchAction extends LookupDispatchAction
{	//class SupportCenterLookupDispatchAction start 
	HttpSession session=null;
	
	/**
	 * @param mapping
	 * @param af
	 * @param request
	 * @param response
	 * @return
	 */
	
	/* This function is used to get the support centers and groups from the database */
	
	public ActionForward SupportCenterPage(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
    {//function SupportCenterPage start
		SupportCenterNewActionForm form=(SupportCenterNewActionForm)af;
		session=request.getSession(false);
		
		ArrayList SupportCenterList=new  ArrayList();
		
		String strStatusSelected;
		strStatusSelected=form.getStatusType();
		
		String strLoginUser;
		strLoginUser=(String) session.getAttribute("user");
		
		/*if(strStatusSelected==null || "".equalsIgnoreCase(strStatusSelected))
		{
			strStatusSelected="0";
	        form.setStatusType(strStatusSelected);		
		}
		else
		{
			System.out.println("=======strStatusSelected========"+strStatusSelected);
			form.setStatusType(strStatusSelected);
		}*/
		SupportCenterList=SupportCenterBD.ViewSupportCenters(strLoginUser);
		
		if(SupportCenterList.size()!=0)
		{
			session.setAttribute("SupportCenterList", SupportCenterList);
			return mapping.findForward("BasePage");
	    }
		else
		{
			//session.setAttribute("SupportCenterList", null);
			//request.setAttribute("SupportCenterSearchMessage","No Records Found");
			session.setAttribute("SupportCenterList", SupportCenterList);
			return mapping.findForward("BasePage");
		}
		/*SupportCenterForm form=(SupportCenterForm)af;
	 session=request.getSession(false); 
	 
	 String strPage="MainPage";
	 
	 ArrayList supportcenters=new ArrayList();
	 ArrayList groups=new ArrayList();
	 
	 supportcenters=SupportCenterBD.getActiveSupportCenters();
	 groups=SupportCenterBD.getGroupsDetails(form);
	 session.setAttribute("supportcenters", supportcenters);
	session.setAttribute("group", groups);*/
	//return mapping.findForward("BasePage");
	 
 }//function SupportCenterPage end
 /**
 * @param mapping
 * @param af
 * @param request
 * @param response
 * @return
 */
	/* This function is used to retrieve the manager of the support center and group selected */
	
public ActionForward SupportCenterDetails(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
 {//function SupportCenterDetails start
	 
	 SupportCenterForm form=(SupportCenterForm)af;
	 String strLoginUser=null;
	 
	 session=request.getSession(false);
	 strLoginUser=(String)session.getAttribute("user");
	 ArrayList groups=new ArrayList();
	 ArrayList supportcenters=new ArrayList();
	 
	 String supportcentermanager;
	 
	 groups=SupportCenterBD.getGroupsDetails(form);
	 
	 supportcentermanager=SupportCenterBD.getSupportCenterManager(form);
	 form.setSupportmanager(supportcentermanager);
	 session.setAttribute("group", groups);
	 //request.setAttribute("supportcentermanager", supportcentermanager);
	 supportcenters=SupportCenterBD.getActiveSupportCenters(strLoginUser);
	 //groups=SupportCenterBD.getGroupsDetails();
	session.setAttribute("supportcenters", supportcenters);
	 
	 return mapping.findForward("BasePage");
	 
 }//function SupportCenterDetails end

 /**
 * @param mapping
 * @param af
 * @param request
 * @param response
 * @return
 */

/* This function is used to get the group supervisor name and group member details to display in the support center home page */

public ActionForward groupDetails(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
 {//function groupDetails start
	
	HttpSession session=null;
	String strLoginUser=null; 
	 SupportCenterForm form=(SupportCenterForm)af;
	 
	 session=request.getSession(false);
	
	 ArrayList GroupMembersDetails=new ArrayList();
	 ArrayList supportcenters=new ArrayList();
	
	 strLoginUser=(String)session.getAttribute("user");
	 String GroupSupervisor;
	 
	 GroupMembersDetails=SupportCenterBD.getGroupsMemberDetails(form);
	 GroupSupervisor=SupportCenterBD.getGroupSupervisorName(form);
	 
	 form.setGroupsupervisor(GroupSupervisor);
	 request.setAttribute("GroupMembersDetails", GroupMembersDetails);
	 //request.setAttribute("GroupSupervisor",GroupSupervisor);
	 
	 supportcenters=SupportCenterBD.getActiveSupportCenters(strLoginUser);
	 //groups=SupportCenterBD.getGroupsDetails();
	request.setAttribute("supportcenters", supportcenters);
	 	 
	 return mapping.findForward("BasePage");
	 
 }//function groupDetails end
 

 /**
 * @param mapping
 * @param af
 * @param request
 * @param response
 * @return
 */
public ActionForward GroupsPage(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
 {//function GroupsPage start
	 session=request.getSession(false);
	 String strLoginUser=null;
	 
	 strLoginUser=(String)session.getAttribute("user");
	 ArrayList EmployeeList=new ArrayList();
	 ArrayList supportcenters=new ArrayList();
	 ArrayList SupervisorEmployees=new ArrayList();
	 
	 String strPage="Groups";
	 
	 System.out.println("=============In Groups Page Action=================");
	 EmployeeList=SupportCenterBD.getEmployees();
	 
	 SupervisorEmployees=SupportCenterBD.getHigherDesgEmployees(strPage);
	 
	 session.setAttribute("Supervisors", SupervisorEmployees);
	 session.setAttribute("EmployeeList", EmployeeList);
	 
	 supportcenters=SupportCenterBD.getActiveSupportCenters(strLoginUser);
	 //groups=SupportCenterBD.getGroupsDetails();
	 session.setAttribute("supportcenters", supportcenters);
	
	 return mapping.findForward("GroupsPage");
	 
 }//function GroupsPage end
 
 /**
 * @param mapping
 * @param af
 * @param request
 * @param response
 * @return
 */
public ActionForward checkSupportCenter(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
 {//function checkSupportCenter start
	 String strSupportCenterName=request.getParameter("SupportCenterName");
	 System.out.println("==========strSupportCenterName=========="+strSupportCenterName);
	 boolean result=SupportCenterBD.checkSupportCenterAvailability(strSupportCenterName);
	 try
	 {//try start
			String strMsg="";
			if(result==true)
			{//if start
				strMsg="Name already exists";
			}//if end
			else
			{//else start
				strMsg="Ok";
			}//else end
			response.setContentType("text/xml");        
	        response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			response.getOutputStream().println("<response>");
			response.getOutputStream().println("<result>"+strMsg+"</result>");
			response.getOutputStream().println("</response>"); 
		}//try end
		catch(Exception e)
		{//catch start
			e.printStackTrace();
		}	//catch end	
		return null;
	 
 }//function checkSupportCenter end
 
 /**
 * @param mapping
 * @param af
 * @param request
 * @param response
 * @return
 */
public ActionForward newSupportCenterPage(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
 {//function newSupportCenterPage start
	  String strPage="SupportCenter";
	  
	  String strUserID=null;
	  
	 ArrayList EmployeeList=new ArrayList();
	 ArrayList SupportCenterManagers=new ArrayList();
	 ArrayList Locations=new ArrayList();
	 
	 session=request.getSession(false);
	 strUserID=(String) session.getAttribute("user");
	 
	 EmployeeList=SupportCenterBD.getEmployees();
	 SupportCenterManagers=SupportCenterBD.getHigherDesgEmployees(strPage);
	 Locations=ConfigBD.getLocationDetailsBD("0","0",strUserID);
	 System.out.println("==========Locations in SupportCenter========="+Locations);
	
	 if(Locations!=null)
	 {
		 session.setAttribute("Locations",Locations);
	 }
	 
	 session.setAttribute("EmployeeList", EmployeeList);
	 session.setAttribute("SupportManagers", SupportCenterManagers);
	 
	 
	 return mapping.findForward("newSupportCenterPage");
	 
 }//function newSupportCenterPage end
 /**
 * @param mapping
 * @param af
 * @param request
 * @param response
 * @return
 */
public ActionForward AddSupportCenter(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
 {//function AddSupportCenter start
	
	 SupportCenterNewActionForm form=(SupportCenterNewActionForm)af;
	 		 
	 int intResult;
	 String strLoginUser;
	 session=request.getSession(false);
	 strLoginUser=(String) session.getAttribute("user");
	 intResult=SupportCenterBD.AddNewSupportCenter(strLoginUser,form);
	 if(intResult==0)
	 {//if start
		 request.setAttribute("Success","Support Center added successfully");
		 return SupportCenterPage(mapping,af,request,response);
	 }//if end
	 else
	 {//else start
		 request.setAttribute("Failure","Support Center was not added");
		 return SupportCenterPage(mapping,af,request,response);
	 }//else end
	 //return mapping.findForward("newSupportCenterPage"); 
 }//function AddSupportCenter end
 
 /**
 * @param mapping
 * @param af
 * @param request
 * @param response
 * @return
 */
public ActionForward checkGroup(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
 {//function checkGroup start
	 String strGroupName=request.getParameter("GroupName");
	 boolean result=SupportCenterBD.checkGroupAvailability(strGroupName);
	 try
	 {//try start
			String strMsg="";
			if(result==true)
			{//if start
				strMsg="Name already exists";
			}//if end
			else
			{//else start
				strMsg="Ok";
			}//else end
			response.setContentType("text/xml");        
	        response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			response.getOutputStream().println("<response>");
			response.getOutputStream().println("<result>"+strMsg+"</result>");
			response.getOutputStream().println("</response>"); 
		}//try end
		catch(Exception e)
		{//catch start
			e.printStackTrace();
		}//catch end		
	 return null;
	 
 }//function checkGroup end
 /**
 * @param mapping
 * @param af
 * @param request
 * @param response
 * @return
 */
public ActionForward AddSupportCenterGroup(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
 {//function AddSupportCenterGroup start
	
	 System.out.println("============AddGroup============"); 
	 ArrayList supportcenters=new ArrayList();
	 
	SupportCenterForm form=(SupportCenterForm)af;
	
	int intResult;
	String[] strEmployees;
	String[] emps=form.getSelectDest();
	System.out.println("emps"+emps);
	//strEmployees=form.getSelectDest();
	String strLoginUser=null;
	
	session=request.getSession(false);
	strLoginUser=(String) session.getAttribute("user");
	System.out.println("========LoginUser=========="+strLoginUser);
	
	strEmployees=request.getParameterValues("selectDest");
	System.out.println("============GroupEmployees============"+strEmployees);
	
	for(int i=0;i<strEmployees.length;i++)
	{//for loop start
	System.out.println("======GroupEmployees====="+strEmployees[i]);
	}//for loop end
	
	intResult=SupportCenterBD.addGroup(strLoginUser,form,strEmployees);
	
	supportcenters=SupportCenterBD.getActiveSupportCenters(strLoginUser);
	 //groups=SupportCenterBD.getGroupsDetails();
	request.setAttribute("supportcenters", supportcenters);
		
	if(intResult==0)
	{//if start
		request.setAttribute("Success","Group added successfully");
		return ViewGroups(mapping,af,request,response);
	}//if end
	else
	{//else start
		request.setAttribute("Failure","Error occured while adding group");
		return ViewGroups(mapping,af,request,response);
	}//else end
	
	 //return mapping.findForward("GroupsPage");
	 
 }//function AddSupportCenterGroup end
 /**
 * @param mapping
 * @param af
 * @param request
 * @param response
 * @return
 */
public ActionForward EditGroups(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
 {//function EditGroups start
	 String strPage="Groups";
	 String strLoginUser=null;
	 session=request.getSession(false);
	 
	 ArrayList ExistingGroups=new ArrayList();
	 strLoginUser=(String)session.getAttribute("user");
	 ArrayList Employees=new ArrayList();
	 ArrayList supportcenters=new ArrayList();
	 ArrayList SupervisorEmployees=new ArrayList();
	 
	 ExistingGroups=SupportCenterBD.getExistingGroups();
	 SupervisorEmployees=SupportCenterBD.getHigherDesgEmployees(strPage);
	 
	 
	 //ArrayList GroupEmployees=new ArrayList();
	 
	 //System.out.println("=========ExistingGroups==========="+ExistingGroups);
	 
	 Employees=SupportCenterBD.getEmployees();
	 
	 //GroupEmployees=SupportCenterBD.getEmployees();
	 
	 session.setAttribute("Employee", Employees);
	 session.setAttribute("Supervisors", SupervisorEmployees);
	 session.setAttribute("ExistingGroups", ExistingGroups);
	 
	 supportcenters=SupportCenterBD.getActiveSupportCenters(strLoginUser);
	 //groups=SupportCenterBD.getGroupsDetails();
	session.setAttribute("supportcenters", supportcenters);
	
	 
	// request.setAttribute("GroupEmployees", GroupEmployees);
	 
	 return mapping.findForward("EditGroup");
 }//function EditGroups end
 
 /**
 * @param mapping
 * @param af
 * @param request
 * @param response
 * @return
 */
public ActionForward getGroupDetails(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
 {//function getGroupDetails start
	 String strPage="Groups";
	 
	 HttpSession session;
	 session=request.getSession(false);
	 
	 String strSelectedGroupID=null;
	 String strGroupName=null;
	 String strSupportCenterID=null;
	 String GroupSupervisor=null;
	 String strGroupStatus=null;
	 String strLoginUser=null;
	 
	 strLoginUser=(String)session.getAttribute("user");
	 ArrayList supportcenters=new ArrayList();
	 ArrayList SelectedGroupDetails=new ArrayList();
	 ArrayList employees=new ArrayList();
	 ArrayList GroupEmployees=new ArrayList();
	 ArrayList SupervisorEmployees=new ArrayList();
	 
	 SupportCenterForm form=(SupportCenterForm)af;
	 
	 //strSelectedGroupID=form.getPresentgroups();
	 strSelectedGroupID=request.getParameter("SelectedGroup");
	 
	 System.out.println("=========strSelectedGroupID========="+strSelectedGroupID);
	 
	 SelectedGroupDetails = SupportCenterBD.getSelectedGroupDetails(strSelectedGroupID);
	 
	 GroupEmployees=SupportCenterBD.getGroupEmployees(strSelectedGroupID);
	 employees=SupportCenterBD.getEmployeesNotinGroup(strSelectedGroupID);
	 SupervisorEmployees=SupportCenterBD.getHigherDesgEmployees(strPage);
	 
	 strGroupName=(String) SelectedGroupDetails.get(0);
	 strSupportCenterID=(String) SelectedGroupDetails.get(1);
	 GroupSupervisor=(String) SelectedGroupDetails.get(2);
	 strGroupStatus=(String) SelectedGroupDetails.get(3);
	 
	 System.out.println("==========strSupportCenterID=========="+strSupportCenterID);
	 System.out.println("===========Group Status=========="+strGroupStatus);
	 
	 //request.removeAttribute("Employee");
	 session.setAttribute("Employee", employees);
	 session.setAttribute("Supervisors", SupervisorEmployees);
	 supportcenters=SupportCenterBD.getActiveSupportCenters(strLoginUser);
	 //groups=SupportCenterBD.getGroupsDetails();
	 session.setAttribute("supportcenters", supportcenters);
	
	 
	 //request.setAttribute("GroupID",strSelectedGroupID);
	 
	 //request.setAttribute("GroupName",strGroupName);
	 
	 //form.setGroupid(strSelectedGroupID);
	
	form.setGroupname(strGroupName);
	form.setGroupstatus(strGroupStatus);
	form.setGroupsupportcenterid(strSupportCenterID);
	form.setGroupsupervisors(GroupSupervisor);
	
	form.setSelectedGroup(strSelectedGroupID);
	System.out.println("==========form.setSelectedGroup(strSelectedGroupID)========="+form.getSelectedGroup());
	//request.setAttribute("supportCenterID", strSupportCenterID);
	
	//request.setAttribute("GroupSupervisor",GroupSupervisor);
	
	//System.out.println("=======GroupEmployees===in ACTION=========="+GroupEmployees);
	
	session.setAttribute("GroupEmployees",GroupEmployees);
	 
	return mapping.findForward("EditGroup");
	 
 }//function getGroupDetails end
 
 /**
 * @param mapping
 * @param af
 * @param request
 * @param response
 * @return
 */
public ActionForward ModifySupportCenterGroup(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
 {//function ModifySupportCenterGroup start
	 SupportCenterForm form=(SupportCenterForm)af;
	 
	 ArrayList employees=new ArrayList();
	 ArrayList supportcenters=new ArrayList();
	 
	 session=request.getSession(false);
	 
	 String strSelectedGroupID;
	 String strLoginUser=null;
	 strLoginUser=(String) session.getAttribute("user");
	 
	 String[] strGroupMembers; 
	 //ArrayList strGroupMembers=new ArrayList();
	 
	 strGroupMembers=form.getDestemployee();
	 strSelectedGroupID=form.getSelectedGroup();
	 System.out.println("===========strSelectedGroupID============"+strSelectedGroupID);
	 System.out.println("===========strGroupMemnbers============"+strGroupMembers);
	 
	int Response=SupportCenterBD.ModifySupportCenterGroup(strLoginUser,strSelectedGroupID,form);
	
	if(Response==0)
	{//if start
	 request.setAttribute("Success","Group details updated successfully");
	 return ViewGroups(mapping,af,request,response);
	}//if end
	else
	{//else start
		request.setAttribute("Failure","Group details updation failed due to internal error");
		return ViewGroups(mapping,af,request,response);
	}//else end
	
	//employees=SupportCenterBD.getEmployees();
	//request.setAttribute("Employee", employees);
		
	//supportcenters=SupportCenterBD.getActiveSupportCenters();
	 //groups=SupportCenterBD.getGroupsDetails();
	//request.setAttribute("supportcenters", supportcenters);

 }//function ModifySupportCenterGroup end
 
 /**
 * @param mapping
 * @param af
 * @param request
 * @param response
 * @return
 */
public ActionForward EditSupportCenterPage(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
 {//function EditSupportCenterPage start
	 session=request.getSession(false);
	 
	 String strPage="SupportCenter";
	 ArrayList Employees=new ArrayList();
	 ArrayList supportcenters=new ArrayList();
	 ArrayList SupportCenterManagers=new ArrayList();
	 
	 //String strPage="EditSupportCenterPage";
	 Employees=SupportCenterBD.getEmployees();
     SupportCenterManagers=SupportCenterBD.getHigherDesgEmployees(strPage);
	 
	 session.setAttribute("SupportManagers", SupportCenterManagers);	
	 supportcenters=SupportCenterBD.getAllSupportCenters();
	 //groups=SupportCenterBD.getGroupsDetails();
	 session.setAttribute("supportcenters", supportcenters);
	 
	return mapping.findForward("EditSupportCenterPage");
	 
 }//function EditSupportCenterPage end
 /**
 * @param mapping
 * @param af
 * @param request
 * @param response
 * @return
 */
public ActionForward ModifySupportCenterDetails(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
 {//function ModifySupportCenterDetails start
	 session=request.getSession(false);
	 String strPage="SupportCenter";
	 SupportCenterNewActionForm form=(SupportCenterNewActionForm)af;
	 String strUserID=null;
	 
	 strUserID=(String) session.getAttribute("user");
	 
	 ArrayList SupportCenterDetails=new ArrayList();
	 ArrayList supportcenters=new ArrayList();
	 ArrayList SupportCenterManagers=new ArrayList();
	 
	 String strSupportCenterID;
	 String strSupportCenterName;
	 String strLocation;
	 String strManager;
	 String strStatus;
	 
	 ArrayList Employees=new ArrayList();
	 ArrayList Locations=new ArrayList();
	 
	 Employees=SupportCenterBD.getEmployees();
	 SupportCenterManagers=SupportCenterBD.getHigherDesgEmployees(strPage);
	 session.setAttribute("SupportManagers", SupportCenterManagers);
	 Locations=ConfigBD.getLocationDetailsBD("0","0",strUserID);
	
	 if(Locations!=null)
	 {
		 session.setAttribute("Locations",Locations);
	 }
	/* if(Locations==null)
	 {
		 session.setAttribute("Locations",Locations); 
	 }*/
	 //System.out.println("==========SupportCenterManagers=========="+SupportCenterManagers);
	 String strSelectedSupportcenter;
	 
	 strSelectedSupportcenter=request.getParameter("selSupCen");
	 
	 SupportCenterDetails=SupportCenterBD.getSupportCenterDetails(strSelectedSupportcenter);
	 
	 strSupportCenterID=(String) SupportCenterDetails.get(0);
	 strSupportCenterName=(String) SupportCenterDetails.get(1);
	 strLocation=(String) SupportCenterDetails.get(3);
	 System.out.println("=======Locatrion ID=========="+strLocation);
	 strStatus=(String) SupportCenterDetails.get(2);
	 strManager=(String) SupportCenterDetails.get(4);
	 
	 form.setSupportcenterid(strSupportCenterID);
	 form.setSupportcentername(strSupportCenterName);
	 form.setSupportcenterlocation(strLocation);
	 form.setStatus(strStatus);
	 form.setSupportmanagers(strManager);
	 
	 form.setSelSupCen(strSelectedSupportcenter);
	 form.setStrSelectedSupportCenterName(strSupportCenterName);
	
	 //form.setSupportmanagers(strManager);
	 //request.setAttribute("supportcenterid", strSupportCenterID);
	 //request.setAttribute("supportcentername", strSupportCenterName);
	 //request.setAttribute("location", strLocation);
	 //request.setAttribute("Manager", strManager);
	 //request.setAttribute("status", strStatus);
	 
	 
	 return mapping.findForward("EditSupportCenterPage");
	 
 }//function ModifySupportCenterDetails end
 
 /**
 * @param mapping
 * @param af
 * @param request
 * @param response
 * @return
 */
public ActionForward EditSupportCenter(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
 {//function EditSupportCenter start
	 SupportCenterNewActionForm form=(SupportCenterNewActionForm)af;
	 
	 ArrayList Employees=new ArrayList();
	 ArrayList supportcenters=new ArrayList();
	 ArrayList employees=new ArrayList();
	 ArrayList supportcenterList=new ArrayList();
	 String strMessage=null;
	 String strLoginUser=null;
	 
	 session=request.getSession(false);

	 strLoginUser=(String) session.getAttribute("user");
	 String strStatusSelected;
	 strStatusSelected=form.getStatusType();
	 //Employees=SupportCenterBD.getEmployees();
	 //request.setAttribute("Employees", Employees);
	 
	 //supportcenters=SupportCenterBD.getAllSupportCenters();
	 //groups=SupportCenterBD.getGroupsDetails();
	 //request.setAttribute("supportcenters", supportcenters);
	 form.setStatusType(strStatusSelected);
	 int  intMessage=SupportCenterBD.SupportCenterModify(strLoginUser,form);
	 
	 if(intMessage==0)
	 {//if start
		 strMessage="Support Center details updated successfully";
		 request.setAttribute("Success",strMessage);
		 return SupportCenterPage(mapping,af,request,response);
		 //supportcenterList=SupportCenterBD.ViewSupportCenters(strLoginUser,strStatusSelected);
		// session.setAttribute("SupportCenterList", supportcenterList);
		 
	 }//if end
	 if(intMessage==10)
	 {
		 strMessage="This support center has active groups.So unable to modify";
		 //strMessage="Child records : groups found.Can not delete";
		 request.setAttribute("DeleteError",strMessage);
		 return SupportCenterPage(mapping,af,request,response);
	 }
	 else
	 {//else start
		 strMessage="Error occured while updating";
		 request.setAttribute("Failure", strMessage);
		 return SupportCenterPage(mapping,af,request,response);
		 //supportcenterList=SupportCenterBD.ViewSupportCenters(strLoginUser,strStatusSelected);
		 //session.setAttribute("SupportCenterList", supportcenterList);
	 }//else end
	 //return mapping.findForward("BasePage");
 }//function EditSupportCenter end
 
public ActionForward ViewGroups(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
{
	SupportCenterForm form=(SupportCenterForm)af;
	
	session=request.getSession(false);
	
	ArrayList GroupsList=new  ArrayList();
	
	String strStatusSelected;
	strStatusSelected=form.getStatusType();
	
	String strLoginUser;
	strLoginUser=(String) session.getAttribute("user");
	
/*	if(strStatusSelected==null || "".equalsIgnoreCase(strStatusSelected))
	{
		System.out.println("=======strStatusSelected========"+strStatusSelected);
		strStatusSelected="0";
        form.setStatusType(strStatusSelected);		
	}
	else
	{
		System.out.println("=======strStatusSelected========"+strStatusSelected);
		form.setStatusType(strStatusSelected);
	}*/
	GroupsList=SupportCenterBD.ViewGroups(strLoginUser);
	
	session.setAttribute("GroupsList", GroupsList);
	
	return mapping.findForward("ViewGroups");
	
}

public ActionForward SupportCenterSearch(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
{
	System.out.println("==========In Support Center Search ============");
	
	SupportCenterNewActionForm form=(SupportCenterNewActionForm)af;
	session=request.getSession(false);
	
	ArrayList SupportCenterSearchList=new  ArrayList();
	
	String strStatusSelected;
	strStatusSelected=form.getStatusType();
	
	String strLoginUser;
	strLoginUser=(String) session.getAttribute("user");
	
	String strSearchString;
	strSearchString=form.getSearchSupportCenter();
	System.out.println("===========strSearchString=========="+strSearchString);
	
	/*if(strStatusSelected==null || "".equalsIgnoreCase(strStatusSelected))
	{
		strStatusSelected="0";
        form.setStatusType(strStatusSelected);		
	}
	else
	{
		form.setStatusType(strStatusSelected);
	}*/
	//System.out.println("=======strStatusSelected========"+strStatusSelected);
	SupportCenterSearchList=SupportCenterBD.SupportCenterSearch(strLoginUser,strSearchString);
	
	if((SupportCenterSearchList!=null)&& (SupportCenterSearchList.size()>0))
	{
	session.removeAttribute("SupportCenterList");
	session.setAttribute("SupportCenterList", SupportCenterSearchList);
	System.out.println("==========List Search============"+SupportCenterSearchList);
	}
	else
	{
		session.removeAttribute("SupportCenterList");
		request.setAttribute("SupportCenterSearchMessage","No Records Found");
	}
    return mapping.findForward("BasePage");
}

public ActionForward SearchGroup(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
{
    SupportCenterForm form=(SupportCenterForm)af;
	
	session=request.getSession(false);
	
	ArrayList SearchGroupsList=new  ArrayList();
	
	String strStatusSelected;
	String strLoginUser;
	String strSearchGroup;
	
	strStatusSelected=form.getStatusType();
	strLoginUser=(String) session.getAttribute("user");
	strSearchGroup=form.getSearchGroup();
	
	if(strStatusSelected==null || "".equalsIgnoreCase(strStatusSelected))
	{
		System.out.println("=======strStatusSelected========"+strStatusSelected);
		strStatusSelected="0";
        form.setStatusType(strStatusSelected);		
	}
	else
	{
		System.out.println("=======strStatusSelected========"+strStatusSelected);
		form.setStatusType(strStatusSelected);
	}
	SearchGroupsList=SupportCenterBD.SearchGroups(strLoginUser,strStatusSelected,strSearchGroup);
	
	if((SearchGroupsList!=null) && (SearchGroupsList.size()>0))
	{
	    session.removeAttribute("GroupsList");
		session.setAttribute("GroupsList", SearchGroupsList);
		System.out.println("==========GroupsList Search============"+SearchGroupsList);
	}
	else
	{
		session.removeAttribute("GroupsList");
		request.setAttribute("GroupsSearchMessage","No Records Found");
	}
	
	return mapping.findForward("ViewGroups");
	
}
public ActionForward DeleteGroup(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
{
	SupportCenterForm form=(SupportCenterForm)af;
	
	session=request.getSession(false);
	
	String strGroupID=null;
	String strLoginUser=null;;
	
	int intResult;
	
	strGroupID=request.getParameter("GroupID");
	System.out.println("========strGroupID========="+strGroupID);
	strLoginUser=(String) session.getAttribute("user");
	
	intResult=SupportCenterBD.DeleteGroup(strGroupID,strLoginUser);
	if(intResult==0)
	{
		return ViewGroups(mapping,af,request,response);
	}
	return mapping.findForward("ViewGroups");
}

public ActionForward DeleteSupportCenter(ActionMapping mapping,ActionForm af,HttpServletRequest request,HttpServletResponse response)
{
	SupportCenterNewActionForm form=(SupportCenterNewActionForm)af;
	
	session=request.getSession(false);
	
	String strSupportCenterID=null;
	String strLoginUser;
	
	int intResult;
	
	strSupportCenterID=request.getParameter("supportCenterID");
	System.out.println("========strSupportCenterID========="+strSupportCenterID);
	strLoginUser=(String) session.getAttribute("user");
	
	intResult=SupportCenterBD.DeleteSupportCenter(strSupportCenterID,strLoginUser);
	if(intResult==0)
	{
		return SupportCenterPage(mapping,af,request,response);
	}
/*	if(intResult==10)
	{
		request.setAttribute("DeleteError","Child Recods:Groups Found. Unable to delete");
		return mapping.findForward("BasePage");
	}*/
	return mapping.findForward("BasePage");
}
@Override
protected Map getKeyMethodMap()
{
	HashMap methodKeyMap = new HashMap();
	methodKeyMap.put("VIMSApplication.SupportCenterPage", "SupportCenterPage");
	methodKeyMap.put("VIMSApplication.SupportCenterDetails", "SupportCenterDetails");
	methodKeyMap.put("VIMSApplication.groupDetails", "groupDetails");
	methodKeyMap.put("VIMSApplication.GroupsPage", "GroupsPage");
	methodKeyMap.put("VIMSApplication.newSupportCenterPage", "newSupportCenterPage");
	methodKeyMap.put("VIMSApplication.AddSupportCenter", "AddSupportCenter");
	methodKeyMap.put("VIMSApplication.AddSupportCenterGroup", "AddSupportCenterGroup");
	methodKeyMap.put("VIMSApplication.EditGroups", "EditGroups");
	methodKeyMap.put("VIMSApplication.getGroupDetails", "getGroupDetails");
	methodKeyMap.put("VIMSApplication.ModifySupportCenterGroup", "ModifySupportCenterGroup");
	methodKeyMap.put("VIMSApplication.EditSupportCenterPage", "EditSupportCenterPage");
	methodKeyMap.put("VIMSApplication.ModifySupportCenterDetails", "ModifySupportCenterDetails");
	methodKeyMap.put("VIMSApplication.EditSupportCenter", "EditSupportCenter");
	methodKeyMap.put("VIMSApplication.checkSupportCenter", "checkSupportCenter");
	methodKeyMap.put("VIMSApplication.checkGroup", "checkGroup");
	methodKeyMap.put("VIMSApplication.ViewGroups", "ViewGroups");
	methodKeyMap.put("VIMSApplication.SupportCenterSearch", "SupportCenterSearch");
	methodKeyMap.put("VIMSApplication.SearchGroup","SearchGroup");
	methodKeyMap.put("VIMSApplication.DeleteGroup","DeleteGroup");
	methodKeyMap.put("VIMSApplication.DeleteSupportCenter","DeleteSupportCenter");
	return methodKeyMap;
}
}