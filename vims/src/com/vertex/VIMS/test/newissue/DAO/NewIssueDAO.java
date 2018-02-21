/*  File Name : NewIssueDAO.java
 *  
 *  copyright (c) 2008 Vertex.All rights reserved.
 *  
 *  Created By : Aditya.p
 *  
 *  Description : This is the DAO(DataAccessObject) class which contains some functions which will be called by the BD Class.
 *  			  By using this we can collect the List of Application names,Application sub categories and issue types.
 *  			  We are using this file to insert the issue in to the database as an issue entry too.
 *  
 *   Change History:
 * 	  Revision No.			Date		 @author			Description
 *		1.0					11-11-2008   Aditya.p			Initial version
 *
 *   
 *  
 *  			
 * 
 */

package com.vertex.VIMS.test.newissue.DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.vertex.VIMS.test.common.DBConnection;
import com.vertex.VIMS.test.common.VIMSMail;
import com.vertex.VIMS.test.newissue.IssueScheduler.NewIssueScheduler;
import com.vertex.VIMS.test.newissue.form.NewIssueForm;
import com.vertex.VIMS.test.VIMSSQLQueryInterface.*;

public class NewIssueDAO
{	 //class NewIssueDAO start
	static Connection conn=null;
	static ResultSet resultset=null;
	static CallableStatement callableStatement=null;
	static Statement stmt=null;
	
	static Logger logger=Logger.getLogger("Admin");
	
	public static ArrayList ApplicationNames(String strRoleType, String strUserID)
	{	//function ApplicationNames start
		
		PreparedStatement preparedStatement=null;
		ArrayList AppNames=new ArrayList();
		HashMap hashmap=null;
		
		try
		{//try start
			conn = DBConnection.createConnection();
			stmt = conn.createStatement(); 
			if(strRoleType.equals("Admin"))
			  {
				 //resultset = stmt.executeQuery(VIMSQueryInterface.SLACretaedApplicationNamesSql);
				
				callableStatement=conn.prepareCall("{CALL vims_user.USP_Get_Application_Dtls(?,?,?,?,?,?,?)}");
				 callableStatement.setString(1,null);
				 callableStatement.setString(2,"Active");
				 callableStatement.setString(3,null);
				 callableStatement.setString(4,null);
				 callableStatement.setString(5,null);
				 callableStatement.setString(6,null);
				 callableStatement.setString(7,strUserID);
				 callableStatement.execute();
				 resultset=callableStatement.getResultSet();
			  }
			else if(strRoleType.equals("User"))
			  {
				 /*preparedStatement = conn.prepareStatement(VIMSQueryInterface.UserApplicationNamesSql);

			     preparedStatement.setString(1, strUserID);
				
			    
			     //Calling an executeQuery method 
			       resultset = preparedStatement.executeQuery();*/
				
				 callableStatement=conn.prepareCall("{CALL vims_user.USP_Get_Application_Dtls(?,?,?,?,?,?,?)}");
				 callableStatement.setString(1,null);
				 callableStatement.setString(2,"Active");
				 callableStatement.setString(3,strUserID);
				 callableStatement.setString(4,null);
				 callableStatement.setString(5,null);
				 callableStatement.setString(6,null);
				 callableStatement.setString(7,strUserID);
				 callableStatement.execute();
				 resultset=callableStatement.getResultSet();
			
              }
			else if(strRoleType.equals("Customer"))
			{
				 /*preparedStatement = conn.prepareStatement(VIMSQueryInterface.CustomerNewIssue);
				 preparedStatement.setString(1, strUserID);
				 //System.out.println("--------In Customer-----------");
				 //System.out.println("--------strUserID-----------"+strUserID);
					
				 
			   //Calling an executeQuery method 
			     resultset = preparedStatement.executeQuery();*/
				 
				 callableStatement=conn.prepareCall("{CALL vims_user.USP_Get_Application_Dtls(?,?,?,?,?,?,?)}");
				 callableStatement.setString(1,null);
				 callableStatement.setString(2,"Active");
				 callableStatement.setString(3,null);
				 callableStatement.setString(4,strUserID);
                 callableStatement.setString(5,null);
				 callableStatement.setString(6,null);
				 callableStatement.setString(7,null);
				 
	             callableStatement.execute();
				 resultset=callableStatement.getResultSet();
				
			}
			while (resultset.next())
			{//while started
				hashmap = new HashMap();
				hashmap.put("id", resultset.getString(1));
				hashmap.put("name", resultset.getString(2));
				AppNames.add(hashmap);
				//AppNames.add(resultset.getString(1));
				 
			}//while end
			stmt.close();
			resultset.close();
		} //try end
		catch (Exception e)
		{//catch start
			logger.error(e);
			System.out.println("==========Exception Occured==========");
			e.printStackTrace();
		}//catch end
		finally
		{//finally start
			DBConnection.closeConnection();
		}//finally end
		return AppNames;	//returning the ApplicationNames arraylist to the BD class
		
	}	//end of function ApplicationNames

