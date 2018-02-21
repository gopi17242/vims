/*----------------------------------------------------------------------------\
|                               Tab Pane 1.02                                 |
|-----------------------------------------------------------------------------|
|                         Created by Erik Arvidsson                           |
|                  (http://webfx.eae.net/contact.html#erik)                   |
|                      For WebFX (http://webfx.eae.net/)                      |
|-----------------------------------------------------------------------------|
|                  Copyright (c) 1998 - 2003 Erik Arvidsson                   |
|-----------------------------------------------------------------------------|
| This software is provided "as is", without warranty of any kind, express or |
| implied, including  but not limited  to the warranties of  merchantability, |
| fitness for a particular purpose and noninfringement. In no event shall the |
| authors or  copyright  holders be  liable for any claim,  damages or  other |
| liability, whether  in an  action of  contract, tort  or otherwise, arising |
| from,  out of  or in  connection with  the software or  the  use  or  other |
| dealings in the software.                                                   |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| This  software is  available under the  three different licenses  mentioned |
| below.  To use this software you must chose, and qualify, for one of those. |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Non-Commercial License          http://webfx.eae.net/license.html |
| Permits  anyone the right to use the  software in a  non-commercial context |
| free of charge.                                                             |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Commercial license           http://webfx.eae.net/commercial.html |
| Permits the  license holder the right to use  the software in a  commercial |
| context. Such license must be specifically obtained, however it's valid for |
| any number of  implementations of the licensed software.                    |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| GPL - The GNU General Public License    http://www.gnu.org/licenses/gpl.txt |
| Permits anyone the right to use and modify the software without limitations |
| as long as proper  credits are given  and the original  and modified source |
| code are included. Requires  that the final product, software derivate from |
| the original  source or any  software  utilizing a GPL  component, such  as |
| this, is also licensed under the GPL license.                               |
|-----------------------------------------------------------------------------|
| 2002-01-?? | First working version                                          |
| 2002-02-17 | Cleaned up for 1.0 public version                              |
| 2003-02-18 | Changed from javascript uri for anchors to return false        |
| 2003-03-03 | Added dispose methods to release IE memory                     |
|-----------------------------------------------------------------------------|
| Dependencies: *.css           a css file to define the layout               |
|-----------------------------------------------------------------------------|
| Created 2002-01-?? | All changes are in the log above. | Updated 2003-03-03 |
\----------------------------------------------------------------------------*/

// This function is used to define if the browser supports the needed
// features
function hasSupport() {

  if (typeof hasSupport.support != "undefined")
    return hasSupport.support;
  
  var ie55 = /msie 5\.[56789]/i.test( navigator.userAgent );
  
  hasSupport.support = ( typeof document.implementation != "undefined" &&
      document.implementation.hasFeature( "html", "1.0" ) || ie55 )
      
  // IE55 has a serious DOM1 bug... Patch it!
  if ( ie55 ) {
    document._getElementsByTagName = document.getElementsByTagName;
    document.getElementsByTagName = function ( sTagName ) {
      if ( sTagName == "*" )
        return document.all;
      else
        return document._getElementsByTagName( sTagName );
    };
  }

  return hasSupport.support;
}

///////////////////////////////////////////////////////////////////////////////////
// The constructor for tab panes
//
// el : HTMLElement    The html element used to represent the tab pane
// bUseCookie : Boolean  Optional. Default is true. Used to determine whether to us
//            persistance using cookies or not
//
function WebFXTabPane( el, bUseCookie ) {
  if ( !hasSupport() || el == null ) return;
  
  this.element = el;
  this.element.tabPane = this;
  this.pages = [];
  this.selectedIndex = null;
  this.useCookie = bUseCookie != null ? bUseCookie : true;
  
  // add class name tag to class name
  this.element.className = this.classNameTag + " " + this.element.className;
  
  // add tab row
  this.tabRow = document.createElement( "div" );
  this.tabRow.className = "tab-row";
  el.insertBefore( this.tabRow, el.firstChild );

  var tabIndex = 0;
  if ( this.useCookie ) {
    tabIndex = Number( WebFXTabPane.getCookie( "webfxtab_" + this.element.id ) );
    if ( isNaN( tabIndex ) )
      tabIndex = 0;
  }
  this.selectedIndex = tabIndex;
  
  // loop through child nodes and add them
  var cs = el.childNodes;
  var n;
  for (var i = 0; i < cs.length; i++) {
    if (cs[i].nodeType == 1 && cs[i].className == "tab-page") {
      this.addTabPage( cs[i] );
    }
  }
}

