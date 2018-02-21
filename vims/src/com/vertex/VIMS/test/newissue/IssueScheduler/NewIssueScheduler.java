package com.vertex.VIMS.test.newissue.IssueScheduler;

/*import java.text.DateFormat;
import java.text.SimpleDateFormat;*/

/*import java.util.GregorianCalendar;*/
import java.util.HashMap;
/*import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
*/
import java.sql.Date;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;



public class NewIssueScheduler 
{
	public static int  index=0;
	
	public static Scheduler scheduler=null;
	public static void initilizeIssueScheduler(String jobName,String triggerName, HashMap IssueAssignDetails, String strContextPath) throws SchedulerException 
	{
		System.out.println("============Issue id in NewIssueScheduler is=============== : "+jobName);
		 // Initiate a Schedule Factory
        //SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        // Retrieve a scheduler from schedule factory
        Scheduler scheduler = getScheduler();
        System.out.println("scheduler object "+scheduler);
        
        // current time
        long ctime = System.currentTimeMillis(); 
        
        // Initiate JobDetail with job name, job group, and executable job class
        JobDetail jobDetail = new JobDetail(jobName, "IssueJobDetailGroup", NewIssueJob.class);
        // Initiate SimpleTrigger with its name and group name
        SimpleTrigger simpleTrigger = new SimpleTrigger(triggerName, "IssueTriggerGroup");
        
        
        //get date in string format and parse it to date format
        System.out.println((Date)IssueAssignDetails.get("WarningDate")+" ###########");
        /*DateFormat myDateFormat = new SimpleDateFormat((String)IssueAssignDetails.get("WarningDate"));
        Date WarningDate = null;
        try 
        {
        	WarningDate = myDateFormat.parse((String)IssueAssignDetails.get("WarningDate"));
        	System.out.println("@@@@@@@@@@@@@@@@@@   "+WarningDate+"   $$$$$$$$$$$$$$$$$$$$$$$");
        }
        catch (ParseException e)
        {
            System.out.println("Invalid Date Parser Exception ");
            e.printStackTrace();
        } */


        
        // set its start up time        
        simpleTrigger.setStartTime((Date)IssueAssignDetails.get("WarningDate"));
        //simpleTrigger.setStartTime(new Date((String)IssueAssignDetails.get("WarningDate")));
        
        
        
        
        // set the interval, how often the job should run (10 seconds here) 
       // simpleTrigger.setRepeatInterval(10000);
        
        
        // set the number of execution of this job, set to 10 times. 
        // It will run 10 time and exhaust.
        simpleTrigger.setRepeatCount(0);
        // set the ending time of this job. 
        // We set it for 60 seconds from its startup time here
        // Even if we set its repeat count to 10, 
        // this will stop its process after 6 repeats as it gets it endtime by then.
        //simpleTrigger.setEndTime(new Date(ctime + 60000L));
        // set priority of trigger. If not set, the default is 5
        //simpleTrigger.setPriority(10);
        // schedule a job with JobDetail and Trigger
        
        
        
       JobDataMap jobDataMap= jobDetail.getJobDataMap();
       jobDataMap.put("IssueId", jobName);
       jobDataMap.put("SupervisorName", (String)IssueAssignDetails.get("SupervisorName"));
       jobDataMap.put("SupervisorMailId", (String)IssueAssignDetails.get("SupervisorMailId"));
       jobDataMap.put("SenderMailID", (String)IssueAssignDetails.get("SenderMailID"));
       jobDataMap.put("EmployeeName", (String)IssueAssignDetails.get("EmployeeName"));
       jobDataMap.put("EmployeeMailId", (String)IssueAssignDetails.get("EmployeeMailId"));
       jobDataMap.put("PostedDate", (String)IssueAssignDetails.get("PostedDate"));
       jobDataMap.put("DueDate", (String)IssueAssignDetails.get("DueDate"));
       //jobDataMap.put("WarningDate", (Date)IssueAssignDetails.get("WarningDate"));
       
       //jobDataMap.put("ApplicationName", (String)IssueAssignDetails.get("ApplicationName"));
       jobDataMap.put("ContextPath",strContextPath );
       
       
       
       System.out.println("================================="+jobDataMap.getString("IssueId"));
       
      //jobDataMap.put("HttpServletRequest", request);
       jobDetail.setJobDataMap(jobDataMap);
        // start the scheduler
       scheduler.scheduleJob(jobDetail, simpleTrigger);
        scheduler.start();
	}
	public static Scheduler getScheduler() throws SchedulerException
	{
		// Initiate a Schedule Factory
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        // Retrieve a scheduler from schedule factory
        
        if (scheduler==null)
        	{
        	System.out.println("in if block");
        	scheduler=schedulerFactory.getScheduler(); 
        	}
        else
        {
        	 System.out.println("in else block");
        }
        System.out.println("scheduler object "+scheduler);
        return scheduler;
	}
}

