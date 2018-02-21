<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page import="java.util.*" %>
<html:html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
	<script type="text/javascript" src="./script-test/applications.js"></script>
	<script type="text/javascript" src="./script-test/VIMS_Alerts.js"></script>
	<script type="text/javascript" src="./script-test/multifile.js"></script>
	<script language="javascript" src="./script-test/advPopCalender.js" type="text/javascript"></script>
	<script type="text/javascript">
		
		var emailfilter=/^\w+[\+\.\w-]*@([\w-]+\.)*\w+[\w-]*\.([a-z]{2,4}|\d+)$/i
		function checkmail()
		{
			var returnval=emailfilter.test(document.getElementById("email").value)
			if(document.getElementById("email").value=="")
			{ 
				document.getElementById("msg").innerHTML=" ";
			}
			else
			{ 
				if (returnval==false)
				{
					//alert("Please enter a valid email address.");
					document.getElementById("msg").innerHTML="Please enter a valid E-mail";
					document.getElementById("email").select();				
				}
				if(returnval==true)
				{
					document.getElementById("msg").innerHTML=" ";
				}
			}
			return returnval
		}
		function onlyNonZero(id)
		{
			if(id.value!="")
			{
				if(id.value==0)
				{
					id.setAttribute("value","");
					alert(onlyZero);
					id.focus();
					return false;
				}
			}			
			return true;
		}
		
		function onlyNumbers(evt)
		{   
			var e = event || evt; // for trans-browser compatibility    
			var charCode = e.which || e.keyCode;    
			if (charCode > 31 && (charCode < 48 || charCode > 57)) 
			{  
				alert(onlyNumeric); 
				//document.getElementById("slaMsg").innerHTML="Enter only numerics";
				return false;
			}	
			return true;
		}
		function checkPrimContMail()
		{
			var returnval=emailfilter.test(document.getElementById("primContEmail").value)
			if(document.getElementById("primContEmail").value=="")
			{
				document.getElementById("primContSpanid").innerHTML=" ";
			}
			else
			{
				if (returnval==false)
				{
					//alert("Please enter a valid email address.");
					document.getElementById("primContSpanid").innerHTML="Please enter a valid E-mail ";
					document.getElementById("primContEmail").select();				
				}
				if(returnval==true)
				{
					document.getElementById("primContSpanid").innerHTML=" ";
				}
			}
			return returnval
		}
		function isChar()
		{
			var vString=/^[a-zA-Z]+$/
				if(document.getElementById("subCatId").value=="")
				{
					document.getElementById("subCatSpanId").innerHTML=" ";
				}
				else
				{
				    var rValue=vString.test(document.getElementById("subCatId").value)
					if(rValue==false)
					{
						//alert("Special characters are not allowed");
						document.getElementById("subCatSpanId").innerHTML="Special characters are not allowed";
						document.getElementById("subCatId").select()
					}
					else
					{
						document.getElementById("subCatSpanId").innerHTML="";
					}
				}
			return rValue;
		}
	function fnGoBack()
    {
     	window.location="./viewApplicationList.do?param=viewApplicationList&menuId=Application&pageId=Applications";
    }		
    
