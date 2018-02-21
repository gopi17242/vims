<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/cewolf.tld" prefix="cewolf" %>  
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
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
	 	 //alert(document.getElementById('exportType').value)
	 	  var formObj=document.forms[1];
		 formObj.action="./reportsGenerate.do?reportType=generateApplicationIssuesRecords&export="+document.getElementById('exportType').value;
		 formObj.submit();
	 }	
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
		
		document.forms[form].ec_eti.value='';
	}	
</script>
</head>
<%
HashMap hashMap = (HashMap)session.getAttribute("ApplicationsIdAndNames");
int [] IssuesList=(int[])hashMap.get("IssuesList");
String [] ApplList=(String[])hashMap.get("ApplicationNames");
int ApplCount=ApplList.length;
HashMap ReportsList=null;
ArrayList alist=new ArrayList();
for(int i=0;i<IssuesList.length;i++)
{
	ReportsList=new HashMap();
	ReportsList.put("IssuesList",IssuesList[i]);
	ReportsList.put("ApplicationNames",ApplList[i]);
	alist.add(ReportsList);
}
session.setAttribute("IssuesinAppl",alist);
%>
<body>
<jsp:useBean id="pieChartDisplay" class="com.vertex.VIMS.test.reports.DSProducer.VIMSReportsPieChartDatasetProducer" scope="page"/>
<jsp:useBean id="simpleChartDisplay" class="com.vertex.VIMS.test.reports.DSProducer.VIMSReportsSimpleDatasetProducer" scope="page" />
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >
<html:form action="reports.do&method=" method="post" >
<tr>
	<td  align="left"  class="labelField" width="12%">Status</td>
	<td width="88%">
	<html:radio property="status" value="active" onclick="javascript:radioclick();" ><font class="labelField">Active</font></html:radio>&nbsp;&nbsp;&nbsp;&nbsp;
	<html:radio property="status" value="inactive" onclick="javascript:radioclick();"><font class="labelField">Inactive</font></html:radio>
	</td>

</tr> 
<tr>
	<td  align="left"  class="labelField">Display</td>
	<td>
	<html:radio property="chartType" value="pie3D" onclick="javascript:radioclick();"><font class="labelField">Pie Chart</font></html:radio>
	<html:radio property="chartType" value="verticalbar3D" onclick="javascript:radioclick();"><font class="labelField">Bar Graph</font></html:radio>
	<html:radio property="chartType" value="list" onclick="javascript:radioclick();"><font class="labelField">List</font></html:radio>
	</td>	
</tr>
</html:form>	
<%if(ApplList.length!=0){
if(ApplList.length>50)
{
	pageContext.setAttribute("maxRows",ApplList.length);
}
else
{
	pageContext.setAttribute("maxRows","100");
}
%>
<tr>
<td align="left"  colspan="2">
<table border="0" width="100%" align="left" >
<tr>
<td width="12%">&nbsp;</td><font class="heading_blue"><c:out value="Total number of Issues : ${TotalIssuesCount}"></c:out></font>
<td align="left" width="88%"">
<logic:present name="strChartType">
<c:set var="graphType" value="${strChartType}"/>
<c:if test="${graphType eq 'pie3D'}">
	<cewolf:chart id="display" type="pie" title="" >
		<cewolf:data> 
			<cewolf:producer id="pieChartDisplay"/>
        </cewolf:data>
	</cewolf:chart>	
	<cewolf:img chartid="display" renderer="/cewolf" width="500" height="500" >
	<cewolf:map tooltipgeneratorid="pieChartDisplay"/>
	<cewolf:map linkgeneratorid="pieChartDisplay"/>
	</cewolf:img>
	<cewolf:imgurl chartid="display" renderer="/cewolf"  width="500" height="500" var="imagePath"/>
</c:if>	
<c:if test="${graphType eq 'verticalbar3D'}">
	<cewolf:chart     id="display" title="" type="stackedhorizontalbar" xaxislabel="Application" yaxislabel="No of Issues">
		<cewolf:data> 
		  	<cewolf:producer id="simpleChartDisplay"/> 
		</cewolf:data>
	 </cewolf:chart>
	 <cewolf:img chartid="display" renderer="/cewolf" width="500" height="500" >	 
	 <cewolf:map tooltipgeneratorid="simpleChartDisplay"/>
	 <cewolf:map linkgeneratorid="simpleChartDisplay"/>
	 </cewolf:img>
	 <cewolf:imgurl chartid="display" renderer="/cewolf"  width="500" height="500" var="imagePath"/>
 </c:if>
 <c:if test="${graphType eq 'list'}"> 
 <ec:table border="0" items="IssuesinAppl" sortable="true" action="./reports.do?reportType=getApplicationIssuesRecords"
		imagePath="./images/*.gif" filterable="false" width="70%" rowsDisplayed="10" maxRowsDisplayed="${maxRows}" var="serRes">
		<ec:row highlightRow="true">		
			<ec:column property="ApplicationNames" width="80%" title="Application" escapeAutoFormat="true"/>
			<ec:column property="IssuesList" width="20%" title="Total Issues"/>				    
		</ec:row>
</ec:table>
 </c:if>
</logic:present>
</td>
</tr>
</table>
</td>
</tr>
<tr>
<td colspan="2">
<table border="0" width="100%" align="left">
<html:form action="reports.do&method=" method="post" >
<tr>
<%String path=(String)pageContext.getAttribute("imagePath"); %>
<input type="hidden" name="imgPath" value="<%=path%>">
<input type="hidden" name="export" value="" id="exportType"> 
<td width="13%">&nbsp;</td>
<td align="left"><button type="button" name="pdf"  class="linkbutton_Reports" accesskey="P" onclick="generateReport('Export to PDF')" >Export To <u>P</u>DF</button>&nbsp;&nbsp;&nbsp;&nbsp;
<button type="button" name="excel" class="linkbutton_Reports" accesskey="E" onclick="generateReport('Export to EXCEL')" >Export To <u>E</u>xcel</button></td>
<td width="58%"></td>
</tr>
</html:form>
</table>
</td>
</tr>
<%}else{ %>
<tr>
<td class="heading_blue" colspan="2">No Records found</td>
</tr>
<%} %>
</table>
</body>
</html:html>