
	function onlyNonZero(id)
		{
		//alert(id.value)
			if(id.value=='')
				{
					id.setAttribute("value","");
					alert("cannot be empty");
					id.focus();
					return false;
				}		
			else if(id.value==0)
			{
				id.setAttribute("value","");
				alert("cannot be zero");
				id.focus();
				return false;
			}					
			return true;
		}
		
		function fun_sla_app_param(){
		var formObj=document.forms[0];		
		document.getElementById("ChangeType").value="changed"; 
		var res=document.getElementById("changeType").value;		
		var formobj=document.forms[0];				
		formObj.action="./getSLADetails.do?param=SLADetails";		
		formObj.submit();		 
	}
	function displaySelected()
	{
	var res=confirm('Are you sure you want to modify ')
		if(res)
		{
		    var formObj=document.forms[0];			
			formObj.action="./sLASettingLDAction.do?param=changeButton";		
			formObj.submit();
		}
	
	}
	function modifySLA()
	{	
		var formObj=document.forms[0];			
		formObj.action="./sLASettingUpdateAction.do?param=Modify";		
		formObj.submit();
					 
	}
	function cancelButton()
	{
		window.location="./sLA.do?param=ApplicationsList";			
	}
		function onlyNumbers(evt)
		{   
			var e = event || evt; // for trans-browser compatibility    
			var charCode = e.which || e.keyCode;    
			if (charCode > 31 && (charCode < 48 || charCode > 57)) 
			{  
				alert("Only numeric values are accepted"); 				
				return false;
			}	
			return true;
		}