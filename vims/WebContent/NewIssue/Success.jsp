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

<br><center><h1> <font color="green">Issue inserted successfully!!!</font></h1></center>

<br><center><h3><font color="blue">Your Issue ID is <c:out value="${NewIssueID}"/></font></h3></center>
<logic:present name="strGroupSupervisorName">
<logic:present name="strSupportManagername">
<br><center><h4><font color="blue">Mail has been sent to <c:out value="${strSupportManagername}"/>(Support Manager) and <c:out value="${strGroupSupervisorName}"/>(Group Supervisor)</font></h4></center>
</logic:present></logic:present>

</body>
</html>