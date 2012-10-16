/***********************************************************************************
* cookies.js: the essential functions for dealing with cookies
***********************************************************************************/
/* code taken from http://www.webreference.com/js/column8/functions.html */

/*
   name - name of the cookie
   value - value of the cookie (default: "")
   [expires] - expiration date of the cookie as a Date object (default: 12/31/2037 16:00 GMT)
   (default occurs when null is passed in)
*/
function setCookie(name, value, expires)
{
	if (value == null) value = '';
	if (expires == null)
	{
		//I think this is a couple weeks before end of epoch -- Evan
		expires = new Date();
		expires.setYear(2037);
		expires.setMonth(11);
		expires.setDate(31);
		expires.setHours(16);
	}
	var path = '/';
  var curCookie = name + "=" + escape(value) +
      ((expires) ? "; expires=" + expires.toGMTString() : "") +
      ((path) ? "; path=" + path : "");
  document.cookie = curCookie;
}

/*
  name - name of the desired cookie
  return string containing value of specified cookie, or null if cookie does not exist
*/
function getCookie(name) {
  var dc = document.cookie;
  var prefix = name + "=";
  var begin = dc.indexOf("; " + prefix);
  if (begin == -1) {
    begin = dc.indexOf(prefix);
    if (begin != 0) return null;
  } else
    begin += 2;
  var end = document.cookie.indexOf(";", begin);
  if (end == -1)
    end = dc.length;
  return unescape(dc.substring(begin + prefix.length, end));
}

/*
   name - name of the cookie
   [path] - path of the cookie (must be same as path used to create cookie)
   [domain] - domain of the cookie (must be same as domain used to create cookie)
   -- path and domain default if assigned null or omitted if no explicit argument proceeds
*/
function deleteCookie(name, path, domain) {
  if (getCookie(name)) {
    document.cookie = name + "=" +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") +
    "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
}
