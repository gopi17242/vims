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
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
public class VIMSMail 
{

    public static boolean sendMail(HashMap details,String strContextPath, String strAction) {
    
    	String strReciever=null;
    	String strMessage=null;
    	String strSender=null;
     	Properties props=null;
    	Session session=null;
    	MimeMessage message=null;
    	
    	Connection connection=null;
    	CallableStatement callableStatement=null;
    	ResultSet resultset=null;
    	
    	String strMailServer=null;
    	String strVIMSIssueAssignmentPath;
    	strVIMSIssueAssignmentPath=strContextPath+"/VIMSMailLogin.do?issueId="+details.get("ID");
    	InternetAddress sender=null;
    	InternetAddress reciever=null;
    	InternetAddress ccreciever=null;
    	
    	Multipart multiMsg=null;
  	   	
	    try {	
    	
	      strReciever=(String)details.get("reciever");
	      System.out.println("==============In VIMS Mail=======strReciever============"+strReciever);
	      strSender=(String)details.get("sender");
	      System.out.println("==============In VIMS Mail=======strSender============"+strSender);
	      //System.out.println("=============details in Mail =============="+details);
	      //System.out.println("=============strAction in Mail=============="+strAction);
    	  props=new Properties();
    	  
    	  //System.out.println("==================strContextPath==================="+strContextPath);
    	   
    	  connection=DBConnection.createConnection();
    	  callableStatement=connection.prepareCall("{CALL vims_user.USP_Get_Mail_Server(?,?)}");
    	  callableStatement.setString(1,"0");
    	  callableStatement.setString(2,"1");
    	  callableStatement.execute();
    	  resultset=callableStatement.getResultSet();
    	  while(resultset.next())
    	  {
    		  strMailServer=resultset.getString(2);
    		  System.out.println("=========Mail Server==========="+strMailServer);
    	  }
    	  
    	  callableStatement.close();
    	  resultset.close();
    	  
    	  props.put("mail.smtp.host",strMailServer);
    	  
    	  //props.put("mail.smtp.host","clev.vertexcs.com");
    	  
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
	     	
	     	//message.addRecipients(Message.RecipientType.TO,reciever);
	     	
	     	     // set the subject of mail
	     	if(details.get("title")==null && "".equalsIgnoreCase((String) details.get("title")))
	     	{
	     		 message.setSubject(details.get("ID")+"-VIMS Defect-Info Mail");
	     	}
	     	else
	     	{
	     	 message.setSubject(details.get("ID")+"-"+details.get("title")+"-"+details.get("applicationName"));
	     	}   
	     	    
	     	 // Create multipart object 
	     	        
	     	     multiMsg=new MimeMultipart();
	     	        
  	     	 
	     	    // Mail body has been set using an html file
	     	     
	     	      BodyPart object=new MimeBodyPart();
	     	     
	     	       
	     	      object=getFileBodyPart(details, strVIMSIssueAssignmentPath,strAction,strContextPath);
	     	             
	     	      multiMsg.addBodyPart(object);
	     	     
	     	      // Image has been included into message body
	     	     
	     	      //BodyPart image=new MimeBodyPart();
	     	     
	     	     //image=getImageBodyPart("D:/VIMS-WorkFolder/Engineering/Source_Code/I_VIMSD/VIMS/Web/web-test/images/vertexlogo.gif","text/image"); 
	     	        
	     	     //multiMsg.addBodyPart(image);
	     	      
	     	  	      
	     	      // All parts of message are set to actual Message
	     	      	     	      
	     	      message.setContent(multiMsg);
	     	      
	     	      // Send the mail
	     	        
	     	   Transport.send(message);
	     	     System.out.println("===========Mail sent===============");
	     	      return true;  
       }
         catch(Exception exception) {
         	
         	System.out.println("Message Sending Failure"); 
         	System.out.println("Exception raised in SendMail class"+exception);
         	exception.printStackTrace();
         	return false; 
    	 }   
    }        
         
    
      public static BodyPart getFileBodyPart(HashMap details1,String strVIMSIssueAssignmentPath,String strAction, String strContextPath)     {
      
      	String fileName=null;
      	String contentType=null;
      	//String strContextPath=null;
      	String strActualMsg=null;
      	BodyPart bodyPart=null;
      	
      	FileInputStream fis=null;
      	String strFormattedTable;
      	byte data[];
      	
      	//System.out.println("----------------details1 in VIMSMAIL---------------"+details1);
	    
      	String strContent = null;
      	
      	
        try { 
      	  
          bodyPart = new MimeBodyPart();
         
          contentType="text/html"; 
          
         //strContextPath=(String)details.get("defectLinkPath");
          
      	   // System.out.println("===========strContextPath1=========="+strContxtPath);   	  
          //String fileNames[]={"D:\\VIMS-WorkFolder\\Engineering\\Source_Code\\I_VIMSD\\VIMS\\Web\\web-test\\mail\\test.jsp"};
		   //System.out.println("================"+fileNames[0]);
          
          strActualMsg=new String();
          
          
          strActualMsg="<img src='"+strContextPath+"/images/vertexLogo.png'/><br><br>";
          
          strActualMsg=strActualMsg+"<br><font color='black' size='2' style='font-family:Verdana,Arial;font-size: 70%;	font-weight: normal; color:Black;text-decoration: none;padding-left: 0px;line-height:24px;'>";
          
          strActualMsg=strActualMsg+"URL:&nbsp;&nbsp;<a href='"+strVIMSIssueAssignmentPath+"'>Vertex Issue Management System</a><br>";
          
         // System.out.println("========strActualMsg=========="+strActualMsg);
          
                    
          //System.out.println("===========name in VIMSMAIL=========="+(String)details1.get("name")); 
          
          
          strActualMsg=strActualMsg+"Dear&nbsp;&nbsp;"+((String)details1.get("name"))+":";
          
          //System.out.println("===========strContextPath3=========="+strContxtPath); 
          //System.out.println("---------strAction-------------"+strAction);
         // System.out.println("==========="+strAction.equalsIgnoreCase("changestatus"));
          //System.out.println("==========="+strAction.equalsIgnoreCase("newissue"));
          //System.out.println("==========="+strAction.equalsIgnoreCase("assign"));
          
          if(strAction.equalsIgnoreCase("newissue"))
          {
	          if(((String)details1.get("person")).equalsIgnoreCase("GroupSupervisor"))
	          {
	        	  strContent="<br> An issue is added to the system please assign.<br>"; 
	          }
	          else if(((String)details1.get("person")).equalsIgnoreCase("SupportManager"))
	          {
	        	  strContent="<br> A new issue is added to the system.<br>"; 
	          }
          }
          /*if(strAction.equalsIgnoreCase("newissue"))
          {
        	  strContent="<br> Below are the new Defect details assigned to your group <br><br>";
          }*/
          else if(strAction.equalsIgnoreCase("assign"))
          {
        	  strContent="<br> Here is an issue assigned to you. <br>";
          }
          
          else if(strAction.equalsIgnoreCase("changestatus"))
          {
        	  //System.out.println("---------------In strAction changestatus-------------");

        	  //System.out.println("------person----"+(String)details1.get("person"));
        	  //System.out.println("------Status----"+(String) details1.get("status"));

        	 // System.out.println("------person----"+(String)details1.get("person"));
        	  //System.out.println("------Status----"+(String) details1.get("status"));

         	 
        	  if(((String)details1.get("person")).equalsIgnoreCase("Internal") && (((String) details1.get("status")).equalsIgnoreCase("Rejected")))
        	  {
        		  strContent="<br> Thanks for your e-mail. Due to the following reason I am rejecting the issue. <br>";
        	      //System.out.println("-----------strContent------------"+strContent);
        	  }
        	  else if(((String)details1.get("person")).equalsIgnoreCase("Internal") && ((String)details1.get("status")).equalsIgnoreCase("Completed"))
        	  {
        	    strContent="<br> Thanks for your e-mail. Below are the details of an issue that is assigned to me. <br>";
        	    //System.out.println("-----------strContent------------"+strContent);
        	  }
        	  else if(((String)details1.get("person")).equalsIgnoreCase("Customer") && (((String) details1.get("status")).equalsIgnoreCase("Reopened")))
        	  {
                strContent="<br> Thanks for your Support on this Issue. But I would like to reopen the issue due to the below reason. <br>";
                //System.out.println("-----------strContent------------"+strContent);
        	  }
        	  else if(((String)details1.get("person")).equalsIgnoreCase("Customer") && (((String) details1.get("status")).equalsIgnoreCase("Closed")))
        	  {
                strContent="<br> Thanks for your Support on this Issue.<br>";
                //System.out.println("-----------strContent------------"+strContent);
        	  }
        	  else
        	  {
        		  strContent="<br> Below are the details of the issue that is assigned to you. <br>";
        		  //System.out.println("-----------strContent------------"+strContent);
        	  }
        	  
          }
          else if(strAction.equalsIgnoreCase("Escalation"))
          {
        	  System.out.println(" escalation mail in VIMSMAIL ");
        	  strContent="<br> Below are the details of the issue that is Escalated. <br><br>";        	 

          }
          else if(strAction.equalsIgnoreCase("Warning"))
          { 
        	  System.out.println(" Warning  mail in VIMSMAIL ");  
        	  strContent="<br> Below are the details of the issue that is supposed to complete on or before due date <br>";
          }
          else if(strAction.equalsIgnoreCase("client"))
          {
        	  strContent="<br>Use the following details to log in.<br><br>For your privacy and security,please change your password once you log in the first time.<br><br>It is advised not to share your password with anyone.<br><br>Thank You.<br>";
          }
          else if(strAction.equalsIgnoreCase("verified"))
          {
        	  strContent="<br> I verified the work done for your issue. You can check that and let me know the status.<br><br>"; 
          }
          strActualMsg=strActualMsg+strContent;          
          strActualMsg=strActualMsg+IssueMail(details1,strAction);
     
        	strActualMsg=strActualMsg+"<p align='left'>Thank you.<br>Vertex Computer Systems,<br>Hyderabad,<br>India.</p></font>";
          
      	  bodyPart.setDataHandler(new DataHandler(strActualMsg,contentType));
     }
         catch(Exception exception) {
         	
         	   System.out.println("Exception raised in getFileBodyPart"+exception);
         	   
         	   exception.printStackTrace();
         }
           return bodyPart;
   }
      
    
          
             
      /**  The getFormattedTable() return the data to be
       *  embedded into message body
       * 
       *  @param details of HashMap type
       * @return String
       */
      
