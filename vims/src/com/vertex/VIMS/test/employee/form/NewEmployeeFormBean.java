// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 1/31/2009 6:41:24 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   NewEmployeeFormBean.java

package com.vertex.VIMS.test.employee.form;

import java.io.PrintStream;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

public class NewEmployeeFormBean extends ActionForm
{

     

    public String getStrEmployeeID()
    {
        return strEmployeeID;
    }

    public void setStrEmployeeID(String strEmployeeID)
    {
        this.strEmployeeID = strEmployeeID;
    }

    public String getStrEmployeePosition()
    {
        return strEmployeePosition;
    }

    public void setStrEmployeePosition(String strEmployeePosition)
    {
        this.strEmployeePosition = strEmployeePosition;
    }

    public String getStrFirstName()
    {
        return strFirstName;
    }

    public void setStrFirstName(String strFirstName)
    {
        this.strFirstName = strFirstName;
    }

    public String getStrSupervisorName()
    {
        return strSupervisorName;
    }

    public void setStrSupervisorName(String strSupervisorName)
    {
        this.strSupervisorName = strSupervisorName;
    }

    public String getStrMiddleName()
    {
        return strMiddleName;
    }

    public void setStrMiddleName(String strMiddleName)
    {
        this.strMiddleName = strMiddleName;
    }

    public String getStrJoiningDate()
    {
        return strJoiningDate;
    }

    public void setStrJoiningDate(String strJoiningDate)
    {
        this.strJoiningDate = strJoiningDate;
    }

    public String getStrLastName()
    {
        return strLastName;
    }

    public void setStrLastName(String strLastName)
    {
        this.strLastName = strLastName;
    }

    public String getStrWorkLocation()
    {
        return strWorkLocation;
    }

    public void setStrWorkLocation(String strWorkLocation)
    {
        this.strWorkLocation = strWorkLocation;
    }

    public String getStrWorkPhone()
    {
        return strWorkPhone;
    }

    public void setStrWorkPhone(String strWorkPhone)
    {
        this.strWorkPhone = strWorkPhone;
    }

    public String getStrQualification()
    {
        return strQualification;
    }

    public void setStrQualification(String strQualification)
    {
        this.strQualification = strQualification;
    }

    public String getStrHomePhone()
    {
        return strHomePhone;
    }

    public void setStrHomePhone(String strHomePhone)
    {
        this.strHomePhone = strHomePhone;
    }

    public String getStrMobile()
    {
        return strMobile;
    }

    public void setStrMobile(String strMobile)
    {
        this.strMobile = strMobile;
    }

    public String getStrDateOfBirst()
    {
        return strDateOfBirst;
    }

    public void setStrDateOfBirst(String strDateOfBirst)
    {
        this.strDateOfBirst = strDateOfBirst;
    }

    public String getStrExperience()
    {
        return strExperience;
    }

    public void setStrExperience(String strExperience)
    {
        this.strExperience = strExperience;
    }

    public String getStrAddress1()
    {
        return strAddress1;
    }

    public void setStrAddress1(String strAddress1)
    {
        this.strAddress1 = strAddress1;
    }

    public String getStrState()
    {
        return strState;
    }

    public void setStrState(String strState)
    {
        this.strState = strState;
    }

    public String getStrCountry()
    {
        return strCountry;
    }

    public void setStrCountry(String strCountry)
    {
        this.strCountry = strCountry;
    }

    public String getStrCity()
    {
        return strCity;
    }

    public void setStrCity(String strCity)
    {
        this.strCity = strCity;
    }

    public String getStrZip()
    {
        return strZip;
    }

    public void setStrZip(String strZip)
    {
        this.strZip = strZip;
    }

    public String getStrEmail()
    {
        return strEmail;
    }

    public void setStrEmail(String strEmail)
    {
        this.strEmail = strEmail;
    }

    public String getStrAddress2()
    {
        return strAddress2;
    }

    public void setStrAddress2(String strAddress2)
    {
        this.strAddress2 = strAddress2;
    }

    public String getStrStatus()
    {
        return strStatus;
    }

