package jif.ast;

import jif.types.JifPolyType;
import jif.types.JifTypeSystem;
import jif.types.ParamInstance;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.Node_c;
import polyglot.types.Context;
import polyglot.types.SemanticException;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.Translator;
import polyglot.visit.TypeBuilder;

/** An implementation of the <code>ParamDecl</code> interface. 
 */
public class ParamDecl_c extends Node_c implements ParamDecl
{
    ParamInstance pi;
    Id name;
    ParamInstance.Kind kind;

    public ParamDecl_c(Position pos, ParamInstance.Kind kind,
            Id name) {
        super(pos);
        this.kind = kind;
        this.name = name;
    }

    public boolean isDisambiguated() {
        return pi != null && pi.isCanonical() && super.isDisambiguated();
    }

    public ParamInstance.Kind kind() {
        return this.kind;
    }

    public ParamDecl kind(ParamInstance.Kind kind) {
        ParamDecl_c n = (ParamDecl_c) copy();
        n.kind = kind;
        return n;
    }

    public String name() {
        return this.name.id();
    }

    public ParamDecl name(String name) {
        ParamDecl_c n = (ParamDecl_c) copy();
        n.name = n.name.id(name);
        return n;
    }

    public ParamInstance paramInstance() {
        return pi;
    }

    public ParamDecl paramInstance(ParamInstance pi) {
        ParamDecl_c n = (ParamDecl_c) copy();
        n.pi = pi;
        return n;
    }

    public boolean isPrincipal() {
        return kind == ParamInstance.PRINCIPAL;
    }

    public boolean isLabel() {
        return kind == ParamInstance.INVARIANT_LABEL ||
        kind == ParamInstance.COVARIANT_LABEL;
    }

    public boolean isInvariantLabel() {
        return kind == ParamInstance.INVARIANT_LABEL;
    }

    public boolean isCovariantLabel() {
        return kind == ParamInstance.COVARIANT_LABEL;
    }

    public void leaveScope(Context c) {
        c.addVariable(pi);
    }

    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        JifTypeSystem ts = (JifTypeSystem) tb.typeSystem();

        JifPolyType ct = (JifPolyType) tb.currentClass();

        ParamInstance pi = ts.paramInstance(position(), ct, kind, name.id());

        return paramInstance(pi);
    }

    public String toString() {
        return kind.toString();
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        if (kind == ParamInstance.COVARIANT_LABEL) {
            w.write("covariant label ");
        }
        else if (kind == ParamInstance.INVARIANT_LABEL) {
            w.write("label ");
        }
        else if (kind == ParamInstance.PRINCIPAL) {
            w.write("principal ");
        }

        w.write(name.id());
    }

    public void translate(CodeWriter w, Translator tr) { } 
}
