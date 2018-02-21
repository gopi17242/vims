<%@ taglib uri="/WEB-INF/tlds/c-1_0.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/extremecomponents.tld" prefix="ec" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html>
<head>
<script>
function gotoHome() {
 
   window.location="./home.do?method=getHomePageApplicationsList&menuId=Home&pageId=home";
}
</script>
</head>
<body>
	
	<Center><c:out value="${resultMsg}"/></Center>
	
	<table border="0" width="100%">
	<c:if test="${applicationDetails ne null}">
	<tr>

		<td colspan="5" align="center" class="heading_blue">
			Applications
		</td>
		
	</tr>
	<tr>
		<td colspan="5">
			<ec:table items="applicationDetails" action="./viewNewApplicationList.do?param=getNewApplicationList&menuId=Application&pageId=Applications"
			imagePath="./images/*.gif" filterable="false" width="100%"
				rowsDisplayed="10">
			
			<ec:row highlightRow="true"> 
				<ec:column property="appName" title="Application Name"/>
				<ec:column property="appOwner" title="Application Owner"/>
				<ec:column property="supBegDate" format="MM/dd/yyyy" cell="date" title="Start Date"/>
				<ec:column property="supEndDate" format="MM/dd/yyyy" cell="date" title="End Date"/>
				<ec:column property="supportManager" title="Support Manager"/>
			</ec:row>
			
		</ec:table>
		</td>
	</tr>
  </c:if>	
</table>
<center><button type="button" onclick="gotoHome()" class="linkbutton_background" accesskey="c" tabindex="1"><u>C</u>ancel</button></center>
	</body>
</html>