</script> 
</head>
<body>
<br>
   <html:form action="addApplication.do" enctype="multipart/form-data">
	
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left" colspan="2"><fieldset><legend class="heading_blue">Application</legend>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">  
		<tr>
			<td colspan="3" width="100%" align="center"><font color="red"><c:out value="${resultMsg}"/></font></td>					
		</tr>		
		
		 <tr>	       
				<td class="labelField" align="left" width="20%">Customer Name</td>
				<td  align="left" width="80%" colspan="2"><font color="red" class="labelMandatory">*</font><html:select onchange="fnGetAppOwners()"  property="custId" style="width:250px" styleId="custField" styleClass="file_Upload">
					<html:option value="">Select Customer Name</html:option>
					<html:optionsCollection name="custIdList" value="customerId" label="customerName"/>
				</html:select>&nbsp;&nbsp;<font color="red" size="2"><I><span id="custName"></span><html:errors property="custId"/></I></font>
				</td>
		</tr>	
		<tr>
			<td class="labelField"  align="left">Application Name</td>
			<td  align="left"  colspan="2"><font color="red" class="labelMandatory">*</font><html:text maxlength="100" style="width:250px"  property="appName" styleId="appName" styleClass="textbox_default" onblur="showHint()"/>
			<font color="red" size="2"><I>&nbsp;&nbsp;<span id="showMsg"></span><html:errors property="appName"/></I></font></td>			
		</tr>
		<tr>
			<td  align="left"  class="labelField">Status</td>
			<td  align="left"  colspan="2">&nbsp;&nbsp;<html:select  onchange="checkStatus();" styleId="status" property="appStatus" size="1" style="width:80px" styleClass="textbox_default">
					<html:option value="Active">Active</html:option>
					<html:option value="Inactive">Inactive</html:option>				
				</html:select>&nbsp;<font color="red" size="2"><I><html:errors property="appStatus"/></I></font>
			</td>					
		</tr>
		<tr valign="top">
			<td class="labelField"  align="left">Application Owner</td>
			<td  align="left"  colspan="2"><font color="red" class="labelMandatory">*</font><html:text maxlength="50" style="width:250px"  styleId="ownerField" property="appOwner" styleClass="textbox_default"/>
			&nbsp;<a href="#" onclick="fnLookup()" class="linkbtn">Lookup</a>&nbsp;<font color="red" size="2" ><I><span id="owner"></span><html:errors property="appOwner"/></I></font></td>			
		</tr>
		<tr valign="top" >
			<td class="labelField"  width="20%" align="left" valign="top">Application Owner's E-mail</td>
			<td  align="left" valign="middle" colspan="1" width="33%" valign="top"><font color="red" class="labelMandatory">*</font><html:text onblur="return checkmail();" styleId="email" maxlength="50" style="width:250px" property="appOwnerEmail" styleClass="textbox_default"/>
			</td><td valign="middle" align="left" id="appOwners1" style="display:none;" >&nbsp;&nbsp;<html:select  styleClass="file_Upload"  property="appOwners" onchange="fnSelectOwner()" styleId="appOwners">
				<option value="">Select Owner</option>
			</html:select>&nbsp;<font color="red" size="2"><I><html:errors property="appOwnerEmail"/><span id="msg"></span></I></font>
			</td>			
		</tr>
		<tr>
			<td class="labelField"  align="left">Start Date</td>
			<td  align="left"  colspan="2" valign="middle"><font color="red" class="labelMandatory">*</font><html:text property="supBegDate"  style="width:80px" styleClass="textbox_default" styleId="SupportBeginDate" readonly="true"/>
				 <script>
				     document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].supBegDate,window.document.forms[0].supBegDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
				 </script>				
				&nbsp;&nbsp;<font color="red" size="2"><I><span id="startDate"></span><html:errors property="supBegDate"/></I></font>		
			</td>					 
		</tr> 
		<tr>
			<td class="labelField"  align="left">End Date</td>
			<td  align="left"  colspan="2" valign="middle"><font color="red" class="labelMandatory">*</font><html:text property="supEndDate"  style="width:80px" styleClass="textbox_default" styleId="SupportEndDate" readonly="true"/>
				 <script>
				     document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].supEndDate,window.document.forms[0].supEndDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
				 </script>
				&nbsp;&nbsp;<font color="red" size="2"><I><span id="endDate"></span><html:errors property="supEndDate"/></I></font>		
			</td>					
		</tr> 
		<tr>
			<td class="labelField"  align="left">Primary Contact</td>
			<td  colspan="2"  align="left">&nbsp;&nbsp;<html:text property="primCont" maxlength="50" style="width:250px" styleClass="textbox_default"/>&nbsp;&nbsp;
			<font color="red" size="4"><I><html:errors property="primCont"/></I></font></td>
		</tr>
		
		<tr>
			<td class="labelField"  align="left">Primary Contact's E-mail</td>
			<td  colspan="2"  align="left">&nbsp;&nbsp;<html:text property="primContEmail" onblur="checkPrimContMail()" styleId="primContEmail" maxlength="50" style="width:250px" styleClass="textbox_default"/>&nbsp;&nbsp;
			<font color="red" size="2"><I><span id="primContSpanId"></span></I></font></td>
		</tr>
		
		<tr>
			<td  align="left"  class="labelField">Support Center</td>
			<td  align="left"  colspan="2"><font color="red" class="labelMandatory">*</font><html:select property="supportCenter" size="1" style="width:250px" styleId="supCen" onchange="fnChange();" styleClass="file_Upload">
					<html:option value="">Select Support Center</html:option>
					 <html:optionsCollection name="supportCenterList" value="suppCenId" label="suppCenName"/>					
				</html:select>&nbsp;&nbsp;<font color="red" size="2"><I><span id="supCenName"></span><html:errors property="supportCenter"/></I></font>
			</td>					
		</tr>		
		<tr>
			<td  align="left"  class="labelField">Support Manager Name</td>
			<td  align="left"  colspan="2"><font color="red" class="labelMandatory">*</font><html:text property="appSpecComment" styleId="supMgrName" style="width:250px" styleClass="textbox_default" readonly="true"/>
				<html:hidden property="supportManager" styleId="supMgrId"/><font color="red" size="2"><I>&nbsp;<span id="supMgr"></span></I></font>
			</td>			
		</tr>
		<tr>				
				<td class="labelField"  align="left" >Group Name</td>
				<td  align="left"  colspan="2"><font color="red" class="labelMandatory">*</font><html:select onchange="fnGetGroupMembers()" property="grpId" styleId="grpSel" style="width:250px" styleClass="file_Upload">
							 <html:option value="">Select Group Name</html:option>
							</html:select>&nbsp;&nbsp;<font color="red" size="2"><I><span id="grpName"></span><html:errors property="grpId"/></I></font>
				</td>				
			 </tr>	
		<tr>
			<td valign="top"   class="labelField" align="left">Attachments</td>		
			<td valign="top"  colspan="2" class="labelField" align="left" >&nbsp;&nbsp;<html:file property="transitionFile" styleId="my_file_element"/>&nbsp;Max 5 files.
			<input type="hidden" name="filesUploaded" id="filesUploaded"/>
			</td>		
		</tr> 
		<tr valign="top" >
			<td valign="top"   class="labelField"  align="left">&nbsp;</td>
			<td  colspan="2" >
			<table border="0" width="98%" align="center" STYLE="table-layout:fixed;">
			<tr>
			<td valign="top"><div id="files_list" class="linkbtn"></div>
				<script>
					var multi_selector = new MultiSelector( document.getElementById( 'files_list' ), 5 );
					multi_selector.addElement( document.getElementById( 'my_file_element' ) );
				</script>
			</td>
			</tr>
			</table>
			</td>
		</tr>		
	</table> 
