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
<br><center><h2><font color="green">Issue <c:out value="${action}"/> successfully</font></h2></center></logic:present>
<logic:present name="statusFrom"><logic:present name="statusTo">
<br><center><h3><font color="blue">Status changed from <c:out value="${statusFrom}"/> to <c:out value="${statusTo}"/></font></h3></center>
</logic:present></logic:present>
<logic:present name="AsignmentReceiver">
<br>
<center><h4>
<font color="blue">Mail  has been sent to <c:out value="${AsignmentReceiver}"/>
</font></h4>
</center>
</logic:present>
<logic:present name="GroupSupervisorName">
<br>
<center><h4>
<font color="blue">Mail  has been sent to <c:out value="${GroupSupervisorName}"/> (Group Supervisor)<logic:present name="ApplicationOwnerName"> and <c:out value="${ApplicationOwnerName}"/> (Application Owner)</logic:present>
</font></h4>
</center>
</logic:present>
<logic:present name="SupervisorName">
<logic:present name="SupportManagerName">
<br>
<center><h4>
<font color="blue">Mail  has been sent to <c:out value="${SupportManagerName}"/> (Support manager) and <c:out value="${SupervisorName}"/> (Group Supervisor)
</font></h4>
</center>
</logic:present>
</logic:present>
<logic:present name="AppOwnerName">
<br><center><h2><font color="green">Verification mail sent successfully</font></h2></center>
<br>
<center><h4>
<font color="blue">Mail  has been sent to <c:out value="${AppOwnerName}"/> (Application Owner)
</font></h4>
</center>
</logic:present>
<!-- <br><center><h3><font color="red">Currently Mail Server is Disabled!!!!!</font></h3></center>-->
</body>
</html>