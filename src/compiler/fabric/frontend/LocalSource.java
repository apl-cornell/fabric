package fabric.frontend;

import java.io.File;
import java.io.IOException;

import fabil.Codebases;
import fabric.lang.Codebase;

import jif.parse.UTF8FileSource;

public class LocalSource extends UTF8FileSource implements Codebases {

  protected Codebase codebase;
  
  public LocalSource(File f, boolean userSpecified, Codebase codebase)
      throws IOException {
    super(f, userSpecified, null);
    this.codebase = codebase;
  }

  public Codebase codebase() {
    return codebase;
  }

}
