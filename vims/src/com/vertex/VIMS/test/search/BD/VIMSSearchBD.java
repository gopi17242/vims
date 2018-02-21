/*  
  FileName	    : VIMSSearchBD.java
  
                  
  
  Description	: This BD class is used to call different methods 
                  in DAO(Data Access Object) and set the return values 
                    
  Developed by  : Vertex Computer Systems.
                  copyright (c) 2008 Vertex.
                  All rights reserved.
                   
  Change History:            
  
  Revision No.	:		Date		  @author		Description
    1.0				Friday 21,2008	  Geeta.M	   Initial Version.*/


//Package structure followed for this class.
package com.vertex.VIMS.test.search.BD;

//import statements
import java.io.*;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.vertex.VIMS.test.search.DAO.VIMSSearchDAO;
import com.vertex.VIMS.test.search.form.VIMSSearchForm;

//Start of VIMSSearchBD class	
  public class VIMSSearchBD
	{

	//Start of getSearchApplicationNamesBD() method and is used to get the applicationNames by calling a method from DAO. 
	  
	  public static ArrayList getSearchApplicationNamesBD(String strUserID, String strRoleType, String strCustomerID)
		{
		 //Adding all the exception to the getLogger method for admin module 
		   Logger logger=Logger.getLogger("Admin"); 
		 
		 //Start of try block
			try
			{
				ArrayList strAppNames=null;
				//System.out.println("Iam in BD");
			    strAppNames=VIMSSearchDAO.getSearchApplicationNamesDAO(strUserID,strRoleType,strCustomerID);
			    //System.out.println("---------strAppNames------------"+strAppNames);
			  
			  //Adding the hashmap to arrayList object
			    return strAppNames;
			}
		//end of try block 
		  
	   //start of catch block	
		 catch(Exception exception)
		      {
		    	   
		    	  logger.info("VIMSSearchBD.getSearchApplicationNamesBD");
		    	  logger.error(exception);
		    	  System.out.println("The Exception is:"+exception);
		    	  return null;
	          }
	  //end of catch block  
		}
	//End of getSearchApplicationNamesBD() method.  
    

    //Start of getSearchCustomerNamesBD() method and is used to get the customerNames by calling a method from DAO.
	  
		public static ArrayList getSearchCustomerNamesBD()
		{
		  //Adding all the exception to the getLogger method for admin module 
			Logger logger=Logger.getLogger("Admin");  
		  
		//Start of try block	
		  try
			{
			    ArrayList strCustomerNames=VIMSSearchDAO.getSearchCustomerNamesDAO();
			  //System.out.println("---------strCustomerNames------------"+strCustomerNames);
			  
			  //Adding the hashmap to arrayList object
			    return strCustomerNames;
			}
		//end of try block 
		  
	    //start of catch block
		  catch(Exception exception)
		      {
		    	  
		    	  logger.info("VIMSSearchBD.getSearchCustomerNamesBD");
		    	  logger.error(exception);
		    	  System.out.println("The Exception is:"+exception);
		    	  return null;
	          }	
		//end of catch block  
		}
	   //End of getSearchCustomerNamesBD() method.  
		
	  //Start of getSearchStatusBD() method and is used to get the status by calling a method from DAO.
		
		public static ArrayList getSearchStatusBD()
		{
		  //Adding all the exception to the getLogger method for admin module 
			Logger logger=Logger.getLogger("Admin"); 
			
		//Start of try block	
		  try
			{
				ArrayList strStatus=VIMSSearchDAO.getSearchStatusDAO();
			  //System.out.println("---------strStatus------------"+strStatus);
			   
			  //Adding the hashmap to arrayList object	
				return strStatus;
	        }
		//end of try block 
		
	   //start of catch block  
		 catch(Exception exception)
		      {
		    	  
		    	  logger.info("VIMSSearchBD.getSearchStatusBD");
		    	  logger.error(exception);
		    	  System.out.println("The Exception is:"+exception);
		    	  return null;
	          }		
	  //end of catch block	
		}
	 //End of getSearchStatusBD() method.  
		
	//Start of getSearchSeverityBD() method and is used to get the severity by calling a method from DAO.
		
		public static ArrayList getSearchSeverityBD()
		{
		
		//Adding all the exception to the getLogger method for admin module  
		  Logger logger=Logger.getLogger("Admin"); 
			
		//Start of try block 
		  try
			 {
			   ArrayList strSeverity=VIMSSearchDAO.getSearchSeverityDAO();
			   //System.out.println("---------strSeverity------------"+strSeverity);
			   
			  //Adding the hashmap to arrayList object 
			    return strSeverity;
			 }
		//end of try block 
			
	   //start of catch block  
		 catch(Exception exception)
		      {
		    	  
		    	  logger.info("VIMSSearchBD.getSearchSeverityBD");
		    	  logger.error(exception);
		    	  System.out.println("The Exception is:"+exception);
		    	  return null;
	          }	
	  //end of catch block
		}
	 //End of getSearchSeverityBD() method.    
		
	//Start of searchRecordBD() method and is used to get the records by calling a method from DAO.
		
		public static ArrayList searchRecordBD(VIMSSearchForm searchForm, String strUserID, String strRoleType)
		{
		   
		//Adding all the exception to the getLogger method for admin module 	
		  Logger logger=Logger.getLogger("Admin"); 
		  
		//Start of try block
		  try
			 {
			
			   //System.out.println("Iam in searchRecordBD");
			   ArrayList strRecords=VIMSSearchDAO.searchRecordDAO(searchForm,strUserID,strRoleType);
			   //System.out.println("-------Records in BD----------"+strRecords);
			   
			   //Adding the hashmap to arrayList object  
			    return strRecords;
			 }
		//end of try block 
		  
	    //start of catch block  
		  catch(Exception exception)
	      {
	    	  
	    	  logger.info("VIMSSearchBD.searchRecordBD");
	    	  logger.error(exception);
	    	  System.out.println("The Exception is:"+exception);
	    	  return null;
          }	
		//end of catch block	
		}
	//End of searchRecordBD() method.    	
	}
//End of VIMSSearchBD class.  
