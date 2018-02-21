/*
  FileName	    : VIMSClientBD.java
  
 
  Description	: This BD class is used to call different methods 
                  in DAO(Data Access Object) and set the return values 
  
                   
  Developed by  : Vertex Computer Systems.
                  copyright (c) 2008 Vertex.
                  All rights reserved.
  
  Change History:
 
  Revision No.	:		Date		  @author		Description
    1.0				Friday 15,2008	  Geeta.M	   Initial Version.*/


//Package structure followed for this class.
  package com.vertex.VIMS.test.clients.BD;

//import statements 
  import com.vertex.VIMS.test.applications.DAO.VIMSApplicationDAO;
  import com.vertex.VIMS.test.clients.DAO.VIMSClientDAO;
  import com.vertex.VIMS.test.clients.form.VIMSCustomerForm;
  import com.vertex.VIMS.test.employee.DAO.VIMSEmployeeDAO;
  import com.vertex.VIMS.test.listofissues.DAO.ListofIssuesDAO;
  import java.util.*;
  import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
 
//Start of VIMSClientBD class
  
  public class VIMSClientBD 
  {
	/**********Method Name:-------->getClientDetailsBD ****************/
	
	/**********This method returns arrayList containing few details of the client which are needed for displaying in the landing page*******/	
   
	 /**
	 * @return
	 */
	public static ArrayList getClientDetailsBD() 
	  {
	    
		 //Adding all the exception to the getLogger method for admin module	  
		   Logger logger=Logger.getLogger("Admin"); 
	       
		 //Start of try block 
		   try
   	         {
	          ArrayList list=VIMSClientDAO.getClientDetailsDAO();
	          return list;
		     }
		 //End of try block
	    
		 //Start of catch block   
		   catch(Exception exception)
	        {
			  exception.printStackTrace();
	    	  logger.info("VIMSClientBD.getClientDetailsBD");
	    	  logger.error(exception);
	    	  System.out.println("The Exception is:"+exception);
	    	  return null;
            }
	   //End of catch block  
	  }
	//End of getClientDetailsBD method
	
	  /**********Method Name:-------->addClientDetailsBD ****************/
		
	  /**********This method contains a boolean as return type and calls a method in DAO for the resultant passing form object as parameter******
	 
	 * @param strRandomGnrtPassword */	
	   	 
      /**
     * @param customerForm
     * @param strRandomGnrtPassword
     * @return
     */
    public static String[] addClientDetailsBD(VIMSCustomerForm customerForm, String strRandomGnrtPassword) 
	   {
	    
    	//Adding all the exception to the getLogger method for admin module  
    	  Logger logger=Logger.getLogger("Admin");  
    	  String[] strValues=null;
       //Start of try block	  
         try
  	       {
	    	 //calling a method in DAO sending form object as parameter
        	 strValues=VIMSClientDAO.addClientDetailsDAO(customerForm,strRandomGnrtPassword);
	         
		    //Start of if condition 
        	 
  	      }
        //End of try block
         
        //Start of catch block  
	      catch(Exception exception)
	       {
	    	 logger.info("VIMSClientBD.addClientDetailsBD");
	    	 logger.error(exception);  
	    	 System.out.println("The Exception is:"+exception);
	       }
	    //End of catch block
	     
	    //Returns boolean value   
	      return strValues;	
	 }
   //End of addClientDetailsBD method    
      

	  /**********Method Name:-------->displayClientDetailsBD ****************/
		
	  /**********This method returns an arrayList calling a method in DAO by sending parameter as checkboxID *******/	
	   	 
      /**
     * @param strCustID
     * @return
     */
    public static HashMap displayClientDetailsBD(String strCustID) 
	   {
        //Adding all the exception to the getLogger method for admin module  
    	  Logger logger=Logger.getLogger("Admin");  
	   
        //Start of try block	 
          try
  	       {
	    	 HashMap hashMap=VIMSClientDAO.displayClientDetailsDAO(strCustID);
	         return hashMap;
		   }
       //End of try block  
	    
      //Start of catch block  
        catch(Exception exception)
	      {
	    	  logger.info("VIMSClientBD.displayClientDetailsBD");
	    	  logger.error(exception);  
	    	  System.out.println("The Exception is:"+exception);
	    	  return null;
          }		
	  //End of catch block
	  }
    //End of displayClientDetailsBD method
    
   /**********Method Name:-------->modifyClientDetailsBD ****************/
		
   /**********This method returns boolean type variable calling a method in DAO by sending form object as parameter*******/	
	   	 
     
     /**
     * @param customerForm
     * @param strCustID
     * @return
     */
    public static boolean modifyClientDetailsBD(VIMSCustomerForm customerForm,String strCustID) 
	  {
		//Adding all the exception to the getLogger method for admin module   
	      Logger logger=Logger.getLogger("Admin");   
	      
	    //Start of try block  
	      try
 	      {
	    	//Calling a method from DAO class using form object as parameter 
		      int result=VIMSClientDAO.modifyClientDetailsDAO(customerForm,strCustID);
	          //System.out.println("Iam in UpdateBD class");
		    
		    //Start of if condition 
		      if(result==1)
		      {
		     	 return true;
		      }else
		      {
		    	 return false;
		      }
		    //End of if condition  
 	      }
	    //End of try block 
	    
	    //Start of catch block  
	      catch(Exception exception)
	       {
	    	 logger.info("VIMSClientBD.modifyClientDetailsBD");
	    	 logger.error(exception);    
	    	 System.out.println("The Exception is:"+exception);
	       }
	    //End of catch block  
	    
	  //Returns boolean value   
	    return false;	
	 }
    //End of modifyClientDetailsBD method
     
     /**********Method Name:-------->deleteClientDetailsBD ****************/
		
     /**********This method returns a integer type calling a method in DAO by sending the parameter as checkboxID*******/	
  	   
     /**
     * @param strCustID
     * @return
     */
    public static int deleteClientDetailsBD(String strCustID) 
	  {
	    //Adding all the exception to the getLogger method for admin module   
    	  Logger logger=Logger.getLogger("Admin");  
	    
    	//Start of try block   	
    	try
 	      {
	    	int intDel=VIMSClientDAO.deleteClientDetailsDAO(strCustID);
	        //System.out.println("Iam in delete BD");
		  }
    	//End of try block
    	
    	//Start of catch block
	      catch(Exception exception)
	       {
	    	 logger.info("VIMSClientBD.deleteClientDetailsBD");
	    	 logger.error(exception);      
	    	 System.out.println("The Exception is:"+exception);
	       }
	    //End of catch block  
	   
	   //Returns int value
	     return 0;		
	}
   //End of deleteClientDetailsBD method 
    
   /**********Method Name:-------->viewClientDetailsBD ****************/
		
   /**********This method returns arrayList by calling a method in DAO by sending the parameter as customerID*******/   
     
     /**
     * @param customerID
     * @return
     */
    public static ArrayList viewClientDetailsBD(String customerID) 
	  {
	    
       //Adding all the exception to the getLogger method for admin module   	 
    	 Logger logger=Logger.getLogger("Admin");  
	    
       //Start of try block   		
    	try
 	      {
	    	ArrayList arrayList=VIMSClientDAO.viewClientDetailsDAO(customerID);
	        //System.out.println("Iam in viewClientBD");
	    	return arrayList;
		  }
       //End of try block
    	
       //Start of catch block	
	     catch(Exception exception)
	      {
	    	  logger.info("VIMSClientBD.viewClientDetailsBD");
	    	  logger.error(exception);      
	    	  System.out.println("The Exception is:"+exception);
	    	  return null;
        }		
	  //End of catch block
	  }
   //End of viewClientDetailsBD method
     
     
  public static HashMap getNewCustomerDetailsBD() {
 		HashMap customerList=null;
 		 try {
 			    customerList=VIMSClientDAO.getNewCustomerDetailsDAO();
 		 } catch(Exception exception) {
 			 System.out.println("Exception in getNewCustomerDetailsBD---------"+exception);
 		 }
 		 
 		    return customerList;
 	}
 	
  /**********Method Name:-------->checkCustomerEmailIdBD ****************/
	
  /**********This method returns boolean value by calling a method in DAO by sending the parameter as strEmailID*******/   
 
  public static boolean checkCustomerEmailIdBD(String strEmailID) 
	   {
    //Start of try block	  
     try
	       {
	    	 //calling a method in DAO sending form object as parameter
 	       boolean result=VIMSClientDAO.checkCustomerEmailIdDAO(strEmailID);
 	       //System.out.println("------result in checkCustomerIdBD------"+result);
 	       return result;
	       }
     //End of try block
  
     //Start of catch block  
	      catch(Exception exception)
	       {
	    	 System.out.println("The Exception is:"+exception);
	       }
	    //End of catch block
	     
	    //Returns boolean value   
	      return false;	
	 }
  //End of checkCustomerEmailIdBD method
  
 	public static void setClientFlagBD(String strCustomerId) {
		           Logger logger=null;
		  try{
		        logger=Logger.getLogger("Admin");
		        VIMSClientDAO.setClientFlagDAO(strCustomerId);
            } catch(Exception exception) {
		    	  logger.info("----Exception in setApplicationFlagBD-----------");
		    	  logger.error(exception);
		    }
	  }
 	
 	 public static ArrayList searchCustomerBD(String strCustSearch)
	   {
      //Adding all the exception to the getLogger method for admin module  
  	  Logger logger=Logger.getLogger("Admin");  
	   
      //Start of try block	 
        try
	       {
	    	 ArrayList arrayList=VIMSClientDAO.searchCustomerDAO(strCustSearch);
	         return arrayList;
		   }
     //End of try block  
	    
    //Start of catch block  
      catch(Exception exception)
	      {
	    	  logger.info("VIMSClientBD.searchCustomerBD");
	    	  logger.error(exception);  
	    	  System.out.println("The Exception is:"+exception);
	    	  return null;
        }		
	  //End of catch block
	  }
 }
//End of VIMSClientBD class 
