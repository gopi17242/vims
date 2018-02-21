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
	
	function fnGetApplByStatus(status)
		{
			x=GetXmlHttpObject();
		
			if (x==null)
			  {
				  alert ("Your browser does not support AJAX!");
				  return
			  } 			 
			  //alert(status)
			  
			var url="specificstatbar.do?reportType=getApplByStatus&strStatus="+status;
			//alert(url)
			
			x.onreadystatechange=fnSetApplByStatus;
			x.open("POST",url,false);
			x.send(null);
		}
	function fnSetApplByStatus() 
	{ 
		if (x.readyState==4)	
		{ 
				response  = x.responseXML.documentElement;  
				var ApplId=response.getElementsByTagName('result')[0].firstChild.data;	
				//alert(ApplId)
				
				var ApplName=response.getElementsByTagName('result')[1].firstChild.data;
				//alert(ApplName)
				removeOptions(document.getElementById("applList"));
				//document.getElementById("applList").options[0]=null;
				if(ApplName!="none" && ApplId!="none")
				{
				
					var ApplIdArray=ApplId.split(";");  
					var ApplNameArray=ApplName.split(";");
					for (var i=1; i<=ApplIdArray.length;i++)
					{
						addOption(document.getElementById("applList"), ApplNameArray[i-1], ApplIdArray[i-1]);
						//alert(document.getElementById("applList"))
					}	
				}
				else
				{
					alert("No Applications in this Status");
				}
	    } 
      	else 
      	{
            
       	}
	}
	function addOption(selectbox,text,value)
		{
			
			var optn = document.createElement("OPTION");
			optn.text = text;
			optn.value = value;
			selectbox.options.add(optn);
		}
		function removeOptions(selectBox)
	{	
		var len=selectBox.length;
		
		for(var j=len;j>0;j--)		
			{
			
				selectBox.options[j] =null;
				
			}	
		
	}
	function getIssueTypesList() 
	{	
		x=GetXmlHttpObject();		
			if (x==null)
			  {
				  alert ("Your browser does not support AJAX!");
				  return
			  } 
			 
			var url="getIssuesTypes.do?reportType=getIssueTypes&applType="+document.getElementById('applList').value;			
			
			x.onreadystatechange=setIssueTypesList;
			x.open("POST",url,false);
			x.send(null);
	}
	
	function setIssueTypesList()
	{
		if (x.readyState==4)	
		{ 	
				response  = x.responseXML.documentElement;  
				var IssuetypeID=response.getElementsByTagName('result')[0].firstChild.data;	
				
				var IssueTypeName=response.getElementsByTagName('result')[1].firstChild.data;
				
				
				removeOptions(document.getElementById("issueTypeList"));
				
				if(IssueTypeName!="none" && IssuetypeID!="none")
				{				
					var IssuetypeIDArray=IssuetypeID.split(";");  
					var IssueTypeNameArray=IssueTypeName.split(";");
					for (var i=1; i <= IssuetypeIDArray.length;i++)
					{
						addOption(document.getElementById("issueTypeList"), IssueTypeNameArray[i-1], IssuetypeIDArray[i-1]);
						
					}	
				}
				else
				{
					alert("No Issue Types available");
				}
	    } 
      	else 
      	{
            
       	}		
	}
	function generateImage()  
	 {
	 	 var formObj=document.forms[0];
	 	// alert(document.getElementById('issueTypeSelected').value)
		 formObj.action="./reportsBySpecificStatus.do?reportType=getSpecificIssuesRecords";
		 formObj.submit();
	 }
	 function generateReport(reportType) 
	 {		 
	 	document.getElementById('exportType').value=reportType;	 	
	 	document.getElementById('application').value=document.getElementById('applicationSelected').value;
	 	document.getElementById('issue').value=document.getElementById('issueTypeSelected').value
		 var formObj=document.forms[1];
		 formObj.action="./generateSpecificStatusReports.do?reportType=generateSpecificIssuesRecords&export="+document.getElementById('exportType').value;
		 formObj.submit();
	 }