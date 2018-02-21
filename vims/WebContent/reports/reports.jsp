<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/cewolf.tld" prefix="cewolf" %>  
<%@page import="java.util.*" %>
<html:html>
<head>
<script language="javascript" type="text/javascript" src="./script-test/datetimepicker.js"></script>
<script>
	function radioclick()
	{
		var formObj=document.forms[0];
		formObj.action="./reports.do?reportType=getApplicationIssuesRecords";		
		formObj.submit();		
	}
	 function generateReport(reportType) 
	 {
	 	 document.getElementById('exportType').value=reportType;
	 	 alert(document.getElementById('status').value)
	 	  var formObj=document.forms[0];
		 formObj.action="./reportsGenerate.do?reportType=generateApplicationIssuesRecords";
		 formObj.submit();
	 }
	
	
</script>
</head>
<body>
<jsp:useBean id="pieChartDisplay" class="com.vertex.VIMS.test.reports.DSProducer.VIMSReportsPieChartDatasetProducer" scope="page"/>
<jsp:useBean id="simpleChartDisplay" class="com.vertex.VIMS.test.reports.DSProducer.VIMSReportsSimpleDatasetProducer" scope="page" />
<html:form action="reports.do&method=" method="post" >
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >
<tr>
	<td  align="left"  class="labelField" colspan="2">Status&nbsp;&nbsp;	
	<input type="radio" name="status" value="active"  checked onclick="javascript:radioclick();" class="labelField">Active
	<input type="radio" name="status" value="inactive" onclick="javascript:radioclick();" class="labelField">Inactive
	</td>	
</tr>
<tr>
	<td  align="left"  class="labelField" colspan="2">Display&nbsp;&nbsp;
	<html:radio property="chartType" value="pie3D" onclick="javascript:radioclick();" styleClass="labelField">Pie Chart</html:radio>
	<html:radio property="chartType" value="verticalbar3D" onclick="javascript:radioclick();" styleClass="labelField">Bar Graph</html:radio>
	</td>	
</tr>
<tr>
<td align="left" width="60%" colspan="2">
<table border="0" width="100%" align="left" valign="top">
<logic:present name="strChartType">
<c:set var="graphType" value="${strChartType}"/>
<c:if test="${graphType eq 'pie3D'}">
	<cewolf:chart id="display" type="pie" title="" >
		<cewolf:data> 
			<cewolf:producer id="pieChartDisplay"/>
        </cewolf:data>
	</cewolf:chart>	
	<cewolf:img chartid="display" renderer="/cewolf" width="600" height="600" >
	<cewolf:map tooltipgeneratorid="pieChartDisplay"/>
	<cewolf:map linkgeneratorid="pieChartDisplay"/>
	</cewolf:img>
</c:if>	
<c:if test="${graphType eq 'verticalbar3D'}">
	<cewolf:chart     id="display" title="" type="stackedhorizontalbar" xaxislabel="Application" yaxislabel="No of Issues">
		<cewolf:data> 
		  	<cewolf:producer id="simpleChartDisplay"/> 
		</cewolf:data>
	 </cewolf:chart>
	 <cewolf:img chartid="display" renderer="/cewolf" width="600" height="600" >	 
	 <cewolf:map tooltipgeneratorid="simpleChartDisplay"/>
	 <cewolf:map linkgeneratorid="simpleChartDisplay"/>
	 </cewolf:img>
 </c:if>
<cewolf:imgurl chartid="display" renderer="/cewolf"  width="500" height="400" var="imagePath"/>
</logic:present>
</table>
<%String path=(String)pageContext.getAttribute("imagePath"); %>
<input type="hidden" name="imgPath" value="<%=path%>">
<input type="hidden" name="export" value="" id="exportType"> 
</td>
</tr>
<%ArrayList alist = (ArrayList)session.getAttribute("ApplicationsIdAndNames");
if(alist.size()!=0){%>
<tr>
<td>
<table border="0" width="100%" align="left">
<tr>
<td width="33%"></td>
<td align="center"><button type="button" name="pdf"  class="linkbutton_Reports" accesskey="P" onclick="generateReport('Export to PDF')" >Export To <u>P</u>DF</button>&nbsp;&nbsp;</td>
<td align="center">&nbsp;&nbsp;<button type="button" name="excel" class="linkbutton_Reports" accesskey="E" onclick="generateReport('Export to EXCEL')" >Export To <u>E</u>xcel</button></td>
<td width="57%"></td>
</tr>
</table>
</td>
</tr>
<%} %>
</table>
</html:form>
</body>
</html:html>