<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
      <%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body><br><br><br>
<logic:present name="action">
<br><center><h1> <font color="green">Issue <c:out value="${action}"/> Successfully</font></h1></center>
<br><center><h3><font color="blue">Error Occurred While Forwarding Mail</font></h3></center>
</logic:present>
<logic:present name="Error">
<br><center><h1> <font color="red">Error Occured</font></h1></center>
<br><center><h1><font color="red">Issue was not assigned due to internal problem</font></h1></center>
</logic:present>
<logic:present name="ChangeError">
<br><center><h1> <font color="red">Error Occured </font></h1></center>
<br><center><h3><font color="blue">Issue status was not changed due to internal problem</font></h3></center>
</logic:present>
</body>
</html>