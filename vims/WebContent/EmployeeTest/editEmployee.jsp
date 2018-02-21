<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>


<html:html>

<head>
<script language="javascript" type="text/javascript" src="./script-test/VIMS_Alerts.js"></script>
   <script language="javascript" type="text/javascript" src="./script-test/datetimepicker.js">
</script>
<script>

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
	function fnSubmit(){
	document.getElementById("isSubmited").value="yes";
    var formobj=document.forms[0];
    document.getElementById("selected_state").value=formobj.strState.value;
    //alert(document.getElementById("selected_state").value);
	formobj.action="./modifyEmployeeLookUp.do?function=EditEmployee";
	formobj.submit(); 
}

function funEmailCheck()
{		
	    var formobj=document.forms[0];
		var empemail=formobj.strEmail.value;
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
		
		var url="TestEditEmailIDEmployee.do?function=CheckEmailID&Email="+document.getElementById("Email").value;
		
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
  function fnCountry() 
  {
	var formobj=document.forms[0];
	var selcountry=formobj.strCountry.value;
	formobj.action="./ModifyCountry.do?function=changeCountry&country="+selcountry; 
	document.getElementById("sel_count").value=selcountry;
	formobj.submit(); 
  }	
function fnGoBack()
{
  window.location="./employeeLookupDispatch.do?function=EmployeeFirstPage&menuId=Employee&pageId=Employees";
}	

function fnPhoneNoMsg()
{
   var formobj=document.forms[0];
   var selWorkLocation=formobj.strWorkLocation[formobj.strWorkLocation.selectedIndex].value;
   //alert(selWorkLocation);
   if(selWorkLocation=="Hyderabad")
   {
	//alert("----In selWorkLocation Hyderabad--------");
	document.getElementById("phoneNoAlert").innerHTML="<font size='2' color='black'>(ex:123-12348956)</font>";
	//document.getElementById("phoneNoAlert").style.visibility="visible";
   }
   else
   {
    //alert("----In selWorkLocation others--------");
    document.getElementById("phoneNoAlert").innerHTML="<font size='2' color='black'>(ex:123-123-4456)</font>";
   // document.getElementById("phoneNoAlert").style.visibility="visible";
   }
   //document.getElementById("strWorkPhone").value="";
 }
  function fnClear()
{
    document.getElementById("phoneNoAlert").innerHTML="";
}

function funCountryState()
{		
	if(document.getElementById("strCountry").selectedIndex!=0)
	{
	x=GetXmlHttpObject(url);
	var t=document.getElementById("strEmail").value;
	//alert("----x----"+x);
	//alert(document.getElementById("strCountry").options[document.getElementById("strCountry").selectedIndex].value);
	var url="ModifyCountry.do?function=changeCountry&eid="+t.substring(0,t.indexOf('@'))+"&strSelCountry="+document.getElementById("strCountry").options[document.getElementById("strCountry").selectedIndex].value+"&strSelState="+document.getElementById("strState").options[document.getElementById("strState").selectedIndex].value+"&request_from=ajaxcall";  
	//alert("--------url-----"+url);  
	if (x==null)
	  {
		  alert (ajaxAlert);
		  return 
	  } 
	
	x.onreadystatechange= function() {
	    if (x.readyState == 4) {
	    //alert("--------x.status-----"+x.status);
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
		for (var i=0; i<strStatesList.length;i++)
		{ 
			  //add options
			    var strStates=strStatesList[i].split(":");
			    //alert("-----strStates1---"+strStates[0]);
			    //alert("------strStates2----"+strStates[1]);
			    var elSel = document.getElementById('strState');
			    //alert("-----elSel----"+elSel);
				var elOptNew = document.createElement('option');
				  elOptNew.text  = strStates[1];
				  elOptNew.value = strStates[0];
			  	try {
				  elSel.add(elOptNew, elOptOld); // standards compliant; doesn't work in IE
				}
				catch(ex) {
				  elSel.add(elOptNew,elSel.selectedIndex); // IE only
				}
		}
			 
    } 
 
}

function removeOptionSelected()
{
  var elSel = document.getElementById('statesList');
  var i;
  for (i = elSel.length - 1; i>=0; i--) {
 		if (elSel.options[i].value!=' ') {
		  elSel.remove(i);
		}
   }
}
</script>
</head>
<body>
<br>
<table border="0" width="100%" align="left" cellspacing="0" cellpadding="0">
<tr>
<td>
<html:form action="modifyEmployeeLookUp.do">
<input type="hidden" name="isSubmited" id="isSubmited" value=""/>
<input type="hidden" name="selected_state" id="selected_state"/>
<input type="hidden" name="sel_count" id="sel_count" />
<fieldset><legend class="heading_blue">Employee Details</legend>
<table border="0" width="100%" align="left" cellspacing="0" cellpadding="0" >
	
	 <tr><td colspan="5" align="left" class="labelField"><font color=red><c:out value="${MSG}"/></font></td></tr>
	
		<tr>
		<td class="labelField" width="20%" align="left"><bean:message key="VIMSApplication.EmployeeID"/></td>
		<td  align="left" width="80%"><font class="labelMandatory" color=red>*</font><html:text property ="strEmployeeID" style="width:117px" styleClass="textbox_default" readonly="true" tabindex="1" maxlength="16"/>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="employeeid.size"></html:errors></font></td>				
		</tr>
		<tr>
		<td class="labelField" align="left"><bean:message key="VIMSApplication.status"/></td>
		        <td>&nbsp;&nbsp;<html:select property ="strStatus" styleClass="dropdownlist" style="width:80px" tabindex="2">
				<html:option value="Active">Active</html:option>
				<html:option value="Inactive">Inactive</html:option>				
				</html:select></td>
	</tr>
	
	<tr>
		<td class="labelField" align="left" ><bean:message key="VIMSApplication.firstName"/></TD>
		<td  align="left" ><font class="labelMandatory" color=red>*</font><html:text property ="strFirstName" styleClass="textbox_default" maxlength="25" style="width:180px" tabindex="3"/>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="FirstName.size"></html:errors></font></td>	    		
		</tr>
		
	<tr>
		 <td class="labelField" align="left" width="20%">Middle Name</TD>
		 <td align="left" width="30%">&nbsp;&nbsp;<html:text property ="strMiddleName" styleClass="textbox_default" maxlength="25" style="width:117px"  tabindex="4"/>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="strMiddleName.size"></html:errors></font></td>	    		
	</tr>	
		
		<tr>
		<td class="labelField" align="left" ><bean:message key="VIMSApplication.lastName"/></TD>
		<td  align="left" ><font class="labelMandatory" color=red>*</font><html:text property ="strLastName" styleClass="textbox_default" maxlength="25" style="width:180px" tabindex="5"/>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="Lastname.size"></html:errors></font></td>
		
	</tr>
	
    <tr>
         <td align="left" class="labelField"><bean:message key="VIMSApplication.position"/></td>
	     <td><font class="labelMandatory" color=red>*</font><html:select style="width:180px" property="strEmployeePosition" size="1" styleClass="dropdownlist" tabindex="6">
           <html:option value="">Select Position</html:option> 
           <html:optionsCollection name="positionList" value="PositionNo" label="PositionNames" />			         
	       </html:select>
         &nbsp;&nbsp;<font color="red" size="2"><html:errors property="position.size"></html:errors></font>
         </td> 
	</tr>
	 
	 <tr>
         <td align="left" class="labelField"><bean:message key="VIMSApplication.workLocation"/></td>
	     <td><font class="labelMandatory" color=red>*</font><html:select style="width:180px" property="strWorkLocation" size="1" styleClass="dropdownlist" tabindex="7">
           <html:option value="">Select Work Location</html:option> 
           <html:optionsCollection name="WorkLocationList" value="WorkLocationNo" label="WorkLocationNames" />			         
	       </html:select>&nbsp;&nbsp;<i><font color="red" size="2"><html:errors property="worklocation error"></html:errors></font></i>
         </td> 
	 </tr>
	
	 <tr>	       
		<td class="labelField" align="left"><bean:message key="VIMSApplication.phone"/></td>
		<td  align="left"><font class="labelMandatory" color=red>*</font><html:text property ="strWorkPhone" style="width:117px" size="15" styleClass="textbox_default" onblur="javascript:fnClear();" tabindex="8" maxlength="12"/>&nbsp;&nbsp;<span id="phoneNoAlert"></span>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="WorkPhone error"></html:errors></font></td>				
		</tr>
		<tr>
		<td class="labelField" align="left"><bean:message key="VIMSApplication.mobile"/></td>
		<td  align="left"><font class="labelMandatory" color=red>*</font><html:text property ="strMobile" style="width:117px" size="15" styleClass="textbox_default" onkeypress="return onlyNumbers();" tabindex="9" maxlength="10"/>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="strMobile.size"></html:errors></font></td>				
		
		
    </tr>
    
    <tr>
        <td class="labelField" align="left"><bean:message key="VIMSApplication.email"/></td>
		<td  align="left"><font class="labelMandatory" color="red">*</font><html:text property ="strEmail" styleId="Email" styleClass="textbox_long" maxlength="50" style="width:250px" tabindex="10" onchange="javascript:funEmailCheck();"/>&nbsp;&nbsp;<font color="red" size="2">
	    <i><span id="spanId"></span></i>
		<html:errors property="Email error"></html:errors></font></td>
	   </tr>
	   <tr>
	    <td align="left" class="labelField"><bean:message key="VIMSApplication.supervisorName"/></td>
		<td>&nbsp;&nbsp;<html:select style="width:250px" property="strSupervisorName" size="1" styleClass="dropdownlist" tabindex="11">
	        <html:option value="">Select Supervisor</html:option> 
	        <html:optionsCollection name="SupervisorList" value="strUsername" label="strEmpNames"/> 
		    </html:select>
	    </td> 		      	

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
	        <td align="left"><font class="labelMandatory" color=red>*</font><html:text property="strAddress1" style="width:250px" styleClass="textbox_default" tabindex="12" maxlength="100"></html:text>&nbsp;&nbsp;<font color="red" size="2"><html:errors property="strAddress1.size"></html:errors></font></td>	
    </tr>
    
    <tr>
	        <td class="labelField" align="left"><bean:message key="VIMSApplication.address2"/></td>
	        <td align="left">&nbsp;&nbsp;<html:text property="strAddress2" style="width:250px" styleClass="textbox_default" tabindex="13" maxlength="100"></html:text></td>	
    </tr>	
	
	
	<tr>
		<td class="labelField" align="left"><bean:message key="VIMSApplication.city"/></td>
		<td  align="left">&nbsp;&nbsp;<html:text property ="strCity" size="15" style="width:170px" styleClass="textbox_default" tabindex="14" maxlength="30"/>    
		&nbsp;&nbsp;<font size="2" color="red"><i><html:errors property="city.Error"></html:errors></i></font></td>				
	</tr>	
	
		
	 <tr>
	              <td align="left" class="labelField"><bean:message key="VIMSApplication.country"/></td>
				  <td>&nbsp;&nbsp;<html:select style="width:170px" property="strCountry" size="1" styleId="Country" styleClass="dropdownlist" onchange="funCountryState();" tabindex="15">
	                  <html:option value="">Select Country</html:option> 
	                  <html:optionsCollection name="countriesList" value="countryNo" label="countryNames"/> 
		             </html:select>
	              </td> 
	              
				  </tr>
	 <tr>
				  <td align="left" class="labelField"><bean:message key="VIMSApplication.state"/></td>
			      <td>&nbsp;&nbsp;<html:select style="width:170px" property="strState" size="1" styleId="statesList" styleClass="dropdownlist" tabindex="16">
	                  <html:option value=" ">Select State</html:option> 
	                  <c:if test="${statesList ne null}">
	                  <html:optionsCollection name="statesList" value="stateNo" label="stateNames"/> 
		             </c:if>
		              </html:select>
	              </td> 
	 </tr>
	 
		<tr>
		<td class="labelField" align="left"><bean:message key="VIMSApplication.zip"/></td>
		<td  align="left">&nbsp;&nbsp;<html:text property ="strZip" maxlength="16" style="width:117px" styleClass="textbox_default" onkeypress="return onlyNumbers();" tabindex="17"/> 
	 	&nbsp;&nbsp;<font size="2" color="red"><i><html:errors property="strZip.Error"></html:errors></i></font></td> 
	 </tr>	
   </table>
  </fieldset> 
  <html:hidden property="subType" value="Modify"></html:hidden>
   <tr><td colspan="5" height="10px"></TD></tr>
	<tr>		
		<td colspan="5" align="left">
			<table border="0" width="100%">
			<tr>
			<td width="20%">&nbsp;</td>
			  <td>&nbsp;&nbsp;<button type="button" name="Modify" class="linkbutton_background" tabindex="18" onclick="fnSubmit();" accesskey="U"><u>U</u>pdate</button>&nbsp;&nbsp;&nbsp;&nbsp;
			  <button type="button"  name="back" class="linkbutton_background" tabindex="19" onClick="fnGoBack();" accesskey="C"><u>C</u>ancel</button>
			</td>
		 </tr>
		
		</table>
   </tr>
 
 </html:form>
 </td>
 </tr>
 </table>
 </body> 
</html:html>