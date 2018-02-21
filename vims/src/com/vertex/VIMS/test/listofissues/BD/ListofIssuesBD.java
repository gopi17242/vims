/*  File Name : ListofIssuesBD.java
 *  
 *  copyright (c) 2008 Vertex.All rights reserved.
 *  
 *  Created By : Aditya.p
 *  
 *  Description : This is the BD(BusinessDeligate) class which contains some functions which will be called by the Action Class.
 *  			  By using this we can collect the information about the issues,we can assign the issue to a particular person,
 *                we can change the issue status. We are using this file to assign an issue and also to change the issue status.
 *  
 *   Change History:
 * 	  Revision No.			Date		 @author			Description
 *		1.0					16-11-2008   Aditya.p			Initial version
 *   
 *  
 *  			
 * 
 */

package com.vertex.VIMS.test.listofissues.BD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vertex.VIMS.test.listofissues.form.ListofIssuesForm;
import com.vertex.VIMS.test.listofissues.DAO.*;
import org.apache.log4j.Logger;

/**
 * @author aditya.p
 *
 */
public class ListofIssuesBD
{	//class ListofIssuesBD start

	/**
	 * @param issuesform
	 * @param strUserID
	 * @param strRoleType
	 * @return
	 */
	public static ArrayList IssuesList(ListofIssuesForm issuesform,String strUserID,String strRoleType)
	{	//function IssuesList start
		
		ArrayList IssuesList=new ArrayList();
		try{
		IssuesList=ListofIssuesDAO.IssuesList(issuesform,strUserID,strRoleType);
		}catch (Exception e) {
			e.printStackTrace();
		}
			return IssuesList;
	}	//function IssuesList end

	/**
	 * @param request
	 * @param strID
	 * @return
	 */
	
	public static ArrayList IssueDetails1(HttpServletRequest request, String strID)
	{	//function IssueDetails1 start
		
		ArrayList IssueDetails1=new ArrayList();
		
		IssueDetails1=ListofIssuesDAO.IssueDetails1(request,strID);
		
		return IssueDetails1;
	}	//function IssueDetails1 end

	/**
	 * @param strID
	 * @return
	 */
	public static ArrayList IssueDetails2(String strID)
	{	//function IssueDetails2 start
		ArrayList IssueDetails2=new ArrayList();
		
		IssueDetails2=ListofIssuesDAO.IssueDetails2(strID);
		
		return IssueDetails2;
	}	//function IssueDetails2 end

	/**
	 * @param strID
	 * @return
	 */
	public static ArrayList IssueDetails3(String strID)
	{	//function IssueDetails3 start
		
		ArrayList IssueDetails3=new ArrayList();
		
		IssueDetails3=ListofIssuesDAO.IssueDetails3(strID);
		
		return IssueDetails3;
	}	//function IssueDetails3 end

	/**
	 * @param strID
	 * @return
	 */
	public static ArrayList IssueDetails4(String strID)
	{	//function IssueDetails4 start
		ArrayList IssueDetails4=new ArrayList();
		
		IssueDetails4=ListofIssuesDAO.IssueDetails4(strID);
		
		return IssueDetails4;
	}	//function IssueDetails4 end

	/**
	 * @param strID
	 * @return
	 */
	public static ArrayList getFileAttachment(String strID)
	{	//function getFileAttachment start
		ArrayList FileName=new ArrayList();
		
		FileName=ListofIssuesDAO.getFileAttachment(strID);
		
		return FileName;
	}	//function getFileAttachment end

	/**
	 * @return
	 */
	public static ArrayList getGroups()
	{	//function getGroups start
		ArrayList Groups=ListofIssuesDAO.getGroups();
		return Groups;
	}	//function getGroups end

	/**
	 * @param strID
	 * @return
	 */
	public static ArrayList getGroupEmployees(String strID)
	{	//function getGroupEmployees start
		ArrayList GroupEmployees=ListofIssuesDAO.getGroupEmployees(strID);
		return GroupEmployees;
	}	//function getGroupEmployees end

	/**
	 * @param strIssuesID
	 * @param strAssignedBy
	 * @param strIssueStatusFrom
	 * @param strIssuesStatusTo
	 * @param strAssignTo
	 * @param strComments
	 * @return
	 */
	public static int AssignIssue(String strIssuesID, String strAssignedBy,
			String strIssueStatusFrom, String strIssuesStatusTo,
			String strAssignTo, String strComments)
	{	
		//function AssignIssue start
		int intResult=ListofIssuesDAO.AssignIssue(strIssuesID,strAssignedBy,strIssueStatusFrom,strIssuesStatusTo,strAssignTo,strComments);
		return intResult;
	}	//function AssignIssue end

