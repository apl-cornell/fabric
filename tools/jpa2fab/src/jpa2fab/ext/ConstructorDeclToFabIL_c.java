package jpa2fab.ext;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jif.translate.ClassDeclToJavaExt_c;
import polyglot.ast.Block;
import polyglot.ast.ConstructorCall;
import polyglot.ast.ConstructorDecl;
import polyglot.ast.Empty;
import polyglot.ast.Ext;
import polyglot.ast.Id;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
import polyglot.ext.jl5.translate.JL5ConstructorDeclToJL_c;
import polyglot.ext.jl5.types.JL5ParsedClassType;
import polyglot.translate.ExtensionRewriter;
import polyglot.types.ClassType;
import polyglot.types.ConstructorInstance;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.visit.NodeVisitor;
import fabil.types.FabILTypeSystem;

public class ConstructorDeclToFabIL_c extends JL5ConstructorDeclToJL_c
    implements Ext {

  @Override
  public NodeVisitor toExtEnter(ExtensionRewriter rw) throws SemanticException {
    JPA2FabILRewriter j2f = (JPA2FabILRewriter) super.toExtEnter(rw);
    j2f.inConstructor(true);
    return j2f;
  }

  @Override
  public Node toExt(ExtensionRewriter rw) throws SemanticException {
    JPA2FabILRewriter j2f = (JPA2FabILRewriter) rw;

    ConstructorDecl n = (ConstructorDecl) node();

    NodeFactory nf = rw.to_nf();
    ConstructorInstance ci = n.constructorInstance();
    ClassType ct = ci.container().toClass();

    if (!j2f.isPersistent(ct)) return super.toExt(rw);

    Block body = n.body();
    List<Stmt> inits = new ArrayList<Stmt>(3);

    if (body.statements().isEmpty()
        || (body.statements().size() == 1 && body.statements().get(0) instanceof Empty)) {
      // no body to add...
    } else {
      // check that the first statement of the body is a constructor call
      Stmt s = body.statements().get(0);
      if (s instanceof ConstructorCall) {
        ConstructorCall cc = (ConstructorCall) s;
        if (cc.kind() == ConstructorCall.SUPER) {
          // it's a super call.
          // check that it's the default constructor
          if (cc.arguments().size() != 0) {
            j2f.checkInheritence((JL5ParsedClassType) rw.context()
                .currentClass());
            throw new InternalCompilerError(
                "Unexpected call to (non-default) super constructor.");
          } else {
            // replace the super constructor call with the appropriate method call
            List<Stmt> stmtList = new LinkedList<Stmt>(body.statements());
            ClassType superCT = ct.superType().toClass();
            FabILTypeSystem ts = (FabILTypeSystem) rw.to_ts();
            if (superCT.equals(rw.from_ts().Object())) {
              superCT = ts.FObject();
            }
            Stmt defaultCtor =
                rw.qq().parseStmt("%s();",
                    superCT.fullName().replace(".", "$") + "$");
            stmtList.set(0, defaultCtor);
            body = body.statements(stmtList);
          }
        }
      }

      inits.add(body);
    }

    // Add an explicit return to the body.
    inits.add(nf.Return(n.position(), nf.This(n.position())));

    body = nf.Block(n.position(), inits);

    Id name =
        nf.Id(n.position(), ClassDeclToJavaExt_c.constructorTranslatedName(ct));

    TypeNode tn = rw.from_nf().CanonicalTypeNode(n.position(), ct);
    tn = (TypeNode) tn.visit(rw);

    MethodDecl m =
        nf.MethodDecl(n.position(), n.flags(), tn, name, n.formals(),
            n.throwTypes(), body);
    m = m.methodInstance(null);

    return m;
  }
}
