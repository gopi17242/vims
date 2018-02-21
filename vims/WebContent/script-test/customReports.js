function moveFromLeftToRight()
{
  var count=0;
  var i=0;
  if (document.getElementById("orginalList").options.length == 0)
   alert("No items in Select Criteria");
  else{
	while(i<document.getElementById("orginalList").options.length)
	{
		len = document.getElementById("selectedList").options.length;
  
		if(document.getElementById("orginalList").options[i].selected)
		{			
			document.getElementById("selectedList").options[len] = new Option(document.getElementById("orginalList").options[i].text,document.getElementById("orginalList").options[i].value);
			document.getElementById("orginalList").options[i] =null;
			count++;
			i=0;
		}
		else
			i++;
	}
	if(count==0)
	alert("Please Select an item");
     }
}
function moveFromRightToLeft()
{
	var count=0;
	var i=0;
	if (document.getElementById("selectedList").options.length == 0)
		alert("No items in Selected Criteria");
  	else
  	{
		while(i<document.getElementById("selectedList").options.length)
		{
			len = document.getElementById("orginalList").options.length
			if(document.getElementById("selectedList").options[i].selected) 
			{
				document.getElementById("orginalList").options[len] = new Option(document.getElementById("selectedList").options[i].text,document.getElementById("selectedList").options[i].value);
				document.getElementById("selectedList").options[i] =null;
				
				count++;
				i=0;				
			}
			else
			i++;		
		}
		if(count==0)
		alert("Please Select an item");
	}
}
function displaySelected()
{	
//alert('displayed selected')
	var selList=document.getElementById('selectedList');
	//alert(document.getElementById('ectable'));
	if(selList.length==0)
	{
	//document.getElementById('error').write('Selected Criteria is required')
		alert('No items in Selected Criteria')
	}
	else
	{
			setInvisible();
			//document.getElementById('reportList').style.display="none";
			
		for(j=0;j<document.getElementById("columList").length;j++)		
		{
			//alert('in column list deleting')
			document.getElementById("columList").options[j] =null;
		}		
			
		for(j=0;j<selList.length;j++)
		{
			document.getElementById(selList.options[j].value).style.display="block";			
			document.getElementById("columList").options[j] = new Option(document.getElementById("selectedList").options[j].text,document.getElementById("selectedList").options[j].value);
			
		}
		document.getElementById('generate').style.display="block";
	
	//document.getElementById('displayMsg').style.display="none"
	}
	
}
function setInvisible()
{	
//alert('setInvisible')
	var orgList=document.getElementById('orginalList');
	for(j=0;j<orgList.length;j++)
	{
		document.getElementById(orgList.options[j].value).style.display="none";
	}	
	
	//document.getElementById('generate').style.display=none;
}
function getCustomreports()
{
	for(k=0;k<document.getElementById('columList').length;k++)
	{
		document.getElementById('columList').options[k].selected="true";
		//alert(document.getElementById('columList').options[k].selected);
	}
	//alert('in get custom reports')
	//alert(document.getElementById('ectable'));
	//document.getElementById('ectable').style.display="block";
	//document.getElementById('displayMsg').style.display="block"
	var formobj=document.forms[0];	   
    formobj.action="./customReports.do?reportType=generateCustomReports";
    formobj.submit();
}
function backToReports()
{
	//alert('in backToReports')
	
	var formobj=document.forms[0];	   
    formobj.action="./customReports.do?reportType=getCustomReports";
    formobj.submit();
}
function getParameterMap(form) {
    var p = document.forms[form].elements;
    var map = new Object();
    for(var x=0; x < p.length; x++) {
        var key = p[x].name;
        var val = p[x].value;
        
        //Check if this field name is unique.
        //If the field name is repeated more than once
        //add it to the current array.
        var curVal = map[key]; 
        if (curVal) { // more than one field so append value to array
        	curVal[curVal.length] = val;
        } else { // add field and value
        	map[key]= [val];
        }
    }
    return map;
}

function setFormAction(form, action, method) {
	if (action) {
		document.forms[form].setAttribute('action', action);
	}
	
	if (method) {
		document.forms[form].setAttribute('method', method);
	}
	
	document.forms[form].ec_eti.value='';
}
function generateReport(reportType) 
	 {	
	 //alert(reportType); 	
	 	 //document.getElementById('exportType').value=reportType;
		 var formObj=document.forms[0];
		 //formObj.action="./openIssuesExport.do?exportType=generateHomeOpenissues&exportTo="+reportType+"&application="+applId;
		 formObj.action="./customReports.do?reportType=exportCustomReports&exportTo="+reportType;
		 formObj.submit();
	 }
	 
	 
	 