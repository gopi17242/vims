
<%@ taglib uri="/WEB-INF/tlds/extremecomponents.tld" prefix="ec" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<head>
<script type="text/javascript" src="./script-test/applications.js"></script>
<script type="text/javascript" src="./script-test/autoComplete.js"></script>
<script language="javascript">
function fnDelete(appId)
{
	alert("Currently this facility is not available");
}
function fnSearchApplication()
{    
	   var formobj=document.forms[0];
	   formobj.action="./VIMSApplicationLookupDispatchAction.do?param=searchApplication";
	   formobj.submit();
} 
function fnGoBack()
{
  window.location="./viewApplicationList.do?param=viewApplicationList&menuId=Application&pageId=Applications";
}
</script>
<style type="text/css">

.popupItem {
  background: #FFFAFA;
  color: #000000;
  text-decoration: none;
  font-size: 12;
}

.popupItem:hover {
  background: #7A8AFF;
  color: #FFFAFA;
}

.popupRow {
  background: #FFFAFA;
}

</style>
</head>
	<body><br>
	<table border="0" width="100%">
	<html:form action="VIMSApplicationLookupDispatchAction.do" focus="searchKey">
	<tr valign="top">
		<td colspan="3" width="100%" align="center">
			<h4><font size="2" color="green"><c:out value="${resultMsg}"/></font></h4>
		</td>
	</tr>	
	<tr>
		<td width="20%" align="left"><font class="labelfield">Application Name</font></td>
		<td width="80%">&nbsp;<input type="text"  id="appKey" style="width:250px" name="searchKey" style="width:150px" class="textBox_default"/>&nbsp;&nbsp;<input type="image" src="./images/goButton.gif"  style="position:absolute" onclick="fnSearchApplication();"/></td>
	</tr>
	<tr>
		<td width="20%" align="left"><font class="labelfield">Customer Name</font></td>
		<td width="80%">&nbsp;<input type="text"  id="custKey" style="width:250px" name="custName" value="" style="width:150px" class="textBox_default"/></td>
	</tr>
	<c:if test="${searchMsg ne null}">
	<tr>
		<td  width="20%" align="left">&nbsp;&nbsp;</td>
		<td width="80%"><font class="labelfield">&nbsp;&nbsp;<c:out value="${searchMsg}"/></font><br>
		&nbsp;<button type="button"  name="back" onClick="fnGoBack();" accesskey="b" class="linkbutton_background"><u>B</u>ack</button></td>
	</tr>
	</c:if>		
	</html:form>
	<tr>
		<td colspan="3" width="100%"><c:if test="${searchMsg eq null}">
			<ec:table items="applicationDetails" action="./viewApplicationList.do?param=viewApplicationList"
				imagePath="./images/*.gif" filterable="false" width="100%"
					rowsDisplayed="10">	
					<ec:row highlightRow="true">				
						<ec:column property="appName" title="Application Name" width="15%"/>
						<ec:column property="appStatus" title="Application Status" width="10%"/>
						<ec:column property="customerName" title="Customer Name" width="16%"/>
						<ec:column property="appOwner" title="Application Owner" width="12.5%"/>
						<ec:column property="supBegDate" format="MM/dd/yyyy" cell="date" title="Start Date" width="13%"/>
						<ec:column property="supEndDate" format="MM/dd/yyyy" cell="date" title="End Date" width="12.5%"/>
						<ec:column property="supportManager" title="Support Manager" width="12%"/>
						<ec:column property="modifyDeleteLink" title="Actions" width="9%"/>
					</ec:row>
				</ec:table></c:if>
		</td>
	</tr>
	
	</table>
	</body>
</html>