package com.vertex.VIMS.test.VIMSSQLQueryInterface;

/**
 * @author saikumar.m
 *
 */
public interface VIMSQueryInterface {
	
	/* Home Page related Queries
	 * Author santhosh.k
	 * date : 11-19-2008
	 */
	 
	/* Query to retrieve count of open issues and sla details of an application in order to display in home page */
	public final static String getHomeApplicationOpenAndSLAdetails="EXEC vims_user.Usp_Get_App_Issue_Sla_Count @Application_ID=?";	
	
	/* Query to retrieve list of open(and all if required) issues of an application */
	public final static String getIssuesListInApplication="exec vims_user.Usp_Get_Application_Issue @Application_Id=?,@INCT_STATUS=?,@Incident_Type=?,@User_Id=?";
	
	/*
	 * These are the queries used to get the SLA details from the database.
	 * author : santhosh.k
	 * date:11/14/2008
	 */
	
	/* Query String to set and modify  SLA Details of an application*/
	public final static String SLA_Details=" EXEC vims_user.USP_Save_App_Sla_Params @APPLICATION_ID=?,@TYPE=?,@INCT_TYPE_VALUE_CRI=?,@INCT_WARNING_INTERVAL_CRI=?,@INCT_TYPE_VALUE_MAJ=?,@INCT_WARNING_INTERVAL_MAJ=?,@INCT_TYPE_VALUE_MIN=?,@INCT_WARNING_INTERVAL_MIN=?	";
	 
	/* Query String to  retrieve SLA Details of an application*/
	public final static String getSLADetails="select inct_type_value,inct_warning_interval from vims_user.app_sla_params where application_id=? and incident_type=?";
	
	public final static String getApplicationSLADetails="EXEC vims_user.Usp_Get_App_Sla_Details @application_id=?";

	/*  Query String to delete SLA Details of an application*/
	public final static String deleteSLADetails="delete vims_user.from vims_user.app_sla_params where APPLICATION_ID=?";
	/*
	 * Queries used for retrieving information to generate Reports
	 * Author :  Santhosh.k
	 * date : 11/27/2008
	*/
	
	public static final String getApplicatioIdAndNames="select distinct i.application_id, a.application_name from vims_user.application a  INNER join vims_user.incident i on a.application_id=i.application_id";
	public static final String getIssuesInApplication="select distinct i.application_id, count(incident_id) as Issues ,a.application_name from vims_user.application a INNER join vims_user.incident i on a.application_id=i.application_id group by i.application_id,a.application_name ORDER BY a.Application_Name";
	
	public static final String getIssuesInApplicationName = "SELECT distinct i.application_id ,COUNT(i.incident_id) AS NoOfIssues, a.APPLICATION_NAME FROM vims_user.INCIDENT i INNER JOIN vims_user.APPLICATION a ON i.Application_ID = a.Application_ID where a.app_status=? GROUP BY i.application_id,a.APPLICATION_NAME ORDER BY a.Application_Name";

	public static final String getSpecificIssuesInAllApplication="SELECT distinct i.application_id ,COUNT(i.incident_id) AS NoOfIssues, a.APPLICATION_NAME FROM vims_user.INCIDENT i INNER JOIN vims_user.APPLICATION a ON i.Application_ID = a.Application_ID WHERE INCT_STATUS=? AND a.app_status=? GROUP BY i.application_id,a.APPLICATION_NAME ORDER BY a.Application_Name";
	public static final String getSpecificIssuesInApplication = "SELECT distinct i.application_id ,COUNT(i.INCT_STATUS) AS NoOfIssues, a.APPLICATION_NAME FROM vims_user.INCIDENT i INNER JOIN vims_user.APPLICATION a ON i.Application_ID = a.Application_ID WHERE i.INCT_STATUS=? and i.Application_ID=? GROUP BY i.application_id,a.APPLICATION_NAME ";
	public static final String getALLIssuesInApplication="EXEC vims_user.USP_Get_App_Issue_Status @APPLICATION_ID=?";
	//public static final String getSLAInformationReports="select Count(Distinct case when INCT_STATUS='Escalated' THEN APPLICATION_ID END ) AS [ESCALATED],Count(Distinct CASE WHEN INCT_STATUS!='Escalated' THEN APPLICATION_ID END ) AS [NOT ESCALATED]FROM INCIDENT";
	public static final String getSLAInformationReports="EXEC vims_user.USP_Get_Appl_Cnt_Inct @status=?";
	public static final String displayAllApplcationDetails="EXEC vims_user.USP_Get_App_Issue";
	public static final String getApplicationByIncident="EXEC vims_user.USP_Get_Appl_By_Inct @status=?";
	
	/*
	 * queries added by santhosh.k for custom reports
	*/
	//Query used to retrieve reports based on the user selection
	public static final String getCustomReportsDetails = "Exec vims_user.USP_Get_Custom_Report" +
			"@INCT_STATUS=?,@INCIDENT_PRIORITY_TYPE=?,@INCIDENT_TYPE=?,@INCIDENT_POSTED_DATE_From=?," +
			"@INCIDENT_POSTED_DATE_To=?,@CLOSED_DATE_From=?,@CLOSED_DATE_To=?,@APPLICATION_ID=?," +
			"@CUSTOMER_ID=?,@User_NM=?,@SUPPORT_CENTER_ID=?,@SUP_CNTR_MANAGER=?,@USRGROUP_ID=?," +
			"@GROUP_SUPERVISOR=?,@Group_User_NM=?,@PURGING_FLAG=?";
	
	//Query used to retrieve reports criteria in custom reports
	public static final String getCustomReportsCriteria = "Exec vims_user.USP_Get_Report_Criteria";
	/*
	 * Escalation related queries by santhosh.k on 01/21/2009
	 */
	//Query Used to retrieve escalated issues list
	public static final String getEscalatedIssueDetails="EXEC vims_user.USP_Get_Escltd_Email_Dtls @Assgd_By_User_NM=?,@USRGROUP_ID=?,@Assgd_To_User_NM=?,@Incident_ID=?";

