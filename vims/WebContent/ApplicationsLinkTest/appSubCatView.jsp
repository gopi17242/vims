<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/tlds/c-1_0.tld" prefix="c" %>
<html:html>

<body>
<br><br>
<SPAN class="heading"><CENTER><h2>Application Sub Category Details </h2></CENTER></SPAN>
<br><html:errors/>
<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="1" WIDTH="100%" align="center">
	<Tr bgcolor="#326EB5">
		<td class="tableheader" align="center">Application ID</td>
		<td class="tableheader" align="center">Application Name</td>
		<!-- td class="tableheader" align="center">App Sub Category ID</td -->
		<td class="tableheader" align="center">App Sub Category Name</td>
	</Tr>
<c:forEach items="${appSubCatList}"  var="AppSubCategories" step="1">

	<Tr bgcolor="#E4DFE3">
		<td class="normalcontenttext"><c:out value="${AppSubCategories.appId}"/></TD>
		<td class="normalcontenttext"><c:out value="${AppSubCategories.appName}"/></TD>
		<td class="normalcontenttext"><c:out value="${AppSubCategories.appSubCat}"/></TD>
	</tr>
</c:forEach>

	<tr><td >&nbsp;</td></tr>
	<tr><td colspan="4" align="center">
		<html:link page="/backToApplicationModules.do">[ Back ]</html:link>
	</td></tr>
</table>
</body>
</html:html>

