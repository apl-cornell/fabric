package jif.ast;

import polyglot.ast.*;
import polyglot.types.Flags;
import polyglot.types.LocalInstance;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.NodeVisitor;

public class JifLocalDecl_c extends LocalDecl_c
{
    public JifLocalDecl_c(Position pos, Flags flags, TypeNode type, Id name, Expr init) {
        super(pos, flags, type, name, init);
    }

    public Node visitChildren(NodeVisitor v) {
        TypeNode type = (TypeNode) visitChild(type(), v);
        Id name = (Id)visitChild(id(), v);
        if (v instanceof AmbiguityRemover) {
            // ugly hack to make sure that the local instance
            // has the correct information in it by the time
            // the init expression is disambiguated.
            LocalInstance li = localInstance();
            li.setFlags(flags());
            li.setName(name());
            li.setType(declType());            
        }
        Expr init = (Expr) visitChild(init(), v);

        return reconstruct(type, name, init);
    }
}