</fieldset></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><fieldset>
    <legend class="heading_blue">Service Level Agreement</legend>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="heading_blue" align="left" width="20%"><b>Severity</b></td>
			<td align="left" class="labelField" width="8%"><bean:message key ="VIMS.SLA.CRITICAL"/></td>
			<td align="left" class="labelField" width="8%"><bean:message key ="VIMS.SLA.MAJOR"/></td>
			<td align="left" class="labelField" width="8%"><bean:message key ="VIMS.SLA.MINOR"/></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td class="heading_blue" align="left" ><b>Response Time(Hrs)</b></td>	
			<td  align="left"><font color="red" class="labelMandatory">*</font><html:text onchange="return onlyNonZero(document.getElementById('CriRes'));" onkeypress="return onlyNumbers();" property="criticalResponseTime" styleId="CriRes" style="width: 30px;" maxlength="2" styleClass="textbox_short"/></td>
			<td  align="left" ><font color="red" class="labelMandatory">*</font><html:text onchange="return onlyNonZero(document.getElementById('MajRes'));" onkeypress="return onlyNumbers();" property="majorResponseTime" styleId="MajRes" style="width: 30px;" maxlength="2"  styleClass="textbox_short"/></td>
			<td  align="left" ><font color="red" class="labelMandatory">*</font><html:text onchange="return onlyNonZero(document.getElementById('MinRes'));" onkeypress="return onlyNumbers();" property="minorResponseTime" styleId="MinRes" style="width: 30px;" maxlength="2"  styleClass="textbox_short"/></td>
			<td><font color="red" size="2"><I><span id="ResTime"></span><html:errors property="ResTime"/></I></font></td>
		</tr>
		<tr>
			<td class="heading_blue" align="left" ><b>Warning Before(Hrs)</b></td>
			<td align="left"><font color="red" class="labelMandatory">*</font><html:text onchange="return onlyNonZero(document.getElementById('CriWarn'));" onkeypress="return onlyNumbers();" property="criticalWarningInterval" styleId="CriWarn" style="width: 30px;"  maxlength="2" styleClass="textbox_short"/></td>		
			<td align="left" ><font color="red" class="labelMandatory">*</font><html:text onchange="return onlyNonZero(document.getElementById('MajWarn'));" onkeypress="return onlyNumbers();" property="majorWarningInterval" styleId="MajWarn" style="width: 30px;" maxlength="2"  styleClass="textbox_short"/></td>	
			<td  align="left" ><font color="red" class="labelMandatory">*</font><html:text onchange="return onlyNonZero(document.getElementById('MinWarn'));" onkeypress="return onlyNumbers();" property="minorWarningInterval" styleId="MinWarn" style="width: 30px;" maxlength="2"  styleClass="textbox_short"/></td>
		    <td><font color="red" size="2"><I><span id="warBefore"></span><html:errors property="WarBefore"/></I></font></td>
		</tr>
		<tr>
			<td colspan="4" align="left">&nbsp;</td> 
			<td align="left"><font color="red" size="2"><i>&nbsp;&nbsp;<span id="slaMsg"></span><i></font></td>
		</tr>
		
		<!--<tr>
			<td class="heading_blue" align="left" valign="top" width="20%"><b>Issue Type</b></td>
			<td class="heading_blue" align="left" valign="top" width="32%"><b>Response Time(Hrs)</b></td>							
			<td class="heading_blue" align="left" valign="top" width="32%" ><b>Warning Before(Hrs)</b></td>	
			<td colspan="2" width="14%">&nbsp;</td>										
		</tr>	
		<tr>
			<td align="left" class="labelField" width="20%">Critical&nbsp;&nbsp;</td>
			<td align="left"  width="32%"><font color="red" class="labelMandatory">*</font><html:text onchange="return onlyNonZero(document.getElementById('CriRes'));" onkeypress="return onlyNumbers();" property="criticalResponseTime" styleId="CriRes" style="width: 30px;" maxlength="2" styleClass="textbox_short"/><font color="red" size="2"><i>&nbsp;&nbsp;<html:errors property="criticalResponseTime"></html:errors></i></font></td>	
			<td  align="left" colspan="3" width="32%"><font color="red" class="labelMandatory">*</font><html:text onchange="return onlyNonZero(document.getElementById('CriWarn'));" onkeypress="return onlyNumbers();" property="criticalWarningInterval" styleId="CriWarn" style="width: 30px;"  maxlength="2" styleClass="textbox_short"/><font color="red" size="2"><I>&nbsp;&nbsp;<html:errors property="criticalWarningInterval"></html:errors></I></font></td>
		</tr>
		<tr>
			<td align="left" class="labelField">Major&nbsp;&nbsp;</td>
			<td align="left"  width="32%"><font color="red" class="labelMandatory">*</font><html:text onchange="return onlyNonZero(document.getElementById('MajRes'));" onkeypress="return onlyNumbers();" property="majorResponseTime" styleId="MajRes" style="width: 30px;" maxlength="2"  styleClass="textbox_short"/><font color="red" size="2"><i>&nbsp;&nbsp;<html:errors property="majorResponseTime"></html:errors></i></font></td>	
			<td align="left" colspan="3" width="32%"><font color="red" class="labelMandatory">*</font><html:text onchange="return onlyNonZero(document.getElementById('MajWarn'));" onkeypress="return onlyNumbers();" property="majorWarningInterval" styleId="MajWarn" style="width: 30px;" maxlength="2"  styleClass="textbox_short"/><font color="red" size="2"><I>&nbsp;&nbsp;<html:errors property="majorWarningInterval"></html:errors></I></font></td>
		</tr>
		<tr>		
			<td align="left" class="labelField">Minor&nbsp;&nbsp;</td>
			<td align="left"  width="32%"><font color="red" class="labelMandatory">*</font><html:text onchange="return onlyNonZero(document.getElementById('MinRes'));" onkeypress="return onlyNumbers();" property="minorResponseTime" styleId="MinRes" style="width: 30px;" maxlength="2"  styleClass="textbox_short"/><font color="red" size="2"><i>&nbsp;&nbsp;<html:errors property="minorResponseTime"></html:errors></i></font></td>	
			<td align="left" colspan="3" width="32%"><font color="red" class="labelMandatory">*</font><html:text onchange="return onlyNonZero(document.getElementById('MinWarn'));" onkeypress="return onlyNumbers();" property="minorWarningInterval" styleId="MinWarn" style="width: 30px;" maxlength="2"  styleClass="textbox_short"/><font color="red" size="2"><I>&nbsp;&nbsp;<html:errors property="minorWarningInterval"></html:errors></I></font></td>
		</tr>-->
		<tr>
			<td colspan="1">
				<button type="button" onclick="fnGetStdSLA()" style="font-weight:bold;font-size:70%;color:#000000;font-family: Verdana, Arial, Helvetica, sans-serif;TEXT-DECORATION: none;	text-align:center;vertical-align:middle;background-image:url(../Images/button.png);background-repeat:no-repeat;line-height:20px;width:150px;">Import Standard SLA</button>			
			</td>
			<td colspan="4" align="left">
				<font class="labelField">&nbsp;&nbsp;E-mail Notification on warning interval</font>
				<html:radio property="sendMail" value="1"><font class="labelfield">Yes</font></html:radio>
				<html:radio property="sendMail" value="0"><font class="labelfield">No</font></html:radio>
			</td>
		</tr>			
	</table>	
	</fieldset></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="50%">
	        <fieldset>
	          <legend class="heading_blue">Modules</legend>
	          <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">          
	             <tr>				
					<td class="labelField" align="left" width="20%" valign="middle">Sub-Category</td>
					<td valign="middle" width="33%" align="left">&nbsp;&nbsp;<html:text  styleId="subCatId" property="subCatId"  value="" maxlength="100" style="width:200px"  size="20" styleClass="textbox_default" /><br><font color="red" size="2"><I><span id="subCatSpanId"></span></I></font></td>
					 <td  align="center" valign="middle" width="6%">
				         <p>
				         	<input type="button" name="btnRight3" style="width : 30px" value=">>" class="linkbutton_arrow" onClick="return moveOriginIndstTypes2()"/>
				         	<br>     
				           <input type="button" name="btnLeft3"  style="width : 30px" value="<<" class="linkbutton_arrow" onClick="return moveDestIndstTypes2()"/>		           
				         </p>
			       </td>			       
			        <td width="39%" align="center">&nbsp;&nbsp;<select name="appSubCatName" multiple size="6"  class="multiplelist" id="selectDest1" style="WIDTH: 200px"></select><br><font color="red" size="2"><I><html:errors property="appSubCatName"/></I></font>
			        </td>
			        <td width="2%" valign="top">&nbsp;&nbsp;</td>			
				 </tr>
	          </table> 
	        </fieldset>
        </td>
      </tr>    
		<tr>		
        <td width="50%">
        <fieldset>
          <legend class="heading_blue">Specialists</legend>
          <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">            
          <tr>				
			<td class="labelField" align="left" width="20%" valign="middle">Specialist Name</td>        
		    <td valign="middle" width="33%" align="left">&nbsp;&nbsp;<html:select property="empId" styleId="specSel"  size="6" style="width :200px" styleClass="multiplelist">
		  	<!--<html:optionsCollection name="empList" value="empId" label="empName"/>
		  	--></html:select>
			</td>
			 <td  align="center" valign="middle" width="6%">
	        	<p>
	              <input type="button" style="width : 30px" name="btnRight2" value=">>" class="linkbutton_arrow" onClick="return moveOriginIndstTypes1()">
				<br><input type="button" style="width : 30px" name="btnLeft2" value="<<" " class="linkbutton_arrow" onClick="return moveDestIndstTypes1()">
	           </p>
      		 </td>      		 
      		 <td width="39%" align="center">&nbsp;&nbsp;<select name="appSpecialists" multiple size="6"  style="width : 200px" class="multiplelist" id="selectDest"></select><br><font color="red" size="2"><I><html:errors property="appSpecialists"/></I></font></td>  
		 	<td width="2%" valign="top">&nbsp;&nbsp;</td>
		 </tr>
          <tr>          
          </tr>
          </table>
        </fieldset></td>
      </tr>
    </table></td>
  </tr>
  <tr><td colspan="2" height="10px"></TD></tr>
  <tr><td>
  <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">  
  <tr align="left" ><td width="21%"></td>
  </tr>
  	  <tr align="left" ><td>&nbsp;</td>
  		<td  align="left">
  		<button type="button" onclick="fnAddApplication()" class="linkbutton_background" accesskey="s"><u>S</u>ubmit</button>&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="reset" class="linkbutton_background" accesskey="r"><u>R</u>eset</button>&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" accesskey="c" onClick="fnGoBack();" class="linkbutton_background" tabindex="7"><u>C</u>ancel</button>
		</td>
 	 </tr>
</table>
</td>
</tr>
</table>	
	</html:form>
	</body>
</html:html>