WebFXTabPane.prototype.classNameTag = "dynamic-tab-pane-control";

WebFXTabPane.prototype.setSelectedIndex = function ( n ) {
  if (this.selectedIndex != n) {
    if (this.selectedIndex != null && this.pages[ this.selectedIndex ] != null )
      this.pages[ this.selectedIndex ].hide();
    this.selectedIndex = n;
    this.pages[ this.selectedIndex ].show();
    
    if ( this.useCookie )
      WebFXTabPane.setCookie( "webfxtab_" + this.element.id, n );  // session cookie
  }
};
  
WebFXTabPane.prototype.getSelectedIndex = function () {
  return this.selectedIndex;
};
  
WebFXTabPane.prototype.addTabPage = function ( oElement ) {
  if ( !hasSupport() ) return;
  
  if ( oElement.tabPage == this )  // already added
    return oElement.tabPage;

  var n = this.pages.length;
  var tp = this.pages[n] = new WebFXTabPage( oElement, this, n );
  tp.tabPane = this;
  
  // move the tab out of the box
  this.tabRow.appendChild( tp.tab );
      
  if ( n == this.selectedIndex )
    tp.show();
  else
    tp.hide();
    
  return tp;
};
  
WebFXTabPane.prototype.dispose = function () {
  this.element.tabPane = null;
  this.element = null;    
  this.tabRow = null;
  
  for (var i = 0; i < this.pages.length; i++) {
    this.pages[i].dispose();
    this.pages[i] = null;
  }
  this.pages = null;
};



// Cookie handling
WebFXTabPane.setCookie = function ( sName, sValue, nDays ) {
  var expires = "";
  if ( nDays ) {
    var d = new Date();
    d.setTime( d.getTime() + nDays * 24 * 60 * 60 * 1000 );
    expires = "; expires=" + d.toGMTString();
  }

  document.cookie = sName + "=" + sValue + expires + "; path=/";
};

WebFXTabPane.getCookie = function (sName) {
  var re = new RegExp( "(\;|^)[^;]*(" + sName + ")\=([^;]*)(;|$)" );
  var res = re.exec( document.cookie );
  return res != null ? res[3] : null;
};

WebFXTabPane.removeCookie = function ( name ) {
  setCookie( name, "", -1 );
};








///////////////////////////////////////////////////////////////////////////////////
// The constructor for tab pages. This one should not be used.
// Use WebFXTabPage.addTabPage instead
//
// el : HTMLElement      The html element used to represent the tab pane
// tabPane : WebFXTabPane  The parent tab pane
// nindex :  Number      The index of the page in the parent pane page array
//
function WebFXTabPage( el, tabPane, nIndex ) {
  if ( !hasSupport() || el == null ) return;
  
  this.element = el;
  this.element.tabPage = this;
  this.index = nIndex;
  
  var cs = el.childNodes;
  for (var i = 0; i < cs.length; i++) {
    if (cs[i].nodeType == 1 && cs[i].className == "tab") {
      this.tab = cs[i];
      break;
    }
  }
  
  // insert a tag around content to support keyboard navigation
  
  
  var a = document.createElement( "A" );
  this.aElement = a;
  a.href = "#";
  a.onclick = function () { return false; };
  while ( this.tab.hasChildNodes() )
    a.appendChild( this.tab.firstChild );
  this.tab.appendChild( a );

  
  // hook up events, using DOM0
  var oThis = this;
  this.tab.onclick = function () { oThis.select(); };
  this.tab.onmouseover = function () { WebFXTabPage.tabOver( oThis ); };
  this.tab.onmouseout = function () { WebFXTabPage.tabOut( oThis ); };
}

