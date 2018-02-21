package com.vertex.VIMS.test.customer.DAO;
/**
 * @author saikumar.m
 *
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.vertex.VIMS.test.VIMSSQLQueryInterface.VIMSQueryInterface;
import com.vertex.VIMS.test.common.DBConnection;
public class VIMSCustomerDAO {
	static PreparedStatement preparedStatement=null;
	static Statement statement=null;		
	static Connection conn=null;
	static ResultSet resultSet=null;
	static ArrayList arrayList=null;
	static HashMap hashMap=null;
	static Logger logger=null;
	
	public static ArrayList getCustIssueListDAO(String custId, String issueStatus)
	{
		arrayList=new ArrayList();
		conn = DBConnection.createConnection();
		logger=Logger.getLogger("Customer");
		
			try 	
			{
				if(issueStatus.equals("all")||issueStatus==null)
				{
					preparedStatement = conn.prepareStatement(VIMSQueryInterface.getAllCustIssuesListSql);
					preparedStatement.setString(1,custId);
					resultSet = preparedStatement.executeQuery();
					while(resultSet.next())
					{
						hashMap=new HashMap();
						
						hashMap.put("issueId", resultSet.getString(1));
						hashMap.put("appName", resultSet.getString(2));
						hashMap.put("title", resultSet.getString(3));
						hashMap.put("status", resultSet.getString(4));
						hashMap.put("createdDateTime", resultSet.getString(5));
						hashMap.put("severity", resultSet.getString(6));
						hashMap.put("targetDateTime", resultSet.getString(7));
						hashMap.put("closedDate", resultSet.getString(8));
						hashMap.put("resolutionInHour", resultSet.getString(9));
						
						arrayList.add(hashMap);		
					}
					return arrayList;
			
				}
				else
				{
					
						preparedStatement = conn.prepareStatement(VIMSQueryInterface.getCustIssuesListSql);
						preparedStatement.setString(1,custId);
						preparedStatement.setString(2,issueStatus);
						resultSet = preparedStatement.executeQuery();
						while(resultSet.next())
						{
							
							hashMap=new HashMap();
							
							hashMap.put("issueId", resultSet.getString(1));
							hashMap.put("appName", resultSet.getString(2));
							hashMap.put("title", resultSet.getString(3));
							hashMap.put("status", resultSet.getString(4));
							hashMap.put("createdDateTime", resultSet.getString(5));
							hashMap.put("severity", resultSet.getString(6));
							hashMap.put("targetDateTime", resultSet.getString(7));
							hashMap.put("closedDate", resultSet.getString(8));
							hashMap.put("resolutionInHour", resultSet.getString(9));
							
							arrayList.add(hashMap);		
							
						}
						return arrayList;
				}		
			}
			catch (Exception e)
			{
				logger.info("VIMSCustomerDAO.getCustIssueListDAO()");
				logger.error(e);
				return null;
			}
			finally
			{			
				DBConnection.closeConnection();				
			}
		
	}
	
	/* public static HashMap getNewCustomerDetailsDAO() {
		       
		        Logger logger=null;
		        Connection connection=null;
		        String sqlQuery="select * from CUSTOMER";
		      
		 try {
			    logger.getLogger("Admin");
			    connection=DBConnection.createConnection();
			    statement=connection.createStatement();
			    
			    resultSet=statement.executeQuery(sqlQuery);
			    
			    if(resultSet!=null) {
			    	
			    	while(resultSet.next()) {
			    		hashMap=new HashMap();
			    		
			    		hashMap.put("customer_name",resultSet.getString(""));
			    	}
			    }
		 } catch(Exception exception) {
			 logger.error(exception);
			 System.out.println("--------Exception in getNewCustomerDetailsDAO---------"+exception);
		 }
	 } */
}
	
