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
 * The context for Bolt type checking. Introduces the notion of a
 * final-initialization context.
 */
public class BoltContext_c extends JL5Context_c implements BoltContext {

  /**
   * Indicates whether this is a final-initialization context.
   */
  protected boolean finalContext;

  protected BoltContext_c(Lang lang, BoltTypeSystem ts) {
    super(lang, ts);
  }

  @Override
  public boolean inFinalContext() {
    return finalContext;
  }

  @Override
  public BoltContext pushSource(ImportTable it) {
    BoltContext_c result = (BoltContext_c) super.pushSource(it);
    result.finalContext = false;
    return result;
  }

  @Override
  public Context pushClass(ParsedClassType classScope, ClassType type) {
    BoltContext_c result = (BoltContext_c) super.pushClass(classScope, type);
    result.finalContext = false;
    return result;
  }

  @Override
  public Context pushCode(CodeInstance ci) {
    BoltContext_c result = (BoltContext_c) super.pushCode(ci);

    // We are in a final-initialization context if we just pushed a final
    // initializer.
    result.finalContext = isFinalInitializer(ci);
    return result;
  }

  @Override
  public Context pushCTORCall() {
    BoltContext_c result = (BoltContext_c) super.pushCTORCall();
    result.staticContext = false;
    result.finalContext = true;
    return result;
  }

  @Override
  public BoltContext pushFinalInitializer(FinalInitKind kind) {
    if (Report.should_report(TOPICS, 4)) {
      Report.report(4, "push final-init(" + kind + ")");
    }

    BoltContext_c result = (BoltContext_c) super.push();
    if (kind == FinalInitKind.INLINE) {
      // Eagerly instantiate data structs so we can alias them.
      instantiate();

      result.types = types;
      result.vars = vars;
      result.typeVars = typeVars;
    }

    return result;
  }

  /**
   * Determines whether the given {@link CodeInstance} represents a final
   * initializer.
   */
  protected boolean isFinalInitializer(CodeInstance ci) {
    return ci.flags().isFinal() && ci instanceof InitializerInstance;
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
