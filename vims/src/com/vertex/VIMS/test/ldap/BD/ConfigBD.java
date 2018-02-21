package com.vertex.VIMS.test.ldap.BD;

/**
 * @author Rajashekar.B
 * 
 */

import com.vertex.VIMS.test.ldap.DAO.*;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import com.vertex.VIMS.test.ldap.form.ConfigForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class ConfigBD {

	 static String result=null;
     static Logger logger=null;	
	 public static String storeLdapConfig(String strServerName,String strPortNumber,String strStatus,String serverId) {
		     
		      
		      String strLdapServerName=null;
		      
		     try {
		    	   logger=Logger.getLogger("Admin");
		    	   strLdapServerName=buildServerName(strServerName,strPortNumber);
		    	   if(strLdapServerName!=null) {
		    		  result=ConfigDAO.storeLdapConfig(strLdapServerName,strStatus,serverId);
		    	   }
		        } 
		       catch(Exception exception) {
		    	    logger.info("=====Exception in storeLdapConfig BD=========");
		    	    logger.error(exception);
		    	    return null;
		       }
		        return result;
	 }
	 
	  public static String storeMailConfig(String serverName,String strStatus,String strServerId) {
		
	             
	            try {   logger=Logger.getLogger("Admin");
	            	    result=ConfigDAO.storeMailCongif(serverName,strStatus,strServerId);
	             } catch(Exception exception) {
	            	 logger.error(exception);
	            	 logger.info("=====Exception in storeMailConfig BD=========");
	            	 return null;
	            }
		         
	             return result; 
	  }
	 
	 
	 public static String buildServerName(String strServerName,String strPortNumber) {
		   
		     String targetServerName=null;
		    
		     try {
		    	 logger=Logger.getLogger("Admin");
			     targetServerName="LDAP://";
			     targetServerName=targetServerName+strServerName+":";
			     targetServerName=targetServerName+strPortNumber;
		     }
		      catch(Exception exception) {
		    	   logger.error(exception);
		    	   logger.info("----exception in buildServerName method----");
		    	   return null;
		      }
		      return targetServerName;
	 }
	 
	 public static HashMap getCustomOptions(String userId) {
		
		         HashMap customOptions=null;
		  try {
			       logger=Logger.getLogger("Admin");
			       customOptions=ConfigDAO.getCustomOptions(userId);
			           
		   } catch(Exception exception) {
			   logger.info("======Exception in getCustomOptions BD=========");
			   logger.error(exception);
		   }
		     return customOptions;
	 }
	 
	  public static ArrayList getServerList(String strServerType) {
		 
		           ArrayList serverList=null;
		      try {
		    	    logger=Logger.getLogger("Admin");
		    	    serverList=ConfigDAO.getServerList(strServerType);
		    	  } catch(Exception exception) {
		    	       logger.error(exception);
		    		   logger.info("------Exception raised in getServerList BD-----");
		       
		      }
		    	    return serverList;
	  }
	  
	  public static HashMap getServerDetails(String strServerType,String strServerId,String strDefaultId) {
		              HashMap serverRecord=null;
		      try {
		    	      logger=Logger.getLogger("Admin");
		    	      serverRecord=ConfigDAO.getServerDetails(strServerType,strServerId,strDefaultId);      
		    	      
		      } catch(Exception exception) {
		    	  logger.error(exception);
		    	  logger.info("=======Exception in  getServerDetails BD=========");
		    	  return null;
		      }
		       return serverRecord;
	  }
	  
	  public static int deleteServerDetails(String strServerType,String strServerId) {
		            int result;
		     try {
		    	       logger=Logger.getLogger("Admin");
		    	       result=ConfigDAO.deleteServerDetails(strServerType,strServerId);
		    	       
		     } catch(Exception exception) {
		    	 logger.info("======Exception in deleteServerDetails Bd======"); 
		    	 logger.error(exception);
		    	 return 0;
		     }
		        return result;
	  }
	  
	  public static int persistSettings(String[] strOptions,String strDuration,String strUserId) {
		          int result=0;
		     try {
		    	   logger=Logger.getLogger("Admin");
		    	   result=ConfigDAO.persistSettings(strOptions,strDuration,strUserId);
		    	   
		     } catch(Exception exception) {
		    	 logger.error(exception);
		    	 logger.info("=====Exception in persistSettings========");
		     }
		       return result;
	  }
	  
	  public static void getCustomFieldOptionsBD(HttpSession session) {
		  
		  try { // try block starts
			    logger=Logger.getLogger("Admin");
			    ConfigDAO.getCustomFieldOptionsDAO(session);
			  } // try block End
		   catch(Exception exception) { // catch block starts
			   logger.info("=====Exception in getCustomFieldOptionsBD========");   
			   logger.error(exception);
		    } //catch block ends
	  }
	  
	  public static String addPositionBD(String strPositionId,String strPosition,String strPositionStatus,String strRoleType,String strUserId) {
		       
		  String executeResult=null;
		  
		  try { // try block starts
			    logger=Logger.getLogger("Admin");
			    executeResult=ConfigDAO.addPositionDAO(strPositionId,strPosition,strPositionStatus,strRoleType,strUserId);
			  } // try block End
		   catch(Exception exception) { // catch block starts
			   logger.info("=====Exception in getCustomFieldOptionsBD========");   
			   logger.error(exception);
		    } //catch block ends
		    return executeResult;
	  }
	  
	  
	  public static ArrayList getPositionListBD(String strPositionId,String strUserId) {
	       
		  ArrayList positionList=null;
		  
		  try { // try block starts
			    logger=Logger.getLogger("Admin");
			    System.out.println("======position id from BD======="+strPositionId);
			    positionList=ConfigDAO.getPositionListDAO(strPositionId,strUserId);
			  } // try block End
		   catch(Exception exception) { // catch block starts
			   logger.info("=====Exception in getCustomFieldOptionsBD========");   
			   logger.error(exception);
		    } //catch block ends
		    return positionList;
	  }
	  
	  
	  public static String deletePositionBD(String strPositionId,String strUserId) {
		      
		        String executeResult=null;
		  try { // try block starts
			    logger=Logger.getLogger("Admin");
			    executeResult=ConfigDAO.deletePositionDAO(strPositionId,strUserId);
			  } // try block End
		   catch(Exception exception) { // catch block starts
			   logger.info("=====Exception in deletePositionBD========");   
			   logger.error(exception);
		    } //catch block ends
		    return executeResult;
	  }
	  
	  public static String insertCountryDetailsBD(ConfigForm form,String strUserId,String strCountryId) {
		  String strResult=null;
		   try {
                     strResult=ConfigDAO.insertCountryDetailsDAO(form,strUserId,strCountryId);
		   } catch(Exception exception) {
			    logger.info("----Exception in insertCountryDetails=========");
			    logger.error(exception);
		   }
		    return strResult;
	  }
	  
	  public static String setServerStatusBD(String strServerType,String strServerId) {
		     String strResult=null; 
          try {
        	     strResult=ConfigDAO.setServerStatusDAO(strServerType, strServerId);
          } 
           catch(Exception exception) {
          	    logger.info("=====Exception in setServerStatusBD===========");
          	    logger.error(exception);
          	    exception.printStackTrace();
           }
            return strResult;
	  }
	  
	  public static HashMap getTabTreeBD() {
		        HashMap tabList=null;
		   try {
			      logger=Logger.getLogger("Admin");
			     tabList=ConfigDAO.getTabTreeDAO();
	    }
		 catch(Exception exception) {
			 
			  logger.info("----Exception in getTabTreeBD-----------");
			  logger.error(exception);
			  exception.printStackTrace();
		 }
		   return tabList;
	  }
	  
	  public static ArrayList getRoleTypesBD() {
		      Logger logger=null;
		      ArrayList roleTypes=null;
		try {
               logger=Logger.getLogger("Admin");
                roleTypes=ConfigDAO.getRoleTypesDAO();
		}
		 catch(Exception exception) {
			 logger.info("========Exception in getRoleTypesBD method=======");
			 logger.info(exception);
			 exception.printStackTrace();
		 }
		  return roleTypes;
	  }
	  
	  public static String insertRoleTypeBD(String strRoleId,String strRoleName,String strDescription,String strFlag) {
	      Logger logger=null;
	      String executeResult=null;
	try {
           logger=Logger.getLogger("Admin");
            executeResult=ConfigDAO.insertRoleTypeDAO(strRoleId, strRoleName, strDescription,strFlag);
	}
	 catch(Exception exception) {
		 logger.info("========Exception in getRoleTypesBD method=======");
		 logger.info(exception);
		 exception.printStackTrace();
	 }
	  return executeResult;
  }
	  public static ArrayList getRoleListBD(String roleId) {
		 return ConfigDAO.getRoleListDAO(roleId);  
	  }
  
	  public static String deleteRoleTypeBD(String strRoleId) {
	      
	        String executeResult=null;
	  try { // try block starts
		    logger=Logger.getLogger("Admin");
		    executeResult=ConfigDAO.deleteRoleTypeDAO(strRoleId);
		  } // try block End
	   catch(Exception exception) { // catch block starts
		   logger.info("=====Exception in deletePositionBD========");   
		   logger.error(exception);
	    } //catch block ends
	    return executeResult;
  }
	  
	public static ArrayList getNavigationSetBD(String strRoleId,String strUserId) {
	   return ConfigDAO.getNavigationSetDAO(strRoleId, strUserId);
	}
	
	public static int storeRoleNavigSettingsBD(HttpServletRequest request,String strUserId) {
                return ConfigDAO.storeRoleNavigSettingsDAP(request, strUserId);
	}

    public static ArrayList getLocationDetailsBD(String strLocationId,String strStatus,String strUserId) {
            return ConfigDAO.getLocationDetailsDAO(strLocationId, strStatus, strUserId);
    }
    public static String insertLocationDetailsBD(String strLocationId,String strLocation,String strLocationStatus,String strUserId) {
        return ConfigDAO.insertLocationDetailsDAO(strLocationId, strLocation, strLocationStatus, strUserId);	
    }
    
    public static String getRoleNameBD(String strRoleId) {
    	return ConfigDAO.getRoleNameDAO(strRoleId);
    }
    
    public static ArrayList getCountryListBD(String strCountryCode,String strCountryStatus) {
             	return ConfigDAO.getCountryListDAO(strCountryCode, strCountryStatus); 
    }
    
    public static String insertCountryDetailsBD(String strCountryId,String strCountry,String strCountryStatus) {
    	return ConfigDAO.insertCountryDetailsDAO(strCountryId, strCountry, strCountryStatus);
    }

	public static ArrayList getCountryList()
	{
		return ConfigDAO.getCountryList();
	}

	public static ArrayList getStateList(String strCountryID, String strSateID)
	{
		return ConfigDAO.getStateList(strCountryID,strSateID);
	}

	public static String SaveStateDetails(String strCountryID,String strStateID, String strStateName, String strStateStatus)
	{
	   return ConfigDAO.SaveStateDetails(strCountryID,strStateID,strStateName,strStateStatus);
	}
	
	public static ArrayList getFooterOptionsBD(String strOptionId) {
		 Logger logger=null;
		 ArrayList list=null;
		 try {
			 logger=Logger.getLogger("Admin");
			 list=ConfigDAO.getFooterOptionsDAO(strOptionId); 
			 }  
		  catch(Exception exception) {
			  logger.info("---Exception in getFooterOptionsBD method----");
			  logger.error(exception);
			  exception.printStackTrace();
			  return null;
		  }
		   return list;
	}
	
	public static String storeContentBD(String strOptionId,String strContent) {
		return ConfigDAO.storeContentDAO(strOptionId, strContent);
	}
}