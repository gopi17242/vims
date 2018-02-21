<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
<title><tiles:getAsString name="title" ignore="true"/></title></head>
<body>
<table border="0" cellpadding="0" cellspacing="0" width="100%"   bgcolor="#E7FDFE">
  <tr>
    <td width="100%" colspan="2" valign="top"><tiles:insert attribute="header"/></td>
	</tr>
	<tr>
	<td width="100%" colspan="2" valign="top"><tiles:insert attribute="tabs"/></td>
  </tr>
  <tr>    
    <td width="77%" valign="top" valign="top"><tiles:insert attribute="body"/></td>
  </tr>
  <tr>
    <td width="100%" colspan="2" valign="top"><tiles:insert attribute="footer"/></td>
  </tr>
</table>
</body>
</html>