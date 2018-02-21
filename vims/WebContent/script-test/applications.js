/****************************Begin ExtremeComponents.js**************************************/
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
	if (action) 
	{
		document.forms[form].setAttribute('action', action);
	}
	if (method)  
	{
		document.forms[form].setAttribute('method', method);
	}
	document.forms[form].ec_eti.value='';
}

/****************************Begin ExtremeComponents.js**************************************/
/****************************Begin AddApplication.js****************************************/

var x
	
	function GetXmlHttpObject()
	{
	    var xmlHttp=null;
			try
			  {
				  // Firefox, Opera 8.0+, Safari
				  xmlHttp=new XMLHttpRequest();
			  }
			catch (e)
			  {
				  // Internet Explorer
			    try
			    {
			    	xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
			    }
			  	catch (e)
			    {
			    	xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
			    }
			  }
		  
		return xmlHttp; 
	}
	function getGrpMbrs()
	{
		x=GetXmlHttpObject();		
			if (x==null)
			  {
				  alert (ajaxAlert);
				  return
			  } 			
			var url="VIMSApplicationLookupDispatchAction.do?param=getGroupMembers&grpId="+document.getElementById("grpSel").value;
							
			x.onreadystatechange=stateChanged4;
			x.open("POST",url,false);
			x.send(null);
	}
	function stateChanged4() 
	{ 
		if (x.readyState==4)	
		{ 						
				response  = x.responseXML.documentElement;  
				var strEmpId=response.getElementsByTagName('result')[0].firstChild.data;			
				var strEmpName=response.getElementsByTagName('result')[1].firstChild.data;
				removeOptions(document.getElementById("specSel"));
				removeOptions(document.getElementById("selectDest"));				
				document.getElementById("specSel").options[0]=null;
				document.getElementById("selectDest").options[0]=null;
				if(strEmpName!="none" && strEmpId!="none") 
				{
					var strEmpIdArray=strEmpId.split(";");  
					var strEmpNameArray=strEmpName.split(";");
					for (var i=1; i <= strEmpIdArray.length;i++)
					{
						addOption(document.getElementById("specSel"), strEmpNameArray[i-1], strEmpIdArray[i-1]);
					}	
				}
				else
				{
					alert(noEmp);
				}
	    } 
      	else 
      	{
            
       	}
	}
	function fnGetStdSLA()
	{
		x=GetXmlHttpObject();
		
		if (x==null)
		  {
			  alert (ajaxAlert);
			  return
		  } 
		  
		var url="VIMSApplicationAction.do";		
		
		//url=url+"?q="+str;		
			
		x.onreadystatechange=stateChanged;
		x.open("POST",url,false);
		x.send(null);
	}
	function stateChanged() 
	{ 
		if (x.readyState==4)	
		{ 			
	      		response  = x.responseXML.documentElement;	
							
				document.getElementById("CriRes").setAttribute("value",response.getElementsByTagName('result')[0].firstChild.data);
	      		
	      		document.getElementById("CriWarn").setAttribute("value", response.getElementsByTagName('result')[1].firstChild.data);
	      		
	      		document.getElementById("MajRes").setAttribute("value", response.getElementsByTagName('result')[2].firstChild.data);
	      		
	      		document.getElementById("MajWarn").setAttribute("value", response.getElementsByTagName('result')[3].firstChild.data);
	      		
	      		document.getElementById("MinRes").setAttribute("value", response.getElementsByTagName('result')[4].firstChild.data);	
	      		
	      		document.getElementById("MinWarn").setAttribute("value", response.getElementsByTagName('result')[5].firstChild.data);
	    } 
	    else 
	    {
	           
	    }
	}
	function fnGetGroupMembers()
		{
			x=GetXmlHttpObject();
		
			if (x==null)
			  {
				  alert (ajaxAlert);
				  return
			  } 
			  
			var url="VIMSApplicationLookupDispatchAction.do?param=getGroupMembers&grpId="+document.getElementById("grpSel").value;
			
			
			//url=url+"?q="+str;
			
				
			x.onreadystatechange=stateChanged3;
			x.open("POST",url,false);
			x.send(null);
		}
	function stateChanged3() 
	{ 
		if (x.readyState==4)	
		{ 		
		
				
				response  = x.responseXML.documentElement;  
				var strEmpId=response.getElementsByTagName('result')[0].firstChild.data;			
				var strEmpName=response.getElementsByTagName('result')[1].firstChild.data;
				removeOptions(document.getElementById("specSel"));
				document.getElementById("specSel").options[0]=null;
				removeOptions(document.getElementById("selectDest"));
				document.getElementById("selectDest").options[0]=null;
				if(strEmpName!="none" && strEmpId!="none")
				{
					var strEmpIdArray=strEmpId.split(";");  
					var strEmpNameArray=strEmpName.split(";");
					for (var i=1; i <= strEmpIdArray.length;i++)
					{
						addOption(document.getElementById("specSel"), strEmpNameArray[i-1], strEmpIdArray[i-1]);
					}	
				}
				else
				{
					alert(noEmp);
				}
	    } 
      	else 
      	{
            
       	}
	}
	function fnGetApplicationDetails()
	{		
		var formObj=document.forms[0]
		formObj.action="VIMSApplicationAction.do?param=viewApplicationDetails"
		formObj.submit();
	}
	function fnChange()
	{		
	  //alert(document.getElementById("supCen").value);
	  
		if(document.getElementById("supCen").value=='')
		{
			document.getElementById("supMgrId").value='';
			document.getElementById("supMgrName").value='';
			removeOptions(document.getElementById("grpSel"));
		}
		else
		{
			x=GetXmlHttpObject();
		
			var url="getSupportManagerAction.do?param=getSupportManagerAction&supCen="+document.getElementById("supCen").value;		
			
			if (x==null)
			  {
				  alert (ajaxAlert);
				  return
			  } 
			x.onreadystatechange=stateChanged1;
			x.open("POST",url,false);
			x.send(null);
		}
	}	
	function stateChanged1() 
	{ 
		if (x.readyState==4)	
		{ 								
	      		response  = x.responseXML.documentElement;		      		
				document.getElementById("supMgrId").setAttribute("value",response.getElementsByTagName('result')[0].firstChild.data);	
				document.getElementById("supMgrName").setAttribute("value",response.getElementsByTagName('result')[1].firstChild.data);  
				var strGrpId=response.getElementsByTagName('result')[2].firstChild.data;			
				
				var strGrpName=response.getElementsByTagName('result')[3].firstChild.data;
				removeOptions(document.getElementById("grpSel")); 
				if(strGrpName!="none" && strGrpId!="none")
				{
					var strGrpIdArray=strGrpId.split(";"); 					
					var strGrpNameArray=strGrpName.split(";");					
					if(document.getElementById("grpSel").options[0].value != "")
					{
						//alert("in modify page");
						removeAll(document.getElementById("grpSel"));
						addOption(document.getElementById("grpSel"), 'Select Group Name', '');
					}	
					else
					{
						
					}
					for (var i=1; i <= strGrpIdArray.length;i++)
					{
						addOption(document.getElementById("grpSel"), strGrpNameArray[i-1], strGrpIdArray[i-1]);
					}	
				}
				else
				{
					alert(noGrps);
					removeAll(document.getElementById("grpSel"));
					addOption(document.getElementById("grpSel"), 'Select Group Name', '');
					//addOption(document.getElementById("grpSel").options[0], 'Select Group Name', '');
				}
	    } 
      	else 
      	{
            
       	}
	}
	function removeAll(selectBox)
	{
		
		var len=selectBox.length;
		//alert(len)
		for(var j=len;j>=0;j--)		
			{
				selectBox.options[j] =null;
			}	
	}
	function removeOptions(selectBox)
	{	
		var len=selectBox.length;
		
		for(var j=len;j>0;j--)		
			{
				selectBox.options[j] =null;
			}	
		
	}
	function showHint()
	{
		try{	
				
				var str=document.getElementById("appName").value;
				
				if (str.length==0)
				  { 
				  		
					  document.getElementById("showMsg").innerHTML="";
					  return
				  }
				  
				x=GetXmlHttpObject();
				
				
			
				if (x==null)
				  {
					  alert (ajaxAlert);
					  return
				  } 
				  
				var url="checkAppAvailability.do?param=checkAppName&appName="+str;
				
				
				
				
					
				x.onreadystatechange=stateChanged2;
				
				x.open("POST",url,false);
				
				x.send(null);
			}
			catch(e)
			{
			}
		
	} 
	function stateChanged2() 
	{ 
	  try{
			if (x.readyState==4)	
			{ 			
		      		response  = x.responseXML.documentElement;	
		      		
					
					result    = response.getElementsByTagName('result')[0].firstChild.data;		
		      		
		      		result = unescape(result);
		      		var p =	document.getElementById('showMsg');
		      		if(result==".")
		      		{
		      			p.innerHTML=" ";	
		      		}
		      		else
		      		{
		      			p.innerHTML=result;
		      			document.getElementById('appName').select();
		      		}							
		      } 
		      else 
		      {
		            alert("There was a problem retrieving the XML data:\n" + x.statusText);
		      }
	    }
		catch(e)
		{
		}
	}
	
