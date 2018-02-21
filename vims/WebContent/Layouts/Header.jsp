
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/datetime.tld" prefix="date" %>
<%@ taglib uri="/WEB-INF/tlds/VIMSBreadCrumbs.tld" prefix="crumb"%>

<html>
<head>
<script>
function openVersionWindow() {
   
    if (navigator.appName == "Microsoft Internet Explorer")
	{
	 	window.open('./test-errorPages/version.html', "VIMS", 'toolbar=no, status=no, left=360, top=250, scrollbars=no, resize=no,  width=350, height=300');
	}
	else if (navigator.appName == "Netscape") 
	{
       window.open("./test-errorPages/version.html", "VIMS",  'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=no,resizable=0,width=350,height=300,top=250,left=360');		
	}
}
</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0">
<c:if test="${(Role==null)||(pageContext.session.id==null)}" >
<script>
window.location.href='./test-errorPages/VIMSSessionErrorpage.jsp';
</script>
</c:if>
<table width="100%" border="0" cellspacing="0"  cellpadding="0">
 <tr>
	<td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="22%"><img src="images/vertexLogo.png" width="178" height="63" /></td>
	<td width="64%" align="center"><img src="images/headerText.gif" width="363" height="30" /></td>
	<td width="14%" align="right" valign="top">
	<table width="65%" border="0" cellspacing="2" cellpadding="0">
		  <tr>
			<c:if test="${status ne '0'}">
			<td width="37%"><a href="./home.do?method=getHomePageApplicationsList&menuId=Home&pageId=home"><img src="images/home.gif" width="66" height="21" border="0"/></a></td>
			<td width="26%" align="right"><a href="./help.do?menuId=help&pageId=Help"><img src="images/help.gif" width="49" height="16"  border="0" /></a></td>
			</c:if>
			<td width="37%"><a href="./logOut.do"><img src="images/logout.gif" width="75" height="21"  border="0" /></a></td>
			<td width="37%"></td>
		  </tr>
	</table>
	</td>
</tr>	
<tr>
	<td colspan="3" style= "height:1px"></td>
</tr>
<tr align="right">
	<td align="right" colspan="3" bgcolor="#64a6d8" style="height:22px" class="heading_white"><date:format pattern="MMMM d, yyyy"><date:currentTime/></date:format>&nbsp;&nbsp;</td>
</tr>

<tr align="right">

<td colspan="3">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
<tr>

<td align="left" colspan="1" bgcolor="eeeeee" style="height:15px; width:150px"></td>
<td align="left" colspan="1" bgcolor="eeeeee" style="height:15px; width:53%">
<%
	        String pageId="";
			if(request.getParameter("pageId")!=null)
			{
				session.setAttribute("pageId",request.getParameter("pageId"));
			}	
			if(session.getAttribute("pageId")!=null)
			{
				pageId=(String)session.getAttribute("pageId");
			}
	%>								
							<crumb:breadcrumbs currPageName="<%=pageId%>"/>
</td>

<td align="right" bgcolor="eeeeee" style="height:15px; width:30%">
<c:if test="${Role eq 'Admin'}">
	  <font class="heading_blue">Welcome&nbsp;<c:out value="${UserName}"/></font>&nbsp;&nbsp;
    </c:if>
    <c:if test="${Role eq 'User'}">
	  <font class="heading_blue">Welcome&nbsp;<c:out value="${UserName}"/></font>&nbsp;&nbsp;
    </c:if>
    <c:if test="${Role eq 'Customer'}">
      <font class="heading_blue">Welcome&nbsp;<c:out value="${ApplicationOwnerName}"/></font>&nbsp;&nbsp;	
	</c:if>
</td>
</tr>


</table>
</td>
</tr>				
</table>
</body>
</html>

