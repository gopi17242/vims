/*
  FileName	    : RandomGeneration.java
  
 
  Description	: This class is used to random generate passwords
  
                   
  Developed by  : Vertex Computer Systems.
                  copyright (c) 2008 Vertex.
                  All rights reserved.
  
  Change History:
 
  Revision No.	:		Date		  @author		Description
    1.0				Friday 15,2008	  Geeta.M	   Initial Version.*/


package com.vertex.VIMS.test.clients.action;

import java.util.Random;

public class RandomGeneration 
{    
	private static final int NUM_CHARS = 10;  
	private static String    chars     = "abcdefghijklmonpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  
	private static Random    r         = new Random();     
	
/********Method------------>getUniqueID***********/
	  
/********This method is used to get unique random generated passwords..*********/ 
	 	
	public static String getUniqueID()
	 {        
		 char[] buf = new char[NUM_CHARS]; 
		 for (int i = 0; i < buf.length; i++) 
		 {            
			buf[i] = chars.charAt(r.nextInt(chars.length())); 
		 }         
	    return new String(buf); 
	 }     
}
