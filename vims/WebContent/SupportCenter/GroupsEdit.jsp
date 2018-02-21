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
			  document.getElementById("showMsg").innerHTML="";
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
	      		  
	      		  //document.getElementById("groupname").focus();
	      		}		
	    } 
      	else 
      	{
           
       	}
	}
function moveOriginIndstTypes2()
{
  var count=0;
  var i=0;
  if (document.forms[0].employees.options.length == 0)
   alert(noValues);
  else{
	while(i<document.forms[0].employees.options.length)
	{
		len = document.forms[0].destemployee.options.length;
  
		if(document.forms[0].employees.options[i].selected)
		{
			
			document.forms[0].destemployee.options[len] = new Option(document.forms[0].employees.options[i].text,document.forms[0].employees.options[i].value);
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
function moveDestIndstTypes2()
{
	var count=0;
	var i=0;
	if (document.forms[0].destemployee.options.length == 0)
		alert(noValues);
  	else
  	{
		while(i<document.forms[0].destemployee.options.length)
		{
			len = document.forms[0].employees.options.length
			if(document.forms[0].destemployee.options[i].selected) 
			{
				document.forms[0].employees.options[len] = new Option(document.forms[0].destemployee.options[i].text,document.forms[0].destemployee.options[i].value);
				document.forms[0].destemployee.options[i] =null;
				
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

function EditGroup()
{
 	var formObj=document.forms[0];
 	var selectDestLength=formObj.destemployee.length;
 	//alert(selectDestLength); 	
 	for(var i=0;i<selectDestLength;i++)
 	{ 	
  		document.forms[0].destemployee.options[i].selected=true;
  	}  	
  	 document.getElementById("editgroup").value="";	
  	 formObj.action="./ModifyGroup.do?methodname=ModifySupportCenterGroup";
  	 formObj.submit();
 }
  function fnGoBack()
  {
    window.location="./supportCenter.do?methodname=ViewGroups&menuId=Group&pageId=Group";
    }
    
    
</script>
</head>
<body>
<br>
<html:form action="/supportCenter.do?methodname=EditGroupsPage" focus="groupsupportcenterid">
<input type="hidden" name="groupedit" id="editgroup" value="GroupEdit"/>
<html:hidden property="selectedGroup"/>
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >		
	<!-- <tr><td height="10px" colspan="5" align="left" class="labelField"><font color="red"></font></td></tr> -->	

	<tr>
	  <td colspan="3">
	  <logic:present name="Success"><h4><font color="green"><c:out value="${Success}"/></font></h4>
	  </logic:present>
	  <logic:present name="Failure"><h4><font color="red"><c:out value="${Failure}"/></font></h4>
	  </logic:present>
	  </td>
	</tr>
	<!-- <tr>	       
		<td class="labelField" align="left"   valign="top">Group ID</TD>
		<td  align="left"  valign="top" colspan="2"><font color=red>*</font><html:text property="groupid" styleClass="textbox_default" maxlength="16" style="width:117px"/><c:out value="${groupid}"/><font color="red" size="2"><i><html:errors property="GroupIDError"></html:errors></i></font></td>
   	</tr>	 -->	
	<tr>
	    <td class="labelField" align="left" width="20%">Support Center &nbsp;</TD>
		<td  align="left" width="80%" valign="top" colspan="2"><font color="red" class="labelMandatory">*</font><html:select property="groupsupportcenterid" styleClass="dropdownlist" style="width:250px" tabindex="2">
			<option value="">Select Support Center</option>
			<!-- <option value="<c:out value="${supportCenterID}"/>"><c:out value="${supportCenterID}"/></option>-->
			<html:optionsCollection name="supportcenters" value="id" label="name"/>
			</html:select>&nbsp;&nbsp;<font color="red" size="2"><i><html:errors property="SupportCenterIDError"></html:errors></i></font></td>
	<tr>
		<td class="labelField" align="left" >Group Name&nbsp;</TD>
		<td  align="left" width="80%" valign="top" colspan="2"><font color="red" class="labelMandatory">*</font><html:text property="groupname" styleClass="textbox_default" maxlength="40" style="width:250px" onchange="showHint();" tabindex="3"/>&nbsp;&nbsp;<font color="red" size="2"><i><span id="spanID"></span><html:errors property="GroupNameError"></html:errors></i></font></td>
	</tr>
	<tr>
		<td class="labelField" align="left" width="20%" >Group Supervisor&nbsp;</TD>
		<td  align="left" width="80%" valign="top" colspan="2"><font color="red" class="labelMandatory">*</font><html:select property="groupsupervisors" styleClass="dropdownlist" style="width:250px" tabindex="4">
			<option value="">Select Group Supervisor</option>
			<!-- <option value="<c:out value="${GroupSupervisor}"/>"><c:out value="${GroupSupervisor}"/></option>-->
			<html:optionsCollection name="Supervisors" value="SupervisorID" label="SupervisorName"/>
			</html:select>&nbsp;&nbsp;<font color="red" size="2"><i><html:errors property="GroupsupervisorsError"></html:errors></i></font></td>	
	</tr>
		<tr>
		<td class="labelField" align="left">Status&nbsp;</td>
		<td align="left" colspan="2">&nbsp;&nbsp;<html:select property="groupstatus"style="width:80px" styleClass="dropdownlist">
		<html:option value="0">Active</html:option>
		<html:option value="1">Inactive</html:option>
		</html:select>&nbsp;&nbsp;<font color="red" size="2"><i><html:errors property="GroupStatusError"></html:errors></i></font>
		</td>
	</tr> 
	<tr>
	<td class="labelField" align="left" valign="top">Employees</td>
	<td  align="left" valign="top" >
	<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >		
	<tr>
		<td width="44%" valign="top">&nbsp;<html:select property="employees" size="6" styleClass="multiplelist" tabindex="5">
			<html:optionsCollection name="Employee" value="empid" label="name"/>
		</html:select>&nbsp;
		</td>
		 <td  align="left" valign="middle" width="6%">
        <p>
              <input type="button" name="btnRight2" value=">>" style="width : 30px"class="linkbutton_arrow" onClick="return moveOriginIndstTypes2()" tabindex="6">      
           <input type="button" name="btnLeft2" value="<<" " style="width : 30px"class="linkbutton_arrow" onClick="return moveDestIndstTypes2()" tabindex="8">
           
         </p>
       </td>
        <td width="1%" valign="top"><font color="red" class="labelMandatory">*</font></td>
        <td><html:select property="destemployee" size="6" multiple="true"styleClass="multiplelist" tabindex="7">
        <logic:present name="GroupEmployees">
        <html:optionsCollection name="GroupEmployees" value="empid" label="name"/>
        </logic:present>
        </html:select>
        </td>  
   <td align="left" width="35%" valign="top"><font color="red" size="2"><i><html:errors property="GroupEmployeeError"/></i></font></td>
   </tr>
</table>
</td>
    
</tr>

<tr>
<td>&nbsp;</td>
	<td align="left" colspan="3">
	<table width="20%" >
	<tr>
	<td height="60px">&nbsp;<button type="button" onClick="EditGroup();" accesskey="u" class="linkbutton_background" tabindex="9"/><u>U</u>pdate</button></td>
	<td>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" accesskey="c" onClick="fnGoBack();" class="linkbutton_background" tabindex="10"><u>C</u>ancel</button>
	</td>
</tr>
</table>
</td>
</tr>
</table>
</html:form>
</body>
</html:html>