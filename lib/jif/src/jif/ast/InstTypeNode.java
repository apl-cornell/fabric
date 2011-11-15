package jif.ast;

import polyglot.ast.*;
import java.util.*;

/** Instantiated type node. 
 */
public interface InstTypeNode extends TypeNode
{
    TypeNode base();
    InstTypeNode base(TypeNode base);

    List params();
    InstTypeNode params(List params);
}
