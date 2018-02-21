<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<script language="javascript" src="./script-test/advPopCalender.js" type="text/javascript"></script>
<script language="javascript" src="./script-test/reports.js" type="text/javascript"></script>
<script language="javascript" src="./script-test/VIMS_Alerts.js" type="text/javascript"></script>


<script>
function submitForm() 
{
	var formObj=document.forms[0];
	formObj.action="VIMSReportsLookUpDispatchAction.do?param=generateCustomerReports"
   	formObj.submit();	 	
}
 function fnGoBack()
  {
    window.location="./reportsHome.do?pageId=Reports&menuId=Report";
  }
</script>
</head>	
<body><br>
<table border="0" width="100%"> 
		
				<html:form action="VIMSReportsLookUpDispatchAction.do" focus="status">
				
		<tr>
			<td class="labelField" align="left">Status</td>
			<td>&nbsp;&nbsp;<html:select property="status" styleClass="file_Upload" onchange="fnGetCustomers(this.value)">
			    	<html:option value="All">All</html:option>
			    	<html:option value="Active">Active</html:option>
				    <html:option value="Inactive">Inactive</html:option>
			    </html:select>
			</td>    
		</tr>		
		<tr>
			<td class="labelField" align="left">Customer Name</td>
			<td>&nbsp;&nbsp;<html:select property="custName"  styleId="customerSel" styleClass="textbox_default" style="width:250px">
				<html:option value="">All</html:option>	
				<c:if test="${customerList ne null}">
		    		<html:optionsCollection name="customerList" value="custId" label="custName"/>
		    		</c:if>		
			</html:select></td>
		</tr>
		<tr>
			<td class="labelField" align="left" width="20%">Date Range From
		      <td  align="left">&nbsp;&nbsp;<html:text property="fromDate" styleClass="textbox_default" style="width:80px"></html:text>
			 <script>
		          document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].fromDate,window.document.forms[0].fromDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
		      </script>
              <font class="labelField">&nbsp;&nbsp;To</font>
		        <html:text property="toDate"  styleClass="textbox_default" style="width:80px"></html:text>
 			  <script>
                document.write('<a href="#" onclick="advPopUpCalendar(window.document.forms[0].toDate,window.document.forms[0].toDate, \'dd-mm-yyyy\');"><img src="./images/calendar.gif" border="0" align="absmiddle"></a>');
	         </script></td>
		</tr>	
		<tr>
		<td class="labelField" align="left">View By</td>
		<td class="labelField" align="left">&nbsp;&nbsp;<html:radio property="viewType" value="summary">Summary</html:radio>&nbsp;&nbsp;<html:radio property="viewType" value="detailed">Detailed</html:radio></td>
		</tr>
		<tr height="10px"><td colspan="2"></td></tr>
		<tr>
		<td>&nbsp;</td>
		<td align="left">&nbsp;&nbsp;<button type="button" class="linkbutton_background" accesskey="g" tabindex="8" onclick="submitForm();"><u>G</u>enerate</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="reset" class="linkbutton_background" accesskey="r"><u>R</u>eset</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="linkbutton_background" tabindex="8" accesskey="r" onclick="fnGoBack();"><u>C</u>ancel</button></td>
		</tr>
		
			<tr>
			<td colspan="2" align="left" class="labelField"><c:out value="${resultMsg}"/>
			</td>    
		</tr>	
			
				</html:form>
			</table>
			</body>
			</html>