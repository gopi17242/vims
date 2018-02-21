<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>

<html>
<head>
<script src="./script-test/common.js"></script>
<LINK href="./css/StyleSheet.css" rel="STYLESHEET" type="text/css">
<title><tiles:getAsString name="title" ignore="true"/></title>
</head>
<body>
<table width="1003px" align="center" border="0" cellspacing="0" cellpadding="0">
  <tr>
	   <!-- Top header starts here -->
	    <td colspan="2" align="left" valign="top"><tiles:insert attribute="header"/></td>
   </tr>
  <tr>
      <!-- Left navigation starts here -->
    <td width="166" align="left" valign="top"><tiles:insert attribute="tabs"/></td>    
        <!-- Center Panel starts here -->    
    <td width="90%" align="left" valign="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="left">
	      <tr>
		        <td width="1%" valign="top"><img src="images/centerpaneltopleft.gif" /></td>
		        <td width="98%" style="background-image:url(images/centerpaneltopmiddle.gif)">
			        <table width="100%" border="0" cellspacing="1" cellpadding="0">
			            <tr><td height="5"></td></tr>
			            <tr>
			            	<td width="15%" class="heading_white"><tiles:getAsString name="title"/></td>
			            </tr>
			        </table>
		       </td>
		       <td width="1%" align="left" valign="top"><img src="images/centerpaneltopright.gif"/></td>
	      </tr>
	      <tr height="366px">
		        <td height="186" valign="top" style="background-image:url(images/left.gif)"></td>
		        <td align="left" valign="top" style="background-image:url(images/centerpanelcenterCourt.gif)">
			        <table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
			          <tr><td>
			             <!-- Body content -->            
			            <tiles:insert attribute="body"/>
			          </td></tr>
			        </table>          
		        </td>
		        <td valign="top" style="background-image:url(images/right.gif)"></td>
	      </tr>
	      <tr>
	        <td valign="top"><img src="images/centerpanelbottomleft.gif" /></td>
	        <td height="31" valign="top" style="background-image:url(images/centerpanelbottommiddle.gif)">&nbsp;</td>
	        <td align="left" valign="top"><img src="images/centerpanelbottomright.gif" /></td>
	      </tr>
    </table>
   </td>   <!-- Center Panel closed here -->     
    </tr>
  <tr>
    <td colspan="2" width="100%"><tiles:insert attribute="footer"/></td>
  </tr>
</table>
</body>
</html>