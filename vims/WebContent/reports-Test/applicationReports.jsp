<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*" %>
<html>
<head>
<script language="javascript" src="./script-test/advPopCalender.js" type="text/javascript"></script>

<script>

function resetForm() {

   document.getElementById("status").selectedIndex=0;
   document.getElementById("appName").selectedIndex=0;
   document.getElementById("fromDate").value="";
   document.getElementById("toDate").value="";
   document.getElementById("dateId").style.display="block";
   
}
function addOption(selectbox,text,value)
	{
		var optn = document.createElement("OPTION");
		optn.text = text;
		optn.value = value;
		selectbox.options.add(optn);
	}
function removeOptions(selectBox)
	{	
		var len=selectBox.length;	
		for(var j=len;j>1;j--)		
		{
			selectBox.options[j] =null;
			
		}
}
function disableFunction()
{	
	var appl=document.getElementById('applId').value;
	if(appl=="selAppl" || appl=="All")
	{		
		document.getElementById('todate').value="";
		document.getElementById('frmdate').value="";
		//alert(document.getElementById('todate').value)
		//alert(document.getElementById('frmdate').value)		
		document.getElementById('dateId').style.display="block"
	}	
	else
	{		
		document.getElementById('dateId').style.display="none"
	}
}
function submitForm()
{
  //alert("Form Submitted");
  if(document.getElementById("dateId").style.display=='none') {
     document.getElementById("fromDate").value="";
   document.getElementById("toDate").value="";
  }
   var formObj=document.forms[0]; 
  formObj.action='./VIMSReportsLookUpDispatchAction.do?param=displayApplicationReports';
  formObj.submit();
}
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
function fnGetApplByStatus()
	{  	  
		removeOptions(document.getElementById('applId'));		
		x=GetXmlHttpObject();	    
		if (x==null)
		  {
			  alert ("Your browser does not support AJAX!");
			  return
		  } 			 
		var status=document.getElementById('statusId').value;			
		var url="VIMSReportsLookUpDispatchAction.do?param=getApplicationsByStatus&strStatus="+status;
		x.open("POST",url,false);
		x.send(null);		
		if(x.readyState==4)
		 {
		 	fnSetApplByStatus(x.responseText);
		 }
	}
function fnSetApplByStatus(responseText) 
{    
	if (responseText!=null){
			var strList=responseText.split(";");
			for (var i=0; i<strList.length-1;i++)
			{
				var idname=strList[i].split(":");
				var id=idname[0];
				var name=idname[1];
 				addOption(document.getElementById("applId"), name, id);
			}
		}
}

function goHome() {
 window.location="./reportsHome.do?pageId=Reports&menuId=Report";
}		
</script>
</head>	
<br>
<table border="0" width="100%">		
<html:form action="VIMSReportsLookUpDispatchAction.do" focus="status">
<tr>
	<td class="labelField" align="left">Status</td>
	<td>&nbsp;&nbsp;<html:select property="status" style="width:80px" styleId="statusId"  styleClass="dropdownlist" onchange="fnGetApplByStatus();">		    
	    <html:option value="All">All</html:option>
	    <html:option value="Active">Active</html:option>
		<html:option value="Inactive">Inactive</html:option>
	    </html:select>
	</td>    
</tr>	 
<tr>
	<td class="labelField" align="left" width="20%">Application Name</td>
	<td width="80%">&nbsp;&nbsp;<html:select property="appName" styleId="applId" styleClass="textbox_default" tabindex="1" style="width:250px" onchange="disableFunction();">
		<html:option value="All">All Applications</html:option>
		<html:optionsCollection name="ApplicationListInReports" value="id" label="name"/> 			
		</html:select>
	</td>
</tr>		

<tr id="dateId" style="display:block">
	  <td class="labelField" align="left">Date Range From
	  <td  align="left">&nbsp;&nbsp;<html:text property="fromDate" styleClass="textbox_default" readonly="true" styleId="frmdate" style="width:80px"></html:text>
	  <script>document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].fromDate,window.document.forms[0].fromDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
	     </script>
	  <font class="labelField">&nbsp;&nbsp;To&nbsp;</font><html:text property="toDate" styleId="todate" readonly="true"  styleClass="textbox_default" style="width:80px"></html:text>
	  <script>document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].toDate,window.document.forms[0].toDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');</script></td>
</tr>
<tr>
<td class="labelField" align="left">View By</td>
<td class="labelField" align="left">&nbsp;&nbsp;<html:radio property="viewType" value="summary">Summary</html:radio>&nbsp;&nbsp;<html:radio property="viewType" value="detailed">Detailed</html:radio></td>
</tr>
	<tr>
	<td colspan="2" height="10px"></td>
	</tr>
	<tr>
	<td>&nbsp;</td>
	<td align="left">&nbsp;&nbsp;<button type="button" class="linkbutton_background" accesskey="g" onclick="submitForm();"><u>G</u>enerate</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="reset" class="linkbutton_background" accesskey="r" onclick="resetForm()"><u>R</u>eset</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="linkbutton_background" accesskey="c" onclick="goHome()"><u>C</u>ancel</button></td>
	</tr>	
	<tr>
	<td colspan="2" align="left" class="labelField"><font color="red"><c:out value="${searchresult}"/></font></td>
	</tr>
	
</html:form>
</table>
</html>