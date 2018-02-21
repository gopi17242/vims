package com.vertex.VIMS.test.supportcenter.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.vertex.VIMS.test.common.DBConnection;
import com.vertex.VIMS.test.supportcenter.form.SupportCenterForm;
import com.vertex.VIMS.test.supportcenter.form.SupportCenterNewActionForm;

public class SupportCenterDAO
{
	
	static Connection connection=null;
	static ResultSet resultset=null;
	static PreparedStatement preparedStatement=null;
	static Statement statement=null;
	static CallableStatement callableStatement=null;
	
	static Logger logger=Logger.getLogger("Admin");
	
	public static ArrayList getActiveSupportCenters(String strLoginUser)
	{
		
		 ArrayList supportcenters=new ArrayList();
		 
		 HashMap hashmap=null;
		 
		 try
		 {
			    connection=DBConnection.createConnection();
				callableStatement=connection.prepareCall("{CALL vims_user.USP_Get_Supp_Cntr_Dtls_All(?,?,?,?,?,?)}");
				callableStatement.setString(1,null);
				callableStatement.setString(2,null);
				callableStatement.setString(3,null);
				callableStatement.setString(4,null);
				callableStatement.setString(5,null);
				callableStatement.setString(6,strLoginUser);
				callableStatement.execute();
				resultset=callableStatement.getResultSet();
				
			 //connection = DBConnection.createConnection();
			//statement = connection.createStatement();
			//select support_center_id,supp_center_name from support_center where active_fg='0' order by supp_center_name
			
			//resultset = statement.executeQuery("");
				
			while (resultset.next())
			{
				hashmap = new HashMap();
				hashmap.put("id", resultset.getString(3));
				hashmap.put("name", resultset.getString(4));
				supportcenters.add(hashmap);
			}
			resultset.close();
			statement.close();
			
		 } 
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		
		return supportcenters;
	}

	public static ArrayList getGroupDetails(SupportCenterForm form)
	{
		 		 
		 ArrayList groups=new ArrayList();
		 
		 HashMap hashmap=null;
		 
		 String strSupportCenter=form.getSupportcenter();
		 
		 try
		 {
			 connection = DBConnection.createConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery("SELECT ug.USRGROUP_ID,ug.GROUP_NAME from vims_user.USER_GROUP ug INNER join vims_user.SUPP_CENTER_GROUP scg ON ug.USRGROUP_ID = scg.USRGROUP_ID WHERE scg.SUPPORT_CENTER_ID ='"+ strSupportCenter + "' order by ug.group_name");
			while (resultset.next())
			{
				hashmap=new HashMap();
				hashmap.put("groupid", resultset.getString(1));
				hashmap.put("groupname", resultset.getString(2));
				
				groups.add(hashmap);

			}
			resultset.close();
			statement.close();
		 } 
		 catch (Exception e)
		 {
			e.printStackTrace();
		 }
		 finally
		 {
			 DBConnection.closeConnection();
		 }
		return groups;
	}

	
	public static String getSupportCentermanager(SupportCenterForm form)
	{
				 
		String supportcentermanager = null;
		 
		 String strSupportCenter=form.getSupportcenter();
		 
		 try
		 {
			 connection = DBConnection.createConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery("select DISTINCT e.FIRST_NAME+' '+ISNULL(e.LAST_NAME,'') AS [Name] from vims_user.EMPLOYEE e INNER join vims_user.support_cntr_manager scm ON e.User_NM=scm.SUP_CNTR_MANAGER where scm.support_center_id='"+ strSupportCenter + "'");
			while (resultset.next())
			{
				supportcentermanager=resultset.getString(1);

			}
			resultset.close();
			statement.close();
		 } 
		 catch (Exception e)
		 {
			logger.error(e);
		 }
		 finally
		 {
			 DBConnection.closeConnection();
		 }
		return supportcentermanager;
		 
		
	}

