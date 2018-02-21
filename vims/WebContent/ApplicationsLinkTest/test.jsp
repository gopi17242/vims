<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/tlds/c-1_0.tld" prefix="c" %>

<html:html>
<head>
<script language="javascript" type="text/javascript" src="./script-test/datetimepicker.js">
</script>
<script language="javascript">
	function fnChange()
	{
		
			var formobj=document.forms[0];
		
			formobj.action="./getSupportManagerAction.do?submit=getSuppMngrAction";
		
			formobj.submit();
			
	}	
	function fnsubmit()
	{
		
			var formobj=document.forms[0];
		
			formobj.action="./modifyApplication.do?submit=Modify";
		
			formobj.submit()
	}
	function getApplicationSLADetails()
	{		
		var formobj=document.forms[0];		
		formobj.action="./getApplSLACreatedDetails.do?submit=getSLAcreatedDetailsOfApplication";	
		//formobj.action="./getApplSLACreatedDetails.do?submit=viewSLAcreatedDetailsOfApplication";	
		formobj.submit();
	}
	function displaySLADetails()
	{
	var criRes=document.getElementById('hiddenCriWarn').value
	var criWar=document.getElementById('hiddenCriWarn').value
	var majRes=document.getElementById('hiddenMajRes').value
	var majWar=document.getElementById('hiddenMajWarn').value
	var minRes=document.getElementById('hiddenMinRes').value
	var minWar=document.getElementById('hiddenMinWarn').value
	var details="Critical Response time= "+criRes+" hrs   Critical warning Time= "+criWar+"hrs"
	confirm(details)
	}
