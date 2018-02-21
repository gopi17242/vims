<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/tlds/c-1_0.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/extremecomponents.tld" prefix="ec" %>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ page import="java.util.*"%>

<html>
<head>
<TITLE>Application Specialist</TITLE>
<script type="text/javascript">
function getParameterMap(form) {
    var p = document.forms[form].elements;
    var map = new Object();
    for(var x=0; x < p.length; x++) {
        var key = p[x].name;
        var val = p[x].value;
        
        //Check if this field name is unique.
        //If the field name is repeated more than once
        //add it to the current array.
        var curVal = map[key]; 
        if (curVal) { // more than one field so append value to array
        	curVal[curVal.length] = val;
        } else { // add field and value
        	map[key]= [val];
        }
    }
    return map;
}

function setFormAction(form, action, method) {
	if (action) {
		document.forms[form].setAttribute('action', action);
	}
	
	if (method) {
		document.forms[form].setAttribute('method', method);
	}
	
	document.forms[form].ec_eti.value='';
}
</script>
</head>
<BODY marginheight="0" marginwidth="0" bgproperties="fixed" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" vlink="blue">


<%
int currPage=1;
int rowsPerPage=5;
int totalPages=0;
String attributeName="appSpecList";
String forwardPage="applicationSpecialist";

if(request.getAttribute("currPage")!=null)
	{
		currPage=((int)(Integer)request.getAttribute("currPage"));
	}
if(session.getAttribute(attributeName)!=null)
{
	int listSize=((ArrayList)session.getAttribute(attributeName)).size();
	if(listSize%rowsPerPage==0)
	{
		totalPages=listSize/rowsPerPage;
	}
	else
	{
		totalPages=(listSize/rowsPerPage)+1;
	}
}
%>
<html:form method="post" action="ApplicationSpecialists.do" >
<html:hidden property="Applications" value="applicationSpecialist"/>

<table border="0" width="65%" align="center" cellspacing="2" cellpadding="0" >
	<tr><td height="10px"></td></tr>
			
			<tr>
		<td align="center" colspan="2" height="10px"><Font color="blue"><c:out value="${resultMsg}"/></Font></td>
		
	</tr>
			
			<tr><td height="10px"></td></tr>
			<tr>
				<td colspan="2" align="center"><I><font color=red>*</font>Fields are Mandatory.</I></td>
				<td></td>
			</tr>
			<tr>
			<td></td>
			</tr>
	        <tr>	       
				<td class="labelField" align="left" width="20%" >Employee Name</TD>
				<td  align="left">
				<font color=red>*</font><html:select property="empId" styleClass="dropdownlist">
					 <html:option value="">---Select---</html:option>
					  <html:optionsCollection name="empList" value="empId" label="empName"/> 					 
				</html:select></td>
				<td class="labelField" align="left" width="33%"><font color="red" ><html:errors property="empId"/></font></td>
			</tr>
			<TR>				
				<td class="labelField" align="left" >Application Name</TD>
				<td  align="left">
					<font color=red>*</font><html:select property="appId" styleClass="dropdownlist">
						 <html:option value="">---Select---</html:option>
						 <html:optionsCollection name="appIdList" value="appId" label="appName"/> 
					</html:select>
				</TD>
				<td class="labelField" align="left"><font color="red" ><html:errors property="appId"/></font></td>
			</TR>
			<TR>	
				<td class="labelField" align="left" >Comments&nbsp;</TD>
				<TD class="labelField" align="left">&nbsp;&nbsp;<html:textarea rows='4' cols='25' property="appSpecComment"></html:textarea></TD>
				<td class="labelField" align="left"><html:errors property="appSpecComment"/></TD>				
			</TR>
			<tr><td height="10px"></td></tr>
			<TR>
				<td  align="center" colspan="3">
					<html:submit property="param" styleClass="linkbutton_background" accesskey="a">
						<bean:message key="VIMSApplication.addApplicationSpecialist"/>
					</html:submit>&nbsp;&nbsp;						
					<button type="Button" class="linkbutton_background" name="back" onClick="javascript:history.back()" accesskey="b">
					<u>B</u>ack
					</button>
				</td>					
			</TR>			
	</TABLE>
	</html:form>
	
	<ec:table items="appSpecList" sortable="true" action="./ApplicationSpecialistsLink.do?param=applicationSpecialist"
		imagePath="./images/*.gif" filterable="false" width="100%"
		rowsDisplayed="10">
		
		<ec:row highlightRow="true"> 
		
			<ec:column property="appId" title="Application ID"/>
			<ec:column property="appName" title="Application Name"/>
			<ec:column property="empId" title="Employee ID"/>
			<ec:column property="empName" title="Employee Name"/>
			<ec:column property="comments" title="Comments"/>
			
		</ec:row>
	</ec:table>
	
</body>
</html>

