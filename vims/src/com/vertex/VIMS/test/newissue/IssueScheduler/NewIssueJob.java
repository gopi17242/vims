package com.vertex.VIMS.test.newissue.IssueScheduler;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;

import com.vertex.VIMS.test.common.VIMSMail;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.sql.Connection;

public class NewIssueJob  implements Job {	
	 Properties props=null;
  	Session session=null;
  	MimeMessage message=null;  	
  	InternetAddress sender=null;
  	InternetAddress reciever=null;
  	String strAction="newissue";
  	
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException 
	{	   
		try	{		 
	      
	      Scheduler scheduler=jobExecutionContext.getScheduler();
	      System.out.println("Scheduler object in Job class is  "+scheduler);
	      
	      JobDetail jobDetail=jobExecutionContext.getJobDetail();
	      
	      JobDataMap jobDataMap=jobDetail.getJobDataMap();
	      String strIssueId=jobDataMap.getString("IssueId");
	      String ContextPath=jobDataMap.getString("ContextPath");
	      String strAction="Escalation";
	      
	      System.out.println("JobDataMap object "+jobDataMap);
	      System.out.println("######strIssueId%%%%%%%%%%"+strIssueId);
	      
	      HashMap SupervisorMail=new HashMap();
			
		   SupervisorMail.put("reciever",jobDataMap.getString("SupervisorMailId"));
		   SupervisorMail.put("sender", jobDataMap.getString("SenderMailID"));
	       SupervisorMail.put("name",jobDataMap.getString("SupervisorName"));
	       SupervisorMail.put("ID",jobDataMap.getString("IssueId"));
	       SupervisorMail.put("postedDate",jobDataMap.getString("PostedDate"));
	       SupervisorMail.put("dueDate",jobDataMap.getString("DueDate"));
	       SupervisorMail.put("applicationName",jobDataMap.getString("ApplicationName"));
	       
	       HashMap EmployeeMail=new HashMap();
	       
	       EmployeeMail.put("reciever",jobDataMap.getString("EmployeeMailId"));
		   EmployeeMail.put("sender", jobDataMap.getString("SenderMailID"));
	       EmployeeMail.put("name",jobDataMap.getString("EmployeeName"));
	       EmployeeMail.put("ID",jobDataMap.getString("IssueId"));
	       EmployeeMail.put("postedDate",jobDataMap.getString("PostedDate"));
	       EmployeeMail.put("dueDate",jobDataMap.getString("DueDate"));
	       EmployeeMail.put("applicationName",jobDataMap.getString("ApplicationName"));
	      
	       //VIMSMail.sendMail(SupervisorMail,ContextPath,strAction);
	      // VIMSMail.sendMail(EmployeeMail,ContextPath,strAction);
	      
	      //SimpleTrigger simpleTrigger=(SimpleTrigger) scheduler.getTrigger("simpleTrigger", "IssueTriggerGroup");
	      /*JobDetail jobDetail=scheduler.getJobDetail("jobDetail-s1", "jobDetailGroup-s1");
	      System.out.println("JobDetails "+jobDetail);
	      */
	      
	   //   int countTriggerred= simpleTrigger.getTimesTriggered();
	      //System.out.println("countTriggerred"+countTriggerred);
	     /* 
	      if(countTriggerred==1)
	      {
	    	  
	    	  System.out.println("You are welcome ") ;
	    	 // scheduler.deleteJob("jobDetail-s1", "jobDetailGroup-s1");
	    	  System.out.println("JobDetails "+scheduler.getJobDetail("jobDetail-s1", "jobDetailGroup-s1"));
	    	  
	    	 // scheduler.shutdown();
	    	  //System.out.println("Scheduler object in Job class of true condition is  "+scheduler);
	      }*/
	      
	      
	       
	      
        }
		catch (Exception exception)	{
			exception.printStackTrace();
			System.out.println("Exception raised");

		}  
		
		
	}
}