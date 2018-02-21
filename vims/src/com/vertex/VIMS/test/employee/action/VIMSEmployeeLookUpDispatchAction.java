// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 1/31/2009 6:37:56 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   VIMSEmployeeLookUpDispatchAction.java

package com.vertex.VIMS.test.employee.action;

import com.vertex.VIMS.test.common.ContryStateList;
import com.vertex.VIMS.test.employee.BD.VIMSEmployeeBD;
import com.vertex.VIMS.test.employee.form.NewEmployeeFormBean;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.struts.actions.LookupDispatchAction;

public class VIMSEmployeeLookUpDispatchAction extends LookupDispatchAction
{

	public ActionForward EmployeeFirstPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession(true);

        //String strIssueTypeSelected = "Active";

        /*String strIssueTypeSelected = "Active";

        NewEmployeeFormBean bean = (NewEmployeeFormBean)form;
        //System.out.println("--------Entering into EmployeeFirstPage-------");
        //System.out.println((new StringBuilder("--------strIssueTypeSelected before if---------")).append(bean.getTypeofIssue()).toString());
        /*if(bean.getTypeofIssue() != null)
        {
            strIssueTypeSelected = bean.getTypeofIssue();
           // System.out.println((new StringBuilder("========strIssueTypeSelected=======")).append(strIssueTypeSelected).toString());
            bean.setTypeofIssue(strIssueTypeSelected);
        } else
        if(request.getParameter("status") != null)
        {
            strIssueTypeSelected = request.getParameter("status");
            //System.out.println((new StringBuilder("========strIssueTypeSelected=======")).append(strIssueTypeSelected).toString());
            bean.setTypeofIssue(strIssueTypeSelected);
        } else
        if(request.getAttribute("status") != null)
        {
            strIssueTypeSelected = (String)request.getAttribute("status");
            bean.setTypeofIssue(strIssueTypeSelected);
            //System.out.println((new StringBuilder("=========FormBean value in status==============")).append(strIssueTypeSelected).toString());
        } else
        {
        	//System.out.println("=========testing==========="+strIssueTypeSelected); 
            bean.setTypeofIssue(strIssueTypeSelected);
            //System.out.println((new StringBuilder("=========FormBean value in else==============")).append(strIssueTypeSelected).toString());
        }*/
        //System.out.println("=========testing11==========="+strIssueTypeSelected); 
        java.util.ArrayList employeesDetails = VIMSEmployeeBD.getAllEmployeeDetails();
        session.removeAttribute("Details");
        session.setAttribute("Details", employeesDetails);
        System.out.println("=======Employeee Details======"+employeesDetails);
        return mapping.findForward("testEmployee");
    }

    public ActionForward GoToHomePage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        return mapping.findForward("HomePage");
    }

    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
    	//saveToken(request);
        HttpSession session = request.getSession(false);
        java.util.ArrayList SupervisorDetails = VIMSEmployeeBD.getSupervisorNamesBD();
        session.setAttribute("SupervisorList", SupervisorDetails);
        
        NewEmployeeFormBean bean = (NewEmployeeFormBean)form;
        String strUserID=(String) session.getAttribute("user");//Getting the UserID from the session 
        ArrayList strWorkLocations=(ArrayList)VIMSEmployeeBD.getWorkLocationListBD(strUserID);
        session.setAttribute("WorkLocationList", strWorkLocations);
	 
        ArrayList strPositions=(ArrayList)VIMSEmployeeBD.getPositionListBD(strUserID);
	    session.setAttribute("positionList", strPositions);
	      
	  //Code for Ajax Funtionality
	    ArrayList strCountries=(ArrayList)VIMSEmployeeBD.getCountryListBD();
        session.removeAttribute("countriesList");
        session.setAttribute("countriesList", strCountries);
   	    String strSelCountry = request.getParameter("strSelCountry");

   	    HashMap hashMap=null;

   	     StringBuffer sbuf=new StringBuffer();
        if(strSelCountry!=null) {
        ArrayList strStates=(ArrayList)VIMSEmployeeBD.getStateListBD(strSelCountry);
        //System.out.println("--------States in list----------"+strStates);
        
        	if(strStates!=null && strStates.size()>0){
	        	for(int i=0;i<strStates.size();i++)
	        	{
	            	  hashMap=(HashMap)strStates.get(i); 
	            	  String strStatesNames=(String)hashMap.get("stateNames");
	            	  String strStateNO=(String)hashMap.get("stateNo");
	            	  if(i!=strStates.size()-1)
	           		   {
	           			 sbuf=sbuf.append(strStateNO+":"+strStatesNames+";");
	           		   }
	           		  else
	           		   { 
	           			 sbuf=sbuf.append(strStateNO+":"+strStatesNames);
	           		   }
	    		  }
	        	 session.removeAttribute("statesList");
                 session.setAttribute("statesList", strStates);
        	 }
        	 response.setContentType("text/xml");
     		 response.getWriter().println(sbuf.toString());
        	 
    		 if(request.getParameter("request_from")!=null && request.getParameter("request_from").equalsIgnoreCase("ajaxcall")){
    	        	return null;
    	     }
         }
        
        //Code for Normal funtionality without Ajax 
        /*String strSelCountry = request.getParameter("selected_country");
        //System.out.println("-----------strSelCountry in add method---------"+strSelCountry);
        session.removeAttribute("countriesList");
        session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
        session.removeAttribute("statesList");
        if(strSelCountry != null && strSelCountry.equalsIgnoreCase("India"))
            session.setAttribute("statesList", Arrays.asList(ContryStateList.indstates));
        else
        if(strSelCountry != null && strSelCountry.equalsIgnoreCase("United States of America"))
            session.setAttribute("statesList", Arrays.asList(ContryStateList.usstates));
        else
            session.setAttribute("statesList", null);*/
        
        //ArrayList strCities=(ArrayList)VIMSEmployeeBD.getCityListBD(strUserID);
        //System.out.println("---------strCities-------"+strCities);
        //session.setAttribute("cityList", strCities);
        
        /*session.removeAttribute("positionList");
        session.setAttribute("positionList", Arrays.asList(ContryStateList.positions));*/
        
        return mapping.findForward("addEmployee");
    }

    public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            NewEmployeeFormBean bean = (NewEmployeeFormBean)form;
            String eid = request.getParameter("eid");
            System.out.println("========Employeee id======="+eid);
            
            HttpSession session = request.getSession(false);
            session.setAttribute("Eid",eid);
            java.util.ArrayList SupervisorDetails = VIMSEmployeeBD.getSupervisorNamesBD();
            session.setAttribute("SupervisorList", SupervisorDetails);

            HashMap hashMap = null;
            hashMap = VIMSEmployeeBD.editEmployeeDetail(eid);
            bean.setStrEmployeeID((String)hashMap.get("strEmployeeID"));
            bean.setStrFirstName((String)hashMap.get("strFirstName"));
            bean.setStrLastName((String)hashMap.get("strLastName"));
            bean.setStrMiddleName((String)hashMap.get("strMiddleName"));
            bean.setStrEmployeePosition((String)hashMap.get("strPositionNo"));
            bean.setStrAddress1((String)hashMap.get("strAddress1"));
            bean.setStrAddress2((String)hashMap.get("strAddress2"));
            bean.setStrCity((String)hashMap.get("strCity"));
            bean.setStrState((String)hashMap.get("strStateNo"));
            bean.setStrCountry((String)hashMap.get("strCountryNo"));
            bean.setStrDateOfBirst((String)hashMap.get("strDateOfBirst"));
            bean.setStrJoiningDate((String)hashMap.get("strJoiningDate"));
            bean.setStrEmail((String)hashMap.get("strEmail"));
            bean.setStrWorkPhone((String)hashMap.get("strWorkPhone"));
            bean.setStrSupervisorName((String)hashMap.get("strSupervisorID"));
            bean.setStrMobile((String)hashMap.get("strMobile"));
            bean.setStrStatus((String)hashMap.get("strStatus"));
            bean.setStrQualification((String)hashMap.get("strQualification"));
            bean.setStrWorkLocation((String)hashMap.get("strWorkLocationNo"));
            bean.setStrExperience((String)hashMap.get("strExperience"));
            bean.setStrZip((String)hashMap.get("strZip"));
            
            session.removeAttribute("positionList");
            String strUserID=(String) session.getAttribute("user");//Getting the UserID from the session 
            ArrayList strPositions=(ArrayList)VIMSEmployeeBD.getPositionListBD(strUserID);
            session.setAttribute("positionList", strPositions);
            
            ArrayList strWorkLocations=(ArrayList)VIMSEmployeeBD.getWorkLocationListBD(strUserID);
      	    session.setAttribute("WorkLocationList", strWorkLocations);
            
        //Code without Ajax Functionality
            /* if((hashMap.get("strState") == null || ((String)hashMap.get("strState")).equals("")) 
            		&& (hashMap.get("strCountry") == null || ((String)hashMap.get("strCountry")).equals(""))){
            	
                    session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
                    
            }else if(!((String)hashMap.get("strCountry")).equals(""))
            {
                session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
                if(((String)hashMap.get("strCountry")).equals("United States of America"))
                    session.setAttribute("statesList", Arrays.asList(ContryStateList.usstates));
                if(((String)hashMap.get("strCountry")).equals("India"))
                    session.setAttribute("statesList", Arrays.asList(ContryStateList.indstates));
            }*/
            
            //System.out.println("------aaa--strState----"+hashMap.get("strState")); 
            //System.out.println("------aaa--strCountry----"+hashMap.get("strCountry")); 
