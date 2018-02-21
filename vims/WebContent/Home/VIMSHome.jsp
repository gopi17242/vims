<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<HTML>
<HEAD>
<script src="./script-test/common.js"></script>
<script src="./script-test/homePage.js"></script>	
<LINK href="./css/StyleSheet.css" rel="STYLESHEET" type="text/css">
</HEAD>
<BODY marginheight="0" marginwidth="0" topmargin=0 leftmargin=0 rightmargin="0" bottommargin="0" vlink="Blue" >
 <br>
<logic:present name="UPDTMSG">
     <font color="green"><c:out value="${UPDTMSG}"/></font>
</logic:present>

<!--parent table starts -->
<TABLE border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
			<TR>
			<TD class="fieldhead" colspan="2" ALIGN="Left" VALIGN="TOP" width="70%">
			<!--child table 1 table starts -->
				<table border="0" cellspacing="0" cellpadding="0">				  
				<c:if test="${(Role eq 'Admin')||(Role eq 'Suprvisor')||(Role eq 'Manager') }">
					<!--child table 2 table starts -->	
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<c:if test="${tableDisplayFlag ne 'null'}">
					<tr>
					<td  colspan="2">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" align="RIGHT" valign="TOP" bgcolor="f8f8f8" bordercolor="white" >
		                    <tr height="10" width="100%">
						          <td class="heading_blue" width="100%">
                  					<c:if test="${applicationCount ne null}" >
                  					 <c:if test="${applicationCount ne 0}" >
									[<a href="./viewNewApplicationList.do?param=getNewApplicationList&menuId=Application&pageId=Applications"> <c:out value="${applicationCount}" />  </a>] ... <c:out value="New Applications Added" />
									<br>
									</c:if>
									</c:if>
									<c:if test="${openIssueCount ne null}" >
									[<a href="./viewIssuesList.do?param=getSelectedTypeIssues&issueType=open&menuId=Issue&pageId=Issues"> <c:out value="${openIssueCount}" /></a>  ] ... <c:out value="New Issues Open" />
									<br>
									</c:if>
					    			<c:if test="${closedIssueCount ne null}" >
									[<a href="./viewIssuesList.do?param=getSelectedTypeIssues&issueType=closed&menuId=Issue&pageId=Issues"> <c:out value="${closedIssueCount}" /> </a> ] ... <c:out value="Issues Closed" />
									<br>
									</c:if>
						    		<c:if test="${escalatedIssueCount ne null}" >
									[<a href="./viewIssuesList.do?param=getSelectedTypeIssues&issueType=escalated&menuId=Issue&pageId=Issues"> <c:out value="${escalatedIssueCount}" /> </a> ] ... <c:out value="Issues Escalated" />
									<br>
									</c:if>
							    	<c:if test="${assignedIssueCount ne null}" >
									[<a href="./viewIssuesList.do?param=getSelectedTypeIssues&issueType=assigned&menuId=Issue&pageId=Issues"> <c:out value="${assignedIssueCount}" /> </a> ] ... <c:out value="Issues Assigned" />
									<br>
									</c:if>
									<c:if test="${rejectedIssueCount ne null}" >
									[<a href="./viewIssuesList.do?param=getSelectedTypeIssues&issueType=rejected&menuId=Issue&pageId=Issues"> <c:out value="${rejectedIssueCount}" /> </a> ] ... <c:out value="Issues Rejected" />
									<br>
									</c:if>
									<c:if test="${completedIssueCount ne null}" >
									[<a href="./viewIssuesList.do?param=getSelectedTypeIssues&issueType=completed&menuId=Issue&pageId=Issues"> <c:out value="${completedIssueCount}" /> </a> ] ... <c:out value="Issues Completed" />
									<br>
									</c:if>
									<c:if test="${reOpenIssueCount ne null}" >
									[<a href="./viewIssuesList.do?param=getSelectedTypeIssues&issueType=reopened&menuId=Issue&pageId=Issues"> <c:out value="${reOpenIssueCount}" /> </a> ] ... <c:out value="Issues Reopened" />
									<br>
									</c:if>
									<c:if test="${acceptedIssueCount ne null}" >
									[<a href="./viewIssuesList.do?param=getSelectedTypeIssues&issueType=accepted&menuId=Issue&pageId=Issues"> <c:out value="${acceptedIssueCount}" /> </a> ] ... <c:out value="Issues Accepted" />
									<br>
									</c:if>
									<c:if test="${newCustomerCount ne null}" >
									[<a href="./viewNewCustomerList.do?client=getNewCustomersList&menuId=Customer&pageId=Customers"> <c:out value="${newCustomerCount}" /> </a> ] ... <c:out value="New Customers Added" />
									</c:if>
								</td>
              				  </TR>
				          </table>
				         </td>
					   </tr> 					  
				      </c:if>
					<tr>						
						<td width="70%">
					        <!-- <p class="bodytext">Thank you for using Vertex Issue Management System, the ultimate Tracking and Resolution System for the Global Support Services.</p>-->
							<span class="heading_blue"><b>Support Centers</b><br></span>
							<font class="labelfield">Information related to support center is displayed. New support center creation, modification and deletion functionalities can be done.</font>
							<br><br><span class="heading_blue"><b>Groups</b><br></span>
							<font class="labelfield">Information related to groups are displayed. New group creation, modification and deletion functionalities can be done.</font>
							<br><br><span class="heading_blue"><b>Applications</b></span>
							<br>
							<font class="labelfield">Stores all the information related to applications, i.e. creating, modifying and deletion of applications. Assigning of specialists, groups and modules to applications. Mapping of customers to applications.</font>
							<br><br><span class="heading_blue"><b>SLA</b></span>
							<br>
							<font class="labelfield">Defines Service Level Agreement (SLA) for an application.</font>
							<br><br><span class="heading_blue"><b>Customers</b></span>
							<br>
							<font class="labelfield">Stores all your client details in one central location.</font>
							<br><br><span class="heading_blue"><b>Employees</b></span>
							<br>
							<font class="labelfield">All employee's information can be viewed. Profile of new employees can be created or profiles of existing employees can be modified.</font>
							<br><br><span class="heading_blue"><b>Issues</b><br></span>
							<font class="labelfield">Information related to Issues is displayed. New Issue can be created.Purging and Escalation activities can be performed.</font>
							<br><br><span class="heading_blue"><b>Reports</b></span>
							<br>
							<font class="labelfield">Various reports are generated to give idea of how much time is being spent on a particular issue or how old is an issue, etc.</font>
							<br><br><span class="heading_blue"><b>Search</b><br></span>
							<font class="labelfield">Search can be done based on criteria such as Application, Issue ID, etc.</font>
							<br><br><span class="heading_blue"><b>Configuration Settings</b><br></span>
							<font class="labelfield">Configuration for Home Page, LDAP Server, Mail Server, KeyWords and Access Privilages can be done.</font>
							</td>
							</tr>
							</c:if>
							
							<c:if test="${Role eq 'User' }">
							<tr>						
						    <td width="70%">	
							<span class="heading_blue"><b>Issues</b><br></span>
							<font class="labelfield">Issues are posted, viewed based on the status and archived for future reference. Escalated issues can be also displayed.</font>
							<br><br><span class="heading_blue"><b>My To Do List</b><br></span>
							<font class="labelfield">Issues which are assigned for the logged in user are displayed.</font>
							<br><br><span class="heading_blue"><b>Search</b><br></span>
							<font class="labelfield">Search can be done based on criteria such as Application, Issue ID, etc.</font>
							</td>
							
							</tr>
							<tr>
							<td height="90px">
							</td>
							</tr>
							</c:if>
							<c:if test="${Role eq 'Customer' }">
							<tr>						
						    <td width="70%">
							<span class="heading_blue"><b>Issues</b><br></span>
							<font class="labelfield">Issues can be posted and  can view issues based on the status.<br>Issues can be archived  for future reference and  escalated issues can be displayed here.</font>
							<br><br><span class="heading_blue"><b>Search</b><br></span>						
							<font class="labelfield">Search can be done based on criteria such as Application, Issue ID, etc.</font>
							<br><br><span class="heading_blue"><b>Change Password</b><br></span>
							<font class="labelfield">Customer can change his password.</font>
							</td>
							</tr>
								<tr>
							<td height="92px">
							</td>
							</tr>
							</c:if>								
						</table>
				<!--child table 1 table closed -->	
				
			</TD>
			
			<c:if test="${Role eq 'Admin' }">	
			<td width="20%" valign="TOP">
			<!--child table 4 table starts -->	
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="RIGHT" valign="TOP" bgcolor="f8f8f8" bordercolor="white" >
					
					<tr>
					<td width="100%" colspan="3" align="left" height="5px"></td>
					</tr>
					<TR>
						<td width="100%" colspan="3" align="left" bgcolor="#64a6d8" class="fieldhead"><font color="white" class="heading_white">Issue Search</font> </TD>
					</TR>
					<TR>
					<html:form action="ListofIssues.do?methodname=IssueDetails">
						<td colspan="3" class="labelField" width="33%">
							<!--child table 5 table starts -->	
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							<td align="left" class="labelField" style="width:25%">Issue ID</td>
							<td align="left" style="width:25%"><html:text property="issueId" styleClass="textbox_short" style="width:120px" maxlength="16"/></td>
							<td align="left" valign="middle"><input type="Image" name="Go" align="middle" src="./images/goButton.gif" border="0" vspace="0"  hspace="5"></td>
							</tr>						
						</table>
					<!--child table 5 table closed -->	
					</td>
					</html:form>
				</TR>
				<tr>
					<td colspan="3" class="labelField" width="100%"><FONT color="red"><c:out value="${InValidIssueId}"/></FONT></td>
				</tr>
				<tr>
					<td height="10px"></td>
				</tr>
				
				<TR>
					<td width="100%" colspan="3" align="left" bgcolor="#64a6d8" class="fieldhead"><font color="white" class="heading_white">SLA Details</font> </TD>
				</TR>
			
				<TR>				
					<html:form action="homeIssueDetails.do?getSLA=getHomeIssueDetails">
						<TD width="100%" COLSPAN="3" >
							<c:if test="${ApplicationsList ne 'null'}">
								
								 <html:select property="applicationId" size="1" style="WIDTH: 280px" styleClass="dropdownlist" onchange="getHomeSLADetailsDisplay();">
					 			    <html:option value="select an application">Select Application</html:option> 	
				  					<html:optionsCollection name="ApplicationsList" value="id" label="ApplicationName"/>
								</html:select> 
			 				</c:if>
						</TD>
					</html:form>
				</TR>
			<logic:present name="HomeOpenAndSLADetailsAction">				
			
				<TR>
					<TD width="35%" bgcolor="eeeeee" class="labelField">&nbsp;Critical</TD>
					<TD width="5%" bgcolor="eeeeee" class="labelField">&nbsp;:&nbsp;</TD>
					<TD width="60%" bgcolor="eeeeee" CLASS="labelField">&nbsp;<bean:write property="CriticalValue" name="HomeOpenAndSLADetailsAction"/> hrs</TD>
				</TR>
				<TR>
					<TD width="35%" class="labelField" bgcolor="eeeeee">&nbsp;Major</TD>
					<TD width="5%" class="labelField" bgcolor="eeeeee">&nbsp;:&nbsp;</TD>
					<TD width="60%" CLASS="labelField" bgcolor="eeeeee">&nbsp;<bean:write property="MajorValue" name="HomeOpenAndSLADetailsAction"/> hrs</TD>
				</TR>	
				<TR>
					<TD width="35%" class="labelField" bgcolor="eeeeee">&nbsp;Minor</TD>
					<TD width="5%" class="labelField" bgcolor="eeeeee">&nbsp;:&nbsp;</TD>
					<TD width="60%" CLASS="labelField" bgcolor="eeeeee">&nbsp;<bean:write property="MinorValue" name="HomeOpenAndSLADetailsAction"/> hrs</TD>
				</TR>					
				
				<TR>
				<c:if test="${HomeOpenAndSLADetailsAction.OpenIssuesCount ne 0}">
					<TD COLSPAN="3" align="left" CLASS="labelField" bgcolor="eeeeee">&nbsp;Currently there are [<Strong><html:link action="./homeSLA.do?getSLA=displayHomeOpenIssueDetails&inctType=all&menuId=Issues&pageId=ListOfIssues" styleClass="linkbtn"><bean:write property="OpenIssuesCount" name="HomeOpenAndSLADetailsAction"/></html:link></Strong>] Issues.</TD>		
				</c:if>
				<c:if test="${HomeOpenAndSLADetailsAction.OpenIssuesCount eq 0}">
					<TD COLSPAN="3" align="left" CLASS="labelField" bgcolor="eeeeee">&nbsp;Currently there are [<Strong><bean:write property="OpenIssuesCount" name="HomeOpenAndSLADetailsAction"/></Strong>] Issues.</TD>		
				</c:if>
				</TR>
				<TR>
					<TD width="40%" class="labelField" bgcolor="eeeeee">&nbsp;Critical Issues</TD>
					<TD width="5%" class="labelField" bgcolor="eeeeee">&nbsp;:&nbsp;</TD>
					<c:if test="${HomeOpenAndSLADetailsAction.CriticalOpenIssuesCount ne 0}">
					<TD width="60%" CLASS="labelField" bgcolor="eeeeee">&nbsp;<html:link action="./homeSLA.do?getSLA=displayHomeOpenIssueDetails&inctType=cri&menuId=Issues&pageId=ListOfIssues" styleClass="linkbtn"><bean:write property="CriticalOpenIssuesCount" name="HomeOpenAndSLADetailsAction"/></html:link></TD>
					</c:if>
					<c:if test="${HomeOpenAndSLADetailsAction.CriticalOpenIssuesCount eq 0}">
					<TD width="60%" CLASS="labelField" bgcolor="eeeeee">&nbsp;<bean:write property="CriticalOpenIssuesCount" name="HomeOpenAndSLADetailsAction"/></TD>
					</c:if>
				</tr>
				<TR>
					<TD width="40%" class="labelField" bgcolor="eeeeee">&nbsp;Major Issues</TD>
					<TD width="5%" class="labelField" bgcolor="eeeeee">&nbsp;:&nbsp;</TD>
					<c:if test="${HomeOpenAndSLADetailsAction.MajorOpenIssuesCount ne 0}">
					<TD width="60%" CLASS="labelField" bgcolor="eeeeee">&nbsp;<html:link action="./homeSLA.do?getSLA=displayHomeOpenIssueDetails&inctType=maj&menuId=Issues&pageId=ListOfIssues" styleClass="linkbtn"><bean:write property="MajorOpenIssuesCount" name="HomeOpenAndSLADetailsAction"/></html:link></TD>
					</c:if>
					<c:if test="${HomeOpenAndSLADetailsAction.MajorOpenIssuesCount eq 0}">
					<TD width="60%" CLASS="labelField" bgcolor="eeeeee">&nbsp;<bean:write property="MajorOpenIssuesCount" name="HomeOpenAndSLADetailsAction"/></TD>
					</c:if>
				</tr> 
				<TR>
					<TD width="40%" class="labelField" bgcolor="eeeeee">&nbsp;Minor Issues</TD>
					<TD width="5%" class="labelField" bgcolor="eeeeee">&nbsp;:&nbsp;</TD>
					<c:if test="${HomeOpenAndSLADetailsAction.MinorOpenIssuesCount ne 0}">
					<TD width="60%" CLASS="labelField" bgcolor="eeeeee">&nbsp;<html:link action="./homeSLA.do?getSLA=displayHomeOpenIssueDetails&inctType=min&menuId=Issues&pageId=ListOfIssues" styleClass="linkbtn"><bean:write property="MinorOpenIssuesCount" name="HomeOpenAndSLADetailsAction"/></html:link></TD>
					</c:if>
					<c:if test="${HomeOpenAndSLADetailsAction.MinorOpenIssuesCount eq 0}">
					<TD width="60%" CLASS="labelField" bgcolor="eeeeee">&nbsp;<bean:write property="MinorOpenIssuesCount" name="HomeOpenAndSLADetailsAction"/></TD>
					</c:if>
				</tr>
				
				</logic:present>											
			</table>
			<!--child table 4 table closed -->	
			
			</td>
			
			</c:if>
			 <td  valign="top" >
			<c:if test="${Role eq 'User'}">				 
			<html:form action="ListofIssues.do?methodname=UserHomePageIssue">		
			<table border="0" cellspacing="0" cellpading="0" width="100%">			
			<tr> <td height="6px"></td></tr>
			<tr>
 			  <td colspan="3" bgcolor="#64a6d8" class="fieldhead" align="left"><font color="white" class="heading_white">Issue Search</font></td>
			</tr>
			<tr>
			    <td  class="labelField" style="width:30%">Issue ID</td>
			    <td align="left" style="width:60%"><html:text style="width:130px" property="issueId" styleClass="textbox_default" maxlength="16"/></td>
			    <td align="left" valign="middle" align="right" style="width:29px"><input type="Image" name="Go" align="middle" src="./images/goButton.gif" border="0" vspace="0"  hspace="0"></td>
			</tr>
			<tr>
			<td colspan="3" class="labelField" width="100%"><FONT color="red"><c:out value="${InValidIssueId}"/></FONT></td>
			</tr>
			<tr>
			</tr>
			<tr><td COLSPAN=3 align="left" CLASS="labelField">Currently there are:[<a href='./homeSLA.do?getSLA=displayUserOpenIssueDetails&issueType=Assiged' class="linkbtn"><c:out value="${AssignCount}"/></a>]Assigned Issues</td></tr>
			</table>
			</html:form>
			</c:if>	
			</td>
			
			<td  valign="top" >
			<c:if test="${Role eq 'Customer'}">				 
			<html:form action="ListofIssues.do?methodname=UserHomePageIssue">		
			<table border="0" cellspacing="0" cellpading="0" width="100%">			
			<tr> <td height="6px"></td></tr>
			<tr>
 			  <td width="100%" colspan="3" bgcolor="#64a6d8" class="fieldhead" align="left"><font color="white" class="heading_white">Issue Search</font></td>
			</tr>
			<tr>
			    <td  class="labelField" style="width:30%">Issue ID</td>
			    <td align="left" style="width:60%"><html:text style="width:120px" property="issueId" styleClass="textbox_default" maxlength="16"/></td>
			    <td align="left" valign="middle"><input type="Image" name="Go" align="middle" src="./images/goButton.gif" border="0" vspace="0"  hspace="5"></td>
			</tr>
			<tr>
			<td colspan="3" class="labelField" width="100%"><FONT color="red"><c:out value="${InValidIssueId}"/></FONT></td>
			</tr>
			</table>
			</html:form>
			</c:if>	
			</td>			
			</TR>
			
			
		</TABLE>
	   <c:if test="${Role eq 'Admin' }">	
		<c:if test="${selectedApplicationId ne 'null'}">
		<script>		
		var test=document.getElementById("applicationId");
		
		for(j=0;j<test.length;j++) {
			if(test.options[j].value=='<c:out value="${selectedApplicationId}"/>') 
			{  
				test.selectedIndex=j;
				 
			 }
		  }
		</script>
	  </c:if>
	</c:if>
	<!--<c:if test="${selectedApplicationId eq 'null'}">
	<script>		
		var test=document.getElementById("applicationId");
		test.selectedIndex=-1;		
		</script>
	</c:if>
--></BODY>
</HTML>