package com.vertex.VIMS.test.common;
/*
 * author@ saikumar.m
 * 
 * Modified By Aditya.p on 2/12/2008
 * 
 * This java class is used to send mails. Here In this file the mail will be sent to the concerned persons who worked on the issue or who assigned the issue etc.
 * 
 * We can use this file as a status mailing concept in further processing.
 * 
 */
//import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
//import javax.activation.FileDataSource;

import org.apache.log4j.Logger;
public class VIMSApplicationMail 
{
	static Logger logger=null;
    public static boolean sendMail(HashMap<String,String> details,String strContextPath) {
    
    	logger=Logger.getLogger("Admin");
    	String strReciever=null;
    	//String strMessage=null;
    	String strSender=null;
     	Properties props=null;
    	Session session=null;
    	MimeMessage message=null;
    	
    	InternetAddress sender=null;
    	InternetAddress reciever=null;
    	Multipart multiMsg=null;
  	   	
	    try 
	    {	
	      
	      strReciever=(String)details.get("receiver");
	     
	      strSender=(String)details.get("sender");
	      
    	  props=new Properties();
    	 
    	  props.put("mail.smtp.host","clev.vertexcs.com");
    	  
    	  // Get the connection 
    	  
    	  session=Session.getInstance(props, null);
    	  
    	  // Create the message object
    	  
    	  message=new MimeMessage(session);
    	 
    	 // Set the Sender and Reciever address information
    	
    	  sender=new InternetAddress(strSender);
    	  
    	  
    	  reciever=new InternetAddress(strReciever);
    	 
    	  
    	 // Attach the details to message
    	  
          // Set the sender address     	  
	     	message.setFrom(sender);
	     	 
	     	    	  
	     	     	
	      // Set the Priority of mail ("High","Normal" or "Low" )
	     	  
	     	message.setHeader("Importance","High");
	     	
	    	
	     	// Set the reciever address
	     	message.setRecipient(Message.RecipientType.TO,reciever);
	     	
	     	     // set the subject of mail
	     	message.setSubject("VIMS Info Mail");
	     	
	     	    
	     	 // Create multipart object 
	     	        
	     	multiMsg=new MimeMultipart();
	     	
  	     	 
	     	// Mail body has been set using an html file
	     	     
	     	BodyPart object=new MimeBodyPart();
	     	
	     	       
	     	object=getFileBodyPart(details, strContextPath);
	     	 
	     	multiMsg.addBodyPart(object);
	     	
	     	message.setContent(multiMsg);
	     
	     	// Send the mail
	     	        
	     	Transport.send(message);
	     	
	     	return true;  
	     	      
       }
         catch(Exception exception) 
         {
         	logger.info("VIMSApplicationMail.sendMail()");
         	logger.error(exception);
         	exception.printStackTrace();
         	return false; 
    	 }   
    }        
        
    public static BodyPart getFileBodyPart(HashMap<String, String> details,String strContxtPath)     
      {
      
    	logger=Logger.getLogger("Admin");
      	//String fileName=null;
      	String contentType=null;
      	//String strContextPath=null;
      	String strActualMsg=null;
      	BodyPart bodyPart=null;
      	
      	//FileInputStream fis=null;
      	//String strFormattedTable;
      	//byte data[];     	
      	try 
      	{      	  
          bodyPart = new MimeBodyPart();
         
          contentType="text/html"; 
         		           
          strActualMsg=new String();          
                  
          strActualMsg="<img src='"+strContxtPath.replace("/addApplication.do","")+"/images/vertexLogo.png'/><br>";
          
          System.out.println("===strActualMsg==="+strActualMsg);
          
          strActualMsg=strActualMsg+"<br><font color='black' size='2' style='font-family:Verdana,Arial;font-size: 70%;	font-weight: normal; color:Black;text-decoration: none;padding-left: 0px;line-height:24px;'>URL:<a href='"+strContxtPath.replace("/addApplication.do","")+"'>Vertex Issue Management System</a><br><br>";
                  
          strActualMsg=strActualMsg+"<i><b>This is an automated message. Please do not reply.</b></i><br><br>";
          
          String appName=(String) details.get("appName");
			
          String userId=(String) details.get("userId");
          
          String pwd=(String) details.get("pwd");
          
          String appOwnerName=((String)details.get("name"));
 			
          strActualMsg=strActualMsg+"Dear&nbsp;"+appOwnerName+":";
          if(details.get("checkUser").equalsIgnoreCase("Old"))
          {
        	  strActualMsg=strActualMsg+"<br>You are already a customer of Vertex."; 
        	  strActualMsg=strActualMsg+"<br>A new application '"+appName+"' is added to your account.<br>";
        	  strActualMsg=strActualMsg+"Use your User ID & Password for logging into <b>Vertex Issue Management System</b>.";
          }
          else
          {
        	  strActualMsg=strActualMsg+"<br>A new Application '"+appName+"' is added to your account.<br>";
              
              strActualMsg=strActualMsg+"Use the following User ID & Password for logging into <b>Vertex Issue Management System</b>. For your privacy and security, please change your password when you log " +
              							"in for the first time.";
              strActualMsg=strActualMsg+"<br><b>User ID:  </b> <font style='text-decoration:none;cursor:none'> "+userId+"</font><br><b>Password:  </b>  "+pwd;
          }
          strActualMsg=strActualMsg+"<br><br><p align='left'>Thank you.<br>Vertex Computer Systems<br>Hyderabad<br>India</p></font>";
      	  bodyPart.setDataHandler(new DataHandler(strActualMsg,contentType));
         }
         catch(Exception exception) 
         {
         	logger.info("VIMSApplicationMail.getFileBodyPart()");
          	logger.error(exception);
         }
         return bodyPart;
   }
                   
    /* The getFormattedTable() return the data to be
       *  embedded into message body
       * 
       *  @param details of HashMap type
       * @return String
    */
      
   /* public static String getFormattedTable(HashMap details) {
       	
       	String resultString=null;
       	String strAssigned=null;
        String strOpSystem=null;
        String strDefectStatus=null; 
	        
       	try
       	{       		
       			String fieldNames[]={"ApplicationName","Support Begin Date","Support Manager","User Id","Password"};
       			String values[]=new String[fieldNames.length];
       	
       			values[0]=(String) details.get("appName");
       			values[1]=(String) details.get("supBegDate");
       			values[2]=(String) details.get("supMgr");
       			values[3]=(String) details.get("userId");
       			values[4]=(String) details.get("pwd");
       			
       		  
       		    String strTableMain="<table border=\"2\" width=\"80%\">"+
                "<tr><td align=\"center\" bgcolor=\"#4b95db\" colspan=\"2\"><b>Details</b></td></tr>";
       		  
 		    	String strRow="<tr><td bgcolor=\"#e5f3fd\">";
       		  
       		    		StringBuffer strTableDesign=new StringBuffer(strTableMain);
       		    		
       		    		for(int cnt=0;cnt<fieldNames.length;cnt++) 
       		    		{
       		    			strTableDesign.append(strRow+fieldNames[cnt]+"</td>");
       		    			strTableDesign.append("<td width='50%' bgcolor=\"#e5f3fd\">"+values[cnt]+"</td></tr>");
       		    			
       		    		}
       		    		
       		    		strTableDesign.append("</table>");
                        
       	                resultString=new String(strTableDesign);
       	}
       catch(Exception exception) 
       {
         	 System.out.println("Exception raised in getFormattedTable ()"+exception);
       }
       	  
       	  return resultString;
       }
*/
    
}	
