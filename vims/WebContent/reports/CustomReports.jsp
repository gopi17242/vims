<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
<%@page import ="java.util.*"%>

<html:html>
<head>
<title>Vertex Issue  Management System - Custom Reports</title>
<script type="text/javascript" src="./script-test/customReports.js"></script>
<script type="text/javascript" src="./script-test/datetimepicker.js"></script>
<script language="javascript" src="./script-test/advPopCalender.js" type="text/javascript"></script>
</head>

<table border="0" width="100%" cellpadding="0" cellspacing="0" >
<%ArrayList RecordsList=new ArrayList();%>
<html:form action="customReports.do?reportType=getCustomReports" focus="orginalList">
<tr>
<td width="45%" align="center" class="heading_blue">Select Criteria</td>
<td  align="center" valign="middle" width="10%">&nbsp;</td>
<td width="45%" align="center" class="heading_blue">Selected Criteria</td>
</tr>
<tr>
<td width="45%" align="left">
	<select name="orginalList" size="10" style="width :350px"  Class="multiplelist" multiple >
		<c:forEach items="${CustomReportsList}" var="reportsList" >
		<option value='<c:out value="${reportsList}"/>'><%=((String)pageContext.getAttribute("reportsList")).replace("_"," ")%></option>
		</c:forEach>
	</select>
</td>

<td  align="center" valign="middle" width="10%">
   <p>
      <input type="button" style="width : 30px" name="btnRight2" value=">>" class="linkbutton_background" onClick="return moveFromLeftToRight()">
   <br>             
      <input type="button" style="width : 30px" name="btnLeft2" value="<<" " class="linkbutton_background" onClick="return moveFromRightToLeft()">
   </p>
</td>
     
<td width="45%" align="center">
<logic:present name="SelectedCriteriaList">
	<html:select property="selectedList" size="10" style="width :350px"  multiple="true" styleClass="multiplelist">		
	<c:forEach items="${SelectedCriteriaList}" var="reportsList" begin="1">
		<option value='<c:out value="${reportsList}"/>'><%=((String)pageContext.getAttribute("reportsList")).replace("_"," ")%></option>
		</c:forEach>
	</html:select>
</logic:present>
<logic:notPresent name="SelectedCriteriaList">
	<select name="selectedList" size="10" style="width :350px"  multiple Class="multiplelist">		
	</select>
</logic:notPresent>
	
	<span id="error"></span> 
</td>
</tr>
<tr id="" style="display:none">
<td >
	<select name="columList" size="10" style="width :350px"  multiple Class="multiplelist">		
	</select>	
</td>
</tr>

<tr>
<td colspan="3" align="center"><button type="button" onclick="displaySelected();" accesskey="k" name="okButton" class="linkbutton_background">O<u>k</u></button></td>
</tr>

<tr>
<td height="10px"></td>
<td height="10px"></td>
</tr>
<tr>
<td colspan="3"  align="left">
<table width="100%" border="0" cellSpacing="0" cellpadding="0" align="left">
<!--<tr id="Issue ID" style="display:none">
<td class="labelField" width="20%">Issue ID</td>
<td><input type="text" name="issueId" class="textbox_default" maxlength="8"></td>
</tr>
-->
<tr id="Status" style="display:none">
<td class="labelField" width="20%">Status</td>
<td width="30%">
<select name="issueStatus" size="20px" class="dropdownlist">
<option value="">Select an Issue Status</option>
<option value="open">Open</option> 
<option value="assigned">Assigned</option> 
<option value="accepted">Accepted</option> 
<option value="closed">Closed</option>
<option value="rejected">Rejected</option>
<option value="escalated">Escalated</option>
</select>
</td>
<td width="50%"></td>
</tr>
<tr id="Priority" style="display:none">
<td class="labelField">Priority</td>
<td>
<select name="issuePriority" size="20px" class="dropdownlist">
<option value="">Select an Issue Priority</option>
<option value="p1">Priority1</option>
<option value="p2">Priority2</option>
<option value="p3">Priority3</option>
</select>
</td>
<td width="50%"></td>
</tr>
<tr id="Severity" style="display:none">
<td class="labelField">Severity</td>
<td>
<select name="issueSeverity" size="20px" class="dropdownlist">
<option value="">Select an Issue Severity</option>
<option value="cri">Critical</option>
<option value="maj">Major</option>
<option value="min">Minor</option>
</select>
</td>
<td width="50%"></td>
</tr>
<tr id="Created_Date" style="display:none">
<td class="labelField">Created Date&nbsp;&nbsp;From</td>
<td class="labelField"><input type="text" name="createdFromDate" maxlength="10" style="width:75px" readonly class="textbox_default">
<!--<a href="javascript:NewCal('createdFromDate','mmddyyyy',false,12)">-->
<script>
     document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].createdFromDate,window.document.forms[0].createdFromDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
