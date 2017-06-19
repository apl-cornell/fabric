package bolt.types;

import java.util.Collection;
import java.util.HashMap;

import polyglot.ast.Lang;
import polyglot.ext.jl5.types.JL5Context_c;
import polyglot.main.Report;
import polyglot.types.ClassType;
import polyglot.types.CodeInstance;
import polyglot.types.Context;
import polyglot.types.ImportTable;
import polyglot.types.InitializerInstance;
import polyglot.types.ParsedClassType;
import polyglot.util.CollectionUtil;

/**
 * The context for Bolt type checking. Introduces the notion of an
 * object-initialization context.
 */
public class BoltContext_c extends JL5Context_c implements BoltContext {

  /**
   * Indicates whether this is an object-initialization context.
   */
  protected boolean objectInitContext;

  protected BoltContext_c(Lang lang, BoltTypeSystem ts) {
    super(lang, ts);
  }

  @Override
  public boolean inObjectInitContext() {
    return objectInitContext;
  }

  @Override
  public BoltContext pushSource(ImportTable it) {
    BoltContext_c result = (BoltContext_c) super.pushSource(it);
    result.objectInitContext = false;
    return result;
  }

  @Override
  public Context pushClass(ParsedClassType classScope, ClassType type) {
    BoltContext_c result = (BoltContext_c) super.pushClass(classScope, type);
    result.objectInitContext = false;
    return result;
  }

  @Override
  public Context pushCode(CodeInstance ci) {
    BoltContext_c result = (BoltContext_c) super.pushCode(ci);

    // We are in a object-initialization context if we just pushed an
    // initializer block.
    result.objectInitContext = isInitializer(ci);
    return result;
  }

  @Override
  public Context pushCTORCall() {
    BoltContext_c result = (BoltContext_c) super.pushCTORCall();
    result.staticContext = false;
    result.objectInitContext = true;
    return result;
  }

  @Override
  public BoltContext pushObjectInitializer(ObjectInitKind kind) {
    if (Report.should_report(TOPICS, 4)) {
      Report.report(4, "push final-init(" + kind + ")");
    }

    BoltContext_c result = (BoltContext_c) super.push();
    if (kind == ObjectInitKind.INLINE) {
      // Eagerly instantiate data structs so we can alias them.
      instantiate();

      result.types = types;
      result.vars = vars;
      result.typeVars = typeVars;
    }

    return result;
  }

  /**
   * Determines whether the given {@link CodeInstance} represents an initializer
   * block.
   */
  protected boolean isInitializer(CodeInstance ci) {
    return !ci.flags().isStatic() && ci instanceof InitializerInstance;
  }

  /**
   * Eagerly instantiates the data structures for this context.
   */
  protected void instantiate() {
    if (types == null) types = new HashMap<>();
    if (vars == null) vars = new HashMap<>();
    if (typeVars == null) typeVars = new HashMap<>();
  }

  private static final Collection<String> TOPICS =
      CollectionUtil.list(Report.types, Report.context);

}
