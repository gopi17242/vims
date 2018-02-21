<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
<html>
<head>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/script-test/formUtil.js' />">
</script>
<script>
var keywordSelected;
function getFieldTable(objSel) {
 
    keywordSelected=objSel.options[objSel.selectedIndex].value;
    if(keywordSelected=='Work Location') {
      window.location="./testAction.do?param=getLocations";
    }      
    else if(keywordSelected=='Employee Position') {
       window.location="./PositionAction.do?param=getPositionList";
    }
    else if(keywordSelected=='Country')  {
       window.location="./CountryAction.do?param=getCountryAction";
    }
     else if(keywordSelected=='State')  {
       window.location="./StateAction.do";
     }
     else if(keywordSelected=='Role based access') {
       window.location="./RoleAction.do?param=roleHome";
     }
} 
function submitForm() {

    var formObject=document.forms[0];
    formObject.action="./insertCountryDetails.do?param=insertCountryDetails";
    //alert(formObject.action);
    if(document.getElementById("keywordValue").value!='') {
    formObject.submit();  
   } 
}

function goHome() {
  window.location="./customizeFields.do?param=getFieldConfigurationSettings&pageId=FieldPage";
}

function fnDelete(supportCenterID)
{
  alert("Currently this facility is not available");
}
</script>
</head>
<body>
<br>
<logic:present name="fieldList">
<html:form action="/insertCountryDetails.do?param=insertCountryDetails" focus="keywordValue">
<html:hidden property="keywordType" value="Country" />
<center><h4><font color="green"><c:out value="${successMsg}" /></font></h4></center>
<table width="100%" border="0">
<!-- 
 <tr>
<td ><font class="labelField">Select an option</font></td>
<td>  
  &nbsp;&nbsp;<html:select property="keywordType" styleClass="file_Upload" onchange="getFieldTable(this)" tabindex="1">
<option value="">---Select option---</option>
<c:forEach items="${fieldList}" var="listItem">
<option value='<c:out value="${listItem}"/>'><c:out value="${listItem}"/></option>
</c:forEach>
</html:select>
</td>
</tr> --> 
<tr align="left">
<td width="20%"><font class="labelField">Country Name</font></td>
<td width="80%"><font color="red" size="2" class="labelMandatory">*</font><html:text property="keywordValue" styleClass="textbox_default" tabindex="2" />&nbsp;&nbsp;<i><font size="2" color="red"><html:errors property="errors.keywordType"/></font></i></td>
</tr>
<tr align="left">
<td ><font class="labelField">Status</font></td>
<td>  
  &nbsp;&nbsp;<html:select property="positionStatus" styleClass="file_Upload" tabindex="3">
	  <html:option value="0">Active</html:option>
	  <html:option value="1">Inactive</html:option>
  </html:select>&nbsp;&nbsp;<i><font size="2" color="red"><html:errors property="errors.status"/></font></i>
</td>
</tr>
<!-- <tr>
<td ><font class="labelField">Status</font></td>
<td>  
  &nbsp;&nbsp;<html:select property="positionStatus" styleClass="file_Upload" tabindex="3">
	  <html:option value="">Select Status</html:option>
	  <html:option value="0">Active</html:option>
	  <html:option value="1">Inactive</html:option>
  </html:select>&nbsp;&nbsp;<i><font size="2" color="red"><html:errors property="errors.status"/></font></i>
</td>
</tr>   
<tr>
	<td class="labelField">Option Value</td>
	<td>&nbsp;&nbsp;<input type="text" id="options" class="textbox_default"></td>
</tr>
<tr><td colspan="2" align="center" class="labelField">*Valid Values</td></tr>
<tr>
    <td colspan="2" align="center">
        <input type="button" value="Add" onClick="getValue()"> 
        <input type="button" value="Remove" onClick="removeValue()">
     </td>    
</tr>
<tr><td colspan="2" align="center">&nbsp;
<select multiple size="5" style="width:150px;"  id="optionValues">
</select>
</td></tr> -->
<tr height="10px"><td width="100%" colspan="2"></td></tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;<button type="button" class="linkbutton_background" tabindex="4" value="Submit" accesskey="s" onclick="submitForm()"><u>S</u>ubmit</button>&nbsp;&nbsp;&nbsp;&nbsp;<button value="Cancel" accesskey="c" type="button" tabindex="5" class="linkbutton_background" onclick="goHome();"><u>C</u>ancel</button></td>
</tr>
</table>
</html:form>
</logic:present>
<!-- <script>
for(var i=0;i<document.getElementById("keywordType").length;i++) {
   if(document.getElementById("keywordType").options[i].value=='Country')
   document.getElementById("keywordType").selectedIndex=i;
 }  
</script>  -->
<logic:present name="countryList">
<ec:table items="countryList" sortable="true" action="./CountryAction.do?param=getCountryAction"  imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10">
 	<ec:row highlightRow="true">
 	    <ec:column property="countryName" title="Country" width="40%"/>
 	    <ec:column property="status" title="Status" width="30%"/>
	    <ec:column property="actionLink" title="Actions" width="30%"/>	 
 	</ec:row>
</ec:table>
</logic:present>
</body>
</html>