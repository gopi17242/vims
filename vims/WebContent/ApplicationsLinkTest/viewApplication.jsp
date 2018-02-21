<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html>
<head>
<script type="text/javascript">
function NewWin(OpenFile){

	if (navigator.appName == "Microsoft Internet Explorer")
	{
		window.open(OpenFile, "UploadFile", 'toolbar=no, status=yes, left=0, top=0, scrollbars=yes, resize=yes,  width=800, height=600');
	}
	else if (navigator.appName == "Netscape") 
	{
		window.open("./FileUpload.jsp", "UploadFile",  'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=yes,resizable=0,width=450,height=350,top=100,left=200');
	}
}
</script>
</head> 
<body>
<br>
<c:forEach items="${appView}"  var="ViewApplication" step="1">
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
<tr>
    <td align="left"><fieldset><legend class="heading_blue">Application</legend>
    
    
    <table border="0" width="100%" STYLE="table-layout:fixed;">
			
			 <tr>	       
				<td class="labelField" align="left" width="24%"><b>Customer Name</b></TD>
				<td  class="labelField" align="left" width="76%"><c:out value='${customer.customerName}'/></td>				
			</tr>
			<tr>	       
				<td class="labelField" align="left"><b>Application Name</b></TD>
				<td class="labelField" align="left">
					<c:out value='${ViewApplication.appName}'/>
				</td>				
			</tr>
			<tr>	       
				<td class="labelField" align="left"><b>Status</b></TD>
				<td class="labelField" align="left">
					<c:out value='${ViewApplication.appStatus}'/>
				</td>				
			</tr>
			<tr>	       
				<td class="labelField" align="left"><b>Application Owner</b></TD>
				<td class="labelField" align="left">
					<c:out value='${ViewApplication.appOwner}'/>
				</td>				
			</tr>
			<tr valign="top">	       
				<td class="labelField" align="left"><b>Application Owner's E-mail</b></TD>
				<td class="labelField" align="left">
					<c:out value='${ViewApplication.appOwnerEmail}'/>
				</td>				
			</tr>
			
			<tr>	       
				<td class="labelField" align="left"><b>Primary Contact</b></TD>
				<td class="labelField" align="left">
					<c:out value='${ViewApplication.primCont}'/>
				</td>				
			</tr>
			<tr valign="top">	      
				<td class="labelField" align="left"><b>Primary Contact's E-mail</b></TD>
				<td class="labelField" align="left">
					<c:out value='${ViewApplication.primContEmail}'/>
				</td>				
			</tr>
			<tr>	       
				<td class="labelField" align="left"><b>Start Date</b></td>
				<td class="labelField" align="left"><c:out value='${ViewApplication.supBegDate}'/></td>				
			</tr>
			<tr>	       
				<td class="labelField" align="left"><b>End Date</b></td>
				<td class="labelField" align="left"><c:out value='${ViewApplication.supEndDate}'/></td>				
			</tr>			
			<tr>	       
				<td class="labelField" align="left"><b>Support Center</b></td>
				<td class="labelField" align="left"><c:out value='${ViewApplication.supportCenterName}'/></td>				
			</tr>			
			<tr>	       
				<td class="labelField" align="left"><b>Support Manager Name</b></TD>
				<td class="labelField" align="left"><c:out value='${ViewApplication.supportManagerName}'/></td>				
			</tr>
			<tr>	       
				<td class="labelField" align="left"><b>Group Name</b></TD>
				<td class="labelField" align="left"><c:out value='${group.grpName}'/></td>				
			</tr>
			<tr>
				<td class="labelField" valign="top" align="left"><b>Attachments</b></td>
				<td>
				<c:if test="${ViewApplication.attachment1 != null}">
				<a href="#" color="blue" class="linkbtn" onclick="NewWin('./VIMSUPLOAD/<c:out value="${ViewApplication.appId}"/>/<c:out value="${ViewApplication.attachment1}"/>')">
				<c:out value="${ViewApplication.attachment1}"/></a>
				<br>
				</c:if>				
				<c:if test="${ViewApplication.attachment2 ne null}">
				<a href="#" color="blue" class="linkbtn" onclick="NewWin('./VIMSUPLOAD/<c:out value="${ViewApplication.appId}"/>/<c:out value="${ViewApplication.attachment2}"/>')">
				<c:out value="${ViewApplication.attachment2}"/></a>				
				<br>
				</c:if>				
				<c:if test="${ViewApplication.attachment3 ne null}">
				<a href="#" color="blue" class="linkbtn" onclick="NewWin('./VIMSUPLOAD/<c:out value="${ViewApplication.appId}"/>/<c:out value="${ViewApplication.attachment3}"/>')">
				<c:out value="${ViewApplication.attachment3}"/></a>				
				<br>
				</c:if>
				<c:if test="${ViewApplication.attachment4 ne null}">
				<a href="#" color="blue" class="linkbtn" onclick="NewWin('./VIMSUPLOAD/<c:out value="${ViewApplication.appId}"/>/<c:out value="${ViewApplication.attachment4}"/>')">
				<c:out value="${ViewApplication.attachment4}"/></a>				
				<br>
				</c:if>			
				<c:if test="${ViewApplication.attachment5 ne null}">
				<a href="#" color="blue" class="linkbtn" onclick="NewWin('./VIMSUPLOAD/<c:out value="${ViewApplication.appId}"/>/<c:out value="${ViewApplication.attachment5}"/>')">
				<c:out value="${ViewApplication.attachment5}"/></a>			
				<br>
				</c:if>
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
        <td style="width:50%"><fieldset>
          <legend class="heading_blue">Modules</legend>
          <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">
            <html:form action="VIMSApplicationAction.do">
			<tr>
				<td class="labelField" align="left" width="48%"><b>Sub-Categories</b></td>
				<td align="left" valign="top" ><html:select property="appSubCatName" multiple="true" size="6"  styleClass="multiplelist" styleId="selectDest1" style="WIDTH: 200px">
       					<html:optionsCollection name="subCategories" value="subcat" label="subcat"></html:optionsCollection>
       				</html:select>							
				</td>
			</tr>
			</html:form>
          </table>
        </fieldset></td>
		<td>&nbsp;</td>
        <td style="width:50%"><fieldset>
          <legend class="heading_blue">Specialists</legend>
          <table align="center" width="100%" border="0" cellspacing="0" cellpadding="0">
          	<html:form action="VIMSApplicationAction.do">
            <tr>
				<td class="labelField" align="left" width="48%"><b>Specialist Names</b></td>
				<td align="left" valign="top"><html:select property="appSpecialists" multiple="true" size="6"  style="width : 200px" styleClass="multiplelist" styleId="selectDest" >
        			<html:optionsCollection name="specialists" value="specId" label="specName"/>
        		</html:select>
					
				</td>		
				</tr>
				
			</html:form>
          </table>
        </fieldset></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
     <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        
        <td style="width:100%"><fieldset>
          <legend class="heading_blue">Service Level Agreement</legend>
          <table border="0" align="left" width="100%">
          
          <tr>
			<td class="heading_blue" align="left" width="24%"><b>Severity</b></td>
			<td align="left" class="labelField" width="8%"><bean:message key ="VIMS.SLA.CRITICAL"/></td>
			<td align="left" class="labelField" width="8%"><bean:message key ="VIMS.SLA.MAJOR"/></td>
			<td align="left" class="labelField" width="8%"><bean:message key ="VIMS.SLA.MINOR"/></td>
			<td>&nbsp;</td>
		 </tr>
		 <tr>
			<td class="heading_blue" align="left" ><b>Response Time(Hrs)</b></td>	
			<td  align="left" class="labelField" ><c:out value='${ViewApplication.CRITICAL_RESPONSE_TIME}'/></td>
			<td  align="left" class="labelField" ><c:out value='${ViewApplication.MAJOR_RESPONSE_TIME}'/></td>
			<td  align="left" class="labelField" ><c:out value='${ViewApplication.MINOR_RESPONSE_TIME}'/></td>
			<td class="labelField" ><font color="red" size="2">&nbsp;</td>
		</tr>
		<tr>
			<td class="heading_blue" align="left" ><b>Warning Before(Hrs)</b></td>
			<td align="left" class="labelField" ><c:out value='${ViewApplication.CRITICAL_WARNING_INTERVAL}'/></td>		
			<td align="left" class="labelField" ><c:out value='${ViewApplication.MAJOR_WARNING_INTERVAL}'/></td>	
			<td  align="left" class="labelField" ><c:out value='${ViewApplication.MINOR_WARNING_INTERVAL}'/></td>
		    <td class="labelField" ><font color="red" size="2">&nbsp;</td>
		</tr>
		 
		 
							
				<!--<tr>
					<td class="labelField" align="left"><B>Issue Type</B></td>
					<td class="labelField" align="center"><B>Response Time(Hrs)</B></td>
					<td class="labelField" align="center"><B>Warning Before(Hrs)</B></td>
				</tr>
				<tr>
					<td class="labelField" align="left"><b>Critical</b></td>
					<td class="labelField" align="center"><c:out value='${ViewApplication.CRITICAL_RESPONSE_TIME}'/></td>
					<td class="labelField" align="center"><c:out value='${ViewApplication.CRITICAL_WARNING_INTERVAL}'/></td>
				</tr>
				<tr>
					<td class="labelField" align="left"><b>Major</b></td>
					<td class="labelField" align="center"><c:out value='${ViewApplication.MAJOR_RESPONSE_TIME}'/></td>
					<td class="labelField" align="center"><c:out value='${ViewApplication.MAJOR_WARNING_INTERVAL}'/></td>
				</tr>	
				<tr>
					<td class="labelField" align="left"><b>Minor</b></td>
					<td class="labelField" align="center"><c:out value='${ViewApplication.MINOR_RESPONSE_TIME}'/></td>
					<td class="labelField" align="center"><c:out value='${ViewApplication.MINOR_WARNING_INTERVAL}'/></td>
				</tr>	
				--><tr>
					<td class="labelField" colspan="5" align="left"><b>E-mail notification on warning interval:</b>
					<c:if test="${ViewApplication.sendMail == 1}">
					Yes
					</c:if>
					<c:if test="${ViewApplication.sendMail == 0}">
					No
					</c:if>
					</td>					
				</tr>	 
			</table>		
        </fieldset></td>
      </tr>      
    </table>
    </td>
  </tr>
  <tr> 
  <td>
	  <table> 
		  <tr>
		      <td width="70%">&nbsp;</td>	
				<td  align="left"><button type="button" onclick="window.history.back();" accesskey="C" class="linkbutton_background"><u>C</u>ancel</button>
				</td>
			</tr>
	 </table>
 	</table>
</c:forEach>
</body>
</html>