package com.vertex.VIMS.test.common;

import java.util.HashMap;

public class ContryStateList {

	
	public static String[] countries={"India","United States of America"};
	
	
	
	public static String[] usstates={"Alabama", "Alaska",
        "Arizona", "Arkansas","California", "Colorado",
        "Connecticut","Delaware", "District of Columbia", "Florida", "Georgia", 
        "Hawaii","Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
        "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
        "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",
        "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York",
        "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon",
        "Pennsylvania", "Rhode Rsland", "South Carolina", "South Dakota",
        "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington",
        "West Virginia", "Wisconsin", "Wyoming" };
	
	public static String[] indstates={"Andaman Nicobar","Andhra Pradesh",
		"Arunanchal Pradesh","Assam","Bihar","Chandigarh","Chattisgarh","Dadar & Nagar Haveli",
		"Daman & Diu","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu & Kashmir","Jharkhand",
		"Karnataka","Kerala","Lakshwdeep","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram",
		"Nagaland","New Delhi","Orissa","Punjab","Pondicherry","Rajasthan","Sikkim","Tamil Nadu","Tripura",
		"Uttar Pradesh","Uttaranchal","West Bengal"};
	
/*	public static String[] positions={"Accounts Executive",	"Asst.Manager- Accounts","Database Programmer",
		"Director HR","Director-Product Development","English Trainer","Executive Operations","Front office,Admin & PRO",
		"HR Recruiter","Java Analyst","Jr Engineer","Jr Programmer","Jr.BDE","Jr.Exe - Business Dev.",
	    "Junior Engineer","Junior Programmer","Manager - Accounts and Operations","Manager - Marketing",
	    "Manager - Recruitments","Manager - RPF","Oracle DBA","President","Program Manager","Program Manager CMMi",
	    "Project Lead","Project Manager","Quality Analyst","Recruiter",	"Recruitment - Lead","Senior Dotnet Programmer",
	    "Senior Finance Executive","Senior HR Executive","Senior Software Engineer","Senior SQL DBA","Senior Test Engineer",
    	"Shift Lead","SIEBEL Consultant","Software Engineer","Software Programmer",	"SQL DBA","Sr. Test Lead","Sr.HR Executive",
	    "Sr.Software Programmer","Sr.System Administrator",	"Sys Admin Manager - CISO",	"System Adminstrator","Team Lead",
	    "Team Lead - DBA","Team Lead - System Administration","Technical Writer","Test Developer","Test Engineer",
	    "Test Lead","Trainee - Project Management Office","Trainee - Proposal Writer","Trainee-Software Engineer",
	    "Trainee-Software Programmer","VP Projects","VP Technical",	"Web Designer"};
	*/
	
	public static String[] getStatesList(String strCountry)
	{
		//System.out.println("------------strCountry in ContryStateList------"+strCountry);
		/*HashMap hashMap=new HashMap();
			
		hashMap.put("indstates", indstates);
		hashMap.put("usstates", usstates);*/
		
		
		String []statesList=null;
		
		if(strCountry.equalsIgnoreCase("India"))
		{
			statesList=indstates;
			for(int i=0;i<statesList.length;i++)
			{
				//System.out.println("-----------statesList--------"+statesList[i]);
			}
			
		}
		else if(strCountry.equalsIgnoreCase("United States of America")) 
		{
			statesList=usstates;
			for(int i=0;i<statesList.length;i++)
			{
				//System.out.println("-----------statesList--------"+statesList[i]);
			}
			
			
		}
		
		return statesList;
	 }
	public static void main(String args[])
	{
		getStatesList("India");
	}
}