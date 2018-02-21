package com.vertex.VIMS.test.supportcenter.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.vertex.VIMS.test.supportcenter.BD.SupportCenterBD;

public class SupportCenterForm extends ActionForm
{
	String supportcenter;
	String supportmanager;
	String groupsupervisor;
	String groups;
	String supportmanagers;
	String supportcenterid;
	String supportcentername;
	String supportcenterlocation;
	String status;
	String groupid;
	String groupname;
	String groupsupportcenterid;
	String groupsupervisors;
	String employees;
	String[] selectDest;
	String presentgroups;
	String[] destemployee;
	String modifysupportcenter;
	String groupedit;
	List groupEmployees;
	String groupstatus;
	String statusType;
	String SelectedGroup;
	String searchGroup;
	
	//String supportbegindate;
	//String supportenddate;
	//ArrayList destemployee=new ArrayList();
	
	public String getSupportcenter()
	{
		return supportcenter;
	}

	public void setSupportcenter(String supportcenter)
	{
		this.supportcenter = supportcenter;
	}

	public String getSupportmanager()
	{
		return supportmanager;
	}

	public void setSupportmanager(String supportmanager)
	{
		this.supportmanager = supportmanager;
	}

	public String getGroupsupervisor()
	{
		return groupsupervisor;
	}

	public void setGroupsupervisor(String groupsupervisor)
	{
		this.groupsupervisor = groupsupervisor;
	}

	public String getGroups()
	{
		return groups;
	}

	public void setGroups(String groups)
	{
		this.groups = groups;
	}

	public String getSupportmanagers()
	{
		return supportmanagers;
	}

	public void setSupportmanagers(String supportmanagers)
	{
		this.supportmanagers = supportmanagers;
	}

	public String getSupportcenterid() {
		return supportcenterid;
	}

	public void setSupportcenterid(String supportcenterid)
	{
		this.supportcenterid = supportcenterid;
	}

	public String getSupportcentername() {
		return supportcentername;
	}

	public void setSupportcentername(String supportcentername)
	{
		this.supportcentername = supportcentername;
	}

	public String getSupportcenterlocation()
	{
		return supportcenterlocation;
	}

	public void setSupportcenterlocation(String supportcenterlocation)
	{
		this.supportcenterlocation = supportcenterlocation;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname)
	{
		this.groupname = groupname;
	}

	public String getGroupsupportcenterid()
	{
		return groupsupportcenterid;
	}

	public void setGroupsupportcenterid(String groupsupportcenterid)
	{
		this.groupsupportcenterid = groupsupportcenterid;
	}

	public String getGroupsupervisors()
	{
		return groupsupervisors;
	}

	public void setGroupsupervisors(String groupsupervisors)
	{
		this.groupsupervisors = groupsupervisors;
	}

	public String getEmployees()
	{
		return employees;
	}

	public void setEmployees(String employees)
	{
		this.employees = employees;
	}

	public String[] getSelectDest()
	{
		return selectDest;
	}

	public void setSelectDest(String[] selectDest)
	{
		this.selectDest = selectDest;
	}

	public String getPresentgroups()
	{
		return presentgroups;
	}

	public void setPresentgroups(String presentgroups)
	{
		this.presentgroups = presentgroups;
	}

	public String[] getDestemployee()
	{
		return destemployee;
	}

	public void setDestemployee(String[] destemployee)
	{
		this.destemployee = destemployee;
	}

	public String getModifysupportcenter()
	{
		return modifysupportcenter;
	}

	public void setModifysupportcenter(String modifysupportcenter)
	{
		this.modifysupportcenter = modifysupportcenter;
	}
	public String getGroupedit() {
		return groupedit;
	}

	public void setGroupedit(String groupedit) {
		this.groupedit = groupedit;
	}
	
	public List getGroupEmployees() {
		return groupEmployees;
	}

	public void setGroupEmployees(List groupEmployees) {
		this.groupEmployees = groupEmployees;
	}
	
	public String getGroupstatus() {
		return groupstatus;
	}

	public void setGroupstatus(String groupstatus) {
		this.groupstatus = groupstatus;
	}
	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	public String getSelectedGroup() {
		return SelectedGroup;
	}
	public void setSelectedGroup(String selectedGroup) {
		SelectedGroup = selectedGroup;
	}
	public String getSearchGroup() {
		return searchGroup;
	}

	public void setSearchGroup(String searchGroup) {
		this.searchGroup = searchGroup;
	}
//	public ArrayList getDestemployee() {
//		return destemployee;
//	}
//
//	public void setDestemployee(ArrayList destemployee) {
//		this.destemployee = destemployee;
//	}

