<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<head>
<script src="./script-test/multifile.js"></script>
<script language="javascript">
	function fnChange()
	{
		document.getElementById("ChangeType").value="changed"; 
		var formobj=document.forms[0];
		formobj.action="./newIssueSub.do";
		formobj.submit(); 
	}
	
	function fnGetHours()
	{
 	  document.getElementById("ChangeType").value="GetSLAHours";
 	  var formobj=document.forms[0];
	  formobj.action="./newIssueSLA.do";
	  formobj.submit(); 
	}
function formSubmit()
{	
    if(document.getElementById("applicationName").value=="")
    {
     document.getElementById("applicationnameerror").innerHTML="Application Name is required";
    }
    else if(document.getElementById("title").value=="")
    {
     document.getElementById("titleerror").innerHTML="Title is required";
    }
    else if(document.getElementById("issueType").value=="")
    {
     document.getElementById("severityerror").innerHTML="Issue Severity is required";
    }
    else if(document.getElementById("issuePriority").value=="")
    {
     document.getElementById("priorityerror").innerHTML="Priority is required";
    }
    else
    {
     document.getElementById("ChangeType").value="Completed";
     var formobj=document.forms[0];
     formobj.action="./newIssues.do";
     formobj.submit();
     }
}
function fnReset()
{
 var formobj=document.forms[0];
 formobj.applicationName.value="";
 formobj.applicationSubCategory.value="";
 formobj.title.value="";
 formobj.reference.value="";
 formobj.issueType.value="";
 formobj.issuePriority.value="";
 formobj.description.value="";
 
 document.getElementById("ChangeType").value="Reset";
 formobj.action="./newIssueReset.do";
 formobj.submit();
 }

