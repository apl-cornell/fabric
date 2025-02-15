package fabil.ast;

import fabil.types.FabILFlags;
import fabil.types.FabILTypeSystem;

import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.Id;
import polyglot.ast.Javadoc;
import polyglot.ast.TypeNode;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.TypeChecker;

public class FieldDecl_c extends polyglot.ast.FieldDecl_c {
  public FieldDecl_c(Position pos, Flags flags, TypeNode type, Id name,
      Expr init, Javadoc javadoc, Ext ext) {
    super(pos, flags, type, name, init, javadoc, ext);
  }

  public FieldDecl_c(Position pos, Flags flags, TypeNode type, Id name,
      Expr init, Javadoc javadoc) {
    this(pos, flags, type, name, init, javadoc, null);
  }

  @Deprecated
  public FieldDecl_c(Position pos, Flags flags, TypeNode type, Id name,
      Expr init) {
    this(pos, flags, type, name, init, null, null);
  }

  @Deprecated
  public FieldDecl_c(Position pos, Flags flags, TypeNode type, Id name,
      Expr init, Ext ext) {
    this(pos, flags, type, name, init, null, ext);
  }

  @Override
  public FieldDecl_c typeCheck(TypeChecker tc) throws SemanticException {
    FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();
    FieldDecl_c result = (FieldDecl_c) super.typeCheck(tc);

    // Verify that we can actually serialize this field, if necessary.
    //
    // Fields must be serializable if they are neither:
    // static final/immutable
    // transient
    //
    // Fields are serializable if they are a Fabric type.
    if (ts.isFabricType(result.fieldInstance().container())
        && !ts.isFabricType(result.declType())
        && !(result.flags().isTransient()
            || (result.flags().isStatic() && (result.flags().isFinal()
                || result.flags().contains(FabILFlags.IMMUTABLE))))) {
      throw new SemanticException("All fields of Fabric objects must be either "
          + "transient, static final, or Fabric type "
          + "to ensure proper serialization.", result.position());
    }

    return result;
  }
}
