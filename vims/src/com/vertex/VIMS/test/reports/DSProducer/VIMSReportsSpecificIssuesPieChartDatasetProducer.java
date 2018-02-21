/*
 * @author santhosh.k    
 * date : 11/27/2008
 * File Name : VIMSReportsPieChartDatasetProducer.java *
 * @description: It is a DataSetProducer for Pie Chart in Issues in Specific Status of all applications.
 */


package com.vertex.VIMS.test.reports.DSProducer; 
import java.io.Serializable;   
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map; 

import org.jfree.data.category.CategoryDataset;  
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.vertex.VIMS.test.reports.BD.VIMSReportsBD;
import com.vertex.VIMS.test.reports.DAO.VIMSReportsDAO;

import de.laures.cewolf.DatasetProduceException; 
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.CategoryItemLinkGenerator;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;
import de.laures.cewolf.tooltips.PieToolTipGenerator;

public class VIMSReportsSpecificIssuesPieChartDatasetProducer implements DatasetProducer,Serializable,CategoryItemLinkGenerator,PieToolTipGenerator{
	private String [] Applications=null;
	private int [] issuesCount=null;
	private String []ApplicationNames=null;
	
	private  String[] details=null;	
	private String strIssueType=null;
	private String strApplication=null;
	private String strStatus=null;
	private int [] allIssues=new int[10];	
	private String[] IssueTypes={"Open","Assigned","Accepted","Rejected","Escalated","Closed","Completed"};
	 	 
	 	 
	 //  Constructor of VIMSReportsSpecificIssuesPieChartDatasetProducer  
	    public VIMSReportsSpecificIssuesPieChartDatasetProducer() {	    	
	       try 
	       {	       	
	    	   HashMap SpecificIssuesApplicationListPCDSP=null;
	    	   details=VIMSReportsBD.getSpecificIssuesInAllApplicationBD();
	    	   strApplication=details[0];
	    	   strIssueType=details[1];
	    	   strStatus=details[2];
	    	   if(strIssueType.trim().equalsIgnoreCase("all"))
	    	   {
	    		   SpecificIssuesApplicationListPCDSP=VIMSReportsDAO.getAllIssuesReports(strApplication, strIssueType);	
		    	   allIssues[0]=(Integer)SpecificIssuesApplicationListPCDSP.get("open");		    	  
		    	   allIssues[1]=(Integer)SpecificIssuesApplicationListPCDSP.get("assigned");		    	  
		    	   allIssues[2]=(Integer)SpecificIssuesApplicationListPCDSP.get("accepted");		    	  
		    	   allIssues[3]=(Integer)SpecificIssuesApplicationListPCDSP.get("rejected");		    	  
		    	   allIssues[4]=(Integer)SpecificIssuesApplicationListPCDSP.get("escalated");		    	  
		    	   allIssues[5]=(Integer)SpecificIssuesApplicationListPCDSP.get("closed");		    	
		    	   allIssues[6]=(Integer)SpecificIssuesApplicationListPCDSP.get("completed");
	    	   }
	    	   else
	    	   {
	    		   SpecificIssuesApplicationListPCDSP=VIMSReportsDAO.getSpecificIssuesInAllApplication(strApplication,strIssueType,strStatus);
		    	   Applications=(String[])SpecificIssuesApplicationListPCDSP.get("ApplicationList");	    	   
		    	   issuesCount=(int[])SpecificIssuesApplicationListPCDSP.get("IssuesList");
		    	   ApplicationNames=(String[])SpecificIssuesApplicationListPCDSP.get("ApplicationNames");
	    	   }
	    	     
	       }
	        catch(Exception exception)
	        {
	        	exception.printStackTrace();
	        }
	    	  
	    }//closing of Constructor VIMSReportsPieChartDatasetProducer
    /*
	 * An Unique id is assigned in order to generate the chart
	*/
	public String getProducerId() {		
		return "PieChartDatasetProducer";
	}
	/*
	 * To regenerate the values  otherwise it takes the earlier values to display the chart
	*/
	public boolean hasExpired(Map arg0, Date arg1) {
		return true;
	}
	/*
	 * To set  values to the pie chart in order to display
	*/
	public Object produceDataset(Map arg0) throws DatasetProduceException {
		DefaultPieDataset defaultPieDataset=new DefaultPieDataset();
		if(strIssueType.trim().equalsIgnoreCase("all"))
		{
			for(int i=0;i<IssueTypes.length;i++) 
			 {			
				 defaultPieDataset.setValue(IssueTypes[i],new Integer(allIssues[i]));
			 }  
		}
		else  
		{
			for(int i=0;i<Applications.length;i++) 
			 {	
				 defaultPieDataset.setValue(ApplicationNames[i],new Integer(issuesCount[i]));
			 }  
		}
		 
	 return defaultPieDataset;
	}
	/*
	 * To Generate Link on the section
	*/
	public String generateLink(Object arg0, int arg1, Object arg2) {
		return null;
	}
	/*
	 * To Generate Tool Tip
	*/
	public String generateToolTip(PieDataset dataset, Comparable arg1, int series)
	{
		 if(strIssueType.trim().equalsIgnoreCase("all"))
		{
			 return (IssueTypes[series]+"-"+dataset.getValue(series).intValue());
		}
		else
		{
			return (ApplicationNames[series]+"-"+dataset.getValue(series).intValue());
		}
	}
}