</script>
&nbsp;&nbsp;To
<input type="text" name="createdToDate" maxlength="10" style="width:75px" readonly class="textbox_default">
<!--<a href="javascript:NewCal('createdToDate','mmddyyyy',false,12)">-->
<script>
     document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].createdToDate,window.document.forms[0].createdToDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
</script>
</td>
<td width="50%"></td>
</tr>

<tr id="Closed_Date" style="display:none">
<td class="labelField">Closed Date&nbsp;&nbsp;From</td>
<td class="labelField"><input type="text" name="closedFromDate" maxlength="10" style="width:75px" readonly class="textbox_default">
<!--<a href="javascript:NewCal('closedFromDate','mmddyyyy',false,12)">-->
<script>
     document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].closedFromDate,window.document.forms[0].closedFromDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
</script>
&nbsp;&nbsp;To
<input type="text" name="closedToDate" maxlength="10" style="width:75px" readonly class="textbox_default">
<!--<a href="javascript:NewCal('closedToDate','mmddyyyy',false,12)">-->
<script>
     document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].closedToDate,window.document.forms[0].closedToDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
</script>
</td>
<td width="50%"></td>
</tr>
<tr id="Application" style="display:none">
<td class="labelField">Application</td>
<td><select name="applicationName" size="20px" class="dropdownlist">
<option value="">Select an Application</option>
<c:forEach items="${ApplicationDetailsList}" var="applNames">
<option value='<c:out value="${applNames.APPLICATION_ID}"/>'><c:out value="${applNames.APPLICATION_NAME}"/></option>
</c:forEach>
</select>
</td>
<td width="50%"></td>
</tr>
<tr id="Customer" style="display:none">
<td class="labelField">Customer</td>
<td><select name="customerName" size="20px" class="dropdownlist">
<option value="">Select a Customer</option>
<c:forEach items="${CustomerDetailsList}" var="cust">
<option value='<c:out value="${cust.CUSTOMER_ID}"/>'><c:out value="${cust.CUSTOMER_NAME}"/></option>
</c:forEach>
</select>
</td>
<td width="50%"></td>
</tr>
<tr id="Employee" style="display:none">
<td class="labelField">Employee</td>
<td><select name="employeeName" size="20px" class="dropdownlist">
<option value="">Select an Employee</option>
<c:forEach items="${EmployeeList}" var="emplist">
<option value='<c:out value="${emplist.User_NM}"/>'><c:out value="${emplist.Employee_Name}"/></option>
</c:forEach>
</select>
</td>
<td width="50%"></td>
</tr>
<tr id="Support_Center" style="display:none">
<td class="labelField">Support Center</td>
<td><select name="supportCenter" size="20px" class="dropdownlist">
<option value="">Select a Support Center</option>
<c:forEach items="${SupportCenterList}" var="sptctr">
<option value='<c:out value="${sptctr.SUPPORT_CENTER_ID}"/>'><c:out value="${sptctr.SUPP_CENTER_NAME}"/></option>
</c:forEach>
</select>
</td>
<td width="50%"></td>
</tr>
<tr id="Manager" style="display:none">
<td class="labelField">Manager</td>
<td><select name="supportManager" size="20px" class="dropdownlist">
<option value="">Select a Support Manager</option>
<c:forEach items="${SupportMgrList}" var="supmgr">
<option value='<c:out value="${supmgr.SUPP_CENTER_MANAGER_ID}"/>'><c:out value="${supmgr.Manager_Name}"/></option>
</c:forEach>
</select>
</td>
<td width="50%"></td>
</tr>
<tr id="Group" style="display:none">
<td class="labelField">Groups</td>
<td>
<select name="groups" size="20px" class="dropdownlist">
<option value="">Select a Group</option>
<c:forEach items="${GroupDetailsList}" var="grps">
<option value='<c:out value="${grps.USRGROUP_ID}"/>'><c:out value="${grps.GROUP_NAME}"/></option>
</c:forEach>
</select>
</td>
<td width="50%"></td>
</tr>
<tr id="Supervisor" style="display:none">
<td class="labelField">Supervisor</td>
<td>
<select name="groupSupervisor" size="20px" class="dropdownlist">
<option value="">Select a Supervisor</option>
<c:forEach items="${GroupSupervisorList}" var="grpSupr">
<option value='<c:out value="${grpSupr.GROUP_SUPERVISOR_ID}"/>'><c:out value="${grpSupr.Supervisor_Name}"/></option>
</c:forEach>
</select>
</td>
<td width="50%"></td>
</tr>
<tr id="Assigned_To" style="display:none">
<td class="labelField">Assigned To</td>
<td>
<select name="groupMember" size="20px" class="dropdownlist">
<option value="">Select a Member</option>
<c:forEach items="${GroupMemList}" var="grpmem">
<option value='<c:out value="${grpmem.User_NM}"/>'><c:out value="${grpmem.Member_Name}"/></option>
</c:forEach>
</select>
</td>
<td width="50%"></td>
</tr>
<tr id="Purging_Status" style="display:none">
<td class="labelField">Purging Flag</td>
<td>
<select name="purgingFlag" size="20px" class="dropdownlist">
<option value="">Select a Purging Flag</option>
<option value="active">active</option>
<option value="inactive">inactive</option>
</select>
</td>
<td width="50%"></td>
</tr>
<tr><td height="10px" colspan="3"></td></tr>
<tr>
<td width="20%">&nbsp;</td>
<td align=left id="generate" style="display:none" width="30%" ><button type="button" name="genreports"  accesskey="G" class="linkbutton_Reports" onclick="getCustomreports()">&nbsp;&nbsp;<u>G</u>enerate</button></td>
<td width="50%">&nbsp;</td>
</tr>

