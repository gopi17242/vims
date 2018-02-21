<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<html>
<head>
<title>Vertex Issue  Management System</title>
<LINK href="./Layouts/StyleSheet.css" rel="STYLESHEET" type="text/css">
<script language="javascript">
	function CustListIssues()
	{
			var formobj=document.forms[0];
			formobj.action="./switch.do?prefix=/customer&page=/VIMSCustomerLookupDispatchAction.do?submit=custListOfIssues";
			formobj.submit();
			
	}	
</script>	
</head>
<body >

<SPAN class="heading"><CENTER><h2>My Issues</h2></CENTER></SPAN>

<html:form action="VIMSCustomerLookupDispatchAction.do">

<input type="hidden" name="SelectedIssue" id="SelectedIssueType"/>

<TABLE border="0" width="100%" cellspacing="2" cellpadding="2" >
<tr>
</tr>
<td  colspan="10" class="normalcontenttext"></td>
<tr>
<br><br><br>	
	
	<td colspan="10" class="normalcontenttext">
		<table>
			<tr>
				<td>
					<fieldset>
							<legend class="normalcontenttext"><b>Issues</b></legend>
							<html:radio property="issueType" value="all" onclick="CustListIssues();"><bean:message key="VIMSApplication.all"/></html:radio>
							<html:radio property="issueType" value="open" onclick="CustListIssues();"><bean:message key="VIMSApplication.open"/></html:radio>
							<html:radio property="issueType" value="assigned" onclick="CustListIssues();"><bean:message key="VIMSApplication.assigned"/></html:radio>
							<html:radio property="issueType" value="accepted" onclick="CustListIssues();"><bean:message key="VIMSApplication.accepted"/></html:radio>
							<html:radio property="issueType" value="closed" onclick="CustListIssues();"><bean:message key="VIMSApplication.closed"/></html:radio>
							<html:radio property="issueType" value="rejected" onclick="CustListIssues();"><bean:message key="VIMSApplication.rejected"/></html:radio>
					</fieldset>
				</td>
			</tr>
		</table>
	</td>
</tr>
<td  colspan="10" class="normalcontenttext"></td>
	


	<tr bgcolor="#4C768C">
		
		<th class="tableheader" align="center">Issue ID</th>
		<th class="tableheader" align="center" width="30%">Application Name</th>
		<th class="tableheader" align="center" >Title</th>
		<th class="tableheader" align="center" style="display:">Status</th>
		<th class="tableheader" align="center">Created&nbsp;Date&Time</th>
		<th class="tableheader" align="center">&nbsp;Severity&nbsp;</th>
		<th class="tableheader" align="center">Target&nbsp;Date&Time</th>

		<th class="tableheader" align="center" style="display:">Closed Date</th>
		<th class="tableheader" align="center" style="display:">Resolution in hr</th>
	</Tr>	

		
<c:forEach items="${custIssuesList}"  var="Issues" step="1">

<Tr bgcolor="#E4DFE3">
		
		<Tr bgcolor="#E4DFE3">
		
		<td class="normalcontenttext" align="center"><a href="./ListofIssues.do?methodname=IssueDetails&id=<c:out value="${Issues.issueId}"/>" class="infobottom">
		<c:out value="${Issues.issueId}"/></a></TD>

		<td class="normalcontenttext"><c:out value="${Issues.appName}"/></TD>
		
		<td width="36%" class="normalcontenttext"><c:out value="${Issues.title}"/></TD>

		<td class="normalcontenttext" style="display"><c:out value="${Issues.status}"/></TD>

		<td class="normalcontenttext"><c:out value="${Issues.createdDateTime}"/></TD>
		
		<td class="normalcontenttext"><c:out value="${Issues.severity}"/></TD>
		
		<td class="normalcontenttext"><c:out value="${Issues.targetDateTime}"/></TD>
		
		<td class="normalcontenttext" style="display:"><c:out value="${Issues.closedDate}"/></TD>
		
		<td class="normalcontenttext" align="center" style="display:"><c:out value="${Issues.resolutionInHour}"/></TD>
</Tr>
</c:forEach>
	<tr><td colspan=5><c:out value="${MyIssuesMessage}"/>&nbsp;</td></tr>
	<tr style="display:" id="EssIssue" ><td colspan=5 class="normalcontenttext"><img src="./images/close.gif">

</td></tr>

</TABLE>
</html:form>
</body>
</html>
