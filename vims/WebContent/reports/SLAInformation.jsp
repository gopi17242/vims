<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/cewolf.tld" prefix="cewolf" %>  
<%@page import="com.vertex.VIMS.test.reports.DSProducer.*" %>
<%@page import="java.util.*" %>

<html:html>
<head>
<script language="javascript" type="text/javascript" src="./script-test/datetimepicker.js"></script>
<script>
	function radioclick()
	{
		var formObj=document.forms[0];
		formObj.action="./reports.do?reportType=getSLAInformationReports";		
		formObj.submit();		
	}
	 function generateReport(reportType) 
	 {
	 	 //alert('generateReport');
	 	 document.getElementById('exportType').value=reportType;
	 	 //alert(document.getElementById('exportType').value);
		 var formObj=document.forms[0];
		 formObj.action="./reportsGenerate.do?reportType=generateSLAInformationReports&export="+reportType;
		 formObj.submit();
	 }
</script>
</head>
<%
int escalatedApplCount=((ArrayList)session.getAttribute("ApplicationEscalated")).size();
int notescalatedApplCount=((ArrayList)session.getAttribute("ApplicationNotEscalated")).size();
%>
<body>
<jsp:useBean id="pieChartDisplay" class="com.vertex.VIMS.test.reports.DSProducer.VIMSReportsSLAPieChartDatasetProducer" scope="page"/>
<jsp:useBean id="simpleChartDisplay" class="com.vertex.VIMS.test.reports.DSProducer.VIMSReportsSLASimpleDatasetProducer" scope="page" />
<html:form action="reports.do&method=" method="post">
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >
<tr>
	<td  align="left"  class="labelField" width="20%">Status</td>
	<td>
	<html:radio property="status" value="active" onclick="javascript:radioclick();"><font class="labelField">Active</font></html:radio>&nbsp;&nbsp;&nbsp;&nbsp;
	<html:radio property="status" value="inactive" onclick="javascript:radioclick();"><font class="labelField">Inactive</font></html:radio>
	</td>	
</tr>
<%if(escalatedApplCount!=0 || notescalatedApplCount!=0){ %>
<tr>
	<td  align="left"  class="labelField"  width="20%">Display</td>
	<td><html:radio property="chartType" value="pie3D" onclick="javascript:radioclick();" ><font class="labelField">Pie Chart</font></html:radio>
	<html:radio property="chartType" value="verticalbar3D" onclick="javascript:radioclick();"><font class="labelField">Bar Graph</font></html:radio>
	</td>
</tr>
<tr>
<td colspan="2">
<table border="0"width="60%" align="left">
<logic:present name="strChartType">
<c:set var="graphType" value="${strChartType}"/>
<c:if test="${graphType eq 'pie3D'}">
	<cewolf:chart id="display" type="pie" >
		<cewolf:data> 
			<cewolf:producer id="pieChartDisplay"/>
        </cewolf:data>
	</cewolf:chart>	
	<cewolf:img chartid="display" renderer="/cewolf" width="400" height="300" >
	<cewolf:map tooltipgeneratorid="pieChartDisplay"/>
	<cewolf:map linkgeneratorid="pieChartDisplay"/>
	</cewolf:img>
	<cewolf:imgurl chartid="display" renderer="/cewolf"  width="400" height="300" var="imagePath"/>
</c:if>	
<c:if test="${graphType eq 'verticalbar3D'}">
	<cewolf:chart     id="display"     title="" type="stackedverticalbar" xaxislabel="SLA Status" yaxislabel="No of Applications">
		<cewolf:data> 
		  	<cewolf:producer id="simpleChartDisplay"/> 
		</cewolf:data>
	 </cewolf:chart>
	 <cewolf:img chartid="display" renderer="/cewolf" width="400" height="300" >
	 
	 <cewolf:map tooltipgeneratorid="simpleChartDisplay"/>
	 <cewolf:map linkgeneratorid="simpleChartDisplay"/>
	 </cewolf:img>
	 <cewolf:imgurl chartid="display" renderer="/cewolf"  width="400" height="300" var="imagePath"/>
 </c:if>
</logic:present>
</table>
<%String path=(String)pageContext.getAttribute("imagePath"); %>
<input type="hidden" name="imgPath" value="<%=path%>">
<input type="hidden" name="export" value="" id="exportType"> 
</td>
<td valign="top">
<table border="0" width="100%">
<tr>
<td align="left" class="heading_blue" valign="top" colspan="2">Applications out of SLA&nbsp;(<%=((ArrayList)session.getAttribute("ApplicationEscalated")).size()%>)<td>
</tr>
<tr>
<td colspan="2">
<logic:present name="ApplicationEscalated">
<select size="5" style="width:350px" class="dropdownlist">
<c:forEach items="${ApplicationEscalated}" var="applEscalate">
<option value="${applEscalate}"><c:out value="${applEscalate}"/></option>
</c:forEach>
</select>
</logic:present>
<logic:notPresent name="ApplicationEscalated">
<c:out value="No Applications is within SLA" />
</logic:notPresent>
</td>
</tr>

<tr>
<td colspan="2" height="10px">
</td>
</tr>
<tr>
<td align="left" class="heading_blue" valign="top" colspan="2">Applications within SLA&nbsp;(<%=((ArrayList)session.getAttribute("ApplicationNotEscalated")).size()%>)<td>
<tr>
<tr>
<td colspan="2">
<logic:present name="ApplicationNotEscalated">
<select size="5" style="width:350px" class="dropdownlist">
<c:forEach items="${ApplicationNotEscalated}" var="applNotEscalate">
<option value="${applNotEscalate}"><c:out value="${applNotEscalate}"/></option>
</c:forEach>
</select>
</logic:present>
<logic:notPresent name="ApplicationNotEscalated">
<c:out value="No Applications is within SLA" />
</logic:notPresent>
</td>
</tr>
</table>
</td>
</tr>
<tr>
<td colspan="2">
<table border="0" width="100%" align="center">
<tr>
<td align="center"><button type="button" name="pdf"   class="linkbutton_Reports" accesskey="P"onclick="generateReport('Export to PDF')">Export To <u>P</u>DF</button>&nbsp;&nbsp;&nbsp;&nbsp;
<button type="button" name="excel"  class="linkbutton_Reports" accesskey="E" onclick="generateReport('Export to EXCEL')">Export To <u>E</u>xcel</button></td>
</tr>
</table>
</td>
</tr>
<%}else{ %>
<tr>
<tr>
	<td class="heading_blue" colspan="2">No Records found</td>	
</tr>
<%} %>
</table>
</html:form>
</body>
</html:html>