	// Employee tab related Queries
	public String getSupervisorNames="select USER_NM,FIRST_NAME+' '+LAST_NAME AS [Name] from vims_user.Employee order by user_nm";
	public String checkEmployeeID="Select EMPLOYEE_ID from vims_user.EMPLOYEE where EMPLOYEE_ID=?"; 
	public String getAllEmployeeDetails = "select EMPLOYEE_ID,FIRST_NAME+' '+LAST_NAME AS [Name],START_DATE,WORK_PHONE,WORK_EMAIL_ADDRESS,EMP_STATUS  from vims_user.EMPLOYEE where EMP_STATUS='Active' order by EMPLOYEE_ID";
	public String addEmployeeDetail = "insert into vims_user.EMPLOYEE (EMPLOYEE_ID,FIRST_NAME,LAST_NAME,EMP_LEVEL,ADDRESS_1,CITY,STATE,COUNTRY,WORK_EMAIL_ADDRESS,WORK_PHONE,SUPERVISOR_ID,MOBILE_NUMBER,LOCATION,ZIP,EMP_STATUS,USER_NM) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public String editEmployeeDetail = "update vims_user.EMPLOYEE set FIRST_NAME=?,LAST_NAME=?,EMP_LEVEL=?, ADDRESS_1=?,ADDRESS_2=?, CITY=? , STATE=?, COUNTRY=? , WORK_EMAIL_ADDRESS=?,WORK_PHONE=?,SUPERVISOR_ID=?,MOBILE_NUMBER=?,QUALIFICATION =?,LOCATION=?,ZIP=?,EMP_STATUS=? where EMPLOYEE_ID=?";
	public String deleteEmployeeDetail = "update  vims_user.EMPLOYEE set  EMP_STATUS='Inactive' where EMPLOYEE_ID=?";
	public String viewEmployeeDetail = "Select EMPLOYEE_ID, FIRST_NAME, ISNULL(LAST_NAME, ' '),ISNULL(EMP_LEVEL,''),ISNULL(ADDRESS_1,''),ISNULL(ADDRESS_2,''),ISNULL(CITY,''),ISNULL(STATE,''),ISNULL(COUNTRY,''),ISNULL(WORK_EMAIL_ADDRESS,''),ISNULL(WORK_PHONE,''),ISNULL(SUPERVISOR_ID,''),ISNULL(MOBILE_NUMBER,''),ISNULL(QUALIFICATION,''),ISNULL(LOCATION,''),ISNULL(EXPERIENCE,''),ISNULL(EMP_STATUS,''),ISNULL(ZIP,'') from  vims_user.EMPLOYEE where EMPLOYEE_ID=?";
	public String checkEmployeeEmailID="Select WORK_EMAIL_ADDRESS from vims_user.EMPLOYEE where WORK_EMAIL_ADDRESS=?";
	public String getPositionList="select Designation_NBR,Designation_NM from vims_user.TDesignation where Active_FG='0'";
	
	//End of Employee tab related Queries
	
	/* These are the queries used to get the CustomerNames ,Customer subCatogiries from database.
	 * Author: Geeta.M
	 * Date  :11-14-2008.
	 */ 
	public final static String clientListSql = "select c.CUSTOMER_ID,c.CUSTOMER_NAME,app.APPLICATION_NAME,c.EMAILID,c.PHONE_NUMBER,c.Status from vims_user.CUSTOMER c LEFT JOIN vims_user.APP_SOLD_TO apps ON c.CUSTOMER_ID=apps.CUSTOMER_ID LEFT JOIN vims_user.Application app ON apps.Application_ID=app.Application_ID where STATUS='Active'";
	public final static String addClientListSql = "insert into vims_user.CUSTOMER(CUSTOMER_ID,CUSTOMER_NAME,FIRST_NAME,LAST_NAME,ADDRESS_1,ADDRESS_2,CITY,STATE,COUNTRY,MOBILE_NO,PHONE_NUMBER,EMAILID,FAXNO,ZIPCODE,STATUS,CUST_PWD,COMPANY_NAME,EXTENSION_NO) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	public String viewClientDetail = "Select c.CUSTOMER_ID,c.CUSTOMER_NAME,c.ADDRESS_1,c.ADDRESS_2,c.CITY,s.STATE_NM,co.COUNTRY_NM,c.Cntct_Mobile_NBR,PHONE_NUMBER,EMAILID,FAXNO,STATUS,COMPANY_NAME,EXTENSION_NO,ZIPCODE,Cntct_First_NM,Cntct_Middle_NM,Cntct_Last_NM,Cntct_Email_Addr,c.Country_NBR,c.State_NBR from vims_user.CUSTOMER c (NOLOCK)LEFT join vims_user.TState s (NOLOCK) ON  c.State_NBR = s.State_NBR LEFT join vims_user.TCountry co(NOLOCK) ON c.Country_NBR	= co.Country_NBR where c.CUSTOMER_ID=?";
	
	
	public String deleteClientDetail = "update  vims_user.CUSTOMER set  STATUS='Inactive' where CUSTOMER_ID=?";
	public String modifyClientDetail ="UPDATE vims_user.CUSTOMER set CUSTOMER_NAME=?,ADDRESS_1=?,ADDRESS_2=?,CITY=?,STATE_NBR=?,COUNTRY_NBR=?,PHONE_NUMBER=?,EMAILID=?,FAXNO=?,STATUS=?,ZIPCODE=?,Cntct_First_NM=?,Cntct_Middle_NM=?,Cntct_Last_NM=?,Cntct_Email_Addr=?,Cntct_Mobile_NBR=? WHERE CUSTOMER_ID=?";
	
	
	public String displayClientDetail ="Select c.CUSTOMER_ID,c.CUSTOMER_NAME,c.ADDRESS_1,c.ADDRESS_2,c.CITY,s.STATE_NM,co.COUNTRY_NM,c.Cntct_Mobile_NBR,PHONE_NUMBER,EMAILID,FAXNO,STATUS,COMPANY_NAME,EXTENSION_NO,ZIPCODE,Cntct_First_NM,Cntct_Middle_NM,Cntct_Last_NM,Cntct_Email_Addr,c.Country_NBR,c.State_NBR from vims_user.CUSTOMER c (NOLOCK)LEFT JOIN vims_user.TState s (NOLOCK) ON  c.State_NBR = s.State_NBR LEFT JOIN vims_user.TCountry co(NOLOCK) ON c.Country_NBR	= co.Country_NBR where c.CUSTOMER_ID=?"; 
	
	
	public String statusClientDetail ="select Pwd_Change_FG from vims_user.USER_TEST where USER_ID=? and PASSWORD=?"; 
	public String checkCustomerEmailID="Select EMAILID from vims_user.CUSTOMER where EMAILID=?";	
	public String applicationOwnerName ="select  APP_OWNER from vims_user.APPLICATION where App_Owner_Email=?"; 
	