function moveOriginIndstTypes1()
{
  var count=0;
  var i=0;
  if (document.getElementById("empId").options.length == 0)
   alert(noValues);
  else{
	while(i<document.getElementById("empId").options.length)
	{
		len = document.getElementById("selectDest").options.length;
  
		if(document.getElementById("empId").options[i].selected)
		{			
			document.getElementById("selectDest").options[len] = new Option(document.getElementById("empId").options[i].text,document.getElementById("empId").options[i].value);
			document.getElementById("empId").options[i] =null;
			count++;
			i=0;
		}
		else
			i++;
	}
	if(count==0)
	alert(selEmp);
     }
}
function moveDestIndstTypes1()
{
	var count=0;
	var i=0;
	if (document.getElementById("selectDest").options.length == 0)
		alert(selEmp);
  	else
  	{
		while(i<document.getElementById("selectDest").options.length)
		{
			len = document.getElementById("empId").options.length
			if(document.getElementById("selectDest").options[i].selected) 
			{
				document.getElementById("empId").options[len] = new Option(document.getElementById("selectDest").options[i].text,document.getElementById("selectDest").options[i].value);
				document.getElementById("selectDest").options[i] =null;				
				count++;
				i=0;				
			}
			else
			i++;		
		}
		if(count==0)
		alert(selEmp);
	}
}


