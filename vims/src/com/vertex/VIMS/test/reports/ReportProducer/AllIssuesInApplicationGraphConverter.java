/*
 * @author: santhosh.k
 * @creation date: 
 * @file name : AllIssuesInApplicationGraphConverter.java
 * @description: 
 * 			It is the Graph converter used to create Excel Sheets and PDf files and to add contents to the files.
*/
package com.vertex.VIMS.test.reports.ReportProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import de.laures.cewolf.ChartImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 * This method is used to store image temporarily and to addthe image to the excel sheet in excel sheet generation. 
*/
public class  AllIssuesInApplicationGraphConverter {
	 public static void  storeAsImage(HashMap details,String contextPath) {
     String strImageId=null;
     byte data[];
     File file;
     FileOutputStream fileOutputStream=null;
     int length;
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
	  * This method is used to store the records as PDf file in specific issues in application for all issues in an application
	 */
	 
	public static byte[] storeAsPdf(HashMap details,String contextPath, String strIssueType, String strApplication) 
	{
	  String Applications[]=null;
	  int IssuesCount[]=null;	  
	  String strImageId;	
	  HashMap dataset=null;
	  String ApplicationName=null;
	  Image image=null;
	  ChartImage chartImage=null;  	      
	  Document document=null;
	  Paragraph paragraph=null;	  	     
	  Table table=null;
	  Cell IssueTypeCell=null;
	  Cell IssuesCell=null;	   
	  String[] IssueTypes={"Open","Assigned","Accepted","Rejected","Escalated","Closed","Completed"};
	  int [] allIssues=new int[7];
	  FileOutputStream fos=null;
	   
	  try {		   
  		   chartImage=(ChartImage)details.get("Image"); 
  		   strImageId=(String)details.get("ImageId");   		   
		   dataset=(HashMap)details.get("IssuesAndApplicationList");		  
		   
		   allIssues[0]=(Integer)dataset.get("open");		
		   allIssues[1]=(Integer)dataset.get("assigned");		
		   allIssues[2]=(Integer)dataset.get("accepted");		 
		   allIssues[3]=(Integer)dataset.get("rejected");		 
		   allIssues[4]=(Integer)dataset.get("escalated");		 
		   allIssues[5]=(Integer)dataset.get("closed");		
		   allIssues[6]=(Integer)dataset.get("completed");		 
		   ApplicationName=(String)dataset.get("ApplicationName");
		  Applications=(String[])dataset.get("ApplicationList");
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
	       document.add(new Paragraph("Vertex Issue Management System"));
    	   document.add(new Paragraph("Report Title:Issues in "+ApplicationName));
    	   document.add(new Paragraph("Created on  :"+getCurrentDate()));	
		   table = new Table(2);			  
		   table.setBorderWidth(1);	 		  
		   table.setWidth(50);		  
		   table.setAlignment("left");      	 
	  
		   IssueTypeCell = new Cell("Issue Type");
		   
		   IssueTypeCell.setHeader(true);
		   IssueTypeCell.setColspan(1);
		   table.addCell(IssueTypeCell);
		  	
		  	
		   IssuesCell = new Cell("No of Issues");
		   IssuesCell.setHeader(true);
		   IssuesCell.setColspan(1);
		   table.addCell(IssuesCell);
		   table.endHeaders();
		  
	  
		  for(int count=0;count<IssueTypes.length;count++)
		  {
			  IssueTypeCell=new Cell(IssueTypes[count]);			 
			  table.addCell(IssueTypeCell);
			  IssuesCell=new Cell(allIssues[count]+"");				
			  table.addCell(IssuesCell);		    
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
		  System.out.println("Exception raised in storeAsPdf ()"+exception);
	  	  exception.printStackTrace();
	  	  return null;
		}		 
 }
	/*
	  * This method is used to store the records as excel sheet in specific issues in application for all issues in an application
	 */
	
	public static byte[] storeAsExcel(HashMap details,String contextPath, String strIssueType,String strApplication)
	{
		String strImageId;
 	    String Applications[];
 	    HashMap dataset;
 	    String ApplicationName=null; 
 	    
 	  
 	   
 	    String[] IssueTypes={"Open","Assigned","Accepted","Rejected","Escalated","Closed","Completed"};
 		int [] allIssues=new int[7];
 		  
 	   WritableWorkbook writableWorkbook=null;
 	   WritableSheet writableSheet=null;
 	   WritableImage writableImage=null;
 	   WritableFont writableFont=null; 
 	   WritableCellFormat writableCellFormat=null;
 	   
 	   FileInputStream fileInputStream;
 	   File ImageFile;
 	   File ExcelFile;
 	   Label IssueTypeLabel=null;
 	   Label IssuesCountLabel=null;     
 	   byte targetData[];
 	   boolean ImageFilestatus;
 	   boolean ExcelFilestatus;
 	   
 	   Label titleLabel=new Label(0,0,"Vertex Issue Management System");	  
	   Label dateLabel=new Label(0,2,"Created Date : "+getCurrentDate());
 	   try
 	   { 
 		  
 		  strImageId=(String)details.get("ImageId"); 		   
 		  dataset=(HashMap)details.get("IssuesAndApplicationList");
 		 if(!(strImageId.equalsIgnoreCase("100")))
  		 {
 			 AllIssuesInApplicationGraphConverter.storeAsImage(details, contextPath);
  		 }
		  allIssues[0]=(Integer)dataset.get("open");		  
		  allIssues[1]=(Integer)dataset.get("assigned");		  
		  allIssues[2]=(Integer)dataset.get("accepted");		  
		  allIssues[3]=(Integer)dataset.get("rejected");		
		  allIssues[4]=(Integer)dataset.get("escalated");		 
		  allIssues[5]=(Integer)dataset.get("closed");		  
		  allIssues[6]=(Integer)dataset.get("completed");		 
		  ApplicationName=(String)dataset.get("ApplicationName");
		  
		  Label reportLabel=new Label(0,1,"Report Title : Issues in "+ApplicationName); 
  		  writableWorkbook =Workbook.createWorkbook(new File(contextPath+"\\reports\\workbook"+strImageId+".xls"), new WorkbookSettings());  		 
          		 
  		  writableSheet = writableWorkbook.createSheet("Issues in "+ApplicationName,0); 
  		writableFont = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);         
        writableCellFormat = new WritableCellFormat(writableFont);
        writableCellFormat.setBackground(Colour.GRAY_25);
        writableCellFormat.setWrap(true);
        
        
        
        CellView TitleCellView=new CellView();
		CellView HeadingCellView=new CellView();
		
	    TitleCellView.setSize(256*50);
	    HeadingCellView.setSize(256*("No Of Issues").length()+2);
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
  			
  	    if(!(strImageId.equalsIgnoreCase("100")))
		 {
            writableImage = new WritableImage(2,4,7,16, new File(contextPath+"\\reports\\Image"+strImageId+".png"));
		 }
	  	  if(writableImage!=null)
			{
	          writableSheet.addImage(writableImage);
			}

                   
         IssueTypeLabel= new Label(0,4,"Issue Type");
         IssueTypeLabel.setCellFormat(writableCellFormat); 
    	 
         IssuesCountLabel= new Label(1,4,"No of Issues");
         IssuesCountLabel.setCellFormat(writableCellFormat); 
    	 writableSheet.addCell(IssueTypeLabel);
	 	 writableSheet.addCell(IssuesCountLabel);
	 	 
	 	writableFont = new WritableFont(WritableFont.ARIAL,8, WritableFont.NO_BOLD);         
        writableCellFormat = new WritableCellFormat(writableFont);        
        writableCellFormat.setWrap(true);
	 	 int   index=0;
	    	 for(int count=0;count<IssueTypes.length;count++)
	    	 {    	 	
	    		 int rowCount=0;
	    		 index=count+5;	    		 
	    		 IssueTypeLabel=new Label(rowCount++,index,IssueTypes[count]);
	    		 IssueTypeLabel.setCellFormat(writableCellFormat);
	    		 
	    		 IssuesCountLabel=new Label(rowCount,index,allIssues[count]+"");
	    		 IssuesCountLabel.setCellFormat(writableCellFormat);
	    		 
	    		 if(count!=0)
	    		 {
	    			 writableSheet.setColumnView(1, HeadingCellView);
	    		 }
	    		 
	   	 	  	 writableSheet.addCell(IssueTypeLabel);
	   	 	  	 writableSheet.addCell(IssuesCountLabel); 
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
	
	public static String getCurrentDate()
	{
		Calendar calendar = Calendar.getInstance () ;  
		DateFormat dateFormat = new SimpleDateFormat ( "MM/dd/yyyy" ) ;
		String strConvertedDate=dateFormat.format (calendar.getTime ());
		return strConvertedDate;
	}
}

	