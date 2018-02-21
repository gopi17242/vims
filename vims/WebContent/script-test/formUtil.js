var edField="";
  var editIndex;
  function changeTab(actTab) {
     
	  resetPage();
	  var divs=document.getElementById("fieldDiv");
	  var attDiv=document.getElementById("attrDiv");
	  var tableDiv=document.getElementById("fieldTableDiv");

	 if(actTab=='field') {
      
           divs.style.display="block";
           attDiv.style.display="none";
           tableDiv.style.display="block";
     }
	  else { 

		    divs.style.display="none";
			attDiv.style.display="block";
			tableDiv.style.display="none";
	  }
   }

  
  function saveField() {

	 var temp=document.getElementById("fieldType");
     var temp1=temp[temp.selectedIndex].value;

	 var msg="<ul>";
	 var control;
     
	 if(temp1!="") {
         
           if(document.getElementById("label").value=='')
			   msg=msg+"<li>Label Name Missing</li>";
            
		   if(document.getElementById("alertMsg").value=='')
               msg=msg+"<li>Alert Message Missing</li>"; 
             if((temp1=='checkbox')||(temp1=='multi-select')||(temp1=='radio')||(temp1=='select')) {
	        
                      control=document.getElementById("optionValues"); 
			  
        			  if(control.length==0)
		        		msg=msg+"<li>Atleast one option is needed for select control</li>";  
			  }

        	  if((temp1=='text')||(temp1=='password')) {

                 var t=document.getElementById("maxLength").value;
		         if(t=='')
			          msg=msg+"<li>Length Not Specified</li>";
		         else
         		   if(isNaN(t))
		        	   msg=msg+"<li>Plz enter an integer as maximum length</li>";

                    t=document.getElementById("size").value;  

 		          if(t=='')
                       msg=msg+"<li>Size Not Specified</li>";
                  else if(isNaN(t))
                       msg=msg+"<li>Plz enter an integer as size</li>";  
			  }

               if((temp1=='textarea'))  {
  
                     if(document.getElementById("rows").value=='')
                        msg=msg+"<li>Number of rows not specified</li>";
                     else if(isNaN(document.getElementById("rows").value))
		                msg=msg+"<li>Give a valid Number as Rows</li>";
               
			         if(document.getElementById("columns").value=='')
                        msg=msg+"<li>Number of columns not specified</li>";
         			 else if(isNaN(document.getElementById("columns").value))
                        msg=msg+"<li>Give a valid Number as columns</li>";

			         if(document.getElementById("maxLength").value=='')
                        msg=msg+"<li>Length not specified</li>"; 
                     else if(isNaN(document.getElementById("maxLength").value))
                        msg=msg+"<li>Give a valid Number as Length</li>";  	
                  }   
       
    
	             if(msg=="<ul>") {


                    var statusValue='Inactive';
					var reqStatus="notReq";
					var nok;
            		var newOption;

					document.getElementById("errorsDiv").innerText="";
	    
            		for(var i=0;i<2;i++)    {
						 if(document.forms[1].fieldStatus(i).checked==true) {
            				statusValue=document.forms[1].fieldStatus(i).value;
		                 } 
		            }
			        
					for(var i=0;i<2;i++) {
                         if(document.forms[1].required(i).checked==true) {
            				reqStatus=document.forms[1].required(i).value;
		                  }
		             }
                if(edField=="") {      
		    		 newOption=document.getElementById("labels");
					 addNewChild(newOption,document.getElementById("label").value);
		
		             newOption=document.getElementById("alertMsgs");
					 addNewChild(newOption,document.getElementById("alertMsg").value);
		
                     newOption=document.getElementById("reqList");
		             addNewChild(newOption,reqStatus); 
 
                     newOption=document.getElementById("fieldsStatus");
		             addNewChild(newOption,statusValue); 

  
         var temp=document.getElementById("fieldType");
         var temp1=temp[temp.selectedIndex].value;

         
		 alert("dljfalskj======"+temp1); 

         if((temp1=='text')||(temp1=='password')) {

             // Field type Store  
                 
				
				newOption=document.getElementById("typeList");
				addNewChild(newOption,temp1); 
  			 
            // Max length store
			   
                newOption=document.getElementById("maxLenList"); 
                addNewChild(newOption,document.getElementById("maxLength").value); 
  		   //size value store

	            newOption=document.getElementById("sizeList");
				addNewChild(newOption,document.getElementById("size").value); 

           // Set oter attribute values to empty

                  // rowz  
		          newOption=document.getElementById("rowList");
				  addNewChild(newOption,"0");

			 	  // columns
				  newOption=document.getElementById("columnList");
                  addNewChild(newOption,"0");
			 
				  // options store
				  newOption=document.getElementById("selectOptions");
                  addNewChild(newOption,"0");
		}

		    if(temp1=='textarea') {
		
			    
			   // Field type Store  

				newOption=document.getElementById("typeList");
				addNewChild(newOption,temp1);

              // Max length store
			   
			     newOption=document.getElementById("maxLenList"); 
                 addNewChild(newOption,document.getElementById("maxLength").value);
				  
              // rowz  
		          newOption=document.getElementById("rowList");
                  addNewChild(newOption,document.getElementById("rows").value);
  			
              // columns
				  newOption=document.getElementById("columnList");
				  addNewChild(newOption,document.getElementById("columns").value);

        	  // set other values to 0

			     
				  //size 
			      newOption=document.getElementById("sizeList");
                  addNewChild(newOption,"0");
		 
				  // options store
				  newOption=document.getElementById("selectOptions");
				  addNewChild(newOption,"0");
     		  }
		  
		    if((temp1=='checkbox')||(temp1=='select')||(temp1=='radio')) {
        
		        
				 var optSet="";
                 var testId=document.getElementById("optionValues"); 
                 for(var i=0;i<testId.length;i++) {

                    optSet=optSet+testId.options[i].value;
				    optSet=optSet+",";
	             }
          
				 // Field type Store  
                  newOption=document.getElementById("typeList");
				  addNewChild(newOption,temp1);

     			  // options store
				  newOption=document.getElementById("selectOptions");
				  addNewChild(newOption,optSet);

                  // set other values to 0

                   //size
 			      newOption=document.getElementById("sizeList");
                  addNewChild(newOption,"0");
			
				   // row
    			   newOption=document.getElementById("rowList");
				   addNewChild(newOption,"0");

 				   //columns
                   newOption=document.getElementById("columnList");
				   addNewChild(newOption,"0");

				   // max length
				   newOption=document.getElementById("maxLenList");
				   addNewChild(newOption,"0");
            }

			 var newRow=document.getElementById("fieldTable").insertRow();
        	 var newFieldName=newRow.insertCell(0);
         	 var newFieldType=newRow.insertCell(1);
         	 var newFieldStatus=newRow.insertCell(2);
			 var newFieldOptions=newRow.insertCell(3);

	         newFieldName.innerHTML="<b>"+document.getElementById("label").value+"</b>";
         newFieldType.innerHTML="<b>"+temp1+"</b>";
         newFieldStatus.innerHTML="<b>"+statusValue+"</b>"; 
		 
		 var optionsText="<span onClick=editField('"+document.getElementById("label").value+"')>Edit </span>"+"&nbsp;&nbsp;&nbsp;<span onClick=deleteField('"+document.getElementById("label").value+"')>Delete</span>";

		 newFieldOptions.innerHTML=optionsText;

		     var divs=document.getElementById("fieldDiv");
             divs.style.display="none";
      
             var attDiv=document.getElementById("attrDiv");   
             attDiv.style.display="block";

		     var tableDiv=document.getElementById("fieldTableDiv"); 
             tableDiv.style.display="none";
	   } 
         else {
			  edField="";
                 
	setEditedValues(document.getElementById("labels"),document.getElementById("label").value); 
    setEditedValues(document.getElementById("alertMsgs"),document.getElementById("alertMsg").value);
    setEditedValues(document.getElementById("sizeList"),document.getElementById("size").value);
    setEditedValues(document.getElementById("maxLenList"),document.getElementById("maxLength").value);
    setEditedValues(document.getElementById("rowList"),document.getElementById("rows").value);
    setEditedValues(document.getElementById("columnList"),document.getElementById("columns").value);	

              for(var i=0;i<2;i++)    {
					if(document.forms[1].fieldStatus(i).checked==true) {
            		   statusValue=document.forms[1].fieldStatus(i).value;
		           } 
		          }
			        
			    for(var i=0;i<2;i++) {
                      if(document.forms[1].required(i).checked==true) {
            	    	reqStatus=document.forms[1].required(i).value;
		             }
		          } 

           setEditedValues(document.getElementById("reqList"),reqStatus);
           setEditedValues(document.getElementById("fieldsStatus"),statusValue);
		   var optSet="";
           var testId=document.getElementById("optionValues"); 
           for(var i=0;i<testId.length;i++) {

                    optSet=optSet+testId.options[i].value;
				    optSet=optSet+",";
	             }

           setEditedValues(document.getElementById("selectOptions"),optSet);
		   
/*	  var temRow=document.getElementById("fieldTable").insertRow(editIndex+1);
          temRow.insertCell(0).innerHTML=document.getElementById("label").value; temRow.insertCell(1).innerHTML="<b>"+temp1+"</b>";
		  temRow.insertCell(2).innerHTML="<b>"+fieldStatus+"</b>";
		 
		  var optionsText="<span onClick=editField('"+document.getElementById("label").value+"')>Edit </span>"+"&nbsp;&nbsp;&nbsp;<span onClick=deleteField('"+document.getElementById("label").value+"')>Delete</span>";

		  temRow.insertCell(3).innerHTML=optionsText; */

           editIndex="";
		 } 
		resetPage();
	  }
	 else {
			 msg=msg+"</ul>";
             document.getElementById("errorsDiv").innerHTML=msg;
		  } 
	  }
    }     
   
   function getValue() {
     
        var source=document.getElementById("options");
        if(source.value!='') {
        var targetControl=document.getElementById("optionValues");
        var newOption=document.createElement("option");

		newOption.text=source.value;
		newOption.value=source.value
		targetControl.add(newOption);

		source.value="";
	  }
	   else  {
	     alert("Enter a State name");
	     source.focus();
	  } 	
    }

    function removeValue() {

       var targetControl=document.getElementById("optionValues");
       if(targetControl.length==0) {
       alert("No States are present");
       return;
      } 
       else if((targetControl.selectedIndex<0)||(targetControl.selectedIndex==null))
       alert("Select any state name");
       else
	    targetControl.removeChild(targetControl[targetControl.selectedIndex]);
	}
   
    function resetPage() {

        document.getElementById("maxLength").value="0"; 
		document.getElementById("size").value="0"; 
		document.getElementById("rows").value="0";
		document.getElementById("columns").value="0"; 
		document.getElementById("label").value=""; 
		document.getElementById("alertMsg").value="";

		var targetControl=document.getElementById("optionValues");
        for(cnt=targetControl.options.length;cnt>-1;cnt--) {
			    targetControl.remove(cnt);
		}       
		makeHidden();
  	    document.getElementById("fieldType").selectedIndex=0;
	}

    function whenChanged() {
    
        var selectedField=document.getElementById("fieldType");
        var ft=selectedField.value;
        
		// set all controls to hidden
		 makeHidden();  
      if((ft=='checkbox')||(ft=='multi-select')||(ft=='radio')||(ft=='select')) {
        
           document.getElementById("optionTr").style.display="block"; 
           document.getElementById("selectTr").style.display="block";

       }
       
       if((ft=='text')||(ft=='password')) {
         
           document.getElementById("lengthTr").style.display="block";
           document.getElementById("sizeTr").style.display="block";
        } 
         
         if((ft=='textarea')) {
         
            document.getElementById("rowsTr").style.display="block";
            document.getElementById("columnsTr").style.display="block";
            document.getElementById("lengthTr").style.display="block";
         }   
      }
	   
	    function setAll() {

              var cnt; 
              var control;
			  
			  control=document.getElementById("labels");
    		  makeActive(control);
			 
			  control=document.getElementById("alertMsgs");
			  makeActive(control);
			  
			  control=document.getElementById("sizeList");
     		  makeActive(control);
             
			  control=document.getElementById("rowList");
			  makeActive(control);
			
			  control=document.getElementById("columnList");
			  makeActive(control);
			
			  control=document.getElementById("typeList");
			  makeActive(control);
			 
			  control=document.getElementById("maxLenList");
			  makeActive(control);
			  
    		  control=document.getElementById("reqList");
			  makeActive(control);
	 
			  control=document.getElementById("fieldsStatus");
			  makeActive(control);

	    	  control=document.getElementById("selectOptions");
			  makeActive(control);
	      }
             
		  function makeActive(control) {

		   var lens=control.length;
		
			  for(cnt=0;cnt<lens;cnt++) {
                     control.options[cnt].selected="true";  
			       }
		  } 

		   function addNewChild(newOption,opValue) {
                var nok;

	    		nok=document.createElement("option");
                nok.setAttribute("value",opValue);
                nok.setAttribute("label",opValue);
    	        nok.setAttribute("selected");
                newOption.add(nok);
		   }

		    function makeHidden() {

                document.getElementById("lengthTr").style.display="none";
                document.getElementById("sizeTr").style.display="none"; 
		        document.getElementById("optionTr").style.display="none"; 
                document.getElementById("selectTr").style.display="none";
                document.getElementById("rowsTr").style.display="none";
                document.getElementById("columnsTr").style.display="none";
        }

		   function editField(opField) { 
		   
		      alert("Edit method called......");
              edField="edit"; 


              var tempVar=document.getElementById("labels"); 
			  var tempType;
			  var cnt;
			  for(cnt=0;cnt<tempVar.length;cnt++) {
                    
				 if(tempVar[cnt].value==opField) {
                    alert("mathced.......");
					editIndex=cnt;
					tempType=document.getElementById("typeList")[cnt].value;
			        
					 var fs=document.getElementById("fieldsStatus")[cnt].value;
					 var req=document.getElementById("reqList")[cnt].value;

                     
	              for(var i=0;i<2;i++)    {
						 if(document.forms[1].fieldStatus(i).value==fs) {
            				statusValue=document.forms[1].fieldStatus(i).checked="checked";
		                 } 
		            }
			        
					for(var i=0;i<2;i++) {

                         if(document.forms[1].required(i).value==req) {
            		      reqStatus=document.forms[1].required(i).checked="checked";
		                  }
		             }    
			  				   // Set all control attributes to hidden
					makeHidden();  

               for(t=0;t<document.getElementById("fieldType").length;t++) {
				
				 if(document.getElementById("fieldType")[t].value==tempType)
					document.getElementById("fieldType")[t].selected="true";
			   }

					document.getElementById("label").value=opField;
                    document.getElementById("alertMsg").value=document.getElementById("alertMsgs")[cnt].value;
          if((tempType=='checkbox')||(tempType=='multi-select')||(tempType=='radio')||(tempType=='select')) {
           document.getElementById("optionTr").style.display="block"; 
		   setOptions(cnt);
		   document.getElementById("selectTr").style.display="block";
         }
       
       if((tempType=='text')||(tempType=='password')) {
           document.getElementById("lengthTr").style.display="block";
		   document.getElementById("maxLength").value=document.getElementById("maxLenList")[cnt].value;
           document.getElementById("size").value=document.getElementById("sizeList")[cnt].value;
           document.getElementById("sizeTr").style.display="block";
        } 
         
         if((tempType=='textarea')) {
            
           document.getElementById("rowsTr").style.display="block";
           document.getElementById("rows").value=document.getElementById("rowList")[cnt].value;
           
		   document.getElementById("columnsTr").style.display="block";
			document.getElementById("columns").value=document.getElementById("columnList")[cnt].value;

            document.getElementById("lengthTr").style.display="block";
			document.getElementById("maxLength").value=document.getElementById("maxLenList")[cnt].value;

         }
        }
	   }
      } 
      

	function deleteField(opField,rowIndex) {
		   
		      alert("delete method called......");

              var tempVar=document.getElementById("labels"); 
			  var cnt;
			  for(cnt=0;cnt<tempVar.length;cnt++) {
                    
					 if(tempVar[cnt].value==opField) {
						tempVar.remove(cnt) ;
						document.getElementById("alertMsgs").remove(cnt);
						document.getElementById("sizeList").remove(cnt);
						document.getElementById("columnList").remove(cnt);
						document.getElementById("rowList").remove(cnt);
						document.getElementById("maxLenList").remove(cnt);
						document.getElementById("typeList").remove(cnt);
						document.getElementById("reqList").remove(cnt);
						document.getElementById("fieldsStatus").remove(cnt);
						document.getElementById("selectOptions").remove(cnt);
                        
						// delete the entry from displaying table
                        document.getElementById("fieldTable").deleteRow(cnt+1);
					 } 
              }
		   }

		    function setOptions(cnt) {
                    alert("called--------");
                    var v=document.getElementById("selectOptions")[cnt].value;
					var tempIndex=0;
                    alert(v);
					for(l=0;l<v.length;l++) {

						if(v.charAt(l)==',') {

					var targetControl=document.getElementById("optionValues");
                    var newOption=document.createElement("option");
                  		newOption.text=v.substring(tempIndex,l);
                		newOption.value=v.substring(tempIndex,l);
                		targetControl.add(newOption);
						tempIndex=l+1;
                       }
                    }
			 }

			 function setEditedValues(control,cValue) {
                   
			    control[editIndex].value=cValue;
                control[editIndex].label=cValue;
			 }