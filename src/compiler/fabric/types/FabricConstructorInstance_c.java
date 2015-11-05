package fabric.types;

import java.util.Iterator;
import java.util.List;

import jif.types.Assertion;
import jif.types.JifConstructorInstance_c;
import jif.types.LabelSubstitution;
import jif.types.VarMap;
import jif.types.label.Label;

import polyglot.main.Report;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;

/**
 * Implementation of <code>FabricConstructorInstance<code>.
 */
public class FabricConstructorInstance_c extends JifConstructorInstance_c
  implements FabricConstructorInstance {

  protected Label beginAccessLab;
  protected boolean isDefaultBeginAccess;
  protected Label endAccessLab;
  protected boolean isDefaultEndAccess;

  public FabricConstructorInstance_c(FabricTypeSystem ts, Position pos,
            ClassType container, Flags flags,
            Label pcBound, boolean isDefaultPCBound,
            Label beginAccessLab, boolean isDefaultBeginAccess,
            Label returnLabel, boolean isDefaultReturnLabel,
            Label endAccessLab, boolean isDefaultEndAccess,
            List<? extends Type> formalTypes, List<Label> formalArgLabels,
            List<? extends Type> excTypes, List<Assertion> constraints) {
    super(ts, pos, container, flags, pcBound, isDefaultPCBound, returnLabel,
        isDefaultReturnLabel, formalTypes, formalArgLabels, excTypes,
        constraints);

    this.beginAccessLab = beginAccessLab;
    this.isDefaultBeginAccess = isDefaultBeginAccess;
    this.endAccessLab = endAccessLab;
    this.isDefaultEndAccess = isDefaultEndAccess;
  }
  
  @Override
  public Label beginAccessLabel() {
    return beginAccessLab;
  }

  @Override
  public boolean isDefaultBeginAccess() {
    return isDefaultBeginAccess;
  }
  
  @Override
  public void setBeginAccessLabel(Label p, boolean isDefault) {
    isDefaultBeginAccess = isDefault;
    beginAccessLab = p;
  }

  @Override
  public Label endAccessLabel() {
    return endAccessLab;
  }

  @Override
  public boolean isDefaultEndAccess() {
    return isDefaultEndAccess;
  }

  @Override
  public void setEndAccessLabel(Label p, boolean isDefault) {
    isDefaultEndAccess = isDefault;
    endAccessLab = p;
  }

  // Mostly stolen from JifMethodInstance_c's implementation.
  @Override
  public String toString() {
    String s = "constructor " + flags.translate() + container;

    if (pcBound != null) {
      s += pcBound.toString();
    }

    //Add begin access label.
    if (beginAccessLab != null) {
      s += "@" + beginAccessLab.toString();
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
      if (endAccessLab != null) {
        //Add end access label.
        s += "@" + endAccessLab.toString();
      }
    } else if (endAccessLab != null) {
      //Add end access label.
      s += ":@" + endAccessLab.toString();
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
    sb.append(container.toString());
    if (!isDefaultPCBound() || Report.should_report(Report.debug, 1)) {
      sb.append(pcBound);
    }
    if (!isDefaultBeginAccess() || Report.should_report(Report.debug, 1)) {
      // Add begin access label
      sb.append("@");
      sb.append(beginAccessLab);
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
      if (!isDefaultEndAccess() || Report.should_report(Report.debug, 1)) {
        // Add end access label
        sb.append("@");
        sb.append(endAccessLab);
      }
    } else if (!isDefaultEndAccess() || Report.should_report(Report.debug, 1)) {
      // Add end access label
      sb.append(":@");
      sb.append(endAccessLab);
    }
    return sb.toString();
  }

  @Override
  public boolean isCanonical() {
    return beginAccessLab.isCanonical() && endAccessLab.isCanonical() &&
      super.isCanonical();
  }

  @Override
  public void subst(VarMap bounds) {
    // Tom: Hoping the casts I'm doing here are okay...
    this.beginAccessLab = bounds.applyTo(this.beginAccessLab);
    this.endAccessLab = bounds.applyTo(this.endAccessLab);
    super.subst(bounds);
  }

  @Override
  public void subst(LabelSubstitution subst) throws SemanticException {
    // Tom: Hoping the casts I'm doing here are okay...
    setBeginAccessLabel(beginAccessLabel().subst(subst), isDefaultBeginAccess());
    setEndAccessLabel(endAccessLabel().subst(subst), isDefaultEndAccess());
    super.subst(subst);
  }

  //TODO: debugString?
}
