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
	
function fnGetApplByStatus()
	{    
		removeOptions(document.getElementById("customerSel"));		
		x=GetXmlHttpObject();	    
		if (x==null)
		  {
			  alert ("Your browser does not support AJAX!");
			  return
		  } 			 
		var status=document.getElementById('statusId').value;
		var url="VIMSReportsLookUpDispatchAction.do?param=getApplicationsByStatus&strStatus="+status;
		x.open("POST",url,false);
		x.send(null);
		
		x.onreadystatechange=function() {		    
            if (x.readyState == 4) {
	            if (x.status == 200) {	             
			        fnSetApplByStatus(x.responseText);
                } else if (x.status == 204){
                    alert('cant do anything');
                }
            }
        };
	}
function fnSetApplByStatus(responseText) 
{ 
	if (responseText!=null){
			var strList=responseText.split(";");//return array of strings
			for (var i=0; i<strList.length;i++)
			{
				var idname=strList[i].split(":");
				var id=idname[0];
				var name=idname[1];
 				addOption(document.getElementById("applId"), name, id);
			}
		}
}
function addOption(selectbox,text,value)
	{
		var optn = document.createElement("OPTION");//creates option object
		optn.text = text;
		optn.value = value;
		selectbox.options.add(optn);
	}
function removeOptions(selectBox)
	{	
		var len=selectBox.length;	
		for(var j=len;j>0;j--)		
		{
			selectBox.options[j] =null;//to remove the option 
			
		}
}

function submitForm()
{
  alert("Form Submitted");
}
function disableFunction()
{
	alert('disableFunction');
	var appl=document.getElementById('applId').value;
	if(appl!='')
	{		
		document.getElementById('todate').value="";
		document.getElementById('frmdate').value="";		
		document.getElementById('dateId').style.display="none"
	}
	else
	{		
		document.getElementById('dateId').style.display="block"
	}
}