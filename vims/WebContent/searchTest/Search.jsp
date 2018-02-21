<%--  
  FileName	:	Search.jsp
  
  copyright (c) 2008 Vertex.
  All rights reserved.
  
  Description	: This jsp is used to display different values based on the constraints. 
                 
  Developed by : Vertex Computer Systems.
  
  Change History:
 
  Revision No.			Date		  @author		Description
    1.0				Friday 21,2008	  Geeta.M	   Initial Version.--%>


<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec"%>
<%@page import ="java.util.*"%>

<html>

<head>
<script language="javascript" type="text/javascript" src="./script-test/VIMS_Alerts.js"></script>
<script language="javascript" src="./script-test/advPopCalender.js" type="text/javascript"></script>
<script><!--
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

 function funCustApp()
{		
		
	x=GetXmlHttpObject(url);
	alert("----x----"+x);
	//alert(document.getElementById("strCountry").options[document.getElementById("strCountry").selectedIndex].value);
	var url="search.do?ChangeType=Changed&Customer="+document.getElementById("customerName").options[document.getElementById("customerName").selectedIndex].value+"&request_from=ajaxcall";  
	alert("--------url-----"+url);
	if (x==null)
	  {
		  alert (ajaxAlert);
		  return 
	  } 
	
	x.onreadystatechange= function() {
	    if (x.readyState == 4) {
	    alert("--------x.status-----"+x.status);
		if (x.status == 200) {
			stateChanged2(x.responseText);
		} else if (x.status == 204){
		    alert('cant do anything');
		}
	    }
	};
	x.open("POST",url,false);
	x.send(null);
}  

function stateChanged2(responseText) 
{   removeOptionSelected();
	if (responseText!=null)	
	{ 						
		var strStatesList=responseText.split(";");
		for (var i=0; i<strStatesList.length-1;i++)
		{ 
			  //add options
			    var elSel = document.getElementById('applicationName');
				var elOptNew = document.createElement('option');
				elOptNew.text = strStatesList[i];
				elOptNew.value = strStatesList[i];
				try {
				  elSel.add(elOptNew, elOptOld); // standards compliant; doesn't work in IE
				}
				catch(ex) {
				  elSel.add(elOptNew,elSel.selectedIndex); // IE only
				}
		  }
    } 
 
}
function removeOptionSelected()
{
  var elSel = document.getElementById('appNames');
  var i;
  for (i = elSel.length - 1; i>=0; i--) {
 		if (elSel.options[i].value!=' ') {
		  elSel.remove(i);
		}
   }
}
function fnChange()
	{
		var formobj=document.forms[0];
		var fromDate=formobj.fromDate.value;
		var todate=formobj.toDate.value;
		var fdate=Date.parse(fromDate);
		 //alert(fdate);
		var tdate=Date.parse(todate);
		 //alert(tdate);
		var diff_date=fdate-tdate;
 
	 if(diff_date>0)
	  {
		  alert(dateValidation);
		  formobj.fromDate.focus();
		  formobj.fromDate.select();
	}
	 else
	  {
	    document.getElementById("ChangeType").value="Submitted"; 
		//var formobj=document.forms[0];
		//alert(formobj.ChangeType.value); 
 		formobj.action="./search.do";
		formobj.submit(); 
	  }
		//alert("Hai");
		
	}
	function custApplication()
	{
	    document.getElementById("ChangeType").value="Changed"; 
		var formobj=document.forms[0];
		//alert(formobj.ChangeType.value); 
 		formobj.action="./search.do";
 		formobj.submit(); 
	}
	function generateExcelFile()
{
	var formObj=document.forms[1];
	//alert(document.getElementById('hiddenViewType').value)
	formObj.action='./generateIssueExcelFile.do?param=generateFile&type='+document.getElementById('hiddenViewType').value;
	formObj.submit();   
}
--></script> 

<script>
function cursor(text)
{
	trail.innerHTML=text;
	trail.style.visibility="visible";
	trail.style.position="absolute";
	trail.style.left=event.clientX+20;
	trail.style.top=event.clientY;
}

