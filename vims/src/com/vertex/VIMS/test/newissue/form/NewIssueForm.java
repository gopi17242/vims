package com.vertex.VIMS.test.newissue.form;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

public class NewIssueForm extends ActionForm
{
	 String applicationName;
	 String applicationSubCategory;
	 String title;
	 String reference;
	 String issueType;
	 String description;
	 String issuePriority;
	
	 FormFile fileUpload;
	 FormFile file0;
	 FormFile file1;
	 FormFile file2;
	 FormFile file3;
	 FormFile file4;
	 
	 FormFile fileUpload1;
	 FormFile fileUpload2;
	 
	public String getapplicationName() {
		//System.out.println("--------------INSIDE getapplicationName()----------"); 
		return applicationName;
	}

	public void setapplicationName(String applicationName) {
		//System.out.println("--------------INSIDE setapplicationName()----------"); 
		this.applicationName = applicationName;
	}

	public String getApplicationSubCategory() {
		return applicationSubCategory;
	}

	public void setApplicationSubCategory(String applicationSubCategory) {
		this.applicationSubCategory = applicationSubCategory;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getreference() {
		return reference;
	}

	public void setreference(String reference) {
		this.reference = reference;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*public FormFile getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}*/
	 
	public FormFile getFileUpload1() {
		return fileUpload1;
	}

	public void setFileUpload1(FormFile fileUpload1) {
		this.fileUpload1 = fileUpload1;
	}

	public FormFile getFileUpload2() {
		return fileUpload2;
	}

	public void setFileUpload2(FormFile fileUpload2) {
		this.fileUpload2 = fileUpload2;
	}
	public String getIssuePriority() {
		return issuePriority;
	}

	public void setIssuePriority(String issuePriority) {
		this.issuePriority = issuePriority;
	}
	public FormFile getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
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
	public ActionErrors validate(ActionMapping actionMapping,HttpServletRequest request)	
	{

		ActionErrors actionErrors=new ActionErrors();
		//System.out.println("========getTitle()========="+getTitle());
		if(getapplicationName()==null||getapplicationName().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("ApplicationError", new ActionMessage("Application Name is required",false));
		}
		/*if(getApplicationSubCategory()==null||getApplicationSubCategory().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("ApplSubCatError", new ActionMessage("Application SubCategory is required",false));
		}*/
		if(getTitle()==null||getTitle().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("TitleError", new ActionMessage("Title is required",false));
		}
		/*else if(getTitle().length()>0)
		{
			boolean b = Pattern.matches("^[a-zA-Z][a-zA-Z0-9.: ]+$", title);
			if(b==false)				 
				actionErrors.add("TitleError", new ActionMessage("Title Should be a valid title",false));
		}*/
		if(getIssueType()==null||getIssueType().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("SeverityError", new ActionMessage("Issue Severity is required",false));
		}
		if(getIssuePriority()==null||getIssuePriority().equals(""))
		{
			//System.out.println("========getTitle() in IF ========="+getTitle());
			actionErrors.add("PriorityError", new ActionMessage("Priority is required",false));
		}
		return actionErrors;
	}
}
