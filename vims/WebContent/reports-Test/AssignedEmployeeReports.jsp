<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*" %>
<html>
<head>
<script language="javascript" src="./script-test/advPopCalender.js" type="text/javascript"></script>
<script type="text/javascript" src="./script-test/reports.js"></script>
<script type="text/javascript" src="./script-test/VIMS_Alerts.js"></script>

<script>
function submitForm(submitType) 
{
	var formObj=document.forms[0];
	formObj.action="./AssignedEmployeeReport.do?param=generateEmployeeReports"
   	formObj.submit();
	
}
function resetForm()
{
	var formObj=document.forms[0];
	formObj.action="./AssignedEmployeeReport.do?param=EmployeeReports"
   	formObj.submit();
}
 function fnGoBack()
  {
    window.location="./reportsHome.do?pageId=Reports&menuId=Report";
  }
  function enableFunction()
  {
  if(document.getElementById('resourceType').value!='workingResinGrps')
 	{
 		var formObj=document.forms[0];
		formObj.action="./AssignedEmployeeReport.do?param=employeeReports&pageId=EmployeeReports"
		formObj.submit();
 	} 	
  }
</script>
</head>	
<body>
<br>
	<table border="0" width="100%">
		
		<html:form action="VIMSReportsLookUpDispatchAction.do" >
		<tr>
			<td class="labelField" width="20%">Employee Type</td>
			<td  colspan="2" align="left">&nbsp;&nbsp;<html:select property="resourceType" style="width:250px" styleClass="labelField" onchange="enableFunction();">
			<html:option value="All">All</html:option>
			<html:option value="availableRes">Available resources in VIMS</html:option>
			<html:option value="availableResinGrps">Unassigned resources under Groups</html:option>
			<html:option value="workingResinGrps">Assigned resources under Groups</html:option>
			</html:select></td>
			</tr>			
		<tr>
			<td class="labelField" align="left" width="20%">Position</td>
			<td  align="left">&nbsp;&nbsp;<html:select property="role" styleId="roleSel" styleClass="file_Upload" onchange="fnGetEmployees(this.value);" style="width:180px">

			    	<!--<html:option value="">Select Position</html:option>-->
			    	<html:option value="All">All</html:option>
			    	<html:optionsCollection name="rolesList" value="PositionNo" label="PositionNames"/>
			    </html:select>&nbsp;<font color="red" size="2"><I><span id="roleMsg"></span></I></font>
			</td>    
		</tr>	
		<tr>
			<td class="labelField" align="left">Employee Name</td>
			<td colspan="2" align="left">&nbsp;&nbsp;<html:select property="empId" styleClass="file_Upload" styleId="empSel" style="width:250px">
			    	<html:option value="All">All</html:option>
			    	<c:if test="${AllEmployees ne null}">
		    		<html:optionsCollection name="AllEmployees" value="empId" label="empName"/>
		    		</c:if>
		    		<c:if test="${employeeList ne null}">
		    		<html:optionsCollection name="employeeList" value="empId" label="empName"/>
		    		</c:if>
			    </html:select>&nbsp;<font color="red" size="2"><I><span id="empNameMsg"></span></I></font>
			</td>    
		</tr>	
		<tr>
			<td class="labelField" align="left">Date Range From
		      <td  colspan="2" align="left">&nbsp;&nbsp;<html:text property="fromDate" readonly="true" styleId="fromDate" styleClass="textbox_default" style="width:80px"></html:text>
				 <script>
			          document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].fromDate,window.document.forms[0].fromDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
			      </script>
	              <font class="labelField">&nbsp;&nbsp;To&nbsp;</font>
			        <html:text property="toDate" styleId="toDate" readonly="true" styleClass="textbox_default" style="width:80px"></html:text>
	 			  <script>
	                  document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].toDate,window.document.forms[0].toDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
		         </script><font color="red" size="2"><I><span id="date"></span></I></font>
	         </td>
		</tr>
		<tr height="10px"><td colspan="2"></td></tr>
		<tr>
			<td width="20%">&nbsp;</td>
			<td align="left">&nbsp;&nbsp;<button type="button" class="linkbutton_background" accesskey="g" tabindex="8" onclick="submitForm('available');"><u>G</u>enerate</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="reset" class="linkbutton_background" accesskey="r" tabindex="9"><u>R</u>eset</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="linkbutton_background" tabindex="8" accesskey="r" onclick="fnGoBack();"><u>C</u>ancel</button></td>
		</tr>	
		<tr>
			<td colspan="2" align="left" class="labelField"><font color="red"><c:out value="${resultMsg}"/></font></td>    
		</tr>	
		</html:form>
	</table>	
</body>
</html>