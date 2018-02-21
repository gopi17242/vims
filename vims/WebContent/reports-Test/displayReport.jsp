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
  window.location="./issueReportForm.do?param=getIssueReportHomePage&pageId=ApplicationPool&menuId=Report";
}

function generateExcelFile()
{	
	var formObj=document.forms[0];
	formObj.action='./generateIssueExcelFile.do?param=generateFile&type='+document.getElementById('hiddenViewType').value;
	formObj.submit();   
}
function printList() {
  
  if (navigator.appName == "Microsoft Internet Explorer")
	{
	 	window.open('./reports-Test/printReport.jsp?type='+document.getElementById('hiddenViewType').value, "VIMS", 'toolbar=no, status=no, left=360, top=250, scrollbars=yes, resize=yes');
	}
	else if (navigator.appName == "Netscape") 
	{
       window.open("./reports-Test/printReport.jsp?type="+document.getElementById('hiddenViewType').value, "VIMS",  'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=no,resizable=0,width=350,height=300,top=250,left=360');		
	}
}
</script>
</head>
<body>
<br>
<table width="100%" border="0">
<tr valign="top">
<td align="center" class="heading_blue" colspan="3"><font size="2">Issue Report</font></td>
</tr>
<tr valign="top">
	<td align="left" class="labelField" width="20%"><b>Report Date </b></td>
	<td align="left" class="labelField" width="2%"><b>:</b></td>
	<td align="left" class="labelField" width="78%"><date:format pattern="MM/dd/yyyy"><date:currentTime/></date:format></td>
</tr>
<%String searchcri=(String)session.getAttribute("searchCriteria");
int index=searchcri.indexOf(':');
String strReportsbased=searchcri.substring(index+1,searchcri.length());%>
<tr valign="top">
	<td align="left" class="labelField"><b>Report based on </b></td>
	<td align="left" class="labelField"><b>:</b></td>
	<td align="left" class="labelField"><%=strReportsbased%></td>	
</tr>
<%int maximumRows=((ArrayList)session.getAttribute("IssueReportList")).size(); 
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
<td colspan="3">
<ec:table items="IssueReportList" cellpadding="2" cellspacing="2" border="0" action="./generateIssueReport.do?param=generateIssueReport" imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10" maxRowsDisplayed="${maxRows}" var="serRes">

 <ec:row highlightRow="true">
 <c:if test="${viewType eq 'summary'}">
 	 <ec:column property="Issue_Id" title="Issue&nbsp;&nbsp;ID" width="12.5%"/>
	 <ec:column property="Application_Name" title="Application Name"  width="20%"/>
	 <ec:column property="Title" title="Title"  width="15%"/>
	 <ec:column property="Status" title="Status" width="12.5%"/>
	 <ec:column property="Issue_Type" title="Severity" width="12"/>
	 <ec:column property="Posted_Date" title="Created Date" width="13%"/>
	 <ec:column property="Due_Date" title="Due Date" width="13%"/>    
	 <ec:column property="DetailView" title="Detailed View" width="15%"/>
 </c:if>
 <c:if test="${viewType eq 'detailed'}">
 	 <ec:column property="Issue_Id" title="Issue ID" width="13%"/>
	 <ec:column property="Application_Name" title="Application Name"  width="20%"/>
	 <ec:column property="Title" title="Title"  width="25%"/>
	 <ec:column property="Status" title="Status" width="20%"/>
	 <ec:column property="Description" title="Description" width="15%"/>
	 <ec:column property="Issue_Type" title="Severity" width="13%" />
	 <ec:column property="Priority" title="Priority" width="13%"/>
	 <ec:column property="Posted_By" title="Posted By" width="13%"/>
	 <ec:column property="Posted_Date" title="Created Date" width="13%"/>
	 <ec:column property="Due_Date" title="Due Date" width="13%"/>
     <ec:column property="Issue_Completion_Date" title="Completion Date" width="13%"/>	 
	 <ec:column property="Closed_Date" title="Closed Date" width="13%" />
	 <ec:column property="DetailView" title="Detailed View" width="15%"/>
 </c:if>
  </ec:row>
</ec:table>
</td>
</tr>
<tr>
 	<td align="center"  colspan="3"> 
 	<html:form action="generateIssueExcelFile.do">
 	<input type="hidden" name="hiddenViewType" value='<%=(String)request.getAttribute("viewType")+"Issues"%>'/>
		<button type="button" onclick="printList()" class="linkbutton_background" accesskey="p" tabindex="1"><u>P</u>rint</button>&nbsp;&nbsp;<button type="button"  class="linkbutton_background" accesskey="x" style="width:150px" onclick="generateExcelFile()" tabindex="2">Export to E<u>x</u>cel</button>&nbsp;&nbsp;<button type="button" onclick="goHome();" class="linkbutton_background" accesskey="b" tabindex="3"><u>B</u>ack</button>
	</html:form>
	</td>	
</tr>
</table>
</body>
</html>