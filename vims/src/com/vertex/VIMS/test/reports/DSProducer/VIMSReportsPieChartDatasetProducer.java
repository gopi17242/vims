/*
 * @author santhosh.k    
 * date : 11/27/2008
 * File Name : VIMSReportsPieChartDatasetProducer.java 
 * @description: It is a DataSetProducer for Pie Chart in Issues in Application Pool(s)
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

 
public class VIMSReportsPieChartDatasetProducer implements DatasetProducer,Serializable,CategoryItemLinkGenerator,PieToolTipGenerator {
	
	private String [] Applications=null;
	private int [] issuesCount=null;
	private String []ApplicationNames=null;
	private String strStatus=null;
	
	
	  //  Constructor of VIMSReportsPieChartDatasetProducer
	    public VIMSReportsPieChartDatasetProducer() {	    	
	       try 
	       {
	    	   strStatus=VIMSReportsBD.getApplicationIssuesRecordsStatus();
	    	   System.out.println("Appl Status in dataSet Producer is "+strStatus);  
	    	   HashMap IssuesAndApplicationListSPCDSP=null;
	    	   IssuesAndApplicationListSPCDSP=VIMSReportsDAO.getIssuesInApplicationDAO(strStatus);
	    	   Applications=(String[])IssuesAndApplicationListSPCDSP.get("ApplicationList");
	    	   issuesCount=(int[])IssuesAndApplicationListSPCDSP.get("IssuesList");
	    	   ApplicationNames=(String[])IssuesAndApplicationListSPCDSP.get("ApplicationNames");
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
		 for(int i=0;i<Applications.length;i++) 
		 {			
			 defaultPieDataset.setValue(ApplicationNames[i],new Integer(issuesCount[i]));
		 }  
		      return defaultPieDataset;
	}
	/*
	 * To Generate Link on the section
	*/
	public String generateLink(java.lang.Object dataset, int series, java.lang.Object category) {
		// TODO Auto-generated method stub
		return ApplicationNames[series];
	}
	/*
	 * To Generate Tool Tip
	*/
	public String generateToolTip(PieDataset dataset, Comparable arg1, int series) {
		// TODO Auto-generated method stub
		return (ApplicationNames[series]+"-"+dataset.getValue(series).intValue());
	}

}
