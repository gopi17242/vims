<%@page import="java.util.StringTokenizer" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/datetime.tld" prefix="date" %>
<%@ taglib uri="/WEB-INF/tlds/VIMSBreadCrumbs.tld" prefix="crumb"%>
<html>
<head>
<script src="../script-test/common.js"></script>
<LINK href="../css/StyleSheet.css" rel="STYLESHEET" type="text/css">
<title>Home</title>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>	  
	    <td colspan="2" align="left" valign="top">
<table width="100%" border="0" cellspacing="0"  cellpadding="0">
 <tr>
	<td colspan="2">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="19%"><img src="../images/vertexLogo.png" width="176" height="63" /></td>
			<td width="63%" align="center"><img src="../images/headerText.gif" width="363" height="30" /></td>
			<td width="18%" align="right" valign="top">
				<table width="65%" border="0" cellspacing="2" cellpadding="0">
					  <tr>
						<td width="37%"><a href="#"><img src="../images/home.gif" width="66" height="21" border="0"/></a></td>
						<td width="26%" align="right"><a href="#"><img src="../images/help.gif" width="49" height="16"  border="0" /></a></td>
						<td width="37%"><a href="#"><img src="../images/logout.gif" width="75" height="21"  border="0" /></a></td>
					  </tr>
				</table>
			</td>
		</tr>	
		<tr>
			<td colspan="3" style= "height:1px"></td>
		</tr>
		<tr>
			<td align="right" colspan="3" bgcolor="#64a6d8" style="height:22px" class="heading_white"><date:format pattern="MMMM d, yyyy"><date:currentTime/></date:format>&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr align="right" >
			<td align="left" height="25" colspan="1" bgcolor="eeeeee" style="height:15px"></td>	
			<td align="left" height="25" colspan="1" bgcolor="eeeeee" style="height:15px" ></td>	
			<td height="25" colspan="1" bgcolor="eeeeee" style="height:15px"><font class="heading_blue">Welcome&nbsp;&nbsp;<c:out value="${user}" /></font>&nbsp;&nbsp;&nbsp;</td>
		</tr>				
		</table>
	</td>
