<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/tlds/c-1_0.tld" prefix="c" %>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
<%@ page import="java.util.*"%>

<html>
<head>

<script>
function goBack()
{
	alert("In goBack")
	alert(document.forms[0].action="./viewApplicationList.do?param=viewApplicationList")
	document.forms[0].submit();
}
</script>

</head>
<body>


 

<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >
	
			<html:form action="ApplicationCustomers.do" method="post">
			<html:hidden property="Applications" value="applicationCustomers"/>
			<tr>
				<td colspan="2" height="10px"><Font color="blue"><c:out value="${resultMsg}"/></Font></td>
				
			</tr>
			<tr><td height="10px"></td></tr>
			<tr>
				<td colspan="3" align="left"><I><font color=red>*</font>Fields are Mandatory.</I></td>
			</tr>
			<tr>
			<td></td>
			</tr>
	        <tr>	       
				<td class="labelField" align="left" width="20%">Customer Name</TD>
				<td  align="left" width="80%"><font color=red>*</font><html:select property="custId" styleClass="dropdownlist">
					<html:option value="">---Select---</html:option>
					<html:optionsCollection name="custIdList" value="customerId" label="customerName"/>
				</html:select><font color="red" size="2"><html:errors property="custId"/></font></td>
			</tr>
			<TR>				
				<td class="labelField" align="left" width="20%">Applications Name</TD>
				<td  align="left"><font color=red>*</font><html:select property="appId" styleClass="dropdownlist">
					<html:option value="">---Select---</html:option>
						<html:optionsCollection name="appIdList" value="appId" label="appName"/>
					</html:select><font color="red" size="2"><html:errors property="appId"/></font></td>
			</TR>			
			<tr><td height="10px"></td></tr>
			<TR>
				<td  align="center" colspan="3">
					<html:submit property="param" accesskey="a" styleClass="linkbutton_background"><bean:message key="VIMSApplication.addApplicationCustomer"/></html:submit>&nbsp;&nbsp;						
					<button type="Button" class="linkbutton_background" name="back" onClick="javascript:history.back()" accesskey="b"><u>B</u>ack</button>
				</td>					
			</TR>		
			</html:form>
			<TR>
				<td  align="center" colspan="3">
					<ec:table items="appCustList" sortable="true" action="./ApplicationCustomersLink.do?param=applicationCustomers"
						imagePath="./images/*.gif" filterable="false" width="100%"
						rowsDisplayed="10" cellspacing="2" cellpadding="2" border="0">
						
						<ec:row highlightRow="true"> 		
							<ec:column property="appId" title="Application ID" format="String"/>
							<ec:column property="appName" title="Application Name"/>
							<ec:column property="custId" title="Customer ID"/>			
							<ec:column property="custName" title="Customer Name"/>
						</ec:row>
					</ec:table>
					
				</td>
			</TR>
			
				
	</TABLE>

</body>
</html>