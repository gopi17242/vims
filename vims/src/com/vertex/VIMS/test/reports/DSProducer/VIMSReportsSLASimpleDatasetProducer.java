/*
 * @author santhosh.k
 * date : 01/04/2009
 * File Name : VIMSReportsSimpleDatasetProducer.java *
 * @description:It is a dataSetProducer class used to display Bar Graph in SLA Information Page.
 */
package com.vertex.VIMS.test.reports.DSProducer;  

import java.io.Serializable; 
//import java.util.ArrayList;
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

 
public class VIMSReportsSLASimpleDatasetProducer implements DatasetProducer,Serializable,CategoryItemLinkGenerator,CategoryToolTipGenerator {
	
	private String [] SLAType=null;
	private int [] SLACount=null;
	private String strStatus=null;
	  // Default Constructor of VIMSReportsSLASimpleDatasetProducer	  
	    public VIMSReportsSLASimpleDatasetProducer() {	    	
	    	try 
		       {
	    		   strStatus=VIMSReportsBD.getApplicationIssuesRecordsStatus();
	    		   System.out.println("Appl Status in dataSet Producer is "+strStatus); 
		    	   HashMap IssuesAndApplicationListSDSP=null;
		    	   IssuesAndApplicationListSDSP=VIMSReportsDAO.getSLAInformationReportsDAO(strStatus); 
		    	   SLAType=(String[])IssuesAndApplicationListSDSP.get("ApplicationList");
		    	   SLACount=(int[])IssuesAndApplicationListSDSP.get("IssuesList");		    	   
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
		 for(int i=0;i<SLAType.length;i++) 
		 {			
			 defaultCategoryDataset.addValue((new Integer(SLACount[i])).intValue(),((new Integer(SLACount[i])).intValue()+""),SLAType[i]);			
		 }  
		 return defaultCategoryDataset;
	}
	/*
	 * To Generate Link on the section
	*/
	public String generateLink(java.lang.Object dataset, int series, java.lang.Object category) 
	{
		return SLAType[series];
	}
	/*
	 * To Generate Tool Tip
	*/
	public String generateToolTip(CategoryDataset dataset, int series, int item)
	{
		return (SLAType[item]+"-"+dataset.getValue(series, item).intValue());	
	}
}

	
	
 

