package fabric.extension;

import fabric.types.FabricReferenceType;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.ast.Receiver;
import polyglot.ast.Special;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import jif.ast.Jif_c;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;
import jif.visit.LabelChecker;

/**
 * A class containing code for checking access labels on dereferences.
 */
public class DereferenceHelper {
  /**
   * Adds constraints to lc reflecting the fetch side effects of a dereference
   */
  public static void checkDereference(final Receiver target, LabelChecker lc, Position pos)
  throws SemanticException
  {
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();

    // All classes referred to in executing code have already been fetched
    // during typechecking.  Thus static dispatches do not cause fetches
    if (target instanceof TypeNode)
      return;
    
    // this and super are known to be resident when a method is executing.  Thus
    // they do not cause side effects when dereferenced.
    if (target == null || target instanceof Special)
      return;
    
    if (!(target instanceof Expr))
      throw new InternalCompilerError("unexpected receiver type");

    // get the type of the target
    FabricReferenceType targetType = (FabricReferenceType) ts.unlabel(target.type()); 
    
    // get the access label of the type
    final ConfPolicy accessLabel = targetType.accessPolicy();
    
    // check that the pc and ref label can flow to the access label
    JifContext       A  = lc.context();
    
    Label objLabel = Jif_c.getPathMap(target).NV();
    Label pc       = A.pc();
    Label lhs      = ts.join(objLabel, pc);
    
    lc.constrain(new NamedLabel("reference label ⊔ pc", lhs),
                 LabelConstraint.LEQ,
                 new NamedLabel("access label", ts.toLabel(accessLabel)),
                 A.labelEnv(), pos,new ConstraintMessage() {
                    @Override
                    public String msg() {
                      return "Dereferencing " + target + " may cause it to be "
                           + "fetched, revealing too much information to its "
                           + "store";
          }
        });
  }
}