	public static ArrayList ApplicationSubCategory()
	{	//function ApplicationSubCategory start
				
		ArrayList AppSubCategoryNames=new ArrayList();
		HashMap hashmap=null;
		
		try
		{//try start
			conn = DBConnection.createConnection();
			stmt = conn.createStatement();
			resultset = stmt.executeQuery(VIMSQueryInterface.ApplicationSubCategorySql);
			
			while (resultset.next())
			{//while started
				hashmap = new HashMap();
				//hashmap.put("AppId", resultset.getString(1));
				//hashmap.put("AppSubName", resultset.getString(2));
				//AppSubCategoryNames.add(hashmap);
				AppSubCategoryNames.add(resultset.getString(1));

			}//while end
			stmt.close();
			resultset.close();
		} //try end
		catch (Exception e)
		{//catch start
			logger.error(e);
			System.out.println("==========Exception Occured==========");
			e.printStackTrace();
		}//catch end
		finally
		{//finally start
			DBConnection.closeConnection();
		}//finally end
		//System.out.println("AppSubCategoryNames in DAO class is $$%^&*()*^%#$#%^&*"+AppSubCategoryNames);
		return AppSubCategoryNames;	//returning the AppSubCategoryNames arraylist to the BD class
	}	//end of function ApplicationSubCategory

	public static ArrayList IssueTypes()throws Exception
	{	//function IssueTypes start
				
		ArrayList IssueType=new ArrayList();
		HashMap hashmap=null;ResultSet rs =null;
		Statement stm=null;
		Connection con = null;
		try 
		{//try start
			con = DBConnection.createConnection();
			stm = con.createStatement();
			rs = stm.executeQuery(VIMSQueryInterface.IncidentTypeSql);
			while (rs.next())
			{//while start
				hashmap = new HashMap();
				hashmap.put("id", rs.getString(1));
				hashmap.put("name", rs.getString(2));
				IssueType.add(hashmap);

			}//while end			
		} //try end
		catch (Exception e)
		{//catch start
			logger.error(e);
			System.out.println("=============Exception Occured================");
			e.printStackTrace();
		}//catch end
		finally
		{//finally start
			if(con!=null){ con.close(); }
			if(stm!=null){ stm.close(); }
			if(rs!=null){ rs.close(); }
		}//finally end
		return IssueType;	//returning the IssueType arraylist to the BD class
	}	//end of function IssueTypes

