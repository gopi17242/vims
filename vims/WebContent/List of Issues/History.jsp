<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<title>Issue History</title>
<LINK href="../css/StyleSheet.css" rel="STYLESHEET" type="text/css">
<script language="javascript">
	function closeChild(){
	        window.close();
	}
</script>
</head>
<body>
<table cellspacing="2" width="35%">
<table border="0">
 <tr>
	<td width="5%" align="left"><img src="../images/vertexLogo.png" width="156" height="55" /></td>
	<td width="30%" align="center"><img src="../images/headerText.gif" width="363" height="30" /></td>
</tr>
</table>
<table cellspacing="2" width="100%">
<tr>
	<td colspan="3" style= "height:1px"></td>
</tr>

 <tr>
	<td height="25" colspan="7" bgcolor="#4b95db" style="height:22px"></td>
</tr>
<c:forEach items="${IssueDetails1}"  var="IssueDetails1" step="1">
  <input type="hidden" name="issueid" value="<c:out value='${IssueDetails1.IssueId}'/>"/>
</c:forEach>
<tr>
		<TH  colspan="7" class="heading_blue" ALIGN="center">Issue History of <script>document.write(document.getElementById('issueid').value);</script>&nbsp;</TH>
	</tr>
<tr class="gridAlternate">
<th class="gridBluheader" align="left"><font color="white">Assigned By</font></th>
<th class="gridBluheader" align="left"><font color="white">Assigned To</font></th>
<th class="gridBluheader" align="left"><font color="white">Status From</font></th>
<th class="gridBluheader" align="left"><font color="white">Status To</font></th>
<th class="gridBluheader" align="left"><font color="white">Date</font></th>
<th class="gridBluheader" align="left"><font color="white">Comments/Resolutions</font></th>

<!-- <th class="gridBluheader" align="left">Change Status Reason</th>-->
</tr>
<c:forEach items="${IssueDetails1}" var="IssueDetails1" step="1">
  <input type="hidden" name="postedby" value="<c:out value='${IssueDetails1.CustomerID}'/>"/>
</c:forEach>
<c:forEach items="${IssueDetails3}" var="IssueDetails3" step="1">
  <input type="hidden" name="postedto" value="<c:out value='${IssueDetails3.assignedby}'/>"/>
</c:forEach>
<c:forEach items="${IssueDetails4}" var="IssueDetails4" step="1">
<tr class="gridAlternate">
<td  align="left"><script>document.write(document.getElementById('postedby').value);</script></td>
<td  align="left"><script>document.write(document.getElementById('postedto').value);</script></td> 
<td  align="left">Open</td>
<td  align="left">Open</td>
<td  align="left"></td>
<td  align="left"><textarea class="textArea"rows="4" cols="50"name="tadesc" readonly><c:out value="${IssueDetails4.description}"/></textarea>
</td>
</tr>
</c:forEach>

<c:forEach items="${IssueDetails3}"  var="IssueDetails3" step="1">
<tr class="gridAlternate">

	<td  align="left"><c:out value="${IssueDetails3.assignedby}"/></td>
	<td  align="left"><c:out value="${IssueDetails3.assignedto}"/></td>
	<td  align="left"><c:out value="${IssueDetails3.statusfrom}"/></td>
	<td  align="left"><c:out value="${IssueDetails3.statusto}"/></td>
	<td  align="left"><c:out value="${IssueDetails3.date}"/></td>
	<td  align="left"><textarea class="textArea"rows="4" cols="50"name="tadesc" readonly><c:out value="${IssueDetails3.comments}"/>
	</textarea></td>
	
	<!-- <td  align="left"><textarea class="textArea"rows="4" cols="50"name="tadesc"><c:out value="${IssueDetails3.reason}"/>
	</textarea></td>-->
	</tr>
</c:forEach>
</table><br>
<center><button type="button" onClick="closeChild();" class="linkbutton_background" accesskey="c"><u>C</u>lose</button></center>
</body>
</html>