package cms.www.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateTimeUtil: date/time formatting and parsing utilities
 * @author evan, ray, surge, jon
 * created 12 / 12 / 05
 */
public class DateTimeUtil
{
	public static final long
		SECS_PER_DAY= 3600*24,
		SECS_PER_WEEK= SECS_PER_DAY*7,
		SECS_PER_YEAR= SECS_PER_DAY*365;
	
	// used in formatDate()
	public static final SimpleDateFormat 
		NUMMONTH_DAY_TIME= new SimpleDateFormat("MM/d hh:mma"),
		SHORTMONTH_DAY= new SimpleDateFormat("MMM d"),
		MONTH_DAY_TIME= new SimpleDateFormat("MMMM d, hh:mma"),
		TIME_MONTH_DAY_YEAR= new SimpleDateFormat("hh:mma, MMMM d, yyyy"),
		DATE_TIME_NUMERIC = new SimpleDateFormat("EEE MM/d/yyyy hh:mm:ss a"), // Mon 1/1/2005 12:00:00 AM
		// Separates out the components
		DATE= new SimpleDateFormat("MMMM d, yyyy"), // January 1, 2005
		// For parsing
		DATE2= new SimpleDateFormat("MMMM d,yyyy"),
		DATE3= new SimpleDateFormat("MMMM d yyyy"),
		DATE4= new SimpleDateFormat("MMMM d"),
		TIME2= new SimpleDateFormat("hh"),
		YEAR= new SimpleDateFormat("yyyy"), // 2005
		TIME= new SimpleDateFormat("h:mm"), // 2:35
		AMPM= new SimpleDateFormat("a"), // PM
		DATE_TIME_AMPM= new SimpleDateFormat("MMMM d, yyyy hh:mma"), // the 3 together
	
		/* formats used in specific circumstances */
		//on the cms admin page, for the list of open assignments
		ADMIN_ASGN_DUEDATE_FORMAT = new SimpleDateFormat("EEE, MMM d, h:mm a"); //Wed, Jan 3, 4:53 pm
	
	/* "The Epoch", January 1, 1970 - make sure all dates are later than this
	 to prevent the SQL server from returning an error */
	public static final Date
		EPOCH= new Date(0);
	
	public static String monthFromInt(int month) {
		switch (month) {
		case 0: return "January";
		case 1: return "February";
		case 2: return "March";
		case 3: return "April";
		case 4: return "May";
		case 5: return "June";
		case 6: return "July";
		case 7: return "August";
		case 8: return "September";
		case 9: return "October";
		case 10: return "November";
		default: return "December";
		}
	}
	
	/**
	 * Parses a string into a Date.  If year is not specified, assume current year.
	 * @param str The string to parse
	 * @param format format to parse by
	 * @throws ParseException If the string cannot be parsed
	 * @return Date parsed from string
	 */
	public static Timestamp parseDate(String str, SimpleDateFormat format) throws ParseException {
		Date result;
	//	String input = str.replaceAll(" * ", " ");
		result= format.parse(str);
		if (format == SHORTMONTH_DAY || format == MONTH_DAY_TIME) {
			Calendar now= Calendar.getInstance();		// Current time
			Calendar then= Calendar.getInstance();
			then.setTime(result);						// Parsed date
			then.set(Calendar.YEAR, now.get(Calendar.YEAR));	// set parsed year to current
			result= new Date(then.getTimeInMillis());
		}
		return new Timestamp(result.getTime());
	}
	
	
	/**
	 * Parse a date string, time string, and AM/PM string into a Timestamp object.
	 * @param date "November 27, 2005"
	 * @param time "3:45"
	 * @param ampm "PM"
	 * @return A Timestamp representing "November 27, 2005, 3:45PM"
	 * @throws ParseException If any of the input strings are invalid
	 */
	public static Timestamp parseDate(String date, String time, String ampm) throws ParseException, IllegalArgumentException {
		// Remove extraneous spaces
	    String date_ = date.replaceAll(" * ", " ");
		String time_ = time.replaceAll(" *", "");
		Timestamp result;
		Date tmp = null;
		boolean noyear = false;
		int yearcheck;
		Calendar d = Calendar.getInstance(), d2 = Calendar.getInstance();
		try { tmp = DATE.parse(date_); } catch (ParseException e) {}
		if (tmp == null) {
		    try { tmp = DATE2.parse(date_); } catch (ParseException e) {}
		}
		if (tmp == null) {
		    try { tmp = DATE3.parse(date_); } catch (ParseException e) {}
		}
		if (tmp == null) {
		    try { tmp = DATE4.parse(date_); noyear = true;} catch (ParseException e) {}
		}
		if (tmp == null) throw new ParseException("Could not parse date", 0);
		d.setTime(tmp);
		if (noyear) {
		    d2.setTimeInMillis(System.currentTimeMillis());
		    yearcheck = d2.get(Calendar.MONTH) - d.get(Calendar.MONTH);
		    if (yearcheck < -6) {
		        d.set(Calendar.YEAR, d2.get(Calendar.YEAR) - 1);
		    } else if (yearcheck > 6) {
		        d.set(Calendar.YEAR, d2.get(Calendar.YEAR) + 1);
		    } else {
		        d.set(Calendar.YEAR, d2.get(Calendar.YEAR));
		    }
		}
		tmp = null;
		try { tmp = TIME.parse(time_); } catch (ParseException e) {}
		if (tmp == null) {
		    try {tmp = TIME2.parse(time_); } catch (ParseException e) {}
		}
		if (tmp == null) throw new ParseException("Could not parse date", 0);
		d2.setTime(tmp);
		d.set(Calendar.HOUR, d2.get(Calendar.HOUR));
		d.set(Calendar.MINUTE, d2.get(Calendar.MINUTE));
		d.set(Calendar.SECOND, d2.get(Calendar.SECOND));
		d.set(Calendar.AM_PM, ampm.equalsIgnoreCase("am") ? Calendar.AM : Calendar.PM);
	    //Date result= DATE_TIME_AMPM.parse(date + " " + time + ampm);
		result = new Timestamp(d.getTimeInMillis());
		if (result.before(EPOCH)) throw new IllegalArgumentException("must be later than January 1, 1970.");
		return result;
	}
	
	/**
	 * Parses dur as a long and converts it from minutes to seconds
	 * @param dur
	 * @return
	 * @throws ParseException
	 */
	public static long parseDuration(String dur) throws ParseException{
		long mins = Long.parseLong(dur);
		return mins * 60;
	}
	
	/**
	 * Retrieve the Year from a timestamp
	 * @param time
	 * @return
	 */
	public static String getYear(Timestamp time) {
	    return YEAR.format(time);
	}
	
	/**
	 * Formats a date countdown
	 * @param date Date to count from
	 * @param due date to count to
	 * @return A string representing the difference
	 */
	public static String formatCountdown(Date date, Date due) {
		long diff = due.getTime() - date.getTime(); //in milliseconds
		if (diff < 0) return SHORTMONTH_DAY.format(due);
		diff /= 1000; //ms -> sec
		diff /= 60; //sec -> min
		long minutes = diff % 60;
		diff /= 60; //min -> hrs
		long hours = diff % 24;
		diff /= 24; //hrs -> days
		long days = diff;
		String countdown = "";
		if (days > 0) countdown += days + " days, ";
		countdown += hours + " hours, " + minutes + " minutes";
		return countdown + " (" + SHORTMONTH_DAY.format(due) + ")";
	}
	
	public static String formatDate(Date date) {
	    return DATE_TIME_AMPM.format(date);
	}
}
