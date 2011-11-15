package jif.extension;

import java.util.Iterator;
import java.util.Set;

import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.types.SemanticException;
import polyglot.util.Position;

/** The Jif extension of the <code>DeclassifyExpr</code> node. 
 * 
 *  @see jif.ast.DeclassifyExpr
 */
public class JifDeclassifyExprExt extends JifDowngradeExprExt
{
    public JifDeclassifyExprExt(ToJavaExt toJava) {
        super(toJava);
    }

    protected void checkOneDimenOnly(LabelChecker lc, 
                                  final JifContext A,
                                  Label labelFrom, 
                                  Label labelTo, Position pos) 
         throws SemanticException {
       checkOneDimen(lc, A, labelFrom, labelTo, pos, true);
   }
   protected static void checkOneDimen(LabelChecker lc, 
                                 final JifContext A,
                                 Label labelFrom, 
                                 Label labelTo, Position pos,
                                 boolean isExpr) 
        throws SemanticException {
       final String exprOrStmt = (isExpr?"expression":"statement");
       
       JifTypeSystem jts = lc.jifTypeSystem();
       Label topConfLabel = jts.pairLabel(pos, 
                                          jts.topConfPolicy(pos),
                                          jts.bottomIntegPolicy(pos));
       
       lc.constrain(new NamedLabel("declass_from", labelFrom), 
                                        LabelConstraint.LEQ, 
                                        new NamedLabel("declass_to", labelTo).
                                           join(lc, "top_confidentiality", topConfLabel),
                                        A.labelEnv(),       
                                        pos,
                                        new ConstraintMessage() {
                public String msg() {
                    return "Declassify " + exprOrStmt + "s cannot downgrade integrity.";
                }
                public String detailMsg() { 
                    return "The declass_from label has lower integrity than the " +
                                "declass_to label; declassify " + exprOrStmt + "s " +
                                "cannot downgrade integrity.";
                }                     
    }
    );
   }
   
   protected void checkAuthority(LabelChecker lc, 
                                 final JifContext A,
                                 Label labelFrom, 
                                 Label labelTo, Position pos) 
        throws SemanticException {
      checkAuth(lc, A, labelFrom, labelTo, pos, true);
  }
  protected static void checkAuth(LabelChecker lc, 
                                final JifContext A,
                                Label labelFrom, 
                                Label labelTo, Position pos, boolean isExpr) 
       throws SemanticException {
      final String exprOrStmt = (isExpr?"expression":"statement");

      Label authLabel = A.authLabel();    
  lc.constrain(new NamedLabel("declass_from", labelFrom), 
               LabelConstraint.LEQ, 
               new NamedLabel("declass_to", labelTo).
                         join(lc, "auth_label", authLabel),
               A.labelEnv(),
               pos,
                    new ConstraintMessage() {
               public String msg() {
                   return "The method does not have sufficient " +
                          "authority to declassify this " + exprOrStmt + ".";
               }
               public String detailMsg() { 
                   StringBuffer sb = new StringBuffer();
                   Set authorities = A.authority();
                   if (authorities.isEmpty()) {
                       sb.append("no principals");
                   }
                   else {
                       sb.append("the following principals: ");
                   }
                   for (Iterator iter = authorities.iterator(); iter.hasNext() ;) {
                       Principal p = (Principal)iter.next();
                       sb.append(p.toString());
                       if (iter.hasNext()) {
                           sb.append(", ");
                       }
                   }
                   
                    
                   return "The " + exprOrStmt + " to declassify has label " + 
                          namedRhs()+ ", and the " + exprOrStmt + " " +
                          "should be downgraded to label " +
                          "declass_to. However, the method has " +
                          "the authority of " + sb.toString() + ". " +
                          "The authority of other principals is " +
                          "required to perform the declassify.";
               }
               public String technicalMsg() {
                   return "Invalid declassify: the method does " +
                          "not have sufficient authorities.";
               }                     
   }
   );
  }
    
    protected void checkRobustness(LabelChecker lc, 
                                   JifContext A,
                                   Label labelFrom, 
                                   Label labelTo, Position pos) 
                 throws SemanticException {
        checkRobustDecl(lc, A, labelFrom, labelTo, pos, true);
    }

    protected static void checkRobustDecl(LabelChecker lc, 
                                          JifContext A,
                                          Label labelFrom, 
                                          Label labelTo, Position pos, boolean isExpr) 
        throws SemanticException {
        
        final String exprOrStmt = (isExpr?"expression":"statement");
        
        JifTypeSystem jts = lc.typeSystem();
        Label pcInteg = jts.writersToReadersLabel(pos, A.pc());

        lc.constrain(new NamedLabel("declass_from", labelFrom), 
                     LabelConstraint.LEQ, 
                     new NamedLabel("declass_to", labelTo).
                               join(lc, "pc_integrity", pcInteg),
                     A.labelEnv(),
                     pos,
                    new ConstraintMessage() {
                     public String msg() {
                         return "Declassification not robust: a new reader " +
                                        "may influence the decision to " +
                                        "declassify.";
                     }
                     public String detailMsg() { 
                         return "The declassification of this " + exprOrStmt + " is " +
                         "not robust; at least one of the principals that is " +
                         "allowed to read the information after " +
                         "declassification may be able to influence the " +
                         "decision to declassify.";
                     }
         }
         );

        Label fromInteg = jts.writersToReadersLabel(pos, labelFrom);
        lc.constrain(new NamedLabel("declass_from_label", labelFrom), 
                     LabelConstraint.LEQ, 
                     new NamedLabel("declass_to_label", labelTo).
                               join(lc, "from_label_integrity", fromInteg),
                     A.labelEnv(),
                     pos,
                     new ConstraintMessage() {
                     public String msg() {
                         return "Declassification not robust: a new reader " +
                                        "may influence the data to be " +
                                        "declassified.";
    }
                     public String detailMsg() { 
                         return "The declassification of this " + exprOrStmt + " is " +
                         "not robust; at least one of the principals that is " +
                         "allowed to read the information after " +
                         "declassification may be able to influence the " +
                         "data to be declassified.";
}
         }
         );
    }


}