var len;
function moveOriginIndstTypes2()
{
len=0;
len++
  if (document.getElementById("subCatId").value == "")
  {
   alert(entSubCat);
  }
  else
  { 		 			
		addOption(document.getElementById("selectDest1"),document.getElementById("subCatId").value,document.getElementById("subCatId").value);	
		//document.getElementById("selectDest1").options[len]= new Option(document.getElementById("subCatId").value,len);
		document.getElementById("subCatId").setAttribute("value","");
		len++;		
  }	
}
function addOption(selectbox,text,value)
		{
			
			var optn = document.createElement("OPTION");
			optn.text = text;
			optn.value = value;
			selectbox.options.add(optn);
		}

function moveDestIndstTypes2()
{
	
	var count=0;
	var i=0;
	if (document.getElementById("selectDest1").options.length == 0)
	{
		alert(selMod);
	}
  	else
  	{ 
  	
		while(i<document.getElementById("selectDest1").options.length)
		{
			
			//len = document.getElementById("subCatId").options.length
			if(document.getElementById("selectDest1").options[i].selected) 
			{
				
				document.getElementById("subCatId").setAttribute("value",document.getElementById("selectDest1").options[i].value);
				
				document.getElementById("selectDest1").options[i] =null;
				
				count++;
				i=0;				
			}
			else
			i++;		
		}
		if(count==0)
		alert(selMod);
	}
}
 
