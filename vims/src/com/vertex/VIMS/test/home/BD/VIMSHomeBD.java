/*
 * Author: santhosh.k
 * Creation date: 11/19/2008
 * File Name : VIMSHomeBD.java
 * Description: 
 * 			This is the Business Delegate class of the home page module.This is called by the action based on the request.
 * 
*/
package com.vertex.VIMS.test.home.BD;

import java.util.ArrayList;
import java.util.HashMap;
import com.vertex.VIMS.test.SLA.DAO.VIMSSLADAO;
import com.vertex.VIMS.test.home.DAO.VIMSHomeDAO;

public class VIMSHomeBD {

	/*
	 * This method calls the getHomePageSLADAO of VIMSHomeDAO class to get SLA details  and open issues details of an  application
	 * 
	*/
	public static HashMap getHomeApplicationOpenAndSLABD(String strApplicationId) {
		HashMap HomeSLADetailsBD=VIMSHomeDAO.getHomeApplicationOpenAndSLADAO(strApplicationId);
		System.out.println("getHomePageSLABD in  VIMSHomeBD class "+HomeSLADetailsBD);
		return HomeSLADetailsBD;				
	}
	
	
	/*
	 * This method calls the displayHomeOpenissuesDAo of VIMSHomeDAO class to display list of open issues of an  application
	 * 
	*/
	
	public static ArrayList displayHomeOpenissuesBD(String strApplicationId,String strIncidentType)
	{
		ArrayList OpenIssuesDisplay=VIMSHomeDAO.displayHomeOpenissuesDAO(strApplicationId,strIncidentType);
		return OpenIssuesDisplay;
	}

	public static ArrayList displayUserIssuesBD(String strUserID)
	{
		ArrayList UserOpenIssuesDisplay=VIMSHomeDAO.displayUserIssuesDAO(strUserID);
		System.out.println("--------------In displayUserissuesBD--------------");
		return UserOpenIssuesDisplay;
	}
	public static HashMap displayUserAssignedissuesBD(String strUserId)
	{
		System.out.println("--------------In displayUserAssignedissuesBD--------------");
		System.out.println("--------------strUserID--------------"+strUserId);
		HashMap UserAssignedIssuesDisplay=VIMSHomeDAO.displayUserAssignedissuesDAO(strUserId);
		
		return UserAssignedIssuesDisplay;
	}

}