function fnGoBack()
{
  window.location="./ListofIssues.do?methodname=BasePage&menuId=Issue&pageId=Issues";
}
</script>
</head>
<body>
<br>
<html:form action="/newIssues" method="post" enctype="multipart/form-data" focus="applicationName">
<% if(request.getAttribute("message")!=null){ %>
	<%=request.getAttribute("message") %>
<% }else{%> 
<input type="hidden" name="ChangeType" id="ChangeType"/>

<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >
	<tr><td colspan="3" ><c:out value="${message}"/></td><td></td></tr>
	<tr>	       
		<td class="labelField" align="left" width="20%"><bean:message key="VIMSApplication.applicationName"/>&nbsp;</TD>
		<td  align="left" colspan="2" width="80%">&nbsp;<font color="red" class="labelMandatory">*</font><html:select property="applicationName" size="1" onchange="fnChange();" styleClass="dropdownlist" style="width:250px" tabindex="1">
  				<html:option value="">Select Application</html:option>
  				<html:optionsCollection name="applicationNames" value="id" label="name"/> 
			    </html:select>&nbsp;&nbsp;<font color="red" size="2"><i><span id="applicationnameerror"></span><html:errors property="ApplicationError"></html:errors></i></font></td>	
	</tr>
	<tr>	       
		<td class="labelField" align="left"><bean:message key="VIMSApplication.applicationsubcategory"/>&nbsp;</TD>
		<td  align="left" colspan="2">
		<%if(request.getAttribute("nosubcateg")==null){%>&nbsp;&nbsp;&nbsp;<html:select property="applicationSubCategory" styleClass="dropdownlist" style="width:250px" tabindex="2">
		        <html:option value="">Select Sub Category</html:option>
		        <logic:present name="AppSubCategory">
  				<html:options name="AppSubCategory"/>
  				</logic:present>
			    </html:select>&nbsp;&nbsp;&nbsp;<!--<font color="red" size="2"><i><html:errors property="ApplSubCatError"></html:errors></i></font>
	    --><%}else{ %><%=request.getAttribute("nosubcateg")%><%}%>
		</td>	
			
	</tr>	
	<TR>		
		<td class="labelField" align="left"><bean:message key="VIMSApplication.title"/></TD>
		<td align="left" colspan="2">&nbsp;<font color="red" class="labelMandatory">*</font><html:text property="title" styleClass="textbox_default" style="width:357px" maxlength="250" tabindex="3"/>&nbsp;&nbsp;<font color="red" size="2"><i><span id="titleerror"></span><html:errors property="TitleError"></html:errors></i></font></td>		
	</TR>
	<TR>
		<td class="labelField" align="left"><bean:message key="VIMSApplication.reference"/>&nbsp;</TD>
		<TD align="left" colspan="2">&nbsp;&nbsp;&nbsp;<html:text property="reference" styleClass="textbox_default" style="width:357px" maxlength="50" tabindex="4"/></TD>		
	</TR>
	<TR>		
		<td class="labelField" align="left"><bean:message key="VIMSApplication.issueSeverity"/></TD>
		<TD colspan="3">&nbsp;<font color="red" class="labelMandatory">*</font><html:select property="issueType" styleClass="dropdownlist" style="width:117px" onchange="fnGetHours();" tabindex="5">
		        <html:option value="">Select Severity</html:option>
		        <html:optionsCollection name="Issues" value="id" label="name"/>
			</html:select>
			<logic:present name="Hours"><font class="labelField">Response Time is <c:out value="${Hours}"/><c:out value="${strHourString}"/></font></logic:present>
			&nbsp;&nbsp;<font color="red" size="2"><i><span id="severityerror"></span><html:errors property="SeverityError"></html:errors></i></font></td>	
	</TR>
	<TR>		
		<td class="labelField" align="left">Priority</TD>
		<TD colspan="2">&nbsp;<font color="red" class="labelMandatory">*</font><html:select property="issuePriority" styleClass="dropdownlist" style="width:117px" tabindex="6">
		        <html:option value="">Select Priority</html:option>
		        <html:optionsCollection name="IssuePriority" value="Priorityid" label="priorityname"/>
			</html:select>&nbsp;&nbsp;<font color="red" size="2"><i><span id="priorityerror"></span><html:errors property="PriorityError"></html:errors></i></font>							
		</TD>		
	</TR>
	<TR valign="top">		
		<td class="labelField" align="left"><bean:message key="VIMSApplication.description"/></TD>
		<td  align="left" colspan="2">&nbsp;&nbsp;&nbsp;<html:textarea property="description" rows="7" style="width:357px" styleClass="textbox_default" cols="40" tabindex="7"/></TD>	
	</TR>
	<tr valign="top">		
		<td class="labelField" align="left">Attachments</td>
	    <td align="left"colspan="2">&nbsp;&nbsp;&nbsp;<input id="my_file_element" type="file"name="fileUpload" tabindex="8"><font class="labelField">Max 5 files.</font>
	    <input type="hidden" name="filesUploaded" id="filesUploaded"/>
	    </td></tr>
	    <tr><td></td>
	    <td colspan="2" align="right">
	    <table width="99%" STYLE="table-layout:fixed;">
	    <tr><td>
	    <div id="files_list" class="linkbtn"></div>
	    </td></tr>
	     <tr><td colspan="1" height="10px"></TD></tr>
	    <tr>
		<td  align="left"><button type="button" name="Submit" onClick="formSubmit()" class="linkbutton_background" accesskey="s" tabindex="10"><u>S</u>ubmit</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button" class="linkbutton_background" accesskey="r" tabindex="11" onclick="fnReset();"><u>R</u>eset</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button"  name="back" onClick="fnGoBack();" accesskey="c" class="linkbutton_background"><u>C</u>ancel</button>		
		</TD>	
		</tr>
	    
	    </table>
	    <div id="files_list" style="font-family:verdana;font-size:70%; font-weight:bold; width:440px; align:left;"></div></td></tr></table>
		<script>var multi_selector = new MultiSelector( document.getElementById( 'files_list' ),5);
	   	multi_selector.addElement( document.getElementById( 'my_file_element' ) );</script></td></tr>
	
<%}%>
</html:form>
</body>
</html>