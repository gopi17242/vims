<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page import="java.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/fn"  prefix="fn" %>

<html>
<head>
<LINK href="./css/StyleSheet.css" rel="STYLESHEET" type="text/css">
<script language="javascript" type="text/javascript" src="./script-test/configuration.js"></script>
<script>
window.onload=function() {
     window.print();
    window.close();
};
</script>
</head>
<body>
<br>

<c:if test="${(param.type) eq 'detailedIssues'}">
<logic:present name="IssueReportList">
<table border="0" width="300%" cellspacing="2" cellpadding="2">
<tr>
<td align="left" class="labelField">Issue Id</td>
    <td align="left" class="labelField">Application Name</td>
    <td align="left" class="labelField">Title</td>
    <td align="left" class="labelField">Issue Type</td>
    <td align="left" class="labelField">Posted By</td>
    <td align="left" class="labelField">Posted Date</td>
    <td align="left" class="labelField">Due Date</td>
    <td align="left" class="labelField">Closed Date</td>
    <td align="left" class="labelField">Issue Completion Date</td>
    <td align="left" class="labelField">Status</td>
    <td align="left" class="labelField">Priority</td>
    <td align="left" class="labelField">Description</td>
</tr>
<c:forEach items="${IssueReportList}" var="record"> 
<tr valign="top">
    <td align="left" class="labelField"><c:out value="${record.Issue_Id}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Application_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Title}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Issue_Type}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Posted_By}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Posted_Date}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Due_Date}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Closed_Date}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Issue_Completion_Date}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Status}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Priority}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Description}" /></td>    
</tr>
</c:forEach>
</table> 
</logic:present>
</c:if>

<c:if test="${(param.type) eq 'summaryIssues'}">
<logic:present name="IssueReportList">
<table border="0" width="300%" cellspacing="2" cellpadding="2">
<tr>
	<td align="left" class="labelField">Issue Id</td>
    <td align="left" class="labelField">Application Name</td>    
    <td align="left" class="labelField">Title</td>
    <td align="left" class="labelField">Status</td>
    <td align="left" class="labelField">Severity</td>
    <td align="left" class="labelField">Posted Date</td>
    <td align="left" class="labelField">Due Date</td>
</tr>
<c:forEach items="${IssueReportList}" var="record"> 
<tr valign="top">
    <td align="left" class="labelField"><c:out value="${record.Issue_Id}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Application_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Title}" /></td>
     <td align="left" class="labelField"><c:out value="${record.Status}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Issue_Type}" /></td>    
    <td align="left" class="labelField"><c:out value="${record.Posted_Date}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Due_Date}" /></td>
    
   
   
</tr>
</c:forEach>
</table> 
</logic:present>
</c:if>



<c:if test="${(param.type) eq 'DetailView'}">
<logic:present name="issueDetails">
<table border="0" width="100%" cellspacing="2" cellpadding="2">
<tr>
	<td align="left" class="labelField" width="30%">Issue Id</td>
	<td align="left" class="labelField" width="70%"><c:out value="${issueDetails.Issue_Id}" /></td>
</tr>
<tr>
 	 <td align="left" class="labelField">Title</td>
 	 <td align="left" class="labelField"><c:out value="${issueDetails.Title}" /></td>
 </tr> 
 <tr>
  	<td align="left" class="labelField">Description</td>
  	<td align="left" class="labelField"><c:out value="${issueDetails.Description}" /></td>    
 </tr>
 <tr>
  	<td align="left" class="labelField">Application Name</td>
  	<td align="left" class="labelField"><c:out value="${issueDetails.Application_Name}" /></td>
 </tr>
 <tr>
     <td align="left" class="labelField">Priority</td>
     <td align="left" class="labelField"><c:out value="${issueDetails.Priority}" /></td>
 </tr>
 <tr>
     <td align="left" class="labelField">Posted By</td>
	 <td align="left" class="labelField"><c:out value="${issueDetails.Posted_By}" /></td>
 </tr>
 <tr>
     <td align="left" class="labelField">Issue Type</td>
     <td align="left" class="labelField"><c:out value="${issueDetails.Issue_Type}" /></td>
 </tr>
 <tr>
     <td align="left" class="labelField">Status</td>
     <td align="left" class="labelField"><c:out value="${issueDetails.Status}" /></td>
 </tr>
 <tr>
     <td align="left" class="labelField">Posted Date</td>
     <td align="left" class="labelField"><c:out value="${issueDetails.Posted_Date}" /></td>
 </tr>
 <tr>
     <td align="left" class="labelField">Due Date</td>
     <td align="left" class="labelField"><c:out value="${issueDetails.Due_Date}" /></td>
 </tr>
 <tr>
     <td align="left" class="labelField">Closed Date</td>
     <td align="left" class="labelField"><c:out value="${issueDetails.Closed_Date}" /></td>
 </tr>
 <tr>
     <td align="left" class="labelField">Issue Completion Date</td>
     <td align="left" class="labelField"><c:out value="${issueDetails.Issue_Completion_Date}" /></td>    
 </tr>   
