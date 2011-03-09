package fabil.frontend;

import java.util.Date;

import fabil.Codebases;
import fabric.lang.Codebase;
import polyglot.frontend.Source;

/**
 * This class is primarily for sources that are generated during translation,
 * such as *_JIF_IMPL classes.
 * 
 */
public class CodebaseSource_c extends Source implements CodebaseSource {

  protected Codebase codebase;

  public CodebaseSource_c(String name, String path, Date lastModified, Codebase codebase) {
    super(name, path, lastModified);
    this.codebase = codebase;
  }

  public Codebase codebase() {
    return codebase;
  }

}