	/**
	 * @param strIssuesID
	 * @param strAssignedBy
	 * @param strIssueStatusFrom
	 * @param strIssuesStatusTo
	 * @param strReason
	 * @param strRoleType
	 * @param strAssignTo 
	 * @return
	 */
	public static int ChangeIssue(String strIssuesID, String strAssignedBy,String strIssueStatusFrom, String strIssuesStatusTo,	String strReason,String strRoleType, String strAssignTo)
	{	//function ChangeIssue start
		
		int intResult=ListofIssuesDAO.ChangeStatus(strIssuesID,strAssignedBy,strIssueStatusFrom,strIssuesStatusTo,strReason,strRoleType,strAssignTo);
		return intResult; 
	}	//function ChangeIssue end

	/**
	 * @param request
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request)
	{	//function getContextPath start
		
		String strContextPath=ListofIssuesDAO.getContextPath(request);
		return strContextPath;
	}	//function getContextPath end

	/**
	 * @param strContextPath
	 * @param strIssuesID
	 * @param strAssignTo
	 * @param strComments
	 * @param strGroupSelected
	 * @param strAssignedBy
	 * @param strSeverity
	 * @param strApplicationName
	 * @param strIssueTitle 
	 * @return
	 */
	public static boolean sendMail(String strContextPath, String strIssuesID, String strAssignTo, String strComments, String strGroupSelected, String strAssignedBy, String strSeverity, String strApplicationName, String strIssueTitle)
	{	//function sendMail start
		boolean Response;
		
		Response= ListofIssuesDAO.sendMail(strContextPath,strIssuesID,strAssignTo,strComments,strGroupSelected,strAssignedBy,strSeverity,strApplicationName,strIssueTitle);
		
		return Response;
	}	//function sendMail end

	/**
	 * @param strContextPath
	 * @param strIssuesID
	 * @param strApplicationName
	 * @param strIssueStatusFrom
	 * @param strIssuesStatusTo
	 * @param strReason
	 * @param strAssignedBy
	 * @param strUserID
	 * @param strRoleType
	 * @param strIssueTitle 
	 * @param strPostedBy 
	 * @return
	 */
	public static boolean changeStatusSendMail(String strContextPath,String strIssuesID, String strApplicationName,String strIssueStatusFrom, String strIssuesStatusTo,String strReason, String strAssignedBy,String strUserID, String strRoleType, String strIssueTitle, String strPostedBy)
	{	//function changeStatusSendMail start
		boolean MailResponse;
		
		//System.out.println("------------In changeStatusSendMail BD---------------");
		MailResponse=ListofIssuesDAO.changeStatusSendMail(strContextPath,strIssuesID,strApplicationName,strIssueStatusFrom,strIssuesStatusTo,strReason,strAssignedBy,strUserID,strRoleType,strIssueTitle,strPostedBy);
		
		return MailResponse;
		
	}	//function changeStatusSendMail end

