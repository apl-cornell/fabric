package fabil.types;

import java.net.URI;
import java.util.Collection;

import polyglot.main.Report;
import polyglot.types.Context;
import polyglot.types.Context_c;
import polyglot.types.ImportTable;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.CollectionUtil;
import polyglot.util.InternalCompilerError;
import codebases.types.CBImportTable;
import codebases.types.CodebaseTypeSystem;

/**
 * Codebase support for the FabIL typesystem. This class duplicates some of the
 * code in FabricContext_c, but enables FabIL classes to use codebase-relative
 * names.
 */
public class FabILContext_c extends Context_c implements FabILContext {
  @SuppressWarnings("unchecked")
  private static final Collection<String> TOPICS = 
      CollectionUtil.list(Report.types, Report.context);

  protected FabILContext_c(TypeSystem ts) {
    super(ts);
  }

  /**
   * Finds the definition of a particular type.
   */
  @Override
  public Named find(String name) throws SemanticException {
    if (Report.should_report(TOPICS, 3))
      Report.report(3, "find-type " + name + " in " + this);

    if (isOuter()) return ((CodebaseTypeSystem) ts).namespaceResolver(namespace()).find(name);
    if (isSource()) return it.find(name);

    Named type = findInThisScope(name);

    if (type != null) {
      if (Report.should_report(TOPICS, 3))
        Report.report(3, "find " + name + " -> " + type);
      return type;
    }

    if (outer != null) {
      return outer.find(name);
    }

    throw new SemanticException("Type " + name + " not found.");
  }

  @Override
  public URI namespace() {
    if (isOuter())
      throw new InternalCompilerError("No namespace!");
    return ((CBImportTable)it).namespace();
  }
  
  @Override
  public Context pushSource(ImportTable it) {
    if (it instanceof CBImportTable)
      return super.pushSource(it);
    throw new InternalCompilerError("CBImportTable expected"); 
  }

  @Override
  public URI resolveCodebaseName(String name) {
    return ((CBImportTable)it).resolveCodebaseName(name);
  }
}
