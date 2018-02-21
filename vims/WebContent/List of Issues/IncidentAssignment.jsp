<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>Vertex Issue Management System - Home Page</title>
<script>
	
function NewWin(OpenFile)
{
	if (navigator.appName == "Microsoft Internet Explorer")
	{
		window.open(OpenFile, "UploadFile", 'toolbar=no, status=yes, left=0, top=0, scrollbars=yes, resize=no,  width=840, height=600');
	}
	else if (navigator.appName == "Netscape") 
	{
		window.open("./NewIncidentUpload.jsp", "UploadFile",  'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=yes,resizable=0,width=450,height=350,top=100,left=200');
	}
}
	   function fnGetEmployees()
	    {	     
	     var formobj=document.forms[0];
	     formobj.action="./ListofIssues.do?methodname=IssueDetails";
 	     formobj.submit();
	    }
	     function fnAssignIssue()
	    {
	     var formobj=document.forms[0];
	     var assignedEmployee=formobj.SelectedEmployee.value;
	     var selectedEmployee=formobj.employee.value;
	     if(selectedEmployee=="")
	     {
	       alert("Please select an employee");
	       formobj.employee.focus();
	     }
	     else if(assignedEmployee==selectedEmployee)
	     {
	       if(confirm("Are you sure you want to assign the issue to same employee?"))
	       {
	        formobj.IssueStatus.value="Assign";
	        formobj.action="./ListofIssuesSubmit.do?methodname=AssignIssue&IssueStatus=Assign";
 	        formobj.submit();
 	        } 	    
	     }
	     else
	     {
	        formobj.IssueStatus.value="Assign";
	        formobj.action="./ListofIssuesSubmit.do?methodname=AssignIssue&IssueStatus=Assign";
 	        formobj.submit();
	      }
	    }
	    function funChangeStatus()
	    {
	      var formobj=document.forms[0];
	      formobj.IssueStatus.value="StatusChange";
	      formobj.action="./ListofIssuesSubmit.do?methodname=ChangeIssueStatus&IssueStatus=ChangeStatus";
 	      formobj.submit();
 	    }
 	    function fnAssignList()
 	    {
 	      var formobj=document.forms[0];
 	      var selectedValue=formobj.issueststus.value;
 	      if((selectedValue!='Closed')&&(selectedValue!=""))
 	      {
 	        formobj.action="./ListofIssuesSubmit.do?methodname=checkChangeStatus";
 	        formobj.submit();
 	      }
 	    }
 	      
 	      function fnBack()
 	      {
 	       window.location="./ListofIssues.do?methodname=BasePage&menuId=Issue&pageId=Issues";
 	       }
 	       window.onload=function(){fnLoad()}
 	       
 	       function fnLoad()
 	       {
 	       
 	       var formobj=document.forms[0];
 	       var role=formobj.role.value;
 	       //alert(role);
 	       if(role=='Admin')
 	       {
 	         //alert(role);
 	       if(formobj.issueststus!=null)
 	       {
 	       formobj.issueststus.selectedIndex=0;
 	       }
 	       }
 	         
 	      }
 	    function fnVerified()
 	    {
 	      var formobj=document.forms[0];
 	      var checked=formobj.Verified.checked;
 	      if(checked==true)
 	      {
 	        formobj.action="./ListofIssuesSubmit.do?methodname=IssueVerified";
 	        formobj.submit();
 	      }
      }
</script>
</head>
<body>

<html:form action="/ListofIssues.do?methodname=BasePage">
<br>
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >			
<input type="hidden" name="IssueStatus">
<tr><td height="10px" colspan="5" align="left" class="labelField"><font color="red"><c:out value="${MSG}"/></font></td></tr>
<c:forEach items="${IssueDetails1}"  var="IssueDetails1" step="1">
<tr>
	<td class="labelField"  align="left" width="22%"><b><bean:message key="VIMSApplication.IssueId"/></b></td>
	<td class="labelField" align="left" width="80%" colspan="4">&nbsp;&nbsp;<c:out value="${IssueDetails1.IssueId}"/></td>
