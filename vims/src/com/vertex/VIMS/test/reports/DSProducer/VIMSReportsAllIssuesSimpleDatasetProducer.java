
/*
 * @author santhosh.k
 * date : 11/27/2008
 * File Name : VIMSReportsAllIssuesSimpleDatasetProducer.java *
 * @description: It is a DataSetProducer for Bar Graph in Issues in Specific status of all issues in an application.
 */
package com.vertex.VIMS.test.reports.DSProducer;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.vertex.VIMS.test.reports.BD.VIMSReportsBD;
import com.vertex.VIMS.test.reports.DAO.VIMSReportsDAO;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.CategoryItemLinkGenerator;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;
 
public class VIMSReportsAllIssuesSimpleDatasetProducer implements DatasetProducer,Serializable,CategoryItemLinkGenerator,CategoryToolTipGenerator{
	private  String[] details=null;	
	private String strIssueType=null;
	private String strApplication=null;
	private int [] allIssues=new int[10];
	
	private String[] IssueTypes={"Open","Assigned","Accepted","Rejected","Escalated","Closed","Completed"};
	 	 
	  // Default Constructor 	  
	    public VIMSReportsAllIssuesSimpleDatasetProducer() {	    	
	       try 
	       {	       	
	    	   HashMap SpecificIssuesApplicationListPCDSP=null;
	    	   details=VIMSReportsBD.getSpecificIssuesInAllApplicationBD();
	    	   strApplication=details[0];
	    	   strIssueType=details[1];	    	  
	        
	    	   SpecificIssuesApplicationListPCDSP=VIMSReportsDAO.getAllIssuesReports(strApplication, strIssueType);
	    	   
	    	   allIssues[0]=(Integer)SpecificIssuesApplicationListPCDSP.get("open");
	    	   allIssues[1]=(Integer)SpecificIssuesApplicationListPCDSP.get("assigned");
	    	   allIssues[2]=(Integer)SpecificIssuesApplicationListPCDSP.get("accepted");
	    	   allIssues[3]=(Integer)SpecificIssuesApplicationListPCDSP.get("rejected");
	    	   allIssues[4]=(Integer)SpecificIssuesApplicationListPCDSP.get("escalated");
	    	   allIssues[5]=(Integer)SpecificIssuesApplicationListPCDSP.get("closed");
	    	   allIssues[6]=(Integer)SpecificIssuesApplicationListPCDSP.get("Completed");
	       }
	        catch(Exception exception)
	        {	        	
	        	exception.printStackTrace();
	        }
	    	  
	    }//closing of Constructor VIMSReportsAllIssuesPieChartDatasetProducer
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
	 * To set  values to the bar Graph in order to display
	*/
	public Object produceDataset(Map arg0) throws DatasetProduceException 
	{
		DefaultCategoryDataset defaultCategoryDataset=new DefaultCategoryDataset();	

		 for(int i=0;i<IssueTypes.length;i++) 
		 {			
			 //defaultCategoryDataset.addValue(allIssues[i],(allIssues[i]+""),IssueTypes[i]);
			 defaultCategoryDataset.addValue((new Integer(allIssues[i])).intValue(),((new Integer(allIssues[i])).intValue()+""),IssueTypes[i]);
		 }  
		      return defaultCategoryDataset;
	}
	/*
	 * To Generate Link on the section
	*/
	public String generateLink(java.lang.Object dataset, int series, java.lang.Object category) 
	{
		return IssueTypes[series];
	}

	/*
	 * To Generate Tool Tip
	*/
	public String generateToolTip(CategoryDataset dataset, int series, int item)
	{
		return (IssueTypes[item]+"-"+dataset.getValue(series, item).intValue());
	}
}
