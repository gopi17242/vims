<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec"%>
<%@ taglib uri="/WEB-INF/tlds/datetime.tld" prefix="date" %>
<%@page import ="java.util.*"%>
<html>
<head>
<script>

function goHome()
 {
 window.location="./OtherEmployeeReport.do?param=employeeReports&pageId=EmployeeReports";
}

 var formObj; 
function generateExcelFile() {   
     formObj=document.forms[0];
  // alert(formObj.empType.value);
	formObj.action='./generateIssueExcelFile.do?param=generateFile&type='+formObj.empType.value;
	formObj.submit(); 
}
function printList() { 
formObj=document.forms[0];  
//alert(formObj.empType.value);
  if (navigator.appName == "Microsoft Internet Explorer")
	{
	 	window.open('./reports-Test/printReport.jsp?type='+formObj.empType.value, "VIMS", 'toolbar=no, status=no, left=360, top=250, scrollbars=yes, resize=yes');
	}
	else if (navigator.appName == "Netscape") 
	{
       window.open("./reports-Test/printReport.jsp?type="+formObj.empType.value, "VIMS",  'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=no,resizable=0,width=350,height=300,top=250,left=360');		
	}
}
</script>
<body>
<br>
	<table width="100%" border="0">
	<html:form action="VIMSReportsLookUpDispatchAction.do">
	<input type="hidden" name="empType" value=""/>
	</html:form>
	<logic:present name="empReports">
<tr valign="top">
<td align="center" class="heading_blue" colspan="3"><font size="2">Employee Report</font></td>
</tr>	
	
	<tr valign="top">
		<td align="left" class="labelField" width="20%"><b>Report Date </b></td>
		<td align="left" class="labelField" width="2%"><b>:</b></td>
		<td align="left" class="labelField" width="78%"><date:format pattern="MM/dd/yyyy"><date:currentTime/></date:format></td>
	</tr>
	<%String searchcri=(String)session.getAttribute("searchCriteria");
	int index=searchcri.indexOf(':');
	String strReportsbased=searchcri.substring(index+1,searchcri.length());%>
	<tr valign="top">
		<td align="left" class="labelField"><b>Report based on </b></td>
		<td align="left" class="labelField"><b>:</b></td>
		<td align="left" class="labelField"><%=strReportsbased%></td>	
	</tr>
<tr>
<%int maximumRows=((ArrayList)session.getAttribute("empReports")).size(); 
if(maximumRows>50)
{
	pageContext.setAttribute("maxRows",maximumRows);
}
else
{
	pageContext.setAttribute("maxRows","100");
}
%>
<c:if test="${ResourceType eq 'Programmer'}">
<td colspan="3">
<script>
document.getElementById('empType').value='Programmer'
</script>
<ec:table items="empReports" cellpadding="2" cellspacing="2" border="0" action="./VIMSReportsLookUpDispatchAction.do?param=generateEmployeeReports" imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10" maxRowsDisplayed="${maxRows}" var="serRes">
 <ec:row highlightRow="true">
 	 <ec:column property="Employee_Name" title="Employee&nbsp;&nbsp;Name" width="15%"/>
 	 <ec:column property="Group_Supervisor" title="Group&nbsp;&nbsp;Manager" width="15%"/>
 	 <ec:column property="Application_Name" title="Application&nbsp;&nbsp;Name" width="20%"/>
	 <ec:column property="Assigned" title="Issues&nbsp;&nbsp;Assigned" width="10%"/>
	 <ec:column property="Rejected" title="Issues&nbsp;&nbsp;Rejected" width="10%" />
	 <ec:column property="Completed" title="Issues&nbsp;&nbsp;Completed" width="10%"/>
	 <ec:column property="Escalated" title="Issues&nbsp;&nbsp;Escalated" width="10%"/>
	 <ec:column property="Closed" title="Issues&nbsp;&nbsp;Closed" width="10%"/> 
  </ec:row>
</ec:table>
</td>
</c:if>
<c:if test="${ResourceType eq 'Supervisor'}">
<td colspan="3">
<script>
document.getElementById('empType').value='Supervisor'
</script>
<ec:table items="empReports" action="./VIMSReportsLookUpDispatchAction.do?param=generateEmployeeReports" imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10" maxRowsDisplayed="${maxRows}" var="serRes">
 <ec:row highlightRow="true">
 	 <ec:column property="Group_Supervisor" title="Group Supervisor" width="18%"/>
 	 <ec:column property="Group_Name" title="Group Name" width="13%"/>
 	 <ec:column property="Group_Members" title="Group Members" width="16%"/>
	 <ec:column property="Application_Name" title="Application Name" width="18%"/>	
	 <ec:column property="Status" title="Status" width="8%"/>
	 <ec:column property="Begin_Date" title="Begin Date" width="9%"/>
	 <ec:column property="End_Date" title="End Date" width="11%"/> 
	 <ec:column property="No_of_Issues" title="No of Issues" width="7%"/>
  </ec:row>
