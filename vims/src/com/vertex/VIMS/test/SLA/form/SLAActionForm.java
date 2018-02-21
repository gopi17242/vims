/*
 * @author: santhosh.k
 * @creation date: November 13th, 2008 
 * @file name : SLAActionForm.java
 * @description: 
 * 			It is the Action Form Bean  of the SLA page.
*/
package com.vertex.VIMS.test.SLA.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import java.util.regex.Pattern;
public class SLAActionForm extends ActionForm
{	
	String application;
	String criticalResponseTime;
	String criticalWarningInterval;	
	String majorResponseTime;
	String majorWarningInterval;
	String minorResponseTime;
	String minorWarningInterval;
	
	int CriRes,CriWar,MajRes,MajWar,MinRes,MinWar;
	boolean criResflag,criWarflag,majResflag,majWarflag,minResflag,minWarflag; 
	
	 
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getCriticalResponseTime() {
		return criticalResponseTime;
	}
	public void setCriticalResponseTime(String criticalResponseTime) {
		this.criticalResponseTime = criticalResponseTime;
	}
	public String getCriticalWarningInterval() {
		return criticalWarningInterval;
	}
	public void setCriticalWarningInterval(String criticalWarningInterval) {
		this.criticalWarningInterval = criticalWarningInterval;
	}
	public String getMajorResponseTime() {
		return majorResponseTime;
	}
	public void setMajorResponseTime(String majorResponseTime) {
		this.majorResponseTime = majorResponseTime;
	}
	public String getMajorWarningInterval() {
		return majorWarningInterval;
	}
	public void setMajorWarningInterval(String majorWarningInterval) {
		this.majorWarningInterval = majorWarningInterval;
	}
	public String getMinorResponseTime() {
		return minorResponseTime;
	}
	public void setMinorResponseTime(String minorResponseTime) {
		this.minorResponseTime = minorResponseTime;
	}
	public String getMinorWarningInterval() {
		return minorWarningInterval;
	}
	public void setMinorWarningInterval(String minorWarningInterval) {
		this.minorWarningInterval = minorWarningInterval;
	}
	public ActionErrors validate(ActionMapping actionMapping,HttpServletRequest request)	
	{
		ActionErrors actionErrors=new ActionErrors();
		 
//		if(getApplication()==null || "".equals(getApplication())){
//			
//		} 
		criResflag=Pattern.matches("^[0-9]+$", getCriticalResponseTime());
		majResflag=Pattern.matches("^[0-9]+$", getMajorResponseTime());
		minResflag=Pattern.matches("^[0-9]+$", getMinorResponseTime());
		 
		criWarflag=Pattern.matches("^[0-9]+$", getCriticalWarningInterval());
		majWarflag=Pattern.matches("^[0-9]+$", getMajorWarningInterval());
		minWarflag=Pattern.matches("^[0-9]+$", getMinorWarningInterval());
		
		if(getApplication()!=null)
		{
			if(getApplication().equals(""))
			{				
				actionErrors.add("application", new ActionMessage("Please Select an Application",false));
			}			
		}
		else
		{			
			actionErrors.add("application", new ActionMessage("Please Select an Application",false));
		}
		
		if((getCriticalResponseTime()!=null) && (getMajorResponseTime()!=null ) && (getMinorResponseTime()!=null ) && (getCriticalWarningInterval()!=null) && (getMajorWarningInterval()!=null ) && (getMinorWarningInterval()!=null ))
		{
			if((getCriticalResponseTime().equals("") || getCriticalResponseTime().trim().length()==0) || (getMajorResponseTime().equals("") || getMajorResponseTime().trim().length()==0) || (getMinorResponseTime().equals("") || getMinorResponseTime().trim().length()==0))
			{
				actionErrors.add("ResTime", new ActionMessage("Response Time cannot be empty",false));
			}
			
			if((getCriticalWarningInterval().equals("") || getCriticalWarningInterval().trim().length()==0) || (getMajorWarningInterval().equals("") || getMajorWarningInterval().trim().length()==0) || (getMinorWarningInterval().equals("") || getMinorWarningInterval().trim().length()==0))
			{ 
				actionErrors.add("WarBefore", new ActionMessage("Warning Before cannot be empty",false));
			}
			
			 if((getCriticalResponseTime().equalsIgnoreCase("0")) || (getMajorResponseTime().equalsIgnoreCase("0")) || (getMinorResponseTime().equalsIgnoreCase("0")))
			{
				actionErrors.add("ResTime", new ActionMessage("Response Time cannot be zero",false));
			}
			 
			
			if((getCriticalWarningInterval().equalsIgnoreCase("0")) || (getMajorWarningInterval().equalsIgnoreCase("0")) || (getMinorWarningInterval().equalsIgnoreCase("0")))
			{
				actionErrors.add("WarBefore", new ActionMessage("Warning Before cannot be zero",false));
			}
			if(criResflag && majResflag && minResflag && criWarflag && majWarflag && minWarflag)
			{
				CriRes=Integer.parseInt(getCriticalResponseTime().trim());
				MajRes=Integer.parseInt(getMajorResponseTime().trim());
				MinRes=Integer.parseInt(getMinorResponseTime().trim());	
				
				CriWar=Integer.parseInt(getCriticalWarningInterval().trim());
				MajWar=Integer.parseInt(getMajorWarningInterval().trim());
				MinWar=Integer.parseInt(getMinorWarningInterval().trim());	
				
				if((CriWar>=CriRes) ||  (MajWar>=MajRes) || (MinWar>=MinRes))
				{
					actionErrors.add("WarLessThanRes", new ActionMessage("Warning Before must be less than Response Time",false));
				}
			}
			/* else
			 {
					actionErrors.add("WarLessThanRes", new ActionMessage("Response Time and Warning Before must be numeric",false));
			 }*/
		} 
			
	return actionErrors;		
	}
}
		
		
		
		
		/*
		
		
		
		
		
		if(getCriticalResponseTime()!=null )
		{
			if((getCriticalResponseTime().equals("") || getCriticalResponseTime().trim().length()==0) )
			{
			
				actionErrors.add("criticalResponseTime", new ActionMessage("Critical Response Time is required",false));
			}
			else if(getCriticalResponseTime().equalsIgnoreCase("0"))
			{
			
				actionErrors.add("criticalResponseTime", new ActionMessage("Critical Response Time cannot be Zero",false));
			}
			else if(getCriticalResponseTime().trim().length()>0)
			{
				boolean b = Pattern.matches("^[0-9]+$", getCriticalResponseTime());
				if(b==false) 
				actionErrors.add("criticalResponseTime", new ActionMessage("Critical Response Time must be numeric ",false));
			}
		}		
		else
		{			
			actionErrors.add("criticalResponseTime", new ActionMessage("Critical Response Time is required",false));
		}
		
		if(getCriticalWarningInterval()!=null )
		{
			if(getCriticalWarningInterval().equals("") || getCriticalWarningInterval().trim().length()==0)
			{				
				actionErrors.add("criticalWarningInterval", new ActionMessage("Critical Warning Before  is required",false));
			}
			else if(getCriticalWarningInterval().equalsIgnoreCase("0"))
			{
			
				actionErrors.add("criticalWarningInterval", new ActionMessage("Critical Warning Before cannot be Zero",false));
			}
			else if(getCriticalWarningInterval().trim().length()>0)
			{
				boolean b = Pattern.matches("^[0-9]+$", getCriticalWarningInterval());
				if(b==false) 
				{
					actionErrors.add("criticalWarningInterval", new ActionMessage("Critical Warning Before  must be numeric ",false));
				}				
			}			
		} 
		else
		{			
			actionErrors.add("criticalWarningInterval", new ActionMessage("Critical Warning Before  is required",false));
		}		
		
		if(getMajorResponseTime()!=null )
		{
			if(getMajorResponseTime().equals("") || getMajorResponseTime().trim().length()==0)
			{				
				actionErrors.add("majorResponseTime", new ActionMessage("Major Response Time is required",false));
			}
			else if(getMajorResponseTime().equalsIgnoreCase("0"))
			{
			
				actionErrors.add("majorResponseTime", new ActionMessage("Major Response Time cannot be Zero",false));
			}
			else if(getMajorResponseTime().trim().length()>0)
			{
				boolean b = Pattern.matches("^[0-9]+$", getMajorResponseTime());
				if(b==false) 
				{
					actionErrors.add("majorResponseTime", new ActionMessage("Major Response Time must be numeric ",false));
				}				
			}
			
		}	
		else
		{			
			actionErrors.add("majorResponseTime", new ActionMessage("Major Response Time is required",false));
		}
		
		if(getMajorWarningInterval()!=null)
		{
			if(getMajorWarningInterval().equals("") || getMajorWarningInterval().length()==0)
			{				
				actionErrors.add("majorWarningInterval", new ActionMessage("Major Warning Before  is required",false));
			}
			else if(getMajorWarningInterval().equalsIgnoreCase("0"))
			{
			
				actionErrors.add("majorWarningInterval", new ActionMessage("Major Warning Before cannot be Zero",false));
			}
			else if(getMajorWarningInterval().trim().length()>0)
			{
				boolean b = Pattern.matches("^[0-9]+$", getMajorWarningInterval());
				if(b==false) 
				{
					actionErrors.add("majorWarningInterval", new ActionMessage("Major Warning Before  must be numeric ",false));
				}
			}
		}
		else
		{
			
			actionErrors.add("majorWarningInterval", new ActionMessage("Major Warning Before is required",false));
		}
		if(getMinorResponseTime()!=null )
		{
			if(getMinorResponseTime().equals("") || getMinorResponseTime().trim().length()==0)
			{				
				actionErrors.add("minorResponseTime", new ActionMessage("Minor Response Time is required",false));
			}
			else if(getMinorResponseTime().equalsIgnoreCase("0"))
			{
			
				actionErrors.add("minorResponseTime", new ActionMessage("Minor Response Time cannot be Zero",false));
			}
			else if(getMinorResponseTime().trim().length()>0)
			{
				boolean b = Pattern.matches("^[0-9]+$", getMinorResponseTime());
				if(b==false) 
				{
					actionErrors.add("minorResponseTime", new ActionMessage("Minor Response Time must be numeric ",false));
				}				
			}
			
		}
		else
		{			
			actionErrors.add("minorResponseTime", new ActionMessage("Minor Response Time is required",false));
		} 
		
		if(getMinorWarningInterval()!=null)
		{
			if(getMinorWarningInterval().equals("") || getMinorWarningInterval().trim().length()==0)
			{				
				actionErrors.add("minorWarningInterval", new ActionMessage("Minor Warning Before  is required",false));
			}
			else if(getMinorWarningInterval().equalsIgnoreCase("0"))
			{
			
				actionErrors.add("minorWarningInterval", new ActionMessage("Minor Warning Before cannot be Zero",false));
			}
			else if(getMinorWarningInterval().trim().length()> 0)
			{
				boolean b = Pattern.matches("^[0-9]+$", getMinorWarningInterval());
				if(b==false) 
				{
					actionErrors.add("minorWarningInterval", new ActionMessage("Minor Warning Before  must be numeric ",false));
				}
			}
		}	
		else 
		{			
			actionErrors.add("minorWarningInterval", new ActionMessage("Minor Warning Before is required",false));
		}	 
		
		
		if((Pattern.matches("^[0-9]+$", getCriticalWarningInterval())!=false) && (Pattern.matches("^[0-9]+$", getCriticalResponseTime())!=false))
		{
			if((Integer.parseInt(getCriticalWarningInterval().trim()))>=(Integer.parseInt(getCriticalResponseTime().trim())))
			{
				actionErrors.add("criticalWarningInterval", new ActionMessage("Warning Before  must be less than  Response Time",false));
			}
		}
		if((Pattern.matches("^[0-9]+$", getMajorWarningInterval())!=false) && (Pattern.matches("^[0-9]+$", getMajorResponseTime())!=false))
		{
			if((Integer.parseInt(getMajorWarningInterval().trim()))>=(Integer.parseInt(getMajorResponseTime().trim())))
			{					
				actionErrors.add("majorWarningInterval", new ActionMessage("Warning Before  must be less than Response Time",false));
			}
		}
		if((Pattern.matches("^[0-9]+$", getMinorWarningInterval())!=false) && (Pattern.matches("^[0-9]+$", getMinorResponseTime())!=false))
		{
			if((Integer.parseInt(getMinorWarningInterval().trim()))>=(Integer.parseInt(getMinorResponseTime().trim())))
			{
				actionErrors.add("minorWarningInterval", new ActionMessage("Warning Before must be less than Response Time",false));
			}
					
		} 
		return actionErrors;
	}
	*/
	

