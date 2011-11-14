package fabric.translate;

import java.util.ArrayList;
import java.util.List;

import fabil.ast.FabILNodeFactory;
import fabil.types.FabILTypeSystem;
import fabric.types.FabricClassType;
import fabric.types.FabricTypeSystem;
import fabric.visit.FabricToFabilRewriter;

import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import jif.ast.JifClassDecl;
import jif.translate.ClassDeclToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import jif.types.JifPolyType;
import jif.types.label.Label;

public class ClassDeclToFabilExt_c extends ClassDeclToJavaExt_c {
  public static final String jifConstructorTranslatedName(ClassType ct) {
    return ClassDeclToJavaExt_c.constructorTranslatedName(ct);
  }


  @SuppressWarnings("unchecked")
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {

    ClassDecl cd = (ClassDecl) super.toJava(rw);

    cd = cd.body(addLabelInitializer(cd.body(), rw));

    FabILNodeFactory nf = (FabILNodeFactory) rw.nodeFactory();
    FabILTypeSystem ts = (FabILTypeSystem) rw.java_ts();

    TypeNode worker =
        nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Worker());

    List<ClassMember> members =
        new ArrayList<ClassMember>(cd.body().members().size() + 1);
    members.add(nf.FieldDecl(
        Position.compilerGenerated(),
        Flags.FINAL.Static(),
        worker,
        nf.Id(Position.compilerGenerated(), "worker$"),
        nf.Call(Position.compilerGenerated(), worker,
            nf.Id(Position.compilerGenerated(), "getWorker"))));
    members.addAll(cd.body().members());

    return cd.body(cd.body().members(members));
  }

  @Override
  protected ClassBody addInitializer(ClassBody cb, JifToJavaRewriter rw) {
    List<Stmt> inits = new ArrayList<Stmt>(rw.getInitializations());
    rw.getInitializations().clear();
    return cb.addMember(rw.qq().parseMember("private void %s() { %LS }",
        INITIALIZATIONS_METHOD_NAME, inits));
  }

  /**
   * Create a method for initializing update and access labels
   * 
   * @throws SemanticException
   *           if the field or access label cannot be translated
   */
  protected ClassBody addLabelInitializer(ClassBody cb, JifToJavaRewriter rw)
      throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) rw.jif_ts();
    boolean sigMode = ((FabricToFabilRewriter) rw).inSignatureMode();
    
    //FIXME: why is rw.currentClass() null?
    ClassDecl n = (ClassDecl) node();
    FabricClassType ct = (FabricClassType)n.type();

    if (!sigMode && ts.isFabricClass(ct)) {

      // translate the labels to FabIL
      Label updateLabel = ct.classUpdateLabel();
      Label accessLabel = ct.classAccessLabel();

      if (updateLabel == null || accessLabel == null) {
        throw new InternalCompilerError("Null field or access label");
      }
      Expr updateLabelExpr = rw.labelToJava(updateLabel);
      Expr accessLabelExpr = rw.labelToJava(accessLabel);

      return cb.addMember(rw.qq().parseMember(
          "public Object %s() { " + "this.$updateLabel = %E;  "
              + "this.$accessPolicy = %E.confPolicy();" + "return this;" + "}",
          FabricToFabilRewriter.LABEL_INITIALIZER_METHOD_NAME, updateLabelExpr, accessLabelExpr));
    }

    return cb;
  }
}
