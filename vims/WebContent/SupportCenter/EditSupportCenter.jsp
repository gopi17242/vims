<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>Vertex Incident Management System - Home Page</title>
<script>
	var x
	
	function GetXmlHttpObject()
	{
	    var xmlHttp=null;
			try
			  {
				  // Firefox, Opera 8.0+, Safari
				  xmlHttp=new XMLHttpRequest();
			  }
			catch (e)
			  {
				  // Internet Explorer
			    try
			    {
			    	xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
			    }
			  	catch (e)
			    {
			    	xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
			    }
			  }
		  
		return xmlHttp;
	}
	
	function showHint()
	{
		str=document.getElementById("supportcentername").value;
		if (str.length==0)
		  { 
			  document.getElementById("showMsg").innerHTML="";
			  return
		  }
		  
		x=GetXmlHttpObject();	
		if (x==null)
		  {
			  alert ("Your browser does not support AJAX!");
			  return
		  } 
		  
		var url="checkSupportCenterAvailability.do?methodname=checkSupportCenter&SupportCenterName="+str;
				
		//url=url+"?q="+str;
		
			
		x.onreadystatechange=stateChanged;
		x.open("GET",url,true);
		x.send(null);
	}
		function stateChanged() 
	{ 
		if (x.readyState==4)	
		{ 		
			    response  = x.responseXML.documentElement;
	      		var result=response.getElementsByTagName('result')[0].firstChild.data;
	      		if(result=="Ok")
	      		{
	      		  result="";
	      		  document.getElementById("spanID").innerHTML=result;
	      		}
	      		else
	      		{
	      		  document.getElementById("supportcentername").select();
	      		  document.getElementById("spanID").innerHTML=result;
	      		  //document.getElementById("supportcentername").focus();
	      		}	
	    } 
      	else 
      	{
           
       	}
	}

function EditSupportCenter()
	{	 
	 var formobj=document.forms[0];
	 document.getElementById("editSupportCenter").value="edit";
	 formobj.action="./ModifySupportCenter.do?methodname=EditSupportCenter";
	 formobj.submit();
	 }
	 
	function fnGoBack()
    {
    window.location="./EditSupportCenter.do?methodname=SupportCenterPage&menuId=Support&pageId=SupportCenters";
    }
</script>
</head>
<body>
<br>
<html:form action="/EditSupportCenter.do?methodname=EditSupportCenter" focus="supportcentername">
<input type="hidden" name="editSupportCenter" id="editSupportCenter" value="EditSupportCenter"/>
<html:hidden property="selSupCen"/>
<html:hidden property="statusType" />
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >
	<tr><td colspan="2"><logic:present name="strMessage"><h4><font color="green"><c:out value="${strMessage}"/></font></h4></logic:present></td></tr>
		<!-- <tr>
		<td class="labelField" align="left">Support Center ID&nbsp;</TD>
		<td align="left"><font color=red>*</font><html:text property="supportcenterid" styleClass="textbox_default" maxlength="16" style="width:117px"/>&nbsp;&nbsp;
		<font color="red" size="2"><i><html:errors property="SupportCenterIDError"></html:errors></i></font></td>
	</tr> -->
	<tr>
		<td class="labelField" align="left" width="20%">Support Center Name&nbsp;</TD>
		<td align="left" width="80%">&nbsp;<font color="red" class="labelMandatory">*</font><html:text property="supportcentername" styleClass="textbox_default" maxlength="40" style="width:250px" onchange="showHint();" tabindex="2"/>&nbsp;&nbsp;<font color="red" size="2"><i><span id="spanID"></span><html:errors property="SupportCenterNameError"></html:errors></i></font>
		<i><font color="red" size="2"><html:errors property="SupportCenterNameExists"></html:errors></font></i>
		</td>
	</tr>
	<tr>
		<td class="labelField"align="left">Location&nbsp;</TD>
		<td >&nbsp;<font color="red" class="labelMandatory">*</font><html:select property="supportcenterlocation" styleClass="dropdownlist" style="width:180px" tabindex="3">
		<option value="">Select Location</option>
		<html:optionsCollection name="Locations" value="locationId" label="locationName"/>
		<!--<html:option value="India">India</html:option>
		<html:option value="USA">United States of America</html:option>
		--></html:select>&nbsp;&nbsp;<font color="red" size="2"><i><html:errors property="LocationError"></html:errors></i></font>
		</TD>
		<td class="labelField" align="left"><font color="red"><i><html:errors property="GroupNameError"></html:errors></i></font></td>
	</tr>
	<tr>
		<td class="labelField"align="left">Support Manager&nbsp;</TD>
		<td >&nbsp;<font color="red" class="labelMandatory">*</font><html:select property="supportmanagers" styleClass="dropdownlist" style="width:250px" tabindex="4">
		<option value="">Select Support Manager</option>
          <!-- <option value="<c:out value="${Manager}"/>"><c:out value="${Manager}"/></option>-->
	        <html:optionsCollection name="SupportManagers" value="SupervisorID" label="SupervisorName"/>
	       </html:select>&nbsp;&nbsp;<font color="red" size="2"><i><html:errors property="SupportCenterManagerError"></html:errors></i></font></td>
	</tr>
	<tr>
		<td class="labelField"align="left">Status&nbsp;</TD>
		<td>&nbsp;&nbsp;&nbsp;<html:select property="status" style="width:80px" styleClass="dropdownlist" tabindex="5">
		<html:option value="0">Active</html:option>
		<html:option value="1">Inactive</html:option>
		</html:select>&nbsp;&nbsp;<font color="red" size="2"><i><html:errors property="StatusError"></html:errors></i></font>
		</TD>
	</tr>
	<tr><td colspan="2" height="9px"></TD></tr>
	<tr>
	<td>&nbsp;</td>
	<td align="left" colspan="2">	
	<table width="20%">
		<tr>
		<td height="20px">&nbsp;&nbsp;<button type="button" onClick="EditSupportCenter();" accesskey="u" class="linkbutton_background" tabindex="6"/><u>U</u>pdate</button></td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" accesskey="c" onClick="fnGoBack();" class="linkbutton_background" tabindex="7"><u>C</u>ancel</button>
		</td>	
		</tr>
	</table>
	</td>
	</tr>
	</table>
	<script>
	document.getElementById("statusType").selectedIndex=document.getElementById("status").selectedIndex;
	</script>
</html:form>
</body>
</html>