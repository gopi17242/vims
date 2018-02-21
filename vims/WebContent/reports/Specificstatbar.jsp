<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/cewolf.tld" prefix="cewolf" %> 
<%@page import="java.util.*" %> 
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
<html:html>
<head>
<script type="text/javascript" src="./script-test/specificIssues.js"></script>
</head>
<%
HashMap hashMap=(HashMap)session.getAttribute("SpecificIssuesLength");
String[] ApplList=null;
int [] IssuesList=null;
int ApplCount=0;
HashMap ReportsList=null;
ArrayList alist=new ArrayList();
if(hashMap!=null)
{
	ApplList=(String[])hashMap.get("ApplicationNames");
	IssuesList=(int[])hashMap.get("IssuesList");
	if(ApplList!=null)
	{
		ApplCount=ApplList.length; 
		
		for(int i=0;i<IssuesList.length;i++)
		{
			ReportsList=new HashMap();
			ReportsList.put("IssuesList",IssuesList[i]);
			ReportsList.put("ApplicationNames",ApplList[i]);
			alist.add(ReportsList);
		}
	}
	
}
session.setAttribute("IssuesinAppl",alist);
%>
<body>
<jsp:useBean id="pieChartDisplay" class="com.vertex.VIMS.test.reports.DSProducer.VIMSReportsSpecificIssuesPieChartDatasetProducer" scope="page"/>
<jsp:useBean id="simpleChartDisplay" class="com.vertex.VIMS.test.reports.DSProducer.VIMSReportsSpecificIssuesSimpleDatasetProducer" scope="page" />
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >
<html:form action="/reportsBySpecificStatus.do&reportType=getSpecificIssuesRecords" >
<tr>
	<td align="left"><I><font color="red">*</font>Fields are Mandatory.</I></td><td></td>				
</tr>
<tr>
	<td class="labelField" align="left" width="20%">Status</TD>
	<td  align="left"  class="labelField" colspan="2">&nbsp;&nbsp;
	<html:radio property="status" value="active" styleId="rad1" styleClass="labelField" onclick="fnGetApplByStatus('active')">Active</html:radio>&nbsp;&nbsp;&nbsp;&nbsp;
	<html:radio property="status" value="inactive" styleId="rad2"  styleClass="labelField" onclick="fnGetApplByStatus('inactive')">Inactive</html:radio>
	</td>	
</tr>
<tr>	       
	<td class="labelField" align="left" width="20%">Display Type</TD>
	<td  align="left"  class="labelField" colspan="2" ><font color="red" >*</font>
	<html:radio property="chartType" value="pie3D" tabindex="1">Pie Chart</html:radio>
	<html:radio property="chartType" value="verticalbar3D" tabindex="2">Bar Graph</html:radio>
	<html:radio property="chartType" value="list">List</html:radio></td>
	<td><font color="red" size="2" >&nbsp;&nbsp;<html:errors property="ChartType"/></font></td>
	
</tr>
<tr>	       
	<td class="labelField" align="left" width="20%">Application</TD>
	<td  align="left" colspan="2"><font color="red" class="labelMandatory">*</font><html:select property="applicationSelected" size="1" styleId="applList" onchange="getIssueTypesList();" style="WIDTH: 280px" styleClass="dropdownlist" tabindex="3">				
		<html:option value="">Select an Application</html:option>
		<html:option value="allApps">All Applications</html:option>				 			   
		<html:optionsCollection name="ApplicationNamesInSpecificIssues" value="id" label="ApplicationName"/> 
	</html:select>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="ApplicationSelected"/></font>
	</td>
</tr>
<logic:present name="issueTypesList">
<tr>	       
	<td class="labelField" align="left" width="20%">Issue Type</TD>
	<td  align="left" colspan="2"><font color="red" class="labelMandatory">*</font><html:select property="issueTypeSelected" size="1" styleId="issueTypeList" styleClass="dropdownlist" tabindex="4">
		<html:option value="">Select an Issue Type</html:option>
		<html:optionsCollection name="issueTypesList" value="id" label="name"/> 
	</html:select>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="IssueTypeSelected"/></font>
	</td>
</tr>
</logic:present>
<logic:notPresent name="issueTypesList">
<tr>	       
	<td class="labelField" align="left" width="20%">Issue Type</TD>
	<td  align="left" colspan="2"><font color="red" class="labelMandatory">*</font><html:select property="issueTypeSelected" size="1" styleId="issueTypeList" styleClass="dropdownlist" tabindex="4">
		<html:option value="">Select an Issue Type</html:option>		
	</html:select>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="IssueTypeSelected"/></font>
	</td>