	/**
	 * @param strIssuesID
	 * @param filePath
	 */
	public static void writeDataToFile(String strIssuesID, String filePath)
	{	//function writeDataToFile start
		try
		{
			ListofIssuesDAO.writeDataToFile(strIssuesID,filePath);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	//function writeDataToFile end
	
	/**
	 * @return
	 */
	public static HashMap getCountofNewIssuesAddedBD(String strLoginUser)
	{	//function getCountofNewIssuesAddedBD start
		      Logger logger=null;
		      HashMap hashMap=null;
		try {
			   logger.getLogger("Admin");
			   hashMap=ListofIssuesDAO.getCountofNewIssuesAddedDAO(strLoginUser);
			  
		} catch(Exception exception){
			
			logger.info("-----Exception in getCountofNewIssuesAdded BD--------");
			logger.error(exception);
		
			return null;
		}
		     return hashMap;
	}	//function getCountofNewIssuesAddedBD end


	/**
	 * @param strIssuesID
	 * @return
	 */
	public static String getIssueSeverity(String strIssuesID)
	{	//function getIssueSeverity start
		
		return ListofIssuesDAO.getIssueSeverity(strIssuesID);
	}	//function getIssueSeverity end

	/**
	 * @param strContextPath
	 * @param strIssuesID
	 * @param strAssignTo
	 * @param strComments
	 * @param strGroupSelected
	 * @param strAssignedBy
	 * @param strSeverity
	 * @param strApplicationName
	 * @return
	 */
	public static HashMap EscalatedMailDetailsBD(String strContextPath,String strIssuesID, String strSeverity, String strApplicationName)
	{	//function EscalatedMailDetailsBD start
		
		return ListofIssuesDAO.EscalatedMailDetailsDAO(strContextPath,  strIssuesID,strSeverity, strApplicationName);
		
	}	//function EscalatedMailDetailsBD end
	/**
	 * @param strIssueId
	 * @return
	 */
	public static String getIssuePostedBy(String strIssueId)
	{	//function getIssuePostedBy start
		return ListofIssuesDAO.getIssuePostedBy(strIssueId);
	}	//function getIssuePostedBy end
	

	/**
	 * @param strIssuesID
	 * @param issueAssignDetails
	 * @param strContextPath
	 */
	public static void generateIssueScheduler(String strIssuesID,HashMap issueAssignDetails, String strContextPath)
	{	//function generateIssueScheduler start
		
		ListofIssuesDAO.generateIssueScheduler(strIssuesID,issueAssignDetails,strContextPath);		
	}	//function generateIssueScheduler end

	/**
	 * @param strIssueId
	 * @return
	 */
	public static String getGroupName(String strIssueId)
	{	//function getGroupName start
		
		return ListofIssuesDAO.getGroupName(strIssueId);
	}	//function getGroupName end

	/**
	 * @param strIssueId
	 * @return
	 */
	public static ArrayList getAssignedEmployeeDetails(String strIssueId)
	{	//function getAssignedEmployeeDetails start
		
		return ListofIssuesDAO.getAssignedEmployeeDetails(strIssueId);
		
	}	//function getAssignedEmployeeDetails end
	
	/*public static String getIssuePostedBy(String strIssueId)
	{
		return ListofIssuesDAO.getIssuePostedBy(strIssueId);
	}
	*/

	/**
	 * @param strIssueId
	 */
	public static void changeIssueFlagValueBD(String strIssueId)
	{	//function changeIssueFlagValueBD start
		
		Logger logger=null;
	   	 try {
	   		     logger=Logger.getLogger("Admin");
	   		     System.out.println("before change Issue Flag in BD clasas");
	   		     ListofIssuesDAO.changeIssueFlagValueDAO(strIssueId);
	   		       System.out.println("before change Issue Flag in BD Class");
	   	     } 
	   	 catch(Exception exception)
	   	 {
	   		 logger.info("-------changeIssueFlagValueDAO-----------");
	   		 logger.error(exception);
	   	 }
    }	//function changeIssueFlagValueBD end

	/**
	 * @param strAssignTo
	 * @return
	 */
	public static String getMailReceiver(String strAssignTo)
	{	//function getMailReceiver start
		
		return ListofIssuesDAO.getMailReceiver(strAssignTo);
	}	//function getMailReceiver end
	/**
	 * @param strUserID
	 * @param strRoleType
	 * @param strIssuesID
	 * @param strIssuesStatusTo 
	 * @return
	 */
	public static String[] getSupervisorMailReceiver(String strUserID, String strRoleType, String strIssuesID, String strIssuesStatusTo)
	{	//function getSupervisorMailReceiver start
		
		return ListofIssuesDAO.getSupervisorMailReceiverDAO(strUserID,strRoleType,strIssuesID,strIssuesStatusTo);
	}	//function getSupervisorMailReceiver end
	/**
	 * @param strUserID
	 * @param strRoleType
	 * @param strIssuesID
	 * @return
	 */
	public static ArrayList getCustomerSendMailBD(String strUserID, String strRoleType, String strIssuesID)
	{	//function getCustomerSendMailBD start
		System.out.println("---------In getCustomerSendMailBD-----------");
		ArrayList arrayList=ListofIssuesDAO.getCustomerSendMailDAO(strUserID, strRoleType, strIssuesID);
		return arrayList;
	}	//function getCustomerSendMailBD end

	public static ArrayList getSpecificIssueList(String strIssueType,HttpSession session) {
		            
		         return ListofIssuesDAO.getSpecificIssueList(strIssueType,session);
	}

	public static ArrayList ListofIssuesSearch(ListofIssuesForm form, String strLoginUser, String strLoginRole)
	{
		
		return ListofIssuesDAO.ListofIssuesSearch(form,strLoginUser,strLoginRole);
	}

	public static int getIssueSearchPageBD(String strUserID, String strIssueID,String strRoleType)
	{
		//TODO Auto-generated method stub
		 return ListofIssuesDAO.getIssueSearchPageDAO(strUserID,strIssueID,strRoleType);
	}

	public static boolean IssueVerified(String strContextPath,
			String strIssueTitle, String strApplicationName,
			String strIssueStatusFrom, String strAssignedBy, String strIssuesID, String strUserID)
	{
		
		return ListofIssuesDAO.IssueVerified(strContextPath,strIssueTitle,strApplicationName,strIssueStatusFrom,strAssignedBy,strIssuesID,strUserID);
	}

	public static String getApplicationOwnerName(String strIssuesID)
	{
		
		return ListofIssuesDAO.getApplicationOwnerName(strIssuesID);
	}
}//class ListofIssuesBD end
