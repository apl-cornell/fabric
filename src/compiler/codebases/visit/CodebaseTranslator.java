package codebases.visit;

import polyglot.ast.Import;
import polyglot.ast.NodeFactory;
import polyglot.ast.SourceFile;
import polyglot.frontend.Job;
import polyglot.frontend.TargetFactory;
import polyglot.types.TypeSystem;
import polyglot.util.CodeWriter;
import polyglot.visit.Translator;
import codebases.frontend.CodebaseSource;
import codebases.frontend.ExtensionInfo;
import fabric.common.FabricLocation;

public class CodebaseTranslator extends Translator {
  protected final ExtensionInfo extInfo;

  public CodebaseTranslator(Job job, TypeSystem ts, NodeFactory nf,
      TargetFactory tf) {
    super(job, ts, nf, tf);
    this.extInfo = (ExtensionInfo) job.extensionInfo();
  }

  @Override
  protected void writeHeader(SourceFile sfn, CodeWriter w) {
    // XXX: HACK -- The translator hasn't entered the scope of the file yet,
    // so we basically are inlining what translate() would do.
    FabricLocation ns = ((CodebaseSource) sfn.source()).canonicalNamespace();
    if (sfn.package_() != null) {
      w.write("package ");
      w.write(extInfo.namespaceToJavaPackagePrefix(ns));
      sfn.package_().del().translate(w, this);
      w.write(";");
      w.newline(0);
      w.newline(0);
    } else {
      ExtensionInfo extInfo = (ExtensionInfo) job.extensionInfo();
      if (!ns.equals(extInfo.localNamespace())) {
        String pkgName = extInfo.namespaceToJavaPackagePrefix(ns);
        w.write("package ");
        w.write(pkgName);
        w.write(";");
        w.newline(0);
        w.newline(0);
      }
    }

    boolean newline = false;
    for (Import imp : sfn.imports()) {
      imp.del().translate(w, this);
      newline = true;
    }

    if (newline) {
      w.newline(0);
    }
  }
}
