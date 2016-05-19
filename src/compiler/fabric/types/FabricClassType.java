package fabric.types;

import codebases.types.CodebaseClassType;
import jif.types.JifClassType;
import jif.types.label.Label;
import polyglot.ast.FieldDecl;
import polyglot.types.SemanticException;

public interface FabricClassType
    extends JifClassType, CodebaseClassType, FabricReferenceType {

  /**
   * Objects are split into a "root object" that points to several "fragment
   * object" that contain the actual fields. This method computes the update
   * label for the root object as a meet (in the information flow ordering) of
   * the object's fields. If the object has no fields, then null is returned.
   *
   * @return the update label for the root object that will result from object
   *           splitting, or null if the class and its superclasses have no
   *           fields.
   */
  Label rootObjectLabel() throws SemanticException;

  /**
   * Sets the class name of the split fragment in which the given field will
   * reside after translation to FabIL. The split fragment will live as a class,
   * nested in the class corresponding to this class type. It is assumed that
   * the given field decl is a member of this class.
   */
  void setSplitClassName(FieldDecl fieldDecl, String splitName);

  /**
   * Gets the class name of the split fragment in which the field with the given
   * name will reside after translation to FabIL, or {@code null} if the field
   * should not be moved into a fragment. The split fragment will live as a
   * class, nested in the class corresponding to this class type. It is assumed
   * that the given field decl is a member of this class.
   */
  String splitClassName(String fieldName);
}