WebFXTabPage.prototype.show = function () {
  var el = this.tab;
  var s = el.className + " selected";
  s = s.replace(/ +/g, " ");
  el.className = s;
  
  this.element.style.display = "block";
};

WebFXTabPage.prototype.hide = function () {
  var el = this.tab;
  var s = el.className;
  s = s.replace(/ selected/g, "");
  el.className = s;

  this.element.style.display = "none";
};
  
WebFXTabPage.prototype.select = function () {
  this.tabPane.setSelectedIndex( this.index );
};
  
WebFXTabPage.prototype.dispose = function () {
  this.aElement.onclick = null;
  this.aElement = null;
  this.element.tabPage = null;
  this.tab.onclick = null;
  this.tab.onmouseover = null;
  this.tab.onmouseout = null;
  this.tab = null;
  this.tabPane = null;
  this.element = null;
};

WebFXTabPage.tabOver = function ( tabpage ) {
  var el = tabpage.tab;
  var s = el.className + " hover";
  s = s.replace(/ +/g, " ");
  el.className = s;
};

WebFXTabPage.tabOut = function ( tabpage ) {
  var el = tabpage.tab;
  var s = el.className;
  s = s.replace(/ hover/g, "");
  el.className = s;
};


// This function initializes all uninitialized tab panes and tab pages
function setupAllTabs() {
  if ( !hasSupport() ) return;

  var all = document.getElementsByTagName( "*" );
  var l = all.length;
  var tabPaneRe = /tab\-pane/;
  var tabPageRe = /tab\-page/;
  var cn, el;
  var parentTabPane;
  
  for ( var i = 0; i < l; i++ ) {
    el = all[i]
    cn = el.className;

    // no className
    if ( cn == "" ) continue;
    
    // uninitiated tab pane
    if ( tabPaneRe.test( cn ) && !el.tabPane )
      new WebFXTabPane( el );
  
    // unitiated tab page wit a valid tab pane parent
    else if ( tabPageRe.test( cn ) && !el.tabPage &&
          tabPaneRe.test( el.parentNode.className ) ) {
      el.parentNode.tabPane.addTabPage( el );      
    }
  }
}

function disposeAllTabs() {
  if ( !hasSupport() ) return;
  
  var all = document.getElementsByTagName( "*" );
  var l = all.length;
  var tabPaneRe = /tab\-pane/;
  var cn, el;
  var tabPanes = [];
  
  for ( var i = 0; i < l; i++ ) {
    el = all[i]
    cn = el.className;

    // no className
    if ( cn == "" ) continue;
    
    // tab pane
    if ( tabPaneRe.test( cn ) && el.tabPane )
      tabPanes[tabPanes.length] = el.tabPane;
  }
  
  for (var i = tabPanes.length - 1; i >= 0; i--) {
    tabPanes[i].dispose();
    tabPanes[i] = null;
  }
}




// initialization hook up

// DOM2
if ( typeof window.addEventListener != "undefined" )
  window.addEventListener( "load", setupAllTabs, false );

// IE 
else if ( typeof window.attachEvent != "undefined" ) {
  window.attachEvent( "onload", setupAllTabs );
  window.attachEvent( "onunload", disposeAllTabs );
}

else {
  if ( window.onload != null ) {
    var oldOnload = window.onload;
    window.onload = function ( e ) {
      oldOnload( e );
      setupAllTabs();
    };
  }
  else 
    window.onload = setupAllTabs;
}

