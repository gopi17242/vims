<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<script language="javascript" type="text/javascript"
	src="<html:rewrite page='/script-test/configuration.js' />">
</script>
<script>
function submitForm() {
   
   document.getElementById("spanId").innerText="Submitted Successfully";
   var formObj=document.forms[0];
   formObj.action="./tabTest.do?param=configureTabList";
   formObj.submit();
}

function getList() {

  alert("Method called");
}

function selectMainTab(cnt,cntName) {
   if((cnt!='')&&(cntName!='')) { 
    var objCheckBoxes=document.forms[0].elements[cntName];
    var countCheckBoxes = objCheckBoxes.length;
    if(countCheckBoxes!=null) {
    if((objCheckBoxes.length==null)) {
        
        if(control.checked==true)
         document.forms[0].elements[cnt].checked=true;
        else
         document.forms[0].elements[cnt].checked=false;
   	    return
   	 }
   	 var c=0;
   	if((countCheckBoxes!=null)&&(countCheckBoxes>0)) {
	for(var k = 0; k < countCheckBoxes; k++) {
		if(objCheckBoxes[k].checked==true) { 
		   c=1;
		   break;
		} 
	  }
	   if(c==1)
	     document.getElementById(cnt).checked=true;
	   else 
	    document.getElementById(cnt).checked=false;    
    }
   }
    else if(document.getElementById(cntName).checked==true){
       document.getElementById(cnt).checked=true;
    }
    else if(document.getElementById(cntName).checked==false){
       document.getElementById(cnt).checked=false;
    }
  } 
}
</script>
</head>
<body>

<center>
    <font class="heading_blue">Access Privileges</font>
</center>
<br>
<html:form action="/tabTest.do?param=configureTabList">
<table width="100%">
<tr>
<td>
<table> 
<tr>
<td class="bodytext">Select User Type&nbsp;&nbsp;</td>
<td><select name="userType" class="file_Upload" onchange="getList()">
    <option value="">Select Role Type</option>
    <c:forEach items="${roleTypes}" var="roles">
    <option value='<c:out value="${roles.roleId}"/>'><c:out value="${roles.roleName}"/></option>
   </c:forEach>
  </select>  
</tr>
</table>
</td>
</tr>
<tr>
<td align="center">
<table width="50%">
<c:forEach items="${tabList}" var="record">
<tr>
	<td class='heading_blue' colspan='2' align='left'><input type='checkbox' name='<c:out value="${record.tabName}" />' onclick="selectAll(this,'<c:out value="${record.keyName}" />')"><c:out value="${record.tabName}" /></td>
</tr>
<c:if test="${record.subMenu ne null}">
<c:forEach items="${record.subMenu}" var="dispName">
<tr><td width="20%">&nbsp;</td><td  align='left'><input type='checkbox' name='<c:out value="${record.keyName}" />' value='<c:out value="${dispName.id}" />'onclick="selectMainTab('<c:out value="${record.tabName}" />','<c:out value="${record.keyName}" />')"><c:out value="${dispName.name}" /></td></tr>
</c:forEach>
</c:if>
</c:forEach>
</table>
</td>
</tr>
</table>
<div id="spanId"></div>
<button type="button" class="linkbutton_background" onclick="submitForm()">Submit</button>
</html:form>
</body>
</html>