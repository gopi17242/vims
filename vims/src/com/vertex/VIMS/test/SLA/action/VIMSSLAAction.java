

package com.vertex.VIMS.test.SLA.action;

import org.apache.struts.action.*;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.validator.DynaValidatorActionForm;
import org.apache.struts.validator.DynaValidatorForm;

import javax.servlet.http.*;
import com.vertex.VIMS.test.SLA.BD.*;

import java.util.*;

public class VIMSSLAAction extends Action 
{
	
			public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
			{
				System.out.println("in VIMSSLAACTION class");
				String strChangeType=request.getParameter("ChangeType");
				System.out.println("==========strChangeType=========="+strChangeType);
				String strLoginUser=null;
				HttpSession session = request.getSession(false);
				strLoginUser=(String)session.getAttribute("user");
				if(strChangeType==null)
				{
				ArrayList ApplicationNames=VIMSSLABD.getApplicationNamesBD(strLoginUser);
				System.out.println("ArrayList in VIMSSLAACTION class"+ApplicationNames);
				
				session.setAttribute("Applications", ApplicationNames);				
				return actionMapping.findForward("SLASettings");
				}
				else if(strChangeType.equalsIgnoreCase("changed"))
				{
					System.out.println("==========strChangeType=========="+strChangeType);
					try{				
					
						DynaValidatorForm vimsSLAForm = (DynaValidatorForm)actionForm;
					
					System.out.println("object of DynaValidatorActionForm "+vimsSLAForm);
					
					String strApplicationId=(String)vimsSLAForm.get("application");
					
					System.out.println("Application Id in getApplicationSLADetails"+strApplicationId);
					
					HashMap ApplicationSLADetailsLD=VIMSSLABD.getApplicationSLADetailsBD(strApplicationId);
					
					System.out.println("ApplicationSLADetailsLDAction  in getApplicationSLADetails of  VIMSSLALookUpDispatchAction"+ApplicationSLADetailsLD);
					System.out.println("ArrayList size is : "+ApplicationSLADetailsLD.size());
					
						if((ApplicationSLADetailsLD!=null) && (ApplicationSLADetailsLD.size()!=0))
								
						{
						request.setAttribute("applicationSLADetailsLD", ApplicationSLADetailsLD);						
						}
						else
						{
							vimsSLAForm.set("criticalResponseTime","");
							vimsSLAForm.set("criticalWarningInterval","");
							vimsSLAForm.set("majorResponseTime","");
							vimsSLAForm.set("majorWarningInterval","");
							vimsSLAForm.set("minorResponseTime","");
							vimsSLAForm.set("minorWarningInterval","");
							
							
						}
					}
					
					catch(Exception exception)
					{
						System.out.println("Exception occurred in getApplicationSLADetails of VIMSSLALookUpDispatchAction");
						exception.printStackTrace();
					}
				}
					
					return actionMapping.findForward("SLASettings");				
		}
}