</table> 
<br>
<table width="100%" border="0" cellspacing="2" cellpadding="2">
<tr>
<td align="left" class="labelField" width="15%">Assigned By</td>
<td align="left" class="labelField" width="15%">Assigned To</td>
<td align="left" class="labelField" width="15%">Status From</td>
<td align="left" class="labelField" width="15%">Status To</td>
<td align="left" class="labelField" width="15%">Status Change on Date</td>
<td align="left" class="labelField" width="40%">Comments/Resolution</td>
</tr>
<c:forEach items="${issueCycle}" var="record">
<tr>
 <td align="left" class="labelField"><c:out value="${record.Assigned_By}" /></td>
 <td align="left" class="labelField"><c:out value="${record.Assigned_To}" /></td>
 <td align="left" class="labelField"><c:out value="${record.status_From}" /></td>
 <td align="left" class="labelField"><c:out value="${record.Status_To}" /></td>
  <td align="left" class="labelField"><c:out value="${record.Status_Change_on_Date}" /></td>
 <td align="left" class="labelField"><c:out value="${record.Comments}" /></td>
</tr>
</c:forEach>
</table>
</logic:present>
</c:if>


<c:if test="${(param.type) eq 'detailedCustomer'}">
<logic:present name="customerReports">
<table border="0" width="200%" cellspacing="2" cellpadding="2">
<tr>
<td align="left" class="labelField">Customer Name</td>
    <td align="left" class="labelField">Application Name</td>
    <td align="left" class="labelField">Status</td>
    <td align="left" class="labelField">Start Date</td>
    <td align="left" class="labelField">End Date</td>
    <td align="left" class="labelField">Application Owner</td>
    <td align="left" class="labelField">Issues</td>
    <td align="left" class="labelField">Support Manager</td>
    <td align="left" class="labelField">Group</td>
    <td align="left" class="labelField">Group Manager</td>
    <td align="left" class="labelField">Members</td>
</tr>
<c:forEach items="${customerReports}" var="record"> 
<tr valign="top">
    <td align="left" class="labelField"><c:out value="${record.Customer_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Application_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Status}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Start_Date}" /></td>
    <td align="left" class="labelField"><c:out value="${record.End_Date}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Application_Owner}" /></td>
    <td align="left" class="labelField"><c:out value="${record.No_of_Issues}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Support_Manager}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Group}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Group_Supervisor}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Members}" /></td>
</tr>
</c:forEach>
</table> 
</logic:present>
</c:if>


<c:if test="${(param.type) eq 'summaryCustomer'}">
<logic:present name="customerReports">
<table border="0" width="200%" cellspacing="2" cellpadding="2">
<tr>
<td align="left" class="labelField">Customer Name</td>
    <td align="left" class="labelField">Application Name</td>
    <td align="left" class="labelField">Status</td>
    <td align="left" class="labelField">Start Date</td>
    <td align="left" class="labelField">End Date</td>
    <td align="left" class="labelField">Application Owner</td>
    <td align="left" class="labelField">No of Issues</td>    
</tr>
<c:forEach items="${customerReports}" var="record"> 
<tr valign="top">
    <td align="left" class="labelField"><c:out value="${record.Customer_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Application_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Status}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Start_Date}" /></td>
    <td align="left" class="labelField"><c:out value="${record.End_Date}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Application_Owner}" /></td>
    <td align="left" class="labelField"><c:out value="${record.No_of_Issues}" /></td>   
</tr>
</c:forEach>
</table> 
</logic:present>
</c:if>




<c:if test="${(param.type) eq 'detailedApplication'}">
<logic:present name="ApplicationReportsList">
<table border="0" width="200%" cellspacing="2" cellpadding="2">
<tr>
<td align="left" class="labelField">Application Name</td>
    <td align="left" class="labelField">Customer Name</td>
    <td align="left" class="labelField">Application Owner</td>
    <td align="left" class="labelField">Support Manager</td>
    <td align="left" class="labelField">Status</td>
    <td align="left" class="labelField">Start Date</td>
    <td align="left" class="labelField">End Date</td>
    <td align="left" class="labelField">Critical Response Time(Hrs)</td>
    <td align="left" class="labelField">Critical Warning Before(Hrs)</td>
    <td align="left" class="labelField">Major Response Time(Hrs)</td>
    <td align="left" class="labelField">Major Warning Before(Hrs)</td>
    <td align="left" class="labelField">Minor Response Time(Hrs)</td>
    <td align="left" class="labelField">Minor Warning Before(Hrs)</td>
    <td align="left" class="labelField">Group Members</td>
