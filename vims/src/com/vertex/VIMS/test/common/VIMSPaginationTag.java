package com.vertex.VIMS.test.common;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class VIMSPaginationTag extends TagSupport
{	
	
	private int maxPages;
	private int pageNumber;
	private ArrayList arrayList;
	private int rowsPerPage;
	private String listName;
	private String forwardPage;
	
	public String getForwardPage() {
		return forwardPage;
	}
	public void setForwardPage(String forwardPage) {
		this.forwardPage = forwardPage;
	}
	
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}	
	public int getMaxPages() {
		return maxPages;
	}
	public void setMaxPages(int maxPages) {
		this.maxPages = maxPages;
	}
	public int getPageNumber() 
	{
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) 
	{
		this.pageNumber = pageNumber;
	}	
	public ArrayList getArrayList() {
		return arrayList;
	}
	public void setArrayList(ArrayList arrayList) {
		this.arrayList = arrayList;
	}
	public int getRowsPerPage() {
		return rowsPerPage;
	}
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	public synchronized int doStartTag() throws JspTagException
	{
	  try
	    {	   
		    String prevPage = ""; //String variable, which will contain the Previous page URL.
			String nextPage = "";     //String variable, which will contain the Next page URL.
			int totalPages = 0;       //int variable which gives the count for total number of pages for a search.       
			int currentPage = 0;      //int variable which gives the page being accessed by the user.
			
			//String locationCount = getLocation() + "Count";
			//String locationColor = getLocation() + "Color"; 
			
			Integer recordsPerPage = new Integer(10); 
			
			//ArrayList arrayList = getArrayList();
	        totalPages = getMaxPages();
	        
			if ((new Integer(getPageNumber()).intValue())== 0 )
			{
			   currentPage = 1;
	        }
	        else
	        { 
	        	currentPage = (new Integer(getPageNumber()).intValue());
	        }
			
		    StringBuffer sbuf = new StringBuffer();
			String pageSpecificURL = "";
		
			String p="";
		
			if ((new Integer(getMaxPages()).intValue())== 0)
			{
				pageSpecificURL  = "";
				sbuf.append(pageSpecificURL);
			}
		
			else if (totalPages <= 1)
			{
				pageSpecificURL  = 	"<b><font class='normalcontenttext'>"+totalPages+"</font></b> ";
				sbuf.append(pageSpecificURL);
			}
			else 
			{				
					if (currentPage > 1)
					{
						int prevPageNumber = currentPage - 1; 
						prevPage = 	"<a class='linkPag' href='./PaginationAction.do?pageNo="+prevPageNumber+"&rowsPerPage="+getRowsPerPage()+"&source="+getListName()+"&forwardPage="+getForwardPage()+"'>" + "<b>&lt;&lt;Previous</b> </a>&nbsp;&nbsp;" ;
						sbuf.append(prevPage);
					}
										
					/*for(int j=1;j<=totalPages;j++)
					{
						if (j == (new Integer(getPageNumber()).intValue()))
						{
							pageSpecificURL  = "<b>"+j+"</b>&nbsp;&nbsp; " ;
						}
						else 
						{
							pageSpecificURL  = 	"<a class='normalcontenttext' href='./PaginationAction.do?pageNo="+j+"&rowsPerPage="+getRowsPerPage()+"&source="+getListName()+"&forwardPage="+getForwardPage()+"'><b>"+j+"</b></a>&nbsp;&nbsp;"+" " ;
						}
						sbuf.append(pageSpecificURL);						
					}*/
					
					int startIndex=1;
					int maxPageDisplay=Integer.parseInt(getKeyValue("maxPageDisplay"));
					int endIndex=maxPageDisplay;
					int prevDisplacement=Integer.parseInt(getKeyValue("prevDisplacement"));
					int nextDisplacement=Integer.parseInt(getKeyValue("nextDisplacement"));
					if((totalPages<=maxPageDisplay)) 
					{
						System.out.println("======cust tag====="+currentPage);
						startIndex=1;
						endIndex=totalPages;
					}
					else if(currentPage<maxPageDisplay){
						startIndex=1;
						endIndex=maxPageDisplay;;
					}
					else						
					if(totalPages>maxPageDisplay)
					{
						if(currentPage==1)
						{
							startIndex=currentPage;
							endIndex=maxPageDisplay;
						}
						else if(!((currentPage+nextDisplacement)>totalPages))
						{
							startIndex=currentPage-prevDisplacement;
							endIndex=currentPage+nextDisplacement;
						}
						else if(((currentPage+nextDisplacement)>totalPages))
						{
							startIndex=totalPages-(prevDisplacement+nextDisplacement);
							endIndex=totalPages;
						}
						
					}
					
					for(int j=startIndex;j<=endIndex;j++)
					{
						
						if(j>=1 && j<=totalPages)
						{
									
							if (j == (new Integer(getPageNumber()).intValue()))
							{
								p=""+j;
								pageSpecificURL  = "<font size=\"2.5\"><b>"+j+"</b></font>&nbsp;&nbsp; " ;
							}
							else 
							{
									pageSpecificURL  = 	"<a class='linkPag' href='./PaginationAction.do?pageNo="+j+"&rowsPerPage="+getRowsPerPage()+"&source="+getListName()+"&forwardPage="+getForwardPage()+"'><b>"+j+"</b></a>&nbsp;&nbsp;"+" " ;
								
							}
						}
						sbuf.append(pageSpecificURL);						
					}
					if ((currentPage < totalPages)&& (totalPages > 1)) 
					{
			 			int nextPageNumber = currentPage + 1;									
						nextPage = 	"<a class='linkPag' <a href='./PaginationAction.do?pageNo="+nextPageNumber+"&rowsPerPage="+getRowsPerPage()+"&source="+getListName()+"&forwardPage="+getForwardPage()+"'><b>Next&gt;&gt;</b> </a>" ;
						sbuf.append(nextPage);			   	
					}
					
				}			
				pageContext.getOut().write("<font color='blue'>"+sbuf.toString());
				pageContext.getOut().write("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+p+" of "+totalPages+"</font>");
	    }
		catch (IOException e) 
		{
				throw new JspTagException("JSPTagError :" + e.toString());
		}		
		return EVAL_PAGE;
	}
	protected synchronized String getKeyValue(String key)  {
		
		ResourceBundle rs = ResourceBundle.getBundle("resources.VIMSApplication");
		String keyValue = rs.getString(key);
	
		return keyValue;
		}
	
}	