var tabPane;

function showArticleTab( sName ) {
  if (typeof tabPane != "undefined" ) {
  
    switch ( sName ) {
    
      case "main":
        tabPane.setSelectedIndex( 0 );
        break;
        
      case "usage":
        tabPane.setSelectedIndex( 1 );
        break;
    
      case "api":
        tabPane.setSelectedIndex( 2 );
        break;

      case "implementation":
        tabPane.setSelectedIndex( 3 );
        break;
        
      case "looknfeel":
        tabPane.setSelectedIndex( 4 );
        break;
    }  
  }
}

// help tips
htDom = "Document Object Model 1 is a standard developed by the W3C.<br />" +
    "<a href=\"http://www.w3.org/DOM/\" target=\"_blank\">http://www.w3.org/DOM/</a>";


var articleMenu= new WebFXMenu;
articleMenu.left  = 384;
articleMenu.top   = 86;
articleMenu.width = 140;
articleMenu.add(new WebFXMenuItem("Tab Pane", "javascript:showArticleTab( \"main\" )"));
articleMenu.add(new WebFXMenuItem("Usage", "javascript:showArticleTab( \"usage\" )"));
articleMenu.add(new WebFXMenuItem("API", "javascript:showArticleTab( \"api\" )"));
articleMenu.add(new WebFXMenuItem("Implementation", "javascript:showArticleTab( \"implementation\" )"));
articleMenu.add(new WebFXMenuItem("Look &amp; Feel", "javascript:showArticleTab( \"looknfeel\" )"));
articleMenu.add(new WebFXMenuItem("Demo", "demo.html"));
articleMenu.add(new WebFXMenuSeparator);
articleMenu.add(new WebFXMenuItem("Download", "http://webfx.eae.net/download/tabpane102.zip"));
webfxMenuBar.add(new WebFXMenuButton("Article Menu", null, null, articleMenu));

//webfxLayout.writeTitle("Tab Pane");
webfxLayout.writeMenu();
webfxLayout.writeDesignedByEdger();
/*
 *  This script is used for WebFX Api pages
 *
 *  It defines one funtion and includes helptip.js, helptip.css and webfxapi.css
 */

document.write( "<script type='text/javascript' src='local/helptip.js'><\/script>" );
document.write( "<link type='text/css' rel='stylesheet' href='local/helptip.css' />" );
document.write( "<link type='text/css' rel='stylesheet' href='local/webfxapi.css' />" );

function toggleMethodArguments( e, a ) {
  if ( a && a.nextSibling &&
    typeof a.nextSibling.innerHTML != "undefined" &&
    typeof showHelpTip != "undefined" ) {
  
    showHelpTip( e, a.nextSibling.innerHTML );
    
  }
}
/* this is a dummy webfxlayout file to be used in download zip files */


/* Do includes */

if (window.pathToRoot == null)
  pathToRoot = "./";

document.write('<link type="text/css" rel="stylesheet" href="local/webfxlayout.css">');
webfxMenuDefaultImagePath = pathToRoot + "images/";

/* end includes */

/* set up browser checks and add a simple emulation for IE4 */

// check browsers
var op = /opera 5|opera\/5/i.test(navigator.userAgent);
var ie = !op && /msie/i.test(navigator.userAgent);  // preventing opera to be identified as ie
var mz = !op && /mozilla\/5/i.test(navigator.userAgent);  // preventing opera to be identified as mz

if (ie && document.getElementById == null) {  // ie4
  document.getElementById = function(sId) {
    return document.all[sId];
  };
}

/* end browser checks */

