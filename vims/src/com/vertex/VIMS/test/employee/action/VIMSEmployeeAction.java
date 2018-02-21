package com.vertex.VIMS.test.employee.action;

import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import org.apache.log4j.*;

import org.apache.struts.action.*;

import com.vertex.VIMS.test.employee.BD.VIMSEmployeeBD;

public class VIMSEmployeeAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response)
	{	
		
		System.out.println("\n\n\n at the starting action session attribute employeesDetails is set");
		//Logger logger=Logger.getLogger("Employee");
		HttpSession session=request.getSession(false);
		//ArrayList employeesDetails=VIMSEmployeeBD.getAllEmployeeDetails();
		//session.setAttribute("Details",employeesDetails);
		//System.out.println("=====================In action session attribute employeesDetails is set======================="+employeesDetails);
		//logger.info("In action session attribute employeesDetails is set");
		
		
		return mapping.findForward("testEmployee");
	}
}
