<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/cewolf.tld" prefix="cewolf" %> 
<%@page import ="java.util.*" %>
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
<html:html>
<head>
<script language="javascript" type="text/javascript" src="./script-test/datetimepicker.js"></script>
<script type="text/javascript" src="./script-test/specificIssues.js"></script>
<script>
function generateImage()  
	 {
	 	 var formObj=document.forms[0];
	 	// alert(document.getElementById('issueTypeSelected').value)
		 formObj.action="./reportsBySpecificStatus.do?reportType=getSpecificIssuesRecords";
		 formObj.submit();
	 }

function generateReport(reportType) 
 {	
 	 document.getElementById('exportType').value=reportType;
 	// alert(document.getElementById('exportType').value)	 	
 	 document.getElementById('application').value=document.getElementById('applicationSelected').value;
 	 document.getElementById('issue').value=document.getElementById('issueTypeSelected').value
	 var formObj=document.forms[1];
	 formObj.action="./generateSpecificStatusReports.do?reportType=generateAllIssuesInApplicationRecords&export="+document.getElementById('exportType').value;
	 formObj.submit(); 
 }
</script>
</head>
<%HashMap hashMap=(HashMap)session.getAttribute("AllissuesLength");
String[] IssueTypes={"Open","Assigned","Accepted","Rejected","Escalated","Closed","Completed"};
String[] colnames={"open","assigned","accepted","rejected","escalated","closed","completed"};
int[] issuesCount=new int[7];
ArrayList alist=new ArrayList();
HashMap ReportsList=null;
for(int i=0;i<IssueTypes.length;i++)
{
	ReportsList=new HashMap();
	ReportsList.put("IssueType",IssueTypes[i]);
	ReportsList.put("IssueCount",(Integer)hashMap.get(colnames[i]));
	alist.add(ReportsList);
}
session.setAttribute("AllissuesinApplReports",alist);%>
<body>
<html:errors/>
<jsp:useBean id="pieChartDisplay" class="com.vertex.VIMS.test.reports.DSProducer.VIMSReportsSpecificIssuesPieChartDatasetProducer" scope="page"/>
<jsp:useBean id="simpleChartDisplay" class="com.vertex.VIMS.test.reports.DSProducer.VIMSReportsSpecificIssuesSimpleDatasetProducer" scope="page" />
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >
<html:form action="/reportsBySpecificStatus.do&reportType=getSpecificIssuesRecords" focus="chartType">
<tr>
	<td align="left"><I><font color="red">*</font>Fields are Mandatory.</I></td><td></td>				
</tr>
<tr>
	<td class="labelField" align="left" width="20%">Status</TD>
	<td  align="left"  class="labelField" colspan="2">&nbsp;
	<html:radio property="status" value="active" styleId="rad1" styleClass="labelField" onclick="fnGetApplByStatus('active')">Active</html:radio>&nbsp;&nbsp;&nbsp;&nbsp;
	<html:radio property="status" value="inactive" styleId="rad2"  styleClass="labelField" onclick="fnGetApplByStatus('inactive')">Inactive</html:radio>
	</td>	
</tr>
<tr>	       
	<td class="labelField" align="left" width="20%">Display</TD>
	<td  align="left"  class="labelField"><font color="red" >*</font><html:radio property="chartType" value="pie3D" tabindex="1">Pie Chart</html:radio>
	<html:radio property="chartType" value="verticalbar3D" tabindex="2">Bar Graph</html:radio>
	<html:radio property="chartType" value="list" tabindex="2">List</html:radio>
	</td>
</tr>
<tr>	       
	<td class="labelField" align="left" width="20%">Application</TD>
	<td  align="left"><font color="red" class="labelMandatory">*</font><html:select property="applicationSelected" size="1" onchange="getIssueTypesList();" styleId="applList" style="WIDTH: 280px" styleClass="dropdownlist" tabindex="3">				
		<html:option value="">Select an Application</html:option>
		<html:option value="allApps">All Applications</html:option>				 			   
		<html:optionsCollection name="ApplicationNamesInSpecificIssues" value="id" label="ApplicationName"/> 
	</html:select>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="ApplicationSelected"/></font>
	</td>
