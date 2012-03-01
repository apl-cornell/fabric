package cms.www.util;

import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import com.Ostermiller.util.MD5;

import cms.www.AccessController;


/**
 * FileUtil: utilities for downloading files from users to the system and for
 * displaying files once on the server. This should be the only class used to
 * get paths for storing/reading files on the server. created 12 / 12 / 05
 * 
 * @author evan, ray, surge, jon
 */
public class FileUtil
{
        public static final char 
                SYS_SLASH = java.io.File.separatorChar, //the (system-specific) directory separator
                DB_SLASH = '*'; //the directory separator character used in the database
        private static final char S = SYS_SLASH;
        
        public static final String
        //there may need to be symlinks here
                //SYSTEM_HOME_DIR = "/usr/www/Courses"; //prod-server home
                SYSTEM_HOME_DIR = System.getProperty("user.home") + S + "CMS_Files"; //dev-server home
        
        public static final String 
                TEMP_DIR = System.getProperty("java.io.tmpdir");
        
        //the text of an error thrown by some third-party library or other; we compare to this to get error type
        public static final String      SIZE_VIOLATION = "the request was rejected because it's size exceeds allowed range";
        
        /**
         * Return a path to a file used in a category cell
         * @param courseID Note this shouldn't be necessary; category IDs are course-independent now
         * @param categoryID
         * @param ctgRow
         * @param ctgCol
         * @param filename Title plus extension
         * @return A system-dependent, fully qualified path
         */
        public static String getCategoryFileSystemPath(long courseID, long categoryID, long ctgRow, long ctgCol, 
                                                                                                                                        long ctgFileCount, String filename)
        {
        /* planned future path
                return SYSTEM_HOME_DIR + S + "CtgContentFiles" + S + courseID + S + categoryID + S + ctgRow 
                                        + S + ctgCol + S /*+ cellFileCount + S* + filename;
        */
                return SYSTEM_HOME_DIR + S + "CtgContentFiles" + S + courseID + S + categoryID + S + ctgFileCount 
                                        + S + filename;
        }
        
        /**
         * Return a path to an assignment file (instructions or related code)
         * @return A system-dependent, fully qualified path
         */
        public static String getAssignmentFileSystemPath(long semesterID, long courseID, long assignmentID, 
                                                                                                                                                long courseFileCount, String filename)
        {
        /* planned future path
                return SYSTEM_HOME_DIR + S + "AssignmentFiles" + S + "instructions" + S + courseID + S + assignmentID 
                                        + S +  + filename;
        */
                return SYSTEM_HOME_DIR + S + "AssignmentFiles" + S + semesterID + S + courseID + S + courseFileCount 
                                        + S + filename;
        }
        
        /**
         * Return a path to an assignment solution file
         * @return A system-dependent, fully qualified path
         */
        public static String getSolutionFileSystemPath(long semesterID, long courseID, long assignmentID, 
                                                                                                                                        long courseFileCount, String filename)
        {
        /* planned future path
                return SYSTEM_HOME_DIR + S + "AssignmentFiles" + S + "solutions" + S + courseID + S + assignmentID 
                                        + S +  + filename;
        */
                return SYSTEM_HOME_DIR + S + "SolutionFiles" + S + semesterID + S + courseID + S + courseFileCount 
                                        + S + filename;
        }
        
        /**
         * Return a path to a submitted file
         * @return A system-dependent, fully qualified path
         */
        public static String getSubmittedFileSystemPath(long courseID, long assignmentID, long originalGroupID, 
                                                                                                                                        long groupFileCount, long submissionID, String fileExtension)
        {
                return SYSTEM_HOME_DIR + S + "Submissions" + S + courseID + S + assignmentID + S + originalGroupID 
                                        + S + groupFileCount + S + submissionID + (fileExtension == "" ? "" : "." + fileExtension);
        }
        
        /**
         * Return a path to a comment or regrade file
         * @return A system-dependent, fully qualified path
         */
        public static String getCommentFileSystemPath(long courseID, long assignmentID, long groupID, 
                                                                                                                                        long groupFileCount, String filename)
        {
                return SYSTEM_HOME_DIR + S + "CommentFiles" + S + courseID + S + assignmentID + S + groupID
                                        + S + groupFileCount + S + filename;
        }
        
