<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
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
       window.location="./StateAction.do";
     }
     else if(keywordSelected=='Role') {
       window.location="./RoleAction.do?param=roleHome";
     }
} 
function submitForm() {

    var formObject=document.forms[0];
    
    formObject.action="./insertRoleDetails.do?param=insertRoleDetails";
    //alert(formObject.action);
    formObject.submit();  
    
}

function goHome() {
  window.location="./customizeFields.do?param=getFieldConfigurationSettings&pageId=FieldPage";
}
</script>
</head>
<body><br>
<center><h4><font color="green"><c:out value="${msg}" /></font></h4></center>
<logic:present name="fieldList">
<html:form action="/insertRoleDetails.do?param=insertRoleDetails" focus="roleName">
<html:hidden property="roles" value="roleForm" />
<table width="100%" border="0">
<!-- 
<tr>
<td width="19%"><font class="labelField">Select an option</font></td>
<td width="81%">&nbsp;&nbsp;&nbsp;<html:select property="keywordType" styleClass="file_Upload" onchange="getFieldTable(this)" tabindex="1">
<option value="">---Select option---</option>
<c:forEach items="${fieldList}" var="listItem">
<option value='<c:out value="${listItem}"/>'><c:out value="${listItem}"/></option>
</c:forEach>
</html:select>
</td>
</tr> -->
<tr>
<td width="19%"><font class="labelField">Add Role</font></td>
<td width="81%">&nbsp;&nbsp;<font color="red" size="2" class="labelMandatory">*</font><html:text property="roleName" styleClass="textbox_default" tabindex="2" />&nbsp;&nbsp;<i><font size="2" color="red"><html:errors property="errors.roleName"/></font></i></td>
</tr>
<tr>
<td width="19%"><font class="labelField">Status</font></td>
<td width="81%">&nbsp;&nbsp;&nbsp;&nbsp;<html:select property="positionStatus" styleClass="file_Upload" tabindex="3">
	  <html:option value="0">Active</html:option>
	  <html:option value="1">Inactive</html:option>
  </html:select>&nbsp;&nbsp;<i><font size="2" color="red"><html:errors property="errors.positionStatus"/></font></i>
</td>
</tr>
<tr height="10px"><td colspan="3"></td></tr>
<tr>
<td>&nbsp;</td>
<td colspan="2" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="linkbutton_background" tabindex="4" value="Submit" accesskey="s" onclick="submitForm()"><u>S</u>ubmit</button>&nbsp;&nbsp;&nbsp;&nbsp;<button value="Cancel" accesskey="c" type="button" tabindex="5" class="linkbutton_background" onclick="goHome();"><u>C</u>ancel</button></td>
</tr>
</table>
</html:form>
</logic:present>
<logic:present name="RoleList" >
<ec:table items="RoleList" action="./RoleAction.do?param=roleHome" showStatusBar="false" imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10">
	<ec:row highlightRow="true"> 
			<ec:column property="roleName" title="Role&nbsp;&nbsp;Name" width="34%"/>
			<ec:column property="roleStatus" title="Status" width="33%"/>
			<ec:column property="actionLink" title="Actions" width="33%"/>
	</ec:row>
</ec:table>
</logic:present>
<!-- <script>
for(var i=0;i<document.getElementById("keywordType").length;i++) {
   if(document.getElementById("keywordType").options[i].value=='Role')
   document.getElementById("keywordType").selectedIndex=i;
 }  
</script> -->
</body>
</html>