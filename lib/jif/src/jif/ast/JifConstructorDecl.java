package jif.ast;

import polyglot.ast.*;
import java.util.*;

/** An immutable representation of the Jif constructor declaration.
 *  It extends the Java constructor declaration with the start label,
 *  the return label, and various constraints, including the authority
 *  constraint, the caller constraint, and the acts-for constraint. 
 */
public interface JifConstructorDecl extends JifProcedureDecl, ConstructorDecl 
{
    JifConstructorDecl startLabel(LabelNode startLabel);
    
    JifConstructorDecl returnLabel(LabelNode returnLabel);

    JifConstructorDecl constraints(List constraints);
}
