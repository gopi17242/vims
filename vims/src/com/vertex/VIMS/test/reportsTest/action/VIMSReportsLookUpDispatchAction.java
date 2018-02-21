package com.vertex.VIMS.test.reportsTest.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;


import com.vertex.VIMS.test.SLA.BD.VIMSSLABD;



import com.vertex.VIMS.test.applications.BD.VIMSApplicationBD;
import com.vertex.VIMS.test.employee.BD.VIMSEmployeeBD;
import com.vertex.VIMS.test.reports.DAO.VIMSReportsDAO;
import com.vertex.VIMS.test.reports.form.ReportsActionForm;
import com.vertex.VIMS.test.reportsTest.BD.ReportsBD;
import com.vertex.VIMS.test.reportsTest.form.ApplicationReportsForm;
import com.vertex.VIMS.test.reportsTest.form.ApplicationReportsForm;
import com.vertex.VIMS.test.reportsTest.form.IssueReportForm;


public class VIMSReportsLookUpDispatchAction extends LookupDispatchAction
{
	static Logger logger; 
	HttpSession session=null;
	String strStatus=null;
	public ActionForward applicationReports(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		//System.out.println("=====application Reports===");
		String strLoginId=null;
		session=request.getSession(false);
		strLoginId=(String)session.getAttribute("user");
		ApplicationReportsForm ReportsForm = (ApplicationReportsForm)actionForm;
		ReportsForm.setStatus("All");
		ReportsForm.setAppName("All");
		ReportsForm.setViewType("summary");
		ArrayList ApplicationNames=ReportsBD.getApplicationNamesBD("All",strLoginId); 
		session.setAttribute("ApplicationListInReports",ApplicationNames);
		return actionMapping.findForward("ApplicationReports");
	}
	public ActionForward getApplicationsByStatus(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		StringBuffer SBApplId=null;
		StringBuffer SBApplName=null;
		String strApplId="none";
		String strApplName="none";
		String strLoginId=null;
		HashMap hashMap=null;
		ArrayList ApplicationNames=null;
		session=request.getSession(false);
		if(session!=null)
		{
			//ApplicationReportsForm ReportsForm = (ApplicationReportsForm)actionForm;
			//strStatus=ReportsForm.getStatus();
			strStatus=request.getParameter("strStatus"); 
			SBApplId=new StringBuffer();
			SBApplName=new StringBuffer();
			strLoginId=(String)session.getAttribute("user");
			StringBuffer sbFinal=new StringBuffer();
			ApplicationNames=ReportsBD.getApplicationNamesBD(strStatus,strLoginId);
			
			SBApplId=SBApplId.append("allApps;");
			SBApplName=SBApplName.append("All Applications;");
			
			for(int i=0;i<ApplicationNames.size();i++)
			{
				hashMap=(HashMap)ApplicationNames.get(i);
				
				if(i!=(ApplicationNames.size()-1))
				{
					SBApplId=SBApplId.append((String)hashMap.get("id")+";");
					SBApplName=SBApplName.append((String)hashMap.get("name")+";");
				}
				else
				{
					SBApplId=SBApplId.append((String)hashMap.get("id"));
					SBApplName=SBApplName.append((String)hashMap.get("name"));
				}
				sbFinal=sbFinal.append((String)hashMap.get("id")+":"+(String)hashMap.get("name")+";"); 
				strApplId=SBApplId.toString();
				strApplName=SBApplName.toString();				
			}
			//System.out.println(strApplId+"===strApplId=====strApplName="+strApplName); 
			try
			{			
				response.setContentType("text/xml");
				response.getWriter().write(sbFinal.toString());
//		        response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
//				response.getOutputStream().println("<response>");			
//				response.getOutputStream().println("<result>" + strApplId + "</result>");
//				response.getOutputStream().println("<result>" + strApplName + "</result>");
//				response.getOutputStream().println("</response>"); 
//				response.getOutputStream().close();
			}
			catch(Exception e) 
			{
				e.printStackTrace();
			}
			session.setAttribute("ApplicationListInReports", ApplicationNames);
			
			return null;
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}		
	}
	public ActionForward displayApplicationReports(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		session=request.getSession(false);
		String strStatus=null;
		String strApplicationSelected=null;
		String strFromDate=null;
		String strToDate=null;
		String strViewBy=null;
		ArrayList ApplicationReports=null;
		String strLoginId=null;
		int flag=0;
		if(session!=null)
		{

			ApplicationReportsForm ReportsForm = (ApplicationReportsForm)actionForm;
			strStatus=ReportsForm.getStatus();
			strApplicationSelected=ReportsForm.getAppName();
			strFromDate=ReportsForm.getFromDate();
			strToDate=ReportsForm.getToDate();
			
			strViewBy=ReportsForm.getViewType();
			/*System.out.println("In display Application Reports");
			System.out.println("--------strStatus---------------"+strStatus);
			System.out.println("----------strApplicationSelected-------------"+strApplicationSelected);
			System.out.println("---------strFromDate--------------"+strFromDate);
			System.out.println("---------strToDate--------------"+strToDate);*/
			strLoginId=(String)session.getAttribute("user");
			ApplicationReports=ReportsBD.displayApplicationReportsBD(strStatus,strApplicationSelected,strFromDate,strToDate,session,strViewBy,strLoginId);
			
			if(ApplicationReports.size()>0)
			{
				request.setAttribute("viewType",strViewBy);
				session.setAttribute("ApplicationReportsList", ApplicationReports); 
				return actionMapping.findForward("displayApplicationReports");
			}
			else
			{
				request.setAttribute("searchresult", "No records found");
				return actionMapping.findForward("ApplicationReports"); 
			}
			
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
	}	

	public ActionForward SLAReports(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		//System.out.println("=====SLA Reports==="); 
		session=request.getSession(false);	
		String strStatus=null;
		String strSLA=null;
		String strFromDate=null;
		String strToDate=null;
		ArrayList SLAReportsList=null;
		if(session!=null)
		{
			ApplicationReportsForm ReportsForm = (ApplicationReportsForm)actionForm;			
			
			ReportsForm.setStatus("selStatus");
			ReportsForm.setSla("selSLA");
			ReportsForm.setFromDate("");
			ReportsForm.setToDate("");
			return actionMapping.findForward("SLAReports");	
			
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
		
	}
	
	public ActionForward displaySLAReports(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		//System.out.println("=====Display Reports ==="); 
		session=request.getSession(false);
		String strStatus=null;
		String strSLA=null;
		String strFromDate=null;
		String strToDate=null;
		ArrayList SLAReportsList=null;
		if(session!=null)
		{
			ApplicationReportsForm ReportsForm = (ApplicationReportsForm)actionForm;
			strStatus=ReportsForm.getStatus();
			strSLA=ReportsForm.getSla();
			if(strSLA!=null && !(strSLA.equalsIgnoreCase("withinSLA"))){
				request.setAttribute("isAcceptanceLevel", "display");
			}
			strToDate=ReportsForm.getToDate();
			strFromDate=ReportsForm.getFromDate();
			
			SLAReportsList=ReportsBD.getSLAReportsBD(strStatus,strSLA,strFromDate,strToDate,session);
			
			if(SLAReportsList.size()>0)
			{
				session.setAttribute("SLAReportsList", SLAReportsList); 
				return actionMapping.findForward("displaySLAReports");
			}
			else
			{
				request.setAttribute("searchresult", "No records found");
				return actionMapping.findForward("SLAReports");
			}			
			
		}
		else
		{
			return actionMapping.findForward("sessionExpirePage");
		}
		
	}
	public ActionForward customerReports(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		//System.out.println("=====customerReports===");
		ApplicationReportsForm ReportsForm = (ApplicationReportsForm)actionForm;
		ReportsForm.setViewType("summary");
		/*ArrayList customerList=VIMSApplicationBD.getCustIdListBD();
		ArrayList appList =VIMSApplicationBD.getAppIdListBD();
		HttpSession session=request.getSession(false);
		session.setAttribute("appList",appList);
		session.setAttribute("customerList",customerList);*/
		return actionMapping.findForward("CustomerReports");
	}
	public ActionForward employeeReports(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		//System.out.println("=====employeeReports==="); 
		String strResourceType=null;
		String strUserId=null;
		ArrayList rolesList=null;
		ArrayList arrayList=null;
		ApplicationReportsForm form =(ApplicationReportsForm)actionForm;
		HttpSession session=request.getSession(false);
		if(session!=null)
		{
			strResourceType=form.getResourceType();
			//System.out.println("-----------strResourceType---------------"+strResourceType); 
			strUserId=(String) session.getAttribute("user");
			//System.out.println("========strUser Id============"+strUserId); 
			rolesList=VIMSEmployeeBD.getPositionListBD(strUserId);
			session.setAttribute("rolesList", rolesList);
			arrayList=ReportsBD.getEmployeesBD("All");
			request.setAttribute("AllEmployees",arrayList);
			
			if(strResourceType!=null)
			{
				if(strResourceType.equalsIgnoreCase("workingResinGrps"))
				{
					return actionMapping.findForward("AssignedEmployeeReports");
				}
				else
				{
					return actionMapping.findForward("EmployeeReports");
				}
			}
			else
			{
				return actionMapping.findForward("EmployeeReports");
			}
			
		}
		else 
		{
			return actionMapping.findForward("sessionExpirePage");
		}
	}	
	public ActionForward getEmployees(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		String strEmpId="none";
		String strEmpName="none";
		HttpSession session=request.getSession(false);
		if(session!=null) {
		String strRole=request.getParameter("role");
		//int iPosition=Integer.parseInt(strRole);
		//HttpSession session=request.getSession(false);
		//String strUserId=(String) session.getAttribute("user");
		ArrayList arrayList=ReportsBD.getEmployeesBD(strRole);
		StringBuffer sbufEmpId=new StringBuffer();
		StringBuffer sbufEmpName=new StringBuffer();
		HashMap hashMap=null; 
		
		//System.out.println("======size======="+arrayList.size());
		if(arrayList!=null)
		{
			for(int i=0;i<arrayList.size();i++)
			{
					hashMap=(HashMap)arrayList.get(i);
					//strGrpId[i-1]=(String)hashMap.get("grpId");
					//strGrpName[i-1]=(String)hashMap.get("grpName");
					if(i!=arrayList.size()-1)
					{
						sbufEmpId=sbufEmpId.append((String)hashMap.get("empId")+";");
						sbufEmpName=sbufEmpName.append((String)hashMap.get("empName")+";");
					}
					else
					{
						sbufEmpId=sbufEmpId.append((String)hashMap.get("empId"));
						sbufEmpName=sbufEmpName.append((String)hashMap.get("empName"));
					}
					strEmpId=sbufEmpId.toString();
					strEmpName=sbufEmpName.toString();
					//System.out.println(strEmpId+"===strEmpId=====strEmpName="+strEmpName);
			}
		}
		try
		{			
			response.setContentType("text/xml");        
	        response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			response.getOutputStream().println("<response>");
			response.getOutputStream().println("<result>" + strEmpId + "</result>");
			response.getOutputStream().println("<result>" + strEmpName + "</result>");
			response.getOutputStream().println("</response>"); 
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	  }
		else {
			actionMapping.findForward("sessionExpirePage");
		}
		return null;
	}
	
	
	/*************Generate employee reports****************************/
	public ActionForward generateEmployeeReports(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
	{
		ApplicationReportsForm form =(ApplicationReportsForm)actionForm;
		String strRole=null;
		String strResourceType=null;
		String strFromDate=form.getFromDate();
		String strToDate=form.getToDate();
		String strEmpId=form.getEmpId();
		int iPosition=0;
		HttpSession session=request.getSession(false);
		String strUserId=null;
		ArrayList arrayList=new ArrayList();
		String searchCriteria="Report based on";
		if(session!=null)
		{
			strResourceType=form.getResourceType();
			strRole=form.getRole();
			strUserId=(String)session.getAttribute("user");
			
			//System.out.println("----------strResourceType--------------"+strResourceType); 
			//System.out.println("-------strRole in generate employee---------------"+strRole); 
			if(strResourceType.equalsIgnoreCase("workingResinGrps"))
			{
				//System.out.println("-------------Working  Resources in Groups-------------------------");
				//iPosition=Integer.parseInt(strRole);
				
				if(strRole.equalsIgnoreCase("All"))
				{
					//System.out.println("---------Role--is All-------"); 
					arrayList = ReportsBD.generateAllPositionReportsBD(form,strUserId);
					//System.out.println("-------------- All Positions-----------------------"+arrayList); 
					session.setAttribute("ResourceType","AllPositions"); 
				}
				
				else if(Integer.parseInt(strRole)>3)
				{
					arrayList = ReportsBD.generateEmployeeReportsBD(form,strUserId);
					//System.out.println("-------------Programmer------------------------"+arrayList); 
					session.setAttribute("ResourceType","Programmer");
								
				}
				else if(Integer.parseInt(strRole)==3)
				{
					//System.out.println("---------Role--is Group Manager-------"); 
					arrayList = ReportsBD.generateSupervisorReportsBD(form,strUserId);
					
					//System.out.println("--------------Group Supervisor-----------------------"+arrayList); 
					session.setAttribute("ResourceType","Supervisor");
				}
				else if(Integer.parseInt(strRole)==2)
				{
					//System.out.println("---------Role--is Support Manager-------"); 
					arrayList = ReportsBD.generateManagerReportsBD(form,strUserId);
					//System.out.println("--------------Support Manager-----------------------"+arrayList); 
					session.setAttribute("ResourceType","Manager");
				}
				
				

			}
			else if(strResourceType.equalsIgnoreCase("availableResinGrps"))
			{
				//System.out.println("--------------Available Resources in Groups----------------");
				arrayList = ReportsBD.generateAvailableResourcesReportsBD(strResourceType,strRole);
				
				//System.out.println("--------------Available resources in Group-----------------------"+arrayList); 
				session.setAttribute("ResourceType","Availableingroups");
			}
			else if(strResourceType.equalsIgnoreCase("availableRes"))
			{
				//System.out.println("-------------Available Resources-------------------------");
	 			arrayList = ReportsBD.generateAvailableResourcesReportsBD(strResourceType,strRole);
				
				//System.out.println("--------------Available Resources-----------------------"+arrayList); 
				session.setAttribute("ResourceType","AvailableEmployees");
			}
			
			else if(strResourceType.equalsIgnoreCase("All"))
			{
				//System.out.println("-------------All -------------------------");
	 			arrayList = ReportsBD.generateAvailableResourcesReportsBD(strResourceType,strRole);
				
				//System.out.println("--------------All -----------------------"+arrayList); 
				session.setAttribute("ResourceType","AllEmployees");
			}
			if(arrayList.size()>0)
			{
				//System.out.println("-------arrayList.size()>0------------"+arrayList.size());
				session.setAttribute("searchCriteria",(String)arrayList.get((arrayList.size()-1)));
				arrayList.remove((arrayList.size()-1));
				session.setAttribute("empReports", arrayList);
				ArrayList employeeList=ReportsBD.getEmployeesBD(strRole);
				session.removeAttribute("employeeList");
				session.setAttribute("employeeList", employeeList);
				
	 			return actionMapping.findForward("GenerateEmployeeReports");				
			}
			else 
			{
				//System.out.println("-------arrayList.size()==0------------"+arrayList.size());
				ArrayList employeeList=ReportsBD.getEmployeesBD(strRole);
				session.removeAttribute("employeeList");
				session.removeAttribute("searchCriteria");
				session.removeAttribute("empReports");	
				session.setAttribute("employeeList", employeeList);
				request.setAttribute("resultMsg","No records found");
				
				form.setFromDate(strFromDate);
				form.setToDate(strToDate);
				form.setEmpId(strEmpId);
				//System.out.println("---------end of else loop--------------");				
				return actionMapping.getInputForward();
			}			
	}
	else
	{ 
		return actionMapping.findForward("sessionExpirePage");
	}
}
	public ActionForward getCustomers(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
	{
		String strCust="none";
		//String strCustName="none";
		HttpSession session=request.getSession(false);
		if(session!=null) {
		String strStatus=request.getParameter("status");
		
		
		ArrayList arrayList=ReportsBD.getCustomersBD(strStatus);
		StringBuffer sbufCustId=new StringBuffer();
		StringBuffer sbufCustName=new StringBuffer();
		StringBuffer sbFinal=new StringBuffer();
		HashMap hashMap=null; 
		
		//System.out.println("======size======="+arrayList.size());
		if(arrayList!=null)
		{
			for(int i=0;i<arrayList.size();i++)
			{
				hashMap=(HashMap)arrayList.get(i);
				if(i==arrayList.size()-1)
				{
					sbFinal=sbFinal.append((String)hashMap.get("custId")+":"+(String)hashMap.get("custName"));
				}
				else
				{
					sbFinal=sbFinal.append((String)hashMap.get("custId")+":"+(String)hashMap.get("custName")+";");
				}
				 
			}
			strCust=sbFinal.toString();
		}
		
		try
		{			
			response.setContentType("text/xml");   
			response.getWriter().write(strCust);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	  }
	  else {
		actionMapping.findForward("sessionExpirePage");
	}
		return null;
	}
	
	public ActionForward generateCustomerReports(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response)
	{
		 System.out.println("====The action has called====================");
		HttpSession session=request.getSession(false);
		ArrayList arrayList=null;
		ApplicationReportsForm form=(ApplicationReportsForm)actionForm;
		String strReportCriteria=null;
		String strLoginId=null;
		
		strLoginId=(String)session.getAttribute("user");
		System.out.println("=====strLoginId============="+strLoginId);
		arrayList=ReportsBD.generateCustomerReportsBD(form,strLoginId);
		String strStatus=form.getStatus();
		String strCustId=form.getCustName();
		String strFromDate=form.getFromDate();
		String strToDate=form.getToDate();
		String strViewBy=form.getViewType();
		System.out.println("=====after getting arrayList============"+arrayList);
		
		if(session!=null) {
		
		
		//strReportCriteria=new 
		if("".equals(strStatus))
		{
			strStatus=null;
		}
		else {
			
		}
			
		if("".equals(strCustId))
		{
			strCustId=null;
		}
		if("".equals(strFromDate) || "".equals(strToDate))
		{
			strFromDate=null;
			strToDate=null;
		}
		
		
	  }
	   else {
		   actionMapping.findForward("sessionExpirePage");
	   }
		if(arrayList!=null)
		{
			if(arrayList.size()>0)
			{ 
				request.setAttribute("viewType",strViewBy);
				session.setAttribute("searchCriteria",(String)arrayList.get((arrayList.size()-1)));
				arrayList.remove((arrayList.size()-1));
				session.setAttribute("customerReports", arrayList);
				ArrayList customerList=ReportsBD.getCustomersBD(strStatus);
				session.removeAttribute("customerList");
				session.setAttribute("customerList",customerList);
				return actionMapping.findForward("GenerateCustomerReports");	
			}
			else
			{
				request.setAttribute("resultMsg", "No records found"); 
				ArrayList customerList=ReportsBD.getCustomersBD(strStatus);
				session.removeAttribute("customerList");
				session.setAttribute("customerList",customerList);
				return actionMapping.findForward("CustomerReports");
			}					
		}
		else
		{
			request.setAttribute("resultMsg", "No records found"); 
			ArrayList customerList=ReportsBD.getCustomersBD(strStatus);
			session.removeAttribute("customerList");
			session.setAttribute("customerList",customerList);
			return actionMapping.findForward("CustomerReports");
		}
		
	}
	// Issue Reports Action
	public ActionForward getIssueReportHomePage(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		        
	  HttpSession session=null;
	  ArrayList statusTypes=null;
	  ArrayList issueSeverity=null;
	  ArrayList issuePriority=null;
	  HashMap hashMap=null;
		          
		 try
		 {
			    IssueReportForm ReportsForm = (IssueReportForm)actionForm;
	    		session=request.getSession(false);
	    		//System.out.println("=======Action Called================");
	    		if(session!=null)
	    		{  // If the request is from a valid session 
	    			ReportsForm.setViewType("summary");
	    			hashMap=ReportsBD.getIssueSettingsBD();
	    			
	    			if(hashMap!=null)
	    			{
	    				//System.out.println("=======true part============");
	    			  session.setAttribute("statusTypes",(ArrayList)hashMap.get("statusTypes"));
	    			  session.setAttribute("IssueSeverities",(ArrayList)hashMap.get("issueSeverity"));
	    			  session.setAttribute("IssuePriorities",(ArrayList)hashMap.get("issuePriority"));
	    			}
	    			else
	    			{
	    				//System.out.println("-------no hashmap values found----------");
	    			}
	    				
	    		}
	    		else
	    		{        			
	    			logger.info("------Invalid session from getIssueReportHomePage action=========");
	    			actionMapping.findForward("sessionExpirePage");
	    		}
		 } 
	   catch(Exception exception)
	   {		 
		  logger.info("--------Exception in getIssueReportHomePage action------------");
		  logger.error(exception);
		  exception.printStackTrace();
	   }
		   
	   //System.out.println("=========end of action==============");
	   return actionMapping.getInputForward();
	}
	
	public ActionForward generateIssueReport(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
		          
	   HttpSession session=null;
	   IssueReportForm reportForm=null;
	   ArrayList list=null;
	   String strUserId=null;
	   HashMap hashMap=null;
	   String strViewBy=null;
	   session=request.getSession(false);    
	   if(session!=null)
  		{
	        try  
	        {
    			logger=Logger.getLogger("Admin");    				   		
    		    strUserId=(String)session.getAttribute("user");
    		    reportForm=(IssueReportForm)actionForm;
    		    strViewBy=reportForm.getViewType();
    		    hashMap=new HashMap();
    		    hashMap.put("session",session);
    		    hashMap.put("form",reportForm);
    		    hashMap.put("userId", strUserId);
    		    
                list=ReportsBD.genereteIssueReport(hashMap);
                
                if(session.getAttribute("noRecords")!=null)
                	{
	     	    	// System.out.println("=======no record found==========");
	     	    	 request.setAttribute("errorMsg",(String)session.getAttribute("noRecords"));
	     	    	 session.removeAttribute("noRecords");
	     	    	 return actionMapping.getInputForward();
                	}
                else
                {
                	request.setAttribute("viewType",strViewBy);
                	session.setAttribute("IssueReportList",list);    	 
                }
                	  		   	   	   
	        }
	         catch(Exception exception)
	         {	        	 
	        	 logger.info("======Exception in generateIssueReport action=======");
	        	 logger.error(exception);
	         }
  		}
	   else
	   {
		   		logger.info("------Invalid session from generateIssueReport action=========");
		   		return actionMapping.findForward("sessionExpirePage");
	   } 
	        return actionMapping.findForward("displayReport");
	}
	
	public ActionForward getIssueDetailView(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
        
        HttpSession session=null;
        HashMap issueDetails=null;
        String strUserId=null;
        String strIssueId=null;
        
 try {
 	   logger=Logger.getLogger("Admin");
 	   session=request.getSession(false);
 	   
 	   if(session!=null) {
 		     strUserId=(String)session.getAttribute("user");
             strIssueId=request.getParameter("issueId");
 		     
              issueDetails=ReportsBD.getIssueDetailViewBD(strIssueId, session);
              //System.out.println("==========detail action===============");
              session.setAttribute("issueDetails",issueDetails);
              session.setAttribute("issueCycle",(ArrayList)issueDetails.get("issueLife"));
              //System.out.println("========"+(ArrayList)issueDetails.get("issueLife"));
 		   
 	   }
 	   else {
 		    logger.info("------Invalid session from generateIssueReport action=========");
   			actionMapping.findForward("sessionExpirePage");
 	   }
 	}
  		catch(Exception exception) {
 	 
		 	 logger.info("======Exception in generateIssueReport action=======");
		 	 logger.error(exception);
    }
  
        return actionMapping.findForward("displayDetailView");
}
	
	public ActionForward generateIssueExcelFile(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
	     HttpSession session=null;
	        ArrayList reportList=null;
	        String strTargetPath=null;
	        byte[] targetData=null;
	        ServletOutputStream servletOutputStream=null;
	        String reportType=null;
	        String strSearchCriteria=null;
 try {  
 	    session=request.getSession(false);
 	    if(session!=null){
         logger=Logger.getLogger("Admin");
          
         if(request.getParameter("type")!=null) {
         	
         	reportType=request.getParameter("type");
         }
         strTargetPath=getTargetPath(request,(this.getServlet().getServletContext()));
        // System.out.println("===========target path============"+strTargetPath);
         
	            if(reportType.equals("summaryIssues")) {
	             strSearchCriteria=(String)session.getAttribute("searchCriteria");
	             reportList=(ArrayList)session.getAttribute("IssueReportList");
	            // System.out.println("=======issue list=========="+reportList);
	             targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,strSearchCriteria,reportType);
	    	    }
	            else  if(reportType.equals("detailedIssues")) {
		             strSearchCriteria=(String)session.getAttribute("searchCriteria");
		             reportList=(ArrayList)session.getAttribute("IssueReportList");
		           //  System.out.println("=======issue list=========="+reportList);
		             targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,strSearchCriteria,reportType);
		    	    }
	            else if(reportType.equalsIgnoreCase("DetailView"))
	            {	
	            	HashMap detailIssue=null;
	            	 strSearchCriteria=(String)session.getAttribute("searchCriteria");
	            	 detailIssue=(HashMap)session.getAttribute("issueDetails");
		            // System.out.println("=======issue list=========="+reportList);
		             targetData=ReportsBD.generateDetailIssueExcelFileBD(detailIssue, strTargetPath,strSearchCriteria,reportType);
		    	}
	            else if(reportType.equalsIgnoreCase("detailedCustomer")) {
	            	
	              reportList=(ArrayList)session.getAttribute("customerReports");	
	             // System.out.println("=======customer list=========="+reportList);
	              targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,"",reportType);
	            }
	            
	            else if(reportType.equalsIgnoreCase("summaryCustomer")) {
	            	
		              reportList=(ArrayList)session.getAttribute("customerReports");	
		              //System.out.println("=======customer list=========="+reportList);
		              targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,"",reportType);
		            }
	            
	            
	            else if(reportType.equalsIgnoreCase("detailedApplication"))
	            {		            	
	            	 strSearchCriteria=(String)session.getAttribute("searchCriteria");
		             reportList=(ArrayList)session.getAttribute("ApplicationReportsList");
		             //System.out.println("=======issue list=========="+reportList);
		             targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,strSearchCriteria,reportType);
		    	}
	            
	            else if(reportType.equalsIgnoreCase("summaryApplication"))
	            {		            	
	            	 strSearchCriteria=(String)session.getAttribute("searchCriteria");
		             reportList=(ArrayList)session.getAttribute("ApplicationReportsList");
		            // System.out.println("=======issue list=========="+reportList);
		             targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,strSearchCriteria,reportType);
		    	}
	            
	            else if(reportType.equalsIgnoreCase("SLA"))
	            {		            	
	            	 strSearchCriteria=(String)session.getAttribute("searchCriteria");
		             reportList=(ArrayList)session.getAttribute("SLAReportsList");
		             //System.out.println("=======issue list=========="+reportList);
		             targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,strSearchCriteria,reportType);
		    	}
	            
	            else if(reportType.equalsIgnoreCase("Programmer"))
	            {		            	
	            	 strSearchCriteria=(String)session.getAttribute("searchCriteria");
		             reportList=(ArrayList)session.getAttribute("empReports");
		             //System.out.println("=======issue list=========="+reportList);
		             targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,strSearchCriteria,reportType);
		    	}
	            else if(reportType.equalsIgnoreCase("Supervisor"))
	            {		            	
	            	 strSearchCriteria=(String)session.getAttribute("searchCriteria");
		             reportList=(ArrayList)session.getAttribute("empReports");
		             //System.out.println("=======issue list=========="+reportList);
		             targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,strSearchCriteria,reportType);
		    	}
	            else if(reportType.equalsIgnoreCase("Manager"))
	            {		            	
	            	 strSearchCriteria=(String)session.getAttribute("searchCriteria");
		             reportList=(ArrayList)session.getAttribute("empReports");
		             //System.out.println("=======issue list=========="+reportList);
		             targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,strSearchCriteria,reportType);
		    	}
	            else if(reportType.equalsIgnoreCase("Availableingroups")) {
	            	
	            	reportList=(ArrayList)session.getAttribute("empReports");
	            	strSearchCriteria=(String)session.getAttribute("searchCriteria");
	            	//System.out.println("=======employee list=========="+reportList);
	            	targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,strSearchCriteria,reportType);
	            }
	            else if(reportType.equalsIgnoreCase("AvailableEmployees")) {
	            	
	            	reportList=(ArrayList)session.getAttribute("empReports");
	            	strSearchCriteria=(String)session.getAttribute("searchCriteria");
	            	//System.out.println("=======employee list=========="+reportList);
	            	targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,strSearchCriteria,reportType);
	            }
	            else if(reportType.equalsIgnoreCase("AllEmployees")) {
	            	
	            	reportList=(ArrayList)session.getAttribute("empReports");
	            	strSearchCriteria=(String)session.getAttribute("searchCriteria");
	            	//System.out.println("=======employee list=========="+reportList);
	            	targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,strSearchCriteria,reportType);
	            }
	            
	            else if(reportType.equalsIgnoreCase("SearchPage")) {
	            	
	            	reportList=(ArrayList)session.getAttribute("Record");
	            	strSearchCriteria=(String)session.getAttribute("searchCriteria");
	            	//System.out.println("=======employee list=========="+reportList);
	            	targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,strSearchCriteria,reportType);
	            }
	            else if(reportType.equalsIgnoreCase("AllPositions")) {
 	
				 	reportList=(ArrayList)session.getAttribute("empReports");
				 	strSearchCriteria=(String)session.getAttribute("searchCriteria");
				 	//System.out.println("=======employee list=========="+reportList);
				 	targetData=ReportsBD.getIssueExcelReport(reportList, strTargetPath,strSearchCriteria,reportType);
				 }
 	    } 
 	     else {
 	    	   logger.info("------Invalid session from generateIssueExcelFile action=========");
 	   			actionMapping.findForward("sessionExpirePage");
 	     }
 	    // Set the response type as Excel sheet
 	    response.setContentType("application/xls");
 	    response.setHeader("Content-Disposition", "attachment; filename=" +"VIMSReport.xls");
 	    
 	    servletOutputStream = response.getOutputStream();
			servletOutputStream.write(targetData);
	    	servletOutputStream.close();
	  }
		catch(Exception exception) {

		 	 logger.info("======Exception in generateIssueReport action=======");
		 	 logger.error(exception);
}
	 return null;
  }
	public ActionForward generateIssuePDFFile(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) {
	HttpSession session=null;
    ArrayList reportList=null;
    String strTargetPath=null;
    byte[] targetData=null;
    ServletOutputStream servletOutputStream=null;
    String reportType=null;
    String strSearchCriteria=null;
    try {  
 	    session=request.getSession(false);
 	    if(session!=null)
 	    {
         logger=Logger.getLogger("Admin");
          
         if(request.getParameter("type")!=null)
         {         	
         	reportType=request.getParameter("type");
         }
         strTargetPath=getTargetPath(request,(this.getServlet().getServletContext()));
         
	        if(reportType.equals("HomeIssues"))
	        {
	         strSearchCriteria=(String)session.getAttribute("searchCriteria");
	         reportList=(ArrayList)session.getAttribute("IssueReportList");
	         targetData=ReportsBD.getIssuePDFReport(reportList, strTargetPath,strSearchCriteria,reportType);
		    }	            
 	    } 
 	     else 
 	     {
 	    	   logger.info("------Invalid session from generateIssueExcelFile action=========");
 	   			actionMapping.findForward("sessionExpirePage");
 	     }
 	    response.setContentType("application/pdf");
 	    response.setHeader("Content-Disposition", "attachment; filename=" +"VIMSReport.pdf");
 	    
 	    servletOutputStream = response.getOutputStream();
		servletOutputStream.write(targetData);
    	servletOutputStream.close();
	  }
		catch(Exception exception)
		{
		 	 logger.info("======Exception in generateIssueReport action=======");
		 	 logger.error(exception);
		}
	 return null;
	}
	/*
	 * This method is used to retrieve the path in order to store the File
	*/
	public static String getTargetPath(HttpServletRequest request, ServletContext servletContext)
	{		
		int dotIndex;      	
      	String subPath=null; 
      	
      	try {  
                String absolutePath = servletContext.getRealPath("/").replace("\\.\\", "\\"); 
                return absolutePath;
      	  }
  	     catch(Exception exception)
  	     {      	 	
  	    	 exception.printStackTrace();
	         return null;      	 	 
  	      }
      }
	public Map getKeyMethodMap()
	{
		HashMap<String, String> methodKeyMap = new HashMap<String, String>();		
		methodKeyMap.put("VIMSReports.employeeReports","employeeReports");
		methodKeyMap.put("VIMSReports.generateCustomerReports","generateCustomerReports");
		methodKeyMap.put("VIMSReports.getEmployees","getEmployees");

		methodKeyMap.put("VIMSReports.generateEmployeeReports","generateEmployeeReports");
		methodKeyMap.put("VIMSReports.customerReports","customerReports");	
		methodKeyMap.put("VIMSReports.getCustomers","getCustomers");
		
		methodKeyMap.put("VIMSReports.getOwners","getOwners");
		methodKeyMap.put("VIMSReports.getApplications","getApplications");				
		
		//Application Reports
		methodKeyMap.put("VIMSReports.applicationReports","applicationReports");
		methodKeyMap.put("VIMSReports.getApplicationsByStatus","getApplicationsByStatus");
		methodKeyMap.put("VIMSReports.displayApplicationReports","displayApplicationReports");
		
		methodKeyMap.put("VIMSReports.customerReports","customerReports");
		methodKeyMap.put("VIMSReports.employeeReports","employeeReports");
		
		// SLA Reports
		
		methodKeyMap.put("VIMSReports.SLAReports","SLAReports");
		methodKeyMap.put("VIMSReports.displaySLAReports","displaySLAReports");
		// Issue report generation action

		methodKeyMap.put("VIMS.generateIssueReport","generateIssueReport");
		methodKeyMap.put("VIMS.getIssueDetailView", "getIssueDetailView");
		methodKeyMap.put("VIMS.getIssueReportHomePage","getIssueReportHomePage");
		methodKeyMap.put("VIMS.generateFile","generateIssueExcelFile");
     	return methodKeyMap;
	}
}
