<!--

FileName	    : EmployeeMyToDoList.jsp


 Description	:  This jsp is to display list of Issues related to a particular employee. 
 Developed by  : Vertex Computer Systems.
 copyright (c) 2008 Vertex.
 All rights reserved.

 Change History:

 Revision No.	:		Date		  @author		Description
 1.0				Friday 26,2008	  Sudhir.D	   Initial Version.
 -->
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>

<html>
<head>
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
<ec:table items="ListOfEmployeIssue" action="./EmpMyToDoListLookUp.do"
		imagePath="./images/*.gif" filterable="false" width="100%"
		rowsDisplayed="10">
		<ec:row highlightRow="true"> 
			<ec:column property="strIncidentId" title="Issue ID" width="10%"/>
			<ec:column property="strApplicationName" title="Application Name" width="15%"/>
			<ec:column property="strTitle" title="Title" width="15%"/>
			<ec:column property="strAssignedBy" title="Assigned By" width="13.5%"/>
			<ec:column property="strPostedDate" title="Created Date" width="14.5%"/>
		    <ec:column property="strDueDate" title="Target Date" width="13.5%"/>
		    <ec:column property="strSeverity" title="Severity" width="10.5%"/>
		    <ec:column property="strStatus" title="Status" width="8%"/>
		</ec:row>
	</ec:table>
</body>
</html>
