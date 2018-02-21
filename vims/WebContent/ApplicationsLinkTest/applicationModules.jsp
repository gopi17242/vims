<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/tlds/c-1_0.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ page import="java.util.*"%>

<html:html>
<HEAD>
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
<TITLE>Product Specialist</TITLE>
</head>
<%
int currPage=1;
int rowsPerPage=8;
int totalPages=0;
String attributeName="appSubCatList";
String forwardPage="applicationModules";

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
<BODY marginheight="0" marginwidth="0" bgproperties="fixed" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" vlink="blue">


<table border="0" width="100%" align="left" cellspacing="1" cellpadding="0" >
	
			
			<tr>
		<td colspan="2" height="10px"><Font color="blue"><c:out value="${resultMsg}"/></Font></td>
		
	</tr>
		<tr>
				<td colspan="3" align="left"><I><font color=red>*</font>Fields are Mandatory.</I></td>
			</tr>
			<html:form method="post" action="ApplicationModules.do">
			<html:hidden property="Applications" value="applicationModules"/>
	        <tr>	       
				<td class="labelField" align="left" width="20%">Application Name</TD>
				<td  align="left" width="80%">
				<font color=red>*</font><html:select property="appId" styleClass="dropdownlist">
					<html:option value="">---Select---</html:option>
					<html:optionsCollection name="appIdList" value="appId" label="appName"/> 
				</html:select>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="appId"/></font></td>
			</tr>
			<TR>				
				<td class="labelField" align="left" width="20%">Sub Category Name</TD>
				<td align="left">&nbsp;&nbsp;<html:text property="appSubCatName" size="20" styleClass="textbox_default"/>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="appSubCatName"/></font></td>				
			</TR>			
			<tr><td height="10px"></td></tr>
			<TR>
				<td  align="center" colspan="3">
					<html:submit property="param" accesskey="a" styleClass="linkbutton_applicationModules"><bean:message key="VIMSApplication.addAppSubCategories"/></html:submit>&nbsp;&nbsp;						
					<button type="Button" class="linkbutton_background" name="back" onClick="javascript:history.back()" accesskey="b"><u>B</u>ack</button>
				</td>					
			</TR>	
			</html:form>
			<tr>
			<td colspan="3" align="left">
			<ec:table items="appSubCatList" sortable="true" action="./ApplicationModulesLink.do?param=applicationModules"
		imagePath="./images/*.gif" filterable="false" width="100%"
		rowsDisplayed="10" cellspacing="2" cellpadding="2" border="0">
		
		<ec:row highlightRow="true"> 		
			<ec:column property="appId" title="Application ID"/>
			<ec:column property="appName" title="Application Name"/>
			<ec:column property="appSubCat" title="Application Sub Category"/>			
		</ec:row>
	</ec:table>
			</td>
			</tr>		
	</TABLE>
<br>


</body>
</html:html>