</tr>
<tr>	       
	<td class="labelField" align="left" width="20%">Issue Type</TD>
	<td  align="left"><font color="red" class="labelMandatory">*</font><html:select property="issueTypeSelected" size="1" styleId="issueTypeList" styleClass="dropdownlist" tabindex="4">
		<html:option value="">Select an Issue Type</html:option>
		<html:optionsCollection name="issueTypesList" value="id" label="name"/>		
	</html:select>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="IssueTypeSelected"/></font>
	</td>
</tr>
<tr>
<td height="10px"></td>
<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;&nbsp;<button type="button" value="Generate" class="linkbutton_Reports" accesskey="G" onclick="generateImage()" tabindex="5"><u>G</u>enerate</button>&nbsp;&nbsp;</td>
</tr>
<tr>
<td height="10px"></td>
<td>&nbsp;</td>
</tr>
</html:form>
<tr>
<td width="20%"></td>
<td colspan="2" align="center">
<table border="0" width="100%" align="center">
<tr>
<td>
<logic:present name="strChartType">
	<c:set var="graphType" value="${strChartType}"/>
	<c:if test="${graphType eq 'pie3D'}">
	
		<cewolf:chart id="display"     title="" type="pie" >
			<cewolf:data> 
				<cewolf:producer id="pieChartDisplay"/>
	        </cewolf:data>
		</cewolf:chart>
		
	<cewolf:img chartid="display" renderer="/cewolf" width="350" height="350" >
		<cewolf:map tooltipgeneratorid="pieChartDisplay"/>
		<cewolf:map linkgeneratorid="pieChartDisplay"/>
	</cewolf:img>
	<cewolf:imgurl chartid="display" renderer="/cewolf" width="350" height="350" var="imagePath"/>
	</c:if>	
	<c:if test="${graphType eq 'verticalbar3D'}">	
		<cewolf:chart     id="display"     title="" type="stackedhorizontalbar" xaxislabel="Issue Types" yaxislabel="No of Issues">
			<cewolf:data> 
			  	<cewolf:producer id="simpleChartDisplay"/> 
			</cewolf:data>
		 </cewolf:chart>
 <cewolf:img chartid="display" renderer="/cewolf" width="350" height="350" >
	<cewolf:map tooltipgeneratorid="simpleChartDisplay"/>
	<cewolf:map linkgeneratorid="simpleChartDisplay"/>
</cewolf:img>
<cewolf:imgurl chartid="display" renderer="/cewolf" width="350" height="350" var="imagePath"/>	
	 </c:if>

<c:if test="${graphType eq 'list'}"> 
 <ec:table border="0" items="AllissuesinApplReports" sortable="true" action="./reportsBySpecificStatus.do?reportType=getSpecificIssuesRecords"
		imagePath="./images/*.gif" filterable="false" width="70%" rowsDisplayed="10" var="serRes">

		<ec:row highlightRow="true">		
			<ec:column property="IssueType" width="50%" title="Issue Type" escapeAutoFormat="true"/>
			<ec:column property="IssueCount" width="50%" title="No of Issues"/>				    
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
<td colspan="3">
<table border="0" width="100%" align="left">
<html:form action="/reportsBySpecificStatus.do&reportType=getSpecificIssuesRecords" focus="chartType">
<%String path=(String)pageContext.getAttribute("imagePath"); %>
<input type="hidden" name="imgPath" value="<%=path%>">
<input type="hidden" name="export" value="" id="exportType"> 
<input type="hidden" name="application" value=""> 
<input type="hidden" name="issue" value="" > 
<tr>
<td width="20%"></td>
<td align="left">&nbsp;<button type="button"  class="linkbutton_Reports" accesskey="P"  onclick="generateReport('Export to PDF')" tabindex="6">Export To <u>P</u>DF</button>&nbsp;&nbsp;&nbsp;&nbsp;
<button type="button" class="linkbutton_Reports" accesskey="E"  onclick="generateReport('Export to EXCEL')" tabindex="7">Export To <u>E</u>xcel</button></td>
<td width="35%"></td>
</tr>
</html:form>
</table>
</td>
</tr>
</table>

</body>
</html:html>