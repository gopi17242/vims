<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html:html>
<br>
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >
<tr>
<td>
<fieldset><legend class="heading_blue">Employee Details</legend>
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >			 
		  <tr>	   
		    <td  align="left" class="labelField" width="20%"><b><bean:message key="VIMSApplication.EmployeeID"/></b></TD>
			<td  align="left" class="labelField"width="80%"><bean:write name="viewEmployeeRecord"  property="strEmployeeID"/></td>	   		
    		</tr>
    		<tr>
    		    <td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.status"/></b></TD>
			    <td  align="left" class="labelField" ><bean:write name="viewEmployeeRecord" property="strStatus"/></td>
		        
		 </tr>
		
		 <tr>	   
		    <td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.firstName"/></b></TD>
			<td  align="left" class="labelField" ><bean:write name="viewEmployeeRecord" property="strFirstName"/></td>	    		
    		</tr>
    		
    	<tr>
    		<td  align="left" class="labelField" ><b>Middle Name</b></TD>
			<td  align="left" class="labelField" ><bean:write name="viewEmployeeRecord" property="strMiddleName"/></td>	    		
    			    
		 </tr>
		 
    		<tr>
    		<td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.lastName"/></b></TD>
			<td  align="left" class="labelField" ><bean:write name="viewEmployeeRecord" property="strLastName"/></td>	    		
    			    
		 </tr>
		 <tr>
		    <td align="left" class="labelField" ><b><bean:message key="VIMSApplication.position"/></b></TD>
			<td align="left" class="labelField" ><bean:write name="viewEmployeeRecord"  property="strEmployeePosition"/></td>	    		
		    </tr>
		    <tr>
		    <td align="left" class="labelField" ><b><bean:message key="VIMSApplication.workLocation"/></b></td>
   		    <td align="left" class="labelField"><bean:write name="viewEmployeeRecord" property="strWorkLocation"/></td>	    
		 </tr>
		 
		 <tr>	   
		    <td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.phone"/></b></TD>
			<td  align="left" class="labelField" ><bean:write name="viewEmployeeRecord" property="strWorkPhone"/></td>	    		
    		</tr>
    		<tr>
    		<td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.mobile"/></b></TD>
			<td  align="left" class="labelField" ><bean:write name="viewEmployeeRecord" property="strMobile"/></td>	    		
        </tr>
		 
		 <tr>	   
		    <td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.email"/></b></TD>
			<td  align="left" class="labelField" ><bean:write name="viewEmployeeRecord" property="strEmail"/></td>  		   
		   </tr>
		   <tr>
		    <td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.supervisorName"/></b></td>
   		    <td align="left" class="labelField"><bean:write name="viewEmployeeRecord" property="strSupervisorName"/></td>
		 </tr>
	 </table>
  </fieldset>	  
  <br>
  <fieldset><legend class="heading_blue">Address for Communication</legend>
 	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		 
		  <tr>
		    <td width="20%">&nbsp;</td><td width="80%">&nbsp;</td>
	      </tr>
		
		<tr>
		    <td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.address1"/></b></TD>
			<td  align="left" class="labelField" ><bean:write name="viewEmployeeRecord" property="strAddress1"/></td>	    		
	    </tr>
    
		<tr>
		    <td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.address2"/></b></TD>
			<td  align="left" class="labelField" ><bean:write name="viewEmployeeRecord" property="strAddress2"/></td>	    		
	    </tr>
	    
	     <tr>	   
		    <td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.city"/></b></TD>
			<td  align="left" class="labelField" ><bean:write name="viewEmployeeRecord" property="strCity"/></td>	    		
	   	 </tr>
	   		
		<tr>	   
		    <td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.country"/></b></td>
	  		<td align="left" class="labelField"><bean:write name="viewEmployeeRecord" property="strCountry"/></td>	    
		</tr>
		
		<tr>
		    <td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.state"/></b></td>
	  		<td align="left" class="labelField"><bean:write name="viewEmployeeRecord" property="strState"/></td>	    
		</tr>
		 
		
	   		<tr> 
	   		<td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.zip"/></b></TD>
			<td  align="left" class="labelField" ><bean:write name="viewEmployeeRecord" property="strZip"/></td>	    		
	   	
		 </tr>
		<tr>
			<td height="10px"></td>
		</tr>
	 </table>
 </fieldset>
 <br>	
		<tr>		
			<td colspan="4" align="center">	
			<table border="0" width="100%">
		<tr>
		<td width="20%">&nbsp;</td>			
			<td><button type="Button"  name="back" tabindex="1" class="linkbutton_background" onClick="javascript:history.back()" accesskey="C"><u>C</u>ancel</button>
		</td>
			
		</tr>
		</table>
		</td>
		</tr>
		</table>
		</html:html>