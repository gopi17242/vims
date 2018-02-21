package com.vertex.VIMS.test.reportsTest.excelFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.log4j.Logger;

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

import org.apache.log4j.Logger;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import de.laures.cewolf.ChartImage;

public class ExcelGenerator {
	 static Logger logger=null;
	 public static byte[] generateExcelFile(HashMap details)
        {
		   logger=Logger.getLogger("Admin");
		  // System.out.println("=============1=====================");
		   WritableWorkbook writableWorkbook=null;
		   WritableSheet writableSheet=null;
		   WritableImage writableImage=null;
	 	   WritableFont writableFont=null; 
		   WritableCellFormat writableCellFormat=null;
		   
		   FileInputStream fileInputStream;  
		   File ExcelFile;
		   
		   Label titleLabel=new Label(0,0,"Vertex Issue Management System");	  
		   Label dateLabel=new Label(0,2,"Created Date : "+getCurrentDate());
		   Label reportLabel=null;
		   Label searchLabel=null;
		   
		   Logger logger=null;
		   String contextPath=null;
		   String reportTitle=null;
		   ArrayList dataset=null;
		   CellView TitleCellView=null;
		   String[] columnNames=null;
		   String[]columnSizes=null;
		   byte[] targetData=null;
		   String strSearchCriteria=null;
		   
		   File RedImageFile=null;
		   File YellowImageFile=null;
		   File GreenImageFile=null;
		  
		   
		  try
	    	{
	    		//System.out.println("=============Entered into try block=====================");
	    		contextPath=(String)details.get("path");
	    		System.out.println("----------contextPath---------------"+contextPath); 
	    		reportTitle=(String)details.get("reportTitle");
	    		columnNames=(String[])details.get("ColumnNames");
	    		columnSizes=(String[])details.get("columnSizes");
	    		dataset=(ArrayList)details.get("dataset");
	    		strSearchCriteria=(String)details.get("searchCriteria");
	     		
	    		reportLabel=new Label(0,1,"Report Title : "+reportTitle);
	    		searchLabel=new Label(0,3,strSearchCriteria.replace("<br>",", "));
	    		
	    		Label[] columnLabels=new Label[columnNames.length];
	    		
	    	   RedImageFile=new File(contextPath+"\\images\\red-btn.png");
	  		   GreenImageFile=new File(contextPath+"\\images\\green-btn.png");
	  		   YellowImageFile=new File(contextPath+"\\images\\yellow-btn.png");
	  		   
	  		   //System.out.println("--------RedImageFile------------"+RedImageFile+"------RedImageFile length-----------");
			  // System.out.println("--------GreenImageFile------------"+GreenImageFile+"--------GreenImageFile length---------");
			  // System.out.println("--------YellowImageFile------------"+YellowImageFile+"------YellowImageFile length-----------");
		    	
	    		 
				writableWorkbook =Workbook.createWorkbook(new File(contextPath+"\\reports-Test\\workbook.xls"), new WorkbookSettings());  		 
				writableSheet = writableWorkbook.createSheet(reportTitle,0); 
				writableFont = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);   
				writableCellFormat = new WritableCellFormat(writableFont);
				
	 			//writableCellFormat.setBackground(Colour.GRAY_25);
				writableCellFormat.setWrap(false);
					          
				//commented title view to apply the changes
				
	            /*TitleCellView=new CellView();
	            TitleCellView.setSize(256*30);
	            writableSheet.setColumnView(0, TitleCellView);*/
	            
	            writableSheet.setRowView(0,(20*20),false);
	  		    writableSheet.setRowView(1,(20*20),false);
	  		    writableSheet.setRowView(2,(20*20),false);
	  		    writableSheet.setRowView(3,(20*20),false);
	            
	            CellView[] HeadingCellView=new CellView[(columnNames.length)];
	            for(int index=0;index<HeadingCellView.length;index++)
	            {
	            	HeadingCellView[index]=new CellView();
	            }
	            
	            //changed start index from '1' to '0'
	            for(int colIndex=0;colIndex<HeadingCellView.length;colIndex++)
	            {
	            	HeadingCellView[colIndex].setSize(256*(Integer.parseInt(columnSizes[colIndex])));
	            	writableSheet.setColumnView(colIndex, HeadingCellView[colIndex]);
	            	//System.out.println("######################### "+colIndex);
	            }
	  		     
	            titleLabel.setCellFormat(writableCellFormat);
	  		    reportLabel.setCellFormat(writableCellFormat);
	  		    dateLabel.setCellFormat(writableCellFormat);
	            
	    		writableSheet.addCell(titleLabel);
	    	    writableSheet.addCell(reportLabel);
	    	    writableSheet.addCell(dateLabel);  
	  		    writableSheet.addCell(searchLabel);
	  		    
	  		    writableFont = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);         
				writableCellFormat = new WritableCellFormat(writableFont);
				writableCellFormat.setBackground(Colour.GRAY_25);
				writableCellFormat.setWrap(true);
	  		   
