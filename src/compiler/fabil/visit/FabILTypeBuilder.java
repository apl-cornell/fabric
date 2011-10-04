package fabil.visit;

import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.main.Report;
import polyglot.types.Flags;
import polyglot.types.Named;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.TypeBuilder;
import fabil.frontend.CodebaseSource;
import fabil.types.CodebaseTypeSystem;
import fabric.common.SysUtil;
import fabric.lang.Codebase;
import fabric.visit.CodebaseTypeBuilder;

public class FabILTypeBuilder extends TypeBuilder implements
    CodebaseTypeBuilder {
  protected CodebaseSource source;

  public FabILTypeBuilder(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
  }

  @Override
  protected ParsedClassType newClass(Position pos, Flags flags, String name)
      throws SemanticException {
    // see if an appropriately named class already exists in the resolver
    String fullName = name;
    if (currentClass() != null) {
      fullName = currentClass().fullName() + "." + name;
    } else if (currentPackage() != null) {
      fullName = currentPackage().fullName() + "." + name;
    }

    Codebase cb = currentSource().codebase();
    String absoluteName = ((CodebaseTypeSystem) ts).absoluteName(cb, fullName, false);
    Named n = ts.systemResolver().check(absoluteName);

    ParsedClassType pct = null;
    if (n instanceof ParsedClassType) {
      pct = (ParsedClassType) n;
    }

    if (pct != null && job().source().equals(pct.fromSource())) {
      // If a type of the same name and from the same source has been loaded,
      // there is no need to generate it again.
      // It won't miss real duplicate class declarations, because we only
      // take this shortcut when the cached class type was from the same source.
      // Moreover, type-checking of SourceFile will take care of duplicate
      // definitions in the same source.
      return pct;
    }

    return super.newClass(pos, flags, name);
  }

  public TypeBuilder pushSource(CodebaseSource source) {
    if (Report.should_report(Report.visit, 4))
      Report.report(4, "TB pushing source " + source + ": " + context());
    FabILTypeBuilder tb = (FabILTypeBuilder) push();
    tb.inCode = false;
    tb.source = source;
    return tb;
  }

  public CodebaseSource currentSource() {
    return source;
  }
}
