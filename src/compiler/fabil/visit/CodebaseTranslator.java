package fabil.visit;

import java.net.URI;

import polyglot.ast.NodeFactory;
import polyglot.ast.SourceFile;
import polyglot.frontend.Job;
import polyglot.frontend.TargetFactory;
import polyglot.types.TypeSystem;
import polyglot.util.CodeWriter;
import polyglot.visit.Translator;
import codebases.frontend.CodebaseSource;
import codebases.frontend.ExtensionInfo;
import fabric.common.SysUtil;

public class CodebaseTranslator extends Translator {

  public CodebaseTranslator(Job job, TypeSystem ts, NodeFactory nf,
      TargetFactory tf) {
    super(job, ts, nf, tf);
  }

  @Override
  protected void writeHeader(SourceFile sfn, CodeWriter w) {
    // XXX: HACK -- The translator hasn't entered the scope of the file yet,
    // so we basically are inlining what translate() would do.
    URI ns = ((CodebaseSource) sfn.source()).canonicalNamespace();
    if (sfn.package_() != null) {
      w.write("package ");
      w.write(SysUtil.namespaceToPackageName(ns));
      w.write(".");
      sfn.package_().del().translate(w, this);
      w.write(";");
      w.newline(0);
      w.newline(0);
    } else {
      ExtensionInfo extInfo = (ExtensionInfo) job.extensionInfo();
      if (!ns.equals(extInfo.localNamespace())) {
        String pkgName = SysUtil.namespaceToPackageName(ns);
        w.write("package ");
        w.write(pkgName);
        w.write(";");
        w.newline(0);
        w.newline(0);
      }
    }

    //XXX: assuming fully_qualified_names is set, no imports are written
    // boolean newline = false;
    // // No imports
    // for (Iterator i = sfn.imports().iterator(); i.hasNext();) {
    // Import imp = (Import) i.next();
    // imp.del().translate(w, this);
    // newline = true;
    // }
    //
    // if (newline) {
    // w.newline(0);
    // }
  }
}