	public static ArrayList getGroupMemberDetails(SupportCenterForm form)
	{
		 		 
		 ArrayList GroupMeberDetails=new ArrayList();
		 
		 HashMap hashmap=null;
		 
		 String strGroupID=form.getGroups();
		 
		 try
		 {
			 connection = DBConnection.createConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery("select e.employee_id,e.first_name,e.middle_initial,e.last_name,e.department,e.emp_status,e.work_phone,e.work_email_address,e.location from vims_user.group_members g,employee e where g.User_NM=e.User_NM and g.usrgroup_id='"+ strGroupID + "' order by g.User_NM");
			while (resultset.next())
			{
				hashmap = new HashMap();
				hashmap.put("id", resultset.getString(1));
				hashmap.put("firstname", resultset.getString(2));
				hashmap.put("middleinitial", resultset.getString(3));
				hashmap.put("lastname", resultset.getString(4));
				hashmap.put("dept", resultset.getString(5));
				hashmap.put("status", resultset.getString(6));
				hashmap.put("workphone", resultset.getString(7));
				hashmap.put("email", resultset.getString(8));
				hashmap.put("address", resultset.getString(9));
				
				GroupMeberDetails.add(hashmap);
			}
			resultset.close();
			statement.close();
		} 
		catch (Exception e)
		 {
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return GroupMeberDetails;
	}

	public static String getGroupSupervisorName(SupportCenterForm form)
	{
				 
		String groupsupervisor = null;
		
		String strGroupID;
		 
		strGroupID=form.getGroups();
		 
		 try
		 {
			 connection = DBConnection.createConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery("select DISTINCT e.FIRST_NAME+' '+ISNULL(e.LAST_NAME,'') AS [Name] from vims_user.supp_center_group scg INNER join vims_user.Employee e ON e.User_NM=scg.group_supervisor where usrgroup_id='"+strGroupID+"'");
			while (resultset.next())
			{
				groupsupervisor=resultset.getString(1);

			}
			resultset.close();
			statement.close();
		 } 
		 catch (Exception e)
		 {
			logger.error(e);
		 }
		 finally
		 {
			 DBConnection.closeConnection();
		 }
		return groupsupervisor;
		
	}

	public static ArrayList getEmployeesList()
	{
		ArrayList EmployeesList=new ArrayList();
		
			
		 HashMap hashmap=null;
		 
		try
		{
			connection = DBConnection.createConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery("select user_nm,first_name,last_name from vims_user.employee where emp_status='active' order by user_nm");
			while (resultset.next())
			{
				hashmap = new HashMap();
				hashmap.put("empid", resultset.getString(1));
//				
				String name=resultset.getString(2)+" "+resultset.getString(3);
				hashmap.put("name", name);
     			EmployeesList.add(hashmap);
				
			}
			resultset.close();
			statement.close();
		} 
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return EmployeesList;
	}

	public static int AddNewSupportCenter(String strLoginUser, SupportCenterNewActionForm form)
	{
		
		int intResult = 0;
		int intReturnValue1;
		int intReturnValue2;
		
		String strSupportCenterID;
		String strSupportCenterName;
		String strSupportCenterLocation;
		String strStatus;
		String strSupportCenterManager;
		String strResponse=null;
		String strStartDate = null;
		
		//String strSupportStartDate=null;
		//String strSupportEndDate=null;
		
		//String strSupportCenterQuery="insert into support_center(SUPPORT_CENTER_ID,SUPP_CENTER_NAME,LOCATION,STATUS)values(?,?,?,?)";
		//String strSupportCentermanagerQuery="insert into support_cntr_manager(SUPPORT_CENTER_ID,SUP_CNTR_MANAGER,START_DATE,END_DATE,STATUS)values(?,?,?,?,?)";
		
		//strSupportCenterID=form.getSupportcenterid();
		strSupportCenterName=form.getSupportcentername();
		strSupportCenterLocation=form.getSupportcenterlocation();
		strStatus=form.getStatus();
		strSupportCenterManager=form.getSupportmanagers();
		
		//strSupportStartDate=form.getSupportbegindate();
		//strSupportEndDate=form.getSupportenddate();
		
				
		try
		{
			connection = DBConnection.createConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery("select convert(varchar,getDate(),101)");
			while (resultset.next())
			{
				strStartDate = resultset.getString(1);
			}
			resultset.close();
			statement.close();
			
			callableStatement=connection.prepareCall("{?=CALL vims_user.USP_Save_Support_Center(?,?,?,?,?,?,?,?,?)}");
			callableStatement.registerOutParameter(1, Types.OTHER);
			callableStatement.setString(2,"");
			callableStatement.setString(3,strSupportCenterName);
			callableStatement.setString(4,strSupportCenterManager);
			callableStatement.setString(5,strSupportCenterLocation);
			callableStatement.setString(6,strStatus);
			callableStatement.setString(7,strStartDate);
			callableStatement.setString(8,strStartDate);
			System.out.println("=============Insert parameter==========");
			callableStatement.setString(9,"insert");
			callableStatement.setString(10,strLoginUser);
			callableStatement.execute();
			
			intResult=callableStatement.getInt(1);
			callableStatement.close();
			
			
			/*preparedStatement = connection.prepareStatement(strSupportCenterQuery);
			
			preparedStatement.setString(1, strSupportCenterID);
			preparedStatement.setString(2, strSupportCenterName);
			preparedStatement.setString(3, strSupportCenterLocation);
			preparedStatement.setString(4, strStatus);
			
			intReturnValue1 = preparedStatement.executeUpdate();
			
			preparedStatement=null;
			
			preparedStatement=connection.prepareStatement(strSupportCentermanagerQuery);
			
			preparedStatement.setString(1, strSupportCenterID);
			preparedStatement.setString(2, strSupportCenterManager);
			preparedStatement.setString(3, strStartDate);
			preparedStatement.setString(4, strStartDate);
			preparedStatement.setString(5, strStatus);
			
			intReturnValue2 = preparedStatement.executeUpdate();*/
			
			/*if(intReturnValue1==1 && intReturnValue2==1)
			{
				intResult=1;
			}
			else
			{
				intResult=0;
			}
			preparedStatement.close();*/
			
			
		} 
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return intResult;
	}

	public static int addGroup(String strLoginUser, SupportCenterForm form, String[] strEmployees)
	{
		int intResultValue1;
		//int intResultValue2;
		//int intResultValue3 = 0;
		int intReturnValue = 0;
		int intEmployeeCount;
		int i=0;
		
		String strGroupid;
		String strGroupname;
		String strGroupSupportCenterID;
		String strGroupSupervisor;
		String strGroupEmployee;
		String strGroupStatus;
		//String strSupportCenterQuery="insert into user_group(USRGROUP_ID,GROUP_NAME)values(?,?)";
		//String strSupportCentermanagerQuery="insert into supp_center_group(SUPPORT_CENTER_ID,USRGROUP_ID,GROUP_SUPERVISOR)values(?,?,?)";
		//String strGroupMembersQuery="insert into group_members(USRGROUP_ID,USER_NM,STATUS)values(?,?,?)";
				
		//strGroupid=form.getGroupid();
		strGroupname=form.getGroupname();
		strGroupSupportCenterID=form.getGroupsupportcenterid();
		strGroupSupervisor=form.getGroupsupervisors();
		strGroupStatus=form.getGroupstatus();
		
		intEmployeeCount=strEmployees.length;
		
		System.out.println("======Employee Count======"+intEmployeeCount);
		
		try
		{
			strGroupEmployee=strGroupSupervisor;
			
			//strGroupEmployee=(String) strEmployees[0];
			
			while(i<intEmployeeCount)
			{
				strGroupEmployee=strGroupEmployee.concat(","+strEmployees[i]);
				i++;
			}
			
			connection = DBConnection.createConnection();
			callableStatement=connection.prepareCall("{?=CALL vims_user.USP_Save_Group(?,?,?,?,?,?,?,?)}");
			callableStatement.registerOutParameter(1,Types.OTHER);
			callableStatement.setString(2,"");
			callableStatement.setString(3,strGroupname);
			callableStatement.setString(4,strGroupSupportCenterID);
			callableStatement.setString(5,strGroupSupervisor);
			callableStatement.setString(6,strGroupEmployee);
			callableStatement.setString(7,strGroupStatus);
			callableStatement.setString(8,"insert");
			callableStatement.setString(9,strLoginUser);
			
			callableStatement.execute();
			
			intReturnValue=callableStatement.getInt(1);
			
			System.out.println("============intReturnValue============"+intReturnValue);
			
			callableStatement.close();
			/*preparedStatement = connection.prepareStatement(strSupportCenterQuery);
			preparedStatement.setString(1, strGroupid);
			preparedStatement.setString(2, strGroupname);
			intResultValue1 = preparedStatement.executeUpdate();
			
			preparedStatement.close();
			
			preparedStatement=null;
			
			while(i<intEmployeeCount)
			{
				preparedStatement=connection.prepareStatement(strGroupMembersQuery);
				preparedStatement.setString(1,strGroupid);
				preparedStatement.setString(2, strEmployees[i]);
				preparedStatement.setString(3,"Active");
				
				intResultValue3 = preparedStatement.executeUpdate();
				
				i++;
			}
			
			preparedStatement.close();
			
			preparedStatement = null;
			
			preparedStatement = connection.prepareStatement(strSupportCentermanagerQuery);
			preparedStatement.setString(1, strGroupSupportCenterID);
			preparedStatement.setString(2, strGroupid);
			preparedStatement.setString(3, strGroupSupervisor);
			
			intResultValue2 = preparedStatement.executeUpdate();
			
			preparedStatement.close();
			
			if(intResultValue1==1 && intResultValue2==1 && intResultValue3==1)
			{
				intReturnValue=1;
			}
			else
			{
				intReturnValue=0;
			}*/
		} 
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return intReturnValue;
	}

	public static ArrayList getExistingGroups()
	{
		ArrayList ExistingGroups=new ArrayList();
		
		HashMap hashmap=null;
		
		try
		{
			connection = DBConnection.createConnection();
			preparedStatement = connection.prepareStatement("select usrgroup_id,group_name from vims_user.user_group order by group_name");
			
			resultset = preparedStatement.executeQuery();
			
			while(resultset.next())
			{
				hashmap=new HashMap();
				
				hashmap.put("id",resultset.getString(1));
				hashmap.put("name",resultset.getString(2));
				
				ExistingGroups.add(hashmap);
			}
			preparedStatement.close();
			resultset.close();
		} 
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		//System.out.println("=========ExistingGroups========="+ExistingGroups);
		
		return ExistingGroups;
	}

	public static ArrayList getSelectedDetails(String strSelectedGroupID)
	{
		ArrayList SelectedGroupDetails=new ArrayList();
		
		try
		{
			connection = DBConnection.createConnection();
			preparedStatement = connection.prepareStatement("select group_name from vims_user.user_group where usrgroup_id='"+ strSelectedGroupID + "'");
			resultset = preparedStatement.executeQuery();
			
			while(resultset.next())
			{
				SelectedGroupDetails.add(resultset.getString(1));
			}
			preparedStatement.close();
			resultset.close();
			
			preparedStatement=connection.prepareStatement("select support_center_id,group_supervisor from vims_user.supp_center_group where usrgroup_id='"+strSelectedGroupID+"' order by group_supervisor");
			resultset=preparedStatement.executeQuery();
			
			while(resultset.next())
			{
				SelectedGroupDetails.add(resultset.getString(1));
				SelectedGroupDetails.add(resultset.getString(2));
			}
			preparedStatement.close();
			resultset.close();
			
			preparedStatement=connection.prepareStatement("select active_fg from vims_user.supp_center_group where usrgroup_id='"+strSelectedGroupID+"'");
			resultset=preparedStatement.executeQuery();
			while(resultset.next())
			{
				SelectedGroupDetails.add(resultset.getString(1));
			}
			preparedStatement.close();
			resultset.close();
			
		} 
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return SelectedGroupDetails;
	}

	public static ArrayList getGroupEmployees(String strSelectedGroupID)
	{
		ArrayList GroupEmployees=new ArrayList();
		
		HashMap hashmap=null;
		
		try
		{
		 connection = DBConnection.createConnection();
		 preparedStatement = connection.prepareStatement("SELECT e.USER_NM,e.FIRST_NAME+' '+ISNULL(e.MIDDLE_INITIAL,'')+' '+ISNULL(e.LAST_NAME,'') from vims_user.EMPLOYEE e INNER join vims_user.GROUP_MEMBERS gm ON e.User_NM=gm.User_NM WHERE gm.USRGROUP_ID='"+strSelectedGroupID+"' order by e.user_nm");
		 resultset=preparedStatement.executeQuery();
		 
		 while(resultset.next())
		 {
			 hashmap=new HashMap();
			 
			 hashmap.put("empid", resultset.getString(1));
			 hashmap.put("name",resultset.getString(2));
			 
			 GroupEmployees.add(hashmap); 
		 }
		 preparedStatement.close();
		 resultset.close();
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return GroupEmployees;
	}

	public static int ModifySupportCenterGroup(String strLoginUser, String strSelectedGroupID, SupportCenterForm form)
	{
		System.out.println("===========In Support Center DAO============");
		String strGroupId;
		String strGroupName;
		String strSupportCenterID;
		String strSupportCenterSupervisor;
		String strGroupStatus;
		String[] GroupMembers;
		//ArrayList GroupMembers=new ArrayList();
		
		strGroupId=form.getGroupid();
		strGroupName=form.getGroupname();
		strSupportCenterID=form.getGroupsupportcenterid();
		strSupportCenterSupervisor=form.getGroupsupervisors();
		strGroupStatus=form.getGroupstatus();
		GroupMembers=form.getDestemployee();
		
//		String strUserGroupQuery="insert into user_group(USRGROUP_ID,GROUP_NAME)values(?,?)";
//		String strGroupSupervisorQuery="insert into supp_center_group(SUPPORT_CENTER_ID,USRGROUP_ID,GROUP_SUPERVISOR)values(?,?,?)";
//		String strGroupMembersQuery="insert into group_members(USRGROUP_ID,EMPLOYEE_ID,STATUS)values(?,?,?)";
//		String strApplicationGroupModify="update group_application set USRGROUP_ID=? where USRGROUP_id=?";
		
		int intEmployeeCount;
		int intCount=0;
		int i=1;
		String strEmployee=null;
		
		String strResponse=null;
		
		int intResult = 0;
		
		intEmployeeCount=GroupMembers.length;
		
		//strEmployee=strSupportCenterSupervisor;
		strEmployee=(String) GroupMembers[0];
		
		while(i<intEmployeeCount)
		{
			strEmployee=strEmployee.concat(","+GroupMembers[i]);
			i++;
		}
		
		System.out.println("===========strEmployee============"+strEmployee);
		
		try
		{
			connection = DBConnection.createConnection();
			
			callableStatement = connection.prepareCall("{?= CALL vims_user.USP_Save_Group(?,?,?,?,?,?,?,?)}");
			
//			preparedStatement=connection.prepareStatement(strApplicationGroupModify);
//			preparedStatement.setString(1,strGroupId);
//			preparedStatement.setString(2,strSupportCenterID);
//			
//			int ReturnValue6=preparedStatement.executeUpdate();
//			
//			preparedStatement.close();
			
			callableStatement.registerOutParameter(1,Types.OTHER);
			callableStatement.setString(2,strSelectedGroupID);
			callableStatement.setString(3,strGroupName);
			callableStatement.setString(4,strSupportCenterID);
			callableStatement.setString(5,strSupportCenterSupervisor);
			callableStatement.setString(6,strEmployee);
			callableStatement.setString(7,strGroupStatus);
			callableStatement.setString(8,"update");
			callableStatement.setString(9,strLoginUser);
			callableStatement.execute();
			
			intResult=callableStatement.getInt(1);
			
			/*resultset=callableStatement.getResultSet();
			
			while(resultset.next())
			{
				strResponse=resultset.getString(1);
			}
			resultset.close();*/
			callableStatement.close();
		}
		catch (Exception e)
		{
		  logger.error(e);
		  e.printStackTrace();
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return intResult;
	}

	public static ArrayList getSupportCenterDetails(
			String strSelectedSupportcenter)
	{
		ArrayList SupportCenterDetails=new ArrayList();
		
		String strSupportCenterDetailsQuery="select * from vims_user.support_center where support_center_id='"+strSelectedSupportcenter+"' order by supp_center_name";
		//String strWorkLocation="select "
		try
		{
			connection = DBConnection.createConnection();
			preparedStatement = connection.prepareStatement(strSupportCenterDetailsQuery);
			
			resultset=preparedStatement.executeQuery();
			
			while(resultset.next())
			{
				SupportCenterDetails.add(resultset.getString(1));
				SupportCenterDetails.add(resultset.getString(2));
				SupportCenterDetails.add(resultset.getString(3));
				//SupportCenterDetails.add(resultset.getString(4));
				SupportCenterDetails.add(resultset.getString("Location"));
			}
			
			resultset.close();
			preparedStatement.close();
			
			preparedStatement=connection.prepareStatement("select sup_cntr_manager from vims_user.support_cntr_manager where support_center_id='"+strSelectedSupportcenter+"' order by sup_cntr_manager");
			
			resultset=preparedStatement.executeQuery();
			
			while(resultset.next())
			{
				SupportCenterDetails.add(resultset.getString(1));
			}
			resultset.close();
			preparedStatement.close();
		} 
		catch (Exception e)
		{
			logger.error(e);
		}
		return SupportCenterDetails;
	}

	public static int SupportCenterModify(String strLoginUser, SupportCenterNewActionForm form)
	{
		String strSupportCenterID;
		String strSupportCentername;
		String strLocation;
		String strStatus;
		String strManager;
		String strResponse = null;
		
		int intResponse = 0;
		
		//strSupportCenterID=form.getSupportcenterid();
		strSupportCentername=form.getSupportcentername();
		strLocation=form.getSupportcenterlocation();
		strStatus=form.getStatus();
		strManager=form.getSupportmanagers();
		
		String strSelectedSupportCenter=form.getSelSupCen();
//		
		//System.out.println("============strSupportCenterID=========="+strSupportCenterID);
	    System.out.println("============strSupportCentername=========="+strSupportCentername);		System.out.println("============strLocation=========="+strLocation);
		System.out.println("============strStatus=========="+strStatus);
	    System.out.println("============strManager=========="+strManager);
//		
		System.out.println("============strSelectedSupportCenter=========="+strSelectedSupportCenter);
			
		try
		{
			connection = DBConnection.createConnection();
			
			callableStatement = connection.prepareCall("{?= CALL vims_user.USP_Save_Support_Center(?,?,?,?,?,?,?,?,?)}");
			
			callableStatement.registerOutParameter(1,Types.OTHER);
			callableStatement.setString(2,strSelectedSupportCenter);
			callableStatement.setString(3,strSupportCentername);
			callableStatement.setString(4,strManager);
			callableStatement.setString(5,strLocation);
			callableStatement.setString(6,strStatus);
			callableStatement.setString(7,"");
			callableStatement.setString(8,"");
			System.out.println("=============Update parameter==========");
			callableStatement.setString(9,"update");
			callableStatement.setString(10,strLoginUser);
			callableStatement.execute();
			
			intResponse=callableStatement.getInt(1);
			//resultset=callableStatement.getResultSet();
			
			/*while(resultset.next())
			{
				System.out.println("================resultset========="+resultset.getString(1));
				strResponse=resultset.getString(1);
			}
			resultset.close();*/
			
			callableStatement.close();
		
		} 
		catch (Exception e)
		{
			logger.error(e);
		}
		return intResponse;
	}

	public static ArrayList getHigherDesgEmployees(String strPage)
	{
		ArrayList Supervisors=new ArrayList();
		
		int intArgValue = 0;
		
		if(strPage.equalsIgnoreCase("SupportCenter"))
		{
			intArgValue=2;
		}
		if(strPage.equalsIgnoreCase("Groups"))
		{
			intArgValue=3;
		}
		HashMap hashmap=null;
		
		try
		{
			connection = DBConnection.createConnection();
			callableStatement = connection.prepareCall("{CALL vims_user.USP_Get_Employee_Dtls(?,?,?,?)}");
			callableStatement.setInt(1,intArgValue);
			callableStatement.setString(2,null);
			callableStatement.setString(3,"Active");
			callableStatement.setString(4,null);
			callableStatement.execute();
			resultset=callableStatement.getResultSet();
			 
			while(resultset.next())
			{
				hashmap=new HashMap();
				
				hashmap.put("SupervisorID",resultset.getString(7));
				hashmap.put("SupervisorName",resultset.getString(2));
				
				Supervisors.add(hashmap);
			}
		} 
		catch (Exception e)
		{
			logger.error(e);
		}
		return Supervisors;
	}

	public static ArrayList getAllSupportCenters()
	{
		ArrayList supportcenters=new ArrayList();
		 
		 HashMap hashmap=null;
		 
		 try
		 {
			 connection = DBConnection.createConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery("select support_center_id,supp_center_name from vims_user.support_center order by supp_center_name");
			while (resultset.next())
			{
				hashmap = new HashMap();
				hashmap.put("id", resultset.getString(1));
				hashmap.put("name", resultset.getString(2));
				supportcenters.add(hashmap);
			}
			resultset.close();
			statement.close();
			
		 } 
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		
		return supportcenters;
		
	}

	public static boolean checkSupportCenterAvailability(
			String strSupportCenterName)
	{
		boolean result = false;
		Statement statement;
		logger=Logger.getLogger("Admin");
		
		connection=DBConnection.createConnection();
		try
		{
			statement = connection.createStatement();
			resultset = statement.executeQuery("select supp_center_name from vims_user.support_center where supp_center_name='"+ strSupportCenterName + "'");
			result=resultset.next();
			System.out.println("======result========="+result);
			resultset.close();
			statement.close();
			
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return result;
	}

	public static boolean checkGroupAvailability(String strGroupName)
	{
		boolean result = false;
		Statement statement;
		logger=Logger.getLogger("Admin");
		
		connection=DBConnection.createConnection();
		try
		{
			statement = connection.createStatement();
			resultset = statement.executeQuery("select group_name from vims_user.user_group where group_name='"+ strGroupName + "' order by group_name");
			result=resultset.next();
			
			resultset.close();
			statement.close();
			
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return result;
	}

	public static ArrayList getEmployeesNotinGroup(String strSelectedGroupID)
	{
		ArrayList EmployeesNotinGroup=new ArrayList();
		logger=Logger.getLogger("Admin");
		
		HashMap hashmap=null;
		 
		try
		{
			connection = DBConnection.createConnection();
			//System.out.println("==========strSelectedGroupID in DAO ============"+strSelectedGroupID);
			callableStatement=connection.prepareCall("{CALL vims_user.USP_Get_Nt_Grp_Mbrs(?)}");
			callableStatement.setString(1,strSelectedGroupID);
			callableStatement.execute();
			resultset=callableStatement.getResultSet();
			while (resultset.next())
			{
				hashmap = new HashMap();
				//System.out.println("========resultset.getString(1)======="+resultset.getString(1));
				//System.out.println("========resultset.getString(2)======="+resultset.getString(2));
				hashmap.put("empid", resultset.getString(1));
				hashmap.put("name", resultset.getString(2));
				EmployeesNotinGroup.add(hashmap);
			}
			resultset.close();
			callableStatement.close();
		} 
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return EmployeesNotinGroup;
	}

	public static ArrayList ViewGroups(String LoginUser)
	{
		Logger logger=Logger.getLogger("Admin");
		
		ArrayList GroupsList=new ArrayList();
		
		try
		{
			connection=DBConnection.createConnection();
			callableStatement=connection.prepareCall("{CALL vims_user.USP_Get_Group_Dtls(?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1,null);
			callableStatement.setString(2,null);
			callableStatement.setString(3,null);
			callableStatement.setString(4,null);
			callableStatement.setString(5,null);
			callableStatement.setString(6,null);
			callableStatement.setString(7,null);
			callableStatement.setString(8,LoginUser);
			callableStatement.execute();
			resultset=callableStatement.getResultSet();
			
			GroupsList=ListofGroups(resultset);
			
			callableStatement.close();
			resultset.close();
			
		} 
		catch (SQLException e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return GroupsList;
	}

	private static ArrayList ListofGroups(ResultSet resultset)
	{
		Logger logger=Logger.getLogger("Admin");
		
		ArrayList GroupsList=new ArrayList();
		HashMap hashmap=null;
		String linkmodify=null;
		String strStatus=null;
		
		try
		{
			while(resultset.next())
			{
				hashmap=new HashMap();
				hashmap.put("supportcentername",resultset.getString(4)); 
				hashmap.put("groupname",resultset.getString(2));
				hashmap.put("groupmanager",resultset.getString(6));
				hashmap.put("employees",resultset.getString(7));
				if(resultset.getString(8).equalsIgnoreCase("0"))
				{
					strStatus="Active";
					linkmodify="<a href='./EditGroup.do?methodname=getGroupDetails&SelectedGroup="+(String)resultset.getString(1)+"&menuId=Home&pageId=ModifySupportGroup'>Modify</a>";
				}
				else
				{
					strStatus="Inactive";
					linkmodify="<a href='./EditGroup.do?methodname=getGroupDetails&SelectedGroup="+(String)resultset.getString(1)+"&menuId=Home&pageId=ModifySupportGroup'>Modify</a> | <a href=\"#\" onclick=\"fnDelete('"+(String)resultset.getString(1)+"')\"/>Delete</a>";
				}
				hashmap.put("status",strStatus);
				hashmap.put("actions",linkmodify);
				 
				GroupsList.add(hashmap);
				 
			}
		} 
		catch (SQLException e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		return GroupsList;
	}

	public static ArrayList ViewSupportCenters(String strLoginUser)
	{
       Logger logger=Logger.getLogger("Admin");
		
		ArrayList SupportCentersList=new ArrayList();
		
		try
		{
			connection=DBConnection.createConnection();
			callableStatement=connection.prepareCall("{CALL vims_user.USP_Get_Supp_Cntr_Dtls_All(?,?,?,?,?,?)}");
			callableStatement.setString(1,null);
			callableStatement.setString(2,null);
			callableStatement.setString(3,null);
			callableStatement.setString(4,null);
			callableStatement.setString(5,null);
			callableStatement.setString(6,strLoginUser);
			callableStatement.execute();
			resultset=callableStatement.getResultSet();
			
			SupportCentersList=ListofSupportCenters(resultset);
			
			callableStatement.close();
			resultset.close();
			
		} 
		catch (SQLException e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return SupportCentersList;
	}

	private static ArrayList ListofSupportCenters(ResultSet resultset)
	{
        Logger logger=Logger.getLogger("Admin");
		
		ArrayList SupportCentersList=new ArrayList();
		HashMap hashmap=null;
		String linkmodify=null;
		String strStatus=null;
		
		try
		{
			while(resultset.next())
			{
				hashmap=new HashMap();
				hashmap.put("supportcentername",resultset.getString(4)); 
				hashmap.put("supportmanager",resultset.getString(2));
				hashmap.put("location",resultset.getString(7));
				hashmap.put("status",resultset.getString(6));
				hashmap.put("locationname", resultset.getString(5));
				if(resultset.getString(6).equalsIgnoreCase("0"))
				{
					strStatus="Active";
					linkmodify="<a href='./EditSupportCenter.do?methodname=ModifySupportCenterDetails&selSupCen="+(String)resultset.getString(3)+"&menuId=Home&pageId=ModifySupportCenter'>Modify</a>";
				}
				else
				{
					strStatus="Inactive";
					linkmodify="<a href='./EditSupportCenter.do?methodname=ModifySupportCenterDetails&selSupCen="+(String)resultset.getString(3)+"&menuId=Home&pageId=ModifySupportCenter'>Modify</a> | <a href=\"#\" onclick=\"fnDelete('"+(String)resultset.getString(3)+"')\"/>Delete</a>";
				}
				hashmap.put("status",strStatus);
				hashmap.put("actions",linkmodify);
				 
				SupportCentersList.add(hashmap);
				 
			}
		} 
		catch (SQLException e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		return SupportCentersList;
	}

	public static ArrayList SupportCenterSearch(String strLoginUser,
			String strSearchString)
	{
		Logger logger=Logger.getLogger("Admin");
		HashMap hashmap;
		String strStatus;
		String linkmodify;
		ArrayList SupportCenterSearchList=new ArrayList();
		if(strSearchString==null || "".equalsIgnoreCase(strSearchString))
		{
			strSearchString=null;
		}
		//System.out.println("==========Search String=============="+strSearchString);
		//System.out.println("==========Search Status Selected=============="+strStatusSelected);
		//System.out.println("==========Search Login User=============="+strLoginUser);
		try
		{
			System.out.println("========In the DAO Search===========");
			connection=DBConnection.createConnection();
			callableStatement=connection.prepareCall("{CALL vims_user.USP_Get_Supp_Cntr_Dtls_All(?,?,?,?,?,?)}");
			callableStatement.setString(1,null);
			callableStatement.setString(2,strSearchString);
			callableStatement.setString(3,null);
			callableStatement.setString(4,null);
			callableStatement.setString(5,null);
			callableStatement.setString(6,strLoginUser);
			System.out.println("===========callableStatement.execute();==========="+callableStatement.execute());
			
			resultset=callableStatement.getResultSet();
			System.out.println("==========ResultSet============"+resultset);
			/*while(resultset.next())
			{   
				hashmap=new HashMap();
				hashmap.put("supportcentername",resultset.getString(4)); 
				hashmap.put("supportmanager",resultset.getString(2));
				hashmap.put("location",resultset.getString(5));
				hashmap.put("status",resultset.getString(6));
				if(resultset.getString(6).equalsIgnoreCase("0"))
				{
					strStatus="Active";
					linkmodify="<a href='./EditSupportCenter.do?methodname=ModifySupportCenterDetails&selSupCen="+(String)resultset.getString(3)+"'>Modify</a>";
				}
				else
				{
					strStatus="Inactive";
					linkmodify="<a href='./EditSupportCenter.do?methodname=ModifySupportCenterDetails&selSupCen="+(String)resultset.getString(3)+"'>Modify</a> | <a href=\"#\" onclick=\"fnDelete('"+(String)resultset.getString(3)+"')\"/>Delete</a>";
				}
				hashmap.put("status",strStatus);
				hashmap.put("actions",linkmodify);
				 
				SupportCenterSearchList.add(hashmap);
				System.out.println("============Search Result============"+SupportCenterSearchList);
				 
			}*/
			SupportCenterSearchList=ListofSupportCenters(resultset);
			
			resultset.close();
			callableStatement.close();
			
		} 
		catch (SQLException e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return SupportCenterSearchList;
	}

	public static ArrayList SearchGroups(String strLoginUser,
			String strStatusSelected, String strSearchGroup)
	{
        Logger logger=Logger.getLogger("Admin");
		
		ArrayList SearchGroupsList=new ArrayList();
		
		if(strSearchGroup==null || "".equalsIgnoreCase(strSearchGroup))
		{
			strSearchGroup=null;
		}
		try
		{
			connection=DBConnection.createConnection();
			callableStatement=connection.prepareCall("{CALL vims_user.USP_Get_Group_Dtls(?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1,null);
			callableStatement.setString(2,strSearchGroup);
			callableStatement.setString(3,null);
			callableStatement.setString(4,null);
			callableStatement.setString(5,null);
			callableStatement.setString(6,null);
			callableStatement.setString(7,null);
			callableStatement.setString(8,strLoginUser);
			callableStatement.execute();
			resultset=callableStatement.getResultSet();
			
			SearchGroupsList=ListofGroups(resultset);
			
		} 
		catch (SQLException e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		finally
		{
			DBConnection.closeConnection();
		}
		if(callableStatement!=null)
		{
			try
			{
				callableStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			if(resultset!=null)
			{
				try {
					resultset.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return SearchGroupsList;
	}

	public static int DeleteGroup(String strGroupID, String strLoginUser)
	{
		int intResult = 0;
		try
		{
			connection = DBConnection.createConnection();
			
			callableStatement = connection.prepareCall("{?= CALL vims_user.USP_Save_Group(?,?,?,?,?,?,?,?)}");
			
//			preparedStatement=connection.prepareStatement(strApplicationGroupModify);
//			preparedStatement.setString(1,strGroupId);
//			preparedStatement.setString(2,strSupportCenterID);
//			
//			int ReturnValue6=preparedStatement.executeUpdate();
//			
//			preparedStatement.close();
			
			callableStatement.registerOutParameter(1,Types.OTHER);
			callableStatement.setString(2,strGroupID);
			callableStatement.setString(3,null);
			callableStatement.setString(4,null);
			callableStatement.setString(5,null);
			callableStatement.setString(6,null);
			callableStatement.setString(7,null);
			callableStatement.setString(8,"delete");
			callableStatement.setString(9,strLoginUser);
			callableStatement.execute();
			
			intResult=callableStatement.getInt(1);
			
			/*resultset=callableStatement.getResultSet();
			
			while(resultset.next())
			{
				strResponse=resultset.getString(1);
			}
			resultset.close();*/
			callableStatement.close();
		}
		catch (Exception e)
		{
		  logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		return intResult;
	}

	public static int DeleteSupportCenter(String strSupportCenterID,
			String strLoginUser)
	{
	    int intResult = 0;
	    
	    try
		{
			connection = DBConnection.createConnection();
			
			callableStatement = connection.prepareCall("{?= CALL vims_user.USP_Save_Support_Center(?,?,?,?,?,?,?,?,?)}");
			
			callableStatement.registerOutParameter(1,Types.OTHER);
			callableStatement.setString(2,strSupportCenterID);
			callableStatement.setString(3,null);
			callableStatement.setString(4,null);
			callableStatement.setString(5,null);
			callableStatement.setString(6,null);
			callableStatement.setString(7,"");
			callableStatement.setString(8,"");
			callableStatement.setString(9,"delete");
			callableStatement.setString(10,strLoginUser);
			callableStatement.execute();
			
			intResult=callableStatement.getInt(1);
			//resultset=callableStatement.getResultSet();
			
			/*while(resultset.next())
			{
				System.out.println("================resultset========="+resultset.getString(1));
				strResponse=resultset.getString(1);
			}
			resultset.close();*/
			
			callableStatement.close();
		
		} 
		catch (Exception e)
		{
			logger.error(e);
		}
		return intResult;
	}

	public static boolean checkSupport(String strSupportCenterID,
			String strSupportCenterName)
	{
		boolean response;
		
		String strSupportCenter = null;
		Statement statement;
		logger=Logger.getLogger("Admin");
		
		connection=DBConnection.createConnection();
		try
		{
			statement = connection.createStatement();
			resultset = statement.executeQuery("select support_center_id,supp_center_name from vims_user.support_center");
			while(resultset.next())
			{
				if(strSupportCenterName.equalsIgnoreCase(resultset.getString(2)))
				{
					if(!strSupportCenterID.equalsIgnoreCase(resultset.getString(1))) {
						return false;
					}
				}
				
			}
		    //   return true;
			
			resultset.close();
			statement.close();
			
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		finally
		{
			DBConnection.closeConnection();
		}
		
		return true;
	}


	
	/*public static ArrayList getGroups()
	{
		 Connection connection=null;
		 Statement statement=null;
		 ResultSet resultset=null;
		 
		 ArrayList groups=new ArrayList();
		 
		 //HashMap hashmap=null;
		 
		 		 
		 try
		 {
			conn = DBConnection.createConnection();
			statement = conn.createStatement();
			resultset = statement.executeQuery("select usrgroup_id from supp_center_group");
			while (resultset.next())
			{
				groups.add(resultset.getString(1));

			}
		 } 
		 catch (Exception e)
		 {
			logger.error(e);
		 }
		 finally
		 {
			 DBConnection.closeConnection();
		 }
		return groups;
		
	}*/
}
