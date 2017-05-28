package cms.www.util;

import java.security.InvalidParameterException;
import fabric.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtil: web-related string format utilities
 * @author evan, ray, surge, jon
 * created 12 / 12 / 05
 */
public class StringUtil
{
	/**
	 * Return the group of alphanumeric tokens found in the input string
	 * @param str
	 * @return A List of Strings
	 */
	public static List parseList(String str) {
	    ArrayList result = new ArrayList();
	    if (str == null) {
	    	return result;
	    }
            char native[] chars = str.toCharArray();
	    String s = "";
	    for (int i=0; i < chars.length; i++) {
	        if (Character.isLetterOrDigit(chars[i])) {
	            s = s + chars[i];
	        } else if (s.length() > 0) {
	            result.add(s);
	            s = "";
	        }
	    }
	    if (s.length() > 0) {
	        result.add(s);
	    }
	    return result;
	}
	
	/**
	 * Return the NetIDs found in the input string, assuming at least semi-reasonable formatting
	 * @param list
	 * @return A List of Strings
	 * @throws InvalidParameterException if the input doesn't parse into a NetID list
	 */
	public static List parseNetIDList(String list) throws InvalidParameterException
	{
		ArrayList result = new ArrayList();
		//java regex madness: \A means beginning of input; \z means end of input
		Pattern netidlist = Pattern.compile("[^a-zA-Z0-9_]*([a-z]{2,3}[1-9][0-9]*)[^a-zA-Z0-9_]*");
		Matcher matcher = netidlist.matcher(list);
		int startIndex = 0;
		while(startIndex < list.length())
		{
			if (matcher.find(startIndex))
			{
				result.add(matcher.group(1).toLowerCase()); //group 0 is the entire match
				startIndex = matcher.end();
			}
			else throw new InvalidParameterException("StringUtil::parseNetIDList(): input does not parse!");
		}
		return result;
	}
	
	/**
	 * Replace characters of s beyond length with "..." if there's a space in s after length or more characters;
	 * otherwise return s
	 * @param s
	 * @param length
	 * @return
	 */
	public static String shortString(String s, int length) {
		int index = s.lastIndexOf(" ");
		if (index < length)
			return s;
		return s.substring(0, index) + " ...";
	}
	public static String shortString(String s) {
		return shortString(s, 300);
	}
	
	/**
	 * Ensures that no unexpected values are accepted, such as "9d"
	 * or "12f", which will be accepted by Float.parseFloat
	 * @return Returns the result of Float.parseFloat on success
	 * @throws NumberFormatException If value is not an acceptable number
	 */
	public static float parseFloat(String f) throws NumberFormatException {
		for (int i=0; i < f.length(); i++) {
			if (Character.isLetter(f.charAt(i))) {
				throw new NumberFormatException();
			}
		}
		return Float.parseFloat(f);
	}

	public static boolean isAlphaNumeric(String str) {
                char native[] chars = str.toCharArray();
		boolean result = true;
		for (int i=0; i < chars.length; i++) {
			result = Character.isLetterOrDigit(chars[i]);
			if (!result) return false;
		}
		return result;
	}
	
	/**
	 * Return a string of the format "97 64 32" where the input is, in this case, the three chars
	 * with ASCII values 97, 64 and 32
	 * (meant for debugging javascript string handling)
	 * @param s
	 * @return A string with each character replaced by a string representation of its ASCII value
	 */
	public static String charsToASCII(String s)
	{
		String ascii = "";
		for (int i = 0; i < s.length(); i++)
			ascii += new Integer(s.charAt(i)).toString() + " ";
		return ascii;
	}
	
	public static String removeSpaces(String spaced) {
	    String nospaces = "";
	    int index = 0, start = 0;
	    while ((index = spaced.indexOf(' ', start)) >= 0) {
	        nospaces += spaced.substring(start, index);
	        start = index + 1;
	    }
	    nospaces += spaced.substring(start);
	    return nospaces;
	}
	
