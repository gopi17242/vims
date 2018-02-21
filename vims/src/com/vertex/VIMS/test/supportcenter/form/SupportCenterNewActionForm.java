package com.vertex.VIMS.test.supportcenter.form;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.vertex.VIMS.test.supportcenter.BD.SupportCenterBD;

public class SupportCenterNewActionForm extends ActionForm{
	String supportmanagers;
	String supportcenterid;
	String supportcentername;
	String supportcenterlocation;
	String status;
	String modifysupportcenter;
	String editSupportCenter;
	String supportbegindate;
	String supportenddate;
	String selSupCen;
	String statusType;
	String searchSupportCenter;
	String strSelectedSupportCenterName;
	
	public String getSupportmanagers() {
		return supportmanagers;
	}
	public void setSupportmanagers(String supportmanagers) {
		this.supportmanagers = supportmanagers;
	}
	public String getSupportcenterid() {
		return supportcenterid;
	}
	public void setSupportcenterid(String supportcenterid) {
		this.supportcenterid = supportcenterid;
	}
	public String getSupportcentername() {
		return supportcentername;
	}
	public void setSupportcentername(String supportcentername) {
		this.supportcentername = supportcentername;
	}
	public String getSupportcenterlocation() {
		return supportcenterlocation;
	}
	public void setSupportcenterlocation(String supportcenterlocation) {
		this.supportcenterlocation = supportcenterlocation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getModifysupportcenter() {
		return modifysupportcenter;
	}
	public void setModifysupportcenter(String modifysupportcenter) {
		this.modifysupportcenter = modifysupportcenter;
	}
	public String getEditSupportCenter() {
		return editSupportCenter;
	}
	public void setEditSupportCenter(String editSupportCenter) {
		this.editSupportCenter = editSupportCenter;
	}
	public String getSupportbegindate() {
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
	}
	public String getSelSupCen() {
		return selSupCen;
	}
	public void setSelSupCen(String selSupCen) {
		this.selSupCen = selSupCen;
	}
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	public String getSearchSupportCenter() {
		return searchSupportCenter;
	}
	public void setSearchSupportCenter(String searchSupportCenter) {
		this.searchSupportCenter = searchSupportCenter;
	}
	public String getStrSelectedSupportCenterName() {
		return strSelectedSupportCenterName;
	}
	public void setStrSelectedSupportCenterName(String strSelectedSupportCenterName) {
		this.strSelectedSupportCenterName = strSelectedSupportCenterName;
	}
	
	public ActionErrors validate(ActionMapping actionMapping,HttpServletRequest request)	
	{
		ActionErrors actionErrors=new ActionErrors();
		
		System.out.println("======entered into validate method================");
		if(getEditSupportCenter()!=null && getEditSupportCenter().equalsIgnoreCase("EditSupportCenter"))
		{ 
			String strSelSupCenVal=request.getParameter("selSupCen");
			ArrayList SupportCenterDetails=SupportCenterBD.getSupportCenterDetails(strSelSupCenVal);
						
			String strSupportCenterID=(String) SupportCenterDetails.get(0);
			String strSupportCenterName=(String) SupportCenterDetails.get(1);
			String strLocation=(String) SupportCenterDetails.get(2);
			String strStatus=(String) SupportCenterDetails.get(3);
			String strManager=(String) SupportCenterDetails.get(4);
						
			supportcenterid=strSupportCenterID;
			supportcentername=strSupportCenterName;
			supportcenterlocation=strLocation;
			supportmanagers=strManager;
			status=strStatus;
			
			if((getSupportcentername()!=null)&& !("".equalsIgnoreCase(getSupportcentername())))
			{
				String SupportCenterName=getSupportcentername();
				
				String strSelectedSupportCenterID;
				String strOriginalSupportCenterName;
				
				if(!SupportCenterName.equalsIgnoreCase(getStrSelectedSupportCenterName()))
				{
				    
					boolean result=SupportCenterBD.checkSupportCenterAvailability(SupportCenterName);
				
				 if(result==true)
				 {
					actionErrors.add("SupportCenterNameExists", new ActionMessage("Name already exists",false));
				 }
				}
			}
			 
		}
		else if(getEditSupportCenter()!=null && getEditSupportCenter().equalsIgnoreCase("edit"))
		{
			String strSupportCenterName;
			String strSupportCenterID;
			
			strSupportCenterID=getSelSupCen();
			strSupportCenterName=getSupportcentername();
			
			boolean response=SupportCenterBD.checkSupport(strSupportCenterID,strSupportCenterName);
			
			if(response==false)
			{
				actionErrors.add("SupportCenterNameExists", new ActionMessage("Name already exists",false));
				return actionErrors;
			}
			
			
		}
		else if(getEditSupportCenter()!=null && getEditSupportCenter().equalsIgnoreCase("NewSupportCenter"))
		{
			if((getSupportcentername()!=null)&& !("".equalsIgnoreCase(getSupportcentername())))
			{
				String strSupportCenterName=getSupportcentername();
				boolean result=SupportCenterBD.checkSupportCenterAvailability(strSupportCenterName);
				
				if(result==true)
				{
					actionErrors.add("SupportCenterNameExists", new ActionMessage("Name already exists",false));
				}
			}
		}
				
		/*if(getSupportcenterid()==null||getSupportcenterid().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("SupportCenterIDError", new ActionMessage("Support Center ID is required",false));
		}*/
		if(getSupportcentername()==null||getSupportcentername().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("SupportCenterNameError", new ActionMessage("Support Center Name is required",false));
		}
		/*else if(getSupportcentername().length()>0)
		{
			boolean b = Pattern.matches("^[a-zA-Z .:]+$", supportcentername);
			if(b==false)				 
				actionErrors.add("SupportCenterNameError", new ActionMessage("Support Center Name Should be a valid name",false));
		}*/
		if(getSupportmanagers()==null||getSupportmanagers().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("SupportCenterManagerError", new ActionMessage("Support Center Manager is required",false));
		}
		if(getStatus()==null||getStatus().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("StatusError", new ActionMessage("Status is required",false));
		}
		if(getSupportcenterlocation()==null||getSupportcenterlocation().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("LocationError", new ActionMessage("Location is required",false));
		}
		/*if(getSupportbegindate()==null||getSupportbegindate().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("BeginDateError", new ActionMessage("Support Begin Date required",false));
		}
		if(getSupportenddate()==null||getSupportenddate().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("EndDateError", new ActionMessage("Support End Date required",false));
		}*/
		/*if(getEditSupportCenter().equalsIgnoreCase("edit"))
		{   
			 if(getModifysupportcenter()==null || getModifysupportcenter().equals(""))
			 {
			 //System.out.println("========getTitle() in IF ========="+getTitle());
				actionErrors.add("GetSupportCenterError", new ActionMessage("Please select a Support Center",false));
				System.out.println("==========In Validate Methods============");
			 }
		}
*/
		return actionErrors;
	}
}
