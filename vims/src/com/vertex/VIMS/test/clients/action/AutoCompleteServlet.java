package com.vertex.VIMS.test.clients.action;

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

import com.vertex.VIMS.test.clients.BD.VIMSClientBD;
import com.vertex.VIMS.test.clients.form.CustomerBean;

import com.vertex.VIMS.test.applications.BD.*;
public class AutoCompleteServlet extends HttpServlet 
{
	private ServletContext context;
    private HashMap customers = new HashMap();
    
    public void init(ServletConfig config) throws ServletException 
    {
        this.context = config.getServletContext();
        
        customers.put("1", new CustomerBean("V1","ab"));
        customers.put("2", new CustomerBean("V2","abc"));
        customers.put("3", new CustomerBean("V3","bc"));
        customers.put("4", new CustomerBean("V4","bcd"));
    }
    public  void doGet(HttpServletRequest request, HttpServletResponse  response) throws IOException, ServletException
    {
    	
    	//customers=VIMSClientBD.searchCustomerBD();
    	
    	System.out.println("===========Customer size======="+customers.size());
    	Set keySet=customers.keySet();
    	Iterator itrations=keySet.iterator();
    	
    	while(itrations.hasNext()) {
    		
    		System.out.println("===="+itrations.next().toString());
    	}
    	String action = request.getParameter("action");
        String targetId = request.getParameter("custId");
        StringBuffer sb = new StringBuffer();
        if (targetId != null) 
        	{
        		targetId = targetId.trim().toLowerCase();
        	}
        boolean namesAdded = false;
        if ("complete".equals(action)) 
        {
        	System.out.println("=====complete=======");
        	Iterator it = customers.keySet().iterator();
        	System.out.println("===trgtid==="+targetId); 
        	while(it.hasNext())
        	{
        		String id = (String)it.next();
        		System.out.println("==========id======"+id); 
        		CustomerBean c = (CustomerBean)customers.get(id);
        		System.out.println("===appID==="+c.getStrCustId());
        		System.out.println("=======appName===="+c.getStrCustName());
        		// simple matching only for start of Application Name
                if ((targetId != null) && (c.getStrCustName().toLowerCase().startsWith(targetId)) &&  !targetId.equals("")  )
                { 
                	System.out.println("========condition success===="+targetId); 
                	sb.append("<customers>");
                	sb.append("<customer>");
                    sb.append("<id>" + c.getStrCustId() + "</id>");
                    sb.append("<name>" + c.getStrCustName() + "</name>");
                    sb.append("</customer>");
                    sb.append("</customers>");
                    namesAdded = true;	
                }
        	}
        	if (namesAdded) 
			{
        		System.out.println("===sb====names===="+sb.toString()); 
			    response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
		        response.getWriter().write("<customers>" + sb.toString() + "</customers>");
			} 
        	else 
			{
                //nothing to show
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				response.getWriter().write("<customers>" + "No Records Found" + "</customers>");
            }
        }
        if ("lookupbyname".equals(action))
        	{
        	System.out.println("=====lookupby name=======");
        		Iterator it = customers.keySet().iterator();
	            ArrayList names = new ArrayList();
	            while (it.hasNext()) 
	            {
	                String id = (String)it.next();
	                CustomerBean c = (CustomerBean)customers.get(id);
	                // simple matching only for start of first or last name
	                if (c.getStrCustName().toLowerCase().startsWith(targetId))  
	                {
	                   names.add(c);
	                }
	            }
	            if (names.size() > 0) 
	            {
	            	request.setAttribute("customers", names);
	            }
	            context.getRequestDispatcher("./clientTest/ViewCustomer.jsp").forward(request, response);
        	
          } 
       /* else if ("lookup".equals(action)) 
        {
        	System.out.println("=====lookup======="); 
        	 if ((targetId != null) && customers.containsKey(targetId.trim())) 
        	 {
        		 request.setAttribute("application", customers.get(targetId));
        		 context.getRequestDispatcher("./ApplicaationLinkTest/applications.jsp").forward(request, response);
        	 }
        	 else 
        	 {
                 context.getRequestDispatcher("/error.jsp").forward(request, response);
             }
        }*/
    }	
}
