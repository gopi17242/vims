<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
<%@ taglib uri="/WEB-INF/tlds/datetime.tld" prefix="date" %>
<%@page import ="java.util.*"%>
<%@page import="java.text.*" %>

<%
	String strIncidentType=(String)request.getAttribute("inctType");		
	String strApplicationId=(String)request.getAttribute("applicationId");	
%>
<html>
<head>
<title>Vertex Issue  Management System - Home Page</title>
<script language="javascript">
	function ListIncidents()
	{			
		var formobj=document.forms[0];
		formobj.action="./homeSLA.do?getSLA=displayHomeOpenIssueDetails";
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
function generateReport(reportType) 
	 {	 	
	 	 document.getElementById('exportType').value=reportType;
		 var formObj=document.forms[0];
		 //formObj.action="./openIssuesExport.do?exportType=generateHomeOpenissues&exportTo="+reportType+"&application="+applId;
		 formObj.action="./openIssuesExport.do?exportType=generateHomeOpenissues&exportTo="+reportType;
		 formObj.submit();
	 }
</script>
</head>
<body>

<%
ArrayList RecordsList=new ArrayList();
ArrayList alist =(ArrayList)(session.getAttribute("OpenIssuesInApplication"));
int recordsShown=alist.size();
System.out.println("array list size is "+recordsShown);%>

<table border="0" width="100%" cellpadding="0" cellspacing="0">
<%if(alist.size()!=0){ %>
<tr>
<td > 

<ec:table border="0" items="OpenIssuesInApplication" sortable="true" action="./homeSLA.do?getSLA=displayHomeOpenIssueDetails"
		imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10" var="serRes">

		<ec:row highlightRow="true"> 
		<c:set var="singleItem" value="${serRes}" scope="session"></c:set>
			<ec:column property="Incident_ID" title="Issue ID" escapeAutoFormat="true"/>
			<ec:column property="Application_Name" title="Application Name"/>
			<ec:column property="Title" title="Title"/>
			<ec:column property="Status" title="Status"/>
			<ec:column property="Created_Date" title="Created Date"/>
		    <ec:column property="Severity" title="Severity"/>
		    <ec:column property="Due_Date" title="Target Date"/>
		    <ec:column property="Closed_Date" title="Closed Date"/>		    
		</ec:row>
</ec:table>
</td>
</tr>

<tr>
<html:form action="/openIssuesExport.do?exportType=generateHomeOpenissues">
<input type="hidden" name="export" value="" id="exportType">
<input type="hidden" name="application" value="" id="applicationId"> 
<td height="10px"></td>
</html:form>
</tr>
<tr>
<td align="center" >
<button type="button" class="linkbutton_Reports" accesskey="P" onclick="generateReport('PDF')">Export To <u>P</u>DF</button>&nbsp;&nbsp;&nbsp;
<button type="button"  class="linkbutton_Reports" accesskey="E" onclick="generateReport('EXCEL')">Export To <u>E</u>xcel</button>
</td>
</tr>
<tr>
</tr>
<%}else{ %>
<tr>
<td colspan="2" class="heading_blue" >No Records Found</td>
</tr>
<%} %>
</table>

</body>
</html>