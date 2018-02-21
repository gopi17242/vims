<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/tlds/c-1_0.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ page import="java.util.*"%>

<html>

<BODY marginheight="0" marginwidth="0" bgproperties="fixed" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" vlink="blue">


<table border="0" width="100%" align="center" cellspacing="2" cellpadding="0" >
	
		<html:form method="post" action="ApplicationGroups.do">
		<html:hidden property="Applications" value="applicationGroups"/>
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
				<td class="labelField" align="left">Application Name</TD>
				<td  align="left">
				<font color=red>*</font><html:select property="appId" styleClass="dropdownlist">
					 <html:option value="">---Select---</html:option>
					<html:optionsCollection name="appIdList" value="appId" label="appName"/>
				</html:select>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="appId"/></font></td>
			</tr>
			<TR>				
				<td class="labelField" align="left" width="20%">Group Name</TD>
				<td  align="left" width="80%">
					<font color=red>*</font><html:select property="grpId" styleClass="dropdownlist">
									 <html:option value="">---Select---</html:option>
									 <html:optionsCollection name="grpIdList" value="groupId" label="groupName"/>
							</html:select>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="grpId"/></font></td>
			</TR>			
			<tr><td height="10px"></td></tr>
			<TR>
				<td  align="center" colspan="3">
					<html:submit property="param" accesskey="a" styleClass="linkbutton_background"><bean:message key="VIMSApplication.addAppGroup"/></html:submit>&nbsp;&nbsp;						
					<button type="Button" class="linkbutton_background" name="back" onClick="javascript:history.back()" accesskey="b"><u>B</u>ack</button>
				</td>					
			</TR></html:form>
			<tr>
			<td  align="center" colspan="3">
				<ec:table items="usrGrpList" sortable="true" action="./ApplicationGroupsLink.do?param=applicationGroups"
					imagePath="./images/*.gif" filterable="false" width="100%"
					rowsDisplayed="10" cellspacing="2" cellpadding="2" border="0">
					
					<ec:row highlightRow="true"> 		
						<ec:column property="appName" title="Application Name"/>
						<ec:column property="grpName" title="Group Name"/>			
					</ec:row>
				</ec:table>
				
			</td>
			</tr>
			
			
						
	</TABLE>
<BR>
<br>



</body>
</html>

