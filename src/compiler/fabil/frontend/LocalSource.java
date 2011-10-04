package fabil.frontend;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import polyglot.frontend.FileSource;
import fabric.lang.Codebase;

public class LocalSource extends FileSource implements CodebaseSource {
  protected Codebase codebase;

  public LocalSource(File f, boolean userSpecified, Codebase codebase)
      throws IOException {
    super(f, userSpecified);
    this.codebase = codebase;
  }
  public LocalSource(String path, String name, Date lastModified, boolean userSpecified, Codebase codebase) throws IOException {
    super(path, name, lastModified, userSpecified);
    this.codebase = codebase;    
  }       

  public Codebase codebase() {
    return codebase;
  }

  public boolean isRemote() {
    return false;
  }

}
