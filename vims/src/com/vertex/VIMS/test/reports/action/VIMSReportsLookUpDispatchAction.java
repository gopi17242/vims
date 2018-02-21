
/*
 * Author: santhosh.k
 * Creation date: 11/27/2008
 * File Name : VIMSReportsLookUpDispatchAction.java
 * Description: This is an action controller for  all the requests to Reports.All the methods in this are called specifically when  the user clicks on the Reports Tab.
 * 			
 * 
*/
package com.vertex.VIMS.test.reports.action;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import com.lowagie.text.pdf.codec.Base64.OutputStream;
import com.vertex.VIMS.test.reports.BD.VIMSReportsBD;
import com.vertex.VIMS.test.reports.DAO.VIMSReportsDAO;
import com.vertex.VIMS.test.reports.ReportProducer.GraphConverter;
import com.vertex.VIMS.test.reports.form.CustomReportsActionForm;
import com.vertex.VIMS.test.reports.form.ReportsActionForm;

import de.laures.cewolf.ChartImage;
import de.laures.cewolf.Configuration;


public class VIMSReportsLookUpDispatchAction extends LookupDispatchAction {
	HttpSession session=null;//declaring instance variable of type HttpSession
	String[] customList={
			"Issue Status","Issue Priority","Issue Severity","Created Date",
			"Closed Date","Application Name","Customer Name","Employee Name","Support Center",
			"Support Manager","Groups","Group Supervisor",
			"Group Members","Purging Flag"};
	
	String[] customIdList={
			"Status","Priority","Severity","Created_Date",
			"Closed_Date","Application","Customer","Employee","Support_Center",
			"Manager","Group","Supervisor",
			"Assigned_To","Purging_Status"};
	
