package jif.translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import jif.ast.JifConstructorDecl;
import jif.types.JifConstructorInstance;
import jif.types.JifPolyType;
import jif.types.JifSubstType;
import polyglot.ast.*;
import polyglot.types.ClassType;
import polyglot.types.ConstructorInstance;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

public class ConstructorDeclToJavaExt_c extends ToJavaExt_c {
    protected JifConstructorInstance ci;
    protected List formals;

    public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
        JifConstructorDecl n = (JifConstructorDecl) node();

        rw.inConstructor(true);
        ci = (JifConstructorInstance)n.constructorInstance();
        formals = new ArrayList(n.formals());

        // Bypass startLabel, returnLabel and constraints.
        return rw.bypass(n.startLabel()).bypass(n.returnLabel()).bypass(n.constraints());
    }

    /** Rewrite constructor C(a) to method C C$(a) */
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        ConstructorDecl n = (ConstructorDecl) node();

        ClassType ct = ci.container().toClass();
        
        Node retVal;
        // only translate jif constructors
        if (! rw.jif_ts().isJifClass(ct)) {
            if (rw.jif_ts().isParamsRuntimeRep(ct)) {
                // It's a java class that represents parameters at runtime.
                // Produce the correct signature for constructors in the
                // Java class.
                List formals = new ArrayList(n.formals().size() + 2);
                if (ci.container() instanceof JifPolyType) {
                    JifPolyType jpt = (JifPolyType)ci.container();
                    formals.addAll(ClassDeclToJavaExt_c.produceParamFormals(jpt, rw, false));
                }
                formals.addAll(n.formals());
                n = rw.java_nf().ConstructorDecl(n.position(), 
                                                n.flags(), 
                                                n.name(), 
                                                formals, 
                                                n.throwTypes(),
                                                n.body());
                n = n.constructorInstance(null);
                retVal = n;
            }
            else {
                // it's a non-Jif class that doesn't represent parameters
                // at runtime.
                NodeFactory nf = rw.java_nf();
                retVal = nf.ConstructorDecl(n.position(),
                                          n.flags(),
                                          n.name(),
                                          n.formals(),
                                          n.throwTypes(),
                                          n.body());
            }
        }
        else {
            retVal = jifClassConstructorDecl(rw, n);
        }

        rw.inConstructor(false);
        return retVal;
    }

    private Node jifClassConstructorDecl(JifToJavaRewriter rw, ConstructorDecl n) throws SemanticException {
        NodeFactory nf = rw.java_nf();
        ConstructorInstance ci = n.constructorInstance();
        ClassType ct = ci.container().toClass();
        
        
        Block body = n.body();
        List inits = new ArrayList(3);

        // add a call to the initializer.
        inits.add(rw.qq().parseStmt("this." + 
                  ClassDeclToJavaExt_c.INITIALIZATIONS_METHOD_NAME + "();"));

        if (body.statements().isEmpty() ||
            (body.statements().size() == 1 &&
             body.statements().get(0) instanceof Empty)) {
            // no body to add...
        }
        else {

          // If this is a Jif class but the superclass is not a Jif class, then
          // we need to remove any calls to super constructors from the body.
          // Previous checks should have ensured that the first statement
          // is either a this(...) call (permitted if the java superclass is
          // trusted) or the default super call, super().
          if (rw.jif_ts().isJifClass(ct) && !rw.jif_ts().isJifClass(ct.superType())) {
              // first calculate the number of parameters that are being
              // passed to super.
              int numSuperParams = 0;
              if (rw.jif_ts().isParamsRuntimeRep(ct.superType())) {
                  // super class is representing runtime params
                  Type superType = ct.superType();
                  if (superType instanceof JifSubstType) {
                      numSuperParams = ((JifPolyType)((JifSubstType)superType).base()).params().size();
                  }
              }

              // check that the first statement of the body is a constructor call
              Stmt s = (Stmt)body.statements().get(0);
              if (s instanceof ConstructorCall) {
                  ConstructorCall cc = (ConstructorCall)s;
                  if (cc.kind() == ConstructorCall.SUPER) {
                      // it's a super call.
                      // check that it's the default constructor
                      if (cc.arguments().size() != numSuperParams) {
                          throw new InternalCompilerError(body.position(),
                                       "Expected super constructor call to be the " +                                       "default constructor as we have a " +
                                       "Jif class with a non-Jif superclass: " + cc);
                      }
    
                      // remove the default constructor.
                      List stmtList = new LinkedList(body.statements());
                      stmtList.remove(0);
                      body = body.statements(stmtList);
                  }
              }
          }
          
          inits.add(body);
        }
        
        inits.addAll(additionalInits(rw));
        
        // Add an explicit return to the body.
        addConstructorReturn(rw, (JifConstructorInstance)ci, inits, n.position());

        body = nf.Block(n.position(), inits);
        
        body = jifConstructorBody(rw, body);

        String name = ClassDeclToJavaExt_c.constructorTranslatedName(ct);

        TypeNode tn = rw.jif_nf().CanonicalTypeNode(n.position(), ct);
        tn = (TypeNode) tn.visit(rw);

        MethodDecl m = nf.MethodDecl(n.position(), n.flags(), tn, name,
                                     n.formals(), n.throwTypes(), body);
        m = m.methodInstance(null);

        return m;
    }

    protected void addConstructorReturn(JifToJavaRewriter rw, 
            JifConstructorInstance ci, 
            List inits, 
            Position pos) {
        NodeFactory nf = rw.java_nf();
        inits.add(nf.Return(pos, nf.This(pos)));
    }

    /**
     * Allow subclasses to modify the Jif constructor body.
     */
    protected Block jifConstructorBody(JifToJavaRewriter rw, Block body) {
        return body;
    }

    /**
     * Allow subclasses to add additional initializations
     */
    protected List additionalInits(JifToJavaRewriter rw) {
        return Collections.EMPTY_LIST;
    }
}
