<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/c-1_0.tld" prefix="c"%>
<html>
<head>
<script type="text/javascript">
function NewWin(OpenFile){
	if (navigator.appName == "Microsoft Internet Explorer")
	{
		window.open(OpenFile, "UploadFile", 'toolbar=no, status=yes, left=0, top=0, scrollbars=yes, resize=yes,  width=800, height=600');
	}
	else if (navigator.appName == "Netscape") 
	{
		window.open("./FileUpload.jsp", "UploadFile",  'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=yes,resizable=0,width=450,height=350,top=100,left=200');
	}
}
</script>
</head>
<body>
<html:errors/>
<table border="0" width="65%" align="center" cellspacing="2" cellpadding="0" >
			<tr><td height="10px"></td></tr>
			
			<tr><td height="10px"></td></tr>
			<c:forEach items="${appView}"  var="ViewApplication" step="1">
	        <tr>	       
				<td class="labelField" align="left" width="48%"><b>Application ID</b></TD>
				<td class="labelField" align="left"><c:out value='${ViewApplication.appId}'/></td>				
			</tr>
			<tr>	       
				<td class="labelField" align="left" width="48%"><b>Application Name</b></TD>
				<td class="labelField" align="left"><c:out value='${ViewApplication.appName}'/></td>				
			</tr>
			<tr>	       
				<td class="labelField" align="left" width="48%"><b>Application Owner</b></TD>
				<td class="labelField" align="left"><c:out value='${ViewApplication.appOwner}'/></td>				
			</tr>
			<tr>	       
				<td class="labelField" align="left" width="48%"><b>Status</b></TD>
				<td class="labelField" align="left"><c:out value='${ViewApplication.appStatus}'/></td>				
			</tr>
			<tr>	       
				<td class="labelField" align="left" width="48%"><b>Application Primary Contact</b></TD>
				<td class="labelField" align="left"><c:out value='${ViewApplication.primCont}'/></td>				
			</tr>
			<tr>	       
				<td class="labelField" align="left" width="48%"><b>Support Begin Date</b></TD>
				<td class="labelField" align="left"><c:out value='${ViewApplication.supBegDate}'/></td>				
			</tr>
			
			<tr>	       
				<td class="labelField" align="left" width="48%"><b>Support Center</b></TD>
				<td class="labelField" align="left"><c:out value='${ViewApplication.supportCenter}'/></td>				
			</tr>
			
			
			<tr>	       
				<td class="labelField" align="left" width="48%"><b>Support Manager</b></TD>
				<td class="labelField" align="left"><c:out value='${ViewApplication.supportManager}'/></td>				
			</tr>	
			<tr>	       
				<td class="labelField" align="left" width="48%"></TD>
				<td class="labelField" align="left"></td>				
			</tr>		
			<tr>
				<td colspan="2" class="labelField" align="left" width="48%"><b><u>Documents Attached</u></b></td>
			</tr>
			<tr>
				<td class="labelField" align="left" width="48%">
					<b>Technical</b>
				</td>			
				<td class="labelField" align="left">
					<a href="#" color="blue" class="infobottom" onclick="NewWin('./VIMSUPLOAD/<c:out value="${ViewApplication.strTechnicalFile}"/>')"><c:out value="${ViewApplication.strTechnicalFile}"/></a>
				</td>
			</tr>
			<tr>
				<td class="labelField" align="left" width="48%">
					<b>Transitional</b>
				</td>			
				<td class="labelField" align="left">
					<a href="#" color="blue" class="infobottom" onclick="NewWin('./VIMSUPLOAD/<c:out value='${ViewApplication.strTransitionFile}'/>')"><c:out value='${ViewApplication.strTransitionFile}'/></a>
				</td>
			</tr>
			<tr>
				<td>
				</td>
			</tr>
			<tr>
			<td colspan="2" class="labelField" align="center">
			<table border="0" align="left" width="70%">
				<tr><td colspan="3" class="labelField" align="center" width="48%"><b><u>SLA Details</u></b></TD></tr>				
				<tr>
					<td class="labelField" align="left"><I><B>Issue Type</B></I></td>
					<td class="labelField" align="center"><I><B>Response Time</B></I></td>
					<td class="labelField" align="center"><I><B>Warning Interval</B></I></td>
				</tr>
				<tr>
					<td class="labelField" align="left"><b>Critical</b></td>
					<td class="labelField" align="center"><c:out value='${ViewApplication.CRITICAL_RESPONSE_TIME}'/></td>
					<td class="labelField" align="center"><c:out value='${ViewApplication.CRITICAL_WARNING_INTERVAL}'/></td>
				</tr>
				<tr>
					<td class="labelField" align="left"><b>Major</b></td>
					<td class="labelField" align="center"><c:out value='${ViewApplication.MAJOR_RESPONSE_TIME}'/></td>
					<td class="labelField" align="center"><c:out value='${ViewApplication.MAJOR_WARNING_INTERVAL}'/></td>
				</tr>	
				<tr>
					<td class="labelField" align="left"><b>Minor</b></td>
					<td class="labelField" align="center"><c:out value='${ViewApplication.MINOR_RESPONSE_TIME}'/></td>
					<td class="labelField" align="center"><c:out value='${ViewApplication.MINOR_WARNING_INTERVAL}'/></td>
				</tr>		
			</table>			
			
			</td>
			</tr>
			
	</c:forEach>
	<tr>
	<td width="10px"></td>
	</tr>
	<tr>
	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" onclick="window.history.back();" accesskey="B" class="linkbutton_background"><u>B</u>ack</button>
	</td>
	</tr>	
</table>
</body>
</html>