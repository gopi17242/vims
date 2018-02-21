<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<html>
<head>
<script language="javascript">
var keywordSelected;

function fnCountry(){
	var formobj=document.forms[0];
	formobj.action="./testAction.do";
	formobj.param.value="getLocations";
	var selcountry=document.forms[1].countryName.value;
	document.getElementById("selected_country").value=selcountry;
	formobj.submit(); 
}

function getFieldTable(objSel) {
 
    keywordSelected=objSel.options[objSel.selectedIndex].value;
    
    if(keywordSelected=='Work Location') {
       window.location="./testAction.do?param=getLocations";
    }      
    else if(keywordSelected=='Employee Position') {
       window.location="./PositionAction.do?param=getPositionList";
    }
    else if(keywordSelected=='Country&State')  {
       window.location="./CountryAction.do";
    }
     else if(keywordSelected=='State')  {
       window.location="./StateAction.do";
     }
     else if(keywordSelected=='Role based access') {
       window.location="./RoleAction.do?param=roleHome";
     }
}
function submitForm() {

   // alert("me"); 
    var formObject=document.forms[1];
    formObject.action="./insertLocationDetails.do?param=addLocationDetails";
    formObject.submit();  
    
}

function goHome() {
  window.location="./customizeFields.do?param=getFieldConfigurationSettings&pageId=FieldPage";
}
</script>
</head>
<body><br>
<logic:present name="fieldList">
<form>
<center><h4><font color="green"><c:out value="${successMsg}"/></font></h4></center>
<input type="hidden" name="param">
<input type="hidden" name="selected_country" id="selected_country" />
</form>
<html:form action="/insertLocationDetails.do?param=addLocationDetails" focus="keywordValue">
<html:hidden property="keywordType" value="Work Location" />
<table width="100%" border="0">
<!-- <tr>
<td width="20%"><font class="labelField">Select Keyword</font></td>
<td width="80%">&nbsp;&nbsp;<html:select property="keywordType" styleClass="file_Upload" onchange="getFieldTable(this)" tabindex="1">
<option value="">---Select option---</option>
<c:forEach items="${fieldList}" var="listItem">
<option value='<c:out value="${listItem}"/>'><c:out value="${listItem}"/></option>
</c:forEach>
</html:select>
</td>
</tr>  -->
<tr>
<td width="20%"><font class="labelField">Work Location</font></td>
<td width="80%"><font color="red" size="2" class="labelMandatory">*</font><html:text property="keywordValue" styleClass="textbox_default" tabindex="2" />&nbsp;&nbsp;<i><font size="2" color="red"><html:errors property="errors.keywordType"/></font></i></td>
</tr>
<tr>
<td width="20%"><font class="labelField">Status</font></td>
<td width="80%">&nbsp;&nbsp;<html:select property="positionStatus" styleClass="file_Upload" tabindex="4">
	  					<html:option value="0">Active</html:option>
	  					<html:option value="1">Inactive</html:option>
  					  </html:select>&nbsp;&nbsp;<i><font size="2" color="red"><html:errors property="errors.positionStatus"/></font></i>
</td>
</tr>
<tr height="10px"><td colspan="3"></td></tr>
<tr>
<td width="20%">&nbsp;</td>
<td width="80%" colspan="2">&nbsp;&nbsp;<button type="button" class="linkbutton_background" tabindex="9" value="Submit" accesskey="s" onclick="submitForm()"><u>S</u>ubmit</button>&nbsp;&nbsp;&nbsp;&nbsp;<button value="Cancel" accesskey="c" type="button" tabindex="10" class="linkbutton_background" onclick="goHome();"><u>C</u>ancel</button></td>
</tr>
</table>
</html:form>
</logic:present>
<logic:present name="LocationList">
<ec:table items="LocationList" sortable="true" action="./testAction.do?param=getLocations"  imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10">
	<ec:row highlightRow="true">
		<ec:column property="locationName" title="Location" width="34%"/>
		<ec:column property="locationStatus" title="Status" width="33%" />
		<ec:column property="actionLink" title="Actions" width="33%"/>
	</ec:row>
</ec:table>
</logic:present>
<!-- <script>
for(var i=0;i<document.getElementById("keywordType").length;i++) {
   if(document.getElementById("keywordType").options[i].value=='Work Location')
   document.getElementById("keywordType").selectedIndex=i;
 }  
</script> -->
</body>
</html>