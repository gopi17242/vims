package com.vertex.VIMS.test.logout.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertex.VIMS.test.VIMSSQLQueryInterface.VIMSQueryInterface;
import com.vertex.VIMS.test.common.DBConnection;

public class VIMSLogout extends Action {

	/*
	 * The action is used for invalidating the session and for logging out
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response 
	 *  
	 */
	 public ActionForward execute(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse response) throws Exception {
                     Logger logger=null;
                     HttpSession session=null;
                     String strUserType=null;
                     
                     Connection connection=null;
                     PreparedStatement preparedStatement=null;
                     ResultSet resultset=null;
                     CallableStatement callableStatement=null;
                     
                     String strLogoutTime = null;
                     String strLoginID=null;
                     
                     int intLoginIDNumber=0;
                     
                 try { // try block starts here
                	  session=request.getSession(false);
                	  strUserType=(String)session.getAttribute("Role");
                	  logger=Logger.getLogger(strUserType);
                	  
		    	     if(session!=null) { // If starts
		    	    	 
		    	    	 connection=DBConnection.createConnection();
		    	         preparedStatement = connection.prepareStatement(VIMSQueryInterface.getDateSQL);
						 resultset = preparedStatement.executeQuery();
						 while (resultset.next())
						{//while start
							 strLogoutTime = resultset.getString(1);
						}//while end
						 
					 strLoginID=(String) session.getAttribute("user"); 
					 intLoginIDNumber=((Integer)session.getAttribute("LoginIDNumber")).intValue(); 
					 callableStatement=connection.prepareCall("{CALL vims_user.USP_Save_User_Log_Tm(?,?,?,?)}");
					 callableStatement.setInt(1,intLoginIDNumber);
					 callableStatement.setString(2,strLoginID);
					 callableStatement.setString(3,null);
					 callableStatement.setString(4,strLogoutTime);
					 System.out.println("=======Login ID Number============="+intLoginIDNumber);
					 System.out.println("=======Login ID ============="+strLoginID);
					 System.out.println("===========Logout Action==========="+callableStatement.execute());
		    	    	 
		    	    	 session.invalidate();
		    	       	 response.sendRedirect(".");
		    	    	 
		    	     } // If ends 
		    	     else { // Else block starts
		    	    	 return actionMapping.findForward("sessionExpirePage");
		    	     } // End of If
		    	  } //try block ends  
                  catch(Exception exception) { // catch block starts
                	  exception.printStackTrace();
		    	  return actionMapping.findForward("sessionExpirePage");
                  //logger.info("---------VIMS Logout Action------");
		    	  //logger.error(exception);
		      } // End of catch block
                 finally
                 {
                	 if(connection!=null)
                	 {
                		 connection.close();
                	 }
                	 if(preparedStatement!=null)
                	 {
                		 preparedStatement.close();
                	 }
                	 if(callableStatement!=null)
                	 {
                		 callableStatement.close();
                	 }
                	 if(resultset!=null)
                	 {
                		 resultset.close();
                	 }
                 }
		      return actionMapping.findForward("sessionExpirePage");
	 } // End of execute method
}
