<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>


<html:html>
<head>
<title><center><H1>Employees Details</H1></center></title>
<script>

var requestAttribute;
var count=0;
function incrementCount()
{ count++;

}

function checkIfMultiple()
{

if(count!=1)
alert("Select only one check box to be modified");
if(count==1)
{
document.form.submit="";
}
b
}

function fnDelete(eid)
{
	//if(confirm("Are you sure you want to delete the record?"))
	//{
		//window.location="./employeeLookUp.do?function=DeleteEmployee&eid="+eid;
		//alert("Record deleted successfully");
	//}
	alert("Currently this facility is not available");
	
}
function fnGetSelectedTypeEmployeeList()
{
  var formObj=document.forms[0];
  formObj.action="./employeeLookupDispatch.do?function=EmployeeFirstPage";
  formObj.submit();
}
function EmpSearch()
{
         //alert("---In Script--");
         var formobj=document.forms[0];
		 //alert(formobj); 
 		 formobj.action="./employeeLookupDispatch.do?function=EmployeeSearch";
		 formobj.submit();
} 
function fnGoBack()
{
  window.location="./employeeLookupDispatch.do?function=EmployeeFirstPage&menuId=Employee&pageId=Employees";
  
}
function enterSubmit(evt)
{   
	var e = event || evt; // for trans-browser compatibility    
	var charCode = e.which || e.keyCode;    
	if (charCode ==13) 
	{ 		
         var formobj=document.forms[0];		
 		 formobj.action="./employeeLookupDispatch.do?function=EmployeeSearch";
		 formobj.submit();	
		return false;
	}
	else
	{
		return true;	
	}		
}	
</script>
</head>
<body><br>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
<logic:present name="AddingSuccess">
   <tr> <td align="center" width="100%" colspan="3"><font color=green size="2"><h4><c:out value="${AddingSuccess}"/></h4></font></td> </tr>
</logic:present>

<logic:present name="ModificationSuccess">
   <tr> <td align="center" width="100%" colspan="3"><font color=green size="2"><h4><c:out value="${ModificationSuccess}"/></h4></font></td> </tr>
</logic:present>

<logic:present name="DeleteFlag">
    <tr><td align="center" width="100%" colspan="3"><font color=green size="2"><h4><c:out value="${DeleteFlag}"/></h4></font></td> </tr>
</logic:present>


<html:form action="/employeeLookupDispatch.do?function=EmployeeFirstPage" focus="empSearch">
<!--<fieldset>
<legend class="labelField"><b>Status</b></legend>
<html:radio property="typeofIssue" value="Active" onclick="fnGetSelectedTypeEmployeeList();"><font class="labelField">Active</font></html:radio>
<html:radio property="typeofIssue" value="Inactive" onclick="fnGetSelectedTypeEmployeeList();"><font class="labelField">Inactive</font></html:radio>
</fieldset>
	-->
	<tr>
	<td class="labelField" align="left" width="21%">Employee Name</td>
	<td align="left" width="33%"><html:text property="empSearch" styleId="searchEmp" onkeydown="enterSubmit(searchEmp);" style="width:250px" styleClass="textbox_default"/></td>
	<td valign="top"><input type="Image" name="Go" src="./images/goButton.gif" onclick="EmpSearch();"></td>
	</tr>

</html:form>


<tr>
<td colspan="3">
<logic:present name="Details">
<ec:table items="Details" action="./EmpDetails.do?isSubmited=false"
		imagePath="./images/*.gif" filterable="false" width="100%"
		rowsDisplayed="10">
		<ec:row highlightRow="true"> 
			<ec:column property="strEmpid" title="Employee ID" width="15%"/>
			<ec:column property="strName" title="Employee Name" width="25%"/>
			<ec:column property="strEmail" title="E-mail" width="20%"/>
			<ec:column property="strPhone" title="Phone Number" width="18%"/>
			<ec:column property="strStatus" title="Status" width="10%"/>
		    <ec:column property="ModifyDelete" title="Actions" width="12%"/>
		</ec:row>
</ec:table>
</logic:present>
</td>
</tr>
<tr>
<td>&nbsp;</td>
<td colspan="2">
 <logic:notPresent name="Details">
     <table border="0" width="100%">
     	<tr>
     		<td align="left" width="100%" class="labelField"><c:out value="${SearchResult}"/></td>
       	</tr>
     	<tr>
     		<td align="left" width="100%"><button type="button"  name="back" onClick="fnGoBack();" accesskey="b" class="linkbutton_background"><u>B</u>ack</button></td>
       	</tr>
     </table>
 </logic:notPresent>
 </td>
 </tr>
</table>   
</body>
</html:html>