</tr>
<input type="hidden" name="id" value='<c:out value="${IssueDetails1.IssueId}"/>'/>
<input type="hidden" name="role" value='<c:out value="${Role}"/>'/>
<tr>
	<td class="labelField"  align="left"><b><bean:message key="VIMSApplication.title"/>&nbsp;</b></td>
	<td class="labelField" align="left" colspan="4">&nbsp;&nbsp;<c:out value="${IssueDetails1.title}"/>&nbsp;</td>
</tr>

<tr>
	<td class="labelField"  align="left"><b><bean:message key="VIMSApplication.ApplicationName"/>&nbsp;</b></td>
	<td class="labelField" align="left" colspan="4">&nbsp;&nbsp;<c:out value="${IssueDetails1.ApplicationName}"/></td>	
</tr>
	<td class="labelField"  align="left"><b>Application Sub Category &nbsp;</b></td>
	<td class="labelField" align="left" colspan="4" valign="top">&nbsp;&nbsp;<c:out value="${IssueDetails1.appsubcategory}"/></td>	
<tr>
    
<tr>
    <td class="labelField"  align="left"><b><bean:message key="VIMSApplication.PostedBy"/>&nbsp;</b></td>
    <td class="labelField" align="left" colspan="4">&nbsp;&nbsp;<c:out value="${IssueDetails1.CustomerID}"/></td>
</tr>
<tr>
	<td class="labelField"  align="left"><b><bean:message key="VIMSApplication.PostedOn"/></b>&nbsp;</td>
	
	<td class="labelField" align="left">&nbsp;&nbsp;<c:out value="${IssueDetails1.FromDate}"/></td>
	<td class="labelField"  align="left" width="20%"><b><bean:message key="VIMSApplication.DueDate"/></b>&nbsp;</td>
	<td class="labelField" align="left">&nbsp;&nbsp;<c:out value="${IssueDetails1.DueDate}"/>&nbsp;</td>
</tr>
<tr>
	<td class="labelField"  align="left" ><b><bean:message key="VIMSApplication.CurrentStatus"/></b>&nbsp;</td>
	<td class="labelField" align="left">&nbsp;&nbsp;<c:out value="${IssueDetails1.status}"/></td>
	<td class="labelField"  align="left"><b><bean:message key="VIMSApplication.Severity"/></b>&nbsp;</td>
	<td class="labelField" align="left">&nbsp;&nbsp;<c:out value="${IssueDetails1.IncidentType}"/></td>
</tr>
<input type="hidden" name="IssueStatusFrom" value="<c:out value='${IssueDetails1.status}'/>"/>
<input type="hidden" name="ApplicationName" value="<c:out value='${IssueDetails1.ApplicationName}'/>"/>
<input type="hidden" name="Severity" value="<c:out value='${IssueDetails1.IncidentType}'/>"/>
<input type="hidden" name="SelectedEmployee" value="<c:out value='${strAssignedEmployeeID}'/>"/>
<input type="hidden" name="IssueTitle" value="<c:out value='${IssueDetails1.title}'/>"/>
</c:forEach>


<c:forEach items="${IssueDetails4}" var="IssueDetails4" step="1">

<tr>
	<td align="left" valign="top" class="labelField"><b><bean:message key="VIMSApplication.IssueDescription"/></b>&nbsp;</td>
	<td  colspan="4" align="left">&nbsp;&nbsp;<html:textarea property="description" rows="5" cols="60" styleClass="BigtextAreaDescription" readonly="true"><c:out value="${IssueDetails4.description}"/>
	</html:textarea></td>
