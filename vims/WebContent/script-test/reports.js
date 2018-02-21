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
	function fnGetEmployees(role)
	{
		if(role!="")
		{
			x=GetXmlHttpObject();		
			if (x==null)
			  {
				  alert (ajaxAlert);
				  return
			  } 			
			var url="VIMSReportsLookUpDispatchAction.do?param=getEmployees&role="+role;							
			x.onreadystatechange=stateChanged;
			x.open("POST",url,false);
			x.send(null);
		}		
	}
	function stateChanged() 
	{ 
		if (x.readyState==4)	
		{ 						
				response  = x.responseXML.documentElement;  
				var strEmpId=response.getElementsByTagName('result')[0].firstChild.data;			
				var strEmpName=response.getElementsByTagName('result')[1].firstChild.data;
				removeOptions(document.getElementById("empSel"));
				//document.getElementById("specSel").options[0]=null;
				if(strEmpName!="none" && strEmpId!="none") 
				{
					var strEmpIdArray=strEmpId.split(";");  
					var strEmpNameArray=strEmpName.split(";");
					for (var i=1; i <= strEmpIdArray.length;i++)
					{
						addOption(document.getElementById("empSel"), strEmpNameArray[i-1], strEmpIdArray[i-1]);
					}	
				}
				else
				{
					//alert("No employees in this position.");
				}
	    } 
      	else 
      	{
            
       	}
	}
	function fnGetCustomers(stts)
	{
		removeOptions(document.getElementById("customerSel"));
		if(stts!="")
		{
			x=GetXmlHttpObject();		
			if (x==null)
			  {
				  alert (ajaxAlert);
				  return
			  } 			
			var url="VIMSReportsLookUpDispatchAction.do?param=getCustomers&status="+stts;							
			x.onreadystatechange=function() { 
            if (x.readyState == 4) {
	            if (x.status == 200) {
			        stateChanged1(x.responseText);
                } else if (x.status == 204){
                    alert('cant do anything');
                }
            }
        };	
			x.open("POST",url,false);
			x.send(null);
		}		
	}
	function stateChanged1(responseText) 
	{ 	
		if (responseText!=null){
			if(responseText!="none")
			{
				var strList=responseText.split(";");
				for (var i=0; i<strList.length;i++)
				{
					var idname=strList[i].split(":");
					var id=idname[0];
					var name=idname[1];
	 				addOption(document.getElementById("customerSel"), name, id);
				}
			}			
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