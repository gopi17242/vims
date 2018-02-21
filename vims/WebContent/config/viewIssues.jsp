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
	
	  <table border="0" width="100%">
			<c:if test="${list ne null}">
				<tr>
					<td colspan="5" align="center" class="heading_blue">
						<c:out value="${issueTypeSelected}" />  Issues
					</td>
					
				</tr>
				<tr>
						<td colspan="5">
					        <ec:table items="list" sortable="true" action="./viewIssuesList.do?param=getSelectedTypeIssues"
							imagePath="./images/*.gif" filterable="false" width="100%"
							rowsDisplayed="10">
								<ec:row highlightRow="true"> 
									<ec:column property="incident_id" title="Issue ID"/>
									<ec:column property="application_name" title="Application Name"/>
									<ec:column property="inct_status" title="Status"/>
									<ec:column property="incident_posted_date" title="Posted Date"/>
								    <ec:column property="closed_date" title="Closed Date"/>		    
								</ec:row>
					     </ec:table>
				       </td>
				</tr>
		  </c:if>	
	   </table>
	   <center><button type="button" onclick="gotoHome()" class="linkbutton_background" accesskey="c" tabindex="1"><u>C</u>ancel</button></center>
	</body>
</html>
