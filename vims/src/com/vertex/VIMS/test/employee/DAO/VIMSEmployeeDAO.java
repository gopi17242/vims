// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 1/31/2009 6:42:12 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   VIMSEmployeeDAO.java

package com.vertex.VIMS.test.employee.DAO;

import com.vertex.VIMS.test.VIMSSQLQueryInterface.VIMSQueryInterface;
import com.vertex.VIMS.test.clients.form.CustomerBean;
import com.vertex.VIMS.test.common.DBConnection;
import com.vertex.VIMS.test.employee.BD.VIMSEmployeeBD;
import com.vertex.VIMS.test.employee.form.NewEmployeeFormBean;
import java.io.File;
import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;
import org.apache.log4j.Logger;

public class VIMSEmployeeDAO
{

    public static ArrayList listOfEmployees(ResultSet resultSet)
    {
        ArrayList employeesList = new ArrayList();
        try
        {
            for(; resultSet.next(); employeesList.add(employeeHashMap))
            {
                  employeeHashMap = new HashMap();
                  String employeeView = "<a href='employeeLookUp.do?function=ViewEmployee&eid="+(resultSet.getString(5)).substring(0,(resultSet.getString(5).indexOf('@')))+"'>"+resultSet.getString(1)+"</a>";
                  //System.out.println("----Employee View----"+employeeView);
                  employeeHashMap.put("strEmpid", employeeView);
                  employeeHashMap.put("strName", resultSet.getString(2));
                  employeeHashMap.put("strStartDate", resultSet.getString(3));
                  employeeHashMap.put("strPhone", resultSet.getString(4));
                  employeeHashMap.put("strEmail", resultSet.getString(5));
                  //System.out.println("-------Email--------"+resultSet.getString(5));
                  employeeHashMap.put("strStatus", resultSet.getString(6));
                  employeeHashMap.put("strSelCountry", resultSet.getString(17));
             	
                String strStatus =(String)resultSet.getString(6);
                
                String linkmodify = null;
                if(strStatus.equalsIgnoreCase("Active"))
                    //linkmodify = (new StringBuilder("<a href='./employeeLookUp.do?function=ModifyEmployee&strSelCountry="+(String)resultSet.getString(17)+"&status=")).append(resultSet.getString(6)).append("&eid=").append(resultSet.getString(5).substring(0,(resultSet.getString(5).indexOf('@')))).append("&menuId=Employee&pageId=ModifyEmployee'>Modify</a>").toString();
                	
                	linkmodify = (new StringBuilder("<a href='./employeeLookUp.do?function=ModifyEmployee&strSelCountry="+(String)resultSet.getString(17)+"&status=")).append(resultSet.getString(6)).append("&eid=").append(resultSet.getString("USER_NM")).append("&menuId=Employee&pageId=ModifyEmployee'>Modify</a>").toString();
                else
                    linkmodify = (new StringBuilder("<a href='./employeeLookUp.do?function=ModifyEmployee&strSelCountry="+(String)resultSet.getString(17)+"&status=")).append(resultSet.getString(6)).append("&eid=").append(resultSet.getString("USER_NM")).append("&menuId=Employee&pageId=ModifyEmployee'>Modify</a>|<a href=\"#\" onclick=\"fnDelete('").append(resultSet.getString(1)).append("')\"/>Delete</a>").toString();
                employeeHashMap.put("ModifyDelete", linkmodify);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //System.out.println("--------EmployeeList------"+employeesList);
        return employeesList;
    }

    public static ArrayList getAllEmployeeDetails()
    {
        
    	//System.out.println("---------------strSelectedIssueType in DAO---------"+strSelectedIssueType);
    	Logger logger = Logger.getLogger("Employee");
        ArrayList arraylist;
        Connection con = DBConnection.createConnection();
        ResultSet resultSet = null;
        String strEmailID=null;
        ArrayList employeesList = new ArrayList();
        //System.out.println((new StringBuilder("--------strSelectedIssueType in DAO------------")).append(strSelectedIssueType).toString());
       
        //if(strSelectedIssueType.equalsIgnoreCase("Active")) {

            try
            {
                callablestmt = con.prepareCall("{CALL vims_user.USP_Get_Employee_Dtls(?,?,?,?)}");
                
                callablestmt.setString(1, null);
                callablestmt.setString(2, null);
                callablestmt.setString(3, null);
                callablestmt.setString(4, null);
                
                resultSet = callablestmt.executeQuery();
             
                employeesList = listOfEmployees(resultSet);
            }
            catch(Exception exception)
            {
                logger.info("VIMSClientDAO.getClientDetailsDAO");
                logger.error(exception);
                System.out.println((new StringBuilder("The Exception is:")).append(exception).toString());
            }

        /*else {
            try
            {
              
                //System.out.println("-----------In  strSelectedIssueType as InActive----------");
                callablestmt = con.prepareCall("{CALL vims_user.USP_Get_Employee_Dtls(?,?,?,?)}");
                
                callablestmt.setString(1, null);
                callablestmt.setString(2, null);
                callablestmt.setString(3, strSelectedIssueType);
                callablestmt.setString(4, null);
                
                resultSet = callablestmt.executeQuery();
                
                employeesList = listOfEmployees(resultSet);
            }
            catch(Exception exception)
            {
                logger.info("VIMSClientDAO.getClientDetailsDAO");
                logger.error(exception);
                System.out.println((new StringBuilder("The Exception is:")).append(exception).toString());
            }
       } */

        //System.out.println("-----------arrayList----------");
        arraylist = employeesList;
     
        return arraylist;
        
    }

    /**********Method Name:-------->getSupervisorNames****************/
    
    /**********In this method the values are retrieved from database to be displayed under supervisor dropdown*********/    

     public static ArrayList getSupervisorNames() 
   	{
   		Logger logger = Logger.getLogger("Employee");
   		
   		try
   		{
   			Connection con = DBConnection.createConnection();
   			ArrayList supervisorList = new ArrayList();
   			Statement statement = con.createStatement();
   			ResultSet resultSet = statement.executeQuery(VIMSQueryInterface.getSupervisorNames);
   			HashMap hashMap;

   			while (resultSet.next())
   			{
   				hashMap = new HashMap();
   				hashMap.put("strUsername",resultSet.getString(1));
   				hashMap.put("strEmpNames",resultSet.getString(2));
   				supervisorList.add(hashMap);
   			}
   			return supervisorList;
   			
   		} 
   		catch (Exception sql) 
   		{
   			//for logging information messages 
   			logger.info("ClassName.MethodName");
   			System.out.println("=============== int eh catch block ot the DAO class");
   			//for logging error messages
   			logger.error("The Exception raised is :" + sql);

   			return null;
   		}
   		finally 
   		{
   			try
   			 {
   				DBConnection.closeConnection();
   			 } 
   			catch (Exception e) 
   			{
   				e.printStackTrace();
   			}
   		}
   	}
   		
     /**********Method Name:-------->addEmployee****************/
     
     /**********This method is used to add an employee to the list *********/    

     public static int addEmployee(NewEmployeeFormBean bean,String strUserID) 
      {
   		Logger logger = Logger.getLogger("Employee");
   		try {

   			Connection con = DBConnection.createConnection();
   		    String supervisorName=null;
   		    
   		    supervisorName=bean.getStrSupervisorName();
   		       if(supervisorName==null || "".equals(supervisorName)){
   		    	   supervisorName="";   
   		       } 
   		
   			    String emailID= bean.getStrEmail();
   			    String strTemp=null;
        	    String strloginID=null;
        	    strTemp=emailID;
       	        strloginID=strTemp.substring(0,strTemp.indexOf('@'));
      	        
       	        String strFirstName=null;
        	    String strlastName=null;
        	    String position=null;
        		String strAdd1=null;
        		String strAdd2=null;
        		String strCity=null;
          	    String strState=null;
          	    String strCountry=null;
          	    String workPhone=null;
                String mobileNo=null;
                String workLocation=null;
     		    String strZipNo=null;
     		    String strStatus=null;
     		    String empID=null;
     		    String strType="Insert";
     		   
     		  
   			   CallableStatement callablestmt=null;
   			
   			   strFirstName=bean.getStrFirstName();
   			   //System.out.println("-------------strFirstName in DAO---------"+strFirstName);
   			   strlastName= bean.getStrLastName();
   			   //System.out.println("-------------strlastName in DAO---------"+strlastName);
   			   strAdd1=bean.getStrAddress1();
   			   
   			   strAdd2=bean.getStrAddress2();
   			   if(strAdd2==null || "".equals(strAdd2)){
   				strAdd2="";
   			   }
   		       /*if(strAdd1==null || "".equals(strAdd1)){
   		    	   strAdd1="";   
   		       }*/ 
   		       
   		       strCity=bean.getStrCity();
   		       //System.out.println("-------City in add--------"+strCity);
   		       if(strCity==null || "".equals(strCity)){
   		    	   strCity="";   
   		       }
   		       
   		       strState=bean.getStrState();
   		       //System.out.println("--------State No in Add--------:"+strState);
   		       if(strState==null || "".equals(strState) || " ".equals(strState)){
   		    	  strState=null; 
   		       } 
   			
   		       strCountry=bean.getStrCountry();
   		       if(strCountry==null || "".equals(strCountry) || " ".equals(strCountry)){
   		    	   strCountry=null;   
   		       } 
   		     
   		      workPhone= bean.getStrWorkPhone();
   		      position= bean.getStrEmployeePosition();
   		      //System.out.println("-------------position in DAO---------"+position);
   		      strStatus=bean.getStrStatus();
   		      empID=bean.getStrEmployeeID();

   		      mobileNo=bean.getStrMobile();
   		       /*if(mobileNo==null || "".equals(mobileNo)){
   		    	   mobileNo="";   
   		       }*/ 
   		     
   		       workLocation= bean.getStrWorkLocation();
   		       //System.out.println("-------WorkLocation------"+workLocation);
   		       if(workLocation==null || "".equals(workLocation)){
   		    	   workLocation="";   
   		       } 
   		     
   		       strZipNo= bean.getStrZip();
   		      
   			      
   		       if(strZipNo==null || "".equals(strZipNo)){
   		    	   strZipNo="";   
   		       }
   		       String strMiddleName=bean.getStrMiddleName();
   		       System.out.println("=====my test================"+position);
   		    callablestmt=con.prepareCall("{CALL vims_user.USP_Save_Employee(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");//preparing to call the stored procedure
   			callablestmt.setString(1,empID);
   		    callablestmt.setString(2,strFirstName);
   			callablestmt.setString(3,strlastName);
   			callablestmt.setString(4,position);
   			callablestmt.setString(5,strAdd1);
   			callablestmt.setString(6,strAdd2);
   			callablestmt.setString(7,strCity);
   			callablestmt.setString(8,strState);
   			//System.out.println("-------State No--------"+strState);
   			//callablestmt.setString(9,strCountry);
   			callablestmt.setString(9,emailID);
   			callablestmt.setString(10,workPhone);
   			callablestmt.setString(11,supervisorName);
   			callablestmt.setString(12,mobileNo);
   			callablestmt.setString(13,workLocation);
   			callablestmt.setString(14,strZipNo);
   			callablestmt.setString(15,strStatus);
   			callablestmt.setString(16,null);
   			callablestmt.setString(17,strloginID);
   			//System.out.println("-----------strloginID in addEmployeeDAO------------"+strloginID);
   			callablestmt.setString(18,strUserID);
   			//System.out.println("-----------strUserID in addEmployeeDAO------------"+strUserID);
   			callablestmt.setString(19,strType);
   			//System.out.println("-----------strType in addEmployeeDAO------------"+strType);

   			callablestmt.setString(20,strMiddleName);
   			callablestmt.setString(21,strCountry);
   			
   			System.out.println("======Country ID======="+strCountry);
   			callablestmt.setString(20,strMiddleName);

   			
   			callablestmt.execute();	
   		  //calling the stored procedure
   	        return 0;
   		} 
   		catch (Exception sql)
   		{
   			logger.info("ClassName.MethodName");
   			System.out.println("--------Exception-----------"+sql);
   			return 1;
   		}
   		finally 
   		{
   			try 
   			 {
   				DBConnection.closeConnection();
   			 }
   			catch (Exception e) 
   			 {
   				e.printStackTrace();
   			 }
   		}
   		
   	}

     /**********Method Name:-------->editEmployeeDetail****************/
     
     /**********This method is used to display an employee before modifying the details*********/    
   	
     public static HashMap editEmployeeDetail(String eid)
  	  {
  	     Logger logger = Logger.getLogger("Employee");
  	     //System.out.println("----------eid------editEmployeeDetail()------"+eid);
  	  	   
  	       HashMap empMap=new HashMap();
	        
  	    try
  		{
  			ArrayList employeesList = new ArrayList();
  			ResultSet resultSet =null;
  				System.out.println("=====Employee id in the dao class============"+eid);
  				Connection con = DBConnection.createConnection();
  				String strQuery="EXEC vims_user.USP_Get_Employee_Dtls @Designation_NBR=null,@Emp_NAME=null,@Status=null,@User_NM='"+eid+"'";
  				
  				System.out.println("=======Query========="+strQuery);
  				PreparedStatement preparedStatement = con.prepareStatement(strQuery);
  				//preparedStatement = con.prepareCall("{CALL USP_Get_Employee_Dtls(?,?)}");
  				/*preparedStatement.setString(1, null);
  				preparedStatement.setString(2, eid);*/
  				//System.out.println("Query is "+strQuery);
               
  				resultSet = preparedStatement.executeQuery();
                   while(resultSet.next())
               {
                	
	               	empMap = new HashMap();
	               	empMap.put("strEmployeeID", resultSet.getString(1));
	               	empMap.put("strFirstName", resultSet.getString(8));
	               	empMap.put("strLastName", resultSet.getString(9));
	            	empMap.put("strMiddleName", resultSet.getString(10));
	               	empMap.put("strPositionNo", resultSet.getString(11));
	               	empMap.put("strEmployeePosition", resultSet.getString(12));
	               	//System.out.println("=========strEmployeePosition========="+resultSet.getString(10));
	       			empMap.put("strAddress1", resultSet.getString(13));
	       			empMap.put("strAddress2", resultSet.getString(14));
	       			empMap.put("strCity", resultSet.getString(15));
	       			empMap.put("strState", resultSet.getString(16));
	       			empMap.put("strCountry", resultSet.getString(17));
	       			empMap.put("strEmail", resultSet.getString(5));
	       			empMap.put("strWorkPhone", resultSet.getString(4));
	       			empMap.put("strSupervisorID", resultSet.getString(18));
	       			empMap.put("strSupervisorName", resultSet.getString(19));
	       			//System.out.println("=========strSupervisorName========="+resultSet.getString(18));
	       			empMap.put("strMobile", resultSet.getString(20));
	       			empMap.put("strQualification", resultSet.getString(21));
	       			empMap.put("strWorkLocation", resultSet.getString(22));
	       			empMap.put("strZip", resultSet.getString(25));
	       			//System.out.println("=========strZip========="+resultSet.getString(24));
	       			empMap.put("strStatus", resultSet.getString(24));
	       			empMap.put("strWorkLocationNo", resultSet.getString(26));
	       			//System.out.println("------WorkLocationNo-------"+ resultSet.getString(26));
	       			empMap.put("strStateNo", resultSet.getString(27));
	       			empMap.put("strCountryNo", resultSet.getString(28));
      				
               }
  
  		} 
  		catch (Exception sql)
  		{
  			//for logging information messages 
  			logger.info("ClassName.MethodName");
  			System.out.println("=============== int eh catch block ot the DAO class");
  			//for logging error messages
  			logger.error("The Exception raised is :" + sql);
  		} 
  		finally
  		{
  			 
  		}
  		//System.out.println("---strEmployeePosition--1111111----"+empMap.get("strEmployeePosition"));
  		//System.out.println("------strCountry--222222-----"+empMap.get("strCountry"));
  		
  		
		return empMap;
  	}
     public static HashMap viewEmployeeDetailDAO(String eid)
     {
         //System.out.println("---------In viewEmployeeDetailDAO-------"+eid);
    	 Logger logger = Logger.getLogger("Employee");
         Connection con = DBConnection.createConnection();
         ResultSet resultSet = null;
         HashMap employeesview=null;
         
         String strEmpID=null;
         String strFirstName=null;
         String strLastName=null;
         String strEmpLevel=null;
         String strEmpPostionNo=null;
         String strAddress1=null;
         String strAddress2=null;
         String strCity=null;
         String strState=null;
         String strCountry=null;
         String strSupervisorFullName=null;
         String strMobileNo=null;
         String strQualification=null;
         String strLocation=null;
         String strExperience=null;
         String strEmpStatus=null;
         String strZip=null;
         String strFullName=null;
         String strStartDate=null;
         String strWorkPhone=null;
         String strEmailAddress=null;
         String strUserName=null;
         String strSupervisorID=null;
         String strMiddleName=null;
         String strWorkLocationNo=null;
         String strStateNo=null;
         String strCountryNo=null;
         
         //System.out.println((new StringBuilder("--------strSelectedIssueType in DAO------------")).append(strSelectedIssueType).toString());
             try
             {
                 con = DBConnection.createConnection();
                 callablestmt = con.prepareCall("{CALL vims_user.USP_Get_Employee_Dtls(?,?,?,?)}");
                 callablestmt.setString(1, null);
                 callablestmt.setString(2, null);
                 callablestmt.setString(3, null);
                 callablestmt.setString(4, eid);
                 
                 resultSet = callablestmt.executeQuery();
                 //employeesview = listOfEmployees(resultSet);
                 
                 while(callablestmt.getResultSet().next()){
     				//System.out.println("---------IN viewEmployeeDetailDAO------------");
     				
     				strEmpID=callablestmt.getResultSet().getString(1);
     				strFullName=callablestmt.getResultSet().getString(2);
     				strStartDate=callablestmt.getResultSet().getString(3);
     				strWorkPhone=callablestmt.getResultSet().getString(4);
     				strEmailAddress=callablestmt.getResultSet().getString(5);
     				strEmpStatus=callablestmt.getResultSet().getString(6);
     				strUserName=callablestmt.getResultSet().getString(7);
     				strFirstName=callablestmt.getResultSet().getString(8);
     				strLastName=callablestmt.getResultSet().getString(9);
     				strMiddleName=callablestmt.getResultSet().getString(10);
     				strEmpPostionNo=callablestmt.getResultSet().getString(11);
     				strEmpLevel=callablestmt.getResultSet().getString(12);
     				strAddress1=callablestmt.getResultSet().getString(13);
     				strAddress2=callablestmt.getResultSet().getString(14);
     				strCity=callablestmt.getResultSet().getString(15);
     				strState=callablestmt.getResultSet().getString(16);
     				strCountry=callablestmt.getResultSet().getString(17);
     				//strCountry=callablestmt.getResultSet().getString(18);
     				strSupervisorID=callablestmt.getResultSet().getString(18);
     				strSupervisorFullName=callablestmt.getResultSet().getString(19);
     				strMobileNo=callablestmt.getResultSet().getString(20);
     				strQualification=callablestmt.getResultSet().getString(21);
     				strLocation=callablestmt.getResultSet().getString(22);
     				strExperience=callablestmt.getResultSet().getString(23);
     				strEmpStatus=callablestmt.getResultSet().getString(24);
     				strZip=callablestmt.getResultSet().getString(25);
     				strWorkLocationNo=callablestmt.getResultSet().getString(26);
     				strStateNo=callablestmt.getResultSet().getString(27);
     				strCountryNo=callablestmt.getResultSet().getString(28);
     				
     				employeesview=new HashMap();
     				
     				employeesview.put("strEmployeeID",strEmpID);
     				employeesview.put("strWorkPhone",strWorkPhone);
     				employeesview.put("strEmail",strEmailAddress);
     				employeesview.put("strStatus",strEmpStatus);
     				employeesview.put("strFirstName",strFirstName);
     				employeesview.put("strLastName",strLastName);
     				employeesview.put("strMiddleName",strMiddleName);
     				employeesview.put("strAddress1",strAddress1);
     				employeesview.put("strAddress2",strAddress2);
     				employeesview.put("strEmployeePosition",strEmpLevel);
     				employeesview.put("strCity",strCity);
     				employeesview.put("strState",strState);
     				employeesview.put("strCountry",strCountry);
     				employeesview.put("strSupervisorName",strSupervisorFullName);
     				employeesview.put("strMobile",strMobileNo);
     				employeesview.put("strWorkLocation",strLocation);
     				employeesview.put("strZip",strZip);
     				employeesview.put("strUserNM",strUserName);
     				employeesview.put("strWorkLocationNM",strWorkLocationNo);
     				//employeesview.put("strState",strStateNo);
     				//employeesview.put("strCountry",strCountryNo);
     				
     			}
                 
             }
             catch(Exception exception)
             {
                 logger.info("VIMSClientDAO.getClientDetailsDAO");
                 logger.error(exception);
                 System.out.println((new StringBuilder("The Exception is:")).append(exception).toString());
             }
			return employeesview;
         
     }
    
   	
     /**********Method Name:-------->editEmployeeDetail****************/
     
     /**********This method is used to modify an employee in the list*********/    
   	
     public static int modifyEmployee(NewEmployeeFormBean bean,String strUserID,String strEID) {

   		Logger logger = Logger.getLogger("Employee");
   		try {

   			Connection con = DBConnection.createConnection();
   			/*PreparedStatement preparedStatement = con.prepareStatement(VIMSQueryInterface.editEmployeeDetail);
   			
   			String supervisorName=null;
   			supervisorName= bean.getStrSupervisorName();
   			
   			if(supervisorName!=null)
   	         {     
   				supervisorName= bean.getStrSupervisorName();
   		     }
   		
   			preparedStatement.setString(1, bean.getStrFirstName());
   			preparedStatement.setString(2, bean.getStrLastName());
   			preparedStatement.setString(3, bean.getStrEmployeePosition());
   			System.out.println("-----------Position---------"+ bean.getStrEmployeePosition());
   			preparedStatement.setString(4, bean.getStrAddress1());
   			preparedStatement.setString(5, bean.getStrAddress2());
   			//System.out.println("-----------Address---------"+ bean.getStrAddress1());
   			preparedStatement.setString(6, bean.getStrCity());
   			preparedStatement.setString(7, bean.getStrState());
   			preparedStatement.setString(8, bean.getStrCountry());
   			preparedStatement.setString(9, bean.getStrEmail());
   			preparedStatement.setString(10, bean.getStrWorkPhone());
   			preparedStatement.setString(11, supervisorName);
   			preparedStatement.setString(12, bean.getStrMobile());
   			preparedStatement.setString(13, bean.getStrQualification());
   			preparedStatement.setString(14, bean.getStrWorkLocation());
   			preparedStatement.setString(15, bean.getStrZip());
   			preparedStatement.setString(16, bean.getStrStatus());
   			//System.out.println("---------Status in modifyEmployee-------"+ bean.getStrStatus());
   			preparedStatement.setString(17, bean.getStrEmployeeID());

   			int count = preparedStatement.executeUpdate();
        		return count;
   		} 
   		catch (Exception sql)
   		{
   			System.out.println("Exception in DAO method modifyEmployee " + sql);
   			//for logging information messages 
   			logger.info("ClassName.MethodName");
   			System.out.println("=============== int eh catch block ot the DAO class");
   			//for logging error messages
   			logger.error("The Exception raised is :" + sql);

   			return 0;
   		} 
   		finally 
   		{
   			try
   			{
   				DBConnection.closeConnection();
   			}
   			catch (Exception e) 
   			{
   				e.printStackTrace();
   			}
   		}*/
   			
            String supervisorName=null;
   		    supervisorName= bean.getStrSupervisorName();
   		   
			if(supervisorName!=null)
	         {     
				supervisorName= bean.getStrSupervisorName();
		     }
   			    String emailID= bean.getStrEmail();
   			    //System.out.println("-------emailID in modify---------"+emailID);
   			    String strTemp=null;
        	    String strloginID=null;
        	    strTemp=emailID;
       	        strloginID=strTemp.substring(0,strTemp.indexOf('@'));
       	     //System.out.println("-------strloginID in modify---------"+strloginID);
       	        String strFirstName=null;
        	    String strlastName=null;
        	    String position=null;
        		String strAdd1=null;
        		String strAdd2=null;
        		String strCity=null;
          	    String strState=null;
          	    String strCountry=null;
          	    String workPhone=null;
                String mobileNo=null;
                String workLocation=null;
     		    String strZipNo=null;
     		    String strStatus=null;
     		    String empID=null;
     		    String strType="Update";
     		   
     		  
   			   CallableStatement callablestmt=null;
   			
   			   strFirstName=bean.getStrFirstName();
   			   strlastName= bean.getStrLastName();
   			   strAdd1=bean.getStrAddress1();
   			   strAdd2=bean.getStrAddress2();
   			   strCity=bean.getStrCity();
   			   //System.out.println("-------strCity in modify-----"+strCity);
     		   strState=bean.getStrState();
     		   System.out.println("=======State in the DAO========"+strState);
     		   
     		   if(strState==null || "".equals(strState) || " ".equals(strState)){
		    	  strState=null; 
		       } 
     		   
   		       strCountry=bean.getStrCountry();
   		       System.out.println("=======strCountry in the DAO========"+strCountry);
   		       if(strCountry==null || "".equals(strCountry)|| " ".equals(strCountry)){
		    	   strCountry=null;   
		       } 
   		    
   		       workPhone= bean.getStrWorkPhone();
   		       position= bean.getStrEmployeePosition();
   		       strStatus=bean.getStrStatus();
   		       empID=bean.getStrEmployeeID();
   		       mobileNo=bean.getStrMobile();
   		       workLocation= bean.getStrWorkLocation();
    		   strZipNo= bean.getStrZip();
    		   String strMiddleName=bean.getStrMiddleName();
   			       
   		    callablestmt=con.prepareCall("{CALL vims_user.USP_Save_Employee(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");//preparing to call the stored procedure
   			callablestmt.setString(1,empID);
   		    callablestmt.setString(2,strFirstName);
   			callablestmt.setString(3,strlastName);
   			callablestmt.setString(4,position);
   			callablestmt.setString(5,strAdd1);
   			callablestmt.setString(6,strAdd2);
   			callablestmt.setString(7,strCity);
   			callablestmt.setString(8,strState);
   			//System.out.println("-------State Number in DAO-------"+strState);
   			//callablestmt.setString(9,strCountry);
   			callablestmt.setString(9,emailID);
   			callablestmt.setString(10,workPhone);
   			callablestmt.setString(11,supervisorName);
   			callablestmt.setString(12,mobileNo);
   			callablestmt.setString(13,workLocation);
   			callablestmt.setString(14,strZipNo);
   			callablestmt.setString(15,strStatus);
   			callablestmt.setString(16,strEID);
   			//System.out.println("-----------strEID in modifyEmployeeDAO------------"+strEID);
   			callablestmt.setString(17,strloginID);
   			//System.out.println("-----------strloginID in modifyEmployeeDAO------------"+strloginID);
   			callablestmt.setString(18,strUserID);
   			//System.out.println("-----------strUserID in modifyEmployeeDAO------------"+strUserID);
   	   		callablestmt.setString(19,strType);
   	 	    //System.out.println("-----------strType in modifyEmployeeDAO------------"+strType);
   	   	    callablestmt.setString(20,strMiddleName);
   	   	    callablestmt.setString(21,strCountry);
   	   	    callablestmt.setString(20,strMiddleName);

   	   	    
   			callablestmt.executeQuery();	
   		  //calling the stored procedure
   	        return 0;
   		} 
   		catch (Exception sql)
   		{
   			logger.info("ClassName.MethodName");
   			//System.out.println("--------Exception-----------"+sql);
   			return 1;
   		}
   		finally 
   		{
   			try 
   			 {
   				DBConnection.closeConnection();
   			 }
   			catch (Exception e) 
   			 {
   				e.printStackTrace();
   			 }
   		}
   		
	

   	}
    
     /**********Method Name:-------->deleteEmployeeDetail****************/
     
     /**********This method is used to delete the employee record and set the status to inactive.*********/    

     public static int deleteEmployeeDetail(String eid)
   	{
   		int deleteStatus=0;
   		Logger logger = Logger.getLogger("Employee");

   		try 
   		{
   			Connection con = DBConnection.createConnection();
   			ArrayList employeesList = new ArrayList();
   			PreparedStatement preparedStatement = con.prepareStatement(VIMSQueryInterface.deleteEmployeeDetail);
            preparedStatement.setString(1, eid);
   			deleteStatus = preparedStatement.executeUpdate();
   			return deleteStatus;
   		}
   		catch(Exception sql)
   		{
   			//for logging information messages 
   			logger.info("ClassName.MethodName");
   			System.out.println("=============== int eh catch block ot the DAO class");
   			//for logging error messages
   			logger.error("The Exception raised is :"+sql);

   		return deleteStatus;
   		}
   		finally
   		{
   			try
   			{
   			DBConnection.closeConnection();
   			}
   			catch(Exception e)
   			{
   				e.printStackTrace();
   			}
   		}
   	}


   	/********Method------------>storeAsExcel***********/
   	
   	/********This method is used for export to excel functionality*******/
   	
   	public byte[] storeAsExcel(HashMap details,String contextPath) {
         	
    	     String strImageId;
    	     String names[];
    	     ArrayList dataset;
    	     
    	   WritableWorkbook workbook=null;
    	   WritableSheet sheet=null;
    	   WritableImage wi=null;
    	   WritableFont wf=null; 
    	   WritableCellFormat cf=null;
    	   Label name=null;
    	   Label rank=null;
        //  ChartImage chartImage=null;
    	     int ranks[];
    	try {
    		
   		  //chartImage=(ChartImage)details.get("image");

    		  strImageId=(String)details.get("imageId");
    		 
    		  dataset=(ArrayList)details.get("dataset1");
    		  
    		  names=(String[])dataset.get(0);
    		  
    		  ranks=(int[])dataset.get(1);
    		  
    		  
    		workbook =Workbook.createWorkbook(new File(contextPath+"\\excelsheets\\workbook"+strImageId+".xls"), new WorkbookSettings());
          	 
            sheet = workbook.createSheet("Details_Sheet",0);
          	 
           wf = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);
           
           cf = new WritableCellFormat(wf);
          	
          	 cf.setWrap(true);
          	 	    
          	 int rowCount=0;
          	 
          	 
          	 for(int cnt=0;cnt<names.length;cnt++) {
          	 	
          	     rowCount=0; 	 
          	 	  name=new Label(rowCount++,cnt,names[cnt]);
          	 	  rank=new Label(rowCount,cnt,ranks[cnt]+"");
          	 	 
          	 	sheet.addCell(name);
          	 	sheet.addCell(rank);
          	 }
          	 
          	 workbook.write();
          	 
          	 workbook.close();
    		
    		 return null;
    	} 
    	 catch(Exception exception) {
    	 	
    	 	System.out.println("Exception raised in storeAsExcel()"+exception);
    	 	exception.printStackTrace();
    	 	  return null;
    	 }
    }
   	/********Method------------>checkEmployeeIdDAO***********/
   	
      /********This method is used to check whether the emailID already exists using Ajax functionality*******/
   		
   	public static boolean checkEmployeeIdDAO(String strEmployeeID) 
   		{
   		 
   	 //Adding all the exception to the getLogger method for admin module  
   	   Logger logger=Logger.getLogger("Admin"); 
   	   boolean isFind=false;
   		 //Start of try block 
   		   try
   		    {
   		     Connection connection=DBConnection.createConnection();
   		     Statement statement=connection.createStatement();
   		     PreparedStatement preparedStatement=null;
   		
   		     preparedStatement = connection.prepareStatement(VIMSQueryInterface.checkEmployeeID);

   		     preparedStatement.setString(1, strEmployeeID);
                   		    
   		    //Calling an executeQuery method 
   		      ResultSet resultSet= preparedStatement.executeQuery();
     		     
   		    //start of while loop 
   		    return resultSet.next();
   		    }
   		   //start of catch block  
   			  catch(Exception exception)
   			  {
   				  System.out.println("The Exception is:"+exception);
   				  return false;
   			  }
   	  }
   	 public static boolean checkEmployeeEmailIdDAO(String strEmailID) 
   	 {
   	   //Adding all the exception to the getLogger method for admin module  
   		 Logger logger=Logger.getLogger("Admin"); 
   		 boolean isFind=false;
   	   //Start of try block 
   		   try
   		    {
   		     Connection connection=DBConnection.createConnection();
   		     Statement statement=connection.createStatement();
   		     PreparedStatement preparedStatement=null;
   		     
   		    //calling a prepared statement to call a query fpr displaying client details
   		      preparedStatement = connection.prepareStatement(VIMSQueryInterface.checkEmployeeEmailID);
   		      preparedStatement.setString(1, strEmailID);
                		    
   		    //Calling an executeQuery method 
   		      ResultSet resultSet= preparedStatement.executeQuery();
          
   		    //start of while loop 
   		        while(resultSet.next())
   			     {
    		       isFind=true;
   			     } 
               }
   		   //start of catch block  
   			  catch(Exception exception)
   			  {
   				  System.out.println("The Exception is:"+exception);
   			  }
   			
   		//end of catch block
   		return isFind;
   	  }
  
    static HashMap employeeHashMap = null;
    static CallableStatement callablestmt = null;
	
    public static ArrayList getPositionListDAO(String strUserID) 
	{
      Logger logger = Logger.getLogger("Employee");
      CallableStatement callablestmt=null;
				
   		try
   		{
   			//System.out.println("------------strUserID-------"+strUserID);
   			Connection con = DBConnection.createConnection();
   			ArrayList positionList = new ArrayList();
   			ResultSet resultSet =null;
   			String PositionNo=null;
   			String PositionNames=null;
   			
   	        HashMap hashMap;
            
   		    con = DBConnection.createConnection();
            callablestmt = con.prepareCall("{CALL vims_user.USP_Get_Designation(?,?,?)}");
            callablestmt.setInt(1, 0);
            callablestmt.setInt(2, 0);
            callablestmt.setString(3, strUserID);
	          
          resultSet = callablestmt.executeQuery();
      
          /*while(callablestmt.getResultSet().next())
          {
        	  PositionNo=callablestmt.getResultSet().getString(1);
        	  PositionNames=callablestmt.getResultSet().getString(2);
          }
          */
          while (resultSet.next())
   			{
   				hashMap = new HashMap();
   		    	hashMap.put("PositionNo",resultSet.getString(1));
   				hashMap.put("PositionNames",resultSet.getString(2));
   				positionList.add(hashMap);
   			}
          //System.out.println("----------positionList in DAO-----"+positionList);
          return positionList;
   		} 
   		catch (Exception sql) 
   		{
   			//for logging information messages 
   			logger.info("ClassName.MethodName");
   			System.out.println("=============== int eh catch block ot the DAO class");
   			//for logging error messages
   			logger.error("The Exception raised is :" + sql);

   			return null;
   		}
   		finally 
   		{
   			try
   			 {
   				DBConnection.closeConnection();
   			 } 
   			catch (Exception e) 
   			{
   				e.printStackTrace();
   			}
   		}
	}
    
    public static ArrayList getWorkLocationListDAO(String strUserID) 
	{
      Logger logger = Logger.getLogger("Employee");
      CallableStatement callablestmt=null;
				
   		try
   		{
   			//System.out.println("------------strUserID-------"+strUserID);
   			Connection con = DBConnection.createConnection();
   			ArrayList locationList = new ArrayList();
   			ResultSet resultSet =null;
   			String PositionNo=null;
   			String PositionNames=null;
   			
   			//Statement statement = con.createStatement();
   			//ResultSet resultSet = statement.executeQuery(VIMSQueryInterface.getPositionList);
   		      HashMap hashMap;
            
	   		  con = DBConnection.createConnection();
	          callablestmt = con.prepareCall("{CALL vims_user.USP_Get_Location(?,?,?)}");
	          callablestmt.setInt(1, 0);
	          callablestmt.setInt(2, 0);
	          callablestmt.setString(3, strUserID);
	          
          resultSet = callablestmt.executeQuery();
      
          while (resultSet.next())
   			{
   				hashMap = new HashMap();
   		    	hashMap.put("WorkLocationNo",resultSet.getString(1));
   				hashMap.put("WorkLocationNames",resultSet.getString(2));
   				locationList.add(hashMap);
   			}
          return locationList;
   						
   		} 
   		catch (Exception sql) 
   		{
   			//for logging information messages 
   			logger.info("ClassName.MethodName");
   			System.out.println("=============== int eh catch block ot the DAO class");
   			//for logging error messages
   			logger.error("The Exception raised is :" + sql);

   			return null;
   		}
   		finally 
   		{
   			try
   			 {
   				DBConnection.closeConnection();
   			 } 
   			catch (Exception e) 
   			{
   				e.printStackTrace();
   			}
   		}
		
		
	}
	
    public static ArrayList getCountryListDAO() 
	{
      Logger logger = Logger.getLogger("Employee");
      CallableStatement callablestmt=null;
				
   		try
   		{
   			//System.out.println("------------strUserID-------"+strUserID);
   			Connection con = DBConnection.createConnection();
   			ArrayList countryList = new ArrayList();
   			ResultSet resultSet =null;
   			HashMap hashMap;
   		 	  con = DBConnection.createConnection();
	          callablestmt = con.prepareCall("{CALL vims_user.USP_Get_Country(?,?,?)}");
	          callablestmt.setInt(1, 0);
	          callablestmt.setString(2, null);
	          callablestmt.setInt(3,0);
	          
          resultSet = callablestmt.executeQuery();
      
          while (resultSet.next())
   			{
   				hashMap = new HashMap();
   		    	hashMap.put("countryNo",resultSet.getString(1));
   				hashMap.put("countryNames",resultSet.getString(2));
   				countryList.add(hashMap);
   			}
          return countryList;
   						
   		} 
   		catch (Exception sql) 
   		{
   			//for logging information messages 
   			logger.info("ClassName.MethodName");
   			System.out.println("=============== int eh catch block ot the DAO class");
   			//for logging error messages
   			logger.error("The Exception raised is :" + sql);

   			return null;
   		}
   		finally 
   		{
   			try
   			 {
   				DBConnection.closeConnection();
   			 } 
   			catch (Exception e) 
   			{
   				e.printStackTrace();
   			}
   		}
		
		
	}
    

    public static ArrayList getStateListDAO(String strSelCountry) 
	{
      Logger logger = Logger.getLogger("Employee");
      System.out.println("--------Country No in DAO--------"+strSelCountry);
      System.out.println("==========aditya=================");
      CallableStatement callablestmt=null;
				
   		try
   		{
   			Connection con = DBConnection.createConnection();
   			System.out.println("==========con================="+con);
   			ArrayList stateList = new ArrayList();
   			ResultSet resultSet =null;
   			HashMap hashMap; 
   		 	  con = DBConnection.createConnection();
	          callablestmt = con.prepareCall("{CALL vims_user.USP_Get_State(?,?,?,?)}");
	          callablestmt.setString(1,strSelCountry);
	          callablestmt.setInt(2, 0);
	          callablestmt.setString(3,null);
	          callablestmt.setString(4,"0");
	          
          resultSet = callablestmt.executeQuery();
       System.out.println("-----------1---------------------"+resultSet);
          while (resultSet.next())
   			{
        	  System.out.println("-----------2---------------------"); 
   				hashMap = new HashMap();
   				System.out.println("-----------3---------------------");
   		    	hashMap.put("stateNo",resultSet.getString(3));
   		    	System.out.println("-----------4---------------------");
   		    	//System.out.println("==========State No=========="+resultSet.getString(3));
   				hashMap.put("stateNames",resultSet.getString(4));
   				System.out.println("-----------5---------------------");
   				//System.out.println("==========State Name=========="+resultSet.getString(4));
   				stateList.add(hashMap);
   				
        	  /*stateList.add(0,resultSet.getString(1));
        	  stateList.add(1,resultSet.getString(2));*/
        
   			}
          //System.out.println("-------StatesList in DAO--------"+stateList);
          System.out.println("-----------6---------------------"+stateList);
          return stateList;
   						
   		} 
   		catch (Exception sql) 
   		{
   			//for logging information messages 
   			logger.info("ClassName.MethodName");
   			System.out.println("=============== int eh catch block ot the DAO class");
   			//for logging error messages
   			logger.error("The Exception raised is :" + sql);

   			return null;
   		}
   		finally 
   		{
   			try
   			 {
   				DBConnection.closeConnection();
   			 } 
   			catch (Exception e) 
   			{
   				e.printStackTrace();
   			}
   		}
		
		
	}
    
    public static ArrayList searchEmployeeDAO(String strEmpSearch, String strStatus)
	{
    	Logger logger = null;
    	ArrayList employeesList = new ArrayList();
		logger=Logger.getLogger("Admin");
		ResultSet resultSet=null;
		HashMap hashMap=null;
		Statement statement=null;
		try
		{			
			Connection connection=DBConnection.createConnection();
			 callablestmt=connection.prepareCall("{CALL vims_user.USP_Get_Employee_Dtls(?,?,?,?)}");
			 
			 callablestmt.setString(1,null);
			 callablestmt.setString(2,strEmpSearch);
			 callablestmt.setString(3,strStatus);
			 callablestmt.setString(4,null);
			
		      resultSet = callablestmt.executeQuery();
              
		      employeesList = listOfEmployees(resultSet);
    	
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
		return employeesList;
	}
    
    public static ArrayList getCityListDAO(String strUserID) 
	{
      Logger logger = Logger.getLogger("Employee");
      CallableStatement callablestmt=null;
				
   		try
   		{
   			Connection con = DBConnection.createConnection();
   			ArrayList cityList = new ArrayList();
   			ResultSet resultSet =null;
   			String cityNo=null;
   			String cityNames=null;
   			
   		      HashMap hashMap;
            
	   		  con = DBConnection.createConnection();
	   		  callablestmt=con.prepareCall("{CALL vims_user.USP_Get_City(?,?,?)}");
		      callablestmt.setString(1,null);
	          callablestmt.setInt(2,0);
	          callablestmt.setString(3,strUserID);
	          
            resultSet = callablestmt.executeQuery();
      
          while (resultSet.next())
   			{
   				hashMap = new HashMap();
   		    	hashMap.put("cityNo",resultSet.getString(1));
   				hashMap.put("cityNames",resultSet.getString(2));
   				cityList.add(hashMap);
   			}
          //System.out.println("----------cityList in DAO-----"+cityList);
          return cityList;
   						
   		} 
   		catch (Exception sql) 
   		{
   			//for logging information messages 
   			logger.info("ClassName.MethodName");
   			System.out.println("=============== int eh catch block ot the DAO class");
   			//for logging error messages
   			logger.error("The Exception raised is :" + sql);

   			return null;
   		}
   		finally 
   		{
   			try
   			 {
   				DBConnection.closeConnection();
   			 } 
   			catch (Exception e) 
   			{
   				e.printStackTrace();
   			}
   		}
	}
	
    public static void main(String args[])
    {
    	VIMSEmployeeDAO obj=new VIMSEmployeeDAO();
    	System.out.println(obj.editEmployeeDetail("B315")); 
    }
}