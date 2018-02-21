/* 
 * @author: santhosh.k
 * @creation date:  Nov 13, 2008
 * @file name : VIMSSLABD.java
 * @description: 
 * 			It is the Business Delegate class  of the SLA page.It is called when the user clicks on the SLA Tab.
*/
package com.vertex.VIMS.test.SLA.BD;
 
import com.vertex.VIMS.test.SLA.DAO.VIMSSLADAO;

import java.util.*;

public class VIMSSLABD {
	/*
	 * This is the business delegate method to retrieve the list of applications
	*/
	public static ArrayList getApplicationNamesBD(String strLoginUser)
	{
		ArrayList ApplicationNames=VIMSSLADAO.getApplicationNames(strLoginUser);
	return ApplicationNames;
	}
	/*
	 * This is the business delegate method to retrieve the SLA details  of selected application
	*/
	public static HashMap getApplicationSLADetailsBD(String strApplicationName)
	{
		HashMap ApplicationSLADetailsBD=VIMSSLADAO.getApplicationSLADetailsDAO(strApplicationName);
		return ApplicationSLADetailsBD;
	}
	/*
	 * This is the business delegate method to set or create SLA details of an application
	*/
	public static boolean setApplicationSLADetailsBD(String strApplicationId, String strcriticalResponseTime,String strcriticalWarningTime,String strmajorResponseTime, String strmajorWarningTime, String strminorResponseTime, String strminorWarningTime)
	{
		boolean createSLADetails=VIMSSLADAO.setApplicationSLADetailsDAO(strApplicationId, strcriticalResponseTime, strcriticalWarningTime, strmajorResponseTime,strmajorWarningTime,strminorResponseTime,strminorWarningTime);
		return createSLADetails;
	}
	/*
	 * This is the business delegate method to delete SLA details of an application
	*/
	public static boolean deleteApplicationSLADetailsBD(String strApplicationId) {
		
		boolean deleteSLADetails=VIMSSLADAO.deleteApplicationSLADetailsDAO(strApplicationId);
		return deleteSLADetails;
	}
	/*
	 * This is the business delegate method to modify SLA details of an application
	*/
	public static boolean modifyApplicationSLADetailsBD(String strApplicationId, String strcriticalResponseTime,String strcriticalWarningTime,String strmajorResponseTime, String strmajorWarningTime, String strminorResponseTime, String strminorWarningTime)
	{
		boolean modifySLADetails=VIMSSLADAO.modifyApplicationSLADetailsDAO(strApplicationId, strcriticalResponseTime, strcriticalWarningTime, strmajorResponseTime,strmajorWarningTime,strminorResponseTime,strminorWarningTime);
		return modifySLADetails;
	}
	
	
}