	/* These are the queries used search the details like CustomerNames ,Customer subCatogiries from database.
	 * Author: Geeta.M
	 * Date  :11-17-2008.
	 */ 
	public String SearchApplicationNamesSql="select APPLICATION_ID,APPLICATION_NAME FROM vims_user.APPLICATION where APP_STATUS='Active' ORDER BY APPLICATION_NAME";
	
	public String SearchCustApplicationNamesSql="SELECT DISTINCT app.APPLICATION_ID,app.APPLICATION_NAME FROM vims_user.APPLICATION app(NOLOCK) INNER JOIN vims_user.App_Sold_To apps(NOLOCK) ON app.Application_ID	= apps.Application_ID INNER JOIN vims_user.Customer c(NOLOCK)	ON apps.Customer_ID = c.Customer_ID	WHERE app.APP_STATUS='Active' AND c.Customer_ID=? ORDER BY APPLICATION_NAME";

	
	public String SearchCustomerNamesSql="SELECT DISTINCT cust.CUSTOMER_NAME,cust.Customer_ID FROM vims_user.Customer cust(NOLOCK) INNER JOIN vims_user.APP_Sold_To apps (NOLOCK) ON cust.Customer_ID = apps.Customer_ID INNER JOIN vims_user.Application app(NOLOCK) ON apps.Application_ID = app.Application_ID WHERE app.App_Status='Active'";
	//public String SearchCustomerNamesSql="SELECT DISTINCT cust.CUSTOMER_NAME,app.App_Owner_Email FROM Customer cust INNER JOIN APP_Sold_To apps ON cust.Customer_ID = apps.Customer_ID INNER JOIN Application app ON apps.Application_ID = app.Application_ID WHERE app.App_Status='Active'";
	//public String SearchCustomerNamesSql="select CUSTOMER_ID,CUSTOMER_NAME FROM CUSTOMER ORDER BY CUSTOMER_NAME";
	public String SearchStatusSql="select INCT_STATUS,INC_STATUS_DESC FROM vims_user.INC_STATUS ORDER BY INCT_STATUS";
	public String SearchSeveritySql="select INCIDENT_TYPE,INCIDENT_TYPE_DESC from vims_user.INCIDENT_TYPE ORDER BY INCIDENT_TYPE_DESC";
	
	//ChangePassword tab related Queries
	public String checkOldPassword="select PASSWORD from vims_user.USER_TEST where PASSWORD=? and USER_ID=?";
	
	
	
	/* These are the queries used to get the Appication names,Application subCatogiries and Issue types from database.
	 * Author: Aditya.p
	 * Date  :11-11-2008.
	 */ 
	

	public final static String ApplicationNamesSql="select application_id,application_name from vims_user.application";
	public final static String SLACretaedApplicationNamesSql="SELECT distinct sla.APPLICATION_ID,app.APPLICATION_NAME FROM vims_user.APPLICATION app INNER JOIN vims_user.APP_SLA_PARAMS sla ON app.APPLICATION_ID=sla.APPLICATION_ID where app.app_status='active' order by app.APPLICATION_NAME";
	public final static String IncidentTypeSql="select INCIDENT_TYPE,INCIDENT_TYPE_DESC from vims_user.incident_type ORDER BY INCIDENT_TYPE_DESC";

	public final static String ApplicationSubCategorySql="select APP_SUB_CAT_NAME from vims_user.APPLICATION_SUB_CATEGORIES ORDER BY APP_SUB_CAT_NAME";
	
	public final static String AddNewIssueSql="insert into vims_user.NEW_ISSUE values(?,?,?,?,?,?,?)";
	
	public final static String AddFileDetailsSql="insert into vims_user.incident_object(INCIDENT_ID,OBJ_ID,OBJECT_PATH,ATTACHED_BY)values(?,?,?,?)";
	
	/* The below Queries are useful in the List of Issues tab */
	
	public final static String AllListIssues="select i.incident_id,a.application_name,i.incident_title,convert(varchar,i.incident_posted_date,101)+' '+convert(varchar,i.incident_posted_date,108),i1.incident_type_desc,convert(varchar,i.due_date,101)+' '+convert(varchar,i.due_date,108),i.inct_status,convert(varchar,i.closed_date,101)+' '+convert(varchar,i.closed_date,108) as closedDate,DATEDIFF(hour,i.incident_posted_date,i.closed_date) as ResolutionHours from vims_user.incident i,vims_user.application a,vims_user.incident_type i1 where i.application_id=a.application_id and i.incident_type=i1.incident_type order by convert(int,incident_id) asc";
	