</tr>
<c:forEach items="${ApplicationReportsList}" var="record"> 
<tr valign="top">
    <td align="left" class="labelField"><c:out value="${record.Application_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Customer_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Application_Owner}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Support_Manager}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Status}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Start_Date}" /></td>
    <td align="left" class="labelField"><c:out value="${record.End_Date}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Critical_Response_Time_in_Hrs}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Critical_Warning_Before_in_Hrs}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Major_Response_Time_in_Hrs}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Major_Warning_Before_in_Hrs}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Minor_Response_Time_in_Hrs}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Minor_Warning_Before_in_Hrs}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Group_Members}" /></td>
</tr>
</c:forEach>
</table> 
</logic:present>
</c:if>

<c:if test="${(param.type) eq 'summaryApplication'}">
<logic:present name="ApplicationReportsList">
<table border="0" width="200%" cellspacing="2" cellpadding="2">
<tr>
<td align="left" class="labelField">Application Name</td>
    <td align="left" class="labelField">Customer Name</td>
    <td align="left" class="labelField">Application Owner</td>
    <td align="left" class="labelField">Support Manager</td>
    <td align="left" class="labelField">Status</td>
    <td align="left" class="labelField">Start Date</td>
    <td align="left" class="labelField">End Date</td>    
</tr>
<c:forEach items="${ApplicationReportsList}" var="record"> 
<tr valign="top">
    <td align="left" class="labelField"><c:out value="${record.Application_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Customer_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Application_Owner}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Support_Manager}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Status}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Start_Date}" /></td>
    <td align="left" class="labelField"><c:out value="${record.End_Date}" /></td>  
</tr>
</c:forEach>
</table> 
</logic:present>
</c:if>






<c:if test="${(param.type) eq 'SLA'}">
<logic:present name="SLAReportsList">
<table border="0" width="200%" cellspacing="2" cellpadding="2">
<tr>
<td align="left" class="labelField">Application Name</td>
    <td align="left" class="labelField">Total Issues</td>
    <td align="left" class="labelField">Issues within SLA</td>
    <td align="left" class="labelField">Issues out of  SLA</td>    
</tr>
<c:forEach items="${SLAReportsList}" var="record"> 
<tr valign="top">
    <td align="left" class="labelField"><c:out value="${record.Application_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Total_Issues}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Issues_within_SLA}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Issues_out_of__SLA}" /></td>
   </tr>
</c:forEach>
</table> 
</logic:present>
</c:if>

<c:if test="${(param.type) eq 'Programmer'}">
<logic:present name="empReports">
<table border="0" width="200%" cellspacing="2" cellpadding="2">
<tr>
<td align="left" class="labelField">Employee Name</td>
    <td align="left" class="labelField">Group Manager</td>
    <td align="left" class="labelField">Application Name</td>   
    <td align="left" class="labelField">Assigned</td>
    <td align="left" class="labelField">Rejected</td>
    <td align="left" class="labelField">Completed</td>
    <td align="left" class="labelField">Escalated</td>
    <td align="left" class="labelField">Closed</td>
</tr>
<c:forEach items="${empReports}" var="record"> 
<tr valign="top">
    <td align="left" class="labelField"><c:out value="${record.Employee_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Group_Supervisor}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Application_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Assigned}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Rejected}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Completed}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Escalated}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Closed}" /></td>
</tr>
</c:forEach>
</table> 
</logic:present>
</c:if>
<c:if test="${(param.type) eq 'Supervisor'}">
<logic:present name="empReports">
<table border="0" width="200%" cellspacing="2" cellpadding="2">
<tr>
<td align="left" class="labelField">Group_Supervisor</td>
    <td align="left" class="labelField">Group_Name</td>
    <td align="left" class="labelField">Group_Members</td>
    <td align="left" class="labelField">Application_Name</td>
    <td align="left" class="labelField">No_of_Issues</td>
    <td align="left" class="labelField">Status</td>   
    <td align="left" class="labelField">Begin_Date</td>
    <td align="left" class="labelField">End_Date</td>
</tr>
<c:forEach items="${empReports}" var="record"> 
<tr valign="top">
    <td align="left" class="labelField"><c:out value="${record.Group_Supervisor}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Group_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Group_Members}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Application_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.No_of_Issues}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Status}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Begin_Date}" /></td>
    <td align="left" class="labelField"><c:out value="${record.End_Date}" /></td>