	/* 
	 * This method is called to retrieve SLA details of a selected application as well as the 
	 * number of open, open critical, open major and open minor issues.
	 * 
	*/	
	public ActionForward getApplicationIssuesRecords(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		session=request.getSession(false);
		HashMap hashMap=null;	
		int IssuesCount=0;
		if(session!=null)
		{
			ReportsActionForm ReportsForm = (ReportsActionForm)actionForm; 
			String strChartType=(String)ReportsForm.getChartType();	
			String strStatus=(String)ReportsForm.getStatus();
			String pageId=request.getParameter("pageId");
			System.out.println("page Id is : "+pageId); 
			
			if(pageId!=null)
			{
				strChartType="pie3D";
				ReportsForm.setChartType("pie3D");
				strStatus="active";
				ReportsForm.setStatus("active");
			}
			VIMSReportsBD.setApplicationIssuesRecordsStatus(strStatus);
			HashMap ApplicationsIdAndNames=VIMSReportsDAO.getIssuesInApplicationDAO(strStatus);	 
			int[] IssuesCountList=(int[])ApplicationsIdAndNames.get("IssuesList");
			for(int count=0;count<IssuesCountList.length;count++)
			{	
				IssuesCount=IssuesCount+IssuesCountList[count];
			}			
			if( session.getAttribute("TotalIssuesCount")!=null)
			{
				session.removeAttribute("TotalIssuesCount");
				session.setAttribute("TotalIssuesCount", IssuesCount);
			}
			else
			{
				session.setAttribute("TotalIssuesCount", IssuesCount);
			}
			
			if( session.getAttribute("ApplicationsIdAndNames")!=null)
			{
				session.removeAttribute("ApplicationsIdAndNames");
				session.setAttribute("ApplicationsIdAndNames",ApplicationsIdAndNames);
			}
			else
			{
				session.setAttribute("ApplicationsIdAndNames",ApplicationsIdAndNames);
			}
		   request.setAttribute("strChartType", strChartType);
		   if(strChartType.equalsIgnoreCase("list"))
		   {
			   return actionMapping.findForward("display");
		   }
		   else
		   {
			   return actionMapping.findForward("display");
		   }

		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
		
	}
	/*
	 * This mathod is used to generate excel sheets and PDF files in Issues in Application Page
	*/
	
	public ActionForward generateApplicationIssuesRecords(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		session=request.getSession(false);
		String imageId=null;
		Configuration configuration;
		ChartImage chartImage=null;
		String fileType;
		String strStatus;
		String strChartType;
		String imgPath;
		if(session!=null)
		{	
			ReportsActionForm ReportsForm = (ReportsActionForm)actionForm; 
			imgPath=ReportsForm.getImgPath();
			System.out.println("@@@@@@@@@@@@@@Image path"+imgPath);
			strChartType=ReportsForm.getChartType();
			System.out.println("@@@@@@@@@@@@@@chart type"+strChartType);
			if(imgPath!=null)
			{
				imageId=VIMSReportsBD.getImageId(imgPath.trim()).trim();
			}
			if(strChartType!=null)
			{
				if(strChartType.equalsIgnoreCase("list"))
				{
					imageId="100";
				}
				
			}

			fileType=request.getParameter("export");
			System.out.println("@@@@@@@@@@@@@@Image file type"+fileType);
			HashMap hashMap = new HashMap();
			
			try  
		       {	       	
		    	   HashMap IssuesAndApplicationList=null;	    	   
		    	  IssuesAndApplicationList=(HashMap)session.getAttribute("ApplicationsIdAndNames");
		    	   ServletContext servletContext=this.getServlet().getServletContext();
		    	   configuration=Configuration.getInstance(servletContext);	 
		    	   if(imageId!=null)
		    	   {
		    		   if(imageId.equals("100"))
		    		   {
		    			   chartImage=null;
				    	   System.out.println("Chart Image "+chartImage);
		    		   }
			    	   else
			    	   {
				    	   chartImage=configuration.getStorage().getChartImage(imageId,request); 
				    	   System.out.println("Chart Image "+chartImage);
			    	   }
		    	   }
		    	   hashMap.put("Image",chartImage);
		    	   hashMap.put("ImageId",imageId);
		    	   hashMap.put("IssuesAndApplicationList",IssuesAndApplicationList);
		    	   
		    	   String strTargetpath=VIMSReportsBD.getTargetPath(request,servletContext); 
		    	   VIMSReportsBD.FileType(fileType,response,hashMap,strTargetpath,"All Issues","All Applications");
	          	    
		       }
		        catch(Exception exception)
		        {	        	
		        	exception.printStackTrace();
		        }
			return null;
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
		
	}
	/*This method is used to retrieve issue type based on the application(s) selected
	*/
	public ActionForward getIssueTypes(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		session=request.getSession(false);
		if(session!=null)
		{
			ArrayList arrayList=new ArrayList();
			HashMap hashMap=null;
			ReportsActionForm ReportsForm = (ReportsActionForm)actionForm; 
			
			String strApplicationSelected=request.getParameter("applType"); 
			
			String[] allAppsIssuTypes={"Accepted","Assigned","Closed","Completed","Escalated","Open","Rejected"};
			String[] singleAppsIssuTypes={"All"};
			
			StringBuilder SBIssueTypeID=new StringBuilder();
			StringBuilder SBIssueTypeName=new StringBuilder();
			
			String strIssueTypeID="none";
			String strIssueTypeName="none";
			
			
			if(session.getAttribute("issueTypesList")!=null)
			{
				session.removeAttribute("issueTypesList");
			}
			if(strApplicationSelected!=null && strApplicationSelected.equalsIgnoreCase("allApps"))
			{
				for(int index=0;index<allAppsIssuTypes.length;index++)
				{
					if(index!=(allAppsIssuTypes.length-1))
					{
						SBIssueTypeID=SBIssueTypeID.append(allAppsIssuTypes[index]+";");
						SBIssueTypeName=SBIssueTypeName.append(allAppsIssuTypes[index]+";");
					}
					else
					{
						SBIssueTypeID=SBIssueTypeID.append(allAppsIssuTypes[index]);
						SBIssueTypeName=SBIssueTypeName.append(allAppsIssuTypes[index]);
					}
					
				}
				for(int index=0;index<allAppsIssuTypes.length;index++)
				{
					hashMap=new HashMap();
					hashMap.put("id", allAppsIssuTypes[index]);
					hashMap.put("name", allAppsIssuTypes[index]);
					arrayList.add(hashMap);
				}
				
				session.setAttribute("issueTypesList",arrayList);
				
			}
			else
			{
				for(int index=0;index<singleAppsIssuTypes.length;index++)
				{
					SBIssueTypeID=SBIssueTypeID.append(singleAppsIssuTypes[index]);
					SBIssueTypeName=SBIssueTypeName.append(singleAppsIssuTypes[index]);
				}
				for(int index=0;index<singleAppsIssuTypes.length;index++)
				{
					hashMap=new HashMap();
					hashMap.put("id", singleAppsIssuTypes[index]);
					hashMap.put("name", singleAppsIssuTypes[index]);
					arrayList.add(hashMap);
				}
				
				session.setAttribute("issueTypesList",arrayList);
				
			}
			
			strIssueTypeID=SBIssueTypeID.toString();
			strIssueTypeName=SBIssueTypeName.toString();
			//System.out.println(strIssueTypeID+"===strApplId=====strApplName="+SBIssueTypeName); 
			
			try
			{			
				response.setContentType("text/xml");        
		        response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
				response.getOutputStream().println("<response>");			
				response.getOutputStream().println("<result>" + strIssueTypeID + "</result>");
				response.getOutputStream().println("<result>" + strIssueTypeName + "</result>");
				response.getOutputStream().println("</response>"); 
			}
			catch(Exception e) 
			{
				e.printStackTrace();
			}
			ReportsForm.setApplicationSelected(strApplicationSelected);
			
			return null;
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
		
	}
	/*
	 * This method is called when the user clicks on the Issues in Specific Status
	*/
	
	public ActionForward getSpecificIssuesRecords(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		session=request.getSession(false);
		HashMap SpecificIssuesApplication=null;
		HashMap AllIssuesApplication=null;
		if(session!=null)
		{
			ReportsActionForm ReportsForm = (ReportsActionForm)actionForm; 

			String strChartType=(String)ReportsForm.getChartType();
			String strApplication=(String)ReportsForm.getApplicationSelected();
			String strIssueType=(String)ReportsForm.getIssueTypeSelected();	
			String strStatus=(String)ReportsForm.getStatus();
			System.out.println("Application Status is :"+strStatus);
		
			VIMSReportsBD.setSpecificIssuesInAllApplicationBD(strApplication,strIssueType,strStatus);
			
			AllIssuesApplication=VIMSReportsDAO.getAllIssuesReports(strApplication, strIssueType);
 		    SpecificIssuesApplication=VIMSReportsDAO.getSpecificIssuesInAllApplication(strApplication,strIssueType,strStatus);
			
 		    session.setAttribute("AllissuesLength", AllIssuesApplication);
			
			
	 		    session.setAttribute("SpecificIssuesLength", SpecificIssuesApplication);
				/*
			if(strChartType==null || strChartType.length()==0)
			{
				strChartType="pie3D";
				ReportsForm.setChartType("pie3D");
				
			}*/
			request.setAttribute("strChartType", strChartType);
			request.setAttribute("strIssueType", strIssueType);
			ReportsForm.setIssueTypeSelected(strIssueType);
			if(strIssueType.trim().equalsIgnoreCase("all"))
			{
				return actionMapping.findForward("allIssuesReports");
			}
			
			else
			{
				return actionMapping.findForward("specificStatusIssues");
			}
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
		
			
	}
	/*This method is used to generate graph in Issues in Specific Issues
	*/
	public ActionForward displaySpecificIssuesRecords(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		session=request.getSession(false);
		if(session!=null)
		{
			ReportsActionForm ReportsForm = (ReportsActionForm)actionForm; 
			String strStatus=ReportsForm.getStatus();
			String pageId=request.getParameter("pageId");
			System.out.println("page Id is : "+pageId); 
			
			if(pageId!=null)
			{
				ReportsForm.setChartType("pie3D");
				ReportsForm.setApplicationSelected("");
				ReportsForm.setStatus("active");
				strStatus="active";
			}			
			ArrayList ApplicationNamesInSpecificIssues=VIMSReportsDAO.getApplicationNamesDAO(strStatus);			
			session.setAttribute("ApplicationNamesInSpecificIssues", ApplicationNamesInSpecificIssues);
			if(session.getAttribute("issueTypesList")!=null)
			{
				session.removeAttribute("issueTypesList");	
			}

			return actionMapping.findForward("specificStatusIssues");
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
		
	}
	/*This method is used to generate excel sheets and PDf files in Issues in Specific Status page of all applications 
	*/
	public ActionForward generateSpecificIssuesRecords(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		session=request.getSession(false);
		if(session!=null)
		{
			Configuration configuration;
			ChartImage chartImage=null;
			HashMap hashMap=null;
			String imgPath=null;
			String imageId=null;
			String fileType=null;
			String strApplication=null;
			String strIssueType=null;
			String ApplicationStatus=null;
			String strChartType=null;
			try
			{
				ReportsActionForm ReportsForm = (ReportsActionForm)actionForm; 
				strApplication=ReportsForm.getApplicationSelected();
				strIssueType=ReportsForm.getIssueTypeSelected();
				imgPath=ReportsForm.getImgPath();
				strChartType=ReportsForm.getChartType();
				if(imgPath!=null)
				{
					imageId=VIMSReportsBD.getImageId(imgPath.trim()).trim();
				}
				if(strChartType!=null)
				{
					if(strChartType.equalsIgnoreCase("list"))
					{
						imageId="100";
					}
					
				}
				fileType=request.getParameter("export");		
				hashMap=(HashMap)session.getAttribute("SpecificIssuesLength");
				ServletContext servletContext=this.getServlet().getServletContext();
		 	    configuration=Configuration.getInstance(servletContext);	
				if(imageId!=null)
		    	   {
		    		   if(imageId.equals("100"))
		    		   {
		    			   chartImage=null;
				    	   System.out.println("Chart Image "+chartImage);
		    		   }
			    	   else
			    	   {
				    	   chartImage=configuration.getStorage().getChartImage(imageId,request); 
				    	   System.out.println("Chart Image "+chartImage);
			    	   }
		    	   }
		 	    hashMap.put("Image",chartImage);
		 	    hashMap.put("ImageId",imageId);
		 	    hashMap.put("IssuesAndApplicationList",hashMap);
		 	   
		 	    String strTargetpath=VIMSReportsBD.getTargetPath(request,servletContext);	 	  
		 	    VIMSReportsBD.FileType(fileType,response,hashMap,strTargetpath,strIssueType,strApplication);			
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}
			return null;
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
		
		
	}
	/*This method is used to generate excel sheets and PDf files in Issues in Specific Status page of all issues in an application 
	*/	
	public ActionForward generateAllIssuesInApplicationRecords(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		session=request.getSession(false);
		if(session!=null)
		{
			Configuration configuration=null;
			ChartImage chartImage=null;
			HashMap AllIsssuesInApplication=null;
			HashMap hashMap=null;
			String imgPath=null;
			String strChartType=null;
			String imageId=null;
			String fileType=null;
			String strIssueType=null;
			String strApplication=null;
			String strTargetpath=null;
			try
			{
				ReportsActionForm ReportsForm = (ReportsActionForm)actionForm; 
				strApplication=ReportsForm.getApplicationSelected();
				strIssueType=ReportsForm.getIssueTypeSelected();
				imgPath=ReportsForm.getImgPath();
				strChartType=ReportsForm.getChartType();
				if(imgPath!=null)
				{
					imageId=VIMSReportsBD.getImageId(imgPath.trim()).trim();
				}
				if(strChartType!=null)
				{
					if(strChartType.equalsIgnoreCase("list"))
					{
						imageId="100";
					}
					
				}
				fileType=request.getParameter("export");				
				hashMap = new HashMap();	
				hashMap=(HashMap)session.getAttribute("AllissuesLength");

				 ServletContext servletContext=this.getServlet().getServletContext();
		 	    configuration=Configuration.getInstance(servletContext);	
				if(imageId!=null)
		    	   {
		    		   if(imageId.equals("100"))
		    		   {
		    			   chartImage=null;
				    	   System.out.println("Chart Image "+chartImage);
		    		   }
			    	   else
			    	   {
				    	   chartImage=configuration.getStorage().getChartImage(imageId,request); 
				    	   System.out.println("Chart Image "+chartImage);
			    	   }
		    	   }
		 	   
		 	    AllIsssuesInApplication=new HashMap();
		 	    AllIsssuesInApplication.put("Image",chartImage);
		 	    AllIsssuesInApplication.put("ImageId",imageId);
		 	    AllIsssuesInApplication.put("IssuesAndApplicationList",hashMap);
		 	   
		 	    strTargetpath=VIMSReportsBD.getTargetPath(request,servletContext);	 	 
		 	    VIMSReportsBD.FileType(fileType,response,AllIsssuesInApplication,strTargetpath,strIssueType,strApplication);
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
			return null;		
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
		
	}
	/*
	 * This method is used get SLA information in SLA Information Page
	*/
	
	public ActionForward getSLAInformationReports(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		session=request.getSession(false);
		if(session!=null)
		{
			ReportsActionForm ReportsForm = (ReportsActionForm)actionForm; 
			String strChartType=(String)ReportsForm.getChartType();
			String strStatus=(String)ReportsForm.getStatus();
			VIMSReportsBD.setApplicationIssuesRecordsStatus(strStatus);
			
			String pageId=request.getParameter("pageId");
			System.out.println("page Id is : "+pageId); 
			if(pageId!=null)
			{
				ReportsForm.setChartType("pie3D");
				ReportsForm.setStatus("active");
			}			
			ArrayList[] ApplicationEscalatedStatus=VIMSReportsBD.getApplicationByIncident(strStatus);
			
			HttpSession session = request.getSession(false);
			
			session.setAttribute("ApplicationEscalated", (ArrayList)ApplicationEscalatedStatus[0]);
			session.setAttribute("ApplicationNotEscalated", (ArrayList)ApplicationEscalatedStatus[1]); 
			request.setAttribute("strChartType", strChartType);
			return actionMapping.findForward("displaySLAInformation");
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}

		
	}
	
	/*
	 * This method is used to generate excel sheets and PDF files in SLA Information page
	*/
	public ActionForward generateSLAInformationReports(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		session=request.getSession(false);
		if(session!=null)
		{
			Configuration configuration;
			ChartImage chartImage;
			ArrayList[] IssuesAndApplicationList=new ArrayList[2];
			
			ReportsActionForm ReportsForm = (ReportsActionForm)actionForm; 
			
			String imgPath=ReportsForm.getImgPath();
			String imageId=VIMSReportsBD.getImageId(imgPath.trim()).trim();
			String fileType=request.getParameter("export");
			HashMap hashMap = new HashMap();		
			try  
		       {	    	   
		    	   //IssuesAndApplicationList=VIMSReportsDAO.displayAllApplcationDetailsForSLAInformation();
		    	   
				IssuesAndApplicationList[0]=(ArrayList)session.getAttribute("ApplicationEscalated");
				IssuesAndApplicationList[1]=(ArrayList)session.getAttribute("ApplicationNotEscalated");
				
		    	   ServletContext servletContext=this.getServlet().getServletContext();
		    	   configuration=Configuration.getInstance(servletContext);	    	   
		    	   chartImage=configuration.getStorage().getChartImage(imageId,request);
		    	   
		    	   hashMap.put("Image",chartImage);
		    	   hashMap.put("ImageId",imageId);
		    	   hashMap.put("IssuesAndApplicationList",IssuesAndApplicationList);
		    	   
		    	   String strTargetpath=VIMSReportsBD.getTargetPath(request,servletContext);
		    	   VIMSReportsBD.FileType(fileType,response,hashMap,strTargetpath,"SLA","All Applications");
	          	 }
		        catch(Exception exception)
		        {	        	
		        	exception.printStackTrace();
		        }
			return null;
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
		
	}
	/*
	 * This method is called when the user clicks on the Custom reports Tab under Reports	
	*/
	public ActionForward getCustomReports(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		session=request.getSession(false);
		if(session!=null)
		{
			ArrayList CustomReportsList=new ArrayList();
			ArrayList[] ReportsCriteriaList=new ArrayList[8];
			HashMap hashMap;
			
			if(session.getAttribute("CustomReportsArrayList")!=null)
			{
				session.removeAttribute("CustomReportsArrayList");
			}
			
			
			for(int index=0;index<customList.length;index++)
			{
				CustomReportsList.add(customIdList[index]);
			}		
			
			ReportsCriteriaList=VIMSReportsBD.getCustomHomeDetailsBD();
			
			session.setAttribute("ApplicationDetailsList", ReportsCriteriaList[0]);
			session.setAttribute("CustomerDetailsList", ReportsCriteriaList[1]);
			session.setAttribute("SupportCenterList", ReportsCriteriaList[2]);
			session.setAttribute("GroupDetailsList", ReportsCriteriaList[3]);
			session.setAttribute("GroupMemList", ReportsCriteriaList[4]);
			session.setAttribute("EmployeeList", ReportsCriteriaList[5]);
			session.setAttribute("GroupSupervisorList", ReportsCriteriaList[6]);
			session.setAttribute("SupportMgrList", ReportsCriteriaList[7]);		
			session.setAttribute("disableOK","false");
			if(session.getAttribute("CustomReportsList")!=null)
			{
				session.removeAttribute("CustomReportsList");
				session.setAttribute("CustomReportsList", CustomReportsList); 
			}
			else
			{
				session.setAttribute("CustomReportsList", CustomReportsList); 
			}
			if(session.getAttribute("SelectedCriteriaList")!=null)
			{
				session.removeAttribute("SelectedCriteriaList"); 
			}

			return actionMapping.findForward("customHome");
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
	}
	/*
	 * This method is used to generate Reports in Custom Reports Page
	*/
	public ActionForward generateCustomReports(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		session=request.getSession(false);
		if(session!=null)
		{
			CustomReportsActionForm  customReportsActionForm=(CustomReportsActionForm)actionForm;
			String[] columnNames=customReportsActionForm.getColumList();		
			ArrayList arrayList=VIMSReportsBD.getColumnNames(columnNames);
			session.setAttribute("ColumnNames", arrayList);	
			ArrayList CustomReportsAction=VIMSReportsBD.generateCustomReportsBD(customReportsActionForm,arrayList);
			if((CustomReportsAction!=null) && (CustomReportsAction.size()!=0))
			{
				session.setAttribute("CustomReportsArrayList", CustomReportsAction);
			}
			else
			{
				request.setAttribute("NoRecordsFound", "No Records Found");
			}
			ArrayList SelectedAndSelectCriteria=VIMSReportsBD.getSelectedAndSelectCriteriaBD(columnNames,customIdList);
			session.setAttribute("disableOK","true");
			session.setAttribute("CustomReportsList", SelectedAndSelectCriteria);
			//arrayList.remove(0);
			ArrayList SelectedColumns=arrayList;
			session.setAttribute("SelectedCriteriaList", SelectedColumns);
			return actionMapping.findForward("customHome");
		}

		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
		
	}
	/*
	 * This method is used to generate excel sheets and PDf files  in Custom Reports Page
	*/
	public ActionForward exportCustomReports(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		session=request.getSession(false);
		if(session!=null)
		{
			ArrayList CustomReportsArrayList=(ArrayList)session.getAttribute("CustomReportsArrayList");
			ArrayList ColumnNames=(ArrayList)session.getAttribute("ColumnNames");
			String exportType=request.getParameter("exportTo");
			ServletContext servletContext=this.getServlet().getServletContext();
			String strTargetpath=VIMSReportsBD.getTargetPath(request,servletContext);
			VIMSReportsBD.CustomFileType(exportType, response,strTargetpath,CustomReportsArrayList,ColumnNames);		 
			return null;
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
		
	}
	public ActionForward getApplByStatus(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{	
		session=request.getSession(false);
		if(session!=null)
		{
			String strStatus=request.getParameter("strStatus");
			
			StringBuffer SBApplId=new StringBuffer();
			StringBuffer SBApplName=new StringBuffer();
			String strApplId="none";
			String strApplName="none";
			HashMap hashMap=null; 
			
			ArrayList ApplicationNamesInSpecificIssues=VIMSReportsDAO.getApplicationNamesDAO(strStatus);
			
			SBApplId=SBApplId.append("allApps;");
			SBApplName=SBApplName.append("All Applications;");
			
			for(int i=0;i<ApplicationNamesInSpecificIssues.size();i++)
			{
				hashMap=(HashMap)ApplicationNamesInSpecificIssues.get(i);
				
				if(i!=ApplicationNamesInSpecificIssues.size()-1)
				{
					SBApplId=SBApplId.append((String)hashMap.get("id")+";");
					SBApplName=SBApplName.append((String)hashMap.get("ApplicationName")+";");
				}
				else
				{
					SBApplId=SBApplId.append((String)hashMap.get("id"));
					SBApplName=SBApplName.append((String)hashMap.get("ApplicationName"));
				}
				strApplId=SBApplId.toString();
				strApplName=SBApplName.toString();
				//System.out.println(strApplId+"===strApplId=====strApplName="+strApplName); 
			}
			try
			{			
				response.setContentType("text/xml");        
		        response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
				response.getOutputStream().println("<response>");			
				response.getOutputStream().println("<result>" + strApplId + "</result>");
				response.getOutputStream().println("<result>" + strApplName + "</result>");
				response.getOutputStream().println("</response>"); 
			}
			catch(Exception e) 
			{
				e.printStackTrace();
			}
			
			
			session.setAttribute("ApplicationNamesInSpecificIssues", ApplicationNamesInSpecificIssues);
			//session.setAttribute("ApplicationStatus", strStatus);
			if(session.getAttribute("issueTypesList")!=null)
			{
				session.removeAttribute("issueTypesList");
			}
			return null;
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
		
	}
	@Override
	protected Map getKeyMethodMap() {
		HashMap methodKeyMap = new HashMap();
		
		methodKeyMap.put("VIMS.Reports.displayReportsLinks", "displayReportsLinks");
		methodKeyMap.put("VIMS.reports.getApplicationIssuesRecords", "getApplicationIssuesRecords");	
		methodKeyMap.put("VIMS.reports.generateApplicationIssuesRecords", "generateApplicationIssuesRecords");
		methodKeyMap.put("VIMS.REPORTS.getIssueTypes", "getIssueTypes");
		methodKeyMap.put("VIMS.reports.getSpecificIssuesRecords", "getSpecificIssuesRecords");	
		methodKeyMap.put("VIMS.reports.generateSpecificIssuesRecords", "generateSpecificIssuesRecords");
		methodKeyMap.put("VIMS.reports.displaySpecificIssuesRecords", "displaySpecificIssuesRecords");		
		methodKeyMap.put("VIMS.reports.generateAllIssuesInApplicationRecords", "generateAllIssuesInApplicationRecords");
		methodKeyMap.put("VIMS.reports.getSLAInformationReports", "getSLAInformationReports");		
		methodKeyMap.put("VIMS.reports.generateSLAInformationReports", "generateSLAInformationReports");
		methodKeyMap.put("VIMS.REPORTS.SUBMIT", "getSpecificIssuesRecords");
		methodKeyMap.put("VIMS.reports.getCustomReports", "getCustomReports");
		methodKeyMap.put("VIMS.reports.generateCustomReports","generateCustomReports");
		methodKeyMap.put("VIMS.reports.exportCustomReports","exportCustomReports");
		methodKeyMap.put("VIMS.Reports.getApplByStatus","getApplByStatus");
		return methodKeyMap;
	}

}