//            if((hashMap.get("strState") == null || ((String)hashMap.get("strState")).equals("")) 
//            		&& (hashMap.get("strCountry") == null || ((String)hashMap.get("strCountry")).equals(""))){
//            	
//                    session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
//                    
//            System.out.println("-----------111-------");
//            
//            }else 
    	 	 
            session = request.getSession(false);
            ArrayList strCountries=(ArrayList)VIMSEmployeeBD.getCountryListBD();
            session.removeAttribute("countriesList");
    		session.setAttribute("countriesList", strCountries);
            
            if((String)hashMap.get("strCountry")!=null && (!((String)hashMap.get("strCountry")).equals("")))
            {
            	 
            	 session=request.getSession(false);
            	 String strStatesNames=null;
            	 String strCountry=(String)hashMap.get("strCountryNo");
             	 session.removeAttribute("countriesList");
                 session.setAttribute("countriesList",strCountries);
            	 ArrayList strStates=(ArrayList)VIMSEmployeeBD.getStateListBD(strCountry);
             	 session.removeAttribute("statesList");
                 session.setAttribute("statesList", strStates);
            } 
            //Code without Ajax Functionality
              /* if(((String)hashMap.get("strCountry")).equals("United States of America"))
                 session.setAttribute("statesList", Arrays.asList(ContryStateList.usstates));
            if(((String)hashMap.get("strCountry")).equals("India"))
                 session.setAttribute("statesList", Arrays.asList(ContryStateList.indstates));
            	
            } */
         
         
        }
        catch(Exception exception)
        {
            System.out.println((new StringBuilder("The Exception is:")).append(exception).toString());
        }
        return mapping.findForward("modifyEmployee");
    }

    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession(false);
        String eid = request.getParameter("eid");
        HashMap employeeView = VIMSEmployeeBD.viewEmployeeDetail(eid);
        session.setAttribute("viewEmployeeRecord", employeeView);
        return mapping.findForward("viewEmployee");
    }

    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession();
        String eid = request.getParameter("eid");
        int deleteStatus = VIMSEmployeeBD.deleteEmployeeDetail(eid);
        if(deleteStatus == 1)
        {
            request.setAttribute("status", "Inactive");
            return mapping.findForward("vimsEmployeeAction");
        } else
        {
            request.setAttribute("DeleteFlag", "Employee deletion failed");
            return mapping.findForward("deletionFailure");
        }
    }

    public ActionForward addEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
    	String strReturn = "testEmployee";  
        //System.out.println("-----Token value------"+isTokenValid(request));
       /* if(isTokenValid(request))
        {*/
            //System.out.println("--------Inside isTokenValid--------");
        	//resetToken(request);
            
            ArrayList arrayList = null;
            HttpSession session = request.getSession(false);
           	String strUserID=(String) session.getAttribute("user");//Getting the UserID from the session 
            NewEmployeeFormBean bean = (NewEmployeeFormBean)form;
            String strSelState = request.getParameter("selected_state");
      	    bean.setStrState(strSelState);
            if(request.getParameter("isSubmited") != null && request.getParameter("isSubmited").equalsIgnoreCase("yes"))
            {
                int insertSuccess = VIMSEmployeeBD.addEmployee(bean,strUserID);
                if(insertSuccess == 0)
                {
                    request.setAttribute("AddingSuccess", "Employee added successfully");
                    strReturn = "addingEmployeeSuccess";
                } 
                else
                {
                    request.setAttribute("AddingSuccess", "Employee addition failed");
                    strReturn = "addingEmployeeFailure";
                }
            }
        //}
        return mapping.findForward("vimsEmployeeAction");
    }

    public ActionForward saveEditedEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession(false);
        java.util.ArrayList employeesDetails = null;
        String strReturn = "testEmployee";
        String strEID=(String)session.getAttribute("Eid");
        String strUserID=(String) session.getAttribute("user");//Getting the UserID from the session 
        NewEmployeeFormBean bean = (NewEmployeeFormBean)form;
        String strSelState = request.getParameter("selected_state");
        
        System.out.println("========Selected State in edit employeee====="+strSelState);
        bean.setStrState(strSelState);
  	    
        if(request.getParameter("isSubmited") != null && request.getParameter("isSubmited").equalsIgnoreCase("yes"))
        {
            int insertSuccess = VIMSEmployeeBD.modifyEmployee(bean,strUserID,strEID);
            if(insertSuccess == 1)
            {
                request.setAttribute("status", request.getParameter("status"));
                request.setAttribute("ModificationSuccess", "Employee details updated successfully");
            } else
            {
                request.setAttribute("ModificationSuccess", "Employee details updation failed");
            }
        }
        return mapping.findForward("vimsEmployeeAction");
    }

    public ActionForward checkEmployeeEmailID(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            NewEmployeeFormBean bean = (NewEmployeeFormBean)actionform;
            String strEmailID = request.getParameter("Email");
            String strEmployeeID = request.getParameter("EmpID");
            String strMsg = "";
            if(strEmailID != null)
            {
                boolean result = VIMSEmployeeBD.checkEmployeeEmailIdBD(strEmailID);
                if(result)
                    strMsg = "E-mail ID already exists";
                else
                    strMsg = "ok";
            } else
            {
                boolean result = VIMSEmployeeBD.checkEmployeeIdBD(strEmployeeID);
                if(result)
                    strMsg = "E-mail ID already exists";
                else
                    strMsg = "ok";
            }
            request.setAttribute("MSG", "E-mail Already Exists");
            response.setContentType("text/xml");
            response.getOutputStream().println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
            response.getOutputStream().println("<response>");
            response.getOutputStream().println((new StringBuilder("<result>")).append(strMsg).append("</result>").toString());
            response.getOutputStream().println("</response>");
        }
        catch(Exception exception)
        {
            System.out.println((new StringBuilder("The Exception is:")).append(exception).toString());
            return null;
        }
        return null;
    }

    public ActionForward changeCountry(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest request, HttpServletResponse response)
    {
    	     	
      HttpSession session = null;
      HashMap hashMap=null; 
        try
        {
            /*Ajax Functionality before dumping with DataBase values
             * 
        	session = request.getSession(false);
            String strSelCountry = request.getParameter("country");
                      
            if(strSelCountry == null)
                strSelCountry = (String)request.getAttribute("country");
            session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
            if(!strSelCountry.equals(""))
            {
                //ArrayList strList=(ArrayList)Arrays.asList(ContryStateList.countries);
                //System.out.println("-----------strList---------"+strList);
            	session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
                if(strSelCountry.equals("United States of America"))
                    session.setAttribute("statesList", Arrays.asList(ContryStateList.usstates));
            
                //ArrayList strIndiaStates=(ArrayList)Arrays.asList(ContryStateList.usstates);
                //System.out.println("-----------strIndiaStates---------"+strIndiaStates);
            
                if(strSelCountry.equals("India"))
                    session.setAttribute("statesList", Arrays.asList(ContryStateList.indstates));
                
               //ArrayList strUSStates=(ArrayList)Arrays.asList(ContryStateList.indstates);
                //System.out.println("-----------strUSStates---------"+strUSStates);
            
            }*/
            /* else if(!((String)hashMap.get("strCountry")).equals(""))
         	{
         		System.out.println("---------In Country not null block-----------");
         		session = request.getSession(false);
         		session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
                 if(((String)hashMap.get("strCountry")).equals("United States of America"))
                     session.setAttribute("statesList", Arrays.asList(ContryStateList.usstates));
                 if(((String)hashMap.get("strCountry")).equals("India"))
                     session.setAttribute("statesList", Arrays.asList(ContryStateList.indstates));
                 System.out.println("-----------222-------");
             }
             */
            // session.removeAttribute("countriesList");
            // session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
       
          //Code for Ajax Funtionality
        	ArrayList strCountries=(ArrayList)VIMSEmployeeBD.getCountryListBD();
            session = request.getSession(false);
            session.setAttribute("countriesList", strCountries);
        	
    	    String strSelCountry1 = request.getParameter("strSelCountry");
       	  	StringBuffer sbuf=new StringBuffer();
             
             if((strSelCountry1!=null)&&(!strSelCountry1.equals(""))) {
             	
             ArrayList strStates=(ArrayList)VIMSEmployeeBD.getStateListBD(strSelCountry1);
          	 if(strStates!=null && strStates.size()>0){
	        	for(int i=0;i<strStates.size();i++)
	        	{
	      	
	        		  hashMap=(HashMap)strStates.get(i); 
	        		  String strStateNO=(String)hashMap.get("stateNo");
	            	  String strStatesNames=(String)hashMap.get("stateNames");
	                  
	            	  if(i!=strStates.size()-1)
	           		   {
	           			 sbuf=sbuf.append(strStateNO+":"+strStatesNames+";");
	           		   }
	           		  else
	           		   { 
	           			 sbuf=sbuf.append(strStateNO+":"+strStatesNames);
	           		   }
             	 }
             
             	 response.setContentType("text/xml");
          		 response.getWriter().println(sbuf.toString());
             
         		 if(request.getParameter("request_from")!=null && request.getParameter("request_from").equalsIgnoreCase("ajaxcall")){
         	       return null;
         		 }
             }
             else if((hashMap.get("strState") == null || ((String)hashMap.get("strState")).equals("")) 
            		&& (hashMap.get("strCountry") == null || ((String)hashMap.get("strCountry")).equals(""))){
         	   
         	session.setAttribute("countriesList", Arrays.asList(ContryStateList.countries));
        	strSelCountry1 = request.getParameter("strSelCountry");
        	    
             if(strSelCountry1!=null)
             {
            	 strStates=(ArrayList)VIMSEmployeeBD.getStateListBD(strSelCountry1);
             	
            	 if(strStates!=null && strStates.size()>0)
             	  {
             		for(int i=0;i<strStates.size();i++)
    	        	{
    	        		  hashMap=(HashMap)strStates.get(i); 
    	            	  String strStatesNames=(String)hashMap.get("stateNames");
    		            	 
    	            	  if(i!=strStates.size()-1)
    	           		   {
    	           			 sbuf=sbuf.append(strStatesNames+";");
    	           		   }
    	           		  else
    	           		   { 
    	           			 sbuf=sbuf.append(strStatesNames);
    	           		   }
                 	 }
             	 }
             
             	 response.setContentType("text/xml");
          		 response.getWriter().println(sbuf.toString());
             
         		 if(request.getParameter("request_from")!=null && request.getParameter("request_from").equalsIgnoreCase("ajaxcall")){
          	        return null;
         		 }
             }
            }          
          }
       }
        catch(Exception exception)
        {
            System.out.println((new StringBuilder("The Exception is:")).append(exception).toString());
        }
        return actionmapping.getInputForward();
    }
    
    public ActionForward employeeSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession(false);
        NewEmployeeFormBean bean = (NewEmployeeFormBean)form;
        String strEmpSearch=(String)bean.getEmpSearch();
        if(strEmpSearch==null || "".equals(strEmpSearch)){
        	strEmpSearch=null;   
	       } 
        
	    String strStatus=(String)bean.getTypeofIssue();
        ArrayList employeeSearchList = VIMSEmployeeBD.searchEmployeeBD(strEmpSearch,strStatus);
        
        
        if((employeeSearchList!=null)&&(employeeSearchList.size()>0))
        {
        	session.removeAttribute("Details");
        	session.setAttribute("Details", employeeSearchList);
            
        }
        else
        {
        	session.removeAttribute("Details");
        	session.setAttribute("SearchResult", "No Records Found");
        }
         return mapping.findForward("testEmployee");
    }
    

    protected Map getKeyMethodMap()
    {
        Map map = new HashMap();
        map.put("Employee.buttonAdd", "add");
        map.put("Employee.buttonModify", "modify");
        map.put("Employee.buttonDelete", "delete");
        map.put("Employee.addNewEmployee", "addEmployee");
        map.put("Employee.editNewEmployee", "saveEditedEmployee");
        map.put("Employee.ViewAndComeBack", "GoToHomePage");
        map.put("Employee.ViewEmployeeDetails", "view");
        map.put("Employee.ExportToExcel", "Exporting");
        map.put("Employee.EmployeeFirstPage", "EmployeeFirstPage");
        map.put("VIMSApplication.viewAddEmployeeEmailIDCheck", "checkEmployeeEmailID");
        map.put("Employee.changeCountry", "changeCountry");
        map.put("Employee.employeeSearch", "employeeSearch");
        
        return map;
    }
    
}