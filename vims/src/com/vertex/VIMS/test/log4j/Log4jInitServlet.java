/*
 * Created on Nov 6, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.vertex.VIMS.test.log4j;

import javax.servlet.http.*;

import org.apache.log4j.PropertyConfigurator;


/**
 * @author saikumar.m
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 *
 ****** This is the servlet class whose purpose is to initialize log4j.xml configuration file */

public class Log4jInitServlet extends HttpServlet
{
		public void init() 
		{
			String prefix = getServletContext().getRealPath("/");
			String file = getInitParameter("log4jconfig");
			// if the log4j-init-file is not set, then no point in trying
			if(file != null) 
			{
				PropertyConfigurator.configure(prefix+file);
			}
		}		
}
