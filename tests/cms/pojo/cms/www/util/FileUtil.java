package cms.www.util;

/**
 * FileUtil: utilities for downloading files from users to the system and for
 * displaying files once on the server. This should be the only class used to
 * get paths for storing/reading files on the server. created 12 / 12 / 05
 * 
 * @author evan, ray, surge, jon
 */
public class FileUtil {
  /**
   * Some browsers will return an entire path name with the file name, so we
   * trim that here. For clarity: if the filename is 'abc.txt', the file title
   * is 'abc' and the extension is 'txt'
   * 
   * @return The filename (just title and extension)
   */
  public static String trimFilePath(String filename) {
    int idx1 = filename.lastIndexOf("/");
    int idx2 = filename.lastIndexOf("\\");
    int idx = idx1 > idx2 ? idx1 : idx2;
    
    if (idx > 0) filename = filename.substring(idx);
    return filename;
  }

  /**
   * Return the file title and extension, given the full name
   * 
   * @return A two-element array of Strings
   */
  public static String[] splitFileNameType(String filename) {
    int index = filename.lastIndexOf(".");
    if (index == -1)
      return new String[] { filename, "" };
    else
      return new String[] { filename.substring(0, index), filename.substring(index) };
  }
}