	public final static String OtherListIssues="select i.incident_id,a.application_name,i.incident_title,convert(varchar,i.incident_posted_date,101)+' '+convert(varchar,i.incident_posted_date,108),i1.incident_type_desc,convert(varchar,i.due_date,101)+' '+convert(varchar,i.due_date,108),i.inct_status,convert(varchar,i.closed_date,101)+' '+convert(varchar,i.closed_date,108) as closedDate,DATEDIFF(hour,i.incident_posted_date,i.closed_date) as ResolutionHours from vims_user.incident i,vims_user.application a,vims_user.incident_type i1 where i.application_id=a.application_id and i.incident_type=i1.incident_type and i.inct_status=? order by convert(int,incident_id) asc";

	public final static String IssueDetails1="select a.application_name,i1.incident_type_desc,i.posted_by,convert(varchar,i.due_date,101)+' '+convert(varchar,i.due_date,108),convert(varchar,i.closed_date,101)+' '+convert(varchar,i.closed_date,108),convert(varchar,i.incident_posted_date,101)+' '+convert(varchar,i.incident_posted_date,108),i.incident_title,i.inct_status from vims_user.incident i,vims_user.application a,vims_user.incident_type i1 where i.application_id=a.application_id and i1.incident_type=i.incident_type and incident_id=?";
	
	public final static String IssueDetails2="SELECT Assigned_by,Assigned_by_date,Inct_status_from,Inct_status_to,convert(varchar,Inct_status_date,101)+'@' + convert(varchar,Inct_status_date,108),Assigned_to,Comments from vims_user.incident_status WHERE Incident_id=?";
	
	//public static final String IssueDetails3 ="SELECT Assigned_by,Assigned_by_date,Inct_status_from,Inct_status_to,Assigned_to,Comments,escalation_reason from incident_status WHERE Incident_id=?";
	
	public static final String IssueDetails3 ="SELECT Assigned_by,Assigned_by_date,Inct_status_from,Inct_status_to,Assigned_to,ISNULL(Comments,escalation_reason ) AS [Comments],MAILED_FROM_ID,MAILED_TO_ID from vims_user.incident_status WHERE incident_id=? order by inct_statusid";
	
	public static final String IssueDescription ="select incident_text from vims_user.incident_descrition where incident_id=?";
	
	public static final String getFileName ="select object_path from vims_user.incident_object where incident_id=?";
	
	public static final String getGroupsSQL ="select usrgroup_id,group_name from vims_user.user_group order by group_name";
	
	public static final String getGroupEmployeesSQL = "SELECT e.USER_NM,e.FIRST_NAME+' '+ISNULL(e.MIDDLE_INITIAL,'')+' '+ISNULL(e.LAST_NAME,'')FROM vims_user.EMPLOYEE e INNER JOIN vims_user.GROUP_MEMBERS gm ON e.User_NM=gm.User_NM WHERE gm.USRGROUP_ID=? order by e.USER_NM";
	
	public static final String getAllGroupEmployeesSQl ="SELECT e.USER_NM,e.FIRST_NAME+' '+ISNULL(e.MIDDLE_INITIAL,'')+' '+ISNULL(e.LAST_NAME,'')FROM vims_user.EMPLOYEE e INNER JOIN vims_user.GROUP_MEMBERS gm ON e.User_NM=gm.User_NM order by e.USER_NM";
	
	public static final String getDateSQL="select convert(varchar,getDate(),101)+' ' +convert(varchar,getDate(),108)";
	
	public static final String getIssueStatusIDSQL="select ISNULL(max(inct_statusid),'')as Inct_statusid from vims_user.incident_status where incident_id=?";
	
	public static final String AssignIssueSQL="insert into vims_user.incident_status(INCIDENT_ID,INCT_STATUSID,ASSIGNED_BY,ASSIGNED_BY_DATE,ASSIGNED_TO,INCT_STATUS_FROM,INCT_STATUS_TO,INCT_STATUS_DATE,COMMENTS)values(?,?,?,?,?,?,?,?,?)";
	
	//public static final String Incident_Table_StatusChangeSQL="update incident set inct_status=? where incident_id=?";
	
	//public static final String Incident_Table_StatusChangeSQL="update incident set inct_status=?, view_fg=0 where incident_id=?";
	
	public static final String Incident_Table_StatusChangeSQL="EXEC vims_user.USP_Save_Inct_Stats @inct_status=?,@incident_id=?";
	
	public static final String getIssueAssignedtoSQL ="select ASSIGNED_TO from vims_user.INCIDENT_STATUS where INCT_STATUSID=(select max(INCT_STATUSID)from vims_user.INCIDENT_STATUS where incident_ID=?)and incident_ID=?";
	
	public static final String ChangeIssueStatusSQL ="insert into vims_user.incident_status(INCIDENT_ID,INCT_STATUSID,ASSIGNED_BY,ASSIGNED_BY_DATE,ASSIGNED_TO,INCT_STATUS_FROM,INCT_STATUS_TO,INCT_STATUS_DATE,ESCALATION_REASON)values(?,?,?,?,?,?,?,?,?)";
	
	public static final String getSupervisormailidSQL ="select ISNULL(max(ist.inct_statusid),'') as Inct_statusid,e.WORK_EMAIL_ADDRESS,e.User_NM from vims_user.incident_status ist INNER JOIN vims_user.INCIDENT i ON i.INCIDENT_ID=ist.INCIDENT_ID INNER JOIN vims_user.GROUP_APPLICATION ga ON i.APPLICATION_ID=ga.APPLICATION_ID INNER JOIN vims_user.SUPP_CENTER_GROUP scg ON ga.USRGROUP_ID=scg.USRGROUP_ID INNER JOIN vims_user.EMPLOYEE e ON scg.GROUP_SUPERVISOR=e.User_NM WHERE ist.incident_id=? GROUP BY e.WORK_EMAIL_ADDRESS,e.User_NM";
	
