function addEvent( emt, evt, fnc, bbl)
{
	if( 'attachEvent' in emt) emt.attachEvent( 'on' + evt, fnc); //4 MSIE et test avec in
	else if( 'addEventListener' in emt) emt.addEventListener( evt, fnc, bbl); //4 ECMA ex: MFF et test d'existence non false
}

function stopEvent( event)
{
	//Pour IE
	event.cancelBubble = true;
	event.returnValue = false;

	//Pour les autres
	if( event.stopPropagation)
	{
		event.stopPropagation();
		event.preventDefault();
	}

}

function e$(id) {
	return document.getElementById(id);
}

function getParent(event) {
    if (event.target) return event.target ;
    else return event.srcElement;
}

function getRequester()
{
	var xhr = null;

// Pour MSIE
/*@cc_on @*/ // active les commentaires conditionnels sur MSIE
/*@if( @_jscript_version >= 5)
	try
	{
		xhr = new ActiveXObject( 'Msxml2.XMLHTTP');
		//ou 'MSXML2.XMLHTTP.3.0' 'MSXML3.XMLHTTP'
	}
	catch( e)
	{	//imbriqu�s pour gagner en temps d'ex�cution
		try
		{
			xhr = new ActiveXObject( 'Microsoft.XMLHTTP');
		}
		catch( E){}
	}
@end @*/
/*@cc_off@*/

//pour MFF...
	if( !xhr && 'undefined' != typeof XMLHttpRequest)
	{
		try
		{
			xhr = new XMLHttpRequest();
		}
		catch( e){}
	}

	return xhr;
}

// fonction ajax(url, vars, success, beforeSend)
function ajax( url, params)
{
	if (null == params) params = {};
	var _success = params.success || window.alert;
	var _method = params.method || 'GET' ;
	var _async = params.async || true ;
	var _vars = params.vars || null;
	var _beforeSend = params.beforeSend || function(){} ;
	var _afterSend = params.afterSend || function(){};
	var _format = params.format || 'text';
	var _receiving = params.receiving || function(){};
	var _complete = params.complete || function(){};
	var _error = params.error || function(){};

	var xhr = getRequester();

	// Pr�parer l'URL selon le mode
	if('POST' == _method)
	{
		xhr.open( _method, url, _async);  // true pour pr�ciser le mode asynchrone, false pour le mode synchrone
		xhr.setRequestHeader( 'Content-Type', 'application/x-www-form-urlencoded');
	} else {
		xhr.open( _method, url+'?'+_vars, _async);  // true pour pr�ciser le mode asynchrone, false pour le mode synchrone
		_vars = null;
	}

	// D�but de la requ�te
	_beforeSend();


	xhr.onreadystatechange = function()
	{
		/*
		if (0== xhr.readyState) alert("Cr��"); // Impossible car d�j� chang� donc commence � 1
		// d�but d'envoi
		if (1== xhr.readyState) alert("Pr�par�"); // Fonctionne pas sur Chrome
		*/
		// D�but de r�ception
		if (2== xhr.readyState) {
			//alert("Envoy�");
			_receiving();
		}

		// Fin de r�ception
		if (3== xhr.readyState) {
			//alert("R�ception");
			_complete();
		}

		if( 4 == xhr.readyState && (200 == xhr.status || 0 == xhr.status)) //d�sactiv� pour les tests locaux (pas de status de r�ponse
		{
			//if( xhr.responseXML)
			if('xml' == _format) {
				var _response = xhr.responseXML;
				if (_response) {
					_success(_response, params.params);
				} else {
					// Message d'erreur personalis�
					_error(10001, 'Format XML non valide');
				}
			} else {
				if( xhr.responseText)
				{
					//callbackFunction( xhr.responseXML);
					_success( xhr.responseText, params.params);
					_afterSend();
				}
			}
		} else 	if( 4 == xhr.readyState && (200 != xhr.status && 0 != xhr.status)) //d�sactiv� pour les tests locaux (pas de status de r�ponse
		{
			_error(xhr.status, xhr.statusText);
		}
	};

	xhr.send( _vars);
}