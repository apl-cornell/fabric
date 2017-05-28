package cms.www.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Specify the various CSV formats parsed and/or written by the system.
 * A format is specified by the contents of its header line in order.
 * Parsed files are expected to have column headers that match those specified here
 * in spelling and capitalization (whitespace is allowed).
 *
 * @author evan
 * Created 5 / 17 / 06
 */
public class CSVFileFormatsUtil
{
	//the different pieces of info that can go in a column; NOT case-sensitive
	public static final String 
		NETID = "NetID", 
		LASTNAME = "Last Name", 
		FIRSTNAME = "First Name",
		CUID = "CUID",
		COLLEGE = "College",
		DEPARTMENT = "Department", //eg COM S
		COURSE_NUM = "Course Num", //eg 211
		COURSE_CODE = "Course Code", //eg COM S 211
		LECTURE = "Lecture", //eg 06
		SECTION = "Section",
		LAB = "Lab",
		CREDITS = "Credit Hours",
		GRADE_OPTION = "Grade Option",
		GRADE = "Grade",
		FINAL_GRADE = "Final Grade",
		TOTAL = "Total";
	
	//regular-expression strings defining various uploadable data
	public static final String
		netidRegexp = "[a-z]{1,3}[1-9][0-9]*",
		cuidRegexp = "[0-9]{6,9}", //6 for old, 7-9 for new IDs
		lecNoRegexp = "0*[0-9]+",
		gradeOptRegexp = "L|S|A|W", //letter or S/U or audit
		finalGradeRegexp = "((A|B|C|D)(\\+|-)?)|F|S|U|W|(INC)|(AUD)";
	
	//columns allowed in a flexible-format upload
	public static final ColumnInfo[] ALLOWED_COLUMNS = new ColumnInfo[]
	{
		new ColumnInfo(NETID, ColumnInfo.TYPE_STRING, netidRegexp, true, true),
		new ColumnInfo(LASTNAME, ColumnInfo.TYPE_STRING, null, true, false),
		new ColumnInfo(FIRSTNAME, ColumnInfo.TYPE_STRING, null, true, false),
		new ColumnInfo(CUID, ColumnInfo.TYPE_STRING, cuidRegexp, true, false),
		new ColumnInfo(COLLEGE, ColumnInfo.TYPE_STRING, null, true, true),
		new ColumnInfo(DEPARTMENT, ColumnInfo.TYPE_STRING, null, true, true),
		new ColumnInfo(COURSE_NUM, ColumnInfo.TYPE_INT, null, false, true),
		new ColumnInfo(LECTURE, ColumnInfo.TYPE_STRING, lecNoRegexp, false, true),
		new ColumnInfo(SECTION, ColumnInfo.TYPE_STRING, lecNoRegexp, false, true),
		new ColumnInfo(LAB, ColumnInfo.TYPE_STRING, lecNoRegexp, false, true),
		new ColumnInfo(CREDITS, ColumnInfo.TYPE_FLOAT, null, false, true),
		new ColumnInfo(GRADE, ColumnInfo.TYPE_FLOAT, null, false, true),
		new ColumnInfo(TOTAL, ColumnInfo.TYPE_FLOAT, null, false, true),
		new ColumnInfo(GRADE_OPTION, ColumnInfo.TYPE_STRING, gradeOptRegexp, false, true),
		new ColumnInfo(FINAL_GRADE, ColumnInfo.TYPE_STRING, finalGradeRegexp, false, true)
	};
	
	/* the formats we read */
	//classlist: add data to known students prior to submitting final grades
	//CLASSLIST_FORMAT = new String[] {DEPARTMENT, COURSE_NUM, LASTNAME, FIRSTNAME, CUID, SECTION, GRADE_OPTION, CREDITS},
	public static final String[] 
		CLASSLIST_FORMAT = new String[] {LASTNAME, FIRSTNAME, CUID, GRADE_OPTION, CREDITS},
		CUID_FORMAT = new String[] {NETID, CUID},
		GRADES_FORMAT_NOSUBPROBS = new String[] {NETID, GRADE},
		FINALGRADES_TEMPLATE_FORMAT = new String[] {NETID, FINAL_GRADE};
	
