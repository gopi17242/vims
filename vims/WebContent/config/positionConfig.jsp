<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<html>
<head>
<script>
var keywordSelected;
function getFieldTable(objSel) {
 
    keywordSelected=objSel.options[objSel.selectedIndex].value;
    
    if(keywordSelected=='Location') {
      window.location="./testAction.do?param=getLocations";
    }      
    else if(keywordSelected=='Position') {
       window.location="./PositionAction.do?param=getPositionList";
    }
    else if(keywordSelected=='Country')  {
      window.location="./CountryAction.do";
    }
     else if(keywordSelected=='State')  {
      window.location="./CountryAction.do";
     }
     else if(keywordSelected=='Role') {
       window.location="./RoleAction.do?param=roleHome";
     }
}
function submitForm() {

   
    var formObject=document.forms[0];
    formObject.action="./insertSpecificKeywordValue.do?param=addSpecificKeywordOptionValue";
    formObject.submit();  
    
}

function goHome() {
  window.location="./customizeFields.do?param=getFieldConfigurationSettings&pageId=FieldPage";
}
</script>
</head>
<body><br>
<logic:present name="fieldList">
<html:form action="/insertSpecificKeywordValue.do?param=addSpecificKeywordOptionValue" focus="keywordValue">
<html:hidden property="keywordType" value="Employee Position" />
<table width="100%" border="0">
<!-- <tr>
<td width="19%"><font class="labelField">Select an option</font></td>
<td width="81%">&nbsp;&nbsp;&nbsp;<html:select property="keywordType" style="width:150px" styleClass="file_Upload" onchange="getFieldTable(this)" tabindex="1">
<option value="">---Select option---</option>
<c:forEach items="${fieldList}" var="listItem">
<option value='<c:out value="${listItem}"/>'><c:out value="${listItem}"/></option>
</c:forEach>
</html:select>
</td>
</tr>  -->
<tr>
<td width="19%"><font class="labelField">Add Position</font></td>
<td width="81%">&nbsp;&nbsp;<font color="red" size="2" class="labelMandatory">*</font><html:text property="keywordValue" style="width:150px" styleClass="textbox_default" tabindex="2" />&nbsp;&nbsp;<i><font size="2" color="red"><html:errors property="errors.keywordType"/></font></i></td>
</tr>
<tr>
<td width="19%"><font class="labelField">Role Type</font></td>
<td width="81%">&nbsp;&nbsp;<font color="red" size="2" class="labelMandatory">*</font><html:select property="roleType" style="width:150px" styleClass="file_Upload" tabindex="3">
  <html:option value="">Select Role</html:option>
  <html:optionsCollection name="roleTypes" value="roleId" label="roleName" />
  </html:select>&nbsp;&nbsp;<i><font size="2" color="red"><html:errors property="errors.roleType"/></font></i>
</td>
</tr>
<tr>
<td ><font class="labelField">Status</font></td>
<td>&nbsp;&nbsp;<font color="red" size="2" class="labelMandatory">*</font><html:select property="positionStatus" style="width:100px" styleClass="file_Upload" tabindex="4">
	  <html:option value="0">Active</html:option>
	  <html:option value="1">Inactive</html:option>
  </html:select>&nbsp;&nbsp;<i><font size="2" color="red"><html:errors property="errors.positionStatus"/></font></i>
</td>
</tr>
<tr height="10px"><td colspan="3"></td></tr>
<tr>
<td>&nbsp;</td>
<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="linkbutton_background" tabindex="5" value="Submit" accesskey="s" onclick="submitForm()"><u>S</u>ubmit</button>&nbsp;&nbsp;&nbsp;&nbsp;<button value="Cancel" accesskey="c" type="button" tabindex="6" class="linkbutton_background" onclick="goHome();"><u>C</u>ancel</button></td>
</tr>
</table>
</html:form>
</logic:present>
<logic:present name="NOPosition">
<font class="heading_blue"> No Positions Found </font> 
</logic:present>

<logic:present name="positionList">
<ec:table items="positionList" sortable="true" action="./PositionAction.do?param=getPositionList"  imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10">
		<ec:row highlightRow="true"> 
			<ec:column property="positionTitle" title="Position" width="30%"/>
			<ec:column property="positionFlag" title="Status" width="20%"/>
			<ec:column property="roleType" title="Role&nbsp;Type" width="20%"/>
			<ec:column property="optLink" title="Actions" width="30%"/>
		</ec:row>
</ec:table> 
</logic:present>
<!-- 
<script>

for(var i=0;i<document.getElementById("keywordType").length;i++) {
   if(document.getElementById("keywordType").options[i].value=='Employee Position')
   document.getElementById("keywordType").selectedIndex=i;
 }  
</script>-->
<logic:present name="PositionId">
<c:out value="${PositionId}" />
<script>
</script>
</logic:present>
</body>
</html>