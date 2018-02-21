package com.vertex.VIMS.test.escalation.EscalatedScheduler;

/*import java.text.DateFormat;
import java.text.SimpleDateFormat;*/

/*import java.util.GregorianCalendar;*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.sql.Timestamp;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

 

public class EscalatedIssueScheduler 
{
	public static int  index=0;
	
	
	public static Scheduler scheduler=null;
	public static void initilizeIssueScheduler(String jobName,String triggerName, HashMap IssueAssignDetails, String strContextPath) throws SchedulerException 
	{		
        Scheduler scheduler = getScheduler();
        JobDetail TestWarningJobDetail=scheduler.getJobDetail(jobName, "WarningIssueJobDetailGroup");
        JobDetail TestEscalatedJobDetail=scheduler.getJobDetail(jobName, "EscalateIssueJobDetailGroup");
       // System.out.println("Checking whether already warning Job exists or not ? "+TestWarningJobDetail); 
       // System.out.println("Checking whether already Escalated Job exists or not ? "+TestEscalatedJobDetail); 
        System.out.println("scheduler object "+scheduler);
        System.out.println("send warning mail ?"+((Boolean)IssueAssignDetails.get("SendWarningMail")).booleanValue()); 
        boolean sendWarningMail=((Boolean)IssueAssignDetails.get("SendWarningMail")).booleanValue(); 
        System.out.println("mail Details in Issue Scheduler is "+IssueAssignDetails); 
        if(sendWarningMail) 
        {
        	System.out.println("inside the send warning mail loop");
        	  JobDetail warningJobDetail = new JobDetail(jobName, "WarningIssueJobDetailGroup", WarningIssueJob.class);
        	  SimpleTrigger warningSimpleTrigger = new SimpleTrigger(triggerName, "WarningIssueTriggerGroup");
        
        	warningSimpleTrigger.setStartTime((Timestamp)IssueAssignDetails.get("WarningDate"));
        	 
        	 // warningSimpleTrigger.setStartTime((new Date()));
        	  warningSimpleTrigger.setRepeatCount(0);
        	  
        	  JobDataMap warningJobDataMap= warningJobDetail.getJobDataMap();
              warningJobDataMap.put("IssueId", jobName); 
              warningJobDataMap.put("ApplicationName", (String)IssueAssignDetails.get("ApplicationName"));
              warningJobDataMap.put("PostedDate", (String)IssueAssignDetails.get("FormatedPostedDate"));
              warningJobDataMap.put("DueDate", (String)IssueAssignDetails.get("FormatedDueDate"));
              warningJobDataMap.put("SenderMailID", (String)IssueAssignDetails.get("SenderMailID"));
              warningJobDataMap.put("SupportMgrName", (String)IssueAssignDetails.get("SupportMgrName"));
              warningJobDataMap.put("SupportMgrMailId", (String)IssueAssignDetails.get("SupportMgrMailId"));
              warningJobDataMap.put("GroupManagerName", (String)IssueAssignDetails.get("GroupManagerName"));
              warningJobDataMap.put("GroupManagerMailId", (String)IssueAssignDetails.get("GroupManagerMailId"));   
              warningJobDataMap.put("title", (String)IssueAssignDetails.get("title")); 
             // warningJobDataMap.put("EmployeeName", (String)IssueAssignDetails.get("EmployeeName"));
             // warningJobDataMap.put("EmployeeMailId", (String)IssueAssignDetails.get("EmployeeMailId"));
              warningJobDataMap.put("ContextPath",strContextPath );       
              
            // System.out.println("Job Data map warningJobDataMap details  in schedular is "+warningJobDataMap);
              
              warningJobDetail.setJobDataMap(warningJobDataMap);    
              scheduler.scheduleJob(warningJobDetail, warningSimpleTrigger);        
        }
      
        JobDetail escalateJobDetail = new JobDetail(jobName, "EscalateIssueJobDetailGroup", EscalatedIssueJob.class);
        SimpleTrigger escalateSimpleTrigger = new SimpleTrigger(triggerName, "EscalateIssueTriggerGroup");
       
        
        System.out.println("Due Date is "+(Timestamp)IssueAssignDetails.get("DueDate")+" ###########");
        System.out.println("Warning Date is "+(Timestamp)IssueAssignDetails.get("WarningDate")+" ###########");
     

       
        escalateSimpleTrigger.setStartTime((Timestamp)IssueAssignDetails.get("DueDate"));
        
        //escalateSimpleTrigger.setStartTime((new Date())); 
        escalateSimpleTrigger.setRepeatCount(0);
        
       JobDataMap escalateJobDataMap= escalateJobDetail.getJobDataMap();
       escalateJobDataMap.put("IssueId", jobName); 
       escalateJobDataMap.put("ApplicationName", (String)IssueAssignDetails.get("ApplicationName"));
       //escalateJobDataMap.put("PostedDate", ((Timestamp)IssueAssignDetails.get("PostedDate")).toString());
       //escalateJobDataMap.put("DueDate", ((Timestamp)IssueAssignDetails.get("DueDate")).toString());
       escalateJobDataMap.put("PostedDate", (String)IssueAssignDetails.get("FormatedPostedDate"));
       escalateJobDataMap.put("DueDate", (String)IssueAssignDetails.get("FormatedDueDate"));
       
       escalateJobDataMap.put("SenderMailID", (String)IssueAssignDetails.get("SenderMailID"));
       escalateJobDataMap.put("SupportMgrName", (String)IssueAssignDetails.get("SupportMgrName"));
       escalateJobDataMap.put("SupportMgrMailId", (String)IssueAssignDetails.get("SupportMgrMailId"));
       escalateJobDataMap.put("GroupManagerName", (String)IssueAssignDetails.get("GroupManagerName"));
       escalateJobDataMap.put("GroupManagerMailId", (String)IssueAssignDetails.get("GroupManagerMailId"));  
       escalateJobDataMap.put("title", (String)IssueAssignDetails.get("title")); 
       //escalateJobDataMap.put("EmployeeName", (String)IssueAssignDetails.get("EmployeeName"));
       //escalateJobDataMap.put("EmployeeMailId", (String)IssueAssignDetails.get("EmployeeMailId"));
       escalateJobDataMap.put("ContextPath",strContextPath );       
      
      // System.out.println("================================="+escalateJobDataMap.getString("IssueId"));       
       
     // System.out.println("Job Data map escalateJobDataMap details  in schedular is "+escalateJobDataMap);
       
       escalateJobDetail.setJobDataMap(escalateJobDataMap);    
       scheduler.scheduleJob(escalateJobDetail, escalateSimpleTrigger);
        scheduler.start();
	}
	public static Scheduler getScheduler() throws SchedulerException
	{		
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        
        
        if (scheduler==null)
        {
        	System.out.println("scheduler object created...");
        	scheduler=schedulerFactory.getScheduler(); 
        }
        	
        else
        {
        	 System.out.println("scheduler object already exists!!");
        }
        System.out.println("scheduler object "+scheduler);
        return scheduler;
	}
}

