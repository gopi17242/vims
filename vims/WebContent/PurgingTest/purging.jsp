<!-- 
FileName	    : purging.jsp


Description:This jsp displays various option for purging a selected set of issues i.e. based on Dates or based on months, issue id etc 
 Developed by  : Vertex Computer Systems.
 copyright (c) 2008 Vertex.
 All rights reserved.

 Change History:

 Revision No.	:		Date		  @author		Description
 1.0				Friday 21,2008	  Sudhir.D	   Initial Version.


--> 
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html:html>
<head>
<title>Vertex Issue Management System - Purging</title>
<script language="javascript" type="text/javascript" src="./script-test/advPopCalender.js">
</script>
<script>
//this function is to disable other radio button and its corresponding textboxes, when a particular radio buton is selected.
	function SetEditble(){
	
		if(document.form1.purge(0).checked==true){			
			document.form1.fromDate.disabled=true;
			document.form1.toDate.disabled=true;
			document.form1.prevMonths.disabled=true;
			document.form1.prevDays.disabled=true;
			document.form1.incidentID.disabled=true;
			document.getElementById('daterange').style.display='none'
		}
		else if(document.form1.purge(1).checked==true){
		
			document.form1.fromDate.disabled=false;
			document.form1.toDate.disabled=false;
			document.form1.prevMonths.disabled=true;
			document.form1.prevDays.disabled=true;
			document.form1.incidentID.disabled=true;
			document.getElementById('daterange').style.display='block'
		}
		else if(document.form1.purge(2).checked==true){
		
			document.form1.fromDate.disabled=true;
			document.form1.toDate.disabled=true;
			document.form1.prevMonths.disabled=false;
			document.form1.prevDays.disabled=true;
			document.form1.incidentID.disabled=true;
			document.getElementById('daterange').style.display='none'

		}
		else if(document.form1.purge(3).checked==true){
		
			document.form1.fromDate.disabled=true;
			document.form1.toDate.disabled=true;
			document.form1.prevMonths.disabled=true;
			document.form1.prevDays.disabled=false;
			document.form1.incidentID.disabled=true;
			document.getElementById('daterange').style.display='none'
		}
		else if(document.form1.purge(4).checked==true){
		
			document.form1.fromDate.disabled=true;
			document.form1.toDate.disabled=true;
			document.form1.prevMonths.disabled=true;
			document.form1.prevDays.disabled=true;
			document.form1.incidentID.disabled=false;
			document.getElementById('daterange').style.display='none'
		}
	}
</script>
</head>
<body>
<form method="post"  name="form1" action="./purgingLookupDispatch.do?purgingfunction=Submit">
<table border="0" width="100%" align="center" cellspacing="2" cellpadding="0" >		
	<tr><td colspan="3" align="center" height="15px"><h4><font color="red" size="2"><c:out value="${purgingmessage}"/></font></h4></td></tr>
	<tr>
		<td class="heading_blue" align="left" width="20%">Operation</td>	       
		<td class="labelField" align="left" width="80%">&nbsp;<input type="radio" name="operation" checked value="Archive" /><bean:message key="VIMSApplication.archive"/></TD>
	</tr>
	<tr>
		<td class="heading_blue" align="left">Issue Status</td>	       
		<td class="labelField" align="left">&nbsp;<input type="radio" name="issueStatus" value="Rejected" /><bean:message key="VIMSApplication.IssueStatusRejected"/>
		<input type="radio" name="issueStatus" checked value="Closed" /><bean:message key="VIMSApplication.IssueStatusClosed"/></td>	 
	</tr>
	<tr><td colspan="3" height="10px"></td></tr>
	<tr>
		<td class="heading_blue" align="left">Select Range of Issues</td>
	</tr>
	<tr><td colspan="3" height="10px"></td></tr>
<tr>
	<td class="labelField"><input type="radio" name="purge" onClick="SetEditble()" checked value="all" /><bean:message key="VIMSApplication.EmployeeAll"/></td>
</tr>
<tr>	
	<td class="labelField"><input type="radio" name="purge" onClick="SetEditble()" value="betweendates"/><bean:message key="VIMSApplication.PurgingBetweenDates"/></td>
	<td id="daterange" style="display:none">&nbsp;&nbsp;<input type="text" name="fromDate"  readonly class="textbox_default" size="15"/>&nbsp;
	 <script>document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].fromDate,window.document.forms[0].fromDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
	</script>&nbsp;<span class="labelField"><bean:message key="VIMSApplication.and"/></span>
	<input type="text" name="toDate"  class="textbox_default" size="15" readonly/>&nbsp;
    <script>document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].toDate,window.document.forms[0].toDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');</script>
	 </td>
</tr>
<tr>
	<td class="labelField"><input type="radio" name="purge" onClick="SetEditble()" value="PrevMonths" />During the Previous</td>	
	<td class="dropdownlist">&nbsp;&nbsp;<select style="WIDTH: 50px" name="prevMonths" disabled  >
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
		    <option value="5">5</option>
			<option value="6">6</option>
		</select>&nbsp;Months
	</td>
</tr>
<tr>
	<td class="labelField"><input type="radio" name="purge" onClick="SetEditble()" value="PrevDays" />During the Previous</td>
	<td class="dropdownlist">&nbsp;&nbsp;<select   style="WIDTH: 50px" name="prevDays"  disabled >
			<option value="1">1</option>
			<option value="7">7</option>
			<option value="15">15</option>
			<option value="30">30</option>
		    <option value="60">60</option>
			<option value="100">100</option>
		</select>&nbsp;Days
	</td>
</tr>
<tr>
	<td class="labelField"><input type="radio" name="purge" onClick="SetEditble()" value="incid" />
	<bean:message key="VIMSApplication.incidentID"/></td>
	<td>&nbsp;&nbsp;<input type="text" name="incidentID" class="textbox_default"/></td>
</tr>	
<tr><td colspan="3" height="15px"></td></tr>
<tr>
	<td>&nbsp;</td>
	<td align="left" colspan ="2">&nbsp;&nbsp;<button type="submit"  class="linkbutton_background" accessKey="s"><u>S</u>ubmit</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="reset" class="linkbutton_background" accessKey="r"><u>R</u>eset</button></td>
</tr>
<tr><td colspan="3" height="10px"></td></tr>
<tr>
	<td colspan="3"><fieldset><legend class="heading_blue"><bean:message key="VIMSApplication.NoteHeader"/></legend>
	<font class="labelField"><bean:message key="VIMSApplication.NoteBody"/></font>
	</fieldset>	</td>		
</tr>
</table>
</form>
</body>
</html:html>

