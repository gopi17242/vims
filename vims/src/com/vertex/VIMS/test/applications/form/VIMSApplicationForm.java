package com.vertex.VIMS.test.applications.form;
 
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
public class VIMSApplicationForm extends ActionForm
{
	String appOwners;
	String appId;
	String appName;
	String appOwner;
	String appOwnerEmail;
	String subCatId;
	String searchKey;
	String appStatus;
	String primCont;
	String primContEmail;
	String supBegDate;
	String supEndDate;
	String empId;
	String []appSpecialists;
	String appSpecComment;
	String []appSubCatName;	
	String applications;	
	String grpId;
	String custId;
	String custName;
	String supportCenter;
	String supportManager;
	String filesUploaded;
	
	//SLA details
	String criticalResponseTime;
	String criticalWarningInterval;	
	String majorResponseTime;
	String majorWarningInterval;
	String minorResponseTime;
	String minorWarningInterval;	
	String criticalResponseTimeHidden;
	String criticalWarningIntervalHidden;	
	String majorResponseTimeHidden;
	String majorWarningIntervalHidden;
	String minorResponseTimeHidden;
	String minorWarningIntervalHidden;
	FormFile transitionFile;
	FormFile transitionFile1;
	FormFile transitionFile2;
	FormFile transitionFile3;
	FormFile technicalFile;
	FormFile technicalFile1;
	FormFile technicalFile2;
	FormFile technicalFile3;
	FormFile file0;
	FormFile file1;
	FormFile file2;
	FormFile file3;
	FormFile file4;
	FormFile file5;
	
	int sendMail;
	String SLACreatedApplication;	
	
	int numOfRecs=0;
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getPrimContEmail() {
		return primContEmail;
	}
	public void setPrimContEmail(String primContEmail) {
		this.primContEmail = primContEmail;
	}
	public String getAppOwnerEmail() {
		return appOwnerEmail;
	}
	public void setAppOwnerEmail(String appOwnerEmail) {
		this.appOwnerEmail = appOwnerEmail;
	}
	
	public String[] getAppSpecialists() {
		return appSpecialists;
	}
	public void setAppSpecialists(String[] appSpecialists) {
		this.appSpecialists = appSpecialists;
	}
	