</script>
</head>
<body>
<html:form action="addApplication.do" enctype="multipart/form-data">
<html:hidden property="Applications" value="addApplication"/>
<table border="0" width="100%" align="left" cellspacing="1" cellpadding="0" >
	<tr>
		<td colspan="2" height="10px"><Font color="blue"><c:out value="${resultMsg}"/></Font></td>				
	</tr>
	<tr><td height="10px"></td></tr>
	<tr>
		<td colspan="2" align="left"><I><font color="red">*</font>Fields are Mandatory.</I></td><td></td>				
	</tr>
	<tr><td height="10px"></td></tr>
       <tr>	       
		<td class="labelField" align="left" width="36%"><bean:message key="VIMSApplication.appId"/>&nbsp;</TD>
		<td  align="left" colspan="2"><font color="red">*</font><input class="textbox_default" type=text readonly name="appId" value="<c:out value='${Applications.appId}'/>"/>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="appId"/></font></td>
		
	</tr>
			<tr>
				<td class="labelField" align="left"><bean:message key="VIMSApplication.appName"/>&nbsp;</td>
				<td  align="left" colspan="2"><font color="red">*</font><input type="text" class="textbox_default" name="appName" value="<c:out value='${Applications.appName}'/>"/>&nbsp;&nbsp;<font color="red"><html:errors property="appName"/></font></td>			
			</tr>
			<tr>
				<td class="labelField" align="left"><bean:message key="VIMSApplication.appOwner"/>&nbsp;</td>
				<td  align="left" colspan="2"><font color="red">*</font><input class="textbox_default" type="text" name="appOwner" value="<c:out value='${Applications.appOwner}'/>"/>&nbsp;&nbsp;<font color="red" size="2" ><html:errors property="appOwner"/></font></td>
				
			</tr>
		<tr>
			<td class="labelField" align="left"><bean:message key="VIMSApplication.primCont"/>&nbsp;</td>
			<td  align="left"><font color="red">*</font><input class="textbox_default" type="text" name="primCont" value="<c:out value='${Applications.primCont}'/>"/>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="primCont"/></font></td>
			
			
		</tr>
		<tr>
			<td  align="left" class="labelField"><bean:message key="VIMSApplication.supportCenter"/></td>
			<td  align="left" colspan="2">&nbsp;
				<html:select styleClass="dropdownlist" property="supportCenter" size="1" onchange="fnChange();">
						<html:option value="<c:out value='${Applications.supportCenter}'/>"><c:out value='${Applications.supportCenter}'/></html:option>
						<html:options name="supportCenterList"/>
					</html:select>
			</td>
					
		</tr>
		<tr>
			<td  align="left" class="labelField"><bean:message key="VIMSApplication.supportManager"/>&nbsp;</td>
			<td  align="left" colspan="2">&nbsp;&nbsp;<c:set var="suppMgr" value="${supportManager}" scope="request" />
					<c:choose>				
					  <c:when test="${suppMgr ne null}">							
						<input class="textbox_default" type="text" name="supportManager" readonly value="<c:out value='${supportManager}'/>"/>
					 </c:when>
					 <c:otherwise>
					  	<input name="supportManager"  type="text"   value="<c:out value='${Applications.supportManager}'/>">
					 </c:otherwise>
					</c:choose>
			</td>			
		</tr>
		<tr>
			<td class="labelField" align="left"><bean:message key="VIMSApplication.supBegDate"/></td>
			<td  align="left" colspan="2"><font color="red">*</font><input class="textbox_default" type="text" name="supBegDate" id="SupportBeginDate" size="25" readonly value="<c:out value='${Applications.supBegDate}'/>"/>
				<a href="javascript:NewCal('SupportBeginDate','mmddyyyy',true,12)"><img src="./images/calendar.gif" width=18 height=17 border=0 alt="Calender"></a>
				&nbsp;&nbsp;<font color="red" size="2"><html:errors property="supBegDate"/></font>				
			</td>					
		</tr> 		
		<c:if test="${SLAcreatedApplications ne 'null' }">	
		<tr>
		<td valign="TOP"  class="labelField" align="left">SLA created Applications</td>
		<td align="left" colspan="2">&nbsp;&nbsp;<html:select property="SLACreatedApplication" styleClass="dropdownlist" onchange="getApplicationSLADetails()">
			<html:option value="" >select an application</html:option>
	  		<html:optionsCollection name="SLAcreatedApplications" value="id" label="ApplicationName"/> 
			</html:select> 
		</td>	
		</tr>	
		</c:if>	
		<tr>
		<td align="left" colspan="3">
		<table border="0" width="100%" align="left" cellspacing="1" cellpadding="0" >
			<TR>
				<td class="heading_blue" align="left" valign="top" width="20%"><b>Issue Type</b></td>
				<td class="heading_blue" align="left" valign="top" width="20%"><b>Response Time(hrs)</b></td>	
				<td class="heading_blue" align="left" valign="top" width="20%"><b>Warning Interval(hrs)</b></td>	
				<td  width="40%">&nbsp;</td>	
			</tr>	
			<tr>
				<td align="left" class="labelField" ><bean:message key ="VIMS.SLA.CRITICAL"/>&nbsp;&nbsp;</td>
				<td align="left" ><font color="red">*</font><html:text property="criticalResponseTime" styleId="CriRes" styleClass="textbox_short"/></td>	
				<html:hidden property="criticalResponseTimeHidden" styleId="hiddenCriRes"/>
				<td  align="left"><font color="red">*</font><html:text property="criticalWarningInterval" styleId="CriWarn" styleClass="textbox_short"/></td>
				<html:hidden property="criticalWarningIntervalHidden" styleId="hiddenCriWarn"/>
				<td align="left" colspan="2"><font color="red" size="2"><html:errors property="criticalResponseTime"></html:errors><br><html:errors property="criticalWarningInterval"></html:errors></font></td>
				
			</tr>

			<tr>
				<td align="left" class="labelField"><bean:message key ="VIMS.SLA.MAJOR"/>&nbsp;&nbsp;</td>
				<td align="left"><font color="red">*</font><html:text property="majorResponseTime" styleId="MajRes" styleClass="textbox_short"/></td>	
				<html:hidden property="majorResponseTimeHidden" styleId="hiddenMajRes"/>
				<td align="left"><font color="red">*</font><html:text property="majorWarningInterval" styleId="MajWarn" styleClass="textbox_short"/></td>	
				<html:hidden property="majorWarningIntervalHidden" styleId="hiddenMajWarn"/>
				<td align="left"  colspan="2"><font color="red" size="2"><html:errors property="majorResponseTime"></html:errors><br><html:errors property="majorWarningInterval"></html:errors></font></td>
				
			</tr>

			<tr>		
				<td align="left" class="labelField"><bean:message key ="VIMS.SLA.MINOR"/>&nbsp;&nbsp;</td>
				<td align="left"><font color="red">*</font><html:text property="minorResponseTime" styleId="MinRes" styleClass="textbox_short"/></td>	
				<html:hidden property="minorResponseTimeHidden" styleId="hiddenMinRes"/>
				<td align="left"><font color="red">*</font><html:text property="minorWarningInterval" styleId="MinWarn" styleClass="textbox_short"/></td>	
				<html:hidden property="minorWarningIntervalHidden" styleId="hiddenMinWarn"/>
				<td align="left"  colspan="2"><font color="red" size="2"><html:errors property="minorResponseTime"></html:errors><br><html:errors property="minorWarningInterval"></html:errors></font></td>
				
			</tr>
			<tr><td height="5px"></td></tr>
			<tr>
				<td align="center" colspan="3">	
				<table width="20%" >
					<tr>
						<td><input class="linkbutton_background" type="submit" value="submit" onclick="fnsubmit();"/></td>				
						<td><html:reset styleClass="linkbutton_background" value="Reset"/></td>
						<td><button type="Button" class="linkbutton_background" name="back" onClick="javascript:history.back()" accesskey="b"><u>B</u>ack</button></td>
					</tr>
				</table>
				</td>
			</tr>	
			</table>
		</td>
	</tr>
</table>
</html:form>
</body>
</html:html>