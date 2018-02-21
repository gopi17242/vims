 package com.vertex.VIMS.test.VIMSFAQ.DAO;

//import statements
  import java.sql.*;
  import java.util.*;

  import javax.servlet.http.HttpServletRequest;
  import org.apache.log4j.Logger;
  import com.vertex.VIMS.test.VIMSSQLQueryInterface.VIMSQueryInterface;
import com.vertex.VIMS.test.common.DBConnection;
  
//Start of VimsFaqDAO class
  public class VimsFaqDAO
  {
	    static Connection connection=null;
	    static Statement statement=null;
	    static HashMap hashMap=null;
	    static ArrayList arrayList=null; 
	    
	   
	    public static ArrayList getFaqDetailsDAO() 
		{
	    	int questionNum=1;
	      //System.out.println("-----------In DAO class------------");
	      Logger logger=Logger.getLogger("Admin"); 
    	  try
    	   {
    		  Connection connection=DBConnection.createConnection();
  		      Statement statement=connection.createStatement();
  		      ResultSet resultSet=statement.executeQuery(VIMSQueryInterface.getFaqListsql);
              arrayList =new ArrayList();
              
  		     while(resultSet.next())
  			  {
  		    	 hashMap=new HashMap();
  				 hashMap.put("QuestionID",questionNum+". "); 
  				 hashMap.put("Question",resultSet.getString(2));
  				 hashMap.put("Answer","A. "+resultSet.getString(3));
  				 
  			     arrayList.add(hashMap);
  			   questionNum++;
			  }
  		     
  		     //System.out.println("---------arrayList in FAQ------"+arrayList);
  		     return arrayList;
  		   
    	   }
    	   catch(Exception exception)
 		   {
 			  logger.info("VimsFaqDAO.getFaqDetailsDAO");
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