package com.vertex.VIMS.test.supportcenter.BD;

import java.util.ArrayList;

import com.vertex.VIMS.test.supportcenter.DAO.SupportCenterDAO;
import com.vertex.VIMS.test.supportcenter.form.SupportCenterForm;
import com.vertex.VIMS.test.supportcenter.form.SupportCenterNewActionForm;

public class SupportCenterBD
{

	public static ArrayList getActiveSupportCenters(String strLoginUser)
	{
		ArrayList supportcenters=new ArrayList();
		
		supportcenters=SupportCenterDAO.getActiveSupportCenters(strLoginUser);
		return supportcenters;
	}

	public static ArrayList getGroupsDetails(SupportCenterForm form)
	{
		ArrayList groups=SupportCenterDAO.getGroupDetails(form);
		
		return groups;
	}

	public static String getSupportCenterManager(SupportCenterForm form)
	{
		String supportcentermanager=SupportCenterDAO.getSupportCentermanager(form);
		return supportcentermanager;
	}

	public static ArrayList getGroupsMemberDetails(SupportCenterForm form)
	{
		ArrayList GroupMembersDetails=SupportCenterDAO.getGroupMemberDetails(form);
		return GroupMembersDetails;
	}

	public static String getGroupSupervisorName(SupportCenterForm form)
	{
		String GroupSupervisorName=SupportCenterDAO.getGroupSupervisorName(form);
		return GroupSupervisorName;
	}

	public static ArrayList getEmployees()
	{
		ArrayList EmployeesList=SupportCenterDAO.getEmployeesList();
		return EmployeesList;
	}

	public static int AddNewSupportCenter(String strLoginUser, SupportCenterNewActionForm form)
	{
		int intResult=SupportCenterDAO.AddNewSupportCenter(strLoginUser,form);
		return intResult;
	}

	public static int addGroup(String strLoginUser, SupportCenterForm form, String[] strEmployees)
	{
		int intResult=SupportCenterDAO.addGroup(strLoginUser,form,strEmployees);
		return intResult;
	}

	public static ArrayList getExistingGroups()
	{
		ArrayList ExistingGroups=new ArrayList();
		
		return ExistingGroups=SupportCenterDAO.getExistingGroups();
		
	}

	public static ArrayList getSelectedGroupDetails(String strSelectedGroupID)
	{
		ArrayList strSelectedGroupDetails=new ArrayList();
		strSelectedGroupDetails=SupportCenterDAO.getSelectedDetails(strSelectedGroupID);
		return strSelectedGroupDetails;
	}

	public static ArrayList getGroupEmployees(String strSelectedGroupID)
	{
		ArrayList GroupEmployees=SupportCenterDAO.getGroupEmployees(strSelectedGroupID);
		
		return GroupEmployees;
	}

	public static int ModifySupportCenterGroup(String strLoginUser,String strSelectedGroupID, SupportCenterForm form)
	{
		int intResult=SupportCenterDAO.ModifySupportCenterGroup(strLoginUser,strSelectedGroupID,form);
		return intResult;
	}

	public static ArrayList getSupportCenterDetails(
			String strSelectedSupportcenter)
	{
	   ArrayList SupportCenterDetails=SupportCenterDAO.getSupportCenterDetails(strSelectedSupportcenter);
		return SupportCenterDetails;
	}

	public static int SupportCenterModify(String strLoginUser, SupportCenterNewActionForm form)
	{
		int intMessage=SupportCenterDAO.SupportCenterModify(strLoginUser,form);
		
		return intMessage;
	}

	public static ArrayList getHigherDesgEmployees(String strPage)
	{
		ArrayList SupervisorEmployees=new ArrayList();
		
		SupervisorEmployees= SupportCenterDAO.getHigherDesgEmployees(strPage);
		
		return SupervisorEmployees;
	}

	public static ArrayList getAllSupportCenters()
	{
		ArrayList AllSupportCenters=new ArrayList();
		AllSupportCenters=SupportCenterDAO.getAllSupportCenters();
		return AllSupportCenters;

	}

	public static boolean checkSupportCenterAvailability(
			String strSupportCenterName)
	{
		boolean result=SupportCenterDAO.checkSupportCenterAvailability(strSupportCenterName);
		return result;
	}

	public static boolean checkGroupAvailability(String strGroupName)
	{
		boolean result=SupportCenterDAO.checkGroupAvailability(strGroupName);
		return result;
	}

	public static ArrayList getEmployeesNotinGroup(String strSelectedGroupID)
	{
		ArrayList EmployeesNotinGroup=new ArrayList();
		EmployeesNotinGroup=SupportCenterDAO.getEmployeesNotinGroup(strSelectedGroupID);
		return EmployeesNotinGroup;
	}

	public static ArrayList ViewGroups(String LoginUser)
	{
		ArrayList GroupsList=new ArrayList();
		GroupsList=SupportCenterDAO.ViewGroups(LoginUser);
		
		return GroupsList;
	}

	public static ArrayList ViewSupportCenters(String strLoginUser)
	{
		ArrayList SupportCenterList=new ArrayList();
		
		SupportCenterList=SupportCenterDAO.ViewSupportCenters(strLoginUser);
		return SupportCenterList;
	}

	public static ArrayList SupportCenterSearch(String strLoginUser,
			String strSearchString)
	{
		ArrayList SearchSupportCenter=new ArrayList();
		
		SearchSupportCenter=SupportCenterDAO.SupportCenterSearch(strLoginUser,strSearchString);
		return SearchSupportCenter;
	}

	public static ArrayList SearchGroups(String strLoginUser,
			String strStatusSelected, String strSearchGroup)
	{
		ArrayList SearchGroupsList=new ArrayList();
		SearchGroupsList=SupportCenterDAO.SearchGroups(strLoginUser,strStatusSelected,strSearchGroup);
		return SearchGroupsList;
	}

	public static int DeleteGroup(String strGroupID, String strLoginUser)
	{
		return SupportCenterDAO.DeleteGroup(strGroupID,strLoginUser);
	}

	public static int DeleteSupportCenter(String strSupportCenterID,
			String strLoginUser)
	{
		return SupportCenterDAO.DeleteSupportCenter(strSupportCenterID,strLoginUser);
	}

	public static boolean checkSupport(String strSupportCenterID,
			String strSupportCenterName)
	{
		return SupportCenterDAO.checkSupport(strSupportCenterID,strSupportCenterName);
		
	}




//	public static ArrayList getGroupsDetails()
//	{
//		ArrayList groups=SupportCenterDAO.getGroups();
//		return groups;
//	}

}
