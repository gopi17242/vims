<HTML>
<HEAD>
<LINK href="./Layouts/StyleSheet.css" rel="STYLESHEET" type="text/css">
<script>
	function frmSubmit(){
		HomePage.action="./vimsUserHome.do"
		HomePage.submit();
	}

	function fun_IncidentID(){
		if(document.frmIncfeedback.incidentid.value==""){
			alert("Please enter Issue ID ");
			document.frmIncfeedback.incidentid.focus();
			return false;
		}
	}
</script>

</HEAD>
<BODY marginheight="0" marginwidth="0" topmargin=0 leftmargin=0 rightmargin="0" bottommargin="0" vlink="Blue" >
<tiles:insert definition="VIMSUserHome"/>
<title>Home Page</title>
<TABLE border="0" cellpadding="0" cellspacing="0" width="100%">
	
	<TR>
		<TD>
		<TABLE border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
			<TR>
			<TD class="fieldhead" ALIGN="Left" VALIGN="TOP" width="80%">
				<table border="0" cellspacing="3" cellpadding="0">
					<tr><td>&nbsp;</td></tr>
					<tr>
					<td valign="TOP">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td width="2%">&nbsp;</td>
						<td width="98%" class="normalcontenttext">
							<p>Thank you for using Vertex Issue Management System, the ultimate Tracking and Resolution System for the Global Support Services.</p>
							<span  class="cname"><b>Automatic Notification</b></span><br>
							<p>Automatically notify clients and internal users of issues and any changes.</P>
							<span class="cname"><b>Knowledge Base</b></span><br><br>A fully searchable knowledge base with resolution histories. 
							<span class="cname"><b>Client Management </b></span><br><br>Stores all your client details in one central location
							<span class="cname"><b>Escalation </b></span><br><br>Escalation allows you to escalate an issue if it is not closed or if the last response has passed a specified interval.
							<span class="cname"><b>Reports </b></span><br><br>Print a variety of reports to answer queries like how long it took to resolve an issue, how old is an issue etc.
							
						</td>
						</tr>
					</table>
					</td>
					</tr>
				</table>
			</TD>
			<td width="20%" valign="TOP"><br>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="RIGHT" valign="TOP" bgcolor="#EFF4FB" bordercolor="white" ><!---rules="groups"  --->
				<TR>
					<td width="100%" colspan="3" align="CENTER" bgcolor="#023f58" class="fieldhead"><font face="" color="White">SLA Details </font></TD>
				</TR>
	<FORM action="#" method="post" Name="HomePage">
				<TR>
					<TD width="100%" COLSPAN="3">
						<SELECT name="cmbappid" style="WIDTH: 200px" onChange="frmSubmit()"> 
													

			<option class="normalcontenttext" value="" selected></option>

			<option class="normalcontenttext" value=""></option>
										
						</SELECT>
					</TD>
				</TR>
																		<TR>
					<TD width="35%" class="normalcontenttext">&nbsp;Minor</TD>
					<TD width="5%" class="normalcontenttext">&nbsp;:&nbsp;</TD>
					<TD width="60%" CLASS="normalcontenttext">&nbsp; hrs</TD>
				</TR>																										
				<TR>
					<TD width="35%" class="normalcontenttext">&nbsp;Major</TD>
					<TD width="5%" class="normalcontenttext">&nbsp;:&nbsp;</TD>
					<TD width="60%" CLASS="normalcontenttext">&nbsp; hrs</TD>
				</TR>																									
				<TR>
					<TD width="35%" class="normalcontenttext">&nbsp;Critical</TD>
					<TD width="5%" class="normalcontenttext">&nbsp;:&nbsp;</TD>
					<TD width="60%" CLASS="normalcontenttext">&nbsp; hrs</TD>
				</TR>						
										
				<TR>
					<TD colspan="3"><hr size="2" width="100%" color="white"></TD>	
				</TR>
				<TR >
					<TD COLSPAN=3 align="center" CLASS="normalcontenttext">Total [<Strong>
				<TR>
					<TD colspan="3"><hr size="2" width="100%" color="white"></TD>	
				</TR>
				<TR >
					<TD COLSPAN=3 align="center" CLASS="normalcontenttext">Currently there are [<Strong>></Strong>] Open Issues.</TD>		
				</TR>
		<!-- </form> -->
				<TR>
					<TD colspan="3"><hr size="2" width="100%" color="white"></TD>	
				</TR>
				<TR>
					<td colspan="3" class="normalcontenttext">Enter Issue ID: </TD>
				</TR>										
				<TR>
															
					<td colspan="3">
		<form name = frmIncfeedback action="./userIncidentInformation.do" method="post" onSubmit="return fun_IncidentID()">
					<input type="Text" name="incidentid" size="15">&nbsp;&nbsp;<input type="Image" name="Go" src="./images/go.gif" border="0" vspace="0" hspace="0">
		</FORM>	
					</TD>
				</TR>									
			</TABLE>	
			</TD>
			</TR>
		</TABLE>
	</TD>
	</TR>		
</TABLE>			
</BODY>
</HTML>