	/**
	 * @param strSelectedApplication
	 * @return subCategories
	 */
	public static ArrayList subCategories(String strSelectedApplication)
	{	//function subCategories start
		
		//System.out.println("=============strSelectedApplication in DAO==========="+strSelectedApplication);
		//String strQuery="SELECT APP_SUB_CAT_NAME FROM APPLICATION_SUB_CATEGORIES WHERE APPLICATION_ID IN(SELECT APPLICATION_ID from vims_user.APPLICATION WHERE APPLICATION_NAME='"+strSelectedApplication+"')";
		String strQuery="SELECT APP_SUB_CAT_NAME from vims_user.APPLICATION_SUB_CATEGORIES WHERE APPLICATION_ID='"+strSelectedApplication+"' order by APP_SUB_CAT_NAME" ;
		ArrayList subCategories=new ArrayList();
		HashMap hashmap=null;
		try
		{	//try start
			conn = DBConnection.createConnection();
			stmt = conn.createStatement();
			resultset = stmt.executeQuery(strQuery);
			while (resultset.next())
			{	//while start
				if((resultset.getString(1)!=null) && !("".equalsIgnoreCase(resultset.getString(1))))
				{
				subCategories.add(resultset.getString(1));
				}//retrieving the subcategories for the selected application
			}//while end
			stmt.close();
			resultset.close();
			//System.out.println("===========Inside DAO============"+subCategories);
		} //try end
		catch (Exception e)
		{//catch start
			logger.error(e);
			System.out.println("=============Exception Occured==================");
			e.printStackTrace();
		}//catch end
		finally
		{//finally start
			DBConnection.closeConnection();
		}//finally end
		return subCategories;	//returning the SubCategoryNames arraylist for the selected Application to the BD class
	}	//end of function subCategories

//	public static int AddNewIssue(String strApplicationName, String strApplicationSubCategories, String strTitle, String strReference, String strIssueSeverity, String strDescription, FormFile fileUpload,String filePath) throws FileNotFoundException, IOException
//	{
//		String strContentType=fileUpload.getContentType();
//		String fileName=fileUpload.getFileName();
//	    byte[] filedata=fileUpload.getFileData();
//	    
//	    if(fileName.equals(""))
//	    {//if start
//	    	File filetoCreate=new File(filePath,fileName);
//	    
//	    if(!filetoCreate.exists())
//        {//if start
//            FileOutputStream fileoutputstream=new FileOutputStream(filetoCreate);
//            fileoutputstream.write(filedata);
//            fileoutputstream.flush();
//            fileoutputstream.close();
//        }//if end
//	}//if end
//		 
//	}

