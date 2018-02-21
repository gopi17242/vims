<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>Vertex Incident Management System - Home Page</title>
<script type="text/javascript" src="./script-test/Calender.js"></script>
<script>
var cal = new CalendarPopup(); 

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
		
		if(document.getElementById("supnameerr")!=null && document.getElementById("supnameerr")!=''){
			document.getElementById("supnameerr").innerText='';
		}
		
		str=document.getElementById("supportcentername").value;
		if (str.length==0)
		  { 
		  		
			  document.getElementById("spanID").innerHTML="";
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
	      		}	      		
					    	
	    } 
      	else 
      	{
           
       	}
	}
	function NewSupportCenter()
	{
		  var formobj=document.forms[0];
		  document.getElementById("editSupportCenter").value="NewSupportCenter"
		  formobj.action="./NewSupportCenter.do?methodname=AddSupportCenter";
		  formobj.submit();		
	}
  function fnGoBack()
  {
    window.location="./EditSupportCenter.do?methodname=SupportCenterPage&menuId=Support&pageId=SupportCenter";
  }
</script>
</head>
<body>
<br>
<html:form action="/NewSupportCenter.do?methodname=AddSupportCenter" focus="supportcentername">
<input type="hidden" name="editSupportCenter" value=""/>
<table border="0" width="100%" align="left" cellspacing="0" cellpadding="0" >
	<tr><td colspan="3"><logic:present name="SuccessMessage"><h4><font color="green"><c:out value="${SuccessMessage}"/></font></h4></logic:present></td></tr>
	<tr><td colspan="3"><logic:present name="FailureMessage"><h4><font color="red"><c:out value="${FailureMessage}"/></font></h4></logic:present></td></tr>
	<!--  <tr>
		<td class="labelField" align="left" width="110px" valign="top">Support Center ID&nbsp;</TD>
		<td align="left" width="80%" valign="top"><font color=red>*</font><html:text property="supportcenterid" styleClass="textbox_default" maxlength="16" style="width:117px"/>&nbsp;&nbsp;<font color="red" size="2"><i><html:errors property="SupportCenterIDError"></html:errors></i></font></td>
	</tr>-->
	<tr>
		<td class="labelField" align="left" width="22%">Support Center Name</TD>
		<td align="left" width="78%" >&nbsp;<font color="red" class="labelMandatory">*</font><html:text property="supportcentername" styleClass="textbox_default" maxlength="40"  style="width:250px" onblur="showHint();" tabindex="1"/>&nbsp;&nbsp;<font color="red" size="2"><i><span id="spanID"></span><html:errors property="SupportCenterNameError"></html:errors></i></font>
		<i id="supnameerr"><font color="red" size="2"><html:errors property="SupportCenterNameExists"></html:errors></font></i>
		</td>
	</tr>
	<tr>
		<td class="labelField" align="left">Location&nbsp;</TD>
		<td>&nbsp;<font color="red" class="labelMandatory">*</font><html:select property="supportcenterlocation" styleClass="dropdownlist" style="width:180px" tabindex="2">
		<option value="">Select Location</option>
		<logic:present name="Locations">
		<html:optionsCollection name="Locations" value="locationId" label="locationName"/></logic:present>
		<!--<html:option value="India">India</html:option>
		<html:option value="USA">United States of America</html:option>
		--></html:select>&nbsp;&nbsp;<font color="red" size="2"><i><html:errors property="LocationError"></html:errors></i></font>
		</TD>
	</tr>
	<tr>
		<td class="labelField"align="left">Support Manager&nbsp;</TD>
		<td >&nbsp;<font color="red" class="labelMandatory">*</font><html:select property="supportmanagers" styleClass="dropdownlist" style="width:250px" tabindex="3">
          <html:option value="">Select Support Manager</html:option>
    	  <html:optionsCollection name="SupportManagers" value="SupervisorID" label="SupervisorName"/>
	       </html:select>&nbsp;&nbsp;<font color="red" size="2"><i><html:errors property="SupportCenterManagerError"></html:errors></i></font></td>
	</tr>
	<!-- <tr>
	    <td class="labelField" align="left">Support Begin Date&nbsp;</td>
	    <td><font color="red" class="labelMandatory">*</font><html:text property="supportbegindate" style="width:150px" styleClass="textbox_default" styleId="SupportBeginDate" readonly="true"/>
	    <a href="#" onClick="cal.select(document.getElementById('SupportBeginDate'),'anchor1','MM/dd/yyyy'); return false;" NAME="anchor1" ID="anchor1">
	    <img src="./images/calendar.gif" width="18" height="17" border="0" alt="Calender"/></a>
	    &nbsp;&nbsp;<font color="red" size="2"><i><html:errors property="BeginDateError"></html:errors></i></font>
	    </td>
	</tr>
	<tr>
	    <td class="labelField" align="left">Support End Date&nbsp;</td>
	    <td><font color="red" class="labelMandatory">*</font><html:text property="supportenddate" style="width:150px" styleClass="textbox_default" styleId="SupportEndDate" readonly="true"/>
	    <a href="#" onClick="cal.select(document.getElementById('SupportEndDate'),'anchor2','MM/dd/yyyy'); return false;" NAME="anchor2" ID="anchor2">
	    <img src="./images/calendar.gif" width="18" height="17" border="0" alt="Calender"/></a>
	    &nbsp;&nbsp;<font color="red" size="2"><i><html:errors property="EndDateError"></html:errors></i></font>
	    </td>
	</tr>-->
		<tr>
		<td class="labelField"align="left">Status&nbsp;</TD>
		<td>&nbsp;&nbsp;&nbsp;<html:select style="width:80px" property="status" styleClass="dropdownlist" tabindex="4">
		<html:option value="0">Active</html:option>
		<html:option value="1">Inactive</html:option>
		</html:select>&nbsp;&nbsp;<font color="red" size="2"><i><html:errors property="StatusError"></html:errors></i></font>
		</TD>
	</tr>
	<!-- <tr><td><input type="hidden" name="status" value="Active"/></TD></tr>-->
	<tr><td colspan="2" height="10px"></TD></tr>
	<tr>
	<td>&nbsp;</td>
	<td align="left" colspan="2">
	<table width="20%" border="0">
		<tr>
		<td height="20px">&nbsp;&nbsp;<button type="button" onClick="return NewSupportCenter();" accesskey="s" class="linkbutton_background" tabindex="5"/><u>S</u>ubmit</button></td>
		<!--<a href="javascript:;" onClick="return NewSupportCenter();" accesskey="s"><img border="0" src="images/submit.gif" /></a>  -->
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<button type="reset" accesskey="r" class="linkbutton_background" tabindex="6"><u>R</u>eset</button></td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" accesskey="c" onClick="fnGoBack();" class="linkbutton_background" tabindex="7"><u>C</u>ancel</button>
		</td>
		</tr>
	</table>
	</td>	
	</tr>
	<tr height="95px">
	<td></td>
	</tr>
		
	</table>
</html:form>
</body>
</html>