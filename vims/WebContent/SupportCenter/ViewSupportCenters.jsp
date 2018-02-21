<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*"%>

<html>
<title>Vertex Incident Management System - Home Page</title>
<script>
	function SupportCenterDetails()
	{
	    var formobj=document.forms[0];
	     formobj.groupsupervisor.value="";
	    formobj.action="./supportCenter.do?methodname=SupportCenterDetails";
		formobj.submit();
	}
	function GroupDetails()
	{
	    var formobj=document.forms[0];	    
	    formobj.action="./supportCenter.do?methodname=groupDetails";
		formobj.submit();
	}
</script>
<body>
<html:form action="/supportCenter.do?methodname=BasePage">
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >			
<tr>
<td width="100%">
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >			
	
	<tr><td height="10px" colspan="5" align="center" class="labelField"><font color=red><c:out value="${MSG}"/></font></td></tr>
	<!-- <tr><td height="10px"></td></tr> -->	
	<tr>	       
		<td class="labelField" align="left" width="100px">Support Center</TD>
		<td  align="left"><html:select property="supportcenter"  onchange="SupportCenterDetails()" styleClass="dropdownlist" style="width:280px">
			<html:option value="">Select Support Center</html:option>
			<html:optionsCollection name="supportcenters" value="id" label="name"/>
			</html:select>
		</td>
   		<td class="labelField" align="left" width="120px">Support Manager</td>
  		<td><html:text property="supportmanager" readonly="true" styleClass="textbox_default" style="width:220px"/></td>				
	</tr>
	
	<tr>
		<td class="labelField" align="left" width="90px">Groups&nbsp;</td>
		<td  align="left"><html:select property="groups"  onchange="GroupDetails()" styleClass="dropdownlist" style="width:280px">
		<html:option value="">Select Group</html:option>
	    <logic:present name="group">
		<html:optionsCollection name="group" value="groupid" label="groupname"/>
		</logic:present>
		</html:select>
		</td>
		<td class="labelField" align="left" width="120px">Group Supervisor&nbsp;</td>	
		<td  align="left"><html:text property="groupsupervisor" readonly="true" styleClass="textbox_default" style="width:220px"/></td>
  </tr>
</table>
</td>
</tr>
<logic:present name="GroupMembersDetails">

<tr>
<td width="100%">
<TABLE border="0"  cellspacing="1" cellpadding="0" width="100%" align="left">
<%if(((ArrayList)request.getAttribute("GroupMembersDetails")).size()>0)
{
%>
	<tr>
		<TH  colspan="7" class="heading_blue" ALIGN="left">Employees in the Group &nbsp;</TH>
	</tr>
	<tr class="gridAlternate">	
		<!--<th class="gridBluheader" align="left"><font color="white">Employee ID</font></th>	
		--><th class="gridBluheader" align="left"><font color="white">Employee Name</font></th>
		<!-- <th class="gridBluheader" align="left"><font color="white">Department</font></th>-->
		<!--<th class="gridBluheader" align="left">Status</th>-->
		<th class="gridBluheader" align="left"><font color="white">Work Phone</font></th>
		<th class="gridBluheader" align="left"><font color="white">E-mail</font></th>
		<th class="gridBluheader" align="left"><font color="white">Location</font></th>
	</tr>
<c:forEach items="${GroupMembersDetails}"  var="groupmembers" step="1">

	<tr class="gridAlternate">
		<!--<td align="left" >
		<c:out value="${groupmembers.id}"/></td>	
		--><td  align="left"><c:out value="${groupmembers.firstname}"/>
		<c:out value="${groupmembers.middleinitial}"/>
		<c:out value="${groupmembers.lastname}"/></td>
		<!-- <td  align="left"><c:out value="${groupmembers.dept}"/></td>-->
		<!--<td  align="left"><c:out value="${groupmembers.status}"/></td>-->
		<td  align="left"><c:out value="${groupmembers.workphone}"/></td>
		<td  align="left"><c:out value="${groupmembers.email}"/></td>
		<td  align="left"><c:out value="${groupmembers.address}"/></td>
	</tr>
</c:forEach>
<% }
else
{%>
	<tr><td class="labelField"><font color="red">No Employees in this Group</font></td></tr>
<% }%>
</table>
</td>
</tr>


</logic:present>
</table>
</html:form>
</body>
</html>