function fnAddApplication()
{
    var fdate=Date.parse(document.getElementById("supBegDate").value);
	 var formObj=document.forms[0];
     var selectDestLength=formObj.appSpecialists.length;
     var subCatLength=formObj.appSubCatName.length;
     var tdate=Date.parse(document.getElementById("supEndDate").value);  
    
		
	 var diff_date=fdate-tdate;
	
	 var count=0;
	 if(document.getElementById("custField").value=="")
	 {
	 	count++;
	 	document.getElementById("custName").innerHTML="Please Select a Customer";
	 }
	 else
	 {
	   document.getElementById("custName").innerHTML="";
	 }
	 if(document.getElementById("appName").value=="")
	 {
	 	count++;
	 	document.getElementById("showMsg").innerHTML="Application Name is required";
	 }
	 else
	 {
	   document.getElementById("showMsg").innerHTML="";
	 }
	 if(document.getElementById("ownerField").value=="")
	 {
	 	count++;
	 	document.getElementById("owner").innerHTML="Application Owner is required";
	 }
	 else
	 {
	   document.getElementById("owner").innerHTML="";
	 }
	   if(document.getElementById("email").value=="")
	 {
	 	count++;
	 	document.getElementById("msg").innerHTML="Owner's E-mail is required";
	 }
	 else
	 {
	   document.getElementById("msg").innerHTML="";
	 }
	 if(document.getElementById("SupportBeginDate").value=="")
	 {
	 	count++;
	 	document.getElementById("startDate").innerHTML="Start Date is required";
	 }
	 else
	 {
	   document.getElementById("startDate").innerHTML="";
	 }
	   if(document.getElementById("SupportEndDate").value=="")
	 {
	 	count++;
	 	document.getElementById("endDate").innerHTML="End Date is required";
	 }
	 else
	 {
	   document.getElementById("endDate").innerHTML="";
	 }
	  if(document.getElementById("supCen").value=="")
	 {
	 	count++;
	 	document.getElementById("supCenName").innerHTML="Please Select a Support Center";
	 }
	 else
	 {
	   document.getElementById("supCenName").innerHTML="";
	 }
	  if(document.getElementById("supMgrId").value=="")
	 {
	 	count++;
	 	document.getElementById("supMgr").innerHTML="Support Manager is required";
	 }
	 else
	 {
	   document.getElementById("supMgr").innerHTML="";
	 }
	 
	  if(document.getElementById("grpSel").value=="")
	 {
	 	count++;
	 	document.getElementById("grpName").innerHTML="Please Select a Group";
	 }
	 else
	 {
	   document.getElementById("grpName").innerHTML="";
	 }
	 if(document.getElementById("CriRes").value=="" || document.getElementById("MajRes").value=="" || document.getElementById("MinRes").value=="")
	 {
	 	count++;
	 	document.getElementById("ResTime").innerHTML="Response Time is required";
	 }
	 else
	 {
	   document.getElementById("ResTime").innerHTML="";
	 }	 
	 if(document.getElementById("CriWarn").value=="" || document.getElementById("MajWarn").value=="" || document.getElementById("MinWarn").value=="")
	 {
	 	count++;
	 	document.getElementById("warBefore").innerHTML="Warning Before is required";
	 }
	 else
	 {
	   document.getElementById("warBefore").innerHTML="";
	 }	 	 
	// alert(count);
	if(diff_date>0)
	{	
		alert("End Date should be greater than Start Date");
		document.getElementById("supEndDate").select();
		count++;		
	}
	 var cur_dat=new Date();
	 var curdate=cur_dat.getDate(); 
	 var curMonth=cur_dat.getMonth();
	 curMonth=curMonth+1;
	 var curYear=cur_dat.getYear();	
	 var today=Date.parse(curMonth+"/"+curdate+"/"+curYear);
	 if(tdate<today && document.getElementById("status").value=="Active")
	 {
		alert("End Date should be greater than Current Date");
		document.getElementById("supEndDate").select();
		count++;
	 }
	 if(count == 0)
	{   
	   for(var i=0;i<subCatLength;i++)
	   {
	    document.forms[0].appSubCatName.options[i].selected=true;
	   }	
	   for(var i=0;i<selectDestLength;i++)
	   {
	     document.forms[0].appSpecialists.options[i].selected=true;
	   }	 
		formObj.action="./addApplication.do?param=Add";
	   	formObj.submit();	
	}
}
function deleteFile(cnt,filePath) 
{
	//alert("====file Path==== "+filePath);
	if(confirm(delAtt))
	{
		var hiddenField=document.getElementById("filesUploaded");
		var prevFiles=hiddenField.value;
		var delFile=filePath;
		//alert(delFile);
		delFile=delFile.substring(delFile.lastIndexOf("/")+1);
		//alert("====delFile====="+delFile);		
		//alert(prevFiles);
		//alert("===method result==="+prevFiles.replace(delFile,''));
		prevFiles=prevFiles.replace(delFile,'');
		//alert(prevFiles);
		hiddenField.value=prevFiles;	
				
		var cntName="div"+cnt;
		   
   		document.getElementById(cntName).innerHTML="";
   		document.getElementById(cntName).style.display="none";
		  
			try{	
				var str=document.getElementById("appName").value;
				
				if (str.length==0)
				  { 				  		
					  document.getElementById("showMsg").innerHTML="";
					  return
				  }
				x=GetXmlHttpObject();
				if (x==null)
				  {
					  alert (ajaxAlert);
					  return
				  } 				  
				var url="VIMSApplicationLookupDispatchAction.do?param=deleteFile&filePath="+filePath;						
					
				x.onreadystatechange=stateChanged5;
				
				x.open("POST",url,false);
				
				x.send(null);
			}
			catch(e)
			{
			}
		
	}
		
} 
	function stateChanged5() 
	{ 
	  try
	  	{
			if (x.readyState==4)	
			{ 			
		      		response  = x.responseXML.documentElement;	
		      		result    = response.getElementsByTagName('result')[0].firstChild.data;	
		      		//document.getElementById("filesCount").setAttribute("value",response.getElementsByTagName('result')[0].firstChild.data);
		      		var p =	document.getElementById('filesCount');
		      		if(result>0)
		      		{
		      			document.getElementById("my_file_element").disabled=false;
		      		}	
		      		p.innerHTML=result;	     				
		    } 
		    else 
		    {
		           alert("There was a problem retrieving the XML data:\n" + x.statusText);
		    }
	    }
		catch(e)
		{
		}
	}

