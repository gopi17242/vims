/*
 FileName	    : VIMSMyToDoListBD.java


 Description	: This  VIMSMyToDoListBD Object class has different methods that are called from the Controller. The mehods of this class inturn call method of DAO Class. The purpose of Business delegate class is to act as a mediator  class between the DAO and the controller. 
 Developed by  : Vertex Computer Systems.
 copyright (c) 2008 Vertex.
 All rights reserved.

 Change History:

 Revision No.	:		Date		  @author		Description
 1.0				Friday 26,2008	  Sudhir.D	   Initial Version.*/

package com.vertex.VIMS.test.mytodolist.BD;

import java.util.ArrayList;
import com.vertex.VIMS.test.mytodolist.DAO.*;



public class VIMSMyToDoListBD {

	/**
	 * Description:This method gets/returns the list of issues pertaining to a user.
	 * @param  strUserId is passed that it can be used in the query.
	 * @return ArrayList of Issues.
	 */
	public static ArrayList getMyToDoListOfIssues( String strUserId)
	{
		//System.out.println("============In My To Do List BD ==============strUserId========="+strUserId);
		return VIMSMyToDoListDAO.getMyToDoListOfIssues( strUserId);
	}
	
}
