<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>

<html>
<head>
<title>Vertex Issue  Management System - Home Page</title>
<script language="javascript">
	function ListIncidents()
	{			
		var formobj=document.forms[0];
		formobj.action="./ListofIssues.do?methodname=ListIssues";
		formobj.submit();
	}		
</script>
<script type="text/javascript">
function getParameterMap(form) {
    var p = document.forms[form].elements;
    var map = new Object();
    for(var x=0; x < p.length; x++) {
        var key = p[x].name;
        var val = p[x].value;
        
        //Check if this field name is unique.
        //If the field name is repeated more than once
        //add it to the current array.
        var curVal = map[key]; 
        if (curVal) { // more than one field so append value to array
        	curVal[curVal.length] = val;
        } else { // add field and value
        	map[key]= [val];
        }
    }
    return map;
}

function setFormAction(form, action, method) {
	if (action) {
		document.forms[form].setAttribute('action', action);
	}
	
	if (method) {
		document.forms[form].setAttribute('method', method);
	}
	
	document.forms[form].ec_eti.value='';
}

function fnGetSelectedTypeIssueList()
{
  var formObj=document.forms[0];
  formObj.action="./ListofIssues.do?methodname=BasePage";
  formObj.submit();
}

function fnListofIssuesSearch()
{
 var formobj=document.forms[0];
 formobj.IssueSearch.value="IssueSearch";
 formobj.action="./ListofIssues.do?methodname=BasePage&IssueSearch=IssueSearch";
 formobj.submit();
 }
function fnGoBack()
{
 window.location="./ListofIssues.do?methodname=BasePage&menuId=Issue&pageId=Issues";
}

</script>	
</head>
<body>
<html:form action="/ListofIssues.do?methodname=BasePage">
<input type="hidden" name="IssueSearch"/>
<br>
<fieldset>
<legend class="labelField"><b>Issue Search</b></legend>
<table align="left" border="0" width="100%">
<logic:present name="SearchUser">
<c:if test="${(SearchUser eq 'Admin') || (SearchUser eq 'Internal')}">
<tr>
	<td class="labelField" width="20%" align="left">Customer Name</td>
    <td align="left" width="80%" colspan="2" valign="top">&nbsp;<html:text property="strSearchCustomer" styleClass="textbox_default" maxlength="50" style="width:250px"/>
    &nbsp;&nbsp;<input type="Image" name="Go" src="./images/goButton.gif" border="0" style="position:absolute" onclick="fnListofIssuesSearch();"></td>
</tr>
</c:if>
</logic:present>
<tr>
	<td class="labelField" width="20%"align="left" >Application Name</td>
	<td align="left" width="80%" colspan="2">&nbsp;<html:text property="strSearchApplicationName" styleClass="textbox_default" maxlength="50" style="width:250px"/>
	<logic:notPresent name="SearchUser">
	&nbsp;&nbsp;<input type="Image" name="Go" src="./images/goButton.gif" border="0" style="position:absolute" onclick="fnListofIssuesSearch();">
	</logic:notPresent>
	</td>
	
</tr>
<logic:present name="SearchUser">
<c:if test="${(SearchUser eq 'Admin')}">
<tr>
	<td class="labelField" align="left" width="20%">Employee Name</td>
	<td align="left" width="80%">&nbsp;<html:text property="strSearchEmployee" styleClass="textbox_default" maxlength="50" style="width:250px"/></td>
</tr>
</c:if>
</logic:present>
<tr>
	<td class="labelField" align="left">Issue Status</td>
	<td align="left">&nbsp;<html:select property="strSearchStatus" styleClass="dropdownlist" style="width:145px">
	<html:option value="">Select Status</html:option>
	<html:option value="all">All</html:option>
	<html:option value="Accepted">Accepted</html:option>
	<html:option value="Assigned">Assigned</html:option>
	<html:option value="Closed">Closed</html:option>
	<html:option value="Completed">Completed</html:option>
	<html:option value="Escalated">Escalated</html:option>
	<html:option value="Open">Open</html:option>
	<html:option value="Rejected">Rejected</html:option>
	</html:select>
	</td>