	public int getNumOfRecs() {
		return numOfRecs;
	}
	public void setNumOfRecs(int numOfRecs) {
		this.numOfRecs = numOfRecs;
	}   
	public String getSupportCenter() {
		return supportCenter;
	}
	public void setSupportCenter(String supportCenter) {
		//System.out.println("========supcen in form===="+supportCenter);
		this.supportCenter = supportCenter;
	}
	public String getSupportManager() { 
		return supportManager;
	}
	public void setSupportManager(String supportManager) {
		this.supportManager = supportManager;
	}
	public FormFile getTransitionFile() {
		return transitionFile;
	}
	public void setTransitionFile(FormFile transitionFile) {
		this.transitionFile = transitionFile;
	}
	public FormFile getTechnicalFile() {
		return technicalFile;
	}
	public void setTechnicalFile(FormFile technicalFile) {
		this.technicalFile = technicalFile;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppOwner() {
		return appOwner;
	}
	public void setAppOwner(String appOwner) {
		this.appOwner = appOwner;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		System.out.println("====apstatus in form"+appStatus); 
		this.appStatus = appStatus;
	}
	public String getPrimCont() {
		System.out.println("primary ciontact in action form is "+primCont); 
		return primCont;
	}
	public void setPrimCont(String primCont) {
		this.primCont = primCont;
	System.out.println("primary ciontact in action form is "+primCont); 
	}
	public String getSupBegDate() {
		return supBegDate;
	}
	public void setSupBegDate(String supBegDate) {
		this.supBegDate = supBegDate;
	}
	public String getSupEndDate() {
		return supEndDate;
	}
	public void setSupEndDate(String supEndDate) {
		this.supEndDate = supEndDate;
	}
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getAppSpecComment() {
		return appSpecComment;
	}
	public void setAppSpecComment(String appSpecComment) {
		this.appSpecComment = appSpecComment;
	}
	public String[] getAppSubCatName() {
		return appSubCatName;
	}
	public void setAppSubCatName(String []appSubCatName) {
		this.appSubCatName = appSubCatName;
	}
	public String getGrpId() {
		return grpId;
	}
	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCriticalResponseTime() {
		return criticalResponseTime;
	}
	public void setCriticalResponseTime(String criticalResponseTime) {
		this.criticalResponseTime = criticalResponseTime;
	}
	public String getCriticalWarningInterval() {
		return criticalWarningInterval;
	}
	public void setCriticalWarningInterval(String criticalWarningInterval) {
		this.criticalWarningInterval = criticalWarningInterval;
	}
	public String getMajorResponseTime() {
		return majorResponseTime;
	}
	public void setMajorResponseTime(String majorResponseTime) {
		this.majorResponseTime = majorResponseTime;
	}
	public String getMajorWarningInterval() {
		return majorWarningInterval;
	}
	public void setMajorWarningInterval(String majorWarningInterval) {
		this.majorWarningInterval = majorWarningInterval;
	}
	public String getMinorResponseTime() {
		return minorResponseTime;
	}
	public void setMinorResponseTime(String minorResponseTime) {
		this.minorResponseTime = minorResponseTime;
	}
	public String getMinorWarningInterval() {
		return minorWarningInterval;
	}
	public void setMinorWarningInterval(String minorWarningInterval) {
		this.minorWarningInterval = minorWarningInterval;
	}
	/*
	 * setter and getter methods for hidden fields in add application page	
	*/
	public String getCriticalResponseTimeHidden() {
		return criticalResponseTimeHidden;
	}
	public void setCriticalResponseTimeHidden(String criticalResponseTimeHidden) {
		this.criticalResponseTimeHidden = criticalResponseTimeHidden;
	}
	public String getCriticalWarningIntervalHidden() {
		return criticalWarningIntervalHidden;
	}
	public void setCriticalWarningIntervalHidden(
			String criticalWarningIntervalHidden) {
		this.criticalWarningIntervalHidden = criticalWarningIntervalHidden;
	}
	public String getMajorResponseTimeHidden() {
		return majorResponseTimeHidden;
	}
	public void setMajorResponseTimeHidden(String majorResponseTimeHidden) {
		this.majorResponseTimeHidden = majorResponseTimeHidden;
	}
	public String getMajorWarningIntervalHidden() {
		return majorWarningIntervalHidden;
	}
	public void setMajorWarningIntervalHidden(String majorWarningIntervalHidden) {
		this.majorWarningIntervalHidden = majorWarningIntervalHidden;
	}
	public String getMinorResponseTimeHidden() {
		return minorResponseTimeHidden;
	}
	public void setMinorResponseTimeHidden(String minorResponseTimeHidden) {
		this.minorResponseTimeHidden = minorResponseTimeHidden;
	}
	public String getMinorWarningIntervalHidden() {
		return minorWarningIntervalHidden;
	}
	public void setMinorWarningIntervalHidden(String minorWarningIntervalHidden) {
		this.minorWarningIntervalHidden = minorWarningIntervalHidden;
	}
	public String getFilesUploaded() {
		return filesUploaded;
	}
	public void setFilesUploaded(String filesUploaded) {
		this.filesUploaded = filesUploaded;
	}
	/*
	 * getter and setter methods for an application which has SLA details
	*/
	public String getSLACreatedApplication() {
		return SLACreatedApplication;
	}
	public void setSLACreatedApplication(String createdApplication) {
		SLACreatedApplication = createdApplication;
	}
	public String getSubCatId() {
		return subCatId;
	}
	public void setSubCatId(String subCatId) {
		this.subCatId = subCatId;
	}
	public String getApplications() {
		return applications;
	}
	public void setApplications(String applications) {
		this.applications = applications;
	}
	public FormFile getTransitionFile1() {
		return transitionFile1;
	}
	public void setTransitionFile1(FormFile transitionFile1) {
		this.transitionFile1 = transitionFile1;
	}
	public FormFile getTransitionFile2() {
		return transitionFile2;
	}
	public void setTransitionFile2(FormFile transitionFile2) {
		this.transitionFile2 = transitionFile2;
	}
	public FormFile getTransitionFile3() {
		return transitionFile3;
	}
	public void setTransitionFile3(FormFile transitionFile3) {
		this.transitionFile3 = transitionFile3;
	}
	public FormFile getTechnicalFile1() {
		return technicalFile1;
	}
	public void setTechnicalFile1(FormFile technicalFile1) {
		this.technicalFile1 = technicalFile1;
	}
	public FormFile getTechnicalFile2() {
		return technicalFile2;
	}
	public void setTechnicalFile2(FormFile technicalFile2) {
		this.technicalFile2 = technicalFile2;
	}
	public FormFile getTechnicalFile3() {
		return technicalFile3;
	}
	public void setTechnicalFile3(FormFile technicalFile3) {
		this.technicalFile3 = technicalFile3;
	}
	public int getSendMail() {
		return sendMail;
	}
	public void setSendMail(int sendMail) {
		this.sendMail = sendMail;
		System.out.println("=====sendmail==="+sendMail); 
	}
	public FormFile getFile0() {
		return file0;
	}
	public void setFile0(FormFile file0) {
		this.file0 = file0;
	}
	public FormFile getFile1() {
		return file1;
	}
	public void setFile1(FormFile file1) {
		this.file1 = file1;
	}
	public FormFile getFile2() {
		return file2;
	}
	public void setFile2(FormFile file2) {
		this.file2 = file2;
	}
	public FormFile getFile3() {
		return file3;
	}
	public void setFile3(FormFile file3) {
		this.file3 = file3;
	}
	public FormFile getFile4() {
		return file4;
	}
	public void setFile4(FormFile file4) {
		this.file4 = file4;
	}
	public FormFile getFile5() {
		return file5;
	}
	public void setFile5(FormFile file5) {
		this.file5 = file5;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getAppOwners() {
		return appOwners;
	}
	public void setAppOwners(String appOwners) {
		this.appOwners = appOwners;
	}
	
	//validation
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{
    
			ActionErrors errors = new ActionErrors();
			
				
				if(appName!=null)
				{
					if(appName.equals(""))
					{
					  errors.add("appName", new ActionMessage("Application name is required",false));
					}
				}
				if(appStatus!=null)
				{
					if(appStatus.equals(""))
					{
					  errors.add("appStatus", new ActionMessage("Applicatio status is required",false));
					}
				}
				if(appOwner!=null)
				{
					if(appOwner.equals(""))
					{
						 errors.add("appOwner", new ActionMessage("Application Owner is required",false));
					}
				}
				if(appOwnerEmail!=null)
				{
					if(appOwnerEmail.equals(""))
					{
						 errors.add("appOwnerEmail", new ActionMessage("Owner's E-mail is required",false));
					}					
					/*else
					{					
						String strEmailID="^[^0-9!@#$%\\^&*()~+=/.><|{}\\[\\]\\\\?:;\"'][a-zA-Z0-9._]+@[a-zA-Z]+\\.[c][o][m]$";
					    boolean b = Pattern.matches(strEmailID,appOwnerEmail); 
					    if(b==false)
					    {
					    	errors.add("appOwnerEmail", new ActionMessage("Please enter a valid Email Id"));
						}				   
		  			}	*/			
				}
				
				/*if(criticalResponseTime!=null)
				{
					if(criticalResponseTime.equals(""))
					{
						 errors.add("criticalResponseTime", new ActionMessage("Critical Response Time is required",false));
					}
				}
				if(criticalWarningInterval!=null)
				{
					if(criticalWarningInterval.equals(""))
					{
						 errors.add("criticalWarningInterval", new ActionMessage("Critical Warning Before is required",false));
					}
				}
				if(majorResponseTime!=null)
				{
					if(majorResponseTime.equals(""))
					{
						 errors.add("majorResponseTime", new ActionMessage("Major Response Time is required",false));
					}
				}
				if(majorWarningInterval!=null)
				{
					if(majorWarningInterval.equals(""))
					{
						 errors.add("majorWarningInterval", new ActionMessage("Major Warning Before is required",false));
					}
				}
				if(minorResponseTime!=null)
				{
					if(minorResponseTime.equals(""))
					{
						 errors.add("minorResponseTime", new ActionMessage("Minor Response Time is required",false));
					}
				}
				if(minorWarningInterval!=null)
				{
					if(minorWarningInterval.equals(""))
					{
						 errors.add("minorWarningInterval", new ActionMessage("Minor Warning Before is required",false));
					}
				}*/
				if((getCriticalResponseTime()!=null) && (getMajorResponseTime()!=null ) && (getMinorResponseTime()!=null ) && (getCriticalWarningInterval()!=null) && (getMajorWarningInterval()!=null ) && (getMinorWarningInterval()!=null ))
				{
					if((getCriticalResponseTime().equals("") || getCriticalResponseTime().trim().length()==0) || (getMajorResponseTime().equals("") || getMajorResponseTime().trim().length()==0) || (getMinorResponseTime().equals("") || getMinorResponseTime().trim().length()==0))
					{
						errors.add("ResTime", new ActionMessage("Response Time cannot be empty",false));
					}
					
					if((getCriticalWarningInterval().equals("") || getCriticalWarningInterval().trim().length()==0) || (getMajorWarningInterval().equals("") || getMajorWarningInterval().trim().length()==0) || (getMinorWarningInterval().equals("") || getMinorWarningInterval().trim().length()==0))
					{ 
						errors.add("WarBefore", new ActionMessage("Warning Before cannot be empty",false));
					}
					
					 if((getCriticalResponseTime().equalsIgnoreCase("0")) || (getMajorResponseTime().equalsIgnoreCase("0")) || (getMinorResponseTime().equalsIgnoreCase("0")))
					{
						errors.add("ResTime", new ActionMessage("Response Time cannot be zero",false));
					}
					 
					
					if((getCriticalWarningInterval().equalsIgnoreCase("0")) || (getMajorWarningInterval().equalsIgnoreCase("0")) || (getMinorWarningInterval().equalsIgnoreCase("0")))
					{
						errors.add("WarBefore", new ActionMessage("Warning Before cannot be zero",false));
					}
					 
						/*int CriRes=Integer.parseInt(getCriticalResponseTime().trim());
						int MajRes=Integer.parseInt(getMajorResponseTime().trim());
						int MinRes=Integer.parseInt(getMinorResponseTime().trim());	
						
						int CriWar=Integer.parseInt(getCriticalWarningInterval().trim());
						int MajWar=Integer.parseInt(getMajorWarningInterval().trim());
						int MinWar=Integer.parseInt(getMinorWarningInterval().trim());	
						
						if((CriWar>=CriRes) ||  (MajWar>=MajRes) || (MinWar>=MinRes))
						{
							errors.add("WarLessThanRes", new ActionMessage("Warning Before must be less than Response Time",false));
						}
					*/
				} 
				
				/*if(primCont!=null)
				{
					if(primCont.equals(""))
					{
							errors.add("primCont", new ActionMessage("Please enter Primary Contact",false));
					}
				}*/
				if(supBegDate!=null)
				{
					if(supBegDate.equals(""))
					{
					  errors.add("supBegDate", new ActionMessage("Start Date is required",false));
					}
				}	
				if(supEndDate!=null)
				{
					if(supEndDate.equals(""))
					{
					  errors.add("supEndDate", new ActionMessage("End Date is required",false));
					}
				}	
				if(supportCenter!=null)
				{
					if(supportCenter.equals(""))
					{
					  errors.add("supportCenter", new ActionMessage("Please select a Support Center",false));
					}
				}				
				if(empId!=null)
				{
					if(empId.equals(""))
					{
					  errors.add("empId", new ActionMessage("Employee ID is required ",false));
					}
				}
				
				/*if(appSpecialists==null)
				{									
					  errors.add("appSpecialists", new ActionMessage("Application Specialist is required",false));
				}
				if(appSubCatName==null)
				{					
					  errors.add("appSubCatName", new ActionMessage("Application Sub-Category is required",false));
				}	*/
				if(grpId==null)
				{
					errors.add("grpId", new ActionMessage("Please Select a Group",false));
				}						
				if(grpId!=null)
				{
					if(grpId.equals(""))
					{
					  errors.add("grpId", new ActionMessage("Please Select a Group",false));
					}
				}				
				if(custId!=null)
				{
					if(custId.equals(""))
					{
						 errors.add("custId", new ActionMessage("Please Select a Customer",false));
					}
				}
				
			return errors;
  }
	
	
	
		
}