	public static final String getApplication_Group_SupervisorSQL ="SELECT e.WORK_EMAIL_ADDRESS from vims_user.incident_status ist INNER JOIN vims_user.INCIDENT i ON i.INCIDENT_ID=ist.INCIDENT_ID INNER JOIN vims_user.GROUP_APPLICATION ga ON i.APPLICATION_ID=ga.APPLICATION_ID INNER JOIN vims_user.SUPP_CENTER_GROUP scg ON ga.USRGROUP_ID=scg.USRGROUP_ID INNER JOIN vims_user.EMPLOYEE e ON scg.GROUP_SUPERVISOR=e.User_NM WHERE ga.APPLICATION_ID=? GROUP BY e.WORK_EMAIL_ADDRESS";
	
	public static final String getEmployeeWorkedSQL ="SELECT DISTINCT e.User_NM,e.WORK_EMAIL_ADDRESS,ID FROM vims_user.INCIDENT_STATUS i INNER JOIN vims_user.EMPLOYEE e ON e.User_NM=i.ASSIGNED_TO LEFT JOIN (SELECT MAX(INCT_STATUSID) AS ID FROM vims_user.INCIDENT_STATUS WHERE INCIDENT_ID=? )inc ON 1=1 WHERE i.INCIDENT_ID=? AND i.INCT_StatusID=ID";
	/*added by santhosh.k to retrieve escalated issue list*/
	//public final static String EscalatedIssues="select i.incident_id,a.application_name,i.incident_title,convert(varchar,i.incident_posted_date,101)+' '+convert(varchar,i.incident_posted_date,108),i1.incident_type_desc,convert(varchar,i.due_date,101)+' '+convert(varchar,i.due_date,108),i.inct_status,convert(varchar,i.closed_date,101)+' '+convert(varchar,i.closed_date,108) as closedDate,DATEDIFF(hour,i.incident_posted_date,i.closed_date) as ResolutionHours from incident i,application a,incident_type i1 where i.application_id=a.application_id and i.incident_type=i1.incident_type and i.inct_status='escalated' order by convert(int,incident_id) asc";
	//public final static String EscalatedIssues="EXEC USP_Get_Incident_Dtls_Escld";
	
	public final static String EscalatedIssues="EXEC vims_user.USP_Report_Issues @Incident_ID=?,@Customer=?,@Emp_Name=?,@INCT_STATUS=?,@INCIDENT_PRIORITY=?,@Application_Name=?,@From_DT=?,@To_DT=?,@INCIDENT_TYPE=?,@Cust_User_NM=?,@Emp_User_NM=?,@User_NM=?,@Purged_flag=?";
	
	/* 
	 * geeta added queries to issues 	
	*/
	public final static String CustomerNewIssue="select APPLICATION_ID,APPLICATION_NAME from vims_user.APPLICATION where APPLICATION_ID=?";
	
	public final static String CustomerAllIssues="SELECT DISTINCT inc.INCIDENT_ID,app.APPLICATION_NAME,inc.INCIDENT_TITLE,convert(varchar(10),inc.incident_posted_date,101)+' '+convert(varchar(10),inc.incident_posted_date,108),inct.INCIDENT_TYPE_DESC,convert(varchar(10),inc.due_date,101)+' '+convert(varchar(10),inc.due_date,108),inc.INCT_STATUS,convert(varchar(10),inc.closed_date,101)+' '+convert(varchar(10),inc.closed_date,108) as closedDate,'' FROM vims_user.INCIDENT inc INNER JOIN vims_user.APPLICATION app ON inc.APPLICATION_ID=app.APPLICATION_ID INNER JOIN vims_user.INCIDENT_TYPE inct ON inc.INCIDENT_TYPE=inct.INCIDENT_TYPE WHERE inc.POSTED_BY=? ";
	
	public final static String CustomerOtherIssues="SELECT DISTINCT inc.INCIDENT_ID,app.APPLICATION_NAME,inc.INCIDENT_TITLE,convert(varchar(10),inc.incident_posted_date,101)+' '+convert(varchar(10),inc.incident_posted_date,108),inct.INCIDENT_TYPE_DESC,convert(varchar(10),inc.due_date,101)+' '+convert(varchar(10),inc.due_date,108),inc.INCT_STATUS,convert(varchar(10),inc.closed_date,101)+' '+convert(varchar(10),inc.closed_date,108) as closedDate,'' FROM vims_user.INCIDENT inc INNER JOIN vims_user.APPLICATION app ON inc.APPLICATION_ID=app.APPLICATION_ID INNER JOIN vims_user.INCIDENT_TYPE inct ON inc.INCIDENT_TYPE=inct.INCIDENT_TYPE WHERE inc.POSTED_BY=? AND inc.INCT_STATUS=?";
	
	public static final String employeeAllIssues ="SELECT DISTINCT inc.INCIDENT_ID,app.APPLICATION_NAME,inc.INCIDENT_TITLE,convert(varchar(10),inc.incident_posted_date,101)+' '+convert(varchar(10),inc.incident_posted_date,108),inct.INCIDENT_TYPE_DESC,convert(varchar(10),inc.due_date,101)+' '+convert(varchar(10),inc.due_date,108),inc.INCT_STATUS,convert(varchar(10),inc.closed_date,101)+' '+convert(varchar(10),inc.closed_date,108) as closedDate,inc.POSTED_BY	FROM vims_user.Incident inc INNER JOIN vims_user.GROUP_APPLICATION ga ON inc.APPLICATION_ID=ga.APPLICATION_ID LEFT JOIN vims_user.APPLICATION_SPECIALISTS asp ON asp.APPLICATION_ID=inc.APPLICATION_ID  INNER JOIN vims_user.GROUP_MEMBERS gm ON gm.USRGROUP_ID=ga.USRGROUP_ID INNER JOIN vims_user.EMPLOYEE e ON gm.USER_NM=e.USER_NM LEFT JOIN vims_user.APPLICATION_SPECIALISTS asp1 ON asp1.USER_NM=e.USER_NM INNER JOIN vims_user.APPLICATION app ON inc.APPLICATION_ID=app.APPLICATION_ID  INNER JOIN vims_user.INCIDENT_TYPE inct ON inc.INCIDENT_TYPE=inct.INCIDENT_TYPE WHERE e.USER_NM=?";
	