	/* the formats we write */
	//final grades: all data known about all students in the class
	public static final String[] 
		FINALGRADES_FORMAT = new String[] {COLLEGE, DEPARTMENT, COURSE_NUM, SECTION, FINAL_GRADE, LASTNAME, FIRSTNAME, CUID, NETID, GRADE_OPTION, CREDITS};
	
	/**
	 * Return the number of columns in the given format in a manner that doesn't depend
	 * on the representation of the format
	 */
	public static int getNumColumns(String[] format)
	{
		return format.length;
	}
	
	/**
	 * Return the column number corresponding, in a certain file format, to a given piece of information
	 * (or -1 if that info isn't included in the given format).
	 * See the lists above for the appropriate column names.
	 * @param columnName One of the constant string identifiers in CSVFileFormatsUtil
	 */
	public static int getColumnNumber(String[] format, String columnName)
	{
		for (int i = 0; i < format.length; i++)
			if (format[i].equalsIgnoreCase(columnName))
				return i;
		return -1;
	}
	
	/**
	 * Return the column header strings for the given format in order, separated by commas
	 * (suitable for outputting as the header line)
	 * @param format
	 * @return
	 */
	public static String getColumnListing(String[] format)
	{
		String str = format[0];
		for (int i = 1; i < format.length; i++)
			str += ", " + format[i];
		return str;
	}
	
	/**
	 * Return whether the unknown column name can be reasonably parsed as a name we understand
	 * (ie, to within capitalization and whitespace)
	 * @param name
	 * @param knownName
	 * @return boolean
	 */
	private static boolean nameApproximatelyMatches(String name, String knownName)
	{
		return name.replaceAll("\\s+", "").equalsIgnoreCase(knownName.replaceAll("\\s+", ""));
	}
	
	/**
	 * Take in a CSV header line and try to figure out which possible columns are mentioned and in what order
	 * @param line The result of a CSVParser getLine() call
	 * @return An array of indices into ALLOWED_COLUMNS, where each index is -1 if the provided string
	 * couldn't be recognized as any information we know
	 */
	public static int[] parseColumnNamesFlexibly(String[] line) throws CSVParseException {
		int[] indices = new int[line.length];
		HashMap seenIndices = new HashMap();
		for (int i = 0; i < line.length; i++) {
			indices[i] = -1;
			String name = line[i].trim();
			for (int j = 0; j < ALLOWED_COLUMNS.length; j++)
				if (nameApproximatelyMatches(name, ALLOWED_COLUMNS[j].getName())) {
					Integer key = new Integer(j);
					if (seenIndices.containsKey(key)) {
						throw new CSVParseException("Duplicate name detected (" + 
								name + ") in columns " +
								(((Integer)seenIndices.get(key)).intValue()+1) + " and " + (i+1));
					} else {
						seenIndices.put(key, new Integer(i));
					}
					indices[i] = j;
					break;
				}
		}
		return indices;
	}
	
	/**
	 * Take in the output of flexibly parsing a file and return the number of the column for
	 * colName, if there is one, or -1 if not or if colName isn't a valid flex-column name
	 * @param colsFound Output from parseColumnNamesFlexibly()
	 * @param colName One of the COLUMN_NAMES above (NETID etc)
	 * @return
	 */
	public static int getFlexibleColumnNum(int[] colsFound, String colName) {
		if (colsFound == null) return -1;
		int nameIndex = -2; //a value that should never be in colsFound
		//figure out which index we're looking for
		for (int i = 0; i < ALLOWED_COLUMNS.length; i++)
			if (ALLOWED_COLUMNS[i].getName().equals(colName))
			{
				nameIndex = i;
				break;
			}
		//look for the index
		for (int i = 0; i < colsFound.length; i++)
			if (colsFound[i] == nameIndex)
				return i;
		return -1;
	}
	
	
	/**
	 * Take in a CSV header line and try to figure out which possible columns are mentioned and in what order
	 * @param line The result of a CSVParser getLine() call
	 * @return An array of indices into ALLOWED_COLUMNS, where each index is -1 if the provided string
	 * couldn't be recognized as any information we know
	 */
	public static int[] parseSubProblemColumnNamesFlexibly(String[] line, Vector subProblemNames) throws CSVParseException {
		int[] indices = new int[subProblemNames.size()];
		HashMap seenIndices = new HashMap();
		for (int i = 0; i < subProblemNames.size(); i++) {
			indices[i] = -1;
			String name = line[i].trim();
			
			for (int j = 0; j < subProblemNames.size(); j++) {
				if (nameApproximatelyMatches(name, (String)subProblemNames.get(i))) {
					Integer key = new Integer(j);
					if (seenIndices.containsKey(key)) {
						throw new CSVParseException("Duplicate name detected (" + 
								subProblemNames.get(i) + ") in columns " +
								(((Integer)seenIndices.get(key)).intValue()+1) + " and " + (i+1));
					} else {
						seenIndices.put(key, new Integer(i));
					}
					indices[i] = j;
					break;
				}
			}
		}
		return indices;
	}
	
