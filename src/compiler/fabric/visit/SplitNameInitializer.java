package fabric.visit;

import java.util.HashMap;
import java.util.Map;

import fabric.types.FabricClassType;
import fabric.types.FabricFieldInstance;
import fabric.types.FabricTypeSystem;
import jif.translate.ClassDeclToJavaExt_c;
import jif.types.label.Label;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.FieldDecl;
import polyglot.ast.Lang;
import polyglot.ast.Node;
import polyglot.visit.NodeVisitor;

/**
 * Initializes the split class names for the fields of any ClassDecls being
 * compiled.
 */
public class SplitNameInitializer extends NodeVisitor {

  protected final FabricTypeSystem ts;

  public SplitNameInitializer(Lang lang, FabricTypeSystem ts) {
    super(lang);
    this.ts = ts;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof ClassDecl) {
      // Make sure this isn't an interface.
      ClassDecl classDecl = (ClassDecl) n;
      if (classDecl.flags().isInterface()) return n;

      // Make sure this is a Fabric class; otherwise, instances won't be
      // persistent.
      if (!ts.isFabricClass(classDecl.type())) return n;

      // Initialize the split class names of the fields declared in the
      // ClassDecl.
      Map<Label, String> splitMap = new HashMap<>();

      for (ClassMember member : classDecl.body().members()) {
        if (!(member instanceof FieldDecl)) continue;

        // Leave static fields alone.
        FieldDecl fieldDecl = (FieldDecl) member;
        if (fieldDecl.flags().isStatic()) continue;

        // Have a FieldDecl. Get its update label.
        FabricFieldInstance ffi =
            (FabricFieldInstance) fieldDecl.fieldInstance();
        Label fieldLabel = ffi.label().simplify();

        // Convert the update label into the name of a split fragment.
        String splitName = splitMap.get(fieldLabel);
        if (splitName == null) {
          // No fragment for this label yet, so add one.
          splitName =
              ClassDeclToJavaExt_c.constructorTranslatedName(classDecl.type())
                  + "_split_" + splitMap.size();
          splitMap.put(fieldLabel, splitName);
        }

        // Annotate the ClassType with a mapping from the field name to the
        // name of the split fragment.
        FabricClassType ct = (FabricClassType) classDecl.type();
        ct.setSplitClassName(fieldDecl, splitName);
      }
    }

    return n;
  }

}
