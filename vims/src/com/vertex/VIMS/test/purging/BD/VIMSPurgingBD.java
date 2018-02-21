/*
 FileName	    : VIMSPurgingBD.java


 Description	: This  VIMSPurgingBD Object class is has methods which are called from the Controller class. It is just a intermediate class between the DAO and the Controller.
 				  It has methods which call the DAO class Object.
  
 Developed by  : Vertex Computer Systems.
 copyright (c) 2008 Vertex.
 All rights reserved.

 Change History:

 Revision No.	:		Date		  @author		Description
 1.0				Friday 21,2008	  Sudhir.D	   Initial Version.*/
package com.vertex.VIMS.test.purging.BD;

import com.vertex.VIMS.test.purging.DAO.VIMSPurgingDAO;
import com.vertex.VIMS.test.purging.form.PurgingFormBean;

public class VIMSPurgingBD {
	
	/**
	 * Description: This method is calls a method of DAO class and returns a message what it gets from the DAO class method. 
	 * @param form
	 * @param employeeId
	 * @return String message from the DAO Class method.
	 */
	public static String purgingOperation(PurgingFormBean form, String employeeId)
	{ 
		return VIMSPurgingDAO.purgingOperation(form,employeeId);
	}
}