function fnModifyApplication()
{
	var formObj=document.forms[0];
    var selectDestLength=formObj.appSpecialists.length;
    var subCatLength=formObj.appSubCatName.length;
    var fdate=Date.parse(document.getElementById("SupportBeginDate").value);
	var formObj=document.forms[0];
    var selectDestLength=formObj.appSpecialists.length;
    var subCatLength=formObj.appSubCatName.length;
   var tdate=Date.parse(document.getElementById("SupportEndDate").value);

	 var diff_date=fdate-tdate
	 
	 var cur_dat=new Date();
	 var curdate=cur_dat.getDate(); 
	 var curMonth=cur_dat.getMonth();
	 curMonth=curMonth+1;
	 var curYear=cur_dat.getYear();	
	 var today=Date.parse(curMonth+"/"+curdate+"/"+curYear);
	 

	if(diff_date>0)
	{		
		alert(dtValid);
		document.getElementById("SupportEndDate").select();
	}
	else if(tdate<today && document.getElementById("status").value=="Active")
	 {
		alert("End Date should be greater than Current Date");
		document.getElementById("SupportEndDate").select();
	 }
	else
	{   
	   for(var i=0;i<subCatLength;i++)
	   {
	    document.forms[0].appSubCatName.options[i].selected=true;
	   }   
	   for(var i=0;i<selectDestLength;i++)
	   {
	    document.forms[0].appSpecialists.options[i].selected=true;
	   }
	   if(confirm(confirmModify))
	   {
	   	formObj.action="./modifyApplication.do?param=Modify";
	   	formObj.submit();
	   }
	 }
}

