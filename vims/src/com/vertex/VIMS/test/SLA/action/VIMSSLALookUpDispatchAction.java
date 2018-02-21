/* 
 * @author: santhosh.k
 * @creation date:  Nov 13, 2008
 * @file name : VIMSSLABD.java
 * @description: 
 * 			It is the Controller of the SLA page.It is called when the user clicks on the SLA Tab.
*/

package com.vertex.VIMS.test.SLA.action;

import org.apache.struts.action.*;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import javax.servlet.http.*;

import com.vertex.VIMS.test.SLA.BD.*;
import com.vertex.VIMS.test.SLA.DAO.VIMSSLADAO;
import com.vertex.VIMS.test.SLA.form.SLAActionForm;

import java.util.*;


public class VIMSSLALookUpDispatchAction extends LookupDispatchAction 
{	
	String strSuccess="Successfully Deleted";
	String strFailure="Failed to Delete";
	HttpSession session =null;
	/*
	 * This method is called when the user clicks on SLA tab and is used to retrieve the list of applications which possess SLA.
	*/
	public ActionForward getApplications(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		session = request.getSession(true);
		String strLoginUser=null;
		String strChangeType=request.getParameter("ChangeType");
		strLoginUser=(String)session.getAttribute("user");
		if(session.getAttribute("applicationSLADetailsLD")!=null)
		{
			session.removeAttribute("applicationSLADetailsLD");
		}
		
		ArrayList ApplicationNames=VIMSSLABD.getApplicationNamesBD(strLoginUser);		
		session.setAttribute("ApplicationsList", ApplicationNames);				
		return actionMapping.findForward("SLASettings");
			
	}
	/*
	 * This method is to retrieve he SLA details of the selected application 
	*/
	public ActionForward getApplicationSLADetails(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		try{
			session.removeAttribute("changeBtn");
			session.removeAttribute("applicationSLADetailsLD");
			SLAActionForm vimsSLAForm = (SLAActionForm)actionForm;		
			String strApplicationId=(String)vimsSLAForm.getApplication();		
		 
		HashMap ApplicationSLADetailsLD=VIMSSLABD.getApplicationSLADetailsBD(strApplicationId);
		System.out.println("Sla Details is : "+ApplicationSLADetailsLD); 
		if(ApplicationSLADetailsLD!=null && ApplicationSLADetailsLD.size()!=0)
		{
			vimsSLAForm.setCriticalResponseTime((String)ApplicationSLADetailsLD.get("CRITICAL_RESPONSE_TIME"));
			vimsSLAForm.setCriticalWarningInterval((String)ApplicationSLADetailsLD.get("CRITICAL_WARNING_INTERVAL"));
			vimsSLAForm.setMajorResponseTime((String)ApplicationSLADetailsLD.get("MAJOR_RESPONSE_TIME"));
			vimsSLAForm.setMajorWarningInterval((String)ApplicationSLADetailsLD.get("MAJOR_WARNING_INTERVAL"));
			vimsSLAForm.setMinorResponseTime((String)ApplicationSLADetailsLD.get("MINOR_RESPONSE_TIME"));
			vimsSLAForm.setMinorWarningInterval((String)ApplicationSLADetailsLD.get("MINOR_WARNING_INTERVAL"));
			session.setAttribute("applicationSLADetailsLD", ApplicationSLADetailsLD);
			session.removeAttribute("application_id");
			session.setAttribute("application_id",strApplicationId);
		}
		else
		{
			session.removeAttribute("applicationSLADetailsLD");
			vimsSLAForm.setCriticalResponseTime("");
			vimsSLAForm.setCriticalWarningInterval("");
			vimsSLAForm.setMajorResponseTime("");
			vimsSLAForm.setMajorWarningInterval("");
			vimsSLAForm.setMinorResponseTime("");
			vimsSLAForm.setMinorWarningInterval("");
		}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return actionMapping.findForward("SLASettings");
	}
	/*
	 * This method is used to create SLA for an application.This requirement is no more exists but for future reference it is not erased.
	*/
	/*public ActionForward createSLA(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		
		SLAActionForm vimsSLAForm = (SLAActionForm)actionForm;		
		String strApplicationId=(String)vimsSLAForm.getApplication();
		String strcriticalResponseTime=(String)vimsSLAForm.getCriticalResponseTime();
		String strcriticalWarningTime=(String)vimsSLAForm.getCriticalWarningInterval();
		String strmajorResponseTime=(String)vimsSLAForm.getMajorResponseTime();
		String strmajorWarningTime=(String)vimsSLAForm.getMajorWarningInterval();
		String strminorResponseTime=(String)vimsSLAForm.getMinorResponseTime();
		String strminorWarningTime=(String)vimsSLAForm.getMinorWarningInterval();
		boolean createSLADetails=VIMSSLABD.setApplicationSLADetailsBD(strApplicationId,strcriticalResponseTime,strcriticalWarningTime,strmajorResponseTime,strmajorWarningTime,strminorResponseTime,strminorWarningTime);		
		
		String strSuccess="Successfully Created SLA";
		String strFailure="Failed to create SLA";
		if(createSLADetails==true)
		{
			request.setAttribute("SLAMessage", strSuccess);
		}
		else
		{
			request.setAttribute("SLAMessage", strFailure);
		}
		vimsSLAForm.setApplication("");
		vimsSLAForm.setCriticalResponseTime("");
		vimsSLAForm.setCriticalWarningInterval("");
		vimsSLAForm.setMajorResponseTime("");
		vimsSLAForm.setMajorWarningInterval("");
		vimsSLAForm.setMinorResponseTime("");
		vimsSLAForm.setMinorWarningInterval("");
		return actionMapping.findForward("SLASettings");					
	}*/
	/*
	 * This method is used to modify the Existing SLA details of an application.
	*/
	public ActionForward changeButton(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{ 
		//SLAActionForm vimsSLAForm = (SLAActionForm)actionForm;		
		session=request.getSession(false);
		//String strApplicationId=(String)vimsSLAForm.getApplication();	
		session.setAttribute("changeBtn","");
		String strAppId=(String)session.getAttribute("application_id");
		System.out.println("--------strAppId-----in changeButton- action-----"+strAppId); 
		SLAActionForm vimsSLAForm = (SLAActionForm)actionForm;	
		vimsSLAForm.setApplication(strAppId);
		
		return actionMapping.findForward("SLASettings");
	}
	
	
	
	public ActionForward modifySLA(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{	
		session=request.getSession(false);
		if(session.getAttribute("changeBtn")!=null)
		{
			session.removeAttribute("changeBtn");
		}
		SLAActionForm vimsSLAForm = (SLAActionForm)actionForm;		
		String strApplicationId=(String)vimsSLAForm.getApplication();	
		String strcriticalResponseTime=(String)vimsSLAForm.getCriticalResponseTime();
		String strcriticalWarningTime=(String)vimsSLAForm.getCriticalWarningInterval();
		String strmajorResponseTime=(String)vimsSLAForm.getMajorResponseTime();		
		String strmajorWarningTime=(String)vimsSLAForm.getMajorWarningInterval();
		String strminorResponseTime=(String)vimsSLAForm.getMinorResponseTime();
		String strminorWarningTime=(String)vimsSLAForm.getMinorWarningInterval();
		boolean modifySLADetails=VIMSSLABD.modifyApplicationSLADetailsBD(strApplicationId,strcriticalResponseTime, strcriticalWarningTime, strmajorResponseTime, strmajorWarningTime, strminorResponseTime, strminorWarningTime);
		String ApplicationName=VIMSSLADAO.getApplicationNameDAo(strApplicationId);
		
		String strSuccess="SLA details updated sucessfully";
		String strFailure="Failed to Modify";
		if(modifySLADetails==true)
		{
			request.setAttribute("SLAMessage", strSuccess);
		}
		else
		{
			request.setAttribute("SLAMessage", strFailure);
		}
		vimsSLAForm.setApplication("");
		vimsSLAForm.setCriticalResponseTime("");
		vimsSLAForm.setCriticalWarningInterval("");
		vimsSLAForm.setMajorResponseTime("");
		vimsSLAForm.setMajorWarningInterval("");
		vimsSLAForm.setMinorResponseTime("");
		vimsSLAForm.setMinorWarningInterval("");
		if(session.getAttribute("applicationSLADetailsLD")!=null)
		{
			session.removeAttribute("applicationSLADetailsLD");
		}
		
		return actionMapping.findForward("SLASettings");		
	}
	/*
	 * This method is used to delete the Existing SLA details of an application.But this requirement is no more.For future references it is not erased
	*/
	/*public ActionForward deleteSLA(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		SLAActionForm vimsSLAForm = (SLAActionForm)actionForm;		
		String strApplicationId=(String)vimsSLAForm.getApplication();		
		boolean deleteApplicationSLADetails=VIMSSLABD.deleteApplicationSLADetailsBD(strApplicationId);
			
		if(deleteApplicationSLADetails==true)
		{
			request.setAttribute("SLAMessage", strSuccess);
		}
		else
		{
			request.setAttribute("SLAMessage", strFailure);
		}		
		vimsSLAForm.setCriticalResponseTime("");
		vimsSLAForm.setCriticalWarningInterval("");
		vimsSLAForm.setMajorResponseTime("");
		vimsSLAForm.setMajorWarningInterval("");
		vimsSLAForm.setMinorResponseTime("");
		vimsSLAForm.setMinorWarningInterval("");		
		return actionMapping.findForward("SLASettings");
	}*/
	@Override
	protected Map getKeyMethodMap() {
		HashMap methodKeyMap = new HashMap();		
		methodKeyMap.put("VIMS.SLA.SUBMIT", "createSLA");
		methodKeyMap.put("VIMS.SLA.createSLA", "createSLA");
		methodKeyMap.put("VIMS.SLA.MODIFY", "modifySLA");
		methodKeyMap.put("VIMS.SLA.modifySLA", "modifySLA");
		methodKeyMap.put("VIMS.SLA.DELETE", "deleteSLA");
		methodKeyMap.put("VIMS.SLA.deleteSLA", "deleteSLA");
		methodKeyMap.put("VIMS.SLA.getApplicationSLADetails", "getApplicationSLADetails");
		methodKeyMap.put("VIMS.SLA.getApplications", "getApplications");
		methodKeyMap.put("VIMS.SLA.changeButton", "changeButton");
		return methodKeyMap;
	}
}

