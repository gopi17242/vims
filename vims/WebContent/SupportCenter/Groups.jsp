<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html>
<head>
<title>Vertex Incident Management System - Home Page</title>
<script language="javascript" src="./script-test/VIMS_Alerts.js"></script>
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
		
		str=document.getElementById("groupname").value;
		if (str.length==0)
		  { 
			  document.getElementById("spanID").innerHTML="";
			  return
		  }
		  
		x=GetXmlHttpObject();
		
		if (x==null)
		  {
			  alert (ajaxAlert);
			  return
		  } 
		  
		var url="checkGroupAvailability.do?methodname=checkGroup&GroupName="+str;
		
		
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
	      		  document.getElementById("groupname").select();
	      		  document.getElementById("spanID").innerHTML=result;
	      		}		
	    } 
      	else 
      	{
           
       	}
	}
function moveOriginIndstTypes1()
{
  var count=0;
  var i=0;
  if (document.forms[0].employees.options.length == 0)
   alert(noValues);
  else{
	while(i<document.forms[0].employees.options.length)
	{
		len = document.forms[0].selectDest.options.length;
  
		if(document.forms[0].employees.options[i].selected)
		{
			
			document.forms[0].selectDest.options[len] = new Option(document.forms[0].employees.options[i].text,document.forms[0].employees.options[i].value);
			document.forms[0].employees.options[i] =null;
			count++;
			i=0;
		}
		else
			i++;
	}
	if(count==0)
	alert(empSelect);
     }
}
function moveDestIndstTypes1()
{
	var count=0;
	var i=0;
	if (document.forms[0].selectDest.options.length == 0)
		alert(empSelect);
  	else
  	{
		while(i<document.forms[0].selectDest.options.length)
		{
			len = document.forms[0].employees.options.length
			if(document.forms[0].selectDest.options[i].selected) 
			{
				document.forms[0].employees.options[len] = new Option(document.forms[0].selectDest.options[i].text,document.forms[0].selectDest.options[i].value);
				document.forms[0].selectDest.options[i] =null;
				
				count++;
				i=0;				
			}
			else
				i++;		
		}
		if(count==0)
		alert(empSelect);
	}
}

function AddNewGroup()
{

   var formObj=document.forms[0];
   var selectDestLength=formObj.selectDest.length;
   
   for(var i=0;i<selectDestLength;i++)
   {
    document.forms[0].selectDest.options[i].selected=true;
   }	
   
   formObj.action="./NewSupportCenterGroup.do?methodname=AddSupportCenterGroup";
   formObj.submit();
  }

 function fnGoBack()
  {
    window.location="./supportCenter.do?methodname=ViewGroups&menuId=Group&pageId=Groups";
    }
