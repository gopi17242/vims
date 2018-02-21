<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html>
<head>
<script language="javascript" src="./script-test/advPopCalender.js" type="text/javascript"></script>

<script>
function submitForm() {

      var formObject=document.forms[0];
	  formObject.action="./generateIssueReport.do?param=generateIssueReport"
	  formObject.submit();
  
 /* if((document.forms[0].fromDate.value!='')&&(document.forms[0].toDate.value==''))
   {
        document.getElementById("dateRange").innerHTML="&nbsp;&nbsp;<i><font color=\"red\">To date is required</i></font>";
  }
   else if((document.forms[0].fromDate.value=='')&&(document.forms[0].toDate.value!='')) {
       document.getElementById("dateRange").innerHTML="&nbsp;&nbsp;<i><font color=\"red\">From date is required</i></font>";     
   }
   else {
      document.getElementById("dateRange").innerHTML="&nbsp;&nbsp;<i><font color=\"red\">Date range is required</i></font>";
   }  */
}
function clearForm() {
   
   document.forms[0].issueId.value="";
   document.forms[0].issueStatus.selectedIndex=0;
   document.forms[0].issueSeverity.selectedIndex=0;
   document.forms[0].issuePriority.selectedIndex=0;
   document.forms[0].fromDate.value="";
   document.forms[0].toDate.value="";
   document.getElementById("dateRange").innerText="";
   checkValue(); 

}

function setMode() {

   document.forms[0].issueStatus.selectedIndex=0;
   document.forms[0].issueSeverity.selectedIndex=0;
   document.forms[0].issuePriority.selectedIndex=0;
   document.forms[0].fromDate.value="";
   document.forms[0].toDate.value="";
   document.getElementById("dateRange").innerText="";
   
   document.forms[0].issueStatus.disabled=true;
   document.forms[0].issueSeverity.disabled=true;
   document.forms[0].issuePriority.disabled=true;
   document.forms[0].fromDate.disabled=true;
   document.forms[0].toDate.disabled=true;
}

function checkValue() {

    if(document.forms[0].issueId.value=='') {
         
         document.forms[0].issueStatus.disabled=false;
   		 document.forms[0].issueSeverity.disabled=false;
   		 document.forms[0].issuePriority.disabled=false;
   		 document.forms[0].fromDate.disabled=false;
   		 document.forms[0].toDate.disabled=false;
    }
}

/*function clearSpanField() {
   alert("test");
  if(document.getElementById("dateRange").innerText!='') {
     document.getElementById("dateRange").innerHTML=''; 
  }
}*/

 function fnGoBack()
  {
    window.location="./reportsHome.do?pageId=Reports&menuId=Report";
  }
</script>
</head>
<body>
<br>
<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<html:form action="issueReportForm">
	
		<tr>
			<td class="labelField" align="left" width="20%">Issue Id</td>
			<td  align="left" width="80%">&nbsp;&nbsp;&nbsp;<html:text property="issueId" maxlength="16" styleClass="textbox_default" style="width:117px" tabindex="1" onfocus="setMode()" onblur="checkValue()"/></td>
		</tr>
		
		<tr>
			<td class="labelField" align="left">Issue Status</td>
			<td>&nbsp;&nbsp;&nbsp;<html:select property="issueStatus" styleClass="file_Upload" style="width:117px" tabindex="2">
			    <html:option value="">All</html:option>
				    <html:optionsCollection name="statusTypes" value="issueId" label="issueLabel" />
			    </html:select>
			</td>    
		</tr>
		
		<tr>
			<td class="labelField" align="left">Issue Severity</td>
			<td>&nbsp;&nbsp;&nbsp;<html:select property="issueSeverity" styleClass="file_Upload" tabindex="3" style="width:117px">
			    <html:option value="">All</html:option> 
			    	<html:optionsCollection name="IssueSeverities" value="id" label="name" />
			    </html:select>
			    
			</td>
		</tr>
		
		<tr>
			<td class="labelField" align="left">Issue Priority</td>
			<td>&nbsp;&nbsp;&nbsp;<html:select property="issuePriority" styleClass="file_Upload" tabindex="4" style="width:117px">
			    <html:option value="">All</html:option>
			    	<html:optionsCollection name="IssuePriorities" value="Priorityid" label="priorityname" />
			    </html:select>
			    
			</td>
		</tr>
	 	<tr>
			<td class="labelField" align="left" width="20%"><bean:message key="VIMSApplication.dateRangeFrom" />
		      <td  align="left">&nbsp;&nbsp;&nbsp;<html:text property="fromDate" styleClass="textbox_default"  style="width:80px" tabindex="5" readonly="true" onchange="javascript:clearSpanField();"></html:text>

			 <script>
		          document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].fromDate,window.document.forms[0].fromDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
		      </script>
              &nbsp;&nbsp;<font class="labelField"><bean:message key="VIMSApplication.dateRangeTo"/></font>
		        &nbsp;<html:text property="toDate" style="width:80px"  styleClass="textbox_default"  tabindex="6" readonly="true" onchange="javascript:clearSpanField();"></html:text>
 			  <script>
                document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].toDate,window.document.forms[0].toDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
	         </script> <span id="dateRange"></span></td>
		</tr>
		<tr>
			<td class="labelField" align="left">View By</td>
			<td class="labelField" align="left">&nbsp;&nbsp;<html:radio property="viewType" value="summary">Summary</html:radio>&nbsp;&nbsp;<html:radio property="viewType" value="detailed">Detailed</html:radio></td>
		</tr>	
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>
		<td>&nbsp;</td>
		<td align="left">&nbsp;&nbsp;&nbsp;<button type="button" class="linkbutton_background" accesskey="g" tabindex="7" onclick="submitForm();"><u>G</u>enerate</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="linkbutton_background" tabindex="8" accesskey="r" onclick="clearForm();"><u>R</u>eset</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="linkbutton_background" tabindex="8" accesskey="r" onclick="fnGoBack();"><u>C</u>ancel</button></td>
		</tr>
		<tr>
		<tr>
		<td colspan="2" align="left" class="labelField"><font color="red"><c:out value="${errorMsg}" /></font></td>
    	</tr>
	</html:form>
</table>
</body>
</html>