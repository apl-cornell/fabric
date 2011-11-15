package jif.translate;

import java.util.ArrayList;
import java.util.List;

import jif.ast.JifMethodDecl;
import jif.types.JifMethodInstance;
import jif.types.JifPolyType;
import jif.types.JifTypeSystem;
import polyglot.ast.*;
import polyglot.types.Flags;
import polyglot.types.MethodInstance;
import polyglot.types.SemanticException;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

public class MethodDeclToJavaExt_c extends ToJavaExt_c {
    protected JifMethodInstance mi;
    protected List formals;
    public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
        // Bypass labels and constraints
        JifMethodDecl n = (JifMethodDecl) node();
        
        mi = (JifMethodInstance)n.methodInstance();
        formals = new ArrayList(n.formals());

        // Bypass startLabel, returnLabel and constraints.
        return rw.bypass(n.startLabel()).bypass(n.returnLabel()).bypass(n.constraints());
    }

    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        MethodDecl n = (MethodDecl) node();
        if ("main".equals(n.name()) &&
            n.flags().isStatic() &&
            n.formals().size() == 2) {
            // the method is static main(principal p, String[] args). We
            // need to translate this specially.
            // (The typechecking for JifMethodDecl ensures that the formals
            // are of the correct type.)
            return staticMainToJava(rw, n);
        }

        MethodInstance mi = n.methodInstance();
        List formals = new ArrayList(n.formals().size() + 2);

        // for static methods, add args for the params of the class
        if (mi.flags().isStatic() && mi.container() instanceof JifPolyType) {
            JifPolyType jpt = (JifPolyType)mi.container();
            formals.addAll(ClassDeclToJavaExt_c.produceParamFormals(jpt, rw, false));
        }

        formals.addAll(n.formals());
        n = rw.java_nf().MethodDecl(n.position(), n.flags(), n.returnType(),
                                    rw.java_nf().Id(Position.compilerGenerated(), n.name()), 
                                    formals, n.throwTypes(),
                                    n.body());
        n = n.methodInstance(null);
        return n;
    }

    /** Rewrite static main(principal p, String[] args) {...} to
     * static main(String[] args) {Principal p = Runtime.getUser(); {...} };
     */
    public Node staticMainToJava(JifToJavaRewriter rw, MethodDecl n) {
        Formal formal0 = (Formal)n.formals().get(0); // the principal
        Formal formal1 = (Formal)n.formals().get(1); // the string array
        List formalList = CollectionUtil.list(formal1);

        Block origBody = n.body();

        JifTypeSystem jifTs = rw.jif_ts();
        TypeNode type = rw.qq().parseType(jifTs.PrincipalClassName());
        Expr init = rw.qq().parseExpr(jifTs.RuntimePackageName()
            + ".Runtime.user(null)");

        Stmt declPrincipal =
            rw.java_nf().LocalDecl(origBody.position(),
                               Flags.FINAL,
                               type,
                               rw.java_nf().Id(Position.compilerGenerated(), formal0.name()),
                               init);
        Block newBody = rw.java_nf().Block(origBody.position(),
                                           declPrincipal,
                                           origBody);

        n = rw.java_nf().MethodDecl(n.position(),
                                    n.flags(),
                                    n.returnType(),
                                    rw.java_nf().Id(Position.compilerGenerated(), n.name()),
                                    formalList,
                                    n.throwTypes(),
                                    newBody);
        n = n.methodInstance(null);
        return n;
    }

}
