<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<HTML>
<HEAD>
<LINK href="./css/StyleSheet.css" rel="STYLESHEET" type="text/css">
<script language="javascript" type="text/javascript" src="./script-test/VIMS_Alerts.js"></script>
<script><!--
var x
	
	function GetXmlHttpObject()
	{
	    var xmlHttp=null;
			try
			  {
				  // Firefox, Opera 8.0+, Safari
				  xmlHttp=new XMLHttpRequest();
			  }
			catch (e)
			  {
				  // Internet Explorer
			    try
			    {
			    	xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
			    }
			  	catch (e)
			    {
			    	xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
			    }
			  }
		  
		return xmlHttp;
	}
function fnCheckOldPassword()
{
       if(document.getElementById("Email").value!='') {
		x=GetXmlHttpObject();
		var url="checkPassword.do?methodname=OldPasswordCheck&Email="+document.getElementById("Email").value;
		if (x==null)
		  {
			  alert (ajaxAlert);
			  return
		  } 
		x.onreadystatechange=stateChanged;
		x.open("POST",url,false);
		x.send(null);
	}
	   
}
function stateChanged() 
	{ 
		if (x.readyState==4)	
		{ 						
	      		response  = x.responseXML.documentElement;
	            result    = response.getElementsByTagName('result')[0].firstChild.data;		
	      		if(result=="ok")
	      		{		      		
				 document.getElementById("spanId").innerHTML=" ";	
				}else
				{
				 document.getElementById("spanId").innerHTML=result;
				 document.getElementById("Email").select();
				}
	    } 
      	else 
      	{
           
       	}
    }

function functionUpdate(newPassword,confirmPassword)
{
   try{
   
   if(((document.getElementById("oldPassword").value=='')&&(document.getElementById("newPassword").value=='')&&(document.getElementById("confirmPassword").value=='')))
   {
     document.getElementById("oldPassword").select();
     alert(allEmptyFields);
     return false;
   }
   
   else if((document.getElementById("oldPassword").value=='')&&(document.getElementById("newPassword").value==''))
   {
     alert(checkValidation);
     document.getElementById("oldPassword").select();
     return false;
   }
   else if((document.getElementById("newPassword").value=='')&&(document.getElementById("confirmPassword").value==''))
   {
     alert(checkValidation1);
     document.getElementById("newPassword").select();
     return false;
   }
   else if((document.getElementById("oldPassword").value=='')&&(document.getElementById("confirmPassword").value==''))
   {
     alert(checkValidation2);
     document.getElementById("oldPassword").select();
     return false;
   }
   else if(((document.getElementById("oldPassword").value=='')||(document.getElementById("newPassword").value=='')||(document.getElementById("confirmPassword").value=='')))
   {
     alert(oneFieldValidation);
     return false;
   }
   else if(newPassword.value!=confirmPassword.value)
   {
     alert(passwordCheck);
     return false;
   }
   //alert(newPassword.value.length);
   else if(newPassword.value.length<6)
   {
     alert(passwordLength);
     document.getElementById("newPassword").select();
     return false;
   }
   else 
   {
   if(document.getElementById("pwd").value!='')
   {
     x=GetXmlHttpObject();

		var url="changePwd.do?methodname=Update&NewPassword="+document.getElementById("pwd").value;
		
		if (x==null)
		  {
			  alert (ajaxAlert);
			  return
		  } 
		x.onreadystatechange=stateChanged1;
		x.open("POST",url,false);
		x.send(null);
	 }
	}
   }
   catch(e)
   {
   } 
}

function stateChanged1() 
	{ 
		if (x.readyState==4)	
		{ 						
	      		
	      		response  = x.responseXML.documentElement;
	            result    = response.getElementsByTagName('result')[0].firstChild.data;		
	            alert(result);//This is an alert msg coming from Action Class
	            window.location="./home.do?method=getHomePageApplicationsList&menuId=Home&pageId=home";
	    } 
      	else 
      	{
           
       	}
    }

--></script>
</head>
<body><br>
<%String strUserID=(String)session.getAttribute("user");%>
<logic:present name="MSG">
     <font color="green"><c:out value="${MSG}"/></font>
</logic:present>
<table border="0" width="100%" align="center" cellspacing="0" cellpadding="0" >
<html:form action="changePwd.do?methodname=Update">
	<input type="hidden" name="subType" value="Submit"/>
  	  
	 <tr>	       
		<td  class="labelField" align="left" width="20%"><bean:message key="VIMSApplication.userID"/></td>
		<td  class="labelField" align="left" width="80%">&nbsp;&nbsp;&nbsp;<%=strUserID%></td>	    		
	 </tr>
	 
	 <tr>	       
		<td class="labelField" align="left" ><bean:message key="VIMSApplication.oldPassword"/></TD>
		<td  align="left">&nbsp;<font class="labelMandatory" color=red>*</font><html:password property ="oldPassword" styleId="Email" styleClass="textbox_default" tabindex="1" onblur="fnCheckOldPassword();"/>&nbsp;&nbsp;<font color="red" size="2">
		<i><span id="spanId"></span></i><html:errors property="oldPassword.Error"></html:errors></font></td>	    		
	 </tr>
	 <tr>	       
		<td class="labelField" align="left" ><bean:message key="VIMSApplication.newPassword"/></TD>
		<td  align="left">&nbsp;<font class="labelMandatory" color=red>*</font><html:password property ="newPassword" styleClass="textbox_default" styleId="pwd" tabindex="2"/>&nbsp;
		<font color="red" size="2"><html:errors property="newPassword.Error"></html:errors></font></td>	    		
	 </tr>	 
	 <tr>	       
		<td class="labelField" align="left" ><bean:message key="VIMSApplication.confirmPassword"/></TD>
		<td  align="left" >&nbsp;<font class="labelMandatory" color=red>*</font><html:password property ="confirmPassword" styleClass="textbox_default" tabindex="2"/>&nbsp;<font color="red" size="2"><html:errors property="confirmPassword.Error"></html:errors></font></td>	    		
	 </tr>		 
	   <tr>
			<td height="10px"></td>
	  </tr>
	  
	  <input type="hidden" name="subType" value="Submit"/>
			
	   <tr>		
		 <td class="labelField" align="left" width="20%"></td>
		 <td colspan ="3" align="left">&nbsp;&nbsp;<button type="button" class="linkbutton_background" onclick="functionUpdate(newPassword,confirmPassword);" accesskey="u"><u>U</u>pdate</button>&nbsp;&nbsp;&nbsp;&nbsp;
	     <button type="reset" class="linkbutton_background" accesskey="r"><u>R</u>eset</button>&nbsp;&nbsp;&nbsp;&nbsp;
	     </td>
	 </tr>	
	 </html:form>
	</table>
</body>
</HTML>

