/*
 * Author: santhosh.k
 * Creation date: 11/27/2008
 * File Name : VIMSReportsBD.java
 * Description: This is the Business Delegate Class for the Reports Page. All the methods in this class are called from the reports Controller
 * 			
 * 
*/
package com.vertex.VIMS.test.reports.BD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;

import com.vertex.VIMS.test.reports.DAO.VIMSReportsDAO;
import com.vertex.VIMS.test.reports.ReportProducer.AllIssuesInApplicationGraphConverter;
import com.vertex.VIMS.test.reports.ReportProducer.GraphConverter;
import com.vertex.VIMS.test.reports.form.CustomReportsActionForm;
 
public class VIMSReportsBD { 		
	public static String strIssueType=null;
	public static String strApplication=null;
	public static String strStatus=null;
	
	/*
	 * This method is called in order to set issues type and application type.
	*/
	public static void setSpecificIssuesInAllApplicationBD(String Application, String IssueType, String Status )
	{		
		strIssueType=IssueType;
		strApplication=Application;	
		strStatus=Status;
	}
	/*
	 * This method is called,to get issues type and application type.
	*/
	public static String[] getSpecificIssuesInAllApplicationBD()
	{
		String[] strSpecificIssueDetails={strApplication,strIssueType,strStatus}; 
		return strSpecificIssueDetails;		
	}
	/*
	 * This method is used to get image Id while exporting to excel sheets and PDf files.
	*/
	public static String getImageId(String imgPath) {
	   String strImageId;
	   int index;
   	   StringBuffer strStringBuffer=null;
   	   try
   	   { 
   		   index = imgPath.indexOf("img");
   		   strStringBuffer=new StringBuffer();	               
           for(int count=(index+4);;count++) 
           {	               	
	            if(imgPath.charAt(count)!='&') 	            	
	            	strStringBuffer.append(imgPath.charAt(count));            
		        else 	        
		        	break;
	       }
          // System.out.println("====image id=="+strStringBuffer.toString());
	      strImageId=new String(strStringBuffer);	
	      return strImageId;
   	   }
   	   catch(Exception exception)
   	   {
   		   exception.printStackTrace();
   		   return null;
      }		
	}
	/*
	 * This method is used to retrieve the path in order to store the File
	*/
	public static String getTargetPath(HttpServletRequest request, ServletContext servletContext)
	{		
		int dotIndex;      	
      	String subPath=null; 
      	
      	try {  
                String absolutePath = servletContext.getRealPath("/").replace("\\.\\", "\\"); 
                return absolutePath;
      	  }
  	     catch(Exception exception)
  	     {      	 	
  	    	 exception.printStackTrace();
	         return null;      	 	 
  	      }
      }
	/*
	 * This method is used to check the type of file based on the selection in order to export and to download that file. 
	*/
	public static void FileType(String fileType, HttpServletResponse response, HashMap hashMap, String strTargetpath, String strIssueType, String strApplication)  
	{
		System.out.println("strIssueType in Reports BD class is : "+strIssueType+" ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^6");
		 byte targetData[]=null;
		 if(fileType.equalsIgnoreCase("Export to PDF"))
    	   {          	   	
    	   	   response.setContentType("application/pdf");
    	   	   response.setHeader("Content-Disposition", "attachment; filename=" +"GraphInfo.pdf");
    	   	   if(strIssueType.equalsIgnoreCase("all"))
    	   	   {
    	   		targetData=AllIssuesInApplicationGraphConverter.storeAsPdf(hashMap,strTargetpath,strIssueType,strApplication);
    	   	   }
    	   	   else if(strIssueType.equalsIgnoreCase("SLA") )
 	   	       {
        	   	   targetData=GraphConverter.SLAInformationAsPdf(hashMap,strTargetpath,strIssueType,strApplication);//for storing at server side 
 	   	       } 
	    	   	else if(strIssueType.equalsIgnoreCase("Home"))
	 	   	   {
	     	   	   targetData=GraphConverter.storeSLAFileAsPdf(hashMap,strTargetpath,strIssueType,strApplication);//for storing at server side 
	 	   	   }
    	   	   else
    	   	   {
        	   	   targetData=GraphConverter.storeAsPdf(hashMap,strTargetpath,strIssueType,strApplication);//for storing at server side
    	   	   }
    	   }
    	    else if(fileType.equalsIgnoreCase("Export to EXCEL"))
    	    {          	    	
    	    	response.setContentType("application/xls");
    	    	response.setHeader("Content-Disposition", "attachment; filename=" +"GraphInfo.xls");
    	    	if(strIssueType.equalsIgnoreCase("all"))
     	   	   {
    	    		targetData=AllIssuesInApplicationGraphConverter.storeAsExcel(hashMap,strTargetpath,strIssueType,strApplication);
     	   	   }
    	    	else if(strIssueType.equalsIgnoreCase("SLA"))
  	   	       {
          	   	   targetData=GraphConverter.SLAInformationAsExcel(hashMap,strTargetpath,strIssueType,strApplication);//for storing at server side
  	   	       } 
    	    	else if(strIssueType.equalsIgnoreCase("Home"))
     	   	   {
         	   	   targetData=GraphConverter.storeSLAFileAsExcel(hashMap,strTargetpath,strIssueType,strApplication);//for storing at server side 
     	   	   }
    	    	
     	   	   else
     	   	   {
         	   	   targetData=GraphConverter.storeAsExcel(hashMap,strTargetpath,strIssueType,strApplication);//for storing at server side
     	   	   }
    	    }    	    
    	    ServletOutputStream servletOutputStream;
			try {
				servletOutputStream = response.getOutputStream();
				servletOutputStream.write(targetData);
	    	    servletOutputStream.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}    	    
	}
	/*
	 * This method is used to retreive the list of applications which are in SLA and out of SLA.
	*/
	public static ArrayList[] getApplicationByIncident(String status)
	{		
		return VIMSReportsDAO.getApplicationByIncident(status); 
	}
	/*
	 * This method is called from the Reports Action.
	*/
	public static ArrayList generateCustomReportsBD(ActionForm actionForm, ArrayList arrayList) {

		return VIMSReportsDAO.generateCustomReportsDAO(actionForm,arrayList); 

	}
	/*
	 * This method is called from the Reports Action when the user clicks on the Custom Reports.
	*/
	public static ArrayList[] getCustomHomeDetailsBD() {
		return VIMSReportsDAO.getCustomHomeDetailsDAO();		
	}
	/*
	 * This method is used to check the type of file based on the selection in order to export and to download that file in Custom Reports Page. 
	*/
	public static void CustomFileType(String fileType, HttpServletResponse response,String strTargetpath,ArrayList CustomReportsArrayList, ArrayList columnNames)  
	{	
		 byte targetData[]=null;
		 if(fileType.equalsIgnoreCase("PDF"))
    	   {          	   	
    	   	   response.setContentType("application/pdf");
    	   	   response.setHeader("Content-Disposition", "attachment; filename=" +"CustomReports.pdf");    	   	   
    	   	   targetData=GraphConverter.CreateCustomReportsPDF(CustomReportsArrayList,columnNames,strTargetpath);
    	   }
		 else if(fileType.equalsIgnoreCase("EXCEL"))
 	    {          	    	
 	    	response.setContentType("application/xls");
 	    	response.setHeader("Content-Disposition", "attachment; filename=" +"CustomReports.xls"); 	    	
 	    	targetData=GraphConverter.CreateCustomReportsEXCEl(CustomReportsArrayList,columnNames,strTargetpath);
	    }
		 ServletOutputStream servletOutputStream;
			try {
				servletOutputStream = response.getOutputStream();
				servletOutputStream.write(targetData);
	    	    servletOutputStream.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}    	    
	}
	/*
	 * This method is used to get the items selected in Selected Criteria from the select criteria.
	*/
	public static ArrayList getColumnNames(String[] columnNames) {
		ArrayList alist=new ArrayList();
		alist.add("Issue_ID");
		
		for(int columnIndex=0;columnIndex<columnNames.length;columnIndex++)
		{
			alist.add(columnNames[columnIndex]);
		}
		
		return alist; 
	}
	public static ArrayList getSelectedAndSelectCriteriaBD(String[] SelectedCriteria, String[] customIdList) { 
		return VIMSReportsDAO.getSelectedAndSelectCriteriaDAO(SelectedCriteria,customIdList);
	}
	public static void setApplicationIssuesRecordsStatus(String Status) 
	{
	 strStatus=Status; 		
	}
	public static String getApplicationIssuesRecordsStatus() 
	{
	 return  strStatus;		
	}
	/*public static void setSLAInformationReportsStatus(String Status) {	
		strStatus=Status; 		
	}
	public static String getSLAInformationReportsStatus() 
	{
	 return  strStatus;		
	}*/
}

    	   	  