</tr>
</c:forEach>
<tr>
	<td class="labelField" align="left" valign="top"><b><bean:message key="VIMSApplication.Attachments"/></b>&nbsp;</td>
	<td colspan="4" align="left" valign="top">
		<c:if test="${(FirstFile eq '') && (SecondFile eq '')&& (ThirdFile eq '')&& (FourthFile eq '')&& (FifthFile eq '')}"><font class="labelField">&nbsp;&nbsp;-None-</font></c:if>
		<logic:present name="FirstFile"><c:if test="${FirstFile ne ''}">
		<a href="#" class="issuesLinkbtn" onclick="NewWin('./VIMSUPLOAD/<c:out value="${FirstFile}"/>')">&nbsp;&nbsp;<c:out value="${FirstFile}"/></a><br>
		</c:if></logic:present>
		<logic:present name="SecondFile"><c:if test="${FirstFile ne ''}">
		<a href="#" class="issuesLinkbtn" onclick="NewWin('./VIMSUPLOAD/<c:out value="${SecondFile}"/>')">&nbsp;&nbsp;<c:out value="${SecondFile}"/></a><br>
		</c:if></logic:present>
		<logic:present name="ThirdFile"><c:if test="${FirstFile ne ''}">
		<a href="#" class="issuesLinkbtn" onclick="NewWin('./VIMSUPLOAD/<c:out value="${ThirdFile}"/>')">&nbsp;&nbsp;<c:out value="${ThirdFile}"/></a><br>
		</c:if></logic:present>
		<logic:present name="FourthFile"><c:if test="${FirstFile ne ''}">
		<a href="#" class="issuesLinkbtn" onclick="NewWin('./VIMSUPLOAD/<c:out value="${FourthFile}"/>')">&nbsp;&nbsp;<c:out value="${FourthFile}"/></a><br>
		</c:if></logic:present>
		<logic:present name="FifthFile"><c:if test="${FirstFile ne ''}">
		<a href="#" class="issuesLinkbtn" onclick="NewWin('./VIMSUPLOAD/<c:out value="${FifthFile}"/>')">&nbsp;&nbsp;<c:out value="${FifthFile}"/></a><br>
		</c:if></logic:present>
	</td>
</tr>
<tr>
	<td class="labelField" align="left" valign="top"><b>View History &nbsp;</b></td>
	<td align="left" valign="top">
		<a href="#" class="issuesLinkbtn" onclick="NewWin('./List of Issues/History.jsp')">&nbsp;&nbsp;History</a><br>
	</td>
	<c:if test="${(Role eq 'Admin') && ((CurrentIssueStatus eq 'Assigned')|| (CurrentIssueStatus eq 'Completed'))}">
	<logic:present name="strAssignedEmployeeName">
	<td class="labelField" align="left" valign="top"><b>Assigned To &nbsp;</b></td>
	<td class="labelField" align="left" valign="top">&nbsp;&nbsp;&nbsp;<c:out value="${strAssignedEmployeeName}"/></td>
	</logic:present></c:if>
	<!--<c:if test="${(Role eq 'Admin') && (CurrentIssueStatus eq 'Accepted')}">
	<logic:present name="strAssignedEmployeeName">
	<td class="labelField" align="left" valign="top"><b>Accepted By &nbsp;</b></td>
	<td class="labelField" align="left" valign="top">&nbsp;&nbsp;&nbsp;<c:out value="${strAssignedEmployeeName}"/></td>
	</logic:present></c:if>
	<c:if test="${(Role eq 'Admin') && (CurrentIssueStatus eq 'Rejected')}">
	<logic:present name="strAssignedEmployeeName">
	<td class="labelField" align="left" valign="top"><b>Rejected By &nbsp;</b></td>
	<td class="labelField" align="left" valign="top">&nbsp;&nbsp;&nbsp;<c:out value="${strAssignedEmployeeName}"/></td>
	</logic:present></c:if>-->
	<c:choose>
	<c:when test="${(Role eq 'Admin') && (CurrentIssueStatus eq 'Accepted')}">
	<logic:present name="strAssignedEmployeeName">
	<td class="labelField" align="left" valign="top"><b>Accepted By &nbsp;</b></td>
	<td class="labelField" align="left" valign="top">&nbsp;&nbsp;&nbsp;<c:out value="${strAssignedEmployeeName}"/></td>
	</logic:present>
	</c:when>
	<c:when test="${(Role eq 'Admin') && (CurrentIssueStatus eq 'Rejected')}">
	<logic:present name="strAssignedEmployeeName">
	<td class="labelField" align="left" valign="top"><b>Rejected By &nbsp;</b></td>
	<td class="labelField" align="left" valign="top">&nbsp;&nbsp;&nbsp;<c:out value="${strAssignedEmployeeName}"/></td>
	</logic:present>
	</c:when>
	</c:choose>