      public static String IssueMail(HashMap details1,String strAction)
      {
    	  StringBuffer strbuff=new StringBuffer(); 
    	  
    	  if(strAction.equalsIgnoreCase("newissue"))
    	  { 
    		  strbuff.append("<b>Issue ID:  </b>"+details1.get("ID")+"<br><b>Severity:  </b>"+details1.get("status")+"<br><b>Description:  </b>"+details1.get("description")+"<br>");
    	  }
    	  else if(strAction.equalsIgnoreCase("assign"))
    	  {
    		  strbuff.append("<b>Issue ID:  </b>"+details1.get("ID")+"<br><b>Application Name:  </b>"+details1.get("applicationName")+"<br><b>Severity:  </b>"+details1.get("severity")+"<br><b>Priority:  </b>"+details1.get("priority")+"<br><b>Comments:  </b>"+details1.get("description")+"<br>");
    	  }
    	  else if(strAction.equalsIgnoreCase("changestatus"))
    	  {
    		  strbuff.append("<b>Issue ID:  </b>"+details1.get("ID")+"<br><b>Status:  </b>"+details1.get("status")+"<br><b>Comments:  </b>"+details1.get("description")+"<br>");
    	  }
    	  else if(strAction.equalsIgnoreCase("Escalation"))
    	  {
    		  strbuff.append("<b>Application Name:  </b>"+details1.get("applicationName")+"<br><b>Issue ID:  </b>"+details1.get("ID")+"<br><b>Posted Date:  </b>"+details1.get("postedDate")+"<br><b>Due Date:  </b>"+details1.get("dueDate")+"<br>");
    	  }
    	  else if(strAction.equalsIgnoreCase("Warning"))
    	  {
    		  strbuff.append("<b>Application Name:  </b>"+details1.get("applicationName")+"<br><b>Issue ID:  </b>"+details1.get("ID")+"<br><b>Posted Date:  </b>"+details1.get("postedDate")+"<br><b>Due Date:  </b>"+details1.get("dueDate")+"<br>");
    	  }
    	  else if(strAction.equalsIgnoreCase("client"))
  		  {
    		  strbuff.append("<b>Login ID:  </b>"+details1.get("ID")+"<br><b>Password:  </b>"+details1.get("Password")+"<br>");
  		  }
    	  else if(strAction.equalsIgnoreCase("verified"))
    	  {
    		  strbuff.append("<b>Issue ID:  </b>"+details1.get("ID")+"<br><b>Application Name:  </b>"+details1.get("applicationName")+"<br><b>Title:  </b>"+details1.get("title")+"<br>"); 
    	  }
    	  
       	  return strbuff.toString();
      }
    	  
   }