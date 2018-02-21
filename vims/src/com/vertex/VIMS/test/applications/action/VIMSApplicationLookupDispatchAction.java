package com.vertex.VIMS.test.applications.action; 
/**
 * @author saikumar.m
 * Created on Nov 7, 2008
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import com.vertex.VIMS.test.applications.BD.VIMSApplicationBD;
import com.vertex.VIMS.test.applications.form.VIMSApplicationForm;
public class VIMSApplicationLookupDispatchAction extends LookupDispatchAction
{
	Logger logger=null;
	/**
	 * viewApplicationList will retrieve the list of all applications
	 */	
	public ActionForward viewApplicationList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{		
		HttpSession session=request.getSession(true);
		
		VIMSApplicationForm vimsApplicationForm=(VIMSApplicationForm)actionForm;
		vimsApplicationForm.setApplications("Applications");
		
		String strLoginUser=null;
		strLoginUser=(String) session.getAttribute("user");
		System.out.println("==========Login User==in Applications page========"+strLoginUser);
		/*String strStatus="Active";
		System.out.println("=====form value========"+vimsApplicationForm.getAppStatus()); 
		if(vimsApplicationForm.getAppStatus()!=null)
		{
			if(!"".equals(vimsApplicationForm.getAppStatus()))
			{
				strStatus=vimsApplicationForm.getAppStatus();				
			}
		}
		else if(request.getParameter("status")!=null)
		{
			strStatus=request.getParameter("status");
			vimsApplicationForm.setAppStatus(strStatus);			
		}
		else
		{
			vimsApplicationForm.setAppStatus(strStatus);
		}*/
		//System.out.println("====status in action ====="+strStatus); 
		ArrayList arrayList = (ArrayList)VIMSApplicationBD.getApplicationsListBD(strLoginUser);
		//HttpSession session=request.getSession(false);
		session.removeAttribute("appSubCatList");
		session.removeAttribute("usrGrpList");
		session.removeAttribute("appCustList");
		session.removeAttribute("appSpecList");
		session.removeAttribute("applicationDetails");
		session.setAttribute("applicationDetails",arrayList);
		return actionMapping.findForward("applications");
	}
	/**
	 * forwardAddAppPage will forward to add application page
	 */	
	public ActionForward forwardAddAppPage(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session=request.getSession(false);
		String strLoginUser=null;
		strLoginUser=(String)session.getAttribute("user");
			saveToken(request);
			ArrayList supportCenterList=VIMSApplicationBD.getSupportCenterListBD(strLoginUser);			
			ArrayList empList=VIMSApplicationBD.getEmpIdListBD();
			ArrayList grpIdList=VIMSApplicationBD.getAppGroupListBD();
			ArrayList custIdList=VIMSApplicationBD.getCustIdListBD();
				
			session.setAttribute("custIdList", custIdList);
			session.setAttribute("empList",empList);
			session.setAttribute("supportCenterList", supportCenterList);
			session.setAttribute("grpIdList",grpIdList);
			return actionMapping.findForward("addApplication");
	}
	
	public ActionForward getAppOwnerAction(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{		
		String strCustId=request.getParameter("custId");
		ArrayList arrayList=VIMSApplicationBD.getAppOwnerBD(strCustId);
		HashMap hashMap=null; 
		System.out.println("======size======="+arrayList.size());
		StringBuffer sbufOwnerId=new StringBuffer();
		StringBuffer sbufOwnername=new StringBuffer();
		String strOwnerId="none";
		String strOwnerName="none";
		for(int i=0;i<arrayList.size();i++)
		{
			hashMap=(HashMap)arrayList.get(i);
			if(i!=arrayList.size()-1)
			{
				sbufOwnerId=sbufOwnerId.append((String)hashMap.get("ownerMail")+";");
				sbufOwnername=sbufOwnername.append((String)hashMap.get("ownerName")+";");
			}
			else
			{
				sbufOwnerId=sbufOwnerId.append((String)hashMap.get("ownerMail"));
				sbufOwnername=sbufOwnername.append((String)hashMap.get("ownerName"));
			}
			strOwnerId=sbufOwnerId.toString();
			strOwnerName=sbufOwnername.toString();
			System.out.println(strOwnerId+"===strOwnerId=====strOwnerName="+strOwnerName); 
		}
		try
		{
			
			response.setContentType("text/xml");        
	        response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			response.getOutputStream().println("<response>");
			response.getOutputStream().println("<result>"+strOwnerId+"</result>");
			response.getOutputStream().println("<result>"+strOwnerName+"</result>");
			response.getOutputStream().println("</response>"); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return null;
	}
	
	/**
	 * addApplication will create a new application.
	 */	
	public ActionForward addApplication(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
			VIMSApplicationForm vimsApplicationForm =(VIMSApplicationForm)actionForm;
						
			if(isTokenValid(request))
			{
				resetToken(request);					
				String filePath=(getServlet().getServletContext().getRealPath("/")+"VIMSUPLOAD").replace("\\.\\", "\\").replace("I_VIMSD\\I_VIMSD", "I_VIMSD");
				HttpSession session=request.getSession(false);
				String strUserId=(String)session.getAttribute("user");
				String resultMsg=VIMSApplicationBD.addApplicationBD(vimsApplicationForm,filePath,strUserId,request);
				System.out.println("===result msg in action ======"+resultMsg);
				if(resultMsg.contains("Saved Successfully")) 
				{
					request.setAttribute("resultMsg", "Application added successfully");
					return actionMapping.findForward("vimsApplicationAction");	
				}
				else
				{
					request.setAttribute("resultMsg", resultMsg);
					return actionMapping.findForward("addApplication");						
				}					
			}
			else
			{
				return actionMapping.findForward("vimsApplicationAction");
			}
	}
	/**
	 * populateModifyAppPage will retrieve details of a particular application and populate in modify application page.
	 */	
	public ActionForward populateModifyAppPage(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{	
		logger=Logger.getLogger("Admin");
		HttpSession session=request.getSession(true);
		String strLoginUser=null;
		strLoginUser=(String) session.getAttribute("user");
		try
		{
			VIMSApplicationForm vimsApplicationForm = (VIMSApplicationForm)actionForm;
			String appId=request.getParameter("appId");
			ArrayList arrayList = (ArrayList)VIMSApplicationBD.viewApplicationBD(appId,strLoginUser);//.getAppDetailsBD(appId);
			HashMap hashMap=(HashMap)arrayList.get(0);
			int i=((Integer)hashMap.get("sendMail")).intValue();
			vimsApplicationForm.setSendMail(i);
			ArrayList supportCenterList = VIMSApplicationBD.getSupportCenterListBD(strLoginUser);			
			ArrayList appList=VIMSApplicationBD.getAppIdListBD();
			String supportCenterId=(String)((HashMap)arrayList.get(0)).get("supportCenter");
			
			ArrayList custIdList=VIMSApplicationBD.getCustIdListBD();
			HashMap  customer=VIMSApplicationBD.getApplicationCustomerBD(appId);
			
			String strCustId=(String)customer.get("customerId");
			ArrayList ownerList=VIMSApplicationBD.getAppOwnerBD(strCustId);
			
			HashMap group=VIMSApplicationBD.getApplicationGroupBD(appId);
			String strGrpId=(String)group.get("grpId");
			
			ArrayList empList=VIMSApplicationBD.getGroupMembersBD(strGrpId);//.getEmpIdListBD();
			
			ArrayList grpIdList=VIMSApplicationBD.getSupportCenterManagerBD(supportCenterId);//.getAppGroupListBD();
			System.out.println("======grpList in action===="+grpIdList);
			ArrayList specialists = VIMSApplicationBD.getApplicationSpecialistBD(appId);
			ArrayList subCategories = VIMSApplicationBD.getApplicationSubCategoryDAO(appId);
			
			int filesCount=VIMSApplicationBD.getFilesCountBD(appId);
			request.setAttribute("filesCount",filesCount);
			
			//HttpSession session=request.getSession(false);
			String status=(String)((HashMap)arrayList.get(0)).get("appStatus");
			hashMap=(HashMap)arrayList.get(0);			
			
			String attachment1=(String)hashMap.get("attachment1");
			String attachment2=(String)hashMap.get("attachment2");
			String attachment3=(String)hashMap.get("attachment3");
			String attachment4=(String)hashMap.get("attachment4");
			String attachment5=(String)hashMap.get("attachment5");
			String filesUploaded=attachment1+";"+attachment2+";"+attachment3+";"+attachment4+";"+attachment5;			
			
			session.setAttribute("filesUploaded", filesUploaded);
			
			vimsApplicationForm.setAppStatus(status); 
			session.setAttribute("ownerList", ownerList);
			session.setAttribute("subCategories", subCategories);
			session.setAttribute("customer", customer);
			session.setAttribute("group", strGrpId);
			session.setAttribute("specialists", specialists);
			session.setAttribute("supportCenterList", supportCenterList);
			session.setAttribute("appDet",arrayList);
			session.setAttribute("custIdList", custIdList);
			session.setAttribute("empList",empList);
			session.setAttribute("appIdList",appList);
			session.setAttribute("grpIdList",grpIdList);			
			//System.out.println("======grpIdList====="+grpIdList); 
			return actionMapping.findForward("modifyPage");	
		}
		catch(Exception e)
		{
			logger.info("VIMSApplicationLookupDispatchAction.populateModifyAppPage()");
			logger.error(e);
			return actionMapping.findForward("applications");
		}
	}
	/**
	 * getSupportManagerAction will retrieve the Support manager for the given support center (using AJAX) to populate in add application page
	 */	
	public ActionForward getSupportManagerAction(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		String supportCenterId=request.getParameter("supCen");
		ArrayList arrayList=VIMSApplicationBD.getSupportCenterManagerBD(supportCenterId);
		String []supportManager=null;
		if(arrayList.size()>0)
		{
			supportManager=(String[])arrayList.get(0);
		}			
		//String []strGrpId=new String[arrayList.size()-1];
		//String []strGrpName=new String[arrayList.size()-1];
		String strGrpId="none";
		String strGrpName="none";
		StringBuffer sbufGrpId=new StringBuffer();
		StringBuffer sbufGrpName=new StringBuffer();
		HashMap hashMap=null; 
		System.out.println("======size======="+arrayList.size());
		for(int i=1;i<arrayList.size();i++)
		{
			hashMap=(HashMap)arrayList.get(i);
			//strGrpId[i-1]=(String)hashMap.get("grpId");
			//strGrpName[i-1]=(String)hashMap.get("grpName");
			if(i!=arrayList.size()-1)
			{
				sbufGrpId=sbufGrpId.append((String)hashMap.get("grpId")+";");
				sbufGrpName=sbufGrpName.append((String)hashMap.get("grpName")+";");
			}
			else
			{
				sbufGrpId=sbufGrpId.append((String)hashMap.get("grpId"));
				sbufGrpName=sbufGrpName.append((String)hashMap.get("grpName"));
			}
			strGrpId=sbufGrpId.toString();
			strGrpName=sbufGrpName.toString();
			System.out.println(strGrpId+"===strGrpId=====strGrpName="+strGrpName); 
		}
		try
		{			
			response.setContentType("text/xml");        
	        response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			response.getOutputStream().println("<response>");
			response.getOutputStream().println("<result>" + supportManager[0] + "</result>");
			response.getOutputStream().println("<result>" + supportManager[1] + "</result>");
			response.getOutputStream().println("<result>" + strGrpId + "</result>");
			response.getOutputStream().println("<result>" + strGrpName + "</result>");
			response.getOutputStream().println("</response>"); 
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}

		return null;
	}
	public ActionForward getGroupMembers(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		String strGrpId=request.getParameter("grpId");
		
		ArrayList arrayList=VIMSApplicationBD.getGroupMembersBD(strGrpId);
		String strEmpId="none";
		String strEmpName="none";
		StringBuffer sbufEmpId=new StringBuffer();
		StringBuffer sbufEmpName=new StringBuffer();
		HashMap hashMap=null; 
		System.out.println("======size======="+arrayList.size());
		
		for(int i=0;i<arrayList.size();i++)
		{
			hashMap=(HashMap)arrayList.get(i);
			if(i!=arrayList.size()-1)
			{
				sbufEmpId=sbufEmpId.append((String)hashMap.get("empid")+";");
				sbufEmpName=sbufEmpName.append((String)hashMap.get("name")+";");
			}
			else
			{
				sbufEmpId=sbufEmpId.append((String)hashMap.get("empid"));
				sbufEmpName=sbufEmpName.append((String)hashMap.get("name"));
			}
			strEmpId=sbufEmpId.toString();
			strEmpName=sbufEmpName.toString();
			System.out.println(strEmpId+"===strEmpId=====strEmpName="+strEmpName); 
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
		System.out.println("========calling action=======");
		return null;
	}
	/**
	 * modifyApplication will modify the selected application
	 */	
	public ActionForward modifyApplication(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{		
		VIMSApplicationForm vimsApplicationForm =(VIMSApplicationForm)actionForm;
		String filePath=(getServlet().getServletContext().getRealPath("/")+"VIMSUPLOAD").replace("\\.\\", "\\").replace("I_VIMSD\\I_VIMSD", "I_VIMSD");
		String resultMsg=VIMSApplicationBD.modifyApplicationBD(vimsApplicationForm,filePath);
		
		request.setAttribute("resultMsg", resultMsg);
		if(resultMsg.equals("Saved Successfully"))
		{
			request.setAttribute("resultMsg", "Application details updated successfully");
			return actionMapping.findForward("vimsApplicationAction");
		}
		return actionMapping.findForward("modifyPage");					
	}
	/**
	 * deleteApplication will delete the selected application
	 */	
	public ActionForward deleteApplication(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		String strAppId=request.getParameter("appId");
		
		String resultMsg=VIMSApplicationBD.deleteApplicationBD(strAppId);
		
		//request.setAttribute("resultMsg",resultMsg);
		
		return actionMapping.findForward("vimsApplicationAction");
		
	}
	/**
	 * viewApplication will retrieve the details of selected application
	 */	
	public ActionForward viewApplication(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session=request.getSession(false);		
		String strAppId = request.getParameter("appId");
		String strLoginUser=(String) session.getAttribute("user");
		ArrayList arrayList=VIMSApplicationBD.viewApplicationBD(strAppId,strLoginUser);
		HashMap  customer=VIMSApplicationBD.getApplicationCustomerBD(strAppId);
		HashMap group=VIMSApplicationBD.getApplicationGroupBD(strAppId);
		ArrayList subCategories=VIMSApplicationBD.getApplicationSubCategoryDAO(strAppId);
		ArrayList specialists=VIMSApplicationBD.getApplicationSpecialistBD(strAppId);
		
		HashMap SLADetailsOfApplications=VIMSApplicationBD.getSLADetailsOfApplicationsBD(strAppId);
		
		//set the application flag as viewed 
		VIMSApplicationBD.setApplicationFlagBD(strAppId);
		
		session.setAttribute("subCategories", subCategories);
		session.setAttribute("customer", customer);
		session.setAttribute("group", group);
		session.setAttribute("specialists", specialists);
		session.setAttribute("appView", arrayList);
		return actionMapping.findForward("ViewApplication"); 
	}
	/**
	 * applicationSpecialist will retrieve the list of all application specialist.
	 */	
	public ActionForward applicationSpecialist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		ArrayList appSpecList = VIMSApplicationBD.getAppSpecialistsBD();
		
		HttpSession session=request.getSession(false);
		session.removeAttribute("appSubCatList");
		session.removeAttribute("usrGrpList");
		session.removeAttribute("appCustList");
		session.removeAttribute("appSpecList");
		session.removeAttribute("applicationDetails");
		session.setAttribute("appSpecList",appSpecList);		
		
		return actionMapping.findForward("applications");
	}
	/**
	 * applicationModules will retrieve the list of application modules.
	 */	 
	public ActionForward applicationModules(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		ArrayList appSubCatList=VIMSApplicationBD.getAppSubCatListBD();
		HttpSession session=request.getSession(false);
		session.removeAttribute("appSubCatList");
		session.removeAttribute("usrGrpList");
		session.removeAttribute("appCustList");
		session.removeAttribute("appSpecList");
		session.removeAttribute("applicationDetails");
		session.setAttribute("appSubCatList",appSubCatList);
		
		return actionMapping.findForward("applications");
	}
	/**
	 * applicationGroups will retrieve the list of all application groups
	 */	
	public ActionForward applicationGroups(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		ArrayList usrGrpList=VIMSApplicationBD.getUsrGrpListBD();
		HttpSession session=request.getSession(false);
		session.removeAttribute("appSubCatList");
		session.removeAttribute("usrGrpList");
		session.removeAttribute("appCustList");
		session.removeAttribute("appSpecList");
		session.removeAttribute("applicationDetails");
		session.setAttribute("usrGrpList",usrGrpList);
		
		return actionMapping.findForward("applications");
	}
	public ActionForward applicationCustomers(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		ArrayList appCustList=VIMSApplicationBD.getApplicationCustomerListBD();
		HttpSession session=request.getSession(false);
		session.removeAttribute("appSubCatList");
		session.removeAttribute("usrGrpList");
		session.removeAttribute("appCustList");
		session.removeAttribute("appSpecList");
		session.removeAttribute("applicationDetails");
		session.setAttribute("appCustList", appCustList);
		
		return actionMapping.findForward("applications");
	}
		
	/*
	 * getNewApplicationList() Method returns the applications which are not viewed by administrator till now
	 * 
	 *  
	 */
	public ActionForward getNewApplicationList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		        
		       Logger logger=null;
		       HttpSession session=null;
		       HashMap newApplicationList=null;
		       String strLoginId=null;
		 try { //try block starts
			 
			   session=request.getSession(false);
			   if(session!=null) { // If starts 
				   logger=Logger.getLogger("Admin");
				   strLoginId=(String)session.getAttribute("user");
				   newApplicationList=VIMSApplicationBD.getNewApplicationsAddedBD(strLoginId);
				   if(newApplicationList!=null) { //If block starts
					     session.setAttribute("applicationCount",newApplicationList.get("applicationCount"));
						 session.setAttribute("applicationDetails",newApplicationList.get("applicationList"));
				   } //End of If block
				     return actionMapping.getInputForward();  
			   } // If ends
			   else { //Else block starts
				   return actionMapping.findForward("errorPage");
			   } //end of Else block
	    	} // try block ends
		    catch(Exception exception) {
	    		  logger.info("-----Exception in  getNewApplicationList action method-----");
			      logger.error(exception);
		     } // End of catch block
		   return actionMapping.findForward("errorPage");
	}
	// End of getNewApplicationList action
	
	public ActionForward checkAppName(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		String strAppName=request.getParameter("appName");
		boolean result=VIMSApplicationBD.checkAppAvailabilityBD(strAppName);
		try
		{
			String strMsg="";
			if(result==true)
			{
				strMsg="Application name already exists";
			}
			else
			{
				strMsg=".";
			}
			response.setContentType("text/xml");        
	        response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			response.getOutputStream().println("<response>");
			response.getOutputStream().println("<result>"+strMsg+"</result>");
			response.getOutputStream().println("</response>"); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return null;
	}
	public ActionForward deleteFile(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		String filePath=request.getParameter("filePath");
		try
		{
			
			filePath=(getServlet().getServletContext().getRealPath(filePath)).replace("\\.\\", "\\").replace("I_VIMSD\\I_VIMSD", "I_VIMSD");
			String fileName=filePath.substring(filePath.lastIndexOf("\\")+1); 
			System.out.println("========fileName======"+fileName); 
			System.out.println("====deleting file at "+filePath); 
			 File file = new File(filePath);
			 HttpSession session=request.getSession(false);
			 ArrayList arrayList =(ArrayList)session.getAttribute("appDet");
			 System.out.println("====arraylist====="+arrayList); 
			 HashMap hashMap=(HashMap)arrayList.get(0);
			 System.out.println("=========file nname in hashmap==="+(String)hashMap.get("attachment1")); 
			 System.out.println("=========file nname in hashmap==="+(String)hashMap.get("attachment2")); 
			 System.out.println("=========file nname in hashmap==="+(String)hashMap.get("attachment3")); 
			 System.out.println("=========file nname in hashmap==="+(String)hashMap.get("attachment4")); 
			 System.out.println("=========file nname in hashmap==="+(String)hashMap.get("attachment5")); 
			 int filesCount=0;
			 if(fileName.equalsIgnoreCase((String)hashMap.get("attachment1")))
			 {
				 hashMap.remove("attachment1");
				  filesCount=VIMSApplicationBD.deleteFileBD("Attchmnt1",(String)hashMap.get("appName"),(String)hashMap.get("appId"));
			 }
			 else if(fileName.equalsIgnoreCase((String)hashMap.get("attachment2")))
			 {
				 hashMap.remove("attachment2");
				  filesCount=VIMSApplicationBD.deleteFileBD("Attchmnt2",(String)hashMap.get("appName"),(String)hashMap.get("appId"));
			 }
			 else if(fileName.equalsIgnoreCase((String)hashMap.get("attachment3")))
			 {
				 hashMap.remove("attachment3");
				 filesCount=VIMSApplicationBD.deleteFileBD("Attchmnt3",(String)hashMap.get("appName"),(String)hashMap.get("appId"));
			 }
			 else if(fileName.equalsIgnoreCase((String)hashMap.get("attachment4")))
			 {
				 hashMap.remove("attachment4");
				 filesCount=VIMSApplicationBD.deleteFileBD("Attchmnt4",(String)hashMap.get("appName"),(String)hashMap.get("appId"));
			 }
			 else
			 {
				 hashMap.remove("attachment5");
				  filesCount=VIMSApplicationBD.deleteFileBD("Attchmnt5",(String)hashMap.get("appName"),(String)hashMap.get("appId"));
			 }
			 
			 session.removeAttribute("appDet");
			 arrayList.add(hashMap);
			 session.setAttribute("appDet", arrayList);
			 
			 String strMsg="";
			   boolean success = file.delete();

			      if (!success)
			      {
			      	  System.out.println("Deletion failed.");
			      	  strMsg="Deletion failed.";
			      }
			      else
			      {
			    	 System.out.println("Deleted Successfully"); 
			    	 strMsg="Deleted successfully";
			      }
			 request.removeAttribute("filesCount");
			 request.setAttribute("filesCount", filesCount); 
			     
			response.setContentType("text/xml");        
	        response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			response.getOutputStream().println("<response>");
			response.getOutputStream().println("<result>"+filesCount+"</result>");
			response.getOutputStream().println("</response>"); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return null;
	}
	
	public ActionForward uploadFiles(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		String filePath="";
		try
		{
			
			filePath=(getServlet().getServletContext().getRealPath(filePath)).replace("\\.\\", "\\").replace("I_VIMSD\\I_VIMSD", "I_VIMSD");
			String fileName=filePath.substring(filePath.lastIndexOf("\\")+1); 
			String strMsg="Success";
			
			     System.out.println("======in action upload"); 
			response.setContentType("text/xml");        
	        response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			response.getOutputStream().println("<response>");
			response.getOutputStream().println("<result>"+strMsg+"</result>");
			response.getOutputStream().println("</response>"); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return null;
	}
		
	public ActionForward searchApplication(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		VIMSApplicationForm vimsApplicationForm=(VIMSApplicationForm)actionForm;
		String appKey=request.getParameter("searchKey");
		String custKey=request.getParameter("custName");
		String strStatus="Active";
		String strLoginUser=null;
		HttpSession session=request.getSession(true);
		strLoginUser=(String) session.getAttribute("user");
		System.out.println("=====form value========"+vimsApplicationForm.getAppStatus()); 
		if(vimsApplicationForm.getAppStatus()!=null)
		{
			if(!("".equals(vimsApplicationForm.getAppStatus())))
			{
				strStatus=vimsApplicationForm.getAppStatus();				
			}
		}
		System.out.println("=======application name=============="+appKey);	
		System.out.println("=======customer name=============="+custKey);
		
		ArrayList arrayList=VIMSApplicationBD.searchApplicationNameBD(appKey,custKey,strLoginUser);
		vimsApplicationForm.setAppStatus(strStatus);
		System.out.println("arraylist in action===="+arrayList); 
		if(arrayList.size()==0)
		{
			request.setAttribute("searchMsg","No records found");
		}
		else
		{
			//HttpSession session = request.getSession(false);
			session.removeAttribute("applicationDetails");
			session.setAttribute("applicationDetails",arrayList);
		}		
		return actionMapping.findForward("applications");
	}	
	/**
	 * getKeyMethodMap will map the methods with keys n the properties file.
	 */	
	public Map getKeyMethodMap()
	{
		HashMap<String, String> methodKeyMap = new HashMap<String, String>();
		
		methodKeyMap.put("VIMSApplication.getAppOwnerAction","getAppOwnerAction");
		methodKeyMap.put("VIMSApplication.searchApplication","searchApplication");
		methodKeyMap.put("VIMSApplication.getGroupMembers","getGroupMembers");
		methodKeyMap.put("VIMSApplication.deleteFile","deleteFile");
		methodKeyMap.put("VIMSApplication.uploadFiles","uploadFiles");
		methodKeyMap.put("VIMSApplication.addApplication", "addApplication");
		methodKeyMap.put("VIMSApplication.populateModifyAppPage", "populateModifyAppPage");
		methodKeyMap.put("VIMSApplication.modifyApplication", "modifyApplication");
		methodKeyMap.put("VIMSApplication.deleteApplication", "deleteApplication");
		methodKeyMap.put("VIMSApplication.viewApplication", "viewApplication");
		methodKeyMap.put("VIMSApplication.applicationSpecialist", "applicationSpecialist");
		methodKeyMap.put("VIMSApplication.applicationModules", "applicationModules");
		methodKeyMap.put("VIMSApplication.applicationGroups", "applicationGroups");
		methodKeyMap.put("VIMSApplication.applicationCustomers", "applicationCustomers");
		methodKeyMap.put("VIMSApplication.addApplicationSpecialist", "addApplicationSpecialist");
		methodKeyMap.put("VIMSApplication.viewApplicationSpecialist", "viewApplicationSpecialist");
		methodKeyMap.put("VIMSApplication.addAppSubCategories","addAppSubCategories");
		methodKeyMap.put("VIMSApplication.viewAppSubCategories","viewAppSubCategories");
		methodKeyMap.put("VIMSApplication.addAppGroup","addAppGroup");
		methodKeyMap.put("VIMSApplication.addApplicationCustomer","addApplicationCustomer");
		methodKeyMap.put("VIMSApplication.forwardAddAppPage","forwardAddAppPage");
		methodKeyMap.put("VIMSApplication.viewApplicationList","viewApplicationList");
		methodKeyMap.put("VIMSApplication.getSupportManagerAction","getSupportManagerAction");
		methodKeyMap.put("VIMSApplication.getSuppMngrAction","getSuppMngrAction");
		methodKeyMap.put("VIMSApplication.getSLAcreatedDetailsOfApplication","getSLAcreatedDetailsOfApplication");
		methodKeyMap.put("VIMSApplication.viewSLAcreatedDetailsOfApplication","viewSLAcreatedDetailsOfApplication");
		methodKeyMap.put("VIMSApplication.viewNewApplicationList","getNewApplicationList");
		methodKeyMap.put("VIMSApplication.checkAppName","checkAppName");
		
		return methodKeyMap;
	}
}