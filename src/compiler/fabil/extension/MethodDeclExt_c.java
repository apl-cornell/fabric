package fabil.extension;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import polyglot.ast.Block;
import polyglot.ast.Call;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Expr;
import polyglot.ast.Formal;
import polyglot.ast.If;
import polyglot.ast.Local;
import polyglot.ast.LocalDecl;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.MethodInstance;
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
      result = result.flags(flags);
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
    flags = flags.clear(FabILFlags.MEMOIZED);

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
  public Node rewriteMemoizedMethods(MemoizedMethodRewriter mmr) {
    /* TODO:
     * Refactor
     * Handle Static Methods
     */
    MethodDecl method = node();
    if (!method.flags().contains(FabILFlags.MEMOIZED))
      return super.rewriteMemoizedMethods(mmr);

    /* Memoized flag not necessary after this transformation */
    method = method.flags(method.flags().clear(FabILFlags.MEMOIZED));

    NodeFactory nf = mmr.nodeFactory();
    FabILTypeSystem ts = mmr.typeSystem();
    Block body = method.body();
    Position CG = Position.compilerGenerated();

    /* Declare the $memoCache local variable */
    Call getWorker = nf.Call(CG, nf.CanonicalTypeNode(CG, ts.Worker()),
        nf.Id(CG, "getWorker"));
    /* ANNOYING :( */
    /* TODO: Do I need to set the types? */
    List<? extends MethodInstance> getWorkerList =
      ts.Worker().methods("getWorker", new ArrayList());
    getWorker = getWorker.methodInstance(getWorkerList.get(0));

    Call getMemoCache = nf.Call(CG, getWorker, nf.Id(CG, "getMemoCache"));
    /* ANNOYING :( */
    /* TODO: Do I need to set the types? */
    List<? extends MethodInstance> getMemoCacheList =
      ts.Worker().methods("getMemoCache", new ArrayList());
    getMemoCache = getMemoCache.methodInstance(getMemoCacheList.get(0));
    LocalDecl memoCacheDecl = nf.LocalDecl(CG, FabILFlags.NONE,
        nf.CanonicalTypeNode(CG, ts.MemoCache()), nf.Id(CG, "$memoCache"),
        getMemoCache);

    /* Declare the $memoCallTup local variable */
    ClassType arraysType = null;
    try {
      arraysType = (ClassType) ts.typeForName("java.util.Arrays");
    } catch (SemanticException e) {
      System.err.println("Couldn't get java.util.Arrays!");
      return method;
    }
    List<Expr> argExprs = new ArrayList<Expr>();
    for (Formal f : method.formals()) {
      argExprs.add(nf.Local(CG, nf.Id(CG, f.name())));
    }
    Call argListExpr = nf.Call(CG, nf.CanonicalTypeNode(CG, arraysType),
        nf.Id(CG, "asList"), argExprs);
    argListExpr =
      argListExpr.methodInstance(arraysType.methodsNamed("asList").get(0));
    List<Expr> newArgs = Arrays.asList(nf.StringLit(CG, method.name()),
        nf.This(CG).type(method.methodInstance().container()), argListExpr);
    LocalDecl callTupleDecl = nf.LocalDecl(CG, FabILFlags.NONE,
        nf.CanonicalTypeNode(CG, ts.CallTuple()), nf.Id(CG, "$memoCallTup"),
        nf.New(CG, nf.CanonicalTypeNode(CG, ts.CallTuple()), newArgs));

    Local mc = nf.Local(CG, memoCacheDecl.id());
    ClassType mcType = ts.MemoCache();
    Local ct = nf.Local(CG, callTupleDecl.id());
    ClassType ctType = ts.CallTuple();

    /* Check for item in memo */
    Call memoCheck = nf.Call(CG, mc, nf.Id(CG, "containsCall"), ct);
    memoCheck = memoCheck.methodInstance(mcType.methods("containsCall",
          Arrays.asList(ctType)).get(0));

    /* Get item from memo */
    Call memoGrab = nf.Call(CG, mc, nf.Id(CG, "getCall"), ct);
    memoGrab = memoGrab.methodInstance(mcType.methods("getCall",
          Arrays.asList(ctType)).get(0));

    /* If in memo, get the result and return it.  Otherwise compute the return
     * value and store it for future calls (stores done on Return
     * transformation.
     */
    If memoLogic = nf.If(CG, memoCheck,
        nf.Return(CG, nf.Cast(CG, mmr.methodReturnType(), memoGrab)), body);
    Block newBody = nf.Block(CG, memoCacheDecl, callTupleDecl, memoLogic);
    return method.body(newBody);
  }

  @Override
  public MethodDecl node() {
    return (MethodDecl) super.node();
  }
}
