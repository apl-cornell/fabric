package fabil.visit;

import java.util.ArrayList;
import java.util.List;

import polyglot.ast.Assign;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Expr;
import polyglot.ast.FieldDecl;
import polyglot.ast.Id;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Return;
import polyglot.ast.Stmt;
import polyglot.frontend.Job;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.MethodInstance;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

/**
 * Adds code for memoizing the result of fabric.lang.security.PairLabel.hashCode().
 */
public class Memoizer extends NodeVisitor {
  protected Job job;
  protected NodeFactory nf;
  protected TypeSystem ts;
  protected List<ClassMember> addedMembers = null;
  protected String flagName = null;
  protected String valueName = null;

  public Memoizer(Job job, TypeSystem ts, NodeFactory nf) {
    this.job = job;
    this.ts = ts;
    this.nf = nf;
  }

  @Override
  public NodeVisitor enter(Node n) {
    if (n instanceof ClassDecl) {
      ClassDecl cd = (ClassDecl) n;
      if (cd.name().equals("_Impl")) {
        // XXX Memoizer runs after ProxyRewriter, and only changes the _Impl
        // class.
        Memoizer v = (Memoizer) this.copy();
        v.addedMembers = new ArrayList<>();
        return v;
      }
    } else if (n instanceof MethodDecl) {
      MethodDecl md = (MethodDecl) n;
      MethodInstance mi = md.methodInstance();
      if (needsMemoization(mi)) {
        Memoizer v = (Memoizer) this.copy();
        v.flagName = "$memFlag" + (freshTid++);
        v.valueName = "$memValue" + (freshTid++);
        return v;
      }
    }
    return this;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    Memoizer mv = (Memoizer) v;

    if (n instanceof ClassDecl) {
      ClassDecl cd = (ClassDecl) n;
      if (mv.addedMembers != null && !mv.addedMembers.isEmpty()) {
        List<ClassMember> members = new ArrayList<>();
        members.addAll(cd.body().members());
        members.addAll(mv.addedMembers);
        return cd.body(cd.body().members(members));
      }
    } else if (n instanceof MethodDecl) {
      MethodDecl md = (MethodDecl) n;
      // XXX suppose ProxyRewriter keeps the method instance around.
      MethodInstance mi = md.methodInstance();
      if (needsMemoization(mi)) {
        return mv.transform(md, mi);
      }
    } else if (n instanceof Return) {
      Return ret = (Return) n;
      if (ret.expr() != null && valueName != null) {
        Expr e =
            nf.FieldAssign(
                Position.compilerGenerated(),
                nf.Field(Position.compilerGenerated(),
                    nf.This(Position.compilerGenerated()),
                    nf.Id(Position.compilerGenerated(), valueName)),
                Assign.ASSIGN, ret.expr());
        return ret.expr(e);
      }
    }

    return n;
  }

  protected MethodDecl transform(MethodDecl md, MethodInstance mi) {
    Id flagId = nf.Id(Position.compilerGenerated(), flagName);
    Id valueId = nf.Id(Position.compilerGenerated(), valueName);

    // XXX no need to generate field instance?
    FieldDecl flagDecl =
        nf.FieldDecl(Position.compilerGenerated(), Flags.PRIVATE.Transient(),
            nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Boolean()),
            flagId, nf.BooleanLit(Position.compilerGenerated(), false));
    addedMembers.add(flagDecl);
    FieldDecl valueDecl =
        nf.FieldDecl(
            Position.compilerGenerated(),
            Flags.PRIVATE.Transient(),
            nf.CanonicalTypeNode(Position.compilerGenerated(), mi.returnType()),
            valueId);
    addedMembers.add(valueDecl);

    List<Stmt> stmts = new ArrayList<>();

    stmts.add(nf.If(
        Position.compilerGenerated(),
        nf.Field(Position.compilerGenerated(),
            nf.This(Position.compilerGenerated()), flagId),
        nf.Return(
            Position.compilerGenerated(),
            nf.Field(Position.compilerGenerated(),
                nf.This(Position.compilerGenerated()), valueId))));
    stmts.add(nf.Eval(Position.compilerGenerated(), nf.FieldAssign(
        Position.compilerGenerated(),
        nf.Field(Position.compilerGenerated(),
            nf.This(Position.compilerGenerated()), flagId), Assign.ASSIGN,
        nf.BooleanLit(Position.compilerGenerated(), true))));

    stmts.addAll(md.body().statements());

    return (MethodDecl) md.body(nf.Block(md.body().position(), stmts));
  }

  protected boolean needsMemoization(MethodInstance mi) {
    if (addedMembers == null || mi == null) return false;
    // XXX HACK!
    ClassType ct = (ClassType) mi.container();
    return mi.name().equals("hashCode") && mi.formalTypes().isEmpty()
        && ct.fullName().equals("fabric.lang.security.PairLabel");
  }

  private static int freshTid = 0;
}