function viewApplicationList()
{
   //alert("====viewApplicationList=========");
   var formobj=document.forms[0];
   formobj.action="./viewApplicationList.do?param=viewApplicationList&menuId=Application&pageId=Applications";
   formobj.submit();
  
}
function viewApplicationSpecialists()
{
	//alert("====viewApplicationSpecialists=========");	
	var formobj=document.forms[0];
	formobj.action="./ApplicationSpecialistsLink.do?param=applicationSpecialist&pageId=ApplicationSpecialist";
	formobj.submit();
}
function viewApplicationCustomers()
{
	//alert("====viewApplicationCustomers=========");	
	var formobj=document.forms[0];
   	formobj.action="./ApplicationCustomersLink.do?param=applicationCustomers&pageId=ApplicationCustomers";
   	formobj.submit();
}
function viewApplicationGroups()
{
	//alert("====viewApplicationGroups=========");
	var formobj=document.forms[0];
    formobj.action="./ApplicationGroupsLink.do?param=applicationGroups&pageId=ApplicationGroups";
    formobj.submit();	
}
function viewApplicationModules()
{
	//alert("====viewApplicationModules=========");	
	var formobj=document.forms[0];
    formobj.action="./ApplicationModulesLink.do?param=applicationModules&pageId=ApplicationModules";
    formobj.submit();
}
/****************************End AddApplication.js****************************************/
/****************************Begin ApplicationSpecialist.js****************************************/

/****************************End ApplicationSpecialist.js****************************************/
/****************************Begin ApplicationCustomers.js****************************************/

function goBack()
{
	//alert(document.forms[0].action="./viewApplicationList.do?param=viewApplicationList")
	document.forms[0].submit();
}
function checkStatus()
{
	var element=document.getElementById("status");
	if(element.value=='Inactive')
	{
		var retVal=confirm(confirmStatus);
		if(retVal==false)
		{
			element.value='Active';
		}
	}
}
function fnGetAppOwners()
    {
    	var custId=document.getElementById("custField").value;
    	if(custId!="")
    	{
    		x=GetXmlHttpObject();
		
			if (x==null)
			  {
				  alert (ajaxAlert);
				  return
			  } 
			  
			var url="VIMSApplicationLookupDispatchAction.do?param=getAppOwnerAction&custId="+custId;
						
			//url=url+"?q="+str;
							
			x.onreadystatechange=getOwners;
			x.open("POST",url,false);
			x.send(null);
    	}
    	else
    	{
    		
    	}
    }
     
	function getOwners() 
	{ 
		if (x.readyState==4)	
		{ 						
				response  = x.responseXML.documentElement;  
				var strOwnerId=response.getElementsByTagName('result')[0].firstChild.data;			
				var strOwnerName=response.getElementsByTagName('result')[1].firstChild.data;
				removeOptions(document.getElementById("appOwners"));
				if(strOwnerName!="none" && strOwnerId!="none")
				{
					var strOwnerIdArray=strOwnerId.split(";");  
					var strOwnerNameArray=strOwnerName.split(";");
					for (var i=1; i <= strOwnerIdArray.length;i++)
					{
						addOption(document.getElementById("appOwners"), strOwnerNameArray[i-1], strOwnerIdArray[i-1]);
					}	
				}
				else
				{
					
				}
	    } 
      	else 
      	{
            
       	}
	}
	function fnSelectOwner()
    {
    	var selectedValue=document.getElementById("appOwners");
    	var ownerName=document.getElementById("ownerField");
    	var ownerMail=document.getElementById("email");
    	if(selectedValue.value!="")
    	{
    		ownerName.value=selectedValue.options[selectedValue.selectedIndex].text;
    		ownerMail.value=selectedValue.value;
    	}
    	else
    	{
    		ownerName.value="";
    		ownerMail.value="";    		
    	}    	
    }
    function fnLookup()
    {
    	var custId=document.getElementById("custField").value; 
    	if(custId!="")
    	{
    		document.getElementById("appOwners1").style.display='block';
    	}
    	else
    	{
    		alert("Please select a customer");
    	}
    }
/****************************End ApplicationCustomers.js****************************************/
/****************************Begin ApplicationGroups.js****************************************/
/****************************End ApplicationGroups.js****************************************/
/****************************Begin ApplicationModules.js****************************************/
/****************************End ApplicationModules.js****************************************/
 