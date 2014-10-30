package fabil.extension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import polyglot.ast.Block;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Formal;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.PrimitiveType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import fabil.types.FabILFlags;
import fabil.types.FabILTypeSystem;
import fabil.visit.MemoizedMethodRewriter;
import fabil.visit.ProxyRewriter;
import fabil.visit.ThreadRewriter;

public class MethodDeclExt_c extends ClassMemberExt_c {

  @Override
  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    // Leave private methods as is. Otherwise, since the Impl will implement an
    // interface, the method has to be public.
    MethodDecl result = node();
    if (!result.flags().isPrivate()) {
      Flags flags = ProxyRewriter.toPublic(result.flags());
      result = (MethodDecl) result.flags(flags);
    }
    if (result.flags().isNative()) result = (MethodDecl) result.body(null);
    return Collections.singletonList((ClassMember) result);
  }

  @Override
  public List<ClassMember> interfaceMember(ProxyRewriter pr, ClassDecl parent) {
    MethodDecl methodDecl = node();
    Flags flags = methodDecl.flags();

    // Don't include static or private methods in interfaces.
    if (flags.isStatic() || flags.isPrivate()) return Collections.emptyList();

    // Interface methods must be public and cannot be final nor synchronized.
    flags =
        ProxyRewriter.toPublic(flags).clearFinal().clearSynchronized()
        .clearNative();

    // Clear out the method body.
    ClassMember result = (ClassMember) methodDecl.flags(flags).body(null);
    return Collections.singletonList(result);
  }

  @Override
  public List<ClassMember> proxyMember(ProxyRewriter pr, ClassDecl parent) {
    // Proxy methods will be added based on the method instances in the class
    // type, not on the methods declared. This handles the case where interfaces
    // and abstract classes don't explicitly declare all of their methods.
    return Collections.emptyList();
  }

  @Override
  public Node rewriteMemoizedMethods(MemoizedMethodRewriter mmr) {
    MethodDecl md = node();
    if (!md.flags().contains(FabILFlags.MEMOIZED)) return md;
    if (md.body() == null) return md;
    QQ qq = mmr.qq();
    NodeFactory nf = mmr.nodeFactory();
    FabILTypeSystem ts = mmr.typeSystem();
    Position CG = Position.compilerGenerated();

    TypeNode callInstanceType =
        nf.TypeNodeFromQualifiedName(CG, "fabric.worker.memoize.CallInstance");
    Type returnType = md.returnType().type();
    Type wrappedReturnType = returnType;
    if (returnType.isPrimitive()) {
      try {
        wrappedReturnType =
            ts.typeForName(ts.wrapperTypeString((PrimitiveType) returnType));
      } catch (SemanticException e) {
        System.err.println("Tried to wrap type " + returnType);
      }
    }

    int count = 0;
    String argList = "";
    List<Stmt> finals = new ArrayList<Stmt>(md.formals().size());
    List<Stmt> unpacks = new ArrayList<Stmt>(md.formals().size());
    for (Formal f : md.formals()) {
      argList += ", ";
      if (ts.isJavaInlineable(f.type().type()) || f.type().type().isPrimitive()) {
        argList += "fabric.lang.WrappedJavaInlineable.$wrap(" + f.name() + ")";
      } else {
        argList += f.name();
      }
      finals.add(qq.parseStmt("final %T $arg" + count + " = %s;", f.type(),
          f.name()));
      unpacks
          .add(qq.parseStmt("%T %s = $arg" + count + ";", f.type(), f.name()));
      count++;
    }
    finals.add(qq.parseStmt("final %T $oldThis = this;", md.memberInstance()
        .container()));

    Stmt callCreate =
        qq.parseStmt("final %T $call = new %T(this, \""
            + md.memberInstance().container().toString() + "."
            + md.methodInstance().signature() + "\"" + argList + ");",
            callInstanceType, callInstanceType);

    /* Compute otherwise */
    Stmt resultCreate = qq.parseStmt("fabric.lang.Object $result;");
    Stmt buildWarranty =
        qq.parseStmt(
            "{%LS\n"
                + "return (%T) fabric.worker.Worker.runInSemanticWarrantyTransaction("
                + "fabric.worker.transaction.TransactionManager.getInstance().getCurrentTid(),\n"
                + "new fabric.worker.Worker.Code() {\n"
                + "  public java.lang.Object run() {\n" + "    %LS\n"
                + "    %S\n" + "    %S\n" + "  }\n" + "}, true, $call);}",
            finals, wrappedReturnType, unpacks, resultCreate, md.body());
    return md.body(nf.Block(CG, callCreate, buildWarranty));
  }

  @Override
  public Node rewriteThreads(ThreadRewriter tr) {
    MethodDecl method = node();
    if (!method.name().equals("start")) return super.rewriteThreads(tr);

    ClassType ct = (ClassType) method.methodInstance().container();
    if (!tr.shouldRewrite(ct)) return super.rewriteThreads(tr);

    QQ qq = tr.qq();
    Block body = method.body();
    body =
        body.prepend(qq
            .parseStmt("fabric.worker.transaction.TransactionManager"
                + ".getInstance().registerThread(this);"));

    return method.body(body);
  }

  @Override
  public MethodDecl node() {
    return (MethodDecl) super.node();
  }
}
