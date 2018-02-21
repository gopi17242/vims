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
</script>	
</head>
<body>
<ec:table items="IssueList" action="./ListofIssues.do?methodname=BasePage"
		imagePath="./images/*.gif" filterable="false" width="100%"
		rowsDisplayed="10">
		<ec:row highlightRow="true"> 
			<ec:column property="INCIDENT_ID" title="Issue ID"/>
			<ec:column property="APPLICATION_NAME" title="Application Name"/>
			<ec:column property="INCIDENT_TITLE" title="Title"/>
			<ec:column property="INCT_STATUS" title="Status"/>
			<ec:column property="POSTED_DATE" title="Created Date"/>
		    <ec:column property="SEVERITY" title="Severity"/>
		    <ec:column property="DUE_DATE" title="Target Date"/>
		    <ec:column property="CLOSED_DATE" title="Closed Date"/>	
		</ec:row>
</ec:table>
</body>
</html>


