	/**
	 * Take in the output of flexibly parsing a file and return the number of the column for
	 * colName, if there is one, or -1 if not or if colName isn't a valid flex-column name
	 * @param colsFound Output from parseColumnNamesFlexibly()
	 * @param colName One of the COLUMN_NAMES above (NETID etc)
	 * @return
	 */
	public static int getSubProblemColumnNum(int[] colsFound, String colName, Vector subProblemNames) {
		if (colsFound == null) return -1;
		int nameIndex = -2; //a value that should never be in colsFound
		//figure out which index we're looking for
		for (int i = 0; i < subProblemNames.size(); i++)
			if (((String)subProblemNames.get(i)).equals(colName))
			{
				nameIndex = i;
				break;
			}
		//look for the index
		for (int i = 0; i < colsFound.length; i++)
			if (colsFound[i] == nameIndex)
				return i;
		return -1;
	}
	
	
	/**
	 * Take in the output of flexibly parsing a file and return whether it matches the specified format
	 */
	public static boolean headerLineMatchesFormat(int[] colsFound, String[] format)
	{
		if (colsFound.length != format.length) return false;
		for (int i = 0; i < colsFound.length; i++)
			if (ALLOWED_COLUMNS[colsFound[i]].getName() != format[i])
				return false;
		return true;
	}
	
	/**
	 * @return A List of Strings containing the names of all flexi-upload fields that are appropriate for course staff
	 * to see
	 */
	public static List getStaffAppropriateFlexibleColumnList()
	{
		List names = new ArrayList();
		for (int i = 0; i < ALLOWED_COLUMNS.length; i++)
			if (ALLOWED_COLUMNS[i].isForStaff())
				names.add(ALLOWED_COLUMNS[i].getName());
		return names;
	}
	/**
	 * @return A List of Strings containing the names of all flexi-upload fields that are appropriate for cmsadmins
	 * to see
	 */
	public static List getCMSAdminAppropriateFlexibleColumnList()
	{
		List names = new ArrayList();
		for (int i = 0; i < ALLOWED_COLUMNS.length; i++)
			if (ALLOWED_COLUMNS[i].isForAdmin())
				names.add(ALLOWED_COLUMNS[i].getName());
		return names;
	}
	
	/**
	 * Info about a column that can appear in a flexible csv upload:
	 * its (approximate) name, its datatype and which DB table it goes in
	 */
	public static class ColumnInfo
	{
		private String name;
		private int datatype;
		private String regexp; //if of string datatype, we might need to match a certain format
		private boolean forAdmin, forStaff; //whether the column should be used by each type of uploader
		
		//allowable datatypes
		public static final int TYPE_INT = 0, TYPE_FLOAT = 1, TYPE_STRING = 2;
		
		/**
		 * 
		 * @param name
		 * @param datatype
		 * @param regexp A regular expression if we're of type STRING and need to have a certain format; else null
		 * @param forAdmin
		 * @param forStaff
		 */
		public ColumnInfo(String name, int datatype, String regexp, boolean forAdmin, boolean forStaff)
		{
			this.name = name;
			this.datatype = datatype;
			this.regexp = regexp;
			this.forAdmin = forAdmin;
			this.forStaff = forStaff;
		}
		
		public String getName()
		{
			return name;
		}
		
		public int getDatatype()
		{
			return datatype;
		}
		
		public String getRegexp()
		{
			return regexp;
		}
		
		public boolean isForAdmin()
		{
			return forAdmin;
		}
		
		public boolean isForStaff()
		{
			return forStaff;
		}
	}
}