<logic:present name="NoRecordsFound">
<tr>
<td height="10px" width="20%"class="heading_blue"><span id="displayMsg" >No Records Found</span></td>
<td width="30%"><button type="button" accesskey="R" class="linkbutton_Reports" onclick="backToReports()">&nbsp;&nbsp;Back To <u>R</u>eports</button></td>
<td width="50%">&nbsp;</td>
</tr>
</logic:present>
</html:form>    
<logic:present name="CustomReportsArrayList">
<tr>
<%int maximumRows=((ArrayList)session.getAttribute("CustomReportsArrayList")).size(); 
if(maximumRows>50)
{
	pageContext.setAttribute("maxRows",maximumRows);
}
else
{
	pageContext.setAttribute("maxRows","100");
}
%>
<td height="10px" colspan="3">
<ec:table  border="0" items="CustomReportsArrayList"   sortable="true" action="./customReports.do?reportType=generateCustomReports"
		imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10" maxRowsDisplayed="${maxRows}" var="serRes" >

		<ec:row highlightRow="true"> 
		<c:set var="singleItem" value="${serRes}" scope="session"></c:set>
		<%	RecordsList.add(session.getAttribute("singleItem")); %>
		<%ArrayList alist=(ArrayList)session.getAttribute("ColumnNames"); 
		for(int index=0;index<alist.size();index++){ %>			
		<ec:column property='<%=(String)alist.get(index)%>' title='<%=((String)alist.get(index)).replace("_"," ")%>' width="100px"/>
		<%} %>
			
		</ec:row>
</ec:table>
<% 
RecordsList.remove(0);
session.setAttribute("CustomReportsArrayList",RecordsList);
System.out.println("Records List-------"+RecordsList);
%>
</td>
</tr>
<tr>
<html:form action="customReports.do?reportType=getCustomReports">
<td align="center" colspan="3">
<table border="0" width="100%" align="center">
<tr>
<td width="30%"></td>
<td align="center"><button type="button" name="backreports"  class="linkbutton_Reports" accesskey="R" onclick="backToReports()">Back To <u>R</u>eports</button>&nbsp;&nbsp;</td>
<td align="center"><button type="button" name="pdf"   class="linkbutton_Reports" accesskey="P" onclick="generateReport('PDF')" >Export To <u>P</u>DF</button>&nbsp;&nbsp;</td>
<td align="center"><button type="button" name="excel"  class="linkbutton_Reports" accesskey="E" onclick="generateReport('EXCEL')">Export To <u>E</u>xcel</button></td>
<td width="30%"></td>
</tr>
</table>
</td>
</html:form>
</tr>

</logic:present>
<tr>
<td height="10px"></td>
</tr>
<tr>
</table>
</td>
</tr>
</table>
</html:html>