	public static final String employeeOtherIssues="SELECT DISTINCT inc.INCIDENT_ID,app.APPLICATION_NAME,inc.INCIDENT_TITLE,inc.INCIDENT_POSTED_DATE,inc.INCIDENT_TYPE,inc.DUE_DATE,inc.INCT_STATUS,inc.CLOSED_DATE,inc.POSTED_BY from vims_user.Incident inc INNER JOIN vims_user.GROUP_APPLICATION ga ON inc.APPLICATION_ID=ga.APPLICATION_ID LEFT JOIN vims_user.APPLICATION_SPECIALISTS asp ON asp.APPLICATION_ID=inc.APPLICATION_ID  INNER JOIN vims_user.GROUP_MEMBERS gm ON gm.USRGROUP_ID=ga.USRGROUP_ID INNER JOIN vims_user.EMPLOYEE e ON gm.USER_NM=e.USER_NM LEFT JOIN vims_user.APPLICATION_SPECIALISTS asp1 ON asp1.USER_NM=e.USER_NM INNER JOIN APPLICATION app ON inc.APPLICATION_ID=app.APPLICATION_ID INNER JOIN vims_user.INCIDENT_TYPE inct ON inc.INCIDENT_TYPE=inct.INCIDENT_TYPE WHERE e.USER_NM=? AND inc.INCT_STATUS=?";
	
	public final static String UserApplicationNamesSql="SELECT DISTINCT ISNULL(app.APPLICATION_ID ,app1.APPLICATION_ID),ISNULL(app.APPLICATION_NAME ,app1.APPLICATION_NAME) from vims_user.APPLICATION app INNER JOIN vims_user.GROUP_APPLICATION ga ON app.APPLICATION_ID=ga.APPLICATION_ID INNER JOIN vims_user.GROUP_MEMBERS gm ON gm.USRGROUP_ID=ga.USRGROUP_ID INNER JOIN vims_user.EMPLOYEE e ON gm.USER_NM=e.USER_NM LEFT JOIN vims_user.APPLICATION_SPECIALISTS asp ON asp.USER_NM=e.USER_NM LEFT JOIN vims_user.APPLICATION app1 ON asp.APPLICATION_ID=app1.APPLICATION_ID AND app.APP_STATUS='Active' and app1.APP_STATUS='Active'WHERE e.USER_NM=?";
	//public final static String UserApplicationNamesSql="SELECT DISTINCT ISNULL(app.APPLICATION_ID ,app1.APPLICATION_ID),ISNULL(app.APPLICATION_NAME ,app1.APPLICATION_NAME) FROM APPLICATION app INNER JOIN GROUP_APPLICATION ga ON app.APPLICATION_ID=ga.APPLICATION_ID INNER JOIN GROUP_MEMBERS gm ON gm.USRGROUP_ID=ga.USRGROUP_ID INNER JOIN EMPLOYEE e ON gm.USER_NM=e.USER_NM LEFT JOIN APPLICATION_SPECIALISTS asp ON asp.USER_NM=e.USER_NM LEFT JOIN APPLICATION app1 ON asp.APPLICATION_ID=app1.APPLICATION_ID WHERE app.APP_STATUS='Active' and app1.APP_STATUS='Active'and e.USER_NM=?";
	
	public final static String newIssueSubCategories="SELECT APP_SUB_CAT_NAME FROM vims_user.APPLICATION_SUB_CATEGORIES WHERE APPLICATION_ID=? order by APP_SUB_CAT_NAME";
	
	public static final String getIssuePrioritySQL = "select incident_priority_type,incident_priority_desc from vims_user.incident_priority ORDER BY incident_priority_desc";
			
	/* These are the queries used for the Appications Link to the database.
	 * Author: saikumar.m
	 * Date  :11-11-2008.
	 */ 
	public final static String applicationsListSql = "{Call vims_user.USP_Get_Application_Dtls(NULL,?,NULL,NULL)}";//select * from application WHERE APP_STATUS='Active' ORDER BY APPLICATION_NAME";
	
	public final static String supportCenterDetailsSql="EXEC vims_user.USP_Get_Supp_Cntr_Dtls @SUPPORT_CENTER_ID=?";
	
	public final static String appSavePendingProc="{call vims_user.USP_Save_App_Pending(?,?,?,?)}";
	
	public final static String addApplicationSql="{call vims_user.USP_Save_Application(NULL,?,?,?,?,?,?,'Insert',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//"insert into APPLICATION(APPLICATION_ID,APPLICATION_NAME,APP_OWNER, APP_STATUS, PRIMARY_CONTACT,SUPPORT_BEGIN_DATE,SUPPORT_CENTER_ID,SUPPORT_CNTR_MANAGER) values(?,?,?,?,?,?,?,?)";
	
	public final static String viewAppDetailsSql="select * from vims_user.APPLICATION where APPLICATION_ID=?";
	
	public final static String viewAppSql="select * from vims_user.application where APPLICATION_ID=?";
	 
	public final static String modifyAppSql="{call vims_user.USP_Save_Application(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		
	public final static String delAppSql="{call vims_user.USP_DELETE_APPLICATION(?)}";
	
	public final static String getAppDetailsSql="{call vims_user.USP_Get_Application_Dtls(?,NULL,NULL,NULL)}";//"select * from application where APPLICATION_ID=?";
	 
	public final static String addAppSpecSqlProc="{call vims_user.USP_SAVE_APPLICATION_SPECIALIST(?,?,?)}";
	                                                                                                                                    
