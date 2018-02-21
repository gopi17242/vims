<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
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
       window.location="./StateAction.do?param=getStateAction";
     }
     else if(keywordSelected=='Role based access') {
       window.location="./RoleAction.do?param=roleHome";
     }
}
function submitForm() {

    var formObject=document.forms[0];
    formObject.action="./insertSpecificKeywordValue.do?param=addSpecificKeywordOptionValue";
    formObject.submit();  
    
}
</script>
</head>
<body>
<br>

<logic:present name="fieldList">
<html:form action="/insertSpecificKeywordValue.do?param=addSpecificKeywordOptionValue" focus="keywordType">
<table width="100%" border="0">
<tr>
<td width="20%"><font class="labelField">Select keyword</font></td>
<td width="80%">&nbsp;&nbsp;<html:select property="keywordType" styleClass="file_Upload" onchange="getFieldTable(this)" tabindex="1">
<option value="">Select Option      </option>
<c:forEach items="${fieldList}" var="listItem">
<option value='<c:out value="${listItem}"/>'><c:out value="${listItem}"/></option>
</c:forEach>
</html:select>
</td>
</tr>
<tr>
<td colspan="2"></td>
</tr>
<tr id="showkeyword" style="display:none">
<td width="50%">Add <span id="keywordLabel"> </span></td>
<td><font color="red" size="2" class="labelMandatory">*</font><html:text property="keywordValue" styleClass="textbox_default" tabindex="2" />&nbsp;&nbsp;<span id="errorSpan"><i><font size="2" color="red"><html:errors property="errors.keywordType"/></font></i></span></td>
</tr>
<tr id="showButtons" style="display:none">
<td>&nbsp;</td>
<td colspan="2"><button type="button" class="linkbutton_background" tabindex="3" value="Submit" accesskey="s" onclick="submitForm()"><u>S</u>ubmit</button>&nbsp;&nbsp;&nbsp;&nbsp;<button value="Cancel" accesskey="c" type="button" tabindex="4" class="linkbutton_background"><u>C</u>ancel</button></td>
</tr>
</table>
</html:form>
</logic:present>
<logic:present name="successMsg">
<span id="resultMsg" ><c:out value="${successMsg}" /></span>
</logic:present>
<logic:present name="formValidation">
<script>
 var keywordSelected1='<c:out value="${formValidation}" />';
 for(var i=0;i<document.getElementById("keywordType").length;i++) {
 
   if(document.getElementById("keywordType").options[i].value=keywordSelected1) {
     document.getElementById("keywordType").selectedIndex=i;
     document.getElementById("keywordLabel").innerText=keywordSelected1;
  } 
 } 
 document.getElementById("showKeyword").style.display="block";
 document.getElementById("showButtons").style.display="block";
</script>
</logic:present>
</body>
</html>