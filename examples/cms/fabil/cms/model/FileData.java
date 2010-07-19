package cms.model;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import fabric.worker.Worker;

/**
 * The File class encapsulates storage of file data. Files may be implemented as
 * CLOB/BLOBs in the database, as Filesystem files, or as fabric files.  This
 * class implements them using filesystem files. 
 * 
 * @author mdgeorge
 */
public class FileData {

  //////////////////////////////////////////////////////////////////////////////
  // private members                                                          //
  //////////////////////////////////////////////////////////////////////////////

  private byte[] file;
  private String name;
  private fabric.lang.security.Label dlabel;
  private LocalStore localStore;
  
  //////////////////////////////////////////////////////////////////////////////
  // public setters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public void setName (final String name) { this.name = name; }

  //////////////////////////////////////////////////////////////////////////////
  // public getters                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public String getName() { return this.name; }

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////

  public FileData(String name) {
    this.name = name;
    this.state = new Initialized();
    this.localStore = Worker.getWorker().getLocalStore();
    this.dlabel = localStore.getEmptyLabel();
  }

  //////////////////////////////////////////////////////////////////////////////
  // public methods                                                           //
  //////////////////////////////////////////////////////////////////////////////

  public InputStream read() {
    return null; //XXX //new ByteArrayInputStream(file);
  }

  /**
   * Create and return a new output stream that allows writing to this File.
   * The caller is responsible for closing the stream.
   */
  public OutputStream write() throws IOException {
    return state.write();
  }
  
  /**
   * Computes the md5 hash of the file
   */
  public String getMD5() {
    try {
      return state.md5();
    } catch (IOException e) {
      return "(none)";
    }
  }

  /**
   * Converts the file size in bytes to a String representation that will display
   * kilobytes or megabytes where applicable.
   * 
   * @return A String of the form "X bytes", "X kB", or "X MB"
   */
  public String formatFileSize() {
    
    long fileSize = file.length;
    int megSize = 1048576, kiloSize = 1024;
    
    String result = null;
    float num;
    if (fileSize > megSize) {
      num = ((float) fileSize) / ((float) megSize);
    } else if (fileSize > kiloSize) {
      num = ((float) fileSize) / ((float) kiloSize);
    } else {
      return fileSize + " bytes";
    }
    String strNum = Float.toString(num);
    result = (strNum.length() > 5) ? strNum.substring(0, 5) : strNum;
    if (strNum.charAt(strNum.length() - 1) == '.')
      strNum = strNum.substring(0, strNum.length() - 1);
    result = (fileSize > megSize) ? (result + " MB") : (result + " kB");
    return result;
  }
  
  public long getSize() {
    return file.length;
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // State Mangagement (state design pattern)                                 //
  //////////////////////////////////////////////////////////////////////////////
  
  // We use the state design pattern to manage the md5 computations and the
  // inputting of data to the file.  We have the following state transition
  // diagram:
  //
  // 
  //                                                                    
  //       +-------------+   write    +----------+     md5     +---------+
  //       | Initialized |  ------->  | Writing  |   ------->  | Written |
  //       |             |            | (stream) |  <-------   | (md5)   |
  //       +-------------+            +----------+    write    +---------+
  //          |     ^                   |     ^                 |     ^
  //          |     |                   |     |                 |     |
  //           -----                     -----                   ----- 
  //            md5                      write                    md5  
  //         

  private State state; 
  
  private interface State {
    public String       md5()   throws IOException;
    public OutputStream write() throws IOException;
  }
  
  private final class Initialized implements State {
    public String md5() throws IOException {
      throw new IOException("File not yet written");
    }
    
    public OutputStream write() throws IOException {
      Writing newState = new Writing();
      state = newState;
      return newState.stream;
    }
  }
  
  private final class Writing implements State {
    public final OutputStream  stream;
    public final ByteArrayOutputStream data;
    public final MessageDigest digest;
    
    public Writing() throws IOException {
      try {
        digest = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException e) {
        throw new IOException("Could not create MD5 Computation");
      }
      data   = new ByteArrayOutputStream();
      stream = new BufferedOutputStream(new DigestOutputStream(data, digest));
    }
    
    public String md5() throws IOException {
      file = new byte[0]; //XXX //data.toByteArray();
      stream.close();
      
      String md5 = new String(digest.digest());
      
      state = new Written(md5);
      return md5;
    }
    
    public OutputStream write() throws IOException {
      stream.close();
      
      Writing newState = new Writing();
      state = newState;
      return newState.stream;
    }
  }
  
  private final class Written implements State {
    private final String md5;
    
    public Written(String md5) {
      this.md5 = md5;
    }
    
    public String md5() {
      return md5;
    }

    public OutputStream write() throws IOException {
      Writing newState = new Writing();
      state = newState;
      return newState.stream;
    }
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