	public final static String empIdListSql="select USER_NM, FIRST_NAME+' '+ISNULL(MIDDLE_INITIAL,'')+' '+ISNULL(LAST_NAME,'') AS EMPLOYEE_NAME from vims_user.EMPLOYEE ORDER BY EMPLOYEE_NAME";
	
	public final static String appIdListSql="select APPLICATION_ID, APPLICATION_NAME from vims_user.APPLICATION WHERE APP_STATUS='Active'";
	
	public final static String viewAppSpecListSql="select distinct asp.APPLICATION_ID ,a.APPLICATION_NAME , asp.USER_NM , e.FIRST_NAME+' '+ISNULL(e.MIDDLE_INITIAL,'')+' '+ISNULL(e.LAST_NAME,'') AS EMPNAME FROM vims_user.Application a INNER JOIN vims_user.APPLICATION_SPECIALISTS asp ON a.APPLICATION_ID=asp.APPLICATION_ID INNER JOIN vims_user.EMPLOYEE e ON e.USER_NM=asp.USER_NM";
	 
	public final static String getAppSpecialistSql="select asp.APPLICATION_ID ,a.APPLICATION_NAME ,	asp.USER_NM , e.FIRST_NAME+' ' +e.LAST_NAME AS EMPNAME,asp.COMMENTS FROM vims_user.Application a INNER JOIN vims_user.APPLICATION_SPECIALISTS asp ON a.APPLICATION_ID=asp.APPLICATION_ID INNER JOIN vims_user.EMPLOYEE e ON e.EMPLOYEE_ID=asp.EMPLOYEE_ID";
	
	public final static String viewAppSubCatListSql="select appSubCat.APPLICATION_ID,a.APPLICATION_NAME,appSubCat.APP_SUB_CAT_NAME FROM vims_user.Application a INNER JOIN vims_user.APPLICATION_SUB_CATEGORIES appSubCat ON a.APPLICATION_ID=appSubCat.APPLICATION_ID order by a.APPLICATION_NAME";

	public final static String getAppGrpIdListSql="SELECT USRGROUP_ID,GROUP_NAME FROM vims_user.USER_GROUP ORDER BY GROUP_NAME"; 

	public final static String getUsrGrpListSql="{call [Vims_User].[USP_Get_App_Grps]}";

	public final static String addAppGrpSqlProc="{call vims_user.USP_SAVE_APP_GROUP(?,?)}";
	
	public final static String getCustDetailsSql="select CUSTOMER_ID, CUSTOMER_NAME from vims_user.CUSTOMER ORDER BY CUSTOMER_NAME";
	
	public final static String addAppSubCatSqlProc="{call vims_user.USP_SAVE_APP_SUB_CATEGORIES(?,?)}";
	
	public final static String addAppCustSqlProc="{call vims_user.USP_SAVE_APPLICATION_CUSTOMER(?,?)}";

	public final static String getAppCustDetSql="{call VIMS_USER.USP_Get_Cust_App}";//"select APPLICATION_ID,CUSTOMER_ID from APP_SOLD_TO";
	
	public final static String supportCenterListSql="select SUPPORT_CENTER_ID,SUPP_CENTER_NAME from vims_user.SUPPORT_CENTER ORDER BY SUPP_CENTER_NAME";
	
	public final static String supportCenterManagerSql="select SUP_CNTR_MANAGER from vims_user.SUPPORT_CNTR_MANAGER where SUPPORT_CENTER_ID=?";
	
	
	/* These are the queries used for the Customer Module.
	 * Author: saikumar.m
	 * Date  :11-19-2008.
	 */	
	public final static String getAllCustIssuesListSql="select i.incident_id,a.application_name,i.incident_title,i.inct_status, convert(varchar,i.incident_posted_date,101)+' '+convert(varchar,i.incident_posted_date,108) as CREATED_DATE_TIME, i1.incident_type_desc,convert(varchar,i.due_date,101)+' '+convert(varchar,i.due_date,108), convert(varchar,i.closed_date,101)+' '+convert(varchar,i.closed_date,108) as closedDate,DATEDIFF(hour,i.incident_posted_date,i.closed_date) as ResolutionHours from vims_user.incident i,vims_user.application a,vims_user.incident_type i1 where CUSTOMER_ID=? and i.application_id=a.application_id and i.incident_type=i1.incident_type order by convert(int,incident_id) asc ";
	public final static String getCustIssuesListSql="select i.incident_id,a.application_name,i.incident_title,i.inct_status, convert(varchar,i.incident_posted_date,101)+' '+convert(varchar,i.incident_posted_date,108) as CREATED_DATE_TIME, i1.incident_type_desc,convert(varchar,i.due_date,101)+' '+convert(varchar,i.due_date,108), convert(varchar,i.closed_date,101)+' '+convert(varchar,i.closed_date,108) as closedDate,DATEDIFF(hour,i.incident_posted_date,i.closed_date) as ResolutionHours from vims_user.incident i,vims_user.application a,vims_user.incident_type i1 where CUSTOMER_ID=? and INCT_STATUS=? and i.application_id=a.application_id and i.incident_type=i1.incident_type order by convert(int,incident_id) asc ";	
	
				
	/*
	 * My To Do List related queries
	 * Author :  Sudhir.k
	 * date : 11/27/2008
	*/
	
