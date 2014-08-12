package fabil.visit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import polyglot.ast.Block;
import polyglot.ast.Expr;
import polyglot.ast.For;
import polyglot.ast.ForInit;
import polyglot.ast.Formal;
import polyglot.ast.Local;
import polyglot.ast.LocalAssign;
import polyglot.ast.LocalDecl;
import polyglot.ast.Node;
import polyglot.ast.ProcedureDecl;
import polyglot.ast.Stmt;
import polyglot.ast.Unary;
import polyglot.ast.Unary.Operator;
import polyglot.types.LocalInstance;
import polyglot.visit.NodeVisitor;
import fabil.ast.Atomic;

public class UpdatedVariableFinder extends NodeVisitor {
  protected Set<LocalInstance> declared = new HashSet<>();
  protected Set<LocalInstance> updated = new HashSet<>();

  @Override
  public NodeVisitor enter(Node n) {
    if (n instanceof ProcedureDecl) {
      UpdatedVariableFinder v = (UpdatedVariableFinder) this.copy();
      v.declared = new HashSet<>();
      v.updated = new HashSet<>();

      ProcedureDecl pd = (ProcedureDecl) n;
      for (Formal f : pd.formals()) {
        v.declared.add(f.localInstance());
      }

      return v;
    } else if (n instanceof Block) {
      UpdatedVariableFinder v = (UpdatedVariableFinder) this.copy();
      v.declared = new HashSet<>();
      v.declared.addAll(declared);

      Block b = (Block) n;
      for (Stmt s : b.statements()) {
        if (s instanceof LocalDecl) {
          LocalDecl ld = (LocalDecl) s;
          v.declared.add(ld.localInstance());
        }
      }

      return v;
    } else if (n instanceof For) {
      UpdatedVariableFinder v = (UpdatedVariableFinder) this.copy();
      v.declared = new HashSet<>();
      v.declared.addAll(declared);

      For f = (For) n;
      for (ForInit s : f.inits()) {
        if (s instanceof LocalDecl) {
          LocalDecl ld = (LocalDecl) s;
          v.declared.add(ld.localInstance());
        }
      }

      return v;
    }

    return this;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof LocalAssign) {
      Local l = ((LocalAssign) n).left();
      updated.add(l.localInstance());
    } else if (n instanceof Unary) {
      Unary unary = (Unary) n;
      Expr expr = unary.expr();
      if (expr instanceof Local && isIncDec(unary)) {
        updated.add(((Local) expr).localInstance());
      }
    } else if (n instanceof Atomic) {
      Atomic a = (Atomic) n;
      List<LocalInstance> updatedLocals = new ArrayList<>();
      for (LocalInstance li : updated) {
        if (declared.contains(li)) {
          updatedLocals.add(li);
        }
      }
      return a.updatedLocals(updatedLocals);
    }

    return n;
  }

  /**
   * @return true iff the given Unary is an increment/decrement operation.
   */
  private boolean isIncDec(Unary n) {
    Operator o = n.operator();
    return o == Unary.POST_DEC || o == Unary.POST_INC || o == Unary.PRE_DEC
        || o == Unary.PRE_INC;
  }
}
