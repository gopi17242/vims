<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<head>
<script language="javascript" type="text/javascript" src="./script-test/configuration.js"></script>
<script language="javascript" type="text/javascript" src="./script-test/VIMS_Alerts.js"></script>
<script>
function submitForm() {
         
           var priOptions=document.getElementById("selectedOptions");
	 	   for(var cnt=0;cnt<priOptions.length;cnt++) {
	 			priOptions.options[cnt].selected="selected";
	 		} 
           document.forms[0].action="./customize.do?param=Submit";
           document.forms[0].submit();
}

function goHome() {
    window.location="./configHomePage.do?menuId=Configuration&pageId=ConfigurationSettings";
}
</script>
</head>



<center>
<br>
<html:form action="/customize" onsubmit="selectAll()" focus="selectOptions">
<html:hidden property="configType" value="home" />
<table border="0" width="100%" align="left" cellspacing="2" cellpadding="0" >
	<tr>
			<td align="top" colspan="2"><font class="heading_blue" >Available Options</font></td><td><font class="heading_blue" >Existing Options</font></td></tr>
			<tr>
	    		<td width="150px"><html:select property="selectOptions" size="6" styleClass="multiplelist" multiple="true" >
                    <html:options name="customOptions" property="defaultOptions"/>
                  </html:select>
		    </td>
		 <td width="40px" align="center" valign="middle" >
                        <input type="button" style="width : 30px" name="btnRight2" value=">>" class="linkbutton_arrow"" onClick="return moveOriginIndstTypes1()">
		     <br>                
              <input type="button" style="width : 30px" name="btnLeft2" value="<<" class="linkbutton_arrow" onClick="return moveDestIndstTypes1()">
           </td>
        <td  >
               <html:select property="selectedOptions" multiple="true" size="6"  styleClass="multiplelist"  >
                  <html:options name="customOptions" property="existOptions"/>
               </html:select>
        </td>
        </tr>
        <tr>
             <td colspan="2"><h4><font color="green"><c:out value="${MSG}" /></font></h4></td>
       </tr>  
       <tr>    
     <td  colspan="3">
		<table width="100%" >
			<tr>	
			<td width="20%">&nbsp;</td>
				<td height="60px" colspan="2">&nbsp;<button type="button" accessKey="p" value="Preview" onClick="NewWin()" class="linkbutton_background"><u>P</u>review</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="linkbutton_background" onclick="submitForm()" accesskey="s"><u>S</u>ubmit</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" value="Back" accesskey="c" onClick="goHome();" class="linkbutton_background"><u>C</u>ancel</button>
				</td>
		    </tr> 
	    </table>   
	    </td>
	    </tr>    
  </table>
 </html:form>
 </center>
 <script>
    document.getElementById("selectedOptions").selectedIndex=-1;
 </script>