webfxLayout = {
  writeTitle    :  function (s, s2) {
    document.write("<div id='webfx-title-background'></div>");
    if (op) {
      document.write("<h1 id='webfx-title' style='top:9px;'>" + s + "</h1>");
    }
    else {
      document.write("<h1 id='webfx-title'>" + s + "</h1>");
    }

    if (s2 == null)
      s2 = "WebFX - What you never thought possible!";
    
    if (op) {
      document.write("<span id='webfx-sub-title' style='top:46px;'>" + s2 + "</span>");
    }
    else {
      document.write("<span id='webfx-sub-title'>" + s2 + "</span>");
    }
  },
  writeMainTitle  :  function () {
    this.writeTitle("WebFX", "What you never thought possible!");  
  },
  writeTopMenuBar    :  function () {
    document.write("<div id='webfx-menu-bar-1'></div>");
    if (op) {
      document.write("<style>.webfx-menu-bar a {padding-top:3px;}</style>");
      document.write("<div id='webfx-menu-bar-2' style='height:2px;'></div>");
    }
    else
      document.write("<div id='webfx-menu-bar-2'></div>");
    document.write("<div id='webfx-menu-bar'>");// div is closed in writeBottomMenuBar
  },
  writeBottomMenuBar  :  function () {
    document.write("</div>");
    if (op)
      document.write("<div id='webfx-menu-bar-3' style='height:0px;'></div>");
    else
      document.write("<div id='webfx-menu-bar-3'></div>");
    document.write("<div id='webfx-menu-bar-4'></div>");
    document.write("<div id='webfx-menu-bar-5'></div>");
  },
  writeMenu      :  function () {
    this.writeTopMenuBar();
    //document.write(webfxMenuBar);
    
    this.writeBottomMenuBar();
  },
  writeDesignedByEdger  :  function () {
    if (ie && document.body.currentStyle.writingMode != null)
      document.write("<div id='webfx-about'></div>");
  }
};

if (ie && window.attachEvent) {
  window.attachEvent("onload", function () {
    var scrollBorderColor  =  "rgb(120,172,255)";
    var scrollFaceColor    =  "rgb(234,242,255)";
   /* with(document.body.style) {
      scrollbarDarkShadowColor  =  scrollBorderColor;
      scrollbar3dLightColor    =  scrollBorderColor;
      scrollbarArrowColor      =  "black";
      scrollbarBaseColor      =  scrollFaceColor;
      scrollbarFaceColor      =  scrollFaceColor;
      scrollbarHighlightColor    =  scrollFaceColor;
      scrollbarShadowColor    =  scrollFaceColor;
      scrollbarTrackColor      =  "white";
    }*/
  });
}

/* we also need some dummy constructors */
webfxMenuBar = {
  add : function () {}
};
function WebFXMenu() {
  this.add = function () {};
}
function WebFXMenuItem() {}
function WebFXMenuSeparator() {}
function WebFXMenuButton() {}

function fnChange()
	{
		var formobj=document.forms[0];		
		formobj.action="./getSupportManagerAction.do?param=getSupportManagerAction";		
		formobj.submit();
		
	}	
	function fnsubmit()
	{
		var formobj=document.forms[0];		
		formobj.action="./addApplication.do?param=Add";		
		formobj.submit();
	}
	function getApplicationSLADetails()
	{		
		var formobj=document.forms[0];		
		formobj.action="./getApplSLACreatedDetails.do?param=getSLAcreatedDetailsOfApplication";	
		//formobj.action="./getApplSLACreatedDetails.do?param=viewSLAcreatedDetailsOfApplication";	
		formobj.submit();
	}
	function displaySLADetails()
	{
		var criRes=document.getElementById('hiddenCriWarn').value
		var criWar=document.getElementById('hiddenCriWarn').value
		var majRes=document.getElementById('hiddenMajRes').value
		var majWar=document.getElementById('hiddenMajWarn').value
		var minRes=document.getElementById('hiddenMinRes').value
		var minWar=document.getElementById('hiddenMinWarn').value
		var details="Critical Response time= "+criRes+" hrs Critical warning Time= "+criWar+"hrs"
		confirm(details)
	}
