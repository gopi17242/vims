/*
 * @author santhosh.k
 * date : 11/27/2008
 * File Name : VIMSReportsSpecificIssuesSimpleDatasetProducer.java *
 * @description: It is a DataSetProducer for Bar Graph in Issues in Specific status of all applications.
 */
package com.vertex.VIMS.test.reports.DSProducer;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.vertex.VIMS.test.reports.BD.VIMSReportsBD;
import com.vertex.VIMS.test.reports.DAO.VIMSReportsDAO;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.CategoryItemLinkGenerator;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;
 
public class VIMSReportsSpecificIssuesSimpleDatasetProducer implements DatasetProducer,Serializable,CategoryItemLinkGenerator,CategoryToolTipGenerator{
	private String [] Applications=null;
	private int [] issuesCount=null;
	private String []ApplicationNames=null;
	
	private  String[] details=null;	
	private String strIssueType=null;
	private String strApplication=null;
	private String strStatus=null;
	private int [] allIssues=new int[10];	
	private String[] IssueTypes={"Open","Assigned","Accepted","Rejected","Escalated","Closed","Completed"};
	 	 
	  // Default Constructor of VIMSReportsSpecificIssuesSimpleDatasetProducer  
	    public VIMSReportsSpecificIssuesSimpleDatasetProducer() {	    	
	    	try 
		       {	       	
	    		   HashMap SpecificIssuesApplicationListSDSP=null;
		    	   details=VIMSReportsBD.getSpecificIssuesInAllApplicationBD();
		    	   strApplication=details[0];
		    	   strIssueType=details[1];	
		    	   strStatus=details[2];
		    	   if(strIssueType.trim().equalsIgnoreCase("all"))
    			   {
		    		   SpecificIssuesApplicationListSDSP=VIMSReportsDAO.getAllIssuesReports(strApplication, strIssueType);			    	   

			    	   allIssues[0]=(Integer)SpecificIssuesApplicationListSDSP.get("open");
			    	   allIssues[1]=(Integer)SpecificIssuesApplicationListSDSP.get("assigned");
			    	   allIssues[2]=(Integer)SpecificIssuesApplicationListSDSP.get("accepted");			    	  
			    	   allIssues[3]=(Integer)SpecificIssuesApplicationListSDSP.get("rejected");			    	  
			    	   allIssues[4]=(Integer)SpecificIssuesApplicationListSDSP.get("escalated");			    	 
			    	   allIssues[5]=(Integer)SpecificIssuesApplicationListSDSP.get("closed");			    	  
			    	   allIssues[6]=(Integer)SpecificIssuesApplicationListSDSP.get("completed");	
    			   }
		    	   else
		    	   {
		    		   SpecificIssuesApplicationListSDSP=VIMSReportsDAO.getSpecificIssuesInAllApplication(strApplication,strIssueType,strStatus);	
			    	   Applications=(String[])SpecificIssuesApplicationListSDSP.get("ApplicationList");
			    	   issuesCount=(int[])SpecificIssuesApplicationListSDSP.get("IssuesList");
			    	   ApplicationNames=(String[])SpecificIssuesApplicationListSDSP.get("ApplicationNames");
		    	   }   	   
		    	   
		       }
	        catch(Exception exception)
	        {	        	
	        	exception.printStackTrace();
	        }
	    	  
	    }//closing of Constructor VIMSReportsSimpleDatasetProducer
	   
	    /*
		 * An Unique id is assigned in order to generate the chart
		*/
	public String getProducerId() {
			return "SimpleDatasetProducer";
	}
	/*
	 * To regenerate the values  otherwise it takes the earlier values to display the chart
	*/
	public boolean hasExpired(Map arg0, Date arg1) {
		return true; 
	}
	/*
	 * To set  values to the bar Graph in order to display
	*/ 
	public Object produceDataset(Map arg0) throws DatasetProduceException 
	{
		DefaultCategoryDataset defaultCategoryDataset=new DefaultCategoryDataset();	
		if(strIssueType.trim().equalsIgnoreCase("all"))
		{
			for(int i=0;i<IssueTypes.length;i++) 
			 {			
				 defaultCategoryDataset.addValue((new Integer(allIssues[i])).intValue(),((new Integer(allIssues[i])).intValue()+""),IssueTypes[i]);
			 }  			      
		}
		else
		{
			for(int i=0;i<Applications.length;i++) 
			 {	
				 defaultCategoryDataset.addValue((new Integer(issuesCount[i])).intValue(),((new Integer(issuesCount[i])).intValue()+""),ApplicationNames[i]);
			 }  
		}
		return defaultCategoryDataset;
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
	public String generateToolTip(CategoryDataset dataset, int series, int item) {
		 if(strIssueType.trim().equalsIgnoreCase("all"))
		{
			return (IssueTypes[item]+"-"+dataset.getValue(series, item).intValue());
		}
		else
		{
			return (ApplicationNames[item]+"-"+dataset.getValue(series, item).intValue());
		}
		
	}

}
