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
<logic:present name="error">
<br><center><h1> <font color="red">Error occurred while inserting the issue!!!</font></h1></center>
</logic:present>
<logic:present name="mailerror">
<center><h3><font color="green">Issue inserted successfully!!!</font></h3></center>
<br><center><h3><font color="blue">Your IssueID is <c:out value="${NewIssueID}"/></font></h3></center>
<br><center><h4><font color="red">Error Occurred While sending mail</font></h4></center>
</logic:present>
<logic:present name="MailServerError">
<center><h3><font color="green">Issue inserted successfully!!!</font></h3></center>
<br><center><h3><font color="blue">Your IssueID is <c:out value="${NewIssueID}"/></font></h3></center>
<br><center><h4><font color="red">Mail Server is configured. So unable to send the mail.</font></h4></center>
</logic:present>
</body>
</html>