        /** TODO this function isn't used yet, as we store e-mail texts in the db
         * Return a path to the text of an e-mail sent by the system
         * @param emailID
         * @return A system-dependent, fully qualified path
         *
        public static String getEmailFileSystemPath(long emailID)
        {
        /* planned future path
                return SYSTEM_HOME_DIR + S + "EmailFiles" + S + emailID + S + "message.txt";
        }
        */
        
        /**
         * TODO stop storing full paths in the db, so we won't need this function
         * @param dbPath A path read from the database
         * @return Returns the inputted path translated to the format used on the file system
         */
        public static String translateDBPath(String dbPath) {
                //we might get a null when we're called from a CategoryFileBean that doesn't have a file, just a label
                return (dbPath == null) ? null : dbPath.replace(DB_SLASH, SYS_SLASH);
        }
        
        /**
         * TODO stop storing full paths in the db, so we won't need this function
         * @param sysPath A path on the file system
         * @return Returns the inputted path translated to the format used for storing paths in the database
         */
        public static String translateSysPath(String sysPath) {
                //we might get a null when we're called from a CategoryFileBean that doesn't have a file, just a label
                return (sysPath == null) ? null : sysPath.replace(SYS_SLASH, DB_SLASH);
        }
        
        public static String checkFileException(FileUploadException e)
        {
                if (e.getMessage().equalsIgnoreCase(SIZE_VIOLATION))
                return "An uploaded file violated the CMS maximum file size of " + AccessController.maxFileSize + " bytes";
                return e.getMessage();
        }
        
        /**
         * Some browsers will return an entire path name with the file name, so we trim that here.
         * For clarity: if the filename is 'abc.txt', the file title is 'abc' and the extension is 'txt'
         * @return The filename (just title and extension)
         */
        public static String trimFilePath(String filename) {
                return filename.substring(filename.lastIndexOf(SYS_SLASH) + 1);
        }
        
        /**
         * Return the file title and extension, given the full name
         * @return A two-element array of Strings
         */
        public static String[] splitFileNameType(String filename) {
                int index = filename.lastIndexOf(".");
                if (index == -1) return new String[] {filename, ""};
                else return new String[] {filename.substring(0, index), filename.substring(index + 1)};
        }
        
        /**
         * Calculates a MD5 hash value given a file
         * @param file The file to hash
         * @return A String representation of the hash value, or null if the hashing failed
         */
        public static String calcMD5(FileItem file)
        {
                byte buf[] = new byte[1024];
                String result = null;
                int rc;
                MD5 md = new MD5();
                try {
                        InputStream in = file.getInputStream();
                        while ((rc = in.read(buf, 0, 1024)) > 0) {
                                md.update(buf, rc);
                        }
                        result = md.getHashString();
                }
                catch (Exception ex) {
                        result = null;
                        ex.printStackTrace();
                }
                return result;
        }
        
        /**
         * Converts a file size in bytes to a String representation that will display
         * kilobytes or megabytes where applicable.
         * @param fileSize The size of the file in bytes
         * @return A String of the form "X bytes", "X kB", or "X MB"
         */
        public static String formatFileSize(int fileSize) {
                int megSize = 1048576, kiloSize = 1024;
                String result = null;
                float num;
                if (fileSize > megSize) {
                        num = ((float) fileSize) / ((float) megSize);
                }
                else if (fileSize > kiloSize) {
                        num = ((float) fileSize) / ((float) kiloSize);
                }
                else {
                        return fileSize + " bytes";
                }
                String strNum = Float.toString(num);
                result = (strNum.length() > 5) ? strNum.substring(0, 5) : strNum;
                if (strNum.charAt(strNum.length() - 1) == '.')
                        strNum = strNum.substring(0, strNum.length() - 1);
                result = (fileSize > megSize) ? (result + " MB") : (result + " kB");
                return result;
        }
}