function hidecursor()
{
	trail.style.visibility="hidden";
}
function showmenu(elmnt)
{
		document.getElementById(elmnt).style.visibility="visible";
}
function hidemenu(elmnt)
{
		document.getElementById(elmnt).style.visibility="hidden";
}
</script>
<script type="text/javascript"><!--
function getParameterMap(form) {
    var p = document.forms[form].elements;
    var map = new Object();
    for(var x=0; x < p.length; x++) {
        var key = p[x].name;
        var val = p[x].value;
        
        //Check if this field name is unique.
        //If the field name is repeated more than once
        //add it to the current array.
        var curVal = map[key]; 
        if (curVal) { // more than one field so append value to array
        	curVal[curVal.length] = val;
        } else { // add field and value
        	map[key]= [val];
        }
    }
    return map;
}

function setFormAction(form, action, method) {
	if (action) {
		document.forms[form].setAttribute('action', action);
	}
	
	if (method) {
		document.forms[form].setAttribute('method', method);
	}
	if(document.forms[form].ec_eti!=null)
	{
	  document.forms[form].ec_eti.value='';	
	}
	
}

function onlyNumbers(evt)
		{   
			var e = event || evt; // for trans-browser compatibility    
			var charCode = e.which || e.keyCode;    
			if (charCode > 31 && (charCode < 48 || charCode > 57)) 
			{  
				alert("Only numeric values are accepted"); 
				//document.getElementById("slaMsg").innerHTML="Enter only numerics";
				return false;
			}	
			return true;
		}	
function fnReset()
{
	//alert(document.getElementById('customerName').value)
	document.getElementById('customerName').value=''
	document.getElementById('title').value=''	
	document.getElementById('issueID').value=''
	alert(document.getElementById('issueID').value)
	document.getElementById('applicationName').value=''
	document.getElementById('status').value=''
	document.getElementById('severity').value=''
	document.getElementById('fromDate').value=''
	document.getElementById('toDate').value=''
}
--></script>
</head>
<body><br>
<html:form method="post" action="/search.do" >
<input type="hidden" name="ChangeType" id="ChangeType"/>
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >
<tr>
	<td align="left" width="100%">
		<table border="0" width="100%" align="center" cellspacing="2" cellpadding="0" >
					
			<c:if test="${Role eq 'Admin'}">
				<tr>	       
					<td class="labelField" align="left" width="20%" ><bean:message key="VIMSApplication.CustomerName"/>&nbsp;</TD>
					<td  width="80%" align="left">&nbsp;&nbsp;<html:select property="customerName" size="1" styleClass="dropdownlist" tabindex="1" style="width:250px" onchange="custApplication();">
		 				      <html:option value="">Select Customer</html:option> 
		 				      <html:optionsCollection name="custNames" value="customerID" label="customerName"/> 
				        </html:select> 
					</td>				
				</tr>
		</c:if>
				<tr>	       
					<td class="labelField" width="20%" align="left" ><bean:message key="VIMSApplication.title"/></TD>
					<td  width="80%" align="left">&nbsp;&nbsp;<html:text property ="title" styleClass="textbox_default" tabindex="2" style="width:357px"/></td>				
				</tr>
				<tr>	       
					<td class="labelField" align="left" ><bean:message key="VIMSApplication.issueID"/></TD>
					<td  align="left">&nbsp;&nbsp;<html:text property ="issueID" styleClass="textbox_default" tabindex="3" onkeypress="return onlyNumbers();" maxlength="16" style="width:117px"/></td>				
				</tr>
				<tr>	       
					<td class="labelField" align="left" ><bean:message key="VIMSApplication.appName"/></TD>
					<td  align="left">&nbsp;&nbsp;<html:select property="applicationName" size="1" tabindex="4" styleClass="dropdownlist" style="width:250px">
		 				     <html:option value="">Select Application</html:option> 
		 				     <html:optionsCollection name="appNames" value="applicationID" label="applicationName" />			         
				         </html:select>
				    </td>				
				</tr>
				<tr>
					<td class="labelField" align="left" ><bean:message key="VIMSApplication.status1"/></td> 
					<td  align="left">&nbsp;&nbsp;<html:select property="status" size="1" styleClass="dropdownlist" tabindex="5" style="width:117px">
		 				      <html:option value="">Select Status</html:option> 
		 				     <html:optionsCollection name="status" value="statusID" label="status"/> 
				         </html:select> 									
					</td>
				</tr>
				<tr>
					<td class="labelField" align="left" ><bean:message key="VIMSApplication.severity"/></td>
					<td>&nbsp;&nbsp;<html:select property="severity" size="1" styleClass="dropdownlist" tabindex="6" style="width:117px">
		 				       <html:option value="">Select Severity</html:option> 
		 				       <html:optionsCollection name="severity" value="severityID" label="severity" /> 
				         </html:select>  
					</td>
				</tr>
				<tr>
					<td class="labelField" align="left"><bean:message key="VIMSApplication.dateRangeFrom"/></td>
					      <td  align="left">&nbsp;&nbsp;<html:text property="fromDate" styleClass="textbox_default" style="width:80px"></html:text>

						 <script>
				           document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].fromDate,window.document.forms[0].fromDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
				         </script>
                         <font class="labelField">&nbsp;&nbsp;<bean:message key="VIMSApplication.dateRangeTo"/></font>
					      <html:text property="toDate"  styleClass="textbox_default" style="width:80px" ></html:text>

						  <script>
				             document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].toDate,window.document.forms[0].toDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
				         </script></td>
					</tr>
					<tr height="10px"><td colspan="2"></td></tr>
					<tr>
					<td>&nbsp;</td>
						<td align="left" >
						<table width="15%">
							<tr>
								<td>&nbsp;<button type="button"  class="linkbutton_background" tabindex="9" onClick="fnChange();" name="subButton" accesskey="s"><u>S</u>earch</button></td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;<button type="reset" class="linkbutton_background" tabindex="10" accesskey="r" ><u>R</u>eset</button></td>
							</tr>
						</table>
						</td>
					</tr>
		</table>
	</td>

