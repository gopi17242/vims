package com.vertex.VIMS.test.common;

import java.util.HashMap;

public class VIMSBreadCrumbs
{	
	//Home
	public static String []Home={"Home"};
	
	//Bread crumbs for Support Center
	public static String []SupportCenter={"Home","Support Centers"};
	public static String []SupportGroups={"Home","Support Groups"};
	public static String []NewSupportGroup={"Home","Support Groups","New Support Group"};
	public static String []ModifySupportGroup={"Home","Support Groups","Modify Support Group"};
	public static String []AddSupportCenter={"Home","Support Centers","New Support Center"};
	public static String []ModifySupportCenter={"Home","Support Centers","Modify Support Center"};
	
	//Bread crumbs for Applications
	public static String []Applications={"Home","Applications"};
	public static String []NewApplication={"Home","Applications","New Application"};
	public static String []ModifyApplication={"Home","Applications","Modify Application"};
	public static String []ApplicationSpecialist={"Home","Applications","Application Specialist"};
	public static String []ApplicationModules={"Home","Applications","Application Modules"};
	public static String []ApplicationGroups={"Home","Applications","Application Groups"};
	public static String []ViewApplication={"Home","Applications","View Application"};
	public static String []ApplicationCustomers={"Home","Applications","Application Customers"};
	
	//Bread crumbs for SLA
	public static String []SLA={"Home","SLA"};
	
	//Bread crumbs for Customers
	public static String []Customers={"Home","Customers"};
	public static String []NewCustomer={"Home","Customers","New Customer"};
	public static String []ModifyCustomer={"Home","Customers","Modify Customer"};
	public static String []ViewCustomer={"Home","Customers","View Customer"};
	
	//Bread crumbs for Employees
	public static String []Employees={"Home","Employees"};
	public static String []NewEmployee={"Home","Employees","New Employee"};
	public static String []ModifyEmployee={"Home","Employees","Modify Employee"};
	
	//Bread crumbs for Issues
	public static String []Issues={"Home","Issues"};
	public static String []NewIssue={"Home","Issues","New Issue"};
	public static String []ListOfIssues={"Home","Issues","List of Issues"};
	public static String []AssignIssue={"Home","Issues","List of Issues","Issue Details"};
	public static String []Escalation={"Home","Issues","Escalation"};
	public static String []Purging={"Home","Issues","Purging"};
	
	//Bread crumbs for Reports
	public static String []Reports={"Home","Reports"};
	public static String []IssueReports={"Home","Reports","Issue Report"};
	public static String []ApplicationReports={"Home","Reports","Application Report"};	
	public static String []CustomerReports={"Home","Reports","Customer Report"};
	public static String []EmployeeReports={"Home","Reports","Employee Report"};
	public static String []SLAReports={"Home","Reports","SLA Report"};
		
	//Bread crumbs for Search
	public static String []Search={"Home","Search"};
	
	//Bread crumbs for My To Do List
	public static String []MyToDoList={"Home","MY To Do List"};
	
	
	//Bread crumbs for Configuration Settings
	
	public static String[] ConfigurationSettings={"Home","Configuration Settings"};	
	public static String[] HomePage={"Home","Configuration Settings","Home Page Configuration"};
	public static String[] LDAPPage={"Home","Configuration Settings","LDAP Server Configuration"};
	public static String[] MailPage={"Home","Configuration Settings","Mail Server Configuration"};
    public static String[] FieldPage={"Home","Configuration Settings","Maintain Keywords"}; 
    public static String[] AccessPrevileges={"Home","Configuration Settings","Access Previleges"};
	//Bread crumbs for Footer
	public static String []FAQ={"Home","FAQ"};
	public static String []OfficeLocations={"Home","Office Locations"};
	public static String []AboutUS={"Home","About Us"};
	public static String []TermsandConditions={"Home","Terms and Conditions"};
	public static String []PrivacyPolicy={"Home","Privacy Policy"};
	
	public static HashMap<String, String[]> getPageNameArray()
	{
		HashMap<String, String[]> hashMap=new HashMap<String, String[]>();
		
		//Home
		hashMap.put("Home", Home);
		
		//support centers
		hashMap.put("SupportCenters", SupportCenter);
		hashMap.put("AddSupportCenter", AddSupportCenter);
		hashMap.put("ModifySupportCenter", ModifySupportCenter);
		
		// Groups
		hashMap.put("SupportGroups",SupportGroups);
		hashMap.put("NewSupportGroup", NewSupportGroup);
		hashMap.put("ModifySupportGroup", ModifySupportGroup);
		
		//Applications		
		hashMap.put("Applications", Applications);
		hashMap.put("NewApplication", NewApplication);
		hashMap.put("ModifyApplication", ModifyApplication);
		hashMap.put("ApplicationSpecialist", ApplicationSpecialist);
		hashMap.put("ApplicationModules", ApplicationModules);
		hashMap.put("ApplicationGroups", ApplicationGroups);
		hashMap.put("ViewApplication", ViewApplication);
		hashMap.put("ApplicationCustomers", ApplicationCustomers);
		
		//SLA
		hashMap.put("SLA", SLA);
		
		//Customers
		hashMap.put("Customers",Customers);
		hashMap.put("NewCustomer",NewCustomer);
		hashMap.put("ModifyCustomer",ModifyCustomer);
		hashMap.put("ViewCustomer",ViewCustomer);
		
		//Employees
		hashMap.put("Employees",Employees);
		hashMap.put("NewEmployee",NewEmployee);
		hashMap.put("ModifyEmployee",ModifyEmployee);
		
		//Issues
		hashMap.put("Issues", Issues);
		hashMap.put("NewIssue", NewIssue);
		hashMap.put("ListOfIssues", ListOfIssues);
		hashMap.put("AssignIssue", AssignIssue);
		hashMap.put("Escalation", Escalation);
		hashMap.put("Purging", Purging);
		
		//Reports
		hashMap.put("Reports", Reports);
		hashMap.put("IssueReports", IssueReports);
		hashMap.put("ApplicationReports", ApplicationReports);
		hashMap.put("CustomerReports", CustomerReports);
		hashMap.put("EmployeeReports", EmployeeReports);
		hashMap.put("SLAReports",SLAReports);
			
		//Search
		hashMap.put("Search", Search);

		//MyToDoList
		hashMap.put("MyToDoList", MyToDoList);
		
		//Footer
		hashMap.put("FAQ", FAQ);
		hashMap.put("OfficeLocations", OfficeLocations);
		hashMap.put("AboutUS", AboutUS);
		hashMap.put("TermsandConditions", TermsandConditions);
		hashMap.put("PrivacyPolicy", PrivacyPolicy);		
		
		// Configuration Settings
		//hashMap.put("ConfigurationSettings",ConfigurationSettings);
		hashMap.put("HomePage",HomePage);
		hashMap.put("ConfigurationSettings",ConfigurationSettings);
		hashMap.put("LDAPPage",LDAPPage);
		hashMap.put("MailPage",MailPage);
		hashMap.put("FieldPage",FieldPage);
		hashMap.put("AccessPrevileges",AccessPrevileges);
		return hashMap;
	}
}