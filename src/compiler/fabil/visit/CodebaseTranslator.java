package fabil.visit;

import fabil.frontend.CodebaseSource;
import fabric.common.SysUtil;
import fabric.lang.Codebase;

import polyglot.ast.NodeFactory;
import polyglot.ast.SourceFile;
import polyglot.frontend.Job;
import polyglot.frontend.TargetFactory;
import polyglot.types.TypeSystem;
import polyglot.util.CodeWriter;
import polyglot.visit.Translator;

public class CodebaseTranslator extends Translator {

  public CodebaseTranslator(Job job, TypeSystem ts, NodeFactory nf,
      TargetFactory tf) {
    super(job, ts, nf, tf);
  }

  @Override
  protected void writeHeader(SourceFile sfn, CodeWriter w) {
    //The translator hasn't entered the scope of the file yet.
    Codebase cb = ((CodebaseSource) sfn.source()).codebase();
    if (sfn.package_() != null) {
      w.write("package ");
      w.write(SysUtil.packagePrefix(cb));
      sfn.package_().del().translate(w, this);
      w.write(";");
      w.newline(0);
      w.newline(0);
    } else {
      String cbName = SysUtil.packageName(cb);
      if(!cbName.equals("")) {
        w.write("package ");
        w.write(cbName);
        w.write(";");
        w.newline(0);
        w.newline(0);
      }
    }
    
    boolean newline = false;
    // No imports 
//    for (Iterator i = sfn.imports().iterator(); i.hasNext();) {
//      Import imp = (Import) i.next();
//      imp.del().translate(w, this);
//      newline = true;
//    }

    if (newline) {
      w.newline(0);
    }
  }
}