				int   index=0;
				
				for(int i=0;i<columnNames.length;i++)
				{
					//System.out.println("@@@@@@@@@ "+columnNames[i].toString()); 
				}
				
				int rowCount=0;
				for(int count=0;count<columnLabels.length;count++)
	    		{ 	
					if(columnNames[count].equalsIgnoreCase("E_mail")) 
					{
						columnLabels[count]=new Label(rowCount++,5,(columnNames[count].replace('_', '-')));  
		    			columnLabels[count].setCellFormat(writableCellFormat); 
					}
					else
					{
						columnLabels[count]=new Label(rowCount++,5,(columnNames[count].replace('_', ' '))); 
		    			columnLabels[count].setCellFormat(writableCellFormat); 
					}
	    			
	    		}
				
				for(index=0;index<columnLabels.length;index++) {
					 writableSheet.addCell(columnLabels[index]);
				}
				
				writableFont = new WritableFont(WritableFont.ARIAL,8, WritableFont.NO_BOLD);         
				writableCellFormat = new WritableCellFormat(writableFont);				
				writableCellFormat.setWrap(true);
				
				Label label=null;
				HashMap record=null;				
				int count;
				String strTemp="";
				String strImageType=null;
				for(index=0;index<dataset.size();index++)
					{
					 rowCount=0;
					 record=(HashMap)dataset.get(index);
					 for(int temp=0;temp<columnNames.length;temp++)
					 {
						 count=index+6;
						 if(record.get(columnNames[temp])==null)
						 {
							 strTemp=""; 
							 label=new Label(rowCount++,count,strTemp); 
				    		 label.setCellFormat(writableCellFormat);
				    		 writableSheet.addCell(label);
				    		 
		 				 }
							  
						 else
						 {
							 if((record.get(columnNames[temp])+"").equalsIgnoreCase("green")) 
							 {
								 //System.out.println("---------------green block-----------------------");
						         writableImage = new WritableImage(rowCount++,count,1,1,GreenImageFile);
						         writableSheet.addImage(writableImage);
							 }
							 else if((record.get(columnNames[temp])+"").equalsIgnoreCase("red"))
								 {
									 //System.out.println("----------------red block----------------------");
									 writableImage = new WritableImage(rowCount++,count,1,1,RedImageFile); 
									 writableSheet.addImage(writableImage);
								 }
							 else if((record.get(columnNames[temp])+"").equalsIgnoreCase("yellow"))
							 {
								// System.out.println("--------------yellow block------------------------");
								 writableImage = new WritableImage(rowCount++,count,1,1,YellowImageFile); 
								 writableSheet.addImage(writableImage);
							 }
							 
							 else
							 {   strTemp=record.get(columnNames[temp])+"";
								 label=new Label(rowCount++,count,strTemp.replace("<br>",", ")); 
					    		 label.setCellFormat(writableCellFormat);
					    		 writableSheet.addCell(label);
							 }
							 
						 }
						 
						 /*label=new Label(rowCount++,count,strTemp);
			    		 label.setCellFormat(writableCellFormat);
			    		 writableSheet.addCell(label);*/
			    		 			    		 
					 }
				}
				 writableWorkbook.write();        	 
		    	 writableWorkbook.close();
	  		 
	    	
	    	ExcelFile=new File(contextPath+"\\reports-Test\\workbook.xls");
	    	fileInputStream=new FileInputStream(ExcelFile);
	    	
