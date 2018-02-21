<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<html:html>	
  <table border="0" width="100%" align="left" cellspacing="0" cellpadding="0" >
	
	 <tr><td>
							
	  <c:forEach begin="0" end="9" items="${List}" var="PrivacyPolicy" step="1">
	    
	     
        
	    <tr>
	        <td class="labelField"><c:out value="${PrivacyPolicy.PrivacyData}"/></td>
	    </tr>
	   <tr><td><br></td></tr>
	 
   </c:forEach>
    <br>
   </td></tr>
   </table>
</html:html>