</ec:table>
</td>
</c:if>
<c:if test="${ResourceType eq 'Manager'}">
<td colspan="3">
<script>
document.getElementById('empType').value='Manager'
</script>
<%ArrayList alist=(ArrayList)session.getAttribute("empReports"); %>
<ec:table items="empReports" action="./VIMSReportsLookUpDispatchAction.do?param=generateEmployeeReports" imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10" maxRowsDisplayed="${maxRows}" var="serRes">
 <ec:row highlightRow="true">
 	 <ec:column property="Support_Manager" title="Support Manager" width="25%"/>
 	 <ec:column property="Support_Center_Name" title="Support Center Name" width="25%"/>
 	 <c:set var="SupervisorItem" value="${serRes.Group_Supervisors}" scope="request"></c:set>
 	 <c:set var="ApplItem" value="${serRes.Application_Names}" scope="request"></c:set>
 	 <ec:column property="Group_Supervisors" title="Group Supervisors" width="25%"/>
	 <ec:column property="Application_Names" title="Application Names" width="25%"/>	  
  </ec:row>
</ec:table>
</td>
</c:if>
<c:if test="${ResourceType eq 'Availableingroups'}">
<td colspan="3">
<script>
document.getElementById('empType').value='Availableingroups'
</script>
<ec:table items="empReports" action="./VIMSReportsLookUpDispatchAction.do?param=generateEmployeeReports" imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10" maxRowsDisplayed="${maxRows}" var="serRes">
 <ec:row highlightRow="true">

 	 <ec:column property="Group_Name" width="35%" title="Group Name" />
 	 <ec:column property="Employee_ID"  width="15%" title="Employee ID" />
 	 <ec:column property="Employee_Name" width="20%" title="Employee Name" />
	 <ec:column property="E_mail" width="15%%"  title="E-mail" />	
	 <ec:column property="Position"width="15%" title="Position"  />

  </ec:row>
</ec:table>
</td>
</c:if>
<c:if test="${ResourceType eq 'AvailableEmployees'}">
<td colspan="3">
<script>
document.getElementById('empType').value='AvailableEmployees'
</script>
<ec:table items="empReports" action="./VIMSReportsLookUpDispatchAction.do?param=generateEmployeeReports" imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10" maxRowsDisplayed="${maxRows}" var="serRes">
 <ec:row highlightRow="true">

 	 <ec:column property="Employee_ID" title="Employee&nbsp;ID&nbsp;" width="15%"/>
 	 <ec:column property="Employee_Name" title="Employee&nbsp;Name&nbsp;" width="30%"/>
 	 <ec:column property="E_mail" title="E-mail" width="20%"/>
	 <ec:column property="Phone_Number" title="Phone&nbsp;Number&nbsp;" width="17.5%"/>
	 <ec:column property="Position" title="Position&nbsp;" width="17.5%"/>	 
  </ec:row>
</ec:table>
</td>
</c:if>

<c:if test="${ResourceType eq 'AllEmployees'}">
<td colspan="3">
<script>
document.getElementById('empType').value='AllEmployees'
</script>
<ec:table items="empReports" action="./VIMSReportsLookUpDispatchAction.do?param=generateEmployeeReports" imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10" maxRowsDisplayed="${maxRows}" var="serRes">
 <ec:row highlightRow="true">
 	 <ec:column property="Employee_ID" title="Employee&nbsp;ID" width="15%"/>
 	 <ec:column property="Employee_Name" title="Employee&nbsp;Name" width="30%"/>
 	 <ec:column property="E_mail" title="E-mail" width="20%"/>
	 <ec:column property="Phone_Number" title="Phone&nbsp;Number" width="17.5%"/>
	 <ec:column property="Position" title="Position"  width="17.5%"/>	 
  </ec:row>
</ec:table>
</td>
</c:if>

<c:if test="${ResourceType eq 'AllPositions'}">
<td colspan="3">
<script>
document.getElementById('empType').value='AllPositions'
</script>
<ec:table items="empReports" action="./VIMSReportsLookUpDispatchAction.do?param=generateEmployeeReports" imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10" maxRowsDisplayed="${maxRows}" var="serRes">
 <ec:row highlightRow="true">
 	 <ec:column property="Support_Manager" title="Support&nbsp;Manager" width="15%"/>
 	 <ec:column property="Group_Supervisor" title="Group&nbsp;Supervisor" width="15%"/>
 	 <ec:column property="Group_Members" title="Group&nbsp;Members" width="30%"/>
	 <ec:column property="No_of_Applications" title="No&nbsp;of&nbsp;Applications" width="15%"/>
	 <ec:column property="No_of_Issues" title="No&nbsp;of&nbsp;Issues"  width="10%"/>	 
  </ec:row>
</ec:table>
</td>
</c:if>

</tr>
<tr>
<html:form action="VIMSReportsLookUpDispatchAction.do">
 	<td align="center" colspan="3">
 	<button type="button" onclick="printList()" class="linkbutton_background" accesskey="r">P<u>r</u>int</button>&nbsp;&nbsp;<button type="button"  class="linkbutton_background" accesskey="x" style="width:150px" onclick="generateExcelFile()">Export to E<u>x</u>cel</button>&nbsp;&nbsp;<button type="button" onclick="goHome();" class="linkbutton_background" accesskey="b"><u>B</u>ack</button>
 	
	</td>	
</html:form>
</tr>
</logic:present>
</table>
</body>
</html>