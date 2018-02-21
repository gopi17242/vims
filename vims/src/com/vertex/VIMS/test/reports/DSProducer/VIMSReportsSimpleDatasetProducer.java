/*
 * @author santhosh.k
 * date : 11/27/2008
 * File Name : VIMSReportsSimpleDatasetProducer.java *
 * @description: It is a DataSetProducer for Bar Graph in Issues in Application Pool(s)
 */
package com.vertex.VIMS.test.reports.DSProducer;  

import java.io.Serializable; 
import java.util.ArrayList;
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

 
public class VIMSReportsSimpleDatasetProducer implements DatasetProducer,Serializable,CategoryItemLinkGenerator,CategoryToolTipGenerator {
	
	private String [] Applications=null;
	private int [] issuesCount=null;
	private String []ApplicationNames=null;
	private String strStatus=null;
	 	 
	  //  Constructor of   VIMSReportsSimpleDatasetProducer
	    public VIMSReportsSimpleDatasetProducer() {	    	
	    	try 
		       {	
	    		   strStatus=VIMSReportsBD.getApplicationIssuesRecordsStatus();
	    		   System.out.println("Appl Status in dataSet Producer is "+strStatus);  
		    	   HashMap IssuesAndApplicationListSDSP=null;
		    	   IssuesAndApplicationListSDSP=VIMSReportsDAO.getIssuesInApplicationDAO(strStatus);
		    	   Applications=(String[])IssuesAndApplicationListSDSP.get("ApplicationList");
		    	   issuesCount=(int[])IssuesAndApplicationListSDSP.get("IssuesList");	
		    	   ApplicationNames=(String[])IssuesAndApplicationListSDSP.get("ApplicationNames");
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
		
		 for(int i=0;i<Applications.length;i++) 
		 {			
			 defaultCategoryDataset.addValue((new Integer(issuesCount[i])).intValue(),((new Integer(issuesCount[i])).intValue()+""),ApplicationNames[i]);		 
			
		 }  
		 return defaultCategoryDataset;
	}
	/*
	 * To Generate Link on the section
	*/
	public String generateLink(java.lang.Object dataset, int series, java.lang.Object category) 
	{		
		return ApplicationNames[series];
	}
	/*
	 * To Generate Tool Tip
	*/
	public String generateToolTip(CategoryDataset dataset, int series, int item)
	{
		return (ApplicationNames[item]+"-"+dataset.getValue(series, item).intValue());
	}
}

	
	
 

