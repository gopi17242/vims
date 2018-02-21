
var isIE;
var completeTable;
var completeField;
var autorow;
var req;


function getElementY(element){
	var targetTop = 0;
	if (element.offsetParent) {
		while (element.offsetParent) {
			targetTop += element.offsetTop;
            element = element.offsetParent;
		}
	} else if (element.y) {
		targetTop += element.y;
    }
	return targetTop;
}

function init() {
	completeField = document.getElementById("complete-field");
    var menu = document.getElementById("auto-row");
    autorow = document.getElementById("menu-popup");
    autorow.style.top = getElementY(menu) + "px";
    completeTable = document.getElementById("completeTable");
    completeTable.setAttribute("bordercolor", "white");
}

function initRequest(url) {
    if (window.XMLHttpRequest) {
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}
function doCompletion() {
	init();
	if (completeField.value == "") 
    {
    	clearTable();
    } 
    else 
    {
        var url = "autocomplete?action=complete&appId=" + escape(completeField.value);
        var req = initRequest(url);
        req.onreadystatechange = function() {
            if (req.readyState == 4) {
                 if (req.status == 200) 
                {
                	parseMessages(req.responseXML);
                } else if (req.status == 204){
                	clearTable();
                }
            }
        };
        req.open("GET", url, true);
        req.send(null);
    }
}
function parseMessages(responseXML) {
    clearTable();
	var applications = responseXML.getElementsByTagName("applications")[0];
    if (applications.childNodes.length > 0) {
        completeTable.setAttribute("bordercolor", "black");
        completeTable.setAttribute("border", "1");
    } else {
        clearTable();
    }
    
    for (loop = 0; loop < applications.childNodes.length; loop++) {
	    var application = applications.childNodes[loop];
       // var firstName = application.getElementsByTagName("firstName")[0];
        var appName = application.getElementsByTagName("name")[0];
        var appId = application.getElementsByTagName("id")[0];
        appendApplication(appName.childNodes[0].nodeValue, appId.childNodes[0].nodeValue);
    }
}

function clearTable() {
    if (completeTable) {
      completeTable.setAttribute("bordercolor", "white");
      completeTable.setAttribute("border", "0");
      completeTable.style.visible = false;
      for (loop = completeTable.childNodes.length -1; loop >= 0 ; loop--) {
        completeTable.removeChild(completeTable.childNodes[loop]);
      }
    }
}

function appendApplication(appName,appId) {
   // var firstNameCell;
    var lastNameCell;
    var row;
    var nameCell;
    if (isIE) {
        row = completeTable.insertRow(completeTable.rows.length);
        nameCell = row.insertCell(0);
    } else {
        row = document.createElement("tr");
        nameCell = document.createElement("td");
        row.appendChild(nameCell);
        completeTable.appendChild(row);
    }
    row.className = "popupRow";
   // nameCell.setAttribute("bgcolor", "#FFFAFA");

    
    var linkElement = document.createElement("a");
    linkElement.className = "popupItem";
   linkElement.setAttribute("href", "./viewApplicationAction.do?param=viewApplication&pageId=ViewApplication&appId="+appId);
  	// linkElement.setAttribute("href", employeeId);
    //linkElement.setAttribute(employeeId);
	linkElement.appendChild(document.createTextNode(appName));
    nameCell.appendChild(linkElement);
}