	public final static String MyToList ="SELECT i.Incident_ID, a.Application_Name, i.Incident_Title, s.Assigned_BY,CONVERT(VARCHAR,i.Incident_Posted_Date,101) AS PostedDate, CONVERT(VARCHAR,i.Due_Date,101) AS DueDate, i1.Incident_Type_Desc AS IncidentType, s.Inct_Status_To AS Status FROM vims_user.INCIDENT i, vims_user.INCIDENT_STATUS s, vims_user.INCIDENT_TYPE i1, vims_user.APPLICATION a WHERE i.Incident_ID = s.Incident_ID AND i.Incident_Type = i1.Incident_Type AND i.Application_ID = a.Application_ID AND s.Inct_StatusID = (SELECT MAX(Inct_StatusID) FROM vims_user.INCIDENT_STATUS WHERE Incident_ID =s.Incident_ID) AND (s.Inct_Status_To = 'Assigned' OR s.Inct_Status_To = 'Accepted') AND s.Assigned_To=?";
	//public final static String MyToList ="SELECT inc.Incident_ID,app.Application_Name,inc.Incident_Title,incs.Assigned_By, CONVERT(VARCHAR,inc.Incident_Posted_Date,101) AS PostedDate, CONVERT(VARCHAR,inc.Due_Date,101) AS DueDate,inct.Incident_Type_DESC, incs.Inct_Status_To FROM Incident inc INNER JOIN Incident_Status incs ON inc.Incident_ID = incs.Incident_ID INNER JOIN Incident_Type inct ON inc.Incident_Type = inct.Incident_Type INNER JOIN Application app ON inc.Application_ID = app.Application_ID WHERE incs.inct_status_to in ('Assigned','Accepted','Rejected','Open','Completed') AND inc.Incident_ID IN (SELECT Incident_ID FROM Incident_Status WHERE  assigned_to=?)";
	//public final static String  MyToList = "SELECT inc.Incident_ID,app.Application_Name,inc.Incident_Title,incs.Assigned_By, CONVERT(VARCHAR,inc.Incident_Posted_Date,101) AS PostedDate, CONVERT(VARCHAR,inc.Due_Date,101) AS DueDate,inct.Incident_Type_DESC, incs.Inct_Status_To FROM Incident inc INNER JOIN Incident_Status incs ON inc.Incident_ID = incs.Incident_ID INNER JOIN Incident_Type inct ON inc.Incident_Type = inct.Incident_Type INNER JOIN Application app ON inc.Application_ID = app.Application_ID WHERE incs.inct_status_to in ('Assigned','Accepted','Rejected','Open','Completed') and incs.assigned_to=?";
	public final static String MyToList1 ="SELECT i.Incident_ID, a.Application_Name, i.Incident_Title, s.Assigned_BY, CONVERT(VARCHAR,i.Incident_Posted_Date,101) AS PostedDate, CONVERT(VARCHAR,i.Due_Date,101) AS DueDate, i1.Incident_Type_Desc AS IncidentType, s.Inct_Status_To AS Status FROM vims_user.INCIDENT i INNER JOIN vims_user.INCIDENT_STATUS s ON i.Incident_ID = s.Incident_ID INNER JOIN  vims_user.INCIDENT_TYPE i1 ON i.Incident_Type = i1.Incident_Type INNER JOIN vims_user.APPLICATION a ON i.Application_ID = a.Application_ID WHERE (s.Inct_Status_To = 'Open' OR s.Inct_Status_To = 'Accepted') AND s.Assigned_To=?";
	public final static String MyToListSortByDate="SELECT  i.Incident_ID, a.Application_Name, i.Incident_Title, s.Assigned_BY, CONVERT(VARCHAR,i.Incident_Posted_Date,101) AS PostedDate, CONVERT(VARCHAR,i.Due_Date,101) AS DueDate, i1.Incident_Type_Desc AS IncidentType, s.Inct_Status_To AS Status FROM  vims_user.INCIDENT i, vims_user.INCIDENT_STATUS s, vims_user.INCIDENT_TYPE i1, vims_user.APPLICATION a WHERE  i.Incident_ID = s.Incident_ID AND  i.Incident_Type = i1.Incident_Type AND  i.Application_ID = a.Application_ID AND  s.Inct_StatusID = (SELECT MAX(Inct_StatusID) FROM vims_user.INCIDENT_STATUS WHERE Incident_ID = s.Incident_ID) AND  (s.Inct_Status_To = 'Assigned' OR s.Inct_Status_To = 'Accepted') AND   s.Assigned_To=? order by PostedDate ";
	public final static String MyToListSortByApplication="SELECT  i.Incident_ID, a.Application_Name , i.Incident_Title, s.Assigned_BY, CONVERT(VARCHAR,i.Incident_Posted_Date,101) AS PostedDate, CONVERT(VARCHAR,i.Due_Date,101) AS DueDate, i1.Incident_Type_Desc AS IncidentType, s.Inct_Status_To AS Status FROM  vims_user.INCIDENT i, vims_user.INCIDENT_STATUS s, vims_user.INCIDENT_TYPE i1, vims_user.APPLICATION a WHERE  i.Incident_ID = s.Incident_ID AND  i.Incident_Type = i1.Incident_Type AND  i.Application_ID = a.Application_ID AND  s.Inct_StatusID = (SELECT MAX(Inct_StatusID) FROM vims_user.INCIDENT_STATUS WHERE Incident_ID = s.Incident_ID) AND  (s.Inct_Status_To = 'Assigned' OR s.Inct_Status_To = 'Accepted') AND   s.Assigned_To=? order by a.Application_Name" ;
	

	/*
	 * FAQ's related queries
	 * Author :  geeta.M
	 * date : 12/26/2008
	*/
    
	public static final String getFaqListsql="select Vims_FAQ_NBR,Qstn_TXT,Ans_TXT from vims_user.TVims_FAQ";
	public static final String getAboutUsListsql="select Vims_Aboutus_NBR,About_TXT from vims_user.TVimsAboutUs";
	public static final String getPrivacyPolicyListsql="select Vims_Privacy_Plcy_NBR,Privacy_TXT from vims_user.TVims_Privacy_Plcy";
	public static final String getTermsConditionsListsql="select Vims_Trm_Cond_NBR,Terms_TXT from vims_user.TVims_Trm_Cond";
    
 
	/*Version No Query*/
	
	public static final String getVersionNoSql="select MAX(VERSION_NUMBER) from vims_user.VERSION_NO";
	public static final String getIssueSeveritySQL = "select INCIDENT_TYPE from vims_user.INCIDENT where INCIDENT_ID=?";

}
	