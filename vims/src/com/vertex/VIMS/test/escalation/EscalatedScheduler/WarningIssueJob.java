/*
 * Author: santhosh.k
 * Creation date: 
 * File Name : WarningIssueJob.java
 * Description: It is a job class which is scheduled when the issues cross the warning date.
 * 			
 * 
*/
package com.vertex.VIMS.test.escalation.EscalatedScheduler;

import java.util.HashMap;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;

import com.vertex.VIMS.test.common.VIMSMail;
import com.vertex.VIMS.test.escalation.DAO.EscalationDAO;

public class WarningIssueJob implements Job{
  
	String strWarnAction="Warning";
  	String IssueCurrentStatus;
	HashMap hashMap=null;
  	
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException 
	{		
		 HashMap WarningMail=new HashMap();
		 String Posteddate=null;
		 boolean managerSendMailStatus = false;
		 boolean employeeSendMailStatus=false;
		try	{		      
	      Scheduler scheduler=jobExecutionContext.getScheduler();
	      JobDetail jobDetail=jobExecutionContext.getJobDetail();
	       
	      JobDataMap jobDataMap=jobDetail.getJobDataMap();
	      String strIssueId=(String)jobDataMap.getString("IssueId");
	      String ContextPath=(String)jobDataMap.getString("ContextPath"); 
	      
	      System.out.println("Issue Id in warning job is "+strIssueId); 
	      hashMap=EscalationDAO.getIssueCurrentStatus(strIssueId.trim());
	      System.out.println("Issue and employee details in warning job is :"+hashMap); 
	      if((String)hashMap.get("IssueStatus")!=null)
	      {
	    	  IssueCurrentStatus=(String)hashMap.get("IssueStatus");
		      System.out.println("Issue Current Status in job is : "+IssueCurrentStatus); 
		      if((!(IssueCurrentStatus.equalsIgnoreCase("Closed"))) || (!(IssueCurrentStatus.equalsIgnoreCase("Completed"))))
		      { 
			       WarningMail.put("reciever",jobDataMap.getString("GroupManagerMailId"));
				   WarningMail.put("name",jobDataMap.getString("GroupManagerName")+",VIMS Group Supervisor");
				   WarningMail.put("sender", jobDataMap.getString("SenderMailID"));	      
			       WarningMail.put("ID",jobDataMap.getString("IssueId"));
			       Posteddate=jobDataMap.getString("PostedDate");
			       WarningMail.put("postedDate",jobDataMap.getString("PostedDate"));
			       WarningMail.put("dueDate",jobDataMap.getString("DueDate"));
			       WarningMail.put("applicationName",jobDataMap.getString("ApplicationName"));
			       WarningMail.put("title",jobDataMap.getString("title"));
			       managerSendMailStatus= VIMSMail.sendMail(WarningMail,ContextPath,strWarnAction);	
			       
			       if(hashMap.get("EmpMailID")!=null)
			       {
			    	   WarningMail=new HashMap();
				       WarningMail.put("reciever",(String)hashMap.get("EmpMailID"));
					   WarningMail.put("name",(String)hashMap.get("EmpName"));
					   WarningMail.put("sender", jobDataMap.getString("SenderMailID"));	      
				       WarningMail.put("ID",jobDataMap.getString("IssueId"));
				       Posteddate=jobDataMap.getString("PostedDate");
				       WarningMail.put("postedDate",jobDataMap.getString("PostedDate"));
				       WarningMail.put("dueDate",jobDataMap.getString("DueDate"));
				       WarningMail.put("applicationName",jobDataMap.getString("ApplicationName"));
				       WarningMail.put("title",jobDataMap.getString("title"));
				       employeeSendMailStatus= VIMSMail.sendMail(WarningMail,ContextPath,strWarnAction);
			       }
			       

		      }
	      }
	      
	      System.out.println("Group Manager send mail status : "+managerSendMailStatus);
	      System.out.println("Employee send mail status : "+employeeSendMailStatus);
	      /*SimpleTrigger WarningTrigger=(SimpleTrigger) scheduler.getTrigger(strIssueId, "WarningIssueTriggerGroup");
	      JobDetail WarningJobDetail=scheduler.getJobDetail(strIssueId, "WarningIssueJobDetailGroup");
	      System.out.println("JobDetails "+WarningJobDetail);	      	      
	      int countTriggerred= WarningTrigger.getTimesTriggered();
	      System.out.println("countTriggerred "+countTriggerred);	     
	      if(countTriggerred==1)
	      { 
	    	  System.out.println("You are welcome ") ;
	    	  scheduler.deleteJob(strIssueId, "WarningIssueJobDetailGroup");
	    	  System.out.println("JobDetails "+scheduler.getJobDetail(strIssueId, "WarningIssueJobDetailGroup"));	    	  
	       }*/
        }
		catch (Exception exception)
		{
			exception.printStackTrace();
		} 
	}
	 
}
