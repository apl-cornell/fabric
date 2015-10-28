package codebases.visit;

import java.net.URI;

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
import fabil.FabILOptions;

public class CodebaseTranslator extends Translator {
  protected final ExtensionInfo extInfo;

  public CodebaseTranslator(Job job, TypeSystem ts, NodeFactory nf,
      TargetFactory tf) {
    super(job, ts, nf, tf);
    this.extInfo = (ExtensionInfo) job.extensionInfo();
  }

  public String namespaceToJavaPackagePrefix(URI ns) {
    return extInfo.namespaceToJavaPackagePrefix(ns);
  }

  @Override
  protected void writeHeader(SourceFile sfn, CodeWriter w) {
    // XXX: HACK -- The translator hasn't entered the scope of the file yet,
    // so we basically are inlining what translate() would do.
    URI ns = ((CodebaseSource) sfn.source()).canonicalNamespace();
    if (sfn.package_() != null) {
      w.write("package ");
      w.write(namespaceToJavaPackagePrefix(ns));
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
    for (String pkg : ts.defaultPackageImports()) {
      w.write("import ");
      w.write(pkg + ".*;");
      w.newline(0);
    }

    //XXX: Hack for skeleton creator. Can't emit fully qualified class
    //     names w/o types, so import fabric.lang.Object explicitly.
    FabILOptions opts = (FabILOptions) extInfo.getOptions();
    if (opts.createSkeleton()) {
      w.write("import fabric.lang.Object;");
      w.newline(0);
    }

    for (Import imp : sfn.imports()) {
      imp.del().translate(w, this);
      newline = true;
    }

    if (newline) {
      w.newline(0);
    }
  }
}
