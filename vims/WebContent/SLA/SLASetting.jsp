<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page import="java.util.*" %>
<html:html>
<HEAD>
<TITLE>SLA Setting</TITLE>
<script src="./script-test/SLASetting.js"></script>	
<script>
	function displaySelected()
	{
	var res=confirm('Are you sure you want to modify?')
		if(res)
		{
		    var formObj=document.forms[0];			
			formObj.action="./sLASettingLDAction.do?param=changeButton";		
			formObj.submit();
		}
	}		
</script>
</HEAD>
<BODY  marginheight="0" marginwidth="0" bgproperties="fixed" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" vlink="blue">
<br>
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >
<html:form action="/sLASettingLDAction" focus="application">
<input type="hidden" name="ChangeType" id="ChangeType"/>
<tr><td colspan="2"   align="left">
<c:if test="${SLAMessage ne 'null'}"/><h4><font class="labeField" color="green"><center><c:out value="${SLAMessage}"/></center></font></h4>		
</td></tr>
<tr>	       
	<td  class="labelField" width="20%" align="left">Application Name</TD>
	<c:if test="${Applications ne 'null' }">
	<td  width="80%"  align="left"><font class="labelMandatory" color="red">*</font><html:select property="application"  size="1" styleClass="file_Upload" style="WIDTH: 280px" onchange="fun_sla_app_param();">				
	 	<html:option value="" >Select  Application</html:option>
			<html:optionsCollection name="ApplicationsList" value="id" label="ApplicationName"/> 
	</html:select>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="application"/></font></td>
	</c:if>
	<c:if test="${Applications eq 'null' }">
	<td>
			<c:out value="No Records Found..!"/>
	</td>
	</c:if>
</tr>
<logic:present name="applicationSLADetailsLD">
<TR>
<td colspan="3">
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >	
<logic:notPresent name="changeBtn">	
<tr>
	<td class="heading_blue" align="left" width="20%"><b>Severity</b></td>
	<td align="left" class="labelField" width="8%">&nbsp;&nbsp;<bean:message key ="VIMS.SLA.CRITICAL"/></td>
	<td align="left" class="labelField" width="8%">&nbsp;&nbsp;<bean:message key ="VIMS.SLA.MAJOR"/></td>
	<td align="left" class="labelField" width="8%">&nbsp;&nbsp;<bean:message key ="VIMS.SLA.MINOR"/></td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td class="heading_blue" align="left" ><b>Response Time(Hrs)</b></td>	
	<td  align="left">&nbsp;&nbsp;<html:text property="criticalResponseTime" style="width:30px"  styleClass="dropdownlist" maxlength="2" readonly="true"/></td>
	<td  align="left" >&nbsp;&nbsp;<html:text property="majorResponseTime" style="width:30px" styleClass="dropdownlist" maxlength="2"  readonly="true"/></td>
	<td  align="left" >&nbsp;&nbsp;<html:text property="minorResponseTime" style="width:30px"  styleClass="dropdownlist" maxlength="2"  readonly="true"/></td>
	<td><font color="red" size="2">&nbsp;&nbsp;<html:errors property="criticalResponseTime"/>&nbsp;<html:errors property="criticalWarningInterval"/></font>&nbsp;<html:errors property="majorResponseTime"/></td>
	</tr>
<tr>
	<td class="heading_blue" align="left" ><b>Warning Before(Hrs)</b></td>
	<td align="left">&nbsp;&nbsp;<html:text property="criticalWarningInterval" style="width:30px"  styleClass="dropdownlist" maxlength="2"   readonly="true"/><font color="red" size="2">&nbsp;&nbsp;</td>		
	<td align="left" >&nbsp;&nbsp;<html:text property="majorWarningInterval"  style="width:30px" styleClass="dropdownlist" maxlength="2"    readonly="true"/></td>	
	<td  align="left" >&nbsp;&nbsp;<html:text property="minorWarningInterval" style="width:30px"  styleClass="dropdownlist" maxlength="2"  readonly="true"/></td>
    <td><font color="red" size="2">&nbsp;&nbsp;<html:errors property="majorWarningInterval"/></font>&nbsp;&nbsp;<html:errors property="minorResponseTime"/>&nbsp;&nbsp;<html:errors property="minorWarningInterval"/></td>
