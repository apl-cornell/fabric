/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric.extension;

import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import fabric.types.FabricContext;
import fabric.types.FabricTypeSystem;

/**
 * This class provides common functionality to the New and NewArray for managing
 * a location field
 */
public abstract class LocatedExt_c extends NodeExt_c implements FabricExt {
  protected Expr location;
  protected Principal storePrincipal;

  public Expr location() {
    return location;
  }

  public LocatedExt_c location(Expr location) {
    LocatedExt_c result = (LocatedExt_c) copy();
    result.location = location;
    return result;
  }

  public Principal storePrincipal() {
    return storePrincipal;
  }

  public LocatedExt_c storePrincipal(Principal p) {
    LocatedExt_c result = (LocatedExt_c) this.copy();
    result.storePrincipal = p;
    return result;
  }

  @Override
  public void dump(CodeWriter w) {
    super.dump(w);
    w.write("(location ");
    if (location == null) {
      w.write("null");
    } else {
      location.dump(w);
    }
    w.write(")");
  }

  /**
   * Returns a precise bound on the label of the reference of the allocated
   * object. Can only be called during label checking of a constructor call.
   */
  protected abstract Label referenceLabel(FabricContext ctx);

  /**
   * Checks that the location is compatible with the <code>objectLabel</code>
   * and <code>accessLabel</code>
   * 
   * @param lc
   * @param objectLabel
   * @param accessLabel
   */
  public void labelCheck(LabelChecker lc, final Label objectLabel,
      final Label accessLabel) throws SemanticException {
    Node n = node();

    // TODO if storePrincipal() returns null, then the store principal of the
    // containing class should be used.

    // TODO: if location == null, then we need to do something fancy.
    if (location() != null && objectLabel != null) {
      FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
      JifContext A = lc.jifContext();
      A = (JifContext) n.del().enterScope(A);

      lc.constrain(
          new NamedLabel("access label", accessLabel),
          LabelConstraint.LEQ,
          new NamedLabel("{*->store}", ts.pairLabel(Position
              .compilerGenerated(), ts.readerPolicy(
              Position.compilerGenerated(),
              ts.topPrincipal(Position.compilerGenerated()), storePrincipal()),
              ts.topIntegPolicy(Position.compilerGenerated()))), A.labelEnv(),
          n.position(), new ConstraintMessage() {
            @Override
            public String msg() {
              return "The store should be trusted enough to enforce the confidentiality"
                  + " of the access label";
            }
          });

      // ////////////////////////////////////////////////////////////////
      // Update label should be enforcable by store.
      // For added precision, substitute for {this}
      // ////////////////////////////////////////////////////////////////
      lc.constrain(
          new NamedLabel("object update label", objectLabel),
          LabelConstraint.LEQ,
          new NamedLabel("{*->store}", ts.pairLabel(Position
              .compilerGenerated(), ts.readerPolicy(
              Position.compilerGenerated(),
              ts.topPrincipal(Position.compilerGenerated()), storePrincipal()),
              ts.topIntegPolicy(Position.compilerGenerated()))), A.labelEnv(),
          n.position(), new ConstraintMessage() {
            @Override
            public String msg() {
              return "L <= {*->store} for new C@store() where the update label of C is L.";
            }

            @Override
            public String detailMsg() {
              return "The object being created on the store "
                  + storePrincipal().toString()
                  + " should be readable by the store's principal";
            }

            @Override
            public String technicalMsg() {
              return "The label "
                  + objectLabel.toString()
                  + " should not be more restrictive than the confidentiality label of the store's principal joined with the top integrity label";
            }
          });

      lc.constrain(
          new NamedLabel("{*<-store}", ts.pairLabel(Position
              .compilerGenerated(), ts.bottomConfPolicy(Position
              .compilerGenerated()), ts.writerPolicy(
              Position.compilerGenerated(),
              ts.topPrincipal(Position.compilerGenerated()), storePrincipal()))),
          LabelConstraint.LEQ, new NamedLabel("object update label",
              objectLabel), A.labelEnv(), n.position(),
          new ConstraintMessage() {
            @Override
            public String msg() {
              return "{*<-store} <= L for new C@store() where the update label of C is L.";
            }

            @Override
            public String detailMsg() {
              return "The object being created on the store "
                  + storePrincipal().toString()
                  + " should be writeable by the store's principal";
            }

            @Override
            public String technicalMsg() {
              return "The integrity label of the store's principal joined with the bottom confidentiality label should not be more restrictive than the label "
                  + objectLabel.toString();
            }
          });

      // ////////////////////////////////////////////////////////////////////////
      // Label on the reference to the new object should be enforceable by
      // store.
      // ////////////////////////////////////////////////////////////////////////
      final Label referenceLabel = referenceLabel((FabricContext) A);

      lc.constrain(
          new NamedLabel("label on new allocation", referenceLabel),
          LabelConstraint.LEQ,
          new NamedLabel("{*->store}", ts.pairLabel(Position
              .compilerGenerated(), ts.readerPolicy(
              Position.compilerGenerated(),
              ts.topPrincipal(Position.compilerGenerated()), storePrincipal()),
              ts.topIntegPolicy(Position.compilerGenerated()))), A.labelEnv(),
          n.position(), new ConstraintMessage() {
            @Override
            public String msg() {
              return "L <= {*->store} for new C@store() where L is the label of "
                  + "the reference to the newly allocated object";
            }

            @Override
            public String detailMsg() {
              return "The reference to the object being created on the store "
                  + storePrincipal().toString()
                  + " should be readable by the store's principal";
            }

            @Override
            public String technicalMsg() {
              return "The label "
                  + referenceLabel.toString()
                  + " should not be more restrictive than the confidentiality label of "
                  + "the store's principal joined with the top integrity label";
            }
          });

    }
  }
}
