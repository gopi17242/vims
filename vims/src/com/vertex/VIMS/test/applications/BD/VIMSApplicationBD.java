/*
 * Created on Nov 7, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.vertex.VIMS.test.applications.BD;


import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.vertex.VIMS.test.SLA.DAO.VIMSSLADAO;
import com.vertex.VIMS.test.applications.DAO.VIMSApplicationDAO;
import com.vertex.VIMS.test.applications.form.VIMSApplicationForm;
import com.vertex.VIMS.test.common.DBConnection;
/**
 * @author saikumar.m
 *
 * VIMSApplicationBD is a Bussiness Delegate Method that delegates task to the corresponding DAO method.
 */
public class VIMSApplicationBD 
{
	//Declaring a static Logger class reference to be accessible from static methods. 
	static Logger logger=null;
	
	
	public static ArrayList getAppOwnerBD(String strCustId)
	{
		return VIMSApplicationDAO.getAppOwnerDAO(strCustId);
	}
	/**
	 * @param String
	 * @return String
	 *getSupportCenterListBD method will get the list of all Support Centers
	 */	
	public static ArrayList getSupportCenterListBD(String strLoginUser)
	{
		ArrayList supportCenterList=VIMSApplicationDAO.getSupportCenterListDAO(strLoginUser);
		return supportCenterList;
	}
	/**
	 * @param String
	 * @return String
	 *getSupportCenterManagerBD gets the support manager of given support center
	 */	
	public static ArrayList getSupportCenterManagerBD(String supportCenterId)
	{
		ArrayList arrayList=VIMSApplicationDAO.getSupportCenterManagerDAO(supportCenterId);
		System.out.println("======grpList in BD===="+arrayList); 
		return arrayList;
	}
		/**
	 * @param strLoginUser 
		 * @param 
	 * @return ArrayList
	 *getApplicationsListBD() is a method to retrieve the list of all Applications.
	 */	
	public static ArrayList getApplicationsListBD(String strLoginUser)
	{
		//creating a Logger class object to log errors in a log file.
		logger=Logger.getLogger("Admin");
		 
		//creating an ArrayList object which holds the list of application retrieved from the database through DAO class.
		ArrayList appList=VIMSApplicationDAO.getApplicationsListDAO(strLoginUser);
		
		return appList;	
	}
	
	public static ArrayList searchApplicationNameBD(String strAppName,String custName, String strLoginUser)
	{
		ArrayList arrayList=VIMSApplicationDAO.searchApplicationNameDAO(strAppName,custName,strLoginUser);
		return arrayList;
	}
	
	
	public static HashMap searchApplicationBD()
	{
		HashMap hashMap = VIMSApplicationDAO.searchApplicationDAO();
		
		return hashMap;
	}
	public static ArrayList getGroupMembersBD(String strGrpId)
	{
		return VIMSApplicationDAO.getGroupMembersDAO(strGrpId);
	}
	/**
	 * @param VIMSApplicationForm, String
	 * @return int
	 * addApplicationBD method will insert a new application in to the database.
	 */	
	public static String addApplicationBD(VIMSApplicationForm vimsApplicationForm,String filePath,String strUserId,HttpServletRequest request)
	{		
		//passing the new Application Details to be inserted to a DAO method.
		String resultMsg=VIMSApplicationDAO.addApplicationDAO(vimsApplicationForm,filePath,strUserId,request);
			
		//returning the result either 0 or 1.
		return resultMsg;
	}
		/**
	 * @param VIMSApplicationForm
	 * @return ArrayList
	 * getAppDetailsBD method will get the details of a particular application
	 */	
	public static ArrayList getAppDetailsBD(String strAppId)
	{
		//retrieving only first element from the string
		logger=Logger.getLogger("Admin");
		ArrayList arrayList=new ArrayList();
		//getting the arraylist from a DAO class method getAPp
		//ArrayList arrayList=VIMSApplicationDAO.getAppDetailsDAO(strAppId);
		
		return arrayList;
	}
	public static int getFilesCountBD(String strAppId)
	{
		int filesCount=VIMSApplicationDAO.getFilesCountDAO(strAppId);
		return filesCount;
	}
	/**
	 * @param VIMSApplicationForm
	 * @return String
	 * modifyApplicationBD will modify the existing application
	 */	
	public static String modifyApplicationBD(VIMSApplicationForm vimsApplicationForm,String filePath)
	{
		String resultMsg=VIMSApplicationDAO.modifyApplicationDAO(vimsApplicationForm, filePath);//.modifyApplicationDAO(vimsApplicationForm,filePath);
		return resultMsg;
	}
	/**
	 * @param VIMSApplicationForm
	 * @return String
	 *deleteApplicationBD deletes an application
	 */
	public static String deleteApplicationBD(String strAppId)
	{
		String resultMsg=VIMSApplicationDAO.deleteApplicationDAO(strAppId);
		return resultMsg;
	}
	/**
	 * @param strLoginUser 
	 * @param request 
	 * @param String
	 * @return ArrayList
	 *viewApplicationBD will get the application details.
	 */
	public static ArrayList viewApplicationBD(String strAppId, String strLoginUser)
	{				
		ArrayList arrayList=VIMSApplicationDAO.getAppDetailsDAO(strAppId,strLoginUser);
		HashMap hashMap=null;
		
		return arrayList;		
	}
	/**
	 * @param
	 * @return ArrayList
	 * getEmpIdListBD methpod will get the employee id list
	 */
	public static ArrayList getEmpIdListBD()
	{
			ArrayList empList=VIMSApplicationDAO.getEmpIdListDAO();
			
			return empList;
	}
	/**
	 * @param
	 * @return ArrayList
	 * getAppIdListBD method will get the application id list
	 */
	public static ArrayList getAppIdListBD()
	{
			ArrayList appList=VIMSApplicationDAO.getAppIdListDAO();			
			return appList;		
	}
	/**
	 * @param
	 * @return ArrayList
	 * getAppNameListBD method will get the application name list
	 */
	public static ArrayList getAppNameListBD()
	{
			ArrayList appList=VIMSApplicationDAO.getAppIdListDAO();			
			return appList;		
	}
	
	
	/**
	 * @param 
	 * @return ArrayList
	 * getAppSpecialistsBD method will get the application specialist list
	 */
	public static ArrayList getAppSpecialistsBD()
	{ 
		
			ArrayList appSpecList=VIMSApplicationDAO.getAppSpecialistsDAO();
			return appSpecList;
				
	}
	
