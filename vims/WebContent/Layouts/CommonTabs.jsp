
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<HTML>
<head>
<script src="./script-test/common.js"></script>
<LINK href="./css/StyleSheet.css" rel="STYLESHEET" type="text/css">
</head>
<%	//System.out.println(request.getParameter("menuId")); 	
		if(request.getParameter("menuId")!=null)
		{
			session.setAttribute("MenuId",request.getParameter("menuId"));
			if(request.getParameter("pageId")!=null)
			{
				session.setAttribute("PageId",request.getParameter("pageId"));
			}
		}
	%>
<body onload='SwitchMenu("<c:out value="${MenuId}"/>")'>
<table width="166px" border="0" cellpadding="0" cellspacing="0">
<c:if test="${Role ne 'null'}">
	  <c:if test="${status ne '0'}">
	<c:set var="strRole" value="${Role}"  scope="page"/>
	
	    <tr>
	
        <td width="12" align="left" valign="top"><img src="images/topleft.gif" width="12"/></td>
        <td width="165" valign="middle" style="background-image:url(images/topCenter.gif);" ><a href="./home.do?method=getHomePageApplicationsList&menuId=sub1&pageId=home" class="homelinkbtn">Home</a></td>
        <td width="12" align="right" valign="top"><img src="images/topRight.gif" width="12"/></td>
      
       </tr>
     </c:if> 
      <tr>
      <td colspan="3" height="2"></td>
      </tr>
     <tr>
        <td colspan="3" >
      
        <table width="166px" border="0" cellspacing="0" cellpadding="0">
            <tr>
           
              <td width="12">&nbsp;</td>
              <td><!-- Keep all menus within masterdiv-->
            
                  <div id="masterdiv">
           
			  <logic:present name="testList">
							<c:forEach items="${testList}" var="record">
							  <c:if test="${record.tabName ne 'Home'}" >
							
								<div><a href='<c:out value="${record.path}" />' class="menutitle" onclick="SwitchMenu('<c:out value="${record.keyName}" />')"><c:out value="${record.tabName}" /></a>
							  <c:if test="${(record.subMenu ne null)}">
								<span id='<c:out value="${record.keyName}" />' class="submenu">
									<c:forEach items="${record.subMenu}" var="dispName">
										<div><a href='<c:out value="${dispName.path}" />' class="navLink"><c:out value="${dispName.name}" /></a></div>
									</c:forEach>
								</span>
							  </c:if>
				  				</div>
				  			 </c:if>	
							</c:forEach>
					 </logic:present>
			</div>
			
			</td>
              <td width="12">&nbsp;</td>
            </tr>           
          </table>
            </td>
      </tr>
    
      
   </c:if>
      
    </table>
    </body>
    </HTML>