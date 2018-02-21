<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<html:html>
<head>
<script language="javascript" type="text/javascript" src="./script-test/VIMS_Alerts.js"></script>
<script language="javascript">
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
function functionModify()
	{
		//alert("In functionModify");
		var formobj=document.forms[0];
		document.getElementById("selected_state").value=formobj.state.value;
		formobj.action="./modifyClient.do?client=Update";
		formobj.submit(); 
	}
function funEmailCheck()
	{
		var formobj=document.forms[0];
		var empemail=formobj.emailID.value;
		if(empemail!=null && empemail!='' && document.getElementById("emailErr")!=null && document.getElementById("emailErr")!=''){
			var varemailmsg=document.getElementById("emailErr").innerText;
			if(varemailmsg!=null && varemailmsg=='E-mail is required'){
				document.getElementById("emailErr").style.visibility="hidden";
				document.getElementById("emailErr").innerText='';
			}else if(varemailmsg!=null && varemailmsg=='E-mail format is for ex.,info@vertexcs.com'){
				document.getElementById("emailErr").style.visibility="hidden";
				document.getElementById("emailErr").innerText='';
			}
		}
		x=GetXmlHttpObject();
		
		var url="TestEditEmailIDCustomer.do?client=CheckEmailID&Email="+document.getElementById("Email").value;
		
		
		if (x==null)
		  {
			  alert (ajaxAlert);
			  return
		  } 
		x.onreadystatechange=stateChanged;
		x.open("POST",url,false);
		x.send(null);
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
function fnCountry(){
	var formobj=document.forms[0];
	var selcountry=formobj.country.value;
	formobj.action="./ModifyCountry1.do?function=changeCountry&country="+selcountry; 
	document.getElementById("sel_count").value=selcountry;
	formobj.submit(); 
}	

function onlyNumbers(evt)
		{   
			var e = event || evt; // for trans-browser compatibility    
			var charCode = e.which || e.keyCode;    
			if (charCode > 31 && (charCode < 48 || charCode > 57)) 
			{  
				alert(numericAlert); 
				//document.getElementById("slaMsg").innerHTML="Enter only numerics";
				return false;
			}	
			return true;
		}		
function fnGoBack()
{
  window.location="./dispatchClient.do?client=Base&menuId=Customer&pageId=Customers";
}
function fnPhoneNoMsg()
{
   var formobj=document.forms[0];
   var selPhoneNo=formobj.phoneNumber.value;
   
   if(selPhoneNo!=null)
   {
	//alert("----In selWorkLocation Hyderabad--------");
	  document.getElementById("phoneNoAlert").innerHTML="<font size='2' color='black'>(Ex:123-123-8954)</font>";
	//document.getElementById("phoneNoAlert").style.visibility="visible";
   }
 }
 function fnFaxNoMsg()
{
    var formobj=document.forms[0];
    var selFaxNo=formobj.faxNo.value;
  
    if(selFaxNo!=null)
   {
	//alert("----In selWorkLocation Hyderabad--------");
	  document.getElementById("faxNoAlert").innerHTML="<font size='2' color='black'>(Ex:123-123-8954)</font>";
	//document.getElementById("phoneNoAlert").style.visibility="visible";
   }
}
function fnClear()
{
    document.getElementById("faxNoAlert").innerHTML="";
    document.getElementById("phoneNoAlert").innerHTML="";
}

function funCountryState()
{		
	if(document.getElementById("country").selectedIndex!=0)
	{
	x=GetXmlHttpObject(url);
	//alert("----x----"+x);
	var t=document.getElementById("customerID").value;
	//alert(t);
	//alert(document.getElementById("country").options[document.getElementById("country").selectedIndex].value);
    var url="Details.do?client=changeCountry&custID="+t+"&strSelCountry="+document.getElementById("country").options[document.getElementById("country").selectedIndex].value+"&strSelState="+document.getElementById("state").options[document.getElementById("state").selectedIndex].value+"&request_from=ajaxcall";  
    //alert("--------url-----"+url);
	if (x==null)
	  {
		  alert (ajaxAlert);
		  return 
	  } 
	
	x.onreadystatechange= function() {
	    if (x.readyState == 4) {
	    //alert("--------x.status-----"+x.status);
	    alert(x.status);
		if (x.status == 200) {
			stateChanged2(x.responseText);
		} else if (x.status == 204){
		    alert('cant do anything');
		}
	    }
	};
	x.open("POST",url,false);
	x.send(null);
	}
	else
	{
	  removeOptionSelected();
	}
}  

function stateChanged2(responseText) 
{   removeOptionSelected();
	if (responseText!=null && responseText.length>2)	
	{ 						
		var strStatesList=responseText.split(";");
		for(var i=0; i<strStatesList.length;i++)
		{ 
			  //add options
			  var strStatesList=responseText.split(";");
		  for(var i=0; i<strStatesList.length-1;i++)
		    { 
			  //add options
			    var strStates=strStatesList[i].split(":");
			    //alert("-----strStates1---"+strStates[0]);
			    //alert("------strStates2----"+strStates[1]);
			    var elSel = document.getElementById('state');
			    //alert("-----elSel----"+elSel);
				var elOptNew = document.createElement('option');
				  elOptNew.text  = strStates[1];
				  elOptNew.value = strStates[0];
			  	try 
			  	{
				  elSel.add(elOptNew, elOptOld); // standards compliant; doesn't work in IE
				}
				catch(ex) 
				{
				  elSel.add(elOptNew,elSel.selectedIndex); // IE only
				}
		}
			 
    } 
 
}
}
function removeOptionSelected()
{
  var elSel = document.getElementById('state');
  var i;
  alert(elSel.length);
  for (i = elSel.length - 1; i>=0; i--) {
 		if (elSel.options[i].value!='') {
 		 alert(elSel.options[i].value);
		  elSel.remove(i);
		}
   }
}
</script>
</head>
<body>
<br>
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0">
<tr>
<td>
<html:form action="modifyClient.do" focus="customerName">
<input type="hidden" name="sel_count" id="sel_count"/>
<input type="hidden" name="selected_state" id="selected_state"/>
<fieldset><legend class="heading_blue">Customer Details</legend>
  <table border="0" width="100%" align="left" cellspacing="0" cellpadding="0">	
	<tr><td colspan="2" align="center" class="labelField"><i><c:out value="${MSG}"/></i></td></tr>
	
	         <html:hidden property ="customerID" styleClass="textbox_default"/>		
	
	<tr>	       
			<td class="labelField" align="left" width="20%"><bean:message key="VIMSApplication.CustomerName"/></TD>
			<td  align="left" width="80%"><font class="labelMandatory" color=red>*</font><html:text property ="customerName" styleClass="textbox_default" style="width:250px" tabindex="1" maxlength="40"/>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="CustomerName.Error"></html:errors></font></td>
	</tr>
	<tr>			    		
        	<td class="labelField" align="left"><bean:message key="VIMSApplication.status"/></td>
			<td  align="left">&nbsp;&nbsp;<html:select property="status" style="width:80px" styleClass="file_Upload"  tabindex="2">
		  		<html:option value="Active">Active</html:option>
	  			<html:option value="Inactive">Inactive</html:option>	  			
		        </html:select>
		    </td>
    </tr>
	
	<tr>
	    <td class="labelField" align="left"><bean:message key="VIMSApplication.phone"/></td>
		<td  align="left"><font class="labelMandatory" color=red>*</font><html:text property ="phoneNumber" styleClass="textbox_default" style="width:117px" size="15" maxlength="25" tabindex="3"/>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="PhoneNO.Error"></html:errors></font></td>
	</tr>
	<tr>			
		<td align="left" class="labelField"><bean:message key="VIMSApplication.fax"/></td>
		<td>&nbsp;&nbsp;<html:text property ="faxNo" size="15" styleClass="textbox_default"  style="width:117px"   onblur="javascript:fnClear();" onfocus="javascript:fnFaxNoMsg();" maxlength="12" tabindex="4"/>&nbsp;&nbsp;<span id="faxNoAlert"></span>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="faxNo.Error"></html:errors></font></td>
	</tr>	
	<tr>
       	<td class="labelField" align="left"><bean:message key="VIMSApplication.email"/></td>
	    <td align="left"><font class="labelMandatory" color=red>*</font><html:text property ="emailID" styleId="Email" styleClass="textbox_long" maxlength="50" style="width:250px" tabindex="5" onchange="javascript:funEmailCheck();"/>&nbsp;&nbsp;<font color="red" size="2">
	    <i><span id="spanId"></span></i>
	    <html:errors property="EmailID.Error"></html:errors></font></td>	 		
	</tr>	
	
		<html:hidden property ="custPassword" styleClass="textbox_default"/>		
	
	 </table>
	 
	  </fieldset>
	   <br>
	  <fieldset><legend class="heading_blue">Contact Person Details</legend>
    
    <table border="0" width="100%" align="left" cellspacing="0" cellpadding="0" >
		<tr>	       
			<td class="labelField" align="left" width="20%">First Name</TD>
			<td  align="left" ><font class="labelMandatory" color="red">*</font><html:text property ="contactFirstName"  styleClass="textbox_default" maxlength="50" style="width:250px" tabindex="6"/>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="contactFirstName.Error"></html:errors></font></td>
		</tr>
		<tr>		    		
	        <td class="labelField" align="left">Middle Name</td>
	       <td  align="left" >&nbsp;&nbsp;<html:text property ="contactMiddleName"  styleClass="textbox_default" maxlength="50" style="width:117px" tabindex="7"/></td>
	     </tr>
		<tr>
			<td class="labelField" align="left">Last Name</td>
			<td  align="left" ><font class="labelMandatory" color="red">*</font><html:text style="width:250px" maxlength="50" property="contactLastName"  styleClass="textbox_default" tabindex="8"/>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="contactLastName.Error"></html:errors></font></td>
	    </tr>
	  	<tr>
		    <td class="labelField" align="left"><bean:message key="VIMSApplication.email"/></td>
			<td  align="left"><font class="labelMandatory" color=red>*</font><html:text property ="contactEmailID" styleId="Email" maxlength="50" style="width:250px"  styleClass="textbox_long" tabindex="9"/>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="ContactEmailID.Error"></html:errors></font></td>
		  </tr>
		  
		<tr>
		    <td class="labelField" align="left">Mobile Number</td>
			<td  align="left"><font class="labelMandatory" color=red>*</font><html:text property ="mobileNO" maxlength="50" style="width:117px"  styleClass="textbox_long" tabindex="10"/>&nbsp;&nbsp;<font color="red" size="2">
			<html:errors property="MobileNO.Error"></html:errors></font></td>
		  </tr>	  	
		    	
	  </table>		
		 </fieldset>		 
		<br>
	
   <fieldset><legend class="heading_blue">Address for Communication</legend>	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	 <tr>
		<td width="20%"></td><td width="80%"></td>
	 </tr>
	<tr>
        <td class="labelField" align="left"><bean:message key="VIMSApplication.address1"/></td>
        <td align="left"><font class="labelMandatory" color=red>*</font><html:text property="address1" style="width:250px"  styleClass="textbox_default" tabindex="11" maxlength="1000"></html:text>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="strAddress1.size"></html:errors></font></td>	
    </tr>
    <tr>
        <td class="labelField" align="left"><bean:message key="VIMSApplication.address2"/></td>
        <td align="left">&nbsp;&nbsp;<html:text property="address2" style="width:250px"  styleClass="textbox_default" tabindex="12" maxlength="100"></html:text></td>	
    </tr>
    <tr>
		<td class="labelField" align="left"><bean:message key="VIMSApplication.city"/></td>
		<td  align="left">&nbsp;&nbsp;<html:text style="width:170px" property ="city" styleClass="textbox_default" tabindex="13" maxlength="30"/>
           &nbsp;&nbsp;<font size="2" color="red"><i><html:errors property="city.Error"></html:errors></i></font></td>				
	</tr>
	<tr>
         <td align="left" class="labelField"><bean:message key="VIMSApplication.country"/></td>
		<td>&nbsp;&nbsp;<html:select style="width:170px" property="country" size="1" styleId="Country" tabindex="14" styleClass="file_Upload" onchange="funCountryState()">
             <html:option value="">Select Country</html:option> 
              <html:optionsCollection name="countriesList" value="countryNo" label="countryNames"/> 
		     </html:select>
         </td> 
   </tr>
   <tr>	              
	  <td align="left" class="labelField"><bean:message key="VIMSApplication.state"/></td>
      <td>&nbsp;&nbsp;<html:select style="width:170px" property="state" styleId="statesList" size="1" tabindex="15" styleClass="file_Upload">
                <html:option value=''>Select State</html:option> 
                <c:if test="${statesList ne null}">
                <html:optionsCollection name="statesList" value="stateNo" label="stateNames"/> 
	            </c:if>
             </html:select>
     </td> 
   </tr>
	<tr>			
        <td class="labelField" align="left"><bean:message key="VIMSApplication.zip"/></td>
	    <td  align="left">&nbsp;&nbsp;<html:text style="width:117px" property ="zipCode" styleClass="textbox_default" onkeypress="return onlyNumbers();" tabindex="16" maxlength="12"/>
	    &nbsp;&nbsp;<font size="2" color="red"><i><html:errors property="strZip.Error"></html:errors></i></font></td> 
    </tr>
  	<tr>
	    <td height="10px"></td>
	</tr>
      </table>
</fieldset>
 
   <html:hidden property="subType" value="Modify"></html:hidden>
<tr>		
	<td colspan="5" align="left">
		<table width="100%" border="0">
			<tr>
				<td width="20%"></td>
				<td>&nbsp;&nbsp;<button type="Button" name="Submit" class="linkbutton_background" tabindex="17" onclick="functionModify();" accesskey="U"><u>U</u>pdate</button>&nbsp;&nbsp;&nbsp;&nbsp;
			   					<button type="Button"  name="back" onClick="fnGoBack();" tabindex="18" accesskey="C" class="linkbutton_background"><u>C</u>ancel</button>
			   </td>
	      </tr>
        </table>
     </td>
</tr>
</html:form>
</td>
</tr>
</table>
</body>
</html:html>