</tr>

</html:form>
<logic:present name="Record">
<%String searchcri=(String)session.getAttribute("searchCriteria");
System.out.println("-----searchcri-----"+searchcri);
int index=searchcri.indexOf(':');
String strReportsbased=searchcri.substring(index+1,searchcri.length());%>

<tr>
	<td align="left" class="labelField"><b>Search based on :</b><%=strReportsbased%></td>	
</tr>
<%int maximumRows=((ArrayList)session.getAttribute("Record")).size(); 
System.out.println("--------maximumRows------"+maximumRows);
if(maximumRows>50)
{
	pageContext.setAttribute("maxRows",maximumRows);
}
else
{
	pageContext.setAttribute("maxRows","100");
}
%>
<tr>
	<td align="left" width="100%">
     <ec:table items="Record" action="./search.do" imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10" var="serRes">
     <ec:row highlightRow="true"> 
			<ec:column property="Issue_ID"  title="Issue ID" width="15%">
			 <a href="${pageContext.request.contextPath}/ListofIssues.do?methodname=IssueDetails&id=${serRes.Issue_ID}" >${serRes.Issue_ID}</a> 
			</ec:column>
			<ec:column property="Application_Name" title="Application Name" width="30%"/>
			<ec:column property="Severity" title="Severity" width="15%"/>
			<ec:column property="Status" title="Status" width="15%"/>
		    <c:if test="${Role eq 'Admin'}">
		       <ec:column property="Customer_Name" title="Customer Name" width="25%"/>
            </c:if>   		
		</ec:row>
    </ec:table>
  </td>
</tr>  
<html:form action="generateIssueExcelFile.do">
<tr>
<input type="hidden" name="hiddenViewType" value="SearchPage"/>
<td align="center"><button type="button"  class="linkbutton_background" accesskey="x" style="width:150px" onclick="generateExcelFile()">Export to E<u>x</u>cel</button></td>
</tr>
</html:form>
 </logic:present>
<logic:notPresent name="Record">
<tr>
<td><font class="labelField"><c:out value="${MSG}"/></font></td>
</tr>
   
</logic:notPresent>
	
<tr style="height:4px"><td></td></tr>
</table>

  </body>
</html>
