package fabric.types;

import java.net.URI;

import codebases.types.CodebaseClassType;
import jif.types.JifSubst;
import jif.types.JifSubstClassType_c;
import jif.types.JifTypeSystem;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;
import polyglot.ast.FieldDecl;
import polyglot.types.ClassType;
import polyglot.types.SemanticException;
import polyglot.util.Position;

public class FabricSubstClassType_c extends JifSubstClassType_c
    implements FabricSubstType {
  public FabricSubstClassType_c(JifTypeSystem ts, Position pos, ClassType base,
      JifSubst subst) {
    super(ts, pos, base, subst);
  }

  protected ConfPolicy accessPolicy;

  @Override
  public Label updateLabel() {
    FabricParsedClassType base = (FabricParsedClassType) base();
    Label l = base.updateLabel();
    if (l == null) return null;

    JifSubst subst = (JifSubst) subst();
    return subst.substLabel(base.updateLabel());
  }

  @Override
  public Label rootObjectLabel() throws SemanticException {
    FabricParsedClassType base = (FabricParsedClassType) base();
    Label l = base.rootObjectLabel();
    if (l == null) return null;

    JifSubst subst = (JifSubst) subst();
    return subst.substLabel(base.rootObjectLabel());
  }

  @Override
  public ConfPolicy accessPolicy() throws SemanticException {
    if (accessPolicy == null) accessPolicy = defaultAccessPolicy();
    return accessPolicy;
  }

  @Override
  public URI canonicalNamespace() {
    return ((CodebaseClassType) base).canonicalNamespace();
  }

  protected ConfPolicy defaultAccessPolicy() throws SemanticException {
    FabricParsedClassType base = (FabricParsedClassType) base();
    ConfPolicy c = base.accessPolicy();
    if (c == null) return null;
    FabricTypeSystem fts = (FabricTypeSystem) ts;
    Label l = fts.toLabel(c);
    JifSubst subst = (JifSubst) subst();
    return subst.substLabel(l).confProjection();
  }

  @Override
  public void setSplitClassName(FieldDecl fieldDecl, String splitName) {
    ((FabricClassType) base).setSplitClassName(fieldDecl, splitName);
  }

  @Override
  public String splitClassName(String fieldName) {
    return ((FabricClassType) base).splitClassName(fieldName);
  }
}
