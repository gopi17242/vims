/*
 * @author: santhosh.k
 * @creation date: 
 * @file name : GraphConverter.java
 * @description: 
 * 			It is the Graph converter used to create Excel Sheets and PDf files and to add contents to the files.
*/
package com.vertex.VIMS.test.reports.ReportProducer;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import de.laures.cewolf.ChartImage;

//import jxl.write.biff.*;
//import jxl.format.Colour; 



public class  GraphConverter {
	/*
	 * This method is used to store image temporarily and to add it to the excel sheet while generation of excel sheet.
	*/
	 public static void  storeAsImage(HashMap details,String contextPath) {
      	
     int index;    
     StringBuffer sb=null;
     String strImageId=null;
     byte data[];
     File file;
     FileOutputStream fileOutputStream=null;
     int length;
     String imgPath;
     ChartImage chartImage;
      	 
  	  try 
  	  {
  		  
  		  chartImage=(ChartImage)details.get("Image");
	      strImageId=(String)details.get("ImageId");
	      data=chartImage.getBytes();	     
		  file = new File(contextPath+"\\reports\\Image"+strImageId+".png");
		  fileOutputStream=new FileOutputStream(file);
		  length=data.length;         	            	  
		  fileOutputStream.write(data, 0, length);         	            	  
		  fileOutputStream.close(); 		  
       }
  	catch(Exception exception)
  	{
	    exception.printStackTrace();	   
  	}      	       
}
	 /*
		 * This method is used to create PDF File in Issues in APplication Pool(s) and specific issues in application page for specific issues of all applications
	 	*/ 
	public static byte[] storeAsPdf(HashMap details,String contextPath, String strIssueType, String strApplication) 
	{
	  String Applications[]=null;
	  int IssuesCount[]=null;	  
	  String strImageId;	
	  HashMap dataset=null;
	  Image image=null;
	  ChartImage chartImage=null;  	      
	  Document document=null;
	  Paragraph paragraph=null;	
	  FileOutputStream fos=null;
	  
	  Table table=null;
	  Table ImageTable=null;
	  Cell ApplicationCell=null;
	  Cell IssuesCell=null;
	
	  Cell newCell=null;
	  
	
	  PdfPTable PDFTable=null;	  
	  PdfPCell PDFCell=null;
	  PdfPCell ApplicationPdfCell=null;
	  PdfPCell IssuesPdfCell=null;
	  PdfPCell newPdfCell=null;
	  try {
  		   chartImage=(ChartImage)details.get("Image"); 
  		   strImageId=(String)details.get("ImageId"); 
  		   
		   dataset=(HashMap)details.get("IssuesAndApplicationList");
		  
		   Applications=(String[])dataset.get("ApplicationNames");
		   IssuesCount=(int[])dataset.get("IssuesList");
		   if(chartImage!=null)
		   {
			   image=Image.getInstance(chartImage.getBytes()); 
		   }
		   
		   document = new Document(PageSize.B4, 50, 50, 50, 50); 
		   if(strImageId.equalsIgnoreCase("100"))
		   {
			   fos=new FileOutputStream(contextPath+"//reports//Pdf-100.pdf");      	       
		   }
		   else
		   {
			   fos=new FileOutputStream(contextPath+"//reports//Pdf-"+strImageId+".pdf");      	       
		   }

		   PdfWriter.getInstance(document,fos);      	  
	       document.open();  
	       //
	       if(strApplication.equalsIgnoreCase("allApps"))
	       {
	    	   if(strIssueType.equalsIgnoreCase("All Issues"))
	    	   {
	    		   document.add(new Paragraph("Vertex Issue Management System"));
		    	   document.add(new Paragraph("Report Title:Issues in all Applications"));
		    	   document.add(new Paragraph("Created on  :"+getCurrentDate()));
	    		
	    	   }
	    	   else 
	    	   {
	    		   document.add(new Paragraph("Vertex Issue Management System"));
		    	   document.add(new Paragraph("Report Title:"+strIssueType+" Issues in all Applications"));
		    	   document.add(new Paragraph("Created on  :"+getCurrentDate()));	    		  
	    	   }	    	   
	       }
	       else
	       {
	    	   if(strIssueType.equalsIgnoreCase("All Issues"))
	    	   {
	    		   document.add(new Paragraph("Vertex Issue Management System"));
		    	   document.add(new Paragraph("Report Title:Issues in all Applications"));
		    	   document.add(new Paragraph("Created on  :"+getCurrentDate()));	    		 
	    	   }
	    	   else
	    	   {
	    		   document.add(new Paragraph("Vertex Issue Management System"));
		    	   document.add(new Paragraph("Report Title: "+strIssueType+" Issues in "+strApplication));
		    	   document.add(new Paragraph("Created on  :"+getCurrentDate()));	    		                          
	    	   }
	    	  
	       }	  
		   table = new Table(2);			  
		   table.setBorderWidth(1);			 
		   table.setWidth(60);
		   table.setWidths(new int[]{3,1});
		   table.setAlignment("left");  
		  
		   ApplicationCell = new Cell("Application Name");		 
		   ApplicationCell.setHeader(true);
		   ApplicationCell.setColspan(1);		  
		   table.addCell(ApplicationCell);
		   
		   if(strIssueType.equalsIgnoreCase("All Issues"))
		   {
			   IssuesCell = new Cell(strIssueType);
		   }
		   else
		   {
			   IssuesCell = new Cell(strIssueType+" Issues");
		   }
		 
		   IssuesCell.setHeader(true);
		   IssuesCell.setColspan(1);
		   table.addCell(IssuesCell);
		   table.endHeaders();
		  	  
		  for(int count=0;count<Applications.length;count++)
		  {
			  newCell=new Cell(Applications[count]);			  
			  table.addCell(newCell);			    
			  newCell=new Cell(IssuesCount[count]+"");			    
			  table.addCell(newCell);				 
		  } 	  	   
	  	if(image!=null)
	  	{
			  document.add(image);		  
	  	}

		  document.add(table);		 
		  document.close(); 
		  
		  File file=new File(contextPath+"//reports//Pdf-"+strImageId+".pdf");		  
		  FileInputStream fis=new FileInputStream(file);
	  
	    byte targetData[]=new byte[fis.available()];	    
	    fis.read(targetData);	   
	    fis.close();
	    
	    boolean status=file.delete();	      
	    return targetData;
	  }
	    catch(Exception exception) 
		{
	  	  exception.printStackTrace();
	  	  return null;
		}		 
 }
	/*
	 * This method is used to create Excel Sheet in Issues in APplication Pool(s) and specific issues in application page for specific issues of all applications
 	*/ 
	