	    	targetData=new byte[fileInputStream.available()];
	        System.out.println(fileInputStream.read(targetData));
	         if(logger!=null) {
	        	 System.out.println("===============logger object is not null");
	         }
	    	}
	    	catch(Exception exception)
	    	{
	    		// logger.info("-------Exception in generateExcelFile----------");
		    	// logger.error(exception);
		    	 exception.printStackTrace();
	    	}   
	         return targetData;
	  }
	 
	public static String getCurrentDate()
	{
		Calendar calendar = Calendar.getInstance () ;  
		DateFormat dateFormat = new SimpleDateFormat ( "MM/dd/yyyy" ) ;
		String strConvertedDate=dateFormat.format (calendar.getTime ());
		return strConvertedDate;
	}
	
	
	public static byte[] generateIssueDetailExcelFile(HashMap details)
    {
	   logger=Logger.getLogger("Admin");
	   //System.out.println("=============1=====================");
	   WritableWorkbook writableWorkbook=null;
	   WritableSheet writableSheet=null;
	   WritableImage writableImage=null;
	   WritableFont writableFont=null; 
	   WritableCellFormat writableCellFormat=null;
	    
	   FileInputStream fileInputStream;  
	   File ExcelFile;
	    
	  // System.out.println(details);
	   Label titleLabel=null;
	   Label dateLabel=null;
	   
	   Label reportLabel=null;
	   Label searchLabel=null;
	   Label contenLabel=null;
	   Logger logger=null;
	   String contextPath=null;
	   String reportTitle=null;
	   ArrayList dataset=null;
	   HashMap contentData=null;
	   CellView TitleCellView=null;
	   String[] columnNames=null;
	   String[]columnSizes=null;
	   byte[] targetData=null;
	   String strSearchCriteria=null;
	   HashMap record=null;
	   
	   String[] issueTitleContent={"Issue_Id","Title","Description","Application_Name","Priority","Posted_By","Issue_Type","Status","Posted_Date","Due_Date","Issue_Completion_Date","Closed_Date"};
    	try
    	{
    		//System.out.println("=============Entered into try block=====================");
    		contextPath=(String)details.get("path");
    		reportTitle=(String)details.get("reportTitle");
    		columnNames=(String[])details.get("ColumnNames");
    		columnSizes=(String[])details.get("columnSizes");
    		contentData=(HashMap)details.get("issueDetails");
    		dataset=(ArrayList)contentData.get("issueLife");
    		
    		
    		for(String s:columnNames)
    		{
    			//System.out.println("------------------- "+s);
    		}
    		for(String s:columnSizes)
    		{
    			//System.out.println("------------------- "+s);
    		}
    		strSearchCriteria=(String)details.get("searchCriteria");
    		titleLabel=new Label(0,0,"Vertex Issue Management System");
    		reportLabel=new Label(0,1,"Report Title : "+reportTitle);
    		dateLabel=new Label(0,2,"Created Date : "+getCurrentDate());
    		
    		searchLabel=new Label(0,3,strSearchCriteria.replace("<br>",", "));
    		
    		Label[] columnLabels=new Label[columnNames.length];
    		
    		
    		
			writableWorkbook =Workbook.createWorkbook(new File(contextPath+"\\reports-Test\\workbook.xls"), new WorkbookSettings());  		 
			writableSheet = writableWorkbook.createSheet(reportTitle,0); 
			writableFont = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);         
			writableCellFormat = new WritableCellFormat(writableFont);			
			writableCellFormat.setWrap(false);
          
            TitleCellView=new CellView();
            TitleCellView.setSize(256*20);
            writableSheet.setColumnView(0, TitleCellView);
            
            for(int rowNum=0;rowNum<16;rowNum++)
            {
            	writableSheet.setRowView(rowNum,(20*20),false);            	
            }
            
            CellView[] HeadingCellView=new CellView[(columnNames.length)];
            for(int index=0;index<HeadingCellView.length;index++)
            {
            	HeadingCellView[index]=new CellView();
            }
            
            for(int colIndex=1;colIndex<HeadingCellView.length;colIndex++)
            {
            	HeadingCellView[colIndex].setSize(256*(Integer.parseInt(columnSizes[colIndex])));
            	writableSheet.setColumnView(colIndex, HeadingCellView[colIndex]);
            	//System.out.println("######################### "+colIndex);
            }
  		     
            titleLabel.setCellFormat(writableCellFormat);
  		    reportLabel.setCellFormat(writableCellFormat);
  		    dateLabel.setCellFormat(writableCellFormat);
            
    		writableSheet.addCell(titleLabel);
    	    writableSheet.addCell(reportLabel);
    	    writableSheet.addCell(dateLabel);  
  		   // writableSheet.addCell(searchLabel);
    	    
    	    writableFont = new WritableFont(WritableFont.ARIAL,10, WritableFont.NO_BOLD);         
			writableCellFormat = new WritableCellFormat(writableFont);
			//writableCellFormat.setBackground(Colour.GRAY_25);
			writableCellFormat.setWrap(false);
    	    
    	    
    	    for(int contentStart=4;contentStart<issueTitleContent.length+4;contentStart++) 
            { 
    	    	String value=contentData.get(issueTitleContent[contentStart-4])+"";
    	    	//System.out.println("-----------contentStart----------------------"+contentStart); 
    	    	//System.out.println("--------------"+contentData.get(issueTitleContent[contentStart-4])+""+" -------------------");
            	if(value.equalsIgnoreCase("null")) 
            	{
            		value="";
            	}
    	    	contenLabel=new Label(0,contentStart,issueTitleContent[contentStart-4].replace('_', ' ')+" : "+value);
            	contenLabel.setCellFormat(writableCellFormat);
            	writableSheet.addCell(contenLabel);
            	
            }
  		    
  		    writableFont = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);         
			writableCellFormat = new WritableCellFormat(writableFont);
			writableCellFormat.setBackground(Colour.GRAY_25);
			writableCellFormat.setWrap(true);
  		   
			int   index=0;
			
			for(int i=0;i<columnNames.length;i++)
			{
				//System.out.println("@@@@@@@@@ "+columnNames[i].toString()); 
			}
			
			int rowCount=0;
			for(int count=0;count<columnLabels.length;count++)
    		{ 	
    			columnLabels[count]=new Label(rowCount++,issueTitleContent.length+5,(columnNames[count])); 
    			columnLabels[count].setCellFormat(writableCellFormat); 
    		}
			
			for(index=0;index<columnLabels.length;index++) {
				 writableSheet.addCell(columnLabels[index]);
			}
			
			writableFont = new WritableFont(WritableFont.ARIAL,8, WritableFont.NO_BOLD);         
			writableCellFormat = new WritableCellFormat(writableFont);				
			writableCellFormat.setWrap(true);
			
			Label label=null; 						
			int count;
			for(index=0;index<dataset.size();index++)
				{
				 rowCount=0;
				 record=(HashMap)dataset.get(index);
				 count=issueTitleContent.length+6+index;
				 for(int temp=0;temp<columnNames.length;temp++)
				 {
					 String singleRecord=record.get(columnNames[temp])+"";
		    		if(singleRecord.equalsIgnoreCase("null")) 
		    		{
		    			singleRecord="";
		    		}
		    		
					 label=new Label(rowCount++,count,singleRecord);
		    		 label.setCellFormat(writableCellFormat);
		    		 writableSheet.addCell(label);			    		 
				 }
			}
			 writableWorkbook.write();        	 
	    	 writableWorkbook.close();
  		 
    	
    	ExcelFile=new File(contextPath+"\\reports-Test\\workbook.xls");
    	fileInputStream=new FileInputStream(ExcelFile);
    	
    	targetData=new byte[fileInputStream.available()];
        System.out.println(fileInputStream.read(targetData));
         if(logger!=null) {
        	 System.out.println("===============logger object is not null");
         }
    	}
    	catch(Exception exception)
    	{
    		// logger.info("-------Exception in generateExcelFile----------");
	    	// logger.error(exception);
	    	 exception.printStackTrace();
    	}   
         return targetData;
  }

	public static byte[] generatePdfFile(HashMap details)
	{	
		  Document document=null;
		  //Paragraph paragraph=null;		
		  
		   Table table=null;
		   Cell[] ApplicationDetails= null;
		   
		   Logger logger=null;
		   String contextPath=null;
		   String reportTitle=null;
		   ArrayList dataset=null;
		   
		   String[] columnNames=null;
		   String[]columnSizes=null;
		   byte[] targetData=null;
		   String strSearchCriteria=null;
		   int size=0;
		   HashMap record =null;
		  	
		  try {
			    logger=Logger.getLogger("Admin");
			    //System.out.println("=============1=====================");
			  	contextPath=(String)details.get("path");
	    		//System.out.println("----------contextPath---------------"+contextPath); 
	    		reportTitle=(String)details.get("reportTitle");
	    		columnNames=(String[])details.get("ColumnNames");
	    		columnSizes=(String[])details.get("columnSizes");
	    		dataset=(ArrayList)details.get("dataset"); 
	    		strSearchCriteria=(String)details.get("searchCriteria");
			    size=dataset.size();
			    
			   document = new Document(PageSize.B2, 50, 50, 50, 50);
			   //document.setFooter(new HeaderFooter(new Phrase("This is a header."), false)); 			 
			  
			   FileOutputStream fos=new FileOutputStream(contextPath+"reports-Test\\VIMSReport.pdf");      	       
			   PdfWriter.getInstance(document,fos);			  
		       document.open();   
		       
	    	   document.add(new Paragraph("Vertex Issue Management System"));
	    	   document.add(new Paragraph("Report Title: "+reportTitle));
	    	   document.add(new Paragraph("Created on  :"+getCurrentDate()));
		      
			   table = new Table(columnNames.length);			  
			   table.setBorderWidth(1);			  
			   table.setWidth(100);
			   
			   table.setAlignment("left");  
			   
			   ApplicationDetails=new Cell[columnNames.length];
			   for(int colIndex=0;colIndex<columnNames.length;colIndex++)
			   {
				   ApplicationDetails[colIndex]=new Cell(columnNames[colIndex].replace('_', ' ')); 
				   ApplicationDetails[colIndex].setWidth(columnSizes[colIndex]);
				   ApplicationDetails[colIndex].setHeader(true);
				   ApplicationDetails[colIndex].setColspan(1);
				   table.addCell(ApplicationDetails[colIndex]);
				   //System.out.println("----------colIndex------------"+columnNames[colIndex].replace('_', ' '));
			   }			 		   
			   table.endHeaders();			   
			   //int count;
			   String strTemp;
			   for(int index=0;index<dataset.size();index++)
				{
				// int rowCount=0;
				 record = (HashMap)dataset.get(index);
				 for(int temp=0;temp<columnNames.length;temp++)
				 {
					// count=index+6;
					 if(record.get(columnNames[temp])==null)
					 {
						 strTemp=""; 
						 table.addCell(new Cell(strTemp));			    		 
	 				 }
					 else
					 {
						 strTemp=record.get(columnNames[temp])+"";						 
			    		 table.addCell(new Cell(strTemp));
					 }
					// System.out.println("-------record.get(columnNames[temp]---------------"+record.get(columnNames[temp])); 
				 }
			}
			  document.add(table);			  
			  document.close(); 
			  
			  File file=new File(contextPath+"reports-Test\\VIMSReport.pdf");		  
			  FileInputStream fis=new FileInputStream(file);
		  
		      targetData=new byte[fis.available()];	    
			  fis.read(targetData);	   
			  fis.close();
		    
		      boolean status=file.delete();
		      //System.out.println("------------targetData length in generator--------------"+targetData.length); 
		     
		  }
		    catch(Exception exception) 
			{
		  	  exception.printStackTrace();		  	 
			}	
		    return targetData;
	}	
}
