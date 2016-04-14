package fabric.types;

import java.util.Iterator;
import java.util.List;

import jif.types.Assertion;
import jif.types.JifMethodInstance_c;
import jif.types.LabelSubstitution;
import jif.types.VarMap;
import jif.types.label.Label;

import polyglot.main.Report;
import polyglot.types.Flags;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;

/**
 * Implementation of <code>FabricMethodInstance<code>.
 */
public class FabricMethodInstance_c extends JifMethodInstance_c
  implements FabricMethodInstance {

  protected Label beginConflictLab;
  protected boolean isDefaultBeginConflict;
  protected Label endConflictLab;
  protected boolean isDefaultEndConflict;

  public FabricMethodInstance_c(FabricTypeSystem ts, Position pos,
            ReferenceType container, Flags flags, Type returnType, String name,
            Label pcBound, boolean isDefaultPCBound,
            Label beginConflictLab, boolean isDefaultBeginConflict,
            List<? extends Type> formalTypes, List<Label> formalArgLabels,
            Label returnLabel, boolean isDefaultReturnLabel,
            Label endConflictLab, boolean isDefaultEndConflict,
            List<? extends Type> excTypes, List<Assertion> constraints) {
    super(ts, pos, container, flags, returnType, name, pcBound,
        isDefaultPCBound, formalTypes, formalArgLabels, returnLabel,
        isDefaultReturnLabel, excTypes, constraints);

    this.beginConflictLab = beginConflictLab;
    this.isDefaultBeginConflict = isDefaultBeginConflict;
    this.endConflictLab = endConflictLab;
    this.isDefaultEndConflict = isDefaultEndConflict;
  }
  
  @Override
  public Label beginConflictLabel() {
    return beginConflictLab;
  }

  @Override
  public boolean isDefaultBeginConflict() {
    return isDefaultBeginConflict;
  }
  
  @Override
  public void setBeginConflictLabel(Label p, boolean isDefault) {
    isDefaultBeginConflict = isDefault;
    beginConflictLab = p;
  }

  @Override
  public Label endConflictLabel() {
    return endConflictLab;
  }

  @Override
  public boolean isDefaultEndConflict() {
    return isDefaultEndConflict;
  }

  @Override
  public void setEndConflictLabel(Label p, boolean isDefault) {
    isDefaultEndConflict = isDefault;
    endConflictLab = p;
  }

  // Mostly stolen from JifMethodInstance_c's implementation.
  @Override
  public String toString() {
    String s = "method " + flags.translate() + returnType + " " + name;

    if (pcBound != null) {
      s += pcBound.toString();
    }

    //Add begin conflict label.
    if (beginConflictLab != null) {
      s += "@" + beginConflictLab.toString();
    }

    s += "(";

    for (Iterator<Type> i = formalTypes.iterator(); i.hasNext();) {
      Type t = i.next();
      s += t.toString();

      if (i.hasNext()) {
        s += ", ";
      }
    }

    s += ")";

    if (returnLabel != null) {
      s += " : " + returnLabel.toString();
      if (endConflictLab != null) {
        //Add end confidentiality label.
        s += "@" + endConflictLab.toString();
      }
    } else if (endConflictLab != null) {
      //Add end confidentiality label.
      s += ":@" + endConflictLab.toString();
    }

    if (!this.throwTypes.isEmpty()) {
      s += " throws (";

      for (Iterator<Type> i = throwTypes.iterator(); i.hasNext();) {
        Type t = i.next();
        s += t.toString();

        if (i.hasNext()) {
          s += ", ";
        }
      }

      s += ")";
    }

    if (!constraints.isEmpty()) {
      s += " where ";

      for (Iterator<Assertion> i = constraints.iterator(); i.hasNext();) {
        Assertion c = i.next();
        s += c.toString();

        if (i.hasNext()) {
          s += ", ";
        }
      }
    }

    return s;
  }

  // Mostly stolen from JifMethodInstance_c's implementation.
  @Override
  public String fullSignature() {
    StringBuffer sb = new StringBuffer();
    sb.append(name);
    if (!isDefaultPCBound() || Report.should_report(Report.debug, 1)) {
      sb.append(pcBound);
    }
    if (!isDefaultBeginConflict() || Report.should_report(Report.debug, 1)) {
      // Add begin conflict label
      sb.append("@");
      sb.append(beginConflictLab);
    }
    sb.append("(");

    for (Iterator<Type> i = formalTypes.iterator(); i.hasNext();) {
      Type t = i.next();
      if (Report.should_report(Report.debug, 1)) {
        sb.append(t.toString());
      } else {
        if (t.isClass()) {
          sb.append(t.toClass().name());
        } else {
          sb.append(t.toString());
        }
      }

      if (i.hasNext()) {
        sb.append(", ");
      }
    }

    sb.append(")");
    if (!isDefaultReturnLabel() || Report.should_report(Report.debug, 1)) {
      sb.append(":");
      sb.append(returnLabel);
      if (!isDefaultEndConflict() || Report.should_report(Report.debug, 1)) {
        // Add end conf label
        sb.append("@");
        sb.append(endConflictLab);
      }
    } else if (!isDefaultEndConflict() || Report.should_report(Report.debug, 1)) {
      // Add end conf label
      sb.append(":@");
      sb.append(endConflictLab);
    }
    return sb.toString();
  }

  @Override
  public boolean isCanonical() {
    return beginConflictLab.isCanonical() && endConflictLab.isCanonical() &&
      super.isCanonical();
  }

  @Override
  public void subst(VarMap bounds) {
    // Tom: Hoping the casts I'm doing here are okay...
    this.beginConflictLab = bounds.applyTo(this.beginConflictLab);
    this.endConflictLab = bounds.applyTo(this.endConflictLab);
    super.subst(bounds);
  }

  @Override
  public void subst(LabelSubstitution subst) throws SemanticException {
    // Tom: Hoping the casts I'm doing here are okay...
    setBeginConflictLabel(beginConflictLabel().subst(subst),
        isDefaultBeginConflict());
    setEndConflictLabel(endConflictLabel().subst(subst), isDefaultEndConflict());
    super.subst(subst);
  }

  //TODO: debugString?
}
