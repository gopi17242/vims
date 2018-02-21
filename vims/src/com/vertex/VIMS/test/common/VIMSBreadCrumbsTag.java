package com.vertex.VIMS.test.common;
/*
 * author@ saikumar.m
 * created on 12/10/2008
 * 
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class VIMSBreadCrumbsTag extends TagSupport
{
	
	/**
	 * 
	 */	

	private static final long serialVersionUID = 1L;
	String []pageNameArray={"Home"};
	String currPageName="Home";
	String title_key="body";
	String link_key="";
	
	
	public String[] getPageNameArray() {
		return pageNameArray;
	}
	public void setPageNameArray(String[] pageNameArray) {
		this.pageNameArray = pageNameArray;
	}
	
	public String getCurrPageName() {
		return currPageName;
	}
	public void setCurrPageName(String currPageName) {
		this.currPageName = currPageName;
	}
	
	public synchronized int doStartTag() throws JspTagException
	{
		//System.out.println("======1=======");
		Logger lLogger = Logger.getLogger("Admin");
		JspWriter out = pageContext.getOut();
		try
		{
			//System.out.println("======2=======");
			StringBuffer lSbuf=new StringBuffer();
			//System.out.println("======3=======");
					
			//System.out.println("======4=arrayLength======"+pageNameArray[0]);
			//System.out.println("========getKeyValue()========="+getKeyValue(currPageName));
			//System.out.println("=======HashMap===="+VIMSBreadCrumbs.getPageNameArray());
			HashMap<String,String[]> hashMap=VIMSBreadCrumbs.getPageNameArray();
			//System.out.println("=========currPageName======"+currPageName);
			if(hashMap!=null){
				pageNameArray=(String[])hashMap.get(currPageName);
			}
			if(pageNameArray!=null){
				for(int i=0;i<pageNameArray.length;i++)
				{
					//System.out.println("====pageNameArray=elements==="+pageNameArray[i]);
				}
				for(int i=0;i<pageNameArray.length;i++)
				{
					/*if(!getPageNameArray()[i].equals(currPageName))
					{
						lSbuf=lSbuf.append("<a href=''>"+getPageNameArray()[i]+"</a> >> ");
					} */
					if(i!=pageNameArray.length-1)
					{
						lSbuf=lSbuf.append("<a href='"+getKeyValue(pageNameArray[i])+"' class='heading_breadcrumb_blue'>"+pageNameArray[i]+"</a>  >  ");
					}
					else
					{
						lSbuf=lSbuf.append("<font class='heading_bread_black'>"+getPageNameArray()[i]+"</font>");
						i=pageNameArray.length;
					}
				}
			}
			pageContext.getOut().write(lSbuf.toString());
			//System.out.println("======5=======");
		}
		catch(Exception e)
		{
			lLogger.info("VIMSBreadCrumbsTag.doStartTag()");
			e.printStackTrace();
			lLogger.error(e);
		}
		 
		return EVAL_PAGE;
	}
		
	protected synchronized String getKeyValue(String key)  
	{
		
		ResourceBundle rs = ResourceBundle.getBundle("resources.VIMSApplication");
		String keyValue = rs.getString(key.replace(" ", "")); 
	
		return keyValue;
	}
			
}