</tr>
<c:if test="${(Role eq 'Admin')||(Role eq 'Customer')}">
<c:if test="${(((CurrentIssueStatus eq 'Accepted') || (CurrentIssueStatus eq 'Closed')||(CurrentIssueStatus eq 'Assigned'))&&(StatusPermission ne '1') || (CurrentIssueStatus eq 'Closed'))}">
<tr>
<td class="labelField" align="left"><b><bean:message key="VIMSApplication.Groups"/></b></td>
	<td align="left" class="labelField">&nbsp;&nbsp;<c:out value="${GroupName}"/></td>
	</tr>
	<tr align=left"><td width="20%"></td>
	<td align="left">&nbsp;<button type="button" class="linkbutton_background" name="back" onClick="fnBack();" accesskey="c"><u>C</u>ancel</button></td>
</tr>
</c:if></c:if>
<c:if test="${(Role eq 'User')}"><c:if test="${(CurrentIssueStatus eq 'Closed')}">
<tr align=left"><td width="20%"></td>
	<td align="left">&nbsp;<button type="button" class="linkbutton_background" name="back" onClick="fnBack();" accesskey="c"><u>C</u>ancel</button></td>
</tr>
</c:if></c:if>
<c:if test="${Role eq 'Admin'}"><c:if test="${((CurrentIssueStatus eq 'Open')||(CurrentIssueStatus eq 'Escalated'))||((changeStatusFlag eq 'ChangeStatus'))}">
<tr>
	<td class="labelField" align="left" ><b><bean:message key="VIMSApplication.Groups"/></b></td>
	<td align="left" class="labelField">&nbsp;&nbsp;<c:out value="${GroupName}"/></td>
	<td class="labelField" align="left"><b>Group Employees</b></td>
	<td align="left"><c:if test="${CurrentIssueStatus ne 'Closed'}">
	<font color="red" class="labelMandatory">*</font>
	<html:select property="employee"  styleClass="dropdownlist" style="width:150px">
	<html:option value="">Select Employee</html:option>
	<logic:present name="GroupEmployee">
	<html:optionsCollection name="GroupEmployee" value="EmployeeId" label="EmployeeName"/>
	</logic:present>
	</html:select>
	</c:if>
	<c:if test="${CurrentIssueStatus eq 'Closed'}"><font class="labelField"><c:out value="${strAssignedEmployeeName}"/></font></c:if>
	</td>
</tr>
<tr><td></td><td align="left"><font color="red" size="2">&nbsp;&nbsp;&nbsp;<i><html:errors property="GroupError"></html:errors></i></font><br></td>
    <td></td><td align="left"><font color="red" size="2">&nbsp;&nbsp;&nbsp;<i><html:errors property="EmployeeError"></html:errors></i></font></td></tr>
<c:if test="${CurrentIssueStatus ne 'Closed'}">   
<tr>
	<td class="labelField" valign="top" align="left"><b><bean:message key="VIMSApplication.Comments"/></b>&nbsp;</td>
	<td  colspan="4" align="left">&nbsp;&nbsp;<html:textarea property="comments" rows="5" cols="60" styleClass="BigtextArea"/></td>
