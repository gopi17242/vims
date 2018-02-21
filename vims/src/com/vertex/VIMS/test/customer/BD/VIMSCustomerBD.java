package com.vertex.VIMS.test.customer.BD;
/**
 * @author saikumar.m
 *
 */
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.vertex.VIMS.test.customer.DAO.VIMSCustomerDAO;
import com.vertex.VIMS.test.customer.form.VIMSCustomerForm;

public class VIMSCustomerBD 
{
	
	/*
	 * Declaring static instance variables for using them through out the program
	 */	
	static ArrayList arrayList=null;
	static HashMap hashMap=null;
	static Logger logger=null;
	
	/*
	 * Declaring static method for getting the customer's list of issues
	 */	
	public static ArrayList getCustIssuesList(String custId, VIMSCustomerForm vimsCustomerForm)
	{	
		logger=Logger.getLogger("Customer");
		String issueStatus=null;
		arrayList=new ArrayList();
		issueStatus=vimsCustomerForm.getIssueType();
		if(issueStatus!=null)
		{
			arrayList=VIMSCustomerDAO.getCustIssueListDAO(custId, issueStatus);
			return arrayList;
		}
		else
		{
			System.out.println("============vimsCustomerForm= in else ===="+vimsCustomerForm);
			arrayList=VIMSCustomerDAO.getCustIssueListDAO(custId, "all");
			return arrayList;
		}
		
		
	}

}
