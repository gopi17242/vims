/*
VIMS Home Page Scripts
*/	
function getHomeSLADetailsDisplay()
{
	var formObj=document.forms[1]
	formObj.action="./homeSLA.do?getSLA=getHomeSLADetailsList"
	formObj.submit();
}	