	/**
	 * @param 
	 * @return ArrayList
	 * getAppSubCatBD method will get the list application sub category
	 */
	public static ArrayList getAppSubCatListBD()
	{
		
			ArrayList appSubCatList=VIMSApplicationDAO.getAppSubCatDAO();
			
			return appSubCatList;
		
	}
	
	/**
	 * @param 
	 * @return ArrayList
	 * getAppGrpListBD method will get the list of application groups.
	 */
	public static ArrayList getAppGroupListBD()
	{		
			ArrayList grpList=VIMSApplicationDAO.getAppGrpListDAO();
			
			return grpList;		
	}
	
	/**
	 * @param 
	 * @return ArrayList
	 * getUsrGrpListBD method will get the list of user groups
	 */
	public static ArrayList getUsrGrpListBD()
	{		
			ArrayList usrGrpList=VIMSApplicationDAO.getUsrGrpListDAO();
			
			return usrGrpList;
	}
	
	/**
	 * @param 
	 * @return ArrayList
	 * getCustIdListBD method will get the list application customers id.
	 */
	public static ArrayList getCustIdListBD()
	{
		ArrayList custIdList=VIMSApplicationDAO.getCustIdListDAO();
		return custIdList;
	}
	
	
	/**
	 * @param 
	 * @return ArrayList
	 * getApplicationCustomerListBD method will get the application customers list
	 */
	public static ArrayList getApplicationCustomerListBD()
	{		
		ArrayList appCustList=VIMSApplicationDAO.getApplicationCustomerListDAO();
		return appCustList;
	}

	public static ArrayList getSLAcreatedApplicationsBD() {
		ArrayList SLAcreatedApplication=VIMSSLADAO.getSLAcreatedApplicationsDAO();
		return SLAcreatedApplication;
	}

	public static HashMap getSLADetailsOfApplicationsBD(String strSLACreatedApplName) 
	{
		HashMap SLADetailsOfApplications=VIMSSLADAO.getApplicationSLADetailsDAO(strSLACreatedApplName);
		return SLADetailsOfApplications;
	}

	public static HashMap getApplicationCustomerBD(String strAppId)
	{
		HashMap hashMap=VIMSApplicationDAO.getApplicationCustomerDAO(strAppId);
		return hashMap;
	}

	public static HashMap getApplicationGroupBD(String strAppId)
	{
		HashMap hashMap=VIMSApplicationDAO.getApplicationGroupDAO(strAppId);
		return hashMap;
	}
	
	public static ArrayList getApplicationSpecialistBD(String strAppId)
	{
		ArrayList arrayList=VIMSApplicationDAO.getApplicationSpecialistDAO(strAppId);
		return arrayList;
	}
	public static ArrayList getApplicationSubCategoryDAO(String strAppid)
	{ 
		ArrayList arrayList=VIMSApplicationDAO.getApplicationSubCategoryDAO(strAppid);
		return arrayList;
	}
	public static boolean checkAppAvailabilityBD(String strAppName)
	{	
		boolean result=VIMSApplicationDAO.checkAppAvailabilityDAO(strAppName);
		return result;
	}
	public static HashMap getNewApplicationsAddedBD(String strLoginId) 
	{
		HashMap applicationList=null;
	  try 
	  {	 
		     applicationList=VIMSApplicationDAO.getNewApplicationsAddedDAO(strLoginId);
	  } 
	  catch(Exception exception) 
	  {	
		   System.out.println("====exception in getNewApplicationsAddedBD---------"+exception);    
	  }	
	  return applicationList;
	}
	
	public static void setApplicationFlagBD(String strApplicationId) {
		  
		  try{
		        logger=Logger.getLogger("Admin");
		        VIMSApplicationDAO.setApplicationFlagDAO(strApplicationId);
              } catch(Exception exception) {
		    	  logger.info("----Exception in setApplicationFlagBD-----------");
		    	  logger.error(exception);
		    }
	  }
	public static int deleteFileBD(String file,String strAppName,String strAppId)
	{
		return VIMSApplicationDAO.deleteFileDAO(file,strAppName,strAppId);
		
	}
	public static int[] getStndrdSLABD()
	{
		return VIMSApplicationDAO.getStndrdSLADAO();
	}
}
