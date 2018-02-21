<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html:html>
<head>
<script language="javascript" type="text/javascript" src="./script-test/configuration.js"></script>
<script language="javascript" type="text/javascript" src="./script-test/VIMS_Alerts.js"></script>
<script>

function submitForm() {
 
     document.forms[0].action="./mailAction.do?param=MailServerConfig";
     document.forms[0].submit();
}

function clearData() {
 document.getElementById("serverName").value="";
 document.getElementById("serverStatus").selectedIndex=0;
}
function goHome() {
     window.location="./configHomePage.do?menuId=Configuration&pageId=ConfigurationSettings";   
}


var ajaxObject;	
function setAsDefault(objectSelected,cnt) {
  
   if(objectSelected.checked==true) {
     var sets=document.forms[1].elements("Mail");
    for(i=0;i<sets.length;i++) {
     if((sets[i].checked==true)&&(sets[i].value!=cnt))
       sets[i].checked=false;
   }
   ajaxObject=GetXmlHttpObject();
   if(ajaxObject==null) {
      alert("Your browser does not support Ajax");
      return
   }        
    else {
      
          var url="setServerDefaultStatus.do?param=setServerStatus&type=Mail&serverId="+cnt;
		    ajaxObject.onreadystatechange=displayMsg;
			ajaxObject.open("POST",url,false);
			ajaxObject.send(null);
			
			
      }
     }
   else {
          alert("Atleast one server should be Default");
          objectSelected.checked=true;
    }   
         
}

function displayMsg()
{
     if((ajaxObject.readyState==4)) {
         var resultText=ajaxObject.responseText;
       }
}
</script>
</head>
<body>
<br>
<table border="0" width="100%" cellpadding="0" cellspacing="0">
<html:form action="mailAction" focus="serverName">
<html:hidden property="configType" value="mail" />
	<tr>
		<td class="labelField" width="150px">Server Name</td>
		<td align="left" width="80%">&nbsp;<font color="red" class="labelMandatory" size="2">*</font><html:text tabindex="1" property="serverName" styleClass="textbox_default" maxlength="50" size="50"/>&nbsp;&nbsp;<i><font size="2" color="red"><html:errors property="error.serverName"/></font></i></td>
	</tr>
	<tr>
	    <td class="labelField" >Server Status</td>
	    <td align="left">&nbsp;<font class="labelMandatory" color="red" size="2">*</font><html:select  tabindex="2" style="width:100px"  property="serverStatus" styleClass="dropdownlist">
	             <html:option value="">Select Status</html:option> 
		         <html:option value="Active">Active</html:option>
		         <html:option value="Inactive">Inactive</html:option>
	         </html:select>&nbsp;&nbsp;<i><font size="2" color="red"><html:errors property="error.serverStatus"/></font></i> 
	    </td>
	</tr>
	

	<tr>
	    <td>&nbsp;&nbsp;</td>
		<td colspan="2" height="60px" >&nbsp;&nbsp;&nbsp;<button type="button" tabindex="3" value="Submit" class="linkbutton_background" onclick="submitForm()" accesskey="S"><u>S</u>ubmit</button>
		&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" accessKey="c" tabindex="4" class="linkbutton_background" onclick="goHome();"><u>C</u>ancel</button>
		</td>
	</tr>

	</html:form>
</table> 

<tr>
<td><h4><logic:present name="successMsg"><font color="green"><c:out value="${successMsg}" /></font></logic:present>
        <logic:present name="failureMsg"><font color="red"><c:out value="${failureMsg}" /></font></logic:present></h4>
        <ec:table items="serverList" sortable="true" action="/ldapServerAction.do"
		imagePath="./images/*.gif" filterable="false" width="100%"
		rowsDisplayed="10">
		<ec:row highlightRow="true"> 
			<ec:column property="Server_Name" title="Server Name" width="35%"/>
			<ec:column property="Server_Status" title="Status" width="20%"/>
			<ec:column property="optLink" title="Actions" width="20%"/>
			<ec:column property="defaultStatus" title="Set as Default" width="25%"/>
		</ec:row>
</ec:table>
</td>
</tr>

<c:if test="${optStatus ne null}">
<script>
clearMailData();
</script>
</c:if>
</body>
</html:html>
