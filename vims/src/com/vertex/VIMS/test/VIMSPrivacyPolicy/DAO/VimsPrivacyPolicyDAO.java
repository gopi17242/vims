 package com.vertex.VIMS.test.VIMSPrivacyPolicy.DAO;

//import statements
  import java.sql.*;
  import java.util.*;
  import org.apache.log4j.Logger;
  import com.vertex.VIMS.test.VIMSSQLQueryInterface.VIMSQueryInterface;
  import com.vertex.VIMS.test.common.DBConnection;
  
//Start of VimsFaqDAO class
  public class VimsPrivacyPolicyDAO
  {
	    static Connection connection=null;
	    static Statement statement=null;
	    static HashMap hashMap=null;
	    static ArrayList arrayList=null;  
	   
	    public static ArrayList getPrivacyPolicyDetails() 
		{
	      //System.out.println("-----------In DAO class------------");
	      Logger logger=Logger.getLogger("Admin"); 
    	  try
    	   {
    		  Connection connection=DBConnection.createConnection();
  		      Statement statement=connection.createStatement();
  		      ResultSet resultSet=statement.executeQuery(VIMSQueryInterface.getPrivacyPolicyListsql);
              arrayList =new ArrayList();
  		   
  		     while(resultSet.next())
  			  {
  		    	 hashMap=new HashMap();
  				 hashMap.put("PrivacyID",resultSet.getString(1)); 
  				 hashMap.put("PrivacyData",resultSet.getString(2));
  				
  				 
  			     arrayList.add(hashMap);
			  }
  		     
  		     //System.out.println("---------arrayList in FAQ------"+arrayList);
  		     return arrayList;
  		   
    	   }
    	   catch(Exception exception)
 		   {
 			  logger.info("VimsPrivacyPolicyDAO.getPrivacyPolicyDetails");
 	    	  logger.error(exception);
 			  System.out.println("The Exception is:"+exception);
 		   }
    	   finally
			{
			 try
				{
			      DBConnection.closeConnection();
				} 
			 
			 catch (Exception exception)
				{
					System.out.println("==========exception Occured while closing the connection==============="+exception);
					exception.printStackTrace();
				}
		}
	   return null;
	  }
  }