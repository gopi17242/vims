/**
 * 
 */
package com.vertex.VIMS.test.customer.action;

/**
 * @author saikumar.m
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import com.vertex.VIMS.test.customer.BD.VIMSCustomerBD;
import com.vertex.VIMS.test.customer.form.VIMSCustomerForm;

public class VIMSCustomerLookupDispatchAction extends LookupDispatchAction
{
	ArrayList arrayList=null;
	
	public ActionForward custListOfIssues(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{		
		VIMSCustomerForm vimsCustomerForm=(VIMSCustomerForm)actionForm;	
		
		
		HttpSession session=request.getSession(false);
		
		String custId=session.getAttribute("user").toString();
		
		arrayList=VIMSCustomerBD.getCustIssuesList(custId, vimsCustomerForm);
		System.out.println("=============size====="+arrayList.size());
		if(arrayList.size()!=0)
		{	
			session.setAttribute("custIssuesList", arrayList);
			return actionMapping.findForward("customerIssues");
		}
		else
		{
			session.setAttribute("custIssuesList", null);
			request.setAttribute("MyIssuesMessage","No Records Found");
			return actionMapping.findForward("customerIssues");
		}		
	}
	public ActionForward viewCustIssueDetails(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{		
		return actionMapping.findForward("");
	}
	public Map getKeyMethodMap()
	{
		HashMap methodKeyMap = new HashMap();
		
		methodKeyMap.put("VIMSApplication.custListOfIssues", "custListOfIssues");
				
		return methodKeyMap;
	}

}