</tr>
<tr >
	<td class="labelField" align="left" width="20%">Issue Severity</td>
	<td align="left" width="80%">&nbsp;<html:select property="strSearchSeverity" styleClass="dropdownlist" style="width:145px">
	<html:option value="">Select Severity</html:option>
	<html:option value="Cri">Critical</html:option>
	<html:option value="Maj">Major</html:option>
	<html:option value="Min">Minor</html:option>
	</html:select></td>
</tr>

</table>
</fieldset>

<!--<fieldset>
<legend class="labelField"><b>Issues by Type</b></legend>
<html:radio property="typeofIssue" value="all" onclick="fnGetSelectedTypeIssueList();"><font class="labelField">All</font></html:radio>
<html:radio property="typeofIssue" value="Open" onclick="fnGetSelectedTypeIssueList();"><font class="labelField">Open</font></html:radio>
<html:radio property="typeofIssue" value="Assigned" onclick="fnGetSelectedTypeIssueList();"><font class="labelField">Assigned</font></html:radio>
<html:radio property="typeofIssue" value="Accepted" onclick="fnGetSelectedTypeIssueList();"><font class="labelField">Accepted</font></html:radio>
<html:radio property="typeofIssue" value="Rejected" onclick="fnGetSelectedTypeIssueList();"><font class="labelField">Rejected</font></html:radio>
<html:radio property="typeofIssue" value="Escalated" onclick="fnGetSelectedTypeIssueList();"><font class="labelField">Escalated</font></html:radio>
<html:radio property="typeofIssue" value="Completed" onclick="fnGetSelectedTypeIssueList();"><font class="labelField">Completed</font></html:radio>
<html:radio property="typeofIssue" value="Closed" onclick="fnGetSelectedTypeIssueList();"><font class="labelField">Closed</font></html:radio>
</fieldset>

--></html:form>
<tr><td align="left" colspan="2">
<logic:present name="IssueList">
<ec:table items="IssueList" sortable="true" action="./ListofIssues.do?methodname=BasePage"
		imagePath="./images/*.gif" filterable="false" width="100%"
		rowsDisplayed="10">
		<ec:row highlightRow="true"> 
			<ec:column width="8%" property="id" title="Issue ID"/>
			<ec:column width="15%" property="applicationName" title="Application Name"/>
			<ec:column width="11%" property="customer" title="Customer"/>
			<ec:column width="15%" property="title" title="Title"/>
			<ec:column width="8%" property="status" title="Status"/>
			<ec:column width="10%" property="severity" title="Severity"/>
			<ec:column width="8%" property="assignedto" title="Assigned To"/>
			<ec:column width="12%" property="postedby" title="Posted By"/>
			<ec:column width="6%" property="posteddate" title="Posted Date"/>
		    <ec:column width="7%" property="closeddate" title="Closed Date"/>		    
		</ec:row>
</ec:table>
</logic:present>
</td></tr>
<logic:notPresent name="IssueList">
<tr>
	<td colspan="3">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td class="labelField" align="left" width="21%">&nbsp;</td>
		<td align="left" width="79%" class="labelField">&nbsp;<c:out value="${IsssueSearchMessage}"/></td>
		<td>&nbsp;</td>
		</tr>
		<tr>
		<td class="labelField" align="left" width="21%">&nbsp;</td>
		<td align="left" width="79%">&nbsp;<button type="button"  name="back" onClick="fnGoBack();" accesskey="b" class="linkbutton_background"><u>B</u>ack</button>
		</td>
		<td>&nbsp;</td>
		</tr>
		</table>
	</td>
</tr>
</logic:notPresent>
</body>
</html>