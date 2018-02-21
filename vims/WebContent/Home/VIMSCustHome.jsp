


<HTML>
<HEAD>
<LINK href="./Layouts/StyleSheet.css" rel="STYLESHEET" type="text/css">
<title>Home Page</title>
<script>
	function frmSubmit(){
		vimscustHomePage.action="FrmMain.jsp?inc=Home/VIMSCustHome.jsp"
		vimscustHomePage.submit();
	}
	function fun_IncidentID(){
		if(document.frmIncfeedback.incidentid.value==""){
			alert("Please Enter Incident ID.");
			document.frmIncfeedback.incidentid.focus();
			return false;
		}
	}
</script>
</HEAD>
<BODY marginheight="0" marginwidth="0" topmargin=0 leftmargin=0 rightmargin="0" bottommargin="0" vlink="Blue">
<FORM action="#" method="post" Name="vimscustHomePage">
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
									<span  class="cname">Automatic Notification</span><br>
									<p>Automatically notify clients and internal users of Issue and any changes.</P>
									<span class="cname">Knowledge Base</span><br><br>A fully searchable knowledge base with resolution histories.
									<span class="cname">Client Management </span><br><br>Stores all your client details in one central location
									<span class="cname">Escalation </span><br><br>Escalation allows you to escalate an Issue if it is not closed or if the last response has passed a specified interval.
									<span class="cname">Reports  </span><br><br>Print a variety of reports to answer queries like how long it took to resolve an Issue, how old is an Issue etc.
													
								</td>
							</tr>
						</table>
					</td>
					</tr>
				</table>
			</TD>
			<td width="20%" valign="TOP"><br>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="RIGHT" valign="TOP" bgcolor="#DDE5E8" bordercolor="white" ><!---rules="groups"  --->
					<TR>
						<td width="100%" colspan="3" align="CENTER" bgcolor="#023f58" class="fieldhead"><font face="" color="White">SLA Details </font></TD>
					</TR>
					<TR>
						<TD width="100%" COLSPAN="3">
						
						
						<SELECT name="cmbappid" style="WIDTH: 200px" onChange="frmSubmit()">

			<option class="normalcontenttext" value="" selected></option>

			<option class="normalcontenttext" value=""></option>

			<option class="normalcontenttext" value="">No records</option>
											
							</SELECT>
						</TD>
					</TR>

																									
					<TR>
						<TD width="35%" class="normalcontenttext">&nbsp; Minor</TD>
						<TD width="5%" class="normalcontenttext">&nbsp;:&nbsp;</TD>
						<TD width="60%" CLASS="normalcontenttext">&nbsp; hrs</TD>
					</TR>																										
					<TR>
						<TD width="35%" class="normalcontenttext">&nbsp; Major</TD>
						<TD width="5%" class="normalcontenttext">&nbsp;:&nbsp;</TD>
						<TD width="60%" CLASS="normalcontenttext">&nbsp; hrs</TD>
					</TR>																									
					<TR>
						<TD width="35%" class="normalcontenttext">&nbsp; Critical</TD>
						<TD width="5%" class="normalcontenttext">&nbsp;:&nbsp;</TD>
						<TD width="60%" CLASS="normalcontenttext">&nbsp;hrs</TD>
					</TR>						
					<TR>
						<TD colspan="3"><hr size="2" width="100%" color="white"></TD>	
					</TR>
					<TR>
						<TD COLSPAN=3 align="center" CLASS="normalcontenttext">Total [<Strong></Strong>] Issues tracked by VIMS.</TD>		
					</TR>
					<TR>
						<TD colspan="3"><hr size="2" width="100%" color="white"></TD>	
					</TR>
					<TR>
						<TD COLSPAN=3 align="center" CLASS="normalcontenttext">Currently there are [<Strong></Strong>] Open Issue.</TD>		
					</TR>
	<!--  -->			
					<TR>
						<TD colspan="3"><hr size="2" width="100%" color="white"></TD>	
					</TR>
					<TR>
						<td colspan="3" class="normalcontenttext">Enter Issue ID&nbsp;</TD>
					</TR>										
					<TR>											
						<td colspan="3">
							<form name = frmIncfeedback action="./FrmMain.jsp?inc=IncidentInformation.jsp" method="post" onSubmit="return fun_IncidentID()">		
								<input type="Text" name="incidentid" size="15">&nbsp;&nbsp;<input type="Image" name="Go" src="./images/go.gif" border="0" vspace="0" hspace="0">
							</form>
						</TD>
					</TR>										
					<TR>
						<TD colspan="3"><hr size="2" width="100%" color="white"></TD>	
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