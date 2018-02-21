<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<html:html>
<br>
<table border="0" width="100%" align="left" cellspacing="0" cellpadding="0" >
<logic:iterate  id="view" name="Display">	
<tr>
<td>
<fieldset><legend class="heading_blue">Customer Details</legend>
<table border="0" width="100%" align="left" cellspacing="0" cellpadding="0" >
 <tr><td height="10px" colspan="5" align="left" class="labelField"><font color=red><c:out value="${MSG}"/></font></td></tr>
		 <tr>	   
		    <td  align="left" class="labelField" width="20%" ><b><bean:message key="VIMSApplication.CustomerName"/></b></td>
   		    <td align="left" class="labelField" width="80%" ><bean:write name="view" property="customerName"/></td>
   		 </tr>
   		 <tr>   	
   		    <td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.status"/></b></TD>
		    <td  align="left" class="labelField" ><bean:write name="view" property="status"/></td>	    		
		 </tr>
		 
		 <tr>	   
		    <td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.phone"/></b></TD>
			<td  align="left" class="labelField" ><bean:write name="view" property="phoneNumber"/></td>
		</tr>
		<tr>	    		
    		<td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.fax"/></b></td>
   		    <td align="left" class="labelField"><bean:write name="view" property="faxNo"/></td>	    	   
		 </tr>
		 
		 <tr>
		    <td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.email"/></b></TD>
			<td  align="left" class="labelField" ><bean:write name="view" property="emailID"/></td>  		   
		    
		 </tr>
	</table>
  </fieldset>	
	<br>
	  <fieldset><legend class="heading_blue">Contact Person Details</legend>
    
    <table border="0" width="100%" align="left" cellspacing="0" cellpadding="0" >
		
		 <tr>
		    <td  align="left" class="labelField" width="20%"><b>First Name</b></TD>
			<td  align="left" class="labelField" width="80%"><bean:write name="view" property="contactFirstName"/></td>	    		
	    </tr>
    
		<tr>
		    <td  align="left" class="labelField" ><b>Middle Name</b></TD>
			<td  align="left" class="labelField" ><bean:write name="view" property="contactMiddleName"/></td>	    		
	    </tr>
		 
		<tr>
		    <td  align="left" class="labelField" ><b>Last Name</TD>
			<td  align="left" class="labelField" ><bean:write name="view" property="contactLastName"/></td>
		</tr>		
		
		<tr>
		    <td align="left" class="labelField" ><b><bean:message key="VIMSApplication.email"/></b></td>
	  		<td align="left" class="labelField"><bean:write name="view" property="contactEmailID"/></td>
	  	</tr>
	  	
	  	<tr>
		    <td align="left" class="labelField" ><b>Mobile Number</b></td>
	  		<td align="left" class="labelField"><bean:write name="view" property="mobileNO"/></td>
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
		    <td  align="left" class="labelField" width="20%"><b><bean:message key="VIMSApplication.address1"/></b></TD>
			<td  align="left" class="labelField" width="20%"><bean:write name="view" property="address1"/></td>	    		
	    </tr>
    
		<tr>
		    <td  align="left" class="labelField" width="20%"><b><bean:message key="VIMSApplication.address2"/></b></TD>
			<td  align="left" class="labelField" width="20%"><bean:write name="view" property="address2"/></td>	    		
	    </tr>
		 
		<tr>
		    <td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.city"/></b></TD>
			<td  align="left" class="labelField" ><bean:write name="view" property="city"/></td>
		</tr>		
		
		<tr>
		    <td align="left" class="labelField" ><b><bean:message key="VIMSApplication.country"/></b></td>
	  		<td align="left" class="labelField"><bean:write name="view" property="country"/></td>
	  	</tr>		    
		    
		 <tr>  
		    <td align="left" class="labelField" ><b><bean:message key="VIMSApplication.state"/></b></td>
	  		<td align="left" class="labelField"><bean:write name="view" property="state"/></td>	    
		 </tr>
		
		<tr>		    		
	   		<td  align="left" class="labelField" ><b><bean:message key="VIMSApplication.zip"/></b></TD>
			<td  align="left" class="labelField" ><bean:write name="view" property="zipCode"/></td>	    		
		 </tr>
		<tr>
			<td height="10px"></td>
		</tr>
		</table>
</fieldset>
		<tr>		
			<td colspan="4" align="left">	
				<table width="100%">
				<tr>
				<td width="20%" height="10px"></td>
				<td>&nbsp;
				</td>
				</tr>
				<tr>
				<td width="20%"></td>
				<td>&nbsp;<button type="Button"  name="back" class="linkbutton_background" onClick="javascript:history.back()" accesskey="C"><u>C</u>ancel</button>
				</td>
				</tr>
				</table>
			</td>
		</tr>
	</logic:iterate>
</table>
</html:html>