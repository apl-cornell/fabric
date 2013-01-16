package fabil.extension;

import java.util.Collections;
import java.util.List;

import polyglot.ast.Block;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Formal;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.util.Position;
import fabil.ast.FabILNodeFactory;
import fabil.types.FabILFlags;
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

    /* Added for memoization work */
    body =
      body.prepend(qq.
          parseStmt("if (Thread.currentThread() instanceof "
              + "fabric.common.FabricThread) {\n"
              + "  fabric.common.FabricThread $curThread = "
              + "(fabric.common.FabricThread) Thread.currentThread();\n"
              + "  $mc = new "
              + "fabric.worker.memoize.MemoCache($curThread.getMemoCache());\n"
              + "} else {\n"
              + "  $mc = new fabric.worker.memoize.MemoCache();\n"
              + "}\n"));

    return method.body(body);
  }

  @Override
  public Node rewriteMemoizedMethods(MemoizedMethodRewriter mmr) {
    MethodDecl method = node();
    if (!method.flags().contains(FabILFlags.MEMOIZED))
      return super.rewriteMemoizedMethods(mmr);

    /* Memoized flag not necessary after this transformation */
    method = method.flags(method.flags().clear(FabILFlags.MEMOIZED));

    FabILNodeFactory nf = mmr.nodeFactory();
    Position CG = Position.compilerGenerated();
    QQ qq = mmr.qq();

    String args = "";
    boolean first = true;
    for (Formal arg : method.formals()) {
      if (first) {
        first = false;
      } else {
        args += ", ";
      }
      args += arg.name();
    }

    String callee = "this";
    String ctType = "CallTuple";
    if (method.flags().contains(FabILFlags.STATIC)) {
      callee = "\"" + method.memberInstance().container().toString() + "\"";
      ctType = "StaticCallTuple";
    }
      
    return method.body(nf.Block(CG, qq.parseStmt("{\n"
          + "  final fabric.worker.memoize.CallTuple $memoCallTup = "
          +   "new fabric.worker.memoize." + ctType + "(\"" + method.name()
          +     "\", %s, "
          +     "java.util.Arrays.asList(new java.lang.Object[]"
          +     "{" + args + "}));\n"
          + "  final fabric.worker.memoize.MemoCache $memoCache = " 
          +   "fabric.worker.memoize.MemoCache.getInstance();\n"
          + "  synchronized (fabric.worker.memoize.MemoCache.class) {\n"
          + "    if "
          + "(fabric.worker.memoize.MemoCache.containsCall($memoCallTup))\n"
          + "      return (%T) $memoCache.reuseCall($memoCallTup);\n"
          + "  }\n"
          + "  $memoCache.beginMemoRecord($memoCallTup);\n"
          + "  try {\n"
          + "    %S\n"
          + "  } catch (RuntimeException e) {\n"
          + "    $memoCache.abruptEndMemoRecord($memoCallTup);\n"
          + "    throw e;\n"
          + "  }\n"
          + "}", callee, mmr.methodReturnType(), method.body())));
  }

  @Override
  public MethodDecl node() {
    return (MethodDecl) super.node();
  }
}
