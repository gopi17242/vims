package com.vertex.VIMS.test.supportcenter.form;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class EditSupportCenterForm extends ActionForm
{
	
	String supportmanagers;
	String supportcenterid;
	String supportcentername;
	String supportcenterlocation;
	String status;
	String modifysupportcenter;
	String selSupCen;
	
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
	public String getSelSupCen() {
		return selSupCen;
	}
	public void setSelSupCen(String selSupCen) {
		this.selSupCen = selSupCen;
	}
	public ActionErrors validate(ActionMapping actionMapping,HttpServletRequest request)	
	{
		ActionErrors actionErrors=new ActionErrors();
		
		/*if(getSupportcenterid()==null||getSupportcenterid().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("SupportCenterIDError", new ActionMessage("Support Center ID is required",false));
			System.out.println("==========In Validate Methods============");
		}*/
		if(getSupportcentername()==null||getSupportcentername().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("SupportCenterNameError", new ActionMessage("Support Center Name is required",false));
			System.out.println("==========In Validate Methods============");
		}
		/*else if(getSupportcentername().length()>0)
		{
			boolean b = Pattern.matches("^[a-zA-Z.: ]+$", supportcentername);
			if(b==false)				 
				actionErrors.add("SupportCenterNameError", new ActionMessage("Support Center Name Should be a valid name",false));
		}*/
		if(getSupportmanagers()==null||getSupportmanagers().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("SupportManagerError", new ActionMessage("Support Center Manager is required",false));
			System.out.println("==========In Validate Methods============");
		}
		if(getSupportcenterlocation()==null||getSupportcenterlocation().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("SupportCenterLocation", new ActionMessage("Support Center Location is required",false));
			System.out.println("==========In Validate Methods============");
		}
		if(getStatus()==null||getStatus().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("StatusError", new ActionMessage("Status is required",false));
			//System.out.println("==========In Validate Methods============");
		}
		/*if(getModifysupportcenter()==null||getModifysupportcenter().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("GetSupportCenterError", new ActionMessage("Please select a Support Center",false));
			System.out.println("==========In Validate Methods============");
		}*/
		
		return actionErrors;
	
	}
	
}
