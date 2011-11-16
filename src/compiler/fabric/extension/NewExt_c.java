package fabric.extension;

import polyglot.ast.New;
import polyglot.types.ClassType;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import fabric.types.FabricContext;
import fabric.types.FabricTypeSystem;
import jif.types.JifConstructorInstance;
import jif.types.LabelSubstitution;
import jif.types.label.Label;
import jif.types.label.ThisLabel;

public class NewExt_c extends LocatedExt_c implements FabricExt {

  @Override
  protected Label referenceLabel(FabricContext ctx) {
    New n = (New) node();
    FabricTypeSystem ts = (FabricTypeSystem) ctx.typeSystem();
    JifConstructorInstance ci = (JifConstructorInstance) n.constructorInstance();
    ///XXX: what is correct?
    //return ctx.pc();
    //eturn ci.returnLabel();
    return ts.join(ctx.pc(), ci.returnLabel());
  }
 
  /**
   * Returns a new label where this is replaced by a tighter bound on the
   * reference of the allocated object
   * 
   * @param label
   *          to instantiate occurrences of this within
   */
  @Override
  protected Label instantiateThisLabel(final FabricContext ctx, Label label) {
    New n = (New) node();
    final ClassType ct = n.type().toClass();
    LabelSubstitution sub = new LabelSubstitution() {
      @Override
      public Label substLabel(Label L) throws SemanticException {
        if(L instanceof ThisLabel) {
          ThisLabel tl = (ThisLabel) L;
          if(tl.classType().equals(ct)) {
            return referenceLabel(ctx);
          }
          else if(tl.classType().equals(ctx.currentClass())) {
            return ctx.currentCodePCBound();
          }
        }
        return super.substLabel(L);
      }
    };
    try {
      return label.subst(sub);
    } catch (SemanticException e) {
      throw new InternalCompilerError("Unexpected SemanticException:", e);
    }
  }

}
