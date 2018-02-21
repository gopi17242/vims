<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to Vertex Issue Management System - Login page</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
}
-->
</style>
<link href="css/StyleSheet.css" rel="stylesheet" type="text/css" />
<script language="javascript">
function checkFields()
{ 
	if(document.getElementById("userId").value=="")
	{
		alert("Please enter User Name");
		//document.getElementById("userMsg").innerHTML="Please enter user name";
		document.getElementById("userId").focus();
	}
	else if(document.getElementById("pwd").value=="")
	{
		alert("Please enter Password");
		//document.getElementById("pwdMsg").innerHTML="Please enter password";
		document.getElementById("pwd").focus();
	}
}
</script>
</head>

<body style="background-color:#ebf4fb" >
<html:form action="LoginAction.do" focus="userId">
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="background-image:url(images/loginBG.jpg);background-repeat:y-repeat; height:590px">
  
  <tr>
    <td align="center" valign="middle">
    
    <table width="385px" height="354px" border="0" cellpadding="0" cellspacing="0" background="images/loginCenter.jpg">
      <tr>
        <td valign="bottom" align="center"><table width="385" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="20">&nbsp;</td>
            <td>&nbsp;</td>
            <td width="20">&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td height="21">&nbsp;</td>
            <td>&nbsp;             
            </td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td height="21">&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="center">
            
            <table width="85%" border="0" align="center" cellspacing="3" cellpadding="0">
           
            <tr>
            <td style="width:25%" align="left" class="bodytextwhite">User Name</td>
            <td align="left"><html:text property="userId" styleId="userId" style="width:150px" styleClass="tBox"/>
            <span id="userMsg"></span>
            </td>
            </tr>
            <tr>
            <td align="left" class="bodytextwhite">Password</td>
            <td align="left"><html:password property="password" styleId="pwd"  style="width:150px" styleClass="tBox"/>
             <span id="pwdMsg"></span>
            </td>
            </tr>
            <tr>
            <td align="left" class="bodytext">&nbsp;</td>
            <td align="left">
            
            <table width="100%" border="0" cellspacing="2" cellpadding="0">
            <tr>
            <td style="width:30px" align="left">
           <input type="image" src="./images/login.gif" name="Login" onclick="checkFields();"/>
            </td>
             <td align="left">
             <input type="image" src="images/cancel.gif" onclick="window.close();"/>
            </td>
            </tr>
                       
            </table>
            </td>
            </tr>
            </table>
       </td>
            <td>&nbsp;</td>
          </tr>
           <tr>
             <td>&nbsp;</td>
             <td width="200px" align="center" class=bodytextwhite>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${Invalid}"></c:out></td>             
             <td>&nbsp;</td>
          </tr> 
         
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          
        </table></td>
      </tr>      
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html>
