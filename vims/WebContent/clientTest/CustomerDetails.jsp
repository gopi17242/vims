<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>

<html>
<head>
<script type="text/javascript" src="./script-test/autoCompleteCustomer.js"></script>
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

function fnDelete(custID)
{
	//if(confirm("Are you sure you want to delete the record?"))
	//{
		//window.location="./dispatchClient.do?client=Delete&custID="+custID;
		//alert("Record deleted successfully");
	//}
	alert("Currently this facility is not available");
	
}
function fnGetSelectedTypeCustomerList()
{
  var formObj=document.forms[0];
  formObj.action="./dispatchClient.do?client=Base";
  formObj.submit();
}

function CustSearch()
{
         //alert("---In Script--");
         var formobj=document.forms[0];
		 //alert(formobj); 
 		 formobj.action="./dispatchClient.do?client=CustomerSearch";
		 formobj.submit();
} 
function fnGoBack()
{
  window.location="./dispatchClient.do?client=Base&menuId=Customer&pageId=Customers";
}
function enterSubmit(evt)
{   
	var e = event || evt; // for trans-browser compatibility    
	var charCode = e.which || e.keyCode;    
	if (charCode ==13) 
	{ 	
         
         var formobj=document.forms[0];		
 		 formobj.action="./dispatchClient.do?client=CustomerSearch";
		 formobj.submit();
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
<logic:present name="MSG">
    <tr> <td colspan="3" align="center"><font color="green" size="2"><h4><c:out value="${MSG}"/></h4></font></td></tr>
</logic:present>
<tr>

<html:form action="/dispatchClient.do?client=Base" focus="customerSearch">
<!--<fieldset>
<legend class="labelField"><b>Status</b></legend>
<html:radio property="typeofIssue" value="Active" onclick="fnGetSelectedTypeCustomerList();"><font class="labelField">Active</font></html:radio>
<html:radio property="typeofIssue" value="Inactive" onclick="fnGetSelectedTypeCustomerList();"><font class="labelField">Inactive</font></html:radio>
</fieldset>
	-->

	<tr>
	<td class="labelField" align="left" width="20%">Customer Name</td>
	<td align="left" width="34%">&nbsp;&nbsp;<html:text property="customerSearch" styleId="searchCust" onkeydown="enterSubmit(searchCust);" style="width:250px" styleClass="textbox_default"/></td>
	<td width="46%" valign="top"><input type="Image" name="Go" src="./images/goButton.gif" onclick="CustSearch();"></td>
	</tr>
	
</html:form>

<tr>
<td colspan="3">		
    <logic:present name="List">	
	 <ec:table items="List" action="./dispatchClient.do?client=Base&menuId=Customer"
		  imagePath="./images/*.gif" filterable="false" width="100%"
		  rowsDisplayed="10">
		   <ec:row highlightRow="true"> 
			
			<ec:column property="customerName" title="Customer Name" width="17%"/>
			<ec:column property="applicationName" title="Application Name" width="25%"/>
			<ec:column property="emailID" title="E-mail" width="20%"/>
	        <ec:column property="phoneNumber" title="Phone Number" width="18%"/>
			<ec:column property="status" title="Status" width="10%"/>
		    <ec:column property="ModifyDelete" title="Actions" width="10%"/>
		</ec:row>
	</ec:table>
	</logic:present>
</td>
</tr>
<tr>
<td>&nbsp;</td>
<td colspan="2">
	 <logic:notPresent name="List">
     <table border="0" width="100%">
     	<tr><td align="left" width="100%" class="labelField"><c:out value="${SearchResult}"/></td>
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
</html>