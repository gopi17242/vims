
/*
 * Author: santhosh.k
 * Creation date: 11/19/2008
 * File Name : ReportsActionForm.java
 * Description: 
 * 			It is  an Action Form  Bean for Issues in Specific status page.
 * 
*/
package com.vertex.VIMS.test.reports.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ReportsActionForm extends ActionForm
{	
	String chartType;
	String applicationSelected;
	String issueTypeSelected;
	String status;
	String export;
	String imgPath;
	
	
	public String getChartType() {
		return chartType;
	}
	public void setChartType(String chartType) {
		this.chartType = chartType;
	} 
	
	public String getApplicationSelected() {
		return applicationSelected;
	}
	public void setApplicationSelected(String applicationSelected) {
		this.applicationSelected = applicationSelected;
	}
	public String getIssueTypeSelected() {
		return issueTypeSelected;
	}
	public void setIssueTypeSelected(String issueTypeSelected) {
		this.issueTypeSelected = issueTypeSelected;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExport() {
		return export;
	}
	public void setExport(String export) {
		this.export = export;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public ActionErrors validate(ActionMapping actionMapping,HttpServletRequest request)	
	{
		ActionErrors actionErrors=new ActionErrors();
		//System.out.println("========getTitle()========="+getTitle());
		if(getStatus()!=null)
		{
			if(getStatus().equals(""))
			{
				//System.out.println("========getTitle() in IF ========="+getTitle());
				actionErrors.add("statusType", new ActionMessage("Status is required",false));
			}
			
		}
		else
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("statusType", new ActionMessage("Status  is required",false));
		}
		
		if(getChartType()!=null)
		{
			if(getChartType().equals(""))
			{
				//System.out.println("========getTitle() in IF ========="+getTitle());
				actionErrors.add("ChartType", new ActionMessage("Display Type is required",false));
			}
			
		}
		else
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("ChartType", new ActionMessage("Display Type is required",false));
		}
		if(getApplicationSelected()!=null)
		{
			if(getApplicationSelected().equals(""))
			{
				//System.out.println("========getTitle() in IF ========="+getTitle());
				actionErrors.add("ApplicationSelected", new ActionMessage("Please Select an Application",false));
			}
			
		}
		else
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("ApplicationSelected", new ActionMessage("Please Select an Application",false));
		}
		if(getIssueTypeSelected()!=null)
		{
			if(getIssueTypeSelected().equals(""))
			{
				//System.out.println("========getTitle() in IF ========="+getTitle());
				actionErrors.add("IssueTypeSelected", new ActionMessage("Please Select an Issue Type",false));
			}
			
		}
		else
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("IssueTypeSelected", new ActionMessage("Please Select an Issue Type",false));
		}
		return actionErrors;
	}	
}
	
