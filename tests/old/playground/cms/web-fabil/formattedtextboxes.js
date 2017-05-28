/**************************************************************************************************
* formattedtextboxes.js: Javascript checks for various formattings of textbox contents
**************************************************************************************************/

/****************************************************************************************
* keep track of textboxes to check for specific formats, and check them on request
****************************************************************************************/

var integerTextboxList = new Array(); //textboxes that must contain integers (negative, zero or positive)
var dateTextboxList = new Array(); //textboxes that must contain dates (month, day, year; any decent format)
var timeTextboxList = new Array(); //textboxes that must contain times (8, 8:00, 8:32, 8:32a, 8 AM, ...)

/*
id: string, element ID
*/
function registerIntegerFormatTextbox(id)
{
	integerTextboxList[integerTextboxList.length] = id;
}
function registerDateFormatTextbox(id)
{
	dateTextboxList[dateTextboxList.length] = id;
}
function registerTimeFormatTextbox(id)
{
	timeTextboxList[timeTextboxList.length] = id;
}

/*
return whether the input string is formatted as an integer (in -inf ... inf)

id references the element with which we're concerned
*/
function stringIsInteger(str, id)
{
	if (str.length == 0) return true; //empty means no data
	var i = 0;
	if (str.charAt(0) == '-') i = 1;
	for (i; i < str.length; i++)
		if (str.charCodeAt(i) < 48 || str.charCodeAt(i) > 57)
			return false;
	return true;
}
/*
return whether the input string is formatted as a date

id references the element with which we're concerned
*/
function stringIsDate(str, id)
{
	if (str.length == 0) return true; //empty means no data
	//parseDate() is in CalendarPopup.js
	var date = parseDate(str); //date is a Date object
	if (date != null)
	{
		//in case the date is in a format parsable by our Javascript but not our Java code
		getElementById(id).value = formatDate(date, 'MMM dd, yyyy');
		return true;
	}
	else return false;
}
/*
return whether the input string is formatted as a time

id references the element with which we're concerned
*/
function stringIsTime(str, id)
{
	if (str.length == 0) return true; //empty means no data
	//parseTime() is in datetime.js
	var time = parseTime(str); //time is a Date object
	return (time != null);
}

/*
return whether all required boxes did indeed pass their format tests;
if any don't, highlight them and alert the user

boxList is one of the lists above of textboxes that have been registered
testFunc is one of the functions above to validate format
highlightColor is the color to highlight non-validating textboxes, in string form, eg '#ab1a2a'
colorName is a short string with the name of the color
formatDesc is a short string (first letter lowercase) describing the format
*/
function validateAllBoxesOfOneType(boxList, testFunc, highlightColor, colorName, formatDesc)
{
	var errors = 0;
	for (var i = 0; i < boxList.length; i++)
	{
		var element = getElementById(boxList[i]);
		if (element && !testFunc(element.value, boxList[i]))
		{
			errors++;
			element.style.borderWidth = '4px';
			element.style.borderColor = highlightColor;
		}
		else //make the border go back to normal on submission after it's fixed
		{
			element.style.borderWidth = '';
			element.style.borderColor = '';
		}
	}
	if (errors > 0)
	{
		if (errors == 1) alert('Textbox highlighted in ' + colorName + ' must have ' + formatDesc + ' format.');
		else alert('Textboxes highlighted in ' + colorName + ' must have ' + formatDesc + ' format.');
		return false;
	}
	else return true;
}

/*
to be called when a form with formatted textboxes is about to be submitted

return whether all registered boxes with valid element IDs passed their tests;
if any don't, highlight them and alert the user (this is done by our aux routines)
*/
function validateAllFormattedTextboxes()
{
	return validateAllBoxesOfOneType(integerTextboxList, stringIsInteger, '#ab1a2a', 'red', 'integer')
		&& validateAllBoxesOfOneType(dateTextboxList, stringIsDate, '#1aab2a', 'green', 'date')
		&& validateAllBoxesOfOneType(timeTextboxList, stringIsTime, '#eeaa44', 'orange', 'time');
}
