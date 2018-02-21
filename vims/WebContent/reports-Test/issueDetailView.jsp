<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec"%>
<%@ taglib uri="/WEB-INF/tlds/datetime.tld" prefix="date" %>
<html>
<head>
<script>
function generateExcelFile()
{
	var formObj=document.forms[0];
	formObj.action='./generateIssueExcelFile.do?param=generateFile&type=DetailView';
	formObj.submit();   
}
function printList() {
  
  if (navigator.appName == "Microsoft Internet Explorer")
	{
	 	window.open('./reports-Test/printReport.jsp?type=DetailView', "VIMS", 'toolbar=no, status=no, left=360, top=250, scrollbars=yes, resize=yes');
	}
	else if (navigator.appName == "Netscape") 
	{
       window.open("./reports-Test/printReport.jsp?type=DetailView", "VIMS",  'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=no,resizable=0,width=350,height=300,top=250,left=360');		
	}
}
function goHome() {
  window.location="./issueReportForm.do?param=getIssueReportHomePage&pageId=ApplicationPool&menuId=Report";
}
</script>
</head>
<body>
<br>

<c:if test="${(issueDetails ne null)&&(issueCycle ne null)}">
<table width="100%">
<tr valign="top">
<td align="center" class="heading_blue" colspan="3"><font size="2">Issue Detail View</font></td>
</tr>
<tr>
	<td class="labelField" width="20%">Issue ID</td>
	<td class="labelField" width="80%"><c:out value="${issueDetails.Issue_Id}" /></td>
</tr>

<tr>
	<td class="labelField" width="20%">Application Name</td>
	<td class="labelField" width="80%"><c:out value="${issueDetails.Application_Name}" /></td>
</tr>

<tr>
	<td class="labelField" width="20%">Title</td>
	<td class="labelField" width="80%"><c:out value="${issueDetails.Title}" /></td>
</tr>

<tr>
	<td class="labelField" width="20%">Current Status</td>
	<td class="labelField" width="80%"><c:out value="${issueDetails.Status}" /></td>
</tr>

<tr>
	<td class="labelField" width="20%">Description </td>
	<td class="labelField" width="80%"><c:out value="${issueDetails.Description}" /></td>
</tr>

<tr>
	<td class="labelField" width="20%">Severity</td>
	<td class="labelField" width="80%"><c:out value="${issueDetails.Issue_Type}" /></td>
</tr>

<tr>
	<td class="labelField" width="20%">Priority</td>
	<td class="labelField" width="80%"><c:out value="${issueDetails.Priority}" /></td>
</tr>
<tr>
	<td class="labelField" width="20%">Posted By</td>
	<td class="labelField" width="80%"><c:out value="${issueDetails.Posted_By}" /></td>
</tr>

<tr>
	<td class="labelField" width="20%">Created Date</td>
	<td class="labelField" width="80%"><c:out value="${issueDetails.Posted_Date}" /></td>
</tr>
<tr>
	<td class="labelField" width="20%">Due Date</td>
	<td class="labelField" width="80%"><c:out value="${issueDetails.Due_Date}" /></td>
</tr>
<tr>
	<td class="labelField" width="20%">Completed Date</td>
	<td class="labelField" width="80%"><c:out value="${issueDetails.Issue_Completion_Date}" /></td>
</tr>

<tr>
	<td class="labelField" width="20%">Closed Date</td>
	<td class="labelField" width="80%"><c:out value="${issueDetails.Closed_Date}" /></td>
</tr>

<tr>
<td colspan="2" align="center" class="heading_blue">&nbsp;</td>
</tr>
<tr>
<td colspan="2">
<ec:table items="issueCycle" showStatusBar="false" imagePath="./images/*.gif" filterable="false" width="100%" sortable="false"
 showPagination="false">
<ec:row highlightRow="true"> 
    <ec:column property="Assigned_By" title="Assigned&nbsp;&nbsp;By" width="12%"/>
    <ec:column property="Assigned_To" title="Assigned&nbsp;&nbsp;To" width="12%"/>
    <ec:column property="status_From" title="Status&nbsp;&nbsp;From" width="12%"/>
    <ec:column property="Status_To" title="Status&nbsp;&nbsp;To" width="10%"/>
    <ec:column property="Status_Change_on_Date" title="Status&nbsp;Change&nbsp;on&nbsp;Date" width="10%"/>
    <ec:column property="Comments" title="Comments/Resolution" width="54%"/>
</ec:row>
</ec:table>
</td>
</tr>
<tr>
<td align="center" colspan="2">	
<html:form action="generateIssueExcelFile.do">
	<button type="button" onclick="printList()" class="linkbutton_background" accesskey="p" tabindex="1"><u>P</u>rint</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button"  class="linkbutton_background" accesskey="x" style="width:150px" onclick="generateExcelFile()" tabindex="2">Export to E<u>x</u>cel</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" onclick="window.history.back();" class="linkbutton_background" accesskey="b" tabindex="3"><u>B</u>ack</button>
</html:form>
	</td>	
</tr>
</table>
</tr>
</table>
</c:if>
</body>
</html>