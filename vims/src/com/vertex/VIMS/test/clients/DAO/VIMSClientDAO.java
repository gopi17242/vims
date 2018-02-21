/*
  FileName	    : VIMSClientDAO.java
  
 
  Description	: This Data Access Object class is used to call different queries 
  
                  from query interface class and access those Queries where needed. 
    
  Developed by  : Vertex Computer Systems.
                  copyright (c) 2008 Vertex.
                  All rights reserved.
  
  Change History:
 
  Revision No.	:		Date		  @author		Description
    1.0				Friday 15,2008	  Geeta.M	   Initial Version.*/


//Package structure followed for this class.
  package com.vertex.VIMS.test.clients.DAO;

//import statements
  import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.vertex.VIMS.test.VIMSSQLQueryInterface.VIMSQueryInterface;
import com.vertex.VIMS.test.clients.form.CustomerBean;
import com.vertex.VIMS.test.clients.form.VIMSCustomerForm;
import com.vertex.VIMS.test.common.DBConnection;


//Start of VIMSClientDAO class
  public class VIMSClientDAO 
  {
      //Currently initializing the objects to null values and as static variables as all the methods are static  
	    
	    static Logger logger=null;
	    static Connection connection=null;
	    static Statement statement=null;
	    static HashMap hashMap=null;
	    static ArrayList arrayList=null;
	    static CallableStatement callablestmt=null;
		
   
	    public static ArrayList listOfCustomers(ResultSet resultset)
	    {	//function listOfIssues start
	    	
	    	 try
	    	  {  //try start
	    		 
	    		
	    		 while(resultset.next())
				  {
			    	 String linkView="<a href='./dispatchClient.do?client=Link&custID="+(String)resultset.getString(1)+"'>"+resultset.getString(2)+"</a>";
					  hashMap=new HashMap();
					  hashMap.put("customerID",linkView); 
					  hashMap.put("customerName",linkView);
					 if(resultset.getString(3)!=null)
					  hashMap.put("applicationName",(resultset.getString(3)).replace("<br>",""));
					 else
					  hashMap.put("applicationName",resultset.getString(3));
					  hashMap.put("emailID",resultset.getString(4));
					  hashMap.put("phoneNumber",resultset.getString(5));
					  hashMap.put("status",resultset.getString(6));
					  hashMap.put("country",resultset.getString(12));
				
					String strStatus=(String)resultset.getString(6);
					//System.out.println("======strStatus==============="+strStatus);
					String linkmodify=null;
				
				if(strStatus.equalsIgnoreCase("Active"))
				{
				  linkmodify="<a href='./dispatchClient.do?client=Modify&country="+(String)resultset.getString(12)+"&status="+(String)resultset.getString(6)+"&custID="+(String)resultset.getString(1)+"&menuId=Home&pageId=ModifyCustomer'>Modify</a>"; 
				}
				else
				{
				  linkmodify="<a href='./dispatchClient.do?client=Modify&country="+(String)resultset.getString(12)+"&status="+(String)resultset.getString(6)+"&custID="+(String)resultset.getString(1)+"&menuId=Home&pageId=ModifyCustomer'>Modify</a> |<a href=\"#\" onclick=\"fnDelete('"+(String)resultset.getString(1)+"')\"/>Delete</a>"; 
				}	 
				    hashMap.put( "ModifyDelete", linkmodify);
				   //Adding hashMap values to arrayList	 
					 arrayList.add(hashMap);
				  }
			    //end of while loop
	    	  }
	    	  catch(Exception e)   
	    	  {	//catch start
	    		  logger.error(e);
	    	  }	//catch end
			   //Returns arrayList     
			return arrayList;
	    	}//function listOfIssues end
	     
	    
	    
  /**********Method Name:-------->getClientDetailsDAO ****************/
      
  /**********In this method the values are retrieved from database to be displayed on the clients base page *********/    
      
      /**
     * @return
     */
    public static ArrayList getClientDetailsDAO() 
		{
		 
	     //Adding all the exception to the getLogger method for admin module	  
    	   Logger logger=Logger.getLogger("Admin"); 
    	   //String strSelectedIssueType=null;
    	   //strSelectedIssueType=customerForm.getTypeofIssue();
    	   //Creating a new arrayList 
		    arrayList =new ArrayList();
		    ResultSet resultSet=null;
		    Connection connection=null;
    	   //System.out.println("===========strSelectedIssueType==========="+strSelectedIssueType);
   	  	  
		 //Start of try block 
    	 
    	   try
		    {
    		 //Calling a query in QueryInterface for executing
    		 //ResultSet resultSet=statement.executeQuery(VIMSQueryInterface.clientListSql);
             //System.out.println("-----------In  strSelectedIssueType as Active----------");
			 connection = DBConnection.createConnection();
			 callablestmt=connection.prepareCall("{call vims_user.USP_Get_Customer_Dtls(?,?,?)}");
			 
			 callablestmt.setString(1,null);
			 callablestmt.setString(2,null);
			 callablestmt.setString(3,null);
			 
			 resultSet=callablestmt.executeQuery();
			
			 arrayList=listOfCustomers(resultSet);
			//start of while loop 
		    
		    }
    	    //start of catch block	  
  		    catch(Exception exception)
  		     {
	  			  logger.info("VIMSClientDAO.getClientDetailsDAO");
	  	    	  logger.error(exception);
	  			  System.out.println("The Exception is:"+exception);
  		     }
		
    	  //System.out.println("-----------arrayList----------");
    	 return arrayList;
		}  
		
	 
 
   //End of getClientDetailsDAO method   
	
   /**********Method Name:-------->addClientDetailsDAO ****************/     
      
   /**********This method is used to insert details in to database********/
 
     
      public static String[] addClientDetailsDAO(VIMSCustomerForm customerForm, String strRandomGnrtPassword) 
		{
		 
		//System.out.println("-------------In add Customer---------");
    	  //Adding all the exception to the getLogger method for admin module  
    	  Logger logger=Logger.getLogger("Admin"); 
    	  String strResult = null;
    	  String strID=null;
    	  String strlastName=null;
    	  String strAdd1=null;
    	  String strAdd2=null;
    	  String strCity=null;
    	  String strState=null;
    	  String strCountry=null;
    	  String strEmailID=null;
		  String strFaxNo=null;
		  String strZipNo=null;
		  String strStatus=null;
		  String strExtensionNo=null;
    	  String strFirstName=null;
    	  String strValues[]=new String[4];
    	  String strEmail=null;
    	  String strContactFirstName=null;
    	  String strContactLastName=null;
    	  String strContactMiddleName=null;
    	  String strContactEmailID=null;
    	  
    	//Start of try block 
		  try
		  
		  {
		    Connection connection=DBConnection.createConnection();
		    //Statement statement=connection.createStatement();
		    ResultSet resultSet=null;
		    PreparedStatement preparedStatement=null;
		   
		    CallableStatement callablestmt=null;
		   
		   String strCustName=customerForm.getCustomerName();
		   //System.out.println("----------strCustName-------"+customerForm.getCustomerName());
	      
		   strFirstName=customerForm.getFirstName();
	       if(strFirstName==null || "".equals(strFirstName)){
	    	   strFirstName="";   
	       } 
		   
	       strlastName=customerForm.getLastName();
	       if(strlastName==null || "".equals(strlastName)){
	    	   strlastName="";   
	       } 
	       
	       strAdd1=customerForm.getAddress1();
	       /*if(strAdd1==null || "".equals(strAdd1)){
	    	   strAdd1="";   
	       }*/
	       
	       strAdd2=customerForm.getAddress2();
	       if(strAdd2==null || "".equals(strAdd2)){
	    	   strAdd2="";   
	       }
	       
	       strCity=customerForm.getCity();
	       if(strCity==null || "".equals(strCity)){
	    	   strCity="";   
	       }
	       
	       strState=customerForm.getState();
	       //System.out.println("------State in DAO------"+strState);
	       if(strState==null || "".equals(strState) || " ".equals(strState)){
	    	   strState=null; 
		       } 
	  
	       strCountry=customerForm.getCountry();
	       //System.out.println("------Country in DAO------"+strCountry);
	       if(strCountry==null || "".equals(strCountry) || " ".equals(strCountry)){
	    	   strCountry=null;   
	       }
	       
	       
           
	       String strMobileNo=customerForm.getMobileNO();
           
           String strPhNo=customerForm.getPhoneNumber();
           strEmailID=customerForm.getEmailID();
           //System.out.println("----------strEmailID-------"+customerForm.getEmailID());
  		 
           
           strFaxNo=customerForm.getFaxNo();
           if(strFaxNo==null || "".equals(strFaxNo)){
        	   strFaxNo="";   
	       }
	       
           strZipNo=customerForm.getZipCode();
           if(strZipNo==null || "".equals(strZipNo)){
        	   strZipNo="";   
	       }
	       
	       strStatus=customerForm.getStatus();
	       String strCompanyName=customerForm.getCompanyName();
	       
	       strExtensionNo=customerForm.getExtension();
	       if(strExtensionNo==null || "".equals(strExtensionNo)){
	    	   strExtensionNo="";   
	       }
	       
	       strContactFirstName=customerForm.getContactFirstName();
	       if(strContactFirstName==null || "".equals(strContactFirstName)){
	    	   strContactFirstName="";   
	       } 
	       
	       strContactLastName=customerForm.getContactLastName();
	       if(strContactLastName==null || "".equals(strContactLastName)){
	    	   strContactLastName="";   
	       }
	       
	       strContactMiddleName=customerForm.getContactMiddleName();
	       if(strContactMiddleName==null || "".equals(strContactMiddleName)){
	    	   strContactMiddleName="";   
	       }
	       
	       strContactEmailID=customerForm.getContactEmailID();
	       if(strContactEmailID==null || "".equals(strContactEmailID)){
	    	   strContactEmailID="";   
	       }
	       
		   callablestmt=connection.prepareCall("{CALL vims_user.CUSTOMER_INSERT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");	//preparing to call the stored procedure
		 
		        callablestmt.setString(1,strRandomGnrtPassword);
			    callablestmt.setString(2,strCustName);
				callablestmt.setString(3,strAdd1);
				callablestmt.setString(4,strAdd2);
				callablestmt.setString(5,strCity);
				callablestmt.setString(6,strState);
				callablestmt.setString(7,strCountry);
				
				callablestmt.setString(8,strPhNo);
				callablestmt.setString(9,strEmailID);
				callablestmt.setString(10,strFaxNo);
				callablestmt.setString(11,strZipNo);
				callablestmt.setString(12,strStatus);
				//System.out.println("---------strStatus------------"+strStatus);
				callablestmt.setString(13,null);//Company Name
				callablestmt.setString(14,null);//Extension Name
				callablestmt.setString(15,strContactFirstName);
				callablestmt.setString(16,strContactLastName);
				callablestmt.setString(17,strContactMiddleName);
				callablestmt.setString(18,strContactEmailID);
				callablestmt.setString(19,strMobileNo);//Mobile No
				
				
				callablestmt.executeQuery();	//calling the stored procedure
			//System.out.println("-----------callablestmt---------"+callablestmt.getResultSet().next());
			
		    String strTemp=null;
			String strCustID=null;
			String strloginID=null;
			
			while(callablestmt.getResultSet().next()){
				//System.out.println("---------IN WHILE------------");
				strTemp=callablestmt.getResultSet().getString(1);
			}
			if(strTemp!=null)
			{
				strCustID=strTemp.substring(0,strTemp.indexOf('$'));
				String str2=strTemp.substring(strTemp.indexOf('$')+1,strTemp.length()); 
				strEmail=str2.substring(0,str2.indexOf('$'));
				strCustName=str2.substring(str2.indexOf('$')+1,str2.length());
				strloginID=strEmail.substring(0,strEmail.indexOf('@'));
				
			 }
			strValues[0]=strCustID;
			strValues[1]=strEmail;
			strValues[2]=strCustName;
			strValues[3]=strloginID;
			
		 }
		//End of try block 
		 
		//start of catch block	   
		  catch(Exception exception)
		  {
			  logger.info("VIMSClientDAO.addClientDetailsDAO");
	    	  logger.error(exception);
			  System.out.println("The Exception is:"+exception);
		  }
		 //end of catch block   
		
		//start of finally block 
		  finally
			{
		    //start of try block	
			  try
				 {
					DBConnection.closeConnection();
				 } 
				//end of try block 
				
			  //start of catch block	
				catch (Exception exception)
				{
				    System.out.println("==========exception Occured while closing the connection==============="+exception);
					exception.printStackTrace();
				}
				//end of catch block
			}
		  return strValues;
		
		//end of finally block
		
	  }
   //End of addClientDetailsDAO method    
      
   /**********Method Name:-------->displayClientDetailsDAO ****************/    
	 
   /**********This method is used to retrieve details from database and display in modify page before modifying the details***********/ 
      
      public static HashMap displayClientDetailsDAO(String strCustID) 
		{
		 
    	 //Adding all the exception to the getLogger method for admin module  
    	   Logger logger=Logger.getLogger("Admin"); 
		 
		 //Start of try block 
		   try
		    {
		     Connection connection=DBConnection.createConnection();
		     Statement statement=connection.createStatement();
		     PreparedStatement preparedStatement=null;
		     
		    //calling a prepared statement to call a query fpr displaying client details
		     
		     preparedStatement = connection.prepareStatement(VIMSQueryInterface.displayClientDetail);

		     preparedStatement.setString(1, strCustID);
		    
		    //Calling an executeQuery method 
		     ResultSet resultSet = preparedStatement.executeQuery();

		    //start of while loop 
		      while(resultSet.next())
			  {
			   //Creating a new ArrayList	 
		    	 //arrayList=new ArrayList();
		    
			   //Creating a	new HashMap 
				 hashMap=new HashMap();
				 
		    	 hashMap.put("customerID",resultSet.getString(1)); 
				 hashMap.put("customerName",resultSet.getString(2));
				 hashMap.put("address1",resultSet.getString(3));
				 hashMap.put("address2",resultSet.getString(4));
				 hashMap.put("city",resultSet.getString(5));
				 hashMap.put("state",resultSet.getString(6));
				 hashMap.put("country",resultSet.getString(7));
				 hashMap.put("mobileNO",resultSet.getString(8));
				 hashMap.put("phoneNumber",resultSet.getString(9)); 
				 hashMap.put("emailID",resultSet.getString(10)); 
				 hashMap.put("faxNo",resultSet.getString(11)); 
				 hashMap.put("status",resultSet.getString(12)); 
				 hashMap.put("companyName",resultSet.getString(13)); 
				 hashMap.put("extensionNo",resultSet.getString(14)); 
				 hashMap.put("zipCode",resultSet.getString(15));
				 hashMap.put("contactFirstName",resultSet.getString(16));
				 hashMap.put("contactLastName",resultSet.getString(17));
				 hashMap.put("contactMiddleName",resultSet.getString(18));
				 hashMap.put("contactEmailID",resultSet.getString(19));
				 hashMap.put("stateNo",resultSet.getString(21));
				 //System.out.println("--------States on display DAO--------"+resultSet.getString(21));
				 hashMap.put("countryNo",resultSet.getString(20));
				 //System.out.println("--------Couintry on display DAO--------"+resultSet.getString(20));
			 
			//Adding hashMap values to the arrayList 
				
		
			  }
		   //end of while loop
		  
		  //Returns arrayList  
		    return hashMap;  
		  }
		//End of try block
		 
		//Start of catch block 
		  catch(Exception exception)
		  {
			  
			  logger.info("VIMSClientDAO.displayClientDetailsDAO");
	    	  logger.error(exception);
			  System.out.println("The Exception is:"+exception);
		  }
		//End of catch block 
		  
		//start of finally block  
		  finally
			{
		    //start of try block	
			  try
				{
					DBConnection.closeConnection();
				} 
			  //end of try block 
				
			  //start of catch block	
				catch (Exception exception)
				{
					System.out.println("==========exception Occured while closing the connection==============="+exception);
					exception.printStackTrace();
				}
				//end of catch block
			}
		//end of finally block
		
	   //Returns null value	  
		 return null;
	 }
    //End of displayClientDetailsDAO method     
      
   /**********Method Name:-------->modifyClientDetailsDAO ****************/  
      
   /**********This method is used to modify the client details and update them*********/
      
     public static int modifyClientDetailsDAO(VIMSCustomerForm customerForm,String strCustID) 
		{
		 //Adding all the exception to the getLogger method for admin module 
		   Logger logger=Logger.getLogger("Admin"); 
           //System.out.println("----------In modifyClientDetailsDAO-----------");		 
 		  //Start of try block
		    try
		     {
		        Connection connection=DBConnection.createConnection();
		   
		        PreparedStatement preparedStatement=null;
		        
		        //Calling a prepared statement to execute a query in the query interface
		        
		        preparedStatement = connection.prepareStatement(VIMSQueryInterface.modifyClientDetail);
		      
		        //System.out.println("---------customerForm.getCustomerName()-----------"+customerForm.getCustomerName());
		        //System.out.println("--------- customerForm.getFirstName()-----------"+ customerForm.getFirstName());
		        //System.out.println("--------- customerForm.getEmailID()-----------"+ customerForm.getEmailID());
		       
		        preparedStatement.setString(1, customerForm.getCustomerName());
				//preparedStatement.setString(2, customerForm.getFirstName());
				//preparedStatement.setString(3, customerForm.getLastName());
				preparedStatement.setString(2, customerForm.getAddress1());
				preparedStatement.setString(3, customerForm.getAddress2());
				preparedStatement.setString(4, customerForm.getCity());
				if((customerForm.getState()==null||("".equalsIgnoreCase(customerForm.getState()))||(" ".equalsIgnoreCase(customerForm.getState()))))
					preparedStatement.setString(5,null);
				else
					preparedStatement.setString(5, customerForm.getState());
				
				if((customerForm.getCountry()==null||("".equalsIgnoreCase(customerForm.getCountry()))||(" ".equalsIgnoreCase(customerForm.getCountry()))))
					preparedStatement.setString(6, null);
				else 
					preparedStatement.setString(6, customerForm.getCountry());
				
				preparedStatement.setString(7, customerForm.getPhoneNumber());
				preparedStatement.setString(8,customerForm.getEmailID());
				preparedStatement.setString(9,customerForm.getFaxNo());
				preparedStatement.setString(10,customerForm.getStatus());
				preparedStatement.setString(11,customerForm.getZipCode());
				
				preparedStatement.setString(12,customerForm.getContactFirstName());
			 //System.out.println("---------customerForm.getContactFirstName()-----------"+customerForm.getContactFirstName());
			 //System.out.println("--------customerForm.getContactMiddleName()-----------"+customerForm.getContactMiddleName());
			 //System.out.println("---------customerForm.getContactLastName()-----------"+customerForm.getContactLastName());
			 //System.out.println("---------customerForm.getContactEmailID()-----------"+customerForm.getContactEmailID());
						
				preparedStatement.setString(13,customerForm.getContactMiddleName());
				preparedStatement.setString(14,customerForm.getContactLastName());
				preparedStatement.setString(15,customerForm.getContactEmailID());
				preparedStatement.setString(16,customerForm.getMobileNO());
				preparedStatement.setString(17,strCustID);
				
				//System.out.println("----------customerForm.getCustomerID()---------"+customerForm.getCustomerID());
             
			//Calling an executeUpdate method
			   int intvar=preparedStatement.executeUpdate();
            
            // System.out.println("============intvar=========="+intvar);
				  
              if(intvar>0)
					{
					   //System.out.println("Records updated successfully");
					   return 1;
					}
					else
					{
					   //System.out.println("Records not updated");
						return 0;
					}

		   }
		  //End of if condition
		  
		  //start of catch block   
		   catch(Exception exception)
		   {
			  logger.info("VIMSClientDAO.modifyClientDetailsDAO");
	    	  logger.error(exception);
			  System.out.println("The Exception is:"+exception);
		   }
		  //end of catch block 
		 
		  //start of finally block  
		  finally
		    {
			//start of try block	
			  try
				{
				  DBConnection.closeConnection();
				} 
			//end of try block 
				
			//start of catch block
              catch (Exception exception)
				{
					System.out.println("==========exception Occured while closing the connection==============="+exception);
					exception.printStackTrace();
				}
				//end of catch block
			}
			//end of finally block
		  //Returns some int value    
		    return 0;
	  }
   //End of modifyClientDetailsDAO method	
  
  /**********Method Name:-------->deleteClientDetailsDAO ****************/  
	 
  /**********This method is used to change the status of client as Inactive and removes the record visibility from the base page*********/	 
     
     public static int deleteClientDetailsDAO(String strCustID) 
		{
		 
		//Adding all the exception to the getLogger method for admin module  
    	  Logger logger=Logger.getLogger("Admin"); 
		  PreparedStatement preparedStatement=null;
		 
	   //Start of try block 
		 try
		  {
		    Connection connection=DBConnection.createConnection();
		    Statement statement=connection.createStatement();
		    
		  //Calling a method from query interface to delete details 
		    preparedStatement=connection.prepareStatement(VIMSQueryInterface.deleteClientDetail);
		    
		    //This is a value which should be passed in the query to retrieve delete a record 
		   	preparedStatement.setString(1, strCustID);
		    
		   //Calling an executeUpdate method 
		     int intDel=preparedStatement.executeUpdate();
             //System.out.println("Iam in the deleteClientDAO");
		
		   //Start of if statement	  
             if(intDel>0)
				{
				   //System.out.println("Records deleted successfully");
				   return 1;
				}
				else
				{
				   //System.out.println("Records not deleted");
					return 0;
				}
            //End of if statement
           }
		  //End of try block
		 
		 //Start of catch block  
		  catch(Exception exception)
		  {
			  logger.info("VIMSClientDAO.deleteClientDetailsDAO");
	    	  logger.error(exception);
			  System.out.println("The Exception is:"+exception);
		  }
		 //end of catch block
		 
		 //start of finally block 
		   finally
			 {
			  
			 //start of try block
               try
				{
					DBConnection.closeConnection();
				} 
			 //end of try block 
			  
			 //start of catch block  
			    catch (Exception exception)
				{
					System.out.println("==========exception Occured while closing the connection==============="+exception);
					exception.printStackTrace();
				}
		     //end of catch block
			}
		  //end of finally block
		    
		 //Returns int value 
		   return 0;
	
		}
   //End of deleteClientDetailsDAO method
  
  /**********Method Name:-------->viewClientDetailsDAO ****************/  
	 
  /**********This method is used to get the details from DB and display them in the form of read only 
             when link of custID is clicked in the base page**********/  
     
     public static ArrayList viewClientDetailsDAO(String customerID) 
		{
		 
    	//Adding all the exception to the getLogger method for admin module 
    	  Logger logger=Logger.getLogger("Admin"); 
		  HashMap hashMap=null; 
		  ArrayList arrayList=null;
		 
	   //Start of try block 
		 try
		  {
			PreparedStatement preparedStatement=null;
			Connection connection=DBConnection.createConnection();
		    Statement statement=connection.createStatement();
		   //System.out.println("The customer ID in DAO is:"+customerID);
		   //System.out.println("Iam in viewClientDAO");
		    
		   //Calling a query from QueryInterface to view the application  
		     preparedStatement = connection.prepareStatement(VIMSQueryInterface.viewClientDetail);

		   //This is a value which should be passed in the query to retrieve records
		     preparedStatement.setString(1, customerID);
 
		     ResultSet resultSet = preparedStatement.executeQuery();
		   //ResultSet resultSet=statement.executeQuery("select * from CUSTOMER where CUSTOMER_ID="+customerID+"");
           
           //Creating a new arrayList 
		    arrayList=new ArrayList();
		  
		   //start of while loop  
		    while(resultSet.next())
			  {
			   //Creating a new hashMap
		    	 hashMap=new HashMap();
		    	 
				 hashMap.put("customerID",resultSet.getString(1)); 
				 hashMap.put("customerName",resultSet.getString(2));
				 hashMap.put("address1",resultSet.getString(3));
				 hashMap.put("address2",resultSet.getString(4));
			     hashMap.put("city",resultSet.getString(5));
				 hashMap.put("state",resultSet.getString(6));
				 //System.out.println("--------State in View DAO---------"+resultSet.getString(6));
				 hashMap.put("country",resultSet.getString(7));
				 //System.out.println("--------Country in View DAO---------"+resultSet.getString(7));
				 hashMap.put("mobileNO",resultSet.getString(8));
			     hashMap.put("phoneNumber",resultSet.getString(9)); 
				 hashMap.put("emailID",resultSet.getString(10)); 
				 hashMap.put("faxNo",resultSet.getString(11)); 
	             hashMap.put("status",resultSet.getString(12));
	             hashMap.put("companyName",resultSet.getString(13)); 
	             hashMap.put("extension",resultSet.getString(14)); 
	             hashMap.put("zipCode",resultSet.getString(15));
	             hashMap.put("contactFirstName",resultSet.getString(16));
	             hashMap.put("contactMiddleName",resultSet.getString(17));
	             hashMap.put("contactLastName",resultSet.getString(18));
	             hashMap.put("contactEmailID",resultSet.getString(19));
	          	 
		    //Adding hashMap values to the arrayList	 
			  arrayList.add(hashMap);
	       //end of while loop
			  }
	       //returns arrayList	  
		     return arrayList;  
		 }  
		 //End of try block
	    
		 //start of catch block  
		  catch(Exception exception)
		  {
			  logger.info("VIMSClientDAO.viewClientDetailsDAO");
	    	  logger.error(exception);
			  System.out.println("The Exception is:"+exception);
		  }
		//end of catch block
		 
		//start of finally block 
		  finally
			{
			 //start of try block	
			   try
				{
					
					DBConnection.closeConnection();
				} 
			//end of try block 
				
			 //start of catch block 
			   catch (Exception exception)
				{
					
					System.out.println("==========exception Occured while closing the connection==============="+exception);
					exception.printStackTrace();
				}
			//end of catch block
			}
			//end of finally block
		return arrayList;
		}
   //End of viewClientDetailsDAO method  
  		
	public static HashMap getNewCustomerDetailsDAO() {
		
		     Logger logger=Logger.getLogger("Admin");
		     Connection connection=null;
		     Statement statement=null;
		     ResultSet resultSet=null;
			 HashMap customerSet=null;
			 int recordCount=0;
			 int differenceInDates;
		 //Start of try block 
		   try
		    {
		     connection=DBConnection.createConnection();
		     statement=connection.createStatement();
		    
             //Calling a query in QueryInterface for executing
		    // select DISTINCT c.CUSTOMER_ID,c.create_dt,c.CUSTOMER_NAME,app.APPLICATION_NAME,c.EMAILID,c.MOBILE_NO,c.Status from CUSTOMER c INNER JOIN APP_SOLD_TO apps ON c.CUSTOMER_ID=apps.CUSTOMER_ID INNER JOIN Application app ON apps.Application_ID=app.Application_ID
		     
		     //"select DISTINCT c.CUSTOMER_ID,c.create_dt,c.CUSTOMER_NAME,app.APPLICATION_NAME,c.EMAILID,c.MOBILE_NO,c.Status from CUSTOMER c INNER JOIN APP_SOLD_TO apps ON c.CUSTOMER_ID=apps.CUSTOMER_ID INNER JOIN Application app ON apps.Application_ID=app.Application_ID";
		     String sqlQuery="select c.CUSTOMER_ID,c.view_fg,c.CUSTOMER_NAME,app.APPLICATION_ID,app.APPLICATION_NAME,c.EMAILID,c.PHONE_NUMBER,c.Status from vims_user.CUSTOMER c LEFT join vims_user.APP_SOLD_TO apps ON c.CUSTOMER_ID=apps.CUSTOMER_ID LEFT join vims_user.Application app ON apps.Application_ID=app.Application_ID where STATUS='Active'";
 		     resultSet=statement.executeQuery(sqlQuery);

            arrayList =new ArrayList();
		    customerSet=new HashMap();
		    
             while(resultSet.next())
			  {
            	  String viewFlag=resultSet.getString(2);
		    	 if(viewFlag.equals("0")) {
		    		 recordCount++;
		    	 String linkView="<a href='./dispatchClient.do?client=Link&custID="+(String)resultSet.getString(1)+"'>"+resultSet.getString(3)+"</a>";
				 hashMap=new HashMap();
				 hashMap.put("customerID",linkView);
				 //hashMap.put("addedDate",resultSet.getString(2));
				 hashMap.put("customerName",linkView);
				 hashMap.put("applicationName",resultSet.getString(5));
				 hashMap.put("emailID",resultSet.getString(6));
				 hashMap.put("mobileNO",resultSet.getString(7));
				 hashMap.put("status",resultSet.getString(8));
				 
				  //Adding hashMap values to arrayList	 
				  arrayList.add(hashMap);
		    	 
	    	   } 
			  }
             
               if(recordCount>0) {
            	   customerSet.put("newCustomerCount",recordCount);
            	   customerSet.put("newCustomerList",arrayList);
               }
		    }  
            catch(Exception exception)
		  {
			  logger.info("-===============VIMSClientDAO.getClientDetailsDAO============");
	    	  logger.error(exception);
			  
		  }
            return customerSet;
	}
	
		 public static boolean checkCustomerEmailIdDAO(String strEmailID) 
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
		      
		      preparedStatement = connection.prepareStatement(VIMSQueryInterface.checkCustomerEmailID);
		      preparedStatement.setString(1, strEmailID);
          		    
		    //Calling an executeQuery method 
		      ResultSet resultSet= preparedStatement.executeQuery();
    
		    //start of while loop 
		      return resultSet.next();
		     // while(resultSet.next())
			 // {
		    	// isFind=true;  
      	  //} 
         }
		   //start of catch block  
			  catch(Exception exception)
			  {
				  System.out.println("The Exception is:"+exception);
				  return false;
			  }
		//end of catch block
		//return isFind;
	  }
	 
	 public static void setClientFlagDAO(String strCustomerId) {
		       PreparedStatement preparedStatement=null;
		  try{
		        logger=Logger.getLogger("Admin");
		        connection=DBConnection.createConnection();
		        preparedStatement=connection.prepareStatement("update customer set view_FG=1 where customer_id=?") ;

                preparedStatement.setString(1,strCustomerId);
		        //System.out.println(preparedStatement.executeUpdate());

                preparedStatement.setString(1,strCustomerId);
		        preparedStatement.executeUpdate();
		        

		     } catch(Exception exception) {
		    	  logger.info("----Exception in setClientFlagDAO-----------");
		    	  logger.error(exception);
		    }
	  }
	 
	 public static ArrayList searchCustomerDAO(String strCustSearch)
		{
		   // ArrayList arrayList = new ArrayList();
			logger=Logger.getLogger("Admin");
			ResultSet resultset=null;
			Statement statement=null;
			//System.out.println("----------strCustSearch------"+strCustSearch);
			try
			{			
				 Connection connection=DBConnection.createConnection();
				 callablestmt=connection.prepareCall("{call vims_user.USP_Get_Customer_Dtls(?,?,?)}");
				 callablestmt.setString(1,strCustSearch);
				 callablestmt.setString(2,null);
				 callablestmt.setString(3,null);
				 
				  resultset = callablestmt.executeQuery();
				  
				  arrayList= new ArrayList();
				  
				  HashMap hashMap=null;
				  while(resultset.next())
				  {
					  //System.out.println("====cust id from dao==="+resultset.getString(1));
			    	 String linkView="<a href='./dispatchClient.do?client=Link&custID="+(String)resultset.getString(1)+"'>"+resultset.getString(2)+"</a>";
					 hashMap=new HashMap();
					 hashMap.put("customerID",linkView); 
					 hashMap.put("customerName",linkView);
					 if(resultset.getString(3)!=null)
					  hashMap.put("applicationName",(resultset.getString(3)).replace("<br>",""));
					 else
						 hashMap.put("applicationName","");
					 
					 hashMap.put("emailID",resultset.getString(4));
					 hashMap.put("phoneNumber",resultset.getString(5));
					 hashMap.put("status",resultset.getString(6));
				
				String strStatus=(String)resultset.getString(6);
				String linkmodify=null;
				
				if(strStatus.equalsIgnoreCase("Active"))
				{
				  linkmodify="<a href='./dispatchClient.do?client=Modify&status="+(String)resultset.getString(6)+"&custID="+(String)resultset.getString(1)+"'>Modify</a>"; 
				}
				else
				{
				  linkmodify="<a href='./dispatchClient.do?client=Modify&status="+(String)resultset.getString(6)+"&custID="+(String)resultset.getString(1)+"'>Modify</a> |<a href=\"#\" onclick=\"fnDelete('"+(String)resultset.getString(1)+"')\"/>Delete</a>"; 
				}	 
				    hashMap.put( "ModifyDelete", linkmodify);
				   //Adding hashMap values to arrayList	 
					 arrayList.add(hashMap);
					 
					 //System.out.println(arrayList);
				  }
	    	 
		  		}
			catch(SQLException sqlException)
			{
				logger.info("VIMSClientDAO.searchCustomerDAO()");
				logger.error(sqlException);
			}	
			catch(Exception exception)
			{
				logger.info("VIMSClientDAO.searchCustomerDAO()");
				logger.error(exception);
			}
		  //System.out.println("-------arrayList---------"+arrayList);
			return arrayList;
		}
		
 }
//End of VIMSClientDAO