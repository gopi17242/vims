/*
 * Author: santhosh.k
 * Creation date: 11/19/2008
 * File Name : VIMSHomeLookUpDispatchAction.java
 * Description: 
 * 			It is the controller of the home page module.It is called when the user clicks on the home link in the home page or when user
 * 			selects an application from the application list in home page or when user enters a valid issue id in home page.
 * 
*/
package com.vertex.VIMS.test.home.action;

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

import com.vertex.VIMS.test.SLA.BD.VIMSSLABD;
import com.vertex.VIMS.test.home.BD.VIMSHomeBD;
import com.vertex.VIMS.test.home.DAO.VIMSHomeDAO;
import com.vertex.VIMS.test.listofissues.BD.ListofIssuesBD;
import com.vertex.VIMS.test.reports.BD.VIMSReportsBD;
import com.vertex.VIMS.test.reports.DAO.VIMSReportsDAO;
import com.vertex.VIMS.test.reportsTest.BD.ReportsBD;

import de.laures.cewolf.ChartImage;
import de.laures.cewolf.Configuration;
import com.vertex.VIMS.test.login.BD.LoginBD;

public class VIMSHomeLookUpDispatchAction extends LookupDispatchAction {
	
	HttpSession session=null;//declaring instance variable of type HttpSession
	
	/*
	 * This method is used to retrieve list of applications 	
	*/
	public ActionForward getHomePageApplicationsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		
		session=request.getSession(false);
		String strLoginUser=null;
		strLoginUser=(String)session.getAttribute("user");
		ArrayList ApplicationNames=VIMSSLABD.getApplicationNamesBD(strLoginUser);
		if(session!=null) {
		session.removeAttribute("ApplicationsList");
		session.setAttribute("ApplicationsList", ApplicationNames);	
		
		session.removeAttribute("selectedApplicationId");
		session.setAttribute("selectedApplicationId", "select an application");
		DynaValidatorForm vimsSLAForm = (DynaValidatorForm)actionForm;		
		// Added to get custom settings if user is an administrator type
		if(session!=null) {
			String strUserType=(String)session.getAttribute("Role");
			if((strUserType.equals("Admin"))) {
				String strUserId=(String)session.getAttribute("user");
				LoginBD.applyCustomSettings(request, response, strUserId);
			 }
		}
		// end of custome settings
		}
		else {
			return actionMapping.findForward("sessionExpirePage");
		}
	 	
