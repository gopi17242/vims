<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/tlds/c-1_0.tld" prefix="c" %>

<html:html>
<title>Vertex Incident Management System - Home Page</title>
<LINK href="./Layouts/StyleSheet.css" rel="STYLESHEET" type="text/css">
<body>
<center><h2 class="heading">Escalation</h2></center>

<html:form action="" method="post">
<TABLE border="0" width="100%" cellspacing="2" cellpadding="0">

<tr>
	<td class="normalcontenttext" align="right">IncidentId&nbsp;</td>
	<td class="normalcontenttext"><b></b></td>
	<td class="normalcontenttext" align="right">Title&nbsp;</td>
	<td class="normalcontenttext"><b>Operating Sheet</b></td>
</tr>

<tr>
	<td class="normalcontenttext" align="right">Product Name&nbsp;</td>
	<td class="normalcontenttext"><b>Indosoft Manufacturing System</b></td>
	<td class="normalcontenttext" align="right">Module</td>
	<td class="normalcontenttext"><b>Product Module</b></td>
</tr>

<tr>
	<td width="23%" class="normalcontenttext" align="right">Customer Name&nbsp;</td>
	<td class="normalcontenttext"><b>Nestle India Limited</b></td>
	<td class="normalcontenttext" align="right">Posted Date&nbsp;</td>
	<td class="normalcontenttext"><b>12/5/2002</b></td>
</tr>

<tr>
	<td class="normalcontenttext" align="right">Severity&nbsp;</td>
	<td class="normalcontenttext"><b>Major</b></td>
</tr>

<tr>
	<td class="normalcontenttext" align="right">
		Reason For Escalation&nbsp;
	</td>
</tr>

<tr>	
	<td class="normalcontenttext" colspan="4">
		<html:textarea rows="4" cols="70" property="" readonly="true" >
			Busy with Other projects
		</html:textarea>
	</td>
</tr>

<tr>
	<td class="normalcontenttext" >Comments&nbsp;</td>
</tr>

<tr>
	<td class="normalcontenttext" colspan="4"><html:textarea property="" rows="3" cols="70"></html:textarea></td>
</tr>

<tr>
	<td class="normalcontenttext" align="right">Groups&nbsp;</td>
	<td>
		<html:select property="">
			<html:option value="">-Select an Group-</html:option>
			<html:options name=""/>
		</html:select></td>
		
	<td class="normalcontenttext" align="right">Assigned To&nbsp;</td>
	<td><html:select property="">
			<html:option value="">-Select an Employee-</html:option>
			<html:options name=""/>
		</html:select>
	</td>
</tr>

<tr>
	<td></td>	
</tr>

<tr>
	<td></td>	
</tr>

<tr>
	<td></td>	
</tr>

<tr>
	<td></td>	
</tr>	

<tr>
	<td colspan="4" align="center"><input type="submit" value="ReAssign">&nbsp;&nbsp;<a href="./FrmMain.jsp?inc=EscalationSettigns.jsp" class="infobottom">[ Back ]</a></td>
</tr>

</table>
</html:form>
</body>
</html:html>

