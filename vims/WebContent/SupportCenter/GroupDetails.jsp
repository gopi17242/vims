<html>
<head>
<title>Vertex Incident Management System - Home Page</title>
<LINK href="./Layouts/StyleSheet.css" rel="STYLESHEET" type="text/css">
<script language="javascript" src="./Validations/calendar.js">
</script>
<script language="javascript" src="./Validations/GroupMembers.js">
</script>
<script language="javascript">
	function funGrMem(){

		if(document.frmVIMSgrmembers.groupid.value==""){
		alert("Group Id cant empty");
		document.frmVIMSgrmembers.groupid.focus();
		return false;
		}
		else if (document.frmVIMSgrmembers.empid1.value==""){
		alert("Employee Id cant empty");
		document.frmVIMSgrmembers.empid1.focus();
		return false;
		}	
		else if (document.frmVIMSgrmembers.txtstdate.value==""){
		alert("Start date cant be empty");
		document.frmVIMSgrmembers.txtstdate.focus();
		return false;
		}	
		/*else if (document.frmVIMSgrmembers.txtenddate.value==""){
		alert("End date cant be empty");
		document.frmVIMSgrmembers.txtenddate.focus();
		return false;
		}*/	
		else if (document.frmVIMSgrmembers.empAct.value==""){
		alert("Select the status ");
		document.frmVIMSgrmembers.empAct.focus();
		return false;
		}	
			return true;
			}

</script>
</head>
<body>
<center><h2>Group Members</h2></center>

<FORM method="post" Name="frmVIMSgrmembers" onsubmit="return funGrMem()" action="./groupDetailsAct.do">

<TABLE border="0" width="60%" cellspacing="3" cellpadding="3">
	<tr>
		<td width="30%" class="normalcontenttext" align="right"><font color=red>*</font>Group ID&nbsp;</TD>
		
		<td class="normalcontenttext">
			<select name="groupid" type="text" style="WIDTH: 155px">
				<option value="" selected>Select Group ID-</option>

				<option value="group1">group 1</option>
				<option value="group2">group 2</option>

		      </select></TD>
	</tr>

	<tr>
		<td class="normalcontenttext" align="right"><font color=red>*</font>Employee Name&nbsp;</TD>
		<td class="normalcontenttext">
						<select name=empid1>
							<option value="" selected>-Select an Employee-</option>
				<option value="emp1">emp 1</option>
				<option value="emp2">emp 2</option>
		      </select></TD>
	</tr>

	<tr>
		<td class="normalcontenttext" align="right"><font color=red>*</font>Start Date&nbsp;</TD>
		<td class="fieldhead"><input name="txtstdate"  size=18 readonly>
		<a href="javascript:showCal('STARTDATE')"><img src="./images/calendar.gif" width=18 height=17 border=0 alt="Calender"></a></td>
	</tr>

	<tr>
		<td class="normalcontenttext" align="right">End Date&nbsp;</TD>
		<td class="fieldhead"><input name="txtenddate"  type="text" size=18 readonly>
		<a href="javascript:showCal('ENDDATE')"><img src="./images/calendar.gif" width=18 height=17 border=0 alt="Calender"></a></td>

	</tr>	

	<tr>
		<td class="normalcontenttext" align="right">Status&nbsp;</TD>
		<td class="normalcontenttext"><select name=empAct>
							<option value="Active">Active</option>
							<option value="InActive">InActive</option>
			      </select>
		</TD>

	</tr>

	<tr><td colspan=2>&nbsp;</td></tr>
	<tr height=40>
		<td>&nbsp;</td><td><input type=submit value="Submit"></td>
	</tr>
	
	<tr height=40>
		<td>&nbsp;</td><td><a href="./groups.do" color="blue" class="infobottom"">[ Back ]</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="./supportCenter.do" color="blue" class="infobottom"">[ Support Center ]</a></td>
	</tr>		
</table>