</tr>
<tr>   
    <td width="166" align="left" valign="top"> 
	<table width="166px" border="0" cellpadding="0" cellspacing="0" bgcolor="f8f8f8">
      <tr>
        <td width="12" align="left" valign="top"><img src="../images/topleft.gif" width="12"/></td>
        <td width="165" valign="middle" style="background-image:url(../images/topCenter.gif);" ><a href="#" class="homelinkbtn">Home</a></td>
        <td width="12" align="right" valign="top"><img src="../images/topRight.gif" width="12"/></td>
      </tr>
      <tr>
     	<td colspan="3" height="5"></td>
      </tr>
      <tr>
        <td colspan="3" background="../images/centerpart.gif">
        <table width="166px" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="12">&nbsp;</td>
              <td>            
                  <div id="masterdiv">                    
	                     <div><a href="#" class="menutitle">Support Center</a></div>						
					 	 <div><a href="#" class="menutitle">Applications</a></div>														
				   		 <div><a href="#" class="menutitle" >SLA</a></div>				
				 		 <div><a href="#" class="menutitle">Customers</a></div>						
						 <div><a href="#" class="menutitle">Employees</a></div>								
						 <div><a href="#" class="menutitle">Issues</a></div>								
						 <div><a href="#" class="menutitle">Reports</div>										
						 <div><a href="#" class="menutitle">Search</a></div>
						 <div><a href="#" class="menutitle">Configuration Settings </a></div>
				 </div>
			  </td>
              <td width="12">&nbsp;</td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
          </table>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
            <p>&nbsp;</p>
         </td>
      </tr>    
      <tr>
        <td height="11" valign="top"><img src="../images/bottomLeft.gif" width="12" height="11" /></td>
        <td height="11" background="../images/bottomMiddle.gif"></td>
        <td align="right" valign="top"><img src="../images/bottomRight.gif" width="12" height="11" /></td>
      </tr>      
    </table>
    </td>            
    <td width="90%" align="left" valign="top">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="left">
	      <tr>
		        <td width="1%" valign="top"><img src="../images/centerpaneltopleft.gif" /></td>
		        <td width="98%" style="background-image:url(../images/centerpaneltopmiddle.gif)">
			        <table width="100%" border="0" cellspacing="1" cellpadding="0">
			            <tr>
			           		 <td height="5"></td>
			            </tr>
			            <tr>
							<td width="15%" class="heading_white">Home</td>
						</tr>
			        </table>
		       </td>
		       <td width="1%" align="left" valign="top"><img src="../images/centerpaneltopright.gif"/></td>
	      </tr>
	      <tr>
		        <td height="186" valign="top" style="background-image:url(../images/left.gif)"></td>
		        <td align="left" valign="top" style="background-image:url(../images/centerpanelcenterCourt.gif)">
			        <table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
			          <tr>
			           <td>				          
						<TABLE border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
						<TR>
						<TD class="fieldhead" ALIGN="Left" VALIGN="TOP" width="80%">
							<table border="0" cellspacing="0" cellpadding="0">					
								<tr>
									<td valign="TOP">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr><td >&nbsp;</td></tr>
										<tr>						
											<td width="100%">
							 
						<table width="100%" border="0" cellspacing="0" cellpadding="0" align="RIGHT" valign="TOP" bgcolor="f8f8f8" bordercolor="white" >
		                <tr>
				             <td width="100%" colspan="2" align="left" height="3px"></td>
				        </tr>
				        <tr height="10" width="100%">
						           <td>			
						           <TR bgcolor="eeeeee">
						           <td class="heading_blue" width="100%">
						          
				        <c:forTokens items="${param.selOptions}" delims=","  var="record">
					                  <c:if test="${record eq 'Applications'}" >
										<p>	[ 3 ]... Applications Added</p> 
							            </c:if>
							            <c:if test="${record eq 'Issues'}" >
										  <p>[ 3 ]... Issues Added</p>
									    </c:if>
							               <c:if test="${record eq 'SLA'}" >
									   		<p>SLA Details Modified for [5] Application</p> 
								     	     	</c:if>
											<c:if test="${record eq 'Customers'}" >
												<p>[ 2 ]... Customers Added</p> 
											</c:if>
								</c:forTokens></td>
								<td colspan="1">&nbsp;&nbsp;</TD>					 
				                	 </TR>
                        </table><BR><BR><BR><BR><BR><BR><BR>
												<p class="bodytext">Thank you for using Vertex Issue Management System, the ultimate Tracking and Resolution System for the Global Support Services.</p>
												<span class="heading_blue"><b>Support Center</b><br></span>
												<p class="bodytext">Information related to Support Center is displayed. Creation of new Support Center or Groups is done here. </p>
											</td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
						</TD>
				
						<td width="20%" valign="TOP">
						<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" align="RIGHT" valign="TOP" bgcolor="f8f8f8" bordercolor="white" >
							
							<tr>
							<td width="100%" colspan="3" align="left" height="5px"></td>
							</tr>
							<TR>
								<td width="100%" colspan="3" align="left" bgcolor="#64a6d8" class="fieldhead"><font color="white" class="heading_white">Issue Search</font> </TD>
							</TR>
							<TR>
								<td class="labelField" width="33%">Issue ID</td>
								<td colspan="2"><input type="text" name="issueId" value="" class="textbox_default">&nbsp;&nbsp;<input type="Image" name="Go" src="../images/go.gif" border="0" vspace="0" height="20px" hspace="0"></TD>																						
							</TR>
							<tr>
								<td colspan="3" class="labelField" width="100%"><FONT color="red"></FONT></td>
							</tr>
							<tr>
								<td height="10px"></td>
							</tr>							
							<TR>
								<td width="100%" colspan="3" align="left" bgcolor="#64a6d8" class="fieldhead"><font color="white" class="heading_white">SLA Details</font> </TD>
							</TR>						
							<TR>
								<TD width="100%" COLSPAN="3" >								
				 			 		<select name="applicationId" size="1" style="WIDTH: 280px" class="dropdownlist"><option value="select an application" selected="selected">select an application</option>
			  						</select> 							
								</TD>
							</TR>											
						</TABLE>	
						</TD>			
					</TR>
				</TABLE>
			    </td></tr>
			  </table>          
		      </td>
		      <td valign="top" style="background-image:url(../images/right.gif)"></td>
	        </tr>
	        <tr>
		        <td valign="top"><img src="../images/centerpanelbottomleft.gif" /></td>
		        <td height="31" valign="top" style="background-image:url(../images/centerpanelbottommiddle.gif)">&nbsp;</td>
		        <td align="left" valign="top"><img src="../images/centerpanelbottomright.gif" /></td>
	      	</tr>
    	</table>
   		</td>   <!-- Center Panel closed here -->     
    	</tr>
 		 <tr>
    		<td colspan="2" width="100%"></td>
  		</tr>
	</table>
	<center>
	        <input type="button" onclick="window.close()" value="    Ok    " class="linkbutton_background">
	 </center>       
	</body>
</html>