</tr>
</logic:notPresent>
<tr>
<td height="10px"></td>
<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;<button type="button" value="Generate" class="linkbutton_Reports" accesskey="G" onclick="generateImage()" tabindex="5"><u>G</u>enerate</button>&nbsp;&nbsp;</td>
</tr>
<tr>
<td height="10px"></td>
<td>&nbsp;</td>
</tr>
</html:form>
<%if(ApplList!=null){
if(ApplList.length!=0){
	if(ApplList.length>50)
{
	pageContext.setAttribute("maxRows",ApplList.length);
}
else
{
	pageContext.setAttribute("maxRows","100");
}%>
<tr>
<td colspan="3" align="left">
<table border="0" width="100%" align="left">
<logic:present name="strChartType">
<tr> 
<td class="labelField" align="left" width="20%"></td>
<td class="labelField" align="left" width="80%">
<c:set var="graphType" value="${strChartType}"/>
<c:if test="${graphType eq 'pie3D'}">
	<cewolf:chart id="display"     title="" type="pie" >
		<cewolf:data> 
			<cewolf:producer id="pieChartDisplay"/>
        </cewolf:data>
	</cewolf:chart>	
	
<cewolf:img chartid="display" renderer="/cewolf" width="500" height="600" >
	<cewolf:map tooltipgeneratorid="pieChartDisplay"/>
	<cewolf:map linkgeneratorid="pieChartDisplay"/>
</cewolf:img>
</c:if>
	
<c:if test="${graphType eq 'verticalbar3D'}">
	<cewolf:chart     id="display"     title="" type="stackedhorizontalbar" xaxislabel="Application Name" yaxislabel="No of Issues">
		<cewolf:data> 
		  	<cewolf:producer id="simpleChartDisplay"/> 
		</cewolf:data>
	 </cewolf:chart>
	 	 
 <cewolf:img chartid="display" renderer="/cewolf" width="500" height="600" >
	<cewolf:map tooltipgeneratorid="simpleChartDisplay"/>
	<cewolf:map linkgeneratorid="simpleChartDisplay"/>
</cewolf:img>
 </c:if>
 <c:if test="${graphType eq 'list'}"> 
 <ec:table border="0" items="IssuesinAppl" sortable="true" action="./reportsBySpecificStatus.do?reportType=getSpecificIssuesRecords"
		imagePath="./images/*.gif" filterable="false" width="70%" rowsDisplayed="10" maxRowsDisplayed="${maxRows}" var="serRes">

		<ec:row highlightRow="true">		
			<ec:column property="ApplicationNames" width="80%" title="Application" escapeAutoFormat="true"/>
			<ec:column property="IssuesList" width="20%" title="Total Issues"/>				    
		</ec:row>
</ec:table>
 </c:if>
<cewolf:imgurl chartid="display" renderer="/cewolf" width="400" height="300" var="imagePath"/>	
</td>
</tr>
</logic:present>
</table>
</td>
</tr>
<tr>
<td colspan="3">
<table border="0" width="100%" align="left">
<html:form action="/reportsBySpecificStatus.do&reportType=getSpecificIssuesRecords" >
<%String path=(String)pageContext.getAttribute("imagePath"); %>
<input type="hidden" name="imgPath" value="<%=path%>">
<input type="hidden" name="export" value="" id="exportType"> 
<input type="hidden" name="application" value=""> 
<input type="hidden" name="issue" value="" > 
<input type="hidden" name="statusType" value="" > 
<tr>
<td width="20%"></td>
<% if (path!=null) {%>
<td align="left">
<button type="button"  class="linkbutton_Reports" accesskey="P" onclick="generateReport('Export to PDF')" tabindex="6">Export To <u>P</u>DF</button>&nbsp;&nbsp;&nbsp;&nbsp;
<button type="button"  class="linkbutton_Reports" accesskey="E" onclick="generateReport('Export to EXCEL')" tabindex="7">Export To <u>E</u>xcel</button></td>
<td width="35%"></td>
<% }%>
</tr>
</html:form>
</table>
</td>
</tr>
<%}else{ %>
<tr>
<td class="heading_blue" colspan="2">No Records found</td>
</tr>
<%}
}%>
</table>

</body>

</html:html>