/*
 * Created on Nov 7, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.vertex.VIMS.test.applications.DAO;
 
import com.vertex.VIMS.test.VIMSSQLQueryInterface.*;
import com.vertex.VIMS.test.applications.form.ApplicationBean;
import com.vertex.VIMS.test.applications.form.VIMSApplicationForm;
import com.vertex.VIMS.test.common.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import org.apache.log4j.*;
import org.apache.struts.upload.FormFile;
import java.io.*;

import javax.servlet.http.HttpServletRequest;
import com.vertex.VIMS.test.clients.action.*;
/**
 * @author saikumar.m
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VIMSApplicationDAO
{	
	//Logger reference variable to create logs
	static Logger logger=null;
	//Connection reference to hold connection object
	static Connection conn=null;
	//Statement reference to hold statement object
	static Statement statement=null;
	//PreparedStatement reference to hold PreparedStatement object
	static PreparedStatement preparedStatement=null;
	//CallableStatement reference to hold CallableStatement object
	static CallableStatement cstmt=null;
	static ArrayList arrayList=null;
	static HashMap hashMap=null;
	/**
	 * @param strLoginUser 
	 * @param 
	 * @return ArrayList
	 *getApplicationsListDAO method will get a list of all applications
	 */	
	
	public static ArrayList getApplicationsListDAO(String strLoginUser)
	{
		System.out.println("Extreme Components in DAO class");  
		ArrayList arrayList=null;
		logger=Logger.getLogger("Admin");
		ResultSet appResultSet=null;
		try
		{
			System.out.println("=====grp======"); 
			conn=DBConnection.createConnection();
			cstmt=conn.prepareCall("{Call vims_user.USP_Get_Application_Dtls(?,?,?,?,?,?,?)}");
			//System.out.println("======ststus in DAO======"+strStatus);
			cstmt.setString(1,null);
			cstmt.setString(2,null);
			cstmt.setString(3,null);
			cstmt.setString(4,null);
			cstmt.setString(5,null);
			cstmt.setString(6,null);
			cstmt.setString(7,strLoginUser);
			
			appResultSet=cstmt.executeQuery();
			arrayList=new ArrayList();	
	
			while(appResultSet.next())
			{
				hashMap=new HashMap();
				
				//putting values in to hash Map
				String appId =appResultSet.getString("APPLICATION_ID");
				String appName=appResultSet.getString("APPLICATION_NAME");
				String linkViewApp="<a href='./viewApplicationAction.do?param=viewApplication&pageId=ViewApplication&appId="+appId+"'>"+appName+"</a>";
				String modifyDelete="<a href='./VIMSApplicationLookupDispatchAction.do?param=populateModifyAppPage&pageId=ModifyApplication&appId="+appId+"&menuId=Application&pageId=home'>Modify</a>  |  " +
				"<a href=\"#\" onclick=\"fnDelete('"+appId+"')\"/>Delete</a>";
							
				//hashMap.put("appId",linkViewApp);
				hashMap.put("appName",linkViewApp);
				hashMap.put("appStatus",appResultSet.getString("APP_STATUS"));
				if(appResultSet.getString("APP_STATUS").equalsIgnoreCase("Active"))
				{
					modifyDelete="<a href='./VIMSApplicationLookupDispatchAction.do?param=populateModifyAppPage&pageId=ModifyApplication&appId="+appId+"&menuId=Application&pageId=home'>Modify</a>";
				}	
				hashMap.put("customerName",appResultSet.getString("Customer_name"));
				hashMap.put("appOwner",appResultSet.getString("APP_OWNER"));
				hashMap.put("primCont",appResultSet.getString("PRIMARY_CONTACT"));
				hashMap.put("supBegDate",appResultSet.getString("SUPPORT_BEGIN_DATE"));
				hashMap.put("supEndDate",appResultSet.getString("SUPPORT_End_DATE"));
				hashMap.put("supportCenter",appResultSet.getString("SUPPORT_CENTER_NAME"));
				hashMap.put("supportManager",appResultSet.getString("Support_Center_Manager_Name"));
				hashMap.put("modifyDeleteLink",modifyDelete);
				
			    //adding the HashMap to the ArrayList
				arrayList.add(hashMap);			
				
			}
			
			
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getApplicationsListDAO()");
			logger.error(sqlException);
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.getApplicationsListDAO()");
			logger.error(exception);
		}
		/*finally
		{
			try{
			statement.close();
			DBConnection.closeConnection();
			}
			catch(Exception e)
			{
				
			}
		}*/
		return arrayList;
	}
	
	public static HashMap searchApplicationDAO()
	{
		ArrayList arrayList=null;
		logger=Logger.getLogger("Admin");
		ResultSet appResultSet=null;
		try
		{			
			conn=DBConnection.createConnection();
			cstmt=conn.prepareCall("{Call vims_user.USP_Get_Application_Dtls(?,?,?,?,?,?,?)}");
			cstmt.setString(1,null);
			cstmt.setString(2,null);
			cstmt.setString(3,null);
			cstmt.setString(4,null);
			cstmt.setString(5,null);
			cstmt.setString(6,null);
			cstmt.setString(7,null);
			appResultSet=cstmt.executeQuery();
			arrayList=new ArrayList();
			int cnt=1;
			hashMap=new HashMap();	
			while(appResultSet.next())
			{							
				//putting values in to hash Map
				String appId =appResultSet.getString("APPLICATION_ID");
				String appName=appResultSet.getString("APPLICATION_NAME");				
				hashMap.put(appResultSet.getString("APPLICATION_ID"),new ApplicationBean(appId,appName));
				//hashMap.put(appName);
				//adding the HashMap to the ArrayList
				//arrayList.add(hashMap);
				cnt++;
			}
		       	
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.searchApplicationDAO()");
			logger.error(sqlException);
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.searchApplicationDAO()");
			logger.error(exception);
		}
		/*finally
		{
			try{
			statement.close();
			DBConnection.closeConnection();
			}
			catch(Exception e)
			{
				
			}
		}*/
		return hashMap;
	}
	
	public static ArrayList searchApplicationNameDAO(String strAppName,String custName, String strLoginUser)
	{
		ArrayList arrayList=null;
		logger=Logger.getLogger("Admin");
		ResultSet appResultSet=null;
		String appKey=null;
		String custKey=null;		
		/*if(!"".equals(strAppName)) 
		{
			appKey=strAppName;			
		}
		else {
			appKey=null;
		}
			
		if(!"".equals(custName))
		{
			custKey=custName;
		}
		else {
			custKey=null;
		}
			
		if("".equals(strAppName) && "".equals(custName))
		{
			appKey=null;
			custKey=null;
		}*/
		
		if((strAppName==null)||(strAppName.equals("")))
			appKey=null;
		else
			appKey=strAppName;
		if((custName==null)||(custName.equals("")))
			custKey=null;
		else
			custKey=custName;
		
		//System.out.println("=====appName===="+strAppName);
		//System.out.println("=====custName===="+custName);
		
		if(appKey!=null)
		System.out.println("===mine appKey==========="+appKey);
		if(custKey!=null)
		System.out.println("===mine custKey==========="+custKey);		
		
		try
		{			
			conn=DBConnection.createConnection();
			String strQuery="{Call vims_user.USP_Get_Application_Dtls(?,?,?,?,?,?,?)}";
			System.out.println("===strQuery=="+strQuery); 
			cstmt=conn.prepareCall(strQuery);
			//cstmt.setString(1,strStatus);
			System.out.println("========appKey====="+appKey);
			if((appKey==null)||(appKey.equals(""))){
			 	cstmt.setString(5,null);
			}else{
				cstmt.setString(5,appKey);
			}
			
			if((custKey==null)||(custKey.equals(""))){
				cstmt.setString(6,null);	
			}else{	
				cstmt.setString(6,custKey);
			}
			
			cstmt.setString(1,null);
			cstmt.setString(2,null);
			
			cstmt.setString(3,null);
			cstmt.setString(4,null);
			
			
			cstmt.setString(7,strLoginUser);
			//System.out.println("========strLoginUser====="+strLoginUser);
			//System.out.println("========custKey====="+custKey);
			appResultSet=cstmt.executeQuery();
			//System.out.println("-------appResultSet------"+appResultSet); 
			
			arrayList=new ArrayList();
						
			while(appResultSet.next())
			{			
				hashMap=new HashMap();
				
				//putting values in to hash Map
				String appId =appResultSet.getString("APPLICATION_ID");
				String appName=appResultSet.getString("APPLICATION_NAME");
				String linkViewApp="<a href='./viewApplicationAction.do?param=viewApplication&pageId=ViewApplication&appId="+appId+"'>"+appName+"</a>";
				String modifyDelete="<a href='./VIMSApplicationLookupDispatchAction.do?param=populateModifyAppPage&pageId=ModifyApplication&appId="+appId+"&menuId=sub1&pageId=home'>Modify</a>  |  " +
				"<a href=\"#\" onclick=\"fnDelete('"+appId+"')\"/>Delete</a>";
									
				//hashMap.put("appId",linkViewApp);
				hashMap.put("appName",linkViewApp);
				hashMap.put("appStatus",appResultSet.getString("APP_STATUS"));
				if(appResultSet.getString("APP_STATUS").equalsIgnoreCase("Active"))
				{
					modifyDelete="<a href='./VIMSApplicationLookupDispatchAction.do?param=populateModifyAppPage&pageId=ModifyApplication&appId="+appId+"&menuId=sub1&pageId=home'>Modify</a>";
				}	
				hashMap.put("customerName",appResultSet.getString("Customer_name"));
				hashMap.put("appOwner",appResultSet.getString("APP_OWNER"));
				hashMap.put("primCont",appResultSet.getString("PRIMARY_CONTACT"));
				hashMap.put("supBegDate",appResultSet.getString("SUPPORT_BEGIN_DATE"));
				hashMap.put("supEndDate",appResultSet.getString("SUPPORT_End_DATE"));
				hashMap.put("supportCenter",appResultSet.getString("SUPPORT_CENTER_NAME"));
				hashMap.put("supportManager",appResultSet.getString("Support_Center_Manager_Name"));
				hashMap.put("modifyDeleteLink",modifyDelete);
				
			    //adding the HashMap to the ArrayList
				arrayList.add(hashMap);			
			}		       	
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.searchApplicationNameDAO()");
			logger.error(sqlException);
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.searchApplicationNameDAO()");
			logger.error(exception);
		}
		/*finally
		{
			try{
			statement.close();
			DBConnection.closeConnection();
			}
			catch(Exception e)
			{
				
			}
		}*/
		return arrayList;
	}

	public static ArrayList getAppOwnerDAO(String strCustId)
	{
		logger=Logger.getLogger("Admin");
		ArrayList arrayList=new ArrayList();
		String strOwnerName=null;
		ResultSet resultSet=null;
		try
		{			
			conn=DBConnection.createConnection();						
			cstmt=conn.prepareCall("{CALL vims_user.USP_Get_App_Owner(?)}");	
			cstmt.setString(1,strCustId);
			resultSet=cstmt.executeQuery();
			while(resultSet.next())
			{
				hashMap=new HashMap();
				hashMap.put("ownerName", resultSet.getString("APP_OWNER")); 	
				hashMap.put("ownerMail",resultSet.getString("App_Owner_Email"));
				arrayList.add(hashMap);
			}
			System.out.println("====appOwners in DAO=="+arrayList); 
			return arrayList;
		}
		catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
			logger.info("VIMSApplicationDAO.checkAppOwnerDAO()");
			logger.error(sqlException);
			return null;
		}	
		catch(Exception exception)
		{
			exception.printStackTrace();
			logger.info("VIMSApplicationDAO.checkAppOwnerDAO()");
			logger.error(exception);
			return null;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}
		*/
	}
	
	
	/**
	 * @param String
	 * @return ArrayList
	 *getSupportCenterListDAO method will get the list of all Support Centers
	 */	
	public static ArrayList getSupportCenterListDAO(String strLoginUser)
	{
		logger=Logger.getLogger("Admin");
		ResultSet resultSet=null;
		CallableStatement callableStatement=null;
		try
		{	arrayList=new ArrayList();	
			HashMap hashMap=null;
			/*conn=DBConnection.createConnection();						
			statement=conn.createStatement();			
			resultSet=statement.executeQuery("select SUPPORT_CENTER_ID,SUPP_CENTER_NAME from vims_user.SUPPORT_CENTER where " +
					"Active_FG=0 ORDER BY SUPP_CENTER_NAME");
			
			*/
			
			conn=DBConnection.createConnection();
			callableStatement=conn.prepareCall("{CALL vims_user.USP_Get_Supp_Cntr_Dtls_All(?,?,?,?,?,?)}");
			callableStatement.setString(1,null);
			callableStatement.setString(2,null);
			callableStatement.setString(3,null);
			callableStatement.setString(4,null);
			callableStatement.setString(5,null);
			callableStatement.setString(6,strLoginUser);
			callableStatement.execute();
			resultSet=callableStatement.getResultSet();
			
			while(resultSet.next())
			{
				hashMap=new HashMap();
				hashMap.put("suppCenId", resultSet.getString(3));
				hashMap.put("suppCenName", resultSet.getString(4));
				arrayList.add(hashMap);				
			}
			return arrayList;
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getSupportCenterListDAO()");
			logger.error(sqlException);
			return null;
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.getSupportCenterListDAO()");
			logger.error(exception);
			return null;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}
		*/
	}
	/**
	 * @param String
	 * @return String
	 *getSupportCenterManagerDAO gets the support manager of given support center
	 */
	public static ArrayList getSupportCenterManagerDAO(String strSupportCenterId)
	{
		
		String []supportCenterManager=new String[2];
		logger=Logger.getLogger("Admin");
		try
		{
			conn=DBConnection.createConnection();			
			String strQuery="EXEC vims_user.USP_Get_Supp_Cntr_Dtls @SUPPORT_CENTER_ID='"+strSupportCenterId+"'";
			System.out.println("=====getSupportCenterManagerDAO strQuery========"+strQuery); 
			preparedStatement=conn.prepareStatement(strQuery);
			System.out.println("==========strSupportCenterId===="+strSupportCenterId); 
			//preparedStatement.setString(1,strSupportCenterId);
			ResultSet resultSet1=preparedStatement.executeQuery();
			
				ArrayList arrayList=new ArrayList();
				HashMap hashMap=null;
				while(resultSet1.next())
				{					
					supportCenterManager[0]=resultSet1.getString(1);
					supportCenterManager[1]=resultSet1.getString(2);
					arrayList.add(supportCenterManager);					
				}					
				while(preparedStatement.getMoreResults())
				{
					System.out.println("=======2========");
					ResultSet resultSet2=preparedStatement.getResultSet();
					while(resultSet2.next())
					{
						System.out.println("=======3========");						
						hashMap = new HashMap();
						hashMap.put("grpId", resultSet2.getString("USRGROUP_ID"));
						hashMap.put("grpName", resultSet2.getString("GROUP_NAME"));
						arrayList.add(hashMap);
					}
				}
				System.out.println("======grpList in DAO===="+arrayList);
			
			return arrayList;
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getSupportCenterManagerDAO()");
			logger.error(sqlException);
			return null;
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.getSupportCenterManagerDAO()");
			logger.error(exception);
			return null;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
	}
		
	public static ArrayList getGroupMembersDAO(String strGrpId)
	{
		ArrayList GroupEmployees=new ArrayList();
		
		HashMap hashmap=null;
		ResultSet resultSet=null;
		try
		{
			conn= DBConnection.createConnection();
			cstmt = conn.prepareCall("{CALL vims_user.USP_Get_Grp_Mbrs_By_Issue(NULL,?)}");
			cstmt.setString(1, strGrpId);
			resultSet=cstmt.executeQuery();
		 
		 while(resultSet.next())
		 {
			 hashmap=new HashMap();
			 
			 hashmap.put("empid", resultSet.getString(1));
			 hashmap.put("name",resultSet.getString(2));
			 
			 GroupEmployees.add(hashmap); 
		 }
		 //cstmt.close();
		// resultSet.close();
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
			logger.info("VIMSApplciationDAO.getGroupMembersDAO()");
			logger.error(sqlException);
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
		return GroupEmployees;
	}
	/**
	 * @param String[], FormFile, FormFile, String
	 * @return int
	 * addApplicationDAO method will insert a new application in to the database.
	 */
	public static String addApplicationDAO(VIMSApplicationForm vimsApplicationForm, String filePath,String strUserId, HttpServletRequest request)
	{
			String resultMsg="";
			logger=Logger.getLogger("Admin");
			FormFile technicalFile = vimsApplicationForm.getTechnicalFile();
			FormFile transitionFile = vimsApplicationForm.getTransitionFile();
			String objectId=vimsApplicationForm.getAppName();
			
			try
			{

				boolean isFileUploaded = false;
			
				String filesUploaded = "";
				FormFile []Files=new FormFile[5];
				
				Files[0]=vimsApplicationForm.getFile0();
				
				Files[1]=vimsApplicationForm.getFile1();
				
				Files[2]=vimsApplicationForm.getFile2();
				
				Files[3]=vimsApplicationForm.getFile3();
				
				Files[4]=vimsApplicationForm.getFile4();
				
				String []fileNames=new String[5];
				for(int i=0;i<fileNames.length;i++)
				{
					if(Files[i]!=null)
					{
						fileNames[i]=Files[i].getFileName();
					}
					else
					{
						fileNames[i]=null;
					}
					if("".equals(fileNames[i]))
					{
						fileNames[i]=null;
					}
					System.out.println("====fileName====="+fileNames[i]); 
				}
				
				
				//StringBuffer sbufFileName=new StringBuffer();
				
			conn=DBConnection.createConnection();
						
			cstmt=conn.prepareCall(VIMSQueryInterface.addApplicationSql);
						
			StringBuffer sbufSpecialists=new StringBuffer();
			String []appSpec=vimsApplicationForm.getAppSpecialists();
			if(appSpec!=null)
			{
				for(int i=0;i<appSpec.length;i++)
				{				
					if(i==0)
					{
						sbufSpecialists.append(appSpec[i]); 
					}
					else
					{
						sbufSpecialists.append(","+appSpec[i]);
					}
				}
			}
			StringBuffer sbufSubCat=new StringBuffer();
			String []subCat=vimsApplicationForm.getAppSubCatName();
			if(subCat!=null)
			{
				for(int i=0;i<subCat.length;i++)
				{				
					if(i==0)
					{
						sbufSubCat.append(subCat[i]); 
					}
					else
					{
						sbufSubCat.append(","+subCat[i]);
					}
				}
			}			
				cstmt.setString(1,vimsApplicationForm.getAppName());
				cstmt.setString(2, vimsApplicationForm.getAppStatus());
				cstmt.setString(3,vimsApplicationForm.getAppOwner());
				cstmt.setString(4,vimsApplicationForm.getPrimCont());
				cstmt.setString(5,vimsApplicationForm.getSupBegDate());
				cstmt.setString(6,vimsApplicationForm.getSupEndDate());
				//cstmt.setString(7,vimsApplicationForm.getSupportCenter());
				//cstmt.setString(8,vimsApplicationForm.getSupportManager());
				cstmt.setString(7,vimsApplicationForm.getCriticalResponseTime());
				cstmt.setString(8,vimsApplicationForm.getCriticalWarningInterval());
				cstmt.setString(9,vimsApplicationForm.getMajorResponseTime());
				cstmt.setString(10,vimsApplicationForm.getMajorWarningInterval());
				cstmt.setString(11,vimsApplicationForm.getMinorResponseTime());
				cstmt.setString(12,vimsApplicationForm.getMinorWarningInterval());
				cstmt.setString(13,objectId);
				//cstmt.setString(14,sbufFileName.toString());
				//cstmt.setString(14,"");
				cstmt.setString(14,vimsApplicationForm.getGrpId());
				cstmt.setString(15,vimsApplicationForm.getCustId());						
				cstmt.setString(16,sbufSubCat.toString());
				cstmt.setString(17,sbufSpecialists.toString());
				cstmt.setString(18,vimsApplicationForm.getAppOwnerEmail());		
				System.out.println("=====primcont email=="+vimsApplicationForm.getPrimContEmail()); 
				cstmt.setString(19,vimsApplicationForm.getPrimContEmail());
				String pwd=RandomGeneration.getUniqueID();
				cstmt.setString(20,pwd);
				cstmt.setInt(21,vimsApplicationForm.getSendMail());
				cstmt.setString(22, fileNames[0]);
				cstmt.setString(23, fileNames[1]);
				cstmt.setString(24, fileNames[2]);
				cstmt.setString(25, fileNames[3]);
				cstmt.setString(26, fileNames[4]); 
				
			String strAppId=null;
			boolean isOldCustomer=false;
			System.out.println("===cstmt====="+cstmt); 
			ResultSet resultSet = cstmt.executeQuery();	
			while(resultSet.next())
			{
				resultMsg=resultSet.getString(1);
				System.out.println("===msg===="+resultMsg);
				if(resultSet.getString(2)!=null)
				{
					pwd=resultSet.getString(2);					 
					isOldCustomer = true;
				}
				strAppId=resultSet.getString(3);
				System.out.println(resultMsg+"==========="+pwd); 
				//resultMsg="Saved Successfully";
			}
			//statement=conn.createStatement();
			//ResultSet rs=statement.executeQuery("select WORK_EMAIL_ADDRESS from vims_user.employee where USER_NM='"+strUserId+"'");
			String sender="VIMS@vertexcs.com";
			/*while(rs.next())
			{
				sender=rs.getString(1);
			}*/
			
			if(resultMsg.equals("Saved Successfully"))
			{
				HashMap<String, String> details = new HashMap<String,String>();
				details.put("sender",sender);
				details.put("receiver",vimsApplicationForm.getAppOwnerEmail());
				details.put("name",vimsApplicationForm.getAppOwner());
				details.put("appName",vimsApplicationForm.getAppName());
				details.put("supBegDate",vimsApplicationForm.getSupBegDate());
				details.put("supMgr",vimsApplicationForm.getSupportManager());
				details.put("userId",vimsApplicationForm.getAppOwnerEmail());
				System.out.println("========old or new======"+isOldCustomer); 
				if(isOldCustomer==true)
				{
					details.put("checkUser", "old");
				}
				else
				{
					details.put("checkUser", "new");
				}				
				details.put("pwd",pwd);

				filePath=filePath+"\\"+strAppId;
				
				boolean b=new File(filePath).mkdirs();
				System.out.println("======b========="+b); 
				
				String strContextPath=ServerAddress.getURL(request);
				VIMSApplicationMail.sendMail(details, strContextPath);
				
				for (FormFile formFile : Files) 
				{
					if(formFile!=null)
					{
						String formFileName = formFile.getFileName();
						byte[] formFileData = formFile.getFileData();
						File file = new File(filePath, formFileName);
						//sbufFileName=sbufFileName.append(formFileName+";");
						// Continue if there is no file
						if ("".equals(formFile.getFileName())) 
						{
							continue;
						}
						else 
						{
							FileOutputStream fos = new FileOutputStream(file);
							fos.write(formFileData);
							fos.flush();
							fos.close();
							isFileUploaded = true;
							if (filesUploaded != "") 
							{
								filesUploaded += ", " + formFileName;
								System.out.println("===filesUploaded in if===="+filesUploaded); 
							} 
							else 
							{
								filesUploaded += formFileName;
								System.out.println("===filesUploaded in else===="+filesUploaded);
							}
						}
					}
				}				
				//send mail to the Application Owner				
			}
			//For uploading Transition Document				
			return resultMsg;
		}
		
		catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
			logger.info("VIMSApplicationDAO.addApplicationDAO()");
			logger.error(sqlException);
			return "DataBase problem occured please try again later";
		}	
		
		catch(FileNotFoundException fileNotFoundException)
		{
			fileNotFoundException.printStackTrace();
			logger.info("VIMSApplicationDAO.addApplicationDAO()");
			logger.error(fileNotFoundException);
			return "Files not uploaded";
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			logger.info("VIMSApplicationDAO.addApplicationDAO()");
			logger.error(exception);
			return "Server problem occured please try again later";
		}
		
	}
	/**
	 * @param strLoginUser 
	 * @param request 
	 * @param String
	 * @return ArrayList
	 * getAppDetailsDAO method will get the details of a particular application
	 */
	public static ArrayList getAppDetailsDAO(String appId, String strLoginUser)
	{
		logger=Logger.getLogger("Admin");
		String strCritical="cri";
		String strMajor="maj";
		String strMinor="min";
		try
		{
			conn=DBConnection.createConnection();
			
			statement=conn.createStatement();
			
			cstmt=conn.prepareCall("{call vims_user.USP_Get_Application_Dtls(?,?,?,?,?,?,?)}");
			cstmt.setString(1,appId);
			cstmt.setString(2,null);
			cstmt.setString(3,null);
			cstmt.setString(4,null);
			cstmt.setString(5,null);
			cstmt.setString(6,null);
			cstmt.setString(7,strLoginUser);
			ResultSet resultSet=cstmt.executeQuery();
		
			arrayList=new ArrayList();
			
			hashMap = new HashMap();
			while(resultSet.next())
			{				
				hashMap.put("appId",resultSet.getString("APPLICATION_ID"));
				hashMap.put("appName",resultSet.getString("APPLICATION_NAME"));
				hashMap.put("appStatus",resultSet.getString("APP_STATUS"));
				hashMap.put("appOwner",resultSet.getString("APP_OWNER"));
				hashMap.put("appOwnerEmail",resultSet.getString("App_Owner_Email"));
				hashMap.put("primCont",resultSet.getString("PRIMARY_CONTACT"));
				hashMap.put("primContEmail",resultSet.getString("Primary_Cnt_Email"));
				hashMap.put("supBegDate",resultSet.getString("SUPPORT_BEGIN_DATE"));
				hashMap.put("supEndDate", resultSet.getString("SUPPORT_END_DATE")); 
				hashMap.put("supportCenter",resultSet.getString("SUPPORT_CENTER_ID"));
				hashMap.put("supportCenterName", resultSet.getString("SUPPORT_CENTER_Name"));
				hashMap.put("supportManagerName",resultSet.getString("Support_Center_Manager_Name"));
				hashMap.put("sendMail",resultSet.getInt("Mail_Reqd_FG"));
				hashMap.put("CRITICAL_RESPONSE_TIME", resultSet.getString("Critical_Response"));
				hashMap.put("CRITICAL_WARNING_INTERVAL", resultSet.getString("Critical_Warning"));
				hashMap.put("MAJOR_RESPONSE_TIME", resultSet.getString("Major_Response"));
				hashMap.put("MAJOR_WARNING_INTERVAL", resultSet.getString("Major_Warning"));
				hashMap.put("MINOR_RESPONSE_TIME", resultSet.getString("Minor_Response"));
				hashMap.put("MINOR_WARNING_INTERVAL", resultSet.getString("Minor_Warning"));
				hashMap.put("attachment1", resultSet.getString("Attchmnt1"));
				System.out.println(resultSet.getString("Attchmnt1")); 
				hashMap.put("attachment2", resultSet.getString("Attchmnt2"));
				System.out.println(resultSet.getString("Attchmnt2")); 
				hashMap.put("attachment3", resultSet.getString("Attchmnt3"));
				System.out.println(resultSet.getString("Attchmnt3")); 
				hashMap.put("attachment4", resultSet.getString("Attchmnt4"));
				System.out.println(resultSet.getString("Attchmnt4")); 
				hashMap.put("attachment5", resultSet.getString("Attchmnt5"));
				System.out.println(resultSet.getString("Attchmnt5")); 
			}	
			arrayList.add(hashMap);
			
			
			return arrayList;
		} 
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getAppDetailsDAO()");
			logger.error(sqlException);
			return null;
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.getAppDetailsDAO()");
			logger.error(exception);
			return null;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
	}
	
	public static int getFilesCountDAO(String strAppId)
	{
		int filesCount=0;
		logger=Logger.getLogger("Admin");
		try
		{
			conn=DBConnection.createConnection();
					
			//cstmt=conn.prepareCall(VIMSQueryInterface.delAppSql);			
			//cstmt.setString(1,strAppId);
			statement=conn.createStatement();
			ResultSet resultSet=statement.executeQuery("select Attchmnt1,Attchmnt2,Attchmnt3,Attchmnt4,Attchmnt5 from vims_user.APPLICATION_OBJECT where APPLICATION_ID='"+strAppId+"'");
			int count=0;
			while (resultSet.next())
			{

				String Attachment1=resultSet.getString(1);
				if("".equalsIgnoreCase(Attachment1)||Attachment1==null)
				{
					count++;
				}
				String Attachment2=resultSet.getString(2);
				if("".equalsIgnoreCase(Attachment2)||Attachment2==null)
				{
					count++;
				}
				String Attachment3=resultSet.getString(3);
				if("".equalsIgnoreCase(Attachment3)||Attachment3==null)
				{
					count++;
				}
				String Attachment4=resultSet.getString(4);
				if("".equalsIgnoreCase(Attachment4)||Attachment4==null)
				{
					count++;
				}
				String Attachment5=resultSet.getString(5);
				if("".equalsIgnoreCase(Attachment5)||Attachment5==null)
				{
					count++;
				}
			}
			filesCount=count;
						
			return filesCount;
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.deleteApplicationDAO()");
			logger.error(sqlException);
			return filesCount;
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.deleteApplicationDAO()");
			logger.error(exception);
			return filesCount;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
	}	
	/**
	 * @param String[]
	 * @return int
	 * modifyApplicationDAO will modify the existing application
	 */
	public static String modifyApplicationDAO(VIMSApplicationForm vimsApplicationForm,String filePath)
	{	logger=Logger.getLogger("Admin");
		try
		{
			conn=DBConnection.createConnection();
			
			cstmt=conn.prepareCall(VIMSQueryInterface.modifyAppSql);
			filePath=filePath+"\\"+vimsApplicationForm.getAppId();
			
			boolean b=new File(filePath).mkdirs();
			System.out.println("======b========="+b); 
			StringBuffer sbufSpecialists=new StringBuffer();
			String []appSpecialists=vimsApplicationForm.getAppSpecialists();
			if(appSpecialists!=null)
			{
				for(int i=0;i<appSpecialists.length;i++)
				{
					
					if(i==0)
					{
						sbufSpecialists.append(appSpecialists[i]); 
					}
					else
					{					
						sbufSpecialists.append(","+appSpecialists[i]);
					}
				}	
			}
			
			StringBuffer sbufAppSubCat=new StringBuffer();
			String []appSubCatName=vimsApplicationForm.getAppSubCatName();
			if(appSubCatName!=null)
			{
				for(int i=0;i<appSubCatName.length;i++)
				{				
					if(i==0)
					{
						sbufAppSubCat.append(appSubCatName[i]); 
					}
					else
					{						
						sbufAppSubCat.append(","+appSubCatName[i]);
					}
				}	
			}			
			
			FormFile []Files=new FormFile[5];
			
			Files[0]=vimsApplicationForm.getFile0();
			
			Files[1]=vimsApplicationForm.getFile1();
			
			Files[2]=vimsApplicationForm.getFile2();
			
			Files[3]=vimsApplicationForm.getFile3();
			
			Files[4]=vimsApplicationForm.getFile4();
			String []fileNames=new String[5];
			
			
			
			statement=conn.createStatement();
			ResultSet filesSet=statement.executeQuery("select Attchmnt1,Attchmnt2,Attchmnt3,Attchmnt4,Attchmnt5 from vims_user.APPLICATION_OBJECT where APPLICATION_ID='"+vimsApplicationForm.getAppId()+"'");
			//int fileIndex=0;
			
			cstmt.setString(1,vimsApplicationForm.getAppId());
			cstmt.setString(2,vimsApplicationForm.getAppName());
			cstmt.setString(3,vimsApplicationForm.getAppStatus());
			cstmt.setString(4,vimsApplicationForm.getAppOwner());
			cstmt.setString(5,vimsApplicationForm.getPrimCont());
			cstmt.setString(6,vimsApplicationForm.getSupBegDate());
			cstmt.setString(7,vimsApplicationForm.getSupEndDate());
			cstmt.setString(8,"Update");
			//cstmt.setString(8,vimsApplicationForm.getSupportCenter());
			//cstmt.setString(9,vimsApplicationForm.getSupportManager());
			cstmt.setString(9,vimsApplicationForm.getCriticalResponseTime());
			cstmt.setString(10,vimsApplicationForm.getCriticalWarningInterval());
			cstmt.setString(11,vimsApplicationForm.getMajorResponseTime());
			cstmt.setString(12,vimsApplicationForm.getMajorWarningInterval());
			cstmt.setString(13,vimsApplicationForm.getMinorResponseTime());
			cstmt.setString(14,vimsApplicationForm.getMinorWarningInterval());
			cstmt.setString(15, vimsApplicationForm.getAppName());
			cstmt.setString(16,vimsApplicationForm.getGrpId());
			cstmt.setString(17,vimsApplicationForm.getCustId());
			cstmt.setString(18,sbufAppSubCat.toString());
			cstmt.setString(19,sbufSpecialists.toString());
			cstmt.setString(20,vimsApplicationForm.getAppOwnerEmail());
			System.out.println("=====primcont email in modify=="+vimsApplicationForm.getPrimContEmail());
			cstmt.setString(21,vimsApplicationForm.getPrimContEmail());
			cstmt.setString(22, null); 
			cstmt.setInt(23, vimsApplicationForm.getSendMail());
			
			StringBuffer sbuf=new StringBuffer();
			while(filesSet.next())
			{
				if(!("".equals(filesSet.getString(1))) && (filesSet.getString(1)!=null))
				{
					sbuf=sbuf.append(filesSet.getString(1)+";");
				}
				if(!("".equals(filesSet.getString(2))) && (filesSet.getString(2)!=null))
				{
					sbuf=sbuf.append(filesSet.getString(2)+";");
				}
				if(!("".equals(filesSet.getString(3))) && (filesSet.getString(3)!=null))
				{
					sbuf=sbuf.append(filesSet.getString(3)+";");
				}
				if(!("".equals(filesSet.getString(4))) && (filesSet.getString(4)!=null))
				{
					sbuf=sbuf.append(filesSet.getString(4)+";");
				}
				if(!("".equals(filesSet.getString(5)))&& (filesSet.getString(5)!=null))
				{
					sbuf=sbuf.append(filesSet.getString(5)+";");
				}								
			}
			
//			String strTempFileName[]=sbuf.toString().split(";");
//			for(int i=0;i<strTempFileName.length;i++)
//			{
//				System.out.println("======strTempFileName====="+strTempFileName.length); 
//				System.out.println("========"+strTempFileName[i]); 
//			}
			
						
			for(int i=0;i<fileNames.length;i++)
			{
				if(Files[i]!=null)
				{
					sbuf.append(Files[i].getFileName()+";");
				} 
			}
			String[] strTempFileName=new String[5];
			System.out.println("====sbuf to string==== "+sbuf.toString()); 
			String strFileName[]=sbuf.toString().split(";");
			System.out.println("======strFileName====="+strFileName.length); 
			for(int i=0;i<strFileName.length;i++)
			{
				if(!"".equals(strFileName[i])||strFileName[i]!=null)
				{
					strTempFileName[i]=strFileName[i];
					if("".equals(strTempFileName[i]))
					{
						strTempFileName[i]=null;
					}
				}				
				System.out.println("========"+strFileName[i]); 
			}					
			cstmt.setString(24, strTempFileName[0]);
			cstmt.setString(25, strTempFileName[1]);	
			cstmt.setString(26, strTempFileName[2]);
			cstmt.setString(27, strTempFileName[3]);
			cstmt.setString(28, strTempFileName[4]);		
			
			
			String resultMsg="";
			String strAppId="";
			ResultSet resultSet=cstmt.executeQuery();
			while(resultSet.next())
			{
				//resultMsg="Saved Successfully";
				resultMsg=resultSet.getString(1);
			}
			if(resultMsg.equalsIgnoreCase("Saved Successfully")); 
			{
				for (FormFile formFile : Files) 
				{
					if(formFile!=null)
					{
						String formFileName = formFile.getFileName();
						byte[] formFileData = formFile.getFileData();
						File file = new File(filePath, formFileName);
						//sbufFileName=sbufFileName.append(formFileName+";");
						// Continue if there is no file
						if ("".equals(formFile.getFileName())) 
						{
							continue;
						}
						else 
						{
							FileOutputStream fos = new FileOutputStream(file);
							fos.write(formFileData);
							fos.flush();
							fos.close();
						}
					}
				}	
			}			
			return resultMsg;
		}
		catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
			logger.info("VIMSApplicationDAO.modifyApplicationDAO()");
			logger.error(sqlException);
			return "Database Problem";
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			logger.info("VIMSApplicationDAO.modifyApplicationDAO()");
			logger.error(exception);
			return "Server Problem";
		}	
		/*finally
		{
			try{
			preparedStatement.close();
			DBConnection.closeConnection();
			}
			catch(Exception e)
			{
				
			}
		}*/
	}
	
	/**
	 * @param String[]
	 * @return String
	 * deleteApplicationDAO deletes an application
	 */
	public static String deleteApplicationDAO(String strAppId)
	{
		String resultMsg="";
		logger=Logger.getLogger("Admin");
		try
		{
			conn=DBConnection.createConnection();
					
			cstmt=conn.prepareCall(VIMSQueryInterface.delAppSql);
			
			cstmt.setString(1,strAppId);
			ResultSet resultSet=cstmt.executeQuery();
			while (resultSet.next())
			{
				resultMsg=resultSet.getString(1);
			}
						
			return resultMsg;
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.deleteApplicationDAO()");
			logger.error(sqlException);
			return "Some Internal Problem Occured while Deleting the Application";
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.deleteApplicationDAO()");
			logger.error(exception);
			return "Some Internal Problem Occured while Deleting the Application";
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
	}
	
	/**
	 * @param 
	 * @return ArrayList
	 * getEmpIdListDAO methpod will get the employee id list
	 */
	public static ArrayList getEmpIdListDAO()
	{
		logger=Logger.getLogger("Admin");
		try
		{
			conn=DBConnection.createConnection();
			statement=conn.createStatement();
			ResultSet resultSet=statement.executeQuery(VIMSQueryInterface.empIdListSql);	
			arrayList=new ArrayList();
			while(resultSet.next())
			{
				hashMap=new HashMap();
				hashMap.put("empId", resultSet.getString(1));
				hashMap.put("empName", resultSet.getString(2));
				arrayList.add(hashMap);			
			}
			return arrayList;
			
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getEmpIdListDAO()");
			logger.error(sqlException);
			return null;
		}	 
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.getEmpIdListDAO()");
			logger.error(exception);
			return null;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
	}
	
	/**
	 * @param 
	 * @return ArrayList
	 * getAppIdListDAO method will get the application id list
	 */
	public static ArrayList getAppIdListDAO()
	{	
		logger=Logger.getLogger("Admin");
		arrayList=null;
		try
		{
			conn=DBConnection.createConnection();
			statement=conn.createStatement();
			ResultSet resultSet=statement.executeQuery(VIMSQueryInterface.appIdListSql);	
			arrayList=new ArrayList();
			while(resultSet.next())
			{
				hashMap=new HashMap();
				hashMap.put("appId",resultSet.getString("APPLICATION_ID"));
				hashMap.put("appName",resultSet.getString("APPLICATION_NAME"));
				
				arrayList.add(hashMap);
			}
			return arrayList;
			
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getAppIdListDAO()");
			logger.error(sqlException);
			return null;
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.getAppIdListDAO()");
			logger.error(exception);
			return null;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
	}
	
	/**
	 * @param 
	 * @return ArrayList
	 * getAppNameListDAO method will get the application name list
	 */
	public static ArrayList getAppNameListDAO()
	{	
		logger=Logger.getLogger("Admin");
		arrayList=null;
		try
		{
			conn=DBConnection.createConnection();
			statement=conn.createStatement();
			ResultSet resultSet=statement.executeQuery(VIMSQueryInterface.appIdListSql);	
			arrayList=new ArrayList();
			while(resultSet.next())
			{
				arrayList.add(resultSet.getString("APPLICATION_NAME"));
			}
			return arrayList;			
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getAppIdListDAO()");
			logger.error(sqlException);
			return null;
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.getAppIdListDAO()");
			logger.error(exception);
			return null;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
	}
		/**
	 * @param String[]
	 * @return String
	 * addApplicationSpecialistDAO method will add a new application specialist
	 */
	public static String addApplicationSpecialistDAO(String []strNewAppSpec)
	{
		String resultMsg=null;
		logger=Logger.getLogger("Admin");
		try
		{			
			conn=DBConnection.createConnection();
			cstmt = conn.prepareCall(VIMSQueryInterface.addAppSpecSqlProc);
			ResultSet resultSet=null;
			cstmt.setString(1,strNewAppSpec[0]);
			cstmt.setString(2,strNewAppSpec[1]);
			cstmt.setString(3,strNewAppSpec[2]);
			resultSet=cstmt.executeQuery();				
			
			while(resultSet.next())
			{
				resultMsg=resultSet.getString(1);				
			}
			if(resultMsg.equals("Saved Successfully"))
			{
				statement=conn.createStatement();
				int i=statement.executeUpdate("update TAPP_SAVE_PENDING SET APPLICATION_SPECIALISTS='Yes' WHERE APPLICATION_ID='"+strNewAppSpec[0]+"'");
			}
			return resultMsg;
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.addApplicationSpecialistDAO()");
			logger.error(sqlException);
			return "Some Internal Problem Occured Please Try Again Later";
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.addApplicationSpecialistDAO()");
			logger.error(exception);
			return "Some Internal Problem Occured Please Try Again Later";
		}	
		finally
		{
			DBConnection.closeConnection();
		}
	}
	/**
	 * @param 
	 * @return ArrayList
	 * getAppSpecialistsDAO method will get the application specialist list
	 */
	public static ArrayList getAppSpecialistsDAO()
	{
		logger=Logger.getLogger("Admin");
		try
		{
			String strTemp="";
			String strExistApplicationId="";
			conn=DBConnection.createConnection();
			statement=conn.createStatement();
			ResultSet resultSet=statement.executeQuery(VIMSQueryInterface.viewAppSpecListSql);	
			arrayList=new ArrayList();
			//System.out.println(resultSet.next()); 
			while(resultSet.next())
			{
				
				/*if((strTemp.equals(""))||(strExistApplicationId.equals(""))) {
					 strExistApplicationId=resultSet.getString(2);
				  }
				  if(strExistApplicationId.equals(resultSet.getString(2))) { 
					  if(!strTemp.equals(""))
				    	  strTemp=strTemp+"<br>"+resultSet.getString(4);
					  else
					      strTemp=resultSet.getString(4);	  
				   }	
				    else 
				    {
				    	hashMap=new HashMap();
						
						hashMap.put("appId", resultSet.getString(1));
						hashMap.put("appName", resultSet.getString(2));
						hashMap.put("empId", resultSet.getString(3));
						hashMap.put("empName", strTemp);
						arrayList.add(hashMap);
						strTemp="";
				    }	*/	  
				hashMap=new HashMap();
				
				hashMap.put("appId", resultSet.getString(1));
				hashMap.put("appName", resultSet.getString(2));
				hashMap.put("empId", resultSet.getString(3));
				hashMap.put("empName", resultSet.getString(4));
				arrayList.add(hashMap);
				
			}
			 
			return arrayList;
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getAppSpecialistsDAO()");
			logger.error(sqlException);
			return null;
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.getAppSpecialistsDAO()");
			logger.error(exception);
			return null;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
	}
	/**
	 * @param 
	 * @return ArrayList
	 * getAppSubCatDAO method will get the list application sub category
	 */
	public static ArrayList getAppSubCatDAO()
	{
		logger=Logger.getLogger("Admin");
		String strCurrentApplicationId=null;
		String strExistApplicationId=null;;
		String strTemp=null; 
		try
		{
			strCurrentApplicationId=new String();
			strExistApplicationId=new String();
			
			conn=DBConnection.createConnection();
			statement=conn.createStatement();
			ResultSet resultSet=statement.executeQuery(VIMSQueryInterface.viewAppSubCatListSql);	
			arrayList=new ArrayList();
			strTemp=new String();
			
			while(resultSet.next())
			{ 
				 /* if((strTemp.equals(""))||(strExistApplicationId.equals(""))) {
					 strExistApplicationId=resultSet.getString(1);
				  }
				  if(strExistApplicationId.equals(resultSet.getString(1))) { 
					  if(!strTemp.equals(""))
				    	  strTemp=strTemp+"<br>"+resultSet.getString(3);
					  else
					      strTemp=resultSet.getString(3);	  
				   }	
				    else {*/
						hashMap=new HashMap();
						hashMap.put("appId", resultSet.getString(1));
						hashMap.put("appName", resultSet.getString(2));
						hashMap.put("appSubCat", resultSet.getString(3));	
						arrayList.add(hashMap);
						//strExistApplicationId=resultSet.getString(1);
					/*	strTemp="";
				    }*/
			}
			return arrayList;
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.viewAppSubCatDAO()");
			logger.error(sqlException);
			return null;
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.viewAppSubCatDAO()");
			logger.error(exception);
			return null;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
		
		
	}
	/**
	 * @param String[]
	 * @return String
	 * addAppSubCatDAO method will add a new applpication sub category under given application
	 */
	public static String addAppSubCatDAO(String []strNewAppSubCat)
	{
		String resultMsg="";
		logger=Logger.getLogger("Admin");
		try{
			conn=DBConnection.createConnection();
			cstmt=conn.prepareCall(VIMSQueryInterface.addAppSubCatSqlProc);
			cstmt.setString(1,strNewAppSubCat[0]);
			cstmt.setString(2,strNewAppSubCat[1]);
			ResultSet resultSet=cstmt.executeQuery();	
			while(resultSet.next())
			{
				resultMsg=resultSet.getString(1);
			}
			if(resultMsg.equals("Saved Successfully"))
			{
				statement=conn.createStatement();			 
				int i=statement.executeUpdate("update TAPP_SAVE_PENDING SET APPLICATION_MODULES='Yes' WHERE APPLICATION_ID='"+strNewAppSubCat[0]+"'");
			}
			return resultMsg;
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.viewAppSubCatDAO()");
			logger.error(sqlException);
			return "Exception raised while adding application sub category";
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.viewAppSubCatDAO()");
			logger.error(exception);
			return "Exception raised while adding application sub category";
		}	
		/*finally
		{
			DBConnection.closeConnection();
		}*/
	}
	/**
	 * @param 
	 * @return ArrayList
	 * getAppGrpListDAo method will get the list of application groups.
	 */
	public static ArrayList getAppGrpListDAO()
	{
		logger=Logger.getLogger("Admin");

		try
		{
			conn=DBConnection.createConnection();
			statement=conn.createStatement();
			ResultSet resultSet=statement.executeQuery(VIMSQueryInterface.getAppGrpIdListSql);	
			arrayList=new ArrayList();
			while(resultSet.next())
			{
				hashMap=new HashMap();
				hashMap.put("groupId", resultSet.getString("USRGROUP_ID"));
				hashMap.put("groupName", resultSet.getString("GROUP_NAME"));
				arrayList.add(hashMap);
			}
			return arrayList;			
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getAppGrpIdListDAO()");
			logger.error(sqlException);
			return null;
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.getAppGrpIdListDAO()");
			logger.error(exception);
			return null;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
		
	}
	/**
	 * @param 
	 * @return ArrayList
	 * getUsrGrpListDAO method will get the list of user groups
	 */
	public static ArrayList getUsrGrpListDAO()
	{
		logger=Logger.getLogger("Admin");
		String strTemp="";
		String strExistGrpId="";
		try
		{
			conn=DBConnection.createConnection();
			cstmt=conn.prepareCall(VIMSQueryInterface.getUsrGrpListSql);
			ResultSet resultSet=cstmt.executeQuery();
			arrayList=new ArrayList();			
			while(resultSet.next())
			{
				 /*if((strTemp.equals(""))||(strExistGrpId.equals(""))) {
					 strExistGrpId=resultSet.getString(3);
				  }
				  if(strExistGrpId.equals(resultSet.getString(3))) { 
					  if(!strTemp.equals(""))
				    	  strTemp=strTemp+"<br>"+resultSet.getString(1);
					  else
					      strTemp=resultSet.getString(1);	  
				   }	
				    else {*/
				    	hashMap=new HashMap();
						hashMap.put("appName",resultSet.getString(1));//resultSet.getString(1));
						hashMap.put("grpName",resultSet.getString(2));
						hashMap.put("grpId", resultSet.getString(3));
						hashMap.put("grpSupervisor", resultSet.getString(4));
						arrayList.add(hashMap);
						//strExistApplicationId=resultSet.getString(1);
					/*	strTemp="";
				    }		*/	
			}
			return arrayList;		
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getUsrGrpListDAO()");
			logger.error(sqlException);
			return null;
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.getUsrGrpListDAO()");
			logger.error(exception);
			return null;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
	}
	/**
	 * @param String[]
	 * @return String
	 * addAppGrpDAO method will add a new group under given application
	 */
	public static String addAppGrpDAO(String []strNewAppGrp)
	{
		String resultMsg=null;
		logger=Logger.getLogger("Admin");
		try
		{			
			conn = DBConnection.createConnection();
			
			cstmt = conn.prepareCall(VIMSQueryInterface.addAppGrpSqlProc);
			
			cstmt.setString(1,strNewAppGrp[0]);
			
			cstmt.setString(2,strNewAppGrp[1]);
			
			ResultSet resultSet=cstmt.executeQuery();	
			
			while(resultSet.next())
			{
				resultMsg=resultSet.getString(1);
			}
			if(resultMsg.equals("Saved Successfully"))
			{
				statement=conn.createStatement();
				int i=statement.executeUpdate("update TAPP_SAVE_PENDING SET APPLICATION_GROUPS='Yes' WHERE APPLICATION_ID='"+strNewAppGrp[0]+"'");
			}
			
			return resultMsg;
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.addAppGrpDAO()");
			logger.error(sqlException);
			return "Not Saved";
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.addAppGrpDAO()");
			logger.error(exception);
			return "Not Saved";
		}	
		/*finally
		{
			DBConnection.closeConnection();
		}*/
		
	}
	/**
	 * @param 
	 * @return ArrayList
	 *getCustIdListDAO method will get the list application customers id.
	 */
	public static ArrayList getCustIdListDAO()
	{
		logger=Logger.getLogger("Admin");
		try
		{
			conn=DBConnection.createConnection();
			statement=conn.createStatement();
			ResultSet resultSet=statement.executeQuery("select CUSTOMER_ID, CUSTOMER_NAME from vims_user.CUSTOMER WHERE STATUS='Active' ORDER BY CUSTOMER_NAME");	
			arrayList=new ArrayList();
			
			while(resultSet.next())
			{				
				hashMap=new HashMap();
				hashMap.put("customerId", resultSet.getString("CUSTOMER_ID"));
				hashMap.put("customerName", resultSet.getString("CUSTOMER_NAME"));
				arrayList.add(hashMap);
			}
			return arrayList;
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getCustIdListDAO()");
			logger.error(sqlException);
			return null;
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.getCustIdListDAO()");
			logger.error(exception);
			return null;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
	}
	/**
	 * @param String[]
	 * @return String
	 *addAppCustomerDAO method will add a customer to the application
	 */
	public static String addAppCustomerDAO(String []strNewAppCust)
	{
		String resultMsg="";
		logger=Logger.getLogger("Admin");
		try{
			conn=DBConnection.createConnection();
			cstmt=conn.prepareCall(VIMSQueryInterface.addAppCustSqlProc);
			cstmt.setString(1,strNewAppCust[0]);
			cstmt.setString(2,strNewAppCust[1]);
			ResultSet resultSet=cstmt.executeQuery();	
			while(resultSet.next())
			{
				resultMsg=resultSet.getString(1);
			}
			if(resultMsg.equals("Saved Successfully"))
			{
				statement=conn.createStatement();
				int i=statement.executeUpdate("update TAPP_SAVE_PENDING SET APPLICATION_CUSTOMERS='Yes' WHERE APPLICATION_ID='"+strNewAppCust[0]+"'");
			}
			return resultMsg;
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.addAppCustomerDAO()");
			logger.error(sqlException);
			return "An internal error occured please try after some time";
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.addAppCustomerDAO()");
			logger.error(exception);
			return "An internal error occured please try after some time";
		}	
		/*finally
		{
			DBConnection.closeConnection();
		}*/
	}
	/**
	 * @param 
	 * @return ArrayList
	 *getApplicationCustomerListDAO method will get the application customers list
	 */
	public static ArrayList getApplicationCustomerListDAO()
	{
		ResultSet resultSet=null;
		try
		{
			String strTemp="";
			String strExistCustId="";
			conn=DBConnection.createConnection();
			cstmt = conn.prepareCall(VIMSQueryInterface.getAppCustDetSql);
			resultSet=cstmt.executeQuery();
			arrayList=new ArrayList();
			
			while(resultSet.next())
			{
				/*System.out.println("=====in====");
				
				 if((strTemp.equals(""))||(strExistCustId.equals(""))) {
					 strExistCustId=resultSet.getString("CUSTOMER_NAME");
				  }
				  if(strExistCustId.equalsIgnoreCase(resultSet.getString("CUSTOMER_NAME"))) { 
					  if(!strTemp.equals(""))
				    	  strTemp=strTemp+"<br>"+resultSet.getString("APPLICATION_NAME");
					  else
					      strTemp=resultSet.getString("APPLICATION_NAME");	  
				   }	
				    else {*/
				    	hashMap=new HashMap();
						hashMap.put("appId", resultSet.getString("APPLICATION_ID"));
						hashMap.put("appName",resultSet.getString("APPLICATION_NAME"));
						hashMap.put("appOwner",resultSet.getString("APP_OWNER"));
						hashMap.put("custId", resultSet.getString("CUSTOMER_ID"));
						hashMap.put("custName", resultSet.getString("CUSTOMER_NAME"));
						arrayList.add(hashMap);
					/*	strTemp="";
				     }	*/
			}
			return arrayList;			
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getApplicationCustomerListDAO()");
			logger.error(sqlException);
			return arrayList;
		}
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.getApplicationCustomerListDAO()");
			logger.error(exception);
			return arrayList;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
	}

	public static ArrayList getPendingApplications(String strUserId)
	{
		logger=Logger.getLogger("Admin");
		
		try
		{
			conn=DBConnection.createConnection();
			statement=conn.createStatement();
			ResultSet rs=statement.executeQuery("select APPLICATION_ID, APPLICATION_NAME FROM" +
												" TAPP_SAVE_PENDING WHERE USER_ID='"+strUserId+"'" );
			
			ArrayList pendingApplications=new ArrayList();
			HashMap hashMap=null;
			 		
			while(rs.next())
			{
				hashMap=new HashMap();
				
				hashMap.put("appId",rs.getString("APPLICATION_ID"));
				
				hashMap.put("appName",rs.getString("APPLICATION_NAME"));
				
				pendingApplications.add(hashMap);	
				 
			}	
			
			return pendingApplications;			
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getPendingAplications()");
			logger.error(sqlException);
			return null;
		}
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.getPendingAplications()");
			logger.error(exception);
			return null;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/		
	}
	
	public static ArrayList getUnSavedTabsDAO(String strAppId)
	{
		logger=Logger.getLogger("Admin");
		ResultSet resultSet=null;
		try
		{
			conn=DBConnection.createConnection();
			statement=conn.createStatement();
			ResultSet rs=statement.executeQuery("select APPLICATION_SPECIALISTS,APPLICATION_CUSTOMERS," +
												"APPLICATION_GROUPS,APPLICATION_MODULES from vims_user.APP_SAVE_INDICATOR " +
												"WHERE APPLICATION_ID='"+strAppId+"'" );
			
			ArrayList unSavedTabs=new ArrayList();
			
			
			while(rs.next())
			{
				if(rs.getString("APPLICATION_SPECIALISTS").equals("No"))
				{
					unSavedTabs.add("Application Specialists");
				}
				if(rs.getString("APPLICATION_CUSTOMERS").equals("No"))
				{
					unSavedTabs.add("Application Customers");
				}
				if(rs.getString("APPLICATION_GROUPS").equals("No"))
				{
					unSavedTabs.add("Application Groups");
				}
				if(rs.getString("APPLICATION_MODULES").equals("No"))
				{
					unSavedTabs.add("Application Modules");
				}
			}			
			return unSavedTabs;			
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getUnsavedTabsDAO()");
			logger.error(sqlException);
			return null;
		}
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.getUnsavedTabsDAO()");
			logger.error(exception);
			return null;
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
	}
	
	/**
	 * @param String[],ResultSet
	 * @return ArrayList
	 *populateArrayList method will populate the arraylist with data.
	 */
	
	public static int executeSaveAppPendingProcedure(String strApp[])
	{	
		logger=Logger.getLogger("Admin");
		int i=0;
		try
		{
			
			conn=DBConnection.createConnection();
			cstmt=conn.prepareCall(VIMSQueryInterface.appSavePendingProc);
			
			for(int j=0;j<strApp.length;j++)
			{
				
				cstmt.setString(j+1,strApp[0]);
			}
			i=cstmt.executeUpdate();
			
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.executeSaveAppPendingProcedure()");
			logger.error(sqlException);
		}
		
		return i;
	}

	public static HashMap getApplicationCustomerDAO(String strAppId)
	{
		try
		{
			conn=DBConnection.createConnection();
			
			statement=conn.createStatement();
					
			ResultSet resultSet=statement.executeQuery("select CUSTOMER_ID,CUSTOMER_NAME from vims_user.CUSTOMER WHERE CUSTOMER_ID=(select CUSTOMER_ID from vims_user.APP_SOLD_TO WHERE APPLICATION_ID='"+strAppId+"')");
		
			arrayList=new ArrayList();
			
			hashMap = new HashMap();
			while(resultSet.next())
			{
				hashMap.put("customerId",resultSet.getString(1));
				hashMap.put("customerName",resultSet.getString(2));
			}	
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getApplicationCustomerDAO()");
			logger.error(sqlException);
		}			
		return hashMap;
	}
	public static HashMap getApplicationGroupDAO(String strAppId)
	{
		try
		{
			conn=DBConnection.createConnection();
			
			statement=conn.createStatement();
					
			ResultSet resultSet=statement.executeQuery("select USRGROUP_ID,GROUP_NAME from vims_user.USER_GROUP WHERE USRGROUP_ID=(select USRGROUP_ID from vims_user.GROUP_APPLICATION WHERE APPLICATION_ID='"+strAppId+"')");
		
			arrayList=new ArrayList();
			
			hashMap = new HashMap();
			while(resultSet.next())
			{
				hashMap.put("grpId",resultSet.getString(1));
				hashMap.put("grpName",resultSet.getString(2));				
			}	
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getApplicationGroupDAO()");
			logger.error(sqlException);
		}			
		return hashMap;
	}
	public static ArrayList getApplicationSpecialistDAO(String strAppId)
	{
		try
		{
			conn=DBConnection.createConnection();
			
			statement=conn.createStatement();
					
			ResultSet resultSet=statement.executeQuery("select USER_NM , FIRST_NAME+' '+ISNULL(MIDDLE_INITIAL,'')+' '+ISNULL(LAST_NAME,'') AS EMPLOYEE_NAME from vims_user.EMPlOYEE WHERE USER_NM in (select USER_NM from vims_user.APPLICATION_SPECIALISTS WHERE APPLICATION_ID='"+strAppId+"') ORDER BY EMPLOYEE_NAME");
		
			arrayList=new ArrayList();
						
			while(resultSet.next())
			{
				hashMap = new HashMap();
				hashMap.put("specId",resultSet.getString(1));
				hashMap.put("specName",resultSet.getString(2));
				arrayList.add(hashMap);
			}	
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getApplicationSpecialistDAO()");
			logger.error(sqlException);
		}			
		return arrayList;
	}
	
	public static ArrayList getApplicationSubCategoryDAO(String strAppId)
	{
		try
		{
			conn=DBConnection.createConnection();
			
			statement=conn.createStatement();
					
			ResultSet resultSet=statement.executeQuery("select APP_SUB_CAT_NAME from vims_user.APPLICATION_SUB_CATEGORIES WHERE APPLICATION_ID='"+strAppId+"'");
		
			arrayList=new ArrayList();
			
			hashMap = new HashMap();
			while(resultSet.next())
			{
				hashMap=new HashMap();
				hashMap.put("subcat",resultSet.getString(1));
				arrayList.add(hashMap); 
			}	
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.getApplicationSpecialistDAO()");
			logger.error(sqlException);
		}			
		return arrayList;
	}
	
	public static boolean checkAppAvailabilityDAO(String strAppName)
	{
		boolean result=false;
		logger=Logger.getLogger("Admin");
		try
		{
			conn=DBConnection.createConnection();
			
			statement=conn.createStatement();
					
			ResultSet resultSet=statement.executeQuery("select APPLICATION_NAME from vims_user.APPLICATION WHERE APPLICATION_NAME='"+strAppName+"'");
			
			result=resultSet.next();			
		}
		catch(SQLException sqlException)
		{
			logger.info("VIMSApplicationDAO.checkAppAvailabilityDAO()");
			logger.error(sqlException);		
		}	
		catch(Exception exception)
		{
			logger.info("VIMSApplicationDAO.checkAppAvailabilityDAO()");
			logger.error(exception);			
		}
		/*finally
		{
			DBConnection.closeConnection();
		}*/
		return result;
	}
	
	
	/*
	 * The getNewApplicationsAddedDAO() method is to get the newly added applications which have not been seen by administrator
	 * 
	 * @returns hashmap
	 */
	
	public static HashMap getNewApplicationsAddedDAO(String strLoginId) {
        
	    Logger logger=null;
		Connection connection=null;
	    PreparedStatement preparedStatement=null;
	    ResultSet resultSet=null;
	    ArrayList newApplicationList=null;
	    String strQuery=null;
	    String tempDate=null;
	    String appId=null;
	    String linkViewApp=null;
	    String modifyDelete=null;
	    
	    ArrayList applicationList=null;
	    HashMap applRecord=null;
	    HashMap applList=null;
	    int recordCount=0;
	    
	    try { // try block starts  
	    	   logger=Logger.getLogger("Admin");
	    	   strQuery="select * from vims_user.application WHERE APP_STATUS='Active' ORDER BY APPLICATION_NAME";
	    	   strQuery="EXEC vims_user.USP_Get_Application_Dtls @APPLICATION_ID=null,@APP_STATUS='Active',@User_NM=null,@App_Owner_Email=null,@Application_Name=null,@customer_name=null,@user_nm1='"+strLoginId+"'";
	    	   connection=DBConnection.createConnection();
	    	   preparedStatement=connection.prepareStatement(strQuery);
	    	   resultSet=preparedStatement.executeQuery();
	    	   
	    	   applicationList=new ArrayList();
	    	   applList=new HashMap();
	    	   if(resultSet!=null) {  // If starts
	    		   
	    		    while(resultSet.next()) { // while loop starts
	    		    	
	    		    	 //tempDate=resultSet.getString("SUPPORT_BEGIN_DATE");
	    		    	// System.out.println("=====value======="+resultSet.getString("view_FG"));
	    		    	// iDiffInDates=getDifferenceInDates(tempDate);
	    		    	 if(resultSet.getString("view_FG").equals("0")) {
	    		    		 // Enters into this block when the new applications found 
	    		    		 // If starts
	    		    		 recordCount++;
	    		    		 applRecord=new HashMap();
	    		    		 
	    		    		 appId=resultSet.getString("APPLICATION_ID");
    						 linkViewApp="<a href='./viewApplicationAction.do?param=viewApplication&appId="+appId+"'>"+resultSet.getString("APPLICATION_NAME")+"</a>";
    						// modifyDelete="<a href='./VIMSApplicationLookupDispatchAction.do?param=populateModifyAppPage&appId="+appId+"'>Modify</a> | <a href='./VIMSApplicationLookupDispatchAction.do?submit=deleteApplication&appId="+appId+"'/>Delete</a>";
	    		    		 applRecord.put("appId",linkViewApp);
	    						
	    		    		 applRecord.put("appName",linkViewApp);
	    		    		 applRecord.put("appStatus",resultSet.getString("APP_STATUS"));
	    		    		 applRecord.put("appOwner",resultSet.getString("APP_OWNER"));
	    		    		 applRecord.put("primCont",resultSet.getString("PRIMARY_CONTACT"));
	    		    		 applRecord.put("supBegDate",resultSet.getString("SUPPORT_BEGIN_DATE").substring(0,10));
	    		    		 
	    		    		 if(resultSet.getString("SUPPORT_END_DATE")!=null)
	    		    		 applRecord.put("supEndDate",resultSet.getString("SUPPORT_END_DATE").substring(0,10));
	    		    		 
	    		    		 //applRecord.put("supportCenter",resultSet.getString("SUPPORT_CENTER_ID"));
	    		    		 applRecord.put("supportManager",resultSet.getString("Support_Center_Manager_Name"));
	    		    		// applRecord.put("Modify|Delete",modifyDelete);
	    		    		 applRecord.put("applicationId",appId);
	    		    		  applicationList.add(applRecord);
	    		    	 } // If ends		 
	    		    } // while loop ends
	    	   } // If ends
	       } // try block ends 
	        catch(Exception exception) { //catch block starts
	    	 logger.info("=======Exception in getNewApplicationsAddedDAO======"+exception);
	    	 logger.error(exception);
	    } // end of catch block
	     
	    applList.put("applicationCount",recordCount);
	    applList.put("applicationList",applicationList);
	    
	    return applList;
 }
  // end of newApplicationsAddedDAO() method

	
 /*
  *  The setApplicationFlagDAO() method is to set the application view flag as viewed
  *  @param application id
  */	
	  public static void setApplicationFlagDAO(String strApplicationId) {
		  
		  try{ // try block starts
		        logger=Logger.getLogger("Admin");
		        conn=DBConnection.createConnection();
		        preparedStatement=conn.prepareStatement("update application set view_FG=1 where APPLICATION_ID=?") ;
                preparedStatement.setString(1,strApplicationId);
		        System.out.println(preparedStatement.executeUpdate());
		     } //try block ends
		       catch(Exception exception) { //catch block starts
		    	   
		    	  logger.info("----Exception in setApplicationFlagDAO-----------");
		    	  logger.error(exception);
		    } // end of catch block
	  }
	   // End of setApplicationFlafDAO() method
	  
	  
	  public static int deleteFileDAO(String file,String strAppName,String strAppId)
	  {
		  int filesCount=0;
		  System.out.println(" =====file in dao====="+file);
		  System.out.println(" =====file in dao====="+strAppName); 
		  String resultMsg="";
			logger=Logger.getLogger("Admin");
			try
			{
				conn=DBConnection.createConnection();
				statement=conn.createStatement();
				String delFileSql="update application_object set "+file+"=null where application_id='"+strAppId+"'";
				System.out.println("=========delFileSQL====="+delFileSql);
				int i=statement.executeUpdate(delFileSql);
				boolean result=false;
				if(i!=0)
				{
					result=true;
				}
				System.out.println("========i======"+i);
				filesCount=getFilesCountDAO(strAppId);
				return filesCount;
			}			
			catch(SQLException sqlException)
			{
				logger.info("VIMSApplicationDAO.deleteFileDAO()");
				logger.error(sqlException);	
				return filesCount;
			}	
			catch(Exception exception)
			{
				logger.info("VIMSApplicationDAO.deleteFileDAO()");
				logger.error(exception); 
				return filesCount;
			}
			/*finally
			{
				DBConnection.closeConnection();
			}*/
		
	  } 
	  
	  public static int[] getStndrdSLADAO()
	  {
			logger=Logger.getLogger("Admin");
			ResultSet resultSet=null;
			int []sla=new int[6];
			try
			{
				conn=DBConnection.createConnection();
				cstmt=conn.prepareCall("{CALL vims_user.USP_Get_Standard_Sla}");
				resultSet=cstmt.executeQuery();
				int i=0;
				while(resultSet.next())
				{
					sla[i]=resultSet.getInt("Response_Hrs");
					sla[i+1]=resultSet.getInt("Warning_Hrs");		
					i=i+2;
				}				
				return sla;
			}
			catch(SQLException sqlException)
			{
				sqlException.printStackTrace();
				logger.info("VIMSApplicationDAO.getStndrdSLADAO()");
				logger.error(sqlException);
				return null;
			}	
			catch(Exception exception)
			{
				exception.printStackTrace();
				logger.info("VIMSApplicationDAO.getStndrdSLADAO()");
				logger.error(exception);
				return null;
			}
			/*finally
			{
				try{
				statement.close();
				DBConnection.closeConnection();
				}
				catch(Exception e)
				{
					
				}
			}*/
	  }
	  
	  
	  public static void main(String args[])
	  {
		  System.out.println(getUsrGrpListDAO());
		 
	  }
}