		return actionMapping.findForward("home");
	}
	/* 
	 * This method is called to retrieve SLA details of a selected application as well as the 
	 * number of open, open critical, open major and open minor issues.
	 * 
	*/
	public ActionForward getHomePageSLA(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		session=request.getSession(false);
		if(session!=null) {
		DynaValidatorForm vimsSLAForm = (DynaValidatorForm)actionForm;	//retrieves DynaValidatorForm object and assigned to vimsSLAForm	
		String strApplicationId=(String)vimsSLAForm.get("applicationId");//retrieving application id from the form bean
		HashMap HomeOpenAndSLADetailsAction=VIMSHomeBD.getHomeApplicationOpenAndSLABD(strApplicationId); // retrieving SLA details of the selected application
		if(HomeOpenAndSLADetailsAction.size()!=0)
		{
			request.setAttribute("HomeOpenAndSLADetailsAction",HomeOpenAndSLADetailsAction); 
		}	
		session.removeAttribute("selectedApplicationId");
		session.setAttribute("selectedApplicationId", strApplicationId);
		}
		else {
			return actionMapping.findForward("sessionExpirePage");
		}
		/*vimsSLAForm.set("applicationId", strApplicationId);*/
		return actionMapping.findForward("home");
	}	
	
	/*
	 * This method is called when the application has open issues and user clicks on the any one of the open issues i.e,on open issues, 
	 * open critical,open major,open minor in order to display the contents.	
	 * 
	 */
	public ActionForward displayHomeOpenissues(ActionMapping actionMapping, ActionForm actionForm,HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(false);//initialization of the instance variable
		if(session!=null) {
		DynaValidatorForm vimsSLAForm = (DynaValidatorForm)actionForm;	//retrieves DynaValidatorForm object and assigned to vimsSLAForm	
		String strIncidentType=(String)request.getParameter("inctType");//retrieving the value of the variable inctType which is rewritten in the url.

		String strApplicationId=(String)vimsSLAForm.get("applicationId");//retrieving application id from the form bean
		ArrayList IssuesList=VIMSHomeBD.displayHomeOpenissuesBD(strApplicationId,strIncidentType);//retrieving the list of issues which are specific to that application and the mentioned incident type from the BD class
		request.setAttribute("inctType",strIncidentType);
		request.setAttribute("applicationId",strApplicationId);		
		session.setAttribute("OpenIssuesInApplication", IssuesList);		
		}
		else {
			return actionMapping.findForward("sessionExpirePage");
		}
		return actionMapping.findForward("homePageOpenIssuesDisplay");		
	}
	/*
	 * This method is used to generate excel sheets and PDF files which contains openn issues in an application
	*/
	
	public ActionForward generateHomeOpenissues(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
        HttpSession session=null;
        session=request.getSession(false);
        
        if(session!=null) {
		ArrayList IssuesAndApplicationList=(ArrayList)session.getAttribute("OpenIssuesInApplication");	
		String ApplicationName=(String)((HashMap)IssuesAndApplicationList.get(0)).get("APPLICATION_NAME");
		String fileType=(String)request.getParameter("exportTo");
		HashMap hashMap = new HashMap();
		String strTargetpath=null;
		String reportType=null;
		ServletOutputStream servletOutputStream=null;
		byte[] targetData;
		try  
	       {	
	    	   hashMap.put("IssuesAndApplicationList",IssuesAndApplicationList); 
	    	   strTargetpath=getTargetPath(request,(this.getServlet().getServletContext()));
	    	   reportType="HomeIssues"; 
	    	   if(fileType.equalsIgnoreCase("EXCEL"))
	    	   {
	    		   targetData=ReportsBD.getIssueExcelReport(IssuesAndApplicationList,strTargetpath,"",reportType);
	    		   response.setContentType("application/xls");
		    	    response.setHeader("Content-Disposition", "attachment; filename=" +"VIMSReport.xls");
		    	    
		    	    servletOutputStream = response.getOutputStream();
		   			servletOutputStream.write(targetData);
		   	    	servletOutputStream.close();
	    	   }
	    	   else
	    	   {
	    		   targetData=ReportsBD.getIssuePDFReport(IssuesAndApplicationList,strTargetpath,"",reportType);
	    		 System.out.println("----------targetData in Action Class-------------"+targetData); 
	    		   //  VIMSReportsBD.FileType(fileType,response,hashMap,strTargetpath,"Home",ApplicationName);
	    		   response.setContentType("application/pdf");
		    	    response.setHeader("Content-Disposition", "attachment; filename=" +"VIMSReport.pdf");
		    	    
		    	    servletOutputStream = response.getOutputStream();
		   			servletOutputStream.write(targetData);
		   	    	servletOutputStream.close();
	    	   }
	    	   	
	    	   /*ServletContext servletContext=this.getServlet().getServletContext();	    	   
	    	   strTargetpath=VIMSReportsBD.getTargetPath(request,servletContext);*/
	    	   
	    	  
          	 }
	        catch(Exception exception)
	        {	
	        	exception.printStackTrace();
	        }
        }
         else {
        	 return actionMapping.findForward("sessionExpirePage"); 
         }
		return null;
	}
	// added by Geeta 
	
	
	
	public ActionForward displayissues(ActionMapping actionMapping, ActionForm actionForm,HttpServletRequest request, HttpServletResponse response)
	{
		session = request.getSession(false);
		
		if(session!=null) {
		String strUserId=(String)session.getAttribute("user");	
       
		//initialization of the instance variable	
		ArrayList issueList=VIMSHomeBD.displayUserIssuesBD(strUserId);
	    ArrayList List1=(ArrayList)issueList.get(0);	 
	    ArrayList List2=(ArrayList)issueList.get(1);	  
	    String issueType=request.getParameter("issueType");
	    
	    if(issueType.equalsIgnoreCase("Open"))
	    {
	     session.setAttribute("IssueList",List1);
	 	 return actionMapping.findForward("IssuesUserPage");
	    }
	    else
	    {
	     session.setAttribute("ListOfEmployeIssue",List2);
	     return actionMapping.findForward("MyToDoListForward");
	    }
	   }
		else {
			return actionMapping.findForward("sessionExpirePage");
		}
			
	
	}
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
	@Override
	/*
	 * This is the Overridden method to map the method to a corresponding key which is to be used in calling that method
	 * 
	*/
	protected Map getKeyMethodMap() {
		HashMap methodKeyMap = new HashMap();//initialising and declaration of a local variable of type HashMap
		methodKeyMap.put("VIMS.Home.getHomePageApplicationsList", "getHomePageApplicationsList");
		methodKeyMap.put("VIMS.Home.getHomePageSLA", "getHomePageSLA");
		methodKeyMap.put("VIMS.Home.getHomeIssueDetails", "getHomeIssueDetails");
		methodKeyMap.put("VIMS.HOME.displayHomeOpenIssueDetails", "displayHomeOpenissues");		
		methodKeyMap.put("VIMS.HOME.generateHomeOpenissues", "generateHomeOpenissues");
		methodKeyMap.put("VIMS.HOME.displayUserOpenIssueDetails","displayissues");//added by geeta
		return methodKeyMap;// returns the HashMap object
	}
}
