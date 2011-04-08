package fabil.frontend;

import java.util.Date;

import polyglot.frontend.Source;
import fabric.lang.Codebase;

/**
 * This class is primarily for sources that are generated during translation,
 * such as *_JIF_IMPL classes.
 * 
 */
public class CodebaseSource_c extends Source implements CodebaseSource {

  protected Codebase codebase;
  protected boolean is_remote;
  
  public CodebaseSource_c(String name, String path, Date lastModified, Codebase codebase, boolean remote) {
    super(name, path, lastModified);
    this.codebase = codebase;
    this.is_remote = remote;
  }

  public Codebase codebase() {
    return codebase;
  }

  public boolean isRemote() {
    return is_remote;
  }
}