	public static byte[] storeAsExcel(HashMap details,String contextPath, String strIssueType, String strApplication)
	{
		String strImageId=null;
 	    String Applications[]=null;
 	    HashMap dataset=null;
 	     
 	   WritableWorkbook writableWorkbook=null;
 	   WritableSheet writableSheet=null;
 	   WritableImage writableImage=null;
 	   WritableFont writableFont=null; 
 	   WritableCellFormat writableCellFormat=null;
 	   
 	   FileInputStream fileInputStream;
 	   File ImageFile;
 	   File ExcelFile;
 	   Label ApplicationName=null;
 	   Label Issues=null;
 	   Label IssuesCountLabel=null;
       ChartImage chartImage=null;
 	   int IssuesCount[]=null;
 	   byte targetData[];
 	   boolean ImageFilestatus;
 	   boolean ExcelFilestatus;
 	   
 	   Label titleLabel=null;
	   Label reportLabel=null;
	   Label dateLabel=null;
 	   try
 	   { 		 
 		  strImageId=(String)details.get("ImageId"); 		   
 		  dataset=(HashMap)details.get("IssuesAndApplicationList");
 		  Applications=(String[])dataset.get("ApplicationNames");
 		  if(dataset.get("IssuesList")!=null)
		   {
			   IssuesCount=(int[])dataset.get("IssuesList");
		   }
		   else if(dataset.get("SpecificIssuesList")!=null)
		   {
			   IssuesCount=(int[])dataset.get("SpecificIssuesList"); 
		   }
 		 if(!(strImageId.equalsIgnoreCase("100")))
 		 {
 	 		  GraphConverter.storeAsImage(details, contextPath);
 		 }

 		   		  
  		  writableWorkbook =Workbook.createWorkbook(new File(contextPath+"\\reports\\workbook"+strImageId+".xls"), new WorkbookSettings());  		 
  		if(!(strImageId.equalsIgnoreCase("100")))
		 {
   		  writableImage = new WritableImage(2,3,(10),20, new File(contextPath+"\\reports\\Image"+strImageId+".png"));
		 }

       
  		if(strApplication.equalsIgnoreCase("allApps"))
        {
      	  if(strIssueType.equalsIgnoreCase("All Issues"))
      	  {   
      		  writableSheet = writableWorkbook.createSheet("Issues in all Applications",0);
      	  }
      	  else
      	  {
      		writableSheet = writableWorkbook.createSheet(strIssueType+" Issues in all Applications",0);
      	  }
        }
  		else
  		{
  			if(strIssueType.equalsIgnoreCase("All Issues"))
        	  {   
        		  writableSheet = writableWorkbook.createSheet("Issues in all Applications",0);
        	  }
        	  else
        	  {
        		  writableSheet = writableWorkbook.createSheet(strIssueType+" Issues in all Applications",0);
        	  }
  		}
  		
  		if(writableImage!=null)
  		{
  	  		writableSheet.addImage(writableImage);
  		}

      	
  		 writableFont = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);         
         writableCellFormat = new WritableCellFormat(writableFont);
         writableCellFormat.setBackground(Colour.GRAY_25);
         writableCellFormat.setWrap(true);
         
         CellView TitleCellView=new CellView();
			CellView HeadingCellView=new CellView();
		    TitleCellView.setSize(256*50);
		    HeadingCellView.setSize(256*20);
		    
		    writableSheet.setColumnView(0, TitleCellView);
		    writableSheet.setColumnView(1, HeadingCellView);
		    writableSheet.setRowView(0,(20*20),false);
		    writableSheet.setRowView(1,(20*20),false);
		    writableSheet.setRowView(2,(20*20),false);
  		  
  		  if(strApplication.equalsIgnoreCase("allApps"))
          {
        	  if(strIssueType.equalsIgnoreCase("All Issues"))
        	  {         		  
        		  
        		  titleLabel=new Label(0,0,"Vertex Issue Management System");
        		  titleLabel.setCellFormat(writableCellFormat);
        		  
        		  reportLabel=new Label(0,1,"Report Title: Issues in all Applications");
        		  reportLabel.setCellFormat(writableCellFormat);
        		  
        		  dateLabel=new Label(0,2,"Created Date : "+getCurrentDate());
        		  dateLabel.setCellFormat(writableCellFormat);
        		  
        		  writableSheet.addCell(titleLabel);
            	  writableSheet.addCell(reportLabel);            	 
            	  writableSheet.addCell(dateLabel);
            	 
        	  }
        	  else
        	  {        		  
        		  
        		  titleLabel=new Label(0,0,"Vertex Issue Management System");
        		  titleLabel.setCellFormat(writableCellFormat);
        		  
        		  reportLabel=new Label(0,1,"Report Title : "+strIssueType+" Issues in all Applications");
        		  reportLabel.setCellFormat(writableCellFormat);
        		  
        		  dateLabel=new Label(0,2,"Created Date : "+getCurrentDate());
        		  dateLabel.setCellFormat(writableCellFormat);
        		  
        		  writableSheet.addCell(titleLabel);
            	  writableSheet.addCell(reportLabel);            	 
            	  writableSheet.addCell(dateLabel); 
        	  }
          }
          else
          {
        	  if(strIssueType.equalsIgnoreCase("All Issues"))
	    	   {        		  
        		  
        		  titleLabel=new Label(0,0,"Vertex Issue Management System");
        		  titleLabel.setCellFormat(writableCellFormat);
        		  
        		  reportLabel=new Label(0,1,"Report Title: Issues in all Applications");
        		  reportLabel.setCellFormat(writableCellFormat);
        		  
        		  dateLabel=new Label(0,2,"Created Date : "+getCurrentDate());
        		  dateLabel.setCellFormat(writableCellFormat);
        		  
        		  writableSheet.addCell(titleLabel);
            	  writableSheet.addCell(reportLabel);            	 
            	  writableSheet.addCell(dateLabel);
	    	   }
	    	   else
	    	   {	        		  
	        		  titleLabel=new Label(0,0,"Vertex Issue Management System");
	        		  titleLabel.setCellFormat(writableCellFormat);
	        		  
	        		  reportLabel=new Label(0,1,"Report Title : "+strIssueType+" Issues in all Applications");
	        		  reportLabel.setCellFormat(writableCellFormat);
	        		  
	        		  dateLabel=new Label(0,2,"Created Date : "+getCurrentDate());
	        		  dateLabel.setCellFormat(writableCellFormat);
	        		  
	        		  writableSheet.addCell(titleLabel);
	            	  writableSheet.addCell(reportLabel);            	 
	            	  writableSheet.addCell(dateLabel);       		  
	        		          	 
	    	   }
          } 	 	    
         int rowCount=2;
         Label ApplicationLabel= new Label(0,4,"Application Name");
         ApplicationLabel.setCellFormat(writableCellFormat);
         if(strIssueType.equalsIgnoreCase("All Issues"))
         {
        	  IssuesCountLabel= new Label(1,4,strIssueType);
        	  IssuesCountLabel.setCellFormat(writableCellFormat);

         }
    	 else
    	 {
    		  IssuesCountLabel= new Label(1,4,strIssueType+" Issues");
    		  IssuesCountLabel.setCellFormat(writableCellFormat);

    	 }
    	 writableSheet.addCell(ApplicationLabel);
	 	 writableSheet.addCell(IssuesCountLabel);
	 	 
	 	writableFont = new WritableFont(WritableFont.ARIAL,8, WritableFont.NO_BOLD);         
        writableCellFormat = new WritableCellFormat(writableFont);
        //writableCellFormat.setBackground(Colour.GRAY_25);
        writableCellFormat.setWrap(true); 
	    	 for(int count=0;count<Applications.length;count++)
	    	 {    	 	
	    		 rowCount=0; 	 
	    		 ApplicationName=new Label(rowCount++,count+5,Applications[count]);
	    		 ApplicationName.setCellFormat(writableCellFormat);
	    		 
	    		 Issues=new Label(rowCount,count+5,IssuesCount[count]+"");
	    		 Issues.setCellFormat(writableCellFormat);
	    		 
	   	 	  	 writableSheet.addCell(ApplicationName);
	   	 	  	 writableSheet.addCell(Issues); 
	    	 }
        	 
    	 writableWorkbook.write();        	 
    	 writableWorkbook.close();
    	 
    	 if(writableImage!=null)
    	 {
    		 ImageFile=new File(contextPath+"\\reports\\Image"+strImageId+".png");		  
    			fileInputStream=new FileInputStream(ImageFile);
    		  
    		    targetData=new byte[fileInputStream.available()];	    
    		    fileInputStream.read(targetData);	   
    		    fileInputStream.close();
    		    
    		    ImageFilestatus=ImageFile.delete();	 
    	 }
	    ExcelFile=new File(contextPath+"reports\\workbook"+strImageId+".xls");		  
		fileInputStream=new FileInputStream(ExcelFile);
	  
	    targetData=new byte[fileInputStream.available()];	    
	    fileInputStream.read(targetData);	   
	    fileInputStream.close();
	    
	    ExcelFilestatus=ExcelFile.delete();
	
    	 return targetData;
 	   }
 	 catch(Exception exception) 
 	 {
  	 	exception.printStackTrace();
  	 	return null;
 	 }
  }
	/*
	 * This method is used to create PDf file in SLA Information page and Open Issues in an Application page
 	*/ 
	public static byte[] storeSLAFileAsPdf(HashMap details,String contextPath, String strIssueType, String strApplication) 
	{		
		 String strImageId=null;	
		  ArrayList arrayList=null;
		  Image image=null;
		  ChartImage chartImage=null;  	      
		  Document document=null;
		  Paragraph paragraph=null;		
		  
		   Table table=null;
		   Cell[] ApplicationDetails= new Cell[8];
		   
		   Cell[] ApplicationName =null ;
		   Cell[] IncidentId= null;
		   Cell[] IncidentTitle= null;
		   Cell[] InctStatus= null;
		   Cell[] PostedDate=null;
		   Cell[] DueDate= null;
		   Cell[] ClosedDate= null;
		   Cell[] Severity=null;
		   Cell newCell=null;
		  	
		  try {
			  if(details.get("Image")!=null && details.get("ImageId")!=null)
			  {
				  chartImage=(ChartImage)details.get("Image"); 
				  strImageId=(String)details.get("ImageId");
				  image=Image.getInstance(chartImage.getBytes()); 
			  }
			  
	  		   arrayList=(ArrayList)details.get("IssuesAndApplicationList");
			   int size=arrayList.size();
			   
			    IncidentId= new Cell[size];
			    ApplicationName =new Cell[size];			    
			    IncidentTitle= new Cell[size];
			    InctStatus= new Cell[size];
			    PostedDate=new Cell[size];
			    Severity=new Cell[size];
			    DueDate= new Cell[size];
			    ClosedDate= new Cell[size];
			   document = new Document(PageSize.B2, 50, 50, 50, 50);
			   //document.setFooter(new HeaderFooter(new Phrase("This is a header."), false)); 			 
			  
			   FileOutputStream fos=new FileOutputStream(contextPath+"//reports//Pdf-"+strImageId+".pdf");      	       
			   PdfWriter.getInstance(document,fos);			  
		       document.open();   
		       if(strIssueType.equalsIgnoreCase("SLA"))
		       {
		    	   document.add(new Paragraph("Vertex Issue Management System"));
		    	   document.add(new Paragraph("Report Title: All Application Details"));
		    	   document.add(new Paragraph("Created on  :"+getCurrentDate()));
		       }
		       else
		       {
		    	   document.add(new Paragraph("Vertex Issue Management System"));
		    	   document.add(new Paragraph("Report Title: Open Issues in "+strApplication));
		    	   document.add(new Paragraph("Created on  :"+getCurrentDate()));
		       }  
			   table = new Table(8);			  
			   table.setBorderWidth(1);			  
			  table.setWidth(100);
			   
			   table.setAlignment("left");  
		       
			   
			   ApplicationDetails[0]= new Cell("Incident ID");
			   ApplicationDetails[0].setWidth("10%");
			   
			   ApplicationDetails[1] = new Cell("Application Name");	
			   ApplicationDetails[1].setWidth("30%");
			   
			   ApplicationDetails[2]= new Cell("Title");
			   ApplicationDetails[1].setWidth("30%");
			   
			   ApplicationDetails[3]= new Cell("Status");
			   ApplicationDetails[1].setWidth("15%");
			   
			   ApplicationDetails[4]= new Cell("Posted Date");
			   ApplicationDetails[1].setWidth("15%");
			   
			   ApplicationDetails[5]=new Cell("Severity");
			   ApplicationDetails[1].setWidth("15%");
			   
			   ApplicationDetails[6]= new Cell("Target Date");
			   ApplicationDetails[1].setWidth("15%");
			   
			   ApplicationDetails[7]= new Cell("Closed Date");
			   ApplicationDetails[1].setWidth("15%");
			   
			  for(int index=0;index<ApplicationDetails.length;index++)
			  {
				  ApplicationDetails[index].setHeader(true);
				  ApplicationDetails[index].setColspan(1);
				  table.addCell(ApplicationDetails[index]);
			  }			   
			   table.endHeaders();			   
		  
			  for(int count=0;count<arrayList.size();count++)
			  {
				  HashMap ApplicationDetailsHashMap=(HashMap)arrayList.get(count);
				  
				  ApplicationName[count]=new Cell((String)ApplicationDetailsHashMap.get("APPLICATION_NAME"));
				  IncidentId[count]=new Cell((String)ApplicationDetailsHashMap.get("INCIDENT_ID"));
				  IncidentTitle[count]=new Cell((String)ApplicationDetailsHashMap.get("INCIDENT_TITLE"));
				  InctStatus[count]=new Cell((String)ApplicationDetailsHashMap.get("INCT_STATUS"));
				  PostedDate[count]=new Cell((String)ApplicationDetailsHashMap.get("POSTED_DATE"));
				  DueDate[count]=new Cell((String)ApplicationDetailsHashMap.get("DUE_DATE"));
				  ClosedDate[count]=new Cell((String)ApplicationDetailsHashMap.get("CLOSED_DATE"));
				  Severity[count]=new Cell((String)ApplicationDetailsHashMap.get("SEVERITY"));
				  
				  table.addCell(IncidentId[count]);
				  table.addCell(ApplicationName[count]);
				  table.addCell(IncidentTitle[count]);
				  table.addCell(InctStatus[count]);
				  table.addCell(PostedDate[count]);
				  table.addCell(Severity[count]);
				  table.addCell(DueDate[count]);
				  table.addCell(ClosedDate[count]);					  
			  }
			  if(details.get("Image")!=null && details.get("ImageId")!=null)
			  {
				  document.add(image);	
			  }
			  document.add(table);
			  
			  document.close(); 
			  
			  File file=new File(contextPath+"//reports//Pdf-"+strImageId+".pdf");		  
			  FileInputStream fis=new FileInputStream(file);
		  
		      byte targetData[]=new byte[fis.available()];	    
			  fis.read(targetData);	   
			  fis.close();
		    
		      boolean status=file.delete();	      
		      return targetData;
		  }
		    catch(Exception exception) 
			{
		  	  exception.printStackTrace();
		  	  return null;
			}		 
	}
	/*
	 * This method is used to create excel Sheet in SLA Information page and Open Issues in an Application page
 	*/ 
	public static byte[] storeSLAFileAsExcel(HashMap details,String contextPath, String strIssueType, String strApplication)
	{

		String strImageId=null;
 	    String Applications[];
 	    HashMap dataset;
 	   Label[] ApplicationDetails= null;
 	   ArrayList arrayList=null;
 	   
 	   WritableWorkbook writableWorkbook=null;
 	   WritableSheet writableSheet=null;
 	   WritableImage writableImage=null;
 	   WritableFont writableFont=null; 
 	   WritableCellFormat writableCellFormat=null;
 	   
 	   FileInputStream fileInputStream;
 	   File ImageFile;
 	   File ExcelFile;
 
 	   byte targetData[];
 	   boolean ImageFilestatus;
 	   boolean ExcelFilestatus;
 	   
 	  Label titleLabel=new Label(0,0,"Vertex Issue Management System");
	   Label reportLabel=new Label(0,1,"Report Title : Open Issues in "+strApplication);
	   Label dateLabel=new Label(0,2,"Created Date : "+getCurrentDate());
 	   
 	   try
 	   {
		   arrayList=(ArrayList)details.get("IssuesAndApplicationList");
		   int size=arrayList.size();
		   
		   ApplicationDetails= new Label[8];
 		
 		  if(details.get("ImageId")!=null)
 		  {
 			 GraphConverter.storeAsImage(details, contextPath); 
 			 strImageId=(String)details.get("ImageId"); 
 		  }
 		 	  
  		  writableWorkbook =Workbook.createWorkbook(new File(contextPath+"\\reports\\workbook"+strImageId+".xls"), new WorkbookSettings());  		 
          
          writableSheet = writableWorkbook.createSheet("Open Issues in "+strApplication,0);
         
          writableFont = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);         
          writableCellFormat = new WritableCellFormat(writableFont);
          writableCellFormat.setBackground(Colour.GRAY_25);
          writableCellFormat.setWrap(true);
          
  			CellView TitleCellView=new CellView();
  			CellView HeadingCellView=new CellView();
  		    TitleCellView.setSize(256*50);  		    
  		    
  		    writableSheet.setColumnView(0, TitleCellView);
  		    for(int index=1;index<ApplicationDetails.length;index++)
  		    {
  		    	if(index==1 || index==2)
  		    	{
  		    		HeadingCellView.setSize(256*35);
  		    		writableSheet.setColumnView(index, HeadingCellView);
  		    	}
  		    	else
  		    	{
  		    		HeadingCellView.setSize(256*12);
  		    		writableSheet.setColumnView(index, HeadingCellView);
  		    	}
  	  		    
  		    }

  		  writableSheet.setRowView(0,(20*20),false);
		    writableSheet.setRowView(1,(20*20),false);
		    writableSheet.setRowView(2,(20*20),false);
		    
		  titleLabel.setCellFormat(writableCellFormat);
		  reportLabel.setCellFormat(writableCellFormat);
		  dateLabel.setCellFormat(writableCellFormat);
         
        	  writableSheet.addCell(titleLabel);
        	  writableSheet.addCell(reportLabel);        	  
        	  writableSheet.addCell(dateLabel);
        	  
        
          writableImage = new WritableImage(8,4,10,20, new File(contextPath+"\\reports\\Image"+strImageId+".png"));
         
          if(details.get("ImageId")!=null)
 		  {
        	  writableSheet.addImage(writableImage);
 		  }      	
         
         
         int columnCount=0; 
         ApplicationDetails[0]= new Label(0,4,"Incident ID");
         ApplicationDetails[0].setCellFormat(writableCellFormat);

      
         ApplicationDetails[1]= new Label(1,4,"Application Name"); 
         ApplicationDetails[1].setCellFormat(writableCellFormat);

                           
         ApplicationDetails[2]= new Label(2,4,"Title");
         ApplicationDetails[2].setCellFormat(writableCellFormat);
         
         ApplicationDetails[3]= new Label(3,4,"Status");
         ApplicationDetails[3].setCellFormat(writableCellFormat);
         
         ApplicationDetails[4]= new Label(4,4,"Posted date");
         ApplicationDetails[4].setCellFormat(writableCellFormat);
         
         ApplicationDetails[5]= new Label(5,4,"Severity");
         ApplicationDetails[5].setCellFormat(writableCellFormat);
         
         ApplicationDetails[6]= new Label(6,4,"Target Date");
         ApplicationDetails[6].setCellFormat(writableCellFormat);
         
         ApplicationDetails[7]= new Label(7,4,"Closed Date");
         ApplicationDetails[7].setCellFormat(writableCellFormat);
          for (int i=0;i<ApplicationDetails.length;i++)
         {
        	 writableSheet.addCell(ApplicationDetails[i]);        	
         }
          writableFont = new WritableFont(WritableFont.ARIAL,8, WritableFont.NO_BOLD);         
          writableCellFormat = new WritableCellFormat(writableFont);         	
          writableCellFormat.setWrap(true);  
          //writableCellFormat.setShrinkToFit(true);
    	 for(int count=0;count<arrayList.size();count++)
    	 {    	 	
    		 columnCount=0; 	
    		 HashMap hashMap=(HashMap)arrayList.get(count); 
    		 int rowCount = count+5;
    		 ApplicationDetails[0]=new Label(columnCount++,rowCount,(String)hashMap.get("INCIDENT_ID"));
    		 ApplicationDetails[0].setCellFormat(writableCellFormat);
    		 
    		 ApplicationDetails[1]=new Label(columnCount++,rowCount,(String)hashMap.get("APPLICATION_NAME")); 
    		 ApplicationDetails[1].setCellFormat(writableCellFormat);

    		 ApplicationDetails[2]=new Label(columnCount++,rowCount,(String)hashMap.get("INCIDENT_TITLE"));
    		 ApplicationDetails[2].setCellFormat(writableCellFormat);
    		 
    		 ApplicationDetails[3]=new Label(columnCount++,rowCount,(String)hashMap.get("INCT_STATUS"));
    		 ApplicationDetails[3].setCellFormat(writableCellFormat);
    		 
    		 ApplicationDetails[4]=new Label(columnCount++,rowCount,(String)hashMap.get("POSTED_DATE"));
    		 ApplicationDetails[4].setCellFormat(writableCellFormat);
    		 
    		 ApplicationDetails[5]=new Label(columnCount++,rowCount,(String)hashMap.get("SEVERITY"));
    		 ApplicationDetails[5].setCellFormat(writableCellFormat);
    		 
    		 ApplicationDetails[6]=new Label(columnCount++,rowCount,(String)hashMap.get("DUE_DATE"));
    		 ApplicationDetails[6].setCellFormat(writableCellFormat);
    		 
    		 ApplicationDetails[7]=new Label(columnCount++,rowCount,(String)hashMap.get("CLOSED_DATE"));
    		 ApplicationDetails[7].setCellFormat(writableCellFormat);
    		 
    		 for(int i=0;i<7;i++)
    		 {
    			 writableSheet.addCell(ApplicationDetails[i]);    			
    		 }
    	 }
    	 
    	 writableWorkbook.write();        	 
    	 writableWorkbook.close();
    	 
    	ImageFile=new File(contextPath+"\\reports\\Image"+strImageId+".png");
	    ImageFilestatus=ImageFile.delete();	
	    
	    ExcelFile=new File(contextPath+"reports\\workbook"+strImageId+".xls");		  
		fileInputStream=new FileInputStream(ExcelFile);
	  
	    targetData=new byte[fileInputStream.available()];	    
	    fileInputStream.read(targetData);	   
	    fileInputStream.close();
	    
	    ExcelFilestatus=ExcelFile.delete();	  
	
    	 return targetData;
 	   }
 	 catch(Exception exception) 
 	 {
  	 	exception.printStackTrace();
  	 	return null;
 	 }
	}
	/*
	 * This method is used to create PDf file in Custom reports Page
 	*/ 
	public static byte[] CreateCustomReportsPDF(ArrayList CustomReportsArrayList,ArrayList columnNames, String contextPath) 
	{       
		  Document document=null;
		  Paragraph paragraph=null;	
		  Table table=null;	
		  Cell ColumnName=null;
		  try {	
			  if(columnNames.size()>=7)
			  {
				  document = new Document(PageSize.B2, 50, 50, 50, 50); 
			  }
			  else if(columnNames.size()<7)
			  {
				  document = new Document(PageSize.B4, 50, 50, 50, 50); 
			  }
			  
			   FileOutputStream fos=new FileOutputStream(contextPath+"//reports//CustomReports.pdf");      	       
			   PdfWriter.getInstance(document,fos);      	  
		       document.open();  
		       
		       document.add(new Paragraph("Vertex Issue Management System"));
	    	   document.add(new Paragraph("Report Title:Custom Reports"));
	    	   document.add(new Paragraph("Created on  :"+getCurrentDate()));
	    	  	  
			   table = new Table(columnNames.size());			  
			   table.setBorderWidth(1);	
			   table.setWidth(100);
					  
			   table.setAlignment("left");  
			   
			   for(int column=0;column<columnNames.size();column++)
			     {
			       ColumnName=new Cell(((String)columnNames.get(column)).replace("_"," "));
				   ColumnName.setHeader(true);
				   ColumnName.setColspan(1);		  
				   table.addCell(ColumnName);
			     }
			  for(int count=0;count<CustomReportsArrayList.size();count++)
			  {
				  HashMap hashMap=(HashMap)CustomReportsArrayList.get(count);
				  for(int index=0;index<hashMap.size();index++)
				  {
					  ColumnName=new Cell((String)hashMap.get((String)columnNames.get(index)));					  
					  table.addCell(ColumnName);
				  }				 
			  } 
			  document.add(table);				 
			  document.close(); 
			  
			  File file=new File(contextPath+"//reports//CustomReports.pdf");		  
			  FileInputStream fis=new FileInputStream(file);
		  
		    byte targetData[]=new byte[fis.available()];	    
		    fis.read(targetData);	   
		    fis.close();
		    
		    boolean status=file.delete();
		    return targetData;
		  }
		    catch(Exception exception) 
			{
		  	  exception.printStackTrace();
		  	  return null;
			}		 
	}
	/*
	 * This method is used to create Excel Sheet in Custom reports Page
 	*/ 
	public static byte[] CreateCustomReportsEXCEl(ArrayList CustomReportsArrayList, ArrayList columnNames, String contextPath) 
	{
 	   Label[] CustomDetails= null;
 	   
 	   WritableWorkbook writableWorkbook=null;
 	   WritableSheet writableSheet=null;
 	   WritableImage writableImage=null;
 	   WritableFont writableFont=null; 
 	   WritableCellFormat writableCellFormat=null;
 	  Date presentDate=new Date();
 	  
 	   FileInputStream fileInputStream;
 	   File ImageFile;
 	   File ExcelFile;
 	
 	   byte targetData[];
 	   boolean ImageFilestatus;
 	   boolean ExcelFilestatus;
 	   
 	  Label titleLabel=new Label(0,0,"Vertex Issue Management System");
	   Label reportLabel=new Label(0,1,"Report Title : Custom Reports ");
	   Label dateLabel=new Label(0,2,"Created Date : "+getCurrentDate());
 	   try
 	   { 
		  CustomDetails= new Label[columnNames.size()]; 		   		  
  		  writableWorkbook =Workbook.createWorkbook(new File(contextPath+"\\reports\\CustomReports.xls"), new WorkbookSettings());  		 
          Calendar calendar = Calendar.getInstance () ; 
		  writableSheet = writableWorkbook.createSheet("CustomReports "+getCurrentDate(),0); 
		  
		  writableFont = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);         
          writableCellFormat = new WritableCellFormat(writableFont);
          writableCellFormat.setBackground(Colour.GRAY_25);
          writableCellFormat.setWrap(true);
          
		CellView TitleCellView=new CellView();
		CellView HeadingCellView=new CellView();
	    TitleCellView.setSize(256*50); 	    
	    writableSheet.setColumnView(0, TitleCellView);
	    
	    writableSheet.setRowView(0,(20*20),false);
		writableSheet.setRowView(1,(20*20),false);
	    writableSheet.setRowView(2,(20*20),false);
		    
		  titleLabel.setCellFormat(writableCellFormat);
		  reportLabel.setCellFormat(writableCellFormat);
		  dateLabel.setCellFormat(writableCellFormat);
  		
          writableSheet.addCell(titleLabel);
    	  writableSheet.addCell(reportLabel);
    	  writableSheet.addCell(dateLabel);    	 
        	
             	 	    
         int columnCount=0;  
         
         int rowNum=4;
         
         for(int column=0;column<columnNames.size();column++)
	     {	
		   CustomDetails[column]= new Label(column,rowNum,((String)columnNames.get(column)).replace("_"," ")); 
		   
	       CustomDetails[column].setCellFormat(writableCellFormat); 
	      }
		   
         for (int i=0;i<CustomDetails.length;i++)
         {
        	 writableSheet.addCell(CustomDetails[i]);
         }
         
         writableFont = new WritableFont(WritableFont.ARIAL,8, WritableFont.NO_BOLD);         
         writableCellFormat = new WritableCellFormat(writableFont);        	
         writableCellFormat.setWrap(true);   
         
    	 for(int count=0;count<CustomReportsArrayList.size();count++)
    	 { 
    		 columnCount=0; 	
    		 HashMap hashMap=(HashMap)CustomReportsArrayList.get(count);    		
    		 int rowCount = count+5;
    		 
    		 for(int index=0;index<hashMap.size();index++)
	   		  {  			  
	   			 CustomDetails[index]=new Label(columnCount++,rowCount,(String)hashMap.get((String)columnNames.get(index)));
	   			CustomDetails[index].setCellFormat(writableCellFormat);
	   				   			
			    if(index!=0)
			    {
			    	HeadingCellView=new CellView();
				    TitleCellView.setSize(256*20); 
				    writableSheet.setColumnView(index, TitleCellView);
			    }

	   		  }    		 
    		 for(int i=0;i<CustomDetails.length;i++)
    		 {
    			 writableSheet.addCell(CustomDetails[i]);    			
    		 }
    	 }
    	 
    	 writableWorkbook.write();        	 
    	 writableWorkbook.close();
    	 
	    ExcelFile=new File(contextPath+"reports\\CustomReports.xls");		  
		fileInputStream=new FileInputStream(ExcelFile);
	  
	    targetData=new byte[fileInputStream.available()];	    
	    fileInputStream.read(targetData);	   
	    fileInputStream.close();
	    
	    ExcelFilestatus=ExcelFile.delete();
    	 return targetData;
 	   }
 	 catch(Exception exception) 
 	 {
  	 	exception.printStackTrace();
  	 	return null;
 	 }
	}
	/*
	 * This method is used to get the formated date 
 	*/ 
	public static String getCurrentDate()
	{		
		String strConvertedDate=new SimpleDateFormat ( "MM/dd/yyyy" ).format (Calendar.getInstance ().getTime ());
		return strConvertedDate;
	}
	
	public static byte[] SLAInformationAsPdf(HashMap details,String contextPath, String strIssueType, String strApplication) 
	{		
		  String strImageId=null;	
		  ArrayList[] arrayList=null;
		  Image image=null;
		  ChartImage chartImage=null;  	      
		  Document document=null;
		  Paragraph paragraph=null;		
		  
		  Table table1=null;
		  Table table2=null;
		  Cell[] EscalatedApplNames=null;
		  Cell[] OtherApplNames=null;	
		  
		  Cell WithinSLA=null;
		  Cell OutOfSLA=null;
		  
		  	
		  try {
			  if(details.get("Image")!=null && details.get("ImageId")!=null)
			  {
				  chartImage=(ChartImage)details.get("Image"); 
				  strImageId=(String)details.get("ImageId");
				  image=Image.getInstance(chartImage.getBytes()); 
			  }
			  
	  		   arrayList=(ArrayList[])details.get("IssuesAndApplicationList");
			   int escalatedissuessize=arrayList[0].size();
			   int otherIssuessize=arrayList[1].size();
			  
			   EscalatedApplNames= new Cell[escalatedissuessize];
			   OtherApplNames =new Cell[otherIssuessize];			    
			    
			   document = new Document(PageSize.B4, 50, 50, 50, 50);
			  
			   FileOutputStream fos=new FileOutputStream(contextPath+"//reports//Pdf-"+strImageId+".pdf");      	       
			   PdfWriter.getInstance(document,fos);			  
		       document.open();   
		       
		    	   document.add(new Paragraph("Vertex Issue Management System"));
		    	   document.add(new Paragraph("Report Title: SLA Information "));
		    	   document.add(new Paragraph("Created on  :"+getCurrentDate()));
		    	   
		    	   if(details.get("Image")!=null && details.get("ImageId")!=null)
					  {
						  document.add(image);	
					  }
		       document.add(new Paragraph("                                          "));	
			   document.add(new Paragraph("Applications out of SLA"));
			   table1 = new Table(1);			  
			   table1.setBorderWidth(1);			  
			   table1.setWidth(50);
			   table1.setAlignment("left"); 
			   
			   /*OutOfSLA=new Cell("Applications out of SLA"); 
			   
			   OutOfSLA.setHeader(true);
			   OutOfSLA.setColspan(1);
			   table1.addCell(OutOfSLA);
			   table1.endHeaders();*/
			   
			   for(int count=0;count<EscalatedApplNames.length;count++)
				  {
					  EscalatedApplNames[count]=new Cell((String)arrayList[0].get(count));
					  table1.addCell(EscalatedApplNames[count]);
				  }
			  // document.add(table);
			   document.add(table1);
		       document.add(new Paragraph("                                          "));	
		       document.add(new Paragraph("Applications within SLA"));
		       
			   table2 = new Table(1);			  
			   table2.setBorderWidth(1);			  
			   table2.setWidth(60);
			   
			   table2.setAlignment("left"); 
			   /*WithinSLA=new Cell("Applications within SLA");	  
			   
			   WithinSLA.setHeader(true);
			   WithinSLA.setColspan(1);
			   table2.addCell(WithinSLA);			 		   
			   table2.endHeaders();	*/	  
			  
			  for(int count=0;count<OtherApplNames.length;count++)
			  {
				  OtherApplNames[count]=new Cell((String)arrayList[1].get(count));
				  table2.addCell(OtherApplNames[count]);
			  }  			  
			  
			  document.add(table2);			  
			  document.close(); 
			  
			  File file=new File(contextPath+"//reports//Pdf-"+strImageId+".pdf");		  
			  FileInputStream fis=new FileInputStream(file);
		  
		      byte targetData[]=new byte[fis.available()];	    
			  fis.read(targetData);	   
			  fis.close();
		    
		      boolean status=file.delete();	      
		      return targetData;
		  }
		    catch(Exception exception) 
			{
		  	  exception.printStackTrace();
		  	  return null;
			}		 
	}
	/*
	 * This method is used to create excel Sheet in SLA Information page and Open Issues in an Application page
 	*/ 
	public static byte[] SLAInformationAsExcel(HashMap details,String contextPath, String strIssueType, String strApplication)
	{

		String strImageId=null;
 	    String Applications[];
 	    HashMap dataset;
 	    Label[] ApplicationDetails= null;
 	   ArrayList[] arrayList=null;
 	   
 	   Label[] EscalatedApplNames=null;
 	   Label[] OtherApplNames=null;	
	  
 	   Label WithinSLA=null;
 	   Label OutOfSLA=null;
 	   
 	   
 	   
 	   WritableWorkbook writableWorkbook=null;
 	   WritableSheet writableSheet=null;
 	   WritableImage writableImage=null;
 	   WritableFont writableFont=null; 
 	   WritableCellFormat writableCellFormat=null;
 	   
 	   FileInputStream fileInputStream;
 	   File ImageFile;
 	   File ExcelFile;
 
 	   byte targetData[];
 	   boolean ImageFilestatus;
 	   boolean ExcelFilestatus;
 	   
 	  Label titleLabel=new Label(0,0,"Vertex Issue Management System");
	   Label reportLabel=new Label(0,1,"Report Title : Custom Reports ");
	   Label dateLabel=new Label(0,2,"Created Date : "+getCurrentDate());
 	   try
 	   {
 		  arrayList=(ArrayList[])details.get("IssuesAndApplicationList");
		  
		   
		   int escalatedissuessize=arrayList[0].size();
		   int otherIssuessize=arrayList[1].size();
		   
		   EscalatedApplNames=new Label[escalatedissuessize];
		   OtherApplNames=new Label[otherIssuessize];
 		
 		  if(details.get("ImageId")!=null)
 		  {
 			 GraphConverter.storeAsImage(details, contextPath); 
 			 strImageId=(String)details.get("ImageId"); 
 		  }
 		 	  
  		  writableWorkbook =Workbook.createWorkbook(new File(contextPath+"\\reports\\workbook"+strImageId+".xls"), new WorkbookSettings());  		 
          writableSheet = writableWorkbook.createSheet("All Application Details",0); 
          
          writableFont = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);         
          writableCellFormat = new WritableCellFormat(writableFont);
          writableCellFormat.setBackground(Colour.GRAY_25);
          writableCellFormat.setWrap(true);
          
          CellView TitleCellView=new CellView();
			CellView HeadingCellView=new CellView();
		    TitleCellView.setSize(256*50); 	    
		    writableSheet.setColumnView(0, TitleCellView);
		    
		    writableSheet.setRowView(0,(20*20),false);
			writableSheet.setRowView(1,(20*20),false);
		    writableSheet.setRowView(2,(20*20),false);
		    
		    titleLabel.setCellFormat(writableCellFormat);
			  reportLabel.setCellFormat(writableCellFormat);
			  dateLabel.setCellFormat(writableCellFormat);
  		
		  
        	  writableSheet.addCell(titleLabel);
        	  writableSheet.addCell(reportLabel);
        	  writableSheet.addCell(dateLabel);        	
	              
          writableImage = new WritableImage(1,0,7,12, new File(contextPath+"\\reports\\Image"+strImageId+".png"));
         
          if(details.get("ImageId")!=null)
 		  {
        	  writableSheet.addImage(writableImage);
 		  }
        	
        
         int columnCount=0; 
         
         OutOfSLA=new Label(0,4,"Applications out of SLA");
         OutOfSLA.setCellFormat(writableCellFormat); 
         writableSheet.addCell(OutOfSLA);
        
         WritableFont writableFont1 = new WritableFont(WritableFont.ARIAL,8, WritableFont.NO_BOLD);         
         WritableCellFormat writableCellFormat1 = new WritableCellFormat(writableFont1);        	
         writableCellFormat1.setWrap(true);  
         
         for(int count=0;count<EscalatedApplNames.length;count++)
		  {
        	 int rowCount = count+(5);        	
        	 EscalatedApplNames[count]=new Label(columnCount,rowCount,(String)arrayList[0].get(count));
        	 EscalatedApplNames[count].setCellFormat(writableCellFormat1);
			 writableSheet.addCell(EscalatedApplNames[count]); 
		  }
         WithinSLA=new Label(0,5+(EscalatedApplNames.length)+1,"Applications within SLA");
         WithinSLA.setCellFormat(writableCellFormat); 
         writableSheet.addCell(WithinSLA);
         
         for(int count=0;count<OtherApplNames.length;count++)
		  {
        	 int rowCount = count+5+(EscalatedApplNames.length)+2;        	
             OtherApplNames[count]=new Label(columnCount,rowCount,(String)arrayList[1].get(count));
             OtherApplNames[count].setCellFormat(writableCellFormat1);
			 writableSheet.addCell(OtherApplNames[count]); 
		  }         
         
    	 
    	 writableWorkbook.write();        	 
    	 writableWorkbook.close();
    	 
    	ImageFile=new File(contextPath+"\\reports\\Image"+strImageId+".png");
	    ImageFilestatus=ImageFile.delete();	
	    
	    ExcelFile=new File(contextPath+"reports\\workbook"+strImageId+".xls");		  
		fileInputStream=new FileInputStream(ExcelFile);
	  
	    targetData=new byte[fileInputStream.available()];	    
	    fileInputStream.read(targetData);	   
	    fileInputStream.close();
	    
	    ExcelFilestatus=ExcelFile.delete();	  
	
    	 return targetData;
 	   }
 	 catch(Exception exception) 
 	 {
  	 	exception.printStackTrace();
  	 	return null;
 	 }
	}
}

	