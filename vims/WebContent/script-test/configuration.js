/************** Configuration Setting tab script methods**********************/

function NewWin(){
	
	 
	 var priOptions=document.getElementById("selectedOptions");
	 var elements=new Array(priOptions.length);
	 for(var cnt=0;cnt<priOptions.length;cnt++) {
	 elements[cnt]=priOptions.options[cnt].value;
	 } 
	 if (navigator.appName == "Microsoft Internet Explorer")
	{
	 	window.open('./test-errorPages/priviewHome.jsp?selOptions='+elements, "UploadFile", 'toolbar=no, status=yes, left=200, top=250, scrollbars=no, resize=no,  width=840, height=600');
	}
	else if (navigator.appName == "Netscape") 
	{
       window.open("./test-errorPages/priviewHome.jsp?selOptions="+elements, "UploadFile",  'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=no,resizable=0,width=450,height=350,top=100,left=200');		
	}
}

function moveOriginIndstTypes1()
{
  var count=0;
  var i=0;
  if (document.forms[0].selectOptions.options.length == 0)
   alert(noValues);
  else{
	while(i<document.forms[0].selectOptions.options.length)
	{
		len = document.forms[0].selectedOptions.options.length;
  
		if(document.forms[0].selectOptions.options[i].selected)
		{
			
			document.forms[0].selectedOptions.options[len] = new Option(document.forms[0].selectOptions.options[i].text,document.forms[0].selectOptions.options[i].value);
			document.forms[0].selectOptions.options[i] =null;
			count++;
			i=0;
		}
		else
			i++;
	}
	if(count==0)
	//alert("please Select an Option");
    alert(configAlert);
     }
}
function moveDestIndstTypes1()
{
	var count=0;
	var i=0;
	if (document.forms[0].selectedOptions.options.length == 0)
		//alert("please Select an Option");
		alert(configAlert);
  	else
  	{
		while(i<document.forms[0].selectedOptions.options.length)
		{
			len = document.forms[0].selectOptions.options.length
			if(document.forms[0].selectedOptions.options[i].selected) 
			{
				document.forms[0].selectOptions.options[len] = new Option(document.forms[0].selectedOptions.options[i].text,document.forms[0].selectedOptions.options[i].value);
				document.forms[0].selectedOptions.options[i] =null;
				
				count++;
				i=0;				
			}
			else
				i++;		
		}
		if(count==0)
		//alert("please Select an Option");
		alert(configAlert);
	}
}
 function selectAll() {
       
     var priOptions=document.getElementById("selectedOptions");
	 for(var cnt=0;cnt<priOptions.length;cnt++) {
	 priOptions.options[cnt].selected="selected";
	 } 
}

function modifyMailDetail(serverId,dftId){
      window.location="./modifyMailServerDetails.do?param=getMailDetails&serverId="+serverId+"&default_Id="+dftId+"&type=modify";
}

function deleteMailDetail(serverId) {

    if(confirm(recordDelete)) {
      window.location="./modifyMailServerDetails.do?param=deleteMailDetails&serverId="+serverId+"&type=delete";
    }
}

function modifyLdapDetail(serverId) {

    window.location="./modifyLdapServerDetails.do?param=getLdapDetails&serverId="+serverId+"&type=modify"
}

function deleteLdapDetail(serverId) {
	  
    if(confirm(recordDelete)) {
      window.location="./modifyLdapServerDetails.do?param=deleteLdapDetails&serverId="+serverId+"&type=delete";
    }  
}

function clearLDAPData() {
 
  document.getElementById("serverName").value="";
  document.getElementById("portNumber").value="";
  document.getElementById("serverStatus").selectedIndex=0;
 
}	 


function clearMailData() {
 document.getElementById("serverName").value="";
 document.getElementById("serverStatus").selectedIndex=0;
}

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

function selectAll(control,cnt) {
     
   if(cnt!='') { 
    var objCheckBoxes=document.forms[0].elements[cnt];
    alert(cnt);
   	var countCheckBoxes = objCheckBoxes.length;
   	if((objCheckBoxes.length==null)) {
        
        if(control.checked==true)
         document.forms[0].elements[cnt].checked=true;
        else
         document.forms[0].elements[cnt].checked=false;
   	    return
   	 }
   	if((countCheckBoxes!=null)&&(countCheckBoxes>0)) {
	for(var k = 0; k < countCheckBoxes; k++) {
		if(control.checked==true) 
		 objCheckBoxes[k].checked=true;
		else 
		 objCheckBoxes[k].checked=false;
	  } 
    }
  }
}