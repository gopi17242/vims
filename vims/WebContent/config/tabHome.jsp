<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<html>
<head>
<script>
function selectAll(control,cnt) {
  //alert("from selectAll metho");
  // alert(control);
  // alert(cnt);  
   if(cnt!='') { 
    var objCheckBoxes=document.forms[1].elements[cnt];
   // alert(objCheckBoxes);
   	var countCheckBoxes = objCheckBoxes.length;
   	if((objCheckBoxes.length==null)) {
        
        if(control.checked==true)
         document.forms[1].elements[cnt].checked=true;
        else
         document.forms[1].elements[cnt].checked=false;
   	    return
   	 }
   	if((countCheckBoxes!=null)&&(countCheckBoxes>0)) {
	for(var k = 0; k < countCheckBoxes; k++) {
		if(control.checked==true) 
		 objCheckBoxes[k].checked=true;
		else 
		 objCheckBoxes[k].checked=false;
	  } 
    }
  }
}

function getList() {
  
    var formObj=document.forms[0];
   // alert("form object====="+formObj);
    if(document.getElementById("userType").selectedIndex!=0) {
      formObj.submit();
    }
    else {
        window.location="./TabHome.do?param=tabHomeSettings&pageId=AccessPrevileges";
     }
     
}

function submitForm() {
   
   var firstForm=document.forms[0];
   var rolesSelected=firstForm.userType.options[document.getElementById("userType").selectedIndex].value;

   document.getElementById("roleSelected").value=rolesSelected;
   //alert(rolesSelected);
   var formObj=document.forms[1];
   formObj.action="./tabTest.do?param=configureTabList";
   formObj.submit();
}

function selectMainTab(cnt,cntName) {
    //alert("selected One===="+cntName);
    //alert("main tab====="+cnt);
   if((cnt!='')&&(cntName!='')) { 
    var objCheckBoxes=document.forms[1].elements[cntName];
    var countCheckBoxes = objCheckBoxes.length;
    
    if((objCheckBoxes==null)||(countCheckBoxes==null)) {
       // alert(document.forms[1].elements[cnt].checked);
       /// alert(document.forms[1].elements[cntName].checked);      
        if(document.forms[1].elements[cntName].checked==true)
         document.forms[1].elements[cnt].checked=true;
        else
         document.forms[1].elements[cnt].checked=false;
   	    return
   	 }
   	 //alert("countCheckBoxes===="+countCheckBoxes);
     if(countCheckBoxes!=null) {
       if((objCheckBoxes.length==null)) {
        
        if(cnt.checked==true)
         document.forms[1].elements[cnt].checked=true;
        else
         document.forms[1].elements[cnt].checked=false;
   	    return
   	 }
   	 var c=0;
   	if((countCheckBoxes!=null)&&(countCheckBoxes>0)) {
	for(var k = 0; k < countCheckBoxes; k++) {
	    //alert(objCheckBoxes[k]);
		if(objCheckBoxes[k].checked==true) { 
		   c=1;
		   break;
		} 
	  }
	  // alert("c value--"+c);
	   if(c==1) {
	    //  alert("11");
	    //  alert(document.getElementById(cnt).checked);
	     document.getElementById(cnt).checked=true;
	   }  
	   else {
	  //  alert("22"); 
	    document.getElementById(cnt).checked=false;
	   }     
    }
   }
    else if(document.getElementById(cntName).checked==true){
       document.getElementById(cnt).checked=true;
      // alert("2");
    }
    else if(document.getElementById(cntName).checked==false){
       document.getElementById(cnt).checked=false;
     //  alert("3");
    }
    // alert("end");
  } 
}

function goHome() {
 window.location="./TabHome.do?param=tabHomeSettings&pageId=AccessPrevileges";
}
</script>
</head>
<body><br>
<table width="100%" border="0">
	<tr>
		<td colspan="2" align="center"></td>
	</tr>
	<tr>
	<td colspan="2">
	   <h4><font color="green"><c:out value="${configMsg}" escapeXml="false"/></font></h4>
	</td>
</tr>
<html:form action="/getNavigationSettings.do?param=getNavigSettings">	
	<tr>
		<td width="20%" class="bodytext">Select role type</td>
		<td width="80%">&nbsp;&nbsp;<html:select property="userType" styleClass="file_Upload" onchange="getList()">
		    		<option value="">Select Role Type</option>
		    	<c:forEach items="${roleTypes}" var="roles">
		    		<option value='<c:out value="${roles.roleId}"/>'><c:out value="${roles.roleName}"/></option>
		   		</c:forEach>
  			</html:select>
  		</td>
	</tr>
</html:form>
<logic:present name="tabList">
<tr>
<td width="20%">&nbsp;</td>
<td width="80%" align="left">
<table width="50%" border="0">
<html:form action="/storeTabSettings.do?param=storeSettings">
<html:hidden property="roleSelected" value=""/>
<c:forEach items="${tabList}" var="record">
<tr>
	<td class='heading_blue' colspan='2' align='left'><input type='checkbox' name='<c:out value="${record.tabName}" />' value='<c:out value="${record.tabId}" />' onclick="selectAll(this,'<c:out value="${record.keyName}" />')" <c:out value="${record.permissionFlag}"/> > <c:out value="${record.tabName}" /></td>
</tr>
<c:if test="${record.subMenu ne null}">
<c:forEach items="${record.subMenu}" var="dispName">
<tr><td width=20%'>&nbsp;</td><td  align='left'><input type='checkbox' name='<c:out value="${record.keyName}" />' value='<c:out value="${dispName.id}" />' onclick="selectMainTab('<c:out value="${record.tabName}" />','<c:out value="${record.keyName}" />')" <c:out value="${dispName.permissionFlag}"/> ><font class="labelField"><c:out value="${dispName.name}" /></font></td></tr>
</c:forEach>
</c:if>
</c:forEach>
</html:form>
</table>
</td>
</tr>
<tr height="10px"><td colspan="2"></td></tr>
<tr>
<td width="20%">&nbsp;</td>
	<td width="80%">&nbsp;<button type="button" class="linkbutton_background" onclick="submitForm()">Submit</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button value="Cancel" accesskey="c" type="button" class="linkbutton_background" onclick="goHome();"><u>C</u>ancel</button></td>
</tr>
</logic:present>
</table>
<c:if test="${selectedRoleId ne null}">
<script>
document.getElementById("userType").selectedIndex=<c:out value="${selectedRoleId}"/>
</script>
</c:if>
</body>
</html>