	public static int AddNewIssue(NewIssueForm newIssueForm, String filePath, String strUserID) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException
	{	//function AddNewIssue start
		
		System.out.println("==========User ID in DAO Add New Issue========"+strUserID);
		String strApplicationName;
		String strApplicationSubCategories;
		String strTitle;
		String strReference;
		String strIssueSeverity;
		String strDescription;
		String strContentType;
		String fileName1;
		String fileName2;
		String strIncidentID;		
		String strFile_ID;
		String strIssuePriority;
		String Posted_By;
		
		int intResult;
		int intReturnValue;
		
		FormFile fileUpload1;
		FormFile fileUpload2;
		//ResultSet resultset;
		//ResultSet rs;
		
		
		CallableStatement callablestmt=null;
		//PreparedStatement preparedStatement;
		
				
		/* Getting the values for the respective getter() methods */
		
		strApplicationName=newIssueForm.getapplicationName();
		strApplicationSubCategories=newIssueForm.getApplicationSubCategory();
		strTitle=newIssueForm.getTitle();
		strReference=newIssueForm.getreference();
		strIssueSeverity=newIssueForm.getIssueType();
		strIssuePriority=newIssueForm.getIssuePriority();	
		strDescription=newIssueForm.getDescription();
		//fileUpload1=newIssueForm.getFileUpload1();
		//fileUpload2=newIssueForm.getFileUpload2();
		//System.out.println("=============fileUpload1============"+fileUpload1.equals(""));
		//System.out.println("=============fileUpload2============="+fileUpload2.equals(""));
		//System.out.println("==========strTitle============"+strTitle);
		
		/*if(strTitle==null || strTitle=="" || strTitle==" ")
		{
			return intResult=0;
		}
		else
		{*/	
		//fileName1=fileUpload1.getFileName();
		//fileName2=fileUpload2.getFileName();
		//System.out.println("=================fileName1================="+fileName1);
		//System.out.println("=================fileName2================="+fileName2);
		/*if(fileName1!=null && fileName1!="")
		{
		UploadFile(filePath,fileUpload1);
		}
		if(fileName2!=null && fileName2!="")
		{
		UploadFile(filePath,fileUpload2);
		}*/
		
		/********************************************************/
		//boolean isFileUploaded = false;
		String filesUploaded = "";
		FormFile []files=new FormFile[5];
		files[0]=newIssueForm.getFile0();
		files[1]=newIssueForm.getFile1();
		files[2]=newIssueForm.getFile2();
		files[3]=newIssueForm.getFile3();
		files[4]=newIssueForm.getFile4();
		//String filePath=(getServlet().getServletContext().getRealPath("/")+"VIMSUPLOAD").replace("\\.\\", "\\").replace("I_VIMSD\\I_VIMSD", "I_VIMSD");
		boolean b=new File(filePath).mkdirs();
		System.out.println("======b========="+b); 
		
		for (FormFile formFile : files) 
		{
			if(formFile!=null)
			{
				String formFileName = formFile.getFileName();
				byte[] formFileData = formFile.getFileData();
				File file = new File(filePath, formFileName);
				// Continue if there is no file
				if ("".equals(formFileName)) 
				{
					continue;
				}
				else if(!formFileName.equalsIgnoreCase(""))
				{
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(formFileData);
					fos.flush();
					fos.close();
					//isFileUploaded = true;
					//SaveFileData(strIncidentID,formFileName,strUserID);
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
		/*********************************************************/
		
		conn=DBConnection.createConnection();	//creating the connection with database
				
		callablestmt=conn.prepareCall("{?=CALL vims_user.incident_insert(?,?,?,?,?,?,?,?)}");	//preparing to call the stored procedure
		
		/* Setting the values to be passed/received */
		
		callablestmt.registerOutParameter(1,Types.OTHER);
		callablestmt.setString(2,strApplicationName);
		callablestmt.setString(3,strIssueSeverity);
		callablestmt.setString(4,strIssuePriority);
		callablestmt.setString(5,strUserID);
		callablestmt.setString(6,strTitle);
		callablestmt.setString(7,strApplicationSubCategories);
		callablestmt.setString(8,strDescription);
		callablestmt.setString(9,"@NCM_ID_OUT OUTPUT");//"@NCM_ID_OUT OUTPUT");
		
		callablestmt.execute();	//calling the stored procedure
				
		String strIDValue=callablestmt.getString(1);	//getting the return value from the stored procedure
				
		strIncidentID=getNewIssueID();	//calling the function NewIssueID and storing the return value into a variable
		
		/*if(fileName1!=null && fileName1!="")
		{
		    System.out.println("==========Saving File1================");
			SaveFileData(strIncidentID,fileName1,strUserID);
		}
		if(fileName2!=null && fileName2!="")
		{
			System.out.println("==========Saving File2================");
			SaveFileData(strIncidentID,fileName2,strUserID);
		}*/
		for (FormFile formFile : files) 
		{
			if(formFile!=null)
			{
				String formFileName = formFile.getFileName();
				SaveFileData(strIncidentID,formFileName,strUserID);
			}
		}
		DBConnection.closeConnection();
		callablestmt.close();
		
		if(strIDValue.equals("0"))
		{	//if start
			intResult=1;
		}	//if end
		else
		{	//else start
			intResult=0;
		}	//else end
		
		return intResult;	//returning the result value to the BD class
		
	}//function AddNewIssue end
 public static void SaveFileData(String strIncidentID, String fileName,String strUserID)
 {
	    String strFile_ID;
	    int intReturnValue;
	    
	    PreparedStatement preparedStatement=null;
	    
	    strFile_ID=strIncidentID+""+fileName;
		
		try
		{
			preparedStatement = conn.prepareStatement(VIMSQueryInterface.AddFileDetailsSql);
			preparedStatement.setString(1,strIncidentID);
			preparedStatement.setString(2,strFile_ID);
			preparedStatement.setString(3,fileName);
			preparedStatement.setString(4,strUserID);
			intReturnValue = preparedStatement.executeUpdate();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
 }

	public static void UploadFile(String filePath, FormFile fileUpload) throws IOException
	{
		String strContentType;
		String fileName;
		byte[] filedata;
		
		strContentType=fileUpload.getContentType();	//getting the file content type
		fileName=fileUpload.getFileName();	//getting the file name
		
		try
		{
			filedata=fileUpload.getFileData();
		} 
		catch (FileNotFoundException e)
		{
			
			e.printStackTrace();
		} 
		catch (IOException e) {
			
			e.printStackTrace();
		}	//getting the file data
		
		if(!fileName.equals(""))
	      {	//if start
	          	          
	          File filetoCreate=new File(filePath,fileName);
	          
	          if(!filetoCreate.exists())
	          {	//if start
	              FileOutputStream fileoutputstream=new FileOutputStream(filetoCreate);
	              fileoutputstream.write(fileUpload.getFileData());
	              fileoutputstream.flush();
	              fileoutputstream.close();
	          }//if end
	          
	      }	//if end
		
	}

	public static String getNewIssueID()
	{	//function getNewIssueID start
		
		String strQuery="SELECT MAX(INCIDENT_ID) from vims_user.INCIDENT";
	
		String strIncidentID = null;
		
		try
		{	//try start
			conn=DBConnection.createConnection(); //preparing the connection with database
			stmt = conn.createStatement();
			resultset = stmt.executeQuery(strQuery);//executing the query
			
			while (resultset.next())
			{   //while start
				strIncidentID = resultset.getString(1);//getting the incident id of the posted issue
			}	//while end
			resultset.close();
			stmt.close();
		} //try end
		catch (Exception e)
		{//catch start
			logger.error(e);
			System.out.println("==============Exception Occured=================");
		}//catch end
		
		//System.out.println("=============strIncidentID==============="+strIncidentID);
		
		return strIncidentID;//returning the issueid value
	}//function getNewIssueID end

	public static String getContextPath(HttpServletRequest request) 
	 {	   	
  			String strPath=null;
  		
			try 
			   {				  
				  strPath=request.getServerName();	
				  //System.out.println("===request.getServerName()==="+request.getServerName()); 
				  //System.out.println("===rrequest.getScheme()()==="+request.getScheme()); 
				 // System.out.println("===request.getRemoteHost()==="+request.getRemoteHost());
				  strPath="http://"+strPath;
				  strPath=strPath+":"+request.getServerPort();
				  strPath=strPath+request.getContextPath();
				  //System.out.println("========getServletPath======="+request.getServletPath());
				  //System.out.println("=======path========"+strPath);
				  return strPath;    
			   } 
		  catch(Exception exception)
			 {				
				 System.out.println("Exception raised in getDefectLinkPath"+exception);
				 return strPath;
			 }
		 
	 }
	
	public static boolean NewIssueMail(NewIssueForm newIssueForm, String strUserID, String strContextPath, String strNewIssueID,String strRoleType)
	{
		String strApplicationID;
		String strApplicationName = null;
		String strApplicationSubcategory;
		String strSeverity;
		String strSender = null;
		String strGroupSupervisorName = null;
		String strGroupSupervisorMailID = null;
		String strSupportManagerName = null;
		String strSupportManagerMailID = null;
		String strIssueSeverity = null;
		String strIssueTitle=null;
		
		String strDescription;
		String strReceiverName=null;
		
		String strAction="newissue";
		
		PreparedStatement preparedStatement=null;
		CallableStatement callablestmt=null;
		
		boolean MailResponse;
		
		strApplicationID=newIssueForm.getapplicationName();
		strApplicationSubcategory=newIssueForm.getApplicationSubCategory();
		strSeverity=newIssueForm.getIssueType();
		strDescription=newIssueForm.getDescription();
		strIssueTitle=newIssueForm.getTitle();
		
		System.out.println("==============strApplicationID=========="+strApplicationID);
		
		
			try
			{
				conn = DBConnection.createConnection();
				callablestmt=conn.prepareCall("{CALL vims_user.USP_Get_Mngr_sup_Addr(?)}");
				callablestmt.setString(1,strApplicationID);
				
				System.out.println("============callablestmt.execute();==============="+callablestmt.execute());
				
				resultset=callablestmt.getResultSet();
				
				//preparedStatement = conn.prepareStatement(VIMSQueryInterface.getApplication_Group_SupervisorSQL);
				//preparedStatement.setString(1, strApplicationName);
				//resultset = preparedStatement.executeQuery();
				
				while(resultset.next())
				{
					//strReceiverName=resultset.getString(1);
					strGroupSupervisorName=resultset.getString(1);
					strGroupSupervisorMailID=resultset.getString(2);
					strSupportManagerName=resultset.getString(3);
					strSupportManagerMailID=resultset.getString(4);
					
					//System.out.println("=========strSupportManagerName========="+strSupportManagerName);
					//System.out.println("=========strGroupSupervisorName========="+strGroupSupervisorName);
				}
				System.out.println("=========strSupportManagerName========="+strSupportManagerName);
				System.out.println("=========strGroupSupervisorName========="+strGroupSupervisorName);
				
				resultset.close();
				callablestmt.close();
				
				/*preparedStatement=conn.prepareStatement("select work_email_address from employee where user_nm='"+strUserID+"'");
				resultset = preparedStatement.executeQuery();
				while(resultset.next())
				{
					strSender=resultset.getString(1);
					
					System.out.println("==============strSender============="+strSender);
				}
				resultset.close();
				preparedStatement.close();*/
				
				preparedStatement=conn.prepareStatement("select incident_type_desc from vims_user.incident_type where incident_type='"+strSeverity+"'");
				resultset=preparedStatement.executeQuery();
				while(resultset.next())
				{
					strIssueSeverity=resultset.getString(1);
					
					//System.out.println("==============strIssueSeverity============="+strIssueSeverity);
				}
				resultset.close();
				preparedStatement.close();
				
				preparedStatement=conn.prepareStatement("select application_name from vims_user.application where application_id='"+strApplicationID+"'");
				resultset=preparedStatement.executeQuery();
				while(resultset.next())
				{
					strApplicationName=resultset.getString(1);
				}
				resultset.close();
				preparedStatement.close();
				
				System.out.println("==============strApplicationName=========="+strApplicationName);
				
				if((strRoleType.equalsIgnoreCase("Admin"))||(strRoleType.equalsIgnoreCase("User")))
				{
					callablestmt=conn.prepareCall("{CALL vims_user.USP_Get_Email_By_Type(?)}");
					callablestmt.setString(1,strUserID);
					callablestmt.execute();
					
					resultset=callablestmt.getResultSet();
					while(resultset.next())
					{
						strSender=resultset.getString(1);
						System.out.println("=============strSender for Admin or User================"+strSender);
					}
					callablestmt.close();
					resultset.close();
				}
				else
				{
					strSender=strUserID;
					System.out.println("=============strSender for Customer================"+strSender);
					
				}
			} 
			catch (Exception e)
			{
				logger.error(e);
			}
			finally
			{
				DBConnection.closeConnection();
			}
			
			if(strGroupSupervisorMailID==null && "".equalsIgnoreCase(strGroupSupervisorMailID)&& strSupportManagerMailID==null && "".equalsIgnoreCase(strSupportManagerMailID)&& strSender==null && "".equalsIgnoreCase(strSender))
			{
				MailResponse=false;
				return MailResponse;
			}
			else
			{
				//String Person="GroupSupervisor";
			HashMap details1=new HashMap();
			
			details1.put("reciever",strGroupSupervisorMailID);
			details1.put("sender","VIMS@vertexcs.com");
	        details1.put("name",strGroupSupervisorName+",VIMS Group Supervisor");
	        details1.put("page","newissue");
	        details1.put("person","GroupSupervisor");
	        details1.put("ID",strNewIssueID);
	        //details1.put("ID",strApplicationName);
	        details1.put("AppSubCategory",strApplicationSubcategory);
	        details1.put("status",strIssueSeverity);
	        details1.put("description",strDescription);
	        details1.put("statustoTo","open");
	        details1.put("title",strIssueTitle);
	        details1.put("applicationName",strApplicationName);
	        MailResponse=VIMSMail.sendMail(details1,strContextPath,strAction);
	        
             HashMap details2=new HashMap();
			
			details2.put("reciever",strSupportManagerMailID);
			details2.put("sender", "VIMS@vertexcs.com");
	        details2.put("name",strSupportManagerName+",VIMS Support Manager");
	        details2.put("page","newissue");
	        details2.put("person","SupportManager");
	        details2.put("ID",strNewIssueID);
	       //details2.put("ID",strApplicationName);
	        details2.put("AppSubCategory",strApplicationSubcategory);
	        details2.put("status",strIssueSeverity);
	        details2.put("description",strDescription);
	        details2.put("statustoTo","open");
	        details2.put("title",strIssueTitle);
	        details2.put("applicationName",strApplicationName);
	        MailResponse=VIMSMail.sendMail(details2,strContextPath,strAction);
	        
	        return MailResponse;
		  }
			
	}

	public static ArrayList getIssuePriority()
	{
		ArrayList IssuePriority=new ArrayList();
		PreparedStatement preparedStatement=null;
		HashMap hashmap;
		try
		{
			conn = DBConnection.createConnection();
			preparedStatement = conn.prepareStatement(VIMSQueryInterface.getIssuePrioritySQL);
			resultset=preparedStatement.executeQuery();
			
			while(resultset.next())
			{
				hashmap=new HashMap();
				
				hashmap.put("Priorityid",resultset.getString(1));
				hashmap.put("priorityname", resultset.getString(2));
				
				IssuePriority.add(hashmap);
			}
		} 
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return IssuePriority;
	}

	public static int getApplicationSLA(String strApplicationSelected,
			String strIssueSeverity)
	{
		int intHours = 0; 
		CallableStatement callableStatement;
		
		try
		{
			conn=DBConnection.createConnection();
			callableStatement=conn.prepareCall("{CALL vims_user.USP_Get_INCT_Value(?,?)}");
			callableStatement.setString(1,strApplicationSelected);
			callableStatement.setString(2,strIssueSeverity);
			callableStatement.execute();
			System.out.println("=======callableStatement.execute();====="+callableStatement.execute());
			resultset=callableStatement.getResultSet();
			while(resultset.next())
			{
				intHours=resultset.getInt(1);	
			}
			resultset.close();
		} 
		catch (SQLException e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		return intHours;
	}

	public static String[] getNewIssueMailReceivers(String strApplicationName)
	{
		CallableStatement callablestmt=null;
		String strMailReveiverNames[]=new String[2];
		
			try
			{
				conn = DBConnection.createConnection();
				callablestmt = conn	.prepareCall("{CALL vims_user.USP_Get_Mngr_sup_Addr(?)}");
				callablestmt.setString(1, strApplicationName);
				callablestmt.execute();
				//System.out.println("============callablestmt.execute();==============="+ callablestmt.execute());
				resultset = callablestmt.getResultSet();
				System.out.println("==========resultset========="+resultset);
				
				/*if(resultset.next())
				{
					System.out.println("======In resultset===========");
				}
				else
				{
					resultset.beforeFirst();
				}
				*/
								
				while (resultset.next())
				{
					//strMailReveiverNames={,};
					strMailReveiverNames[0] = resultset.getString(1);
					System.out.println("==============strMailReveiverNames[0]==============="+resultset.getString(1));
					strMailReveiverNames[1] = resultset.getString(3);
					System.out.println("==============strMailReveiverNames[1]==============="+resultset.getString(3));
				}
				resultset.close();
				callablestmt.close();
			} 
			catch (Exception e)
			{
				logger.error(e);
			}
		return strMailReveiverNames;
	}

	public static int CheckMailServer()
	{
		Connection con=null;
		CallableStatement csmt=null;
		int result = 0;
		try
		{
			con=DBConnection.createConnection();
			csmt=con.prepareCall("{?=CALL vims_user.USP_Check_Mail_Server()}");
			csmt.registerOutParameter(1,Types.OTHER);
			csmt.execute();
			result=csmt.getInt(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}	//class NewIssueDAO end
