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
 window.location="./VIMSReportsLookUpDispatchAction.do?param=customerReports&pageId=customerReports";
}
function generateExcelFile() {

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
<body>
<br>
	<table width="100%" border="0">	
	<tr valign="top">
<td align="center" class="heading_blue" colspan="3"><font size="2">Customer Report</font></td>
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
<%int maximumRows=((ArrayList)session.getAttribute("customerReports")).size(); 
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
<ec:table items="customerReports" cellpadding="2" cellspacing="2" border="0" action="./VIMSReportsLookUpDispatchAction.do?param=generateCustomerReports" imagePath="./images/*.gif" filterable="false" width="100%" rowsDisplayed="10" maxRowsDisplayed="${maxRows}" var="serRes">
 <ec:row highlightRow="true"> 
 <c:if test="${viewType eq 'summary'}">
	 <ec:column property="Customer_Name" title="Customer Name" width="17.5%"/>
	 <ec:column property="Application_Name" title="Application Name" width="15%" />
	 <ec:column property="Status" title="Application Status" width="15%"/>
	 <ec:column property="Start_Date" title="Start Date" width="12.5%"/>
	 <ec:column property="End_Date" title="End Date" width="12.5%"/>
	 <ec:column property="Application_Owner" title="Application Owner" width="15%"/>
	 <ec:column property="No_of_Issues" title="No Of Issues" width="15%"/>	 
 </c:if>
 <c:if test="${viewType eq 'detailed'}">
	 <ec:column property="Customer_Name" title="Customer&nbsp;&nbsp;Name" width="10%"/>
	 <ec:column property="Application_Name" title="Application&nbsp;&nbsp;Name" width="10%" />
	 <ec:column property="Status" title="Application&nbsp;&nbsp;Status" width="10%"/>
	 <ec:column property="Start_Date" title="Start&nbsp;&nbsp;Date" width="10%"/>
	 <ec:column property="End_Date" title="End&nbsp;&nbsp;Date" width="15%"/>
	 <ec:column property="Application_Owner" title="Application&nbsp;&nbsp;Owner" width="10%"/>
	 <ec:column property="No_of_Issues" title="No&nbsp;Of&nbsp;Issues&nbsp;&nbsp;&nbsp;" width="10%"/>
	 <ec:column property="Support_Manager" title="Support&nbsp;&nbsp;Manager" width="10%"/>
	 <ec:column property="Group" title="Group" width="10%"/>
	 <ec:column property="Group_Supervisor" title="Group&nbsp;&nbsp;Supervisor" width="10%"/>	
	 <ec:column property="Members" title="Group&nbsp;&nbsp;Members" width="10%"/>
 </c:if>
	 
  </ec:row>
</ec:table>
</td>
</tr>
<tr>
 	<td colspan="3" align="center">
 	<html:form action="generateIssueExcelFile.do">
 		<input type="hidden" name="hiddenViewType" value='<%=(String)request.getAttribute("viewType")+"Customer"%>'/>
		<button type="button" onclick="printList()" class="linkbutton_background" accesskey="p"><u>P</u>rint</button>&nbsp;&nbsp;<button type="button"  class="linkbutton_background" accesskey="x" style="width:150px" onclick="generateExcelFile()">Export to E<u>x</u>cel</button>&nbsp;&nbsp;<button type="button" onclick="goHome();" class="linkbutton_background" accesskey="b"><u>B</u>ack</button>
	</html:form>
	</td>	
</tr>
</table>
</body>
</html>