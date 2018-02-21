/*  File Name : AutoCompleteServlet.java
 *  
 *  copyright (c) 2008 Vertex.All rights reserved.
 *  
 *  Created By : Saikumar.M.P
 *  
 *  Description : This Action class is used for implementing auto complete feature in Application list page for searching the application
 *  modify application page.
 *  
 *  
 *   Change History:
 * 	  Revision No.			Date		  @author			Description
 *		1.0					Feb-07-2009   Saikumar.M.P		Initial version
 *
 *   
 *  
 *  			
 * 
 */

package com.vertex.VIMS.test.applications.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vertex.VIMS.test.applications.form.ApplicationBean;
import  com.vertex.VIMS.test.applications.form.ApplicationBean;
import com.vertex.VIMS.test.applications.BD.*;
public class AutoCompleteServlet extends HttpServlet 
{
	private ServletContext context;
	//creating the HashMAp object to hold list applications
    private HashMap applications = new HashMap();
    //doGet method
    public  void doGet(HttpServletRequest request, HttpServletResponse  response) throws IOException, ServletException
    {
    	//getting the list of applications based on search criteria
    	applications=VIMSApplicationBD.searchApplicationBD();
    	Set keySet=applications.keySet();//getting the key set from the hash map
    	Iterator itrations=keySet.iterator();//assigning an iterator to the key set
    	
    	/*while(itrations.hasNext()) {
    		
    		System.out.println("===="+itrations.next().toString());
    	}*/
    	String action = request.getParameter("action");//getting the action
    	String targetId = request.getParameter("appId");//getting the id of the application for which we are searching
        StringBuffer sb = new StringBuffer();
        if (targetId != null) 
    	{
    		targetId = targetId.trim().toLowerCase();//getting the target search key and converting to lower case
    	}
        boolean namesAdded = false;
        if ("complete".equals(action)) 
        {
        	Iterator it = applications.keySet().iterator();//getting an iterator for the applications HashMap
        	//while there are elements in the hashmap
        	while(it.hasNext())
        	{
        		String id = (String)it.next();
        		ApplicationBean a = (ApplicationBean)applications.get(id);
        		// simple matching only for start of Application Name
                if ((targetId != null) && (a.getStrAppName().toLowerCase().startsWith(targetId)) &&  !targetId.equals("")  )
                {
                	sb.append("<application>");
                    sb.append("<id>" + a.getStrAppId() + "</id>");
                    sb.append("<name>" + a.getStrAppName() + "</name>");
                    sb.append("</application>");
                    namesAdded = true;	
                }
        	}
        	if (namesAdded) 
			{
			    response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
		        response.getWriter().write("<applications>" + sb.toString() + "</applications>");
			} 
        	else 
			{
                //nothing to show
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				response.getWriter().write("<applications>" + "No Records Found" + "</applications>");
            }
        }
        if ("lookupbyname".equals(action))
        	{
        	System.out.println("=====lookupby name=======");
        		Iterator it = applications.keySet().iterator();
	            ArrayList names = new ArrayList();
	            while (it.hasNext()) 
	            {
	                String id = (String)it.next();
	                ApplicationBean a = (ApplicationBean)applications.get(id);
	                // simple matching only for start of first or last name
	                if (a.getStrAppName().toLowerCase().startsWith(targetId))  
	                {
	                   names.add(a);
	                }
	            }
	            if (names.size() > 0) 
	            {
	            	request.setAttribute("applications", names);
	            }
	            context.getRequestDispatcher("./ApplicationLinkTest/viewApplication.jsp").forward(request, response);
        	
          } 
        else if ("lookup".equals(action)) 
        {
        	System.out.println("=====lookup======="); 
        	 if ((targetId != null) && applications.containsKey(targetId.trim())) 
        	 {
        		 request.setAttribute("application", applications.get(targetId));
        		 context.getRequestDispatcher("./ApplicaationLinkTest/applications.jsp").forward(request, response);
        	 }
        	 else 
        	 {
                 context.getRequestDispatcher("/error.jsp").forward(request, response);
             }
        }
    }	
}
