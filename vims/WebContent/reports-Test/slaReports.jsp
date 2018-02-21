<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*" %>
<html>
<head>
<script language="javascript" src="./script-test/advPopCalender.js" type="text/javascript"></script>
<script>
function submitForm()
{
  var formObj=document.forms[0]; 
  formObj.action='./VIMSReportsLookUpDispatchAction.do?param=displaySLAReports';
  formObj.submit();
}
function goHome() {
 window.location="./reportsHome.do?pageId=Reports&menuId=Report";
}
</script>
</head>
<body>
<br>
<table border="0" width="100%">		
<html:form action="VIMSReportsLookUpDispatchAction.do" focus="status">
<tr>
	<td class="labelField" align="left" width="20%">Status</td>
	<td width="80%">&nbsp;&nbsp;<html:select property="status" style="width:80px" styleId="statusId"  styleClass="dropdownlist">		    
	    <html:option value="All">All</html:option>
	    <html:option value="Active">Active</html:option>
		<html:option value="Inactive">Inactive</html:option>
	    </html:select>
	</td>    
</tr>
<tr>
	<td class="labelField" align="left">SLA Type</td>
	<td>&nbsp;&nbsp;<html:select property="sla" style="120px" styleId="statusId"  styleClass="dropdownlist" >		    
	    <html:option value="All">All</html:option>
	    <html:option value="withinSLA">Applications within SLA</html:option>
		<html:option value="outofSLA">Applications out of SLA</html:option>
	    </html:select>
	</td>    
</tr>	
<tr>
	  <td class="labelField" align="left">Date Range From
	  <td  align="left">&nbsp;&nbsp;<html:text property="fromDate" styleClass="textbox_default" readonly="true" styleId="frmdate" style="width:80px"></html:text>
	  <script>document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].fromDate,window.document.forms[0].fromDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
	     </script>
	  <font class="labelField">&nbsp;&nbsp;To&nbsp;</font><html:text property="toDate" styleId="todate" readonly="true"  styleClass="textbox_default" style="width:80px"></html:text>
	  <script>document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].toDate,window.document.forms[0].toDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');</script></td>
</tr>
	<tr>
	<td colspan="2" height="10px"></td>
	</tr>
	<tr>
	<td>&nbsp;</td>
	<td align="left">&nbsp;&nbsp;<button type="button" class="linkbutton_background" accesskey="g" onclick="submitForm();"><u>G</u>enerate</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="reset" class="linkbutton_background" accesskey="r" ><u>R</u>eset</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="linkbutton_background" accesskey="c" onclick="goHome()"><u>C</u>ancel</button></td>
	</tr>	
	<tr>
	<td colspan="2" align="left" class="labelField"><font color="red"><c:out value="${searchresult}"/></td></tr>
</html:form>
</table> 
</body>
</html>
