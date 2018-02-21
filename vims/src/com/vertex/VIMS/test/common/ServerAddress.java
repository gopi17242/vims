package com.vertex.VIMS.test.common;

import javax.servlet.http.*;

public class ServerAddress 
{
		public static String getURL(HttpServletRequest request) 
		{
	        StringBuffer strBuff = new StringBuffer("http://");
	        strBuff.append(request.getServerName());
	        strBuff.append(":");
	        strBuff.append(request.getServerPort());
	        strBuff.append(request.getContextPath());
	        strBuff.append(request.getServletPath());
	        return strBuff.toString();
	     }
}
