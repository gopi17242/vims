/*  File Name : NewIssueBD.java
 *  
 *  copyright (c) 2008 Vertex.All rights reserved.
 *  
 *  Created By : Aditya.p
 *  
 *  Description : This is the BD(BusinessDeligate) class which contains some functions which will be called by the Action Class.
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
package com.vertex.VIMS.test.newissue.BD;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.vertex.VIMS.test.newissue.DAO.*;
import com.vertex.VIMS.test.newissue.form.NewIssueForm;

public class NewIssueBD
{	//class NewIssueBD start

	public static ArrayList ApplicationNames(String strRoleType, String strUserID)
	{	//function ApplicationNames() start
		
		ArrayList AppNames=NewIssueDAO.ApplicationNames(strRoleType,strUserID);	//calling the ApplicationNames() function which is in the NewIssueDAO class
		return AppNames;	//returning the arraylist to the action class
	}	//function ApplicationNames() end.

	public static ArrayList ApplicationsubCategory()
	{	//function ApplicationSubCategory() start
		ArrayList AppSubCategory=NewIssueDAO.ApplicationSubCategory();	//calling the ApplicationSubCategory() function which is in the NewIssueDAO class
		return AppSubCategory;	//returning the arraylist to the action class
	}	//function ApplicationSubCategory() end

	public static ArrayList IssueTypes()
	{	//function IssueTypes() start
		ArrayList IssueType=null;
		try{
			IssueType=NewIssueDAO.IssueTypes();	//calling the IssueTypes() function which is in the NewIssueDAO class
		}catch (Exception e) {
			e.printStackTrace();
		}
		return IssueType;	//returning the arraylist to the action class
	}	//function IssueTypes() end

	/**
	 * @param strSelectedApplication
	 * @return the arrayList subCategoryNames
	 */
	public static ArrayList subCategories(String strSelectedApplication)
	{	//function subCategories() start
		ArrayList subCategoryNames=NewIssueDAO.subCategories(strSelectedApplication);	//calling the subCategories() function which is in the NewIssueDAO class with selected ApplicationName as an argument	
		return subCategoryNames;	//returning the arraylist to the action class
	}	//function subCategories() end

//		public static int AddNewIssue(String strApplicationName, String strApplicationSubCategories, String strTitle, String strReference, String strIssueSeverity, String strDescription, FormFile fileUpload, String filePath)
//		{
//			int intResult=NewIssueDAO.AddNewIssue(strApplicationName,strApplicationSubCategories,strTitle,strReference,strIssueSeverity,strDescription,fileUpload, filePath);
//			return 0;
//		}

		/**
		 * @param newIssueForm
		 * @param filePath
		 * @param strUserID
		 * @return the result value obtained from the database.
		 */
		public static int AddNewIssue(NewIssueForm newIssueForm, String filePath, String strUserID)
		{	//function AddNewIssue() start
			
			int intResult = 0;
			try
			{	//try start
				intResult=NewIssueDAO.AddNewIssue(newIssueForm,filePath,strUserID);//calling the AddNewIssue() function which is in the NewIssueDAO class with formbean class object,filepath,userID as arguments
			}	//try end 
			catch (FileNotFoundException e)
			{	//catch start
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	 //catch end
			catch (IOException e)
			{	//catch start
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //catch end
			catch (ClassNotFoundException e)
			{	//catch start
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //catch end
			catch (SQLException e)
			{	//catch start
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	//catch end
			return intResult;	//returning the result value to the BD(BusinessDeligate) class
		}	//function AddNewIssue() end

		public static String getNewIssueID()
		{	//function getNewIssueID() start
			
			String strNewIssueID;
			
			strNewIssueID=NewIssueDAO.getNewIssueID();//calling the function getNewIssueID() which is in NewIssueDAO class
			
			return strNewIssueID;	//returning the NewIssueID to the action class
		}	//function getNewIssueID() end

		public static boolean NewIssueMail(NewIssueForm newIssueForm,
				String strUserID, String strContextPath, String strNewIssueID,String strRoleType)
		{
			boolean iResponse=NewIssueDAO.NewIssueMail(newIssueForm,strUserID,strContextPath,strNewIssueID,strRoleType);
			return iResponse;
			
		}

		public static String getContext(HttpServletRequest request)
		{
			
			return NewIssueDAO.getContextPath(request);
		}

		public static ArrayList getIssuePriority()
		{
			
			return NewIssueDAO.getIssuePriority();
		}

		public static int getApplicationSLA(String strApplicationSelected, String strIssueSeverity)
		{
			int intHours;
			intHours=NewIssueDAO.getApplicationSLA(strApplicationSelected,strIssueSeverity);
			return intHours;
		}

		public static String[] getNewIssueMailReceivers(String strApplicationName)
		{
			
			return NewIssueDAO.getNewIssueMailReceivers(strApplicationName);
		}

		public static int CheckMailServer()
		{
			return NewIssueDAO.CheckMailServer();
		}

		/*public static void generateIssueScheduler(String strIssueJobName,String strIssueTriggerName, HttpServletRequest request)
		{
			NewIssueDAO.generateIssueScheduler(strIssueJobName,strIssueTriggerName,request);			
		}
*/
}//class NewIssueBD endk
