<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec"%>
<%@ taglib uri="/WEB-INF/tlds/datetime.tld" prefix="date" %>
<%@page import ="java.util.*"%>
<html>
<head>
<script>

function goHome() {
  window.location="./VIMSReportsLookUpDispatchAction.do?param=SLAReports&pageId=ApplicationReports&menuId=Report";
}

function generateExcelFile()
{
	var formObj=document.forms[0];
	formObj.action='./generateIssueExcelFile.do?param=generateFile&type=SLA';
	formObj.submit();   
}
function printList() {
  
  if (navigator.appName == "Microsoft Internet Explorer")
	{
	 	window.open('./reports-Test/printReport.jsp?type=SLA', "VIMS", 'toolbar=no, status=no, left=360, top=250, scrollbars=yes, resize=yes');
	}
	else if (navigator.appName == "Netscape") 
	{
       window.open("./reports-Test/printReport.jsp?type=SLA", "VIMS",  'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=no,resizable=0,width=350,height=300,top=250,left=360');		
	}
}
</script>
</head>
<body>
<br>
<table width="100%" border="0">
<tr valign="top">
<td align="center" class="heading_blue" colspan="3"><font size="2">SLA Report</font></td>
</tr>
<tr valign="top">
	<td align="left" class="labelField" width="20%"><b>Report Date </b></td>
	<td align="left" class="labelField" width="2%"><b>:</b></td>
	<td align="left" class="labelField"><date:format pattern="MM/dd/yyyy"><date:currentTime/></date:format></td>
</tr>
<%String searchcri=(String)session.getAttribute("searchCriteria");
int index=searchcri.indexOf(':');
String strReportsbased=searchcri.substring(index+1,searchcri.length());%>
<tr valign="top">
	<td align="left" class="labelField"><b>Report based on </b></td>
	<td align="left" class="labelField"><b>:</b></td>
	<td align="left" class="labelField"><%=strReportsbased%></td>	
</tr>
<%int maximumRows=((ArrayList)session.getAttribute("SLAReportsList")).size(); 
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
<td colspan="3" align="left">
<ec:table items="SLAReportsList" cellpadding="2" cellspacing="2" border="0" action="./VIMSReportsLookUpDispatchAction.do?param=displaySLAReports" imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10" maxRowsDisplayed="${maxRows}" var="serRes">
 <ec:row highlightRow="true"> 
	 <ec:column property="Application_Name" title="Application&nbsp;&nbsp;Name" width="35%"/>
	  <ec:column property="Total_Issues" title="Total&nbsp;&nbsp;Issues" width="15%"/>	
	  <ec:column property="Issues_within_SLA" title="Issues&nbsp;within&nbsp;SLA" width="18%"/>	  
	  <ec:column property="Issues_out_of__SLA" title="Issues&nbsp;out&nbsp;of&nbsp;SLA" width="18%"/>	
	  <%if(request.getAttribute("isAcceptanceLevel")!=null && ((String)request.getAttribute("isAcceptanceLevel")).equalsIgnoreCase("display")){ %>
	  <ec:column property="Percent_of_Issues" title="Indicator" width="19%"/>
	  <%}%>	 
  </ec:row>
</ec:table>
</td>
</tr>
<tr>
 	<td align="center" colspan="3">
 	<html:form action="generateIssueExcelFile.do">
		<button type="button" onclick="printList()" class="linkbutton_background" accesskey="P"><u>P</u>rint</button>&nbsp;&nbsp;<button type="button"  class="linkbutton_background" accesskey="x" style="width:150px" onclick="generateExcelFile()">Export to E<u>x</u>cel</button>&nbsp;&nbsp;<button type="button" onclick="goHome();" class="linkbutton_background" accesskey="b"><u>B</u>ack</button>
	</html:form>
	</td>	
</tr>
<%if(request.getAttribute("isAcceptanceLevel")!=null && ((String)request.getAttribute("isAcceptanceLevel")).equalsIgnoreCase("display")){ %>
<tr>
	<td align="center" colspan="3"><img src='./images/Green.gif'width="6" height="6"></img>&nbsp;&nbsp;&nbsp;<i><font size="1">Applications having issues between 1 - 10% in out of SLA</font></i>&nbsp;&nbsp;<img src='./images/Yellow.gif' width="6" height="6"></img>&nbsp;&nbsp;<i><font size="1">Applications having issues between 11 - 30% in out of SLA</font></i>&nbsp;&nbsp;<img src='./images/Red.gif' width="6" height="6"></img>&nbsp;&nbsp;<i><font size="1">Applications having issues 31% and above in out of SLA</font></i>
	</td>
</tr>
<%} %>

</table>
</body>
</html>