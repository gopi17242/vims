<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.*"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

<script type="text/javascript" src="./script-test/applications.js"></script>
<script type="text/javascript" src="./script-test/advPopCalender.js"></script>
<script type="text/javascript" src="./script-test/VIMS_Alerts.js"></script>
<script type="text/javascript" src="./script-test/multifile.js"></script>
<script type="text/javascript">	
		
		var emailfilter=/^\w+[\+\.\w-]*@([\w-]+\.)*\w+[\w-]*\.([a-z]{2,4}|\d+)$/i
		function checkmail()
		{
			var returnval=emailfilter.test(document.getElementById("email").value)
			
				if (returnval==false)
				{
					document.getElementById("msg").innerHTML="Please enter a valid E-mail";
					document.getElementById("email").select();				
				}
				if(returnval==true)
				{
					document.getElementById("msg").innerHTML=" ";  
				}
			 
			return returnval
		}		
		function onlyNonZero(id)
		{
			if(id.value==0)
			{
				id.setAttribute("value","");
				alert(onlyZero);
				id.focus();
				//document.getElementById("slaMsg").innerHTML="SLA cannot be zero";
				return false;
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
					document.getElementById("primContSpanid").innerHTML="Please enter a valid E-mail";
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
		
		function NewWin(OpenFile)
		{		
			if (navigator.appName == "Microsoft Internet Explorer")
			{
				window.open(OpenFile, "UploadFile", 'toolbar=no, status=yes, left=0, top=0, scrollbars=yes, resize=yes,  width=800, height=600');
			}
			else if (navigator.appName == "Netscape") 
			{
				window.open("./FileUpload.jsp", "UploadFile",  'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=yes,resizable=0,width=450,height=350,top=100,left=200');
			}
		}
		
	function fnGoBack()
   {
    window.location="./viewApplicationList.do?param=viewApplicationList&menuId=Application&pageId=Applications";
    }
	</script>
</head>

<body>
<br>
<html:form action="modifyApplication.do" enctype="multipart/form-data" focus="custId">
<c:forEach items="${appDet}"  var="Applications" step="1">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left"><fieldset><legend class="heading_blue">Application</legend>	 
	<table width="100%" border="0" cellspacing="0" cellpadding="0">		
					<tr>
						<td colspan="5"  height="10px"><Font color="red"><c:out value="${resultMsg}"/></Font></td>
					</tr>					
					<tr>	       
					<td class="labelField" align="left" width="20%">Customer Name</td>
					<td  align="left" colspan="4" width="80%"><font color="red" class="labelmandatory">*</font><html:select style="width:250px" onchange="fnGetAppOwners()" styleId="custField" property="custId" styleClass="dropdownlist">
					<c:forEach items="${custIdList}" var="custIdList">
					<c:choose>
						<c:when test="${custIdList.customerId == customer.customerId}">
							<option selected="selected" value="<c:out value='${custIdList.customerId}'/>"><c:out value='${custIdList.customerName}'/></option>
						</c:when>
						<c:otherwise>
							<option value="<c:out value='${custIdList.customerId}'/>"><c:out value='${custIdList.customerName}'/></option>
						</c:otherwise>
					</c:choose>
					</c:forEach>
				</html:select><html:errors property="custId"/>
				</td>
			</tr>
			<tr>
				<td colspan="1" width="20%" class="labelField">Application Name</td>
				<td  align="left" colspan="4"><font color=red class="labelmandatory">*</font><input type="text" maxlength="100" style="width:250px"  class="textbox_default" name="appName" onchange="showHint()" id="appName" value="<c:out value='${Applications.appName}'/>"/>
				<input class="textbox_default" maxlength="16" style="width: 150px" type="hidden" readonly name="appId" value="<c:out value='${Applications.appId}'/>"/>
				<font color="red" size="2"><I><span id="showMsg"></span><html:errors property="appName"/></I></font></td>
			</tr>
			<tr>
				<td  align="left" class="labelField">Status</td>
				<td  align="left" colspan="4">&nbsp;
				<c:if test="${Applications.appStatus=='Active'}"><html:select onchange="checkStatus();" styleId="status" property="appStatus" size="1" style="width:80px" styleClass="textbox_default">
							<option selected value="Active">Active</option>
							<option value="Inactive">Inactive</option>
							</html:select>
						</c:if>
						<c:if test="${Applications.appStatus=='Inactive'}">
							<html:select property="appStatus" size="1" style="width:100px" styleClass="textbox_default">
							<option  value="Active">Active</option>
							<option selected value="Inactive">Inactive</option>
							</html:select>
						</c:if>			
					&nbsp;&nbsp;<font color="red" size="2"><I><html:errors property="appStatus"/></I></font>
				</td>
			</tr>
			<tr>
				<td class="labelField">Application Owner</td>
				<td  align="left" colspan="4"><font color=red class="labelmandatory">*</font><input class="textbox_default" maxlength="50" id="ownerField" style="width:250px" type="text" name="appOwner" value="<c:out value='${Applications.appOwner}'/>"/>
				&nbsp;<a href="#" onclick="fnLookup()" class="linkbtn">Lookup</a>&nbsp;<font color="red" size="2"><I><html:errors property="appOwner"/></I></font></td>
			</tr>
			<tr>
				<td class="labelField" width="20%" align="left">Application Owner's E-mail</td>
				<td  align="left" colspan="1" width="34%"><font color="red" class="labelMandatory">*</font><input type="text" id="email" onchange="return checkmail();" maxlength="50" style="width:250px" name="appOwnerEmail" class="textbox_default" value="<c:out value='${Applications.appOwnerEmail}'/>"/>
				</td><td colspan="3" id="appOwners1" style="display:none"><html:select  styleClass="textbox_default"  property="appOwners" onchange="fnSelectOwner()" styleId="appOwners">
					<option value="">Select Owner</option>
					<html:optionsCollection name="ownerList" value="ownerMail" label="ownerName"/>
				</html:select>
			&nbsp;<font color="red" size="2"><I><span id="msg"></span><html:errors property="appOwnerEmail"/></I></font></td>			
			</tr>
			<tr>
				<td class="labelField">Start Date</td>
				<td  align="left" colspan="4"><font color=red class="labelmandatory">*</font><input class="textbox_default"  style="width:80px" type="text" name="supBegDate" id="SupportBeginDate" size="25" readonly value="<c:out value='${Applications.supBegDate}'/>"/>
					
					<script>
				     document.write('<a href="#" class="link_btn" onclick="advPopUpCalendar(window.document.forms[0].supBegDate,window.document.forms[0].supBegDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
				 </script>		
					<font color="red" size="2"><I><html:errors property="supBegDate"/></I></font></td>
				
			</tr>
			<tr>
				<td class="labelField">End Date</td>
				<td  align="left" colspan="4"><font color=red class="labelmandatory">*</font><input class="textbox_default"  style="width:80px"type="text" name="supEndDate" id="SupportEndDate" size="25" readonly value="<c:out value='${Applications.supEndDate}'/>"/>
					 <script>
				     document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].supEndDate,window.document.forms[0].supEndDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
				 </script>
					<font color="red" size="2"><I><html:errors property="supEndDate"/></I></font></td>
				
			</tr>
			
			<tr>
				<td class="labelField">Primary Contact</td>
				<td  align="left" colspan="4">&nbsp;&nbsp;<input class="textbox_default" maxlength="50" style="width:250px" type="text" name="primCont" value="<c:out value='${Applications.primCont}'/>"/>
				</td>
			</tr>
			<tr>
				<td class="labelField" align="left">Primary Contact's E-mail&nbsp;</td>
				<td  align="left" colspan="4">&nbsp;&nbsp;<input type="text" name="primContEmail" onblur="checkPrimContMail()" id="primContEmail" maxlength="50" style="width:250px" class="textbox_default" value="<c:out value='${Applications.primContEmail}'/>"/>
				<font color="red" size="2"><I><span id="primContSpanId"></span></I></font></td>
			</tr>
			<tr>
			<tr>
				<td class="labelField">Support Center Name</td>
				<td  align="left" colspan="4">&nbsp;&nbsp;<html:select styleClass="dropdownlist"  style="width:250px" styleId="supCen" property="supportCenter" size="1" onchange="fnChange();">
						<c:forEach items="${supportCenterList}"  var="supportCenterList" step="1">
						<c:choose> 
						  <c:when test="${Applications.supportCenter == supportCenterList.suppCenId}" > 
						    <option selected="selected" value="<c:out value='${Applications.supportCenter}'/>"><c:out value='${Applications.supportCenterName}'/></option>
						  </c:when> 
						  <c:otherwise> 
						   <option value="<c:out value='${supportCenterList.suppCenId}'/>"><c:out value='${supportCenterList.suppCenName}'/></option>
						  </c:otherwise> 
						</c:choose> 
						</c:forEach>			
					</html:select>
					<font color="red" size="2"><I><html:errors property="supportCenter"/></I></font>
				</td>				
			</tr>
			<tr>
				<td class="labelField">Support Manager</td>
				<td  align="left" colspan="4">&nbsp;
				<c:set var="suppMgr" value="${supportManager}" scope="request" />					
					  	<input name="appSpecComment" readonly maxlength="25"  style="width:250px" class="textbox_default" id="supMgrName" type="text"   value="<c:out value='${Applications.supportManagerName}'/>">
					 <font color="red" size="2"><I><html:errors property="supportManager"/></I></font>
					<input type="hidden" name="supportManager" id="supMgrId" value="<c:out value='${Applications.supportManager}'/>"/>
				</td>				
			</tr>
			<tr>				
				<td class="labelField" align="left">Group Name</td>
				<td  align="left" colspan="4"><font color=red class="labelmandatory">*</font><html:select property="grpId" styleId="grpSel" onchange="getGrpMbrs();" style="width:250px" styleClass="dropdownlist">
									<c:forEach begin="1" items="${grpIdList}" var="grpIdList">
										<c:choose>
											<c:when test="${grpIdList.grpId == group}">
												<option selected value="<c:out value='${grpIdList.grpId}'/>"><c:out value='${grpIdList.grpName}'/></option>
											</c:when>
											<c:otherwise>
												<option selected value="<c:out value='${grpIdList.grpId}'/>"><c:out value='${grpIdList.grpName}'/></option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
																
							</html:select><font color="red" size="2"><I><html:errors property="grpId"/></I></font>
				</td>
				
			</tr>	
				<tr>
				<td class="labelField" valign="top" align="left" >Attachments</td>			
				<td valign="top" colspan="4" class="labelField" align="left" width="40%">&nbsp;&nbsp;<html:file property="transitionFile" onclick="fnAtt()" styleId="my_file_element" />Max <span id="filesCount"><c:out value="${filesCount}"/></span> files.
					<input type="hidden" name="filesUploaded" value="<c:out value='${filesUploaded }'/>" id="filesUploaded"/>
					</td>		
					</tr> 
					<tr valign="top" >
						<td valign="top"  class="labelField"  align="left">&nbsp;</td>
						<td    colspan="4" >
						<table border="0" width="98%" align="center" STYLE="table-layout:fixed;">
						<tr>
						<td>
								<div id="files_list" class="linkbtn"></div>
								<script>
								function fnAtt()
								{
									var multi_selector = new MultiSelector( document.getElementById( 'files_list' ), document.getElementById("filesCount").innerHTML ); 
									multi_selector.addElement( document.getElementById( 'my_file_element' ) );
								}
								</script>					
								</td></tr>					
					</table>
					<div id="div1">
					<c:if test="${Applications.attachment1 != null}">
					 &nbsp;<a href="#" class="linkbtn" color="blue"  onclick="NewWin('./VIMSUPLOAD/<c:out value="${Applications.appId}"/>/<c:out value="${Applications.attachment1}"/>')">
					<c:out value="${Applications.attachment1}"/></a>&nbsp;&nbsp;&nbsp;&nbsp;<img src="./images/Remove.gif" onclick="deleteFile(1,'./VIMSUPLOAD/<c:out value='${Applications.appId}'/>/<c:out value='${Applications.attachment1}'/>')" style="cursor:hand">
					 <br>
					</c:if> 
				    </div>	
				   
					<div id="div2">
					<c:if test="${Applications.attachment2 != null}">
					 &nbsp;<a href="#" class="linkbtn" color="blue" onclick="NewWin('./VIMSUPLOAD/<c:out value="${Applications.appId}"/>/<c:out value="${Applications.attachment2}"/>')">
					<c:out value="${Applications.attachment2}"/></a>&nbsp;&nbsp;&nbsp;&nbsp;<img src="./images/Remove.gif" onclick="deleteFile(2,'./VIMSUPLOAD/<c:out value='${Applications.appId}'/>/<c:out value='${Applications.attachment2}'/>')" style="cursor:hand">
					<br>
					</c:if>  
					</div>
				
					<div id="div3">
						<c:if test="${Applications.attachment3 != null}">
					&nbsp;<a href="#" class="linkbtn" color="blue" onclick="NewWin('./VIMSUPLOAD/<c:out value="${Applications.appId}"/>/<c:out value="${Applications.attachment3}"/>')">
							<c:out value="${Applications.attachment3}"/></a>&nbsp;&nbsp;&nbsp;&nbsp;<img src="./images/Remove.gif" onclick="deleteFile(3,'./VIMSUPLOAD/<c:out value='${Applications.appId}'/>/<c:out value='${Applications.attachment3}'/>')" style="cursor:hand">
										 <br></c:if>
					</div>
				
					<div id="div4">
					<c:if test="${Applications.attachment4 != null}">
					&nbsp;<a href="#" class="linkbtn" color="blue" onclick="NewWin('./VIMSUPLOAD/<c:out value="${Applications.appId}"/>/<c:out value="${Applications.attachment4}"/>')">
					<c:out value="${Applications.attachment4}"/></a>&nbsp;&nbsp;&nbsp;&nbsp;<img src="./images/Remove.gif" onclick="deleteFile(4,'./VIMSUPLOAD/<c:out value='${Applications.appId}'/>/<c:out value='${Applications.attachment4}'/>')" style="cursor:hand">
					 <br>
					
					</c:if> 
					</div>
					
					<div id="div5">
					<c:if test="${Applications.attachment5 != null}">
				    &nbsp;<a href="#" class="linkbtn" color="blue" onclick="NewWin('./VIMSUPLOAD/<c:out value="${Applications.appId}"/>/<c:out value="${Applications.attachment5}"/>')">
					<c:out value="${Applications.attachment5}"/></a>&nbsp;&nbsp;&nbsp;&nbsp;<img src="./images/Remove.gif" onclick="deleteFile(5,'./VIMSUPLOAD/<c:out value='${Applications.appId}'/>/<c:out value='${Applications.attachment5}'/>')" style="cursor:hand">
					  <br></c:if>
					</div>
					
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
	
	<table border="0"width="100%" align="left" cellspacing="1" cellpadding="0" >
	<tr>
		<td class="heading_blue" align="left" width="20%"><b>Severity</b></td>
		<td align="left" class="labelField" width="8%"><bean:message key ="VIMS.SLA.CRITICAL"/></td>
		<td align="left" class="labelField" width="8%"><bean:message key ="VIMS.SLA.MAJOR"/></td>
		<td align="left" class="labelField" width="8%"><bean:message key ="VIMS.SLA.MINOR"/></td>
		<td>&nbsp;</td>
	</tr>
		
		<tr>
			<td class="heading_blue" align="left" ><b>Response Time(Hrs)</b></td>	
			<td  align="left"><font color="red" class="labelMandatory">*</font><input type="text" onchange="return onlyNonZero(document.getElementById('CriRes'));" onkeypress="return onlyNumbers();"  id="CriRes" name="criticalResponseTime" style="width:35px" class="textbox_short" value="<c:out value='${Applications.CRITICAL_RESPONSE_TIME}'/>"/></td>
			<td  align="left" ><font color="red" class="labelMandatory">*</font><input type="text" onchange="return onlyNonZero(document.getElementById('MajRes'));" onkeypress="return onlyNumbers();" class="textbox_short" id="MajRes" style="width:35px" name="majorResponseTime"  value="<c:out value='${Applications.MAJOR_RESPONSE_TIME}'/>"/></td>
			<td  align="left" ><font color="red" class="labelMandatory">*</font><input type="text" onchange="return onlyNonZero(document.getElementById('MinRes'));" onkeypress="return onlyNumbers();" class="textbox_short" id="MinRes" style="width:35px" name="minorResponseTime"  value="<c:out value='${Applications.MINOR_RESPONSE_TIME}'/>"/></td>
			<td><font color="red" size="2"><I><html:errors property="ResTime"/></I></font></td>
			</tr>
		<tr>
			<td class="heading_blue" align="left" ><b>Warning Before(Hrs)</b></td>
			<td align="left"><font color="red" class="labelMandatory">*</font><input type="text" onchange="return onlyNonZero(document.getElementById('CriWarn'));" onkeypress="return onlyNumbers();" class="textbox_short" id="CriWarn" style="width:35px" name="criticalWarningInterval"  value="<c:out value='${Applications.CRITICAL_WARNING_INTERVAL}'/>"/></td>		
			<td align="left" ><font color="red" class="labelMandatory">*</font><input type="text" onchange="return onlyNonZero(document.getElementById('MajWarn'));" onkeypress="return onlyNumbers();" class="textbox_short" id="MajWarn" style="width:35px" name="majorWarningInterval"  value="<c:out value='${Applications.MAJOR_WARNING_INTERVAL}'/>"/></td>	
			<td  align="left" ><font color="red" class="labelMandatory">*</font><input type="text" onchange="return onlyNonZero(document.getElementById('MinWarn'));" onkeypress="return onlyNumbers();" class="textbox_short" id="MinWarn" style="width:35px" name="minorWarningInterval"  value="<c:out value='${Applications.MINOR_WARNING_INTERVAL}'/>"/></td>
		    <td><font color="red" size="2"><I><html:errors property="WarBefore"/></I></font></td>
		</tr>
		<tr>
			<td colspan="4" align="left">&nbsp;</td> 
			<td align="left"><font color="red" size="2"><i>&nbsp;&nbsp;<html:errors property="WarLessThanRes"/></i></font></td>
		</tr>
			<!--<tr>
					<td class="heading_blue" align="left" valign="top" width="20%"><b>Issue Type</b></td>
					<td class="heading_blue" align="left" valign="top" width="32%"><b>Response Time(Hrs)</b></td>
					<td class="heading_blue" align="left" valign="top" width="33%" ><b>Warning Before(Hrs)</b></td>	
					<td>&nbsp;</td>	
					<td>&nbsp;</td>		
			</tr>	
			<tr>
				<td align="left" class="labelField" width="20%">Critical&nbsp;&nbsp;</td>
				<td align="left" colspan="1" width="32%"><font color="red" class="labelMandatory">*</font><input type="text" onchange="return onlyNonZero(document.getElementById('CriRes'));" onkeypress="return onlyNumbers();"  id="CriRes" name="criticalResponseTime" style="width:35px" class="textbox_short" value="<c:out value='${Applications.CRITICAL_RESPONSE_TIME}'/>"/><font color="red" size="2"><i>&nbsp;&nbsp;<html:errors property="criticalResponseTime"></html:errors></i></font></td>
				<td  align="left" colspan="1" width="33%"><font color="red" class="labelMandatory">*</font><input type="text" onchange="return onlyNonZero(document.getElementById('CriWarn'));" onkeypress="return onlyNumbers();" class="textbox_short" id="CriWarn" style="width:35px" name="criticalWarningInterval"  value="<c:out value='${Applications.CRITICAL_WARNING_INTERVAL}'/>"/><font color="red" size="2"><i>&nbsp;&nbsp;<html:errors property="criticalWarningInterval"></html:errors></i></font></td>
				<td>&nbsp;</td>	
				<td>&nbsp;</td>	
			</tr>
			
			<tr>
				<td align="left" class="labelField">Major&nbsp;&nbsp;</td>
				<td align="left" colspan="1" width="32%"><font color="red" class="labelMandatory">*</font><input type="text" onchange="return onlyNonZero(document.getElementById('MajRes'));" onkeypress="return onlyNumbers();" class="textbox_short" id="MajRes" style="width:35px" name="majorResponseTime"  value="<c:out value='${Applications.MAJOR_RESPONSE_TIME}'/>"/><font color="red" size="2"><i>&nbsp;&nbsp;<html:errors property="majorResponseTime"></html:errors></i></font></td>
				<td align="left" colspan="1" width="33%"><font color="red" class="labelMandatory">*</font><input type="text" onchange="return onlyNonZero(document.getElementById('MajWarn'));" onkeypress="return onlyNumbers();" class="textbox_short" id="MajWarn" style="width:35px" name="majorWarningInterval"  value="<c:out value='${Applications.MAJOR_WARNING_INTERVAL}'/>"/><font color="red" size="2"><i>&nbsp;&nbsp;<html:errors property="majorWarningInterval"></html:errors></i></font>
				<td>&nbsp;</td>	
				<td>&nbsp;</td>	
			</tr>

			<tr>		
				<td align="left" class="labelField">Minor&nbsp;&nbsp;</td>
				<td align="left" colspan="1" width="33%"><font color="red" class="labelMandatory">*</font><input type="text" onchange="return onlyNonZero(document.getElementById('MinRes'));" onkeypress="return onlyNumbers();" class="textbox_short" id="MinRes" style="width:35px" name="minorResponseTime"  value="<c:out value='${Applications.MINOR_RESPONSE_TIME}'/>"/><font color="red" size="2"><i>&nbsp;&nbsp;<html:errors property="minorResponseTime"></html:errors></i></font>
				</td>	
				<td align="left" colspan="1" width="33%"><font color="red" class="labelMandatory">*</font><input type="text" onchange="return onlyNonZero(document.getElementById('MinWarn'));" onkeypress="return onlyNumbers();" class="textbox_short" id="MinWarn" style="width:35px" name="minorWarningInterval"  value="<c:out value='${Applications.MINOR_WARNING_INTERVAL}'/>"/><font color="red" size="2"><i>&nbsp;&nbsp;<html:errors property="minorWarningInterval"></html:errors></i></font>
				</td>
				<td>&nbsp;</td>	
				<td>&nbsp;</td>						
			</tr>
			--><tr>
				<td colspan="1" align="left">
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
    <td><tr>
    <td><table width="100%%" border="0"cellspacing="0" cellpadding="0">
      <tr>
        <td style="width:50%"><fieldset>
          <legend class="heading_blue">Modules</legend>
          <table align="center" width="100%" border="0"cellspacing="0" cellpadding="0">          
             <tr>			
				<td class="labelField" align="left" width="20%" valign="middle">Sub Category</td>
				<td valign="middle" width="33%" align="left">&nbsp;&nbsp;<html:text property="subCatId"  styleId="subCatId" value="" maxlength="50" style="width:200px" size="20" styleClass="textbox_default" /><br><font color="red" size="2"><I><span id="subCatSpanId"></span></I></font></td>
				<td  align="center" valign="middle" width="6%">
			         <p>
			            <input type="button" name="btnRight3" style="width : 30px" value=">>" class="linkbutton_background" onClick="return moveOriginIndstTypes2()"/>
					 <br>            
			           <input type="button" name="btnLeft3"  style="width : 30px" value="<<"" class="linkbutton_background" onClick="return moveDestIndstTypes2()"/>		           
			         </p>
		       </td>		       
		        <td width="39%" align="center">&nbsp;&nbsp;<html:select property="appSubCatName" multiple="true" size="6"  styleClass="multiplelist" styleId="selectDest1" style="WIDTH: 200px">
		        		<html:optionsCollection name="subCategories" value="subcat" label="subcat"></html:optionsCollection>
		        	</html:select><br><font color="red" size="2"><I><html:errors property="appSubCatName"/></I></font>
				</td>
				<td width="2%" valign="top">&nbsp;&nbsp;</td>					
			 </tr>	
			 
          </table>
        </fieldset></td>
        </tr>
		<tr>
		
        <td style="width:50%"><fieldset>
          <legend class="heading_blue">Specialists</legend>
          <table align="center" width="100%" border="0"cellspacing="0" cellpadding="0">
            
          <tr>				
			<td class="labelField" align="left" width="20%" valign="middle">Specialist Name</td>
		    <td valign="middle" width="33%" align="left">&nbsp;&nbsp;<html:select property="empId" styleId="specSel" size="6" style="width :200px" styleClass="multiplelist">
			<c:forEach items="${empList}" var="empList">
			      <c:set var="found" value="false"/>		
					<c:forEach items="${specialists}" var="specialists">
					<c:if test="${specialists.specId eq empList.empid}">
						<c:set var="found" value="true" />						
					</c:if>
					</c:forEach>
					<c:if test="${found eq 'false'}">
					<option value="<c:out value='${empList.empid}'/>"><c:out value='${empList.name}'/></option>
					</c:if>				
			</c:forEach>
		  	</html:select>
		</td>
		 <td  align="left" valign="middle" width="6%">
        	<p>
             &nbsp;&nbsp;<input type="button" style="width : 30px" name="btnRight2" value=">>" class="linkbutton_background" onClick="return moveOriginIndstTypes1()">
			<br>        
          	 &nbsp;&nbsp;<input type="button" style="width : 30px" name="btnLeft2" value="<<" " class="linkbutton_background" onClick="return moveDestIndstTypes1()">
           </p> 
       </td>
        <td width="39%" align="center">&nbsp;&nbsp;<html:select property="appSpecialists" multiple="true" size="6"  style="width : 200px" styleClass="multiplelist" styleId="selectDest" >
        	<html:optionsCollection name="specialists" value="specId" label="specName"/>
        </html:select><br><font color="red"><html:errors property="appSpecialists"/></font></td>  
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
  		<button type="button" onclick="fnModifyApplication()" class="linkbutton_background" accesskey="u"><u>U</u>pdate</button>&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" accesskey="c" onClick="fnGoBack();" class="linkbutton_background" tabindex="7"><u>C</u>ancel</button>		
		</td>
 	 </tr>
</table>
</td>
</tr>
</table>
</c:forEach>
</html:form>
	<c:if test="${filesCount==0}"><script>document.getElementById("my_file_element").disabled=true;</script></c:if>
</body>
</html:html>