</tr>
<tr><td height="10px"></td></tr>
<tr>
	<td  align="center">&nbsp;</td>
	<td colspan="3" align="left">&nbsp;&nbsp;<button type="button" class="linkbutton_background" onClick=" return fnAssignIssue();" accesskey="a"><u>A</u>ssign</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" class="linkbutton_background" name="back" onClick="fnBack();" accesskey="c"><u>C</u>ancel</button>
	
   </td>
</tr>
</c:if>
<tr><td><br></td></tr>
</c:if>
</c:if>

<logic:present name="CurrentIssueStatus">
<logic:notPresent name="changeStatusFlag">
	<tr>
	 <c:if test="${Role eq 'Admin'}">
     <c:if test="${(CurrentIssueStatus eq 'Completed') || (CurrentIssueStatus eq 'Rejected')}">
	<td class="labelField" valign="top" align="left"><b>Change Status</b></td>
	<td colspan="3" width="20%">
		<font color="red" class="labelMandatory">*</font><html:select property="issueststus" styleClass="dropdownlist" style="width:190px" onchange="fnAssignList();">
		<html:option value="">Select Status</html:option>
		<!--<c:if test="${CurrentIssueStatus eq 'Completed'}">
		<html:option value="Accepted"><bean:message key="VIMSApplication.Accepted"/></html:option>
		</c:if>
		<html:option value="Rejected"><bean:message key="VIMSApplication.Rejected"/></html:option>-->
		<c:if test="${CurrentIssueStatus eq 'Completed'}">
		<html:option value="Re-opened"><bean:message key="VIMSApplication.Reopen"/></html:option>
		<logic:present name="InternalClose"><c:if test="${InternalClose eq 'Close'}">
		<html:option value="Closed"><bean:message key="VIMSApplication.Closed"/></html:option>
		</c:if>
		</logic:present>
		</c:if>
		<c:if test="${CurrentIssueStatus eq 'Rejected'}">
		<html:option value="Assigned"><bean:message key="VIMSApplication.Reassign"/></html:option>
		<logic:present name="InternalClose"><c:if test="${InternalClose eq 'Close'}">
		<html:option value="Closed"><bean:message key="VIMSApplication.Closed"/></html:option>
		</c:if>
		</logic:present>
		</c:if>
		<c:if test="${CurrentIssueStatus eq 'Completed'}">
		</c:if>
		</html:select>&nbsp;<font color="red" size="2"><i><html:errors property="ChangeStatusError"></html:errors></i></font>
		 <c:if test="${(Role eq 'Admin') && (CurrentIssueStatus eq 'Completed')}">
         <logic:present name="CustomerClose"><c:if test="${CustomerClose eq 'Verify'}">
         <logic:present name="IssueVerified"><c:if test="${IssueVerified eq '1'}">
         <input type="checkbox" name="Verified" checked disabled><font  class="labelField">Verified</font>
         </c:if>
         <c:if test="${IssueVerified ne '1'}">
         <input type="checkbox" name="Verified" onClick="fnVerified();"><font  class="labelField">Verified</font>
         </c:if>
         </logic:present>
         </c:if></logic:present>
          </c:if>
          </td>
   </c:if>
   </c:if>
  
      <c:if test="${((Role eq 'User')&&(CheckParam eq null))||((Role eq 'Admin')&&(StatusPermission eq '1'))}">
      <c:if test="${(CurrentIssueStatus ne 'Closed') && (CurrentIssueStatus ne 'Rejected') && (CurrentIssueStatus ne 'Completed')&& (CurrentIssueStatus ne 'Escalated')&&(CurrentIssueStatus ne 'Open')}">
      <td class="labelField" valign="top" align="left"><b>Change Status</b></td>
	  <td colspan="3">
   		<font color="red" class="labelMandatory">*</font><html:select property="issueststus" styleClass="dropdownlist">
   		<html:option value="">Select Status</html:option>
   		<c:if test="${(CurrentIssueStatus eq 'Accepted')}">
   		<html:option value="Completed"><bean:message key="VIMSApplication.Completed"/></html:option>
   		</c:if>
		<!--<html:option value="Can not reproduce"><bean:message key="VIMSApplication.CannotReproduce"/></html:option>
		<html:option value="Can not solve"><bean:message key="VIMSApplication.Can'tDo"/></html:option>-->
		<html:option value="Rejected"><bean:message key="VIMSApplication.Rejected"/></html:option>
		<c:if test="${(CurrentIssueStatus ne 'Accepted')}">
		<html:option value="Accepted"><bean:message key="VIMSApplication.Accepted"/></html:option>
		</c:if>
		</html:select>&nbsp;<font color="red" size="2"><i><html:errors property="ChangeStatusError"></html:errors></i></font></td>
    </c:if>
   </c:if>
  <c:if test="${Role eq 'Customer'}">
	  <c:if test="${CurrentIssueStatus eq 'Completed'}">
	  <td class="labelField" valign="top" align="left"><b>Change Status</b></td>
		<td colspan="3">
			<font color="red" class="labelMandatory">*</font><html:select property="issueststus" styleClass="dropdownlist">
	   		<html:option value="">Select Status</html:option>
	   		<html:option value="Re-opened"><bean:message key="VIMSApplication.Reopen"/></html:option>
	   		<html:option value="Closed"><bean:message key="VIMSApplication.Closed"/></html:option>
	   		</html:select>&nbsp;<font color="red" size="2"><i><html:errors property="ChangeStatusError"></html:errors></i></font></td>
	  </c:if>
	  
  </c:if>
 