</tr>
<tr height="5px"><td>&nbsp;</td></tr>
<tr id="btnMod" >	
	<td align="left" colspan="5">	
	<table border="0" width="100%" >
		<tr>
		<td width="20%"></td>
		<td>&nbsp;<button type="button" class="linkbutton_background" onclick="displaySelected();"  accesskey="M"><u>M</u>odify</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="linkbutton_background" onclick="cancelButton();"  accesskey="C"><u>C</u>ancel</button></td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
	</table >
	</td>	
</tr>
</logic:notPresent>
<logic:present name="changeBtn">
<TR>
	<td class="heading_blue" align="left" width="20%"><b>Severity</b></td>
	<td align="left" class="labelField" width="8%">&nbsp;&nbsp;<bean:message key ="VIMS.SLA.CRITICAL"/></td>
	<td align="left" class="labelField" width="8%">&nbsp;&nbsp;<bean:message key ="VIMS.SLA.MAJOR"/></td>
	<td align="left" class="labelField" width="8%">&nbsp;&nbsp;<bean:message key ="VIMS.SLA.MINOR"/></td>
	<td></td>
</tr>	
<tr>
	<td class="heading_blue" align="left" ><b>Response Time(Hrs)</b></td>	
	<td  align="left"><font class="labelMandatory" color="red">*</font><html:text property="criticalResponseTime" onkeypress="return onlyNumbers();" style="width:30px" styleClass="dropdownlist" maxlength="2"/></td>
	<td  align="left" ><font class="labelMandatory" color="red">*</font><html:text property="majorResponseTime" onkeypress="return onlyNumbers();" style="width:30px" styleClass="dropdownlist" maxlength="2"  /></td>
	<td  align="left" ><font class="labelMandatory" color="red">*</font><html:text property="minorResponseTime" onkeypress="return onlyNumbers();" style="width:30px"  styleClass="dropdownlist" maxlength="2"  /></td>
	<td><font color="red" size="2"><i>&nbsp;&nbsp;<html:errors property="ResTime"/></td>
	</tr>
<tr>
	<td class="heading_blue" align="left" ><b>Warning Before(Hrs)</b></td>
	<td align="left"><font class="labelMandatory" color="red">*</font><html:text property="criticalWarningInterval" onkeypress="return onlyNumbers();" style="width:30px"  styleClass="dropdownlist" maxlength="2"   /><font color="red" size="2">&nbsp;&nbsp;</td>		
	<td align="left" ><font class="labelMandatory" color="red">*</font><html:text property="majorWarningInterval" onkeypress="return onlyNumbers();"  style="width:30px" styleClass="dropdownlist" maxlength="2"    /></td>	
	<td  align="left" ><font class="labelMandatory" color="red">*</font><html:text property="minorWarningInterval" onkeypress="return onlyNumbers();" style="width:30px"  styleClass="dropdownlist" maxlength="2"  /></td>
	<td><font color="red" size="2"><i>&nbsp;&nbsp;<html:errors property="WarBefore"/></td>
</tr>
<tr>
	<td colspan="4" align="left">&nbsp;</td>
	<td align="left"><font color="red" size="2"><i>&nbsp;&nbsp;<html:errors property="WarLessThanRes"/><i></font></td>
</tr>
<tr id="btnUpd" >	
	<td align="left" colspan="5">	
	<table border="0" width="100%" >
		<tr>
		<td width="20%"></td>
		<td>&nbsp;<button type="button" class="linkbutton_background" onclick="modifySLA();"  accesskey="U"><u>U</u>pdate</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="linkbutton_background" onclick="cancelButton();"  accesskey="C"><u>C</u>ancel</button></td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>	
		</tr>
	</table >
	</td>	
</tr>
</logic:present>
</table>
</td>
</TR>
</logic:present>				
</html:form>
</TABLE>

</body>
</html:html>