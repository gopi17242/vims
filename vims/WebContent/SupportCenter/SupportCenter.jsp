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

function fnDelete(supportCenterID)
{
	//if(confirm("Are you sure you want to delete the record?"))
	//{
		alert("Currently this facility is not available");
		//window.location="./EditSupportCenter.do?methodname=DeleteSupportCenter&supportCenterID="+supportCenterID;
		//alert("Record deleted successfully");
	//}
	
}
function fnGetSelectedType()
{
  var formObj=document.forms[0];
  formObj.action="./EditSupportCenter.do?methodname=SupportCenterPage";
  formObj.submit();
}
function fnSupportCenterSearch()
{
  var formObj=document.forms[0];
  formObj.action="./EditSupportCenter.do?methodname=SupportCenterSearch";
  formObj.submit();
}

function enterSubmit(evt)
{   
	var e = event || evt; // for trans-browser compatibility    
	var charCode = e.which || e.keyCode;    
	if (charCode ==13) 
	{ 
		//alert('you clicked enter button') 
		var formObj=document.forms[0];
  		formObj.action="./EditSupportCenter.do?methodname=SupportCenterSearch";
  		formObj.submit();	
		return false;
	}
	else
	{
		return true;	
	}		
}
function fnGoBack()
{
 window.location="./EditSupportCenter.do?methodname=SupportCenterPage&menuId=Support&pageId=SupportCenters";
}

</script>
</head>
<body>
<html:form action="/EditSupportCenter.do?methodname=SupportCenterPage" focus="searchSupportCenter">
<center>
	  <logic:present name="Success"><h4><font color="green"><c:out value="${Success}"/></font></h4>
	  </logic:present>
	  <logic:present name="Failure"><h4><font color="red"><c:out value="${Failure}"/></font></h4>
	  </logic:present>
	  <logic:present name="DeleteError"><h4><font color="red"><c:out value="${DeleteError}"/></font></h4>
	  </logic:present>
</center>
<!--<fieldset>
<legend class="labelField"><b>Status</b></legend>
<html:radio property="statusType" value="0" onclick="fnGetSelectedType();"><font class="labelField">Active</font></html:radio>
<html:radio property="statusType" value="1" onclick="fnGetSelectedType();"><font class="labelField">Inactive</font></html:radio>
</fieldset>
-->
<br>
<table width="100%" border="0">
<tr>
<td class="labelField" align="left" width="20%">Support Center Name</td>
<td align="left" width="34%">&nbsp;<html:text property="searchSupportCenter" styleId="searchSC" styleClass="textbox_default" onkeydown="enterSubmit(searchSC);" style="width:250px" ></html:text></td>
<td valign="top" width="46%"><input type="Image" name="Go" id="gobtn" src="./images/goButton.gif" border="0" onclick="fnSupportCenterSearch();"></td>
</tr>
</html:form>
<logic:present name="SupportCenterList">
<tr>
<td colspan="3">
<ec:table items="SupportCenterList" action="./EditSupportCenter.do?methodname=SupportCenterPage&menuId=Support"
		imagePath="./images/*.gif" filterable="false" width="100%"
		rowsDisplayed="10">
		<ec:row highlightRow="true">			
			<ec:column property="supportcentername" title="Support Center Name" width="30%"/>
			<ec:column property="supportmanager" title="Support Manager" width="30%"/>
			<ec:column property="locationname" title="Location" width="12.5%"/>
			<ec:column property="status" title="Status" width="12.5%"/>
		    <ec:column property="actions" title="Actions" width="15%"/>
		</ec:row>
	</ec:table>
</td>
</tr>
</logic:present>
<logic:notPresent name="SupportCenterList">
<tr>
<td colspan="3" width="100%">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="labelField" align="left" width="20%">&nbsp;</td>
<td align="left" width="34%" class="labelField"><c:out value="${SupportCenterSearchMessage}"/></td>
<td width="46%">&nbsp;</td>
</tr>
<tr>
<td class="labelField" align="left" width="20%">&nbsp;</td>
<td align="left" width="34%"><button type="button"  name="back" onClick="fnGoBack();" accesskey="b" class="linkbutton_background"><u>B</u>ack</button>
</td>
<td width="46%">&nbsp;</td>
</tr>
</table>
</td>
</tr>
</logic:notPresent>
</table>
</body>
</html>