<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
<%@page import="java.util.*" %>

<html>
<head>
<title>Escalated Issues</title>	
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
<br>


<%if((((ArrayList)session.getAttribute("EscalatedIssuesList")).size())==0){ %>
<font class="heading_blue" size="4">No Issues has been Escalated</font>
<%} else{%>
<ec:table items="EscalatedIssuesList" sortable="true" action="./escalation.do?method=escalatedIssuesList"
		imagePath="./images/*.gif" filterable="false" width="100%"
		rowsDisplayed="10">
		<ec:row highlightRow="true"> 
			<ec:column property="id" title="Issue ID" width="10%"/>
			<ec:column property="title" title="Title" width="15%"/>	
			<ec:column property="applicationName" title="Application Name" width="13%"/>			
			<ec:column property="type" title="Severity" width="12%"/>
			<ec:column property="assignedTo" title="Assigned To" width="13%"/>
			<ec:column property="postedBy" title="Posted By" width="12%"/>		
			<ec:column property="fromDate" title="Posted Date" width="13%"/>		   
		    <ec:column property="dueDate" title="Due Date" width="12%"/>		   	    
		</ec:row>
</ec:table>
<%} %>
</body>
</html>


