	/**
	 * Formats a string taken from the web so that:
	 *  - Newliines ('\n') are converted to HTML line breaks (&lt;br&gt;)
	 *  - brackets (&lt; & &gt;) are converted to their HTML escape sequences.
	 * 		This ensures that no HTML tags can be displayed in user-defined text,
	 * 		preserving page layout
	 * @param input Text to format
	 * @return Formatted text
	 */
	public static String formatWebString(String input) {
		if (input == null) return null;
		StringBuffer buff= new StringBuffer(input);
		for (int i= 0; i != buff.length(); i++) {
			switch (buff.charAt(i)) {
				case '\n':
					// strip leading and trailing newlines
					if (i == 0 || i == buff.length() - 1) {
						buff.replace(i, i+1, "");
						i--;
					} else {
						buff.replace(i, i+1, "<br>");
						i+= 3;
					}
					break;
					/* currently we DON'T remove HTML tags
				case '<':
					buff.replace(i, i+1, "&lt;");
					i+= 3;
					break;
				case '>':
					buff.replace(i, i+1, "&gt;");
					i+= 3;
					break;
				case '�':
				    buff.replace(i, i+1, "&raquo;");
				    i+= 7;
				    break;
				    */
			}
		}
		return buff.toString();
	}
	
	/**
	 * Format a string that may contain html tags so that it can be displayed normally on a JSP,
	 * ie so the angle brackets show up as such
	 * @param input
	 * @return
	 */
	public static String formatNoHTMLString(String input) {
		if (input == null) return null;
		StringBuffer buff= new StringBuffer(input);
		for (int i= 0; i != buff.length(); i++) {
			switch (buff.charAt(i)) {
				case '\n':
					// strip leading and trailing newlines
					if (i == 0 || i == buff.length() - 1) {
						buff.replace(i, i+1, "");
						i--;
					} else {
						buff.replace(i, i+1, "<br>");
						i+= 3;
					}
					break;
				case '\r': //newline is \n\r in Windows
					buff.deleteCharAt(i);
					break;
				case '<':
					buff.replace(i, i+1, "&lt;");
					i+= 3;
					break;
				case '>':
					buff.replace(i, i+1, "&gt;");
					i+= 3;
					break;
		/*		case '�':
				    buff.replace(i, i+1, "&raquo;");
				    i+= 7;
				    break;
	    */
			}
		}
		return buff.toString();
	}
	
	/**
	 * Replace occurrences of the single-quote character ' with the sequence \\'
	 * and of the double-quote character " with the sequence \\" in the string
	 * @param in
	 * @return The String with quotes escaped
	 */
	public static String escapeQuote(String in) {
	    String out = "";
	    for (int i=0; i < in.length(); i++) {
	        if (in.charAt(i) == '\'' || in.charAt(i) == '\"') {
	            out += "\\" + in.charAt(i);
	        } else {
	            out += String.valueOf(in.charAt(i));
	        }
	    }
	    return out;
	}
	
	/**
	 *  Given a String representation of a floating point number,
	 * 	produces at most one-decimal place floating point number 
	 *  represented as a String
	 *  valid input forms are:  dddd.dddd  ,  dddd.   , dddd
	 * 	examples: 	10.563 -> 10.6
	 * 				10.98 -> 11
	 * 				10.0 -> 10
	 */
	public static String roundToOne(String totalscore) {
		int decimal=totalscore.indexOf('.');
		int n= totalscore.length();
		if (decimal==-1) {
			// no decimal necessary, do nothing
			return totalscore;
		}
		else if (decimal == n-1) {
			// decimal appears as the last character, remove it
			return totalscore.substring(0, n-1);
		}
		else if (decimal == n-2) {
			// truncate x.0 to x, otherwise leave it alone
			if (totalscore.charAt(n-1) == '0') {
				return totalscore.substring(0, n-2);
			}
			return totalscore;
		} else {
			int integerPart= Integer.parseInt(totalscore.substring(0,decimal)) ;
		    int secondDecimal= ((int) totalscore.charAt(decimal+2))-(int) '0';
		    int firstDecimal= ((int) totalscore.charAt(decimal+1))-(int) '0';
		    if (secondDecimal>=5) {
		       firstDecimal++;
			    if (firstDecimal==10) {
			    	firstDecimal=0;
			    	integerPart++;
			    }
		    }   
		   // if firstDecimal ends up being 0, truncate it
		   return integerPart + (firstDecimal == 0 ? "" : "." + firstDecimal);
		}	
	}
	
	/**
	 * Trim trailing zero from float to string translation
	 * @param score The score string
	 * @return The trimmed string
	 */
	public static String trimTrailingZero(String score) {
		if (score.endsWith(".0")) {
			return score.substring(0, score.length() - 2);
		}
		return score;
	}
	
}
