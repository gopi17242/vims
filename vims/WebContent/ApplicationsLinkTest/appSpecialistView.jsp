<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/tlds/c-1_0.tld" prefix="c" %>
<SPAN class="heading"><CENTER><h2>Application Specialist Details <h2></CENTER></SPAN>
<br>
<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="1" WIDTH="100%" align="center">
	<Tr bgcolor="#326EB5">
		<td class="tableheader" align="center">Application ID</td>
		<td class="tableheader" align="center">Application Name</td>
		<td class="tableheader" align="center">Employee ID</td>
		<td class="tableheader" align="center">Employee Name</td>
		<td class="tableheader" align="center">Commnets</td>
	</Tr>

<c:forEach items="${appSpecList}"  var="ApplicationSpecialists" step="1">
	<Tr bgcolor="#E4DFE3">
		<td class="normalcontenttext"><c:out value="${ApplicationSpecialists.appId}"/></TD>
		<td class="normalcontenttext"><c:out value="${ApplicationSpecialists.appName}"/></TD>
		<td class="normalcontenttext"><c:out value="${ApplicationSpecialists.empId}"/></TD>
		<td class="normalcontenttext"><c:out value="${ApplicationSpecialists.empName}"/></TD>
		<td class="normalcontenttext"><c:out value="${ApplicationSpecialists.comments}"/></TD>
	</tr>
</c:forEach>
	<tr><td >&nbsp;</td></tr>
	<tr><td colspan="5" align="center">
		<html:link page="/backToApplicationSpecialist.do">[ Back ]</html:link>
	</td></tr>
</table>

