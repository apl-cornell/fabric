package jif.ast;

import polyglot.ast.*;
import java.util.*;

/** An immutable representation of the Jif procedure declaration.
 *  It extends the Java procedure declaration with the start label,
 *  the return label, and various constraints, including the authority
 *  constraint, the caller constraint, and the acts-for constraint. 
 */
public interface JifProcedureDecl extends ProcedureDecl {
    LabelNode startLabel();
    LabelNode returnLabel();
    List constraints();
}