    public void setStrStatus(String strStatus)
    {
        //System.out.println((new StringBuilder("-------------strStatus in form-----------")).append(strStatus).toString());
        this.strStatus = strStatus;
    }

    public String getCheck()
    {
        return check;
    }

    public void setCheck(String check)
    {
        this.check = check;
    }

    public String getSubType()
    {
        return subType;
    }

    public void setSubType(String subType)
    {
        this.subType = subType;
    }

    public String getTypeofIssue()
    {
        return typeofIssue;
    }

    public void setTypeofIssue(String typeofIssue)
    {
        this.typeofIssue = typeofIssue;
    }
    public String getEmpSearch() {
		return empSearch;
	}

	public void setEmpSearch(String empSearch) {
		this.empSearch = empSearch;
	}

    public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest request)
    {
        ActionErrors actionErrors = new ActionErrors();
        String strHidden = request.getParameter("subType");
        if(subType != null && subType.equalsIgnoreCase("Submit"))
        {
            if(strLastName != null)
                if(strLastName.equals("") || strLastName.length() == 0)
                    actionErrors.add("Lastname.size", new ActionMessage("LastName.empty"));
                else
                if(strLastName.length() > 0)
                {
                    boolean b = Pattern.matches("^[a-z A-Z]+", strLastName);
                    if(!b)
                        actionErrors.add("Lastname.size", new ActionMessage("LastName.size"));
                }
            if(strMiddleName != null)
            {
                if(strMiddleName.length() > 0)
                {
                    boolean b = Pattern.matches("^[a-z A-Z]+", strMiddleName);
                    if(!b)
                        actionErrors.add("strMiddleName.size", new ActionMessage("MiddleName.size"));
                }
            }
            if(strFirstName != null)
                if(strFirstName.equals("") || strFirstName.length() == 0)
                    actionErrors.add("FirstName.size", new ActionMessage("FirstName.emtpy"));
                else
                if(strFirstName.length() > 0)
                {
                    boolean b = Pattern.matches("^[a-z A-Z]+", strFirstName);
                    if(!b)
                        actionErrors.add("FirstName.size", new ActionMessage("FirstName.size"));
                }
            if(strEmail != null)
                if(strEmail.equals("") || strEmail.length() == 0)
                    actionErrors.add("Email error", new ActionMessage("Email.empty"));
                else
                if(strEmail.length() > 0)
                {
                    String strEmailID = "^[^0-9!@#$%\\^&*()~+=/.><|{}\\[\\]\\\\?:;\"'][a-zA-Z0-9._]+@[a-zA-Z]+\\.[c][o][m]$";
                    boolean b = Pattern.matches(strEmailID, strEmail);
                    if(!b)
                        actionErrors.add("Email error", new ActionMessage("emailID.error"));
                }
            if(strEmployeePosition != null && (strEmployeePosition.equals("") || strEmployeePosition.length() == 0))
                actionErrors.add("position.size", new ActionMessage("EmployeePostion.empty"));
            if(strEmployeeID != null){
                if(strEmployeeID.equals("") || strEmployeeID.length() == 0)
                    actionErrors.add("employeeid.size", new ActionMessage("EmployeeID.empty"));
                else
                if(strEmployeeID.length() <= 16)
                {
                    boolean b = Pattern.matches("^[a-zA-Z0-9]+$", strEmployeeID);
                    if(!b)
                        actionErrors.add("employeeid.size", new ActionMessage("EmployeeID.size"));
                } else
                if(strEmployeeID.length() > 16)
                    actionErrors.add("employeeid.size", new ActionMessage("EmployeeID.size"));
            }
            
            if(strWorkPhone != null)
            {
                if(strWorkPhone.equals("") || strWorkPhone.length() == 0)
                    actionErrors.add("PhoneNO.Error", new ActionMessage("Phone Number is required", false));
                else if(strWorkPhone.length()>0)
            	{
        	       boolean b = Pattern.matches("^[0-9]+[-][0-9]+$", strWorkPhone); 
                   if(b==false)
        		    {
                	   boolean regExp = Pattern.matches("^[0-9]+[-][0-9]+[-][0-9]+$", strWorkPhone); 
                       if(regExp==false)
                       {
                	     actionErrors.add("PhoneNO.Error", new ActionMessage("phoneNo2.error"));
                       }
        		   }
                   
                }
            }
           
            if(strWorkLocation != null)
            {
                if(strWorkLocation.equals("") || strWorkLocation.length() == 0)
                    actionErrors.add("worklocation error", new ActionMessage("Work Location is required", false));
                /*else if(strWorkLocation.equalsIgnoreCase("Cincinatti") || strWorkLocation.equalsIgnoreCase("Cleveland"))
                {
                    boolean b = Pattern.matches("^[0-9]{3}[-][0-9]{3}[-][0-9]{4}$", strWorkPhone);
                    if(!b)
                        actionErrors.add("WorkPhone error", new ActionMessage("phoneNo.error"));
                } else if(strWorkLocation.equalsIgnoreCase("Hyderabad"))
                {
                    boolean b = Pattern.matches("^[0-9]{3}[-][0-9]{8}$", strWorkPhone);
                    //System.out.println("---------"+b);
                    if(!b)
                        actionErrors.add("WorkPhone error", new ActionMessage("phoneNo1.error"));
                }*/
            }
            if(strZip != null && strZip.length() > 0)
            {
                boolean b = Pattern.matches("^[0-9]+$", strZip);
                if(!b)
                    actionErrors.add("strZip.Error", new ActionMessage("Zip Code format is for ex.,500050", false));
            }
            /*if(strCity != null && strCity.length() > 0)
            {
                boolean b = Pattern.matches("^[a-z A-Z]+$", strCity);
                if(!b)
                    actionErrors.add("city.Error", new ActionMessage("City format is for ex.,Hyderabad", false));
            }*/
            
            if(strMobile != null)
            {
                if(strMobile.equals("") || strMobile.length() == 0)
                    actionErrors.add("strMobile.size", new ActionMessage("Mobile.empty"));
                
                else if(strMobile.length()<10 || strMobile.length()>12)
    			{
    				/* boolean b = Pattern.matches("^[0-9]{10}$", mobileNO); 
    				 if(b==false)*/				 
    					actionErrors.add("MobileNO.Error", new ActionMessage("MobileNo.error"));				 
    			}
            }
            
            if(strAddress1 != null)
            {
                if(strAddress1.equals("") || strAddress1.length() == 0)
                    actionErrors.add("strAddress1.size", new ActionMessage("errors.address1"));
            }
            
        }
        if(subType != null && subType.equalsIgnoreCase("Modify"))
        {
        	if(strLastName != null)
                if(strLastName.equals("") || strLastName.length() == 0)
                    actionErrors.add("Lastname.size", new ActionMessage("LastName.empty"));
                else
                if(strLastName.length() > 0)
                {
                    boolean b = Pattern.matches("^[a-z A-Z]+", strLastName);
                    if(!b)
                        actionErrors.add("Lastname.size", new ActionMessage("LastName.size"));
                }
            if(strMiddleName != null)
            {
                if(strMiddleName.length() > 0)
                {
                    boolean b = Pattern.matches("^[a-z A-Z]+", strMiddleName);
                    if(!b)
                        actionErrors.add("strMiddleName.size", new ActionMessage("MiddleName.size"));
                }
            }
            if(strFirstName != null)
                if(strFirstName.equals("") || strFirstName.length() == 0)
                    actionErrors.add("FirstName.size", new ActionMessage("FirstName.emtpy"));
                else
                if(strFirstName.length() > 0)
                {
                    boolean b = Pattern.matches("^[a-z A-Z]+", strFirstName);
                    if(!b)
                        actionErrors.add("FirstName.size", new ActionMessage("FirstName.size"));
                }
            if(strEmail != null)
                if(strEmail.equals("") || strEmail.length() == 0)
                    actionErrors.add("Email error", new ActionMessage("Email.empty"));
                else
                if(strEmail.length() > 0)
                {
                    String strEmailID = "^[^0-9!@#$%\\^&*()~+=/.><|{}\\[\\]\\\\?:;\"'][a-zA-Z0-9._]+@[a-zA-Z]+\\.[c][o][m]$";
                    boolean b = Pattern.matches(strEmailID, strEmail);
                    if(!b)
                        actionErrors.add("Email error", new ActionMessage("emailID.error"));
                }
            if(strEmployeePosition != null && (strEmployeePosition.equals("") || strEmployeePosition.length() == 0))
                actionErrors.add("position.size", new ActionMessage("EmployeePostion.empty"));
            if(strZip != null && strZip.length() > 0)
            {
                boolean b = Pattern.matches("^[0-9]+$", strZip);
                if(!b)
                    actionErrors.add("strZip.Error", new ActionMessage("Zip Code format is for ex.,500050", false));
            }
            /*if(strCity != null && strCity.length() > 0)
            {
                boolean b = Pattern.matches("^[a-z A-Z]+$", strCity);
                if(!b)
                    actionErrors.add("city.Error", new ActionMessage("City format is for ex.,Hyderabad", false));
            }*/
            
            if(strWorkPhone != null)
            {
                if(strWorkPhone.equals("") || strWorkPhone.length() == 0)
                    actionErrors.add("PhoneNO.Error", new ActionMessage("Work Phone is required", false));
                else if(strWorkPhone.length()>0)
            	{
        	       boolean b = Pattern.matches("^[0-9]+[-][0-9]+$", strWorkPhone); 
                   if(b==false)
        		    {
                	   boolean regExp = Pattern.matches("^[0-9]+[-][0-9]+[-][0-9]+$", strWorkPhone); 
                       if(regExp==false)
                       {
                	     actionErrors.add("PhoneNO.Error", new ActionMessage("phoneNo2.error"));
                       }
        		   }
                   
                }
            }
            
            if(strWorkLocation != null){
                if(strWorkLocation.equals("") || strWorkLocation.length() == 0)
                    actionErrors.add("worklocation error", new ActionMessage("Work Location is required", false));
                /*else if(strWorkLocation.equalsIgnoreCase("Cincinatti") || strWorkLocation.equalsIgnoreCase("Cleveland"))
                {
                    boolean b = Pattern.matches("^[0-9]{3}[-][0-9]{3}[-][0-9]{4}$", strWorkPhone);
                    if(!b)
                        actionErrors.add("WorkPhone error", new ActionMessage("phoneNo.error"));
                } else if(strWorkLocation.equalsIgnoreCase("Hyderabad"))
                {
                    boolean b = Pattern.matches("^[0-9]{3}[-][0-9]{8}$", strWorkPhone);
                    if(!b)
                        actionErrors.add("WorkPhone error", new ActionMessage("phoneNo1.error"));
                }*/
            }
            if(strMobile != null)
            {
                if(strMobile.equals("") || strMobile.length() == 0)
                    actionErrors.add("strMobile.size", new ActionMessage("Mobile.empty"));
                
                else if(strMobile.length()<10 || strMobile.length()>12)
    			{
    				/* boolean b = Pattern.matches("^[0-9]{10}$", mobileNO); 
    				 if(b==false)*/				 
    					actionErrors.add("MobileNO.Error", new ActionMessage("MobileNo.error"));				 
    			}
            }
             
             if(strAddress1 != null)
             {
                 if(strAddress1.equals("") || strAddress1.length() == 0)
                     actionErrors.add("strAddress1.size", new ActionMessage("errors.address1"));
             }
        }
        return actionErrors;
    }

    String strEmployeeID;
    String strEmployeePosition;
    String strFirstName;
    String strSupervisorName;
    String strMiddleName;
    String strJoiningDate;
    String strLastName;
    String strWorkLocation;
    String strWorkPhone;
    String strQualification;
    String strHomePhone;
    String strMobile;
    String strDateOfBirst;
    String strExperience;
    String strAddress1;
    String strState;
    String strCountry;
    String strCity;
    String strZip;
    String strEmail;
    String strAddress2;
    String strStatus;
    String check;
    String subType;
    String typeofIssue;
    String empSearch;
	
}