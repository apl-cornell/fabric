package fabric.types;

import java.util.Iterator;
import java.util.List;

import jif.types.Assertion;
import jif.types.JifMethodInstance_c;
import jif.types.LabelSubstitution;
import jif.types.VarMap;
import jif.types.label.ConfPolicy;
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

  protected ConfPolicy beginAccessPol;
  protected boolean isDefaultBeginAccess;
  protected ConfPolicy endConfPol;
  protected boolean isDefaultEndConf;

  public FabricMethodInstance_c(FabricTypeSystem ts, Position pos,
            ReferenceType container, Flags flags, Type returnType, String name,
            Label pcBound, boolean isDefaultPCBound,
            ConfPolicy beginAccessPol, boolean isDefaultBeginAccess,
            ConfPolicy endConfPol, boolean isDefaultEndConf,
            List<? extends Type> formalTypes, List<Label> formalArgLabels,
            Label returnLabel, boolean isDefaultReturnLabel,
            List<? extends Type> excTypes, List<Assertion> constraints) {
    super(ts, pos, container, flags, returnType, name, pcBound,
        isDefaultPCBound, formalTypes, formalArgLabels, returnLabel,
        isDefaultReturnLabel, excTypes, constraints);

    this.beginAccessPol = beginAccessPol;
    this.isDefaultBeginAccess = isDefaultBeginAccess;
    this.endConfPol = endConfPol;
    this.isDefaultEndConf = isDefaultEndConf;
  }
  
  @Override
  public ConfPolicy beginAccessPolicy() {
    return beginAccessPol;
  }

  @Override
  public boolean isDefaultBeginAccess() {
    return isDefaultBeginAccess;
  }
  
  @Override
  public void setBeginAccessPolicy(ConfPolicy p, boolean isDefault) {
    isDefaultBeginAccess = isDefault;
    beginAccessPol = p;
  }

  @Override
  public ConfPolicy endConfPolicy() {
    return endConfPol;
  }

  @Override
  public boolean isDefaultEndConf() {
    return isDefaultEndConf;
  }

  @Override
  public void setEndConfPolicy(ConfPolicy p, boolean isDefault) {
    isDefaultEndConf = isDefault;
    endConfPol = p;
  }

  // Mostly stolen from JifMethodInstance_c's implementation.
  @Override
  public String toString() {
    String s = "method " + flags.translate() + returnType + " " + name;

    if (pcBound != null) {
      s += pcBound.toString();
    }

    //Add begin access label.
    if (beginAccessPol != null) {
      s += "@" + beginAccessPol.toString();
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
      if (endConfPol != null) {
        //Add end confidentiality label.
        s += "@" + endConfPol.toString();
      }
    } else if (endConfPol != null) {
      //Add end confidentiality label.
      s += ":@" + endConfPol.toString();
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
    if (!isDefaultBeginAccess() || Report.should_report(Report.debug, 1)) {
      // Add begin access label
      sb.append("@");
      sb.append(beginAccessPol);
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
      if (!isDefaultEndConf() || Report.should_report(Report.debug, 1)) {
        // Add end conf label
        sb.append("@");
        sb.append(endConfPol);
      }
    } else if (!isDefaultEndConf() || Report.should_report(Report.debug, 1)) {
      // Add end conf label
      sb.append(":@");
      sb.append(endConfPol);
    }
    return sb.toString();
  }

  @Override
  public boolean isCanonical() {
    return beginAccessPol.isCanonical() && endConfPol.isCanonical() &&
      super.isCanonical();
  }

  @Override
  public void subst(VarMap bounds) {
    // Tom: Hoping the casts I'm doing here are okay...
    this.beginAccessPol = (ConfPolicy) bounds.applyTo(this.beginAccessPol);
    this.endConfPol = (ConfPolicy) bounds.applyTo(this.endConfPol);
    super.subst(bounds);
  }

  @Override
  public void subst(LabelSubstitution subst) throws SemanticException {
    // Tom: Hoping the casts I'm doing here are okay...
    setBeginAccessPolicy((ConfPolicy) beginAccessPolicy().subst(subst),
        isDefaultBeginAccess());
    setEndConfPolicy((ConfPolicy) endConfPolicy().subst(subst),
        isDefaultEndConf());
    super.subst(subst);
  }

  //TODO: debugString?
}