</tr>

<tr><td></td></tr>
<tr>
<c:if test="${((Role eq 'Admin')&&(CurrentIssueStatus ne 'Open')&&(CurrentIssueStatus ne 'Closed')&&(CurrentIssueStatus ne 'Escalated') && ((CurrentIssueStatus eq 'Completed')|| (CurrentIssueStatus eq 'Rejected')||(StatusPermission eq '1')))||((Role eq 'User')&& (CurrentIssueStatus ne 'Closed')&& (CurrentIssueStatus ne 'Rejected')&& (CurrentIssueStatus ne 'Completed')&& (CurrentIssueStatus ne 'Escalated')) ||((Role eq 'Customer')&& (CurrentIssueStatus eq 'Completed'))}">
	<c:if test="${CheckParam eq null}">
	<td class="labelField" valign="top" align="left"><b><bean:message key="VIMSApplication.Comments"/></b></td>
	<td  colspan="3" align="left"><font color="red" class="labelMandatory">*</font><html:textarea property="reason" rows="5" cols="53" styleClass="BigtextArea"/>
	<font color="red" size="2"><i><html:errors property="ReasonError"></html:errors></i></font>
	</td>
	</c:if>
	<tr>
	<td colspan="4" align="center">&nbsp;</td>
</tr>


<tr><td  align="center" colspan="4" >

<c:if test="${CheckParam eq null}">
<button type="button"class="linkbutton_background"  name="escalate" onClick="funChangeStatus();" accesskey="s"><u>S</u>ubmit</button>&nbsp;&nbsp;&nbsp;&nbsp;
<button type="button" class="linkbutton_background" name="back" onClick="fnBack();" accesskey="c"><u>C</u>ancel</button>
</c:if>
</td></tr>

 </c:if>
</tr>

</logic:notPresent>
</logic:present>
<c:if test="${Role eq 'Admin'}">
 <html:hidden property="RoleType" value="Admin"/>
</c:if>
<c:if test="${Role eq 'User'}">	
  <html:hidden property="RoleType" value="User"/>
</c:if>
<c:if test="${Role eq 'Customer'}">
  <html:hidden property="RoleType" value="Customer"/>
</c:if>
</table>
</html:form>
</body>
</html>