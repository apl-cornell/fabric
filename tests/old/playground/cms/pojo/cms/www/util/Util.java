package cms.www.util;

import cms.www.AccessController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * Util: utilities that don't belong in any of the more specialized utility classes in this package.
 * Check those first before adding another method to Util.
 * 
 * @author surge, jon, Ray, Evan
 */
public class Util
{
  /**
   * @return A string containing all the string representations of elements in the collection,
   * separated by commas
   */
  public static String listElements(Collection c) {
    if (c == null) return "<null Collection>";
    Iterator i = c.iterator();
    String result = "";
    while (i.hasNext()) {
      result += i.next().toString();
      if (i.hasNext()) {
        result += ", ";
      }
    }
    return result;
  }
  public static String listElements(long[] c) {
    if (c == null) return "<null array>";
    String result = "";
    for (int i = 0; i < c.length - 1; i++)
      result += c[i] + ", ";
    return result + c[c.length - 1];
  }

  /**
   * @return A string containing the string representations of all distinct elements in the collection,
   * separated by commas
   */
  public static String listUniqueElements(Collection c)
  {
    if (c == null) return "<null Collection>";
    Set s = new HashSet();
    s.addAll(c);
    Iterator i = s.iterator();
    String result = "";
    while (i.hasNext()) {
      result += i.next().toString();
      if (i.hasNext()) {
        result += ", ";
      }
    }
    return result;
  }

  /**
   * Return true if they're equal by .equals() OR by both being null
   * @param one
   * @param two
   * @return
   */
  public static boolean equalNull(Comparable one, Comparable two) {
    if (one == null && two == null) return true;
    if (one == null && two != null) return false;
    if (one != null && two == null) return false;
    return one.equals(two);
  }

  /**
   * Parse a long (but return 0 if parse fails)
   */
  public static long parseLong(String s) {
    try {
      return Long.parseLong(s);
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  /**
   * Return a valid grade string if we can find one in the input string, otherwise null
   * @param grade
   * @return A String representing a grade
   */
  public static String validGrade(String grade) {
    String nospace = StringUtil.removeSpaces(grade);
    final String gradeStrings[] = new String[]
                                             {
        "A+", "A", "A-", "B+", "B", "B-",
        "C+", "C", "C-", "D+", "D", "D-",
        "F", "S", "U", "W", "INC", "AUD"
                                             };
    for (int i = 0; i < gradeStrings.length; i++)
      if (nospace.equalsIgnoreCase(gradeStrings[i]))
        return gradeStrings[i];
    return null;
  }

  /**
   * Look for a full semester name (eg "Spring 2017")
   * @param semesterName
   * @return
   */
  public static boolean isLegalSemesterName(String semesterName)
  {
    return semesterName.matches("(Spring|Summer|Fall) 20\\d\\d");
  }

}
