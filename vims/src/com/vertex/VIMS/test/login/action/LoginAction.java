package com.vertex.VIMS.test.login.action;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import org.apache.struts.validator.*;

import com.vertex.VIMS.test.SLA.BD.VIMSSLABD;
import com.vertex.VIMS.test.applications.BD.VIMSApplicationBD;
import com.vertex.VIMS.test.clients.BD.VIMSClientBD;
import com.vertex.VIMS.test.home.BD.VIMSHomeBD;
import com.vertex.VIMS.test.ldap.BD.ConfigBD;
import com.vertex.VIMS.test.login.BD.*;
import com.vertex.VIMS.test.login.DAO.LoginDAO;
 
import com.vertex.VIMS.test.listofissues.BD.ListofIssuesBD;
import java.util.ArrayList;
 
public class LoginAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	 {
		
		String applicationVersion;
		String strIssueId=null;
		String strUserName=null;
		HttpSession session=null;
		String strLoginUser=null;
		
		DynaValidatorForm dvform = (DynaValidatorForm) form;
		Logger logger=Logger.getLogger("Admin");
		logger.info("========This is aditya Info==========");
		
		int intLoginIDNumber=0;
		
		session = request.getSession();
		
		String strUserId = (String) dvform.get("userId");
		String strPassword = (String) dvform.get("password");
      
		String strUserType=LoginBD.UserType(strUserId,strPassword);
		strUserName=LoginDAO.getUserName(strUserId);
	    
		//System.out.println("----user type &&&&&&&&&&&&"+strUserType);
		//System.out.println("----user name &&&&&&&&&&&&"+strUserName);
		
		ArrayList ApplicationNames=VIMSSLABD.getApplicationNamesBD(strUserId);
		
		session.setAttribute("ApplicationsList", ApplicationNames);
		if(strUserType.equals("Admin"))
		{
			intLoginIDNumber=LoginBD.getLoginIDNumber(strUserId,strPassword);
			session.setAttribute("LoginIDNumber",intLoginIDNumber);
			
			session.setAttribute("user", strUserId);
			session.setAttribute("UserName", strUserName);
			session.setAttribute("Role", strUserType);		
			
			LoginBD.applyCustomSettings(request, response, strUserId);
			
		
		}
		else if(strUserType.equals("Supervisor")||strUserType.equals("Manager")) 
		{
			intLoginIDNumber=LoginBD.getLoginIDNumber(strUserId,strPassword);
			session.setAttribute("LoginIDNumber",intLoginIDNumber);
			
			session.setAttribute("user", strUserId);
			session.setAttribute("UserName", strUserName);
			session.setAttribute("Role", strUserType);		
		}
		 else if (strUserType.equals("User"))
		{			
			 intLoginIDNumber=LoginBD.getLoginIDNumber(strUserId,strPassword);
				session.setAttribute("LoginIDNumber",intLoginIDNumber);
				
			 //System.out.println("-----------strUserId-------"+strUserId);
			HashMap hashMap=VIMSHomeBD.displayUserAssignedissuesBD(strUserId);
			if(hashMap!=null && hashMap.size()>0){
			session.setAttribute("AssignCount", hashMap.get("assignedIssuesCount"));
			session.setAttribute("OpenCount", hashMap.get("openIssuesCount"));
			
			//System.out.println("========assignedIssuesCount=="+hashMap.get("assignedIssuesCount")); 
			//System.out.println("========openIssuesCount=="+hashMap.get("openIssuesCount"));
			}
			intLoginIDNumber=LoginBD.getLoginIDNumber(strUserId,strPassword);
			session.setAttribute("LoginIDNumber",intLoginIDNumber);
			
			session.setAttribute("UserName", strUserName);
			session.setAttribute("user", strUserId);
			session.setAttribute("Role", strUserType);		
		}
		
		else if (strUserType.equalsIgnoreCase("Customer")) 
		{
			intLoginIDNumber=LoginBD.getLoginIDNumber(strUserId,strPassword);
			session.setAttribute("LoginIDNumber",(Integer)intLoginIDNumber);
			
			session.setAttribute("user", strUserId);
			session.setAttribute("Role", strUserType);
			session.setAttribute("UserName", strUserName);
			
			String strAppOwnerName=LoginBD.getAppOwnerNameBD(strUserId);
			session.setAttribute("ApplicationOwnerName", strAppOwnerName);
			//System.out.println("-------ApplicationOwnerName------"+strAppOwnerName);
			
			String strStatus=LoginBD.viewLoginClientStatusBD(strUserId, strPassword);			
			session.setAttribute("status", strStatus);
            
			if(strStatus!=null && strStatus.equals("0")){				
				return mapping.findForward("home1");
			}				      
			
		}
		else if(strUserType.equals("Invalid User Id"))
		{
			request.setAttribute("Invalid","Invalid User Name/Password");			
			return mapping.findForward("login");
		}
		else if(strUserType.equals("Invalid password"))
		{
			request.setAttribute("Invalid","Invalid User Name/Password");			
			return mapping.findForward("login");
		}
		 if(strUserType.equalsIgnoreCase("user"))
			 strUserType="Internal";
		 String strRoleId=LoginBD.getRoleIdBD(strUserType);
		 System.out.println("strRoleId========="+strRoleId);
		ArrayList list=LoginBD.getUserNavigSettingsBD(strUserId);
		 session.setAttribute("testList",list);
		strIssueId=(String)session.getAttribute("IssueId");
			if((strIssueId!=null)&&(!strIssueId.equals(""))) {
					    
				int testResult;
				
				   if(!strUserType.equals("admin")) {			   
					   testResult=LoginBD.validateViewPermissionBD(strUserId, strIssueId);  
				   }
				   else {
				   testResult=1;
				   } 
				   if(testResult==1) {
				   try {		
					      response.sendRedirect("./ListofIssues.do?methodname=IssueDetails&id="+strIssueId);
					   }
					catch(Exception exception) {
						exception.printStackTrace();
					}
			     }
				   else {
					   return mapping.findForward("NoAuthorizationPage");
				   }
			}
			else {
				   return mapping.findForward("home");
			} 
		return mapping.findForward("login");
	  }
}