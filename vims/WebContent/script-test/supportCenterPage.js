function frmchange1()
	{
	    var formobj=document.forms[0];	   
	    formobj.action="./supportCenter.do?methodname=SupportCenterDetails";
		formobj.submit();
	}
	function frmchange2()
	{
	    var formobj=document.forms[0];	    
	    formobj.action="./supportCenter.do?methodname=groupDetails";
		formobj.submit();
	}