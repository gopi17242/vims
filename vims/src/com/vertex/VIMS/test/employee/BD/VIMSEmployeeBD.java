// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 1/31/2009 6:43:23 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   VIMSEmployeeBD.java

package com.vertex.VIMS.test.employee.BD;

import com.vertex.VIMS.test.employee.DAO.VIMSEmployeeDAO;
import com.vertex.VIMS.test.employee.form.NewEmployeeFormBean;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

public class VIMSEmployeeBD
{

    public static ArrayList getAllEmployeeDetails()
    {
        return VIMSEmployeeDAO.getAllEmployeeDetails();
    }


    public static ArrayList getSupervisorNamesBD()
    {
        return VIMSEmployeeDAO.getSupervisorNames();
    }

    public static int addEmployee(NewEmployeeFormBean employeeBean,String strUserID)
    {
        return VIMSEmployeeDAO.addEmployee(employeeBean,strUserID);
    }

    public static HashMap editEmployeeDetail(String eid)
    {
        return VIMSEmployeeDAO.editEmployeeDetail(eid);
    }

    public static int modifyEmployee(NewEmployeeFormBean bean,String strUserID,String strEID)
    {
        return VIMSEmployeeDAO.modifyEmployee(bean,strUserID,strEID);
    }

    public static int deleteEmployeeDetail(String eid)
    {
        return VIMSEmployeeDAO.deleteEmployeeDetail(eid);
    }

    public static String getTargetPath(HttpServletRequest request)
    {
        String subPath = null;
        try
        {
            String absPath = request.getRealPath(request.getContextPath());
            int dotIndex = absPath.indexOf("\\deploy");
            subPath = absPath.substring(0, dotIndex - 1);
            absPath = (new StringBuilder(String.valueOf(subPath))).append(absPath.substring(dotIndex + 1, absPath.length())).toString();
            return absPath;
        }
        catch(Exception exception)
        {
            System.out.println((new StringBuilder("Exception raised in getTargetPath()")).append(exception).toString());
        }
        return null;
    }

    public static boolean checkEmployeeIdBD(String strEmployeeID)
    {
        try
        {
            boolean result = VIMSEmployeeDAO.checkEmployeeIdDAO(strEmployeeID);
            return result;
        }
        catch(Exception exception)
        {
            System.out.println((new StringBuilder("The Exception is:")).append(exception).toString());
        }
        return false;
    }

    public static boolean checkEmployeeEmailIdBD(String strEmailID)
    {
        try
        {
            boolean result = VIMSEmployeeDAO.checkEmployeeEmailIdDAO(strEmailID);
            return result;
        }
        catch(Exception exception)
        {
            System.out.println((new StringBuilder("The Exception is:")).append(exception).toString());
        }
        return false;
    }
    public static HashMap viewEmployeeDetail(String eid)
    {
        return VIMSEmployeeDAO.viewEmployeeDetailDAO(eid);
    }
    public static ArrayList getPositionListBD(String strUserID)
    {
        return VIMSEmployeeDAO.getPositionListDAO(strUserID);
    }
    public static ArrayList searchEmployeeBD(String strEmpSearch, String strStatus)
    {
        return VIMSEmployeeDAO.searchEmployeeDAO(strEmpSearch,strStatus);
    }
    public static ArrayList getCityListBD(String strUserID)
    {
        //System.out.println("------In getCityListBD--------");
    	return VIMSEmployeeDAO.getCityListDAO(strUserID);
        
    }
    public static ArrayList getWorkLocationListBD(String strUserID)
    {
        return VIMSEmployeeDAO.getWorkLocationListDAO(strUserID);
    }

    public static ArrayList getCountryListBD()
    {
        return VIMSEmployeeDAO.getCountryListDAO();
    }
    public static ArrayList getStateListBD(String strSelCountry)
    {
    	return VIMSEmployeeDAO.getStateListDAO(strSelCountry);
    }
   

}