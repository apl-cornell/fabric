/***********************************************************************************
* datetime.js: various date and time functions used throughout the web layer
***********************************************************************************/

/*
 * Return a Date whose hour/minute/second attributes correspond to time. These fields of the Date
 * will always be valid; none of its other fields will be. If time is not a valid time string, return null.
 *
 * whatField tells us what time we're parsing, so we can add errors to the global array. For example, 
 * whatField could be 'Due time'.
 *
 * time is a string or a String
 *
 * let T = {hh | hh:mm | hh:mm:ss}, where hh can be one digit.
 * We check for the following case-insensitive formats:
 * 
 * T   T a    T am    Ta    Tam
 */
function parseTime(time, whatField)
{
	var timeString = new String(time);
	var matchedInfo = /^\s*([0-2]?[0-9])(:([0-5][0-9])(:([0-5][0-9]))?)?\s?(((a|p)m?)|((A|P)M?))?\s*$/.exec(timeString);
	if (matchedInfo == null || matchedInfo == undefined) return null;
	var hours = null, mins = null, secs = null, ampm = null;
	if (matchedInfo[1] != undefined) hours = parseInt(matchedInfo[1]);
	if (matchedInfo[3] != undefined) mins = parseInt(matchedInfo[3]);
	if (matchedInfo[5] != undefined) secs = parseInt(matchedInfo[5]);
	if (matchedInfo[6] != undefined) ampm = matchedInfo[6];
	//check for incorrect formatting and put hours into the range 0-23
	if (hours == null || hours == undefined)
	{
		errors[e++] = whatField + " is not in proper format.";
		return null;
	}
	if (((hours < 1 || hours > 12) && ampm != null) //hours not in [1-12] but includes AM/PM
		|| hours > 23) //hours not in range
	{
		errors[e++] = whatField + " is not in proper format.";
		return null;
	}
	if (ampm == 'p' || ampm == 'P' || ampm == 'pm' || ampm == 'PM') hours = (hours + 12) % 24;
	var timeDate = new Date();
	//make sure we set hrs, mins, secs even if they weren't given in time
	timeDate.setHours(hours);
	if (mins != null) timeDate.setMinutes(mins);
	else timeDate.setMinutes(0);
	if (secs != null) timeDate.setSeconds(secs);
	else timeDate.setSeconds(0);
	return timeDate;
}

/*
return whether the first date is before the second

theDate is a Date object with year/month/day fields valid
theTime is a string with hours, maybe minutes and/or seconds and/or AM/PM
compareDate is a Date object with *all* fields valid
dateDesc is a string (first letter capitalized) describing theDate/theTime, eg 'Due time'
*/
function dateIsBeforeDateObj(theDate, theTime, compareDate, dateDesc)
{
	var timeDate = parseTime(theTime, dateDesc); //its hour, min, sec attributes are set
	if (timeDate == null) return false; //improper formatting; the subroutine stored an error
	theDate.setHours(timeDate.getHours());
	theDate.setMinutes(timeDate.getMinutes());
	theDate.setSeconds(timeDate.getSeconds());
//	alert('checking whether ' + formatDate(theDate, 'NNN dd, yyyy hh:mm:ss a') + ' is before ' + formatDate(compareDate, 'NNN dd, yyyy hh:mm:ss a'));
   return theDate.getTime() < compareDate.getTime();
}
/*
return whether the first date is before the second

theDate is a Date object with year/month/day fields valid
theTime is a string with hours, maybe minutes and/or seconds and/or AM/PM
dateDesc1 is a string (first letter capitalized) describing theDate/theTime, eg 'Due time'
compareDate, compareTime, dateDesc2 specify another date in the same way
*/
function dateIsBeforeFormInput(theDate, theTime, dateDesc1, compareDate, compareTime, dateDesc2)
{
	var timeDate = parseTime(compareTime, dateDesc2); //its hour, min, sec attributes are set
	if (timeDate == null) return false; //improper formatting; the subroutine stored an error
	compareDate.setHours(timeDate.getHours());
	compareDate.setMinutes(timeDate.getMinutes());
	compareDate.setSeconds(timeDate.getSeconds());
   return dateIsBeforeDateObj(theDate, theTime, compareDate, dateDesc1);
}