</script>
</head>
<body>
<br>
<html:form action="/supportCenter.do?methodname=AddSupportCenterGroup" focus="groupsupportcenterid">
<input type="hidden" name="groupedit" id="editgroup" value="NewGroup"/>
<table border="0" width="100%" align="left" cellspacing="0" cellpadding="0">
<!--  <tr><td height="10px" colspan="5" align="left" class="labelField"><font color="red"></font></td></tr>-->	
	<!-- <tr align="left">	       
		<td class="labelField" align="left" width="90px"  valign="top">Group ID</TD>
		<td  align="left" width="80%" valign="top" colspan="2">
			<font color=red>*</font><html:text property="groupid" styleClass="textbox_default" maxlength="16" style="width:117px"></html:text>
			<input type="hidden" name="txtgrpid" size="40" value=""/>
			<font color="red" size="2"><i><html:errors property="GroupIDError"></html:errors></i></font>
		</td>

   	</tr> -->
   	<tr>
	  <td colspan="3">
	  <logic:present name="Success"><h4><font color="green"><c:out value="${Success}"/></font></h4>
	  </logic:present>
	  <logic:present name="Failure"><h4><font color="red"><c:out value="${Failure}"/></font></h4>
	  </logic:present>
	  </td>
	</tr>
	<tr>
	<tr>
	    <td class="labelField" align="left" width="20%">Support Center</TD>
		<td  align="left" width="80%" colspan="2">&nbsp;<font color="red" class="labelMandatory">*</font><html:select property="groupsupportcenterid" styleClass="dropdownlist" style="width:250px" tabindex="1">
			<html:option value="">Select Support Center</html:option>
			<html:optionsCollection name="supportcenters" value="id" label="name"/>
			</html:select>
			<font color="red" size="2"><i><html:errors property="SupportCenterIDError"></html:errors></i></font>	
		</TD>		
	<tr>
		<td class="labelField" align="left" width="20%">Group Name&nbsp;</TD>
		<td  align="left" width="80%" colspan="2">&nbsp;<font color="red" class="labelMandatory">*</font><html:text property="groupname" styleClass="textbox_default" maxlength="40" style="width:250px" onblur="showHint();" tabindex="2"/>
		<font color="red" size="2"><i><span id="spanID"></span><html:errors property="GroupNameError"></html:errors></i></font>
		</TD>		
	</tr>
	<tr>
		<td class="labelField" align="left" width="20%">Group Supervisor&nbsp;</TD>
		<td  align="left" width="80%" colspan="2">&nbsp;<font color="red" class="labelMandatory">*</font><html:select property="groupsupervisors" styleClass="dropdownlist"  style="width:250px" tabindex="3">
			<html:option value="">Select Group Supervisor</html:option>
			<html:optionsCollection name="Supervisors" value="SupervisorID" label="SupervisorName"/>
			</html:select>
			<font color="red" size="2"><i><html:errors property="GroupsupervisorsError"></html:errors></i></font>
	        <input type=hidden value="" name="hidSupCenMgrName">
	        
		</TD>
	</tr>
	<tr>
		<td class="labelField"  width="20%" align="left">Status&nbsp;</td>
		<td align="left"  width="80%" colspan="2">&nbsp;&nbsp;&nbsp;<html:select property="groupstatus" style="width:80px" styleClass="dropdownlist">
		<html:option value="0">Active</html:option>
		<html:option value="1">Inactive</html:option>
		</html:select>&nbsp;&nbsp;<font color="red" size="2"><i><html:errors property="GroupStatusError"></html:errors></i></font>
		</td>
	</tr> 
	<tr>
	<td class="labelField" align="left" width="20%" >Employees</td>
	<td valign="top" width="80%" align="left" colspan="2"><table border="0" width="100%" align="left" cellspacing="0" cellpadding="0" >		
	<tr><td align="top" width="44%">&nbsp;&nbsp;&nbsp;<html:select property="employees" size="6" styleClass="multiplelist" tabindex="4">
		  		<html:optionsCollection name="EmployeeList" value="empid" label="name"/>
		  	</html:select>&nbsp;
		</td>
		 <td align="left" valign="middle" width="10%">
           <p><input type="button" name="btnRight2" value=">>" style="width : 30px" class="linkbutton_arrow"" onClick="return moveOriginIndstTypes1()" tabindex="5"/>
             <input type="button" name="btnLeft2" value="<<" " style="width : 30px" class="linkbutton_arrow" onClick="return moveDestIndstTypes1()" tabindex="7"/>
           </p>
       </td>
        <td width="1%" valign="top"><font color="red" class="labelMandatory">*</font></td>
        <td><select name="selectDest" multiple size="6"  class="multiplelist" id="selectDest" tabindex="6"></select></td>  
<td align="left" width="35%"  valign="top"><font color="red" size="2"><i><html:errors property="GroupEmployeeError"/></i></font></td>
</tr> 
     
</table>
</td>
    
</tr>

<tr>
	<td>&nbsp;</td>
	<td align="left" colspan="2">
	<table width="20%" >
	<tr>
	<td height="60px">&nbsp;&nbsp;<button type="button" onClick="AddNewGroup();" accesskey="s" class="linkbutton_background" tabindex="8"/><u>S</u>ubmit</button>&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
	<td>
		<button type="reset" class="linkbutton_background" accesskey="r" tabindex="9"><u>R</u>eset</button>&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
	<td><button type="button" accesskey="c" onClick="fnGoBack();" class="linkbutton_background" tabindex="10"><u>C</u>ancel</button>
		</td>	
</tr>
</table>
</td>
</tr>

</table>

</html:form>
</body>
</html:html>