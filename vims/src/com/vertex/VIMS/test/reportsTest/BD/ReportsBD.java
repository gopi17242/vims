package com.vertex.VIMS.test.reportsTest.BD;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.vertex.VIMS.test.reportsTest.DAO.ReportsDAO;
import com.vertex.VIMS.test.reportsTest.form.ApplicationReportsForm;
import com.vertex.VIMS.test.reportsTest.form.IssueReportForm;

public class ReportsBD {

	 public static HashMap getIssueSettingsBD() {
		       Logger logger=null;
		       HashMap settings=null;
		 try {
			   logger=Logger.getLogger("Admin");
			   settings=ReportsDAO.getIssueSettingsDAO();
		 }
		  catch(Exception exception) {
			  
			  logger.info("=======Exception in getIssueSettingsBD ======");
			  logger.error(exception);
		  }
		    return settings;
	 }
	 
	 public static ArrayList genereteIssueReport(HashMap hashMap) {
		 
	       Logger logger=null;
	       ArrayList list=null;
	     try {
		   logger=Logger.getLogger("Admin");
		   list=ReportsDAO.genereteIssueReport(hashMap);
	 }
	  catch(Exception exception) {
		  
		  logger.info("=======Exception in getIssueSettingsBD ======");
		  logger.error(exception);
	  }
	    return list;
	 }
	 
	 /******Employee reports related methods*******/
	 
	 public static ArrayList getEmployeesBD(String iPosition)
	 {
		 return ReportsDAO.getEmployeesDAO(iPosition);
	 }
	 public static ArrayList generateEmployeeReportsBD(ApplicationReportsForm form,String strUserId)
	 {
		 return ReportsDAO.generateEmployeeReportsDAO(form,strUserId);
	 }
	 
	 public static ArrayList getCustomersBD(String strStatus)
	 {
		 return ReportsDAO.getCustomersDAO(strStatus);
	 }
	 public static ArrayList generateCustomerReportsBD(ApplicationReportsForm form,String strLoginId)
	 {
		 return ReportsDAO.generateCustomerReportsDAO(form,strLoginId);
	 }
	 
	 public static ArrayList getOwnersBD(String strCustomerId)
	 {
		 return ReportsDAO.getOwnersDAO(strCustomerId);
	 }
	 public static ArrayList getApplicationsBD(String strStatus)
	 {
		 return ReportsDAO.getCustomersDAO(strStatus);
	 }
	 /**************************end of employee****/
	 
	 /******Customer reports related methods*******/
	 /**************************end of Customer****/
	 
	 
	 public static HashMap getIssueDetailViewBD(String strIssueId,HttpSession session) {
		          HashMap issueDetails=null;  
		 try {
			 issueDetails=ReportsDAO.getIssueDetailViewDAO(strIssueId,session);    
		 } 
		  catch(Exception exception) {
			  System.out.println("========Exception in getIssueDetailViewBD=============");
		  }
		    return issueDetails;
	 }


	public static ArrayList getApplicationNamesBD(String StrStatus,String strLoginId) {	 	
		return ReportsDAO.getApplicationNamesDAO(StrStatus,strLoginId);
	}

	 
	 public static byte[] getIssueExcelReport(ArrayList issueList,String strTargetPath,String strSearchCriteria,String reportType) {
             
		    byte[] targetData=ReportsDAO.generateIssueExcelFileDAO(issueList, strTargetPath,strSearchCriteria,reportType);
		    return targetData;
	 }
	 public static byte[] getIssuePDFReport(ArrayList issueList,String strTargetPath,String strSearchCriteria,String reportType) {
         
		    byte[] targetData=ReportsDAO.getIssuePDFReportDAO(issueList, strTargetPath,strSearchCriteria,reportType);
		    return targetData;
	 }

	public static ArrayList displayApplicationReportsBD(String strStatus,String strApplicationSelected, String strFromDate, String strToDate, HttpSession session, String strViewBy,String strLoginId)
	{
		return ReportsDAO.displayApplicationReportsDAO(strStatus,strApplicationSelected,strFromDate,strToDate,session,strViewBy,strLoginId);
	}

	public static ArrayList getSLAReportsBD(String strstatus,String strSLA, String strFromDate,String strToDate, HttpSession session)
	{
		return ReportsDAO.getSLAReportsDAO(strstatus,strSLA,strFromDate,strToDate,session);
	}

	public static byte[] generateDetailIssueExcelFileBD(HashMap detailIssue,String strTargetPath, String strSearchCriteria, String reportType)
	{		
		return ReportsDAO.generateDetailIssueExcelFileDAO(detailIssue, strTargetPath,strSearchCriteria,reportType);
	}

	public static ArrayList generateSupervisorReportsBD(ApplicationReportsForm form, String strUserId)
	{		
		return ReportsDAO.generateSupervisorReportsDAO(form,strUserId);
	}

	public static ArrayList generateManagerReportsBD(ApplicationReportsForm form, String strUserId)
	{		
		return ReportsDAO.generateManagerReportsDAO(form,strUserId);
	}

	public static ArrayList generateAvailableResourcesReportsBD(String strResourceType,String strRoleType) 
	{		
		return ReportsDAO.generateAvailableResourcesReports(strResourceType,strRoleType);
	}

	public static ArrayList generateAllPositionReportsBD(ApplicationReportsForm form, String strUserId)
	{
		return ReportsDAO.generateAllPositionReportsDAO(form,strUserId);
	}

	
 
}