</tr>
</c:forEach>
</table> 
</logic:present>
</c:if>
<c:if test="${(param.type) eq 'Manager'}">
<logic:present name="empReports">
<table border="0" width="200%" cellspacing="2" cellpadding="2">
<tr>
<td align="left" class="labelField">Support Manager</td>
    <td align="left" class="labelField">Support Center Name</td>
    <td align="left" class="labelField">Group Supervisors</td>
    <td align="left" class="labelField">Application Names</td>    
</tr>
<c:set var="break" value='<br>'></c:set>
<c:set var="replaceWith" value=","></c:set>

<%
ArrayList alist=(ArrayList)session.getAttribute("empReports");
for(int count=0;count<alist.size();count++){
HashMap hashMap=(HashMap)alist.get(count); %>
<tr valign="top">
    <td align="left" class="labelField"><%=((String)hashMap.get("Support_Manager"))%></td>
    <td align="left" class="labelField"><%=((String)hashMap.get("Support_Center_Name"))%></td>
    <td align="left" class="labelField"><%=((String)hashMap.get("Group_Supervisors"))%></td>    
    <td align="left" class="labelField"><%=((String)hashMap.get("Application_Names"))%></td>
</tr>
<%} %>
</table> 
</logic:present>
</c:if>
<c:if test="${(param.type) eq 'Availableingroups'}">
<logic:present name="empReports">
<table border="0" width="200%" cellspacing="2" cellpadding="2">
<tr>
<td align="left" class="labelField">Group Name</td>
    <td align="left" class="labelField">Employee ID</td>
    <td align="left" class="labelField">Employee Name</td>
    <td align="left" class="labelField">E-mail</td>
    <td align="left" class="labelField">Position</td>   
    
</tr>
<c:forEach items="${empReports}" var="record"> 
<tr valign="top">
    <td align="left" class="labelField"><c:out value="${record.Group_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Employee_ID}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Employee_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.E_mail}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Position}" /></td>   
   
</tr>
</c:forEach>
</table> 
</logic:present>
</c:if>
<c:if test="${(param.type) eq 'AvailableEmployees'}">
<logic:present name="empReports">
<table border="0" width="200%" cellspacing="2" cellpadding="2">
<tr>
<td align="left" class="labelField">Employee ID</td>
    <td align="left" class="labelField">Employee Name</td>
    <td align="left" class="labelField">E-mail</td>
    <td align="left" class="labelField">Phone Number</td>
    <td align="left" class="labelField">Position</td>   
</tr>
<c:forEach items="${empReports}" var="record"> 
<tr valign="top">
    <td align="left" class="labelField"><c:out value="${record.Employee_ID}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Employee_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.E_mail}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Phone_Number}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Position}" /></td>    
</tr>
</c:forEach>
</table> 
</logic:present>
</c:if>

<c:if test="${(param.type) eq 'AllEmployees'}">
<logic:present name="empReports">
<table border="0" width="200%" cellspacing="2" cellpadding="2">
<tr>
<td align="left" class="labelField">Employee ID</td>
    <td align="left" class="labelField">Employee Name</td>
    <td align="left" class="labelField">E-mail</td>
    <td align="left" class="labelField">Phone Number</td>
    <td align="left" class="labelField">Position</td>   
</tr>
<c:forEach items="${empReports}" var="record"> 
<tr valign="top">
    <td align="left" class="labelField"><c:out value="${record.Employee_ID}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Employee_Name}" /></td>
    <td align="left" class="labelField"><c:out value="${record.E_mail}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Phone_Number}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Position}" /></td>    
</tr>
</c:forEach>
</table>
</logic:present>
</c:if>
<c:if test="${(param.type) eq 'AllPositions'}">
<logic:present name="empReports">
<table border="0" width="200%" cellspacing="2" cellpadding="2">
<tr>
<td align="left" class="labelField">Support Manager</td>
    <td align="left" class="labelField">Group Supervisor</td>
    <td align="left" class="labelField">Group Members</td>
    <td align="left" class="labelField">No of Applications</td>
    <td align="left" class="labelField">No of Issues</td>   
</tr>
<c:forEach items="${empReports}" var="record"> 
<tr valign="top">
    <td align="left" class="labelField"><c:out value="${record.Support_Manager}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Group_Supervisor}" /></td>
    <td align="left" class="labelField"><c:out value="${record.Group_Members}" /></td>
    <td align="left" class="labelField"><c:out value="${record.No_of_Applications}" /></td>
    <td align="left" class="labelField"><c:out value="${record.No_of_Issues}" /></td>    
</tr>
</c:forEach>
</table> 
</logic:present>
</c:if>
</body>
</html>