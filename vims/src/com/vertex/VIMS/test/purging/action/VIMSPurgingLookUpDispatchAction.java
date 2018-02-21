/*
 FileName	    : VIMSPurgingLookUpDispatchAction.java


 Description	: This  VIMSPurgingLookUpDispatchAction Object class has different methods that are called based on mapping in the getKeyMap() of the same  class. The main purpose of this class is to act as controller class for all the urls related to the Purging activity. 
 Developed by  : Vertex Computer Systems.
 copyright (c) 2008 Vertex.
 All rights reserved.

 Change History:

 Revision No.	:		Date		  @author		Description
 1.0				Friday 21,2008	  Sudhir.D	   Initial Version.*/
package com.vertex.VIMS.test.purging.action;

import org.apache.*;
import org.apache.struts.actions.*;
import org.apache.struts.action.*;

import com.vertex.VIMS.test.employee.BD.VIMSEmployeeBD;
import com.vertex.VIMS.test.employee.form.EmployeeForm;
import com.vertex.VIMS.test.employee.form.NewEmployeeFormBean;
import com.vertex.VIMS.test.purging.BD.VIMSPurgingBD;
import com.vertex.VIMS.test.purging.form.PurgingFormBean;

import javax.servlet.http.*;
import javax.servlet.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;

/*import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WritableCell;*/

public class VIMSPurgingLookUpDispatchAction extends LookupDispatchAction {
	/* (non-Javadoc)
	 * @see org.apache.struts.actions.LookupDispatchAction#getKeyMethodMap()
	 * Description: This is a builtin method which maps a url to a method of this class.
	 */
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("VIMSApplication.purgingsubmit", "PurgeArchive");
		map.put("VIMSApplication.purgingPage", "PurgingPage");

		return map;
	}

	/**
	 * Description: This method is to display purging page when the purging-tab is clicked.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return purgingForward (see Struts-config.xml)
	 */
	public ActionForward PurgingPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { // EmployeeForm
	//	System.out.println("============ In the VIMSPurgingLookUpDispatchAction class Purging Page method()============");
		
		return mapping.findForward("PurgingForward");
	}

	/**
	 * Description: This method sends the form data to the and sets a purging message which is returned from the BD class in the session.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  PurgingForward (see Struts-config.xml)
	 */
	public ActionForward PurgeArchive(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { // EmployeeForm
		//System.out.println("============ In the VIMSPurgingLookUpDispatchAction class PurgeArchive method()=============");
		// Logger logger=Logger.getLogger("Purging");
		PurgingFormBean formData = (PurgingFormBean) form;
		HttpSession session = request.getSession(false);
		String strUserId = (String) session.getAttribute("user");

		String Message = VIMSPurgingBD.purgingOperation(formData, strUserId);
		request.setAttribute("purgingmessage", Message);
		System.out.println("=====================In action session attribute Purging is set======================="+ Message);
		// logger.info("In action session attribute employeesDetails is set");

		return mapping.findForward("PurgingForward");
	}

}
