<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="VIMSPagination" prefix="VIMS" %> 
<%@ taglib uri="/WEB-INF/tlds/extremecomponents" prefix="ec" %>

<html>
<head>
<script>
function gotoHome() {
 
   window.location="./home.do?method=getHomePageApplicationsList&menuId=Home&pageId=home";
}
</script>
</head>
<body>
<logic:present name="MSG">
     <font color="green"><c:out value="${MSG}"/></font>
</logic:present>

<ec:table items="newCustomerList" action="./viewNewCustomerList.do?client=getNewCustomersList"
		imagePath="./images/*.gif" filterable="false" width="100%"
		rowsDisplayed="10">
		<ec:row highlightRow="true"> 
			<ec:column property="customerName" title="Customer Name"/>
			<ec:column property="applicationName" title="Application Name"/>
			<ec:column property="emailID" title="E-mail"/>
			<ec:column property="mobileNO" title="Phone Number"/>
		 </ec:row>
	</ec:table>
</body>
<center><button type="button" onclick="gotoHome()" class="linkbutton_background" accesskey="c" tabindex="1"><u>C</u>ancel</button></center>
</html>