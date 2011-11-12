package cms.www.util;

import java.io.File;

/**
 * @author rd94
 * File to send to the user.
 */
public class DownloadFile extends File {

    private String downloadName;  //the user-level identifier for this file
    
    /**
     * @param path The path to the file's parent directory
     * @param filename File title and extension
     */
    public DownloadFile(String path, String filename) {
        super(path, filename);
        this.downloadName= getName();
    }
    
    /**
     * @param path The path to the file's parent directory
     * @param filename File title and extension
     * @param downloadName The string shown to the user to identify this file
     */
    public DownloadFile(String path, String filename, String downloadName) {
        super(path, filename);
        this.downloadName = downloadName;
    }
    
    /**
     * @return The string shown to the user to identify this file
     */
    public String getDownloadName() {
        return downloadName;
    }

}