	/*public String getSupportbegindate() {
		return supportbegindate;
	}
	public void setSupportbegindate(String supportbegindate) {
		this.supportbegindate = supportbegindate;
	}
	public String getSupportenddate() {
		return supportenddate;
	}
	public void setSupportenddate(String supportenddate) {
		this.supportenddate = supportenddate;
	}*/
	public ActionErrors validate(ActionMapping actionMapping,HttpServletRequest request)	
	{
		ActionErrors actionErrors=new ActionErrors();
		
		if(getGroupedit()!=null && getGroupedit().equalsIgnoreCase("GroupEdit"))
		{ 
			 String strSelectedGroupID=request.getParameter("SelectedGroup");
			 ArrayList SelectedGroupDetails=SupportCenterBD.getSelectedGroupDetails(strSelectedGroupID);
			 ArrayList GroupEmployees=SupportCenterBD.getGroupEmployees(strSelectedGroupID);
			 
			 System.out.println("=========GroupEmployees======"+GroupEmployees);
			 
			 String strGroupName=(String)SelectedGroupDetails.get(0);
			 String strSupportCenterID=(String) SelectedGroupDetails.get(1);
			 String GroupSupervisor=(String) SelectedGroupDetails.get(2);
			 groupid=strSelectedGroupID;  
			 groupname=strGroupName;
			 presentgroups=strSelectedGroupID;
			 groupsupportcenterid=strSupportCenterID; 
			 groupsupervisors=GroupSupervisor; 
			 
			 
			 setGroupEmployees(GroupEmployees);    
			 groupEmployees=GroupEmployees;
			 
			 int intLength=GroupEmployees.size();
			 
			 String strTemp[]=new String[intLength];
			 for(int i=0;i<GroupEmployees.size();i++)
			 {
				 HashMap hm=(HashMap)GroupEmployees.get(i); 
				 String str=(String)hm.get("name");
				 strTemp[i]=str;
				 
				 System.out.println("======strTemp[i]========="+strTemp[i]);
			 }
			 destemployee=strTemp;
			 			 
			    /*if(getGroupid()==null||getGroupid().equals(""))
				{
					
					actionErrors.add("GroupIDError", new ActionMessage("Group ID is required",false));
				}*/
				if(getGroupname()==null||getGroupname().equals(""))
				{
					
					actionErrors.add("GroupNameError", new ActionMessage("Group Name is required",false));
				}
				/*if(getGroupstatus()==null||getGroupstatus().equals(""))
				{
					
					actionErrors.add("GroupStatusError", new ActionMessage("Status is required",false));
				}*/
				/*else if(getGroupname().length()>0)
				{
					boolean b = Pattern.matches("^[a-zA-Z .:]+$", groupname);
					if(b==false)				 
						actionErrors.add("GroupNameError", new ActionMessage("Group Name Should be a valid name",false));
				}*/
				/*if(getGroupsupportcenterid()==null||getGroupsupportcenterid().equals(""))
				{
					actionErrors.add("SupportCenterIDError", new ActionMessage("Support Center ID is required",false));
				}*/
				
				if(getGroupsupervisors()==null||getGroupsupervisors().equals(""))
				{
					
					actionErrors.add("GroupsupervisorsError", new ActionMessage("Group Supervisor is required",false));
				}
				
				if(getDestemployee()==null||getDestemployee().length==0)
				{
					
					actionErrors.add("GroupEmployeeError", new ActionMessage("Group Employee(s) required",false));
				}
				
				/*if(getGroupedit()!=null)
				{
					if(getPresentgroups()==null||getPresentgroups().equals(""))
					{
						
						actionErrors.add("GroupSelectionError", new ActionMessage("Please select a Group",false));
					}
				}*/
	
		}
		else if(getGroupedit()!=null && getGroupedit().equalsIgnoreCase("NewGroup"))
		{
		 		/*if(getGroupid()==null||getGroupid().equals(""))
				{
					
					actionErrors.add("GroupIDError", new ActionMessage("Group ID is required",false));
				}*/
				if(getGroupname()==null||getGroupname().equals(""))
				{
					
					actionErrors.add("GroupNameError", new ActionMessage("Group Name is required",false));
				}
				if(getGroupstatus()==null||getGroupstatus().equals(""))
				{
					
					actionErrors.add("GroupStatusError", new ActionMessage("Status is required",false));
				}
				/*else if(getGroupname().length()>0)
				{
					boolean b = Pattern.matches("^[a-z A-Z]+", groupname);
					if(b==false)				 
						actionErrors.add("GroupNameError", new ActionMessage("Group Name Should be a valid name",false));
				}*/
				if(getGroupsupportcenterid()==null||getGroupsupportcenterid().equals(""))
				{
					actionErrors.add("SupportCenterIDError", new ActionMessage("Support Center Name is required",false));
				}
				
				if(getGroupsupervisors()==null||getGroupsupervisors().equals(""))
				{
					
					actionErrors.add("GroupsupervisorsError", new ActionMessage("Group Supervisor is required",false));
				}
				
				if(getSelectDest()==null||getSelectDest().length==0)
				{
					
					actionErrors.add("GroupEmployeeError", new ActionMessage("Group Employee(s) required",false));
				}
		}
		else
		{
			/*if(getGroupid()==null||getGroupid().equals(""))
			{
				
				actionErrors.add("GroupIDError", new ActionMessage("Group ID is required",false));
			}*/
			if(getGroupname()==null||getGroupname().equals(""))
			{
				
				actionErrors.add("GroupNameError", new ActionMessage("Group Name is required",false));
			}
			if(getGroupstatus()==null||getGroupstatus().equals(""))
			{
				
				actionErrors.add("GroupStatusError", new ActionMessage("Status is required",false));
			}
			if(getGroupsupportcenterid()==null||getGroupsupportcenterid().equals(""))
			{
				actionErrors.add("SupportCenterIDError", new ActionMessage("Support Center Name is required",false));
			}
			/*else if(getGroupname().length()>0)
			{
				boolean b = Pattern.matches("^[a-z A-Z]+", groupname);
				if(b==false)				 
					actionErrors.add("GroupNameError", new ActionMessage("Group Name Should be a valid name",false));
			}*/			
			if(getGroupsupervisors()==null||getGroupsupervisors().equals(""))
			{
				
				actionErrors.add("GroupsupervisorsError", new ActionMessage("Group Supervisor is required",false));
			}
			
			if(getDestemployee()==null||getDestemployee().length==0)
			{
				
				actionErrors.add("GroupEmployeeError", new ActionMessage("Group Employee(s) required",false));
			}
			
			/*if(getGroupedit()!=null)
			{
				if(getPresentgroups()==null||getPresentgroups().equals(""))
				{
					
					actionErrors.add("GroupSelectionError", new ActionMessage("Please select a Group",false));
				}
			}*/
		}
		
		return actionErrors;
	}
}
