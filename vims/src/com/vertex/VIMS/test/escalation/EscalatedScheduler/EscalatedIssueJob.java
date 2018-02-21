/*
 * Author: santhosh.k
 * Creation date: 
 * File Name : EscalatedIssueJob.java
 * Description: It is a Job Class which is scheduled when a particular issue is escalated.	
*/
package com.vertex.VIMS.test.escalation.EscalatedScheduler;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;

import com.vertex.VIMS.test.common.VIMSMail;
import com.vertex.VIMS.test.escalation.DAO.EscalationDAO;
import java.util.HashMap;


public class EscalatedIssueJob  implements Job {	

  	String strEscalateAction="Escalation";	
  	HashMap hashMap=null;
  	String IssueCurrentStatus=null;
  	
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException 
	{	
		 HashMap EscalatedMail=new HashMap();		
		try	{	 	 
	      
	      Scheduler scheduler=jobExecutionContext.getScheduler();	      
	      JobDetail jobDetail=jobExecutionContext.getJobDetail();
	        
	      JobDataMap jobDataMap=jobDetail.getJobDataMap();
	      String strIssueId=jobDataMap.getString("IssueId");
	      String ContextPath=jobDataMap.getString("ContextPath");	      
	      hashMap=EscalationDAO.getIssueCurrentStatus(strIssueId);
	      IssueCurrentStatus=(String)hashMap.get("IssueStatus");
	      System.out.println("Issue Current Status in job is : "+IssueCurrentStatus); 
	      if((!(IssueCurrentStatus.equalsIgnoreCase("Closed"))) || (!(IssueCurrentStatus.equalsIgnoreCase("Completed"))))
	      {	    	  			 
			   EscalatedMail.put("reciever",jobDataMap.getString("SupportMgrMailId"));
			   EscalatedMail.put("name",jobDataMap.getString("SupportMgrName")+",VIMS Support Manager");
			   EscalatedMail.put("sender", jobDataMap.getString("SenderMailID"));	      
		       EscalatedMail.put("ID",jobDataMap.getString("IssueId"));
		       EscalatedMail.put("postedDate",jobDataMap.getString("PostedDate"));
		       EscalatedMail.put("dueDate",jobDataMap.getString("DueDate"));
		       EscalatedMail.put("applicationName",jobDataMap.getString("ApplicationName"));
		       EscalatedMail.put("title",jobDataMap.getString("title"));
		      
		       boolean supervisorSendMailStatus= VIMSMail.sendMail(EscalatedMail,ContextPath,strEscalateAction);		      
		       
		       EscalatedMail=new HashMap();
		       
		       EscalatedMail.put("reciever",jobDataMap.getString("GroupManagerMailId"));
			   EscalatedMail.put("name",jobDataMap.getString("GroupManagerName")+",VIMS Group Supervisor");
			   EscalatedMail.put("sender", jobDataMap.getString("SenderMailID"));	      
		       EscalatedMail.put("ID",jobDataMap.getString("IssueId"));
		       EscalatedMail.put("postedDate",jobDataMap.getString("PostedDate"));
		       EscalatedMail.put("dueDate",jobDataMap.getString("DueDate"));
		       EscalatedMail.put("applicationName",jobDataMap.getString("ApplicationName"));
		       EscalatedMail.put("title",jobDataMap.getString("title"));
		       
		       boolean managerSendMailStatus= VIMSMail.sendMail(EscalatedMail,ContextPath,strEscalateAction);		      

	      }
	      /*SimpleTrigger EscalatedTrigger=(SimpleTrigger) scheduler.getTrigger(strIssueId, "EscalateIssueTriggerGroup");
	      JobDetail EscalatedJobDetail=scheduler.getJobDetail(strIssueId, "EscalateIssueJobDetailGroup");
	      System.out.println("JobDetails "+EscalatedJobDetail);	      	      
	      int countTriggerred= EscalatedTrigger.getTimesTriggered();
	      System.out.println("countTriggerred "+countTriggerred);	     
	      if(countTriggerred==1)
	      { 
	    	  System.out.println("You are welcome ") ;
	    	  scheduler.deleteJob(strIssueId, "EscalateIssueJobDetailGroup");
	    	  System.out.println("JobDetails "+scheduler.getJobDetail(strIssueId, "EscalateIssueJobDetailGroup"));	    	  
	       }*/
        }
		catch (Exception exception)
		{
			exception.printStackTrace();
		} 
	}
}